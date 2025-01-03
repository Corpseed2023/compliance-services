package com.lawzoom.complianceservice.dto.complianceTaskDto;


import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class MilestoneRequestForFetch {
    @NotNull(message = "Business Unit ID is required")
    private Long businessUnitId;

    @NotNull(message = "Company ID is required")
    private Long companyId;

    @NotNull(message = "Subscriber ID is required")
    private Long subscriberId;

    @NotNull(message = "Compliance ID is required")
    private Long complianceId;
}

