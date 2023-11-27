package com.weather.application.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.weather.application.model.Exclude;
import com.weather.application.model.location.LocationWeatherResponse;
import com.weather.application.model.weather.WeatherResponse;
import com.weather.application.service.feignClient.FeignClientWeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Optional;

import static com.weather.application.wiremock.WeatherMocks.setupMockLocationResponse;
import static com.weather.application.wiremock.WeatherMocks.setupMockWeatherResponse;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@EnableFeignClients
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WireMockConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class WeatherServiceClientIntegrationTest {

    @Autowired
    private WireMockServer mockWeatherService;

    @Autowired
    FeignClientWeatherService weatherClient;

    @BeforeEach
    void setUp() throws IOException {
        setupMockWeatherResponse(mockWeatherService);
        setupMockLocationResponse(mockWeatherService);
    }

    @Test
    public void whenGetWeatherDetails_thenWeatherShouldBeReturned() {
        Optional<WeatherResponse> weatherResponseOptional = this.weatherClient.getWeatherDetails("1", "2", Exclude.DAILY, "6", null, null);
        WeatherResponse weatherResponse = weatherResponseOptional.get();
        assertNotNull(weatherResponse);
    }

    @Test
    public void whenGetWeatherDetailsByQuery_thenWeatherShouldBeReturned() {
        Optional<LocationWeatherResponse> locationWeatherResponseOptional = this.weatherClient.getWeatherDetailsByQuery("Sydney", "23");
        LocationWeatherResponse locationWeatherResponse = locationWeatherResponseOptional.get();
        assertNotNull(locationWeatherResponse);
    }

}