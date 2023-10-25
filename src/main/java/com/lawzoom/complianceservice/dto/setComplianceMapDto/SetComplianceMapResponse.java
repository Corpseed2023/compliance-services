package com.lawzoom.complianceservice.dto.setComplianceMapDto;

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
public class SetComplianceMapResponse {

	private Long companyId;

	private String companyName;

	private Long businessUnitId;

	private String businessUnitCity;

	private String stateJurisdiction;

	private String businessActivity;

	private Long complianceCount;

	private String teamName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

}
