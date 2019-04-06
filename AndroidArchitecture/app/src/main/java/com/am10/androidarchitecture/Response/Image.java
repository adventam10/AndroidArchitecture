package com.am10.androidarchitecture.Response;
//
//	Image.java
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import org.json.*;

import java.io.Serializable;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class Image implements Serializable {

	@SerializedName("height")
	private int height;
	@SerializedName("link")
	private String link;
	@SerializedName("title")
	private String title;
	@SerializedName("url")
	private String url;
	@SerializedName("width")
	private int width;

	public void setHeight(int height){
		this.height = height;
	}
	public int getHeight(){
		return this.height;
	}
	public void setLink(String link){
		this.link = link;
	}
	public String getLink(){
		return this.link;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return this.title;
	}
	public void setUrl(String url){
		this.url = url;
	}
	public String getUrl(){
		return this.url;
	}
	public void setWidth(int width){
		this.width = width;
	}
	public int getWidth(){
		return this.width;
	}

	/**
	 * Instantiate the instance using the passed jsonObject to set the properties values
	 */
	public Image(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		height = jsonObject.optInt("height");
		link = jsonObject.optString("link");
		title = jsonObject.optString("title");
		url = jsonObject.optString("url");
		width = jsonObject.optInt("width");
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("height", height);
			jsonObject.put("link", link);
			jsonObject.put("title", title);
			jsonObject.put("url", url);
			jsonObject.put("width", width);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}