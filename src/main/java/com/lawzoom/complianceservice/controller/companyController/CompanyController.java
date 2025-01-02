package com.lawzoom.complianceservice.controller.companyController;

import com.lawzoom.complianceservice.dto.companyDto.CompanyRequest;
import com.lawzoom.complianceservice.dto.companyDto.CompanyResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.exception.UnauthorizedException;
import com.lawzoom.complianceservice.service.companyService.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/compliance/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/addCompany")
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody CompanyRequest companyRequest,
                                                         @RequestParam Long userId) {
        CompanyResponse companyResponse = companyService.createCompany(companyRequest, userId);
        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }

    @GetMapping("/fetch-company")
    public ResponseEntity<?> fetchCompany(@RequestParam Long companyId, @RequestParam Long userId) {
        try {
            CompanyResponse response = companyService.fetchCompany(companyId, userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetch-all-companies")
    public ResponseEntity<?> fetchAllCompanies(@RequestParam Long userId) {
        try {
            List<CompanyResponse> response = companyService.fetchAllCompanies(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateCompany")
    public ResponseEntity<CompanyResponse> updateCompany(@RequestBody CompanyRequest companyRequest,
                                                         @RequestParam Long companyId,
                                                         @RequestParam Long userId) {
        try {
            CompanyResponse companyResponse = companyService.updateCompany(companyRequest, companyId, userId);
            return new ResponseEntity<>(companyResponse, HttpStatus.OK);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/disableCompany")
    public ResponseEntity<String> disableCompany(@RequestParam Long companyId, @RequestParam Long userId) {
        try {
            companyService.disableCompany(companyId, userId);
            return ResponseEntity.ok("Company with ID " + companyId + " disabled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while disabling company with ID " + companyId);
        }
    }
}
