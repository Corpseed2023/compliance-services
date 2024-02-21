package com.lawzoom.complianceservice.serviceimpl.reminderServiceImpl;

import com.lawzoom.complianceservice.dto.reminderDto.TaskReminderRequest;
import com.lawzoom.complianceservice.dto.userDto.UserResponse;
import com.lawzoom.complianceservice.feignClient.AuthenticationFeignClient;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.model.reminderModel.TaskReminder;
import com.lawzoom.complianceservice.repository.ComplianceTaskRepository;
import com.lawzoom.complianceservice.repository.TaskReminderRepository;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.reminderService.TaskReminderService;
import io.micrometer.common.util.StringUtils;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class TaskReminderServiceImpl implements TaskReminderService {

    @Autowired
    private TaskReminderRepository taskReminderRepository;

    @Autowired
    private ComplianceTaskRepository complianceTaskRepository;

    @Autowired
    private AuthenticationFeignClient authenticationFeignClient;



    public ResponseEntity<String> saveTaskReminder(TaskReminderRequest taskReminderRequest, Long userId) {
        Long complianceTaskId = taskReminderRequest.getComplianceTaskId();


        UserResponse userData = authenticationFeignClient.getUserId(userId);

        if (userData == null || StringUtils.isBlank(userData.getEmail())) {
            throw new IllegalArgumentException("Invalid user data. Cannot create compliance.");
        }

        System.out.println(userData.getEmail());

        Optional<ComplianceTask> complianceTaskOptional = complianceTaskRepository.findById(complianceTaskId);

        if (complianceTaskOptional.isPresent()) {
            ComplianceTask complianceTask = complianceTaskOptional.get();

            // Convert reminder date to UTC
            Date convertedReminderDate = convertToUTC(taskReminderRequest.getReminderDate());

            TaskReminder taskReminder = TaskReminder.builder()
                    .complianceTask(complianceTask)
                    .complianceSubTask(null)
                    .isEnable(true)
                    .reminderDate(convertedReminderDate)
                    .taskRemark(taskReminderRequest.getTaskRemark())
                    .setByUser(taskReminderRequest.getSetByUser())
                    .createdAt(taskReminderRequest.getCreatedAt())
                    .updatedAt(taskReminderRequest.getUpdatedAt())
                    .build();

            taskReminderRepository.save(taskReminder);
            return new ResponseEntity().ok("Reminder saved successfully");
        } else {
            throw new NotFoundException("Compliance Task not found");
        }
    }

    private Date convertToUTC(Date date) {
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, -5);
            calendar.add(Calendar.MINUTE, -30);
            return calendar.getTime();
        } else {
            return null;
        }
    }


    @Override
    public ResponseEntity<String> updateTaskReminder(TaskReminderRequest taskReminderRequest, Long taskReminderId) {
        Optional<TaskReminder> existingTaskReminderOptional = taskReminderRepository.findById(taskReminderId);

        if (existingTaskReminderOptional.isPresent()) {
            TaskReminder existingTaskReminder = existingTaskReminderOptional.get();

            // Perform any necessary validations or checks before updating

            Date currentDate = new Date();

            // Check if the reminder has already been sent
            if (existingTaskReminder.isReminderSent()) {
                return new ResponseEntity<>().badRequest("Update not allowed. Reminder has already been sent.");
            }

            // Check if the update is allowed (before reminder date and before reminder end date)
            if (currentDate.before(existingTaskReminder.getReminderDate()) ) {
                existingTaskReminder.setReminderDate(taskReminderRequest.getReminderDate());
                existingTaskReminder.setTaskRemark(taskReminderRequest.getTaskRemark());
                existingTaskReminder.setSetByUser(taskReminderRequest.getSetByUser());
                existingTaskReminder.setUpdatedAt(new Date());

                taskReminderRepository.save(existingTaskReminder);

                return new ResponseEntity().ok("Reminder updated successfully");
            } else {
                return new ResponseEntity<>().badRequest("Update not allowed. Reminder date or end date has passed.");
            }
        } else {
            return new ResponseEntity<>().notFound("Task Reminder not found");
        }
    }



}
