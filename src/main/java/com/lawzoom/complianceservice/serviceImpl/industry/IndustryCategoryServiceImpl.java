package com.lawzoom.complianceservice.serviceImpl.industry;

import com.lawzoom.complianceservice.dto.industryDTO.request.IndustryCategoryRequestDTO;
import com.lawzoom.complianceservice.dto.industryDTO.response.IndustryCategoryResponseDTO;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.businessActivityModel.IndustryCategory;
import com.lawzoom.complianceservice.model.user.User;
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

        // Step 1: Validate User
        User userData = userRepository.findActiveUserById(userId);

        if (userData == null) {
            throw new NotFoundException("User not found with userId: " + userId);
        }


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

        User userData = userRepository.findActiveUserById(industryCategoryRequestDTO.getUserId());

        if (userData == null) {
            throw new NotFoundException("User not found with userId: " + industryCategoryRequestDTO.getUserId());
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


