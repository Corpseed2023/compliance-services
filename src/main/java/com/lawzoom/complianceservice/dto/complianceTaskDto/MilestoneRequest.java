package com.lawzoom.complianceservice.dto.complianceTaskDto;

import com.lawzoom.complianceservice.dto.DocumentRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneRequest {

	@NotNull(message = "Business Unit ID is required")
	private Long businessUnitId;

	@NotNull(message = "Compliance ID is required")
	private Long complianceId;

	@NotEmpty(message = "Milestone name is required")
	private String mileStoneName;

	private String description;

	private int workStatus;

	private LocalDate startedDate;

	private LocalDate dueDate;

	private LocalDate issuedDate;

	private LocalDate expiryDate;

	private Long managerId;

	private Long assignee;

	private Long assignedBy;

	private String criticality;

	private Long status;

	private Long subscriberId;

	private String remark;

	private String comment;
	private String documentName;
	private String file;
	private String referenceNumber;
	private String remarks;

	/// need to save in renewal table

	private LocalDate renewalDate;

	private String reminderDurationType;

	private int reminderDurationValue;

	private String renewalNotes;

	private boolean notificationsEnabled = false;


}
