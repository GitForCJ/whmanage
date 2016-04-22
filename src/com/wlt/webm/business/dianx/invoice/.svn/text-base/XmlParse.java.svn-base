package com.wlt.webm.business.dianx.invoice;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wlt.webm.pccommon.ProjectContext;

/**
 * 读取xml配置文件节点信息<br>
 * company 深圳市万恒科技有限公司<br>
 * copyright Copyright (c) 2008<br>
 * version 3.0.0.0
 * @author 胡俊
 */
public class XmlParse {

	/**
	 * 配置文件类
	 */
	public static Configuration config = null;

	/**
	 * Invoice_PC XML配置文件
	 */
	public static final String VOICE_PC = ProjectContext.getInstance().getInvoiceConfigFile();

	/**
	 * 函数功能：获取XML配置文档转化的对象
	 * 
	 * @return 配置文档对象
	 */
	public static final Configuration getConfig() {
		return config;
	}

	/**
	 * 从NODELIST读到字符串
	 * @param list 节点列表
	 * @param tagName 节点名
	 * @param i 项
	 * @return 列表
	 */
	public String getListStr(NodeList list, String tagName, int i) {

		String listStr = "";
		try {
			// 获取节点
			Node child = ((Element) list.item(i)).getElementsByTagName(tagName)
					.item(0);
			// 如果节点不为空
			if (child != null) {
				// 节点下的子节点不为空
				if (child.getFirstChild() != null) {
					listStr = child.getFirstChild().getNodeValue();
				}
			}
		} catch (Exception e) {
		}

		return listStr.trim();
	}

	/**
	 * 解析xml文件节点信息
	 */

	public void xmlParse() {
		try {
			// DOM解析器工厂
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			// DOM解析器
			DocumentBuilder builder = factory.newDocumentBuilder();
			// 文档读取类
			Document doc = builder.parse(VOICE_PC);
			// 文档规范类
			doc.normalize();

			config = new Configuration(1);
			NodeList configList = doc.getElementsByTagName("NodeItem");
			for (int i = 0; i < configList.getLength(); i++) {

				String itemName = getListStr(configList, "NodeName", i);
				String itemXcoor = getListStr(configList, "Xcoor", i);
				String itemYcoor = getListStr(configList, "Ycoor", i);
				config.setItem(itemName, itemXcoor, itemYcoor);
			}

		} catch (Exception e) {
		}
	}

	/*
	 * public static void main(String[] args){
	 * 
	 * XmlParse xm = new XmlParse(); xm.xmlParse();
	 *  }
	 */

}
