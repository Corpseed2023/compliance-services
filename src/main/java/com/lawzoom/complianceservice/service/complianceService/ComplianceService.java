package com.lawzoom.complianceservice.service.complianceService;


import com.lawzoom.complianceservice.dto.complianceDto.CompanyComplianceDTO;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ComplianceService {

    ComplianceResponse saveCompliance(@Valid ComplianceRequest complianceRequest, Long businessUnitId, Long userId);

    ComplianceResponse updateCompliance(@Valid ComplianceRequest complianceRequest, Long businessUnitId,Long complianceId);

    List<ComplianceResponse> fetchCompliancesByBusinessUnit(Long businessUnitId, Long userId, Long subscriberId);

    List<CompanyComplianceDTO> getCompanyComplianceDetails(Long userId, Long subscriberId);

    Map<String, Object> fetchComplianceById(Long complianceId);

}
