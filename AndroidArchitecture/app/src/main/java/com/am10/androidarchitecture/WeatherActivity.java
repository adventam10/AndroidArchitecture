package com.am10.androidarchitecture;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.am10.androidarchitecture.Response.Forecast;
import com.am10.androidarchitecture.Response.Max;
import com.am10.androidarchitecture.Response.Weather;

public class WeatherActivity extends AppCompatActivity {

    public static final String STATE_WEATHER = "STATE_WEATHER";
    public static final String STATE_CITY_DATA = "STATE_CITY_DATA";
    public static final String EXTRA_WEATHER = "EXTRA_WEATHER";
    public static final String EXTRA_CITY_DATA = "EXTRA_CITY_DATA";

    private TextView mTodayDateTextView;
    private TextView mTodaySubdateTextView;
    private TextView mTodayTelopTextView;
    private TextView mTodayMaxCelsiusTextView;
    private TextView mTodayMinCelsiusTextView;
    private ImageView mTodayImageView;

    private TextView mTomorrowDateTextView;
    private TextView mTomorrowSubdateTextView;
    private TextView mTomorrowTelopTextView;
    private TextView mTomorrowMaxCelsiusTextView;
    private TextView mTomorrowMinCelsiusTextView;
    private ImageView mTomorrowImageView;

    private TextView mDayAfterTomorrowDateTextView;
    private TextView mDayAfterTomorrowSubdateTextView;
    private TextView mDayAfterTomorrowTelopTextView;
    private TextView mDayAfterTomorrowMaxCelsiusTextView;
    private TextView mDayAfterTomorrowMinCelsiusTextView;
    private ImageView mDayAfterTomorrowImageView;

    private ProgressDialog mProgressDialog;

    private Weather mWeather;
    private CityData mCityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            mWeather = (Weather) intent.getSerializableExtra(EXTRA_WEATHER);
            mCityData = (CityData) intent.getSerializableExtra(EXTRA_CITY_DATA);
        } else {
            mWeather = (Weather) savedInstanceState.getSerializable(STATE_WEATHER);
            mCityData = (CityData) savedInstanceState.getSerializable(STATE_CITY_DATA);
        }

        // アクションバーに前画面に戻る機能をつける
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mCityData.getName());

        mTodayDateTextView = findViewById(R.id.textView_today_date);
        mTodaySubdateTextView = findViewById(R.id.textView_today_subdate);
        mTodayTelopTextView = findViewById(R.id.textView_today_telop);
        mTodayMaxCelsiusTextView = findViewById(R.id.textView_today_max_celsius);
        mTodayMinCelsiusTextView = findViewById(R.id.textView_today_min_celsius);
        mTodayImageView = findViewById(R.id.imageView_today);

        mTomorrowDateTextView = findViewById(R.id.textView_tomorrow_date);
        mTomorrowSubdateTextView = findViewById(R.id.textView_tomorrow_subdate);
        mTomorrowTelopTextView = findViewById(R.id.textView_tomorrow_telop);
        mTomorrowMaxCelsiusTextView = findViewById(R.id.textView_tomorrow_max_celsius);
        mTomorrowMinCelsiusTextView = findViewById(R.id.textView_tomorrow_min_celsius);
        mTomorrowImageView = findViewById(R.id.imageView_tomorrow);

        mDayAfterTomorrowDateTextView = findViewById(R.id.textView_day_after_tomorrow_date);
        mDayAfterTomorrowSubdateTextView = findViewById(R.id.textView_day_after_tomorrow_subdate);
        mDayAfterTomorrowTelopTextView = findViewById(R.id.textView_day_after_tomorrow_telop);
        mDayAfterTomorrowMaxCelsiusTextView = findViewById(R.id.textView_day_after_tomorrow_max_celsius);
        mDayAfterTomorrowMinCelsiusTextView = findViewById(R.id.textView_day_after_tomorrow_min_celsius);
        mDayAfterTomorrowImageView = findViewById(R.id.imageView_day_after_tomorrow);

        displayForecasts(mWeather.getForecasts());
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(STATE_CITY_DATA, mCityData);
        savedInstanceState.putSerializable(STATE_WEATHER, mWeather);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_refresh:
                // ボタンをタップした際の処理を記述
                requestWeather(mCityData.getCityId());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayForecasts(Forecast[] forecasts) {
        displayTodayForecast(null);
        displayTomorrowForecast(null);
        displayDayAfterTomorrowForecast(null);
        int index = 0;
        for (Forecast forecast : forecasts) {
            if (index == 0) {
                displayTodayForecast(forecast);
            } else if (index == 1) {
                displayTomorrowForecast(forecast);
            } else if (index == 2) {
                displayDayAfterTomorrowForecast(forecast);
            }
            index++;
        }
    }

    private void displayTodayForecast(Forecast forecast) {
        if (forecast == null) {
            mTodayDateTextView.setText("");
            mTodaySubdateTextView.setText("");
            mTodayTelopTextView.setText("");
        } else {
            mTodayDateTextView.setText(forecast.getDate());
            mTodaySubdateTextView.setText(forecast.getDateLabel());
            mTodayTelopTextView.setText(forecast.getTelop());
        }
        mTodayMaxCelsiusTextView.setText(getMaxCelsiusFromForecast(forecast));
        mTodayMinCelsiusTextView.setText(getMinCelsiusFromForecast(forecast));
        displayImageFromForecast(forecast, mTodayImageView);
    }

    private void displayTomorrowForecast(Forecast forecast) {
        if (forecast == null) {
            mTomorrowDateTextView.setText("");
            mTomorrowSubdateTextView.setText("");
            mTomorrowTelopTextView.setText("");
        } else {
            mTomorrowDateTextView.setText(forecast.getDate());
            mTomorrowSubdateTextView.setText(forecast.getDateLabel());
            mTomorrowTelopTextView.setText(forecast.getTelop());
        }
        mTomorrowMaxCelsiusTextView.setText(getMaxCelsiusFromForecast(forecast));
        mTomorrowMinCelsiusTextView.setText(getMinCelsiusFromForecast(forecast));
        displayImageFromForecast(forecast, mTomorrowImageView);
    }

    private void displayDayAfterTomorrowForecast(Forecast forecast) {
        if (forecast == null) {
            mDayAfterTomorrowDateTextView.setText("");
            mDayAfterTomorrowSubdateTextView.setText("");
            mDayAfterTomorrowTelopTextView.setText("");
        } else {
            mDayAfterTomorrowDateTextView.setText(forecast.getDate());
            mDayAfterTomorrowSubdateTextView.setText(forecast.getDateLabel());
            mDayAfterTomorrowTelopTextView.setText(forecast.getTelop());
        }
        mDayAfterTomorrowMaxCelsiusTextView.setText(getMaxCelsiusFromForecast(forecast));
        mDayAfterTomorrowMinCelsiusTextView.setText(getMinCelsiusFromForecast(forecast));
        displayImageFromForecast(forecast, mDayAfterTomorrowImageView);
    }

    private String getMinCelsiusFromForecast(Forecast forecast) {
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

    private String getMaxCelsiusFromForecast(Forecast forecast) {
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

    private void displayImageFromForecast(Forecast forecast, final ImageView imageView) {
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

    private void requestWeather(String cityId) {
        showProgress("", "データ取得中...");
        NetworkManager.getInstance().requestWeather(cityId, new RequestWeatherCallback() {
            @Override
            public void onSuccess(Weather weather) {
                dismissProgress();
                mWeather = weather;
                displayForecasts(mWeather.getForecasts());
            }

            @Override
            public void onFailure(String message) {
                dismissProgress();
                CommonDialogFragment dialog = CommonDialogFragment.newInstance("通信失敗",  message,
                        "OK", "", false);
                dialog.show(getSupportFragmentManager(), CommonDialogFragment.DIALOG_TAG);
            }
        });
    }

    private void showProgress(String title, String message) {
        dismissProgress();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    private void dismissProgress() {
        if (mProgressDialog == null) {
            return;
        }
        mProgressDialog.dismiss();
        mProgressDialog = null;
    }
}
