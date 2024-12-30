package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.dto.userDto.UserRequest;
import com.lawzoom.complianceservice.dto.userDto.UserResponse;
import com.lawzoom.complianceservice.exception.UserNotFoundException;
import com.lawzoom.complianceservice.model.*;
import com.lawzoom.complianceservice.model.teamMemberModel.TeamMember;
import com.lawzoom.complianceservice.payload.request.SignupRequest;
import com.lawzoom.complianceservice.payload.response.ResponseEntity;
import com.lawzoom.complianceservice.repository.*;
import com.lawzoom.complianceservice.repository.team.TeamMemberRepository;
import com.lawzoom.complianceservice.service.OtpService;
import com.lawzoom.complianceservice.service.UserService;
import com.lawzoom.complianceservice.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private OtpServiceImpl otpServiceImpl;

    @Autowired
    private OtpService otpService;

    @Autowired
    private OtpRepository otpRepository;


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;



    @Override
    public ResponseEntity<?> signupUser(SignupRequest signupRequest) {

        // Step 1: Check if the user already exists
        Optional<User> existingUser = userRepository.findByEmail(signupRequest.getEmail());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>("Error: User already exists.", HttpStatus.BAD_REQUEST);
        }

        // Step 2: Verify OTP
        OTP otp = otpService.findOtpByEmailAndOtpCode(signupRequest.getEmail(), signupRequest.getOtp());
        if (otp == null) {
            return new ResponseEntity<>("Error: Invalid OTP.", HttpStatus.BAD_REQUEST);
        }

        // Step 3: Prepare the User object
        User newUser = new User();
        newUser.setUserName(signupRequest.getName());
        newUser.setEmail(signupRequest.getEmail());
        newUser.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());
        newUser.setEnable(true);
        newUser.setDate(LocalDate.now());

        // Step 4: Assign role to the user
        String requestedRole = signupRequest.getRole();
        Roles role = roleRepository.findByRoleName(requestedRole)
                .orElseThrow(() -> new RuntimeException("Error: Role '" + requestedRole + "' not found."));
        newUser.getRoles().add(role);

        // Handle role-specific logic //
        if (requestedRole.equalsIgnoreCase("SUPER_ADMIN")) {

            // Step 5: Ensure the user has a "Basic" subscription
            Subscription subscription = subscriptionRepository.findByType("Basic")
                    .orElseThrow(() -> new RuntimeException("Error: Subscription type 'Basic' not found."));
            newUser.getSubscriptions().add(subscription);

            // Step 6: Check for Department and Designation for SUPER_ADMIN
            Optional<Department> department = departmentRepository.findByName("Super Administrator");
            Optional<Designation> designation = designationRepository.findByName("CEO");
            if (department.isEmpty() || designation.isEmpty()) {
                return new ResponseEntity<>("Error: Required Department or Designation not found for SUPER_ADMIN.", HttpStatus.BAD_REQUEST);
            }

            // Prepare and save TeamMember associated with the SUPER_ADMIN
            TeamMember teamMember = new TeamMember();
            teamMember.setMemberName(newUser.getUserName());
            teamMember.setMemberMail(newUser.getEmail());
            teamMember.setMemberMobile(newUser.getMobile());
            teamMember.setEnable(true);
            teamMember.setDeleted(false);
            teamMember.setCreatedAt(new Date());
            teamMember.setUpdatedAt(new Date());
            teamMember.setDate(LocalDate.now());
            teamMember.setSuperAdminId(newUser); // Self as Super Admin
            teamMember.setCreatedBy(newUser);
            teamMember.setReportingManager(newUser);
            teamMember.setSubscription(subscription);
            teamMember.setDesignation(designation.get());
            teamMember.setDepartment(department.get());

            // Step 7: Save user and team member in a single transaction
            try {
                userRepository.save(newUser); // Save user
                teamMemberRepository.save(teamMember); // Save team member
            } catch (DataIntegrityViolationException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error: Data integrity violation while saving User or TeamMember.", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>("Signup successful for SUPER_ADMIN. User and TeamMember created.", HttpStatus.OK);

        } else if (requestedRole.equalsIgnoreCase("MASTER")) {
            // If the role is MASTER, no subscription is needed, just save the user
            try {
                userRepository.save(newUser);
            } catch (DataIntegrityViolationException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error: Data integrity violation while saving User.", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>("Signup successful for MASTER. User created without subscription.", HttpStatus.OK);

        } else {
            // Handle other roles, if needed
            try {
                userRepository.save(newUser);
            } catch (DataIntegrityViolationException e) {
                e.printStackTrace();
                return new ResponseEntity<>("Error: Data integrity violation while saving User.", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>("Signup successful. User created and mapped to subscription.", HttpStatus.OK);
        }
    }


    public ResponseEntity<?> checkUserExistanceAndSendOTP(String email) {
        Optional<User> userExist = this.userRepository.findByEmail(email);

        if (userExist.isPresent()) {
            User user = userExist.get();

            // Generate OTP
            String otpCode = CommonUtil.generateOTP(6);

            // Save OTP to user entity
//            user.setOtp(Integer.parseInt(otpCode));
            userRepository.save(user);

            // Send OTP via email
            otpService.sendOtpOnEmail(user.getEmail(), otpCode, user.getUserName());

            return new ResponseEntity().ok("OTP sent successfully");
        } else {
            return new ResponseEntity().badRequest("User NOt found");
        }
    }

    public boolean verifyOTP(String email, String otp) {

        Optional<User> userOptional = userRepository.findByEmail(email);

       OTP otp1= otpRepository.findByEmailContainingAndOtpCode(email,otp);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Check if OTP matches
            return otp.equals(otp1.getOtpCode());
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Reset password
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

//    @Override
//    public ResponseEntity<UserResponse> createTeamMemberUser(UserRequest userRequest)  {
//
//        User saveUser = new User();
//        saveUser.setUserName(userRequest.getUserName());
//        saveUser.setEmail(userRequest.getEmail());
//        saveUser.setMobile(userRequest.getMobile());
//        saveUser.setPassword(CommonUtil.encodePassword(userRequest.getPassword()));
//
//        ResourceType resourceType = resourceTypeRepository.findById(userRequest.getResourceTypeId())
//                .orElseThrow(() -> new RuntimeException("Error: Resource type not found."));
////       saveUser.setDesignation(userRequest.getDesignation());
//
//        Designation designation = designationRepository.findById(userRequest.getDesignationId())
//                .orElseThrow(() -> new RuntimeException("Error: Designation type not found."));
//
//        saveUser.setDesignation(designation);
//
//        saveUser.setResourceType(resourceType);
//        saveUser.setCreatedAt(CommonUtil.getDate());
//        saveUser.setUpdatedAt(CommonUtil.getDate());
//        saveUser.setRoles(userRequest.getRoles());
//        saveUser.setEnable(true);
//        this.userRepository.save(saveUser);
//
//
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//
//        try {
//            helper.setFrom("kaushlendra.pratap@corpseed.com");
//            helper.setTo(userRequest.getEmail());
//            helper.setSubject("Set your Password");
//
//            Context context = new Context();
//            context.setVariable("emails", userRequest.getEmail());
//
//            String emailContent = templateEngine.process("email-template", context);
//
//            helper.setText(emailContent, true);
//
//            javaMailSender.send(message);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//        return new ResponseEntity<User>().ok(saveUser);
//    }

    @Override
    public UserResponse getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findByIdAndIsEnableAndNotDeleted(userId);


        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (!user.isEnable()) {
                // Handle the case where the user is not enabled (inactive)
                throw new IllegalStateException("User is not active with id: " + userId);
            }

            return mapUserToUserResponse(user);
        } else {
            // Handle the case where the user is not present
            throw new NoSuchElementException("User not found with id: " + userId);
        }

    }
    private UserResponse mapUserToUserResponse(User user) {
        List<String> roleNames = user.getRoles().stream()
                .map(Roles::getRoleName)
                .collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getUserName());
        userResponse.setEmail(user.getEmail());
        userResponse.setMobile(user.getMobile());

        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());
        userResponse.setEnable(user.isEnable());
        userResponse.setAssociated(false); // Assuming 'isAssociated' is a placeholder or default false.
//        userResponse.setUserId(user.getId());
//        userResponse.setRoles(roleNames);

        return userResponse;
    }


    @Override
    public ResponseEntity<?> updateUser(Long userId, UserRequest updatedUserRequest) {
//        Optional<User> userOptional = userRepository.findByIdAndIsEnableAndNotDeleted(userId);
//
//        if (userOptional.isPresent()) {
//            User existingUser = userOptional.get();
//
//            // Update user details from UserRequest
//            if (updatedUserRequest.getUserName() != null) {
//                existingUser.setUserName(updatedUserRequest.getUserName());
//            }
//            if (updatedUserRequest.getEmail() != null) {
//                existingUser.setEmail(updatedUserRequest.getEmail());
//            }
//            if (updatedUserRequest.getMobile() != null) {
//                existingUser.setMobile(updatedUserRequest.getMobile());
//            }
//            // Update designation if provided
//            if (updatedUserRequest.getDesignationId() != null) {
//                Optional<Designation> designationOptional = designationRepository.findById(updatedUserRequest.getDesignationId());
//                if (designationOptional.isPresent()) {
////                    existingUser.setDesignation(designationOptional.get());
//                } else {
//                    return new ResponseEntity().badRequest("Invalid designation ID");
//                }
//            }
//
//            // Update resource type if provided
//            if (updatedUserRequest.getResourceTypeId() != null) {
//                Optional<ResourceType> resourceTypeOptional = resourceTypeRepository.findById(updatedUserRequest.getResourceTypeId());
//                if (resourceTypeOptional.isPresent()) {
////                    existingUser.setResourceType(resourceTypeOptional.get());
//                } else {
//                    return new ResponseEntity().badRequest("Invalid resource type ID");
//                }
//            }
//
//            existingUser.setAssociated(updatedUserRequest.isAssociated());
//            existingUser.setEnable(updatedUserRequest.isEnable());
//            existingUser.setUpdatedAt(CommonUtil.getDate());
//
//            // Save the updated user
//            User savedUser = userRepository.save(existingUser);

//            return new ResponseEntity().ok(savedUser);
//        } else {
//            return new ResponseEntity().noContent("User Not Found");
//        }

        return null;
    }


    @Override
    public ResponseEntity<?> updateIsAssociated(Long userId, boolean isAssociated) {
        Optional<User> userOptional = userRepository.findByIdAndIsEnableAndNotDeleted(userId);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setAssociated(isAssociated);

            // Save the updated user with the new isAssociated value
            userRepository.save(existingUser);

            return new ResponseEntity<User>().ok(existingUser);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public ResponseEntity<?> updateIsAssociatedAndIsSubscribe(Long userId, boolean isAssociated,
                                                              boolean isSubscribed) {
        Optional<User> userOptional = userRepository.findByIdAndIsEnableAndNotDeleted(userId);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setAssociated(isAssociated);
//            existingUser.setSubscription(isSubscribed);

            // Save the updated user with the new isAssociated value
            userRepository.save(existingUser);

            return new ResponseEntity<User>().ok(existingUser);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
}
