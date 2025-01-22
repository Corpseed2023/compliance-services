package com.lawzoom.complianceservice.serviceImpl.companyLogic;

import com.lawzoom.complianceservice.dto.regionDTO.locatedAt.LocatedAtResponse;
import com.lawzoom.complianceservice.model.region.LocatedAt;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.repository.companyRepo.LocatedAtRepository;
import com.lawzoom.complianceservice.service.companyService.LocatedAtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class LocatedAtServiceImpl implements LocatedAtService {

    @Autowired
    private LocatedAtRepository locatedAtRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String createLocatedAt(String locationName, Long userId) {
        // Validate User
        User user = userRepository.findActiveUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }

        // Check if a location with the same name is already enabled and not deleted
        LocatedAt existingLocation = locatedAtRepository.findByLocationNameAndIsEnableAndIsDeleted(locationName);
        if (existingLocation != null) {
            throw new IllegalArgumentException("Error: Location with name '" + locationName + "' already exists!");
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
        return locatedAtRepository.findAllByIsEnableAndIsDeleted(true, false)
                .stream()
                .map(locatedAt -> new LocatedAtResponse(locatedAt.getId(), locatedAt.getLocationName()))
                .collect(Collectors.toList());
    }


    @Override
    public LocatedAt updateLocatedAt(Long id, LocatedAt updatedLocatedAt) {
        // Fetch the existing location
        LocatedAt existingLocatedAt = locatedAtRepository.findByIdAndIsEnable(id)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));

        // Check for duplicate location name if it's being changed
        if (!existingLocatedAt.getLocationName().equals(updatedLocatedAt.getLocationName())) {
            LocatedAt duplicateLocation = locatedAtRepository.findByLocationNameAndIsEnableAndIsDeleted(
                    updatedLocatedAt.getLocationName());
            if (duplicateLocation != null) {
                throw new IllegalArgumentException("Error: Location with the same name already exists!");
            }
        }

        // Update the location details
        existingLocatedAt.setLocationName(updatedLocatedAt.getLocationName());
        existingLocatedAt.setUpdatedAt(new Date());

        // Save and return the updated location
        return locatedAtRepository.save(existingLocatedAt);
    }

    @Override
    public void softDeleteLocatedAt(Long id) {
        // Fetch the location
        LocatedAt locatedAt = locatedAtRepository.findByIdAndIsEnable(id)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + id));

        // Soft delete the location
        locatedAt.setDeleted(true);
        locatedAt.setUpdatedAt(new Date());

        // Save the updated location
        locatedAtRepository.save(locatedAt);
    }

}
