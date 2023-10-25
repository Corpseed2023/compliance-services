package com.lawzoom.complianceservice.dto.reminderResponse;

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
public class ReminderResponse {

	private Long id;
	
	private Date reminderDate;
	
	private int notificationTimelineValue;
	
	private String notificationTimelineType;
	
	private int repeatTimelineValue;
	
	private String repeatTimelineType;
	
	private String repeatOnDay;
	
	private Date reminderEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

}
