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
    private GstDetailsRepository gstDetailsRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;



    @Override
    public CompanyResponse createCompany(CompanyRequest companyRequest, Long userId) {
        // Step 1: Validate User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        // Step 2: Check if the User has the required role
        boolean hasRequiredRole = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("MASTER")
                        || role.getRoleName().equalsIgnoreCase("SUPER_ADMIN"));
        if (!hasRequiredRole) {
            throw new UnauthorizedException("Only users with MASTER or SUPER_ADMIN roles can create a company");
        }

        // Step 3: Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(companyRequest.getSubscriberId())
                .orElseThrow(() -> new NotFoundException("Subscriber not found"));

        // Step 4: Validate Company Type
        CompanyType companyType = companyTypeRepository.findById(companyRequest.getCompanyTypeId())
                .orElseThrow(() -> new NotFoundException("Company type not found"));

        // Step 5: Validate Country, State, and City
        Country country = countryRepository.findById(companyRequest.getCountryId())
                .orElseThrow(() -> new NotFoundException("Country not found"));

        States state = statesRepository.findById(companyRequest.getStateId())
                .orElseThrow(() -> new NotFoundException("State not found"));

        City city = cityRepository.findById(companyRequest.getCityId())
                .orElseThrow(() -> new NotFoundException("City not found"));

        // Step 6: Validate LocatedAt and BusinessActivity
        LocatedAt locatedAt = locatedAtRepository.findById(companyRequest.getLocatedAtId())
                .orElseThrow(() -> new NotFoundException("Location not found"));

        BusinessActivity businessActivity = businessActivityRepository.findById(companyRequest.getBusinessActivityId())
                .orElseThrow(() -> new NotFoundException("Business activity not found"));

        // Step 7: Validate Industry and SubCategory
        IndustryCategory industryCategory = industryCategoryRepository.findById(companyRequest.getIndustryId())
                .orElseThrow(() -> new NotFoundException("Industry not found"));

        IndustrySubCategory industrySubCategory = null;
        if (companyRequest.getIndustrySubCategoryId() != null) {
            industrySubCategory = industrySubCategoryRepository.findById(companyRequest.getIndustrySubCategoryId())
                    .orElseThrow(() -> new NotFoundException("Sub-industry not found"));
        }

        // Step 8: Create and Save Company
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
        company.setEnable(true);
        company.setDate(LocalDate.now());
        company.setFirstName(companyRequest.getFirstName());
        company.setLastName(companyRequest.getLastName());

        // Save Company
        company = companyRepository.save(company);

        // Step 9: Save GST Details
        GstDetails gstDetails = new GstDetails();
        gstDetails.setCompany(company);
        gstDetails.setGstNumber(companyRequest.getGstNumber());
        gstDetails.setCountry(country);
        gstDetails.setState(state);
        gstDetails.setGstRegistrationDate(companyRequest.getRegistrationDate());
        gstDetails.setCreatedBy(user);
        gstDetails.setCreatedAt(new Date());
        gstDetails.setUpdatedAt(new Date());
        company.setEnable(true);

        gstDetails.setDate(LocalDate.now());
        gstDetailsRepository.save(gstDetails);

        // Step 10: Save Business Unit
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setGstDetails(gstDetails);
        businessUnit.setAddress(companyRequest.getOperationUnitAddress());
        businessUnit.setState(state);
        businessUnit.setCity(city);
        businessUnit.setBusinessActivity(businessActivity);
        businessUnit.setCreatedBy(user);
        businessUnit.setCreatedAt(new Date());
        businessUnit.setUpdatedAt(new Date());
        businessUnit.setEnable(true);

        businessUnit.setLocatedAt(company.getLocatedAt());
        businessUnit.setDate(LocalDate.now());
        businessUnitRepository.save(businessUnit);

        // Step 11: Prepare and Return Response
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setCompanyId(company.getId());
        companyResponse.setUserId(user.getId());
        companyResponse.setCompanyTypeId(companyType.getId());
        companyResponse.setCompanyType(companyType.getCompanyTypeName());
        companyResponse.setCompanyName(company.getCompanyName());
        companyResponse.setBusinessEmailId(company.getBusinessEmailId());
        companyResponse.setCountryId(country.getId());
        companyResponse.setCountryName(country.getCountryName());
        companyResponse.setStateId(state.getId());
        companyResponse.setCompanyState(state.getStateName());
        companyResponse.setCompanyCityId(city.getId());
        companyResponse.setCompanyCity(city.getCityName());
        companyResponse.setCompanyRegistrationNumber(company.getRegistrationNumber());
        companyResponse.setCompanyRegistrationDate(company.getRegistrationDate());
        companyResponse.setCompanyCINNumber(company.getCinNumber());
        companyResponse.setCompanyRemarks(company.getRemarks());
        companyResponse.setPinCode(company.getPinCode());
        companyResponse.setCompanyPanNumber(company.getCompanyPanNumber());
        companyResponse.setCompanyTurnover(company.getTurnover());
        companyResponse.setLocatedAtId(locatedAt.getId());
        companyResponse.setLocatedAt(locatedAt.getLocationName());
        companyResponse.setBusinessActivityNameId(businessActivity.getId());
        companyResponse.setBusinessActivityName(businessActivity.getBusinessActivityName());
        companyResponse.setIndustryNameId(industryCategory.getId());
        companyResponse.setIndustryName(industryCategory.getIndustryName());
        companyResponse.setIndustrySubCategoryNameId(industrySubCategory != null ? industrySubCategory.getId() : null);
        companyResponse.setIndustrySubCategoryName(industrySubCategory != null ? industrySubCategory.getIndustrySubCategoryName() : null);
        companyResponse.setEnable(company.isEnable());
        companyResponse.setPermanentEmployee(company.getPermanentEmployee());
        companyResponse.setContractEmployee(company.getContractEmployee());
        companyResponse.setOperationUnitAddress(company.getOperationUnitAddress());

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
        User user = userRepository.findActiveUserById(userId);
        if (user == null) {
            throw new NotFoundException("User not found with userId: " + userId);
        }


        // Check if the user has SUPER_ADMIN or MASTER role
        Set<Roles> roles = user.getRoles();
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

        // Validate Country, State, and City
        Country country = countryRepository.findById(companyRequest.getCountryId())
                .orElseThrow(() -> new NotFoundException("Country not found with ID: " + companyRequest.getCountryId()));

        States state = statesRepository.findById(companyRequest.getStateId())
                .orElseThrow(() -> new NotFoundException("State not found with ID: " + companyRequest.getStateId()));

        City city = cityRepository.findById(companyRequest.getCityId())
                .orElseThrow(() -> new NotFoundException("City not found with ID: " + companyRequest.getCityId()));

        // Validate LocatedAt
        LocatedAt locatedAt = locatedAtRepository.findById(companyRequest.getLocatedAtId())
                .orElseThrow(() -> new NotFoundException("LocatedAt not found with ID: " + companyRequest.getLocatedAtId()));

        // Validate Business Activity
        BusinessActivity businessActivity = businessActivityRepository.findById(companyRequest.getBusinessActivityId())
                .orElseThrow(() -> new NotFoundException("Business Activity not found with ID: " + companyRequest.getBusinessActivityId()));

        // Update Company Details
        company.setCompanyName(companyRequest.getCompanyName());
        company.setBusinessEmailId(companyRequest.getBusinessEmailId());
        company.setRegistrationNumber(companyRequest.getRegistrationNumber());
        company.setRegistrationDate(companyRequest.getRegistrationDate());
        company.setCinNumber(companyRequest.getCinNumber());
        company.setRemarks(companyRequest.getRemarks());
        company.setPinCode(companyRequest.getPinCode());
        company.setCompanyPanNumber(companyRequest.getPanNumber());
        company.setTurnover(companyRequest.getTurnover());
        company.setLocatedAt(locatedAt);
        company.setCompanyType(companyType);
        company.setCountry(country);
        company.setState(state);
        company.setCity(city);
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
    public void disableCompany(Long companyId, Long userId) throws NotFoundException {
        // Validate User
        User user = userRepository.findActiveUserById(userId);
        if (user == null) {
            throw new NotFoundException("User not found or not active with userId: " + userId);
        }

        // Validate if the user has SUPER_ADMIN role
        Set<Roles> roles = user.getRoles();
        if (roles == null || roles.stream().noneMatch(role -> "SUPER_ADMIN".equals(role.getRoleName()))) {
            throw new UnauthorizedException("User with userId: " + userId + " does not have SUPER_ADMIN role.");
        }

        // Validate and Disable Company
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company with ID " + companyId + " not found"));

        company.disable(); // Set isEnable to false
        companyRepository.save(company); // Save the updated company
    }


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
    public List<CompanyResponse> fetchAllCompanies(Long userId, Long subscriberId) {
        // Step 1: Validate User
        User user = userRepository.findActiveUserById(userId);
        if (user == null) {
            throw new NotFoundException("User not found or not active with userId: " + userId);
        }

        // Step 2: Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + subscriberId));

        // Step 3: Check if the user is associated with the subscriber
        if (!subscriber.getUsers().contains(user)) {
            throw new UnauthorizedException("User is not associated with the provided subscriber ID: " + subscriberId);
        }

        // Step 4: Fetch Companies under Subscriber
        List<Company> companies = companyRepository.findBySubscriberAndIsEnableAndIsDeletedFalse(subscriber.getId());

        // Step 5: Map Company entities to CompanyResponse DTOs
        return companies.stream()
                .map(this::mapCompanyToResponse)
                .collect(Collectors.toList());
    }


    private CompanyResponse mapCompanyToResponse(Company company) {
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
        response.setIndustrySubCategoryNameId(company.getIndustrySubCategory() != null ? company.getIndustrySubCategory().getId() : null);

        // Fetch and set additional counts
        Long stateCount = businessUnitRepository.countDistinctStatesByCompanyId(company.getId());
        Long gstDetailsCount = gstDetailsRepository.countByCompanyId(company.getId());
        Long businessUnitCount = businessUnitRepository.countByCompanyId(company.getId());

        response.setStateCount(stateCount);
        response.setGstDetailsCount(gstDetailsCount);
        response.setBusinessUnitCount(businessUnitCount);

        return response;
    }



    @Override
    public CompanyResponse fetchCompany(Long companyId, Long userId, Long subscriberId) {
        System.out.println("Start fetching company details. Company ID: " + companyId + ", User ID: " + userId + ", Subscriber ID: " + subscriberId);

        // Validate User
        System.out.println("Validating user with ID: " + userId);
        User user = userRepository.findActiveUserById(userId);
        if (user == null) {
            System.out.println("User not found or not active with ID: " + userId);
            throw new NotFoundException("User not found or not active with ID: " + userId);
        }
        System.out.println("User validated successfully. User ID: " + userId + ", User's Subscriber ID: " + user.getSubscriber().getId());

        // Validate Subscriber
        System.out.println("Validating subscriber with ID: " + subscriberId);
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> {
                    System.out.println("Subscriber not found with ID: " + subscriberId);
                    return new NotFoundException("Subscriber not found with ID: " + subscriberId);
                });
        System.out.println("Subscriber validated successfully. Subscriber ID: " + subscriberId);

        // Check if the user belongs to the subscriber
        System.out.println("Checking if the user belongs to the provided subscriber.");
        if (!user.getSubscriber().getId().equals(subscriber.getId())) {
            System.out.println("Unauthorized access. User ID: " + userId + ", User's Subscriber ID: " + user.getSubscriber().getId() + ", Provided Subscriber ID: " + subscriberId);
            throw new UnauthorizedException("User is not authorized to access companies for the provided subscriber.");
        }
        System.out.println("User belongs to the subscriber. Proceeding to validate the company.");

        // Validate Company
        System.out.println("Validating company with ID: " + companyId + " for subscriber ID: " + subscriberId);
        Company company = companyRepository.findByIdAndSubscriberIdAndIsEnableAndIsDeletedFalse(companyId, subscriberId)
                .orElseThrow(() -> {
                    System.out.println("Company not found with ID: " + companyId + " for subscriber ID: " + subscriberId);
                    return new NotFoundException("Company not found with ID: " + companyId + " for the given subscriber.");
                });
        System.out.println("Company validated successfully. Company ID: " + companyId);

        // Ensure the user's role is SUPER_ADMIN to fetch the company
        System.out.println("Checking if the user has SUPER_ADMIN role.");
        boolean isSuperAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("SUPER_ADMIN"));
        if (!isSuperAdmin) {
            System.out.println("Unauthorized access. Only SUPER_ADMIN users can fetch company details. User ID: " + userId);
            throw new UnauthorizedException("Only SUPER_ADMIN users can fetch company details.");
        }
        System.out.println("User has SUPER_ADMIN role. Proceeding to map company details.");

        // Map Company to CompanyResponse DTO
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
        response.setIndustrySubCategoryNameId(company.getIndustrySubCategory() != null ? company.getIndustrySubCategory().getId() : null);
        response.setCompanyTypeId(company.getCompanyType().getId());
        response.setCountryId(company.getCountry().getId());
        response.setStateId(company.getState().getId());
        response.setLocatedAtId(company.getLocatedAt().getId());
        response.setBusinessActivityNameId(company.getBusinessActivity().getId());
        response.setCompanyCityId(company.getCity().getId());

        // Additional counts
        System.out.println("Fetching additional counts for the company.");
        Long stateCount = businessUnitRepository.countDistinctStatesByCompanyId(company.getId());
        Long gstDetailsCount = gstDetailsRepository.countByCompanyId(company.getId());
        Long businessUnitCount = businessUnitRepository.countByCompanyId(company.getId());
        System.out.println("Additional counts fetched. State Count: " + stateCount + ", GST Details Count: " + gstDetailsCount + ", Business Unit Count: " + businessUnitCount);

        response.setStateCount(stateCount);
        response.setGstDetailsCount(gstDetailsCount);
        response.setBusinessUnitCount(businessUnitCount);

        System.out.println("Company details successfully fetched for Company ID: " + companyId);
        return response;
    }


}



