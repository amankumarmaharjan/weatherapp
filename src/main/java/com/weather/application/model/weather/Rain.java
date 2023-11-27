package com.weather.application.model.weather;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Rain {
    @JsonProperty("1h")
    private double _1h;
}