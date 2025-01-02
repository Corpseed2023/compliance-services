package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.model.user.User;
import org.springframework.http.ResponseEntity;

public interface UserService {


    User createUser(User user, Long subscriptionId);
}
