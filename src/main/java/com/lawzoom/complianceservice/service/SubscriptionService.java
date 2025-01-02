package com.lawzoom.complianceservice.service;

import com.lawzoom.complianceservice.model.user.Subscription;

import java.util.List;

public interface SubscriptionService {
    Subscription createSubscription(Subscription subscription);
    List<Subscription> getAllSubscriptions();
}
