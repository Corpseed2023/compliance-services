package com.lawzoom.complianceservice.dto.taskDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskListResponse {

    private Long id;
    private String name;
    private String description;
    private String status;
    private Date startDate;
    private Date dueDate;
    private Date completedDate;
    private String criticality;
    private Long managerId;
    private String managerName;
    private Long assigneeUserId;
    private String assigneeUserName;
    private Long milestoneId;
    private String milestoneName;
    private String remark;
    private Long complianceId;
    private String complianceName;

}
