package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.complianceReminder.ReminderRequest;
import com.lawzoom.complianceservice.dto.complianceReminder.ReminderResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
import com.lawzoom.complianceservice.repository.ReminderRepositroy.ReminderRepository;
import com.lawzoom.complianceservice.repository.SubscriberRepository;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    public ReminderResponse createOrUpdateReminder(ReminderRequest request) {
        Reminder reminder = request.getReminderId() != null
                ? reminderRepository.findById(request.getReminderId()).orElse(new Reminder())
                : new Reminder();

        reminder.setUpdatedAt(new Date());

        // Assign subscriber
        reminder.setSubscriber(subscriberRepository.findById(request.getSubscriberId())
                .orElseThrow(() -> new NotFoundException("Subscriber not found")));

        // Assign createdBy user
        if (request.getCreatedBy() != null) {
            reminder.setUser(userRepository.findById(request.getCreatedBy())
                    .orElseThrow(() -> new NotFoundException("User not found")));
        }

        // Assign milestone
        if (request.getMilestoneId() != null) {
            reminder.setMilestone(milestoneRepository.findById(request.getMilestoneId())
                    .orElseThrow(() -> new NotFoundException("Milestone not found")));
        }

        // Set optional fields
        reminder.setReminderDate(request.getReminderDate());
        reminder.setReminderEndDate(request.getReminderEndDate());
        reminder.setNotificationTimelineValue(request.getNotificationTimelineValue());
        reminder.setRepeatTimelineValue(request.getRepeatTimelineValue());
        reminder.setRepeatTimelineType(request.getRepeatTimelineType());
        reminder.setStopFlag(request.getStopFlag());

        Reminder savedReminder = reminderRepository.save(reminder);

        ReminderResponse response = new ReminderResponse();
        response.setId(savedReminder.getId());
        response.setMilestoneId(savedReminder.getMilestone() != null ? savedReminder.getMilestone().getId() : null);
        response.setSubscriberId(savedReminder.getSubscriber().getId());
        response.setReminderDate(savedReminder.getReminderDate());
        response.setReminderEndDate(savedReminder.getReminderEndDate());
        response.setNotificationTimelineValue(savedReminder.getNotificationTimelineValue());
        response.setRepeatTimelineValue(savedReminder.getRepeatTimelineValue());
        response.setRepeatTimelineType(savedReminder.getRepeatTimelineType());
        response.setStopFlag(savedReminder.getStopFlag());
        response.setUserId(savedReminder.getUser().getId());

        return response;
    }

//    @Override
//    public Map<String, Object> fetchAllRemindersByUserId(Long userId) {
//        // Fetch user details
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
//
//        List<Reminder> reminders;
//
//        // If user is SUPER_ADMIN or ADMIN, fetch all reminders
//        if ("SUPER_ADMIN".equalsIgnoreCase(user.getRoles()) || "ADMIN".equalsIgnoreCase(user.getRole())) {
//            reminders = reminderRepository.findAll();
//        } else {
//            // Fetch only reminders created by the user
//            reminders = reminderRepository.findByUser(user);
//        }
//
//        // Prepare response as Map
//        Map<String, Object> response = new HashMap<>();
//        response.put("status", "success");
//        response.put("totalReminders", reminders.size());
//        response.put("reminders", reminders.stream().map(this::mapToResponse).collect(Collectors.toList()));
//
//        return response;
//    }
//
//    private Map<String, Object> mapToResponse(Reminder reminder) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("reminderId", reminder.getId());
//        response.put("milestoneId", reminder.getMilestone() != null ? reminder.getMilestone().getId() : null);
//        response.put("subscriberId", reminder.getSubscriber().getId());
//        response.put("reminderDate", reminder.getReminderDate());
//        response.put("reminderEndDate", reminder.getReminderEndDate());
//        response.put("notificationTimelineValue", reminder.getNotificationTimelineValue());
//        response.put("repeatTimelineValue", reminder.getRepeatTimelineValue());
//        response.put("repeatTimelineType", reminder.getRepeatTimelineType());
//        response.put("stopFlag", reminder.getStopFlag());
//        response.put("userId", reminder.getUser().getId());
//        return response;
//    }


    @Override
    public Map<String, Object> fetchAllRemindersByUserId(Long userId) {
        return null;
    }
}
