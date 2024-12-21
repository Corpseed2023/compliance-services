package com.lawzoom.complianceservice.serviceimpl;

import com.lawzoom.complianceservice.dto.userDto.UserRequest;
import com.lawzoom.complianceservice.model.User;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.utility.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;


    @Override
    public ResponseEntity<?> createUser(UserRequest userRequest) {

        User findUser = this.userRepository.findByMobileOrEmail(userRequest.getMobile(), userRequest.getEmail()).orElse(null);
        if (findUser != null)
            return new ResponseEntity<String>().badRequest("Error : User Already Exist !!");

        User saveUser = new User();
        saveUser.setUserName(userRequest.getUserName());
        saveUser.setEmail(userRequest.getEmail());
        saveUser.setMobile(userRequest.getMobile());
        saveUser.setCreatedAt(new Date());
        saveUser.setUpdatedAt(new Date());
        saveUser.setEnable(true);
        saveUser.setRoles(userRequest.getr());

        this.userRepository.save(saveUser);

        return new ResponseEntity<User>().ok(saveUser);
    }
}

