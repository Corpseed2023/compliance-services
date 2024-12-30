package com.lawzoom.complianceservice.dto.companyDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyTypeRequest {

    private String companyTypeName;
    private Date createdAt;
    private Date updatedAt;
    private boolean isEnable;
    private Long userId;

}