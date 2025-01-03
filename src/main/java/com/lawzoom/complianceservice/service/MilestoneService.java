package com.lawzoom.complianceservice.service;

import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneRequest;
import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneRequestForFetch;
import com.lawzoom.complianceservice.dto.complianceTaskDto.MilestoneResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface MilestoneService {


    MilestoneResponse createMilestone(@Valid MilestoneRequest milestoneRequest);


    List<MilestoneResponse> fetchAllMilestones(@Valid MilestoneRequestForFetch request);

    MilestoneResponse fetchMilestoneById(Long milestoneId);
}
