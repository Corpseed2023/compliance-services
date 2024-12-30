package com.lawzoom.complianceservice.controller.regionController;

import com.lawzoom.complianceservice.dto.regionDTO.city.CityRequestDto;
import com.lawzoom.complianceservice.model.region.City;
import com.lawzoom.complianceservice.service.regionService.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/city")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping("/save-cities")
    public ResponseEntity<City> createOrUpdateCities(@RequestParam String cityName,
                                                           @RequestParam String cityCode,
                                                           @RequestParam Long stateId) {
        City savedCity = cityService.createOrUpdateCities(cityName, cityCode,stateId);
        return ResponseEntity.ok(savedCity);    }

    @GetMapping("/get-all-cities")
    public ResponseEntity<List<City>> getAllCities(@RequestParam Long stateId) {
        List<City> cities = cityService.getAllCitiesByStateId(stateId);
        return ResponseEntity.ok(cities);
    }


    @DeleteMapping("/softDeleteCity")
    public ResponseEntity<Void> softDeleteCity(@RequestParam Long id) {
        cityService.softDeleteCity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update-city")
    public ResponseEntity<City> updateCity(@RequestBody CityRequestDto cityRequestDto) {
        City updatedCity = cityService.updateCity(cityRequestDto);
        return ResponseEntity.ok(updatedCity);
    }




}
