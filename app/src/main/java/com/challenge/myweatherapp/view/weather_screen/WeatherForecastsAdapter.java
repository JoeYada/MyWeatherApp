package com.challenge.myweatherapp.view.weather_screen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.challenge.myweatherapp.R;
import com.challenge.myweatherapp.model.WeatherResult;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherForecastsAdapter extends RecyclerView.Adapter<WeatherAdapterViewHolder> {

    private List<WeatherResult> weatherResultList = new ArrayList<>();


    @NonNull
    @Override
    public WeatherAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new WeatherAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapterViewHolder holder, int position) {
        WeatherResult weatherResult = weatherResultList.get(position);
        holder.bind(weatherResult);

    }

    @Override
    public int getItemCount() {
        return weatherResultList != null ? weatherResultList.size() : 0;
    }

    public void setWeatherResultList(List<WeatherResult> newItems) {
        if (weatherResultList.size() > 0) {
            weatherResultList.clear();
        }
        weatherResultList.addAll(newItems);
        notifyDataSetChanged();
    }
}
