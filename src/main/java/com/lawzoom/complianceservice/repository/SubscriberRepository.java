package com.lawzoom.complianceservice.repository;



import com.lawzoom.complianceservice.model.user.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    /**
     * Finds a subscriber by its super admin ID.
     *
     * @param superAdminId the ID of the super admin
     * @return the optional subscriber entity
     */
    @Query(value = "SELECT * FROM subscribers WHERE super_admin_id = :superAdminId AND is_active = 1", nativeQuery = true)
    Optional<Subscriber> findBySuperAdminId(Long superAdminId);

    /**
     * Finds a subscriber by its subscription ID.
     *
     * @param subscriptionId the ID of the subscription
     * @return the optional subscriber entity
     */
    @Query(value = "SELECT * FROM subscribers WHERE subscription_id = :subscriptionId AND is_active = 1", nativeQuery = true)
    Optional<Subscriber> findBySubscriptionId(Long subscriptionId);

    /**
     * Checks if a subscriber exists for the given super admin ID and subscription ID.
     *
     * @param superAdminId the ID of the super admin
     * @param subscriptionId the ID of the subscription
     * @return true if a subscriber exists, false otherwise
     */
    @Query(value = "SELECT COUNT(*) > 0 FROM subscribers WHERE super_admin_id = :superAdminId AND subscription_id = :subscriptionId", nativeQuery = true)
    boolean existsBySuperAdminIdAndSubscriptionId(Long superAdminId, Long subscriptionId);
}
