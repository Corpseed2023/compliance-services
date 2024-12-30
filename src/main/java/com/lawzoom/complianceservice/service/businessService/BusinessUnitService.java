package com.lawzoom.complianceservice.service.businessService;




import com.lawzoom.complianceservice.dto.businessUnitDto.BusinessUnitResponse;
import com.lawzoom.complianceservice.dto.businessUnitDto.businessUnitRequest.BusinessUnitRequest;
import com.lawzoom.complianceservice.dto.businessUnitDto.businessUnitRequest.UnitRequest;

import java.util.List;

public interface BusinessUnitService {
    BusinessUnitResponse createBusinessUnit(BusinessUnitRequest businessUnitRequest , Long gstDetailsId);
    BusinessUnitResponse updateBusinessUnit(Long businessUnitId, BusinessUnitRequest businessUnitRequest);
    List<BusinessUnitResponse> getAllBusinessUnits(Long gstDetails,Long userId);

    BusinessUnitResponse getBusinessUnitData(Long businessUnitId);

    List<BusinessUnitResponse> getCompanyUnits(UnitRequest unitRequest);
//    List<BusinessUnitResponse> getAllBusinessUnitsWithAllCompany();

//    List<BusinessUnitResponse> getAllBusinessUnitsWithCompany(Long businessUnitId);
}
