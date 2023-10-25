package com.lawzoom.complianceservice.dto.documentDto;

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
public class DocumentResponse {

	private Long id;
	
	private String documentName;
	
	private String fileName;
	
	private Date issueDate;
	
	private String referenceNumber;
	
	private String remarks;

	private Date uploadDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;
}
