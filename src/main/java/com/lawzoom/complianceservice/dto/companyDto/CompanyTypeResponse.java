package com.lawzoom.complianceservice.dto.companyDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyTypeResponse {

    private Long id;
    private String companyTypeName;
    private Date createdAt;
    private Date updatedAt;
    private boolean isEnable;
    private Long userId;

    public CompanyTypeResponse(Long id, String companyTypeName) {

        this.id=id;
        this.companyTypeName=companyTypeName;
    }
}