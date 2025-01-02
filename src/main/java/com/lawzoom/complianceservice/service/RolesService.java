package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.model.user.Roles;

import java.util.List;

public interface RolesService {

    List<Roles> getAllRoles();

    Roles createRole(String roleName);
}
