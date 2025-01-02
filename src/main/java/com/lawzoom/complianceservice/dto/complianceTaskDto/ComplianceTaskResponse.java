package com.lawzoom.complianceservice.dto.complianceTaskDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ComplianceTaskResponse {

	private Long id;
	private String taskName;
	private String description;
	private String criticality; // Maps criticality as a description
	private Long reporterUserId;
	private String reporterName; // Maps the reporter's name
	private Long assigneeUserId;
	private String assigneeUserName; // Maps the assignee's name
	private Date startDate;
	private Date dueDate;
	private Date createdAt;
	private Date updatedAt;
	private boolean isEnable;
}
