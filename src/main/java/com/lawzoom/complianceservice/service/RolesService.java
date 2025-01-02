package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.model.user.Roles;

import java.util.List;

public interface RolesService {
    Roles createRole(Roles role);

    List<Roles> getAllRoles();

}
