package com.lawzoom.complianceservice.serviceImpl.industry;


import com.lawzoom.complianceservice.dto.industryDTO.request.BusinessActivityRequestDTO;
import com.lawzoom.complianceservice.dto.industryDTO.response.BusinessActivityResponseDTO;
import com.lawzoom.complianceservice.model.businessActivityModel.BusinessActivity;
import com.lawzoom.complianceservice.model.businessActivityModel.IndustrySubCategory;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessActivityRepository;
import com.lawzoom.complianceservice.repository.businessRepo.IndustrySubCategoryRepository;
import com.lawzoom.complianceservice.service.industry.BusinessActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusinessActivityServiceImpl implements BusinessActivityService {

    @Autowired
    private BusinessActivityRepository businessActivityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IndustrySubCategoryRepository industrySubCategoryRepository;

    @Override
    public BusinessActivity createBusinessActivity(String businessActivityName, Long userId, Long industrySubCategoryId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User does not exist with ID: " + userId);
        }

        IndustrySubCategory industrySubCategory = industrySubCategoryRepository.findById(industrySubCategoryId)
                .orElseThrow(() -> new RuntimeException("IndustrySubCategory not found with ID: " + industrySubCategoryId));

        BusinessActivity businessActivity =new BusinessActivity();

        businessActivity.setIndustrySubCategory(industrySubCategory);
        businessActivity.setCreatedAt(new Date());
        businessActivity.setBusinessActivityName(businessActivityName);
        businessActivity.setUpdatedAt(new Date());
        businessActivity.setDeleted(false);
        businessActivity.setDate(LocalDate.now());
        businessActivity.setEnable(true);
        return businessActivityRepository.save(businessActivity);
    }

    @Override
    public BusinessActivity updateBusinessActivity(BusinessActivityRequestDTO businessActivityRequestDTO) {
        if (!userRepository.existsById(businessActivityRequestDTO.getUserId())) {
            throw new RuntimeException("User does not exist with ID: " + businessActivityRequestDTO.getUserId());
        }

        BusinessActivity existingBusinessActivity = businessActivityRepository.findById(businessActivityRequestDTO.getId())
                .orElseThrow(() -> new RuntimeException("BusinessActivity not found with ID: " + businessActivityRequestDTO.getId()));

        IndustrySubCategory industrySubCategory = industrySubCategoryRepository.findById(businessActivityRequestDTO.getIndustrySubCategoryId())
                .orElseThrow(() -> new RuntimeException("IndustrySubCategory not found with ID: " + businessActivityRequestDTO.getIndustrySubCategoryId()));

        existingBusinessActivity.setBusinessActivityName(businessActivityRequestDTO.getBusinessActivityName());
        existingBusinessActivity.setIndustrySubCategory(industrySubCategory);
        existingBusinessActivity.setUpdatedAt(new Date());

        return businessActivityRepository.save(existingBusinessActivity);
    }

    @Override
    public List<BusinessActivityResponseDTO> getAllEnabledAndNonDeletedActivities(Long industrySubCategoryId) {
        List<BusinessActivity> activities = businessActivityRepository.findByIsEnableTrueAndIsDeletedFalse(industrySubCategoryId);

        // Map the activities to BusinessActivityResponseDTO without using constructor
        return activities.stream()
                .map(activity -> {
                    BusinessActivityResponseDTO dto = new BusinessActivityResponseDTO();
                    dto.setId(activity.getId());
                    dto.setBusinessActivityName(activity.getBusinessActivityName());
                    return dto;
                })
                .collect(Collectors.toList());
    }


    public boolean softDeleteBusinessActivity(Long id) {
        Optional<BusinessActivity> optionalActivity = businessActivityRepository.findById(id);

        if (optionalActivity.isPresent()) {
            BusinessActivity activity = optionalActivity.get();

            if (!activity.isDeleted()) {
                activity.setDeleted(true);
                activity.setEnable(false);
                businessActivityRepository.save(activity);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<BusinessActivityResponseDTO> getActiveBusinessActivities(String searchTerm) {
        List<BusinessActivity> activities;

        // Check if searchTerm is null, blank, or empty
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            activities = businessActivityRepository.findActiveBusinessActivities(null);
        } else {
            activities = businessActivityRepository.findActiveBusinessActivities(searchTerm);
        }

        // Map BusinessActivity entities to BusinessActivityResponseDTOs manually
        return activities.stream()
                .map(activity -> {
                    BusinessActivityResponseDTO dto = new BusinessActivityResponseDTO();

                    // Manually set each field
                    dto.setId(activity.getId());
                    dto.setBusinessActivityName(activity.getBusinessActivityName());
                    dto.setIndustrySubCategoryId(activity.getIndustrySubCategory().getId());  // Manually set the subcategory ID
                    dto.setIndustryCategoryId(activity.getIndustrySubCategory().getIndustryCategory().getId());        // Manually set the category ID

                    return dto;
                })
                .collect(Collectors.toList());
    }



}
