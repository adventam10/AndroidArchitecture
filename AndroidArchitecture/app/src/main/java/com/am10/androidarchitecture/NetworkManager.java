package com.am10.androidarchitecture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import com.am10.androidarchitecture.Response.Weather;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

interface RequestWeatherCallback {
    void onSuccess(Weather weather);
    void onFailure(String message);
}

interface RequestImageCallback {
    void onSuccess(Bitmap bitmap);
    void onFailure(String message);
}
class NetworkManager {
    private static final NetworkManager ourInstance = new NetworkManager();

    static NetworkManager getInstance() {
        return ourInstance;
    }
    private OkHttpClient mHttpClient = new OkHttpClient.Builder().build();
    private String mBaseURL = "http://weather.livedoor.com/forecast/webservice/json/v1";
    private NetworkManager() {
    }

    public void requestWeather(String cityId, final RequestWeatherCallback callback) {
        String url = mBaseURL + "?city=" + cityId;
        Request request = new Request.Builder().url(url).build();
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI操作
                        callback.onFailure(e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String jsonData;
                try {
                    jsonData = response.body().string();
                } catch (final IOException e) {
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //UI操作
                            callback.onFailure(e.getLocalizedMessage());
                        }
                    });
                    return;
                }
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI操作
                        if (response.code() != 200) {
                            callback.onFailure("サーバーと通信できません。");
                            return;
                        }
                        Gson gson = new Gson();
                        Weather weather = gson.fromJson(jsonData, Weather.class);
                        callback.onSuccess(weather);
                    }
                });
            }
        });
    }

    public void requestImage(String imageUrl, final RequestImageCallback callback) {
        Request request = new Request.Builder().url(imageUrl).build();
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI操作
                        callback.onFailure(e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI操作
                        if (response.code() != 200) {
                            callback.onFailure("サーバーと通信できません。");
                            return;
                        }
                        callback.onSuccess(bitmap);
                    }
                });
            }
        });
    }

}
