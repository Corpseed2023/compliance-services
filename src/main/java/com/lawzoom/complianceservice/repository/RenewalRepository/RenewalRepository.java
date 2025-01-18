package com.lawzoom.complianceservice.repository.RenewalRepository;


import com.lawzoom.complianceservice.model.renewalModel.Renewal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenewalRepository extends JpaRepository<Renewal, Long> {
    Renewal findByMilestoneId(Long milestoneId);
}
