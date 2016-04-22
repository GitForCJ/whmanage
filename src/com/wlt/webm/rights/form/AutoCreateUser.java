package com.wlt.webm.rights.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * 
 * @author 1989
 *
 */
public class AutoCreateUser extends ActionForm {
	
	/**
	 * 
	 */
	private FormFile sourcefile;
	/**
	 * 
	 */
	private String login;
	
	/**
	 * @return
	 */
	public FormFile getSourcefile() {
		return sourcefile;
	}
	/**
	 * @param sourcefile
	 */
	public void setSourcefile(FormFile sourcefile) {
		this.sourcefile = sourcefile;
	}
	/**
	 * @return
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

}
