package com.wlt.webm.business.bean.unicom3gquery.xml;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.thoughtworks.xstream.XStream;
import com.wlt.webm.business.Car.bean.CarInfo;
import com.wlt.webm.business.Car.bean.WzList;
import com.wlt.webm.business.bean.beijingFlow.Flow;
import com.wlt.webm.business.bean.sikong.Flow_SiKong;
import com.wlt.webm.business.bean.unicom3gquery.FillProductDetail;
import com.wlt.webm.business.bean.unicom3gquery.FillProductInfo;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.db.DBService;

/**
 * 
 * @author 1989
 *
 */
public class JavaOpXml {
	
	/**
	 * java对象转成xml
	 * @param obj 
	 * @param type 
	 * @param root 根目录
	 * @return
	 */
	public static String java2Xml(Object obj,Class type,String root){
		 XStream xstream = new XStream(); 
//	        xstream.alias("MsgBody", RequestMsgBody.class); 
//	        xstream.alias("MsgHeader", RequestMsgHeader.class); 
	        xstream.alias(root, type);
	        String xml = xstream.toXML(obj); 
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"+xml;
		
	}
	
	public static Object xml2Java(String xml,Class type,String root){
		 XStream xstream = new XStream(); 
		 xstream.alias(root, type);
		return xstream.fromXML(xml);
	}
	
	/**
	 * 构造流量查询返回数据
	 * @param ph
	 * @param code
	 * @param msg
	 * @param PackageId
	 * @param PackageName
	 * @param PackageSubname
	 * @param UnitName
	 * @param CumulationTotal
	 * @param CumulationLeft
	 * @param start
	 * @param end
	 * @return
	 */
	public static String string2xml(String ph,String code,String msg,String PackageId
			,String PackageName,String PackageSubname,String UnitName,
			String CumulationTotal,String CumulationLeft,String start,String end){
		String rs="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
		"<Response><MsgHeader><Mobile>"+ph+"</Mobile><RetCode>"+
		code+"</RetCode><Retmsg>"+msg+"</Retmsg></MsgHeader><MsgBody><PackageInfoList><PackageId>"+
		PackageId+"</PackageId><PackageName>"+PackageName+"</PackageName><PackageSubname>"+
		PackageSubname+"</PackageSubname><UnitName>"+UnitName+"</UnitName><CumulationTotal>"+
		CumulationTotal+"</CumulationTotal><CumulationLeft>"+CumulationLeft+
		"</CumulationLeft><StartTime>"+start+"</StartTime><EndTime>"+end
		+"/<EndTime></PackageInfoList></MsgBody></Response>";
		return rs;
	}
	

    /**
     * 腾讯流量订购返回数据
     * @param ph
     * @param code
     * @param msg
     * @param TransId
     * @return xml
     */
	public static String flowOrder2xml(String ph,String code,String msg,String TransId){
		String rs="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
		"<Response><MsgHeader><Mobile>"+ph+"</Mobile><RetCode>"+
		code+"</RetCode><Retmsg>"+msg+"</Retmsg></MsgHeader><MsgBody><TransId>"+
		TransId+"</TransId></MsgBody></Response>";
		return rs;
	}
	
	/**
	 * 腾讯流量订购
	 * @param PackageId
	 * @param userNo
	 * @param login
	 * @param userFacct
	 * @param time
	 * @param phone
	 * @param transId
	 * @return   0成功 2系统异常 1 未知错误 3 面值不存在 -1失败
	 */
	public static int tencentFlowOrder(String PackageId,String userNo,
			String login,String userFacct,String time,String phone,String transId){
		int n=-1;
		DBService db=null;
		try{
			String type=PackageId.substring(0,1);
			String price=PackageId.substring(1);
			String tradetype="";
			if(type.equals("0")){
				tradetype="0011";
			}else if(type.equals("1")){
				tradetype="0010";
			}else{
				tradetype="0009";
			}
		db=new DBService();
		String pid=db.getString("SELECT province_code FROM sys_phone_area WHERE phone_num='"+phone.substring(0,7)+"'");
		String interfaceid="SELECT flow_interId FROM sys_flow WHERE operator_type="+type+"  AND exp1="+price;
		String orderid=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+(int) (Math.random() * (1000 - 100) + 100);
		Object[] orderObj = { null, "0755",orderid, phone, interfaceid,
				tradetype, price,
				price, userFacct + "01", time, time,
				4, PackageId, transId, 3, userNo, login,
				type, pid, null };
		db.logData(20, orderObj, "wht_orderform_" + time.substring(2, 6));
		if(HttpFillOP.FLOw1.equals(interfaceid)){//宽带公司
		boolean flag=false;
		String productId="";
		String realPrice="0";
		FillProductInfo infos=HttpFillOP.filterProduct(phone, HttpFillOP.phoneAreas.get(pid), HttpFillOP.ACTIVITYID);
		ArrayList<FillProductDetail> list=(ArrayList<FillProductDetail>)infos.getData().getList();
		for(FillProductDetail detail:list){
			if((price+"MB").equals(detail.getProduct_name())){
				flag=true;
				productId=detail.getProduct_id();
				realPrice=detail.getPrice_num()+"";
				break;
			}
		}
		if(!flag){
			n=3;
		}else{//0成功 -1失败 2异常 1未知状态
			n=HttpFillOP.fillProduct(orderid, productId, phone, Long.parseLong(realPrice),  HttpFillOP.ACTIVITYID);
		}
		}else if(interfaceid.equals(HttpFillOP.FLOw2)){//北京联通
			String productId="";//G00050#G00200#G00020#G00100#G00500#
			if(price.equals("20")){
				productId="G00020";
			}else if(price.equals("50")){
				productId="G00050";
			}else if(price.equals("100")){
				productId="G00100";
			}else if(price.equals("200")){
				productId="G00200";
			}else if(price.equals("500")){
				productId="G00500";
			}else{
				return 3;
			}
			String sa=Flow.BeiJin_Flow(phone,productId);
			String[] strings=sa.split("#");
			n=Integer.parseInt(strings[0]);
		}else if(interfaceid.equals(HttpFillOP.FLOW3.equals(interfaceid))
				||HttpFillOP.FLOW4.equals(interfaceid)||HttpFillOP.FLOW5.equals(interfaceid)){//司空 
			// 0成功  -1失败  2处理中  3用户余额不足
			n=Integer.parseInt(Flow_SiKong.Recharge(orderid, phone,price+"M"));
			if(n==0||n==2){
				n=0;
			}else{
				n=-1;
			}
		}
		}catch (Exception e) {
			
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
		return n;
	}
	
	public static void main(String[] args) {
		/* 测试请求
		RequestMsgHeader header=new RequestMsgHeader();
		header.setApiName("apiname");
		header.setAppId("123456");
		header.setFormat("xml");
		header.setRandomValue("00000000000");
		header.setSign("ahodhwojdpwpdkwk12ww");
		header.setTimeStamp("20140505");
		header.setTransId("11111111");
		header.setVersion("1.0");
		RequestMsgBody body=new RequestMsgBody();
		body.setMobile("18682033916");
		FlowRequest request=new FlowRequest();
		request.setMsgHeader(header);
		request.setMsgBody(body);
		String ok=java2Xml(request);
		System.out.println(ok);
		
		System.out.println("=================");
		FlowRequest rs=(FlowRequest)xml2Java(ok);
		System.out.println(rs.getMsgBody().getMobile());
		System.out.println(rs.getMsgHeader().getApiName());
		*/
		/*测试返回
		ResMsgHeader header=new ResMsgHeader();
		header.setMobile("18682033916");
		header.setRetCode("0000");
		header.setRetmsg("SUCCESS");
		
		ResBody body=new ResBody();
		body.setCumulationLeft("剩余");
		body.setCumulationTotal("总量");
		body.setEndTime("20150531");
		body.setStartTime("20150501");
		body.setPackageId("123");
		body.setPackageName("套餐名字");
		body.setPackageSubname("套餐内容细项名");
		body.setUnitName("单位");
		
		ResMsgBody resbody=new ResMsgBody();
		resbody.setPackageInfoList(body);
		FlowResponse flowres=new FlowResponse();
		flowres.setMsgHeader(header);
		flowres.setMsgBody(resbody);
		String ok=java2Xml(flowres,FlowResponse.class,"Response");
		System.out.println(ok);
		FlowResponse  res1=(FlowResponse) xml2Java(ok, FlowResponse.class, "Response");
		System.out.println(res1.getMsgHeader().getMobile());
		System.out.println(res1.getMsgBody().getPackageInfoList().getPackageName());
		*/
		CarInfo info=new CarInfo();
		info.setCar_code("000");
		ArrayList<WzList> a=new ArrayList<WzList>();
		WzList c=new WzList();
		c.setAddress("何必");
		a.add(c );
		info.setData(a);
		System.out.println(java2Xml(info,CarInfo.class,"CarInfo"));
	}

}
