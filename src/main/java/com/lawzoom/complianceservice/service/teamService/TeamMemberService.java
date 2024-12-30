package com.lawzoom.complianceservice.service.teamService;


import com.lawzoom.complianceservice.dto.teamMemberDto.TeamMemberRequest;
import com.lawzoom.complianceservice.dto.teamMemberDto.TeamMemberResponse;

import java.util.List;

public interface TeamMemberService {


    TeamMemberResponse updateTeamMember(Long id, TeamMemberRequest teamMemberRequest);


    TeamMemberResponse createTeamMember(TeamMemberRequest teamMemberRequest);

    List<TeamMemberResponse> getAllTeamMembers(Long userId, Long subscriptionId);

    void removeTeamMember(Long userId, Long subscriptionId, Long teamMemberId);
}
