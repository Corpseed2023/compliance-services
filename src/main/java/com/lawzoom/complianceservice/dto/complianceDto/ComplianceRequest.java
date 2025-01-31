package com.lawzoom.complianceservice.dto.complianceDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRequest {


    @NotNull(message = "Compliance name is required.")
    @Size(max = 255, message = "Compliance name must not exceed 255 characters.")
    private String name;

    private String issueAuthority;

    private String approvalState;

    private String applicableZone;

    private int priority;

    private String certificateType;

    private Long subscriberId;



}
