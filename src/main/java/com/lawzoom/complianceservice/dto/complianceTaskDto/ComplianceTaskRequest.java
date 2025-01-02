package com.lawzoom.complianceservice.dto.complianceTaskDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComplianceTaskRequest {

	private Long id;
	private String taskName;
	private String description;
	private Long criticalityId; // Maps criticality as an ID
	private LocalDate startDate; // Optional: Task start date
	private LocalDate dueDate; // Optional: Task due date
	private LocalDate completedDate; // Optional: Task due date
	private Long reporterUserId; // Maps the reporter user ID
	private Long assigneeUserId; // Maps the assignee user ID

}
