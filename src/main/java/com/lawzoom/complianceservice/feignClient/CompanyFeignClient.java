package com.lawzoom.complianceservice.feignClient;


import com.lawzoom.complianceservice.dto.CompanyDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AUTHENTICATION-SERVICE", url = "http://localhost:8082")
@Service
public interface CompanyFeignClient {

    @GetMapping("/getCompanyDetails")
    CompanyDetails getCompanyDetails (@RequestParam CompanyDetails companyDetails);
}
