package com.lawzoom.complianceservice.dto.companyDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {

    private Long companyId;

    private Long userId;

    private Long companyTypeId;

    private String companyType;

    private String companyName;

    private String businessEmailId;

    private Long countryId;

    private String countryName;

    private Long stateId;

    private String companyState;

    private Long companyCityId;

    private String companyCity;

    private String companyRegistrationNumber;

    private LocalDate companyRegistrationDate;

    private String companyCINNumber;

    private String companyRemarks;

    private String pinCode;

    private String companyPanNumber;

    private long companyTurnover;

    private Long  locatedAtId;

    private String locatedAt;

    private Long businessActivityNameId;

    private String businessActivityName;

    private Long industryNameId;

    private String industryName;

    private Long industrySubCategoryNameId;

    private String industrySubCategoryName;

    private boolean isEnable;

    private int permanentEmployee;

    private int contractEmployee;

    private String operationUnitAddress;

    private Long stateCount;

    private Long gstDetailsCount;

    private Long businessUnitCount;



}
