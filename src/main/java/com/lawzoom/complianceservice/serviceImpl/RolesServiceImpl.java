package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.model.user.Roles;
import com.lawzoom.complianceservice.repository.RolesRepository;
import com.lawzoom.complianceservice.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;


    @Override
    public Roles createRole(String roleName) {

        if (rolesRepository.existsByRoleName(roleName)) {
            throw new RuntimeException("Role name already exists: " + roleName );
        }

        Roles roles = new Roles();

        roles.setRoleName(roleName);

        return rolesRepository.save(roles);
    }

    @Override
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }
}
