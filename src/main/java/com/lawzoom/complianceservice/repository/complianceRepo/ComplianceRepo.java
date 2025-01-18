package com.lawzoom.complianceservice.repository.complianceRepo;


import com.lawzoom.complianceservice.model.complianceModel.Compliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplianceRepo extends JpaRepository<Compliance, Long> {


    List<Compliance> findByBusinessUnitId(Long businessUnitId);

    @Query(value = """
        SELECT * 
        FROM compliance 
        WHERE business_unit_id = :businessUnitId 
          AND is_enable = :isEnable 
          AND is_deleted = false
    """, nativeQuery = true)
    List<Compliance> findCompliancesByBusinessUnitAndStatus(
            @Param("businessUnitId") Long businessUnitId,
            @Param("isEnable") boolean isEnable
    );

    @Query("SELECT c FROM Compliance c WHERE c.businessUnit.id = :businessUnitId AND c.isEnable = true AND c.isDeleted = false")
    List<Compliance> findActiveCompliances(@Param("businessUnitId") Long businessUnitId);




}
