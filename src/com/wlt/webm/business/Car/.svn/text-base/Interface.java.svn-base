package com.wlt.webm.business.Car;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.Car.bean.CarInfo;
import com.wlt.webm.business.bean.baibang.BaiBean;
import com.wlt.webm.business.bean.baibang.Md5AndBase64;
import com.wlt.webm.business.bean.baibang.XmlHead;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.MD5;

/**
 * @author admin
 */
public class Interface {
	
	/**
	 * 百事帮 交通罚款查询
	 * @param catUserName 车主姓名
	 * @param plateNumberType 车辆类型
	 * @param carFrameNum  车架号
	 * @param engineNumber 发动机号
	 * @param plateNumber 车牌号
	 * @return List<Map>
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public static List<HashMap> Query(String catUserName,
			String plateNumberType,String carFrameNum,String engineNumber,String plateNumber) throws Exception{
		//百世邦
		//组装加密 queryText 的值 
//		将QueryText应包含的字段组成源串A（不必区分排列顺序），字段之间以&分隔，参数名字和参数值之间用=分隔，样式如下：
//		Vehicle=粤A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李
		StringBuffer A=new StringBuffer();
		A.append("Vehicle="+plateNumber);
		A.append("&VehicleType="+plateNumberType);
		A.append("&FrameNo="+carFrameNum);
		A.append("&EngineNo="+engineNumber);
		A.append("&OwnerName="+catUserName);
		A.append("&Limit=100");
		A.append("&Offset=100");
//		在A后加上md5的key（key由财付通提供）得到串B，样式如下：
//		Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李&Md5Key=123456
		String B=A.toString()+"&Md5Key="+Md5AndBase64.KEY;
		//对B做md5得到Md5Sign（大写） 
		String Md5Sign=MD5.encode(B).toUpperCase();
//		将Md5Sign缀到A后面得到串C，样式如下：
//		Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李&Md5Sign=FD0CF5B71C3D3A6D651078A0B568390F
		String c=A.toString()+"&Md5Sign="+Md5Sign;
		//对C串做BASE64加密得到D（使用GB2312）
		String D=Md5AndBase64.base64EN(c,"GB2312");
		
		//组装 加密 sign 的值
		//字段按照字段名进行字典升序排列
		//在s1后面追加& Md5Key=xxxxxxxxxxxxx得到签名源串s2，对s2做MD5得到Sign的值   MD5统一采用大写
		XmlHead head=new XmlHead("","");
		StringBuffer s2=new StringBuffer();
		s2.append("Attach="+head.getAttach());
		s2.append("&CftMerId="+head.getCftMerId());
		s2.append("&Command="+head.getCommand());
		s2.append("&MerchantId="+head.getMerchantId());
		s2.append("&QueryText="+D);
		s2.append("&ResFormat="+head.getResFormat());
		s2.append("&UserId="+head.getUserId());
		s2.append("&Version="+head.getVersion());
		s2.append("&Md5Key="+Md5AndBase64.KEY);
		String sign=MD5.encode(s2.toString()).toUpperCase();
		
		BaiBean b=new BaiBean();
		//拼接 http url 
		StringBuffer buffer=new StringBuffer();
		buffer.append(b.QUERY);//url
		buffer.append("Attach="+head.getAttach());
		buffer.append("&CftMerId="+head.getCftMerId());
		buffer.append("&Command="+head.getCommand());
		buffer.append("&MerchantId="+head.getMerchantId());
		buffer.append("&QueryText="+URLEncoder.encode(D,"GB2312"));
		buffer.append("&ResFormat="+head.getResFormat());
		buffer.append("&UserId="+head.getUserId());
		buffer.append("&Version="+head.getVersion());
		buffer.append("&Sign="+sign);
		
		//处理百世邦 业务逻辑
		String result=b.sendMsgRequest(buffer.toString());
		if(result==null || "".equals(result) ){
			//本次查询返回的违章条数
			return null;
		}
		Document docResult=DocumentHelper.parseText(result);
		Element rootResult = docResult.getRootElement();
		//获取 指定节点
		Element respText=rootResult.element("RespText");
		if(respText==null || "".equals(respText) ){
			//本次查询返回的违章条数
			return null;
		}
		
		String respTextXML=Md5AndBase64.base64DE(respText.getText(),"GB2312");
		String[] ssa=respTextXML.split("&");
		
		//本次查询返回的违章条数;
		String RetNum=ssa[0].substring(ssa[0].indexOf("RetNum=")+"RetNum=".length(),ssa[0].length());
		if(RetNum==null || "".equals(RetNum) || Integer.parseInt(RetNum)<=0){
			//本次查询返回的违章条数
			return null;
		}
		
		//解密指定字符串
		String Records=ssa[2].substring(ssa[2].indexOf("Records=")+"Records=".length(),ssa[2].length());
		Records=URLDecoder.decode(Records,"GB2312");
		//拼接成 xml 格式字符串
		String Test = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
		"<root>" +
			Records+
		"</root>";
		//解析指定的   xml  字符串
		Document doc=DocumentHelper.parseText(Test);
		Element rootss = doc.getRootElement();
		Element RecordsXML=rootss.element("Records");
		List<HashMap> arr=null;
		List<Element> ras=RecordsXML.elements();
		if(ras!=null){
			arr=new ArrayList<HashMap>();
			for(Element aa:ras){
				HashMap map=new HashMap();
				List<Element> records=aa.elements();
				for(Element rr:records){
					map.put(rr.getName(),rr.getText());
				}
				arr.add(map);
			}
		}
		return arr;
	}
	
	/**
	 * 百事帮交通罚款  预查询
	 * @param inString  代办商侧的违章ID，多个ID以“|”分隔
	 * @param oldMoney 总费用（单位为分），含邮费
	 * @param car 车辆信息
	 * @return HashMap<String,String>
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static HashMap<String,String> PreQuery(String inString,float oldMoney,CarInfo car) throws Exception{
		//财付通订单号
		String CftTransId="baishi"+Tools.getNow3()+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+100);
		//订单日期，
		String Datetime=Tools.getNewDate();
		//代办商侧的违章ID，多个ID以“|”分隔
		String ViolationId=inString;
		//是否邮寄回执：0，不邮寄；1，邮寄；2，扫描单据；3，保留单据，打包邮寄。
		String MailReceipt="0";
		//1 -- RMB
		String FeeType="1";
		//总费用（单位为分），含邮费
		String TotalFee=(int)oldMoney+"";
		//用户联系方式，建议是手机
		String MobileNo=car.getCar_userphone();
		//发动机号后4位或是后6位
		String EngineNo=car.getCar_engine().substring(car.getCar_engine().length()-4,car.getCar_engine().length());
		//拼接 queryText 字符串
		String queryText="CftTransId="+CftTransId+"&Datetime="+Datetime+"&ViolationId="+ViolationId+"&MailReceipt="+MailReceipt+"&FeeType="+FeeType+"&TotalFee="+TotalFee+"&MobileNo="+MobileNo+"&EngineNo="+EngineNo;
		
//		在A后加上md5的key（key由财付通提供）得到串B，样式如下：
//		Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李&Md5Key=123456
		String B=queryText.toString()+"&Md5Key="+Md5AndBase64.KEY;
		//对B做md5得到Md5Sign（大写） 
		String Md5Sign=MD5.encode(B).toUpperCase();
//		将Md5Sign缀到A后面得到串C，样式如下：
//		Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李&Md5Sign=FD0CF5B71C3D3A6D651078A0B568390F
		String c=queryText.toString()+"&Md5Sign="+Md5Sign;
		//对C串做BASE64加密得到D（使用GB2312）
		String D=Md5AndBase64.base64EN(c,"GB2312");
		
		//组装 加密 sign 的值
		XmlHead head=new XmlHead("","");
		StringBuffer buf=new StringBuffer();
		buf.append("Attach="+head.getAttach());
		buf.append("&CftMerId="+head.getCftMerId());
		buf.append("&Command="+head.getCommand());
		buf.append("&MerchantId="+head.getMerchantId());
		buf.append("&QueryText="+D);
		buf.append("&ResFormat="+head.getResFormat());
		buf.append("&UserId="+head.getUserId());
		buf.append("&Version="+head.getVersion());
		buf.append("&Md5Key="+Md5AndBase64.KEY);
		String sign=MD5.encode(buf.toString()).toUpperCase();
		
		BaiBean b=new BaiBean();
		//拼接 http url 
		StringBuffer buffer=new StringBuffer();
		buffer.append(b.PREQUERY);//url
		buffer.append("&Attach="+head.getAttach());
		buffer.append("&CftMerId="+head.getCftMerId());
		buffer.append("&Command="+head.getCommand());
		buffer.append("&MerchantId="+head.getMerchantId());
		buffer.append("&QueryText="+URLEncoder.encode(D,"GB2312"));
		buffer.append("&ResFormat="+head.getResFormat());
		buffer.append("&UserId="+head.getUserId());
		buffer.append("&Version="+head.getVersion());
		buffer.append("&Sign="+sign);
		
		//处理百世邦 业务逻辑
		
		String result=b.sendMsgRequest(buffer.toString());
		Document docResult=DocumentHelper.parseText(result);
		Element root = docResult.getRootElement();
		//预查询返回状态
		String resCode=root.element("SpRetCode").getText();
		//预查询返回可下单 违章 id
		String resWzid=root.element("ViolationId").getText();
		if(resCode==null || "".equals(resCode) || !"0000".equals(resCode) || resWzid==null || "".equals(resWzid)){
			return null;
		}
		//预查询 返回的   商户侧订单号
		String SpTransIds=root.element("SpTransId").getText();
		
		HashMap<String,String> map=new HashMap<String,String>();
		map.put("ViolationId", resWzid);
		map.put("SpTransId", SpTransIds);
		return map;
	}

	/**百世邦 下单
	 * @param dealserial  订单号
	 * @param SpTransId  商户订单号
	 * @param ViolationId  代办商侧的违章ID，多个ID以“|”分隔
	 * @param TotalFee  总费用（单位为分，包括邮寄费），商户侧需要根据违章ID来核实金额
	 * @return int 1失败   0成功 
	 * @throws Exception 
	 */
	@SuppressWarnings("unused")
	public static int PlaceOrder(String dealserial,String SpTransId,String ViolationId,float TotalFee) throws Exception{
		String queryText="CftTransId="+dealserial+"&SpTransId="+SpTransId+"&Datetime="+Tools.getNewDate()+"&ViolationId="+ViolationId+"&MailReceipt=0&FeeType=1&TotalFee="+(int)TotalFee+"";
//			在A后加上md5的key（key由财付通提供）得到串B，样式如下：
//			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李&Md5Key=123456
		String B=queryText.toString()+"&Md5Key="+Md5AndBase64.KEY;
		//对B做md5得到Md5Sign（大写） 
		String Md5Sign=MD5.encode(B).toUpperCase();
//			将Md5Sign缀到A后面得到串C，样式如下：
//			Vehicle=A12345&VehicleType=1&FrameNo=554545&EngineNo=21555&OwnerName=小李&Md5Sign=FD0CF5B71C3D3A6D651078A0B568390F
		String c=queryText.toString()+"&Md5Sign="+Md5Sign;
		//对C串做BASE64加密得到D（使用GB2312）
		String D=Md5AndBase64.base64EN(c,"GB2312");
		
		//组装 加密 sign 的值
		XmlHead head=new XmlHead("","");
		StringBuffer buf=new StringBuffer();
		buf.append("Attach="+head.getAttach());
		buf.append("&CftMerId="+head.getCftMerId());
		buf.append("&Command="+head.getCommand());
		buf.append("&MerchantId="+head.getMerchantId());
		buf.append("&QueryText="+D);
		buf.append("&ResFormat="+head.getResFormat());
		buf.append("&UserId="+head.getUserId());
		buf.append("&Version="+head.getVersion());
		buf.append("&Md5Key="+Md5AndBase64.KEY);
		String sign=MD5.encode(buf.toString()).toUpperCase();
		
		BaiBean b=new BaiBean();
		//拼接 http url 
		StringBuffer buffer=new StringBuffer();
		buffer.append(b.SUBMIT);//url
		buffer.append("&Attach="+head.getAttach());
		buffer.append("&CftMerId="+head.getCftMerId());
		buffer.append("&Command="+head.getCommand());
		buffer.append("&MerchantId="+head.getMerchantId());
		buffer.append("&QueryText="+URLEncoder.encode(D,"GB2312"));
		buffer.append("&ResFormat="+head.getResFormat());
		buffer.append("&UserId="+head.getUserId());
		buffer.append("&Version="+head.getVersion());
		buffer.append("&Sign="+sign);
		
		String result=b.sendMsgRequest(buffer.toString());
		Log.info("百事帮交通罚款下单响应字符串result:"+result);
		Document docResult=DocumentHelper.parseText(result);
		Element root = docResult.getRootElement();
		String resCode=root.element("SpRetCode").getText();
		Log.info("百事帮交通罚款下单响应状态，SpRetCode="+resCode);
		if("0000".equals(resCode)){
			return 0;
		}else{
			return 1;
		}
	}
}
