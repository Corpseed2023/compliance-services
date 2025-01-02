package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.user.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    boolean existsByRoleName(String roleName);
}
