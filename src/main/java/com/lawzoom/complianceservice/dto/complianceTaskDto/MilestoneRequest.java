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

	private int durationMonth;

	private int durationYear;

	private Long managerId;

	private Long assignee;

	private Long assignedBy;

	private String assigneeMail;

	private String criticality;

	private Long status;

	private Long subscriberId;

	private String remark;

	private Date reminderDate;
	private Date reminderEndDate;
	private Integer notificationTimelineValue;
	private Integer repeatTimelineValue;
	private String repeatTimelineType;
	private Long whomToSend;

	private LocalDate nextRenewalDate;
	private int renewalFrequency;
	private String renewalType;
	private String renewalNotes;

	private List<DocumentRequest> documents;



}
