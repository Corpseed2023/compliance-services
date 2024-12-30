package com.lawzoom.complianceservice.serviceImpl.businessLogic;


import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.businessUnitDto.businessUnitRequest.BusinessUnitRequest;
import com.lawzoom.complianceservice.dto.businessUnitDto.businessUnitRequest.UnitRequest;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.*;
import com.lawzoom.complianceservice.model.businessActivityModel.BusinessActivity;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.companyModel.Company;
import com.lawzoom.complianceservice.model.gstdetails.GstDetails;
import com.lawzoom.complianceservice.model.region.City;
import com.lawzoom.complianceservice.model.region.LocatedAt;
import com.lawzoom.complianceservice.model.region.States;
import com.lawzoom.complianceservice.repository.DesignationRepository;
import com.lawzoom.complianceservice.repository.GstDetailsRepository;
import com.lawzoom.complianceservice.repository.SubscriptionRepository;
import com.lawzoom.complianceservice.repository.UserRepository;
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

        User userData = userRepository.findByIdAndIsEnableAndNotDeleted(businessUnitRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with userId: " + businessUnitRequest.getUserId()));

        // Validate Subscription
        Subscription subscription = subscriptionRepository.findById(businessUnitRequest.getSubscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found with ID: " + businessUnitRequest.getSubscriptionId()));

        // Validate GST Details
        GstDetails gstDetails = gstDetailsRepository.findByIdAndEnabledAndNotDeleted(gstDetailsId)
                .orElseThrow(() -> new NotFoundException("GST Details not found or is inactive/deleted with ID: " + gstDetailsId));

        if (!gstDetails.isEnable()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "GST Details are not active. Cannot create Business Unit.");
        }

        // Fetch State
        States state = statesRepository.findById(businessUnitRequest.getStateId())
                .orElseThrow(() -> new NotFoundException("State not found with ID: " + businessUnitRequest.getStateId()));

        // Fetch City
        City city = cityRepository.findById(businessUnitRequest.getCityId())
                .orElseThrow(() -> new NotFoundException("City not found with ID: " + businessUnitRequest.getCityId()));

        // Fetch LocatedAt
        LocatedAt locatedAt = locatedAtRepository.findById(businessUnitRequest.getLocatedAtId())
                .orElseThrow(() -> new NotFoundException("LocatedAt not found with ID: " + businessUnitRequest.getLocatedAtId()));

        BusinessActivity businessActivity = businessActivityRepository.findById(businessUnitRequest.getBusinessActivityId())
                .orElseThrow(() -> new NotFoundException("Business not found with ID: " + businessUnitRequest.getBusinessActivityId()));

        // Create and populate Business Unit entity
        BusinessUnit newBusinessUnit = new BusinessUnit();
        newBusinessUnit.setAddress(businessUnitRequest.getAddress());
        newBusinessUnit.setCity(city);
        newBusinessUnit.setLocatedAt(locatedAt);
        newBusinessUnit.setCreatedAt(new Date());
        newBusinessUnit.setUpdatedAt(new Date());
        newBusinessUnit.setEnable(true);
        newBusinessUnit.setState(state);
        newBusinessUnit.setGstNumber(businessUnitRequest.getGstNumber());
        newBusinessUnit.setGstDetails(gstDetails);
        newBusinessUnit.setSubscription(subscription);
        newBusinessUnit.setUpdatedBy(userData);
        newBusinessUnit.setCreatedBy(userData);
        newBusinessUnit.setBusinessActivity(businessActivity);

        // Save Business Unit
        BusinessUnit savedBusinessUnit = businessUnitRepository.save(newBusinessUnit);

        // Prepare response
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

        return response;
    }
    @Override
    public List<BusinessUnitResponse> getCompanyUnits(UnitRequest unitRequest) {
        // Fetch the user details and validate
        User userData = userRepository.findByIdAndIsEnableAndNotDeleted(unitRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with userId: " + unitRequest.getUserId()));

        // Fetch the subscription details and validate
        Subscription subscription = subscriptionRepository.findById(unitRequest.getSubscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found with ID: " + unitRequest.getSubscriptionId()));

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
            response.setSubscriptionId(subscription.getId());
            responses.add(response);
        }

        return responses;
    }


    @Override
    public BusinessUnitResponse updateBusinessUnit(Long businessUnitId, BusinessUnitRequest businessUnitRequest) {

        User userData = userRepository.findByIdAndIsEnableAndNotDeleted(businessUnitRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with userId: " + businessUnitRequest.getUserId()));


        Subscription subscription = subscriptionRepository.findById(businessUnitRequest.getSubscriptionId())
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found with ID: " + businessUnitRequest.getSubscriptionId()));


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

        // Update other optional fields if they are part of the request
        if (businessUnitRequest.getUserId() != null) {
            User updatedBy = userRepository.findByIdAndIsEnableAndNotDeleted(businessUnitRequest.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found with userId: " + businessUnitRequest.getUserId()));
            existingBusinessUnit.setUpdatedBy(updatedBy);
        }


        // Update the business unit fields
        existingBusinessUnit.setCity(city);
        existingBusinessUnit.setLocatedAt(locatedAt);
        existingBusinessUnit.setAddress(businessUnitRequest.getAddress());
        existingBusinessUnit.setGstNumber(businessUnitRequest.getGstNumber());
        existingBusinessUnit.setState(state);
        existingBusinessUnit.setUpdatedAt(new Date()); // Ensure updated timestamp is set
        existingBusinessUnit.setSubscription(subscription);
        existingBusinessUnit.setCreatedBy(userData);



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
    public List<BusinessUnitResponse> getAllBusinessUnits(Long gstDetailsId,Long userId) {

        User userData = userRepository.findByIdAndIsEnableAndNotDeleted(userId)
                .orElseThrow(() -> new NotFoundException("User not found with userId: " + userId));
        // Fetch the GST details by ID

        Optional<GstDetails> gstDetailsOptional = gstDetailsRepository.findById(gstDetailsId);

        if (gstDetailsOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No GST details found for ID: " + gstDetailsId);
        }

        GstDetails gstDetails = gstDetailsOptional.get();

        // Fetch the list of business units using the custom query
        List<BusinessUnit> businessUnits = businessUnitRepository.findAllByGstDetails(gstDetails);

        if (businessUnits.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No business units found for GST details ID: " + gstDetailsId);
        }

        // Map each BusinessUnit entity to a BusinessUnitResponse DTO
        return businessUnits.stream().map(this::mapToBusinessUnitResponse).collect(Collectors.toList());
    }

    // Helper method to map BusinessUnit to BusinessUnitResponse
    private BusinessUnitResponse mapToBusinessUnitResponse(BusinessUnit businessUnit) {
        BusinessUnitResponse response = new BusinessUnitResponse();

        response.setId(businessUnit.getId());
        response.setCity(businessUnit.getCity() != null ? businessUnit.getCity().getCityName() : null);
        response.setLocatedAt(businessUnit.getLocatedAt() != null ? businessUnit.getLocatedAt().getLocationName() : null);
        response.setAddress(businessUnit.getAddress());
        response.setCreatedAt(businessUnit.getCreatedAt());
        response.setUpdatedAt(businessUnit.getUpdatedAt());
        response.setEnable(businessUnit.isEnable());
        response.setGstNumber(businessUnit.getGstNumber());
        response.setState(businessUnit.getState() != null ? businessUnit.getState().getStateName() : null);
        response.setSubscriptionId(businessUnit.getSubscription() != null ? businessUnit.getSubscription().getId() : null);
        response.setCityId(businessUnit.getCity().getId());
        response.setStateId(businessUnit.getState().getId());
        response.setLocatedAtId(businessUnit.getLocatedAt().getId());

        return response;
    }


    @Override
    public BusinessUnitResponse getBusinessUnitData(Long businessUnitId) {
        Optional<BusinessUnit> businessUnitOptional = businessUnitRepository.findById(businessUnitId);

        BusinessUnit businessUnit = businessUnitOptional.orElseThrow(() ->
                new NotFoundException("Business Unit not found for ID: " + businessUnitId));
        return mapBusinessUnitToResponse(businessUnit);
    }

    private BusinessUnitResponse mapBusinessUnitToResponse(BusinessUnit businessUnit) {
        BusinessUnitResponse response = new BusinessUnitResponse();
        if (businessUnit != null) {
            response.setId(businessUnit.getId());
            response.setCity(businessUnit.getCity().getCityName());
            response.setLocatedAt(businessUnit.getLocatedAt().getLocationName());
            response.setAddress(businessUnit.getAddress());
            response.setCreatedAt(businessUnit.getCreatedAt());
            response.setUpdatedAt(businessUnit.getUpdatedAt());
            response.setEnable(businessUnit.isEnable());
            response.setGstNumber(businessUnit.getGstNumber());
            response.setState(businessUnit.getState().getStateName());
            response.setCityId(businessUnit.getCity().getId());
            response.setStateId(businessUnit.getState().getId());
            response.setLocatedAtId(businessUnit.getLocatedAt().getId());
            response.setSubscriptionId(businessUnit.getSubscription() != null ? businessUnit.getSubscription().getId() : null);
        }
        return response;
    }



}
