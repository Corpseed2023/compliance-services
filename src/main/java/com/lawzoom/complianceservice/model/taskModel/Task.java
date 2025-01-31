package com.lawzoom.complianceservice.model.taskModel;

import com.lawzoom.complianceservice.model.Status;
import com.lawzoom.complianceservice.model.comments.TaskComments;
import com.lawzoom.complianceservice.model.documentModel.Document;
import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.reminderModel.TaskReminder;
import com.lawzoom.complianceservice.model.renewalModel.TaskRenewal;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "status_id", nullable = false)
	private Status status;

	@Column(name = "created_at", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(length = 1, name = "is_enable", columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable = true;

	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private LocalDate startDate;

	@Column(name = "due_date")
	@Temporal(TemporalType.DATE)
	private LocalDate dueDate;

	@Column(name = "completed_date")
	@Temporal(TemporalType.DATE)
	private LocalDate completedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id", nullable = true)
	private User manager;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assignee_id", nullable = true)
	private User assignee;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assigned_by", nullable = true)
	private User assignedBy;



	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_user_id", nullable = true)
	private User createdByUser;

	private String criticality;

	private String remark;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "milestone_id", nullable = false)
	private MileStone milestone;

	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Document> documents = new ArrayList<>();

	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TaskComments> comments = new ArrayList<>();

	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TaskReminder> taskReminders = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "subscriber_id", nullable = false)
	private Subscriber subscriber;


	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
