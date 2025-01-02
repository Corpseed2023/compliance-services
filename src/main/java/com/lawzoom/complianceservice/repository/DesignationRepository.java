package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.user.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {
    boolean existsByName(String name);
}
