package com.lawzoom.complianceservice.controller.userController;


import com.lawzoom.complianceservice.model.user.Roles;
import com.lawzoom.complianceservice.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/roles")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @PostMapping
    public ResponseEntity<Roles> createRole(@RequestBody Roles role) {
        Roles createdRole = rolesService.createRole(role);
        return ResponseEntity.status(201).body(createdRole);
    }


    @GetMapping
    public ResponseEntity<List<Roles>> getAllRoles() {
        List<Roles> roles = rolesService.getAllRoles();
        return ResponseEntity.ok(roles);
    }


}
