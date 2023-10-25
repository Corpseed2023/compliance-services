package com.lawzoom.complianceservice.dto.compliancemap;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.hibernate.annotations.Comment;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ComplianceMasterResponse {

    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    private String title;

    @NotNull
    @NotBlank
    @NotEmpty
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Comment(value = "1 : Active, 0 : Inactive")
    private boolean isEnable;

    private String duration;

    private String businessActivity;

    @Comment(value="1 : Mandatory Compliance, 2: Optional Compliance")
    private int priority;

    private Long categoryId;

	private List<ComplianceTaskResponse> complianceTaskResponseList=new ArrayList<>();

}
