package com.lawzoom.complianceservice.controller.reminderController;

import com.lawzoom.complianceservice.dto.reminderDto.TaskReminderRequest;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.reminderService.TaskReminderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/compliance/task/reminder")
public class TaskReminderController {

        @Autowired
        private TaskReminderService taskReminderService;


    @PostMapping("/createTaskReminder")
    public ResponseEntity<String> saveReminder(@RequestBody @Valid TaskReminderRequest taskReminderRequest,@RequestParam Long userId) {
        try {
            if (!isFutureDate(taskReminderRequest.getReminderDate())) {
                return new ResponseEntity<>().badRequest("Please enter a future date for the reminder");
            }

            taskReminderService.saveTaskReminder(taskReminderRequest,userId);
            return new ResponseEntity().ok("Reminder created successfully");
        } catch (Exception e) {
            return new ResponseEntity<>().internalServerError();
        }
    }

    private boolean isFutureDate(Date date) {
        Date today = new Date();
        return date.after(today) || date.equals(today);
    }


    @PutMapping("/updateTaskReminder")
    public ResponseEntity<String> updateReminder(@RequestParam Long taskReminderId, @RequestBody @Valid TaskReminderRequest taskReminderRequest) {
        try {

            if (!isFutureDate(taskReminderRequest.getReminderDate())) {
                return new ResponseEntity<>().badRequest("Please enter a future date for the reminder");
            }

            taskReminderService.updateTaskReminder(taskReminderRequest,taskReminderId);
            return new ResponseEntity().ok("Reminder updated successfully");
        } catch (Exception e) {
            return new ResponseEntity<>().internalServerError();
        }
    }






}
