package com.lawzoom.complianceservice.controller.userController;

import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compliance/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<User> createUser(
            @RequestBody User user,
            @RequestParam Long subscriptionId) {
        User createdUser = userService.createUser(user, subscriptionId);
        return ResponseEntity.status(201).body(createdUser);
    }
}
