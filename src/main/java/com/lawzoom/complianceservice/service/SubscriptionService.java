package com.lawzoom.complianceservice.service;

import com.lawzoom.complianceservice.model.user.Subscription;

import java.util.List;

public interface SubscriptionService {
    List<Subscription> getAllSubscriptions();

    Subscription createSubscription(String subscription);
}
