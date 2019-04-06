package com.am10.androidarchitecture;
//
//	CC.java
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import org.json.*;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class CityDataList{

	@SerializedName("cityDataList")
	private CityData[] cityDataList;

	public void setCityDataList(CityData[] cityDataList){
		this.cityDataList = cityDataList;
	}
	public CityData[] getCityDataList(){
		return this.cityDataList;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public CityDataList(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		JSONArray cityDataListJsonArray = jsonObject.optJSONArray("cityDataList");
		if(cityDataListJsonArray != null){
			ArrayList<CityData> cityDataListArrayList = new ArrayList<>();
			for (int i = 0; i < cityDataListJsonArray.length(); i++) {
				JSONObject cityDataListObject = cityDataListJsonArray.optJSONObject(i);
				cityDataListArrayList.add(new CityData(cityDataListObject));
			}
			cityDataList = (CityData[]) cityDataListArrayList.toArray();
		}	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			if(cityDataList != null && cityDataList.length > 0){
				JSONArray cityDataListJsonArray = new JSONArray();
				for(CityData cityDataListElement : cityDataList){
					cityDataListJsonArray.put(cityDataListElement.toJsonObject());
				}
				jsonObject.put("cityDataList", cityDataListJsonArray);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}