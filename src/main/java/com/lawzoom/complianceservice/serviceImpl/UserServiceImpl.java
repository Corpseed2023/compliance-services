package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.teamMemberDto.MemberRequest;
import com.lawzoom.complianceservice.dto.teamMemberDto.MemberResponse;
import com.lawzoom.complianceservice.dto.userDto.UserRequest;
import com.lawzoom.complianceservice.dto.userDto.UserResponse;
import com.lawzoom.complianceservice.model.user.*;
import com.lawzoom.complianceservice.repository.*;
import com.lawzoom.complianceservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        user.setMobile(userRequest.getMobile());

        // Conditional logic based on role
        User savedUser;
        if ("Master".equalsIgnoreCase(role.getRoleName())) {
            // For Master: No subscription or subscriber required
            savedUser = userRepository.save(user);
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
            savedUser = userRepository.save(tempUser);
        }

        // Prepare and return response
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setDesignation(savedUser.getDesignation().getName());
        response.setResourceType(savedUser.getResourceType().getTypeOfResourceName());
        response.setEnable(savedUser.isEnable());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());
        response.setMobile(savedUser.getMobile());



        return response;
    }

    @Override
    public MemberResponse createTeamMemberUser(MemberRequest memberRequest) {
        // Step 1: Check for duplicate email
        if (userRepository.existsByEmail(memberRequest.getEmail())) {
            throw new RuntimeException("Email already exists: " + memberRequest.getEmail());
        }

        // Step 2: Create and populate a new User entity
        User newUser = new User();
        newUser.setUserName(memberRequest.getName());
        newUser.setEmail(memberRequest.getEmail());
        newUser.setEnable(memberRequest.isEnable());

        // Step 3: Validate and set required entities
        newUser.setResourceType(resourceTypeRepository.findById(memberRequest.getTypeOfResource())
                .orElseThrow(() -> new RuntimeException("Resource type not found")));
        Designation designation = designationRepository.findById(memberRequest.getDesignationId())
                .orElseThrow(() -> new RuntimeException("Designation not found"));
        newUser.setDesignation(designation);

        Department department = departmentRepository.findById(memberRequest.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));
        newUser.setDepartment(department);

        Subscriber subscriber = subscriberRepository.findById(memberRequest.getSubscribedId())
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));
        newUser.setSubscriber(subscriber);

        User reportingManager = userRepository.findById(memberRequest.getReportingManagerId())
                .orElseThrow(() -> new RuntimeException("Reporting manager not found"));
        newUser.setReportingManager(reportingManager);

        newUser.getRoles().add(roleRepository.findById(memberRequest.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found")));

        // Step 4: Save the new user entity
        User savedUser = userRepository.save(newUser);

        // Step 5: Prepare the response directly
        MemberResponse response = new MemberResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getUserName());
        response.setMemberMail(savedUser.getEmail());
        response.setEnable(savedUser.isEnable());
        response.setMobile(savedUser.getMobile());


        // Add department details
        response.setDepartmentId(department.getId());
        response.setDepartmentName(department.getName());

        // Add designation details
        response.setDesignationId(designation.getId());
        response.setDesignationName(designation.getName());

        // Add resource type
        response.setTypeOfResource(
                savedUser.getResourceType() != null ? savedUser.getResourceType().getTypeOfResourceName() : null);

        // Add subscriber and super admin details
        response.setSubscriberId(subscriber.getId());
        response.setSuperAdminId(subscriber.getSuperAdmin().getId());
        response.setSuperAdminName(subscriber.getSuperAdmin().getUserName());

        // Add reporting manager details
        response.setReportingManagerId(reportingManager.getId());
        response.setReportingManagerName(reportingManager.getUserName());

        // Map roles to a comma-separated string
        response.setRoleName(
                savedUser.getRoles().stream()
                        .map(Roles::getRoleName)
                        .collect(Collectors.joining(", ")));

        return response;
    }

    @Override
    public List<MemberResponse> fetchMembers(Long subscriberId, Long userId) {
        // Step 1: Validate the subscriber
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new RuntimeException("Subscriber not found with ID: " + subscriberId));

        // Step 2: Fetch all users associated with the subscriber
        List<User> members = userRepository.findAllBySubscriber(subscriber);

        // Step 3: Map each User to MemberResponse DTO
        return members.stream().map(member -> {
            MemberResponse response = new MemberResponse();
            response.setId(member.getId());
            response.setName(member.getUserName());
            response.setMemberMail(member.getEmail());
            response.setEnable(member.isEnable());
            response.setSubscriberId(subscriber.getId());
            response.setMobile(member.getMobile());


            // Add department details
            if (member.getDepartment() != null) {
                response.setDepartmentId(member.getDepartment().getId());
                response.setDepartmentName(member.getDepartment().getName());
            }

            // Add designation details
            if (member.getDesignation() != null) {
                response.setDesignationId(member.getDesignation().getId());
                response.setDesignationName(member.getDesignation().getName());
            }

            // Add resource type details
            if (member.getResourceType() != null) {
                response.setTypeOfResource(member.getResourceType().getTypeOfResourceName());
            }

            // Add reporting manager details
            if (member.getReportingManager() != null) {
                response.setReportingManagerId(member.getReportingManager().getId());
                response.setReportingManagerName(member.getReportingManager().getUserName());
            }

            // Add roles as a comma-separated string
            response.setRoleName(
                    member.getRoles().stream()
                            .map(Roles::getRoleName)
                            .collect(Collectors.joining(", "))
            );

            // Add super admin details
            if (subscriber.getSuperAdmin() != null) {
                response.setSuperAdminId(subscriber.getSuperAdmin().getId());
                response.setSuperAdminName(subscriber.getSuperAdmin().getUserName());
            }

            return response;
        }).collect(Collectors.toList());
    }





}
