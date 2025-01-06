package com.lawzoom.complianceservice.service.industry;


import com.lawzoom.complianceservice.dto.industryDTO.request.IndustryCategoryRequestDTO;
import com.lawzoom.complianceservice.dto.industryDTO.response.IndustryCategoryResponseDTO;
import com.lawzoom.complianceservice.model.businessActivityModel.IndustryCategory;

import java.util.List;

public interface IndustryCategoryService {
    
    IndustryCategory createIndustryCategory(String industryName, Long userId);

    IndustryCategory updateIndustryCategory(IndustryCategoryRequestDTO industryCategoryRequestDTO);

    List<IndustryCategoryResponseDTO> fetchAllEnabledCategories();
}
