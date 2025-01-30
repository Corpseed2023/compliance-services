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
    public MilestoneRenewalResponse  createMilestoneRenewal(Long milestoneId, RenewalRequest request) {
        // Validate Milestone
        MileStone milestone = milestoneRepository.findById(milestoneId)
                .orElseThrow(() -> new NotFoundException("Milestone not found with ID: " + milestoneId));

        // Validate User
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + request.getUserId()));

        // Check if a renewal already exists for the milestone
        Renewal renewal = renewalRepository.findByMilestoneId(milestoneId);
        if (renewal == null) {
            renewal = new Renewal();
            renewal.setMilestone(milestone);
            renewal.setSubscriber(milestone.getSubscriber());
            renewal.setUser(user); // Set the creator
            renewal.setCreatedAt(new Date());
        }

        if (request.getIssuedDate() == null || request.getExpiryDate() == null) {
            throw new IllegalArgumentException("Issued date and expiry date cannot be null.");
        }

        // Update Renewal details
        renewal.setUser(user); // Associate user
        renewal.setIssuedDate(request.getIssuedDate());
        renewal.setExpiryDate(request.getExpiryDate());
        renewal.setReminderDurationType(Renewal.ReminderDurationType.valueOf(request.getReminderDurationType().toUpperCase()));
        renewal.setReminderDurationValue(request.getReminderDurationValue());
        renewal.setRenewalNotes(request.getRenewalNotes());
        renewal.setNotificationsEnabled(request.isNotificationsEnabled());

        // Calculate reminder start date
        renewal.calculateNextReminderDate();

        renewal.setUpdatedAt(new Date());

        Renewal savedRenewal = renewalRepository.save(renewal);

        // Map to MilestoneRenewalResponse
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
    public MilestoneRenewalResponse updateMilestoneRenewal(Long renewalId, RenewalRequest renewalRequest, Long mileStoneId) {


        MileStone mileStone = milestoneRepository.findById(mileStoneId).orElseThrow(() ->
                new NotFoundException("Mile Stone not found with ID: " + mileStoneId));

        mileStone.setExpiryDate(renewalRequest.getExpiryDate());
        mileStone.setIssuedDate(renewalRequest.getIssuedDate());

        milestoneRepository.save(mileStone);


        Renewal renewal = renewalRepository.findById(renewalId)
                .orElseThrow(() -> new NotFoundException("Renewal not found with ID: " + renewalId));

        if (renewalRequest.getIssuedDate() == null || renewalRequest.getExpiryDate() == null) {
            throw new IllegalArgumentException("Issued date and expiry date cannot be null.");
        }

        renewal.setIssuedDate(renewalRequest.getIssuedDate());
        renewal.setExpiryDate(renewalRequest.getExpiryDate());
        renewal.setReminderDurationValue(renewalRequest.getReminderDurationValue());
        renewal.setRenewalNotes(renewalRequest.getRenewalNotes());
        renewal.setNotificationsEnabled(renewalRequest.isNotificationsEnabled());
        renewal.calculateNextReminderDate();
        renewal.setUpdatedAt(new Date());

        Renewal updatedRenewal = renewalRepository.save(renewal);

        MilestoneRenewalResponse response = new MilestoneRenewalResponse();
        response.setId(updatedRenewal.getId());
        response.setMilestoneId(updatedRenewal.getMilestone() != null ? updatedRenewal.getMilestone().getId() : null);
        response.setIssuedDate(updatedRenewal.getIssuedDate());
        response.setExpiryDate(updatedRenewal.getExpiryDate());
        response.setReminderDurationType(updatedRenewal.getReminderDurationType().toString());
        response.setReminderDurationValue(updatedRenewal.getReminderDurationValue());
        response.setRenewalNotes(updatedRenewal.getRenewalNotes());
        response.setNotificationsEnabled(updatedRenewal.isNotificationsEnabled());
        response.setReminderFrequency(updatedRenewal.getReminderFrequency());

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
