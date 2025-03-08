package com.weather.application.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/healthCheck")
    public String healthCheck(@RequestParam(value = "name", defaultValue = "Weather App") String name) {
        return String.format("Welcome to %s!", name);
    }
}
