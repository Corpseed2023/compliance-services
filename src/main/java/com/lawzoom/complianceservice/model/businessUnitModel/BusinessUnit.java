package com.lawzoom.complianceservice.model.businessUnitModel;

import com.lawzoom.complianceservice.model.businessActivityModel.BusinessActivity;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.model.gstdetails.GstDetails;
import com.lawzoom.complianceservice.model.region.City;
import com.lawzoom.complianceservice.model.region.LocatedAt;
import com.lawzoom.complianceservice.model.region.States;
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
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "business_unit")
public class BusinessUnit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "gst_details_id", nullable = false)
	private GstDetails gstDetails;

	@Column(name = "address", columnDefinition = "TINYTEXT")
	private String address;

	@ManyToOne
	@JoinColumn(name = "state_id")
	private States state;

	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;

	@ManyToOne
	@JoinColumn(name = "business_activity_id")
	private BusinessActivity businessActivity;


	@ManyToOne
	@JoinColumn(name = "located_at_id")
	private LocatedAt locatedAt;


	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(length = 1, name = "is_enable", columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable = true;

	@Column(name = "is_deleted", columnDefinition = "tinyint(1) default 0")
	private boolean isDeleted = false;

	private LocalDate date;

	@Column(name = "gst_number")
	private String gstNumber;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@ManyToOne
	@JoinColumn(name = "update_by")
	private User updatedBy;

	@OneToMany(mappedBy = "businessUnit", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Compliance> compliance = new ArrayList<>();





}
