package com.lawzoom.complianceservice.services;

import com.lawzoom.complianceservice.dto.roleDTO.RoleResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {
    public List<RoleResponse> fetchAllRoles();

    public ResponseEntity<String> createRole(String roleName) ;
}
