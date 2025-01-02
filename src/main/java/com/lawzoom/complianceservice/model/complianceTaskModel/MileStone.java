package com.lawzoom.complianceservice.model.complianceTaskModel;

import com.lawzoom.complianceservice.model.businessActivityModel.BusinessActivity;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.companyModel.Company;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.Date;
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

	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;

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
