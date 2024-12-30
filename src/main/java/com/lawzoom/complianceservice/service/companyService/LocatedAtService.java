package com.lawzoom.complianceservice.service.companyService;



import com.lawzoom.complianceservice.dto.regionDTO.locatedAt.LocatedAtResponse;
import com.lawzoom.complianceservice.model.region.LocatedAt;

import java.util.List;

public interface LocatedAtService {



    LocatedAt updateLocatedAt(Long id, LocatedAt updatedLocatedAt);

    void softDeleteLocatedAt(Long id);

    String createLocatedAt(String locationName, Long userId);

    List<LocatedAtResponse> getAllLocationNames();
}
