package com.lawzoom.complianceservice.service;

import com.lawzoom.complianceservice.model.Status;

import java.util.List;

public interface StatusService {

    List<Status> getAllStatuses();

    Status createStatus(String status);
}
