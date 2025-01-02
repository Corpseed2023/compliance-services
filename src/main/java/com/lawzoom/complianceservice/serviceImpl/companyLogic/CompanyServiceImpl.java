package com.lawzoom.complianceservice.serviceImpl.companyLogic;

import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.companyDto.CompanyBusinessUnitDto;
import com.lawzoom.complianceservice.dto.companyDto.CompanyRequest;
import com.lawzoom.complianceservice.dto.companyDto.CompanyResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.exception.UnauthorizedException;
import com.lawzoom.complianceservice.model.businessActivityModel.BusinessActivity;
import com.lawzoom.complianceservice.model.businessActivityModel.IndustryCategory;
import com.lawzoom.complianceservice.model.businessActivityModel.IndustrySubCategory;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.companyModel.Company;
import com.lawzoom.complianceservice.model.companyModel.CompanyType;
import com.lawzoom.complianceservice.model.gstdetails.GstDetails;
import com.lawzoom.complianceservice.model.region.City;
import com.lawzoom.complianceservice.model.region.Country;
import com.lawzoom.complianceservice.model.region.LocatedAt;
import com.lawzoom.complianceservice.model.region.States;
import com.lawzoom.complianceservice.model.user.Roles;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.Subscription;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.*;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessActivityRepository;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessUnitRepository;
import com.lawzoom.complianceservice.repository.businessRepo.IndustryCategoryRepository;
import com.lawzoom.complianceservice.repository.businessRepo.IndustrySubCategoryRepository;
import com.lawzoom.complianceservice.repository.companyRepo.CompanyRepository;
import com.lawzoom.complianceservice.repository.companyRepo.CompanyTypeRepository;
import com.lawzoom.complianceservice.repository.companyRepo.LocatedAtRepository;
import com.lawzoom.complianceservice.repository.regionRepo.CityRepository;
import com.lawzoom.complianceservice.repository.regionRepo.CountryRepository;
import com.lawzoom.complianceservice.repository.regionRepo.StatesRepository;
import com.lawzoom.complianceservice.repository.team.TeamMemberRepository;
import com.lawzoom.complianceservice.service.companyService.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

//    @Autowired
//    private ComplianceMap complianceMap;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IndustryCategoryRepository industryCategoryRepository;

    @Autowired
    private IndustrySubCategoryRepository industrySubCategoryRepository;


    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private LocatedAtRepository locatedAtRepository;

    @Autowired
    private BusinessActivityRepository businessActivityRepository;

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private GstDetailsRepository gstDetailsRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;



    @Override
    public CompanyResponse createCompany(CompanyRequest companyRequest, Long userId) {
        // Validate User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(companyRequest.getSubscriberId())
                .orElseThrow(() -> new NotFoundException("Subscriber not found"));

        // Validate Company Type
        CompanyType companyType = companyTypeRepository.findById(companyRequest.getCompanyTypeId())
                .orElseThrow(() -> new NotFoundException("Company type not found"));

        // Validate Country, State, and City
        Country country = countryRepository.findById(companyRequest.getCountryId())
                .orElseThrow(() -> new NotFoundException("Country not found"));

        States state = statesRepository.findById(companyRequest.getStateId())
                .orElseThrow(() -> new NotFoundException("State not found"));

        City city = cityRepository.findById(companyRequest.getCityId())
                .orElseThrow(() -> new NotFoundException("City not found"));

        // Validate LocatedAt and BusinessActivity
        LocatedAt locatedAt = locatedAtRepository.findById(companyRequest.getLocatedAtId())
                .orElseThrow(() -> new NotFoundException("Location not found"));

        BusinessActivity businessActivity = businessActivityRepository.findById(companyRequest.getBusinessActivityId())
                .orElseThrow(() -> new NotFoundException("Business activity not found"));

        // Validate Industry and SubCategory (Optional)
        IndustryCategory industryCategory = industryCategoryRepository.findById(companyRequest.getIndustryId())
                .orElseThrow(() -> new NotFoundException("Industry not found"));

        IndustrySubCategory industrySubCategory = null;
        if (companyRequest.getIndustrySubCategoryId() != null) {
            industrySubCategory = industrySubCategoryRepository.findById(companyRequest.getIndustrySubCategoryId())
                    .orElseThrow(() -> new NotFoundException("Sub-industry not found"));
        }

        // Create and populate Company entity
        Company company = new Company();
        company.setCompanyName(companyRequest.getCompanyName());
        company.setBusinessEmailId(companyRequest.getBusinessEmailId());
        company.setCompanyType(companyType);
        company.setCountry(country);
        company.setState(state);
        company.setCity(city);
        company.setRegistrationNumber(companyRequest.getRegistrationNumber());
        company.setRegistrationDate(companyRequest.getRegistrationDate());
        company.setCinNumber(companyRequest.getCinNumber());
        company.setRemarks(companyRequest.getRemarks());
        company.setPinCode(companyRequest.getPinCode());
        company.setCompanyPanNumber(companyRequest.getPanNumber());
        company.setTurnover(companyRequest.getTurnover());
        company.setLocatedAt(locatedAt);
        company.setBusinessActivity(businessActivity);
        company.setIndustryCategory(industryCategory);
        company.setIndustrySubCategory(industrySubCategory);
        company.setPermanentEmployee(companyRequest.getPermanentEmployee());
        company.setContractEmployee(companyRequest.getContractEmployee());
        company.setOperationUnitAddress(companyRequest.getOperationUnitAddress());
        company.setSubscriber(subscriber);
        company.setSuperAdminId(user);
        company.setEnable(companyRequest.isEnable());
        company.setCreatedBy(user);
        company.setCreatedAt(new Date());
        company.setUpdatedAt(new Date());

        // Save company
        company = companyRepository.save(company);

        // Prepare and return response
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setCompanyId(company.getId());
        companyResponse.setCompanyName(company.getCompanyName());
        companyResponse.setBusinessEmailId(company.getBusinessEmailId());
        companyResponse.setCompanyState(state.getStateName());
        companyResponse.setCompanyCity(city.getCityName());
        companyResponse.setCompanyRegistrationNumber(company.getRegistrationNumber());
        companyResponse.setCompanyRegistrationDate(company.getRegistrationDate());
        companyResponse.setCompanyCINNumber(company.getCinNumber());
        companyResponse.setCompanyRemarks(company.getRemarks());
        companyResponse.setPinCode(company.getPinCode());
        companyResponse.setOperationUnitAddress(company.getOperationUnitAddress());
        companyResponse.setCompanyTurnover(company.getTurnover());
        companyResponse.setLocatedAt(locatedAt.getLocationName());
        companyResponse.setBusinessActivityName(businessActivity.getBusinessActivityName());
        companyResponse.setEnable(company.isEnable());
        companyResponse.setPermanentEmployee(company.getPermanentEmployee());
        companyResponse.setContractEmployee(company.getContractEmployee());

        return companyResponse;
    }



    @Override
    public List<CompanyBusinessUnitDto> getCompanyUnitComplianceDetails(Long userId) {
        return List.of();
    }

    @Override
    public List<BusinessUnitResponse> getUnitsByCompanies(Long companyId) {

            Company company = companyRepository.findById(companyId)
                    .orElseThrow(() -> new NotFoundException("Company not found with companyId: " + companyId));

        return null;
    }

    @Override
    public CompanyResponse updateCompany(CompanyRequest companyRequest, Long companyId, Long userId) {
        // Validate User
        User userData = userRepository.findByIdAndIsEnableAndNotDeleted(userId)
                .orElseThrow(() -> new NotFoundException("User not found with userId: " + userId));

        // Check if the user has SUPER_ADMIN or MASTER role
        Set<Roles> roles = userData.getRoles();
        if (roles == null || roles.stream().noneMatch(role -> "SUPER_ADMIN".equals(role.getRoleName())
                || "MASTER".equals(role.getRoleName()))) {
            throw new UnauthorizedException("User with userId: " + userId + " does not have SUPER_ADMIN or MASTER role.");
        }


        // Validate Company
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company not found with companyId: " + companyId));



        // Validate Company Type
        CompanyType companyType = companyTypeRepository.findById(companyRequest.getCompanyTypeId())
                .orElseThrow(() -> new NotFoundException("Company Type not found with ID: " + companyRequest.getCompanyTypeId()));

        // Validate State
        States state = statesRepository.findById(companyRequest.getCompanyStateId())
                .orElseThrow(() -> new NotFoundException("State not found with ID: " + companyRequest.getCompanyStateId()));

        // Validate City
        City city = cityRepository.findById(companyRequest.getCompanyCityId())
                .orElseThrow(() -> new NotFoundException("City not found with ID: " + companyRequest.getCompanyCityId()));

        // Validate LocatedAt
        LocatedAt locatedAt = locatedAtRepository.findById(companyRequest.getLocatedAtId())
                .orElseThrow(() -> new NotFoundException("LocatedAt not found with ID: " + companyRequest.getLocatedAtId()));

        // Validate Business Activity
        BusinessActivity businessActivity = businessActivityRepository.findById(companyRequest.getBusinessActivityId())
                .orElseThrow(() -> new NotFoundException("Business Activity not found with ID: " + companyRequest.getBusinessActivityId()));

        // Update Company Details
        company.setCompanyName(companyRequest.getCompanyName());
        company.setBusinessEmailId(companyRequest.getBusinessEmailId());
        company.setRegistrationNumber(companyRequest.getCompanyRegistrationNumber());
        company.setRegistrationDate(companyRequest.getCompanyRegistrationDate());
        company.setCinNumber(companyRequest.getCompanyCINNumber());
        company.setRemarks(companyRequest.getCompanyRemarks());
        company.setPinCode(companyRequest.getPinCode());
        company.setCompanyPanNumber(companyRequest.getCompanyPanNumber());
        company.setTurnover(companyRequest.getCompanyTurnover());
        company.setLocatedAt(locatedAt);
        company.setCompanyType(companyType);
        company.setState(state);
        company.setCity(city);
//    company.setDesignation(designation);
        company.setBusinessActivity(businessActivity);
        company.setPermanentEmployee(companyRequest.getPermanentEmployee());
        company.setContractEmployee(companyRequest.getContractEmployee());
        company.setOperationUnitAddress(companyRequest.getOperationUnitAddress());
        company.setEnable(companyRequest.isEnable());
        company.setUpdatedAt(new Date());

        // Save Updated Company
        company = companyRepository.save(company);

        // Prepare and Return Response
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setCompanyId(company.getId());
        companyResponse.setCompanyName(company.getCompanyName());
        companyResponse.setBusinessEmailId(company.getBusinessEmailId());
        companyResponse.setCompanyRegistrationNumber(company.getRegistrationNumber());
        companyResponse.setCompanyRegistrationDate(company.getRegistrationDate());
        companyResponse.setCompanyCINNumber(company.getCinNumber());
        companyResponse.setCompanyRemarks(company.getRemarks());
        companyResponse.setPinCode(company.getPinCode());
        companyResponse.setCompanyPanNumber(company.getCompanyPanNumber());
        companyResponse.setCompanyTurnover(company.getTurnover());
        companyResponse.setLocatedAt(locatedAt.getLocationName());
        companyResponse.setCompanyType(companyType.getCompanyTypeName());
        companyResponse.setCompanyState(state.getStateName());
        companyResponse.setCompanyCity(city.getCityName());
        companyResponse.setBusinessActivityName(businessActivity.getBusinessActivityName());
        companyResponse.setPermanentEmployee(company.getPermanentEmployee());
        companyResponse.setContractEmployee(company.getContractEmployee());
        companyResponse.setOperationUnitAddress(company.getOperationUnitAddress());
        companyResponse.setEnable(company.isEnable());
        companyResponse.setUserId(userId);

        return companyResponse;
    }

    @Override
    public void disableCompany(Long id , Long userId) throws NotFoundException {
        Optional<User> userData = userRepository.findByIdAndIsEnableAndNotDeleted(userId);

        if (userData == null) {
            throw new NotFoundException("User not found with userId: " + userId);
        }


        // Validate if the user has SUPER_ADMIN role
        Set<Roles> roles = userData.get().getRoles();
        if (roles == null || roles.stream().noneMatch(role -> "SUPER_ADMIN".equals(role.getRoleName()))) {
            throw new UnauthorizedException("User with userId: " + userId + " does not have SUPER_ADMIN role.");
        }

        Optional<Company> companyData = companyRepository.findById(id);
        if (companyData.isPresent()) {
            Company company = companyData.get();
            company.disable(); // Set isEnable to false
            companyRepository.save(company); // Save the updated company
        } else {
            throw new NotFoundException("Company with ID " + id + " not found");
        }
    }



//    @Override
//    public List<CompanyBusinessUnitDto> getCompanyUnitComplianceDetails(Long userId) {
//        return null;
//    }


    @Override
    public CompanyResponse getCompanyData(Long companyId) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);

        if (!companyOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Company is not active. Cannot create Business Unit.");
        }

        Company company = companyOptional.get();

        // Create a new CompanyResponse instance and set fields manually
        CompanyResponse companyResponse = new CompanyResponse();

        companyResponse.setCompanyId(company.getId());
//        companyResponse.setUserId(company.getUser().getId());
        companyResponse.setCompanyType(company.getCompanyType() != null ? company.getCompanyType().toString() : null);
        companyResponse.setCompanyName(company.getCompanyName());
        companyResponse.setBusinessEmailId(company.getBusinessEmailId());
//        companyResponse.setDesignation(company.getDesignation() != null ? company.getDesignation().toString() : null);
        companyResponse.setCompanyState(company.getState() != null ? company.getState().toString() : null);
        companyResponse.setCompanyCity(company.getCity() != null ? company.getCity().toString() : null);
        companyResponse.setCompanyRegistrationNumber(company.getRegistrationNumber());
        companyResponse.setCompanyRegistrationDate(company.getRegistrationDate());
        companyResponse.setCompanyCINNumber(company.getCinNumber());
        companyResponse.setCompanyRemarks(company.getRemarks());
        companyResponse.setPinCode(company.getPinCode());
        companyResponse.setCompanyPanNumber(company.getCompanyPanNumber());
        companyResponse.setCompanyTurnover(company.getTurnover());
        companyResponse.setLocatedAt(company.getLocatedAt() != null ? company.getLocatedAt().toString() : null);
        companyResponse.setBusinessActivityName(company.getBusinessActivity() != null ? company.getBusinessActivity().toString() : null);
        companyResponse.setEnable(company.isEnable());
        companyResponse.setPermanentEmployee(company.getPermanentEmployee());
        companyResponse.setContractEmployee(company.getContractEmployee());
        companyResponse.setOperationUnitAddress(company.getOperationUnitAddress());
        companyResponse.setIndustryName(company.getIndustryCategory().getIndustryName());
        companyResponse.setIndustrySubCategoryName(company.getIndustrySubCategory().getIndustrySubCategoryName());

        return companyResponse;
    }


    private boolean companyOptional(Long companyId) {
        Optional<Company> companyData = companyRepository.findById(companyId);
        return companyData.map(Company::isEnable).orElse(false);
    }


    @Override
    public List<CompanyResponse> fetchAllCompanies(Long userId, Long subscriptionId) {

        User user = null;

        // Step 1: Validate and fetch the user if userId is provided
        if (userId != null) {
            user = userRepository.findByIdAndIsEnableAndNotDeleted(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        }

        // Step 2: Validate and fetch the subscription
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Subscription not found with ID: " + subscriptionId));

        // Step 3: Check if the subscription is associated with the provided user
        if (user != null && !subscription.getUsers().contains(user)) {
            throw new NotFoundException("The subscription is not associated with the provided user.");
        }

        // Step 4: Fetch companies associated with the subscription and user
        List<Company> companies = user == null
                ? companyRepository.findBySubscription(subscription)
                : companyRepository.findBySuperAdminIdAndSubscription(user.getId(), subscription.getId());

        if (companies.isEmpty()) {
            throw new NotFoundException("No companies found for the given criteria.");
        }

        // Step 5: Map Company entities to CompanyResponse DTOs
        return companies.stream().map(company -> {
            CompanyResponse response = new CompanyResponse();
            response.setCompanyId(company.getId());
            response.setUserId(company.getCreatedBy().getId());
            response.setCompanyTypeId(company.getCompanyType().getId());
            response.setCompanyType(company.getCompanyType() != null ? company.getCompanyType().getCompanyTypeName() : null);
            response.setCompanyName(company.getCompanyName());
            response.setBusinessEmailId(company.getBusinessEmailId());
            response.setCountryId(company.getCountry().getId());
            response.setCountryName(company.getCountry() != null ? company.getCountry().getCountryName() : null);
            response.setStateId(company.getState().getId());
            response.setCompanyState(company.getState() != null ? company.getState().getStateName() : null);
            response.setCompanyCityId(company.getCity().getId());
            response.setCompanyCity(company.getCity() != null ? company.getCity().getCityName() : null);
            response.setCompanyRegistrationNumber(company.getRegistrationNumber());
            response.setCompanyRegistrationDate(company.getRegistrationDate());
            response.setCompanyCINNumber(company.getCinNumber());
            response.setCompanyRemarks(company.getRemarks());
            response.setPinCode(company.getPinCode());
            response.setCompanyPanNumber(company.getCompanyPanNumber());
            response.setCompanyTurnover(company.getTurnover());
            response.setLocatedAtId(company.getLocatedAt().getId());
            response.setLocatedAt(company.getLocatedAt() != null ? company.getLocatedAt().getLocationName() : null);
            response.setBusinessActivityNameId(company.getBusinessActivity().getId());
            response.setBusinessActivityName(company.getBusinessActivity() != null ? company.getBusinessActivity().getBusinessActivityName() : null);
            response.setIndustryName(company.getIndustryCategory() != null ? company.getIndustryCategory().getIndustryName() : null);
            response.setIndustrySubCategoryName(company.getIndustrySubCategory() != null ? company.getIndustrySubCategory().getIndustrySubCategoryName() : null);
            response.setEnable(company.isEnable());
            response.setPermanentEmployee(company.getPermanentEmployee());
            response.setContractEmployee(company.getContractEmployee());
            response.setOperationUnitAddress(company.getOperationUnitAddress());
            response.setIndustryNameId(company.getIndustryCategory().getId());
            response.setIndustrySubCategoryNameId(company.getIndustrySubCategory().getId());

            // Fetch and set additional counts
            Long stateCount = businessUnitRepository.countDistinctStatesByCompanyId(company.getId());
            Long gstDetailsCount = gstDetailsRepository.countByCompanyId(company.getId());
            Long businessUnitCount = businessUnitRepository.countByCompanyId(company.getId());

            response.setStateCount(stateCount);
            response.setGstDetailsCount(gstDetailsCount);
            response.setBusinessUnitCount(businessUnitCount);

            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public CompanyResponse fetchCompany(Long userId, Long subscriptionId, Long companyId) {
        // Fetch user and subscription to ensure they exist
        User user = userRepository.findByIdAndIsEnableAndNotDeleted(userId)
                .orElseThrow(() -> new NotFoundException("Super Admin not found with ID: " + userId));

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Subscription not found with ID: " + subscriptionId));

        Company company = companyRepository.findByEnabledAndNotDeleted(companyId)
                .orElseThrow(() -> new NotFoundException("No company found for the given"));


        // Map the Company entity to a CompanyResponse DTO
        CompanyResponse response = new CompanyResponse();
        response.setCompanyId(company.getId());
        response.setUserId(company.getCreatedBy().getId());
        response.setCompanyType(company.getCompanyType() != null ? company.getCompanyType().getCompanyTypeName() : null);
        response.setCompanyName(company.getCompanyName());
        response.setBusinessEmailId(company.getBusinessEmailId());
        response.setCountryName(company.getCountry() != null ? company.getCountry().getCountryName() : null);
        response.setCompanyState(company.getState() != null ? company.getState().getStateName() : null);
        response.setCompanyCity(company.getCity() != null ? company.getCity().getCityName() : null);
        response.setCompanyRegistrationNumber(company.getRegistrationNumber());
        response.setCompanyRegistrationDate(company.getRegistrationDate());
        response.setCompanyCINNumber(company.getCinNumber());
        response.setCompanyRemarks(company.getRemarks());
        response.setPinCode(company.getPinCode());
        response.setCompanyPanNumber(company.getCompanyPanNumber());
        response.setCompanyTurnover(company.getTurnover());
        response.setLocatedAt(company.getLocatedAt() != null ? company.getLocatedAt().getLocationName() : null);
        response.setBusinessActivityName(company.getBusinessActivity() != null ? company.getBusinessActivity().getBusinessActivityName() : null);
        response.setIndustryName(company.getIndustryCategory() != null ? company.getIndustryCategory().getIndustryName() : null);
        response.setIndustrySubCategoryName(company.getIndustrySubCategory() != null ? company.getIndustrySubCategory().getIndustrySubCategoryName() : null);
        response.setEnable(company.isEnable());
        response.setPermanentEmployee(company.getPermanentEmployee());
        response.setContractEmployee(company.getContractEmployee());
        response.setOperationUnitAddress(company.getOperationUnitAddress());
        response.setIndustryNameId(company.getIndustryCategory().getId());
        response.setIndustrySubCategoryNameId(company.getIndustrySubCategory().getId());
        response.setCompanyTypeId(company.getCompanyType().getId());
        response.setCountryId(company.getCountry().getId());
        response.setStateId(company.getState().getId());
        response.setCountryId(company.getCountry().getId());
        response.setLocatedAtId(company.getLocatedAt().getId());
        response.setBusinessActivityNameId(company.getBusinessActivity().getId());

        // Fetch and set additional counts
        Long stateCount = businessUnitRepository.countDistinctStatesByCompanyId(company.getId());
        Long gstDetailsCount = gstDetailsRepository.countByCompanyId(company.getId());
        Long businessUnitCount = businessUnitRepository.countByCompanyId(company.getId());

        response.setStateCount(stateCount);
        response.setGstDetailsCount(gstDetailsCount);
        response.setBusinessUnitCount(businessUnitCount);




        response.setCompanyCityId(company.getCity().getId());

        return response;
    }

//    @Override
//    public void assignTeamMembersToCompany(Long companyId, List<Long> teamMemberIds) {
//
//        Company company = companyRepository.findEnabledAndNotDeletedCompanyById(companyId)
//                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + companyId));
//
//        for (Long teamMemberId : teamMemberIds) {
//            TeamMember teamMember = teamMemberRepository.findById(teamMemberId)
//                    .orElseThrow(() -> new NotFoundException("Team member not found with ID: " + teamMemberId));
//
//            teamMember.getCompanies().add(company); // Update inverse side
//            company.getTeamMembers().add(teamMember); // Update owning side
//        }
//
//        companyRepository.save(company);
//
//    }


//    @Override
//    public List<TeamMember> getTeamMembersByCompany(Long companyId) {
//        Company company = companyRepository.findEnabledAndNotDeletedCompanyById(companyId)
//                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + companyId));
//
//        return new ArrayList<>(company.getTeamMembers());
//    }

}



