package com.lawzoom.complianceservice.repository.RenewalRepository;


import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RenewalRepository extends JpaRepository<Renewal, Long> {
    Renewal findByMilestoneId(Long milestoneId);

    @Query("""
        SELECT r FROM Renewal r
        WHERE r.milestone.id = :milestoneId AND r.user.id = :userId
    """)
    List<Renewal> findByMilestoneIdAndUserId(@Param("milestoneId") Long milestoneId,
                                             @Param("userId") Long userId);


    List<Renewal> findByUserId(Long userId);
}
