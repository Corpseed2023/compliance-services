package com.lawzoom.complianceservice.repository;


import com.lawzoom.complianceservice.model.companyModel.Company;
import com.lawzoom.complianceservice.model.gstdetails.GstDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GstDetailsRepository extends JpaRepository<GstDetails, Long> {

    @Query("SELECT g FROM GstDetails g WHERE g.company = :company")
    List<GstDetails> findByCompany(@Param("company") Company company);

//    @Query("SELECT g FROM GstDetails g WHERE g.company = :company AND g.isEnable = true AND g.isDeleted = false")
//    List<GstDetails> findByEnabledGstDetails(@Param("company") Company company);

    @Query("SELECT g FROM GstDetails g JOIN FETCH g.businessUnits WHERE g.company = :company")
    List<GstDetails> findByEnabledGstDetails(@Param("company") Company company);

    @Query("SELECT g FROM GstDetails g WHERE g.id = :id AND g.isEnable = true AND g.isDeleted = false")
    Optional<GstDetails> findByIdAndEnabledAndNotDeleted(@Param("id") Long id);


    @Query("SELECT COUNT(gd) FROM GstDetails gd WHERE gd.company.id = :companyId")
    Long countByCompanyId(@Param("companyId") Long companyId);

    @Query(value = "SELECT g FROM GstDetails g WHERE g.company.id = :companyId " +
            "AND g.subscription.id = :subscriptionId " +
            "AND g.isEnable = true AND g.isDeleted = false")
    List<GstDetails> findActiveByCompanyAndSubscription(@Param("companyId") Long companyId,
                                                        @Param("subscriptionId") Long subscriptionId);

    @Query(value = "SELECT COUNT(1) > 0 " +
            "FROM gst_details g " +
            "WHERE g.gst_number = :gstNumber " +
            "AND g.subscription_id = :subscriptionId " +
            "AND g.state_id = :stateId",
            nativeQuery = true)
    boolean existsByGstNumberAndSubscriptionIdAndStateId(@Param("gstNumber") String gstNumber,
                                                         @Param("subscriptionId") Long subscriptionId,
                                                         @Param("stateId") Long stateId);



}


