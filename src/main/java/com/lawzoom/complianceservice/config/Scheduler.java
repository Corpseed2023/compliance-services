package com.lawzoom.complianceservice.config;

import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.serviceimpl.reminderServiceImpl.ReminderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Scheduler {

    @Autowired
    private ReminderServiceImpl reminderService;

    @Autowired
    private  EmailReminderService emailReminderService;

    @Autowired
    private ComplianceRepo complianceRepo;

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
    }