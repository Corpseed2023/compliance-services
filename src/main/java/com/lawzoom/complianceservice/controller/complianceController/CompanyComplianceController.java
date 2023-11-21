package com.lawzoom.complianceservice.controller.complianceController;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.services.complianceService.ComplianceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
//@RequestMapping("/compliance/company/{companyId}")
@RequestMapping("/compliance/company")

public class CompanyComplianceController {

	@Autowired
	private ComplianceService complianceService;


	@GetMapping("/showAllCompliance")
	public List<ComplianceResponse> fetchAllCompliance(@RequestParam("companyId") Long companyId,
													   @RequestParam("businessId") Long businessUnitId) {
		if (companyId == null || businessUnitId == null) {
			throw new IllegalArgumentException("Please provide  companyId and businessUnitId");
		}
		return complianceService.fetchAllCompliances(companyId, businessUnitId);
	}


	@PostMapping("/saveCompliance")
	public ComplianceResponse saveCompliance(@Valid @RequestBody ComplianceRequest complianceRequest,
											 @RequestParam("companyId") Long companyId,
											 @RequestParam("businessUnitId") Long businessUnitId) {
		if (companyId == null || businessUnitId == null) {
			throw new IllegalArgumentException("Please provide  companyId and businessUnitId");
		}

		return this.complianceService.saveCompliance(complianceRequest, companyId, businessUnitId);
	}


	@PutMapping("/updateCompliance")
	public ComplianceResponse updateCompliance(@Valid @RequestBody ComplianceRequest complianceRequest, @RequestParam("companyId") Long companyId,@RequestParam("businesUnitId") Long businessUnitId){

		if (companyId == null || businessUnitId == null) {
			throw new IllegalArgumentException("Please provide  companyId and businessUnitId");
		}
		return this.complianceService.updateCompliance(complianceRequest,companyId,businessUnitId);
	}
//
////	@GetMapping("/{complianceId}")
//	@GetMapping("/showCompliance")
////write correct code i provided controller,service,serviceimpl i want to fetch all compliance is from database
//	public ComplianceResponse fetchCompliance(@RequestParam("id") Long complianceId, @RequestParam("companyId") Long companyId){
//		return this.complianceService.fetchCompliance(complianceId,companyId);
//	}
//
////	@DeleteMapping("/{complianceId}")
//	@DeleteMapping("/removeCompliance")
//
//	public ResponseEntity deleteCompliance(@RequestParam("complianceId") Long complianceId,@RequestParam("companyId") Long companyId){
//		return this.complianceService.deleteCompliance(complianceId,companyId);
//	}
//==========================================complete till delete on 14 sept 2023==================================================
}