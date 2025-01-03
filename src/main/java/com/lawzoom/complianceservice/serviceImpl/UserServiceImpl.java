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

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        // Check if the email already exists
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email already exists: " + userRequest.getEmail());
        }

        // Validate and fetch Role
        Roles role = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + userRequest.getRoleId()));

        // Validate and fetch Department
        Department department = departmentRepository.findById(userRequest.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + userRequest.getDepartmentId()));

        // Validate and fetch Designation
        Designation designation = designationRepository.findById(userRequest.getDesignationId())
                .orElseThrow(() -> new RuntimeException("Designation not found with ID: " + userRequest.getDesignationId()));

        // Validate and fetch ResourceType
        ResourceType resourceType = resourceTypeRepository.findById(userRequest.getTypeOfResource())
                .orElseThrow(() -> new RuntimeException("ResourceType not found with ID: " + userRequest.getTypeOfResource()));

        // Validate and fetch Subscriber
        Subscriber subscriber = null;
        if (userRequest.getSubscribedId() != null) {
            subscriber = subscriberRepository.findById(userRequest.getSubscribedId())
                    .orElseThrow(() -> new RuntimeException("Subscriber not found with ID: " + userRequest.getSubscribedId()));
        } else {
            // Create a new Subscriber if one doesn't exist
            subscriber = new Subscriber();
            subscriber.setSubscription(null); // Replace with a valid Subscription if needed
            subscriber.setActive(true);
            subscriber = subscriberRepository.save(subscriber);
        }

        // Build the User object
        User user = new User();
        user.setUserName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setEnable(userRequest.isEnable());
        user.setDepartment(department);
        user.setDesignation(designation);
        user.setResourceType(resourceType);
        user.setSubscriber(subscriber);
        user.getRoles().add(role);

        // Save User
        User savedUser = userRepository.save(user);

        // Update the Subscriber with the created User if needed
        subscriber.getUsers().add(savedUser);
        subscriberRepository.save(subscriber);

        // Prepare Response
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
