package com.lawzoom.complianceservice.dto;


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
    private int timelineValue;
    private String timelineType;
    private String status;
    private Date startDate;
    private Date dueDate;
    private Date completedDate;
    private String criticality;
    private Long milestoneId;
    private Long reporterUserId;
    private Long assigneeUserId;
}
