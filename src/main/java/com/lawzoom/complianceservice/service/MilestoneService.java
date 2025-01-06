package com.lawzoom.complianceservice.service;

import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneRequestForFetch;
import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface MilestoneService {


    ResponseEntity<Map<String, Object>> createMilestone(MilestoneRequest milestoneRequest);



    List<MilestoneResponse> fetchAllMilestones(@Valid MilestoneRequestForFetch request);

    MilestoneResponse fetchMilestoneById(Long milestoneId);

    List<MilestoneResponse> fetchMilestonesByStatus(Long userId, Long subscriberId, String status);

}
