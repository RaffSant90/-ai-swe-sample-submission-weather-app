package com.raffaele.weatherapp.service;

import com.raffaele.weatherapp.client.OpenMeteoClient;
import com.raffaele.weatherapp.model.Location;
import com.raffaele.weatherapp.model.WeatherData;

import java.util.List;

public class WeatherService {

    private final OpenMeteoClient client = new OpenMeteoClient();

    public WeatherData getWeather(String city) {
        try {
            double[] coords = client.getCoordinates(city);
            if (coords == null) {
                throw new RuntimeException("Coordinate non trovate");
            }

            double temperature = client.getTemperature(coords[0], coords[1]);
            return new WeatherData(temperature);

        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero meteo per: " + city, e);
        }
    }

    public WeatherData getWeatherFromLocation(Location location) {
        try {
            double temperature = client.getTemperature(location.getLatitude(), location.getLongitude());
            return new WeatherData(temperature);
        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero meteo per la location", e);
        }
    }

    public List<Location> searchLocations(String city) {
        try {
            return client.searchLocations(city);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}