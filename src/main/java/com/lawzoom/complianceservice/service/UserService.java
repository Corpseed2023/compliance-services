package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.teamMemberDto.MemberRequest;
import com.lawzoom.complianceservice.dto.teamMemberDto.MemberResponse;
import com.lawzoom.complianceservice.dto.userDto.UserRequest;
import com.lawzoom.complianceservice.dto.userDto.UserResponse;
import com.lawzoom.complianceservice.model.user.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    MemberResponse createTeamMemberUser(@Valid MemberRequest memberRequest);
}
