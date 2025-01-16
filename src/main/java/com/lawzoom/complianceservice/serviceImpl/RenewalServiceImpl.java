package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.renewalDto.MilestoneRenewalResponse;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalRequest;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.complianceMileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
import com.lawzoom.complianceservice.repository.RenewalRepository.RenewalRepository;
import com.lawzoom.complianceservice.repository.TaskRepository;
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
    public RenewalResponse generateComplianceRenewal(Long complianceId, RenewalRequest request) {

        Compliance compliance = complianceRepo.findById(complianceId)
                .orElseThrow(() -> new NotFoundException("Compliance not found with ID: " + complianceId));

        Renewal renewal = renewalRepository.findByComplianceId(complianceId);
        if (renewal == null) {
            renewal = new Renewal();
            renewal.setCompliance(compliance);
            renewal.setCreatedAt(LocalDate.now());
        }

        // Update Renewal details
        renewal.setNextRenewalDate(request.getNextRenewalDate());
        renewal.setRenewalFrequency(request.getRenewalFrequency());
        renewal.setRenewalType(request.getRenewalType());
        renewal.setRenewalNotes(request.getRenewalNotes());
        renewal.setStopFlag(request.isStopFlag()); // Set stopFlag
        renewal.setUpdatedAt(LocalDate.now());

        Renewal savedRenewal = renewalRepository.save(renewal);

        RenewalResponse response = new RenewalResponse();
        response.setId(savedRenewal.getId());
        response.setComplianceId(savedRenewal.getCompliance().getId());
        response.setNextRenewalDate(savedRenewal.getNextRenewalDate());
        response.setRenewalFrequency(savedRenewal.getRenewalFrequency());
        response.setRenewalType(savedRenewal.getRenewalType());
        response.setRenewalNotes(savedRenewal.getRenewalNotes());
        response.setStopFlag(savedRenewal.isStopFlag());

        return response;
    }

    @Override
    public RenewalResponse getRenewalByComplianceId(Long complianceId) {
        Renewal renewal = renewalRepository.findByComplianceId(complianceId);
        if (renewal == null) {
            throw new NotFoundException("Renewal data not found for Compliance ID: " + complianceId);
        }
        return mapToRenewalResponse(renewal);
    }

    @Override
    public void deleteRenewal(Long complianceId) {
        Renewal renewal = renewalRepository.findByComplianceId(complianceId);
        if (renewal == null) {
            throw new NotFoundException("Renewal data not found for Compliance ID: " + complianceId);
        }
        renewalRepository.delete(renewal);
    }

    private RenewalResponse mapToRenewalResponse(Renewal renewal) {
        RenewalResponse response = new RenewalResponse();
        response.setId(renewal.getId());
        response.setComplianceId(renewal.getCompliance().getId());
        response.setNextRenewalDate(renewal.getNextRenewalDate());
        response.setRenewalFrequency(renewal.getRenewalFrequency());
        response.setRenewalType(renewal.getRenewalType());
        response.setRenewalNotes(renewal.getRenewalNotes());
        return response;
    }

    @Override
    public RenewalResponse createRenewalForCompliance(Long complianceId, RenewalRequest renewalRequest) {
        // Step 1: Validate Compliance
        Compliance compliance = complianceRepo.findById(complianceId)
                .orElseThrow(() -> new NotFoundException("Compliance not found with ID: " + complianceId));

        // Step 2: Create a new Renewal
        Renewal renewal = new Renewal();
        renewal.setCompliance(compliance);
        renewal.setNextRenewalDate(renewalRequest.getNextRenewalDate());
        renewal.setRenewalFrequency(renewalRequest.getRenewalFrequency());
        renewal.setRenewalType(renewalRequest.getRenewalType());
        renewal.setRenewalNotes(renewalRequest.getRenewalNotes());
        renewal.setCreatedAt(LocalDate.now());
        renewal.setUpdatedAt(LocalDate.now());

        // Step 3: Save the Renewal
        Renewal savedRenewal = renewalRepository.save(renewal);

        // Step 4: Map to RenewalResponse
        return mapToRenewalResponse(savedRenewal);
    }


    @Override
    public MilestoneRenewalResponse createMilestoneRenewal(Long milestoneId, RenewalRequest request) {
        // Validate Milestone
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + milestoneId));

        // Fetch existing Renewal or create a new one
        Renewal renewal = renewalRepository.findByMilestoneId(milestoneId);
        if (renewal == null) {
            renewal = new Renewal();
            renewal.setMilestone(milestone);
            renewal.setCreatedAt(LocalDate.now());
        }

        // Validate Request Data
        if (request.getNextRenewalDate() == null) {
            throw new IllegalArgumentException("Next renewal date cannot be null.");
        }
        if (request.getRenewalFrequency() <= 0) {
            throw new IllegalArgumentException("Renewal frequency must be greater than 0.");
        }
        if (request.getRenewalType() == null || request.getRenewalType().isEmpty()) {
            throw new IllegalArgumentException("Renewal type is required.");
        }

        // Update Renewal Details
        renewal.setNextRenewalDate(request.getNextRenewalDate());
        renewal.setRenewalFrequency(request.getRenewalFrequency());
        renewal.setRenewalType(request.getRenewalType());
        renewal.setRenewalNotes(request.getRenewalNotes());
        renewal.setStopFlag(request.isStopFlag());
        renewal.setUpdatedAt(LocalDate.now());

        // Save Renewal
        Renewal savedRenewal = renewalRepository.save(renewal);

        // Manually Set Response
        MilestoneRenewalResponse response = new MilestoneRenewalResponse();
        response.setId(savedRenewal.getId());
        response.setMilestoneId(milestone.getId());
        response.setNextRenewalDate(savedRenewal.getNextRenewalDate());
        response.setRenewalFrequency(savedRenewal.getRenewalFrequency());
        response.setRenewalType(savedRenewal.getRenewalType());
        response.setRenewalNotes(savedRenewal.getRenewalNotes());
        response.setStopFlag(savedRenewal.isStopFlag());

        return response;
    }

}
