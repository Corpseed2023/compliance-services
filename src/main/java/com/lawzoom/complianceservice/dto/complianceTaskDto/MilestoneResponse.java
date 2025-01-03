package com.lawzoom.complianceservice.dto.complianceTaskDto;


import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class MilestoneResponse {
    private Long id;
    private String mileStoneName;
    private String description;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private boolean isEnable;
    private Long complianceId;
    private Long reporterId;
    private Long assignedTo;
    private Long assignedBy;
    private String assigneeMail;
    private LocalDate issuedDate;
    private String criticality;
    private Long businessUnitId;
}

