package com.am10.androidarchitecture.Response;
//
//	Provider.java
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import org.json.*;

import java.io.Serializable;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class Provider implements Serializable {

	@SerializedName("link")
	private String link;
	@SerializedName("name")
	private String name;

	public void setLink(String link){
		this.link = link;
	}
	public String getLink(){
		return this.link;
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
	public Provider(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		link = jsonObject.optString("link");
		name = jsonObject.optString("name");
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("link", link);
			jsonObject.put("name", name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}