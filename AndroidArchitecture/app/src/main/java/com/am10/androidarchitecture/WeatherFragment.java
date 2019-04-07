package com.am10.androidarchitecture;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.am10.androidarchitecture.Response.Forecast;

public class WeatherFragment  extends Fragment {

    public enum Date {
        Today,
        Tomorrow,
        DayAfterTomorrow;
    }
    private WeatherCustomView mTodayView;
    private WeatherCustomView mTomorrowView;
    private WeatherCustomView mDayAfterTomorrowView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_weather, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTodayView = view.findViewById(R.id.view_today);
        mTomorrowView = view.findViewById(R.id.view_tomorrow);
        mDayAfterTomorrowView = view.findViewById(R.id.view_day_after_tomorrow);
    }

    public void displayForecast(Date date, Forecast forecast, String minCelsius, String maxCelsius) {
        switch(date) {
            case Today:
                mTodayView.displayForecast(forecast, minCelsius, maxCelsius);
                break;
            case Tomorrow:
                mTomorrowView.displayForecast(forecast, minCelsius, maxCelsius);
                break;
            case DayAfterTomorrow:
                mDayAfterTomorrowView.displayForecast(forecast, minCelsius, maxCelsius);
                break;
        }
    }

    public ImageView getImageView(Date date) {
        switch(date) {
            case Today:
                return mTodayView.imageView;
            case Tomorrow:
                return mTomorrowView.imageView;
            case DayAfterTomorrow:
                return mDayAfterTomorrowView.imageView;
        }
        return null;
    }

}
