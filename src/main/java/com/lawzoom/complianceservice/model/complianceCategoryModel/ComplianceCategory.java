package com.lawzoom.complianceservice.model.complianceCategoryModel;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
//@Entity
//@Table(name = "compliance_category")
public class ComplianceCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	private String title;

	private String slug;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;
	
//	@OneToMany(mappedBy = "complianceCategory",cascade = CascadeType.ALL,orphanRemoval = true)
//	private List<Compliance> complianceList=new ArrayList<>();

}
