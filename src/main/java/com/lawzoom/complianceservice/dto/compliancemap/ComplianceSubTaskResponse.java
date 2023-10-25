package com.lawzoom.complianceservice.dto.compliancemap;

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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ComplianceSubTaskResponse {

	private Long id;
	
	@NotBlank
	@NotNull
	@NotEmpty
	private String subTaskName;
	
	@Min(value = 1)
	private int timelineValue;
	
	@NotBlank
	@NotNull
	@NotEmpty
	private String timelineType;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

	private String criticality;

}
