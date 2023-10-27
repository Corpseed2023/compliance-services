package com.lawzoom.complianceservice.controller.complianceController;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.response.ResponseEntity;
import com.lawzoom.complianceservice.services.complianceService.ComplianceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
//@RequestMapping("/compliance/company/{companyId}")
@RequestMapping("/compliance/company")

public class CompanyComplianceController {

	@Autowired
	private ComplianceService complianceService;

	@GetMapping("/showAllCompliance")
	public ResponseEntity fetchAllCompliances(@RequestParam("companyId") Long companyId){
		return this.complianceService.fetchAllCompliances(companyId);
	}

	@PostMapping("/saveCompliance")
	public ComplianceResponse saveCompliance(@Valid @RequestBody ComplianceRequest complianceRequest, @RequestParam("companyId") Long companyId){
		return this.complianceService.saveCompliance(complianceRequest,companyId);
	}
	
	@PutMapping("/updateCompliance")
	public ResponseEntity updateCompliance(@Valid @RequestBody ComplianceRequest complianceRequest, @RequestParam("companyId") Long companyId){
		return this.complianceService.updateCompliance(complianceRequest,companyId);
	}
	
//	@GetMapping("/{complianceId}")
	@GetMapping("/showCompliance")

	public ComplianceResponse fetchCompliance(@RequestParam("id") Long complianceId, @RequestParam("companyId") Long companyId){
		return this.complianceService.fetchCompliance(complianceId,companyId);
	}
	
//	@DeleteMapping("/{complianceId}")
	@DeleteMapping("/removeCompliance")

	public ResponseEntity deleteCompliance(@RequestParam("complianceId") Long complianceId,@RequestParam("companyId") Long companyId){
		return this.complianceService.deleteCompliance(complianceId,companyId);
	}
//==========================================complete till delete on 14 sept 2023==================================================
}