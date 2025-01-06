package com.lawzoom.complianceservice.service.regionService;


import com.lawzoom.complianceservice.dto.regionDTO.city.CityRequestDto;
import com.lawzoom.complianceservice.model.region.City;

import java.util.List;

public interface CityService {

    City createOrUpdateCities(String cityName,String cityCode,Long stateId);

    void deleteCity(Long id);

    List<City> getAllCitiesByStateId(Long stateId);


    void softDeleteCity(Long id);

    City updateCity(CityRequestDto cityRequestDto);
}
