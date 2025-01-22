package com.lawzoom.complianceservice.repository.UserRepository;

import com.lawzoom.complianceservice.model.gstdetails.GstDetails;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM user WHERE id = :userId AND is_enable = 1 AND is_deleted = 0", nativeQuery = true)
    User findActiveUserById(@Param("userId") Long userId);

    List<User> findAllBySubscriber(@Param("subscriber") Subscriber subscriber);


}
