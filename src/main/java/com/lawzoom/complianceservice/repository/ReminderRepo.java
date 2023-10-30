package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderRepo extends JpaRepository<Reminder,Long>

{

//Reminder findByComplianceAndComplianceTaskAndComplianceSubTask(
//        Compliance compliance, ComplianceTask complianceTask, ComplianceSubTask complianceSubTask
//);
//
//      @Query("SELECT r FROM Reminder r WHERE " +
//              "(r.compliance = :compliance OR r.complianceTask = :complianceTask OR r.complianceSubTask = :complianceSubTask) " +
//              "AND r.id <> :id")
//      Reminder findReminderByComplianceOrTaskOrSubTaskAndIdNot(
//              @Param("compliance") Compliance compliance,
//              @Param("complianceTask") ComplianceTask complianceTask,
//              @Param("complianceSubTask") ComplianceSubTask complianceSubTask,
//              @Param("id") Long id
//      );

      Reminder findByCompliance(Long compliance);


//    Reminder findReminderByComplianceOrTaskOrSubTask(Compliance compliance, ComplianceTask complianceTask, ComplianceSubTask complianceSubTask);
//
//    Reminder findReminderByComplianceOrTaskOrSubTaskAndIdNot(Compliance compliance, ComplianceTask complianceTask, ComplianceSubTask complianceSubTask, Long id);
//
//    Reminder findReminderById(Long id);


}
