package com.lawzoom.complianceservice.service.impl;

import com.lawzoom.complianceservice.model.user.Subscriber;
import com.lawzoom.complianceservice.model.user.Subscription;
import com.lawzoom.complianceservice.model.user.User;
import com.lawzoom.complianceservice.repository.SubscriberRepository;
import com.lawzoom.complianceservice.repository.SubscriptionRepository;
import com.lawzoom.complianceservice.repository.UserRepository;
import com.lawzoom.complianceservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Override
    public User createUser(User user, Long subscriptionId) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists: " + user.getEmail());
        }

        // Find or create a subscriber for the user
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found with ID: " + subscriptionId));

        Subscriber subscriber = new Subscriber();
        subscriber.setSubscription(subscription);
        subscriber.setActive(true);
        Subscriber savedSubscriber = subscriberRepository.save(subscriber);

        // Map the user to the subscriber
        user.setSubscriber(savedSubscriber);

        return userRepository.save(user);
    }
}
