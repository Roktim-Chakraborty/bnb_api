package net.bnb_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.bnb_api.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsernameOrEmail(String username, String email);
}
