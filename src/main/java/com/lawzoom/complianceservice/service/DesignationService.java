package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.designationDto.DesignationCreateRequest;
import com.lawzoom.complianceservice.dto.designationDto.DesignationResponse;
import com.lawzoom.complianceservice.dto.designationDto.DesignationUpdateRequest;

import java.util.List;

public interface DesignationService {

    DesignationResponse createDesignation(DesignationCreateRequest designationCreateRequest);

    DesignationResponse createMasterDesignation(String designationName, Long departmentId);

    DesignationResponse getDesignationById(Long id);

    List<DesignationResponse> getAllDesignations(Long departmentId);

    void updateDesignation(DesignationUpdateRequest designationUpdateRequest);

    void softDeleteDesignation(Long designationId);
}
