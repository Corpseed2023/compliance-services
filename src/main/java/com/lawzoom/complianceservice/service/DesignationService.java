package com.lawzoom.complianceservice.service;

import com.lawzoom.complianceservice.model.user.Designation;

public interface DesignationService {
    Designation createDesignation(Long departmentId, Designation designation);
}
