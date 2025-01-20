package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.renewalDto.MilestoneRenewalResponse;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalRequest;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.complianceMileStoneModel.MileStone;
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
            renewal.setSubscriber(milestone.getSubscriber()); // Assuming milestone has Subscriber reference
            renewal.setCreatedAt(LocalDate.now());
        }

        // Validate request data
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

        // Calculate next reminder date
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


//    @Override
//    public RenewalResponse createRenewalForCompliance(Long complianceId, RenewalRequest renewalRequest) {
//        // Step 1: Validate Compliance
//        Compliance compliance = complianceRepo.findById(complianceId)
//                .orElseThrow(() -> new NotFoundException("Compliance not found with ID: " + complianceId));
//
//        // Step 2: Create a new Renewal
//        Renewal renewal = new Renewal();
//        renewal.setCompliance(compliance);
//        renewal.setNextRenewalDate(renewalRequest.getNextRenewalDate());
//        renewal.setRenewalFrequency(renewalRequest.getRenewalFrequency());
//        renewal.setRenewalType(renewalRequest.getRenewalType());
//        renewal.setRenewalNotes(renewalRequest.getRenewalNotes());
//        renewal.setCreatedAt(LocalDate.now());
//        renewal.setUpdatedAt(LocalDate.now());
//
//        // Step 3: Save the Renewal
//        Renewal savedRenewal = renewalRepository.save(renewal);
//
//        // Step 4: Map to RenewalResponse
//        return mapToRenewalResponse(savedRenewal);
//    }
//
//
//    @Override
//    public MilestoneRenewalResponse createMilestoneRenewal(Long milestoneId, RenewalRequest request) {
//        // Validate Milestone
//        MileStone milestone = milestoneRepository.findById(milestoneId)
//                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + milestoneId));
//
//        // Fetch existing Renewal or create a new one
//        Renewal renewal = renewalRepository.findByMilestoneId(milestoneId);
//        if (renewal == null) {
//            renewal = new Renewal();
//            renewal.setMilestone(milestone);
//            renewal.setCreatedAt(LocalDate.now());
//        }
//
//        // Validate Request Data
//        if (request.getNextRenewalDate() == null) {
//            throw new IllegalArgumentException("Next renewal date cannot be null.");
//        }
//        if (request.getRenewalFrequency() <= 0) {
//            throw new IllegalArgumentException("Renewal frequency must be greater than 0.");
//        }
//        if (request.getRenewalType() == null || request.getRenewalType().isEmpty()) {
//            throw new IllegalArgumentException("Renewal type is required.");
//        }
//
//        // Update Renewal Details
//        renewal.setNextRenewalDate(request.getNextRenewalDate());
//        renewal.setRenewalFrequency(request.getRenewalFrequency());
//        renewal.setRenewalType(request.getRenewalType());
//        renewal.setRenewalNotes(request.getRenewalNotes());
//        renewal.setStopFlag(request.isStopFlag());
//        renewal.setUpdatedAt(LocalDate.now());
//
//        // Save Renewal
//        Renewal savedRenewal = renewalRepository.save(renewal);
//
//        // Manually Set Response
//        MilestoneRenewalResponse response = new MilestoneRenewalResponse();
//        response.setId(savedRenewal.getId());
//        response.setMilestoneId(milestone.getId());
//        response.setNextRenewalDate(savedRenewal.getNextRenewalDate());
//        response.setRenewalFrequency(savedRenewal.getRenewalFrequency());
//        response.setRenewalType(savedRenewal.getRenewalType());
//        response.setRenewalNotes(savedRenewal.getRenewalNotes());
//        response.setStopFlag(savedRenewal.isStopFlag());
//
//        return response;
//    }


    @Override
    public RenewalResponse generateComplianceRenewal(Long complianceId, RenewalRequest request) {
        return null;
    }

    @Override
    public RenewalResponse getRenewalByComplianceId(Long complianceId) {
        return null;
    }

    @Override
    public void deleteRenewal(Long complianceId) {

    }

    @Override
    public RenewalResponse createRenewalForCompliance(Long complianceId, RenewalRequest renewalRequest) {
        return null;
    }
}
