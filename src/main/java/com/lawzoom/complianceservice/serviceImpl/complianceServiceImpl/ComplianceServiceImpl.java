package com.lawzoom.complianceservice.serviceImpl.complianceServiceImpl;





import com.lawzoom.complianceservice.dto.commonResponse.ReminderRequest;
import com.lawzoom.complianceservice.dto.commonResponse.RenewalRequest;
import com.lawzoom.complianceservice.dto.complianceDto.CompanyComplianceDTO;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.dto.complianceTaskDto.ComplianceMilestoneResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.businessActivityModel.BusinessActivity;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.companyModel.Company;
import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceModel.ComplianceHistory;
import com.lawzoom.complianceservice.model.gstdetails.GstDetails;
import com.lawzoom.complianceservice.model.region.City;
import com.lawzoom.complianceservice.model.region.States;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.*;
import com.lawzoom.complianceservice.repository.RenewalRepository.RenewalRepository;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessUnitRepository;
import com.lawzoom.complianceservice.repository.companyRepo.CompanyRepository;
import com.lawzoom.complianceservice.repository.complianceRepo.ComplianceHistoryRepository;
import com.lawzoom.complianceservice.repository.complianceRepo.ComplianceRepo;
import com.lawzoom.complianceservice.service.complianceService.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class ComplianceServiceImpl implements ComplianceService {


    @Autowired
    private ComplianceRepo complianceRepository;

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GstDetailsRepository gstDetailsRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private RenewalRepository renewalRepository;

    @Autowired
    private ComplianceHistoryRepository complianceHistoryRepository;

    @Override
    public ComplianceResponse saveCompliance(ComplianceRequest complianceRequest, Long businessUnitId, Long userId) {
        // Step 1: Validate User
        User user = userRepository.findActiveUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }

        // Step 2: Validate BusinessUnit
        BusinessUnit businessUnit = businessUnitRepository.findById(businessUnitId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid businessUnitId: " + businessUnitId));

        // Step 3: Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(complianceRequest.getSubscriberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid subscriberId: " + complianceRequest.getSubscriberId()));

        // Step 4: Create and Save Compliance Entity
        Compliance compliance = new Compliance();
        compliance.setComplianceName(complianceRequest.getName());
        compliance.setApprovalState(complianceRequest.getApprovalState());
        compliance.setApplicableZone(complianceRequest.getApplicableZone());
        compliance.setPriority(complianceRequest.getPriority());
        compliance.setCertificateType(complianceRequest.getCertificateType());
        compliance.setIssueAuthority(complianceRequest.getIssueAuthority());
        compliance.setBusinessUnit(businessUnit);
        compliance.setSubscriber(subscriber);
        compliance.setEnable(true);
        compliance.setDeleted(false);
        compliance.setCreatedAt(new Date());
        compliance.setUpdatedAt(new Date());
        Compliance savedCompliance = complianceRepository.save(compliance);

        // Step 5: Log Compliance History
        logComplianceHistory(savedCompliance, user, "CREATE", "Compliance created successfully.");

        // Step 6: Create and Manually Set Response
        ComplianceResponse response = new ComplianceResponse();
        response.setId(savedCompliance.getId());
        response.setName(savedCompliance.getComplianceName());
        response.setIssueAuthority(savedCompliance.getIssueAuthority());
        response.setCertificateType(savedCompliance.getCertificateType());
        response.setApprovalState(savedCompliance.getApprovalState());
        response.setApplicableZone(savedCompliance.getApplicableZone());
        response.setCreatedAt(savedCompliance.getCreatedAt());
        response.setUpdatedAt(savedCompliance.getUpdatedAt());
        response.setEnable(savedCompliance.isEnable());
        response.setPriority(savedCompliance.getPriority());
        response.setBusinessUnitId(savedCompliance.getBusinessUnit().getId());
        response.setSubscriberId(savedCompliance.getSubscriber().getId());
        response.setCompanyId(savedCompliance.getBusinessUnit().getGstDetails().getCompany().getId());

        response.setProgressPercentage(0.0);
        response.setMilestoneCount(0);
        response.setMilestones(Collections.emptyList());

        return response;
    }

    private void logComplianceHistory(Compliance compliance, User user, String action, String details) {
        ComplianceHistory history = new ComplianceHistory();
        history.setCompliance(compliance);
        history.setUser(user);
        history.setAction(action);
        history.setActionDetails(details);
        history.setTimestamp(new Date());
        complianceHistoryRepository.save(history);
    }


    @Override
    public Map<String, Object> updateCompliance(
            ComplianceRequest complianceRequest,
            Long businessUnitId,
            Long complianceId,
            Long userId) {

        // ✅ Step 1: Validate User
        User user = userRepository.findActiveUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found with ID: " + userId);
        }

        // ✅ Step 2: Validate Compliance Entity
        Compliance compliance = complianceRepository.findById(complianceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Compliance not found with ID: " + complianceId));

        // ✅ Step 3: Validate Business Unit
        BusinessUnit businessUnit = businessUnitRepository.findById(businessUnitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid businessUnitId: " + businessUnitId));

        // ✅ Step 4: Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(complianceRequest.getSubscriberId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid subscriberId: " + complianceRequest.getSubscriberId()));

        // ✅ Step 5: Update Compliance Fields (Only Non-Null Values)
        if (complianceRequest.getName() != null) compliance.setComplianceName(complianceRequest.getName());
        if (complianceRequest.getIssueAuthority() != null) compliance.setIssueAuthority(complianceRequest.getIssueAuthority());
        if (complianceRequest.getApprovalState() != null) compliance.setApprovalState(complianceRequest.getApprovalState());
        if (complianceRequest.getApplicableZone() != null) compliance.setApplicableZone(complianceRequest.getApplicableZone());
        if (complianceRequest.getCertificateType() != null) compliance.setCertificateType(complianceRequest.getCertificateType());

        compliance.setPriority(complianceRequest.getPriority()); // Default 0 if null
        compliance.setBusinessUnit(businessUnit);
        compliance.setSubscriber(subscriber);
        compliance.setUpdatedAt(new Date());

        // ✅ Step 6: Save Updated Compliance
        Compliance updatedCompliance = complianceRepository.save(compliance);

        // ✅ Step 7: Log Compliance History
        logComplianceHistory(updatedCompliance, user, "UPDATE", "Compliance updated successfully.");

        // ✅ Step 8: Construct Response Manually
        Map<String, Object> response = new HashMap<>();
        response.put("id", updatedCompliance.getId());
        response.put("name", updatedCompliance.getComplianceName());
        response.put("issueAuthority", updatedCompliance.getIssueAuthority());
        response.put("certificateType", updatedCompliance.getCertificateType());
        response.put("approvalState", updatedCompliance.getApprovalState());
        response.put("applicableZone", updatedCompliance.getApplicableZone());
        response.put("priority", updatedCompliance.getPriority());
        response.put("businessUnitId", updatedCompliance.getBusinessUnit().getId());
        response.put("subscriberId", updatedCompliance.getSubscriber().getId());
        response.put("createdAt", updatedCompliance.getCreatedAt());
        response.put("updatedAt", updatedCompliance.getUpdatedAt());
        response.put("isEnable", updatedCompliance.isEnable());
        response.put("isDeleted", updatedCompliance.isDeleted());

        return response;
    }



    @Override
    public List<ComplianceResponse> fetchComplianceByBusinessUnit(Long businessUnitId, Long userId, Long subscriberId) {
        // Step 1: Validate User
        User user = userRepository.findActiveUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }

        // Step 2: Validate Business Unit
        BusinessUnit businessUnit = businessUnitRepository.findById(businessUnitId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid businessUnitId: " + businessUnitId));

        // Step 3: Fetch Compliances
        List<Compliance> compliances = complianceRepository.findByBusinessUnitId(businessUnitId);

        if (compliances.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No compliance found for the given Business Unit ID");
        }

        // Step 4: Map Compliances to Responses
        List<ComplianceResponse> responses = new ArrayList<>();
        for (Compliance compliance : compliances) {
            ComplianceResponse response = new ComplianceResponse();
            response.setId(compliance.getId());
            response.setName(compliance.getComplianceName());
            response.setIssueAuthority(compliance.getIssueAuthority());
            response.setCertificateType(compliance.getCertificateType());
            response.setApprovalState(compliance.getApprovalState());
            response.setApplicableZone(compliance.getApplicableZone());
            response.setCreatedAt(compliance.getCreatedAt());
            response.setUpdatedAt(compliance.getUpdatedAt());
            response.setEnable(compliance.isEnable());
            response.setPriority(compliance.getPriority());
            response.setBusinessUnitId(compliance.getBusinessUnit().getId());
            response.setSubscriberId(compliance.getSubscriber().getId());
            response.setCompanyId(compliance.getBusinessUnit().getGstDetails().getCompany().getId());
            response.setCompanyName(compliance.getBusinessUnit().getGstDetails().getCompany().getCompanyName());

            // City and State
            response.setCityId(compliance.getBusinessUnit().getCity() != null ? compliance.getBusinessUnit().getCity().getId() : null);
            response.setCity(compliance.getBusinessUnit().getCity() != null ? compliance.getBusinessUnit().getCity().getCityName() : null);
            response.setStateId(compliance.getBusinessUnit().getState() != null ? compliance.getBusinessUnit().getState().getId() : null);
            response.setState(compliance.getBusinessUnit().getState() != null ? compliance.getBusinessUnit().getState().getStateName() : null);

            response.setBusinessActivityId(compliance.getBusinessUnit().getBusinessActivity() != null
                    ? compliance.getBusinessUnit().getBusinessActivity().getId()
                    : null);
            response.setBusinessActivityName(compliance.getBusinessUnit().getBusinessActivity() != null
                    ? compliance.getBusinessUnit().getBusinessActivity().getBusinessActivityName()
                    : null);

            // Fetch milestones
            List<MileStone> milestones = compliance.getMilestones();
            int totalMilestones = milestones.size();

            // Set milestones and progress details
            response.setMilestoneCount(totalMilestones);

            // Map milestones to ComplianceMilestoneResponse
            List<ComplianceMilestoneResponse> milestoneResponses = new ArrayList<>();
            for (MileStone milestone : milestones) {
                ComplianceMilestoneResponse milestoneResponse = new ComplianceMilestoneResponse();
                milestoneResponse.setId(milestone.getId());
                milestoneResponse.setMileStoneName(milestone.getMileStoneName());
                milestoneResponse.setDescription(milestone.getDescription());
                milestoneResponse.setStatusId(milestone.getStatus() != null ? milestone.getStatus().getId() : null);
                milestoneResponse.setStatus(milestone.getStatus() != null ? milestone.getStatus().getName() : "Not Started");
                milestoneResponse.setCreatedAt(milestone.getCreatedAt());
                milestoneResponse.setUpdatedAt(milestone.getUpdatedAt());
                milestoneResponse.setEnable(milestone.isEnable());
                milestoneResponse.setComplianceId(compliance.getId());
                milestoneResponse.setManagerId(milestone.getManager() != null ? milestone.getManager().getId() : null);
                milestoneResponse.setManagerName(milestone.getManager() != null ? milestone.getManager().getUserName() : null);
                milestoneResponse.setAssigneeId(milestone.getAssigned() != null ? milestone.getAssigned().getId() : null);
                milestoneResponse.setAssigneeName(milestone.getAssigned() != null ? milestone.getAssigned().getUserName() : null);
                milestoneResponse.setAssignedBy(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getId() : null);
                milestoneResponse.setAssignedByName(milestone.getAssignedBy() != null ? milestone.getAssignedBy().getUserName() : null);
                milestoneResponse.setIssuedDate(milestone.getIssuedDate());
                milestoneResponse.setStartedDate(milestone.getStartedDate());
                milestoneResponse.setDueDate(milestone.getDueDate());
                milestoneResponse.setCompletedDate(milestone.getCompletedDate());
                milestoneResponse.setCriticality(milestone.getCriticality());
                milestoneResponse.setRemark(milestone.getRemark());
                milestoneResponse.setBusinessUnitId(milestone.getBusinessUnit() != null ? milestone.getBusinessUnit().getId() : null);
                milestoneResponse.setSubscriberId(milestone.getSubscriber() != null ? milestone.getSubscriber().getId() : null);
                milestoneResponse.setExpiryDate(milestone.getExpiryDate());

                // Map Renewal Details
                RenewalRequest renewalRequest = new RenewalRequest();
                if (!milestone.getRenewals().isEmpty()) {
                    Renewal renewal = milestone.getRenewals().get(0); // Assuming one renewal per milestone
                    renewalRequest.setId(renewal.getId());
                    renewalRequest.setIssuedDate(renewal.getIssuedDate());
                    renewalRequest.setExpiryDate(renewal.getExpiryDate());
                    renewalRequest.setRenewalDate(renewal.getRenewalDate());
                    renewalRequest.setReminderDurationType(renewal.getReminderDurationType().name());
                    renewalRequest.setReminderDurationValue(renewal.getReminderDurationValue());
                    renewalRequest.setRenewalNotes(renewal.getRenewalNotes());
                    renewalRequest.setNotificationsEnabled(renewal.isNotificationsEnabled());
                    renewalRequest.setCertificateTypeDuration(renewal.getCertificateTypeDuration().name());
                    renewalRequest.setCertificateDurationValue(renewal.getCertificateDurationValue());
                    renewalRequest.setUserId(milestone.getManager() != null ? milestone.getManager().getId() : null);
                }
                milestoneResponse.setRenewalRequest(renewalRequest);

                // Map Reminder Details
                ReminderRequest reminderRequest = new ReminderRequest();
                if (!milestone.getReminders().isEmpty()) {
                    Reminder reminder = milestone.getReminders().get(0); // Assuming one reminder per milestone
                    reminderRequest.setUserid(reminder.getCreatedBy().getId());
                    reminderRequest.setReminderDate(reminder.getReminderDate());
                    reminderRequest.setReminderEndDate(reminder.getReminderEndDate());
                    reminderRequest.setNotificationTimelineValue(reminder.getNotificationTimelineValue());
                    reminderRequest.setRepeatTimelineValue(reminder.getRepeatTimelineValue());
                    reminderRequest.setRepeatTimelineType(reminder.getRepeatTimelineType());
                    reminderRequest.setStopFlag(reminder.getStopFlag());
                    reminderRequest.setCreatedBy(reminder.getCreatedBy().getId());
                }
                milestoneResponse.setReminderRequest(reminderRequest);

                milestoneResponses.add(milestoneResponse);
            }

            // Add milestone responses to the compliance response
            response.setMilestones(milestoneResponses);

            // Add compliance response to the final list
            responses.add(response);
        }

        return responses;
    }


    @Override
    public List<CompanyComplianceDTO> getCompanyComplianceDetails(Long userId, Long subscriberId) {

        // Step 1: Validate User
        User userData = userRepository.findActiveUserById(userId);

        if (userData == null) {
            throw new NotFoundException("User not found with userId: " + userId);
        }

        // Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + subscriberId));

        if (!userData.getSubscriber().equals(subscriber)) {
            throw new IllegalArgumentException("User does not belong to the provided Subscriber.");
        }

        // Fetch all companies
        List<Company> companies = companyRepository.findCompaniesBySuperAdminAndSubscriber(userData.getId(), subscriberId);

        List<CompanyComplianceDTO> companyComplianceDTOs = new ArrayList<>();
        for (Company company : companies) {
            List<GstDetails> gstDetailsList = gstDetailsRepository.findAllByCompanyAndIsEnableAndIsDeletedFalse(company.getId(), true);
            for (GstDetails gstDetails : gstDetailsList) {
                List<BusinessUnit> businessUnits = businessUnitRepository.findBusinessUnitsByGstDetails(gstDetails.getId(), true);
                for (BusinessUnit businessUnit : businessUnits) {
                    List<Compliance> compliance = complianceRepository.findCompliancesByBusinessUnitAndStatus(businessUnit.getId(), true);

                    long complianceCount = compliance.size();
                    // Add data even if complianceCount is 0
                    CompanyComplianceDTO complianceDTO = new CompanyComplianceDTO();
                    complianceDTO.setCompanyId(company.getId());
                    complianceDTO.setCompanyName(company.getCompanyName());
                    complianceDTO.setBusinessActivityId(company.getBusinessActivity().getId());
                    complianceDTO.setBusinessActivity(company.getBusinessActivity().getBusinessActivityName());
                    complianceDTO.setDate(company.getDate());
                    complianceDTO.setGstDetailsId(gstDetails.getId());
                    complianceDTO.setGstNumber(gstDetails.getGstNumber());
                    complianceDTO.setBusinessUnitId(businessUnit.getId());
                    complianceDTO.setBusinessAddress(businessUnit.getAddress());
                    complianceDTO.setComplianceCount(complianceCount);
                    companyComplianceDTOs.add(complianceDTO);
                }
            }
        }
        return companyComplianceDTOs;
    }

    @Override
    public Map<String, Object> fetchComplianceById(Long complianceId) {
        // Step 1: Fetch Compliance
        Compliance compliance = complianceRepository.findById(complianceId)
                .orElseThrow(() -> new NotFoundException("Compliance not found with ID: " + complianceId));

        // Step 2: Fetch Milestones
        List<MileStone> milestones = compliance.getMilestones();
        long totalMilestones = milestones.size();


        // Step 3: Construct Response Map
        Map<String, Object> response = new HashMap<>();
        response.put("id", compliance.getId());
        response.put("name", compliance.getComplianceName());
        response.put("issueAuthority", compliance.getIssueAuthority());
        response.put("certificateType", compliance.getCertificateType());
        response.put("approvalState", compliance.getApprovalState());
        response.put("applicableZone", compliance.getApplicableZone());
        response.put("createdAt", compliance.getCreatedAt());
        response.put("updatedAt", compliance.getUpdatedAt());
        response.put("isEnable", compliance.isEnable());
        response.put("priority", compliance.getPriority());
        response.put("businessUnitId", compliance.getBusinessUnit().getId());
        response.put("subscriberId", compliance.getSubscriber().getId());

        // Add Company and Business Details
        BusinessUnit businessUnit = compliance.getBusinessUnit();
        GstDetails gstDetails = businessUnit.getGstDetails();
        Company company = gstDetails.getCompany();
        BusinessActivity businessActivity = businessUnit.getBusinessActivity();
        States state = gstDetails.getState();
        City city = company.getCity();
        response.put("companyId", company.getId());
        response.put("companyName", company.getCompanyName());
        response.put("businessActivityId", businessActivity.getId());
        response.put("businessActivityName", businessActivity.getBusinessActivityName());
        response.put("state", state != null ? state.getStateName() : null);
        response.put("city", city != null ? city.getCityName() : null);

        Map<String, Object> milestoneStats = new HashMap<>();

        response.put("milestoneStatistics", milestoneStats);


        return response;
    }




}