package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.complianceReminder.ComplianceReminderRequest;
import com.lawzoom.complianceservice.dto.complianceReminder.ComplianceReminderResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.reminderModel.ComplianceReminder;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.ComplianceReminderRepository;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.repository.SubscriberRepository;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.service.ComplianceReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ComplianceReminderServiceImpl implements ComplianceReminderService {

    @Autowired
    private ComplianceReminderRepository complianceReminderRepository;

    @Autowired
    private ComplianceRepo complianceRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ComplianceReminderResponse createComplianceReminder(Long complianceId, Long subscriberId,
                                                               ComplianceReminderRequest request) {
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

        // Create ComplianceReminder
        ComplianceReminder reminder = new ComplianceReminder();
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
        reminder.setEnable(request.isEnable());

        ComplianceReminder savedReminder = complianceReminderRepository.save(reminder);

        // Map to Response DTO
        ComplianceReminderResponse response = new ComplianceReminderResponse();
        response.setId(savedReminder.getId());
        response.setComplianceId(savedReminder.getCompliance().getId());
        response.setSubscriberId(savedReminder.getSubscriber().getId());
        response.setReminderDate(savedReminder.getReminderDate());
        response.setReminderEndDate(savedReminder.getReminderEndDate());
        response.setNotificationTimelineValue(savedReminder.getNotificationTimelineValue());
        response.setRepeatTimelineValue(savedReminder.getRepeatTimelineValue());
        response.setRepeatTimelineType(savedReminder.getRepeatTimelineType());
        response.setEnable(savedReminder.isEnable());
        response.setCreatedBy(savedReminder.getCreatedBy().getId());
        response.setWhomToSend(savedReminder.getWhomToSend().getId());
        response.setSuperAdminId(savedReminder.getSuperAdmin().getId());

        return response;
    }

    @Override
    public List<ComplianceReminderResponse> fetchComplianceReminders(Long complianceId, Long subscriberId) {
        // Validate Compliance
        Compliance compliance = complianceRepository.findById(complianceId)
                .orElseThrow(() -> new NotFoundException("Compliance not found with ID: " + complianceId));

        // Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + subscriberId));

        // Fetch reminders
        List<ComplianceReminder> reminders = complianceReminderRepository.findByComplianceAndSubscriber(compliance, subscriber);

        // Map to Response DTO
        List<ComplianceReminderResponse> responseList = new ArrayList<>();
        for (ComplianceReminder reminder : reminders) {
            ComplianceReminderResponse response = new ComplianceReminderResponse();
            response.setId(reminder.getId());
            response.setComplianceId(reminder.getCompliance().getId());
            response.setSubscriberId(reminder.getSubscriber().getId());
            response.setReminderDate(reminder.getReminderDate());
            response.setReminderEndDate(reminder.getReminderEndDate());
            response.setNotificationTimelineValue(reminder.getNotificationTimelineValue());
            response.setRepeatTimelineValue(reminder.getRepeatTimelineValue());
            response.setRepeatTimelineType(reminder.getRepeatTimelineType());
            response.setEnable(reminder.isEnable());
            response.setCreatedBy(reminder.getCreatedBy().getId());
            response.setWhomToSend(reminder.getWhomToSend().getId());
            response.setSuperAdminId(reminder.getSuperAdmin().getId());
            responseList.add(response);
        }

        return responseList;
    }


}
