package com.lawzoom.complianceservice.service.industry;



import com.lawzoom.complianceservice.dto.industryDTO.request.BusinessActivityRequestDTO;
import com.lawzoom.complianceservice.dto.industryDTO.response.BusinessActivityResponseDTO;
import com.lawzoom.complianceservice.model.businessActivityModel.BusinessActivity;

import java.util.List;

public interface BusinessActivityService {

    BusinessActivity createBusinessActivity(String businessActivityName, Long userId, Long industrySubCategoryId);

    BusinessActivity updateBusinessActivity(BusinessActivityRequestDTO businessActivityRequestDTO);

    List<BusinessActivityResponseDTO> getAllEnabledAndNonDeletedActivities(Long industrySubCategoryId);

    boolean softDeleteBusinessActivity(Long id);

    List<BusinessActivityResponseDTO> getActiveBusinessActivities(String searchTerm);
}
