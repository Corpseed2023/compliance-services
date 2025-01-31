package com.lawzoom.complianceservice.dto.taskDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDocumentResponse {
    private Long id;
    private String documentName;
    private String fileUrl;
    private LocalDate issueDate;
    private String referenceNumber;
    private String remarks;
    private Date uploadDate;
    private Long addedById;
    private String addedByName;
}
