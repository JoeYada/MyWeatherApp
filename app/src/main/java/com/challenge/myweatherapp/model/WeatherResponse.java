
package com.challenge.myweatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {

    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("message")
    @Expose
    private double message;
    @SerializedName("cnt")
    @Expose
    private int cnt;
    @SerializedName("list")
    @Expose
    private List<WeatherResult> list = new ArrayList<>();
    @SerializedName("city")
    @Expose
    private City city;

    /**
     * No args constructor for use in serialization
     * 
     */
    public WeatherResponse() {
    }

    /**
     * 
     * @param message
     * @param cnt
     * @param cod
     * @param list
     * @param city
     */
    public WeatherResponse(String cod, double message, int cnt, List<WeatherResult> list, City city) {
        super();
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public WeatherResponse withCod(String cod) {
        this.cod = cod;
        return this;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public WeatherResponse withMessage(double message) {
        this.message = message;
        return this;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public WeatherResponse withCnt(int cnt) {
        this.cnt = cnt;
        return this;
    }

    public List<WeatherResult> getList() {
        return list;
    }

    public void setList(List<WeatherResult> list) {
        this.list = list;
    }

    public WeatherResponse withList(List<WeatherResult> list) {
        this.list = list;
        return this;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public WeatherResponse withCity(City city) {
        this.city = city;
        return this;
    }

}
