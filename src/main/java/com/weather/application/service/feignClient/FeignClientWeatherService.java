package com.weather.application.service.feignClient;


import com.weather.application.model.Exclude;
import com.weather.application.model.location.LocationWeatherResponse;
import com.weather.application.model.weather.WeatherResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "WEATHER-SERVICE", url = "${weather.openWeather.baseurl}")
@Component
public interface FeignClientWeatherService {
    @RequestMapping(method = RequestMethod.GET, value = "/data/3.0/onecall")
    @Headers("Content-Type: application/json")
    Optional<WeatherResponse> getWeatherDetails(@RequestParam(value = "lat") String lat,
                                                @RequestParam(value = "lon") String lon,
                                                @RequestParam(value = "exclude") Exclude exclude,
                                                @RequestParam(value = "appid") String appid,
                                                @RequestParam(value = "units") String units,
                                                @RequestParam(value = "lang") String lang);

    @RequestMapping(method = RequestMethod.GET, value = "/data/2.5/weather")
    @Headers("Content-Type: application/json")
    Optional<LocationWeatherResponse> getWeatherDetailsByQuery(@RequestParam(value = "q") String query,
                                                               @RequestParam(value = "appid") String appid);
}
