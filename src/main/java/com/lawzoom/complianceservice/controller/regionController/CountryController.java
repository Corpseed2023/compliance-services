package com.lawzoom.complianceservice.controller.regionController;

import com.lawzoom.complianceservice.dto.regionDTO.country.CountryRequestDto;
import com.lawzoom.complianceservice.dto.regionDTO.country.CountryResponseDto;
import com.lawzoom.complianceservice.model.region.Country;
import com.lawzoom.complianceservice.service.regionService.CountryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth/countries")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CountryController {

    @Autowired
    private CountryServices countryService;

    @PostMapping("/create-country")
    public ResponseEntity<Country> createCountry(@RequestParam String countryName, @RequestParam String countryCode) {
        Country createdCountry = countryService.createCountry(countryName,countryCode);
        return ResponseEntity.ok(createdCountry);
    }

    @GetMapping("/fetch-all-countries")
    public ResponseEntity<List<CountryResponseDto>> getEnabledCountries() {
        List<CountryResponseDto> enabledCountries = countryService.getEnabledCountries();
        return ResponseEntity.ok(enabledCountries);
    }

    @PutMapping("/update-country")
    public ResponseEntity<Country> updateCountry(@RequestBody CountryRequestDto countryRequestDto) {
        Country updatedCountry = countryService.updateCountry(countryRequestDto);
        return ResponseEntity.ok(updatedCountry);
    }

}