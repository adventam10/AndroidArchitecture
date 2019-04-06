package com.am10.androidarchitecture;
//
//	CityDataList.java
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport


import org.json.*;

import java.io.Serializable;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class CityData implements Serializable {

	@SerializedName("area")
	private int area;
	@SerializedName("cityId")
	private String cityId;
	@SerializedName("name")
	private String name;

	public void setArea(int area){
		this.area = area;
	}
	public int getArea(){
		return this.area;
	}
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	public String getCityId(){
		return this.cityId;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public CityData(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		area = jsonObject.optInt("area");
		cityId = jsonObject.optString("cityId");
		name = jsonObject.optString("name");
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("area", area);
			jsonObject.put("cityId", cityId);
			jsonObject.put("name", name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}