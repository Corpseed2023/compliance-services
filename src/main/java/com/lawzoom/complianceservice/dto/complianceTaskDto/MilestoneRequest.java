package com.lawzoom.complianceservice.dto.complianceTaskDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
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

	private String status;

	private Long subscriberId;

	private String remark;

	// Reminder-specific fields
	private Date reminderDate;
	private Date reminderEndDate;
	private Integer notificationTimelineValue;
	private Integer repeatTimelineValue;
	private String repeatTimelineType;
	private Long whomToSend; // User ID of the recipient, null defaults to Super Admin

	// Renewal-specific fields
	private LocalDate nextRenewalDate; // Next renewal date
	private Integer renewalFrequency;  // Renewal frequency in months
	private String renewalType;        // Renewal type (e.g., Half-Yearly, Yearly)
	private String renewalNotes;       // Notes or additional details about the renewal
}
