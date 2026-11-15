package com.countryservice.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.repositories.CountryRepository;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRep;

    public List<Country> getAllCountries() {
        List<Country> countries = countryRep.findAll();
        return countries;
    }

    public Country getCountryById(int id) {
        return countryRep.findById(id).get();
    }

    public Country getCountryByName(String name) {
        return countryRep.findByName(name);
    }

    public Country addCountry(Country country) {
        return countryRep.save(country);
    }

    public Country updateCountry(int id, Country country) {
        Country existing = countryRep.findById(id).get();
        existing.setName(country.getName());
        existing.setCapital(country.getCapital());
        return countryRep.save(existing);
    }

    public void deleteCountry(int id) {
        countryRep.deleteById(id);
    }
}