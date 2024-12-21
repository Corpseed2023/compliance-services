package com.lawzoom.complianceservice.repository;


import com.lawzoom.complianceservice.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Roles,Long> {

    Optional<Roles> findByIdAndIsEnableTrueAndIsDeletedFalse(Long id);

    @Query(value = "SELECT * FROM roles WHERE role_name = :role AND is_enable = true AND is_deleted = false", nativeQuery = true)
    Optional<Roles> findByRoleName(@Param("role") String role);

    @Query(value = "SELECT r FROM Roles r WHERE r.isEnable = true AND r.isDeleted = false", nativeQuery = true)
    List<Roles> findEnabledAndNotDeletedRoles();
}