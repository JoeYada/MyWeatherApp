package com.challenge.myweatherapp.di.modules;


import com.challenge.myweatherapp.view.weather_screen.WeatherActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract WeatherActivity bindAlbumListActivity();
}
