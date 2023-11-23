package com.lawzoom.complianceservice.controller.complianceController;

import com.lawzoom.complianceservice.dto.complianceDto.ComplianceRequest;
import com.lawzoom.complianceservice.dto.complianceDto.ComplianceResponse;
import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import com.lawzoom.complianceservice.services.complianceService.ComplianceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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
											 @RequestParam("businessUnitId") Long businessUnitId,
											 @RequestParam("teamId") Long teamId) {
		if (companyId == null || businessUnitId == null || teamId == null) {
			throw new IllegalArgumentException("Please provide  companyId and businessUnitId");
		}

		return this.complianceService.saveCompliance(complianceRequest, companyId, businessUnitId, teamId);
	}


	@PutMapping("/updateCompliance")
	public ComplianceResponse updateCompliance(@Valid @RequestBody ComplianceRequest complianceRequest, @RequestParam("companyId") Long companyId, @RequestParam("businesUnitId") Long businessUnitId) {

		if (companyId == null || businessUnitId == null) {
			throw new IllegalArgumentException("Please provide  companyId and businessUnitId");
		}
		return this.complianceService.updateCompliance(complianceRequest, companyId, businessUnitId);
	}

	@GetMapping("/getAllComplianceByCompanyUnitTeam")
	public ComplianceResponse getAllComplianceByCompanyUnitTeam(@RequestParam("companyId") Long companyId,
																@RequestParam("businessUnitId") Long businessUnitId,
																@RequestParam("teamId") Long teamId) {
		if (companyId == null || businessUnitId == null || teamId == null) {
			throw new IllegalArgumentException("Please provide  companyId and businessUnitId");
		}
		return this.complianceService.getAllComplianceByCompanyUnitTeam(teamId, companyId, businessUnitId);

	}

	@GetMapping("/getAllComplianceByUserId")
	public ResponseEntity<Map<Long, List<ComplianceResponse>>> getAllComplianceByUserId() {
		Map<Long, List<ComplianceResponse>> result = complianceService.getAllComplianceByCompanyId();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}


}