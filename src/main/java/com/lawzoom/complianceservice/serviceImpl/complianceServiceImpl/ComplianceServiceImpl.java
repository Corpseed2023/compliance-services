package com.lawzoom.complianceservice.serviceImpl.complianceServiceImpl;

import com.authentication.dto.complianceDto.CompanyComplianceDTO;
import com.authentication.dto.complianceDto.ComplianceRequest;
import com.authentication.dto.complianceDto.ComplianceResponse;
import com.authentication.exception.NotFoundException;
import com.authentication.model.businessUnitModel.BusinessUnit;
import com.authentication.model.companyModel.Company;
import com.authentication.model.complianceModel.Compliance;
import com.authentication.model.gstdetails.GstDetails;
import com.authentication.model.user.User;
import com.authentication.repository.ComplianceRepo;
import com.authentication.repository.GstDetailsRepository;
import com.authentication.repository.UserRepository;
import com.authentication.repository.businessRepo.BusinessUnitRepository;
import com.authentication.repository.companyRepo.CompanyRepository;
import com.authentication.service.complianceService.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @Override
    public ResponseEntity<String> deleteBusinessCompliance(Long complianceId, Long companyId) {
        // Check if the compliance exists
        if (!complianceRepository.existsById(complianceId)) {
            // Return a response indicating that the compliance doesn't exist
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Compliance with ID " + complianceId + " not found.");
        }

        // Fetch the compliance object
        Compliance compliance = complianceRepository.findById(complianceId).orElse(null);

        if (compliance != null) {
            // Disable the compliance
            compliance.setEnable(false);
            // Save the updated compliance back to the repository
            complianceRepository.save(compliance);

            // Return a successful response
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Compliance with ID " + complianceId + " has been successfully deleted.");
        } else {
            // Return a response indicating that the compliance was not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Compliance with ID " + complianceId + " not found.");
        }
    }


    @Override
    public void saveAllCompliances(List<Compliance> complianceList) {

    }


    @Override
    public ComplianceResponse fetchCompliance(Long complianceId, Long companyId) {
        return null;
    }

    @Override
    public ComplianceResponse saveCompliance(ComplianceRequest complianceRequest, Long businessUnitId, Long userId) {
        // Fetch BusinessUnit by ID
        BusinessUnit businessUnit = businessUnitRepository.findById(businessUnitId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid businessUnitId: " + businessUnitId));

        // Map ComplianceRequest to Compliance entity
        Compliance compliance = new Compliance();
        compliance.setComplianceName(complianceRequest.getName());
        compliance.setApprovalState(complianceRequest.getApprovalState());
        compliance.setApplicableZone(complianceRequest.getApplicableZone());
        compliance.setCreatedAt(new Date());
        compliance.setUpdatedAt(new Date());
        compliance.setEnable(complianceRequest.isEnable());
        compliance.setStartDate(complianceRequest.getStartDate());
        compliance.setDueDate(complianceRequest.getDueDate());
        compliance.setCompletedDate(complianceRequest.getCompletedDate());
        compliance.setWorkStatus(complianceRequest.getWorkStatus());
        compliance.setPriority(complianceRequest.getPriority());
        compliance.setCertificateType(complianceRequest.getCertificateType());
        compliance.setBusinessUnit(businessUnit);
        compliance.setIssueAuthority(complianceRequest.getIssueAuthority());

        // Save Compliance
        Compliance savedCompliance = complianceRepository.save(compliance);

        // Map saved Compliance entity to ComplianceResponse
        return createComplianceResponse(savedCompliance, userId);
    }

    private ComplianceResponse createComplianceResponse(Compliance compliance, Long userId) {
        ComplianceResponse response = new ComplianceResponse();
        response.setId(compliance.getId());
        response.setName(compliance.getComplianceName());
        response.setApprovalState(compliance.getApprovalState());
        response.setApplicableZone(compliance.getApplicableZone());
        response.setCreatedAt(compliance.getCreatedAt());
        response.setUpdatedAt(compliance.getUpdatedAt());
        response.setEnable(compliance.isEnable());
        response.setStartDate(compliance.getStartDate());
        response.setDueDate(compliance.getDueDate());
        response.setCompletedDate(compliance.getCompletedDate());
        response.setWorkStatus(compliance.getWorkStatus());
        response.setPriority(compliance.getPriority());
        response.setBusinessUnitId(compliance.getBusinessUnit().getId());
        response.setCreatedBy(userId);
        response.setIssueAuthority(compliance.getIssueAuthority());
        return response;
    }

    @Override
    public ComplianceResponse updateCompliance(ComplianceRequest complianceRequest, Long businessUnitId, Long complianceId) {
        // Retrieve the existing Compliance entity
        Optional<Compliance> optionalCompliance = complianceRepository.findById(complianceId);

        if (optionalCompliance.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No compliance found with the provided ID");
        }

        Compliance compliance = optionalCompliance.get();

        // Validate if the compliance belongs to the provided BusinessUnit
        if (!compliance.getBusinessUnit().getId().equals(businessUnitId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Compliance does not belong to the specified BusinessUnit");
        }

        // Update the Compliance entity with new values from the request
        compliance.setComplianceName(complianceRequest.getName());
        compliance.setApprovalState(complianceRequest.getApprovalState());
        compliance.setApplicableZone(complianceRequest.getApplicableZone());
        compliance.setStartDate(complianceRequest.getStartDate());
        compliance.setDueDate(complianceRequest.getDueDate());
        compliance.setCompletedDate(complianceRequest.getCompletedDate());
        compliance.setWorkStatus(complianceRequest.getWorkStatus());
        compliance.setPriority(complianceRequest.getPriority());
        compliance.setUpdatedAt(new Date()); // Update the updatedAt timestamp

        // Save the updated Compliance entity
        Compliance updatedCompliance = complianceRepository.save(compliance);

        // Create and return the response
        return createComplianceResponse(updatedCompliance);
    }

    private ComplianceResponse createComplianceResponse(Compliance compliance) {
        ComplianceResponse response = new ComplianceResponse();
        response.setId(compliance.getId());
        response.setName(compliance.getComplianceName());
        response.setApprovalState(compliance.getApprovalState());
        response.setApplicableZone(compliance.getApplicableZone());
        response.setCreatedAt(compliance.getCreatedAt());
        response.setUpdatedAt(compliance.getUpdatedAt());
        response.setEnable(compliance.isEnable());
        response.setStartDate(compliance.getStartDate());
        response.setDueDate(compliance.getDueDate());
        response.setCompletedDate(compliance.getCompletedDate());
        response.setWorkStatus(compliance.getWorkStatus());
        response.setPriority(compliance.getPriority());
        response.setBusinessUnitId(compliance.getBusinessUnit().getId());
        return response;
    }


    @Override
    public List<ComplianceResponse> fetchCompliancesByBusinessUnit(Long businessUnitId) {
        // Fetch all compliances for the given businessUnitId
        List<Compliance> compliances = complianceRepository.findByBusinessUnitId(businessUnitId);

        if (compliances.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No compliances found for the given Business Unit ID");
        }

        // Manually create ComplianceResponse objects
        List<ComplianceResponse> responses = new ArrayList<>();
        for (Compliance compliance : compliances) {
            ComplianceResponse response = new ComplianceResponse();
            response.setId(compliance.getId());
            response.setName(compliance.getComplianceName());
            response.setApprovalState(compliance.getApprovalState());
            response.setApplicableZone(compliance.getApplicableZone());
            response.setCreatedAt(compliance.getCreatedAt());
            response.setUpdatedAt(compliance.getUpdatedAt());
            response.setEnable(compliance.isEnable());
            response.setStartDate(compliance.getStartDate());
            response.setDueDate(compliance.getDueDate());
            response.setCompletedDate(compliance.getCompletedDate());
            response.setWorkStatus(compliance.getWorkStatus());
            response.setPriority(compliance.getPriority());
            response.setBusinessUnitId(compliance.getBusinessUnit().getId());
            response.setCertificateType(compliance.getCertificateType());
            response.setIssueAuthority(compliance.getIssueAuthority());
            responses.add(response);
        }

        return responses;
    }

    @Override
    public List<Compliance> getCompliancesByBusinessUnitId(Long id) {

        List<Compliance> complianceList = complianceRepository.findByBusinessUnitId(id);

        return complianceList;


    }

    @Override
    public ResponseEntity fetchAllComplianceByBusinessUnitId(Long businessUnitId) {
        return null;
    }

    @Override
    public ComplianceResponse saveBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId) {
        return null;
    }

    @Override
    public ResponseEntity updateBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId) {
        return null;
    }

    @Override
    public ResponseEntity fetchBusinessCompliance(Long complianceId, Long businessUnitId) {
        return null;
    }

    @Override
    public List<ComplianceResponse> fetchAllCompliances(Long companyId, Long businessUnitId) {
        return null;
    }

    @Override
    public ResponseEntity deleteCompliance(Long complianceId, Long companyId) {
        return null;
    }

    @Override
    public ResponseEntity updateComplianceStatus(Long complianceId, int status) {
        return null;
    }

    @Override
    public ResponseEntity fetchManageCompliancesByUserId(Long userId) {
        return null;
    }

    @Override
    public ComplianceResponse getAllComplianceByCompanyUnitTeam(Long teamId, Long companyId, Long businessUnitId) {
        return null;
    }

    @Override
    public Map<Long, List<ComplianceResponse>> getAllComplianceByCompanyId() {
        return null;
    }

    @Override
    public Map<Long, Integer> getComplianceCount() {
        return null;
    }

    @Override
    public Map<Long, Map<Long, Integer>> getComplianceCountsByCompanyAndBusinessUnit() {
        return null;
    }


    @Override
    public List<CompanyComplianceDTO> getCompanyComplianceDetails(Long userId) {
        // Fetch the user data
        User userData = userRepository.findByIdAndIsEnableAndNotDeleted(userId)
                .orElseThrow(() -> new NotFoundException("User not found or inactive"));

        // Fetch all companies by superAdminId
        List<Company> companies = companyRepository.findAllBySuperAdminIdAndIsDeletedFalse(userData);

        // Create a list to hold compliance DTOs
        List<CompanyComplianceDTO> companyComplianceDTOs = new ArrayList<>();

        // Iterate over each company
        for (Company company : companies) {
            // Fetch GST details for the company
            List<GstDetails> gstDetailsList = gstDetailsRepository.findAllByCompanyAndIsEnableAndIsDeletedFalse(company, true);

            // Iterate over each GST detail
            for (GstDetails gstDetails : gstDetailsList) {
                // Fetch business units under each GST detail
                List<BusinessUnit> businessUnits = businessUnitRepository.findAllByGstDetailsAndIsEnableAndIsDeletedFalse(gstDetails, true);

                // Iterate over each business unit
                for (BusinessUnit businessUnit : businessUnits) {
                    // Fetch compliances for the business unit
                    List<Compliance> compliances = complianceRepository.findAllByBusinessUnit(businessUnit);

                    // Calculate the compliance count
                    long complianceCount = compliances.size(); // Get the count of compliances

                    // If there are compliances, map Compliance entity to DTO
                    if (complianceCount > 0) {
                        CompanyComplianceDTO complianceDTO = new CompanyComplianceDTO();
                        complianceDTO.setCompanyId(company.getId()); // Set Company ID
                        complianceDTO.setCompanyName(company.getCompanyName()); // Set Company Name
                        complianceDTO.setBusinessActivityId(company.getBusinessActivity().getId()); // Set Business Activity ID
                        complianceDTO.setBusinessActivity(company.getBusinessActivity().getBusinessActivityName()); // Set Business Activity Name
                        complianceDTO.setDate(company.getDate()); // Set Date
                        complianceDTO.setGstDetailsId(gstDetails.getId()); // Set GST Details ID
                        complianceDTO.setGstNumber(gstDetails.getGstNumber()); // Set GST Number
                        complianceDTO.setBusinessUnitId(businessUnit.getId()); // Set Business Unit ID
                        complianceDTO.setBusinessAddress(businessUnit.getAddress()); // Set Business Address
                        complianceDTO.setComplianceCount(complianceCount); // Set Compliance Count

                        // Add the DTO to the list
                        companyComplianceDTOs.add(complianceDTO);
                    }
                }
            }
        }

        // Return the list of DTOs
        return companyComplianceDTOs;
    }




}