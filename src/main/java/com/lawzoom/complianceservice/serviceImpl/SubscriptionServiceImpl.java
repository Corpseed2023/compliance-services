package com.lawzoom.complianceservice.serviceImpl;


import com.lawzoom.complianceservice.dto.subscriptionDTO.SubscriptionResponse;
import com.lawzoom.complianceservice.exception.NotFoundException;
import com.lawzoom.complianceservice.model.Subscription;
import com.lawzoom.complianceservice.model.User;
import com.lawzoom.complianceservice.repository.SubscriptionRepository;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public SubscriptionResponse createSubscription(String type) {
        // Check for duplicate subscription type

        if (subscriptionRepository.existsByType(type)) {
            throw new IllegalArgumentException("Subscription type already exists: " + type);
        }

        Subscription subscription = new Subscription();
        subscription.setType(type);

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return new SubscriptionResponse(savedSubscription.getId(), savedSubscription.getType());
    }

    @Override
    public List<SubscriptionResponse> fetchSubscriptionsByUser(Long userId) throws NotFoundException {
        User user = userRepository.findByIdAndNotDeleted(userId);


        return user.getSubscriptions().stream()
                .map(subscription -> new SubscriptionResponse(subscription.getId(), subscription.getType()))
                .collect(Collectors.toList());
    }


}
