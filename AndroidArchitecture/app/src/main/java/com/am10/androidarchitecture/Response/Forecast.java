package com.am10.androidarchitecture.Response;
//
//	Forecast.java
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import org.json.*;

import java.io.Serializable;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class Forecast implements Serializable {

	@SerializedName("date")
	private String date;
	@SerializedName("dateLabel")
	private String dateLabel;
	@SerializedName("image")
	private Image image;
	@SerializedName("telop")
	private String telop;
	@SerializedName("temperature")
	private Temperature temperature;

	public void setDate(String date){
		this.date = date;
	}
	public String getDate(){
		return this.date;
	}
	public void setDateLabel(String dateLabel){
		this.dateLabel = dateLabel;
	}
	public String getDateLabel(){
		return this.dateLabel;
	}
	public void setImage(Image image){
		this.image = image;
	}
	public Image getImage(){
		return this.image;
	}
	public void setTelop(String telop){
		this.telop = telop;
	}
	public String getTelop(){
		return this.telop;
	}
	public void setTemperature(Temperature temperature){
		this.temperature = temperature;
	}
	public Temperature getTemperature(){
		return this.temperature;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public Forecast(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		date = jsonObject.optString("date");
		dateLabel = jsonObject.optString("dateLabel");
		image = new Image(jsonObject.optJSONObject("image"));
		telop = jsonObject.optString("telop");
		temperature = new Temperature(jsonObject.optJSONObject("temperature"));
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("date", date);
			jsonObject.put("dateLabel", dateLabel);
			jsonObject.put("image", image.toJsonObject());
			jsonObject.put("telop", telop);
			jsonObject.put("temperature", temperature.toJsonObject());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}