package com.lawzoom.complianceservice.dto.complianceDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ComplianceRequest {

	private Long id;

//	private Long createdBy;
	
	private String name;
	
	private String description;
	
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

	@JsonFormat(pattern="yyyy-MM-dd")
	private Date startDate;

	@JsonFormat(pattern="yyyy-MM-dd")
	private Date dueDate;

	@JsonFormat(pattern="yyyy-MM-dd")
	private Date completedDate;

	private String duration;

	@Comment(value = "0 : No Action ,1 : Apply Now, 2 : Already Done, 3 : Not Applicable")
	private int workStatus;

	@Comment(value="1 : Mandatory Compliance, 2: Optional Compliance")
	private int priority;

	private Long companyId;

	private Long businessUnitId;

	private Long teamMemberId;


}
