package com.challenge.myweatherapp.base;

import com.challenge.myweatherapp.di.components.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class BaseApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().create(this);
    }
}
