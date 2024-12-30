package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.model.Privilege;
import com.lawzoom.complianceservice.payload.response.MessageResponse;
import com.lawzoom.complianceservice.payload.response.ResponseEntity;
import com.lawzoom.complianceservice.repository.PrivilegeRepository;
import com.lawzoom.complianceservice.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Override
    public ResponseEntity<String> createPrivilege(String privilegeName) {
        if (privilegeName != null) {
            // Check if privilege already exists in the database
            Optional<Privilege> existingPrivilege = privilegeRepository.findByName(privilegeName);
            if (existingPrivilege.isPresent()) {
                return new ResponseEntity<MessageResponse>()
                        .badRequest("Privilege with the name '" + privilegeName + "' already exists.");
            }

            // Create new privilege
            Privilege privilege = new Privilege();
            privilege.setCreatedAt(new Date());
            privilege.setUpdatedAt(new Date());
            privilege.setDate(LocalDate.now());
            privilege.setName(privilegeName);

            privilegeRepository.save(privilege);

            return new ResponseEntity<MessageResponse>()
                    .ok(new MessageResponse("Privilege Successfully Created"));
        }
        return new ResponseEntity<MessageResponse>().badRequest("Privilege name cannot be null");
    }


    @Override
    public ResponseEntity<String> updatePrivilege(Long id, String newPrivilegeName) {
        Optional<Privilege> existingPrivilege = privilegeRepository.findById(id);
        if (existingPrivilege.isPresent()) {
            Privilege privilege = existingPrivilege.get();
            privilege.setName(newPrivilegeName);
            privilege.setUpdatedAt(new Date());

            privilegeRepository.save(privilege);
            return new ResponseEntity<MessageResponse>().ok(new MessageResponse("Privilege Successfully Updated"));
        }
        return new ResponseEntity<MessageResponse>().noContent("Privilege not found");
    }

    @Override
    public ResponseEntity<List<String>> getAllPrivileges() {
        List<Privilege> privileges = privilegeRepository.findAll();
        List<String> privilegeNames = privileges.stream()
                .map(Privilege::getName).collect(Collectors.toList());

        return new ResponseEntity<List<String>>().ok(privilegeNames);
    }

}
