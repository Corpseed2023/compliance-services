package com.lawzoom.complianceservice.repository;


import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenewalRepository extends JpaRepository<Renewal, Long> {
    Renewal findByComplianceId(Long complianceId);
}
