package com.lawzoom.complianceservice.dto.complianceTaskDto;

import lombok.*;

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
