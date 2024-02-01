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

    // Scheduled to run every two seconds
    @Scheduled(fixedRate = 5000)
    public void scheduleTask() {
        // Get the current date and time
        Date currentDate = new Date();
        System.out.println(currentDate);

        // Iterate through all reminders in your database
        List<TaskReminder> taskReminders = taskReminderRepository.findAll(); // Modify this based on your data access method
        for (TaskReminder taskReminder : taskReminders) {
            if (taskReminder.isEnable()) {
                Date reminderDate = taskReminder.getReminderDate();

                // Check if the reminder date is in the past and reminderEndDate has not passed
                if (reminderDate.before(currentDate) && currentDate.before(taskReminder.getReminderEndDate())) {
                    // Send the email reminder
                    String toEmail = "kaushlendra.pratap@corpseed.com";
                    String subject = "Reminder: " + taskReminder.getComplianceTask().getTaskName(); // Modify as needed
                    String message = "Your Compliance is going to expire soon. Task ID: " + taskReminder.getId(); // Modify as needed
                    emailReminderService.sendEmail(toEmail, subject, message);

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
        }

        System.out.println("Task Completed");
    }
}
