package com.weatherapi.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    private final String APIkey = "e16ad3d9e8e1653fed66b55c972e7fbb";
    private final String BaseURL = "https://api.openweathermap.org/data/2.5/weather";

    @GetMapping
    public ResponseEntity<?> getWeather(@RequestParam String city) {
        RestTemplate restTemplate = new RestTemplate();

        String geoUrl = "http://api.openweathermap.org/geo/1.0/direct?q=" + city + "&limit=1&appid=" + APIkey;
        List<Map<String, Object>> geoResponse = restTemplate.getForObject(geoUrl, List.class);

        if (geoResponse == null || geoResponse.isEmpty()) {
            return ResponseEntity.badRequest().body("City not found");
        }

        Map<String, Object> location = geoResponse.get(0);

        double lat = (double) location.get("lat");
        double lon = (double) location.get("lon");

        String weatherUrl = BaseURL + "?lat=" + lat + "&lon=" + lon + "&appid=" + APIkey + "&units=metric";
        Map<String, Object> weatherResponse = restTemplate.getForObject(weatherUrl, Map.class);

        return ResponseEntity.ok(weatherResponse);
    }

    }
