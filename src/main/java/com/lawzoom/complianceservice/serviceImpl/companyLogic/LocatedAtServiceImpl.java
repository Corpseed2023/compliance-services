package com.lawzoom.complianceservice.serviceImpl.companyLogic;

import com.lawzoom.complianceservice.dto.regionDTO.locatedAt.LocatedAtResponse;
import com.lawzoom.complianceservice.model.User;
import com.lawzoom.complianceservice.model.region.LocatedAt;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.repository.companyRepo.LocatedAtRepository;
import com.lawzoom.complianceservice.service.companyService.LocatedAtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocatedAtServiceImpl implements LocatedAtService {

    @Autowired
    private LocatedAtRepository locatedAtRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String createLocatedAt(String locationName, Long userId) {

        Optional<User> userOptional = userRepository.findByIdAndIsEnableAndNotDeleted(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Error: User not found!");
        }
        User user = userOptional.get();

        // Check if a location with the same name is already enabled and not deleted
        LocatedAt existingLocation = locatedAtRepository.findByLocationNameAndIsEnableAndIsDeleted(locationName);
        if (existingLocation != null) {
            throw new IllegalArgumentException("Error: Location already exists!");
        }

        // Create a new location
        LocatedAt newLocation = new LocatedAt();
        newLocation.setLocationName(locationName);
        newLocation.setCreatedAt(new Date());
        newLocation.setUpdatedAt(new Date());
        newLocation.setEnable(true);
        newLocation.setDeleted(false);
        newLocation.setDate(LocalDate.now());

        // Save the new location
        LocatedAt savedLocation = locatedAtRepository.save(newLocation);
        return "Location '" + savedLocation.getLocationName() + "' created successfully!";
    }



    @Override
    public List<LocatedAtResponse> getAllLocationNames() {
        return locatedAtRepository.findAllByIsEnableAndIsDeleted()
                .stream()
                .map(locatedAt -> new LocatedAtResponse(locatedAt.getId(), locatedAt.getLocationName()))
                .collect(Collectors.toList());
    }

    @Override
    public LocatedAt updateLocatedAt(Long id, LocatedAt updatedLocatedAt) {

        LocatedAt existingLocatedAt = locatedAtRepository.findByIdAndIsEnable(id)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));
        existingLocatedAt.setLocationName(updatedLocatedAt.getLocationName());
        existingLocatedAt.setUpdatedAt(new Date());
        existingLocatedAt.setEnable(true);
        return locatedAtRepository.save(existingLocatedAt);
    }

    @Override
    public void softDeleteLocatedAt(Long id) {
        LocatedAt locatedAt = locatedAtRepository.findByIdAndIsEnable(id)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));
        locatedAt.setDeleted(true);
        locatedAt.setUpdatedAt(new Date());
        locatedAtRepository.save(locatedAt);
    }
}
