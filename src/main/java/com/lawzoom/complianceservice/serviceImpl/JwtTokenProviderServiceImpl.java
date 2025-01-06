//package com.lawzoom.complianceservice.serviceImpl;
//
//import com.lawzoom.complianceservice.constant.SecurityConstants;
//import com.lawzoom.complianceservice.service.JwtTokenProviderService;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
//@Service
//public class JwtTokenProviderServiceImpl implements JwtTokenProviderService {
//
//
//    @Override
//    public String generateJwtToken(Authentication authentication) {
//        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
//
//        return Jwts.builder()
//                .setId(userPrincipal.getId().toString())
//                .setSubject((userPrincipal.getUsername()))
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + SecurityConstants.EXPIRATION_MS))
//                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET_KEY)
//                .compact();
//    }
//}
