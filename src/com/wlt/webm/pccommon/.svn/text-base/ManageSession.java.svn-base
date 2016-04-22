package com.wlt.webm.pccommon;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ManageSession implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		HttpSession session=arg0.getSession();
		ServletContext application=session.getServletContext();
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session=arg0.getSession();
		ServletContext application=session.getServletContext();
		System.out.println("Ïú»Ùsession"+session);
	}

}
