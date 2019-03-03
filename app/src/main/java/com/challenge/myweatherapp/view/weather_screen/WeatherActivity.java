package com.challenge.myweatherapp.view.weather_screen;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.challenge.myweatherapp.R;
import com.challenge.myweatherapp.base.BaseActivity;
import com.challenge.myweatherapp.common.ViewModelFactory;
import com.challenge.myweatherapp.model.WeatherResult;
import com.challenge.myweatherapp.util.WeatherAppUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class WeatherActivity extends BaseActivity {

    public static final int LOCATION_PERMISSON = 1000;

    @Inject
    ViewModelFactory viewModelFactory;
    @BindView(R.id.weather_info_text)
    TextView weatherInfoTextView;
    @BindView(R.id.location_text_view)
    TextView locationTextView;
    @BindView(R.id.temperature_textview)
    TextView temperatureTextView;
    @BindView(R.id.current_date_textview)
    TextView currentDateTextView;
    @BindView(R.id.temperature_imageView)
    ImageView temperatureImageView;
    @BindView(R.id.forcasts_recyclerView)
    RecyclerView forecastsRecyclerView;

    private WeatherViewModel viewModel;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private WeatherForecastsAdapter forecastsAdapter;

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel.class);

        initViews();

        getWeatherForLocation();

        viewModel.getWeatherForecastsMutableLiveData().observe(this, weatherResponse -> {
            locationTextView.setText(weatherResponse.getCity().getName());
            forecastsAdapter.setWeatherResultList(weatherResponse.getList());

        });

        viewModel.getCurrentWeatherMutableLiveData().observe(this, this::setCurrentWeather);

    }

    private void initViews() {
        forecastsAdapter = new WeatherForecastsAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        forecastsRecyclerView.setLayoutManager(layoutManager);
        forecastsRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
        forecastsRecyclerView.setAdapter(forecastsAdapter);
    }

    private void getWeatherForLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSON);

        } else {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            viewModel.setLatitude(location.getLatitude());
                            viewModel.setLongitude(location.getLongitude());

                            viewModel.getWeather();

                        }
                    });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSON:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getWeatherForLocation();
                }
                break;
        }
    }

    private void setCurrentWeather(WeatherResult weatherResult) {
        String temperature = String.valueOf((int) weatherResult.getMain().getTemp());
        String maxTemperature = String.valueOf((int) weatherResult.getMain().getTempMax());
        String minTemperature = String.valueOf((int) weatherResult.getMain().getTempMin());
        String weatherStatus = weatherResult.getWeather().get(0).getMain();
        temperatureTextView.setText(getResources().getString(R.string.temperature_string, temperature));
        currentDateTextView.setText(WeatherAppUtils.getCurrentDateTimeString());
        weatherInfoTextView.setText(getResources().getString(R.string.weather_message, weatherStatus, maxTemperature, minTemperature));
        WeatherAppUtils.loadImage(this, WeatherAppUtils.getWeatherImageUrl(weatherResult), temperatureImageView);
    }
}
