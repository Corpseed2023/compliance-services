package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.user.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Long> {
    List<Designation> findAllByDepartmentId(Long departmentId);
}
