package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.user.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {




    // Find department by ID, ensuring it's enabled and not deleted
    @Query(value = "SELECT * FROM department WHERE id = :id AND is_enable = 1 AND is_deleted = 0", nativeQuery = true)
    Optional<Department> findByIdAndIsEnableTrueAndIsDeletedFalse(@Param("id") Long id);

    // Fetch all departments that are enabled and not deleted
    @Query(value = "SELECT * FROM department WHERE is_enable = 1 AND is_deleted = 0", nativeQuery = true)
    List<Department> findAllByIsEnableTrueAndIsDeletedFalse();
}
