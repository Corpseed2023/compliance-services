package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.complianceTaskModel.MileStone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilestoneRepository extends JpaRepository<MileStone, Long> {

    @Query(value = """
        SELECT * 
        FROM milestone 
        WHERE compliance_id = :complianceId 
          AND business_unit_id = :businessUnitId 
          AND subscriber_id = :subscriberId 
          AND company_id = :companyId 
          AND is_enable = 1
    """, nativeQuery = true)
    List<MileStone> findMilestonesByParameters(
            @Param("complianceId") Long complianceId,
            @Param("businessUnitId") Long businessUnitId,
            @Param("subscriberId") Long subscriberId,
            @Param("companyId") Long companyId
    );
}
