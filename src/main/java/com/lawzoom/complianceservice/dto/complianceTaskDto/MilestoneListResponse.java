package com.lawzoom.complianceservice.dto.complianceTaskDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneListResponse {


    private Long id;
    private String mileStoneName;
    private String description;
    private Long statusId;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private boolean isEnable;
    private Long complianceId;
    private Long managerId;
    private String managerName;
    private Long assignedId;
    private String assignedName;
    private Long assignedBy;
    private String assignedByName;
    private LocalDate issuedDate;
    private LocalDate startedDate;
    private LocalDate dueDate;
    private LocalDate expiryDate;
    private LocalDate completedDate;
    private String criticality;
    private String remark;
    private Long businessUnitId;
    private Long subscriberId;


}
