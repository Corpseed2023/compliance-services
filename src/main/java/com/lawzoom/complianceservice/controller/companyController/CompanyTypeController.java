package com.lawzoom.complianceservice.controller.companyController;

import com.lawzoom.complianceservice.dto.companyDto.CompanyTypeRequest;
import com.lawzoom.complianceservice.dto.companyDto.CompanyTypeResponse;
import com.lawzoom.complianceservice.service.companyService.CompanyServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/company")
public class CompanyTypeController {

    @Autowired
    private CompanyServiceType companyServiceType;


    @PostMapping("/createCompanyType")
    public ResponseEntity<CompanyTypeResponse> createCompanyType(@RequestBody CompanyTypeRequest companyTypeRequest) {

        CompanyTypeResponse companyResponseType = companyServiceType.createCompanyType(companyTypeRequest.getCompanyTypeName(),companyTypeRequest.getUserId());

        return new ResponseEntity<>(companyResponseType, HttpStatus.CREATED);
    }

    @GetMapping("/getAllCompanyTypeNames")
    public ResponseEntity<List<CompanyTypeResponse>> getAllCompanyTypeNames() {
        List<CompanyTypeResponse> companyTypeNames = companyServiceType.getAllCompanyTypeNames();
        return new ResponseEntity<>(companyTypeNames, HttpStatus.OK);
    }


    @PutMapping("/updateCompanyType")
    public ResponseEntity<CompanyTypeResponse> updateCompanyType(
            @RequestParam Long id,
            @RequestBody CompanyTypeRequest companyTypeRequest) {

        CompanyTypeResponse updatedCompanyType = companyServiceType.updateCompanyType(id, companyTypeRequest);
        return new ResponseEntity<>(updatedCompanyType, HttpStatus.OK);
    }

    @DeleteMapping("/deleteCompanyType")
    public ResponseEntity<String> softDeleteCompanyType(@RequestParam Long id) {
        companyServiceType.softDeleteCompanyType(id);
        return new ResponseEntity<>("CompanyType soft deleted successfully", HttpStatus.OK);
    }

}
