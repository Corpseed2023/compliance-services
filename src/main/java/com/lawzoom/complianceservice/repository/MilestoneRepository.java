package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.complianceMileStoneModel.MileStone;
import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.User;
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
          AND is_enable = 1
    """, nativeQuery = true)
    List<MileStone> findMilestonesByParameters(
            @Param("complianceId") Long complianceId,
            @Param("businessUnitId") Long businessUnitId,
            @Param("subscriberId") Long subscriberId
    );

    List<MileStone> findBySubscriber(Subscriber subscriber);

    List<MileStone> findBySubscriberAndAssignedToAndStatus(Subscriber subscriber, User assignedTo, String status);

    @Query(value = """
        SELECT * 
        FROM milestone 
        WHERE subscriber_id = :subscriberId 
          AND is_enable = 1
    """, nativeQuery = true)
    List<MileStone> findMilestonesBySubscriber(@Param("subscriberId") Long subscriberId);

    @Query(value = """
        SELECT * 
        FROM milestone 
        WHERE subscriber_id = :subscriberId 
          AND assigned_to = :userId 
          AND is_enable = 1
    """, nativeQuery = true)
    List<MileStone> findMilestonesBySubscriberAndAssignedTo(
            @Param("subscriberId") Long subscriberId,
            @Param("userId") Long userId
    );



}
