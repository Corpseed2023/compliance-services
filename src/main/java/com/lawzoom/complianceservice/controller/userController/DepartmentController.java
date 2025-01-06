package com.lawzoom.complianceservice.controller.userController;

import com.lawzoom.complianceservice.dto.DepartmentDTO.DepartmentRequest;
import com.lawzoom.complianceservice.dto.DepartmentDTO.DepartmentResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/department")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/create")
    public ResponseEntity<DepartmentResponse> createDepartment(@RequestParam String departmentName, @RequestParam Long userId) {
        try {
            DepartmentResponse department = departmentService.createDepartment(departmentName, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(department);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/master-create")
    public ResponseEntity<DepartmentResponse> createMasterDepartment(@RequestParam String departmentName) {
        try {
            DepartmentResponse department = departmentService.createMasterDepartment(departmentName);
            return ResponseEntity.status(HttpStatus.CREATED).body(department);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("get-department")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@RequestParam Long id) {
        try {
            DepartmentResponse department = departmentService.getDepartmentById(id);
            return ResponseEntity.ok(department);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        try {
            List<DepartmentResponse> departments = departmentService.getAllDepartments();
            return ResponseEntity.ok(departments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateDepartment(@RequestBody DepartmentRequest departmentRequest) {
        try {
            departmentService.updateDepartment(departmentRequest);
            return ResponseEntity.ok("Department updated successfully.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update department: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update department: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> softDeleteDepartment(@RequestParam Long departmentId) {
        try {
            departmentService.softDeleteDepartment(departmentId);
            return ResponseEntity.ok("Department deleted successfully.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to delete department: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete department: " + e.getMessage());
        }
    }
}
