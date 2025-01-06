package com.lawzoom.complianceservice.dto.companyResponseDto;

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

    private Long companyId; // Company ID

    private Long subscriberId; // Subscriber ID

    private String subscriberName; // Full name of the subscriber's super admin

    private String companyType; // Type of the company

    private String companyName; // Name of the company

    private String businessEmailId; // Business email ID

    private String companyState; // State of the company

    private String companyCity; // City of the company

    private String companyRegistrationNumber; // Registration number of the company

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date companyRegistrationDate; // Registration date of the company

    private String companyCINNumber; // CIN (Corporate Identification Number)

    private String companyRemarks; // Remarks about the company

    private String companyPinCode; // PIN code of the company's location

    private String operationUnitAddress; // Address of the operation unit

    private long companyTurnover; // Turnover of the company

    private String locatedAt; // Location details

    private String businessActivityName; // Name of the business activity

    private String industryCategoryName; // Name of the industry category

    private String industrySubCategoryName; // Name of the industry sub-category (if any)

    private String companyPanNumber; // PAN number of the company

    private int permanentEmployee; // Number of permanent employees

    private int contractEmployee; // Number of contract employees

    private Date createdAt; // Creation timestamp

    private Date updatedAt; // Last updated timestamp

    private boolean isEnable; // Whether the company is enabled

    private boolean isDeleted; // Whether the company is deleted

    private String superAdminName; // Name of the super admin associated with the company

    private String countryName; // Country of the company

    private String gstNumber; // GST number associated with the company
}
