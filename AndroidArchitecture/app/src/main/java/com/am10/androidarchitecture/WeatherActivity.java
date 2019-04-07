package com.am10.androidarchitecture;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.am10.androidarchitecture.Response.Forecast;
import com.am10.androidarchitecture.Response.Weather;

public class WeatherActivity extends AppCompatActivity {

    public static final String STATE_MODEL = "STATE_MODEL";
    public static final String EXTRA_WEATHER = "EXTRA_WEATHER";
    public static final String EXTRA_CITY_DATA = "EXTRA_CITY_DATA";

    private WeatherFragment mFragment;
    private WeatherModel mModel = new WeatherModel();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            mModel.weather = (Weather) intent.getSerializableExtra(EXTRA_WEATHER);
            mModel.cityData = (CityData) intent.getSerializableExtra(EXTRA_CITY_DATA);
            mFragment = new WeatherFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container, mFragment);
            transaction.commit();
        } else {
            mFragment = (WeatherFragment) getSupportFragmentManager().getFragments().get(0);
            mModel = (WeatherModel) savedInstanceState.getSerializable(STATE_MODEL);
        }

        // アクションバーに前画面に戻る機能をつける
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(mModel.cityData.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayForecasts(mModel.weather.getForecasts());
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(STATE_MODEL, mModel);
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
                requestWeather(mModel.cityData.getCityId());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayForecasts(Forecast[] forecasts) {
        mFragment.displayForecast(WeatherFragment.Date.Today, null, mModel.getMinCelsiusFromForecast(null), mModel.getMaxCelsiusFromForecast(null));
        mFragment.displayForecast(WeatherFragment.Date.Tomorrow, null, mModel.getMinCelsiusFromForecast(null), mModel.getMaxCelsiusFromForecast(null));
        mFragment.displayForecast(WeatherFragment.Date.DayAfterTomorrow, null, mModel.getMinCelsiusFromForecast(null), mModel.getMaxCelsiusFromForecast(null));
        int index = 0;
        for (Forecast forecast : forecasts) {
            WeatherFragment.Date date = WeatherFragment.Date.Today;
            if (index == 0) {
                date = WeatherFragment.Date.Today;
            } else if (index == 1) {
                date = WeatherFragment.Date.Tomorrow;
            } else if (index == 2) {
                date = WeatherFragment.Date.DayAfterTomorrow;
            }

            mFragment.displayForecast(date, forecast, mModel.getMinCelsiusFromForecast(forecast), mModel.getMaxCelsiusFromForecast(forecast));
            mModel.displayImageFromForecast(forecast, mFragment.getImageView(date));
            index++;
        }
    }

    private void requestWeather(String cityId) {
        showProgress("", "データ取得中...");
        NetworkManager.getInstance().requestWeather(cityId, new RequestWeatherCallback() {
            @Override
            public void onSuccess(Weather weather) {
                dismissProgress();
                mModel.weather = weather;
                displayForecasts(mModel.weather.getForecasts());
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
