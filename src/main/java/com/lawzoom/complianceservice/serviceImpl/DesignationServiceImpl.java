package com.lawzoom.complianceservice.service.impl;

import com.lawzoom.complianceservice.model.user.Department;
import com.lawzoom.complianceservice.model.user.Designation;
import com.lawzoom.complianceservice.repository.DepartmentRepository;
import com.lawzoom.complianceservice.repository.DesignationRepository;
import com.lawzoom.complianceservice.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DesignationServiceImpl implements DesignationService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Override
    public Designation createDesignation(Long departmentId, Designation designation) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + departmentId));

        designation.setDepartment(department);
        if (designationRepository.existsByName(designation.getName())) {
            throw new RuntimeException("Designation name already exists: " + designation.getName());
        }
        return designationRepository.save(designation);
    }
}
