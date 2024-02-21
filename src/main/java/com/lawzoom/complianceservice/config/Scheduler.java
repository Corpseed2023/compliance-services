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
    private TaskReminderRepository taskReminderRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailReminderService emailReminderService;

    // Scheduled to run every 30 seconds
    @Scheduled(cron = "*/30 * * * * *")
    public void scheduleTask() {
        // Get the current date and time
        Date currentDate = new Date();
        System.out.println(currentDate);

        // Retrieve only enabled tasks with a reminder date in the past and a reminder end date not yet reached
        List<TaskReminder> taskReminders = taskReminderRepository.findByIsEnableAndReminderDateBefore(
                true, currentDate);

        for (TaskReminder taskReminder : taskReminders) {
            // Check if the reminder has not been sent
            if (!taskReminder.isReminderSent()) {
                // Send the notification reminder
                String toUser = "kaushlendra.pratap@corpseed.com";
                String notificationSubject = "Reminder: " + taskReminder.getComplianceTask().getTaskName();
                String notificationMessage = "Your Compliance is going to expire soon. Task ID: " + taskReminder.getId();
                notificationService.sendNotification(toUser, notificationSubject, notificationMessage);

                // Send the email update
                String toEmail = "kaushuthakur610@gmail.com";
                String emailSubject = "Update: " + taskReminder.getComplianceTask().getTaskName();
                String emailMessage = "Your Compliance is going to expire soon. Task ID: " + taskReminder.getId();
                emailReminderService.sendEmail(toEmail, emailSubject, emailMessage);

                // Set the reminderSent flag to true
                taskReminder.setReminderSent(true);

                // Set isEnable to false since the reminder has been sent
                taskReminder.setEnable(false);
                taskReminderRepository.save(taskReminder); // Update the reminder's isEnable and reminderSent properties

                // For additional actions or logging if needed
                System.out.println("Notification and Email update sent for Task ID: " + taskReminder.getId());
                System.out.println("Task Reminder for Task ID: " + taskReminder.getId() + " marked as inactive.");
            }

            // Check if reminderDate has passed, and if so, mark the reminder as inactive
            if (currentDate.after(taskReminder.getReminderDate())) {
                taskReminder.setEnable(false);
                taskReminderRepository.save(taskReminder); // Update the reminder's isEnable property

                // For additional actions or logging if needed
                System.out.println("Reminder for Task ID: " + taskReminder.getId() + " is marked as inactive.");
            }
        }

        System.out.println("Task Completed");
    }
}
