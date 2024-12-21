package com.lawzoom.complianceservice.controller;


import com.lawzoom.complianceservice.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/compliance/user")
public class UserController {
    @Autowired
    private UserService userService;


    @ApiOperation(value = "Return signup result", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered"),
            @ApiResponse(code = 500, message = "Something Went-Wrong"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping("/save")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        return this.userService.createUser(userRequest);
    }





}
