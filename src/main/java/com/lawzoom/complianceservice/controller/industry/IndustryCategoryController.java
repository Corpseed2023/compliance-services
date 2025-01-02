package com.lawzoom.complianceservice.controller.industry;

import com.lawzoom.complianceservice.dto.industryDTO.request.IndustryCategoryRequestDTO;
import com.lawzoom.complianceservice.dto.industryDTO.response.IndustryCategoryResponseDTO;
import com.lawzoom.complianceservice.model.businessActivityModel.IndustryCategory;
import com.lawzoom.complianceservice.service.industry.IndustryCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/industry-category")
@CrossOrigin(origins = "*", maxAge = 3600)

public class IndustryCategoryController {

    @Autowired
    private IndustryCategoryService industryCategoryService;

    @PostMapping("/create-industry")
    public ResponseEntity<IndustryCategory> createIndustryCategory(@RequestParam String industryName, @RequestParam Long userId) {
        try {
            IndustryCategory createdCategory = industryCategoryService.createIndustryCategory(industryName,userId);
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-industry")
    public ResponseEntity<IndustryCategory> updateIndustryCategory(
            @RequestBody IndustryCategoryRequestDTO industryCategoryRequestDTO) {
        try {
            IndustryCategory updatedCategory = industryCategoryService.updateIndustryCategory(industryCategoryRequestDTO);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/fetch-all-industry")
    public ResponseEntity<List<IndustryCategoryResponseDTO>> fetchAllEnabledCategories() {
        try {
            List<IndustryCategoryResponseDTO> enabledCategories = industryCategoryService.fetchAllEnabledCategories();
            return new ResponseEntity<>(enabledCategories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
