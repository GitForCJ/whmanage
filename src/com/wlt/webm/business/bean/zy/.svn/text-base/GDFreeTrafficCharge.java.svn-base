package com.wlt.webm.business.bean.zy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.tempuri.ADCServiceForEC;
import org.tempuri.NGADCServiceForEC;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.zy.entity.ec001.EC001;
import com.wlt.webm.business.bean.zy.entity.ec001.EC001Member;
import com.wlt.webm.business.bean.zy.entity.ec001.EC001PrdList;
import com.wlt.webm.business.bean.zy.entity.ec001.EC001Service;
import com.wlt.webm.business.bean.zy.entity.ec001.back.EC001BackBody;
import com.wlt.webm.business.bean.zy.entity.ec001.back.EC001BackMember;
import com.wlt.webm.business.bean.zy.entity.ec0010.back.EC0010BackBody;
import com.wlt.webm.business.bean.zy.entity.ec0010.back.EC0010BackOneList;
import com.wlt.webm.business.bean.zy.entity.ec0014.back.EC0014BackBody;
import com.wlt.webm.business.bean.zy.entity.ec0014.back.EC0014BackOrderInfo;
import com.wlt.webm.business.bean.zy.entity.ec0014.back.ProductInfo;

import ecinterface.adc.NGEC;
import ecinterface.adc.Response;

/**
 * 广东流量自由充
 * 
 * @author lijin
 * 
 */
public class GDFreeTrafficCharge {
	private static String eccode = "2001605299";
	private static String ecname = "深圳市万恒科技有限公司";
	private static String PrdOrdNum = "51615101001";
	private static String ecusername = "Admin";
	private static String ecpassword = "0ukjYjz/iXz6C0PI0pa4aYdKHnL1eYEt";

	private static HashMap<String,String> map=new HashMap<String, String>();
	static{
		map.put("10","prod.10086000001992#8585.memserv3");
		map.put("30", "prod.10086000001993#8585.memserv5");
		map.put("70", "prod.10086000001994#8585.memserv10");
		map.put("150", "prod.10086000001995#8585.memserv20");
		map.put("500", "prod.10086000001996#8585.memserv30");
		map.put("1024", "prod.10086000001997#8585.memserv50");
		map.put("2048", "prod.10086000001998#8585.memserv70");
		map.put("3072", "prod.10086000001999#8585.memserv100");
		map.put("4096", "prod.10086000002000#8585.memserv130");
		map.put("6144", "prod.10086000002001#8585.memserv180");
		map.put("11264", "prod.10086000002002#8585.memserv280");
		
	}
	
	public static void main(String[] args) {
		String ser=getTransIDO();
		System.out.println("================"+ser);
//		System.out.println(testEC001("18824588427",ser,"10"));

//		System.out.println(EC0014("CEC200160529920160120113810603135668"));
	}
	
	/**
	 * 充值接口
	 * @param mobile
	 * @param orderNum
	 * @param mz
	 * @return 0成功 -1失败  2处理中 
	 */
	public static int testEC001(String mobile,String orderNum,String mz) {
		mz=mz.replace("mb","").replace("MB","");
		
		if(!map.containsKey(mz)){
			return -1;
		}
		String[] strs=map.get(mz).split("#");
		
		EC001 ec001 = new EC001();
		EC001Member member = new EC001Member();
		member.setUserName(ecname);
		member.setUsecyCle("1");
		member.setOptType("0");
		member.setPayFlag("0");//0＝集团代付，1＝个人支付
		member.setMobile(mobile);// testmobile
		member.setEffType("2");//立即(2), 下月(3), 默认(0) 
		List<EC001PrdList> prdList = new ArrayList<EC001PrdList>();
		EC001PrdList prdlistroot = new EC001PrdList();
		prdlistroot.setOptType("0");
		/////////////////////////////////////////////////////////
		prdlistroot.setPrdCode("AppendAttr.8585");
		List<EC001Service> servicelistroot = new ArrayList<EC001Service>();
		EC001Service serviceroot = new EC001Service();
		serviceroot.setServiceCode("Service8585.Mem");
		serviceroot.setUSERINFOMAP(null);
		servicelistroot.add(serviceroot);
		prdlistroot.setService(servicelistroot);
		// /////////////////////////////////////////////////////////
		EC001PrdList prdlist = new EC001PrdList();
		prdlist.setOptType("0");
		prdlist.setPrdCode(strs[0]);// AppendAttr.8585-----------------------
		List<EC001Service> servicelist = new ArrayList<EC001Service>();
		EC001Service service = new EC001Service();
		service.setServiceCode(strs[1]);// Service8585.Mem//------------------------------
//		List<EC001USERINFOMAP> ulist = new ArrayList<EC001USERINFOMAP>();
//		EC001USERINFOMAP u = new EC001USERINFOMAP();
//		u.setItemName("IFPersonPay123456");
//		u.setItemValue("0");
//		u.setOptType("0");
//		ulist.add(u);
//		service.setUSERINFOMAP(ulist);
		servicelist.add(service);
		prdlist.setService(servicelist);
		prdList.add(prdlistroot);
		prdList.add(prdlist);
		member.setPrdList(prdList);
		ec001.setMember(member);
		int result=EC001(ec001, orderNum);
		if(result==2){
			try { Thread.sleep(5*1000); } catch (Exception e) { }
			return EC0014(orderNum);
		}
		return result;
	}
	
	/**
	 * 订单查询
	 * @param BusinessCode
	 * @return  0成功 -1失败  2处理中 
	 */
	public static int EC0014(String BusinessCode) {
		String temp = "<MemberOrderApplyCrmRelationRequest><BODY>";
		String BusinessCodes = "<BusinessCode>" + BusinessCode + "</BusinessCode>";// 查询的业务号码 最多100
		temp += "<SearchType>"
				+ 1
				+ "</SearchType><BusinessCodes>"
				+ BusinessCodes
				+ "</BusinessCodes></BODY></MemberOrderApplyCrmRelationRequest>";
		Log.info("发送的数据为：" + temp);
		String str = requestHead("EC0014", temp, getTransIDO());
		if (str != null) {
			EC0014BackBody ec0014backbody = getEC0014BackBody(str);
			if (ec0014backbody != null){
				EC0014BackOrderInfo ec=ec0014backbody.getEc0014BackItem().get(0);
				if(ec!=null && ec.getProductInfo()!=null && ec.getProductInfo().size()>0){
					ProductInfo infos=ec.getProductInfo().get(0);
					String st=infos.getDealState();
					if("0".equals(st) || "1".equals(st) ||"2".equals(st) ||"4".equals(st) ||"6".equals(st)){
						return 0;
					}else if("3".equals(st) ||"5".equals(st) ||"7".equals(st)){
						return -1;
					}
				}
			}
		}
		return 2;
	}

	
	
	/**
	 * 统一剩余资源明细查询接口
	 */
	public static String testEC006() {
		String TransIDO = getTransIDO();
		String servNumber = "2000702804";// 成员时,填手机,集团时填集团编码
		String type = "1";// 1 集团号码 2 成员号
		String IsMathGiftSmallType = "";// <IsMathGiftSmallType>"+IsMathGiftSmallType+"</IsMathGiftSmallType>"
		String temp = "<FlowSerachRequest><BODY><ServNumber>" + servNumber
				+ "</ServNumber><Type>" + type + "</Type>"
				+ "</BODY></FlowSerachRequest>";
		return requestHead("EC006", temp, TransIDO);
	}

	/**
	 * 
	 * NGEC通过此接口查询订购的套餐,流量总量、剩余流量信息
	 */
	public static String testEC002(String mobile) {
		String TransIDO = getTransIDO();
		String temp = "<FlowSerachRequest><BODY><ECCode>" + eccode
				+ "</ECCode><Mobile>" + mobile
				+ "</Mobile></BODY></FlowSerachRequest>";
		return requestHead("EC0002", temp, TransIDO);
	}

	/**
	 * EC001接口 成功0 失败-1 处理中2
	 * 
	 * @param ec001
	 *            EC001实体
	 * @param TransIDO
	 *            系统流水
	 */
	public static int EC001(EC001 ec001, String TransIDO) {
		String temp = ec001.getBody("MemberShipRequest", eccode, PrdOrdNum);
		Log.info("发送内容：" + temp);
		String response = requestHead("EC0001", temp, TransIDO);
		String str = "";
		if (response != null) {
			if (response.equals("2"))
				return 2;
			EC001BackBody ec001backbody = getEC001BackBodyXMLToObject(response);
			EC001BackMember m = null;
			if (ec001backbody != null) {
				Log.debug("从EC001BackBody中获取信息并打印........");
				Log.info("ECCode=" + ec001backbody.getECCode() + "/PrdOrdNum="
						+ ec001backbody.getPrdOrdNum());
				m = ec001backbody.getBackMembers();
				Log.info("Mobile=" + m.getMobile() + "/CRMApple="
						+ m.getCRMApplyCode() + "/ResultCode="
						+ m.getResultCode() + "/ResultMsg=" + m.getResultMsg());
				str += "Mobile=" + m.getMobile() + "/CRMApple="
						+ m.getCRMApplyCode() + "/ResultCode="
						+ m.getResultCode() + "/ResultMsg=" + m.getResultMsg();
			} else {
				return 2;
			}
			if (m.getCRMApplyCode() != null)
				return 0;
			else
				return -1;
		} else
			return -1;
	}

	/**
	 * 发送请求头 0 成功 983300 该用户对应的集团产品不存在 983301 该用户没有加入该集群网 983302 无法为该用户分配短号
	 * 981018 参数有效性校验 981004 业务规则有效性校验(用户加入的为敏感集团) 983303 不是成员可选套餐 983304 该成员已存在
	 * 983305 该成员不存在 983306 话费余额不足 983308 并网中的短号集群网成员的短号不允许重复 983319
	 * 该成员正在通过二次确认流程等待处理中 983320 该成员本月转网或转套餐次数已超过限制 983307
	 * 该用户已经是其它集团用户[集团产品号码xxx]的在用成员 100 数据库错误，错误代码由sqlcode指定 200
	 * 操作系统错误，错误代码由errno指定 300 交易中间件错误，错误代码由errcode指定 400 超时错误 500 数据报文错误
	 * 
	 * @param bipcode
	 *            接口号
	 * @param bady
	 *            发送报文
	 * @param TransIDO
	 *            系统流水
	 * @return
	 */
	public static String requestHead(String bipcode, String bady,
			String TransIDO) {
		NGADCServiceForEC adc = new ADCServiceForEC().getNGADCServicesForEC();
		NGEC request = new NGEC();
		request.setOrigDomain("NGEC");
		request.setBIPCode(bipcode);
		request.setBIPVer("V1.0");
		request.setTransIDO(TransIDO);
		request.setAreacode("SW");// 固定填写
		request.setECCode(eccode);
		request.setECUserName(ecusername);
		request.setECUserPwd(ecpassword);
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmssSS");
		request.setProcessTime(simple.format(new Date()));// 发起方发起请求的时间/应答方处理请求的时间YYYYMMDDHHMMSS//"1401354376868"
		request.setSvcCont(bady);
		Log.info("发送请求...");
		try {
			NGEC response = adc.adcServices(request);
			Log.info("打印返回值....." + "OrigDomain:" + response.getOrigDomain()
					+ "/BIPCode:" + response.getBIPCode() + "/BIPVer:"
					+ response.getBIPVer() + "/TransIDO:"
					+ response.getTransIDO() + "/Areacode:"
					+ response.getAreacode() + "/ECCode:"
					+ response.getECCode() + "/ECUserName:"
					+ response.getECUserName() + "/ECUserPwd:"
					+ response.getECUserPwd() + "/ProcessTime:"
					+ response.getProcessTime() + "/SvcCont:"
					+ response.getSvcCont());
			Response re = response.getResponse();
			Log.info("re_RspCode:" + re.getRspCode() + "/re_RspDesc:"
					+ re.getRspDesc());// 返回状态码
			if (re.getRspCode().equals("0000"))
				return response.getSvcCont();
			return null;
		} catch (Exception e) {
			Log.error("请求异常", e);
			return "2";
		}
	}

	/**
	 * 将返回来的xml解析出来
	 * 
	 * @param MemberShipResponse
	 */
	public static EC001BackBody getEC001BackBodyXMLToObject(
			String MemberShipResponse) {
		Log.info("开始解析EC001返回内容*******************************");
		org.dom4j.Document doc;
		EC001BackBody ec001backbody = new EC001BackBody();
		try {
			doc = (org.dom4j.Document) DocumentHelper
					.parseText(MemberShipResponse);
			org.dom4j.Element root = doc.getRootElement();
			Log.debug("root=" + root.getName());
			org.dom4j.Element body = root.element("BODY");
			org.dom4j.Element ECCode = body.element("ECCode");
			ec001backbody.setECCode(ECCode.getText());
			org.dom4j.Element PrdOrdNum = body.element("PrdOrdNum");
			ec001backbody.setPrdOrdNum(body.elementText("PrdOrdNum"));// (PrdOrdNum.getText());
			org.dom4j.Element member = body.element("Member");// 指定获取那个元素
			EC001BackMember backmember = new EC001BackMember();
			backmember.setCRMApplyCode(member.elementText("CRMApplyCode"));
			backmember.setMobile(member.elementText("Mobile"));
			backmember.setResultCode(member.elementText("ResultCode"));
			backmember.setResultMsg(member.elementText("ResultMsg"));
			ec001backbody.setBackMembers(backmember);
		} catch (DocumentException e) {
			Log.error("解析EC001返回字符错误", e);
			ec001backbody = null;
		}
		return ec001backbody;
	}

	/**
	 * 解析EC0010返回字符
	 * 
	 * @param ec0010xmlbody
	 * @return
	 */
	public static EC0010BackBody getEC0010BackBodyXMLToObject(
			String ec0010xmlbody) {
		Log.info("开始解析EC0010返回内容*******************************");
		org.dom4j.Document doc;
		EC0010BackBody ec0010backbody = new EC0010BackBody();
		try {
			doc = (org.dom4j.Document) DocumentHelper.parseText(ec0010xmlbody);
			org.dom4j.Element root = doc.getRootElement();
			Log.debug("root=" + root.getName());
			org.dom4j.Element body = root.element("BODY");
			org.dom4j.Element OrderInfoList = body.element("OrderInfoList");
			Iterator orderInfo = OrderInfoList.elementIterator("OrderInfo");// 指定获取那个元素
			List<EC0010BackOneList> list = new ArrayList<EC0010BackOneList>();
			while (orderInfo.hasNext()) {
				EC0010BackOneList item = new EC0010BackOneList();
				org.dom4j.Element e = (org.dom4j.Element) orderInfo.next();
				item.setOrderId(e.elementText("OrderId"));
				item.setChannelId(e.elementText("ChannelId"));
				item.setCustId(e.elementText("CustId"));
				item.setOperId(e.elementText("OperId"));
				item.setRecDate(e.elementText("RecDate"));
				item.setStatus(e.elementText("Status"));
				list.add(item);
			}
			ec0010backbody.setEc002BackList(list);
		} catch (DocumentException e) {
			Log.error("解析EC0010返回字符错误", e);
			ec0010backbody = null;
		}
		return ec0010backbody;
	}

	public static EC0014BackBody getEC0014BackBody(String ec0014xmlbody) {
		Log.info("开始解析EC0014返回内容*******************************");
		org.dom4j.Document doc;
		EC0014BackBody ec0014backbody = new EC0014BackBody();
		try {
			doc = (org.dom4j.Document) DocumentHelper.parseText(ec0014xmlbody);
			org.dom4j.Element root = doc.getRootElement();
			Log.debug("root=" + root.getName());
			org.dom4j.Element body = root.element("BODY");
			Iterator orderInfo = body.elementIterator("OrderInfo");// 指定获取那个元素
			List<EC0014BackOrderInfo> list = new ArrayList<EC0014BackOrderInfo>();
			while (orderInfo.hasNext()) {
				EC0014BackOrderInfo item = new EC0014BackOrderInfo();
				org.dom4j.Element e = (org.dom4j.Element) orderInfo.next();
				item.setBusinessCode(e.elementText("BusinessCode"));
				item.setMobile(e.elementText("Mobile"));
				Iterator productInfo = e.element("ProductInfos")
						.elementIterator("ProductInfo");
				List<ProductInfo> products = new ArrayList<ProductInfo>();
				while (productInfo.hasNext()) {
					ProductInfo pro = new ProductInfo();
					org.dom4j.Element e2 = (org.dom4j.Element) productInfo
							.next();
					pro.setCrmApplyCode(e2.elementText("CrmApplyCode"));
					pro.setDealState(e2.elementText("DealState"));
					pro.setFlowValue(e2.elementText("FlowValue"));
					pro.setOrderNum(e2.elementText("OrderNum"));
					pro.setProductCode(e2.elementText("ProductCode"));
					pro.setProductName(e2.elementText("ProductName"));
					products.add(pro);
				}
				item.setProductInfo(products);
				list.add(item);
			}
			ec0014backbody.setEc0014BackItem(list);
		} catch (DocumentException e) {
			Log.error("解析EC0014返回字符错误", e);
			ec0014backbody = null;
		}
		return ec0014backbody;
	}

	/**
	 * 获取流水号
	 * 
	 * @return
	 */
	public static String getTransIDO() {
		SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String trans = "CEC" + eccode + simple.format(new Date()) + ((int)(Math.random()*100000)+100000);
		return trans;
	}
}
