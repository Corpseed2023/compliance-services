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
public class TaskMileStoneResponse {

    private Long id;
    private String name;
    private String description;
    private LocalDate date;
    private Long statusId;
    private String statusName;
    private Date createdAt;
    private Date updatedAt;
    private boolean isEnable;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate completedDate;
    private Long managerId;
    private String managerName;
    private Long assigneeId;
    private String assigneeName;
    private String criticality;

}
