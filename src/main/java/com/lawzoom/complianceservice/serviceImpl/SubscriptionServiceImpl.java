package com.lawzoom.complianceservice.serviceImpl;

import com.lawzoom.complianceservice.model.user.Subscription;
import com.lawzoom.complianceservice.repository.SubscriptionRepository;
import com.lawzoom.complianceservice.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription createSubscription(String type) {
        // Check if the subscription type already exists
        if (subscriptionRepository.existsByType(type)) {
            throw new IllegalArgumentException("Subscription type already exists: " + type);
        }

        // Create and save the subscription
        Subscription subscription = new Subscription();
        subscription.setType(type);
        subscription.setStartDate(new Date());
        subscription.setEndDate(new Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000))); // Default to 1 year
        subscription.setCompanySize(0);
        subscription.setUserSize(0);
        subscription.setActive(true);

        return subscriptionRepository.save(subscription);
    }
    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }
}
