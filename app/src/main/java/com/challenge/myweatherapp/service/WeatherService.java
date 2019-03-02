package com.challenge.myweatherapp.service;

import com.challenge.myweatherapp.model.WeatherResults;
import com.challenge.myweatherapp.util.Constants;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET(Constants.RELATIVE_URL)
    Single<WeatherResults> getWeatherForLocation(@Query("lat") double latitude,
                                                 @Query("lon") double longitude);
}
