package com.am10.androidarchitecture;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.am10.androidarchitecture.Response.Forecast;
import com.am10.androidarchitecture.Response.Max;
import com.am10.androidarchitecture.Response.Weather;

import java.io.Serializable;

public class WeatherModel implements Serializable {
    public Weather weather;
    public CityData cityData;

    public String getMinCelsiusFromForecast(Forecast forecast) {
        if (forecast == null) {
            return "-";
        }
        if (forecast.getTemperature() == null) {
            return "-";
        }
        if (forecast.getTemperature().getMin() == null) {
            return "-";
        }
        Max min = forecast.getTemperature().getMin();
        if (min.getCelsius() == null || min.getCelsius().isEmpty()) {
            return "-";
        }
        return min.getCelsius() + "℃";
    }

    public String getMaxCelsiusFromForecast(Forecast forecast) {
        if (forecast == null) {
            return "-";
        }
        if (forecast.getTemperature() == null) {
            return "-";
        }
        if (forecast.getTemperature().getMax() == null) {
            return "-";
        }
        Max max = forecast.getTemperature().getMax();
        if (max.getCelsius() == null || max.getCelsius().isEmpty()) {
            return "-";
        }
        return max.getCelsius() + "℃";
    }

    public void displayImageFromForecast(Forecast forecast, final ImageView imageView) {
        if (forecast == null) {
            imageView.setImageResource(R.drawable.icon_no_image);
            return;
        }
        if (forecast.getImage() == null) {
            imageView.setImageResource(R.drawable.icon_no_image);
            return;
        }
        String urlText = forecast.getImage().getUrl();
        if (urlText == null || urlText.isEmpty()) {
            imageView.setImageResource(R.drawable.icon_no_image);
            return;
        }
        NetworkManager.getInstance().requestImage(urlText, new RequestImageCallback() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onFailure(String message) {
                imageView.setImageResource(R.drawable.icon_no_image);
            }
        });
    }
}
