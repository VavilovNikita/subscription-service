package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.entity.Subscription;
import com.example.subscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService service;

    @PostMapping
    public ResponseEntity<Subscription> add(@PathVariable Long userId,
                                            @RequestBody Subscription sub) {
        return ResponseEntity.ok(service.addSubscription(userId, sub));
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getAll(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getUserSubscriptions(userId));
    }

    @DeleteMapping("/{subId}")
    public ResponseEntity<Void> delete(@PathVariable Long subId) {
        service.deleteSubscription(subId);
        return ResponseEntity.noContent().build();
    }
}
