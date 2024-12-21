package com.lawzoom.complianceservice.dto.userDto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class  UserRequest {

    private Long id;

    private String userName;

    private String email;

    @Size(min = 10,max = 13,message = "Mobile length should be 10 to 13 digits..")
    private String mobile;

    private Long departmentId;

    private Long designationId;

    private Long resourceTypeId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Comment(value = "1 : Active, 0 : Inactive")
    private boolean isEnable;

    private Long roleId;

    private boolean isAssociated;

    private Long companyId;

    private Long subscriptionId;

}