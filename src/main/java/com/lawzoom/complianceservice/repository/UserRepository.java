package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    Optional<Object> findByMobileOrEmail(String mobile, String email);
}
