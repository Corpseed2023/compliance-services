package com.lawzoom.complianceservice.dto.reminderDto;

import lombok.*;
import org.hibernate.annotations.Comment;


import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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
