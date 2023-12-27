//package com.lawzoom.complianceservice.repository;
//
//
//import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
//import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface ComplianceSubTaskRepo extends JpaRepository<ComplianceSubTask,Long>
//
//{
//
//
//    List<ComplianceSubTask> findByComplianceTask(Optional<ComplianceTask> complianceTask);
//
//    ComplianceSubTask findByComplianceTaskAndSubTaskName(Optional<ComplianceTask> complianceTask, String subTaskName);
//
//
//    ComplianceSubTask findByComplianceTaskAndSubTaskNameAndId(Optional<ComplianceTask> complianceTask, String subTaskName, Long id);
//}
