package com.lawzoom.complianceservice.dto.complianceSubTaskDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import lombok.*;
import org.hibernate.annotations.Comment;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ComplianceSubTaskRequest {

	private Long id;
	
	@NotBlank
	@NotNull
	@NotEmpty
	private String subTaskName;

	@NotBlank
	@NotNull
	@NotEmpty
	private String description;
	
	@Min(value = 1)
	private int timelineValue;
	
	@NotBlank
	@NotNull
	@NotEmpty
	private String timelineType;
	
	private String status;

	private String approvalState;

	private String applicableZone;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;
	
	@Column(name = "start_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	
	@Column(name = "due_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dueDate;
	
	@Column(name = "completed_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date completedDate;
	
	@Column(name = "reporter_id")
	private Long reporterUserId;
	
	@Column(name = "assignee_id")
	private Long assigneeUserId;

	private String criticality;


}
