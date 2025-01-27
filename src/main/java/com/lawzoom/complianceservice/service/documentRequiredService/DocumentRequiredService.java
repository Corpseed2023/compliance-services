package com.lawzoom.complianceservice.service.documentRequiredService;

import com.lawzoom.complianceservice.dto.documentRequiredDto.CreateDocumentResponseDTO;
import com.lawzoom.complianceservice.dto.documentRequiredDto.MilestoneDocumentsResponseDTO;

import java.util.Map;

public interface DocumentRequiredService {

    CreateDocumentResponseDTO createDocumentRequired(Long milestoneId, String name);

    MilestoneDocumentsResponseDTO getDocumentsByMilestone(Long milestoneId);
}
