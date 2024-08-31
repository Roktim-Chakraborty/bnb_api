package net.bnb_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bnb_api.entity.City;
import net.bnb_api.repository.CityRepo;

@Service
public class CityService {

    @Autowired
    private CityRepo cityRepo;

    public City save(City city) {
        return cityRepo.save(city);
    }

    public List<City> findAll() {
        return cityRepo.findAll();
    }

    public City findByName(String name) {
        return cityRepo.findByName(name).get();
    }

    public boolean delete(Long id) {
        cityRepo.deleteById(id);
        return true;
    }

    public City update(City city, String name) {
        Optional<City> dbCity = cityRepo.findByName(city.getName());
        if (dbCity.isPresent()) {
            dbCity.get().setName(name);
            return cityRepo.save(dbCity.get());
        } else {
            return null;
        }
    }
}
