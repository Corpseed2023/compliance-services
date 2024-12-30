package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    Optional<Subscription> findByType(String basic);

    boolean existsByType(String type);
}
