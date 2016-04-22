package com.wlt.webm.ten.service;

import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.*;
import org.w3c.dom.*;
import java.io.File;

import com.commsoft.epay.util.logging.Log;
/**
 * 操作XML配置文件数据
 */
public class TenpayConfigParser {
	/**
	 * 配置文件名
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
	 * 初始化XML文件
	 * 
	 * @param file
	 *            XML文件名
	 * @throws Exception 
	 */
	public TenpayConfigParser(String file) throws Exception {
		Log.info("开始读取系统配置信息");
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
	 * 获得节点值
	 * 
	 * @param elementName
	 *            节点名称
	 * @return 节点值
	 * @throws Exception 
	 */
	public String getElement(String elementName) throws Exception {
		return doc.getElementsByTagName(elementName).item(0).getFirstChild()
				.getNodeValue();
	}

	/**
	 * 修改XML文件节点
	 * 
	 * @param elementName
	 *            节点名称
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
	 * 将修改的内容提交到XML文件
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
	 * 函数功能：获取XML配置文档转化的对象
	 * 
	 * @return 配置文档对象
	 */
	public static final TenpayConfiguration getConfig() {
		return config;
	}

	/**
	 * 将XML文件的内容写入config中
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
		Log.info("系统配置信息读取结束");
	}
}


