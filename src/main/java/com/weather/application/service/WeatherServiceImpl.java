package com.weather.application.service;

import com.weather.application.model.WeatherRequestDTO;
import com.weather.application.model.location.LocationWeatherResponse;
import com.weather.application.model.weather.WeatherResponse;
import com.weather.application.service.feignClient.FeignClientWeatherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final FeignClientWeatherService feignClientWeatherService;
    private final String apiKey;

    public WeatherServiceImpl(FeignClientWeatherService feignClientWeatherService, @Value("${weather.openWeather.apiKey}") String apiKey) {
        this.feignClientWeatherService = feignClientWeatherService;
        this.apiKey = apiKey;
    }

    @Override
    public WeatherResponse getWeatherDetails(WeatherRequestDTO weatherRequestDTO) {
        Optional<WeatherResponse> weatherResponseOptional = feignClientWeatherService.getWeatherDetails(weatherRequestDTO.getLatitude(),
                weatherRequestDTO.getLongitude(), weatherRequestDTO.getExclude(), apiKey, null, null);
        return weatherResponseOptional.isPresent() ? weatherResponseOptional.get() : null;
    }

    @Override
    public LocationWeatherResponse getWeatherDetailsByLocation(String query) {
        Optional<LocationWeatherResponse> locationWeatherResponseOptional = feignClientWeatherService.getWeatherDetailsByQuery(query, apiKey);
        return locationWeatherResponseOptional.isPresent() ? locationWeatherResponseOptional.get() : null;
    }
}
