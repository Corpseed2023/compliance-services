package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.renewalDto.MilestoneRenewalResponse;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalRequest;
import com.lawzoom.complianceservice.dto.renewalDto.RenewalResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.repository.complianceRepo.ComplianceRepo;
import com.lawzoom.complianceservice.repository.MileStoneRepository.MilestoneRepository;
import com.lawzoom.complianceservice.repository.RenewalRepository.RenewalRepository;
import com.lawzoom.complianceservice.repository.taskRepo.TaskRepository;
import com.lawzoom.complianceservice.service.RenewalService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public MilestoneRenewalResponse createOrUpdateMilestoneRenewal(Long milestoneId, RenewalRequest request, Long renewalId) {
        // Step 1: Validate Milestone
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + milestoneId));

        // Step 2: Validate User
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + request.getUserId()));

        Renewal renewal;

        if (renewalId != null) {
            // Updating an existing renewal
            renewal = renewalRepository.findById(renewalId)
                    .orElseThrow(() -> new NotFoundException("Renewal not found with ID: " + renewalId));
        } else {
            // Creating a new renewal
            renewal = new Renewal();
            renewal.setMilestone(milestone);
            renewal.setSubscriber(milestone.getSubscriber());
            renewal.setUser(user);
            renewal.setCreatedAt(new Date());
        }

        // Step 3: Validate Required Fields
        if (request.getIssuedDate() == null || request.getExpiryDate() == null) {
            throw new IllegalArgumentException("Issued date and expiry date cannot be null.");
        }
        if (request.getCertificateTypeDuration() == null || request.getCertificateDurationValue() == null) {
            throw new IllegalArgumentException("Certificate type duration and certificate duration value cannot be null.");
        }

        // Step 4: Normalize Duration Type (Handle Different Capitalizations)
        String reminderDurationType = request.getReminderDurationType().trim().toUpperCase();
        String certificateTypeDuration = request.getCertificateTypeDuration().trim().toUpperCase();

        try {
            renewal.setReminderDurationType(Renewal.ReminderDurationType.valueOf(reminderDurationType));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid reminder duration type provided. Allowed values: DAYS, WEEKS, MONTHS, YEARS.");
        }

        try {
            renewal.setCertificateTypeDuration(Renewal.ReminderDurationType.valueOf(certificateTypeDuration));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid certificate type duration provided. Allowed values: DAYS, WEEKS, MONTHS, YEARS.");
        }

        // Step 5: Update Renewal details
        renewal.setIssuedDate(request.getIssuedDate());
        renewal.setExpiryDate(request.getExpiryDate());
        renewal.setReminderDurationValue(request.getReminderDurationValue());
        renewal.setRenewalNotes(request.getRenewalNotes());
        renewal.setNotificationsEnabled(request.isNotificationsEnabled());
        renewal.setCertificateDurationValue(request.getCertificateDurationValue());

        // Step 6: Calculate reminder start date
        renewal.calculateNextReminderDate();
        renewal.setUpdatedAt(new Date());

        // Step 7: Save renewal
        Renewal savedRenewal = renewalRepository.save(renewal);

        // Step 8: Map to MilestoneRenewalResponse
        MilestoneRenewalResponse response = new MilestoneRenewalResponse();
        response.setId(savedRenewal.getId());
        response.setMilestoneId(milestone.getId());
        response.setIssuedDate(savedRenewal.getIssuedDate());
        response.setExpiryDate(savedRenewal.getExpiryDate());
        response.setReminderDurationType(savedRenewal.getReminderDurationType().toString());
        response.setReminderDurationValue(savedRenewal.getReminderDurationValue());
        response.setRenewalNotes(savedRenewal.getRenewalNotes());
        response.setNotificationsEnabled(savedRenewal.isNotificationsEnabled());
        response.setReminderFrequency(savedRenewal.getReminderFrequency());
        response.setCertificateTypeDuration(savedRenewal.getCertificateTypeDuration().toString());
        response.setCertificateDurationValue(savedRenewal.getCertificateDurationValue());

        return response;
    }

    private RenewalResponse mapToRenewalResponse(Renewal renewal) {
        RenewalResponse response = new RenewalResponse();
        response.setId(renewal.getId());
        response.setMilestoneId(renewal.getMilestone() != null ? renewal.getMilestone().getId() : null);
        response.setIssuedDate(renewal.getIssuedDate());
        response.setExpiryDate(renewal.getExpiryDate());
        response.setReminderDurationType(renewal.getReminderDurationType().toString());
        response.setReminderDurationValue(renewal.getReminderDurationValue());
        response.setRenewalNotes(renewal.getRenewalNotes());
        response.setStopFlag(renewal.isNotificationsEnabled());
        response.setReminderFrequency(renewal.getReminderFrequency());
        return response;
    }

    @Override
    public List<RenewalResponse> getRenewalsByMilestoneId(Long userId, Long milestoneId) {
        // Fetch renewals for the given milestone ID and user
        List<Renewal> renewals = renewalRepository.findByMilestoneIdAndUserId(milestoneId, userId);

        if (renewals.isEmpty()) {
            throw new NotFoundException("No renewals found for milestone ID: " + milestoneId + " and user ID: " + userId);
        }

        // Map renewals to responses
        return renewals.stream()
                .map(this::mapToRenewalResponse)
                .toList();
    }
    @Override
    public RenewalResponse getRenewalById(Long renewalId) {
        return null;
    }


    @Override
    public List<RenewalResponse> fetchAllRenewals(Long userId) {
        // ✅ Fetch the user details
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        List<Renewal> renewals;

        // ✅ Check if the user is SUPER ADMIN or ADMIN
        boolean isSuperAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("SUPER_ADMIN"));
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));

        if (isSuperAdmin || isAdmin) {
            // ✅ Fetch ALL renewals
            renewals = renewalRepository.findAll();
        } else {
            // ✅ Fetch only renewals where the user is assigned
            renewals = renewalRepository.findByUserId(userId);
        }

        // ✅ Convert renewals to response DTOs
        return renewals.stream()
                .map(this::mapToRenewalResponse)
                .collect(Collectors.toList());
    }



}
