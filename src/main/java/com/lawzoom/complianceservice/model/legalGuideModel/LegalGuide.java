//package com.lawzoom.complianceservice.model.legalGuideModel;
//
//import com.lawzoom.complianceservice.model.complianceGuide.ComplianceGuide;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import lombok.*;
//import org.hibernate.annotations.Comment;
//import java.util.Date;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Builder
//@Entity
//@Table(name = "legal_guide")
//public class LegalGuide {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	@ManyToOne(targetEntity = ComplianceGuide.class)
//	@JoinColumn(name = "guide_id",nullable = false)
//	private ComplianceGuide complianceGuide;
//
//	@NotBlank(message = "Please enter title !!")
//	private String title;
//
//	@NotBlank(message = "Please enter refrence slug !!")
//	@Column(name="reference_slug")
//	private String referenceSlug;
//
//	@NotBlank(message = "Please enter summary !!")
//	@Column(name="description",columnDefinition = "LONGTEXT")
//	private String description;
//
//	@NotBlank(message = "Please enter department !!")
//	private String department;
//
//	@NotBlank(message = "Please enter authority !!")
//	private String authority;
//
//	@Column(name="publish_date")
//	@Temporal(TemporalType.DATE)
//	private Date publishDate;
//
//	@Temporal(TemporalType.DATE)
//	@Column(name="applicable_date")
//	private Date applicableDate;
//
//	@Column(name = "created_at")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date createdAt;
//
//	@Column(name = "updated_at")
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date updatedAt;
//
//	@Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
//	@Comment(value = "1 : Active, 0 : Inactive")
//	private boolean isEnable;
//
//}
