package com.lawzoom.complianceservice.serviceImpl.industry;

import com.lawzoom.complianceservice.dto.industryDTO.request.IndustryCategoryRequestDTO;
import com.lawzoom.complianceservice.dto.industryDTO.response.IndustryCategoryResponseDTO;
import com.lawzoom.complianceservice.model.User;
import com.lawzoom.complianceservice.model.businessActivityModel.IndustryCategory;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.repository.businessRepo.IndustryCategoryRepository;
import com.lawzoom.complianceservice.service.industry.IndustryCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IndustryCategoryServiceImpl implements IndustryCategoryService {

    @Autowired
    private IndustryCategoryRepository industryCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public IndustryCategory createIndustryCategory(String industryName, Long userId) {

        // Check if the user exists
        Optional<User> userOptional = userRepository.findByIdAndIsEnableAndNotDeleted(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Error: User not found!");
        }
        User user = userOptional.get();

        if (industryName == null || industryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: Industry name is required!");
        }

        IndustryCategory industryCategory = new IndustryCategory();
        industryCategory.setIndustryName(industryName);
        industryCategory.setCreatedAt(new Date());
        industryCategory.setUpdatedAt(new Date());
        industryCategory.setEnable(true);
        industryCategory.setDeleted(false);
        industryCategory.setDate(LocalDate.now());

        return industryCategoryRepository.save(industryCategory);
    }


    @Override
    public IndustryCategory updateIndustryCategory(IndustryCategoryRequestDTO industryCategoryRequestDTO) {

        Optional<User> userOptional = userRepository.findByIdAndIsEnableAndNotDeleted(industryCategoryRequestDTO.getUserId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Error: User not found!");
        }

        IndustryCategory existingCategory = industryCategoryRepository.findById(industryCategoryRequestDTO.getId())
                .orElseThrow(() -> new RuntimeException("Industry Category not found with ID: " + industryCategoryRequestDTO.getId()));
        existingCategory.setIndustryName(industryCategoryRequestDTO.getIndustryName());
        existingCategory.setUpdatedAt(new Date());
        return industryCategoryRepository.save(existingCategory);
    }

    @Override
    public List<IndustryCategoryResponseDTO> fetchAllEnabledCategories() {
        List<IndustryCategory> industryCategories = industryCategoryRepository.findByIsEnableTrueAndIsDeletedFalse();

        // Use streams to map IndustryCategory to IndustryCategoryResponseDTO
        return industryCategories.stream()
                .map(industryCategory -> new IndustryCategoryResponseDTO(
                        industryCategory.getId(),
                        industryCategory.getIndustryName()
                ))
                .collect(Collectors.toList());
    }



}


