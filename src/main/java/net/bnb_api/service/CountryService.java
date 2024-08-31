package net.bnb_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bnb_api.entity.Country;
import net.bnb_api.repository.CountryRepo;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CountryRepo countryRepo;

    public Country save(Country country) {
        return countryRepo.save(country);
    }

    public List<Country> findAll() {
        return countryRepo.findAll();
    }

    public Country findByName(String name) {
        return countryRepo.findByName(name).get();
    }

    public boolean delete(Long id) {
        countryRepo.deleteById(id);
        return true;
    }

    public Country update(Country country, String name) {
        Optional<Country> dbCountry = countryRepo.findByName(country.getName());
        if (dbCountry.isPresent()) {
            dbCountry.get().setName(name);
            return countryRepo.save(dbCountry.get());
        } else {
            return null;
        }
    }
}
