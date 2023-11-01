package com.lawzoom.complianceservice.config;

import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.repository.ReminderRepo;
import com.lawzoom.complianceservice.serviceimpl.reminderServiceImpl.ReminderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class Scheduler {

    @Autowired
    private ReminderServiceImpl reminderService;

    @Autowired
    private  EmailReminderService emailReminderService;

    @Autowired
    private ComplianceRepo complianceRepo;

    @Autowired
    private ReminderRepo reminderRepo;

        // Method
        // To trigger the scheduler to run every two seconds
        @Scheduled(fixedRate = 5000)
        public void scheduleTask()
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd-MM-yyyy HH:mm:ss.SSS");
//
//            String strDate = dateFormat.format(new Date());

            emailReminderService.sendEmail("kaushlendra.pratap@corpseed.com","ReminderTesting","You Compliance going to expire Soon");

            System.out.println("Task Started ");
        }

    // Schedule this method to run every day to check for reminders with today's date
    @Scheduled(cron = "0 0 0 * * ?")
    public void sendDailyEmailReminders() {
        Date today = new Date();
        List<Reminder> reminders = reminderRepo.findByReminderDate(today);

        for (Reminder reminder : reminders) {
            emailReminderService.sendEmailReminder(reminder);
        }
    }

    }