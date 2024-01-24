package com.lawzoom.complianceservice.model.complianceTaskModel;

import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.documentModel.Document;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "compliance_task")
public class ComplianceTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "task_name")
	private String taskName;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(name = "timeline_value")
	private int timelineValue;
	
	@Column(name = "timeline_type")
	private String timelineType;
	
	private String status;
	
	@Column(name = "approval_state")
	private String approvalState;
	
	@Column(name = "applicable_zone")
	private String applicableZone;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;
	
	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;
	
	@Column(name = "due_date")
	@Temporal(TemporalType.DATE)
	private Date dueDate;
	
	@Column(name = "completed_date")
	@Temporal(TemporalType.DATE)
	private Date completedDate;
	
	@Column(name = "reporter_id")
	private Long   taskReporter;
	
	@Column(name = "assignee_to")
	private Long   assignedTo;

	@Column(name = "assigned_By")
	private Long   assignedBy ;

	@Column(name = "assignee_mail")
	private String assigneeMail;

	@Column(name = "task_created_by")
	private Long taskCreatedBy;

	private String criticality;

	private Long companyId;

	private Long businessUnitId;

	private Long businessActivityId;

	private Long userId;

	@ManyToOne(targetEntity = Compliance.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "compliance_id",nullable = false)
	private Compliance compliance;

	@OneToMany(mappedBy = "complianceTask",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<ComplianceSubTask> complianceSubTasks=new ArrayList<>();

	@OneToMany(mappedBy = "complianceTask", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Document> complianceDocuments = new ArrayList<>();

//	@OneToMany(mappedBy = "complianceTask",cascade = CascadeType.ALL,orphanRemoval = true)
//	private List<TaskAction> taskActionList=new ArrayList<>();

	@OneToOne(mappedBy = "complianceTask",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
	private Reminder complianceTaskReminder;

//	@OneToOne(mappedBy = "complianceTask",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
//	private RenewalReminder complianceRenewal;


	@Override
	public String toString() {
		return "ComplianceTask{" +
				"id=" + id +
				", taskName='" + taskName + '\'' +
				", description='" + description + '\'' +
				", timelineValue=" + timelineValue +
				", timelineType='" + timelineType + '\'' +
				", status='" + status + '\'' +
				", approvalState='" + approvalState + '\'' +
				", applicableZone='" + applicableZone + '\'' +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				", isEnable=" + isEnable +
				", startDate=" + startDate +
				", dueDate=" + dueDate +
				", completedDate=" + completedDate +
				", taskReporter=" + taskReporter +
				", assignedTo=" + assignedTo +
				", assignedBy=" + assignedBy +
				", assigneeMail='" + assigneeMail + '\'' +
				", criticality='" + criticality + '\'' +
				", companyId=" + companyId +
				", businessUnitId=" + businessUnitId +
				", businessActivityId=" + businessActivityId +
				", userId=" + userId +
				", compliance=" + compliance +
				", complianceSubTasks=" + complianceSubTasks +
				", complianceDocuments=" + complianceDocuments +
				", complianceTaskReminder=" + complianceTaskReminder +
				'}';
	}
}
