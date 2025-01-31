package com.lawzoom.complianceservice.dto.taskDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskListResponse {
    private Long id;
    private String name;
    private String description;

    private Long statusId;
    private String statusName;

    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate completedDate;
    private String criticality;

    private Long managerId;
    private String managerName;

    private Long assigneeId;
    private String assigneeName;

    private Long milestoneId;
    private String milestoneName;

    private String remark;

    private Long complianceId;
    private String complianceName;

    private List<TaskReminderResponse> reminders;
    private List<TaskDocumentResponse> documents;
}
