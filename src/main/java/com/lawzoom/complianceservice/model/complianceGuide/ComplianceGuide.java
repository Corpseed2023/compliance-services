//package com.lawzoom.complianceservice.model.complianceGuide;
//
//import com.lawzoom.complianceservice.model.complianceSubTaskModel.ComplianceSubTask;
//import com.lawzoom.complianceservice.model.complianceTaskModel.ComplianceTask;
//import com.lawzoom.complianceservice.model.legalGuideModel.LegalGuide;
//import lombok.*;
//import org.hibernate.annotations.Comment;
//import jakarta.persistence.*;
//import java.util.Date;
//import java.util.ArrayList;
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Builder
//
//@Entity
//@Table(name = "compliance_guide")
//public class ComplianceGuide {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//
//	@ManyToOne(targetEntity = ComplianceTask.class,fetch = FetchType.LAZY)
//	@JoinColumn(name = "task_id")
//	private ComplianceTask complianceTask;
//
//	@ManyToOne(targetEntity = ComplianceSubTask.class,fetch = FetchType.LAZY)
//	@JoinColumn(name = "sub_task_id")
//	private ComplianceSubTask complianceSubTask;
//
//	@Column(name = "jurisdiction")
//	private String jurisdiction;
//
//	@Column(name = "description",columnDefinition = "LONGTEXT")
//	private String description;
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
//	@OneToMany(mappedBy = "complianceGuide",cascade = CascadeType.ALL,orphanRemoval = true)
//	List<LegalGuide> legalGuides=new ArrayList<>();
//
//}
