package com.lawzoom.complianceservice.repository;


import com.lawzoom.complianceservice.model.user.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByType(String type);
}
