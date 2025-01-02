package com.lawzoom.complianceservice.model.complianceModel;


import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.Date;


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

	private String issueAuthority;

	private String certificateType ;

	@Column(name = "compliance_name")
	private String complianceName;

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

	private LocalDate startDate;

	private LocalDate dueDate;

	private LocalDate completedDate;

	@Comment(value = "0 : No Action ,1 : Apply Now, 2 : Already Done, 3 : Not Applicable")
	private int workStatus;

	@ManyToOne
	@JoinColumn(name = "business_unit_id", nullable = false)
	private BusinessUnit businessUnit;

	@Comment(value="1 : Mandatory Compliance, 2: Optional Compliance")
	private int priority;


//	@OneToMany(mappedBy = "compliance",cascade = CascadeType.ALL,orphanRemoval = true)
//	private List<ComplianceTask> complianceTasks=new ArrayList<>();

//	@OneToMany(mappedBy = "compliance",cascade = CascadeType.ALL,orphanRemoval = true)
//	private List<Document> complianceDocuments=new ArrayList<>();

//	@OneToOne(mappedBy = "compliance",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
//	private Reminder complianceReminder;




}
