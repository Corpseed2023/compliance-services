//package com.lawzoom.complianceservice.serviceImpl.teamLogic;
//
//import com.lawzoom.complianceservice.dto.teamMemberDto.TeamMemberDetailsResponse;
//import com.lawzoom.complianceservice.dto.teamMemberDto.TeamMemberRequest;
//import com.lawzoom.complianceservice.dto.teamMemberDto.TeamMemberResponse;
//import com.lawzoom.complianceservice.exception.NotFoundException;
//import com.lawzoom.complianceservice.model.companyModel.Company;
//import com.lawzoom.complianceservice.model.teamMemberModel.TeamMember;
//import com.lawzoom.complianceservice.repository.*;
//import com.lawzoom.complianceservice.repository.companyRepo.CompanyRepository;
//import com.lawzoom.complianceservice.repository.team.TeamMemberRepository;
//import com.lawzoom.complianceservice.service.teamService.TeamMemberService;
//import com.lawzoom.complianceservice.utils.CommonUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.context.Context;
//
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class TeamMemberServiceImpl implements TeamMemberService {
//
//    @Autowired
//    private TeamMemberRepository teamMemberRepository;
//
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private CompanyRepository companyRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    UserController userController;
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Autowired
//    private SpringTemplateEngine templateEngine;
//
//    @Autowired
//    private ResourceTypeRepository resourceTypeRepository;
//
//    @Autowired
//    private SubscriptionRepository subscriptionRepository;
//
//    @Autowired
//    private  DepartmentRepository departmentRepository;
//
//    @Autowired
//    private DesignationRepository designationRepository;
//
//    @Override
//    public TeamMemberResponse createTeamMember(TeamMemberRequest teamMemberRequest) {
//
//
//        Subscription subscription = null;
//        if (teamMemberRequest.getSubscriptionId() != null) {
//            subscription = subscriptionRepository.findById(teamMemberRequest.getSubscriptionId())
//                    .orElseThrow(() -> new IllegalArgumentException("Subscription not found with ID: " + teamMemberRequest.getSubscriptionId()));
//        }
//
//        User createdBy = userRepository.findByIdAndIsEnableAndNotDeleted(teamMemberRequest.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + teamMemberRequest.getUserId()));
//
//
//        ResourceType resourceType = resourceTypeRepository.findById(teamMemberRequest.getTypeOfResource())
//                .orElseThrow(() -> new RuntimeException("Error: Resource type not found."));
//
//        Roles role = roleRepository.findByIdAndIsEnableTrueAndIsDeletedFalse(teamMemberRequest.getRoleId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + teamMemberRequest.getRoleId()));
//
//        User reportingManager = null;
//        if (teamMemberRequest.getReportingManagerId() != null) {
//            reportingManager = userRepository.findByIdAndIsEnableAndNotDeleted(teamMemberRequest.getReportingManagerId())
//                    .orElseThrow(() -> new IllegalArgumentException("Reporting Manager not found with ID: " + teamMemberRequest.getReportingManagerId()));
//        }
//
//        Department department = departmentRepository.findById(teamMemberRequest.getDepartmentId())
//                .orElseThrow(() -> new NotFoundException("Department not found with id: " + teamMemberRequest.getDepartmentId()));
//
//        Designation designation = null;
//        if (teamMemberRequest.getDesignationId() != null) {
//            designation = designationRepository.findById(teamMemberRequest.getDesignationId())
//                    .orElseThrow(() -> new IllegalArgumentException("Designation not found with ID: " + teamMemberRequest.getDesignationId()));
//        }
//
//
//        String randomPassword = passwordController.generateRandomPassword();
//
//        // Create User
//        User user = new User();
//        user.setUserName(teamMemberRequest.getMemberName());
//        user.setEmail(teamMemberRequest.getMemberMail());
//        user.setMobile(teamMemberRequest.getMemberMobile());
//        user.setPassword(new BCryptPasswordEncoder().encode(randomPassword));
////        user.setSuperAdminId(subscription.getUser());      user.setDesignation(designation);
////        user.setResourceType(resourceType);
////
//        user.setCreatedAt(CommonUtil.getDate());
//        user.setUpdatedAt(CommonUtil.getDate());
////        user.setDepartment(department);
//
//        // Assign the role as a Set (even if it's a single role)
//        Set<Roles> roles = Set.of(role);
//        user.setRoles(roles);
//
//        User savedUser = userRepository.save(user);
//
//        // Send welcome email with password
//        sendWelcomeEmail(savedUser, randomPassword);
//
//        // Create TeamMember
//        TeamMember teamMember = new TeamMember();
//        teamMember.setMemberName(teamMemberRequest.getMemberName());
//        teamMember.setMemberMail(teamMemberRequest.getMemberMail());
//        teamMember.setMemberMobile(teamMemberRequest.getMemberMobile());
//        teamMember.setTypeOfResource(resourceType.getTypeOfResourceName());
//        teamMember.setCreatedAt(new Date());
//        teamMember.setUpdatedAt(new Date());
//        teamMember.setEnable(teamMemberRequest.isEnable());
//        teamMember.setReportingManager(reportingManager);
//        teamMember.setSubscription(subscription);
//        teamMember.setCreatedBy(createdBy);
//        teamMember.setDesignation(designation);
////        teamMember.setSuperAdminId(subscription);
//        teamMember.getRoles();
//
//        TeamMember savedTeamMember = teamMemberRepository.save(teamMember);
//
//        TeamMemberResponse response = new TeamMemberResponse();
//        response.setMemberName(savedTeamMember.getMemberName());
//        response.setMemberMail(savedTeamMember.getMemberMail());
//        response.setMemberMobile(savedTeamMember.getMemberMobile());
//        response.setRoleName(savedTeamMember.getRoles().getRoleName());
//        response.setEnable(savedTeamMember.isEnable());
//        response.setCreatedAt(savedTeamMember.getCreatedAt());
//        response.setUpdatedAt(savedTeamMember.getUpdatedAt());
//
//        return response;
//    }
//
//    private void sendWelcomeEmail(User user, String password) {
//        MimeMessage message = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message);
//            helper.setFrom("kaushlendra.pratap@corpseed.com");
//            helper.setTo(user.getEmail());
//            helper.setSubject("Welcome to the Platform - Set Your Password");
//
//            Context context = new Context();
//            context.setVariable("email", user.getEmail());
//            context.setVariable("password", password);
//
//            String emailContent = templateEngine.process("email-template", context);
//            helper.setText(emailContent, true);
//
//            javaMailSender.send(message);
//        } catch (MessagingException e) {
//            throw new RuntimeException("Failed to send email: " + e.getMessage());
//        }
//    }
//
//
//    @Override
//    public TeamMemberResponse updateTeamMember(Long id, TeamMemberRequest teamMemberRequest) {
//        // Find existing TeamMember
//        TeamMember teamMember = teamMemberRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("TeamMember with ID " + id + " not found"));
//
//        // Find and set Reporting Manager
//        User reportingManager = null;
//        if (teamMemberRequest.getReportingManagerId() != null) {
//            reportingManager = userRepository.findByIdAndIsEnableAndNotDeleted(teamMemberRequest.getReportingManagerId())
//                    .orElseThrow(() -> new IllegalArgumentException("Reporting Manager not found with ID: " + teamMemberRequest.getReportingManagerId()));
//        }
//
//        // Find and set Subscription
//        Subscription subscription = null;
//        if (teamMemberRequest.getSubscriptionId() != null) {
//            subscription = subscriptionRepository.findById(teamMemberRequest.getSubscriptionId())
//                    .orElseThrow(() -> new IllegalArgumentException("Subscription not found with ID: " + teamMemberRequest.getSubscriptionId()));
//        }
//
//        // Find and set Designation
//        Designation designation = null;
//        if (teamMemberRequest.getDesignationId() != null) {
//            designation = designationRepository.findById(teamMemberRequest.getDesignationId())
//                    .orElseThrow(() -> new IllegalArgumentException("Designation not found with ID: " + teamMemberRequest.getDesignationId()));
//        }
//
//        // Find and set Resource Type
//        ResourceType resourceType = resourceTypeRepository.findById(teamMemberRequest.getTypeOfResource())
//                .orElseThrow(() -> new IllegalArgumentException("Error: Resource type not found."));
//
//        // Update TeamMember fields
//        teamMember.setMemberName(teamMemberRequest.getMemberName());
//        teamMember.setMemberMail(teamMemberRequest.getMemberMail());
//        teamMember.setMemberMobile(teamMemberRequest.getMemberMobile());
//        teamMember.setEnable(teamMemberRequest.isEnable());
//        teamMember.setTypeOfResource(resourceType.getTypeOfResourceName());
//        teamMember.setReportingManager(reportingManager);
//        teamMember.setSubscription(subscription);
//        teamMember.setDesignation(designation);
//        teamMember.setUpdatedAt(new Date());
//
//        // Save updated TeamMember
//        TeamMember updatedTeamMember = teamMemberRepository.save(teamMember);
//
//        // Prepare Response
//        TeamMemberResponse response = new TeamMemberResponse();
//        response.setMemberName(updatedTeamMember.getMemberName());
//        response.setMemberMail(updatedTeamMember.getMemberMail());
//        response.setMemberMobile(updatedTeamMember.getMemberMobile());
//        response.setEnable(updatedTeamMember.isEnable());
//        response.setTypeOfResource(updatedTeamMember.getTypeOfResource());
//        response.setCreatedAt(updatedTeamMember.getCreatedAt());
//        response.setUpdatedAt(updatedTeamMember.getUpdatedAt());
//        response.setReportingManagerId(
//                updatedTeamMember.getReportingManager() != null ? updatedTeamMember.getReportingManager().getId() : null);
//        response.setSubscriptionId(
//                updatedTeamMember.getSubscription() != null ? updatedTeamMember.getSubscription().getId() : null);
//        response.setSuperAdminId(updatedTeamMember.getSuperAdminId().getId());
//
//        return response;
//    }
//
//
//    @Override
//    public List<TeamMemberResponse> getAllTeamMembers(Long userId, Long subscriptionId) {
//        if (userId == null || subscriptionId == null) {
//            throw new IllegalArgumentException("UserId and SubscriptionId must not be null");
//        }
//
//        List<TeamMember> teamMembers = teamMemberRepository.findBySuperAdminIdAndSubscriptionId(userId, subscriptionId);
//
//        if (teamMembers.isEmpty()) {
//            return new ArrayList<>();
//        }
//
//        return teamMembers.stream().map(teamMember -> {
//            TeamMemberResponse response = new TeamMemberResponse();
//            response.setMemberName(teamMember.getMemberName());
//            response.setMemberMail(teamMember.getMemberMail());
//            response.setMemberMobile(teamMember.getMemberMobile());
//            response.setRoleName(teamMember.getRoles().getRoleName());
//            response.setEnable(teamMember.isEnable());
//            response.setCreatedAt(teamMember.getCreatedAt());
//            response.setUpdatedAt(teamMember.getUpdatedAt());
//            response.setReportingManagerId(
//                    teamMember.getReportingManager() != null ? teamMember.getReportingManager().getId() : null);
//            response.setSubscriptionId(
//                    teamMember.getSubscription() != null ? teamMember.getSubscription().getId() : null);
//            response.setCreatedBy(teamMember.getCreatedBy() != null ? teamMember.getCreatedBy().getId() : null);
//            response.setSuperAdminId(
//                    teamMember.getSuperAdminId() != null ? teamMember.getSuperAdminId().getId() : null);
//            return response;
//        }).collect(Collectors.toList());
//    }
//
//
//    public void removeTeamMember(Long userId, Long subscriptionId, Long teamMemberId) {
//        // Validate if the subscription ID exists
//        Subscription subscription = subscriptionRepository.findById(subscriptionId)
//                .orElseThrow(() -> new IllegalArgumentException("Subscription not found with ID: " + subscriptionId));
//
//        // Validate if the superAdminId exists in the User repository
//        User superAdmin = userRepository.findByIdAndIsEnableAndNotDeleted(userId)
//                .orElseThrow(() -> new IllegalArgumentException("Super Admin not found with ID: " + userId));
//
//        // Validate if the teamMemberId exists and belongs to the given subscription and superAdmin
//        TeamMember teamMember = teamMemberRepository.findById(teamMemberId)
//                .orElseThrow(() -> new IllegalArgumentException("Team Member not found with ID: " + teamMemberId));
//
//        if (!teamMember.getSuperAdminId().getId().equals(userId)) {
//            throw new IllegalArgumentException("The provided Team Member does not belong to the given Super Admin.");
//        }
//
//        if (!teamMember.getSubscription().getId().equals(subscriptionId)) {
//            throw new IllegalArgumentException("The provided Team Member is not associated with the given Subscription.");
//        }
//
//        // Update the team member's status in the TeamMember entity
//        teamMember.setDeleted(true);
//        teamMember.setEnable(false);
//        teamMember.setUpdatedAt(new Date());
//
//        // Save the updated team member record
//        teamMemberRepository.save(teamMember);
//
//        // Fetch the corresponding user by email
//        Optional<User> userOptional = userRepository.findByEmailAndIsEnableAndNotDeleted(teamMember.getMemberMail());
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            user.setDeleted(true); // Mark user as deleted
//            user.setEnable(false); // Disable the user
//            user.setUpdatedAt(new Date()); // Update timestamp
//
//            // Save the updated user record
//            userRepository.save(user);
//        } else {
//            throw new IllegalArgumentException("User associated with the Team Member not found.");
//        }
//    }
//
//
//
//    public TeamMemberDetailsResponse getTeamMemberDetailsByMail(String memberMail) {
//        List<TeamMember> teamMembers = teamMemberRepository.findByMemberMail(memberMail);
//
//        if (!teamMembers.isEmpty()) {
//            TeamMember teamMember = teamMembers.get(0);
//
//        }
//        return null;
//    }
//
//    public List<HashMap<String, Object>> getTeamMembersWithIdAndName(Long companyId) {
//        Optional<Company> companyOptional = companyRepository.findById(companyId);
//
//        if (companyOptional.isPresent()) {
//            Company company = companyOptional.get();
//
//
//        }
//
//        return null;
//    }
//
//
//}
//
//
//
