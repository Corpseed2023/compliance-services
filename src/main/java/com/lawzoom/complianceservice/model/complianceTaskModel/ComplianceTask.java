package com.lawzoom.complianceservice.model.complianceTaskModel;

import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
import com.lawzoom.complianceservice.model.documentModel.Document;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.model.renewalModel.RenewalReminder;
import com.lawzoom.complianceservice.model.taskActionModel.TaskAction;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
	
//	@NotBlank
//	@NotNull
//	@NotEmpty
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
	private Long reporterUserId;
	
	@Column(name = "assignee_id")
	private Long assigneeUserId;

	private String criticality;

	@ManyToOne(targetEntity = Compliance.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "compliance_id",nullable = false)
	private Compliance compliance;

	@OneToMany(mappedBy = "complianceTask",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<ComplianceSubTask> complianceSubTasks=new ArrayList<>();

//	@OneToMany(mappedBy = "complianceTask",cascade = CascadeType.ALL,orphanRemoval = true)
//	private List<TaskAction> taskActionList=new ArrayList<>();

//	@OneToMany(mappedBy = "compliance",cascade = CascadeType.ALL,orphanRemoval = true)
//	private List<Document> complianceDocuments=new ArrayList<>();

//	@OneToMany(mappedBy = "complianceTask", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Document> complianceDocuments = new ArrayList<>();


//	@OneToOne(mappedBy = "complianceTask",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
//	private Reminder complianceReminder;

//	@OneToOne(mappedBy = "complianceTask",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
//	private RenewalReminder complianceRenewal;

	@Override
	public String toString() {
		return "ComplianceTask{" +
				"id=" + id +
				", taskName='" + taskName + '\'' +
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
				", reporterUserId=" + reporterUserId +
				", assigneeUserId=" + assigneeUserId +
				", criticality='" + criticality + '\'' +
				", complianceSubTasks=" + complianceSubTasks +
				'}';
	}
}
