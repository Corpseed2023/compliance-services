package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.reminderModel.TaskReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskReminderRepository extends JpaRepository<TaskReminder, Long> {
    void deleteByTaskId(Long id);
}
