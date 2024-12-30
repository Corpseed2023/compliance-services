package com.lawzoom.complianceservice.repository.companyRepo;


import com.lawzoom.complianceservice.model.Subscription;
import com.lawzoom.complianceservice.model.companyModel.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.superAdminId.id = :superAdminId AND c.subscription.id = :subscriptionId AND c.isEnable = true AND c.isDeleted = false")
    List<Company> findBySuperAdminIdAndSubscription(@Param("superAdminId") Long superAdminId, @Param("subscriptionId") Long subscriptionId);

    @Query("SELECT c FROM Company c WHERE c.isEnable = true AND c.isDeleted = false")
    List<Company> findAllEnabledAndNotDeletedCompanies();

    @Query("SELECT c FROM Company c WHERE c.id = :id AND c.isEnable = true AND c.isDeleted = false")
    Optional<Company> findEnabledAndNotDeletedCompanyById(@Param("id") Long id);

    @Query("SELECT c FROM Company c WHERE c.id = :id AND c.isEnable = true AND c.isDeleted = false")
    Optional<Company> findByEnabledAndNotDeleted(@Param("id") Long id);



    // Modify this method to correctly reference the Subscription entity
    List<Company> findBySubscription(Subscription subscription);}

