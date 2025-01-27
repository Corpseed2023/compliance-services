package com.lawzoom.complianceservice.service.documentRequiredService;


import com.lawzoom.complianceservice.dto.documentRequiredDto.ComplianceDocumentsResponseDTO;
import com.lawzoom.complianceservice.dto.documentRequiredDto.CreateDocumentResponseDTO;

public interface DocumentRequiredService {


    CreateDocumentResponseDTO createDocumentRequired(Long complianceId, String name);

    ComplianceDocumentsResponseDTO getDocumentsByCompliance(Long complianceId);
}
