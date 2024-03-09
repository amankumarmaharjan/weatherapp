package com.weather.application.controller;

import com.weather.application.aop.logging.LogExecution;
import com.weather.application.model.APIResponse;
import com.weather.application.model.WeatherRequestDTO;
import com.weather.application.service.WeatherService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class WeatherControllerImpl extends WeatherController {
    private final WeatherService weatherService;

    public WeatherControllerImpl(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    @LogExecution
    public ResponseEntity<APIResponse> getWeatherDetails(@ParameterObject WeatherRequestDTO weatherRequestDTO) {
        APIResponse apiResponse = APIResponse.builder().data(weatherService.getWeatherDetails(weatherRequestDTO)).build();
        return ResponseEntity.ok(apiResponse);
    }

    @Override
    @LogExecution
    public ResponseEntity<APIResponse> getWeatherDetailsByCityName(@Parameter(description = "Location for e.g Sydney", required = true) @NotBlank @RequestParam(value = "location") String location) {
        APIResponse apiResponse = APIResponse.builder().data(weatherService.getWeatherDetailsByLocation(location)).build();
        return ResponseEntity.ok(apiResponse);
    }
}
