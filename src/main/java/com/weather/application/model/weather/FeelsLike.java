package com.weather.application.model.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeelsLike {
    private double day;
    private double night;
    private double eve;
    private double morn;
}
