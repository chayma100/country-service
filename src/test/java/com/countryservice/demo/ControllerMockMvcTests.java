package com.countryservice.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = { ControllerMockMvcTests.class })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerMockMvcTests {

    MockMvc mockMvc;

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test
    @Order(1)
    public void test_getCountries() throws Exception {
        List<Country> mycountries = new ArrayList<Country>();
        mycountries.add(new Country(1, "India", "Delhi"));
        mycountries.add(new Country(2, "USA", "Washington"));

        when(countryService.getAllCountries()).thenReturn(mycountries); // Mocking

        this.mockMvc.perform(get("/getcountries"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].name").value("India"));
    }

    @Test
    @Order(2)
    public void test_getCountryByID() throws Exception {
        Country country = new Country(1, "India", "Delhi");
        int countryID = 1;

        when(countryService.getCountryById(countryID)).thenReturn(country); // Mocking

        this.mockMvc.perform(get("/getcountries/{id}", countryID))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.idCountry").value(1))
                .andExpect(jsonPath("$.name").value("India"))
                .andExpect(jsonPath("$.capital").value("Delhi"));
    }

    @Test
    @Order(3)
    public void test_getCountryByName() throws Exception {
        Country country = new Country(1, "India", "Delhi");

        when(countryService.getCountryByName("India")).thenReturn(country); // Mocking

        this.mockMvc.perform(get("/getcountries/countryname").param("name", "India"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.name").value("India"));
    }

    @Test
    @Order(4)
    public void test_addCountry() throws Exception {
        Country country = new Country(3, "Germany", "Berlin");

        when(countryService.addCountry(any(Country.class))).thenReturn(country); // Mocking

        this.mockMvc.perform(post("/addcountry")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idCountry\":3,\"name\":\"Germany\",\"capital\":\"Berlin\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Germany"));
    }

    @Test
    @Order(5)
    public void test_updateCountry() throws Exception {
        Country country = new Country(1, "India", "New Delhi");

        when(countryService.updateCountry(eq(1), any(Country.class))).thenReturn(country); // Mocking

        this.mockMvc.perform(put("/updatecountry/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idCountry\":1,\"name\":\"India\",\"capital\":\"New Delhi\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.capital").value("New Delhi"));
    }

    @Test
    @Order(6)
    public void test_deleteCountry() throws Exception {
        this.mockMvc.perform(delete("/deletecountry/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk());

        verify(countryService, times(1)).deleteCountry(1);
    }
}