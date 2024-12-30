package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.model.ResourceType;
import com.lawzoom.complianceservice.repository.ResourceTypeRepository;
import com.lawzoom.complianceservice.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ResourceTypeServiceImpl implements ResourceTypeService {

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;



    @Override
    public ResourceType createResourceType(String typeOfResourceName) {

        // Create a new instance of ResourceType
        ResourceType resourceType = new ResourceType();

        // Set the required fields
        resourceType.setTypeOfResourceName(typeOfResourceName);
        resourceType.setCreatedAt(new Date());  // Set the current timestamp
        resourceType.setUpdatedAt(new
                Date());  // Set the current timestamp
        resourceType.setDate(LocalDate.now());  // Set the current date
        resourceType.setEnable(true);  // Set 'isEnable' as true by default
        resourceType.setDeleted(false);  // Set 'isDeleted' as false by default

        // Save the resource type to the repository
        return resourceTypeRepository.save(resourceType);
    }

    @Override
    public ResourceType updateResourceType(Long id, ResourceType resourceType) {
        Optional<ResourceType> existingResourceType = resourceTypeRepository.findById(id);
        if (existingResourceType.isPresent()) {
            ResourceType updatedResource = existingResourceType.get();
            updatedResource.setTypeOfResourceName(resourceType.getTypeOfResourceName());
            updatedResource.setUpdatedAt(new Date());
            return resourceTypeRepository.save(updatedResource);
        } else {
            throw new IllegalArgumentException("ResourceType with ID " + id + " not found");
        }
    }

    @Override
    public void softDeleteResourceType(Long id) {
        ResourceType resourceType = resourceTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ResourceType with ID " + id + " not found"));
        resourceType.setDeleted(true);
        resourceTypeRepository.save(resourceType);
    }

    @Override
    public List<ResourceType> getAllResourceTypes() {
        return resourceTypeRepository.findAll();
    }

    @Override
    public ResourceType getResourceTypeById(Long id) {
        return resourceTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ResourceType with ID " + id + " not found"));
    }
}
