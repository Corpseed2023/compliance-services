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
public class TaskRequest {

    private String name;
    private String description;
    private Long statusId;
    private Date startDate;
    private Date dueDate;
    private Date completedDate;
    private String criticality;
    private Long milestoneId;
    private Long managerId;
    private Long assigneeId;

}
