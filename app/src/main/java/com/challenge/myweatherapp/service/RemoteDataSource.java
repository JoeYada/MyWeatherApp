package com.challenge.myweatherapp.service;

import com.challenge.myweatherapp.model.WeatherResponse;
import com.challenge.myweatherapp.model.WeatherResult;

import javax.inject.Inject;

import io.reactivex.Single;

public class RemoteDataSource implements DataSource {

    private final WeatherService weatherService;

    @Inject
    public RemoteDataSource(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public Single<WeatherResult> getCurrentWeatherForLocation(double latitude, double longitude) {
        return weatherService.getCurrentWeatherForLocation(latitude, longitude);
    }

    @Override
    public Single<WeatherResponse> getForecastsForLocation(double latitude, double longitude) {
        return weatherService.getForecastsForLocation(latitude, longitude);
    }
}
