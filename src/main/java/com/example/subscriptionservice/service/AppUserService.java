package com.example.subscriptionservice.service;

import com.example.subscriptionservice.entity.AppUser;
import com.example.subscriptionservice.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository repository;

    public AppUser create(AppUser user) {
        return repository.save(user);
    }

    public Optional<AppUser> getById(Long id) {
        return repository.findById(id);
    }

    public AppUser update(Long id, AppUser updated) {
        updated.setId(id);
        return repository.save(updated);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
