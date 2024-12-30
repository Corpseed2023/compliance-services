package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.roleDTO.RoleResponse;
import com.lawzoom.complianceservice.payload.response.ResponseEntity;

import java.util.List;


public interface RoleService {
    
    ResponseEntity<Void> deleteRole(Long roleId);

    ResponseEntity<String> createRole(String role);

    List<RoleResponse> fetchAllRoles();

    RoleResponse findEnabledAndNotDeletedRoleById(Long id);
}
