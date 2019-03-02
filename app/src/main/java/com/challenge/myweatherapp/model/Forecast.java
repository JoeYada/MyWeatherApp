package com.challenge.myweatherapp.model;

public class Forecast {

    private final String condition;
    private final String currentTemperature;
    private final String sunrise;
    private final String sunset;
    private final String iconUrl;
    private final String timeZone;


    public Forecast(String condition, String currentTemperature, String sunrise,
                    String sunset, String iconUrl, String timeZone) {
        this.condition = condition;
        this.currentTemperature = currentTemperature;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.iconUrl = iconUrl;
        this.timeZone = timeZone;
    }

    public String getCondition() {
        return condition;
    }

    public String getCurrentTemperature() {
        return currentTemperature;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getTimeZone() {
        return timeZone;
    }
}
