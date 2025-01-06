package com.lawzoom.complianceservice.service;

import com.lawzoom.complianceservice.dto.DepartmentDTO.DepartmentRequest;
import com.lawzoom.complianceservice.dto.DepartmentDTO.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse createDepartment(String departmentName, Long userId);
    DepartmentResponse getDepartmentById(Long id);
    List<DepartmentResponse> getAllDepartments();
    void updateDepartment(DepartmentRequest departmentRequest);
    void softDeleteDepartment(Long departmentId);
    DepartmentResponse createMasterDepartment(String departmentName);
}
