package com.weather.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @Operation(summary = "Health check api for weather application")
    @ApiResponse(responseCode = "200", description = "OK",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))})
    @GetMapping("/healthCheck")
    public String healthCheck(@RequestParam(value = "name", defaultValue = "Weather App") String name) {
        return String.format("Welcome to %s!", name);
    }
}
