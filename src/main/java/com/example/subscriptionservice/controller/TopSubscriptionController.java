package com.example.subscriptionservice.controller;

import com.example.subscriptionservice.entity.Subscription;
import com.example.subscriptionservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class TopSubscriptionController {

    private final SubscriptionRepository repository;

    @GetMapping("/top")
    public List<Map.Entry<String, Long>> getTopSubscriptions() {
        return repository.findAll().stream()
            .collect(Collectors.groupingBy(
                Subscription::getServiceName,
                Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(3)
            .toList();
    }
}
