package net.bnb_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.bnb_api.entity.Country;

import java.util.Optional;

public interface CountryRepo extends JpaRepository<Country, Long> {
    
    Optional<Country> findByName(String name);
}
