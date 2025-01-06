package com.lawzoom.complianceservice.dto.companyDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
/*This class is for fetching details of company , business unit and count of compliance */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyBusinessUnitDto {
    private Long companyId;
    private String companyName;
    private Long businessUnitId;
    private String businessUnitAddress;
    private String businessActivityName;
    private List<TotalComplianceDto> totalCompliance;
    private Date lastUpdated;
}