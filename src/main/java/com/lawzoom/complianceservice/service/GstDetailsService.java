package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.gstDTO.GstDetailsFetchRequest;
import com.lawzoom.complianceservice.dto.gstDTO.GstDetailsRequest;
import com.lawzoom.complianceservice.dto.gstDTO.GstDetailsResponse;
import com.lawzoom.complianceservice.dto.gstDTO.gstResponse.GstCompanyResponse;

import java.util.List;

public interface GstDetailsService {
    GstDetailsResponse createGstDetails(GstDetailsRequest gstDetailsRequest);
    GstDetailsResponse updateGstDetails(Long id, GstDetailsRequest gstDetailsRequest);
    
    GstDetailsResponse getGstDetailsById(GstDetailsFetchRequest gstDetailsFetchRequest);

    List<GstCompanyResponse> fetchAllGstDetails(Long companyId, Long userId, Long subscriberId);

    String softDeleteGstDetails(Long id, Long userId);
}
