package com.challenge.myweatherapp.di.modules;

import com.challenge.myweatherapp.service.DataSource;
import com.challenge.myweatherapp.service.RemoteDataSource;
import com.challenge.myweatherapp.service.WeatherService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = NetworkModule.class)
public class DataSourceModule {

    @Singleton
    @Provides
    DataSource provideRemoteDataSource(WeatherService weatherService) {
        return new RemoteDataSource(weatherService);
    }
}
