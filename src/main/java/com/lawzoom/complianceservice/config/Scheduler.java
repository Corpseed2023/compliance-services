package com.lawzoom.complianceservice.config;

import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.repository.ReminderRepo;
import com.lawzoom.complianceservice.serviceimpl.reminderServiceImpl.ReminderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class Scheduler {

    @Autowired
    private ReminderServiceImpl reminderService;

    @Autowired
    private EmailReminderService emailReminderService;

    @Autowired
    private ComplianceRepo complianceRepo;

    @Autowired
    private ReminderRepo reminderRepo;

//     Method
//     To trigger the scheduler to run every two seconds
        @Scheduled(fixedRate = 5000)
        public void scheduleTask()

        {
            // Get the current date and time
            Date currentDate = new Date();
            System.out.println(currentDate);

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd-MM-yyyy HH:mm:ss.SSS");
            String strDate = dateFormat.format(new Date());

//            String strDate = dateFormat.format(new Date());

            emailReminderService.sendEmail("kaushlendra.pratap@corpseed.com","ReminderTesting","You Compliance going to expire Soon");

            // Iterate through all reminders in your database
            List<Reminder> reminders = reminderRepo.findAll(); // Modify this based on your data access method
            for (Reminder reminder : reminders) {
                if (reminder.isEnable()) {
                    Date reminderDate = reminder.getReminderDate();
                    int notificationTimelineValue = reminder.getNotificationTimelineValue();

                    // Calculate the notification date by subtracting notificationTimelineValue from the reminder date
                    Calendar notificationDateCalendar = Calendar.getInstance();
                    notificationDateCalendar.setTime(reminderDate);
                    notificationDateCalendar.add(Calendar.DAY_OF_MONTH, -notificationTimelineValue);

                    Date notificationDate = notificationDateCalendar.getTime();

                    // Check if the notification date is in the past and reminderEndDate has not passed
                    if (notificationDate.before(currentDate) && currentDate.before(reminder.getReminderEndDate())) {
                        // Send the email reminder
                        String toEmail = "kaushlendra.pratap@corpseed.com";
                        String subject = "ReminderTesting";
                        String message = "Your Compliance is going to expire soon";
                        emailReminderService.sendEmail(toEmail, subject, message);

                        System.out.println("Reminder sent for Compliance ID: " + reminder.getCompliance().getId());
                    }

                    // Check if reminderEndDate has passed, and if so, mark the reminder as inactive
                    if (currentDate.after(reminder.getReminderEndDate())) {
                        reminder.setEnable(false);
                        reminderRepo.save(reminder); // Update the reminder's isEnable property
                        System.out.println("Reminder for Compliance ID: " + reminder.getCompliance().getId() + " is marked as inactive.");
                    }
                }
            }

            System.out.println("Task Completed");
        }

//    @Scheduled(fixedRate = 500)
//    public void scheduleTask1() {
//
//        Date currentDate = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "dd-MM-yyyy HH:mm:ss.SSS");
//            String strDate = dateFormat.format(new Date());
////        List<Reminder> reminders = reminderRepo.findAll();
////
////        for (Reminder reminder : reminders) {
////            if (reminder.isEnable()) {
//        emailReminderService.sendEmail("kaushlendra.pratap@corpseed.com", "ReminderTesting", "You Compliance going to expire Soon");
//                System.out.println("Task Started aaaaaa");
////            }
//        }

        // Schedule this method to run every day to check for reminders with today's date
//    @Scheduled(cron = "0 0 7,19 * * ?") // Runs at 7 AM and 7 PM
//    public void scheduleTask() {
//        // Get the current date and time
//        Date currentDate = new Date();
//
//        // Iterate through all reminders in your database
//        List<Reminder> reminders = reminderRepo.findAll(); // Modify this based on your data access method
//        for (Reminder reminder : reminders) {
//            if (reminder.isEnable()) {
//                Date reminderDate = reminder.getReminderDate();
//                int notificationTimelineValue = reminder.getNotificationTimelineValue();
//
//                // Calculate the notification date by subtracting notificationTimelineValue from the reminder date
//                Calendar notificationDateCalendar = Calendar.getInstance();
//                notificationDateCalendar.setTime(reminderDate);
//                notificationDateCalendar.add(Calendar.DAY_OF_MONTH, -notificationTimelineValue);
//
//                Date notificationDate = notificationDateCalendar.getTime();
//
//                // Check if the notification date is in the past
//                if (notificationDate.before(currentDate)) {
//                    // Send the email reminder
//                    String toEmail = "kaushlendra.pratap@corpseed.com";
//                    String subject = "ReminderTesting";
//                    String message = "Your Compliance is going to expire soon";
//                    emailReminderService.sendEmail(toEmail, subject, message);
//
//                    System.out.println("Reminder sent for Compliance ID: " + reminder.getCompliance().getId());
//                }
//            }
//        }
//
//        System.out.println("Task Completed");
//    }


    }


        // Schedule this method to run every day to check for reminders with today's date
//    @Scheduled(cron = "0 0 0 * * ?")
//    public void sendDailyEmailReminders() {
//        Date today = new Date();
//        List<Reminder> reminders = reminderRepo.findByReminderDate(today);
//
//        for (Reminder reminder : reminders) {
//            emailReminderService.sendEmailReminder(reminder);
//        }
//    }


//    @Scheduled(fixedRate = 5000)
//    public void scheduleTask1()
//    {
//        SimpleDateFormat dateFormat = new SimpleDateFormat(
//                "dd-MM-yyyy HH:mm:ss.SSS");
////
////            String strDate = dateFormat.format(new Date());
//
//        emailReminderService.sendEmail("kaushlendra.pratap@corpseed.com","ReminderTesting","You Compliance going to expire Soon");
//
//        System.out.println("Task Started ");
//    }
