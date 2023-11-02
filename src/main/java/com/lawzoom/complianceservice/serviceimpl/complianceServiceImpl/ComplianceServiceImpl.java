package com.lawzoom.complianceservice.serviceimpl.complianceServiceImpl;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.repository.ComplianceRepo;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.complianceService.ComplianceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
//import com.lawzoom.complianceservice.response;



import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComplianceServiceImpl implements ComplianceService {
//

    @Autowired
    private ComplianceRepo complianceRepository;

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
        compliance.setName(complianceRequest.getTitle());
        compliance.setDescription(complianceRequest.getDescription());
        compliance.setApprovalState(complianceRequest.getApprovalState());
        compliance.setApplicableZone(complianceRequest.getApplicableZone());
        compliance.setStartDate(complianceRequest.getStartDate());
        compliance.setDueDate(complianceRequest.getDueDate());
        compliance.setCompletedDate(complianceRequest.getCompletedDate());
        compliance.setDuration(complianceRequest.getDuration());
        compliance.setWorkStatus(complianceRequest.getWorkStatus());
        compliance.setPriority(complianceRequest.getPriority());
        compliance.setCompanyId(complianceRequest.getCompanyId());
        compliance.setBusinessUnitId(businessUnitId);

        Compliance savedCompliance = complianceRepository.save(compliance);

        ComplianceResponse complianceResponse = new ComplianceResponse();

        complianceResponse.setTitle(compliance.getName());
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

        existingCompliance.setName(complianceRequest.getTitle());
        existingCompliance.setDescription(complianceRequest.getDescription());
        existingCompliance.setApprovalState(complianceRequest.getApprovalState());
        existingCompliance.setApplicableZone(complianceRequest.getApplicableZone());
        existingCompliance.setStartDate(complianceRequest.getStartDate());
        existingCompliance.setDueDate(complianceRequest.getDueDate());
        existingCompliance.setCompletedDate(complianceRequest.getCompletedDate());
        existingCompliance.setDuration(complianceRequest.getDuration());
        existingCompliance.setWorkStatus(complianceRequest.getWorkStatus());
        existingCompliance.setPriority(complianceRequest.getPriority());
        existingCompliance.setCompanyId(complianceRequest.getCompanyId());

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

    @Override
    public ResponseEntity fetchAllCompliances(Long companyId) {
        // Retrieve all compliances for the given companyId
        List<Compliance> compliances = complianceRepository.findByCompanyId(companyId);

        // Create a list to store the mapped DTOs
        List<ComplianceResponse> complianceResponses = compliances.stream()
                .map(compliance -> {
                    ComplianceResponse response = new ComplianceResponse();
                    response.setId(compliance.getId());
                    response.setTitle(compliance.getName());
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
                })
                .collect(Collectors.toList());

        // Return the ResponseEntity with the list of ComplianceResponse
        return ResponseEntity.creationComplete("Compliance successfully Create", HttpStatus.CREATED);
    }

    @Override
    public ComplianceResponse saveCompliance(ComplianceRequest complianceRequest, Long companyId) {
        try {
            // Create a new Compliance entity from the request
            Compliance compliance = new Compliance();
            compliance.setName(complianceRequest.getTitle());
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

            complianceRepository.save(compliance);

            ComplianceResponse response = new ComplianceResponse();
            response.setId(compliance.getId());
            response.setTitle(compliance.getName());
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
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public ResponseEntity updateCompliance(ComplianceRequest complianceRequest, Long companyId) {

        Optional<Compliance> optionalCompliance = complianceRepository.findById(complianceRequest.getId());

        if (optionalCompliance.isPresent()) {
            Compliance compliance = optionalCompliance.get();

            compliance.setName(complianceRequest.getTitle());
            compliance.setDescription(complianceRequest.getDescription());
            compliance.setApprovalState(complianceRequest.getApprovalState());
            compliance.setApplicableZone(complianceRequest.getApplicableZone());
            compliance.setStartDate(complianceRequest.getStartDate());
            compliance.setDueDate(complianceRequest.getDueDate());
            compliance.setCompletedDate(complianceRequest.getCompletedDate());
            compliance.setDuration(complianceRequest.getDuration());
            compliance.setWorkStatus(complianceRequest.getWorkStatus());
            compliance.setPriority(complianceRequest.getPriority());
            compliance.setCompanyId(complianceRequest.getCompanyId());

            complianceRepository.save(compliance);

            ComplianceResponse complianceResponse = new ComplianceResponse();
            complianceResponse.setId(compliance.getId());
            complianceResponse.setTitle(compliance.getName());
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

            return ResponseEntity.updatedStatus(complianceResponse+"Updated Successfully");

        }
        else
        {
             return ResponseEntity.notFound().build();
        }
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
            response.setTitle(compliance.getName());
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
        response.setTitle(compliance.getName());
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
}
