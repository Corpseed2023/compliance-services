package com.lawzoom.complianceservice.service.mileStoneService;

import com.lawzoom.complianceservice.dto.complianceTaskDto.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface MilestoneService {


    ResponseEntity<Map<String, Object>> createMilestone(MilestoneRequest milestoneRequest);

    List<MilestoneListResponse> fetchAllMilestones(@Valid MilestoneRequestForFetch request);

    MilestoneResponse fetchMilestoneById(Long milestoneId);

    List<MilestoneResponse> fetchMilestonesByStatus(Long userId, Long subscriberId, String status);

    MilestoneResponse updateMilestoneAssignment(Long milestoneId, Long assignedToId, Long managerId);

    MilestoneResponse updateMilestoneStatus(Long milestoneId, Long statusId);

    Page<MilestoneDetailsResponse> fetchUserAllMilestones(Long userId, Long subscriberId, Pageable pageable);
}
