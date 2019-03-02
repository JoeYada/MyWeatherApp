package com.challenge.myweatherapp.di.components;

import com.challenge.myweatherapp.base.BaseApplication;
import com.challenge.myweatherapp.di.modules.ActivityBuilderModule;
import com.challenge.myweatherapp.di.modules.ApplicationModule;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {AndroidSupportInjectionModule.class, ApplicationModule.class, ActivityBuilderModule.class})
public interface ApplicationComponent extends AndroidInjector<BaseApplication> {
    @Component.Builder
    abstract  class Builder extends AndroidInjector.Builder<BaseApplication>{}
}
