package com.lawzoom.complianceservice.controller.gstDetailsController;


import com.lawzoom.complianceservice.dto.gstDTO.GstDetailsFetchRequest;
import com.lawzoom.complianceservice.dto.gstDTO.GstDetailsRequest;
import com.lawzoom.complianceservice.dto.gstDTO.GstDetailsResponse;
import com.lawzoom.complianceservice.dto.gstDTO.gstResponse.GstCompanyResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.service.GstDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/gst-details")
@CrossOrigin(origins = "*", maxAge = 3600)

public class GstDetailsController {

    @Autowired
    private GstDetailsService gstDetailsService;

    @PostMapping("/add-gstDetails")
    public ResponseEntity<GstDetailsResponse> createGstDetails(@RequestBody GstDetailsRequest gstDetailsRequest) {
        GstDetailsResponse response = gstDetailsService.createGstDetails(gstDetailsRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/fetch-gstDetail")
    public ResponseEntity<GstDetailsResponse> getGstDetailsById(@RequestBody GstDetailsFetchRequest gstDetailsFetchRequest) {
        try {
            GstDetailsResponse response = gstDetailsService.getGstDetailsById(gstDetailsFetchRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/fetch-company-gstDetails")
    public ResponseEntity<?> fetchAllGstDetails(@RequestParam Long companyId,
                                                @RequestParam Long userId,
                                                @RequestParam Long subscriptionId) {
        try {
            List<GstCompanyResponse> response = gstDetailsService.fetchAllGstDetails(companyId, userId, subscriptionId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateGstDetails(@RequestParam Long id, @RequestBody GstDetailsRequest gstDetailsRequest) {
        try {
            GstDetailsResponse response = gstDetailsService.updateGstDetails(id, gstDetailsRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-gst-details")
    public ResponseEntity<?> softDeleteGstDetails(@RequestParam Long id, @RequestParam Long userId) {
        try {
            String response = gstDetailsService.softDeleteGstDetails(id, userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
