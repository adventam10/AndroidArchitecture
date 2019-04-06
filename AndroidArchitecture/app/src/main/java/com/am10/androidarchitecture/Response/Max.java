package com.am10.androidarchitecture.Response;
//
//	Max.java
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import org.json.*;

import java.io.Serializable;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class Max implements Serializable {

	@SerializedName("celsius")
	private String celsius;
	@SerializedName("fahrenheit")
	private String fahrenheit;

	public void setCelsius(String celsius){
		this.celsius = celsius;
	}
	public String getCelsius(){
		return this.celsius;
	}
	public void setFahrenheit(String fahrenheit){
		this.fahrenheit = fahrenheit;
	}
	public String getFahrenheit(){
		return this.fahrenheit;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public Max(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		celsius = jsonObject.optString("celsius");
		fahrenheit = jsonObject.optString("fahrenheit");
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("celsius", celsius);
			jsonObject.put("fahrenheit", fahrenheit);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}