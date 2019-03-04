package com.challenge.myweatherapp.view.weather_screen;

import android.util.Log;

import com.challenge.myweatherapp.R;
import com.challenge.myweatherapp.base.BaseViewModel;
import com.challenge.myweatherapp.common.SchedulerProvider;
import com.challenge.myweatherapp.model.WeatherResponse;
import com.challenge.myweatherapp.model.WeatherResult;
import com.challenge.myweatherapp.service.DataSource;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WeatherViewModel extends BaseViewModel {

    public static final String TAG = "FORECASTS_TAG";

    private final DataSource dataSource;
    private double latitude;
    private double longitude;

    private MutableLiveData<WeatherResponse> weatherForecastsMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<WeatherResult> currentWeatherMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Integer> showSnackBarMessage = new MutableLiveData<>();

    @Inject
    public WeatherViewModel(DataSource dataSource, SchedulerProvider schedulerProvider) {
        super(schedulerProvider);
        this.dataSource = dataSource;
    }

    public void getWeather() {
        getWeatherForCurrentLocation();
        getForecastsForLocation();

    }

    public void getForecastsForLocation() {
        compositeDisposable.add(dataSource.getForecastsForLocation(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> showSnackBarMessage())
                .subscribe(this::setForecastResonse, throwable -> Log.d(TAG, throwable.getMessage())));

    }

    public void getWeatherForCurrentLocation() {
        compositeDisposable.add(dataSource.getCurrentWeatherForLocation(latitude, longitude)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnError(throwable -> showSnackBarMessage())
                .subscribe(this::setCurrentWeatherMutableLiveData, throwable -> Log.d(TAG, throwable.getMessage())));
    }

    private void showSnackBarMessage() {
        this.showSnackBarMessage.setValue(R.string.error_message);
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

    public MutableLiveData<Integer> getShowSnackBarMessage() {
        return showSnackBarMessage;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
