package com.lawzoom.complianceservice.controller.companyController;

import com.lawzoom.complianceservice.dto.regionDTO.locatedAt.LocatedAtRequest;
import com.lawzoom.complianceservice.dto.regionDTO.locatedAt.LocatedAtResponse;
import com.lawzoom.complianceservice.model.region.LocatedAt;
import com.lawzoom.complianceservice.service.companyService.LocatedAtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/compliance/located-at")
public class  LocatedAtController {

    @Autowired
    private LocatedAtService locatedAtService;

    @PostMapping("/create-locatedAt")
    public ResponseEntity<String> createLocatedAt(
            @RequestBody LocatedAtRequest locatedAtRequest) {
        try {
            String result = locatedAtService.createLocatedAt(locatedAtRequest.getLocationName(), locatedAtRequest.getUserId());
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-all-locatedAt")
    public ResponseEntity<List<LocatedAtResponse>> getAllLocationNames() {
        List<LocatedAtResponse> locationNames = locatedAtService.getAllLocationNames();
        return new ResponseEntity<>(locationNames, HttpStatus.OK);
    }


    @PutMapping("/update-locatedAt")
    public ResponseEntity<LocatedAt> updateLocatedAt(
            @RequestParam Long id,
            @RequestBody LocatedAt updatedLocatedAt) {
        LocatedAt locatedAt = locatedAtService.updateLocatedAt(id, updatedLocatedAt);
        return new ResponseEntity<>(locatedAt, HttpStatus.OK);
    }

    @DeleteMapping("/locatedAt-delete")
    public ResponseEntity<String> softDeleteLocatedAt(@RequestParam Long id) {
        locatedAtService.softDeleteLocatedAt(id);
        return new ResponseEntity<>("Location soft-deleted successfully", HttpStatus.OK);
    }



}
