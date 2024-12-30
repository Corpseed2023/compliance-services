package com.lawzoom.complianceservice.service.regionService;


import com.lawzoom.complianceservice.dto.regionDTO.states.StatesRequestDto;
import com.lawzoom.complianceservice.model.region.States;

import java.util.List;

public interface StateService {


    List<States> getAllEnabledAndNotDeletedStates(Long countryId);


    States createStates(Long countryId,String stateName);

    States updateState(StatesRequestDto statesRequestDto);
}
