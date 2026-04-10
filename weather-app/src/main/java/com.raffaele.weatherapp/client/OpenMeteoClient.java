package com.raffaele.weatherapp.client;

import com.raffaele.weatherapp.model.Location;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class OpenMeteoClient {

    private final HttpClient client = HttpClient.newHttpClient();

    public double[] getCoordinates(String city) throws Exception {
        String url = "https://geocoding-api.open-meteo.com/v1/search?name=" + city;

        HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        if (!body.contains("latitude")) {
            throw new RuntimeException("Nessuna coordinata trovata per la città: " + city);
        }

        double lat = Double.parseDouble(body.split("\"latitude\":")[1].split(",")[0]);
        double lon = Double.parseDouble(body.split("\"longitude\":")[1].split(",")[0]);

        return new double[]{lat, lon};
    }

    public double getTemperature(double lat, double lon) throws Exception {
        String url = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&current_weather=true";

        HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // ✅ controllo HTTP
        if (response.statusCode() != 200) {
            throw new RuntimeException("Errore API: " + response.statusCode());
        }

        String body = response.body();

        // ✅ controllo contenuto
        if (!body.contains("current_weather")) {
            throw new RuntimeException("Risposta API non valida");
        }

        String currentWeather = body.split("\"current_weather\":")[1];
        String tempPart = currentWeather.split("\"temperature\":")[1];
        String tempValue = tempPart.split(",")[0];

        return Double.parseDouble(tempValue);
    }

    public List<Location> searchLocations(String city) throws Exception {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);

        String url = "https://geocoding-api.open-meteo.com/v1/search?name="
                + encodedCity + "&count=5&language=it";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        List<Location> results = new ArrayList<>();

        if (!body.contains("results")) return results;

        String[] entries = body.split("\\{");

        for (String entry : entries) {
            if (entry.contains("latitude") && entry.contains("longitude")) {
                try {
                    String name = entry.split("\"name\":\"")[1].split("\"")[0];
                    String country = entry.split("\"country\":\"")[1].split("\"")[0];
                    double lat = Double.parseDouble(entry.split("\"latitude\":")[1].split(",")[0]);
                    double lon = Double.parseDouble(entry.split("\"longitude\":")[1].split(",")[0]);

                    results.add(new Location(name, country, lat, lon));
                } catch (Exception ignored) {}
            }
        }

        return results;
    }
}