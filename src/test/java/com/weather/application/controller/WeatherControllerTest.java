package com.weather.application.controller;

import com.weather.application.service.WeatherService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherControllerImpl.class)
@ActiveProfiles("test")
class WeatherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Value("${weather.user.name}")
    private String username;

    @Value("${weather.user.password}")
    private String password;

    @MockBean
    private WeatherService weatherService;

    @Test
    void getWeatherDetailsTest200() throws Exception {
        this.mockMvc.perform(get("/weather/details").with(user(username).password(password))
                .param("latitude", "33.44").param("longitude", "151.20")).andDo(print())
            .andExpect(status().isOk()).andExpect(jsonPath("$").exists());
    }

    @Test
    void getWeatherDetailsTest400Error() throws Exception {
        this.mockMvc.perform(get("/weather/details").with(user(username).password(password))
                .param("latitude", "33.44").param("longitude", "151.20").param("exclude", "df")).andDo(print())
            .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Invalid Query Parameters")));
    }

    @Test
    @Disabled
    void getWeatherDetailsTest401Error() throws Exception {
        this.mockMvc.perform(get("/weather/details")
                .param("latitude", "33.44").param("longitude", "151.20").with(csrf())).andDo(print())
            .andExpect(status().isUnauthorized()).andExpect(content().string(containsString("User Name or Password incorrect")));
    }

    @Test
    @Disabled
    void getWeatherDetailsTestDownStreamError() throws Exception {
        this.mockMvc.perform(get("/weather/details").with(user(username).password(password))
                .param("latitude", "-133.44").param("longitude", "151.20")).andDo(print())
            .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Downstream API Error")));
    }

    @Test
    void getWeatherDetailsByCityName200() throws Exception {
        this.mockMvc.perform(get("/weather/details/location").with(user(username).password(password))
                .param("location", "sydney")).andDo(print())
            .andExpect(status().isOk()).andExpect(jsonPath("$").exists());
    }

    @Test
    @Disabled
    void getWeatherDetailsByCityName400Error() throws Exception {
        this.mockMvc.perform(get("/weather/details/location").with(user(username).password(password))
                .param("location", " ")).andDo(print())
            .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Invalid Query Parameters")));
    }

    @Test
    @Disabled
    void getWeatherDetailsByCityNameDownStreamError() throws Exception {
        this.mockMvc.perform(get("/weather/details/location").with(user(username).password(password))
                .param("location", "ABC")).andDo(print())
            .andExpect(status().isNotFound()).andExpect(content().string(containsString("Downstream API Error")));
    }

    @Test
    void getResourceNotFoundError() throws Exception {
        this.mockMvc.perform(get("/invalidResource").with(user(username).password(password))
                .param("location", "ABC")).andDo(print())
            .andExpect(status().isNotFound()).andExpect(content().string(containsString("No Page Found")));
    }

}
