//package com.lawzoom.complianceservice.config;
//
//import com.lawzoom.complianceservice.model.reminderModel.Reminder;
//import com.lawzoom.complianceservice.services.reminderService.ReminderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class ReminderEmailScheduler {
//
//    @Autowired
//    private ReminderService reminderService;
//
//    @Autowired
//    private EmailReminderService emailReminderService;
//
//    @Scheduled(fixedRate = 60000)
//    public void sendEmailReminders() {
//        List<Reminder> reminders = reminderService.getUpcomingReminders();
//
//        for (Reminder reminder : reminders) {
//            emailReminderService.sendEmailReminder(reminder);
////            reminder.se(false); // Mark the reminder as sent
//            reminderService.saveReminder(reminder);
//        }
//    }
//}
