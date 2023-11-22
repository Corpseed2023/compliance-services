package com.lawzoom.complianceservice.dto.complianceDto;

import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ComplianceResponse {

	private Long id;
	
	private String name;
	
	private String description;
	
	private String approvalState;
	
	private String applicableZone;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dueDate;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date completedDate;

	private String duration;

	@Comment(value = "0 : No Action ,1 : Apply Now, 2 : Already Done, 3 : Not Applicable")
	private int workStatus;

	@Comment(value="1 : Mandatory Compliance, 2: Optional Compliance")
	private int priority;

	private Long businessUnitId;

	private Long companyId;

	private Long teamId;
}
