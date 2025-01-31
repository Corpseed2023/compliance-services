package com.lawzoom.complianceservice.serviceImpl.industry;

import com.lawzoom.complianceservice.dto.industryDTO.request.IndustrySubCategoryRequestDTO;
import com.lawzoom.complianceservice.dto.industryDTO.response.IndustrySubCategoryResponseDTO;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.businessActivityModel.IndustryCategory;
import com.lawzoom.complianceservice.model.businessActivityModel.IndustrySubCategory;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.repository.businessRepo.IndustryCategoryRepository;
import com.lawzoom.complianceservice.repository.businessRepo.IndustrySubCategoryRepository;
import com.lawzoom.complianceservice.service.industry.IndustrySubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IndustrySubCategoryServiceImpl implements IndustrySubCategoryService {

    @Autowired
    private IndustryCategoryRepository industryCategoryRepository;

    @Autowired
    private IndustrySubCategoryRepository industrySubCategoryRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public IndustrySubCategory createSubIndustryCategory(String industrySubCategoryName, Long categoryId,Long userId) {

        // Step 1: Validate User
        User userData = userRepository.findActiveUserById(userId);
        if (userData == null) {
            throw new NotFoundException("User not found with userId: " + userId);
        }

        Optional<IndustryCategory> industryCategoryOptional = industryCategoryRepository.findById(categoryId);

        IndustrySubCategory industrySubCategory = new IndustrySubCategory();

        if (industryCategoryOptional.isPresent() && industryCategoryOptional !=null) {

            industrySubCategory.setIndustryCategory(industryCategoryOptional.get());
            industrySubCategory.setIndustrySubCategoryName(industrySubCategoryName);
            industrySubCategory.setCreatedAt(new Date());
            industrySubCategory.setUpdatedAt(new Date());
            industrySubCategory.setDate(LocalDate.now());
            industrySubCategory.setEnable(true);
            industrySubCategory.setDeleted(false);
            return industrySubCategoryRepository.save(industrySubCategory);
        } else {
            throw new RuntimeException("Industry Category not found for ID: " + categoryId);
        }
    }



    @Override
    public IndustrySubCategory updateSubIndustryCategory(IndustrySubCategoryRequestDTO industrySubCategoryRequestDTO) {

        // Validate User
        User user = userRepository.findActiveUserById(industrySubCategoryRequestDTO.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }

        Optional<IndustrySubCategory> existingSubCategoryOptional = industrySubCategoryRepository.findById(industrySubCategoryRequestDTO.getId());
        if (existingSubCategoryOptional.isPresent()) {
            IndustrySubCategory existingSubCategory = existingSubCategoryOptional.get();
            existingSubCategory.setIndustrySubCategoryName(industrySubCategoryRequestDTO.getIndustrySubCategoryName());
            existingSubCategory.setUpdatedAt(new Date());
            return industrySubCategoryRepository.save(existingSubCategory);
        } else {
            throw new RuntimeException("Industry SubCategory not found for ID: " + industrySubCategoryRequestDTO.getId());
        }
    }

    public List<IndustrySubCategoryResponseDTO> fetchAllEnabledSubCategories(Long industryCategoryId) {
        List<IndustrySubCategory> subCategories = industrySubCategoryRepository.findByIsEnableTrueAndIndustryCategoryId(industryCategoryId);

        return subCategories.stream()
                .map(subCategory -> new IndustrySubCategoryResponseDTO(
                        subCategory.getId(),
                        subCategory.getIndustrySubCategoryName()
                )).collect(Collectors.toList());
    }



    public IndustrySubCategory softDeleteSubIndustryCategory(Long subCategoryId) {

        IndustrySubCategory subCategory = industrySubCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("Sub-Industry Category not found"));

        subCategory.setEnable(false);
        subCategory.setDeleted(true);
        subCategory.setUpdatedAt(new Date());

        return industrySubCategoryRepository.save(subCategory);
    }
}
