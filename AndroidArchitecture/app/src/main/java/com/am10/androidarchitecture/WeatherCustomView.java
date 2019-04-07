package com.am10.androidarchitecture;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.am10.androidarchitecture.Response.Forecast;


public class WeatherCustomView extends LinearLayout {

    private TextView mDateTextView;
    private TextView mSubdateTextView;
    private TextView mTelopTextView;
    private TextView mMaxCelsiusTextView;
    private TextView mMinCelsiusTitleTextView;
    private TextView mMaxCelsiusTitleTextView;
    private TextView mMinCelsiusTextView;
    public ImageView imageView;

    public WeatherCustomView(Context context) {
        this(context, null);
    }

    public WeatherCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.weatherCustomViewStyle);
    }

    public WeatherCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // このへんで初期化処理
        LayoutInflater.from(context).inflate(R.layout.weather_layout, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WeatherCustomView,
                defStyleAttr, 0);

        float fontSize = (float) a.getInteger(R.styleable.WeatherCustomView_fontSize, 15);
        a.recycle();
        mDateTextView = findViewById(R.id.textView_date);
        mDateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mSubdateTextView = findViewById(R.id.textView_subdate);
        mSubdateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mTelopTextView = findViewById(R.id.textView_telop);
        mTelopTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mMaxCelsiusTextView = findViewById(R.id.textView_max_celsius);
        mMaxCelsiusTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mMinCelsiusTextView = findViewById(R.id.textView_min_celsius);
        mMinCelsiusTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mMinCelsiusTitleTextView = findViewById(R.id.textView_min_celsius_title);
        mMinCelsiusTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        mMaxCelsiusTitleTextView = findViewById(R.id.textView_max_celsius_title);
        mMaxCelsiusTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        imageView = findViewById(R.id.imageView);
    }

    public void displayForecast(Forecast forecast, String minCelsius, String maxCelsius) {
        if (forecast == null) {
            mDateTextView.setText("");
            mSubdateTextView.setText("");
            mTelopTextView.setText("");
        } else {
            mDateTextView.setText(forecast.getDate());
            mSubdateTextView.setText(forecast.getDateLabel());
            mTelopTextView.setText(forecast.getTelop());
        }
        mMaxCelsiusTextView.setText(maxCelsius);
        mMinCelsiusTextView.setText(minCelsius);
    }
}