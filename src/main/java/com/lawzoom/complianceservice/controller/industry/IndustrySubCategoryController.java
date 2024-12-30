package com.lawzoom.complianceservice.controller.industry;

import com.lawzoom.complianceservice.dto.industryDTO.request.IndustrySubCategoryRequestDTO;
import com.lawzoom.complianceservice.dto.industryDTO.response.IndustrySubCategoryResponseDTO;
import com.lawzoom.complianceservice.model.businessActivityModel.IndustrySubCategory;
import com.lawzoom.complianceservice.service.industry.IndustrySubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/sub-industry-category")
@CrossOrigin(origins = "*", maxAge = 3600)
public class IndustrySubCategoryController {

    @Autowired
    private IndustrySubCategoryService industrySubCategoryService;

    @PostMapping("/create-sub-industry")
    public ResponseEntity<IndustrySubCategory> createSubIndustryCategory(@RequestParam String industrySubCategoryName,
                                                                         @RequestParam Long industryCategoryId, @RequestParam Long userId) {
        try {
            IndustrySubCategory createdSubCategory = industrySubCategoryService.createSubIndustryCategory(industrySubCategoryName, industryCategoryId,userId);
            return new ResponseEntity<>(createdSubCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-sub-industry")
    public ResponseEntity<IndustrySubCategory> updateSubIndustryCategory(@RequestBody IndustrySubCategoryRequestDTO industrySubCategoryRequestDTO) {
        try {
            IndustrySubCategory updatedSubCategory = industrySubCategoryService.updateSubIndustryCategory(industrySubCategoryRequestDTO);
            return new ResponseEntity<>(updatedSubCategory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/fetch-all-enabled")
    public ResponseEntity<List<IndustrySubCategoryResponseDTO>> fetchAllEnabledSubCategories(@RequestParam Long industryCategoryId) {
        try {
            List<IndustrySubCategoryResponseDTO> subCategories = industrySubCategoryService.fetchAllEnabledSubCategories(industryCategoryId);
            return new ResponseEntity<>(subCategories, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("/soft-delete")
    public ResponseEntity<IndustrySubCategory> softDeleteSubIndustryCategory(@RequestParam Long id) {
        try {
            IndustrySubCategory updatedSubCategory = industrySubCategoryService.softDeleteSubIndustryCategory(id);
            return new ResponseEntity<>(updatedSubCategory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
