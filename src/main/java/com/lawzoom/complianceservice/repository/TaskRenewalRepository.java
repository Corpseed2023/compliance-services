package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.renewalModel.TaskRenewal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRenewalRepository extends JpaRepository<TaskRenewal, Long> {
}
