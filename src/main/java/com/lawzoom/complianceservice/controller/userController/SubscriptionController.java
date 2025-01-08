package com.lawzoom.complianceservice.controller.userController;

import com.lawzoom.complianceservice.model.user.Subscription;
import com.lawzoom.complianceservice.service.SubscriptionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance/subscription")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // Create a new subscription
    @PostMapping
    @Transactional

    public ResponseEntity<Subscription> createSubscription(@RequestParam String subscription) {
        Subscription createdSubscription = subscriptionService.createSubscription(subscription);
        return ResponseEntity.status(201).body(createdSubscription);
    }

    // Fetch all subscriptions
    @GetMapping
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }
}
