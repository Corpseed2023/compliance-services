package com.lawzoom.complianceservice.dto.complianceDto;


import lombok.Data;

import java.util.Date;

@Data
public class ComplianceResponse {

    private Long id;
    private String name;
    private String description;
    private String approvalState;
    private String applicableZone;
    private Date createdAt;
    private Date updatedAt;
    private boolean isEnable;
    private Date startDate;
    private Date dueDate;
    private Date completedDate;
    private String duration;
    private int workStatus;
    private int priority;
    private Long companyId;
    private Long businessUnitId;
    private Long createdBy;
}
