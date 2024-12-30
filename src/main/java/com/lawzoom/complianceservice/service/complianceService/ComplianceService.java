package com.lawzoom.complianceservice.service.complianceService;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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


    ComplianceResponse fetchCompliance(Long complianceId, Long companyId);

    ResponseEntity deleteCompliance(Long complianceId, Long companyId);

//==========================================complete till delete on 14 sept 2023==================================================


    ResponseEntity updateComplianceStatus(Long complianceId, int status);

    ResponseEntity fetchManageCompliancesByUserId(Long userId);


    ComplianceResponse getAllComplianceByCompanyUnitTeam(Long teamId, Long companyId,
                                                         Long businessUnitId);


    Map<Long, List<ComplianceResponse>> getAllComplianceByCompanyId();

    Map<Long, Integer> getComplianceCount();

    Map<Long, Map<Long, Integer>> getComplianceCountsByCompanyAndBusinessUnit();

    List<Compliance> getCompliancesByBusinessUnitId(Long id);

    ComplianceResponse saveCompliance(@Valid ComplianceRequest complianceRequest, Long businessUnitId, Long userId);

    ComplianceResponse updateCompliance(@Valid ComplianceRequest complianceRequest, java.lang.Long businessUnitId,Long complianceId);

    List<ComplianceResponse> fetchCompliancesByBusinessUnit(Long businessUnitId);
}
