package com.lawzoom.complianceservice.dto;


import lombok.Data;

import java.util.Date;

@Data
public class DocumentRequest {
    private String documentName;
    private String fileName;
    private Date issueDate;
    private String referenceNumber;
    private String remarks;
    private Long addedById; // User ID of the person adding the document
}
