package net.bnb_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.bnb_api.entity.City;

import java.util.Optional;

public interface CityRepo extends JpaRepository<City, Long> {

    Optional<City> findByName(String name);

}
