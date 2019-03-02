package com.challenge.myweatherapp.service;

import com.challenge.myweatherapp.model.WeatherResponse;
import com.challenge.myweatherapp.model.WeatherResult;
import com.challenge.myweatherapp.util.Constants;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET(Constants.CURRENT_WEATHER_RELATIVE_URL)
    Single<WeatherResult> getCurrentWeatherForLocation(@Query("lat") double latitude,
                                                  @Query("lon") double longitude);

    @GET(Constants.FORECASTS_RELATIVE_URL)
    Single<WeatherResponse> getForecastsForLocation(@Query("lat") double latitude,
                                                    @Query("lon") double longitude);
}
