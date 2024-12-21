package com.lawzoom.complianceservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Roles> roles = new HashSet<>();

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Subscription subscription;

	@OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Company> companies = new ArrayList<>();

	private Long resourceType;

	private Long designation;

	@Column(name = "is_enable", columnDefinition = "tinyint(1) default 1")
	private boolean isEnable = true;

	private Date createdAt = new Date();

	private Date updatedAt = new Date();

	private LocalDate date;

	private boolean isAssociated;

	private boolean isDeleted=false;

	@ManyToOne
	@JoinColumn(name = "super_admin_id")
	private User superAdminId;


}

