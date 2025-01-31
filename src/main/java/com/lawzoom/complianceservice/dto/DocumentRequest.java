package com.lawzoom.complianceservice.dto;


import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class DocumentRequest {

    private String documentName;
    private String file;
    private String referenceNumber;
    private String remarks;
    private Long addedById; 
}
