//package com.lawzoom.complianceservice.model.complianceSubTaskModel;
//
//import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
//import com.lawzoom.complianceservice.model.reminderModel.Reminder;
//import lombok.*;
//import org.hibernate.annotations.Comment;
//import jakarta.persistence.*;
//import java.util.Date;
//
//
//
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Builder
//@Entity
//@Table(name = "compliance_sub_task")
//public class ComplianceSubTask {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//
//	@Column(name = "sub_task_name")
//	private String subTaskName;
//
//	@Column(columnDefinition = "TEXT")
//	private String description;
//
//	@Column(name = "timeline_value")
//	private int timelineValue;
//
//	@Column(name = "timeline_type")
//	private String timelineType;
//
//	private String status;
//
//	@Column(name = "approval_state")
//	private String approvalState;
//
//	@Column(name = "applicable_zone")
//	private String applicableZone;
//
//	@Column(name = "created_at")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date createdAt;
//
//	@Column(name = "updated_at")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date updatedAt;
//
//	@Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
//	@Comment(value = "1 : Active, 0 : Inactive")
//	private boolean isEnable;
//
//	@Column(name = "start_date")
//	@Temporal(TemporalType.DATE)
//	private Date startDate;
//
//	@Column(name = "due_date")
//	@Temporal(TemporalType.DATE)
//	private Date dueDate;
//
//	@Column(name = "completed_date")
//	@Temporal(TemporalType.DATE)
//	private Date completedDate;
//
//	@Column(name = "reporter_id")
//	private Long reporterUserId;
//
//	@Column(name = "assignee_id")
//	private Long assigneeUserId;
//
//	private String criticality;
//
//	@ManyToOne(targetEntity = ComplianceTask.class,fetch = FetchType.LAZY)
//	@JoinColumn(name = "compliance_task_id",nullable = false)
//	private ComplianceTask complianceTask;
//
////	@OneToMany(mappedBy = "complianceSubTask",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
////	private List<Document> complianceDocuments=new ArrayList<>();
////
////	@OneToMany(mappedBy = "complianceSubTask",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
////	private List<TaskAction> taskActionList=new ArrayList<>();
//
////	@OneToOne(mappedBy = "complianceSubTask",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
////	private Reminder Reminder;
//
////	@OneToOne(mappedBy = "complianceSubTask",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
////	private RenewalReminder complianceRenewal;
//
//
//	@Override
//	public String toString() {
//		return "ComplianceSubTask{" +
//				"id=" + id +
//				", subTaskName='" + subTaskName + '\'' +
//				", description='" + description + '\'' +
//				", timelineValue=" + timelineValue +
//				", timelineType='" + timelineType + '\'' +
//				", status='" + status + '\'' +
//				", approvalState='" + approvalState + '\'' +
//				", applicableZone='" + applicableZone + '\'' +
//				", createdAt=" + createdAt +
//				", updatedAt=" + updatedAt +
//				", isEnable=" + isEnable +
//				", startDate=" + startDate +
//				", dueDate=" + dueDate +
//				", completedDate=" + completedDate +
//				", reporterUserId=" + reporterUserId +
//				", assigneeUserId=" + assigneeUserId +
//				", criticality='" + criticality + '\'' +
//				", complianceTask=" + complianceTask +
////				", Reminder=" + Reminder +
//				'}';
//	}
//}
