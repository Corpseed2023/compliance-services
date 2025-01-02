package com.lawzoom.complianceservice.service.impl;

import com.lawzoom.complianceservice.model.user.Subscription;
import com.lawzoom.complianceservice.repository.SubscriptionRepository;
import com.lawzoom.complianceservice.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription createSubscription(Subscription subscription) {
        if (subscriptionRepository.existsByType(subscription.getType())) {
            throw new RuntimeException("Subscription type already exists: " + subscription.getType());
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }
}
