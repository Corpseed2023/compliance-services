package com.lawzoom.complianceservice.service.industry;

import com.lawzoom.complianceservice.dto.industryDTO.request.IndustrySubCategoryRequestDTO;
import com.lawzoom.complianceservice.dto.industryDTO.response.IndustrySubCategoryResponseDTO;
import com.lawzoom.complianceservice.model.businessActivityModel.IndustrySubCategory;

import java.util.List;

public interface IndustrySubCategoryService {
    

    IndustrySubCategory createSubIndustryCategory(String industrySubCategoryName, Long categoryId, Long userId);
    
    IndustrySubCategory updateSubIndustryCategory(IndustrySubCategoryRequestDTO industrySubCategoryRequestDTO);

    IndustrySubCategory softDeleteSubIndustryCategory(Long id);

    List<IndustrySubCategoryResponseDTO> fetchAllEnabledSubCategories(Long industryCategoryId);
}
