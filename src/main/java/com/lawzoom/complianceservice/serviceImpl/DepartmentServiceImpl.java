package com.lawzoom.complianceservice.service.impl;

import com.lawzoom.complianceservice.model.user.Department;
import com.lawzoom.complianceservice.repository.DepartmentRepository;
import com.lawzoom.complianceservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department createDepartment(Department department) {
        if (departmentRepository.existsByName(department.getName())) {
            throw new RuntimeException("Department name already exists: " + department.getName());
        }
        return departmentRepository.save(department);
    }
}
