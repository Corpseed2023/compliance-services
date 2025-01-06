package com.lawzoom.complianceservice.controller.userController;


import com.lawzoom.complianceservice.model.user.ResourceType;
import com.lawzoom.complianceservice.service.ResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/resource-type")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ResourceTypeController {

    @Autowired
    private ResourceTypeService resourceTypeService;

    @PostMapping("/create")
    public ResponseEntity<ResourceType> createResourceType(@RequestParam String typeOfResourceName) {
        ResourceType createdResource = resourceTypeService.createResourceType(typeOfResourceName);
        return new ResponseEntity<>(createdResource, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ResourceType> updateResourceType(
            @RequestParam Long id,
            @RequestBody ResourceType resourceType) {
        ResourceType updatedResource = resourceTypeService.updateResourceType(id, resourceType);
        return new ResponseEntity<>(updatedResource, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> softDeleteResourceType(@RequestParam Long id) {
        resourceTypeService.softDeleteResourceType(id);
        return new ResponseEntity<>("ResourceType soft-deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ResourceType>> getAllResourceTypes() {
        List<ResourceType> resourceTypes = resourceTypeService.getAllResourceTypes();
        return new ResponseEntity<>(resourceTypes, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<ResourceType> getResourceTypeById(@RequestParam Long id) {
        ResourceType resourceType = resourceTypeService.getResourceTypeById(id);
        return new ResponseEntity<>(resourceType, HttpStatus.OK);
    }
}
