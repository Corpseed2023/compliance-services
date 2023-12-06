package com.lawzoom.complianceservice.model.complianceModel;

import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "compliance")
public class Compliance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(name = "user_id")
	private Long userId;

	@Column(name = "compliance_name")
	private String name;

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

//	@OneToMany(mappedBy = "compliance",cascade = CascadeType.ALL,orphanRemoval = true)
//	private List<ComplianceTask> complianceTasks=new ArrayList<>();

//	@OneToMany(mappedBy = "compliance",cascade = CascadeType.ALL,orphanRemoval = true)
//	private List<Document> complianceDocuments=new ArrayList<>();

	@OneToOne(mappedBy = "compliance",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
	private Reminder complianceReminder;


	@Override
	public String toString() {
		return "Compliance{" +
				"id=" + id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", approvalState='" + approvalState + '\'' +
				", applicableZone='" + applicableZone + '\'' +
				", createdAt=" + createdAt +
				", updatedAt=" + updatedAt +
				", isEnable=" + isEnable +
				", startDate=" + startDate +
				", dueDate=" + dueDate +
				", completedDate=" + completedDate +
				", duration='" + duration + '\'' +
				", workStatus=" + workStatus +
				", categoryId=" + categoryId +
				", companyId=" + companyId +
				", businessUnitId=" + businessUnitId +
				", priority=" + priority +
//				", complianceTasks=" + complianceTasks +
				", complianceReminder=" + complianceReminder +
				'}';
	}
}
