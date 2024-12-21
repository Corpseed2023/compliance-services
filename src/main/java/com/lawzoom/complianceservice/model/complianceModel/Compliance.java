package com.lawzoom.complianceservice.model.complianceModel;

import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
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
@Entity
@Table(name = "compliance")
public class Compliance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "compliance_name")
	private String complianceName;

	@Column(columnDefinition = "TEXT")
	private String description;
	
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

	private String duration;

	@Comment(value = "0 : No Action ,1 : Apply Now, 2 : Already Done, 3 : Not Applicable")
	private int workStatus;

	@Column(name="category_id")
	private Long categoryId;
	
	@Column(name ="company_id" )
	private Long companyId;

	@Column(name = "business_unit_id")
	private Long businessUnitId;

	@Column(name = "team_id")
	private Long teamId;

	@Column(name = "team_member_id")
	private Long teamMember;

	@Comment(value="1 : Mandatory Compliance, 2: Optional Compliance")
	private int priority;

	@Column(name="certificate_type")
	private String certificateType;

	@Column
	private String companyName;

	@Column
	private String teamName;

	@Column
	private String businessActivity;

	private String businessUnitAddress;

	private Long businessActivityId;

	@OneToMany(mappedBy = "compliance",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<ComplianceTask> complianceTasks=new ArrayList<>();

//	@OneToMany(mappedBy = "compliance",cascade = CascadeType.ALL,orphanRemoval = true)
//	private List<Document> complianceDocuments=new ArrayList<>();

	@OneToOne(mappedBy = "compliance",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
	private Reminder complianceReminder;



}
