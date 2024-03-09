package com.weather.application.model.location;

import lombok.AllArgsConstructor;
import lombok.With;

import java.util.ArrayList;

@With
@AllArgsConstructor
public class LocationWeatherResponse {
    @With
    private Coord coord;
    private ArrayList<Weather> weather;
    private String base;
    private Main main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private int dt;
    private int timezone;
    private Sys sys;
    private int id;
    private String name;
    private int cod;
}
