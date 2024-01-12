package com.lawzoom.complianceservice.serviceimpl.complianceServiceImpl;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.dto.userDto.UserRequest;
import com.lawzoom.complianceservice.feignClient.AuthenticationFeignClient;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.complianceService.ComplianceService;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
//import com.lawzoom.complianceservice.response;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComplianceServiceImpl implements ComplianceService {
//

    @Autowired
    private ComplianceRepo complianceRepository;

    @Autowired
    private AuthenticationFeignClient authenticationFeignClient;

        @Override
      public ResponseEntity deleteBusinessCompliance(Long complianceId, Long companyId) {


        if (!complianceRepository.existsById(complianceId)) {

            return ResponseEntity.notFound();
        }

        Compliance compliance = complianceRepository.findById(complianceId).orElse(null);
        if (compliance != null) {

            compliance.setEnable(false);
            complianceRepository.save(compliance);

            return  ResponseEntity.ok();
        } else {
            return ResponseEntity.notFound();
        }
    }

    @Override
    public ResponseEntity fetchAllComplianceByBusinessUnitId(Long businessUnitId) {
       List<Compliance> complianceList =this.complianceRepository.findAllByBusinessUnitId(businessUnitId);
        return null;
    }

    public ComplianceResponse saveBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId) {
        Compliance compliance = new Compliance();
        compliance.setName(complianceRequest.getName());
        compliance.setDescription(complianceRequest.getDescription());
        compliance.setApprovalState(complianceRequest.getApprovalState());
        compliance.setApplicableZone(complianceRequest.getApplicableZone());
        compliance.setStartDate(complianceRequest.getStartDate());
        compliance.setDueDate(complianceRequest.getDueDate());
        compliance.setCompletedDate(complianceRequest.getCompletedDate());
        compliance.setDuration(complianceRequest.getDuration());
        compliance.setWorkStatus(complianceRequest.getWorkStatus());
        compliance.setPriority(complianceRequest.getPriority());
//        compliance.setCompanyId(complianceRequest.getCompanyId());
        compliance.setBusinessUnitId(businessUnitId);

        Compliance savedCompliance = complianceRepository.save(compliance);

        ComplianceResponse complianceResponse = new ComplianceResponse();

        complianceResponse.setName(compliance.getName());
        complianceResponse.setDescription(compliance.getDescription());
        complianceResponse.setApprovalState(compliance.getApprovalState());
        complianceResponse.setEnable(compliance.isEnable());
        complianceResponse.setApplicableZone(compliance.getApplicableZone());
        complianceResponse.setStartDate(compliance.getStartDate());
        complianceResponse.setPriority(compliance.getPriority());

        return complianceResponse;


//       ResponseEntity.ok(savedCompliance);
//        return ResponseEntity.creationComplete("Compliance successfully Create", HttpStatus.CREATED);

    }
    @Override
    public ResponseEntity updateBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId) {
        Compliance existingCompliance = complianceRepository.findById(complianceRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException("Compliance not found with ID: " ));

        existingCompliance.setName(complianceRequest.getName());
        existingCompliance.setDescription(complianceRequest.getDescription());
        existingCompliance.setApprovalState(complianceRequest.getApprovalState());
        existingCompliance.setApplicableZone(complianceRequest.getApplicableZone());
        existingCompliance.setStartDate(complianceRequest.getStartDate());
        existingCompliance.setDueDate(complianceRequest.getDueDate());
        existingCompliance.setCompletedDate(complianceRequest.getCompletedDate());
        existingCompliance.setDuration(complianceRequest.getDuration());
        existingCompliance.setWorkStatus(complianceRequest.getWorkStatus());
        existingCompliance.setPriority(complianceRequest.getPriority());
//        existingCompliance.setCompanyId(complianceRequest.getCompanyId());

        existingCompliance.setBusinessUnitId(businessUnitId);

        Compliance updatedCompliance = complianceRepository.save(existingCompliance);

        return ResponseEntity.creationComplete(" successfully updated", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity fetchBusinessCompliance(Long complianceId, Long businessUnitId) {
        Compliance compliance = complianceRepository.findByIdAndBusinessUnitId(complianceId, businessUnitId);

        if (compliance != null) {
            return ResponseEntity.creationComplete(" successfully ", HttpStatus.CREATED);
        }
        return ResponseEntity.notFound();
    }

    @Override
    public void saveAllCompliances(List<Compliance> complianceList) {

    }
    public List<ComplianceResponse> fetchAllCompliances(Long companyId, Long businessUnitId) {
        List<Compliance> compliances = complianceRepository.findByCompanyIdAndBusinessUnitId(companyId, businessUnitId);

        if (compliances.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No compliances found for the given CompanyId and BusinessUnitId");
        }

        List<ComplianceResponse> complianceResponses = new ArrayList<>();

        for (Compliance compliance : compliances) {
            ComplianceResponse response = new ComplianceResponse();
            response.setId(compliance.getId());
            response.setName(compliance.getName());
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
            response.setBusinessUnitId(compliance.getBusinessUnitId());
            response.setCompanyId(compliance.getCompanyId());

            complianceResponses.add(response);
        }

        return complianceResponses;
    }

//    @Override
//    public ComplianceResponse fetchAllCompliances(Long companyId) {
//        // Retrieve all compliances for the given companyId
//        List<Compliance> compliances = complianceRepository.findByCompanyId(companyId);
//
//        // Create a list to store the mapped DTOs
//        List<ComplianceResponse> complianceResponses = compliances.stream()
//                .map(compliance -> {
//                    ComplianceResponse response = new ComplianceResponse();
//                    response.setId(compliance.getId());
//                    response.setName(compliance.getName());
//                    response.setDescription(compliance.getDescription());
//                    response.setApprovalState(compliance.getApprovalState());
//                    response.setApplicableZone(compliance.getApplicableZone());
//                    response.setCreatedAt(compliance.getCreatedAt());
//                    response.setUpdatedAt(compliance.getUpdatedAt());
//                    response.setEnable(compliance.isEnable());
//                    response.setStartDate(compliance.getStartDate());
//                    response.setDueDate(compliance.getDueDate());
//                    response.setCompletedDate(compliance.getCompletedDate());
//                    response.setDuration(compliance.getDuration());
//                    response.setWorkStatus(compliance.getWorkStatus());
//                    response.setPriority(compliance.getPriority());
//                    return response;
//                })
//                .collect(Collectors.toList());
//
//        // Return the ResponseEntity with the list of ComplianceResponse
//        return complianceResponses;
//    }

    @PostMapping("/saveCompliance")
    public ComplianceResponse saveCompliance(@Valid @RequestBody ComplianceRequest complianceRequest,
                                             @RequestParam("companyId") Long companyId,
                                             @RequestParam("businessUnitId") Long businessUnitId,
                                             @RequestParam("userId") Long userId) {
        if (companyId == null || businessUnitId == null) {
            throw new IllegalArgumentException("Please provide companyId and businessUnitId");
        }

        UserRequest userData = authenticationFeignClient.getUserId(userId);

        if (userData == null || StringUtils.isBlank(userData.getEmail())) {
            throw new IllegalArgumentException("Invalid user data. Cannot create compliance.");
        }

        try {
            Compliance compliance = new Compliance();

            // Set compliance properties
            compliance.setName(complianceRequest.getName());
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
            compliance.setCompanyId(companyId);
            compliance.setBusinessUnitId(businessUnitId);
            compliance.setCreatedBy(userId);

            // Save compliance entity
            complianceRepository.save(compliance);

            // Check if data got saved successfully
            if (compliance.getId() == null) {
                // Data not saved, handle the scenario accordingly
                throw new RuntimeException("Failed to save compliance. Database save operation did not return a valid ID.");
            }

            // Create and return ComplianceResponse
            ComplianceResponse response = createComplianceResponse(compliance, companyId, businessUnitId, userId);

            return response;


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save compliance");
        }
    }

    private ComplianceResponse createComplianceResponse(Compliance compliance, Long companyId, Long businessUnitId, Long userId) {
        ComplianceResponse response = new ComplianceResponse();
        response.setId(compliance.getId());
        response.setName(compliance.getName());
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
        response.setCompanyId(companyId);
        response.setBusinessUnitId(businessUnitId);
        response.setCreatedBy(userId);
        return response;
    }

    @Override
    public ComplianceResponse updateCompliance(ComplianceRequest complianceRequest, Long companyId, Long businessUnitId) {

        List<Compliance> compliances = complianceRepository.findByCompanyIdAndBusinessUnitId(companyId, businessUnitId);

        if (compliances.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No compliances found for the given CompanyId and BusinessUnitId");
        }


        Optional<Compliance> optionalCompliance = complianceRepository.findById(complianceRequest.getId());

        if (optionalCompliance.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No compliances found");
        }

        if (optionalCompliance.isPresent()) {
            Compliance compliance = optionalCompliance.get();

            compliance.setName(complianceRequest.getName());
            compliance.setDescription(complianceRequest.getDescription());
            compliance.setApprovalState(complianceRequest.getApprovalState());
            compliance.setApplicableZone(complianceRequest.getApplicableZone());
            compliance.setStartDate(complianceRequest.getStartDate());
            compliance.setDueDate(complianceRequest.getDueDate());
            compliance.setCompletedDate(complianceRequest.getCompletedDate());
            compliance.setDuration(complianceRequest.getDuration());
            compliance.setWorkStatus(complianceRequest.getWorkStatus());
            compliance.setPriority(complianceRequest.getPriority());
//            compliance.setCompanyId(complianceRequest.getCompanyId());

            complianceRepository.save(compliance);

            ComplianceResponse complianceResponse = new ComplianceResponse();
            complianceResponse.setId(compliance.getId());
            complianceResponse.setName(compliance.getName());
            complianceResponse.setDescription(compliance.getDescription());
            complianceResponse.setApprovalState(compliance.getApprovalState());
            complianceResponse.setApplicableZone(compliance.getApplicableZone());
            complianceResponse.setCreatedAt(compliance.getCreatedAt());
            complianceResponse.setUpdatedAt(compliance.getUpdatedAt());
            complianceResponse.setEnable(compliance.isEnable());
            complianceResponse.setStartDate(compliance.getStartDate());
            complianceResponse.setDueDate(compliance.getDueDate());
            complianceResponse.setCompletedDate(compliance.getCompletedDate());
            complianceResponse.setDuration(compliance.getDuration());
            complianceResponse.setWorkStatus(compliance.getWorkStatus());
            complianceResponse.setPriority(compliance.getPriority());

            return complianceResponse;

        }
        return null;
    }



//    @Override
//    public ResponseEntity updateCompliance(ComplianceRequest complianceRequest, Long companyId) {
//
//        Optional<Compliance> existingCompliance = Optional.ofNullable(complianceRepository.findById(complianceRequest.getId())
//                .orElseThrow(() -> new EntityNotFoundException("Company Id not found")));
//
//
//        return null;
//    }



    @Override
    public ComplianceResponse fetchCompliance(Long complianceId, Long companyId) {
        // Use the complianceRepository to find the compliance by ID and companyId
        Optional<Compliance> optionalCompliance = complianceRepository.findByIdAndCompanyId(complianceId, companyId);

        if (optionalCompliance.isPresent()) {
            Compliance compliance = optionalCompliance.get();

            // Create a ComplianceResponse object and populate it with data from the Compliance entity
            ComplianceResponse response = new ComplianceResponse();
            response.setId(compliance.getId());
            response.setName(compliance.getName());
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

          return response;
        } else {
            throw new EntityNotFoundException("Compliance not found");
        }
    }

    @Override
    public ResponseEntity deleteCompliance(Long complianceId, Long companyId) {


        if (!complianceRepository.existsById(complianceId)) {

            return ResponseEntity.notFound();
        }

        Compliance compliance = complianceRepository.findById(complianceId).orElse(null);
        if (compliance != null) {

            compliance.setEnable(false);
            complianceRepository.delete(compliance);

            return  ResponseEntity.ok();
        } else {
            return ResponseEntity.notFound();
        }
    }

    @Override
    public ResponseEntity<ComplianceResponse> updateComplianceStatus(Long complianceId, int status) {

        Compliance compliance = complianceRepository.findById(complianceId)
                .orElseThrow(() -> new IllegalArgumentException("Compliance not found "));

        compliance.setWorkStatus(status);

        complianceRepository.save(compliance);

        ComplianceResponse response = new ComplianceResponse();
        response.setId(compliance.getId());
        response.setName(compliance.getName());
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

        return ResponseEntity.updatedStatus(response+"updated successfully");
    }

    @Override
    public ResponseEntity fetchManageCompliancesByUserId(Long userId) {
        return null;
    }

//    @Override
//    public ResponseEntity<List<ComplianceResponse>> fetchManageCompliancesByUserId(Long userId) {
//
//        List<Compliance> compliances = complianceRepository.findByUserId(userId);
//
//        List<ComplianceResponse> complianceResponses = compliances.stream()
//                .map(compliance -> {
//                    ComplianceResponse response = new ComplianceResponse();
//                    response.setId(compliance.getId());
//                    response.setTitle(compliance.getTitle());
//                    response.setDescription(compliance.getDescription());
//                    response.setApprovalState(compliance.getApprovalState());
//                    response.setApplicableZone(compliance.getApplicableZone());
//                    response.setCreatedAt(compliance.getCreatedAt());
//                    response.setUpdatedAt(compliance.getUpdatedAt());
//                    response.setEnable(compliance.isEnable());
//                    response.setStartDate(compliance.getStartDate());
//                    response.setDueDate(compliance.getDueDate());
//                    response.setCompletedDate(compliance.getCompletedDate());
//                    response.setDuration(compliance.getDuration());
//                    response.setWorkStatus(compliance.getWorkStatus());
//                    response.setPriority(compliance.getPriority());
//                    return response;
//                })
//                .collect(Collectors.toList());
//
//        return null;
//    }


    @Override
    public ComplianceResponse getAllComplianceByCompanyUnitTeam(Long teamId, Long companyId, Long businessUnitId) {

        List<Compliance> compliance = complianceRepository.
                findByCompanyIdAndBusinessUnitIdAndTeamId(companyId,businessUnitId,teamId);

        System.out.println(compliance);
        return null;
    }

    @Override
    public Map<Long, List<ComplianceResponse>> getAllComplianceByCompanyId() {
        List<Compliance> allCompliances = complianceRepository.findAll();

        Map<Long, List<ComplianceResponse>> mapalldata = new HashMap<>();

        for (Compliance compliance : allCompliances) {
            Long companyId = compliance.getCompanyId();

            if (companyId != null) {
                List<ComplianceResponse> companyComplianceList =
                        mapalldata.getOrDefault(companyId, new ArrayList<>());

                ComplianceResponse response = new ComplianceResponse();
                response.setId(compliance.getId());
                response.setName(compliance.getName());
                response.setDescription(compliance.getDescription());

                companyComplianceList.add(response);

                mapalldata.put(companyId, companyComplianceList);
            }
        }

        return mapalldata;
    }

    @Override
    public Map<Long, Integer> getComplianceCount(){
        List<Compliance>complianceList = complianceRepository.findAll();
        Map<Long,Integer>resCount = new HashMap<>();
        for(Compliance c : complianceList){
            Long companyId = c.getCompanyId();
            if(resCount.get(companyId)!=null){
                int count  = resCount.get(companyId);
                resCount.put(companyId,count+1);
            }

            else{
                resCount.put(companyId,1);
            }
        }
        return resCount;
    }


    @Override
    public Map<Long, Map<Long, Integer>> getComplianceCountsByCompanyAndBusinessUnit() {
        List<Compliance> compliances = complianceRepository.findAll();
        Map<Long, Map<Long, Integer>> result = new HashMap<>();

        for (Compliance compliance : compliances) {
            Long companyId = compliance.getCompanyId();
            Long businessUnitId = compliance.getBusinessUnitId();

            if (result.containsKey(companyId)) {
                Map<Long, Integer> businessUnitMap = result.get(companyId);
                businessUnitMap.put(businessUnitId, businessUnitMap.getOrDefault(businessUnitId, 0) + 1);
            } else {
                Map<Long, Integer> businessUnitMap = new HashMap<>();
                businessUnitMap.put(businessUnitId, 1);
                result.put(companyId, businessUnitMap);
            }
        }

        return result;
    }

    @Override
    public List<Compliance> getCompliancesByBusinessUnitId(Long id) {
        return null;
    }
}
