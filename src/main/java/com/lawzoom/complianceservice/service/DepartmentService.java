package com.lawzoom.complianceservice.service;



import com.lawzoom.complianceservice.dto.DepartmentDTO.DepartmentRequest;
import com.lawzoom.complianceservice.dto.DepartmentDTO.DepartmentResponse;
import com.lawzoom.complianceservice.model.Department;

import java.util.List;

public interface DepartmentService {


    DepartmentResponse getDepartmentById(Long id);

    List<DepartmentResponse> getAllDepartments();

    void updateDepartment(DepartmentRequest departmentRequest);

    void softDeleteDepartment(Long departmentId);

    DepartmentResponse createDepartment(String departmentName, Long userId);

    Department createMasterDepartment(String departmentName);
}
