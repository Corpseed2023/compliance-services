package com.lawzoom.complianceservice.repository;

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

    boolean existsByUserName(String userName);

    @Query(value = "SELECT * FROM user WHERE id = :userId AND is_enable = 1 AND is_deleted = 0", nativeQuery = true)
    User findActiveUserById(@Param("userId") Long userId);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName = 'SUPER_ADMIN' AND u.subscriber.id = :subscriberId")
    User findSuperAdminBySubscriberId(@Param("subscriberId") Long subscriberId);    @Query("SELECT u FROM User u WHERE u.subscriber = :subscriber")
    List<User> findAllBySubscriber(@Param("subscriber") Subscriber subscriber);





}
