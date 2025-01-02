//package com.lawzoom.complianceservice.model.renewalModel;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
//import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.Comment;
//import jakarta.persistence.*;
//import java.util.Date;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Table(name = "renewal_reminder")
//public class RenewalReminder {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	@OneToOne(targetEntity = ComplianceTask.class,fetch = FetchType.LAZY)
//	@JoinColumn(name = "compliance_task_id")
//	private ComplianceTask complianceTask;
//
//	@OneToOne(targetEntity = ComplianceSubTask.class,fetch = FetchType.LAZY)
//	@JoinColumn(name = "compliance_sub_task_id")
//	private ComplianceSubTask complianceSubTask;
//
//	@Column(name = "certificate_issue_date")
//	@JsonFormat(pattern="yyyy-MM-dd")
//	@Temporal(TemporalType.DATE)
//	private Date certificateIssueDate;
//
//	@Column(name = "certificate_issue_for_value")
//	@Min(value = 1,message = "Minimum value should be 1")
//	private int certificateIssueForValue;
//
//	@NotNull
//	@NotBlank
//	@NotEmpty
//	@Column(name = "certificate_issue_for_type")
//	private String certificateIssueForType;
//
//	@Column(name = "notification_timeline_value")
//	@Min(value = 1,message = "Minimum value should be 1")
//	private int notificationTimelineValue;
//
//	@NotBlank
//	@NotEmpty
//	@NotNull
//	@Column(name ="notification_timeline_type" )
//	private String notificationTimelineType;
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
//}
