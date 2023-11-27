package com.weather.application.model;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class WeatherRequestDTO {
    @NotBlank
    @Parameter(description = "Longitude of Location", required = true)
    private String longitude;
    @NotBlank
    @Parameter(description = "Latitude of Location", required = true)
    private String latitude;
    @Parameter(description = "By using this parameter you can exclude some parts of the weather data from the API response.", required = false)
    private Exclude exclude;
}
