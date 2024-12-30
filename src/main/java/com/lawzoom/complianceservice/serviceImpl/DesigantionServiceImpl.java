package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.dto.designationDto.DesignationCreateRequest;
import com.lawzoom.complianceservice.dto.designationDto.DesignationResponse;
import com.lawzoom.complianceservice.dto.designationDto.DesignationUpdateRequest;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.Department;
import com.lawzoom.complianceservice.model.Designation;
import com.lawzoom.complianceservice.model.User;
import com.lawzoom.complianceservice.repository.DepartmentRepository;
import com.lawzoom.complianceservice.repository.DesignationRepository;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesigantionServiceImpl implements DesignationService {

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    @Override
    public DesignationResponse createDesignation(DesignationCreateRequest designationCreateRequest) {

        // Fetch user and validate
        User userData = userRepository.findByIdAndIsEnableAndNotDeleted(designationCreateRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with userId: " + designationCreateRequest.getUserId()));

        Department department = departmentRepository.findById(designationCreateRequest.getDepartmentId())
                .orElseThrow(() -> new NotFoundException("Department not found with id: " + designationCreateRequest.getDepartmentId()));

        // Create and save designation
        Designation designation = new Designation();
        designation.setName(designationCreateRequest.getDesignationName());
        designation.setCreatedAt(new Date());
        designation.setUpdatedAt(new Date());
        designation.setEnable(true);
        designation.setDate(LocalDate.now());
        designation.setDeleted(false);
        designation.setDepartment(department);

        Designation savedDesignation = designationRepository.save(designation);

        // Map saved designation to response DTO using setters
        DesignationResponse designationResponse = new DesignationResponse();
        designationResponse.setDesignationId(savedDesignation.getId()); // Assuming getId() returns the ID
        designationResponse.setDesignationName(savedDesignation.getName());

        return designationResponse;
    }



    @Override
    public Designation getDesignationById(Long id) {
        return designationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Designation not found with id: " + id));
    }

    @Override
    public List<DesignationResponse> getAllDesignations(Long departmentId) {

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Department not found with id: " + departmentId));


        List<Designation> designations = designationRepository.findAllByIsDeletedFalseAndDepartmentId(departmentId);

        List<DesignationResponse> designationResponses = designations.stream()
                .map(designation -> new DesignationResponse(
                        designation.getId(),
                        designation.getName()
                ))
                .collect(Collectors.toList());

        return designationResponses;
    }



    @Override
    public void updateDesignation(DesignationUpdateRequest desigantionRequest) {

         userRepository.findByIdAndIsEnableAndNotDeleted(desigantionRequest.getUserID())
                .orElseThrow(() -> new NotFoundException("User not found with userId: " + desigantionRequest.getUserID()));

        Designation existingDesignation = designationRepository.findById(desigantionRequest.getDesignationId())
                .orElseThrow(() -> new NotFoundException("Designation not found with id: " + desigantionRequest.getDesignationId()));

        existingDesignation.setName(desigantionRequest.getDesignationName());
        existingDesignation.setUpdatedAt(new Date());
        designationRepository.save(existingDesignation);
    }

    @Override
    public void softDeleteDesignation(Long id,Long userId) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Designation not found with id: " + id));

        designation.setDeleted(true);
        designation.setUpdatedAt(new Date());
        designationRepository.save(designation);
    }

    @Override
    public DesignationResponse createMasterDesignation(String designationName, Long departmentId) {
        // Fetch the department and validate
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Department not found with id: " + departmentId));

        // Create and set values for the designation
        Designation designation = new Designation();
        designation.setName(designationName);
        designation.setCreatedAt(new Date());
        designation.setUpdatedAt(new Date());
        designation.setEnable(true);
        designation.setDate(LocalDate.now());
        designation.setDeleted(false);
        designation.setDepartment(department);

        // Save the designation
        Designation savedDesignation = designationRepository.save(designation);

        // Map the saved designation to a DTO
        return new DesignationResponse(savedDesignation.getId(), savedDesignation.getName());
    }




}



