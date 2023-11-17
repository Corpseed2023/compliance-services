package com.lawzoom.complianceservice.services.complianceService;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.response.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ComplianceService {

    ResponseEntity fetchAllComplianceByBusinessUnitId(Long businessUnitId);

    ComplianceResponse saveBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId);

    ResponseEntity updateBusinessCompliance(ComplianceRequest complianceRequest, Long businessUnitId);

    ResponseEntity fetchBusinessCompliance(Long complianceId, Long businessUnitId);


    ResponseEntity deleteBusinessCompliance(Long complianceId, Long businessUnitId);
//==========================================complete till delete on 13 sept 2023==================================================

//======================================================================================?
    void saveAllCompliances(List<Compliance> complianceList);

    List<ComplianceResponse> fetchAllCompliances(Long companyId);

    ComplianceResponse saveCompliance(ComplianceRequest complianceRequest, Long companyId, Long businessUnitId);

    ResponseEntity updateCompliance(ComplianceRequest complianceRequest, Long companyId);

    ComplianceResponse fetchCompliance(Long complianceId, Long companyId);

    ResponseEntity deleteCompliance(Long complianceId, Long companyId);

//==========================================complete till delete on 14 sept 2023==================================================


    ResponseEntity updateComplianceStatus(Long complianceId, int status);

    ResponseEntity fetchManageCompliancesByUserId(Long userId);


}
