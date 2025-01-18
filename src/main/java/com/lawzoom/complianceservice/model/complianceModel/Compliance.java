	package com.lawzoom.complianceservice.model.complianceModel;

	import com.lawzoom.complianceservice.model.Status;
	import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
	import com.lawzoom.complianceservice.model.complianceMileStoneModel.MileStone;
	import com.lawzoom.complianceservice.model.user.Subscriber;
	import jakarta.persistence.*;
	import lombok.AllArgsConstructor;
	import lombok.Getter;
	import lombok.NoArgsConstructor;
	import lombok.Setter;
	import org.hibernate.annotations.Comment;

	import java.util.ArrayList;
	import java.util.Date;
	import java.util.List;
	@Entity
	@AllArgsConstructor
	@NoArgsConstructor
	@Getter
	@Setter
	@Table(name = "compliance")
	public class Compliance {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;

		private String issueAuthority;

		private String certificateType;

		@Column(name = "compliance_name")
		private String complianceName;

		@Column(name = "approval_state")
		private String approvalState;

		@Column(name = "applicable_zone")
		private String applicableZone;

		@Column(name = "created_at")
		@Temporal(TemporalType.TIMESTAMP)
		private Date createdAt = new Date();

		@Column(name = "updated_at")
		@Temporal(TemporalType.TIMESTAMP)
		private Date updatedAt = new Date();

		@Column(length = 1, name = "is_enable", columnDefinition = "tinyint(1) default 1")
		@Comment(value = "1 : Active, 0 : Inactive")
		private boolean isEnable = true;

		@Column(name = "is_deleted", columnDefinition = "tinyint(1) default 0")
		@Comment(value = "0 : Not Deleted, 1 : Deleted")
		private boolean isDeleted = false;

		@ManyToOne
		@JoinColumn(name = "business_unit_id", nullable = false)
		private BusinessUnit businessUnit;

		@Comment(value = "1 : Mandatory Compliance, 2: Optional Compliance")
		private int priority;

		@ManyToOne
		@JoinColumn(name = "subscriber_id", nullable = false)
		private Subscriber subscriber;

		@OneToMany(mappedBy = "compliance", cascade = CascadeType.ALL, orphanRemoval = true)
		private List<MileStone> milestones = new ArrayList<>();


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
