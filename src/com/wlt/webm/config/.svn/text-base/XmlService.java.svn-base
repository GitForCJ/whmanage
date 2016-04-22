package com.wlt.webm.config;

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.commsoft.epay.util.logging.Log;
/**
 * <p>Title:电子代办平台</p>
 * <p>Description: XML文件解析，方法封装类</p>
 * <p>create: 2010-12-25</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 深圳市万恒科技有限公司</p>
 * @author shenyijie
 * @version 3.0.0.0
 * 
 */
public class XmlService {

	/**
	 * 配置文件名
	 */
	public static final String xmsFile = System.getProperty("user.dir")
			+ File.separator + "epay_uic.xml";

	/**
	 * DOM解析器
	 */
	private DocumentBuilderFactory factory = null;

	private DocumentBuilder builder = null;

	private Document doc = null;

	/**
	 * 配置信息存储类
	 */
	private static XmlService xmlSer = null;
	/**
	 * 初始化XML文件
	 */
	public  XmlService() {
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			doc = builder.parse(xmsFile);		
		} catch (Exception e) {
			Log.error(e);
		}
	}

	/**
	 * 获得节点值
	 * 
	 * @param elementName
	 *            节点名称
	 * @return String 节点值
	 */
	public String getElement(String elementName) {
		try {
			return doc.getElementsByTagName(elementName).item(0)
					.getFirstChild().getNodeValue();
		} catch (Exception ex) {
			Log.error(ex);
			return null;
		}
	}

	/**
	 * 获取节点名下的非文本节点的子节点名
	 * 
	 * @param elementName
	 *            节点名
	 * @return String[] 子节点名
	 */
	public String[] getNodeName(String elementName) {
		try {
			NodeList nodeList = doc.getElementsByTagName(elementName).item(0)
					.getChildNodes();
			int n = nodeList.getLength();

			Vector v = new Vector();
			int nodeNum = 0;

			for (int i = 0; i < n; i++) {
				if (nodeList.item(i).getNodeType() == 1) {
					v.add(nodeList.item(i).getNodeName());
					nodeNum++;
				}
			}

			String nodeName[] = new String[nodeNum];
			for (int i = 0; i < nodeNum; i++) {
				nodeName[i] = (String) v.get(i);
			}

			return nodeName;
		} catch (Exception ex) {
			Log.error(ex);
			return null;
		}
	}

	/**
	 * 根据父节点，找到子节点和其属性的数值一致的该子节点名
	 * 
	 * @param nodeName
	 *            父节点名称
	 * @param attrSrc
	 *            子节点的比较属性
	 * @param attrSrcValue
	 *            子节点的比较属性的数值
	 * @return String 子节点名
	 */
	public String getSonNodeName(String nodeName, String attrSrc,
			String attrSrcValue) {
		try {
			String sonNodeName[] = this.getNodeName(nodeName);
			int n = sonNodeName.length;
			for (int i = 0; i < n; i++) {
				if (this.getNodeAtrr(sonNodeName[i], attrSrc).trim().equals(
						attrSrcValue)) {

					return sonNodeName[i];
				}
			}

		} catch (Exception ex) {
			Log.info("读取XML文件出错。"+ex);
			Log.error("读取XML文件出错。"+ex);
		}

		return null;
	}

	/**
	 * 获得子节点属性
	 * 
	 * @param nodeName父节点
	 * @param attrSrc
	 *            属性名
	 * @param attrSrcValue
	 *            属性值
	 * @param attrDes
	 *            目的属性
	 * @return String 子节点属性
	 */
	public String getSonAttr(String nodeName, String attrSrc,
			String attrSrcValue, String attrDes) {
		String nodeNameTmp = this.getSonNodeName(nodeName, attrSrc,
				attrSrcValue);
		if (nodeNameTmp == null) {
			return null;
		}

		return this.getNodeAtrr(nodeNameTmp, attrDes);
	}

	/**
	 * 返回节点属性值
	 * 
	 * @param strNode
	 *            节点名
	 * @param strAttr
	 *            节点属性名
	 * @return String 节点属性值
	 */
	public String getNodeAtrr(String strNode, String strAttr) {

		try {
			Element nodeTmp = (Element) doc.getElementsByTagName(strNode).item(0);
			if (nodeTmp == null) {
				throw new Exception("无此节点");
			}

			return ((Element) nodeTmp).getAttribute(strAttr);
		} catch (Exception ex) {
			Log.error(ex);
			return null;
		}

	}

	/**
	 * 返回父节点属性值
	 * 
	 * @param strNode
	 *            节点名
	 * @param strAttr
	 *            节点属性名
	 * @return String 父节点属性值
	 */
	public String getFatherNodeAtrr(String strNode, String strAttr) {

		try {
			Element nodeTmp = (Element) doc.getElementsByTagName(strNode).item(0).getParentNode();
			if (nodeTmp == null) {
				throw new Exception("无此节点");
			}

			return ((Element) nodeTmp).getAttribute(strAttr);
		} catch (Exception ex) {
			Log.error(ex);
			return null;
		}

	}

	/**
	 * 函数功能：获取XML配置文档转化的对象
	 * 
	 * @return ConfigBean 配置文档对象
	 */
	public static synchronized XmlService getXmlService() {
		if (xmlSer == null) {
			xmlSer = new XmlService();
		}
		return xmlSer;
	}
	
}
