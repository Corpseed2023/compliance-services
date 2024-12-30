package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findFirstByIsEnableTrue();

    Optional<Department> findByName(String superAdministrator);
}