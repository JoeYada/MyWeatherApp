package com.challenge.myweatherapp.view.weather_screen;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.challenge.myweatherapp.R;
import com.challenge.myweatherapp.model.WeatherResult;
import com.challenge.myweatherapp.util.WeatherAppUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

class WeatherAdapterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_date_text_view)
    TextView dateTimeTextView;
    @BindView(R.id.item_weather_icon)
    ImageView weatherIconImagevIew;
    @BindView(R.id.item_weather_status_text_view)
    TextView weatherStatusTextView;
    @BindView(R.id.item_min_max_temp_text_view)
    TextView minMaxTempTextView;

    public WeatherAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(WeatherResult weatherResult) {
        String temperature = String.valueOf((int) weatherResult.getMain().getTemp());
        String minTemperature = String.valueOf((int) weatherResult.getMain().getTempMin());
        String maxTemperature = String.valueOf((int) weatherResult.getMain().getTempMax());

        dateTimeTextView.setText(WeatherAppUtils.getFormattedDate(weatherResult.getDt()));
        WeatherAppUtils.loadImage(itemView.getContext(), WeatherAppUtils.getWeatherImageUrl(weatherResult), weatherIconImagevIew);
        weatherStatusTextView.setText(itemView.getContext().getResources().getString(R.string.recyclerview_item_weather_string, temperature, weatherResult.getWeather().get(0).getMain()));
        minMaxTempTextView.setText(itemView.getContext().getResources().getString(R.string.recyclerview_min_max_string, minTemperature,  maxTemperature));
    }
}
