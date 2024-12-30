package com.lawzoom.complianceservice.controller.businessUnitController;




import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.businessUnitDto.businessUnitRequest.BusinessUnitRequest;
import com.lawzoom.complianceservice.dto.businessUnitDto.businessUnitRequest.UnitRequest;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.service.businessService.BusinessUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth/business-unit")
public class BusinessUnitController {

    @Autowired
    private BusinessUnitService businessUnitService;

    @PostMapping("/saveBusinessUnit")
    public ResponseEntity<?> createBusinessUnit(
            @RequestBody BusinessUnitRequest businessUnitRequest,
            @RequestParam Long gstDetailsId) {
        try {
            BusinessUnitResponse savedBusinessData = businessUnitService.createBusinessUnit(businessUnitRequest, gstDetailsId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBusinessData);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @PutMapping("/updateBusinessUnit")
    public ResponseEntity<BusinessUnitResponse> updateBusinessUnit(
            @RequestParam Long businessUnitId,
            @RequestBody BusinessUnitRequest businessUnitRequest) {
        try {
            BusinessUnitResponse updatedBusinessData = businessUnitService.updateBusinessUnit(businessUnitId, businessUnitRequest);
            return new ResponseEntity<>(updatedBusinessData, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAllBusinessUnits")
    public ResponseEntity<List<BusinessUnitResponse>> getAllBusinessUnits(@RequestParam Long gstDetails, @RequestParam Long userId) {
        try {
            List<BusinessUnitResponse> businessUnits = businessUnitService.getAllBusinessUnits(gstDetails,userId);
            return ResponseEntity.ok(businessUnits);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/get-company-units")
    public ResponseEntity<List<BusinessUnitResponse>> getCompanyUnits(@RequestBody UnitRequest unitRequest) {
        try {
            List<BusinessUnitResponse> businessUnits = businessUnitService.getCompanyUnits(unitRequest);
            return ResponseEntity.ok(businessUnits);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fetchBusinessData")
    public BusinessUnitResponse getBusinessUnitData(@RequestParam Long businessUnitId) {

        BusinessUnitResponse businessUnitResponse = businessUnitService.getBusinessUnitData(businessUnitId);

       return businessUnitResponse;
    }




}
