package com.lawzoom.complianceservice.controller.complianceController;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.complianceService.ComplianceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
//@RequestMapping("/compliance/business/{businessUnitId}")
@RequestMapping("/compliance/business")

public class BusinessUnitComplianceController {

    @Autowired
    private ComplianceService complianceService;

    @GetMapping("/showAllCompliances")
    public ResponseEntity fetchAllCompliances(@RequestParam("businessUnitId") Long businessUnitId){
        return this.complianceService.fetchAllComplianceByBusinessUnitId(businessUnitId);
    }

    @PostMapping("/saveCompliance")
    public ComplianceResponse saveCompliance(@Valid @RequestBody ComplianceRequest complianceRequest, @RequestParam("businessUnitId") Long businessUnitId){
        return this.complianceService.saveBusinessCompliance(complianceRequest,businessUnitId);
    }

//    @PutMapping("/update")
    @PutMapping("/updateCompliance")

    public ResponseEntity updateCompliance(@Valid @RequestBody ComplianceRequest complianceRequest,@RequestParam("businessUnitId") Long businessUnitId){
        return this.complianceService.updateBusinessCompliance(complianceRequest,businessUnitId);
    }

//    @GetMapping("/{complianceId}")
    @GetMapping("/showCompliance}")

    public ResponseEntity fetchCompliance(@RequestParam("complianceId") Long complianceId,@RequestParam("businessUnitId") Long businessUnitId){
        return this.complianceService.fetchBusinessCompliance(complianceId,businessUnitId);
    }

//    @DeleteMapping("/{complianceId}")
    @DeleteMapping("/removeCompliance")
    public ResponseEntity deleteCompliance(@RequestParam("complianceId") Long complianceId,@RequestParam("businessUnitId") Long businessUnitId){
        return this.complianceService.deleteBusinessCompliance(complianceId,businessUnitId);
    }

}
