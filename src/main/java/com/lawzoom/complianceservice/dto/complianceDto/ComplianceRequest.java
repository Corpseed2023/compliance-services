package com.lawzoom.complianceservice.dto.complianceDto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class ComplianceRequest {

    @NotNull(message = "Compliance name is required.")
    @Size(max = 255, message = "Compliance name must not exceed 255 characters.")
    private String name;

    private String description;

    private String approvalState;

    private String applicableZone;

    private Date startDate;

    private Date dueDate;

    private Date completedDate;

    private String duration;

    private int workStatus;

    private Long categoryId;

    @NotNull(message = "Company ID is required.")
    private Long companyId;

    @NotNull(message = "Business Unit ID is required.")
    private Long businessUnitId;

    private int priority;

    private String certificateType;

    private Long businessActivityId;

    private boolean isEnable;
}

