package com.lawzoom.complianceservice.serviceImpl.regionLogic;

import com.lawzoom.complianceservice.dto.regionDTO.states.StatesRequestDto;
import com.lawzoom.complianceservice.model.region.Country;
import com.lawzoom.complianceservice.model.region.States;
import com.lawzoom.complianceservice.repository.regionRepo.CountryRepository;
import com.lawzoom.complianceservice.repository.regionRepo.StatesRepository;
import com.lawzoom.complianceservice.service.regionService.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public States createStates(Long countryId, String stateName) {
        // Check if country exists and is enabled
        Country country = countryRepository.findEnabledAndNotDeletedById(countryId).orElse(null);
        if (country == null) {
            // If the country is not found or is disabled, return null or throw an exception
            throw new IllegalArgumentException("Country not found or disabled");
        }

        // Check if state name is valid
        if (stateName == null || stateName.isEmpty()) {
            throw new IllegalArgumentException("State name cannot be empty");
        }

        // Create a new state and set properties
        States state = new States();
        state.setStateName(stateName);
        state.setCountry(country);
        state.setCreatedAt(new Date());
        state.setUpdatedAt(new Date());
        state.setEnable(true);
        state.setDeleted(false);
        state.setDate(LocalDate.now());

        // Save the state
        return statesRepository.save(state);
    }

    @Override
    public List<States> getAllEnabledAndNotDeletedStates(Long countryId) {
        return statesRepository.findAllEnabledAndNotDeletedStates(countryId);
    } //with

    @Override
    public States updateState(StatesRequestDto statesRequestDto) {

        // Fetch existing state
        States existingState = statesRepository.findByEnabledAndNotDeleted(statesRequestDto.getId());
        if (existingState != null) {
            // Update the state fields as needed
            existingState.setStateName(statesRequestDto.getStateName());
            existingState.setUpdatedAt(new Date());
            existingState.setDate(LocalDate.now());

            // Save and return the updated state
            return statesRepository.save(existingState);
        } else {
            return null; // Return null if state is not found
        }
    }
}
