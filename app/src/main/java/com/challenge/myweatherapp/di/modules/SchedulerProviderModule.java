package com.challenge.myweatherapp.di.modules;

import com.challenge.myweatherapp.base.BaseSchedulerProvider;
import com.challenge.myweatherapp.common.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SchedulerProviderModule {

    @Singleton
    @Provides
    BaseSchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }
}
