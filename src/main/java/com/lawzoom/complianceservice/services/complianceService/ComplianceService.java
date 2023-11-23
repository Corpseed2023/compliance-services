package com.lawzoom.complianceservice.services.complianceService;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.response.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    List<ComplianceResponse> fetchAllCompliances(Long companyId, Long businessUnitId);

    ComplianceResponse saveCompliance(ComplianceRequest complianceRequest,
                                      Long companyId, Long businessUnitId, Long teamId);

    ComplianceResponse updateCompliance(ComplianceRequest complianceRequest,
                                        Long companyId,Long businessUnitId);

    ComplianceResponse fetchCompliance(Long complianceId, Long companyId);

    ResponseEntity deleteCompliance(Long complianceId, Long companyId);

//==========================================complete till delete on 14 sept 2023==================================================


    ResponseEntity updateComplianceStatus(Long complianceId, int status);

    ResponseEntity fetchManageCompliancesByUserId(Long userId);


    ComplianceResponse getAllComplianceByCompanyUnitTeam(Long teamId, Long companyId,
                                                         Long businessUnitId);


    Map<Long, List<ComplianceResponse>> getAllComplianceByCompanyId();
}
