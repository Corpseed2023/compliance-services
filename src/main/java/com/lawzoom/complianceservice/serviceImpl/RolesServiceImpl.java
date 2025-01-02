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
    public Roles createRole(Roles role) {
        if (rolesRepository.existsByRoleName(role.getRoleName())) {
            throw new RuntimeException("Role name already exists: " + role.getRoleName());
        }
        return rolesRepository.save(role);
    }

    @Override
    public List<Roles> getAllRoles() {
        return rolesRepository.findAll();
    }
}
