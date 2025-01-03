package com.lawzoom.complianceservice.dto.complianceTaskDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MilestoneRequest {
	@NotEmpty(message = "Milestone name is required")
	private String mileStoneName;

	private String description;

	@NotNull(message = "Compliance ID is required")
	private Long complianceId;

	@NotNull(message = "Business Unit ID is required")
	private Long businessUnitId;

	@NotNull(message = "Reporter ID is required")
	private Long reporterId; // User ID of the reporter

	private Long assignedTo; // User ID of the assignee

	private Long assignedBy; // User ID of the assigner

	private String assigneeMail;

	private LocalDate issuedDate;

	private String criticality;

	private String status; // Status of the milestone
}
