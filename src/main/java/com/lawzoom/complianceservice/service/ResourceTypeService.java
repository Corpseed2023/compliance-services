package com.lawzoom.complianceservice.service;



import com.lawzoom.complianceservice.model.user.ResourceType;

import java.util.List;

public interface ResourceTypeService {

    ResourceType updateResourceType(Long id, ResourceType resourceType);

    void softDeleteResourceType(Long id);

    List<ResourceType> getAllResourceTypes();

    ResourceType getResourceTypeById(Long id);

    ResourceType createResourceType(String typeOfResourceName);
}
