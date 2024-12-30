package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.userDto.UserRequest;
import com.lawzoom.complianceservice.dto.userDto.UserResponse;
import com.lawzoom.complianceservice.payload.request.SignupRequest;
import com.lawzoom.complianceservice.payload.response.ResponseEntity;

public interface UserService {

    ResponseEntity<?> signupUser(SignupRequest signupRequest);


    ResponseEntity<?> checkUserExistanceAndSendOTP(String email);

    boolean verifyOTP(String email, String otp);

    void resetPassword(String email, String newPassword);

//    ResponseEntity<UserResponse> createTeamMemberUser(UserRequest userRequest) throws MalformedURLException;

    UserResponse getUserById(Long userId);

    ResponseEntity<?> updateUser(Long userId, UserRequest updatedUserRequest);

    ResponseEntity<?> updateIsAssociated(Long userId, boolean isAssociated);

    ResponseEntity<?> updateIsAssociatedAndIsSubscribe(Long userId, boolean isAssociated, boolean isSubscribed);}