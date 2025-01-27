package com.lawzoom.complianceservice.repository;


import com.lawzoom.complianceservice.model.mileStoneModel.DocumentRequired;
import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRequiredRepository extends JpaRepository<DocumentRequired, Long> {
    List<DocumentRequired> findByMilestone(MileStone milestone);

    @Query("SELECT d FROM DocumentRequired d WHERE d.milestone.id = :milestoneId")
    List<DocumentRequired> findByMilestoneId(@Param("milestoneId") Long milestoneId);

}
