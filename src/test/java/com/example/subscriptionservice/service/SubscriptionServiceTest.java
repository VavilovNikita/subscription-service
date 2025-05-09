package com.example.subscriptionservice.service;

import com.example.subscriptionservice.entity.AppUser;
import com.example.subscriptionservice.entity.Subscription;
import com.example.subscriptionservice.repository.AppUserRepository;
import com.example.subscriptionservice.repository.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private AppUser user;

    @BeforeEach
    public void setUp() {
        user = new AppUser();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("testuser@example.com");
    }

    @Test
    public void testAddSubscription() {
        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setServiceName("Premium Subscription");

        when(appUserRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        Subscription createdSubscription = subscriptionService.addSubscription(1L, subscription);

        assertThat(createdSubscription).isNotNull();
        assertThat(createdSubscription.getId()).isEqualTo(1L);
        assertThat(createdSubscription.getServiceName()).isEqualTo("Premium Subscription");
        assertThat(createdSubscription.getUser()).isEqualTo(user);

        verify(appUserRepository).findById(1L);
        verify(subscriptionRepository).save(subscription);
    }

    @Test
    public void testGetUserSubscriptions() {
        Subscription subscription1 = new Subscription();
        subscription1.setId(1L);
        subscription1.setServiceName("Subscription 1");

        Subscription subscription2 = new Subscription();
        subscription2.setId(2L);
        subscription2.setServiceName("Subscription 2");

        when(subscriptionRepository.findByUserId(1L)).thenReturn(java.util.List.of(subscription1, subscription2));

        List<Subscription> subscriptions = subscriptionService.getUserSubscriptions(1L);

        assertThat(subscriptions).isNotEmpty();
        assertThat(subscriptions.size()).isEqualTo(2);
        assertThat(subscriptions.get(0).getServiceName()).isEqualTo("Subscription 1");
        assertThat(subscriptions.get(1).getServiceName()).isEqualTo("Subscription 2");

        verify(subscriptionRepository).findByUserId(1L);
    }

    @Test
    public void testDeleteSubscription() {
        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setServiceName("Premium Subscription");

        doNothing().when(subscriptionRepository).deleteById(1L);

        subscriptionService.deleteSubscription(1L);

        verify(subscriptionRepository).deleteById(1L);
    }

    @Test
    public void testAddSubscription_UserNotFound() {
        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setServiceName("Premium Subscription");

        when(appUserRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        try {
            subscriptionService.addSubscription(1L, subscription);
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("User not found");
        }

        verify(appUserRepository).findById(1L);
        verify(subscriptionRepository, never()).save(any());
    }
}
