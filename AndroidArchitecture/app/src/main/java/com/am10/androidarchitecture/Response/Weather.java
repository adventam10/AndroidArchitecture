package com.am10.androidarchitecture.Response;
//
//	Weather.java
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport


import org.json.*;

import java.io.Serializable;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class Weather implements Serializable {

	@SerializedName("copyright")
	private Copyright copyright;
	@SerializedName("description")
	private Description description;
	@SerializedName("forecasts")
	private Forecast[] forecasts;
	@SerializedName("link")
	private String link;
	@SerializedName("location")
	private Location location;
	@SerializedName("pinpointLocations")
	private Provider[] pinpointLocations;
	@SerializedName("publicTime")
	private String publicTime;
	@SerializedName("title")
	private String title;

	public void setCopyright(Copyright copyright){
		this.copyright = copyright;
	}
	public Copyright getCopyright(){
		return this.copyright;
	}
	public void setDescription(Description description){
		this.description = description;
	}
	public Description getDescription(){
		return this.description;
	}
	public void setForecasts(Forecast[] forecasts){
		this.forecasts = forecasts;
	}
	public Forecast[] getForecasts(){
		return this.forecasts;
	}
	public void setLink(String link){
		this.link = link;
	}
	public String getLink(){
		return this.link;
	}
	public void setLocation(Location location){
		this.location = location;
	}
	public Location getLocation(){
		return this.location;
	}
	public void setPinpointLocations(Provider[] pinpointLocations){
		this.pinpointLocations = pinpointLocations;
	}
	public Provider[] getPinpointLocations(){
		return this.pinpointLocations;
	}
	public void setPublicTime(String publicTime){
		this.publicTime = publicTime;
	}
	public String getPublicTime(){
		return this.publicTime;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return this.title;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public Weather(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		copyright = new Copyright(jsonObject.optJSONObject("copyright"));
		description = new Description(jsonObject.optJSONObject("description"));
		JSONArray forecastsJsonArray = jsonObject.optJSONArray("forecasts");
		if(forecastsJsonArray != null){
			ArrayList<Forecast> forecastsArrayList = new ArrayList<>();
			for (int i = 0; i < forecastsJsonArray.length(); i++) {
				JSONObject forecastsObject = forecastsJsonArray.optJSONObject(i);
				forecastsArrayList.add(new Forecast(forecastsObject));
			}
			forecasts = (Forecast[]) forecastsArrayList.toArray();
		}		link = jsonObject.optString("link");
		location = new Location(jsonObject.optJSONObject("location"));
		JSONArray pinpointLocationsJsonArray = jsonObject.optJSONArray("pinpointLocations");
		if(pinpointLocationsJsonArray != null){
			ArrayList<Provider> pinpointLocationsArrayList = new ArrayList<>();
			for (int i = 0; i < pinpointLocationsJsonArray.length(); i++) {
				JSONObject pinpointLocationsObject = pinpointLocationsJsonArray.optJSONObject(i);
				pinpointLocationsArrayList.add(new Provider(pinpointLocationsObject));
			}
			pinpointLocations = (Provider[]) pinpointLocationsArrayList.toArray();
		}		publicTime = jsonObject.optString("publicTime");
		title = jsonObject.optString("title");
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("copyright", copyright.toJsonObject());
			jsonObject.put("description", description.toJsonObject());
			if(forecasts != null && forecasts.length > 0){
				JSONArray forecastsJsonArray = new JSONArray();
				for(Forecast forecastsElement : forecasts){
					forecastsJsonArray.put(forecastsElement.toJsonObject());
				}
				jsonObject.put("forecasts", forecastsJsonArray);
			}
			jsonObject.put("link", link);
			jsonObject.put("location", location.toJsonObject());
			if(pinpointLocations != null && pinpointLocations.length > 0){
				JSONArray pinpointLocationsJsonArray = new JSONArray();
				for(Provider pinpointLocationsElement : pinpointLocations){
					pinpointLocationsJsonArray.put(pinpointLocationsElement.toJsonObject());
				}
				jsonObject.put("pinpointLocations", pinpointLocationsJsonArray);
			}
			jsonObject.put("publicTime", publicTime);
			jsonObject.put("title", title);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}