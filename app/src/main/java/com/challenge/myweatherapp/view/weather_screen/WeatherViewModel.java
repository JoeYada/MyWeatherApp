package com.challenge.myweatherapp.view.weather_screen;

import com.challenge.myweatherapp.base.BaseViewModel;
import com.challenge.myweatherapp.common.SchedulerProvider;
import com.challenge.myweatherapp.model.WeatherResults;
import com.challenge.myweatherapp.service.DataSource;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class WeatherViewModel extends BaseViewModel {

    private final DataSource dataSource;

    private MutableLiveData<WeatherResults> weatherResultsMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<Boolean> showErrorToast = new MutableLiveData<>();

    @Inject
    public WeatherViewModel(DataSource dataSource, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.dataSource = dataSource;
    }

    public void getWeatherForLocation(double latitude, double longitude) {
        compositeDisposable.add(dataSource.getWeatherForLocation(latitude, longitude)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe(data -> setIsLoading(true))
                .doOnSuccess(data -> setIsLoading(false))
                .doOnError(data -> {
                    setIsLoading(false);
                    setShowErrorToast();
                })
                .subscribe(this::setWeatherResults));

    }

    private void setShowErrorToast() {
        this.showErrorToast.setValue(true);
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public void setWeatherResults(WeatherResults weatherResults) {
        weatherResultsMutableLiveData.setValue(weatherResults);
    }

    public MutableLiveData<WeatherResults> getWeatherResultsMutableLiveData() {
        return weatherResultsMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Boolean> getShowErrorToast() {
        return showErrorToast;
    }
    
}
