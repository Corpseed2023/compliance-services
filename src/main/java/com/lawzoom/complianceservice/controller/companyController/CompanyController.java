package com.lawzoom.complianceservice.controller.companyController;



import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.companyDto.CompanyBusinessUnitDto;
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
@RequestMapping("/api/auth/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @PostMapping("/addCompany")
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody CompanyRequest companyRequest ,
                                                         @RequestParam Long userId)  {

        CompanyResponse companyResponse = companyService.createCompany(companyRequest,userId);
        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }

    @GetMapping("/fetch-company")
    public ResponseEntity<?> fetchCompany(@RequestParam Long userId , @RequestParam Long subscriptionId,@RequestParam Long companyId) {
        try {
            CompanyResponse response = companyService.fetchCompany(userId,subscriptionId,companyId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetch-all-company")
    public ResponseEntity<?> fetchAllCompanies(@RequestParam Long userId , @RequestParam Long subscriptionId) {
        try {
            List<CompanyResponse> response = companyService.fetchAllCompanies(userId,subscriptionId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/assign-team-members")
//    public ResponseEntity<String> assignTeamMembersToCompany(@RequestParam Long companyId,
//                                                             @RequestBody List<Long> teamMemberIds) {
//        try {
//            companyService.assignTeamMembersToCompany(companyId, teamMemberIds);
//            return new ResponseEntity<>("Team members assigned successfully.", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
////

//    @GetMapping("/fetch-mapped-members")
//    public ResponseEntity<List<TeamMember>> getTeamMembersByCompany(@RequestParam Long companyId) {
//        try {
//            List<TeamMember> teamMembers = companyService.getTeamMembersByCompany(companyId);
//            return new ResponseEntity<>(teamMembers, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @PutMapping("/update-company")
    public ResponseEntity<CompanyResponse> updateCompany(@RequestBody CompanyRequest companyRequest,
                                                         @RequestParam Long companyId,
                                                         @RequestParam Long userId) {
        try {
            CompanyResponse companyResponse = companyService.updateCompany(companyRequest, companyId, userId);
            return new ResponseEntity<>(companyResponse, HttpStatus.OK);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN); // Return Forbidden if user is unauthorized
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return NotFound for missing resources
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Generic error
        }
    }


    @GetMapping("/get-all-business-unit")
    public List<BusinessUnitResponse> getCompaniesByUserId(@RequestParam Long companyId) {
        return companyService.getUnitsByCompanies(companyId);
    }
    @DeleteMapping("/removeCompany")
    public ResponseEntity<String> disableCompany(@RequestParam Long id , @RequestParam Long userId) {
        try {
            companyService.disableCompany(id , userId);
            return ResponseEntity.ok("Company with ID " + id + " disabled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while disabling company with ID " + id);
        }
    }

    @GetMapping("/getCompanyUnitComplianceDetails")
    public List<CompanyBusinessUnitDto> getCompanyUnitComplianceDetails(@RequestParam Long userId) {
        return companyService.getCompanyUnitComplianceDetails(userId);
    }

    @GetMapping("/getCompanyDataForTasks")
    public CompanyResponse getCompanyData(@RequestParam Long companyId) {
        return companyService.getCompanyData(companyId);
    }



}
