package com.wlt.webm.business.action;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import com.alibaba.fastjson.JSON;
import com.bonc.wo_key.WoMd5;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.beijingFlow.Flow;
import com.wlt.webm.business.bean.liuliangfan.LlFan;
import com.wlt.webm.business.bean.unicom3gquery.FillProductDetail;
import com.wlt.webm.business.bean.unicom3gquery.FillProductInfo;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.util.Tools;

/**
 * @author 施建桥
 */
public class Flows_Public_Method {
	static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
	static HttpClient client = new HttpClient(connectionManager);
	static{
		client.getHttpConnectionManager().getParams().setConnectionTimeout(20*1000);
		client.getHttpConnectionManager().getParams().setSoTimeout(20*1000);
	}
	
	/**
	 * 宽带公司流量充值，产品鉴权
	 * @param phone 充值号码
	 * @param productid 产品id
	 * @return boolean true鉴权成功,可以充值,  false鉴权失败或其他失败，不可以充值
	 */
	public static boolean Flows_Authentication(String phone,String productid){
		String str=HttpFillOP.CHECKURL+"productId="+productid+"&activityId="+HttpFillOP.ACTIVITYID+"&phoneNum="+
			phone+"&token="+WoMd5.encode(HttpFillOP.SIGNKEY+phone+productid+HttpFillOP.ACTIVITYID);
		Log.info("宽带公司流量产品鉴权,,,号码:"+phone+",产品ID:"+productid+",,请求url:"+str);
		GetMethod method = new GetMethod(str);
		try {
			int st = client.executeMethod(method);
			if (st == 200) {
				String rs = method.getResponseBodyAsString();
				Log.info("宽带公司流量产品鉴权,,,号码:"+phone+",产品ID:"+productid+",,http响应内容:"+rs);
				if(rs!=null && rs.indexOf("status")>0){
					rs=rs.replace("\"","").replace(":","").replace("{","").replace("}","").replace(" ","").trim();
					rs=rs.substring(rs.indexOf("status")+"status".length(),rs.indexOf("status")+"status".length()+1);
					if("0".equals(rs)){
						return true;
					}
				}
			}
		} catch (Exception e) {
			Log.error("宽带公司流量产品鉴权,,,号码:"+phone+",产品ID:"+productid+",,return false,,系统异常,,ex："+e);
		} finally {
			method.releaseConnection();
		}
		return false;
	}
	
	/**
	 * 宽带公司 流量查询接口
	 * @param wh_OrderNum 万恒订单号 
	 * @return 订单状状态  0成功  -1失败  其他处理中
	 */
	public static String getQueryOrderFlows(String wh_OrderNum){
		String url=HttpFillOP.RESULTURL+"orderNo="+wh_OrderNum+"&activityId="+HttpFillOP.ACTIVITYID;
		Log.info("宽带公司流量订单查询,万恒订单号:"+wh_OrderNum+",,查询请求url:"+url);
		GetMethod get = new GetMethod(url);
		try {
			int status = client.executeMethod(get);
			if (200 == status) {
				String result = HttpFillOP.convertStreamToString(get.getResponseBodyAsStream());
				Log.info("宽带公司流量订单查询,万恒订单号:"+wh_OrderNum+",,http响应内容:"+result);
				if (null == result || "".equals(result.trim())) {
					Log.info("宽带公司流量订单查询,万恒订单号:"+wh_OrderNum+",,return 2 处理中");
					return "2";
				}
				FillProductInfo info = JSON.parseObject(result.toLowerCase(),FillProductInfo.class);
				if(info==null){
					Log.info("宽带公司流量订单查询,万恒订单号:"+wh_OrderNum+",,return 2 处理中");
					return "2";
				}
				if("0".equals(info.getStatus().trim())){
					Log.info("宽带公司流量订单查询,万恒订单号:"+wh_OrderNum+",,return 0 成功");
					return "0";
				}
				if("1".equals(info.getStatus().trim())){
					Log.info("宽带公司流量订单查询,万恒订单号:"+wh_OrderNum+",,return -1 失败");
					return "-1";
				}
			}
		}catch (Exception e) {
			Log.error("宽带公司流量订单查询,万恒订单号:"+wh_OrderNum+",,系统异常,return 2 处理中,,ex:"+e);
			return "2";
		}finally{
			get.releaseConnection();
		}
		Log.info("宽带公司流量订单查询,万恒订单号:"+wh_OrderNum+",,return 2 处理中");
		return "2";
	}
	
	
	/**
	 * 腾讯 京东 流量充值接口公用方法
	 * @param mz 充值面值 50 
	 * @param wh_OrderNum 万恒订单号
	 * @param phone 充值号码
	 * @param phone_type 运营商类型 0电信 1移动 2联通
	 * @param interId 充值接口接口
	 * @param ds 宽带公司地市
	 * @param buf 返回值，用来保存 北分流量充值接口，返回的订单号
	 * @return 0成功 -1失败 其他处理中 异常
	 */
	public static String Request_Flows(String mz,String wh_OrderNum,String phone,String phone_type,String interId,String ds,StringBuffer buf){
		mz=mz.replace("mb","").replace("MB","");
		String rs = "";
		
		//本地 号段，地市 查询
		if(HttpFillOP.FLOW9.equals(interId) || HttpFillOP.FLOW8.equals(interId) || HttpFillOP.FLOW7.equals(interId)){//大汉三通
			rs =getRandomDigit(); 
//			rs=FlowsService.CZ_Orders(phone, mz, wh_OrderNum);
		}else if(HttpFillOP.FLOW12.equals(interId) || HttpFillOP.FLOW11.equals(interId) || HttpFillOP.FLOW10.equals(interId)){//乐流
			rs =getRandomDigit(); 
//			rs=""+LeliuFlowCharge.leliuFill(wh_OrderNum, phone, mz,phone_type);
		}else if(HttpFillOP.FLOW15.equals(interId) || HttpFillOP.FLOW14.equals(interId) || HttpFillOP.FLOW13.equals(interId)){//连连
			rs =getRandomDigit(); 
//			rs=LianlianFlows.CZ_Orders(phone, mz,wh_OrderNum);
		}else if(HttpFillOP.FLOW3.equals(interId) || HttpFillOP.FLOW4.equals(interId) || HttpFillOP.FLOW5.equals(interId)){//思空
			rs =getRandomDigit(); 
//			rs=Flow_SiKong.Recharge(wh_OrderNum, phone,mz+"M");
		}else if(HttpFillOP.FLOW6.equals(interId)){//北京移动
			rs =getRandomDigit(); 
//			String wString=Webservices_Flow.flow_Result(wh_OrderNum,phone,mz);
//			rs=wString.split("#")[0];
//			Log.info("北京移动流量充值接口,,万恒订单号:"+wh_OrderNum+",,,充值响应订单号："+wString.split("#")[1]);
////			if("0".equals(rs)){
////				db.update("update wht_orderform_" + time.substring(2, 6)+" set writeoff='"+wString.split("#")[1]+"' where tradeserial='"+seqNo1+"'");
////			}
		}else if(HttpFillOP.flow16.equals(interId)){//正邦
			rs =getRandomDigit(); 
//			rs=Mobile_Zb_Inter.cz_flow(phone,mz, wh_OrderNum);
		}else if(HttpFillOP.flow17.equals(interId)){//自由移动流量
			rs =getRandomDigit(); 
//			String checkAccount=GDFreeTrafficCharge.getTransIDO();
//			rs=GDFreeTrafficCharge.testEC001(phone,checkAccount,mz)+"";
//			buf.delete(0,buf.length());
//			buf.append(checkAccount);
		}else if(HttpFillOP.flow18.equals(interId)){//流量饭  联通
			rs =getRandomDigit(); 
//			rs=LlFan.llFanFill(wh_OrderNum,phone,mz,"2","")+"";
		}else if(HttpFillOP.FLOw2.equals(interId)){//北分联通
			rs =getRandomDigit(); 
			rs=Flow.BeiJin_Flow(phone,mz);
//			rs=rs.split("#")[0];
//			buf.delete(0,buf.length());
//			buf.append((rs.split("#").length>1)?rs.split("#")[1]:"");
		}else if(HttpFillOP.FLOw1.equals(interId)){//宽带公司
			String cString="{'data':{'list':[{'price_num':10,'pro_num':5,'product_id':'51_001000','product_name':'100mb','product_price':'10.00'},{'price_num':15,'pro_num':5,'product_id':'51_002001','product_name':'200mb','product_price':'15.00'},{'price_num':6,'pro_num':5,'product_id':'51_000501','product_name':'50mb','product_price':'6.00'},{'price_num':3,'pro_num':5,'product_id':'51_002000','product_name':'20mb','product_price':'3.00'},{'price_num':30,'pro_num':5,'product_id':'51_005000','product_name':'500mb','product_price':'30.00'}]},'desc':'数据读取成功','status':'0'}";
			FillProductInfo infos=JSON.parseObject(cString,FillProductInfo.class);
			String realPrice = "";
//			FillProductInfo infos = HttpFillOP.filterProduct(phone,
//					HttpFillOP.phoneAreas1.get(ds),
//					HttpFillOP.ACTIVITYID);
			if (null == infos || !infos.status.equals("0")) {
				Log.info(",同步产品返回信息错误,调用失败通知接口,,,万恒订单号:"+wh_OrderNum+",,,return -1,失败");
				return "-1";
			}
			boolean flag = false;
			@SuppressWarnings("unused")
			String productId = "";
			ArrayList<FillProductDetail> list = (ArrayList<FillProductDetail>) infos.getData().getList();
			for (FillProductDetail detail : list) {
				if ((mz + "MB").equalsIgnoreCase(detail.getProduct_name())) {
					flag = true;
					productId = detail.getProduct_id();
					realPrice = detail.getPrice_num() + "";
					break;
				}
			}
			if (!flag) {
				Log.info("充值流量面值未能匹配同步产品信息,调用失败通知接口,,,万恒订单号:"+wh_OrderNum+",,,return -1,失败");
				return "-1";
			}
			rs =getRandomDigit(); 
//			rs = HttpFillOP.fillProduct(wh_OrderNum, productId, phone, Long.parseLong(realPrice), HttpFillOP.ACTIVITYID) + "";
		}else{
			Log.info("系统配置错误,配置接口错误,调用失败通知接口,,,万恒订单号:"+wh_OrderNum+",,,return -1,失败");
			return "-1";
		}
		Log.info("流量订单充值方法,订单号："+wh_OrderNum+",,充值号码:"+phone+",,,调用接口:"+interId+",,,充值或提交结果:"+rs+",(0成功 -1失败 其他处理中 异常),,,,响应订单号："+buf.toString());
		return rs;
	}
	
	
	/**
	 * 充值结果处理
	 * @param rs 充值结果  0成功 -1失败  其他处理总
	 * @param inter 充值接口
	 * @param bean 订单消息bean
	 * @param call 调用者  tencent || jd
	 */
	public static void Result_Processing(String rs,String inter,Object bean,String call){
		if(call!=null && HttpFillOP.tencent_code.equals(call.trim())){
			//腾讯
			TencentOrdersBean tBean=(TencentOrdersBean)bean;
			Log.info("腾讯流量充值结果业务处理方法,万恒订单号："+tBean.getSeqNo1()+",,腾讯订单号:"+tBean.getPaipai_dealid()+",,充值结果:"+rs+",(0成功 -1失败  其他处理总),,充值调用接口:"+inter);
			if ("0".equals(rs)) {
				if(HttpFillOP.FLOw1.equals(inter) || HttpFillOP.FLOw2.equals(inter)){
					Flows_Public_Method.Is_Back(tBean.getSeqNo1(), "0", call, null,tBean.getBeifen_reesult());
					return ;
				}
			} else if ("-1".equals(rs)) {
				Flows_Public_Method.Is_Back(tBean.getSeqNo1(), "1", call, null,tBean.getBeifen_reesult());
				return ;
			} else {
				if(HttpFillOP.FLOw1.equals(inter)){//宽带公司
					tBean.setLast_time_query(System.currentTimeMillis());
					tBean.setInterid(HttpFillOP.FLOw1);
					PortalSend.getInstance().Send_LianTong_Query_Orders(tBean);
					return ;
				}else if(HttpFillOP.FLOw2.equals(inter)){//北分联通流量
					tBean.setLast_time_query(System.currentTimeMillis());
					tBean.setInterid(HttpFillOP.FLOw2);
					PortalSend.getInstance().Send_LianTong_Query_Orders(tBean);
				}
			}
		}else if(call!=null && HttpFillOP.jd_code.equals(call.trim())){
			//京东
			JdOrderBean jBean=(JdOrderBean)bean;
			Log.info("京东流量充值结果业务处理方法,万恒订单号："+jBean.getWh_order_num()+",,京东订单号:"+jBean.getFillOrderNo()+",,充值结果:"+rs+",(0成功 -1失败  其他处理总),,充值调用接口:"+inter);
			if ("0".equals(rs)) {
				if(HttpFillOP.FLOw1.equals(inter) || HttpFillOP.FLOw2.equals(inter)){
					Flows_Public_Method.Is_Back(jBean.getWh_order_num(), "0", call, null,jBean.getBeifen_reesult());
					return ;
				}
			} else if ("-1".equals(rs)) {
				Flows_Public_Method.Is_Back(jBean.getWh_order_num(), "1", call, null,jBean.getBeifen_reesult());
				return ;
			} else {
				if(HttpFillOP.FLOw1.equals(inter)){//宽带公司
					jBean.setLast_time_query(System.currentTimeMillis());
					jBean.setInterid(HttpFillOP.FLOw1);
					PortalSend.getInstance().Send_JD_Query_Message(jBean);
					return ;
				}else if(HttpFillOP.FLOw2.equals(inter)){//北分联通流量
					jBean.setLast_time_query(System.currentTimeMillis());
					jBean.setInterid(HttpFillOP.FLOw2);
					PortalSend.getInstance().Send_JD_Query_Message(jBean);
				}
			}
		}
	}
	
	/**
	 * 是否回调 
	 * @param order_num 当前交易订单号
	 * @param r_type 0成功 其他失败
	 * @param type 1000腾讯  ，1001 京东
	 * @param obj 腾讯或京东 订单消息bean ,(当订单还没有入库,验证失败时,此参数传入 订单消息bean) or (腾讯重复订单,不需要操作数据库,直接回调) 
	 * @param beifen_orderNum 北分流量充值接口，订单号
	 * @return boolean
	 */
	public static boolean Is_Back(String order_num,String r_type,String type,Object obj,String beifen_orderNum){
		//不需要操作数据库,只需要回调接口
		if(obj!=null){
			try{
				if(HttpFillOP.tencent_code.equals(type)){
					TencentOrdersBean bean=(TencentOrdersBean)obj;
					if("0".equals(r_type)){
						//腾讯通知充值成功
						Log.info("腾讯回调通知,订单状态成功,万恒订单号:"+bean.getSeqNo1()+",,腾讯订单号:"+bean.getPaipai_dealid());
						Tencent_Flows.Success_Back(bean.getPaipai_dealid(),bean.getSeqNo1(),Tencent_Flows.refundFee);
						return true;
					}else{
						//腾讯通知充值失败
						Log.info("腾讯回调通知,订单状态失败,万恒订单号:"+bean.getSeqNo1()+",,腾讯订单号:"+bean.getPaipai_dealid());
						Tencent_Flows.Failure_Back(bean.getPaipai_dealid(),bean.getSeqNo1(),Tencent_Flows.refundFee);
						return true;
					}
				}else if(HttpFillOP.jd_code.equals(type)){
					JdOrderBean jbean=(JdOrderBean)obj;
					if("0".equals(r_type)){
						//京东通知充值成功
						Log.info("京东回调通知,订单状态成功,万恒订单号:"+jbean.getWh_order_num()+",,京东订单号:"+jbean.getFillOrderNo());
						Jd_Flows.Rs_break(jbean.getFillOrderNo(),jbean.getWh_order_num(),jbean.getFillAmount(),Tools.getNow3(),jbean.getNotifyUrl(), "1");
						return true;
					}else{
						//京东通知充值失败
						Log.info("京东回调通知,订单状态失败,万恒订单号:"+jbean.getWh_order_num()+",,京东订单号:"+jbean.getFillOrderNo());
						Jd_Flows.Rs_break(jbean.getFillOrderNo(),jbean.getWh_order_num(),jbean.getFillAmount(),Tools.getNow3(),jbean.getNotifyUrl(), "2");
						return true;
					}
				}
			}catch (Exception e) {
				Log.error("回调腾讯或京东,不操作数据库,系统异常,,,ex："+e);
			}
			return false;
		}
		
		//需要操作数据库
		String tableName="wht_orderform_"+Tools.getNow3().substring(2,6);
		DBService db=null;
		try {
			db=new DBService();
			String update_time=Tools.getNow3();
			String[] isData=db.getStringArray("SELECT fl.o_tradeserial,fl.o_writecheck,fl.o_writeoff,fl.o_notityUrl FROM wht_flow_Reissue fl WHERE fl.r_dis=0 and fl.o_writecheck=(SELECT r.o_writecheck FROM wht_flow_Reissue r WHERE r.o_tradeserial='"+order_num+"' AND r.r_dis=1) LIMIT 0,1");
			if(isData==null || isData.length<=0){
				//没有补充过
				String[] strs=db.getStringArray("SELECT tradeserial,writecheck,writeoff,term_type,buyid,tradeobject FROM "+tableName+" where tradeserial='"+order_num+"'");
				if("0".equals(r_type)){
					Log.info("第一次充值,订单充值成功,修改当前订单状态为成功,回调通知,万恒订单号:"+strs[0]+",,腾讯或京东订单号:"+strs[1]);
					//第一次订单成功
					if(beifen_orderNum==null || "".equals(beifen_orderNum.trim())){
						db.update("update "+tableName+" set state=0,chgtime='"+update_time+"' where tradeserial='"+order_num+"'");
					}else{ 
						db.update("update "+tableName+" set state=0,chgtime='"+update_time+"',fundacct='"+beifen_orderNum+"' where tradeserial='"+order_num+"'");
					}
					if(HttpFillOP.tencent_code.equals(type)){
						//腾讯通知充值成功
						Tencent_Flows.Success_Back(strs[1],strs[0],Tencent_Flows.refundFee);
						return true;
					}else if(HttpFillOP.jd_code.equals(type)){
						//京东通知充值成功
						Jd_Flows.Rs_break(strs[1], strs[0], (strs[2].indexOf("mb")>0)?strs[2].replace("mb",""):strs[2], update_time, strs[3], "1");
						return true;
					}
				}else{
					Log.info("第一次充值,订单充值失败,修改当前订单状态为失败,写入补充订单,万恒订单号:"+strs[0]+",,腾讯或京东订单号:"+strs[1]);
					String tradeserial=Tools.getNow3()+"as"+((int)(Math.random()*1000)+1000)+((char)(new Random().nextInt(26) + (int)'a'))+((char)(new Random().nextInt(26) + (int)'A'));
					//第一次订单失败
					if(beifen_orderNum==null || "".equals(beifen_orderNum.trim())){
						db.update("update "+tableName+" set state=1,chgtime='"+update_time+"' where tradeserial='"+order_num+"'");
					}else{
						db.update("update "+tableName+" set state=1,chgtime='"+update_time+"',fundacct='"+beifen_orderNum+"' where tradeserial='"+order_num+"'");
					}
					//京东没有补充功能
					if(HttpFillOP.jd_code.equals(type)){
						Jd_Flows.Rs_break(isData[1], isData[0], (isData[2].indexOf("mb")>0)?isData[2].replace("mb",""):isData[2], update_time, isData[3], "2");
						return true;
					}
					String create_inter=getInter(strs[4],strs[5],strs[2],tradeserial);
					db.update("INSERT INTO wht_flow_Reissue(r_dis,o_tradeserial,o_tradeobject,o_buyid,o_service,o_fee,o_tradetime,o_chgtime,o_state,o_writeoff,o_writecheck,o_userno,o_phone_type,o_city,o_notityUrl,handle_state,o_type,a1) "+
					 "SELECT '0',tradeserial,tradeobject,buyid,service,fee,tradetime,chgtime,state,writeoff,writecheck,userno,phone_type,phone_pid,term_type,'',phoneleft,fundacct FROM "+tableName+"  WHERE tradeserial='"+order_num+"' "+
					 "UNION "+
					 "SELECT '1','"+tradeserial+"',tradeobject,'"+create_inter+"',service,fee,'"+update_time+"','','4',writeoff,writecheck,userno,phone_type,phone_pid,term_type,'0',phoneleft,'' FROM "+tableName+"  WHERE tradeserial='"+order_num+"' ");
					Log.info("第一次充值,订单充值失败,写入补充订单成功,等待补充,万恒订单号:"+strs[0]+",,腾讯或京东订单号:"+strs[1]);
					return true;
				}
			}else{
				//已经补充过
				if("0".equals(r_type)){
					Log.info("第二次充值,补充订单,订单充值成功,修改当前订单状态为,成功,回调腾讯或京东,补充万恒订单号:"+order_num+",,原始万恒订单号:"+isData[0]+",,腾讯或京东订单号:"+isData[1]);
					if(beifen_orderNum==null || "".equals(beifen_orderNum.trim())){
						db.update("update wht_flow_Reissue set o_state=0,o_chgtime='"+update_time+"',handle_state='2' where o_tradeserial='"+order_num+"' and r_dis=1");
					}else{
						db.update("update wht_flow_Reissue set o_state=0,o_chgtime='"+update_time+"',handle_state='2',a1='"+beifen_orderNum+"' where o_tradeserial='"+order_num+"' and r_dis=1");
					}
					if(HttpFillOP.tencent_code.equals(type)){
						//腾讯通知充值成功
						Tencent_Flows.Success_Back(isData[1],isData[0],Tencent_Flows.refundFee);
						return true;
					}else if(HttpFillOP.jd_code.equals(type)){
						//京东通知充值成功
						Jd_Flows.Rs_break(isData[1], isData[0], (isData[2].indexOf("mb")>0)?isData[2].replace("mb",""):isData[2], update_time, isData[3], "1");
						return true;
					}
				}else{
					Log.info("第二次充值,补充订单,订单充值失败,修改当前订单状态为,失败,回调腾讯或京东,补充万恒订单号:"+order_num+",,原始万恒订单号:"+isData[0]+",,腾讯或京东订单号:"+isData[1]);
					if(beifen_orderNum==null || "".equals(beifen_orderNum.trim())){
						db.update("update wht_flow_Reissue set o_state=1,o_chgtime='"+update_time+"',handle_state='2' where o_tradeserial='"+order_num+"' and r_dis=1");
					}else{
						db.update("update wht_flow_Reissue set o_state=1,o_chgtime='"+update_time+"',handle_state='2',a1='"+beifen_orderNum+"' where o_tradeserial='"+order_num+"' and r_dis=1");
					}
					if(HttpFillOP.tencent_code.equals(type)){
						//腾讯通知充值失败
						Tencent_Flows.Failure_Back(isData[1],isData[0],Tencent_Flows.refundFee);
						return true;
					}else if(HttpFillOP.jd_code.equals(type)){
						//京东通知充值失败
						Jd_Flows.Rs_break(isData[1], isData[0], (isData[2].indexOf("mb")>0)?isData[2].replace("mb",""):isData[2], update_time, isData[3], "2");
						return true;
					}
				}
			}
		} catch (Exception e) {
			Log.error("流量,订单号:"+order_num+",,充值结果:"+r_type+",,系统异常,,,ex:"+e);
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return false;
	}
	
	/**
	 * 获取需要切换的接口id
	 * @param isInter 当前接口 id
	 * @param tradeobject 补充号码
	 * @param mz 补充面值
	 * @param tradeserial 补充订单号 
	 * @return String 接口id
	 */
	public static String getInter(String isInter,String tradeobject,String mz,String tradeserial){ 
		if(HttpFillOP.FLOw1.equals(isInter)){ //如果当前接口是宽带公司，则返回 北分接口
			return HttpFillOP.FLOw2;
		}
		if(HttpFillOP.FLOw2.equals(isInter)){//如果当前接口时北分接口，则返回 宽带公司
			return HttpFillOP.FLOw1;
		}
		
		mz=mz.replace("mb","").replace("MB","").trim();
		
		Log.info("万恒订单号:"+tradeserial+",,鉴权号码:"+tradeobject+",,鉴权面值:"+mz+",,调用宽带公司鉴权接口");
		boolean bool=false;
		//调用鉴权接口 可以充值(宽带公司)，不可以充值走（北分）
		if(HttpFillOP.phone_check.containsKey(mz)){
			bool=Flows_Public_Method.Flows_Authentication(tradeobject,HttpFillOP.phone_check.get(mz));
		}
		if(bool){
			Log.info("万恒订单号:"+tradeserial+",,鉴权号码:"+tradeobject+",,鉴权面值:"+mz+",,鉴权成功,返回 宽带公司接口编号");
			return HttpFillOP.FLOw1;//省公司流量
		}else{
			Log.info("万恒订单号:"+tradeserial+",,鉴权号码:"+tradeobject+",,鉴权面值:"+mz+",,鉴权失败,返回 北分流量接口编号");
			return HttpFillOP.FLOw2;//北分流量
		}
	}
	
	public static String getRandomDigit() {
		Random r = new Random();
		int n = r.nextInt(100);
		String m;
		if (n < 35) {
			m = "0";
			return m;
		} else if (n < 70) {
			m = "-1";
			return m;
		}else {
			m = "2";
			return m;
		}
		
	}
}
