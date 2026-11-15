package com.countryservice.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = { ControllerMockitoTests.class })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerMockitoTests {

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    @Test
    @Order(1)
    public void test_getAllCountries() {
        List<Country> mycountries = new ArrayList<Country>();
        mycountries.add(new Country(1, "India", "Delhi"));
        mycountries.add(new Country(2, "USA", "Washington"));

        when(countryService.getAllCountries()).thenReturn(mycountries); // Mocking

        ResponseEntity<List<Country>> res = countryController.getCountries();

        assertEquals(HttpStatus.FOUND, res.getStatusCode());
        assertEquals(2, res.getBody().size());
    }

    @Test
    @Order(2)
    public void test_getCountryById() {
        Country country = new Country(1, "India", "Delhi");

        when(countryService.getCountryById(1)).thenReturn(country); // Mocking

        ResponseEntity<Country> res = countryController.getCountryById(1);

        assertEquals(HttpStatus.FOUND, res.getStatusCode());
        assertEquals(1, res.getBody().getIdCountry());
    }

    @Test
    @Order(3)
    public void test_getCountryByName() {
        Country country = new Country(1, "India", "Delhi");

        when(countryService.getCountryByName("India")).thenReturn(country); // Mocking

        ResponseEntity<Country> res = countryController.getCountryByName("India");

        assertEquals(HttpStatus.FOUND, res.getStatusCode());
        assertEquals("India", res.getBody().getName());
    }

    @Test
    @Order(4)
    public void test_addCountry() {
        Country country = new Country(3, "Germany", "Berlin");

        when(countryService.addCountry(country)).thenReturn(country); // Mocking

        ResponseEntity<Country> res = countryController.addCountry(country);

        assertEquals(HttpStatus.CREATED, res.getStatusCode());
        assertEquals(country, res.getBody());
    }

    @Test
    @Order(5)
    public void test_updateCountry() {
        Country country = new Country(1, "India", "New Delhi");

        when(countryService.updateCountry(1, country)).thenReturn(country); // Mocking

        ResponseEntity<Country> res = countryController.updateCountry(1, country);

        assertEquals(HttpStatus.OK, res.getStatusCode());
        assertEquals("New Delhi", res.getBody().getCapital());
    }

    @Test
    @Order(6)
    public void test_deleteCountry() {
        ResponseEntity<Void> res = countryController.deleteCountry(1);

        assertEquals(HttpStatus.OK, res.getStatusCode());
        verify(countryService, times(1)).deleteCountry(1);
    }
}