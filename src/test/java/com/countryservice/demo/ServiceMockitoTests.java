package com.countryservice.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.repositories.CountryRepository;
import com.countryservice.demo.services.CountryService;
@ExtendWith(MockitoExtension.class)

@SpringBootTest(classes = { ServiceMockitoTests.class })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class ServiceMockitoTests {

    @Mock
    CountryRepository countryrep;

    @InjectMocks
    CountryService countryService;

    @Test
    @Order(1)
    public void test_getAllCountries() {
        List<Country> mycountries = new ArrayList<Country>();
        mycountries.add(new Country(1, "India", "Delhi"));
        mycountries.add(new Country(2, "USA", "Washington"));

        when(countryrep.findAll()).thenReturn(mycountries); // Mocking

        assertEquals(2, countryService.getAllCountries().size());
    }
    @Test
    @Order(2)
    public void test_getCountryById() {
        Country country = new Country(1, "India", "Delhi");

        when(countryrep.findById(1)).thenReturn(Optional.of(country)); // Mocking

        assertEquals(1, countryService.getCountryById(1).getIdCountry());
    }

    @Test
    @Order(3)
    public void test_getCountryByName() {
        Country country = new Country(1, "India", "Delhi");

        when(countryrep.findByName("India")).thenReturn(country); // Mocking

        assertEquals("India", countryService.getCountryByName("India").getName());
    }

    @Test
    @Order(4)
    public void test_addCountry() {
        Country country = new Country(3, "Germany", "Berlin");

        when(countryrep.save(country)).thenReturn(country); // Mocking

        assertEquals(country, countryService.addCountry(country));
    }

    @Test
    @Order(5)
    public void test_updateCountry() {
        Country existing = new Country(1, "India", "Delhi");
        Country newData = new Country(1, "India", "New Delhi");

        when(countryrep.findById(1)).thenReturn(Optional.of(existing)); // Mocking
        when(countryrep.save(existing)).thenReturn(existing);           // Mocking

        assertEquals("New Delhi", countryService.updateCountry(1, newData).getCapital());
    }

    @Test
    @Order(6)
    public void test_deleteCountry() {
        countryService.deleteCountry(1);

        verify(countryrep, times(1)).deleteById(1);
    }
}