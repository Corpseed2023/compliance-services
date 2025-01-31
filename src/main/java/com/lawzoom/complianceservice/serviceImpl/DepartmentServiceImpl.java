package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.DepartmentDTO.DepartmentRequest;
import com.lawzoom.complianceservice.dto.DepartmentDTO.DepartmentResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.user.Department;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.DepartmentRepository;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public DepartmentResponse createDepartment(String departmentName, Long userId) {
        // Validate User
        User user = userRepository.findActiveUserById(userId);
        if (user == null) {
            throw new NotFoundException("User not found with ID: " + userId);
        }


        // Create and save the department
        Department department = new Department();
        department.setName(departmentName);
        department.setCreatedAt(new Date());
        department.setUpdatedAt(new Date());
        department.setEnable(true);
        department.setDeleted(false);

        Department savedDepartment = departmentRepository.save(department);

        return mapToDepartmentResponse(savedDepartment);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        Department department = departmentRepository.findByIdAndIsEnableTrueAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Department not found with ID: " + id));
        return mapToDepartmentResponse(department);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        List<Department> departments = departmentRepository.findAllByIsEnableTrueAndIsDeletedFalse();
        return departments.stream().map(this::mapToDepartmentResponse).collect(Collectors.toList());
    }

    @Override
    public void updateDepartment(DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findByIdAndIsEnableTrueAndIsDeletedFalse(departmentRequest.getId())
                .orElseThrow(() -> new NotFoundException("Department not found with ID: " + departmentRequest.getId()));

        department.setName(departmentRequest.getDepartmentName());
        department.setUpdatedAt(new Date());

        departmentRepository.save(department);
    }

    @Override
    public void softDeleteDepartment(Long departmentId) {
        Department department = departmentRepository.findByIdAndIsEnableTrueAndIsDeletedFalse(departmentId)
                .orElseThrow(() -> new NotFoundException("Department not found with ID: " + departmentId));

        department.setEnable(false);
        department.setDeleted(true);
        department.setUpdatedAt(new Date());

        departmentRepository.save(department);
    }

    @Override
    public DepartmentResponse createMasterDepartment(String departmentName) {

        // Create a new department
        Department department = new Department();
        department.setName(departmentName);
        department.setCreatedAt(new Date());
        department.setUpdatedAt(new Date());
        department.setEnable(true);
        department.setDeleted(false);

        // Save the department
        Department savedDepartment = departmentRepository.save(department);

        // Map to response DTO
        return mapToDepartmentResponse(savedDepartment);
    }


    private DepartmentResponse mapToDepartmentResponse(Department department) {
        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setDepartmentName(department.getName());
        response.setCreatedAt(department.getCreatedAt());
        response.setUpdatedAt(department.getUpdatedAt());
        response.setEnable(department.isEnable());
        return response;
    }
}
