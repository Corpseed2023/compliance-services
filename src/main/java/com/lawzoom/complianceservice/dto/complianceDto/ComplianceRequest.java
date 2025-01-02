package com.lawzoom.complianceservice.dto.complianceDto;



import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class ComplianceRequest {

    @NotNull(message = "Compliance name is required.")
    @Size(max = 255, message = "Compliance name must not exceed 255 characters.")
    private String name;

    private String issueAuthority;

    private String description;

    private String approvalState;

    private String applicableZone;

    private LocalDate startDate;

    private LocalDate dueDate;

    private LocalDate completedDate;

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

