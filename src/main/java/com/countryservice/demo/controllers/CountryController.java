package com.countryservice.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.services.CountryService;

@RestController
public class CountryController {

    @Autowired
    CountryService countryservice;

    @GetMapping("/getcountries")
    public ResponseEntity<List<Country>> getCountries() {
        try {
            List<Country> countries = countryservice.getAllCountries();
            return new ResponseEntity<List<Country>>(countries, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getcountries/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable(value = "id") int id) {
        try {
            Country country = countryservice.getCountryById(id);
            return new ResponseEntity<Country>(country, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getcountries/countryname")
    public ResponseEntity<Country> getCountryByName(@RequestParam(value = "name") String countryName) {
        try {
            Country country = countryservice.getCountryByName(countryName);
            return new ResponseEntity<Country>(country, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addcountry")
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        try {
            Country created = countryservice.addCountry(country);
            return new ResponseEntity<Country>(created, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updatecountry/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") int id,
                                                 @RequestBody Country country) {
        try {
            Country updated = countryservice.updateCountry(id, country);
            return new ResponseEntity<Country>(updated, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletecountry/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable(value = "id") int id) {
        try {
            countryservice.deleteCountry(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}