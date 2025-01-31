package com.lawzoom.complianceservice.dto.documentRequiredDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDocumentResponseDTO {
    private String status;
    private String message;
    private DocumentRequiredDTO document;
}
