package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.teamMemberDto.MemberRequest;
import com.lawzoom.complianceservice.dto.teamMemberDto.MemberResponse;
import com.lawzoom.complianceservice.dto.userDto.UserRequest;
import com.lawzoom.complianceservice.dto.userDto.UserResponse;
import jakarta.validation.Valid;
import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    MemberResponse createTeamMemberUser(@Valid MemberRequest memberRequest);

    List<MemberResponse> fetchMembers(Long subscriberId, Long userId);
}
