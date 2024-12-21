package com.lawzoom.complianceservice.model.teamMemberModel;


import com.lawzoom.complianceservice.model.Company;
import com.lawzoom.complianceservice.model.Roles;
import com.lawzoom.complianceservice.model.Subscription;
import com.lawzoom.complianceservice.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class TeamMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank
	private String memberName;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Roles roles;

	@NotBlank
	private String memberMail;

	private String memberMobile;

	private String typeOfResource;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

	private boolean isDeleted=false;

	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "reporting_manager_id")
	private User reportingManager;

	@ManyToOne
	@JoinColumn(name = "subscription_id")
	private Subscription subscription;

	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createdBy;

	@ManyToOne
	@JoinColumn(name = "super_admin_id")
	private User superAdminId;


	@ManyToMany(mappedBy = "teamMembers")
	private List<Company> companies = new ArrayList<>();


}
