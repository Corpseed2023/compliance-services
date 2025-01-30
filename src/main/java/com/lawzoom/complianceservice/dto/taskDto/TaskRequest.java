package com.lawzoom.complianceservice.dto.taskDto;

import com.lawzoom.complianceservice.dto.DocumentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private String remark;
    private Boolean isEnable = true; // New field

    private List<TaskReminderRequest> reminders;

    private List<DocumentRequest> documents;
    private String comments;
    private Long subscriberId;
}
