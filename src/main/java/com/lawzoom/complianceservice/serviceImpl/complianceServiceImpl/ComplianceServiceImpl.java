package com.lawzoom.complianceservice.serviceImpl.complianceServiceImpl;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessUnitRepository;
import com.lawzoom.complianceservice.service.complianceService.ComplianceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComplianceServiceImpl implements ComplianceService {


    @Autowired
    private ComplianceRepo complianceRepository;

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

//    @Autowired
//    private AuthenticationFeignClient authenticationFeignClient;

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
//
//    @Override
//    public ResponseEntity fetchAllComplianceByBusinessUnitId(Long businessUnitId) {
//       List<Compliance> complianceList =this.complianceRepository.findAllByBusinessUnitId(businessUnitId);
//        return null;
//    }

//    public ComplianceResponse saveBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId) {
//        Compliance compliance = new Compliance();
//        compliance.setComplianceName(complianceRequest.getName());
//        compliance.setDescription(complianceRequest.getDescription());
//        compliance.setApprovalState(complianceRequest.getApprovalState());
//        compliance.setApplicableZone(complianceRequest.getApplicableZone());
//        compliance.setStartDate(complianceRequest.getStartDate());
//        compliance.setDueDate(complianceRequest.getDueDate());
//        compliance.setCompletedDate(complianceRequest.getCompletedDate());
//        compliance.setDuration(complianceRequest.getDuration());
//        compliance.setWorkStatus(complianceRequest.getWorkStatus());
//        compliance.setPriority(complianceRequest.getPriority());
////        compliance.setCompanyId(complianceRequest.getCompanyId());
//        compliance.setBusinessUnitId(businessUnitId);
//
//        Compliance savedCompliance = complianceRepository.save(compliance);
//
//        ComplianceResponse complianceResponse = new ComplianceResponse();
//
//        complianceResponse.setName(compliance.getComplianceName());
//        complianceResponse.setDescription(compliance.getDescription());
//        complianceResponse.setApprovalState(compliance.getApprovalState());
//        complianceResponse.setEnable(compliance.isEnable());
//        complianceResponse.setApplicableZone(compliance.getApplicableZone());
//        complianceResponse.setStartDate(compliance.getStartDate());
//        complianceResponse.setPriority(compliance.getPriority());
//
//        return complianceResponse;
//
//
////       ResponseEntity.ok(savedCompliance);
////        return ResponseEntity.creationComplete("Compliance successfully Create", HttpStatus.CREATED);
//
//    }
//    @Override
//    public ResponseEntity<String> updateBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId) {
//        // Fetch the existing compliance object based on the ID from the request
//        Compliance existingCompliance = complianceRepository.findById(complianceRequest.getId())
//                .orElseThrow(() -> new EntityNotFoundException("Compliance not found with ID: " + complianceRequest.getId()));
//
//        // Update the existing compliance object with the new values from the request
//        existingCompliance.setComplianceName(complianceRequest.getName());
//        existingCompliance.setDescription(complianceRequest.getDescription());
//        existingCompliance.setApprovalState(complianceRequest.getApprovalState());
//        existingCompliance.setApplicableZone(complianceRequest.getApplicableZone());
//        existingCompliance.setStartDate(complianceRequest.getStartDate());
//        existingCompliance.setDueDate(complianceRequest.getDueDate());
//        existingCompliance.setCompletedDate(complianceRequest.getCompletedDate());
//        existingCompliance.setDuration(complianceRequest.getDuration());
//        existingCompliance.setWorkStatus(complianceRequest.getWorkStatus());
//        existingCompliance.setPriority(complianceRequest.getPriority());
//
//        // You can choose to update the companyId if needed
//        // existingCompliance.setCompanyId(complianceRequest.getCompanyId());
//
//        // Set the business unit ID to the compliance object
//        existingCompliance.setBusinessUnitId(businessUnitId);
//
//        // Save the updated compliance to the repository
//        Compliance updatedCompliance = complianceRepository.save(existingCompliance);
//
//        // Return a response indicating successful update
//        return ResponseEntity.status(HttpStatus.OK)
//                .body("Compliance with ID " + updatedCompliance.getId() + " successfully updated.");
//    }

//    @Override
//    public ResponseEntity<?> fetchBusinessCompliance(Long complianceId, Long businessUnitId) {
//        // Fetch the compliance based on the complianceId and businessUnitId
//        Compliance compliance = complianceRepository.findByIdAndBusinessUnitId(complianceId, businessUnitId);
//
//        if (compliance != null) {
//            // Return the found compliance with a 200 OK status
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(compliance);
//        } else {
//            // Return a 404 Not Found response if the compliance is not found
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("Compliance not found for the given ID and Business Unit.");
//        }
//    }


    @Override
    public void saveAllCompliances(List<Compliance> complianceList) {

    }
//    public List<ComplianceResponse> fetchAllCompliances(Long companyId, Long businessUnitId) {
//        List<Compliance> compliances = complianceRepository.findByCompanyIdAndBusinessUnitId(companyId, businessUnitId);
//
//        if (compliances.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No compliances found for the given CompanyId and BusinessUnitId");
//        }
//
//        List<ComplianceResponse> complianceResponses = new ArrayList<>();
//
//        for (Compliance compliance : compliances) {
//            ComplianceResponse response = new ComplianceResponse();
//            response.setId(compliance.getId());
//            response.setName(compliance.getComplianceName());
//            response.setDescription(compliance.getDescription());
//            response.setApprovalState(compliance.getApprovalState());
//            response.setApplicableZone(compliance.getApplicableZone());
//            response.setCreatedAt(compliance.getCreatedAt());
//            response.setUpdatedAt(compliance.getUpdatedAt());
//            response.setEnable(compliance.isEnable());
//            response.setStartDate(compliance.getStartDate());
//            response.setDueDate(compliance.getDueDate());
//            response.setCompletedDate(compliance.getCompletedDate());
//            response.setDuration(compliance.getDuration());
//            response.setWorkStatus(compliance.getWorkStatus());
//            response.setPriority(compliance.getPriority());
//            response.setBusinessUnitId(compliance.getBusinessUnitId());
//            response.setCompanyId(compliance.getCompanyId());
//
//            complianceResponses.add(response);
//        }
//
//        return complianceResponses;
//    }

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
        compliance.setDescription(complianceRequest.getDescription());
        compliance.setApprovalState(complianceRequest.getApprovalState());
        compliance.setApplicableZone(complianceRequest.getApplicableZone());
        compliance.setCreatedAt(new Date());
        compliance.setUpdatedAt(new Date());
        compliance.setEnable(complianceRequest.isEnable());
        compliance.setStartDate(complianceRequest.getStartDate());
        compliance.setDueDate(complianceRequest.getDueDate());
        compliance.setCompletedDate(complianceRequest.getCompletedDate());
        compliance.setDuration(complianceRequest.getDuration());
        compliance.setWorkStatus(complianceRequest.getWorkStatus());
        compliance.setPriority(complianceRequest.getPriority());
        compliance.setCategoryId(complianceRequest.getCategoryId());
        compliance.setCertificateType(complianceRequest.getCertificateType());
        compliance.setBusinessUnit(businessUnit);

        // Save Compliance
        Compliance savedCompliance = complianceRepository.save(compliance);

        // Map saved Compliance entity to ComplianceResponse
        return createComplianceResponse(savedCompliance, userId);
    }

    private ComplianceResponse createComplianceResponse(Compliance compliance, Long userId) {
        ComplianceResponse response = new ComplianceResponse();
        response.setId(compliance.getId());
        response.setName(compliance.getComplianceName());
        response.setDescription(compliance.getDescription());
        response.setApprovalState(compliance.getApprovalState());
        response.setApplicableZone(compliance.getApplicableZone());
        response.setCreatedAt(compliance.getCreatedAt());
        response.setUpdatedAt(compliance.getUpdatedAt());
        response.setEnable(compliance.isEnable());
        response.setStartDate(compliance.getStartDate());
        response.setDueDate(compliance.getDueDate());
        response.setCompletedDate(compliance.getCompletedDate());
        response.setDuration(compliance.getDuration());
        response.setWorkStatus(compliance.getWorkStatus());
        response.setPriority(compliance.getPriority());
        response.setBusinessUnitId(compliance.getBusinessUnit().getId());
        response.setCreatedBy(userId);
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
        compliance.setDescription(complianceRequest.getDescription());
        compliance.setApprovalState(complianceRequest.getApprovalState());
        compliance.setApplicableZone(complianceRequest.getApplicableZone());
        compliance.setStartDate(complianceRequest.getStartDate());
        compliance.setDueDate(complianceRequest.getDueDate());
        compliance.setCompletedDate(complianceRequest.getCompletedDate());
        compliance.setDuration(complianceRequest.getDuration());
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
        response.setDescription(compliance.getDescription());
        response.setApprovalState(compliance.getApprovalState());
        response.setApplicableZone(compliance.getApplicableZone());
        response.setCreatedAt(compliance.getCreatedAt());
        response.setUpdatedAt(compliance.getUpdatedAt());
        response.setEnable(compliance.isEnable());
        response.setStartDate(compliance.getStartDate());
        response.setDueDate(compliance.getDueDate());
        response.setCompletedDate(compliance.getCompletedDate());
        response.setDuration(compliance.getDuration());
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
            response.setDescription(compliance.getDescription());
            response.setApprovalState(compliance.getApprovalState());
            response.setApplicableZone(compliance.getApplicableZone());
            response.setCreatedAt(compliance.getCreatedAt());
            response.setUpdatedAt(compliance.getUpdatedAt());
            response.setEnable(compliance.isEnable());
            response.setStartDate(compliance.getStartDate());
            response.setDueDate(compliance.getDueDate());
            response.setCompletedDate(compliance.getCompletedDate());
            response.setDuration(compliance.getDuration());
            response.setWorkStatus(compliance.getWorkStatus());
            response.setPriority(compliance.getPriority());
            response.setBusinessUnitId(compliance.getBusinessUnit().getId());
            responses.add(response);
        }

        return responses;
    }


//    @Override
//    public ResponseEntity deleteCompliance(Long complianceId, Long companyId) {
//
//
//        if (!complianceRepository.existsById(complianceId)) {
//
//            return ResponseEntity
//        }
//
//        Compliance compliance = complianceRepository.findById(complianceId).orElse(null);
//        if (compliance != null) {
//
//            compliance.setEnable(false);
//            complianceRepository.delete(compliance);
//
//            return new ResponseEntity
//        } else {
//            return ResponseEntity
//        }
//    }

//    @Override
//    public ResponseEntity<ComplianceResponse> updateComplianceStatus(Long complianceId, int status) {
//
//        Compliance compliance = complianceRepository.findById(complianceId)
//                .orElseThrow(() -> new IllegalArgumentException("Compliance not found "));
//
//        compliance.setWorkStatus(status);
//
//        complianceRepository.save(compliance);
//
//        ComplianceResponse response = new ComplianceResponse();
//        response.setId(compliance.getId());
//        response.setName(compliance.getComplianceName());
//        response.setDescription(compliance.getDescription());
//        response.setApprovalState(compliance.getApprovalState());
//        response.setApplicableZone(compliance.getApplicableZone());
//        response.setCreatedAt(compliance.getCreatedAt());
//        response.setUpdatedAt(compliance.getUpdatedAt());
//        response.setEnable(compliance.isEnable());
//        response.setStartDate(compliance.getStartDate());
//        response.setDueDate(compliance.getDueDate());
//        response.setCompletedDate(compliance.getCompletedDate());
//        response.setDuration(compliance.getDuration());
//        response.setWorkStatus(compliance.getWorkStatus());
//        response.setPriority(compliance.getPriority());
//
//        return ResponseEntity
//    }
//
//    @Override
//    public ResponseEntity fetchManageCompliancesByUserId(Long userId) {
//        return null;
//    }
//
//    @Override
//    public ComplianceResponse getAllComplianceByCompanyUnitTeam(Long teamId, Long companyId, Long businessUnitId) {
//
//        List<Compliance> compliance = complianceRepository.
//                findByCompanyIdAndBusinessUnitIdAndTeamId(companyId,businessUnitId,teamId);
//
//        System.out.println(compliance);
//        return null;
//    }

//    @Override
//    public Map<Long, List<ComplianceResponse>> getAllComplianceByCompanyId() {
//        List<Compliance> allCompliances = complianceRepository.findAll();
//
//        Map<Long, List<ComplianceResponse>> mapalldata = new HashMap<>();
//
//        for (Compliance compliance : allCompliances) {
//            Long companyId = compliance.getCompanyId();
//
//            if (companyId != null) {
//                List<ComplianceResponse> companyComplianceList =
//                        mapalldata.getOrDefault(companyId, new ArrayList<>());
//
//                ComplianceResponse response = new ComplianceResponse();
//                response.setId(compliance.getId());
//                response.setName(compliance.getComplianceName());
//                response.setDescription(compliance.getDescription());
//
//                companyComplianceList.add(response);
//
//                mapalldata.put(companyId, companyComplianceList);
//            }
//        }
//
//        return mapalldata;
//    }

//    @Override
//    public Map<Long, Integer> getComplianceCount(){
//        List<Compliance>complianceList = complianceRepository.findAll();
//        Map<Long,Integer>resCount = new HashMap<>();
//        for(Compliance c : complianceList){
//            Long companyId = c.getCompanyId();
//            if(resCount.get(companyId)!=null){
//                int count  = resCount.get(companyId);
//                resCount.put(companyId,count+1);
//            }
//
//            else{
//                resCount.put(companyId,1);
//            }
//        }
//        return resCount;
//    }


//    @Override
//    public Map<Long, Map<Long, Integer>> getComplianceCountsByCompanyAndBusinessUnit() {
//        List<Compliance> compliances = complianceRepository.findAll();
//        Map<Long, Map<Long, Integer>> result = new HashMap<>();
//
//        for (Compliance compliance : compliances) {
//            Long companyId = compliance.getCompanyId();
//            Long businessUnitId = compliance.getBusinessUnitId();
//
//            if (result.containsKey(companyId)) {
//                Map<Long, Integer> businessUnitMap = result.get(companyId);
//                businessUnitMap.put(businessUnitId, businessUnitMap.getOrDefault(businessUnitId, 0) + 1);
//            } else {
//                Map<Long, Integer> businessUnitMap = new HashMap<>();
//                businessUnitMap.put(businessUnitId, 1);
//                result.put(companyId, businessUnitMap);
//            }
//        }
//
//        return result;
//    }

    @Override
    public List<Compliance> getCompliancesByBusinessUnitId(Long id) {

            List<Compliance> complianceList = complianceRepository.findByBusinessUnitId(id);

        return  complianceList;


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
        return List.of();
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
        return Map.of();
    }

    @Override
    public Map<Long, Integer> getComplianceCount() {
        return Map.of();
    }

    @Override
    public Map<Long, Map<Long, Integer>> getComplianceCountsByCompanyAndBusinessUnit() {
        return Map.of();
    }
}
