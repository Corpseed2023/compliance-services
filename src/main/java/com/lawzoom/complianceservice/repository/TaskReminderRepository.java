package com.lawzoom.complianceservice.repository;


import com.lawzoom.complianceservice.model.reminderModel.TaskReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskReminderRepository extends JpaRepository<TaskReminder,Long> {
    List<TaskReminder> findByIsEnableAndReminderDateBefore(boolean b, Date currentDate);
}
