package com.lawzoom.complianceservice.controller.userController;

import com.lawzoom.complianceservice.model.user.Department;
import com.lawzoom.complianceservice.model.user.Designation;
import com.lawzoom.complianceservice.service.DepartmentService;
import com.lawzoom.complianceservice.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compliance/department")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DesignationService designationService;

    // Create Department
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentService.createDepartment(department);
        return ResponseEntity.status(201).body(createdDepartment);
    }

    // Create Designation for a Department
    @PostMapping("/designation")
    public ResponseEntity<Designation> createDesignation(
            @RequestParam Long departmentId,
            @RequestBody Designation designation) {
        Designation createdDesignation = designationService.createDesignation(departmentId, designation);
        return ResponseEntity.status(201).body(createdDesignation);
    }
}
