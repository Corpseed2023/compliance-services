package com.lawzoom.complianceservice.dto.companyResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponse {

    private Long companyId;

    private String companyType;

    private String companyName;

    private String companyState;

    private String companyCity;

    private String companyRegistrationNumber;

    @Temporal(TemporalType.DATE)
    private LocalDate companyRegistrationDate;

    private String companyCINNumber;

    private String companyActiveStatus;

    private String companyRemarks;

    private String companyPinCode;

    private String companyAddress;

    private Long companyTurnover;

    private String locatedAt;

    private String businessActivity;

    private int permanentEmployee;

    private int contractEmployee;

    private List<Integer> gstListId;

    @Column(name = "created_at",insertable = false,updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
    @Comment(value = "1 : Active, 0 : Inactive")
    private boolean isEnable;
}
