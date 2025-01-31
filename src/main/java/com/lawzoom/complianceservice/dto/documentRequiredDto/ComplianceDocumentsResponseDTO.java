package com.lawzoom.complianceservice.dto.documentRequiredDto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ComplianceDocumentsResponseDTO {
    private Long complianceId;
    private List<DocumentRequiredDTO> documents;
}
