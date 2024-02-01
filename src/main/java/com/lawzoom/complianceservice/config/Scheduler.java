package com.lawzoom.complianceservice.config;

import com.lawzoom.complianceservice.model.reminderModel.TaskReminder;
import com.lawzoom.complianceservice.repository.TaskReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class Scheduler {

    @Autowired
    private EmailReminderService emailReminderService;

    @Autowired
    private TaskReminderRepository taskReminderRepository;

    // Scheduled to run every 30 seconds
    @Scheduled(cron = "*/30 * * * * *")
    public void scheduleTask() {
        // Get the current date and time
        Date currentDate = new Date();
        System.out.println(currentDate);

        // Retrieve only enabled tasks with a reminder date in the past and a reminder end date not yet reached
        List<TaskReminder> taskReminders = taskReminderRepository.findByIsEnableAndReminderDateBeforeAndReminderEndDateAfter(
                true, currentDate, currentDate);

        for (TaskReminder taskReminder : taskReminders) {
            // Check if the reminder has not been sent
            if (!taskReminder.isReminderSent()) {
                // Send the email reminder
                String toEmail = "kaushlendra.pratap@corpseed.com";
                String subject = "Reminder: " + taskReminder.getComplianceTask().getTaskName();
                String message = "Your Compliance is going to expire soon. Task ID: " + taskReminder.getId();
                emailReminderService.sendEmail(toEmail, subject, message);

                // Set the reminderSent flag to true
                taskReminder.setReminderSent(true);

                // For additional actions or logging if needed
                System.out.println("Reminder sent for Task ID: " + taskReminder.getId());
            }

            // Check if reminderEndDate has passed, and if so, mark the reminder as inactive
            if (currentDate.after(taskReminder.getReminderEndDate())) {
                taskReminder.setEnable(false);
                taskReminderRepository.save(taskReminder); // Update the reminder's isEnable property

                // For additional actions or logging if needed
                System.out.println("Reminder for Task ID: " + taskReminder.getId() + " is marked as inactive.");
            }
        }

        System.out.println("Task Completed");
    }
}
