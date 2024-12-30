package com.lawzoom.complianceservice.service.regionService;


import com.lawzoom.complianceservice.dto.regionDTO.country.CountryRequestDto;
import com.lawzoom.complianceservice.dto.regionDTO.country.CountryResponseDto;
import com.lawzoom.complianceservice.model.region.Country;

import java.util.List;

public interface CountryServices {


    Country createCountry(String countryName, String countryCode);

    Country updateCountry(CountryRequestDto countryRequestDto);

    List<CountryResponseDto> getEnabledCountries();
}
