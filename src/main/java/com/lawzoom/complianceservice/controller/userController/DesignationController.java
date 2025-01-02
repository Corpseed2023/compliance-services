package com.lawzoom.complianceservice.controller.userController;

import com.lawzoom.complianceservice.dto.designationDto.DesignationCreateRequest;
import com.lawzoom.complianceservice.dto.designationDto.DesignationResponse;
import com.lawzoom.complianceservice.dto.designationDto.DesignationUpdateRequest;
import com.lawzoom.complianceservice.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/designation")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @PostMapping("/create")
    public ResponseEntity<DesignationResponse> createDesignation(@RequestBody DesignationCreateRequest designationCreateRequest) {
        try {
            DesignationResponse designation = designationService.createDesignation(designationCreateRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(designation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/master-create")
    public ResponseEntity<DesignationResponse> createMasterDesignation(
            @RequestParam String designationName, @RequestParam Long departmentId) {
        try {
            DesignationResponse designationResponse = designationService.createMasterDesignation(designationName, departmentId);
            return ResponseEntity.status(HttpStatus.CREATED).body(designationResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DesignationResponse> getDesignationById(@PathVariable Long id) {
        try {
            DesignationResponse designation = designationService.getDesignationById(id);
            return ResponseEntity.ok(designation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<DesignationResponse>> getAllDesignations(@RequestParam Long departmentId) {
        try {
            List<DesignationResponse> designations = designationService.getAllDesignations(departmentId);
            return ResponseEntity.ok(designations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateDesignation(@RequestBody DesignationUpdateRequest designationUpdateRequest) {
        try {
            designationService.updateDesignation(designationUpdateRequest);
            return ResponseEntity.ok("Designation updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update designation.");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> softDeleteDesignation(@RequestParam Long designationId) {
        try {
            designationService.softDeleteDesignation(designationId);
            return ResponseEntity.ok("Designation deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete designation.");
        }
    }
}
