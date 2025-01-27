package com.lawzoom.complianceservice.model.mileStoneModel;

import com.lawzoom.complianceservice.model.comments.MileStoneComments;
import com.lawzoom.complianceservice.model.Status;
import com.lawzoom.complianceservice.model.businessActivityModel.BusinessActivity;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.documentModel.Document;
import com.lawzoom.complianceservice.model.reminderModel.Reminder;
import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.model.taskModel.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


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

	@NotNull
	@Size(max = 255)
	private String mileStoneName;

	@Column(columnDefinition = "TEXT")
	private String description;

	private LocalDate startedDate;

	private LocalDate dueDate;

	private LocalDate completedDate;

	private LocalDate issuedDate;

	private LocalDate expiryDate;

	@OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<MileStoneComments> mileStoneComments = new ArrayList<>();

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private Date createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(length = 1, name = "is_enable", columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable = true;

	@ManyToOne
	@JoinColumn(name = "manager_id")
	private User manager;

	@ManyToOne
	@JoinColumn(name = "assigned_id")
	private User assigned;

	@ManyToOne
	@JoinColumn(name = "assigned_by")
	private User assignedBy;

	private String criticality;

	private String remark;

	@ManyToOne
	@JoinColumn(name = "status_id")
	private Status status;

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

	@OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reminder> reminders = new ArrayList<>();

	@OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Renewal> renewals = new ArrayList<>();

	@OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> tasks = new ArrayList<>();

	@OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Document> documents = new ArrayList<>();

	@OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DocumentRequired> documentRequiredList = new ArrayList<>();


}

