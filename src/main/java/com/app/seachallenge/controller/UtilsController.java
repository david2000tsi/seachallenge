package com.app.seachallenge.controller;

import org.json.JSONObject;

public class UtilsController {
	
	public static String getMsgJson(int code, String message) {
		String result = "";
		try {
			JSONObject error = new JSONObject();
			error.put("code", code);
			error.put("message", message);
			result = error.toString();
		}
		catch(Exception e) {
			e.printStackTrace();
			result = "NO_MSG";
		}
		return result;
	}
}
