package com.lawzoom.complianceservice.dto.documentRequiredDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequiredDTO {
    private Long id;
    private String name;
    private Long complianceId;
}
