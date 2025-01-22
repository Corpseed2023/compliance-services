//package com.lawzoom.complianceservice.serviceImpl;
//
//import com.lawzoom.complianceservice.repository.UserRepository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByEmailAndIsEnableAndNotDeleted(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
//
//        return UserDetailsImpl.build(user);
//    }
//}
