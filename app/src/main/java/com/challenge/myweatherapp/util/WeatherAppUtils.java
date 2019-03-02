package com.challenge.myweatherapp.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WeatherAppUtils {

    public static final String DATE_PATTERN = "EE dd MMM yyyy, HH:mm";


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
}
