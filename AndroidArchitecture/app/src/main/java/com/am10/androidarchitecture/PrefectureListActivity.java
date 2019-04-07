package com.am10.androidarchitecture;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.am10.androidarchitecture.Response.Weather;

import java.util.ArrayList;

public class PrefectureListActivity extends AppCompatActivity implements PrefectureListRecyclerAdapter.OnRecyclerListener, PrefectureListFragment.OnEventListener {

    public static final String STATE_MODEL = "STATE_MODEL";
    private PrefectureListFragment mFragment;
    private PrefectureListModel mModel;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefecture_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("地域");

        if (savedInstanceState == null) {
            mFragment = new PrefectureListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container, mFragment);
            transaction.commit();
            mModel = new PrefectureListModel(this);
        } else {
            mFragment = (PrefectureListFragment)getSupportFragmentManager().getFragments().get(0);
            mModel = (PrefectureListModel) savedInstanceState.getSerializable(STATE_MODEL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFragment.setupAdapter(mModel.tableDataList, mModel.favoriteCityIdList);
        mFragment.recyclerViewRefresh();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable(STATE_MODEL, mModel);
    }

    @Override
    public void onAreaFilterClickListener(View v) {
        showAreaPopupWindow(v);
    }

    @Override
    public void onFavoriteCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mModel.isFavoriteChecked = isChecked;
        refreshRecyclerView();
    }

    @Override
    public void onRecyclerClicked(View v, int position) {
        // セルクリック処理
        showProgress("", "データ取得中...");
        mFragment.notifyItemChanged(position, mModel.favoriteCityIdList);
        mModel.selectedCityData = mModel.tableDataList.get(position);
        requestWeather(mModel.selectedCityData.getCityId());
    }

    @Override
    public void onRecyclerChecked(View v, int position, boolean isCheck) {
        CityData cityData = mModel.tableDataList.get(position);
        String cityId = cityData.getCityId();
        mModel.editFavoriteCityIdList(cityId, isCheck);
        PrefectureListModel.saveFavoriteCityIdList(this, mModel.favoriteCityIdList);
        mFragment.notifyItemChanged(position, mModel.favoriteCityIdList);
    }

    private void refreshRecyclerView() {
        mModel.tableDataList = mModel.setTableDataList(mModel.isFavoriteChecked);
        mFragment.recyclerViewRefresh(mModel.tableDataList);
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
                mFragment.recyclerViewRefresh();
            }
        });
    }

    private void showWeatherActivity(Weather weather) {
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra(WeatherActivity.EXTRA_WEATHER, weather);
        intent.putExtra(WeatherActivity.EXTRA_CITY_DATA, mModel.selectedCityData);
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
        final AreaFilterListAdapter adapter = new AreaFilterListAdapter(this, mModel.areaFilterList, new AreaFilterListAdapter.OnAreaFilterListener() {
            @Override
            public void onAreaFilterChecked(AreaFilterListAdapter.Area area, boolean isCheck) {
                mModel.editAreaFilterList(area, isCheck);
                allCheckBox.setChecked(mModel.areaFilterList.containsAll(AreaFilterListAdapter.areaList));
                refreshRecyclerView();
            }
        });

        allCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox)v;
                mModel.areaFilterList.clear();
                if (checkBox.isChecked()) {
                    mModel.areaFilterList = new ArrayList<>(AreaFilterListAdapter.areaList);
                }
                adapter.refresh(mModel.areaFilterList);
                refreshRecyclerView();
            }
        });
        allCheckBox.setChecked(mModel.areaFilterList.containsAll(AreaFilterListAdapter.areaList));
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
}
