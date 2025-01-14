package com.lawzoom.complianceservice.dto.document;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponse{
    private Long id;
    private String documentName;
    private String fileName;
    private Date issueDate;
    private String referenceNumber;
    private String remarks;
    private Date uploadDate;
    private boolean isEnable;
}
