package com.example.subscriptionservice.service;

import com.example.subscriptionservice.entity.AppUser;
import com.example.subscriptionservice.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;  // Импортируем для логирования
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService {

    private final AppUserRepository repository;

    public AppUser create(AppUser user) {
        log.info("Creating user with email: {}", user.getEmail());
        AppUser createdUser = repository.save(user);
        log.info("User created with id: {}", createdUser.getId());
        return createdUser;
    }

    public Optional<AppUser> getById(Long id) {
        log.info("Fetching user with id: {}", id);
        Optional<AppUser> user = repository.findById(id);
        if (user.isPresent()) {
            log.info("User found with id: {}", id);
        } else {
            log.warn("User not found with id: {}", id);
        }
        return user;
    }

    public AppUser update(Long id, AppUser updated) {
        updated.setId(id);
        log.info("Updating user with id: {}", id);
        AppUser updatedUser = repository.save(updated);
        log.info("User updated with id: {}", updatedUser.getId());
        return updatedUser;
    }

    public void delete(Long id) {
        log.info("Deleting user with id: {}", id);
        repository.deleteById(id);
        log.info("User deleted with id: {}", id);
    }
}
