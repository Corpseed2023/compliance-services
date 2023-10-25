package com.lawzoom.complianceservice.dto.complianceGuideDto;

import lombok.*;
import org.hibernate.annotations.Comment;

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
public class ComplianceGuideResponse {

	private Long id;
	
	private String jurisdiction;
	
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

}
