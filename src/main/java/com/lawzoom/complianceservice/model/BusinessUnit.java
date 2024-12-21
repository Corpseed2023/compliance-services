package com.lawzoom.complianceservice.model;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;


import java.time.LocalDate;
import java.util.Date;


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

	private Long state;

	private Long city;

	private Long  businessActivity;

	private Long locatedAt;

	private Date createdAt;

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
	@JoinColumn(name = "subscription_id", nullable = false)
	private Subscription subscription;

	@ManyToOne
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

	@ManyToOne
	@JoinColumn(name = "update_by")
	private User updatedBy;



}
