package com.lawzoom.complianceservice.dto.complianceTaskDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MilestoneUpdateRequest {

    @NotNull
    private Long milestoneId;
    private String mileStoneName;
    private String description;
    private LocalDate dueDate;
    private Long statusId;
    private Long assignedToUserId;
    private Long assignedByUserId;
    private String criticality;
    private String remark;
}
