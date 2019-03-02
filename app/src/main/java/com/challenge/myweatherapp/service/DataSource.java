package com.challenge.myweatherapp.service;

import com.challenge.myweatherapp.model.WeatherResults;

import io.reactivex.Single;

public interface DataSource {

    Single<WeatherResults> getWeatherForLocation(double latitude, double longitude);
}
