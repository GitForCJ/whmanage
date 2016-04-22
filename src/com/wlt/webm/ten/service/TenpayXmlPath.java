package com.wlt.webm.ten.service;

import com.wlt.webm.pccommon.ProjectContext;

/**
 * ��ȡtenpay�����ļ�·����<br>
 */
public class TenpayXmlPath {
	/**
	 * ��ʼ��
	 */
	private static TenpayConfigParser parser = null;
	/**
	 * ��ȡtenpay�����ļ�·��
	 */
	public static final String VOICE_PC = ProjectContext.getInstance().getInvoiceConfigFile();
//	public static final String VOICE_PC =  System.getProperty("user.dir")+File.separator+"Tenpay.xml";
	
	/**
	 * ���췽��
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
