package com.example.subscriptionservice.service;

import com.example.subscriptionservice.entity.AppUser;
import com.example.subscriptionservice.entity.Subscription;
import com.example.subscriptionservice.repository.AppUserRepository;
import com.example.subscriptionservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;  // Импортируем для логирования
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository repository;
    private final AppUserRepository userRepository;

    public Subscription addSubscription(Long userId, Subscription subscription) {
        log.info("Adding subscription for user with id: {}", userId);
        AppUser user = userRepository.findById(userId)
            .orElseThrow(() -> {
                log.error("User not found with id: {}", userId);
                return new RuntimeException("User not found");
            });
        subscription.setUser(user);
        Subscription savedSubscription = repository.save(subscription);
        log.info("Subscription added with id: {}", savedSubscription.getId());
        return savedSubscription;
    }

    public List<Subscription> getUserSubscriptions(Long userId) {
        log.info("Fetching subscriptions for user with id: {}", userId);
        List<Subscription> subscriptions = repository.findByUserId(userId);
        if (subscriptions.isEmpty()) {
            log.warn("No subscriptions found for user with id: {}", userId);
        } else {
            log.info("Found {} subscriptions for user with id: {}", subscriptions.size(), userId);
        }
        return subscriptions;
    }

    public void deleteSubscription(Long subId) {
        log.info("Deleting subscription with id: {}", subId);
        repository.deleteById(subId);
        log.info("Subscription deleted with id: {}", subId);
    }
}
