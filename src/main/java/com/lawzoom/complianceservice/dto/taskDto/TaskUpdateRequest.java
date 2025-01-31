package com.lawzoom.complianceservice.dto.taskDto;


import com.lawzoom.complianceservice.dto.DocumentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateRequest {
    private Long taskId;
    private String name;
    private String description;
    private Long statusId;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate completedDate;
    private String criticality;
    private String remark;
    private Long managerId;
    private Long assigneeId;
    private Long milestoneId;
    private Long subscriberId;
    private List<DocumentRequest> documents;
    private List<TaskReminderRequest> reminders;
}
