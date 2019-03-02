package com.challenge.myweatherapp.di.viewmodel;

import com.challenge.myweatherapp.common.ViewModelFactory;
import com.challenge.myweatherapp.view.weather_screen.WeatherViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel.class)
    abstract ViewModel bindWeatherViewModel(WeatherViewModel listViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
