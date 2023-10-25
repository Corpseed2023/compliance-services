package com.lawzoom.complianceservice.dto.complianceTaskDto;

import lombok.*;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ManageComplianceTaskResponse {

    private String companyName;

    private String businessUnitName;

    private String businessActivity;

    private Long taskId;

    private String taskName;

    private String startDate;

    private String dueDate;

    private String completedDate;

    private String status;

    private Long reporterId;

    private String reporterName;

    private Long assigneeId;

    private String assigneeName;

    private int expectedProgress;

    private int actualProgress;

    private int totalDays;

    private int dayConsumed;

    private String criticality;

}
