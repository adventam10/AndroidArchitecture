package com.am10.androidarchitecture.Response;
//
//	Temperature.java
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import org.json.*;

import java.io.Serializable;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class Temperature implements Serializable {

	@SerializedName("max")
	private Max max;
	@SerializedName("min")
	private Max min;

	public void setMax(Max max){
		this.max = max;
	}
	public Max getMax(){
		return this.max;
	}
	public void setMin(Max min){
		this.min = min;
	}
	public Max getMin(){
		return this.min;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public Temperature(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		max = new Max(jsonObject.optJSONObject("max"));
		min = new Max(jsonObject.optJSONObject("min"));
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("max", max.toJsonObject());
			jsonObject.put("min", min.toJsonObject());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}