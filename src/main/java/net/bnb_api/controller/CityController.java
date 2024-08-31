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

import net.bnb_api.entity.City;
import net.bnb_api.service.CityService;

@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @PostMapping("/save_city")
    public ResponseEntity<City> saveCity(@RequestBody City country) {
        return new ResponseEntity<>(cityService.save(country), HttpStatus.CREATED);
    }

    @GetMapping("/all_cities")
    public ResponseEntity<List<City>> findAllCities() {
        return new ResponseEntity<>(cityService.findAll(), HttpStatus.FOUND);
    }

    @GetMapping("/find_city_by_name")
    public ResponseEntity<City> findCityByName(@RequestParam String name) {
        return new ResponseEntity<>(cityService.findByName(name), HttpStatus.FOUND);
    }

    @DeleteMapping("/delete_city")
    public String deleteCity(@RequestParam Long id) {
        boolean deleted = cityService.delete(id);
        if (deleted) {
            return "City deleted";
        } else {
            return "City not found";
        }
    }

    @PutMapping("/update_city")
    public ResponseEntity<City> updateCity(@RequestBody City city, @RequestParam String name) {
        City updatedCity = cityService.update(city, name);
        if (updatedCity != null) {
            return new ResponseEntity<>(updatedCity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
