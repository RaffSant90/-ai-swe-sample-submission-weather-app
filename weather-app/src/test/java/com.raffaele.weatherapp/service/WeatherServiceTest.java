package com.raffaele.weatherapp.service;

import com.raffaele.weatherapp.model.Location;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherServiceTest {

    private final WeatherService service = new WeatherService();

    @Test
    void testSearchLocations() {
        List<Location> results = service.searchLocations("Roma");

        assertNotNull(results);
        assertFalse(results.isEmpty());

        Location first = results.get(0);

        assertNotNull(first.getName());
        assertNotNull(first.getCountry());
    }

    @Test
    void testGetWeatherFromLocation() {
        Location location = new Location("Roma", "Italy", 41.9028, 12.4964);

        var weather = service.getWeatherFromLocation(location);

        assertNotNull(weather);
        assertTrue(weather.getTemperature() > -50); // valore realistico
    }

    @Test
    void testSearchMultipleCities() {
        var roma = service.searchLocations("Roma");
        var milano = service.searchLocations("Milano");

        assertFalse(roma.isEmpty());
        assertFalse(milano.isEmpty());
    }
}