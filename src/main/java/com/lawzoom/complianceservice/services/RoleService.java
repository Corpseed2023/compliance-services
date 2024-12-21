package com.lawzoom.complianceservice.services;


import com.lawzoom.complianceservice.dto.roleDTO.RoleResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {
    
    ResponseEntity<Void> deleteRole(Long roleId);

    ResponseEntity<String> createRole(String role);

    List<RoleResponse> fetchAllRoles();

    RoleResponse findEnabledAndNotDeletedRoleById(Long id);
}
