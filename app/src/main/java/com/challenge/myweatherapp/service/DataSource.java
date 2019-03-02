package com.challenge.myweatherapp.service;

import com.challenge.myweatherapp.model.WeatherResponse;

import io.reactivex.Single;

public interface DataSource {

    Single<WeatherResponse> getWeatherForLocation(double latitude, double longitude);
}
