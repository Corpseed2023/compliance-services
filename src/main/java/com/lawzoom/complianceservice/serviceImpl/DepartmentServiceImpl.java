package com.lawzoom.complianceservice.serviceImpl;



import com.lawzoom.complianceservice.dto.DepartmentDTO.DepartmentRequest;
import com.lawzoom.complianceservice.dto.DepartmentDTO.DepartmentResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.Department;
import com.lawzoom.complianceservice.model.User;
import com.lawzoom.complianceservice.repository.DepartmentRepository;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public DepartmentResponse createDepartment(String departmentName , Long userId) {

        // Fetch user and validate
        User userData = userRepository.findByIdAndIsEnableAndNotDeleted(userId)
                .orElseThrow(() -> new NotFoundException("User not found with userId: " + userId));


        Department department = new Department();
        department.setName(departmentName);
        department.setCreatedAt(new Date());
        department.setUpdatedAt(new Date());
        department.setEnable(true);
        department.setDeleted(false);
        department.setDate(LocalDate.now());

        Department savedDepartment = departmentRepository.save(department);

        return mapToResponse(savedDepartment);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Department not found with id: " + id));

        return mapToResponse(department);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void updateDepartment(DepartmentRequest departmentRequest) {
        Department department = departmentRepository.findById(departmentRequest.getId())
                .orElseThrow(() -> new NotFoundException("Department not found with id: " + departmentRequest.getId()));

        department.setName(departmentRequest.getDepartmentName());
        department.setUpdatedAt(new Date());
        departmentRepository.save(department);
    }

    @Override
    public void softDeleteDepartment(Long departmentId) {


        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException("Department not found with id: " + departmentId));

        department.setDeleted(true);
        department.setEnable(false);
        departmentRepository.save(department);
    }

    private DepartmentResponse mapToResponse(Department department) {
        DepartmentResponse response = new DepartmentResponse();
        response.setId(department.getId());
        response.setDepartmentName(department.getName());
        response.setEnable(department.isEnable());
        response.setCreatedAt(department.getCreatedAt());
        response.setUpdatedAt(department.getUpdatedAt());
        response.setDesignations(
                department.getDesignations().stream()
                        .map(designation -> designation.getName())
                        .collect(Collectors.toList())
        );
        return response;
    }

    @Override
    public Department createMasterDepartment(String departmentName) {

        Department department = new Department();
        department.setName(departmentName);
        department.setCreatedAt(new Date());
        department.setUpdatedAt(new Date());
        department.setEnable(true);
        department.setDeleted(false);
        department.setDate(LocalDate.now());

        return departmentRepository.save(department);

    }
}
