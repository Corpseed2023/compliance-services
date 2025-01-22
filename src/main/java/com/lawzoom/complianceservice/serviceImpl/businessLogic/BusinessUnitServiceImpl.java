package com.lawzoom.complianceservice.serviceImpl.businessLogic;


import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.businessUnitDto.businessUnitRequest.BusinessUnitRequest;
import com.lawzoom.complianceservice.dto.businessUnitDto.businessUnitRequest.UnitRequest;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.businessActivityModel.BusinessActivity;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.companyModel.Company;
import com.lawzoom.complianceservice.model.gstdetails.GstDetails;
import com.lawzoom.complianceservice.model.region.City;
import com.lawzoom.complianceservice.model.region.LocatedAt;
import com.lawzoom.complianceservice.model.region.States;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.DesignationRepository;
import com.lawzoom.complianceservice.repository.GstDetailsRepository;
import com.lawzoom.complianceservice.repository.SubscriptionRepository;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessActivityRepository;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessUnitRepository;
import com.lawzoom.complianceservice.repository.businessRepo.IndustryCategoryRepository;
import com.lawzoom.complianceservice.repository.businessRepo.IndustrySubCategoryRepository;
import com.lawzoom.complianceservice.repository.companyRepo.CompanyRepository;
import com.lawzoom.complianceservice.repository.companyRepo.CompanyTypeRepository;
import com.lawzoom.complianceservice.repository.companyRepo.LocatedAtRepository;
import com.lawzoom.complianceservice.repository.regionRepo.CityRepository;
import com.lawzoom.complianceservice.repository.regionRepo.StatesRepository;
import com.lawzoom.complianceservice.service.businessService.BusinessUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class BusinessUnitServiceImpl implements BusinessUnitService {

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

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
    private GstDetailsRepository gstDetailsRepository;

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
    private SubscriptionRepository subscriptionRepository;

    @Override
    public BusinessUnitResponse createBusinessUnit(BusinessUnitRequest businessUnitRequest, Long gstDetailsId) {
        // Validate User
        User user = userRepository.findActiveUserById(businessUnitRequest.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }


        // Validate Subscriber
        Subscriber subscriber = user.getSubscriber();
        if (subscriber == null || !subscriber.getId().equals(businessUnitRequest.getSubscriberID())) {
            throw new NotFoundException("User is not associated with the provided subscriber ID.");
        }

        // Validate GST Details
        GstDetails gstDetails = gstDetailsRepository.findByIdAndEnabledAndNotDeleted(gstDetailsId)
                .orElseThrow(() -> new NotFoundException("GST Details not found or inactive/deleted with ID: " + gstDetailsId));

        // Validate that GST Details belong to a Company associated with the Subscriber
        Company company = gstDetails.getCompany();
        if (company == null || !company.getSubscriber().getId().equals(subscriber.getId())) {
            throw new NotFoundException("GST Details do not belong to a company associated with the subscriber.");
        }

        // Validate State
        States state = statesRepository.findById(businessUnitRequest.getStateId())
                .orElseThrow(() -> new NotFoundException("State not found with ID: " + businessUnitRequest.getStateId()));

        // Validate City
        City city = cityRepository.findById(businessUnitRequest.getCityId())
                .orElseThrow(() -> new NotFoundException("City not found with ID: " + businessUnitRequest.getCityId()));

        // Validate LocatedAt
        LocatedAt locatedAt = locatedAtRepository.findById(businessUnitRequest.getLocatedAtId())
                .orElseThrow(() -> new NotFoundException("LocatedAt not found with ID: " + businessUnitRequest.getLocatedAtId()));

        // Validate Business Activity
        BusinessActivity businessActivity = businessActivityRepository.findById(businessUnitRequest.getBusinessActivityId())
                .orElseThrow(() -> new NotFoundException("Business Activity not found with ID: " + businessUnitRequest.getBusinessActivityId()));

        // Create and populate Business Unit entity
        BusinessUnit newBusinessUnit = new BusinessUnit();
        newBusinessUnit.setAddress(businessUnitRequest.getAddress());
        newBusinessUnit.setCity(city);
        newBusinessUnit.setLocatedAt(locatedAt);
        newBusinessUnit.setCreatedAt(new Date());
        newBusinessUnit.setUpdatedAt(new Date());
        newBusinessUnit.setDate(LocalDate.now());
        newBusinessUnit.setEnable(true);
        newBusinessUnit.setState(state);
        newBusinessUnit.setGstNumber(businessUnitRequest.getGstNumber());
        newBusinessUnit.setGstDetails(gstDetails);
        newBusinessUnit.setCreatedBy(user);
        newBusinessUnit.setUpdatedBy(user);
        newBusinessUnit.setBusinessActivity(businessActivity);

        // Save Business Unit
        BusinessUnit savedBusinessUnit = businessUnitRepository.save(newBusinessUnit);

        // Prepare response
        return mapBusinessUnitToResponse(savedBusinessUnit, company);
    }

    private BusinessUnitResponse mapBusinessUnitToResponse(BusinessUnit businessUnit, Company company) {
        BusinessUnitResponse response = new BusinessUnitResponse();
        response.setId(businessUnit.getId());
        response.setCompanyId(company.getId());
        response.setCompanyName(company.getCompanyName());
        response.setCityId(businessUnit.getCity().getId());
        response.setCity(businessUnit.getCity().getCityName());
        response.setLocatedAtId(businessUnit.getLocatedAt().getId());
        response.setLocatedAt(businessUnit.getLocatedAt().getLocationName());
        response.setAddress(businessUnit.getAddress());
        response.setCreatedAt(businessUnit.getCreatedAt());
        response.setUpdatedAt(businessUnit.getUpdatedAt());
        response.setEnable(businessUnit.isEnable());
        response.setStateId(businessUnit.getState().getId());
        response.setState(businessUnit.getState().getStateName());
        response.setGstNumber(businessUnit.getGstNumber());
        response.setBusinessActivityId(businessUnit.getBusinessActivity().getId());
        response.setBusinessActivity(businessUnit.getBusinessActivity().getBusinessActivityName());
        return response;
    }




    @Override
    public List<BusinessUnitResponse> getCompanyUnits(UnitRequest unitRequest) {

        User user = userRepository.findActiveUserById(unitRequest.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }

        // Fetch the company details and validate
        Company company = companyRepository.findById(unitRequest.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found with companyId: " + unitRequest.getCompanyId()));

        // Extract business units
        List<BusinessUnit> businessUnits = company.getGstDetails().stream()
                .flatMap(gstDetails -> gstDetails.getBusinessUnits().stream())
                .collect(Collectors.toList());

        // Map business units to response objects
        List<BusinessUnitResponse> responses = new ArrayList<>();
        for (BusinessUnit businessUnit : businessUnits) {
            BusinessUnitResponse response = new BusinessUnitResponse();
            response.setId(businessUnit.getId());
            response.setCompanyId(company.getId());
            response.setLocatedAt(businessUnit.getLocatedAt() != null ? businessUnit.getLocatedAt().getLocationName() : null);

            response.setBusinessActivityId(businessUnit.getBusinessActivity() != null ? businessUnit.getBusinessActivity().getId() : null );
            response.setBusinessActivity(businessUnit.getBusinessActivity() != null ? businessUnit.getBusinessActivity().getBusinessActivityName() :null);
            response.setCityId(businessUnit.getCity().getId());
            response.setCity(businessUnit.getCity().getCityName());
            response.setLocatedAtId(businessUnit.getLocatedAt().getId());
            response.setLocatedAt(businessUnit.getLocatedAt().getLocationName());
            response.setAddress(businessUnit.getAddress());
            response.setCreatedAt(businessUnit.getCreatedAt());
            response.setUpdatedAt(businessUnit.getUpdatedAt());

            response.setEnable(businessUnit.isEnable());
            response.setGstNumber(businessUnit.getGstNumber());
            response.setStateId(businessUnit.getState().getId());
            response.setState(businessUnit.getState().getStateName());
            responses.add(response);
        }

        return responses;
    }


    @Override
    public BusinessUnitResponse updateBusinessUnit(Long businessUnitId, BusinessUnitRequest businessUnitRequest) {

        User user = userRepository.findActiveUserById(businessUnitRequest.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }




        // Validate and fetch state
        States state = statesRepository.findById(businessUnitRequest.getStateId())
                .orElseThrow(() -> new NotFoundException("State not found with ID: " + businessUnitRequest.getStateId()));

        // Validate and fetch city
        City city = cityRepository.findById(businessUnitRequest.getCityId())
                .orElseThrow(() -> new NotFoundException("City not found with ID: " + businessUnitRequest.getCityId()));

        // Validate and fetch location
        LocatedAt locatedAt = locatedAtRepository.findById(businessUnitRequest.getLocatedAtId())
                .orElseThrow(() -> new NotFoundException("LocatedAt not found with ID: " + businessUnitRequest.getLocatedAtId()));

        // Validate and fetch existing business unit
        BusinessUnit existingBusinessUnit = businessUnitRepository.findById(businessUnitId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business Unit not found with ID: " + businessUnitId));

        // Update the business unit fields
        existingBusinessUnit.setCity(city);
        existingBusinessUnit.setLocatedAt(locatedAt);
        existingBusinessUnit.setAddress(businessUnitRequest.getAddress());
        existingBusinessUnit.setGstNumber(businessUnitRequest.getGstNumber());
        existingBusinessUnit.setState(state);
        existingBusinessUnit.setUpdatedAt(new Date()); // Ensure updated timestamp is set
        existingBusinessUnit.setCreatedBy(user);


        // Save the updated business unit
        BusinessUnit savedBusinessUnit = businessUnitRepository.save(existingBusinessUnit);

        // Create and populate the response
        BusinessUnitResponse response = new BusinessUnitResponse();
        response.setId(savedBusinessUnit.getId());
        response.setCity(savedBusinessUnit.getCity().getCityName());
        response.setLocatedAt(savedBusinessUnit.getLocatedAt().getLocationName());
        response.setAddress(savedBusinessUnit.getAddress());
        response.setCreatedAt(savedBusinessUnit.getCreatedAt());
        response.setUpdatedAt(savedBusinessUnit.getUpdatedAt());
        response.setEnable(savedBusinessUnit.isEnable());
        response.setState(savedBusinessUnit.getState().getStateName());
        response.setGstNumber(savedBusinessUnit.getGstNumber());
        response.setCityId(savedBusinessUnit.getCity().getId());
        response.setStateId(savedBusinessUnit.getState().getId());
        response.setLocatedAtId(savedBusinessUnit.getLocatedAt().getId());

        return response;
    }

    @Override
    public List<BusinessUnitResponse> getAllBusinessUnits(Long gstDetailsId, Long userId) {
        // Step 1: Validate User
        User user = userRepository.findActiveUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("Error: User not found!");
        }

        // Step 2: Validate Subscriber
        Subscriber subscriber = user.getSubscriber();
        if (subscriber == null) {
            throw new NotFoundException("User is not associated with any subscriber.");
        }

        // Step 3: Fetch GST Details
        GstDetails gstDetails = gstDetailsRepository.findByIdAndEnabledAndNotDeleted(gstDetailsId)
                .orElseThrow(() -> new NotFoundException("GST Details not found or inactive/deleted with ID: " + gstDetailsId));

        // Step 4: Validate that GST Details belong to a Company associated with the Subscriber
        Company company = gstDetails.getCompany();
        if (company == null || !company.getSubscriber().getId().equals(subscriber.getId())) {
            throw new NotFoundException("GST Details do not belong to a company associated with the subscriber.");
        }

        // Step 5: Fetch the list of Business Units
        List<BusinessUnit> businessUnits = businessUnitRepository.findAllByGstDetails(gstDetails);
        if (businessUnits.isEmpty()) {
            throw new NotFoundException("No business units found for GST details ID: " + gstDetailsId);
        }

        // Step 6: Map Business Units to Responses
        List<BusinessUnitResponse> responseList = businessUnits.stream().map(businessUnit -> {
            BusinessUnitResponse response = new BusinessUnitResponse();

            // Map each field, checking for nulls
            response.setId(businessUnit.getId());
            response.setCompanyId(company.getId());
            response.setCompanyName(company.getCompanyName());
            response.setCity(businessUnit.getCity() != null ? businessUnit.getCity().getCityName() : null);
            response.setCityId(businessUnit.getCity() != null ? businessUnit.getCity().getId() : null);
            response.setLocatedAt(businessUnit.getLocatedAt() != null ? businessUnit.getLocatedAt().getLocationName() : null);
            response.setLocatedAtId(businessUnit.getLocatedAt() != null ? businessUnit.getLocatedAt().getId() : null);
            response.setAddress(businessUnit.getAddress());
            response.setCreatedAt(businessUnit.getCreatedAt());
            response.setUpdatedAt(businessUnit.getUpdatedAt());
            response.setEnable(businessUnit.isEnable());
            response.setGstNumber(businessUnit.getGstDetails().getGstNumber());
            response.setState(businessUnit.getState() != null ? businessUnit.getState().getStateName() : null);
            response.setStateId(businessUnit.getState() != null ? businessUnit.getState().getId() : null);
            response.setBusinessActivity(businessUnit.getBusinessActivity() != null ? businessUnit.getBusinessActivity().getBusinessActivityName() : null);
            response.setBusinessActivityId(businessUnit.getBusinessActivity() != null ? businessUnit.getBusinessActivity().getId() : null);

            return response;
        }).collect(Collectors.toList());

        return responseList;
    }


    @Override
    public BusinessUnitResponse getBusinessUnitData(Long businessUnitId) {
        // Fetch the BusinessUnit
        Optional<BusinessUnit> businessUnitOptional = businessUnitRepository.findById(businessUnitId);

        BusinessUnit businessUnit = businessUnitOptional.orElseThrow(() ->
                new NotFoundException("Business Unit not found for ID: " + businessUnitId));

        // Map BusinessUnit to BusinessUnitResponse inline
        BusinessUnitResponse response = new BusinessUnitResponse();
        response.setId(businessUnit.getId());
        response.setCity(businessUnit.getCity() != null ? businessUnit.getCity().getCityName() : null);
        response.setLocatedAt(businessUnit.getLocatedAt() != null ? businessUnit.getLocatedAt().getLocationName() : null);
        response.setAddress(businessUnit.getAddress());
        response.setCreatedAt(businessUnit.getCreatedAt());
        response.setUpdatedAt(businessUnit.getUpdatedAt());
        response.setEnable(businessUnit.isEnable());
        response.setGstNumber(businessUnit.getGstDetails() != null ? businessUnit.getGstDetails().getGstNumber() : null);
        response.setState(businessUnit.getState() != null ? businessUnit.getState().getStateName() : null);
        response.setCityId(businessUnit.getCity() != null ? businessUnit.getCity().getId() : null);
        response.setStateId(businessUnit.getState() != null ? businessUnit.getState().getId() : null);
        response.setLocatedAtId(businessUnit.getLocatedAt() != null ? businessUnit.getLocatedAt().getId() : null);
        response.setBusinessActivity(businessUnit.getBusinessActivity() != null ? businessUnit.getBusinessActivity().getBusinessActivityName() : null);
        response.setBusinessActivityId(businessUnit.getBusinessActivity() != null ? businessUnit.getBusinessActivity().getId() : null);

        return response;
    }



}
