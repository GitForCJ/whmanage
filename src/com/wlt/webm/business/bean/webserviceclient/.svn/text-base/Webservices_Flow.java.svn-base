package com.wlt.webm.business.bean.webserviceclient;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import com.commsoft.epay.util.logging.Log;
import com.thoughtworks.xstream.XStream;

public class Webservices_Flow {
	
	private static final String AdminName="admin";
	private static final String Password=com.wlt.webm.tool.MD5.encode("SZWANHENGKEJI");
	private static final String CA="19YZTEJB";//"SZWANHENGKEJI";
	
	private static Map<String,String> map=null;
	static{
		map=new HashMap<String, String>();
		map.put("10_mb","10");
		map.put("30_mb","11");
		map.put("70_mb","12");
		map.put("150_mb","13");
		map.put("500_mb","18");
		map.put("1024_mb","19");
		map.put("2048_mb","20");
		map.put("3072_mb","21");
		map.put("4096_mb","22");
		map.put("6144_mb","23");
		map.put("11264_mb","24");
	}
	
	/**
	 * @param args
	 * @throws ServiceException 
	 * @throws RemoteException 
	 */
	public static void main(String[] args) {
		String[] phones={"13620970251","13538110856","15919437676","13751008102"
				,"15015939337","15889177789","13420009368","15828139611","13580158196","13500104632","13763221546",
				"15012925946","15016305964","15016303259","13684959211"};
		for(String phone:phones)
			System.out.println(flow_Result("22223333"+(int)(Math.random()*10000),phone,"30"));
	}
	
	/**
	 * 流量充值
	 * @param SerialNo 流水号
	 * @param MobNum 充值号码
	 * @param mm 多少兆 50M 就填 50
	 * @return 0成功  -1失败  2异常，处理中
	 */
	public static String flow_Result(String SerialNo,String MobNum,String mm){
		String ResponseTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String UserPackage=map.get(mm+"_mb");//流量套餐
		if(UserPackage==null || "".equals(UserPackage)){
			Log.info("北京移动流量充值,,订单号："+SerialNo+",,流量包不匹配,return 1 失败");
			return "-1#-1";
		}
		Tea tea = new Tea();
		String xmlParams=
		"<WebRequest>"+
			"<Header>"+
			"	<ChannelID>"+tea.encryptByTea("EDSMP")+"</ChannelID>"+
			"	<RequestTime>"+tea.encryptByTea(ResponseTime)+"</RequestTime>"+
			"	<SerialNo>"+tea.encryptByTea(SerialNo)+"</SerialNo>"+
			"</Header>"+
			"<WebBody>"+
			"	<Params>"+
			"		<CA>"+tea.encryptByTea(CA)+"</CA>"+
			"		<AdminName>"+tea.encryptByTea(AdminName)+"</AdminName>"+
			"		<Password>"+tea.encryptByTea(Password)+"</Password>"+
			"		<PhoneList>"+
			"			<PhoneInfo>"+
			"				<MobNum>"+tea.encryptByTea(MobNum)+"</MobNum>"+
			"				<UserPackage>"+tea.encryptByTea(UserPackage)+"</UserPackage>"+
			"			</PhoneInfo>"+
			"		</PhoneList>"+
			"	</Params>"+
			"</WebBody>"+
		"</WebRequest>";
		
		Log.info("北京移动流量充值,充值响应xml,,订单号："+SerialNo+",,请求xml:"+xmlParams);
		
		try{
			TFInternalServiceLocator locator=new TFInternalServiceLocator();
			String resultXML=locator.getedsmpService().tf_djbdg(xmlParams);
			if(resultXML==null || "".equals(resultXML)){
				Log.info("北京移动流量充值,充值响应xml为空，,订单号："+SerialNo+",,,return 2 处理中");
				return "2#-1";
			}
			
			Log.info("北京移动流量充值,订单号："+SerialNo+",,充值响应resultXML:"+resultXML);
			
			XStream xstream = new XStream(); 
			xstream.alias("WebResponse", ResultXmlRoot.class);
			xstream.alias("Header", Header.class);
			xstream.alias("WebBody", WebBody.class);
			xstream.alias("Params", Params.class);
			ResultXmlRoot bean = (ResultXmlRoot)xstream.fromXML(resultXML);
			
			if(bean!=null && bean.getWebBody()!=null ){
				String code=bean.getWebBody().getParams().getOperSeq()==null?"":bean.getWebBody().getParams().getOperSeq();
				if(!"".equals(code) && !"-".equals(code.substring(0,1))){
					Log.info("北京移动流量充值,订单号："+SerialNo+",提交成功,return 0,返回码:"+code);
					return "0#"+code;
				}
				if("-1000".equals(code) ||
						"-1100".equals(code)||
						"-1200".equals(code)||
						"-7777".equals(code)||
						"-8888".equals(code)||
						"-2000".equals(code)||
						"-2001".equals(code)||
						"-2002".equals(code)||
						"-2003".equals(code)||
						"-2004".equals(code)||
						"-3000".equals(code)||
						"-3001".equals(code)||
						"-3002".equals(code)||
						"-3003".equals(code)||
						"-3004".equals(code)||
						"-4000".equals(code)||
						"-4001".equals(code)||
						"-4002".equals(code)||
						"-4003".equals(code)||
						"-4004".equals(code)||
						"-4005".equals(code)||
						"-4006".equals(code)||
						"-4007".equals(code)){
					Log.info("北京移动流量充值,订单号："+SerialNo+",提交成功,return 1,返回码:"+code);
					return "-1#-1";
				}
			}
		}catch (Exception e) {
			Log.error("北京移动流量充值,系统异常,,订单号："+SerialNo+",,ex:"+e);
		}
		Log.info("北京移动流量充值,订单号："+SerialNo+",未知提交状态,reutrn 2 处理中");
		return "2#-1";
	}

// result xml 
//	<WebResponse>
//		<Header>
//			<ChannelID></ChannelID>
//			<ResponseTime></ResponseTime>
//			<SerialNo></SerialNo>
//		</Header>
//		<WebBody>
//			<Params>
//				<OperSeq></OperSeq>
//			</Params>
//		</WebBody>
//	</WebResponse>
}

