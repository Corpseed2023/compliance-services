package com.lawzoom.complianceservice.serviceImpl.regionLogic;

import com.lawzoom.complianceservice.dto.regionDTO.city.CityRequestDto;
import com.lawzoom.complianceservice.model.region.City;
import com.lawzoom.complianceservice.model.region.States;
import com.lawzoom.complianceservice.repository.regionRepo.CityRepository;
import com.lawzoom.complianceservice.repository.regionRepo.StatesRepository;
import com.lawzoom.complianceservice.service.regionService.CityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StatesRepository statesRepository;

    public City createOrUpdateCities(String cityName, String cityCode, Long stateId) {
        // Fetch the state from the repository
        States state = statesRepository.findByEnabledAndNotDeleted(stateId);

        if (state == null) {
            throw new IllegalArgumentException("State not found or is disabled/deleted for ID: " + stateId);
        }

        // Create and populate the City object
        City city = new City();
        city.setCityName(cityName);
        city.setStates(state);
        city.setEnable(true);
        city.setCreatedAt(new Date());
        city.setDate(LocalDate.now());
        city.setCityCode(cityCode);
        city.setDate(LocalDate.now());

        // Save and return the city
        return cityRepository.save(city);
    }

    @Override
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public List<City> getAllCitiesByStateId(Long stateId) {
        States state = statesRepository.findByEnabledAndNotDeleted(stateId);

        if (state == null) {
            throw new IllegalArgumentException("State not found or is disabled/deleted for ID: " + stateId);
        }

        return cityRepository.findEnabledAndNotDeletedCitiesByState(state);
    }


    @Override
    public void softDeleteCity(Long id) {
        // Use the custom method to find the city by ID, ensuring it is enabled and not deleted
        City city = cityRepository.findEnabledAndNotDeletedCityById(id)
                .orElseThrow(() -> new EntityNotFoundException("City not found, or it is either disabled or deleted"));

        // Set the city as deleted and disabled
        city.setDeleted(true);
        city.setEnable(false);

        // Save the updated city
        cityRepository.save(city);
    }



    @Override
    public City updateCity(CityRequestDto cityRequestDto) {

        City city = cityRepository.findEnabledAndNotDeletedCityById(cityRequestDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("City not found or it is either disabled or deleted"));

        // Find the state using the provided state ID
        States state = statesRepository.findById(cityRequestDto.getStateId())
                .orElseThrow(() -> new EntityNotFoundException("State not found"));

        // Update the city details
        city.setCityName(cityRequestDto.getCityName());
        city.setCityCode(cityRequestDto.getCityCode());
        city.setUpdatedAt(new Date());
        city.setStates(state);

        // Save the updated city
        cityRepository.save(city);

        return city;
    }



}
