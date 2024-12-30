package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.dto.roleDTO.RoleResponse;
import com.lawzoom.complianceservice.model.Roles;
import com.lawzoom.complianceservice.payload.response.MessageResponse;
import com.lawzoom.complianceservice.payload.response.ResponseEntity;
import com.lawzoom.complianceservice.repository.RoleRepository;
import com.lawzoom.complianceservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleResponse> fetchAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> new RoleResponse(role.getId(), role.getRoleName()))
                .collect(Collectors.toList());
    }




    @Override
    public ResponseEntity<String> createRole(@RequestBody String roleName) {
        if (roleName != null) {

            Roles roles = new Roles();

            roles.setCreatedAt(new Date());
            roles.setUpdatedAt(new Date());
            roles.setDate(LocalDate.now());
            roles.setEnable(true);
            roles.setRoleName(roleName);


            roleRepository.save(roles);

            return new ResponseEntity<MessageResponse>().ok(new MessageResponse("Role Successfully Created"));
        }
        return new ResponseEntity<>().badRequest("Value is Null");
    }
    @Override
    public ResponseEntity<Void> deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
        return new ResponseEntity<MessageResponse>().ok(new MessageResponse("Deleted"));
    }


    public RoleResponse findEnabledAndNotDeletedRoleById(Long id) {
        return roleRepository.findByIdAndIsEnableTrueAndIsDeletedFalse(id)
                .map(role -> {
                    RoleResponse response = new RoleResponse();
                    response.setId(role.getId());
                    response.setRole(role.getRoleName());
                    return response;
                })
                .orElseThrow(() -> new IllegalArgumentException("Role not found or is disabled/deleted"));
    }

}
