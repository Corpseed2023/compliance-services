package com.lawzoom.complianceservice.model.reminderModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawzoom.complianceservice.annotation.NotBeforeToday;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import jakarta.persistence.*;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "reminder")
public class Reminder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(targetEntity = Compliance.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "compliance_id")
	private Compliance compliance;

	@OneToOne(targetEntity = ComplianceTask.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "compliance_task_id")
	private ComplianceTask complianceTask;

	@OneToOne(targetEntity = ComplianceSubTask.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "compliance_sub_task_id")
	private ComplianceSubTask complianceSubTask;
	
	@Column(name = "reminder_date")
	@NotBeforeToday(message = "Please enter future date..!!")
	@JsonFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date reminderDate;

	@Column(name = "reminder_end_date")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date reminderEndDate;


	@Comment("It will record value of when will notification trigger how much day before")
	@Column(name = "notification_timeline_value")
	@Min(value = 1,message = "Minimum value should be 1")
	private int notificationTimelineValue;
	
//	@NotBlank
//	@NotEmpty
//	@NotNull
//	@Column(name ="notification_timeline_type" )
//	private String notificationTimelineType;
//

	@Comment("It will store value of when willl user will get notification like 1 year , 1 half yealy , daily , 1 montly something like that ")
	@Column(name = "repeat_timeline_value")
	@Min(value = 0,message = "Value should not be -ve.")
	private int repeatTimelineValue;

	@Comment("type can be yealry, daily, hald yearley , monthly, quaterly like that ")
	@Column(name ="repeat_timeline_type" )
	private String repeatTimelineType;
//
//	@Comment("On which day they want reminder like monday ,tuesday, wednesday ")
//	@Column(name ="repeat_on_day" )
//	private String repeatOnDay;
//


	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date updatedAt;

	@Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;
}
