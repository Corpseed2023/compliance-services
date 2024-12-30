package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByMobileOrEmail(String mobile, String email);

    @Query(value = "SELECT COUNT(*) > 0 FROM user WHERE (email = :email OR mobile = :mobile) AND company_id = :companyId", nativeQuery= true)
    int existsByEmailOrMobileAndCompanyId(@Param("email") String email, @Param("mobile") String mobile,
                                                 @Param("companyId") Long companyId);

    @Query("SELECT u FROM User u WHERE u.id = :userId AND u.isEnable = true")
    User findByUser(@Param("userId") Long userId);

    @Query("SELECT u FROM User u WHERE u.id = :id AND u.isEnable = true AND u.isDeleted = false")
    Optional<User> findByIdAndIsEnableAndNotDeleted(@Param("id") Long id);

    @Query(value = "SELECT * FROM user u WHERE u.id = :id", nativeQuery= true)
    User findByIdAndNotDeleted(@Param("id") Long id);
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isEnable = true AND u.isDeleted = false")
    Optional<User> findByEmailAndIsEnableAndNotDeleted(@Param("email") String email);





}
