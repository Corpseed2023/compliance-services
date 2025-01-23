package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.dto.complianceReminder.ReminderRequest;
import com.lawzoom.complianceservice.dto.complianceReminder.ReminderResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.complianceRepo.ComplianceRepo;
import com.lawzoom.complianceservice.repository.ReminderRepositroy.ReminderRepository;
import com.lawzoom.complianceservice.repository.SubscriberRepository;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReminderServiceImpl implements ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private ComplianceRepo complianceRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ReminderResponse createReminder(Long complianceId, Long subscriberId,
                                           ReminderRequest request) {
        // Validate Compliance
        Compliance compliance = complianceRepository.findById(complianceId)
                .orElseThrow(() -> new NotFoundException("Compliance not found with ID: " + complianceId));

        // Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + subscriberId));

        // Validate Users
        User superAdmin = subscriber.getSuperAdmin();

        User createdBy = userRepository.findActiveUserById(request.getCreatedBy());
        if (createdBy == null) {
            throw new NotFoundException("User not found with ID: " + request.getCreatedBy());
        }

        User whomToSend = userRepository.findActiveUserById(request.getWhomToSend());
        if (createdBy == null) {
            throw new NotFoundException("Recipient user not found with ID :" + request.getWhomToSend());
        }

        // Create Reminder
        Reminder reminder = new Reminder();
        reminder.setCreatedBy(createdBy);
        reminder.setReminderDate(request.getReminderDate());
        reminder.setReminderEndDate(request.getReminderEndDate());
        reminder.setNotificationTimelineValue(request.getNotificationTimelineValue());
        reminder.setRepeatTimelineValue(request.getRepeatTimelineValue());
        reminder.setRepeatTimelineType(request.getRepeatTimelineType());
        reminder.setCreatedAt(new Date());
        reminder.setUpdatedAt(new Date());

        Reminder savedReminder = reminderRepository.save(reminder);

        // Map to Response DTO
        ReminderResponse response = new ReminderResponse();
        response.setId(savedReminder.getId());
        response.setSubscriberId(savedReminder.getSubscriber().getId());
        response.setReminderDate(savedReminder.getReminderDate());
        response.setReminderEndDate(savedReminder.getReminderEndDate());
        response.setNotificationTimelineValue(savedReminder.getNotificationTimelineValue());
        response.setRepeatTimelineValue(savedReminder.getRepeatTimelineValue());
        response.setRepeatTimelineType(savedReminder.getRepeatTimelineType());
        response.setCreatedBy(savedReminder.getCreatedBy().getId());

        return response;
    }


}
