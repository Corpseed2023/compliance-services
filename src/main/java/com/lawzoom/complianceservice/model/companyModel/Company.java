package com.lawzoom.complianceservice.model.companyModel;

import com.lawzoom.complianceservice.model.businessActivityModel.BusinessActivity;
import com.lawzoom.complianceservice.model.businessActivityModel.IndustryCategory;
import com.lawzoom.complianceservice.model.businessActivityModel.IndustrySubCategory;
import com.lawzoom.complianceservice.model.companyModel.CompanyType;
import com.lawzoom.complianceservice.model.gstdetails.GstDetails;
import com.lawzoom.complianceservice.model.region.City;
import com.lawzoom.complianceservice.model.region.Country;
import com.lawzoom.complianceservice.model.region.LocatedAt;
import com.lawzoom.complianceservice.model.region.States;
import com.lawzoom.complianceservice.model.user.Designation;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
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
	private Long id;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@Column(name = "business_email_id", nullable = false)
	private String businessEmailId;

	@ManyToOne
	@JoinColumn(name = "company_type_id", nullable = false)
	private CompanyType companyType;

	@Column(name = "company_name", nullable = false, unique = true)
	private String companyName;

	@ManyToOne
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;

	@ManyToOne
	@JoinColumn(name = "state_id", nullable = false)
	private States state;

	@ManyToOne
	@JoinColumn(name = "city_id", nullable = false)
	private City city;

	@Column(name = "registration_number", unique = true)
	private String registrationNumber;

	private LocalDate registrationDate;

	@Column(name = "cin_number", unique = true)
	private String cinNumber;

	@Column(columnDefinition = "text")
	private String remarks;

	private String pinCode;

	@Column(name = "pan_number", unique = true)
	private String companyPanNumber;

	@Column(name = "turnover")
	private long turnover;

	@ManyToOne
	@JoinColumn(name = "located_at_id", nullable = false)
	private LocatedAt locatedAt;

	@ManyToOne
	@JoinColumn(name = "business_activity_id", nullable = false)
	private BusinessActivity businessActivity;

	@ManyToOne
	@JoinColumn(name = "industry_id", nullable = false)
	private IndustryCategory industryCategory;

	@ManyToOne
	@JoinColumn(name = "industry_sub_category_id", nullable = false)
	private IndustrySubCategory industrySubCategory;

	@Column(name = "permanent_employee")
	private int permanentEmployee;

	@Column(name = "contract_employee")
	private int contractEmployee;

	@Column(name = "operation_unit_address")
	private String operationUnitAddress;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, updatable = false)
	private Date createdAt = new Date();

	private LocalDate date;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false)
	private Date updatedAt = new Date();

	@Column(name = "is_enable", columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable = true;

	@Column(name = "is_deleted", columnDefinition = "tinyint(1) default 0")
	private boolean isDeleted = false;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<GstDetails> gstDetails = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "super_admin_id", nullable = false)
	private User superAdminId;

	@ManyToOne
	@JoinColumn(name = "subscriber_id", nullable = false)
	private Subscriber subscriber;

	public void disable() {
		this.isEnable = false;
	}

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
