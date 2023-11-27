package com.weather.application.model.weather;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Temp {
    private double day;
    private double min;
    private double max;
    private double night;
    private double eve;
    private double morn;
}