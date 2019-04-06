package com.am10.androidarchitecture.Response;
//
//	Copyright.java
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import org.json.*;

import java.io.Serializable;
import java.util.*;
import com.google.gson.annotations.SerializedName;


public class Copyright implements Serializable {

	@SerializedName("image")
	private Image image;
	@SerializedName("link")
	private String link;
	@SerializedName("provider")
	private Provider[] provider;
	@SerializedName("title")
	private String title;

	public void setImage(Image image){
		this.image = image;
	}
	public Image getImage(){
		return this.image;
	}
	public void setLink(String link){
		this.link = link;
	}
	public String getLink(){
		return this.link;
	}
	public void setProvider(Provider[] provider){
		this.provider = provider;
	}
	public Provider[] getProvider(){
		return this.provider;
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
	public Copyright(JSONObject jsonObject){
		if(jsonObject == null){
			return;
		}
		image = new Image(jsonObject.optJSONObject("image"));
		link = jsonObject.optString("link");
		JSONArray providerJsonArray = jsonObject.optJSONArray("provider");
		if(providerJsonArray != null){
			ArrayList<Provider> providerArrayList = new ArrayList<>();
			for (int i = 0; i < providerJsonArray.length(); i++) {
				JSONObject providerObject = providerJsonArray.optJSONObject(i);
				providerArrayList.add(new Provider(providerObject));
			}
			provider = (Provider[]) providerArrayList.toArray();
		}		title = jsonObject.optString("title");
	}

	/**
	 * Returns all the available property values in the form of JSONObject instance where the key is the approperiate json key and the value is the value of the corresponding field
	 */
	public JSONObject toJsonObject()
	{
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("image", image.toJsonObject());
			jsonObject.put("link", link);
			if(provider != null && provider.length > 0){
				JSONArray providerJsonArray = new JSONArray();
				for(Provider providerElement : provider){
					providerJsonArray.put(providerElement.toJsonObject());
				}
				jsonObject.put("provider", providerJsonArray);
			}
			jsonObject.put("title", title);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject;
	}

}