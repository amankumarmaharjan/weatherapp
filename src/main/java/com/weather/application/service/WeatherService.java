package com.weather.application.service;

import com.weather.application.model.WeatherRequestDTO;
import com.weather.application.model.location.LocationWeatherResponse;
import com.weather.application.model.weather.WeatherResponse;

public interface WeatherService {
    /**
     * This method will get the details of weather using parameter latitude and longitude
     *
     * @param weatherRequestDTO
     * @return weatherResponse
     */
    WeatherResponse getWeatherDetails(WeatherRequestDTO weatherRequestDTO);

    /**
     * This method will get the details of weather using location
     *
     * @param location
     * @return locationWeatherResponse
     */
    LocationWeatherResponse getWeatherDetailsByLocation(String location);
}
