package com.am10.androidarchitecture.Response;
//
//	Location.java
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import org.json.*;

import java.io.Serializable;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class Location implements Serializable {

	@SerializedName("area")
	private String area;
	@SerializedName("city")
	private String city;
	@SerializedName("prefecture")
	private String prefecture;

	public void setArea(String area){
		this.area = area;
	}
	public String getArea(){
		return this.area;
	}
	public void setCity(String city){
		this.city = city;
	}
	public String getCity(){
		return this.city;
	}
	public void setPrefecture(String prefecture){
		this.prefecture = prefecture;
	}
	public String getPrefecture(){
		return this.prefecture;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public Location(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		area = jsonObject.optString("area");
		city = jsonObject.optString("city");
		prefecture = jsonObject.optString("prefecture");
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("area", area);
			jsonObject.put("city", city);
			jsonObject.put("prefecture", prefecture);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}