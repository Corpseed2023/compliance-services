package com.lawzoom.complianceservice.dto.complianceDto;

import com.lawzoom.complianceservice.dto.DocumentRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class ComplianceRequest {

    @NotNull(message = "Compliance name is required.")
    @Size(max = 255, message = "Compliance name must not exceed 255 characters.")
    private String name;

    private String issueAuthority;

    private Long durationMonth;

    private Long durationYear;

    private String approvalState;

    private String applicableZone;

    private LocalDate startDate;

    private LocalDate dueDate;

    private LocalDate completedDate;

    private int workStatus;

    private int priority;

    private String certificateType;

    private boolean isEnable;

    private Long subscriberId;

    // New field for documents
    private List<DocumentRequest> documents;
}
