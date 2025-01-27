package com.lawzoom.complianceservice.repository;


import com.lawzoom.complianceservice.model.mileStoneModel.DocumentRequired;
import com.lawzoom.complianceservice.model.mileStoneModel.MileStone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRequiredRepository extends JpaRepository<DocumentRequired, Long> {
    List<DocumentRequired> findByMilestone(MileStone milestone);
}
