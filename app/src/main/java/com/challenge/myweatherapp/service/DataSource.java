package com.challenge.myweatherapp.service;

import com.challenge.myweatherapp.model.WeatherResponse;
import com.challenge.myweatherapp.model.WeatherResult;

import io.reactivex.Single;

public interface DataSource {

    Single<WeatherResult> getCurrentWeatherForLocation(double latitude, double longitude);


    Single<WeatherResponse> getForecastsForLocation(double latitude, double longitude);
}
