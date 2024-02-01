package com.lawzoom.complianceservice.serviceimpl.reminderServiceImpl;

import com.lawzoom.complianceservice.dto.reminderDto.ReminderRequest;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.repository.ComplianceTaskRepository;
import com.lawzoom.complianceservice.repository.ReminderRepo;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.reminderService.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class ReminderServiceImpl implements ReminderService {


    @Autowired
    private ReminderRepo reminderRepo;


    @Autowired
    private ComplianceTaskRepository complianceTaskRepository;

    public ResponseEntity saveReminder(ReminderRequest requestDTO) {
        Optional<ComplianceTask> complianceTaskData = complianceTaskRepository.findById(requestDTO.getTaskId());

        if (complianceTaskData.isPresent()) {
            Reminder reminder = new Reminder();
            reminder.setComplianceTask(complianceTaskData.get());
            reminder.setReminderDate(requestDTO.getReminderDate());
            reminder.setReminderEndDate(requestDTO.getReminderEndDate());
            reminder.setNotificationTimelineValue(requestDTO.getNotificationTimelineValue());
            reminder.setRepeatTimelineValue(requestDTO.getRepeatTimelineValue());
            reminder.setRepeatTimelineType(requestDTO.getRepeatTimelineType());

            calculateReminderDates(reminder);

            reminderRepo.save(reminder);

            return new ResponseEntity().ok("Reminder saved successfully");
        } else {
            return new ResponseEntity().ok("ComplianceTask with ID " + requestDTO.getTaskId() + " not found");
        }
    }

    private void calculateReminderDates(Reminder reminder) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reminder.getReminderDate());

        // Calculate the reminder date based on the notification timeline value
        calendar.add(Calendar.DAY_OF_MONTH, -reminder.getNotificationTimelineValue());
        reminder.setReminderDate(calendar.getTime());

        // Calculate subsequent reminder dates based on repeatTimelineValue and repeatTimelineType
        for (int i = 1; i < reminder.getRepeatTimelineValue(); i++) {
            incrementDate(calendar, reminder);
        }
    }

    private void incrementDate(Calendar calendar, Reminder reminder) {
        switch (reminder.getRepeatTimelineType()) {
            case "daily":
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                break;
            case "weekly":
                calendar.add(Calendar.WEEK_OF_YEAR, 1);
                break;
            case "half_yearly":
                calendar.add(Calendar.MONTH, 6);
                break;
            case "yearly":
                calendar.add(Calendar.YEAR, 1);
                break;
            // Handle other frequencies as needed
        }
        reminder.setReminderDate(calendar.getTime());
    }



    @Override
    public ResponseEntity updateReminder(Reminder reminder) {
        return null;
    }

    @Override
    public ResponseEntity fetchReminder(Long id) {
        return null;
    }

    @Override
    public ResponseEntity deleteReminder(Long id) {
        // Check if the reminder exists
        if (reminderRepo.existsById(id)) {
            // If it exists, delete it from the database
            reminderRepo.deleteById(id);
//            return ResponseEntity.ok("Reminder with ID " + id + " deleted successfully");

            return new ResponseEntity().ok("Reminder with ID " + id + " deleted successfully");
        } else {
            // Handle the case where the reminder does not exist
            return ResponseEntity.notFound().build(); // You can customize this response as needed
        }
    }


}
