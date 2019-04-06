package com.am10.androidarchitecture.Response;
//
//	Description.java
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import org.json.*;

import java.io.Serializable;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class Description implements Serializable {

	@SerializedName("publicTime")
	private String publicTime;
	@SerializedName("text")
	private String text;

	public void setPublicTime(String publicTime){
		this.publicTime = publicTime;
	}
	public String getPublicTime(){
		return this.publicTime;
	}
	public void setText(String text){
		this.text = text;
	}
	public String getText(){
		return this.text;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public Description(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		publicTime = jsonObject.optString("publicTime");
		text = jsonObject.optString("text");
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("publicTime", publicTime);
			jsonObject.put("text", text);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}