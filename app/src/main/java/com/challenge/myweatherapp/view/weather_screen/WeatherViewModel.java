package com.challenge.myweatherapp.view.weather_screen;

import com.challenge.myweatherapp.base.BaseViewModel;
import com.challenge.myweatherapp.common.SchedulerProvider;
import com.challenge.myweatherapp.model.WeatherResponse;
import com.challenge.myweatherapp.model.WeatherResult;
import com.challenge.myweatherapp.service.DataSource;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Completable;

public class WeatherViewModel extends BaseViewModel {

    private final DataSource dataSource;
    private double latitude;
    private double longitude;

    private MutableLiveData<WeatherResponse> weatherForecastsMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<WeatherResult> currentWeatherMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Boolean> showErrorToast = new MutableLiveData<>();

    @Inject
    public WeatherViewModel(DataSource dataSource, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.dataSource = dataSource;
    }

    public void getWeather() {
        getWeatherForCurrentLocation()
                .andThen(getForecastsForLocation())
                .subscribe();
    }

    public Completable getForecastsForLocation() {
        return Completable.create(emitter -> {
            compositeDisposable.add(dataSource.getForecastsForLocation(latitude, longitude)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnError(data -> {
                        showErrorToast();
                        emitter.onError(new Throwable());
                    })
                    .subscribe(weatherResponse -> {
                        setForecastResonse(weatherResponse);
                        emitter.onComplete();
                    }, emitter::onError));
        });

    }

    public Completable getWeatherForCurrentLocation() {
        return Completable.create(emitter -> {
            compositeDisposable.add(dataSource.getCurrentWeatherForLocation(latitude, longitude)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .doOnError(data -> {
                        showErrorToast();
                        emitter.onError(new Throwable());
                    })
                    .subscribe(weatherResult -> {
                        setCurrentWeatherMutableLiveData(weatherResult);
                        emitter.onComplete();

                    }, emitter::onError));
        });
    }

    private void showErrorToast() {
        this.showErrorToast.setValue(true);
    }


    public void setForecastResonse(WeatherResponse weatherResults) {
        weatherForecastsMutableLiveData.setValue(weatherResults);
    }

    public void setCurrentWeatherMutableLiveData(WeatherResult weatherResult) {
        currentWeatherMutableLiveData.setValue(weatherResult);
    }

    public MutableLiveData<WeatherResponse> getWeatherForecastsMutableLiveData() {
        return weatherForecastsMutableLiveData;
    }

    public MutableLiveData<WeatherResult> getCurrentWeatherMutableLiveData() {
        return currentWeatherMutableLiveData;
    }

    public MutableLiveData<Boolean> getShowErrorToast() {
        return showErrorToast;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
