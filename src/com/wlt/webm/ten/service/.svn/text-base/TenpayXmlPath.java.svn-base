package com.wlt.webm.ten.service;

import com.wlt.webm.pccommon.ProjectContext;

/**
 * 获取tenpay配置文件路径类<br>
 */
public class TenpayXmlPath {
	/**
	 * 初始化
	 */
	private static TenpayConfigParser parser = null;
	/**
	 * 获取tenpay配置文件路径
	 */
	public static final String VOICE_PC = ProjectContext.getInstance().getInvoiceConfigFile();
//	public static final String VOICE_PC =  System.getProperty("user.dir")+File.separator+"Tenpay.xml";
	
	/**
	 * 构造方法
	 */
	public TenpayXmlPath(){
		try {
			parser = new TenpayConfigParser(VOICE_PC);
			parser.parse();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
