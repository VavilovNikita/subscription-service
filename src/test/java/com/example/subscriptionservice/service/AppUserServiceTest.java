package com.example.subscriptionservice.service;

import com.example.subscriptionservice.entity.AppUser;
import com.example.subscriptionservice.repository.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AppUserServiceTest {

    @Autowired
    private AppUserRepository appUserRepository;

    private AppUserService appUserService;

    @BeforeEach
    public void setUp() {
        appUserService = new AppUserService(appUserRepository);
    }

    @Test
    public void testCreateAppUser() {
        AppUser user = new AppUser();
        user.setName("Test User");
        user.setEmail("testuser@example.com");

        AppUser createdUser = appUserService.create(user);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getName()).isEqualTo("Test User");
        assertThat(createdUser.getEmail()).isEqualTo("testuser@example.com");
    }

    @Test
    public void testGetAppUserById() {
        AppUser user = new AppUser();
        user.setName("Another User");
        user.setEmail("anotheruser@example.com");
        AppUser createdUser = appUserService.create(user);

        AppUser fetchedUser = appUserService.getById(createdUser.getId()).orElse(null);

        assertThat(fetchedUser).isNotNull();
        assertThat(fetchedUser.getId()).isEqualTo(createdUser.getId());
        assertThat(fetchedUser.getName()).isEqualTo("Another User");
    }

    @Test
    public void testUpdateAppUser() {
        AppUser user = new AppUser();
        user.setName("User To Update");
        user.setEmail("updateme@example.com");
        AppUser createdUser = appUserService.create(user);

        createdUser.setName("Updated Name");
        AppUser updatedUser = appUserService.update(createdUser.getId(), createdUser);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getName()).isEqualTo("Updated Name");
    }

    @Test
    public void testDeleteAppUser() {
        AppUser user = new AppUser();
        user.setName("User To Delete");
        user.setEmail("deleteuser@example.com");
        AppUser createdUser = appUserService.create(user);

        appUserService.delete(createdUser.getId());

        AppUser fetchedUser = appUserService.getById(createdUser.getId()).orElse(null);
        assertThat(fetchedUser).isNull();
    }
}
