package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {

    // Custom method to fetch enabled and non-deleted designation by ID
    Designation findByIdAndIsEnableTrueAndIsDeletedFalse(Long id);

    @Query("SELECT d FROM Designation d WHERE d.department.id = :departmentId AND d.isDeleted = false")
    List<Designation> findAllByIsDeletedFalseAndDepartmentId(@Param("departmentId") Long departmentId);


    Optional<Designation> findFirstByIsEnableTrue();

    Optional<Designation> findByName(String ceo);
}