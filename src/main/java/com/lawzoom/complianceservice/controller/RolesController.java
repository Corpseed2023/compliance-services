package com.lawzoom.complianceservice.controller;


import com.lawzoom.complianceservice.dto.roleDTO.RoleResponse;
import com.lawzoom.complianceservice.model.Roles;
import com.lawzoom.complianceservice.services.RoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/compliance/roles")
public class RolesController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "Return all roles",
            notes = "Return all roles as list", response = Roles.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully token generated"),
            @ApiResponse(code = 500, message = "Something Went-Wrong"),
            @ApiResponse(code = 400, message = "Bad Request")
    })

    @GetMapping("/getAllRoles")
    public List<RoleResponse> fetchRoles() {
        List<RoleResponse> roles = roleService.fetchAllRoles();
        return roles;
    }


    @ApiOperation(value = "Create a new role",
            notes = "Create a new role", response = Roles.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Role created successfully"),
            @ApiResponse(code = 500, message = "Something Went Wrong"),
            @ApiResponse(code = 400, message = "Bad Request")
    })

    @PostMapping("/createRole")
    public ResponseEntity<String> createRole(@RequestParam String role) {
        return roleService.createRole(role);
    }

    @ApiOperation(value = "Delete a role by ID",
            notes = "Delete a role by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Role deleted successfully"),
            @ApiResponse(code = 500, message = "Something Went Wrong"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @DeleteMapping("/removeRole")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
        return roleService.deleteRole(roleId);
    }

    @GetMapping("/fetch-role")
    public ResponseEntity<RoleResponse> getRoleById(@RequestParam Long id) {
        RoleResponse roleResponse = roleService.findEnabledAndNotDeletedRoleById(id);
        return new ResponseEntity<>(roleResponse, HttpStatus.OK);
    }


}
