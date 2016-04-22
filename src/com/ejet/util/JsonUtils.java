package com.ejet.util;

import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {


	public static String getInformationMsg(String msg, boolean isEncode){
		String str = "";
		try {
			str = getMsg("RESULT", msg, isEncode);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String getMsg(String msgType, String msg, boolean isEncode) throws JSONException{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(msgType, msg);
		String str=null;
		if(isEncode){
			str = java.net.URLDecoder.decode(msg);
		}else
		{
			str=msg;
		}
		return str;
	}
}
