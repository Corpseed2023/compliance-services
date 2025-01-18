package com.lawzoom.complianceservice.repository.complianceRepo;

import com.lawzoom.complianceservice.model.complianceModel.ComplianceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplianceHistoryRepository extends JpaRepository<ComplianceHistory,Long> {
}
