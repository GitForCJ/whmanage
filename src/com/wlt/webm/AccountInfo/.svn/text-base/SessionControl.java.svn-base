package com.wlt.webm.AccountInfo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class SessionControl {
	private static SessionControl control=null;
	
	public Map<String,HttpSession> maps=Collections.synchronizedMap(new HashMap<String,HttpSession>());
	
	private SessionControl(){}

	public static SessionControl getControl() {
		if(control==null){
			control=new SessionControl();
		}
		return control;
	}
}
