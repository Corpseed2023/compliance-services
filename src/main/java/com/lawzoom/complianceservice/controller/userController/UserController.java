package com.lawzoom.complianceservice.controller;

import com.lawzoom.complianceservice.dto.teamMemberDto.MemberRequest;
import com.lawzoom.complianceservice.dto.teamMemberDto.MemberResponse;
import com.lawzoom.complianceservice.dto.userDto.UserRequest;
import com.lawzoom.complianceservice.dto.userDto.UserResponse;
import com.lawzoom.complianceservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
        try {
            UserResponse createdUser = userService.createUser(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }

    @PostMapping("/addTeamMember")
    public ResponseEntity<?> addTeamMember(@Valid @RequestBody MemberRequest memberRequest) {
        try {
            MemberResponse memberResponse = userService.createTeamMemberUser(memberRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(memberResponse);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }

    @GetMapping("/fetch-members")
    public ResponseEntity<?> fetchMembers(@RequestParam Long subscriberId, @RequestParam Long userId) {
        try {
            List<MemberResponse> members = userService.fetchMembers(subscriberId, userId);
            return ResponseEntity.ok(members);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + ex.getMessage());
        }
    }


}
