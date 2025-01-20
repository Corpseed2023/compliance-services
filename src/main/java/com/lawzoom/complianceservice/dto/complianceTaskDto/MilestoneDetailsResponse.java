package com.lawzoom.complianceservice.dto.complianceTaskDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneDetailsResponse {

    private Long companyId;
    private String companyName;

    private Long businessUnit;

    private String businessName;

    private Long businessActivityId;

    private String businessActivityName;

    private Long complianceId;

    private String complianceName;

    private Long mileStoneId;

    private String mileStoneName;

    private String criticality;

    private LocalDate startedDate;

    private LocalDate dueDate;

    private String statusName;

    private Long managerId;

    private String managerName;





}
