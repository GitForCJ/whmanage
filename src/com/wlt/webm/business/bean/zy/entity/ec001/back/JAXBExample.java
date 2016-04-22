package com.wlt.webm.business.bean.zy.entity.ec001.back;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;




import javax.xml.parsers.ParserConfigurationException;







import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
public class JAXBExample {
    private static String temp="<MemberShipResponse><BODY><ECCode>2000702804</ECCode><PrdOrdNum>30730000731</PrdOrdNum><Member><Mobile>13900000001</Mobile><CRMApplyCode>2016010517013001</CRMApplyCode><ResultCode>0</ResultCode><ResultMsg>成功</ResultMsg></Member><Member><Mobile>13900000001</Mobile><CRMApplyCode>2016010517013001</CRMApplyCode><ResultCode>0</ResultCode><ResultMsg>成功</ResultMsg></Member></BODY></MemberShipResponse>";
	public static void main(String[] args) {    
		test2();     
    }
	public static void test2(){
		org.dom4j.Document doc;
		try {
			doc = (org.dom4j.Document)DocumentHelper.parseText(temp);
			org.dom4j.Element root = doc.getRootElement();  
			System.out.println("root="+root.getName());			
            org.dom4j.Element body=root.element("BODY");
            Iterator users_subElements = body.elementIterator("Member");//指定获取那个元素   
           List member=body.elements("Member");
            System.out.println("member="+"/sise="+body.elementText("srfewrrew"));
            while(users_subElements.hasNext()){
            	org.dom4j.Element e=(org.dom4j.Element)users_subElements.next();
            	List m=e.elements();
            	for(int i=0;i<m.size();i++){
             	   org.dom4j.Element mm=(org.dom4j.Element)m.get(i);
             	   System.out.println("节点:"+mm.getName()+"/text="+mm.getText());
                }
            }            
		} catch (DocumentException e) {
			e.printStackTrace();
		}   		
	}
	public static void test(){
		 //SAXReader saxReader=new SAXReader();
	     DocumentBuilderFactory factory = DocumentBuilderFactory   
	             .newInstance();   
	     DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			 Document doc = builder.parse(new InputSource(new StringReader(temp)));	
			 Element root = doc.getDocumentElement();   
			 NodeList Member=root.getElementsByTagName("Member");
			 System.out.println("root="+root.getNodeName()+"/"+root.getTextContent());
			 NodeList Members=Member.item(0).getChildNodes();
			 for(int i=0;i<Members.getLength();i++){
		          Node node=Members.item(i);
		          System.out.println("节点："+node.getNodeName()+"/text="+node.getNodeValue());
			 }
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}   
	}
}