package com.lawzoom.complianceservice.serviceimpl.reminderServiceImpl;

import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.repository.ReminderRepo;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.reminderService.ReminderService;
import com.lawzoom.complianceservice.utility.CommonUtil;
import com.lawzoom.complianceservice.utility.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private ResponseMapper responseMapper;

    @Autowired
    private ReminderRepo reminderRepo;

    @Override
    public ResponseEntity saveReminder(Reminder reminder) {
        if (reminder.getCompliance() == null
                && reminder.getComplianceTask() == null && reminder.getComplianceSubTask() == null)
            return new ResponseEntity().badRequest("Please select compliance/Task/Sub-Task !!");

        Reminder findReminder = this.reminderRepo.findReminderByComplianceOrTaskOrSubTask
                (reminder.getCompliance(), reminder.getComplianceTask(), reminder.getComplianceSubTask());

        if (findReminder != null)
            return new ResponseEntity().badRequest("Reminder already exist !!");
        reminder.setCreatedAt(CommonUtil.getDate());
        reminder.setUpdatedAt(CommonUtil.getDate());
        reminder.setEnable(true);
        Reminder saveReminder = this.reminderRepo.save(reminder);
        if (saveReminder == null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity updateReminder(Reminder reminder) {
        if (reminder.getCompliance() == null
                && reminder.getComplianceTask() == null && reminder.getComplianceSubTask() == null)
            return new ResponseEntity().badRequest("Please select compliance/Task/Sub-Task !!");

        Reminder findReminder = this.reminderRepo.findReminderByComplianceOrTaskOrSubTaskAndIdNot
                (reminder.getCompliance(), reminder.getComplianceTask(), reminder.getComplianceSubTask(), reminder.getId());

        if (findReminder != null)
            return new ResponseEntity().badRequest("Reminder already exist !!");

        reminder.setUpdatedAt(CommonUtil.getDate());
        Reminder updateReminder = this.reminderRepo.save(reminder);
        if (updateReminder == null)
            return new ResponseEntity().internalServerError();

        return new ResponseEntity().ok();
    }

    @Override
    public ResponseEntity fetchReminder(Long id) {

        Reminder reminder = this.reminderRepo.findReminderById(id);
        if (reminder == null)
            return new ResponseEntity().badRequest("Reminder Not Found !!");

        return new ResponseEntity().ok(this.responseMapper.mapToReminderResponse(reminder));
    }

    @Override
    public ResponseEntity deleteReminder(Long id) {
        // Check if the reminder exists
        if (reminderRepo.existsById(id)) {
            // If it exists, delete it from the database
            reminderRepo.deleteById(id);
            return ResponseEntity.ok("Reminder with ID " + id + " deleted successfully");
        } else {
            // Handle the case where the reminder does not exist
            return ResponseEntity.notFound().build(); // You can customize this response as needed
        }
    }
}
