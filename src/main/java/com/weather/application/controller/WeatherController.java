package com.weather.application.controller;

import com.weather.application.error.model.APIErrorResponse;
import com.weather.application.model.APIResponse;
import com.weather.application.model.WeatherRequestDTO;
import com.weather.application.model.location.LocationWeatherResponse;
import com.weather.application.model.weather.WeatherResponse;
import com.weather.application.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@Validated
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Operation(summary = "Get weather Details by latitude and longitude")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the weather",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = WeatherResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid latitude and longitude",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "error400",
                                            summary = "BAD request error",
                                            description = "BAD request error",
                                            value = "{\n" +
                                                    "    \"error\": {\n" +
                                                    "        \"status\": \"BAD_REQUEST\",\n" +
                                                    "        \"code\": \"400\",\n" +
                                                    "        \"timestamp\": \"27-11-2023 11:31:20\",\n" +
                                                    "        \"message\": \"Downstream API Error\",\n" +
                                                    "        \"errorDetails\": \"[400 Bad Request] during [GET] to [https://api.openweathermap.org/data/3.0/onecall?lat=-133.8688&lon=151.2&exclude=CURRENT&appid=be3f4cd8c673bf936796b5ad7ad3d1ff] [FeignClientWeatherService#getWeatherDetails(String,String,Exclude,String,String,String)]: [{\\\"cod\\\":\\\"400\\\",\\\"message\\\":\\\"wrong latitude\\\"}]\"\n" +
                                                    "    }\n" +
                                                    "}")})
                    }),
            @ApiResponse(responseCode = "401", description = "Invalid Credential",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "401 error",
                                            summary = "Authentication error",
                                            description = "Authentication error",
                                            value = "{\n" +
                                                    "    \"error\": {\n" +
                                                    "        \"status\": \"UNAUTHORIZED\",\n" +
                                                    "        \"code\": \"401\",\n" +
                                                    "        \"timestamp\": \"27-11-2023 11:31:11\",\n" +
                                                    "        \"message\": \"User Name or Password incorrect\",\n" +
                                                    "        \"errorDetails\": \"Full authentication is required to access this resource\"\n" +
                                                    "    }\n" +
                                                    "}")}
                    )}),
            @ApiResponse(responseCode = "404", description = "Resource not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "404 error",
                                            summary = "Resource not found error",
                                            description = "Resource not found error",
                                            value = "{\n" +
                                                    "    \"error\": {\n" +
                                                    "        \"status\": \"NOT_FOUND\",\n" +
                                                    "        \"code\": \"404\",\n" +
                                                    "        \"timestamp\": \"27-11-2023 11:31:14\",\n" +
                                                    "        \"message\": \"No Page Found\",\n" +
                                                    "        \"errorDetails\": \"No static resource weather/detail.\"\n" +
                                                    "    }\n" +
                                                    "}")}
                    )}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "500 error",
                                            summary = "Internal Server error",
                                            description = "Internal Server error",
                                            value = "{\n" +
                                                    "    \"error\": {\n" +
                                                    "        \"status\": \"I\",\n" +
                                                    "        \"code\": \"500\",\n" +
                                                    "        \"timestamp\": \"27-11-2023 11:31:14\",\n" +
                                                    "        \"message\": \"Internal Server error\",\n" +
                                                    "        \"errorDetails\": \"Internal Server error\"\n" +
                                                    "    }\n" +
                                                    "}")}
                    )})})
    @GetMapping("/details")
    public ResponseEntity<?> getWeatherDetails(@ParameterObject @Valid WeatherRequestDTO weatherRequestDTO) {
        APIResponse apiResponse = APIResponse.builder().data(weatherService.getWeatherDetails(weatherRequestDTO)).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Operation(summary = "Get weather Details by Location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the weather",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = LocationWeatherResponse.class)
                    )}),
            @ApiResponse(responseCode = "400", description = "Invalid location",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "error400",
                                            summary = "BAD request error",
                                            description = "BAD request error",
                                            value = "{\n" +
                                                    "    \"error\": {\n" +
                                                    "        \"status\": \"BAD_REQUEST\",\n" +
                                                    "        \"code\": \"400\",\n" +
                                                    "        \"timestamp\": \"27-11-2023 11:31:20\",\n" +
                                                    "        \"message\": \"Downstream API Error\",\n" +
                                                    "        \"errorDetails\": \"[400 Bad Request] during [GET] to [https://api.openweathermap.org/data/3.0/onecall?lat=-133.8688&lon=151.2&exclude=CURRENT&appid=be3f4cd8c673bf936796b5ad7ad3d1ff] [FeignClientWeatherService#getWeatherDetails(String,String,Exclude,String,String,String)]: [{\\\"cod\\\":\\\"400\\\",\\\"message\\\":\\\"wrong latitude\\\"}]\"\n" +
                                                    "    }\n" +
                                                    "}")}
                    )}),
            @ApiResponse(responseCode = "401", description = "Invalid Credential",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIErrorResponse.class), examples = {
                            @ExampleObject(name = "401 error",
                                    summary = "Authentication error",
                                    description = "Authentication error",
                                    value = "{\n" +
                                            "    \"error\": {\n" +
                                            "        \"status\": \"UNAUTHORIZED\",\n" +
                                            "        \"code\": \"401\",\n" +
                                            "        \"timestamp\": \"27-11-2023 11:31:11\",\n" +
                                            "        \"message\": \"User Name or Password incorrect\",\n" +
                                            "        \"errorDetails\": \"Full authentication is required to access this resource\"\n" +
                                            "    }\n" +
                                            "}")}

                    )}),
            @ApiResponse(responseCode = "404", description = "Weather not found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "404 error",
                                            summary = "Resource not found error",
                                            description = "Resource not found error",
                                            value = "{\n" +
                                                    "    \"error\": {\n" +
                                                    "        \"status\": \"NOT_FOUND\",\n" +
                                                    "        \"code\": \"404\",\n" +
                                                    "        \"timestamp\": \"27-11-2023 11:31:14\",\n" +
                                                    "        \"message\": \"No Page Found\",\n" +
                                                    "        \"errorDetails\": \"No static resource weather/detail.\"\n" +
                                                    "    }\n" +
                                                    "}")}

                    )}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = APIErrorResponse.class),
                            examples = {
                                    @ExampleObject(name = "500 error",
                                            summary = "Internal Server error",
                                            description = "Internal Server error",
                                            value = "{\n" +
                                                    "    \"error\": {\n" +
                                                    "        \"status\": \"I\",\n" +
                                                    "        \"code\": \"500\",\n" +
                                                    "        \"timestamp\": \"27-11-2023 11:31:14\",\n" +
                                                    "        \"message\": \"Internal Server error\",\n" +
                                                    "        \"errorDetails\": \"Internal Server error\"\n" +
                                                    "    }\n" +
                                                    "}")})
                    })})
    @GetMapping("/details/location")
    public ResponseEntity<?> getWeatherDetailsByCityName(@Parameter(description = "Location for e.g Sydney", required = true) @NotBlank @RequestParam(value = "location") String location) {
        APIResponse apiResponse = APIResponse.builder().data(weatherService.getWeatherDetailsByLocation(location)).build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
