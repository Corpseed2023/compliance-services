package com.lawzoom.complianceservice.feignClient;


import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.companyResponseDto.CompanyResponse;
import com.lawzoom.complianceservice.dto.userDto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = "AUTHENTICATION-SERVICE", url = "http://localhost:8081")
@Service
public interface AuthenticationFeignClient {

    @GetMapping("/api/auth/user/getUserId")
    UserResponse getUserId(@RequestParam Long userId);

    @GetMapping("/companyServices/company/getCompanyDataForTasks")
    CompanyResponse getCompanyData(@RequestParam Long companyId);

    @GetMapping("/companyServices/business-unit/fetchBusinessData")
    BusinessUnitResponse getBusinessUnitData(@RequestParam Long businessUnitId);


    @GetMapping("/companyServices/business-unit/getAllBusinessUnits")
    List<BusinessUnitResponse> getAllBusinessUnits(@RequestParam Long companyId);


    @GetMapping("/companyServices/business-unit/fetchBusinessData")
    BusinessUnitResponse getBusinessUnitById(@RequestParam Long businessUnitId);


}
