package com.devglan.utils;

import org.json.JSONObject;
import org.json.XML;

import com.google.gson.Gson;

public class ConversionUtils {

	public static String toString(Object obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}
	
	public static String jaxbObjectToXML(String payload) {
		JSONObject json = new JSONObject(payload);
		return XML.toString(json);
	}
}
