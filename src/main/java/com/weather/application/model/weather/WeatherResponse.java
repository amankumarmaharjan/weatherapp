package com.weather.application.model.weather;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class WeatherResponse {
    private double lat;
    private double lon;
    private String timezone;
    private int timezone_offset;
    private Current current;
    private ArrayList<Hourly> hourly;
    private ArrayList<Daily> daily;
}
