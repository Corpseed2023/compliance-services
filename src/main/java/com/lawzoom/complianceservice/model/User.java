package com.lawzoom.complianceservice.model;

import com.lawzoom.complianceservice.model.companyModel.Company;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userName;

	@NotEmpty(message = "Email must not be empty")
	private String email;

	private String mobile;

	@Size(min = 6, message = "Password length should be minimum 6.")
	@NotNull(message = "Password cannot be null!")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Roles> roles = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_subscription",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "subscription_id")
	)
	private Set<Subscription> subscriptions = new HashSet<>();


	@OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Company> companies = new ArrayList<>();

	@Column(name = "is_enable", columnDefinition = "tinyint(1) default 1")
	private boolean isEnable = true;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt = new Date();

	private LocalDate date;

	private boolean isAssociated;

	private boolean isDeleted = false;

	@ManyToOne
	@JoinColumn(name = "super_admin_id")
	private User superAdminId;

}

