package com.lawzoom.complianceservice.service;

import com.lawzoom.complianceservice.dto.designationDto.DesignationCreateRequest;
import com.lawzoom.complianceservice.dto.designationDto.DesignationResponse;
import com.lawzoom.complianceservice.dto.designationDto.DesignationUpdateRequest;
import com.lawzoom.complianceservice.model.Designation;

import java.util.List;

public interface DesignationService {



    Designation getDesignationById(Long id);



    void softDeleteDesignation(Long designationId, Long userId);

    void updateDesignation(DesignationUpdateRequest DesignationUpdateRequest);



    DesignationResponse createMasterDesignation(String designationName, Long departmentId);

    DesignationResponse createDesignation(DesignationCreateRequest designationCreateRequest);

    List<DesignationResponse> getAllDesignations(Long departmentId);
}
