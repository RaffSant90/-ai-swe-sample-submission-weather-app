package com.raffaele.weatherapp.model;

public class WeatherData {

    private final double temperature;

    public WeatherData(double temperature) {
        this.temperature = temperature;
    }

    public double getTemperature() {
        return temperature;
    }
}