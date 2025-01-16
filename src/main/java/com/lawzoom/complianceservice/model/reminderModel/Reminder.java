package com.lawzoom.complianceservice.model.reminderModel;

import com.lawzoom.complianceservice.model.complianceMileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.Comment;
import  com.lawzoom.complianceservice.model.mileStoneTask.Task;


import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reminder")
public class Reminder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "compliance_id", nullable = true)
	private Compliance compliance;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "milestone_id", nullable = true)
	private MileStone milestone;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subscriber_id", nullable = false)
	private Subscriber subscriber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "super_admin_id", nullable = false)
	private User superAdmin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "whom_to_send", nullable = false)
	private User whomToSend;

	@Column(name = "reminder_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date reminderDate;

	@Column(name = "reminder_end_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date reminderEndDate;

	@Comment("Notification trigger timeline in days before the due date")
	@Column(name = "notification_timeline_value", nullable = false)
	private int notificationTimelineValue;

	@Comment("Number of intervals between repeated notifications")
	@Column(name = "repeat_timeline_value", nullable = false)
	@Min(value = 0, message = "Value should not be negative.")
	private int repeatTimelineValue;

	@Comment("Type of repeat timeline: daily, weekly, etc.")
	@Column(name = "repeat_timeline_type", nullable = false)
	private String repeatTimelineType;

	// 1 means active and 0 means stopped
	private int stopFlag=1;

	@Column(name = "created_at", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = new Date();

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt = new Date();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id", nullable = true)
	private Task task;


	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
