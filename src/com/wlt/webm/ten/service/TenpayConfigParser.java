package com.wlt.webm.ten.service;

import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.*;
import org.w3c.dom.*;
import java.io.File;

import com.commsoft.epay.util.logging.Log;
/**
 * ����XML�����ļ�����
 */
public class TenpayConfigParser {
	/**
	 * �����ļ���
	 */
	private String XML_FILE = null;

	/**
	 * 
	 */
	private DocumentBuilderFactory factory = null;

	/**
	 * 
	 */
	private DocumentBuilder builder = null;

	/**
	 * 
	 */
	private Document doc = null;

	/**
	 * 
	 */
	public static TenpayConfiguration config = null;

	/**
	 * ��ʼ��XML�ļ�
	 * 
	 * @param file
	 *            XML�ļ���
	 * @throws Exception 
	 */
	public TenpayConfigParser(String file) throws Exception {
		Log.info("��ʼ��ȡϵͳ������Ϣ");
		this.XML_FILE = file;
		factory = DocumentBuilderFactory.newInstance();
		builder = factory.newDocumentBuilder();
		doc = builder.parse(file);
	}
	/**
	 * @throws Exception
	 */
	public TenpayConfigParser() throws Exception {
	}
	/**
	 * ��ýڵ�ֵ
	 * 
	 * @param elementName
	 *            �ڵ�����
	 * @return �ڵ�ֵ
	 * @throws Exception 
	 */
	public String getElement(String elementName) throws Exception {
		return doc.getElementsByTagName(elementName).item(0).getFirstChild()
				.getNodeValue();
	}

	/**
	 * �޸�XML�ļ��ڵ�
	 * 
	 * @param elementName
	 *            �ڵ�����
	 * @param elementValue 
	 */
	public void updateElement(String elementName, String elementValue) {
		try {
			doc.getElementsByTagName(elementName).item(0).getFirstChild()
					.setNodeValue(elementValue);
		} catch (Exception e) {
			//ErrLog.log(e);
		}
	}

	/**
	 * ���޸ĵ������ύ��XML�ļ�
	 */
	public void commit() {
		try {
			File file = new File(XML_FILE);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer tr = tf.newTransformer();
			DOMSource ds = new DOMSource(doc);
			StreamResult sr = new StreamResult(file);
			tr.transform(ds, sr);
		} catch (Exception e) {
		}
	}

	/**
	 * �������ܣ���ȡXML�����ĵ�ת���Ķ���
	 * 
	 * @return �����ĵ�����
	 */
	public static final TenpayConfiguration getConfig() {
		return config;
	}

	/**
	 * ��XML�ļ�������д��config��
	 * @throws Exception 
	 */
	public void parse() throws Exception {
		config = new TenpayConfiguration();
		config.setBargainor_id(getElement("bargainor_id"));
		config.setKey((getElement("key")));
		config.setReturn_url(getElement("return_url"));
		config.setShow_url(getElement("show_url"));
		config.setTask_interval(getElement("task_interval"));
		config.setXinsheng_id(getElement("xinsheng_id"));
		config.setPkey(getElement("pkey"));
		config.setXsreturn_url(getElement("xsreturn_url"));
		config.setXsnotice_url(getElement("xsnotice_url"));
		config.setXsip(getElement("xsip"));
		config.setQbid(getElement("qb_id"));
		config.setQbsign(getElement("qb_sign"));
		config.setQburl(getElement("qb_url"));
		config.setQbshow(getElement("qb_show"));
		config.setQbhost(getElement("qb_host"));
		Log.info("ϵͳ������Ϣ��ȡ����");
	}
}


