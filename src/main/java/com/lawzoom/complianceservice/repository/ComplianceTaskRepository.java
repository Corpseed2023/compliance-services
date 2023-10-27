package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceTaskRepository extends JpaRepository<ComplianceTask,Long> {
    List<ComplianceTask> findComplianceTaskByCompliance(Compliance compliance);

    ComplianceTask findTaskByComplianceAndTaskName(Compliance compliance, String taskName);

    ComplianceTask findTaskByComplianceAndTaskNameAndIdNot(Compliance compliance, String taskName, Long id);

    Compliance findComplianceById(Long complianceId);

    ComplianceTask findTaskByComplianceAndId(Compliance compliance, Long taskId);

    ComplianceTask findComplianceTaskById(Long taskId);
}
