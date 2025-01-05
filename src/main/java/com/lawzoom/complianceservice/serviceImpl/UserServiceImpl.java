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
        // Validate email
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new RuntimeException("Email already exists: " + userRequest.getEmail());
        }

        // Fetch and validate required entities
        Roles role = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + userRequest.getRoleId()));
        Department department = departmentRepository.findById(userRequest.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + userRequest.getDepartmentId()));
        Designation designation = designationRepository.findById(userRequest.getDesignationId())
                .orElseThrow(() -> new RuntimeException("Designation not found with ID: " + userRequest.getDesignationId()));
        ResourceType resourceType = resourceTypeRepository.findById(userRequest.getTypeOfResource())
                .orElseThrow(() -> new RuntimeException("ResourceType not found with ID: " + userRequest.getTypeOfResource()));

        User user = new User();
        user.setUserName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setEnable(userRequest.isEnable());
        user.setDepartment(department);
        user.setDesignation(designation);
        user.setResourceType(resourceType);
        user.getRoles().add(role);

        // Conditional logic based on role
        if ("Master".equalsIgnoreCase(role.getRoleName())) {
            // For Master: No subscription or subscriber required
            return saveUserAndPrepareResponse(user);
        } else {
            // For other roles: Subscription and Subscriber required
            Subscription subscription = subscriptionRepository.findById(userRequest.getSubscriptionId())
                    .orElseThrow(() -> new RuntimeException("Subscription not found with ID: " + userRequest.getSubscriptionId()));

            User tempUser = userRepository.save(user);

            Subscriber subscriber = new Subscriber();
            subscriber.setSuperAdmin(tempUser);
            subscriber.setSubscription(subscription);
            subscriber.setActive(true);

            Subscriber savedSubscriber = subscriberRepository.save(subscriber);

            // Link User with Subscriber
            tempUser.setSubscriber(savedSubscriber);
            User savedUser = userRepository.save(tempUser);

            return prepareUserResponse(savedUser);
        }
    }

    private UserResponse saveUserAndPrepareResponse(User user) {
        User savedUser = userRepository.save(user);
        return prepareUserResponse(savedUser);
    }

    private UserResponse prepareUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setDesignation(user.getDesignation().getName());
        response.setResourceType(user.getResourceType().getTypeOfResourceName());
        response.setEnable(user.isEnable());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }
}
