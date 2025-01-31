package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.gstDTO.GstDetailsFetchRequest;
import com.lawzoom.complianceservice.dto.gstDTO.GstDetailsRequest;
import com.lawzoom.complianceservice.dto.gstDTO.GstDetailsResponse;
import com.lawzoom.complianceservice.dto.gstDTO.gstResponse.GstCompanyResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.businessUnitModel.BusinessUnit;
import com.lawzoom.complianceservice.model.companyModel.Company;
import com.lawzoom.complianceservice.model.gstdetails.GstDetails;
import com.lawzoom.complianceservice.model.region.Country;
import com.lawzoom.complianceservice.model.region.States;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.GstDetailsRepository;
import com.lawzoom.complianceservice.repository.SubscriberRepository;
import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
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
    private SubscriberRepository subscriberRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public GstDetailsResponse createGstDetails(GstDetailsRequest gstDetailsRequest) {

        // Step 1: Validate User
        User userData = userRepository.findActiveUserById(gstDetailsRequest.getUserId());
        if (userData == null) {
            throw new NotFoundException("User not found with userId: " + gstDetailsRequest.getUserId());
        }

        // Step 2: Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(gstDetailsRequest.getSubscriberId())
                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + gstDetailsRequest.getSubscriberId()));

        if (!subscriber.getSuperAdmin().equals(userData)) {
            throw new IllegalArgumentException("User is not authorized to create GST details for this subscriber.");
        }

        // Step 3: Validate Company
        Company company = companyRepository.findEnabledAndNotDeletedCompanyById(gstDetailsRequest.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + gstDetailsRequest.getCompanyId()));

        if (!company.getSubscriber().equals(subscriber)) {
            throw new IllegalArgumentException("Company does not belong to the specified subscriber.");
        }

        // Step 4: Validate Country
        Country country = countryRepository.findById(gstDetailsRequest.getCountryId())
                .orElseThrow(() -> new NotFoundException("Country not found with ID: " + gstDetailsRequest.getCountryId()));

        // Step 5: Validate State
        States state = statesRepository.findById(gstDetailsRequest.getStateId())
                .orElseThrow(() -> new NotFoundException("State not found with ID: " + gstDetailsRequest.getStateId()));

        // Step 6: Check for Duplicate GST Number for the Company
        List<GstDetails> existingGstDetails = gstDetailsRepository.findByGstNumberAndCompanyIdAndStateId(
                gstDetailsRequest.getGstNumber(),
                gstDetailsRequest.getCompanyId(),
                gstDetailsRequest.getStateId()
        );

        if (!existingGstDetails.isEmpty()) {
            throw new IllegalArgumentException("GST number already exists for the specified company and state.");
        }

        // Step 7: Create GST Details
        GstDetails gstDetails = new GstDetails();
        gstDetails.setGstNumber(gstDetailsRequest.getGstNumber());
        gstDetails.setCompany(company);
        gstDetails.setCountry(country);
        gstDetails.setState(state);
        gstDetails.setEnable(true);
        gstDetails.setGstRegistrationDate(gstDetailsRequest.getGstRegistrationDate());
        gstDetails.setCreatedAt(new Date());
        gstDetails.setUpdatedAt(new Date());
        gstDetails.setCreatedBy(userData);

        // Save GST Details
        gstDetails = gstDetailsRepository.save(gstDetails);

        // Step 8: Count Associated Business Units
        Long businessUnitCount = (long) (gstDetails.getBusinessUnits() != null ? gstDetails.getBusinessUnits().size() : 0);

        // Step 9: Prepare Response
        GstDetailsResponse response = new GstDetailsResponse();
        response.setId(gstDetails.getId());
        response.setGstNumber(gstDetails.getGstNumber());
        response.setCompanyId(gstDetails.getCompany().getId());
        response.setCompanyName(gstDetails.getCompany().getCompanyName()); // Include company name
        response.setCountryId(gstDetails.getCountry().getId());
        response.setCountryName(gstDetails.getCountry().getCountryName());
        response.setStateId(gstDetails.getState().getId());
        response.setStateName(gstDetails.getState().getStateName());
        response.setGstRegistrationDate(gstDetails.getGstRegistrationDate());
        response.setBusinessUnitCount(businessUnitCount); // Include business unit count

        return response;
    }


    @Override
    public GstDetailsResponse updateGstDetails(Long id, GstDetailsRequest gstDetailsRequest) {
        // Validate GST Details
        GstDetails gstDetails = gstDetailsRepository.findByIdAndEnabledAndNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("GST Details not found or inactive/deleted with ID: " + id));

        Company company = companyRepository.findEnabledAndNotDeletedCompanyById(gstDetailsRequest.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + gstDetailsRequest.getCompanyId()));

        if (!company.equals(gstDetails.getCompany())) {
            throw new IllegalArgumentException("GST details cannot be updated for a different company.");
        }

        States state = statesRepository.findById(gstDetailsRequest.getStateId())
                .orElseThrow(() -> new NotFoundException("State not found with ID: " + gstDetailsRequest.getStateId()));

        // Step 6: Check for Duplicate GST Number for the Company
        List<GstDetails> existingGstDetails = gstDetailsRepository.findByGstNumberAndCompanyIdAndStateId(
                gstDetailsRequest.getGstNumber(),
                gstDetailsRequest.getCompanyId(),
                gstDetailsRequest.getStateId()
        );

        // Ensure the existing GST number belongs to a different record
        if (!existingGstDetails.isEmpty() && !existingGstDetails.get(0).getId().equals(id)) {
            throw new IllegalArgumentException("GST number already exists for the specified company and state.");
        }

        // Step 5: Update GST details
        gstDetails.setGstNumber(gstDetailsRequest.getGstNumber());
        gstDetails.setState(state);
        gstDetails.setGstRegistrationDate(gstDetailsRequest.getGstRegistrationDate());
        gstDetails.setUpdatedAt(new Date());
        gstDetails.setDate(LocalDate.now());

        // Assume the current user making the request is retrieved from the security context or session
        User currentUser = userRepository.findById(gstDetailsRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with userId: " + gstDetailsRequest.getUserId()));
        gstDetails.setUpdatedBy(currentUser);

        // Step 6: Save the updated GST details
        gstDetails = gstDetailsRepository.save(gstDetails);

        // Step 7: Map and return the response
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
    public String softDeleteGstDetails(Long id, Long userId) {

        GstDetails gstDetails = gstDetailsRepository.findByIdAndEnabledAndNotDeleted(id)
                .orElseThrow(() -> new NotFoundException("GST Details not found or inactive/deleted with ID: " + id));


        // Step 2: Validate the User
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with userId: " + userId));

        // Step 3: Validate the User's Permission
        if (!gstDetails.getCreatedBy().equals(currentUser)) {
            throw new IllegalArgumentException("User is not authorized to delete these GST details.");
        }

        // Step 4: Soft delete by setting enabled and deleted flags
        gstDetails.setEnable(false);
        gstDetails.setDeleted(true);
        gstDetails.setUpdatedBy(currentUser); // Track the user who performed the deletion
        gstDetails.setUpdatedAt(new Date());

        // Step 5: Save the soft-deleted GST details
        gstDetailsRepository.save(gstDetails);

        return "GST Details with ID " + id + " have been successfully deleted.";
    }


    @Override
    public List<GstCompanyResponse> fetchAllGstDetails(Long companyId, Long userId, Long subscriberId) {
        // Step 1: Validate the Subscriber
        Subscriber subscriber = subscriberRepository.findById(subscriberId)
                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + subscriberId));

        // Step 2: Validate the User
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        // Step 3: Validate the Company
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + companyId));

        // Ensure the company belongs to the subscriber
        if (!company.getSubscriber().equals(subscriber)) {
            throw new IllegalArgumentException("Company does not belong to the specified subscriber.");
        }

        // Step 4: Fetch GST Details
        List<GstDetails> gstDetailsList = gstDetailsRepository.findAllByCompanyIdAndIsDeletedFalse(companyId);
        if (gstDetailsList.isEmpty()) {
            throw new NotFoundException("No GST details found for the given criteria.");
        }

        // Step 5: Construct Response
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

            // Build associated business unit responses
            List<BusinessUnitResponse> businessUnitResponses = new ArrayList<>();
            for (BusinessUnit businessUnit : gstDetails.getBusinessUnits()) {
                BusinessUnitResponse businessUnitResponse = new BusinessUnitResponse();
                businessUnitResponse.setId(businessUnit.getId());
                businessUnitResponse.setCityId(businessUnit.getCity() != null ? businessUnit.getCity().getId() : null);
                businessUnitResponse.setCity(businessUnit.getCity() != null ? businessUnit.getCity().getCityName() : null);
                businessUnitResponse.setLocatedAtId(businessUnit.getLocatedAt() != null ? businessUnit.getLocatedAt().getId() : null);
                businessUnitResponse.setLocatedAt(businessUnit.getLocatedAt() != null ? businessUnit.getLocatedAt().getLocationName() : null);
                businessUnitResponse.setAddress(businessUnit.getAddress());
                businessUnitResponse.setCreatedAt(businessUnit.getCreatedAt());
                businessUnitResponse.setUpdatedAt(businessUnit.getUpdatedAt());
                businessUnitResponse.setEnable(businessUnit.isEnable());
                businessUnitResponse.setGstNumber(businessUnit.getGstNumber());
                businessUnitResponse.setStateId(businessUnit.getState() != null ? businessUnit.getState().getId() : null);
                businessUnitResponse.setState(businessUnit.getState() != null ? businessUnit.getState().getStateName() : null);
                businessUnitResponse.setCompanyName(gstDetails.getCompany().getCompanyName());
                businessUnitResponse.setBusinessActivityId(businessUnit.getBusinessActivity() != null ? businessUnit.getBusinessActivity().getId() : null);
                businessUnitResponse.setBusinessActivity(businessUnit.getBusinessActivity() != null ? businessUnit.getBusinessActivity().getBusinessActivityName() : null);

                businessUnitResponses.add(businessUnitResponse);
            }

            gstCompanyResponse.setBusinessUnitResponseList(businessUnitResponses);
            responseList.add(gstCompanyResponse);
        }

        return responseList;
    }

    @Override
    public GstDetailsResponse getGstDetailsById(GstDetailsFetchRequest gstDetailsFetchRequest) {
        // Step 1: Validate Subscriber
        Subscriber subscriber = subscriberRepository.findById(gstDetailsFetchRequest.getSubscriberId())
                .orElseThrow(() -> new NotFoundException("Subscriber not found with ID: " + gstDetailsFetchRequest.getSubscriberId()));

        // Step 2: Validate User
        User user = userRepository.findById(gstDetailsFetchRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + gstDetailsFetchRequest.getUserId()));

        // Step 3: Validate Company
        Company company = companyRepository.findById(gstDetailsFetchRequest.getCompanyId())
                .orElseThrow(() -> new NotFoundException("Company not found with ID: " + gstDetailsFetchRequest.getCompanyId()));

        if (!company.getSubscriber().equals(subscriber)) {
            throw new IllegalArgumentException("Company does not belong to the specified subscriber.");
        }

        // Step 4: Fetch GST Details
        GstDetails gstDetails = gstDetailsRepository.findById(gstDetailsFetchRequest.getGstDetailsId())
                .orElseThrow(() -> new NotFoundException("GST Details not found with ID: " + gstDetailsFetchRequest.getGstDetailsId()));

        if (!gstDetails.getCompany().equals(company)) {
            throw new IllegalArgumentException("GST details do not belong to the specified company.");
        }

        // Step 5: Construct Response
        GstDetailsResponse response = new GstDetailsResponse();
        response.setId(gstDetails.getId());
        response.setGstNumber(gstDetails.getGstNumber());
        response.setCompanyId(company.getId());
        response.setCompanyName(company.getCompanyName());
        response.setCountryId(gstDetails.getCountry().getId());
        response.setCountryName(gstDetails.getCountry().getCountryName());
        response.setStateId(gstDetails.getState().getId());
        response.setStateName(gstDetails.getState().getStateName());
        response.setGstRegistrationDate(gstDetails.getGstRegistrationDate());
        response.setBusinessUnitCount((long) gstDetails.getBusinessUnits().size()); // Count of Business Units

        return response;
    }



}
