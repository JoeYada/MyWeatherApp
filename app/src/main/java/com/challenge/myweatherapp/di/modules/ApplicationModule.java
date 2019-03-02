package com.challenge.myweatherapp.di.modules;

import android.app.Application;
import android.content.Context;

import com.challenge.myweatherapp.di.viewmodel.ViewModelModule;

import dagger.Binds;
import dagger.Module;

@Module(includes = {ViewModelModule.class,
        NetworkModule.class,
        SchedulerProviderModule.class})
public abstract class ApplicationModule {

    @Binds
    abstract Context provideContext(Application application);

}
