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

//    List<MilestoneListResponse> fetchAllMilestones(@Valid MilestoneRequestForFetch request);
    
    List<MilestoneResponse> fetchMilestonesByStatus(Long userId, Long subscriberId, String status);

    MilestoneResponse updateMilestoneStatus(Long milestoneId, Long statusId);

    Map<String, Object> fetchUserAllMilestones(Long userId, Long subscriberId, Pageable pageable);

    Map<String, Object> updateMilestone(MilestoneUpdateRequest updateRequest);

    Map<String, Object> fetchMilestoneById(Long milestoneId);
}
