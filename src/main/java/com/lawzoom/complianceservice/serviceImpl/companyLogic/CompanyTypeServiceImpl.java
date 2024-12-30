package com.lawzoom.complianceservice.serviceImpl.companyLogic;


import com.lawzoom.complianceservice.dto.companyDto.CompanyTypeRequest;
import com.lawzoom.complianceservice.dto.companyDto.CompanyTypeResponse;
import com.lawzoom.complianceservice.model.companyModel.CompanyType;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.repository.companyRepo.CompanyTypeRepository;
import com.lawzoom.complianceservice.service.companyService.CompanyServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyTypeServiceImpl implements CompanyServiceType {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Override
    public CompanyTypeResponse createCompanyType(String companyTypeName, Long userId) {

        // Create a new CompanyType entity
        CompanyType companyType = new CompanyType();
        companyType.setCompanyTypeName(companyTypeName);
        companyType.setCreatedAt(new Date());
        companyType.setUpdatedAt(new Date());
        companyType.setEnable(true);
        companyType.setUserId(userId);
        companyType.setDate(LocalDate.now());
        companyType.setDeleted(false);

        // Save the CompanyType entity
        CompanyType savedCompanyType = companyTypeRepository.save(companyType);

        // Create and return the CompanyTypeResponse
        CompanyTypeResponse companyResponseType = new CompanyTypeResponse(
                savedCompanyType.getId(),
                savedCompanyType.getCompanyTypeName(),
                savedCompanyType.getCreatedAt(),
                savedCompanyType.getUpdatedAt(),
                savedCompanyType.isEnable(),
                savedCompanyType.getUserId()

        );

        return companyResponseType;
    }



    @Override
    public List<CompanyTypeResponse> getAllCompanyTypeNames() {
        List<CompanyType> companyTypes = companyTypeRepository.findAllCompanyTypeNames();

        return companyTypes.stream()
                .map(c -> new CompanyTypeResponse(c.getId(), c.getCompanyTypeName())) // Only map id and companyTypeName
                .collect(Collectors.toList());
    }



    @Override
    public CompanyTypeResponse updateCompanyType(Long id, CompanyTypeRequest companyTypeRequest) {
        CompanyType companyType = companyTypeRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new RuntimeException("CompanyType not found or is deleted"));

        companyType.setCompanyTypeName(companyTypeRequest.getCompanyTypeName());
        companyType.setUpdatedAt(new Date());
        companyType.setEnable(companyTypeRequest.isEnable());

        CompanyType updatedCompanyType = companyTypeRepository.save(companyType);

        return new CompanyTypeResponse(
                updatedCompanyType.getId(),
                updatedCompanyType.getCompanyTypeName(),
                updatedCompanyType.getCreatedAt(),
                updatedCompanyType.getUpdatedAt(),
                updatedCompanyType.isEnable(),
                updatedCompanyType.getUserId()
        );
    }

    @Override
    public void softDeleteCompanyType(Long id) {
        if (!companyTypeRepository.existsById(id)) {
            throw new RuntimeException("CompanyType not found");
        }
        companyTypeRepository.softDeleteById(id);
    }

}
