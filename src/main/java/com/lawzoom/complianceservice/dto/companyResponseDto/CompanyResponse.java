package com.lawzoom.complianceservice.dto.companyResponseDto;


import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponse {

    private Long companyId;

    private Long userId;

    private String companyType;

    private String companyName;

    private String firstName;

    private String lastName;

    private String businessEmailId;

    private String designation;

    private String companyState;

    private String companyCity;

    private String companyRegistrationNumber;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date companyRegistrationDate;

    private String companyCINNumber;

    private String companyRemarks;

    private String companyPinCode;

    private String companyAddress;

    private long companyTurnover;

    private String locatedAt;

    private String businessActivityName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private boolean isEnable;

    private int permanentEmployee;

    private int contractEmployee;

    private String gstNumber;

    private String operationUnitAddress;
}
