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

    @Query(value = "SELECT * FROM gst_details WHERE id = :id AND is_enable = 1 AND is_deleted = 0", nativeQuery = true)
    Optional<GstDetails> findByIdAndEnabledAndNotDeleted(@Param("id") Long id);


    @Query(value = "SELECT EXISTS(SELECT 1 FROM gst_details WHERE gst_number = :gstNumber AND company_id = :companyId AND state_id = :stateId AND is_deleted = 0)", nativeQuery = true)
    boolean existsByGstNumberAndCompanyIdAndStateId(@Param("gstNumber") String gstNumber,
                                                    @Param("companyId") Long companyId,
                                                    @Param("stateId") Long stateId);

    @Query(value = "SELECT * FROM gst_details WHERE company_id = :companyId AND is_deleted = 0", nativeQuery = true)
    List<GstDetails> findAllByCompanyIdAndIsDeletedFalse(@Param("companyId") Long companyId);

    @Query(value = """
        SELECT * 
        FROM gst_details 
        WHERE company_id = :companyId 
          AND is_enable = :isEnable 
          AND is_deleted = false
    """, nativeQuery = true)
    List<GstDetails> findAllByCompanyAndIsEnableAndIsDeletedFalse(
            @Param("companyId") Long companyId,
            @Param("isEnable") boolean isEnable
    );

    @Query(value = "SELECT COUNT(*) FROM gst_details WHERE company_id = :companyId AND is_deleted = 0", nativeQuery = true)
    Long countByCompanyId(@Param("companyId") Long companyId);

}

