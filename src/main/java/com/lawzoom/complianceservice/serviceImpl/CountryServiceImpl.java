package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.regionDTO.country.CountryRequestDto;
import com.lawzoom.complianceservice.dto.regionDTO.country.CountryResponseDto;
import com.lawzoom.complianceservice.model.region.Country;
import com.lawzoom.complianceservice.repository.regionRepo.CountryRepository;
import com.lawzoom.complianceservice.service.regionService.CountryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryServices {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    public List<CountryResponseDto> getEnabledCountries() {
        List<Country> enabledCountries = countryRepository.findAllEnabledCountries();

        // Convert the list of Country to a list of CountryResponseDto
        List<CountryResponseDto> countryResponseDtos = new ArrayList<>();
        for (Country country : enabledCountries) {
            CountryResponseDto dto = new CountryResponseDto(
                    country.getId(),
                    country.getCountryName(),
                    country.getCountryCode()
            );
            countryResponseDtos.add(dto);
        }
        return countryResponseDtos;
    }


    @Override
    public Country createCountry(String countryName, String countryCode) {
        // Validate input parameters
        if (countryName == null || countryName.isEmpty()) {
            throw new IllegalArgumentException("Country name cannot be empty");
        }

        if (countryCode == null || countryCode.isEmpty()) {
            throw new IllegalArgumentException("Country code cannot be empty");
        }

        // Check if a country with the same name already exists
        Optional<Country> existingCountry = countryRepository.findByCountryName(countryName);
        if (existingCountry.isPresent()) {
            throw new IllegalArgumentException("Country with the same name already exists");
        }

        // Create and save the new country
        Country country = new Country();
        country.setCountryName(countryName);
        country.setCountryCode(countryCode);
        country.setDate(LocalDate.now());
        country.setCreatedAt(new Date());
        country.setUpdatedAt(new Date());
        country.setEnable(true);
        country.setDeleted(false);

        return countryRepository.save(country);
    }


    @Override
    public Country updateCountry(CountryRequestDto countryRequestDto) {

        Optional<Country> existingCountryOptional = countryRepository.findEnabledAndNotDeletedById(countryRequestDto.getId());

        if (existingCountryOptional.isPresent()) {
            Country existingCountry = existingCountryOptional.get();

            if (countryRequestDto.getCountryName() != null && !countryRequestDto.getCountryName().isEmpty()) {
                existingCountry.setCountryName(countryRequestDto.getCountryName());
            }

            if (countryRequestDto.getCountryCode() != null && !countryRequestDto.getCountryCode().isEmpty()) {
                existingCountry.setCountryCode(countryRequestDto.getCountryCode());
            }
            existingCountry.setUpdatedAt(new Date());

            // Save and return the updated country
            return countryRepository.save(existingCountry);
        } else {
            // If the country is not found, throw an exception or handle the error
            throw new RuntimeException("Country with ID " + countryRequestDto.getId() + " not found, or it is either disabled or deleted.");
        }
    }




}
