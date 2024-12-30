package com.lawzoom.complianceservice.service.companyService;





import com.lawzoom.complianceservice.dto.companyDto.CompanyTypeRequest;
import com.lawzoom.complianceservice.dto.companyDto.CompanyTypeResponse;

import java.util.List;

public interface CompanyServiceType {

    CompanyTypeResponse createCompanyType(String companyTypeName, Long userId);


    CompanyTypeResponse updateCompanyType(Long id, CompanyTypeRequest companyTypeRequest);

    void softDeleteCompanyType(Long id);

    List<CompanyTypeResponse> getAllCompanyTypeNames();
}
