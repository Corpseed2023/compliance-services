package com.lawzoom.complianceservice.feignClient;


import com.lawzoom.complianceservice.dto.userDto.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "AUTHENTICATION-SERVICE", url = "http://localhost:8081")
@Service
public interface AuthenticationFeignClient {

    @GetMapping("/api/auth/user/getUserId")
    UserRequest getUserId(@RequestParam Long userId);


}
