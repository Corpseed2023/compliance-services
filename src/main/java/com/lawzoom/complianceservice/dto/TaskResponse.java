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
public class TaskResponse {
    private Long id;
    private String name;
    private String description;
    private String status;
    private Date startDate;
    private Date dueDate;
    private Date completedDate;
    private String criticality;
    private Long reporterUserId;
    private String reporterUserName;
    private Long assigneeUserId;
    private String assigneeUserName;
}
