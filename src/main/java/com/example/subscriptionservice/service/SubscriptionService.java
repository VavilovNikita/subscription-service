package com.example.subscriptionservice.service;

import com.example.subscriptionservice.entity.AppUser;
import com.example.subscriptionservice.entity.Subscription;
import com.example.subscriptionservice.repository.AppUserRepository;
import com.example.subscriptionservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository repository;
    private final AppUserRepository userRepository;

    public Subscription addSubscription(Long userId, Subscription subscription) {
        AppUser user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        subscription.setUser(user);
        return repository.save(subscription);
    }

    public List<Subscription> getUserSubscriptions(Long userId) {
        return repository.findByUserId(userId);
    }

    public void deleteSubscription(Long subId) {
        repository.deleteById(subId);
    }
}
