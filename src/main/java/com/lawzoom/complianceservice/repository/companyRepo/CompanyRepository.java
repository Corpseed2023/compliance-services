package com.lawzoom.complianceservice.repository.companyRepo;


import com.lawzoom.complianceservice.model.companyModel.Company;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {


    @Query("SELECT c FROM Company c WHERE c.id = :id AND c.isEnable = true AND c.isDeleted = false")
    Optional<Company> findEnabledAndNotDeletedCompanyById(@Param("id") Long id);

    @Query("SELECT c FROM Company c WHERE c.id = :companyId AND c.subscriber.id = :subscriberId AND c.isEnable = true AND c.isDeleted = false")
    Optional<Company> findByIdAndSubscriberIdAndIsEnableAndIsDeletedFalse(@Param("companyId") Long companyId,
                                                                          @Param("subscriberId") Long subscriberId);

    @Query(value = "SELECT * FROM company c " +
            "WHERE c.subscriber_id = :subscriberId " +
            "AND c.is_enable = 1 " +
            "AND c.is_deleted = 0", nativeQuery = true)
    List<Company> findBySubscriberAndIsEnableAndIsDeletedFalse(@Param("subscriberId") Long subscriberId);

    @Query(value = """
    SELECT * 
    FROM company 
    WHERE super_admin_id = :superAdminId 
      AND subscriber_id = :subscriberId 
      AND is_deleted = 0
""", nativeQuery = true)
    List<Company> findCompaniesBySuperAdminAndSubscriber(
            @Param("superAdminId") Long superAdminId,
            @Param("subscriberId") Long subscriberId
    );




}

