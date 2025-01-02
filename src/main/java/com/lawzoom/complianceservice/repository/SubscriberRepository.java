package com.lawzoom.complianceservice.repository;

import com.lawzoom.complianceservice.model.user.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
}
