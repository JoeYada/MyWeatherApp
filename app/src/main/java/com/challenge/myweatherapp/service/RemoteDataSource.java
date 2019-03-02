package com.challenge.myweatherapp.service;

import com.challenge.myweatherapp.model.WeatherResponse;

import javax.inject.Inject;

import io.reactivex.Single;

public class RemoteDataSource implements DataSource {

    private final WeatherService weatherService;

    @Inject
    public RemoteDataSource(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public Single<WeatherResponse> getWeatherForLocation(double latitude, double longitude) {
        return weatherService.getWeatherForLocation(latitude, longitude);
    }
}
