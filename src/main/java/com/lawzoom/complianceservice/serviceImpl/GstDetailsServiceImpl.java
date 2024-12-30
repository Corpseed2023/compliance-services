package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.gstDTO.GstDetailsFetchRequest;
import com.lawzoom.complianceservice.dto.gstDTO.GstDetailsRequest;
import com.lawzoom.complianceservice.dto.gstDTO.GstDetailsResponse;
import com.lawzoom.complianceservice.dto.gstDTO.gstResponse.GstCompanyResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.Subscription;
import com.lawzoom.complianceservice.model.User;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.companyModel.Company;
import com.lawzoom.complianceservice.model.gstdetails.GstDetails;
import com.lawzoom.complianceservice.model.region.Country;
import com.lawzoom.complianceservice.model.region.States;
import com.lawzoom.complianceservice.repository.GstDetailsRepository;
import com.lawzoom.complianceservice.repository.SubscriptionRepository;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.repository.businessRepo.BusinessUnitRepository;
import com.lawzoom.complianceservice.repository.companyRepo.CompanyRepository;
import com.lawzoom.complianceservice.repository.regionRepo.CountryRepository;
import com.lawzoom.complianceservice.repository.regionRepo.StatesRepository;
import com.lawzoom.complianceservice.service.GstDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GstDetailsServiceImpl implements GstDetailsService {

    @Autowired
    private GstDetailsRepository gstDetailsRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StatesRepository statesRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessUnitRepository businessUnitRepository;


    @Override
    public GstDetailsResponse createGstDetails(GstDetailsRequest gstDetailsRequest) {
        // Validate User
        User userData = userRepository.findByIdAndIsEnableAndNotDeleted(gstDetailsRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with userId: " + gstDetailsRequest.getUserId()));

        // Validate Subscription (if provided)
        Subscription subscription = null;
        if (gstDetailsRequest.getSubscriptionId() != null) {
            subscription = subscriptionRepository.findById(gstDetailsRequest.getSubscriptionId())
                    .orElseThrow(() -> new IllegalArgumentException("Subscription not found with ID: " + gstDetailsRequest.getSubscriptionId()));
        }

        // Validate Company
        Company company = companyRepository.findEnabledAndNotDeletedCompanyById(gstDetailsRequest.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + gstDetailsRequest.getCompanyId()));

        // Validate Country
        Country country = countryRepository.findById(gstDetailsRequest.getCountryId())
                .orElseThrow(() -> new NotFoundException("Country not found with ID: " + gstDetailsRequest.getCountryId()));

        // Validate State
        States state = statesRepository.findById(gstDetailsRequest.getStateId())
                .orElseThrow(() -> new NotFoundException("State not found with ID: " + gstDetailsRequest.getStateId()));

        // Check for duplicate GST number within the same subscription and state
        if (gstDetailsRequest.getSubscriptionId() != null) {
            boolean gstExists = gstDetailsRepository.existsByGstNumberAndSubscriptionIdAndStateId(
                    gstDetailsRequest.getGstNumber(),
                    gstDetailsRequest.getSubscriptionId(),
                    gstDetailsRequest.getStateId()
            );

            if (gstExists) {
                throw new IllegalArgumentException("GST number already exists for the same subscription ID and state: "
                        + gstDetailsRequest.getSubscriptionId());
            }
        }

        // Create GST Details
        GstDetails gstDetails = new GstDetails();
        gstDetails.setGstNumber(gstDetailsRequest.getGstNumber());
        gstDetails.setState(state);
        gstDetails.setCompany(company);
        gstDetails.setGstRegistrationDate(gstDetailsRequest.getGstRegistrationDate());
        gstDetails.setCreatedAt(new Date());
        gstDetails.setUpdatedAt(new Date());
        gstDetails.setDate(LocalDate.now());
        gstDetails.setCountry(country);
        gstDetails.setSubscription(subscription);
        gstDetails.setCreatedBy(userData);
        gstDetails.setUpdatedBy(userData);

        // Save GST Details
        gstDetails = gstDetailsRepository.save(gstDetails);

        // Map and return the response
        GstDetailsResponse response = new GstDetailsResponse();
        response.setId(gstDetails.getId());
        response.setGstNumber(gstDetails.getGstNumber());
        response.setCompanyId(gstDetails.getCompany().getId());
        response.setCountryId(gstDetails.getCountry().getId());
        response.setCountryName(gstDetails.getCountry().getCountryName());
        response.setStateId(gstDetails.getState().getId());
        response.setStateName(gstDetails.getState().getStateName());
        response.setGstRegistrationDate(gstDetails.getGstRegistrationDate());

        return response;
    }



    @Override
    public GstDetailsResponse getGstDetailsById(GstDetailsFetchRequest gstDetailsFetchRequest) {

        // Check if the user exists and is enabled
        userRepository.findByIdAndIsEnableAndNotDeleted(gstDetailsFetchRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with userId: " + gstDetailsFetchRequest.getUserId()));

        // Check if the subscription exists if subscriptionId is provided
        if (gstDetailsFetchRequest.getSubscriptionId() != null) {
            subscriptionRepository.findById(gstDetailsFetchRequest.getSubscriptionId())
                    .orElseThrow(() -> new NotFoundException("Subscription not found with ID: " + gstDetailsFetchRequest.getSubscriptionId()));
        }

        // Validate Company
        companyRepository.findEnabledAndNotDeletedCompanyById(gstDetailsFetchRequest.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + gstDetailsFetchRequest.getCompanyId()));

        // Fetch GST Details and validate
        GstDetails gstDetails = gstDetailsRepository.findByIdAndEnabledAndNotDeleted(gstDetailsFetchRequest.getGstDetailsId())
                .orElseThrow(() -> new NotFoundException("GST Details not found or is inactive/deleted with ID: " + gstDetailsFetchRequest.getGstDetailsId()));

        Long businessUnitCount = (long) gstDetails.getBusinessUnits().size();

        // Build and return the response
        GstDetailsResponse response = new GstDetailsResponse();
        response.setId(gstDetails.getId());
        response.setGstNumber(gstDetails.getGstNumber());
        response.setCompanyId(gstDetails.getCompany().getId());
        response.setCountryName(gstDetails.getCountry().getCountryName());
        response.setStateName(gstDetails.getState().getStateName());
        response.setGstRegistrationDate(gstDetails.getGstRegistrationDate());
        response.setStateId(gstDetails.getState().getId());
        response.setCountryId(gstDetails.getCountry().getId());
        response.setCompanyName(gstDetails.getCompany().getCompanyName());
        response.setBusinessUnitCount(businessUnitCount);  // Set business unit count here

        return response;
    }

    @Override
    public GstDetailsResponse updateGstDetails(Long id, GstDetailsRequest gstDetailsRequest) {
        // Fetch the existing GST details
        GstDetails gstDetails = gstDetailsRepository.findByIdAndEnabledAndNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("GST Details not found or is inactive/deleted with ID: " + id));

        // Validate User
        User user = userRepository.findByIdAndIsEnableAndNotDeleted(gstDetailsRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with userId: " + gstDetailsRequest.getUserId()));

        // Validate Subscription if provided
        Subscription subscription = null;
        if (gstDetailsRequest.getSubscriptionId() != null) {
            subscription = subscriptionRepository.findById(gstDetailsRequest.getSubscriptionId())
                    .orElseThrow(() -> new NotFoundException("Subscription not found with ID: " + gstDetailsRequest.getSubscriptionId()));
        }

        // Validate State
        States state = statesRepository.findById(gstDetailsRequest.getStateId())
                .orElseThrow(() -> new NotFoundException("State not found with ID: " + gstDetailsRequest.getStateId()));

        // Update GST details
        gstDetails.setGstNumber(gstDetailsRequest.getGstNumber());
        gstDetails.setState(state);
        gstDetails.setGstRegistrationDate(gstDetailsRequest.getGstRegistrationDate());
        gstDetails.setUpdatedAt(new Date());

        // Update subscription if provided
        if (subscription != null) {
            gstDetails.setSubscription(subscription);
        }

        // Update the user who modified this record
        gstDetails.setUpdatedBy(user);

        // Save updated GST details
        gstDetails = gstDetailsRepository.save(gstDetails);

        // Map and return the response
        GstDetailsResponse response = new GstDetailsResponse();
        response.setId(gstDetails.getId());
        response.setGstNumber(gstDetails.getGstNumber());
        response.setCompanyId(gstDetails.getCompany().getId());
        response.setCountryId(gstDetails.getCountry().getId());
        response.setCountryName(gstDetails.getCountry().getCountryName());
        response.setStateId(gstDetails.getState().getId());
        response.setStateName(gstDetails.getState().getStateName());
        response.setGstRegistrationDate(gstDetails.getGstRegistrationDate());

        return response;
    }


    @Override
    public String softDeleteGstDetails(Long id) {
        // Fetch the existing GST details
        GstDetails gstDetails = gstDetailsRepository.findByIdAndEnabledAndNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("GST Details not found or is inactive/deleted with ID: " + id));


        // Soft delete by setting enabled and deleted flags
        gstDetails.setEnable(false);
        gstDetails.setDeleted(true);
        gstDetails.setUpdatedAt(new Date());

        // Save the soft-deleted GST details
        gstDetailsRepository.save(gstDetails);

        return "GST Details with ID " + id + " have been successfully deleted.";
    }


    @Override
    public List<GstCompanyResponse> fetchAllGstDetails(Long companyId, Long userId, Long subscriptionId) {
        // Validate inputs
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + companyId));

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Subscription not found with ID: " + subscriptionId));

        // Fetch GST Details
        List<GstDetails> gstDetailsList = gstDetailsRepository.findActiveByCompanyAndSubscription(company.getId(), subscription.getId());

        if (gstDetailsList.isEmpty()) {
            throw new NotFoundException("No GST details found for the given criteria.");
        }

        // Directly construct the required response in one traversal
        List<GstCompanyResponse> responseList = new ArrayList<>();
        for (GstDetails gstDetails : gstDetailsList) {
            // Build GST response object
            GstCompanyResponse gstCompanyResponse = new GstCompanyResponse();
            gstCompanyResponse.setGstId(gstDetails.getId());
            gstCompanyResponse.setGstNumber(gstDetails.getGstNumber());
            gstCompanyResponse.setCountryId(gstDetails.getCountry().getId());
            gstCompanyResponse.setCountryName(gstDetails.getCountry().getCountryName());
            gstCompanyResponse.setStateId(gstDetails.getState().getId());
            gstCompanyResponse.setStateName(gstDetails.getState().getStateName());
            gstCompanyResponse.setCompanyId(company.getId());
            gstCompanyResponse.setCompanyName(company.getCompanyName());
            gstCompanyResponse.setGstRegistrationDate(gstDetails.getGstRegistrationDate().toString());
            gstCompanyResponse.setSubscriptionId(subscription.getId());

            // Build associated business unit responses
            List<BusinessUnitResponse> businessUnitResponses = new ArrayList<>();
            for (BusinessUnit businessUnit : gstDetails.getBusinessUnits()) {
                BusinessUnitResponse businessUnitResponse = new BusinessUnitResponse();
                businessUnitResponse.setId(businessUnit.getId());

                businessUnitResponse.setCityId(businessUnit.getCity().getId());
                businessUnitResponse.setCity(businessUnit.getCity().getCityName());
                businessUnitResponse.setLocatedAtId(businessUnit.getLocatedAt().getId());
                businessUnitResponse.setLocatedAt(businessUnit.getLocatedAt().getLocationName());
                businessUnitResponse.setAddress(businessUnit.getAddress());
                businessUnitResponse.setCreatedAt(businessUnit.getCreatedAt());
                businessUnitResponse.setUpdatedAt(businessUnit.getUpdatedAt());
                businessUnitResponse.setEnable(businessUnit.isEnable());
                businessUnitResponse.setGstNumber(gstDetails.getGstNumber());
                businessUnitResponse.setStateId(businessUnit.getState().getId());
                businessUnitResponse.setState(businessUnit.getState().getStateName());
                businessUnitResponse.setSubscriptionId(subscription.getId());
                businessUnitResponse.setCountryId(gstDetails.getCountry().getId());
                businessUnitResponse.setCountryName(gstDetails.getCountry().getCountryName());
                businessUnitResponse.setCompanyName(gstDetails.getCompany().getCompanyName());
                businessUnitResponse.setBusinessActivity(businessUnit.getBusinessActivity().getBusinessActivityName());
                businessUnitResponse.setBusinessActivityId(businessUnit.getBusinessActivity().getId());

                businessUnitResponses.add(businessUnitResponse);
            }

            gstCompanyResponse.setBusinessUnitResponseList(businessUnitResponses);
            responseList.add(gstCompanyResponse);
        }

        return responseList;
    }




}
