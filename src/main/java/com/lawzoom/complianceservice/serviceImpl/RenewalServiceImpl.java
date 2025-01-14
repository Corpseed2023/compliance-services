package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.dto.RenewalRequest;
import com.lawzoom.complianceservice.dto.RenewalResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.repository.RenewalRepository;
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

    @Override
    public RenewalResponse createOrUpdateRenewal(Long complianceId, RenewalRequest request) {
        // Validate Compliance
        Compliance compliance = complianceRepo.findById(complianceId)
                .orElseThrow(() -> new NotFoundException("Compliance not found with ID: " + complianceId));

        Renewal renewal = renewalRepository.findByComplianceId(complianceId);
        if (renewal == null) {
            renewal = new Renewal();
            renewal.setCompliance(compliance);
        }

        renewal.setNextRenewalDate(request.getNextRenewalDate());
        renewal.setRenewalFrequency(request.getRenewalFrequency());
        renewal.setRenewalType(request.getRenewalType());
        renewal.setRenewalNotes(request.getRenewalNotes());
        renewal.setUpdatedAt(LocalDate.now());

        Renewal savedRenewal = renewalRepository.save(renewal);
        return mapToRenewalResponse(savedRenewal);
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

}
