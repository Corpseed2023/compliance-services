package com.lawzoom.complianceservice.dto.documentRequiredDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilestoneDocumentsResponseDTO {
    private Long milestoneId;
    private List<DocumentRequiredDTO> documents;
}
