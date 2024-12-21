package com.lawzoom.complianceservice.model;



import com.lawzoom.complianceservice.model.teamMemberModel.TeamMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@Column(name = "business_email_id", nullable = false)
	private String businessEmailId;

	private Long companyType;

	@Column(name = "companyName")
	private String companyName;

	private Long country;

	private Long state;

	private Long city;

	@Column(name = "registration_number")
	private String registrationNumber;

	private LocalDate registrationDate;

	@Column(name = "cin_number")
	private String cinNumber;

	@Column(columnDefinition = "text", name = "remarks")
	private String remarks;

	private String pinCode;

	private String companyPanNumber;

	@Column(name = "turnover")
	private long turnover;

	private Long locatedAt;

	private Long businessActivity;

	private Long industryCategory;

	private Long industrySubCategory;

	private int permanentEmployee;

	private int contractEmployee;

	private String operationUnitAddress;

	private Date createdAt;

	private LocalDate date;

	private Date updatedAt;

	@Column(length = 1, name = "is_enable", columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable = true;

	@Column(name = "is_deleted", columnDefinition = "tinyint(1) default 0")
	private boolean isDeleted = false;

	// One company can have multiple GST details
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GstDetails> gstDetails;

	@ManyToOne
	@JoinColumn(name = "super_admin_id", nullable = false)
	private User superAdminId; // Each company is associated with one user

	@ManyToOne
	@JoinColumn(name = "subscription_id", nullable = false)
	private Subscription subscription;

	@ManyToMany
	@JoinTable(
			name = "company_team_members",
			joinColumns = @JoinColumn(name = "company_id"),
			inverseJoinColumns = @JoinColumn(name = "team_member_id")
	)
	private Set<TeamMember> teamMembers = new HashSet<>();


	}
