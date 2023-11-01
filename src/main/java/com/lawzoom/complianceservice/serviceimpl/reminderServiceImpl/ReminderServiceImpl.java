package com.lawzoom.complianceservice.serviceimpl.reminderServiceImpl;

import com.lawzoom.complianceservice.dto.reminderDto.ReminderRequest;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.repository.ReminderRepo;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.reminderService.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReminderServiceImpl implements ReminderService {


    @Autowired
    private ReminderRepo reminderRepo;

    @Autowired
    private ComplianceRepo complianceRepo;



//    @Override
//    public ResponseEntity saveReminder(Reminder reminder) {
//        if (reminder.getCompliance() == null
//                && reminder.getComplianceTask() == null && reminder.getComplianceSubTask() == null)
//            return new ResponseEntity().badRequest("Please select compliance/Task/Sub-Task !!");
//
//        Reminder findReminder = this.reminderRepo.findByComplianceAndComplianceTaskAndComplianceSubTask
//                (reminder.getCompliance(), reminder.getComplianceTask(), reminder.getComplianceSubTask());
//
//        if (findReminder != null)
//            return new ResponseEntity().badRequest("Reminder already exist !!");
//        reminder.setCreatedAt(CommonUtil.getDate());
//        reminder.setUpdatedAt(CommonUtil.getDate());
//        reminder.setEnable(true);
//        Reminder saveReminder = this.reminderRepo.save(reminder);
//        if (saveReminder == null)
//            return new ResponseEntity().internalServerError();
//
//        return new ResponseEntity().ok();
//    }

    @Override
    public ResponseEntity saveReminder(ReminderRequest reminderRequest, Long complianceId) {

      Optional<Compliance> compliance = this.complianceRepo.findById(complianceId);
//        Optional<ComplianceSubTask> complianceSubTask = this.complianceRepo.findById(complianceId);
//        Optional<ComplianceTask> complianceTask = this.complianceRepo.findById(complianceId);

        if (!compliance.isPresent()) {
            return new ResponseEntity().badRequest("Please select Compliance");
        }
        Reminder reminder=new Reminder();
//        Optional<Reminder> findReminder = this.reminderRepo.findById(reminder.getId());

//        if (findReminder!=null)
//        return new ResponseEntity().badRequest("Reminder already exist");

        reminder.setReminderDate(reminderRequest.getReminderEndDate());

        reminder.setRepeatTimelineValue(reminder.getRepeatTimelineValue());

//        reminder.setNotificationTimelineValue(reminderRequest.getNotificationTimelineValue() == 0 ? 1 : reminderRequest.getNotificationTimelineValue());

        reminder.setNotificationTimelineValue((reminderRequest.getNotificationTimelineValue()==0 ? 1 : reminderRequest.getNotificationTimelineValue()));


        this.reminderRepo.save(reminder);

        return  new ResponseEntity().ok();
    }
//
//    @Override
//    public ResponseEntity updateReminder(Reminder reminder) {
//        if (reminder.getCompliance() == null
//                && reminder.getComplianceTask() == null && reminder.getComplianceSubTask() == null)
//            return new ResponseEntity().badRequest("Please select compliance/Task/Sub-Task !!");
//
//        Reminder findReminder = this.reminderRepo.findReminderByComplianceOrTaskOrSubTaskAndIdNot
//                (reminder.getCompliance(), reminder.getComplianceTask(), reminder.getComplianceSubTask(), reminder.getId());
//
//        if (findReminder != null)
//            return new ResponseEntity().badRequest("Reminder already exist !!");
//
//        reminder.setUpdatedAt(CommonUtil.getDate());
//        Reminder updateReminder = this.reminderRepo.save(reminder);
//        if (updateReminder == null)
//            return new ResponseEntity().internalServerError();
//
//        return new ResponseEntity().ok();
//    }




    @Override
    public ResponseEntity updateReminder(Reminder reminder) {
        return null;
    }

    public ResponseEntity<?> fetchReminder(Long id) {
        Optional<Reminder> reminder = reminderRepo.findById(id);

        if (!reminder.isPresent()) {

            return new ResponseEntity().badRequest("Reminder Not Found");

        }
        return new ResponseEntity().ok(reminder.get());
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
