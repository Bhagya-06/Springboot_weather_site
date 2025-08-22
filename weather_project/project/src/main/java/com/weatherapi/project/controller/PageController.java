package com.weatherapi.project.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class PageController {

    @GetMapping("/weather")
    public String weatherForm() {
        return "index";
    }

    @GetMapping("/results")
    public String getWeather(@RequestParam("city") String city, Model model) {
        String apiUrl = "http://localhost:8080/api/weather?city=" + city;

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);

        if (response != null && response.containsKey("main")) {
            // Main weather data
            Map<String, Object> mainData = (Map<String, Object>) response.get("main");
            double temp = (double) mainData.get("temp");
            double feelsLike = (double) mainData.get("feels_like");
            int humidity = (int) mainData.get("humidity");

            // Wind data
            Map<String, Object> windData = (Map<String, Object>) response.get("wind");
            double windSpeed = (double) windData.get("speed");

            // Weather array (first object has description & icon)
            List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
            Map<String, Object> weather = weatherList.get(0);
            String description = (String) weather.get("description");
            String icon = (String) weather.get("icon");

            // Country code
            Map<String, Object> sysData = (Map<String, Object>) response.get("sys");
            String country = (String) sysData.get("country");

            // Add to model
            model.addAttribute("city", city);
            model.addAttribute("country", country);
            model.addAttribute("temp", temp);
            model.addAttribute("feelsLike", feelsLike);
            model.addAttribute("humidity", humidity);
            model.addAttribute("windSpeed", windSpeed);
            model.addAttribute("description", description);
            model.addAttribute("icon", icon);

        } else {
            model.addAttribute("city", city);
            model.addAttribute("temp", "N/A");
            model.addAttribute("description", "Not available");
        }

        return "index";
    }
}
