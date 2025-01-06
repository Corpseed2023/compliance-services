package com.lawzoom.complianceservice.service.companyService;



import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.companyDto.CompanyBusinessUnitDto;
import com.lawzoom.complianceservice.dto.companyDto.CompanyRequest;
import com.lawzoom.complianceservice.dto.companyDto.CompanyResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {

    CompanyResponse createCompany(CompanyRequest companyRequest , Long userId) ;

    CompanyResponse updateCompany(CompanyRequest companyRequest,Long companyId, Long userId);
//
    void disableCompany(Long id,Long userId);
//
    List<CompanyBusinessUnitDto> getCompanyUnitComplianceDetails(Long userId);
//
    CompanyResponse getCompanyData(Long companyId);
    
    List<BusinessUnitResponse> getUnitsByCompanies(Long companyId);

    CompanyResponse fetchCompany(Long companyId, Long userId, Long subscriberId);

    List<CompanyResponse> fetchAllCompanies(Long userId, Long subscriberId);
}
