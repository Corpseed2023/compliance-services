package com.lawzoom.complianceservice.model.complianceMileStoneModel;

import com.lawzoom.complianceservice.model.businessActivityModel.BusinessActivity;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.model.mileStoneTask.Task;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "milestone")
public class MileStone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String mileStoneName;

	@Column(columnDefinition = "TEXT")
	private String description;

	private String status;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = new Date();

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt = new Date();

	@Column(length = 1, name = "is_enable", columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable = true;

	@ManyToOne
	@JoinColumn(name = "reporter_id")
	private User taskReporter;

	@ManyToOne
	@JoinColumn(name = "assigned_to")
	private User assignedTo;

	@ManyToOne
	@JoinColumn(name = "assigned_by")
	private User assignedBy;

	@Column(name = "assignee_mail")
	private String assigneeMail;

	@ManyToOne
	@JoinColumn(name = "task_created_by")
	private User taskCreatedBy;

	private LocalDate issuedDate;

	private String criticality;

	private String remark;

	@ManyToOne
	@JoinColumn(name = "business_unit_id", nullable = false)
	private BusinessUnit businessUnit;

	@ManyToOne
	@JoinColumn(name = "business_activity_id")
	private BusinessActivity businessActivity;

	@ManyToOne
	@JoinColumn(name = "compliance_id", nullable = false)
	private Compliance compliance;

	@ManyToOne
	@JoinColumn(name = "subscriber_id", nullable = false)
	private Subscriber subscriber;

	// Multiple Reminders
	@OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reminder> reminders = new ArrayList<>();

	// Multiple Renewals
	@OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Renewal> renewals = new ArrayList<>();

	// Multiple Tasks
	@OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> tasks = new ArrayList<>();

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
