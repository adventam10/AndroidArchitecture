package com.am10.androidarchitecture;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.am10.androidarchitecture.Response.Weather;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PrefectureListActivity extends AppCompatActivity implements PrefectureListRecyclerAdapter.OnRecyclerListener {

    public static final String PREF_KEY_FAVORITE = "PREF_KEY_FAVORITE";
    private RecyclerView mRecyclerView;
    private TextView mNoDataTextView;
    private PrefectureListRecyclerAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private ArrayList<CityData> mCityDataList;
    private ArrayList<CityData> mTableDataList;
    private CityData mSelectedCityData;
    private ArrayList<String> mFavoriteCityIdList;
    private ArrayList<AreaFilterListAdapter.Area> mAreaFilterList = new ArrayList<>(AreaFilterListAdapter.areaList);
    private boolean mIsFavoriteChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefecture_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("地域");

        String cityDataJson = readJsonFile(this, "CityData.json");
        Gson gson = new Gson();
        CityDataList cityDataList = gson.fromJson(cityDataJson, CityDataList.class);
        mNoDataTextView = findViewById(R.id.textView_no_data);
        mNoDataTextView.setVisibility(View.GONE);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFavoriteCityIdList = loadFavoriteCityIdList(this);
        mCityDataList = new ArrayList<>(Arrays.asList(cityDataList.getCityDataList()));
        mTableDataList = new ArrayList<>(mCityDataList);
        mAdapter = new PrefectureListRecyclerAdapter(this, mTableDataList, this);
        mAdapter.setFavoriteCityIdList(mFavoriteCityIdList);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        CheckBox checkBox = findViewById(R.id.checkBox_favorite);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsFavoriteChecked = isChecked;
                refreshRecyclerView();
            }
        });
        checkBox.setChecked(mIsFavoriteChecked);

        ImageButton button = findViewById(R.id.button_area_filter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAreaPopupWindow(v);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.refresh();
    }

    @Override
    public void onRecyclerClicked(View v, int position) {
        // セルクリック処理
        showProgress("", "データ取得中...");
        mAdapter.notifyItemChanged(position);
        mSelectedCityData = mTableDataList.get(position);
        requestWeather(mSelectedCityData.getCityId());
    }

    @Override
    public void onRecyclerChecked(View v, int position, boolean isCheck) {
        CityData cityData = mTableDataList.get(position);
        String cityId = cityData.getCityId();
        editFavoriteCityIdList(cityId, isCheck);
        saveFavoriteCityIdList(this, mFavoriteCityIdList);
        mAdapter.setFavoriteCityIdList(mFavoriteCityIdList);
        mAdapter.notifyItemChanged(position);
    }

    private void refreshRecyclerView() {
        mTableDataList = setTableDataList(mIsFavoriteChecked);
        mAdapter.setCityDataList(mTableDataList);
        mAdapter.refresh();

        if (mTableDataList.isEmpty()) {
            mNoDataTextView.setVisibility(View.VISIBLE);
        } else {
            mNoDataTextView.setVisibility(View.GONE);
        }
    }

    private void editFavoriteCityIdList(String cityId, boolean isCheck) {
        if (isCheck) {
            if (!mFavoriteCityIdList.contains(cityId)) {
                mFavoriteCityIdList.add(cityId);
            }
        } else {
            if (mFavoriteCityIdList.contains(cityId)) {
                mFavoriteCityIdList.remove(cityId);
            }
        }
    }

    private ArrayList<CityData> setTableDataList(boolean isFavorite) {
        ArrayList<CityData> tableDataList = setFavoriteFilter(isFavorite, mCityDataList);
        return setAreaFilter(tableDataList, mAreaFilterList);
    }

    private ArrayList<CityData> setFavoriteFilter(boolean isFavorite, ArrayList<CityData> cityDataList) {
        if (!isFavorite) {
            return new ArrayList<>(cityDataList);
        }
        ArrayList<CityData> filteredList = new ArrayList<>();
        for (CityData cityData : cityDataList) {
            if (mFavoriteCityIdList.contains(cityData.getCityId())) {
                filteredList.add(cityData);
            }
        }
        return filteredList;
    }

    private ArrayList<CityData> setAreaFilter(ArrayList<CityData> cityDataList, ArrayList<AreaFilterListAdapter.Area> areaList) {
        ArrayList<CityData> filteredList = new ArrayList<>();
        for (CityData cityData : cityDataList) {
            for (AreaFilterListAdapter.Area area : areaList) {
                if (cityData.getArea() == area.id) {
                    filteredList.add(cityData);
                }
            }
        }
        return filteredList;
    }

    private void requestWeather(String cityId) {
        NetworkManager.getInstance().requestWeather(cityId, new RequestWeatherCallback() {
            @Override
            public void onSuccess(Weather weather) {
                dismissProgress();
                showWeatherActivity(weather);
            }

            @Override
            public void onFailure(String message) {
                dismissProgress();
                CommonDialogFragment dialog = CommonDialogFragment.newInstance("通信失敗",  message,
                        "OK", "", false);
                dialog.show(getSupportFragmentManager(), CommonDialogFragment.DIALOG_TAG);
                mAdapter.refresh();
            }
        });
    }

    private void showWeatherActivity(Weather weather) {
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra(WeatherActivity.EXTRA_WEATHER, weather);
        intent.putExtra(WeatherActivity.EXTRA_CITY_DATA, mSelectedCityData);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

    private void showAreaPopupWindow(View v) {
        View popupView = getLayoutInflater().inflate(R.layout.area_filter_popwindow, null);
        ListView listView = popupView.findViewById(R.id.listView);
        final CheckBox allCheckBox = popupView.findViewById(R.id.checkBox);
        final AreaFilterListAdapter adapter = new AreaFilterListAdapter(this, mAreaFilterList, new AreaFilterListAdapter.OnAreaFilterListener() {
            @Override
            public void onAreaFilterChecked(AreaFilterListAdapter.Area area, boolean isCheck) {
                if (isCheck) {
                    if (!mAreaFilterList.contains(area)) {
                        mAreaFilterList.add(area);
                    }
                } else {
                    if (mAreaFilterList.contains(area)) {
                        mAreaFilterList.remove(area);
                    }
                }
                allCheckBox.setChecked(mAreaFilterList.containsAll(AreaFilterListAdapter.areaList));
                refreshRecyclerView();
            }
        });

        allCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                mAreaFilterList.clear();
                if (checkBox.isChecked()) {
                    mAreaFilterList = new ArrayList<>(AreaFilterListAdapter.areaList);
                }
                adapter.refresh(mAreaFilterList);
                refreshRecyclerView();
            }
        });
        allCheckBox.setChecked(mAreaFilterList.containsAll(AreaFilterListAdapter.areaList));
        ImageButton button = findViewById(R.id.button_area_filter);
        listView.setAdapter(adapter);
        PopupWindow popupWindow = new PopupWindow(this);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        int height = popupWindow.getMaxAvailableHeight(v) >= 900 ? 900 : popupWindow.getMaxAvailableHeight(v);
        popupWindow.setHeight(height);
        popupWindow.setWidth(button.getWidth());
        popupWindow.setContentView(popupView);
        popupWindow.showAsDropDown(v);
    }

    private static void saveFavoriteCityIdList(Context context, ArrayList<String> favoriteCityIdList) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> set = new HashSet<>(favoriteCityIdList);
        prefs.edit().putStringSet(PREF_KEY_FAVORITE, set).apply();
    }

    private static ArrayList<String> loadFavoriteCityIdList(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> set = prefs.getStringSet(PREF_KEY_FAVORITE, new HashSet<String>());
        return new ArrayList<>(set);
    }

    private static String readJsonFile(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);

            String data = "";
            String str = br.readLine();
            while (str != null) {
                data += str;
                str = br.readLine();
            }

            br.close();
            return data;
        } catch(FileNotFoundException e) {
            return null;
        } catch(IOException e) {
            return null;
        }
    }
}
