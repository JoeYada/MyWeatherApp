
package com.challenge.myweatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind {

    @SerializedName("speed")
    @Expose
    private double speed;
    @SerializedName("deg")
    @Expose
    private float deg;

    public double getSpeed() {
        return speed;
    }

    public float getDeg() {
        return deg;
    }
}
