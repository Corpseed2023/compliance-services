package com.lawzoom.complianceservice.repository;


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



}
