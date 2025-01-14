package com.lawzoom.complianceservice.dto.complianceTaskDto;

import com.lawzoom.complianceservice.dto.DocumentRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

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
	private Long reporterId;

	private Long assignedTo;

	private Long assignedBy;

	private String assigneeMail;

	private LocalDate issuedDate;

	private String criticality;

	private Long status;

	private Long subscriberId;

	private String remark;

	// Reminder-specific fields
	private Date reminderDate;
	private Date reminderEndDate;
	private Integer notificationTimelineValue;
	private Integer repeatTimelineValue;
	private String repeatTimelineType;
	private Long whomToSend;


	// Document-specific field
	private List<DocumentRequest> documents;
}
