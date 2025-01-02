package com.lawzoom.complianceservice.controller.regionController;


import com.lawzoom.complianceservice.dto.regionDTO.states.StatesRequestDto;
import com.lawzoom.complianceservice.model.region.States;
import com.lawzoom.complianceservice.service.regionService.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/states")
@CrossOrigin(origins = "*", maxAge = 3600)

public class StatesController {

    @Autowired
    private StateService statesService;

    // Get all states
    @GetMapping("/fetch-all-states")
    public ResponseEntity<List<States>> getAllStates(@RequestParam Long countryId) {
        List<States> statesList = statesService.getAllEnabledAndNotDeletedStates(countryId);
        return new ResponseEntity<>(statesList, HttpStatus.OK);
    }

    @PostMapping("/create-state")
    public ResponseEntity<States> createState(@RequestParam Long countryId, @RequestParam String stateName) {
        States savedState = statesService.createStates(countryId, stateName);
        return new ResponseEntity<>(savedState, HttpStatus.CREATED);
    }

    // Update an existing state
    @PutMapping("/update-states")
    public ResponseEntity<States> updateState(@RequestBody StatesRequestDto statesRequestDto) {
        States updatedState = statesService.updateState(statesRequestDto);
        return new ResponseEntity<>(updatedState, HttpStatus.OK);
    }


}
