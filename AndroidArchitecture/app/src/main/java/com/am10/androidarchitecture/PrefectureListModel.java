package com.am10.androidarchitecture;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PrefectureListModel implements Serializable {

    public static final String PREF_KEY_FAVORITE = "PREF_KEY_FAVORITE";

    public ArrayList<CityData> cityDataList;
    public ArrayList<CityData> tableDataList;
    public CityData selectedCityData;
    public ArrayList<String> favoriteCityIdList;
    public ArrayList<AreaFilterListAdapter.Area> areaFilterList = new ArrayList<>(AreaFilterListAdapter.areaList);
    public boolean isFavoriteChecked = false;

    public PrefectureListModel(Context context) {
        String cityDataJson = readJsonFile(context, "CityData.json");
        Gson gson = new Gson();
        CityDataList cityDataList = gson.fromJson(cityDataJson, CityDataList.class);
        this.favoriteCityIdList = loadFavoriteCityIdList(context);
        this.cityDataList = new ArrayList<>(Arrays.asList(cityDataList.getCityDataList()));
        this.tableDataList = new ArrayList<>(this.cityDataList);
    }

    public static void saveFavoriteCityIdList(Context context, ArrayList<String> favoriteCityIdList) {
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

    public void editFavoriteCityIdList(String cityId, boolean isCheck) {
        if (isCheck) {
            if (!favoriteCityIdList.contains(cityId)) {
                favoriteCityIdList.add(cityId);
            }
        } else {
            if (favoriteCityIdList.contains(cityId)) {
                favoriteCityIdList.remove(cityId);
            }
        }
    }

    public void editAreaFilterList(AreaFilterListAdapter.Area area, boolean isCheck) {
        if (isCheck) {
            if (!areaFilterList.contains(area)) {
                areaFilterList.add(area);
            }
        } else {
            if (areaFilterList.contains(area)) {
                areaFilterList.remove(area);
            }
        }
    }

    public ArrayList<CityData> setTableDataList(boolean isFavorite) {
        ArrayList<CityData> tableDataList = setFavoriteFilter(isFavorite, cityDataList);
        return setAreaFilter(tableDataList, areaFilterList);
    }

    private ArrayList<CityData> setFavoriteFilter(boolean isFavorite, ArrayList<CityData> cityDataList) {
        if (!isFavorite) {
            return new ArrayList<>(cityDataList);
        }
        ArrayList<CityData> filteredList = new ArrayList<>();
        for (CityData cityData : cityDataList) {
            if (favoriteCityIdList.contains(cityData.getCityId())) {
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
}
