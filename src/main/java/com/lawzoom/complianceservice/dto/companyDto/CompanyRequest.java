package com.lawzoom.complianceservice.dto.companyDto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {


    @Email(message = "Please provide a valid email address")
    private String businessEmailId;

    private long companyTypeId;

    private String companyName;

    private Long countryId;

    private long companyStateId;

    private long companyCityId;

    private String companyRegistrationNumber;

    private LocalDate companyRegistrationDate;

    private String companyCINNumber;

    private String companyRemarks;

    private String pinCode;

    private String companyPanNumber;

    private String companyAddress;

    private Long companyTurnover;

    private Long locatedAtId;

    private Long businessActivityId;

    private Long industryId;

    private Long industrySubCategoryId;

    private boolean isEnable;

    private int permanentEmployee;

    private int contractEmployee;

    private String gstNumber;

    private String operationUnitAddress;

    private Long subscriptionId;


}
