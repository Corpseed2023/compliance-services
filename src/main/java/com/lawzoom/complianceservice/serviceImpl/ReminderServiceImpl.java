package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.dto.complianceReminder.ReminderRequest;
import com.lawzoom.complianceservice.dto.complianceReminder.ReminderResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.repository.ReminderRepository;
import com.lawzoom.complianceservice.repository.SubscriberRepository;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        reminder.setCompliance(compliance);
        reminder.setSubscriber(subscriber);
        reminder.setSuperAdmin(superAdmin);
        reminder.setCreatedBy(createdBy);
        reminder.setWhomToSend(whomToSend);
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
        response.setComplianceId(savedReminder.getCompliance().getId());
        response.setSubscriberId(savedReminder.getSubscriber().getId());
        response.setReminderDate(savedReminder.getReminderDate());
        response.setReminderEndDate(savedReminder.getReminderEndDate());
        response.setNotificationTimelineValue(savedReminder.getNotificationTimelineValue());
        response.setRepeatTimelineValue(savedReminder.getRepeatTimelineValue());
        response.setRepeatTimelineType(savedReminder.getRepeatTimelineType());
        response.setCreatedBy(savedReminder.getCreatedBy().getId());
        response.setWhomToSend(savedReminder.getWhomToSend().getId());
        response.setSuperAdminId(savedReminder.getSuperAdmin().getId());

        return response;
    }

    @Override
    public List<ReminderResponse> fetchReminders(Long complianceId, Long subscriberId) {
        // Validate Compliance
        Compliance compliance = complianceRepository.findById(complianceId)
                .orElseThrow(() -> new NotFoundException("Compliance not found with ID: " + complianceId));

        // Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + subscriberId));

        // Fetch reminders
        List<Reminder> reminders = reminderRepository.findByComplianceAndSubscriber(compliance, subscriber);

        // Map to Response DTO
        List<ReminderResponse> responseList = new ArrayList<>();
        for (Reminder reminder : reminders) {
            responseList.add(mapToReminderResponse(reminder));
        }

        return responseList;
    }

    private ReminderResponse mapToReminderResponse(Reminder reminder) {
        ReminderResponse response = new ReminderResponse();
        response.setId(reminder.getId());
        response.setComplianceId(reminder.getCompliance().getId());
        response.setSubscriberId(reminder.getSubscriber().getId());
        response.setReminderDate(reminder.getReminderDate());
        response.setReminderEndDate(reminder.getReminderEndDate());
        response.setNotificationTimelineValue(reminder.getNotificationTimelineValue());
        response.setRepeatTimelineValue(reminder.getRepeatTimelineValue());
        response.setRepeatTimelineType(reminder.getRepeatTimelineType());
        response.setCreatedBy(reminder.getCreatedBy().getId());
        response.setWhomToSend(reminder.getWhomToSend().getId());
        response.setSuperAdminId(reminder.getSuperAdmin().getId());
        return response;
    }

}
