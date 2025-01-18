package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.designationDto.DesignationCreateRequest;
import com.lawzoom.complianceservice.dto.designationDto.DesignationResponse;
import com.lawzoom.complianceservice.dto.designationDto.DesignationUpdateRequest;
import com.lawzoom.complianceservice.model.user.Department;
import com.lawzoom.complianceservice.model.user.Designation;
import com.lawzoom.complianceservice.repository.DepartmentRepository;
import com.lawzoom.complianceservice.repository.DesignationRepository;
import com.lawzoom.complianceservice.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesignationServiceImpl implements DesignationService {

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DesignationResponse createDesignation(DesignationCreateRequest designationCreateRequest) {
        Department department = departmentRepository.findById(designationCreateRequest.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Designation designation = new Designation();
        designation.setName(designationCreateRequest.getDesignationName());
        designation.setDepartment(department);

        Designation savedDesignation = designationRepository.save(designation);
        return mapToResponse(savedDesignation);
    }

    @Override
    public DesignationResponse getDesignationById(Long id) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Designation not found"));
        return mapToResponse(designation);
    }

    @Override
    public List<DesignationResponse> getAllDesignations(Long departmentId) {
        List<Designation> designations = designationRepository.findAllByDepartmentId(departmentId);
        return designations.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void updateDesignation(DesignationUpdateRequest designationUpdateRequest) {
        Designation designation = designationRepository.findById(designationUpdateRequest.getDesignationId())
                .orElseThrow(() -> new RuntimeException("Designation not found"));
        designation.setName(designationUpdateRequest.getDesignationName());
        designationRepository.save(designation);
    }

    @Override
    public void softDeleteDesignation(Long id) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Designation not found"));
        designationRepository.delete(designation);
    }

    @Override
    public DesignationResponse createMasterDesignation(String designationName, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Designation designation = new Designation();
        designation.setName(designationName);
        designation.setDepartment(department);

        Designation savedDesignation = designationRepository.save(designation);
        return mapToResponse(savedDesignation);
    }

    private DesignationResponse mapToResponse(Designation designation) {
        DesignationResponse response = new DesignationResponse();
        response.setDesignationId(designation.getId());
        response.setDesignationName(designation.getName());
        return response;
    }
}
