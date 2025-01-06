package com.lawzoom.complianceservice.dto.companyDto.companyType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyTypeRequest {

    private Long userId;
    private String companyTypeName;
}
