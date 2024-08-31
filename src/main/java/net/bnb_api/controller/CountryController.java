package net.bnb_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.bnb_api.entity.Country;
import net.bnb_api.service.CountryService;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping("/save_country")
    public ResponseEntity<Country> saveCountry(@RequestBody Country country) {
        return new ResponseEntity<>(countryService.save(country), HttpStatus.CREATED);
    }

    @GetMapping("/all_countries")
    public ResponseEntity<List<Country>> findAllCountries() {
        return new ResponseEntity<>(countryService.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/find_country_by_name")
    public ResponseEntity<Country> findCountryByName(@RequestParam String name) {
        return new ResponseEntity<>(countryService.findByName(name), HttpStatus.FOUND);
    }

    @DeleteMapping("/delete_country")
    public String deleteCountry(@RequestParam Long id) {
        boolean deleted = countryService.delete(id);
        if (deleted) {
            return "Country deleted";
        } else {
            return "Country not found";
        }
    }

    @PutMapping("/update_country")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country, @RequestParam String name) {
        Country updatedCountry = countryService.update(country, name);
        if (updatedCountry != null) {
            return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
