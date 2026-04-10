package com.raffaele.weatherapp.ui;

import com.raffaele.weatherapp.model.Location;
import com.raffaele.weatherapp.model.WeatherData;
import com.raffaele.weatherapp.service.WeatherService;

import java.util.List;
import java.util.Scanner;

public class ConsoleUi {

    private final WeatherService weatherService = new WeatherService();

    public void start() {
        Scanner scanner = new Scanner(System.in);

        boolean continua = true;

        while (continua) {
            System.out.print("\nInserisci una o più città (separate da -): ");
            String input = scanner.nextLine();

            String[] cities;

            // ✅ MULTI-CITTÀ con "-"
            if (input.contains("-")) {
                cities = input.split("-");
            } else {
                // ✅ singola città (anche con paese)
                cities = new String[]{input};
            }

            // ✅ MODALITÀ MULTI-CITTÀ
            if (cities.length > 1) {
                for (String city : cities) {
                    city = city.trim();

                    try {
                        List<Location> locations = weatherService.searchLocations(city);

                        if (locations.isEmpty()) {
                            System.out.println("❌ " + city + ": non trovata");
                            continue;
                        }

                        Location loc;

                        if (locations.size() > 1) {
                            System.out.println("\n" + city + " - più risultati trovati:");

                            for (int i = 0; i < locations.size(); i++) {
                                Location l = locations.get(i);
                                System.out.println((i + 1) + ". " + l.getName() + ", " + l.getCountry());
                            }

                            System.out.print("Scegli: ");
                            int scelta = Integer.parseInt(scanner.nextLine());

                            loc = locations.get(scelta - 1);
                        } else {
                            loc = locations.get(0);
                        }

                        WeatherData data = weatherService.getWeatherFromLocation(loc);

                        System.out.println("🌤 " + loc.getName() + ", "
                                + loc.getCountry() + ": "
                                + data.getTemperature() + "°C");

                    } catch (Exception e) {
                        System.out.println("❌ Errore per " + city + ": " + e.getMessage());
                    }
                }

                // 👇 domanda per continuare
                System.out.print("\nVuoi cercare un'altra città? (s/n): ");
                String risposta = scanner.nextLine();

                if (!risposta.equalsIgnoreCase("s")) {
                    continua = false;
                }

                continue; // 🔥 torna all'inizio del ciclo
            }

            // ✅ MODALITÀ SINGOLA (come prima)
            String city = cities[0].trim();

            List<Location> locations;
            try {
                locations = weatherService.searchLocations(city);
            } catch (Exception e) {
                System.out.println("Errore nella ricerca.");
                return;
            }

            if (locations.isEmpty()) {
                System.out.println("❌ Nessuna città trovata.");
                return;
            }

            // ✅ Se c'è un solo risultato
            if (locations.size() == 1) {
                Location loc = locations.get(0);

                System.out.println("\n📍 Trovata una sola città: "
                        + loc.getName() + ", " + loc.getCountry());

                WeatherData data = weatherService.getWeatherFromLocation(loc);

                if (data != null) {
                    System.out.println("🌤 Meteo:");
                    System.out.println("🌡 Temperatura: " + data.getTemperature() + "°C");
                } else {
                    System.out.println("Errore nel recupero meteo.");
                }

                continue;
            }

            // 👇 MOSTRA SCELTA
            System.out.println("\nHo trovato questi risultati:");
            for (int i = 0; i < locations.size(); i++) {
                Location loc = locations.get(i);
                System.out.println((i + 1) + ". " + loc.getName() + ", " + loc.getCountry());
            }

            // 👇 INPUT SCELTA
            System.out.print("Scegli (1-" + locations.size() + "): ");
            int scelta = Integer.parseInt(scanner.nextLine());

            Location selected = locations.get(scelta - 1);

            WeatherData data = weatherService.getWeatherFromLocation(selected);

            System.out.println("\n🌤 Meteo a " + selected.getName() + ", " + selected.getCountry() + ":");
            System.out.println("🌡 Temperatura: " + data.getTemperature() + "°C");

            // 👇 domanda per continuare
            System.out.print("\nVuoi cercare un'altra città? (s/n): ");
            String risposta = scanner.nextLine();

            if (!risposta.equalsIgnoreCase("s")) {
                continua = false;
            }
        }

        System.out.println("\n👋 Uscita dal programma. A presto!");
    }
}