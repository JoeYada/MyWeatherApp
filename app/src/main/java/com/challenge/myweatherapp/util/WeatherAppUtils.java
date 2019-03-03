package com.challenge.myweatherapp.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.challenge.myweatherapp.model.WeatherResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherAppUtils {

    public static final String DATE_PATTERN = "EE dd MMM yyyy, HH:mm";
    public static final String DATE_PATTERN_NO_YEAR = "EE dd MMM, HH:mm";



    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .into(imageView);

    }

    public static String getCurrentDateTimeString() {
        Date currentDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        return simpleDateFormat.format(currentDate);
    }

    public static String getFormattedDate(int dateTime) {
        long timeStampMillis = dateTime * 1000L;
        Date date = new Date(timeStampMillis);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN_NO_YEAR, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public static String getWeatherImageUrl(WeatherResult weatherResult) {
        return Constants.WEATHER_IMAGE_URL + weatherResult.getWeather().get(0).getIcon() + ".png";
    }
}
