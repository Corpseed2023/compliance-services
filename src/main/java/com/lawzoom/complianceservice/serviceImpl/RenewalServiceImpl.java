package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.renewalDto.MilestoneRenewalResponse;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalRequest;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import com.lawzoom.complianceservice.repository.complianceRepo.ComplianceRepo;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
import com.lawzoom.complianceservice.repository.RenewalRepository.RenewalRepository;
import com.lawzoom.complianceservice.repository.taskRepo.TaskRepository;
import com.lawzoom.complianceservice.service.RenewalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RenewalServiceImpl implements RenewalService {

    @Autowired
    private RenewalRepository renewalRepository;

    @Autowired
    private ComplianceRepo complianceRepo;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private TaskRepository taskRepository;


    @Override
    public MilestoneRenewalResponse createMilestoneRenewal(Long milestoneId, RenewalRequest request) {
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + milestoneId));

        Renewal renewal = renewalRepository.findByMilestoneId(milestoneId);
        if (renewal == null) {
            renewal = new Renewal();
            renewal.setMilestone(milestone);
            renewal.setSubscriber(milestone.getSubscriber());
            renewal.setCreatedAt(LocalDate.now());
        }

        if (request.getIssuedDate() == null || request.getExpiryDate() == null) {
            throw new IllegalArgumentException("Issued date and expiry date cannot be null.");
        }

        // Update Renewal details
        renewal.setIssuedDate(request.getIssuedDate());
        renewal.setExpiryDate(request.getExpiryDate());
        renewal.setReminderDurationType(request.getReminderDurationType());
        renewal.setReminderDurationValue(request.getReminderDurationValue());
        renewal.setRenewalNotes(request.getRenewalNotes());
        renewal.setStopFlag(request.isStopFlag());

        renewal.calculateNextReminderDate();

        renewal.setUpdatedAt(LocalDate.now());

        Renewal savedRenewal = renewalRepository.save(renewal);

        // Map to MilestoneRenewalResponse
        MilestoneRenewalResponse response = new MilestoneRenewalResponse();
        response.setId(savedRenewal.getId());
        response.setMilestoneId(milestone.getId());
        response.setIssuedDate(savedRenewal.getIssuedDate());
        response.setExpiryDate(savedRenewal.getExpiryDate());
        response.setReminderDurationType(savedRenewal.getReminderDurationType());
        response.setReminderDurationValue(savedRenewal.getReminderDurationValue());
        response.setNextReminderDate(savedRenewal.getNextReminderDate());
        response.setRenewalNotes(savedRenewal.getRenewalNotes());
        response.setStopFlag(savedRenewal.isStopFlag());
        response.setReminderFrequency(savedRenewal.getReminderFrequency());

        return response;
    }
    private RenewalResponse mapToRenewalResponse(Renewal renewal) {
        RenewalResponse response = new RenewalResponse();
        response.setId(renewal.getId());
        response.setMilestoneId(renewal.getMilestone() != null ? renewal.getMilestone().getId() : null);
        response.setIssuedDate(renewal.getIssuedDate());
        response.setExpiryDate(renewal.getExpiryDate());
        response.setReminderDurationType(renewal.getReminderDurationType());
        response.setReminderDurationValue(renewal.getReminderDurationValue());
        response.setNextReminderDate(renewal.getNextReminderDate());
        response.setRenewalNotes(renewal.getRenewalNotes());
        response.setStopFlag(renewal.isStopFlag());
        response.setReminderFrequency(renewal.getReminderFrequency());
        return response;
    }

    @Override
    public MilestoneRenewalResponse updateMilestoneRenewal(Long renewalId, RenewalRequest request) {
        Renewal renewal = renewalRepository.findById(renewalId)
                .orElseThrow(() -> new NotFoundException("Renewal not found with ID: " + renewalId));

        if (request.getIssuedDate() == null || request.getExpiryDate() == null) {
            throw new IllegalArgumentException("Issued date and expiry date cannot be null.");
        }

        renewal.setIssuedDate(request.getIssuedDate());
        renewal.setExpiryDate(request.getExpiryDate());
        renewal.setReminderDurationType(request.getReminderDurationType());
        renewal.setReminderDurationValue(request.getReminderDurationValue());
        renewal.setRenewalNotes(request.getRenewalNotes());
        renewal.setStopFlag(request.isStopFlag());

        renewal.calculateNextReminderDate();

        renewal.setUpdatedAt(LocalDate.now());

        Renewal updatedRenewal = renewalRepository.save(renewal);

        MilestoneRenewalResponse response = new MilestoneRenewalResponse();
        response.setId(updatedRenewal.getId());
        response.setMilestoneId(updatedRenewal.getMilestone() != null ? updatedRenewal.getMilestone().getId() : null);
        response.setIssuedDate(updatedRenewal.getIssuedDate());
        response.setExpiryDate(updatedRenewal.getExpiryDate());
        response.setReminderDurationType(updatedRenewal.getReminderDurationType());
        response.setReminderDurationValue(updatedRenewal.getReminderDurationValue());
        response.setNextReminderDate(updatedRenewal.getNextReminderDate());
        response.setRenewalNotes(updatedRenewal.getRenewalNotes());
        response.setStopFlag(updatedRenewal.isStopFlag());
        response.setReminderFrequency(updatedRenewal.getReminderFrequency());

        return response;
    }





}
