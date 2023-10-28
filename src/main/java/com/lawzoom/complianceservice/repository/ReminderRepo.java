package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderRepo extends JpaRepository<Reminder,Long>

{
//    Reminder findReminderByComplianceOrTaskOrSubTask(Compliance compliance, ComplianceTask complianceTask, ComplianceSubTask complianceSubTask);
//
//    Reminder findReminderByComplianceOrTaskOrSubTaskAndIdNot(Compliance compliance, ComplianceTask complianceTask, ComplianceSubTask complianceSubTask, Long id);
//
//    Reminder findReminderById(Long id);
      Reminder findReminderByComplianceAndComplianceTaskAndComplianceSubTask(
        Compliance compliance,
        ComplianceTask complianceTask,
        ComplianceSubTask complianceSubTask
);

      @Query("SELECT r FROM Reminder r WHERE " +
              "(r.compliance = :compliance OR r.complianceTask = :complianceTask OR r.complianceSubTask = :complianceSubTask) " +
              "AND r.id <> :id")
      Reminder findReminderByComplianceOrTaskOrSubTaskAndIdNot(
              @Param("compliance") Compliance compliance,
              @Param("complianceTask") ComplianceTask complianceTask,
              @Param("complianceSubTask") ComplianceSubTask complianceSubTask,
              @Param("id") Long id
      );


//    Reminder findReminderByComplianceOrTaskOrSubTask(Compliance compliance, ComplianceTask complianceTask, ComplianceSubTask complianceSubTask);
//
//    Reminder findReminderByComplianceOrTaskOrSubTaskAndIdNot(Compliance compliance, ComplianceTask complianceTask, ComplianceSubTask complianceSubTask, Long id);
//
//    Reminder findReminderById(Long id);


}
