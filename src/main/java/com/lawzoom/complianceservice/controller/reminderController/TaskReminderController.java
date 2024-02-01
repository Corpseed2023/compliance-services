package com.lawzoom.complianceservice.controller.reminderController;

import com.lawzoom.complianceservice.dto.reminderDto.TaskReminderRequest;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.repository.ComplianceTaskRepository;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.reminderService.TaskReminderService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/compliance/task/reminder")
public class TaskReminderController {

        @Autowired
        private TaskReminderService taskReminderService;


    @PostMapping("/create")
    public ResponseEntity<String> saveReminder(@RequestBody @Valid TaskReminderRequest taskReminderRequest) {
        try {
            if (!isFutureDate(taskReminderRequest.getReminderDate())) {
                return new ResponseEntity<>().badRequest("Please enter a future date for the reminder");
            }

            taskReminderService.saveTaskReminder(taskReminderRequest);
            return new ResponseEntity().ok("Reminder created successfully");
        } catch (Exception e) {
            return new ResponseEntity<>().internalServerError();
        }
    }

    private boolean isFutureDate(Date date) {
        Date today = new Date();
        return date.after(today) || date.equals(today);
    }
}
