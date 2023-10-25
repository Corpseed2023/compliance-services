package com.lawzoom.complianceservice.dto.complianceCategoryDto;

import lombok.*;
import org.hibernate.annotations.Comment;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ComplianceCategoryResponse {

    private Long id;

    private String title;

    private String categorySlug;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Comment(value = "1 : Active, 0 : Inactive")
    private boolean isEnable;

}
