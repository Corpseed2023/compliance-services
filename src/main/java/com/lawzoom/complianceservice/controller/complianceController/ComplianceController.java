package com.lawzoom.complianceservice.controller.complianceController;

import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.complianceService.ComplianceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/compliance")
public class ComplianceController {

    @Autowired
    private ComplianceService complianceService;

//    @GetMapping("/{userid}")
     @GetMapping("/userAllCompliance")
    public ResponseEntity fetchAllCompliance(@RequestParam("userid") Long userId){
        return this.complianceService.fetchManageCompliancesByUserId(userId);
    }

//    @PutMapping("/status/{complianceId}/{status}")
    @PutMapping("/updateComplianceStatus")
    public ResponseEntity updateStatus(@RequestParam("complianceId") Long complianceId,@RequestParam("status") int status){
        return this.complianceService.updateComplianceStatus(complianceId,status);
    }
}
