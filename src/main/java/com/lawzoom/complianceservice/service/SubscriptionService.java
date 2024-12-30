package com.lawzoom.complianceservice.service;


import com.lawzoom.complianceservice.dto.subscriptionDTO.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {


    List<SubscriptionResponse> fetchSubscriptionsByUser(Long userId);

    SubscriptionResponse createSubscription(String type);
}
