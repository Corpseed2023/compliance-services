package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.userDto.UserRequest;
import com.lawzoom.complianceservice.dto.userDto.UserResponse;
import com.lawzoom.complianceservice.model.user.*;
import com.lawzoom.complianceservice.repository.*;
import com.lawzoom.complianceservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        // Step 1: Check if the email already exists
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email already exists: " + userRequest.getEmail());
        }

        // Step 2: Validate and fetch Role
        Roles role = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + userRequest.getRoleId()));

        // Step 3: Validate and fetch Department
        Department department = departmentRepository.findById(userRequest.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + userRequest.getDepartmentId()));

        // Step 4: Validate and fetch Designation
        Designation designation = designationRepository.findById(userRequest.getDesignationId())
                .orElseThrow(() -> new RuntimeException("Designation not found with ID: " + userRequest.getDesignationId()));

        // Step 5: Validate and fetch ResourceType
        ResourceType resourceType = resourceTypeRepository.findById(userRequest.getTypeOfResource())
                .orElseThrow(() -> new RuntimeException("ResourceType not found with ID: " + userRequest.getTypeOfResource()));

        // Step 6: Validate Subscription
        Subscription subscription = subscriptionRepository.findById(userRequest.getSubscriptionId())
                .orElseThrow(() -> new RuntimeException("Subscription not found with ID: " + userRequest.getSubscriptionId()));

        // Step 7: Create a temporary User (without Subscriber)
        User user = new User();
        user.setUserName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setEnable(userRequest.isEnable());
        user.setDepartment(department);
        user.setDesignation(designation);
        user.setResourceType(resourceType);
        user.getRoles().add(role);

        // Save the User temporarily
        User tempUser = userRepository.save(user);

        // Step 8: Create and save Subscriber with User as Super Admin
        Subscriber subscriber = new Subscriber();
        subscriber.setSuperAdmin(tempUser); // Link User as Super Admin
        subscriber.setSubscription(subscription);
        subscriber.setActive(true);

        Subscriber savedSubscriber = subscriberRepository.save(subscriber);

        // Step 9: Update User with Subscriber reference
        tempUser.setSubscriber(savedSubscriber);
        User savedUser = userRepository.save(tempUser);

        // Step 10: Prepare and return the Response
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setDesignation(savedUser.getDesignation().getName());
        response.setResourceType(savedUser.getResourceType().getTypeOfResourceName());
        response.setEnable(savedUser.isEnable());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());

        return response;
    }



}


