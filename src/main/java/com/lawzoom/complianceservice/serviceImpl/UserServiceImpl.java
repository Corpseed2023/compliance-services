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
        // Step 1: Validate the email
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email already exists: " + userRequest.getEmail());
        }

        Roles role = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + userRequest.getRoleId()));
        Department department = departmentRepository.findById(userRequest.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + userRequest.getDepartmentId()));
        Designation designation = designationRepository.findById(userRequest.getDesignationId())
                .orElseThrow(() -> new RuntimeException("Designation not found with ID: " + userRequest.getDesignationId()));
        ResourceType resourceType = resourceTypeRepository.findById(userRequest.getTypeOfResource())
                .orElseThrow(() -> new RuntimeException("ResourceType not found with ID: " + userRequest.getTypeOfResource()));
        Subscription subscription = subscriptionRepository.findById(userRequest.getSubscriptionId())
                .orElseThrow(() -> new RuntimeException("Subscription not found with ID: " + userRequest.getSubscriptionId()));

        // Step 2: Create User
        User user = new User();
        user.setUserName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setEnable(userRequest.isEnable());
        user.setDepartment(department);
        user.setDesignation(designation);
        user.setResourceType(resourceType);
        user.getRoles().add(role);

        User tempUser = userRepository.save(user);

        // Step 3: Create Subscriber
        Subscriber subscriber = new Subscriber();
        subscriber.setSuperAdmin(tempUser);
        subscriber.setSubscription(subscription);
        subscriber.setActive(true);

        Subscriber savedSubscriber = subscriberRepository.save(subscriber);

        // Link User with Subscriber
        tempUser.setSubscriber(savedSubscriber);
        User savedUser = userRepository.save(tempUser);

        // Step 4: Prepare Response
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


