package com.lawzoom.complianceservice.dto.companyDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotEmpty(message = "Business email is required")
    @Email(message = "Please provide a valid email address")
    private String businessEmailId;

    @NotNull(message = "Company type ID is required")
    private Long companyTypeId;

    @NotEmpty(message = "Company name is required")
    private String companyName;

    @NotNull(message = "Country ID is required")
    private Long countryId;

    @NotNull(message = "State ID is required")
    private Long stateId;

    @NotNull(message = "City ID is required")
    private Long cityId;

    private String registrationNumber;

    private LocalDate registrationDate;

    private String cinNumber;

    private String remarks;

    private String pinCode;

    private String panNumber;

    private Long turnover;

    @NotNull(message = "Location ID is required")
    private Long locatedAtId;

    @NotNull(message = "Business activity ID is required")
    private Long businessActivityId;

    @NotNull(message = "Industry ID is required")
    private Long industryId;

    private Long industrySubCategoryId;

    private boolean enable;

    private int permanentEmployee;

    private int contractEmployee;

    private String gstNumber;

    private String operationUnitAddress;

    @NotNull(message = "Subscriber ID is required")
    private Long subscriberId;
}
