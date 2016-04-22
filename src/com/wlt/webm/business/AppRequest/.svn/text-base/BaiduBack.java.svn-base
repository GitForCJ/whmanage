package com.wlt.webm.business.AppRequest;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.AppRequest.baidu.PartnerConfig;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.beijingFlow.BeanFlow;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.gm.bean.GuanMingBean;
import com.wlt.webm.message.MemcacheConfig;

public class BaiduBack extends DispatchAction {

	/**
	 * @throws  
	 * 
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		/**
		 * 1、设置编码
		 */
		request.setCharacterEncoding("gbk");
		response.setContentType("text/html;charset=gbk");
		response.setCharacterEncoding("gbk");
		
		String sp_no=request.getParameter("sp_no");
		String order_no=request.getParameter("order_no");
		String bfb_order_no=request.getParameter("bfb_order_no");
		String bfb_order_create_time=request.getParameter("bfb_order_create_time");
		String pay_time=request.getParameter("pay_time");
		String pay_type=request.getParameter("pay_type");
		String total_amount=request.getParameter("total_amount");
		String fee_amount=request.getParameter("fee_amount");
		String currency=request.getParameter("currency");
		String buyer_sp_username=request.getParameter("buyer_sp_username");
		String pay_result=request.getParameter("pay_result");
		String input_charset=request.getParameter("input_charset");
		String version=request.getParameter("version");
		String sign=request.getParameter("sign");
		String sign_method=request.getParameter("sign_method");
		
		String extra=request.getParameter("extra");
		String transport_amount=request.getParameter("transport_amount");
		String unit_amount=request.getParameter("unit_amount");
		String unit_count=request.getParameter("unit_count");
		String bank_no=request.getParameter("bank_no");
		
		Log.info("百度钱包回调信息,,,订单号:"+order_no+",,支付结果:"+pay_result+",,,支付金额(分):"+total_amount);
		
		String local_sign=com.wlt.webm.business.AppRequest.baidu.MD5.toMD5("bank_no="+bank_no+"&bfb_order_create_time="+bfb_order_create_time+"&bfb_order_no="+bfb_order_no+"&buyer_sp_username="+buyer_sp_username+"&currency="+currency+"&extra="+extra+"&fee_amount="+fee_amount+"&input_charset="+input_charset+"&order_no="+order_no+"&pay_result="+pay_result+"&pay_time="+pay_time+"&pay_type="+pay_type+"&sign_method="+sign_method+"&sp_no="+sp_no+"&total_amount="+total_amount+"&transport_amount="+transport_amount+"&unit_amount="+unit_amount+"&unit_count="+unit_count+"&version="+version+"&key="+PartnerConfig.MD5_PRIVATE);
		if("1".equals(pay_result.trim())){
			if(sign.trim().equalsIgnoreCase(local_sign.trim())){
				Log.info("百度钱包回调信息,,,订单号:"+order_no+",,支付结果:"+pay_result+",支付成功,,,支付金额(分):"+total_amount);
				
				if(order_no==null || "".equals(order_no.trim()) ||
						total_amount==null || "".equals(total_amount.trim())){
					Log.info("百度钱包回调信息,,,订单号:"+order_no+",,支付结果:"+pay_result+",支付成功,,订单号或支付金额未空,return null,,,支付金额(分):"+total_amount);
					return null;
				}
				
				StringBuffer buffer=new StringBuffer();
				buffer.append("<HTML><head>");
				buffer.append("<meta name=\"VIP_BFB_PAYMENT\" content=\"BAIFUBAO\">");
				buffer.append("</head>");
				buffer.append("<body>");
				buffer.append("支付成功，验签通过" + "订单号：" + order_no + "<br/>");
				buffer.append("百付宝返回的签名串 :" + sign + "<br/>");
				buffer.append("本地生成的签名串     :" + local_sign + "<br/>");
				buffer.append("</body></html>");
				
				PrintWriter out = response.getWriter();
				out.println(buffer.toString());
				
				Log.info("百度钱包回调信息,,,订单号:"+order_no+",,支付结果:"+pay_result+",支付成功,,,支付金额(分):"+total_amount+",,响应html:"+buffer.toString());
				
				//支付成功 业务处理
				services_oper(order_no);
				
				out.flush();
				out.close();
			}else{
				Log.info("百度钱包回调信息,,,订单号:"+order_no+",,支付结果:"+pay_result+",支付成功,签名错误,,,支付金额(分):"+total_amount);
			}
		}else{
			Log.info("百度钱包回调信息,,,订单号:"+order_no+",,支付结果:"+pay_result+",支付失败,,,支付金额(分):"+total_amount);
		}
		return null;
	}
	
	/**
	 * 百度支付成功业务处理
	 * @param order_no app_RechargeOrder_1509 orderNum
	 * @param pay_result 1支付成功
	 * @param total_amount 支付金额 分
	 */
	@SuppressWarnings("static-access")
	public static void services_oper(String order_no){
		Log.info("回调成功,支付成功,进入业务处理方法,,,订单号:"+order_no);
		
		String tableName="app_RechargeOrder_"+order_no.substring(2,6);
		
		DBService db=null;
		try {
			db=new DBService();
			String[] strs=db.getStringArray("SELECT ap.id,ap.userno,ap.productId,ap.productName,ap.productBody,ap.amount,ap.price,ap.realPrice,ap.phoneNum,ap.payStatus,ap.rechargeStatus,ap.createOrderTime,ap.phoneType,ap.areaId FROM "+tableName+" ap WHERE ap.orderNum='"+order_no.trim()+"'");
			if(strs==null || strs.length<=0){
				Log.info("回调成功,支付成功,,,订单号:"+order_no+",,订单不存在,reutrn null");
				return ;
			}
			if(!"1004".equals(strs[9])){
				Log.info("回调成功,支付成功,,,订单号:"+order_no+",,该订单已处理过,重复回调,reutrn null");
				return;
			}
			TPForm tp = MemcacheConfig.getInstance().getTP(strs[1]);
			if(tp==null){
				Log.info("回调成功,支付成功,,,订单号:"+order_no+",,我们系统问题,无法获取到接口商信息userno:"+strs[1]+",reutrn null");
				return ;	
			}
			//修改支付结果为成功，，，
			db.update("UPDATE "+tableName+" SET payStatus=1000 WHERE orderNum='"+order_no.trim()+"'");
			
			String rechargeStatus="";
			
			if("10000001".equals(strs[3])){
				Log.info("回调成功,支付成功,,,订单号:"+order_no+",,Q币业务,组装Q币请求消息,发起Q币充值,,,");
				//Q币业务
				// 支付宝
				GuanMingBean gmbean = new GuanMingBean();
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("num", strs[5]);
				map.put("in_acct", strs[8]);
				map.put("gameapi","0005");
				map.put("ip", tp.getIp());
				map.put("flag", "1");// 1为直营，0为非直营
				map.put("qbCommission",tp.getQbCommission());//每个接口商对应的佣金
				map.put("caidan",tp.getCaidan());//每个接口商对应的拆单 开关   -- 0拆单  1不拆单
				map.put("term_type","0");
				
				// 0 成功 1 失败 2处理中或异常 3重复交易 4账号余额不足 5佣金不存在
				int state = gmbean.qbInterfaceDistribution(map, tp.getUser_no(),tp.getUser_login(), "1", tp.getSa_zone(), order_no, tp.getQbFlag());
				if(state==0){
					rechargeStatus="1000";//成功
				}else if(state==1 || state==3 || state==4 || state==5){
					rechargeStatus="1002";//失败
				}else {
					rechargeStatus="1003";//处理中
				}
				db.update("UPDATE "+tableName+" SET rechargeStatus="+rechargeStatus+" WHERE orderNum='"+order_no.trim()+"'");
				Log.info("回调成功,支付成功,,,订单号:"+order_no+",,Q币业务,充值处理完成,充值结果:"+state+",,结果描述(0 成功 1 失败 2处理中或异常 3重复交易 4账号余额不足 5佣金不存在)");
				return ;
			}else if("10000000".equals(strs[3])){
				Log.info("回调成功,支付成功,,,订单号:"+order_no+",,话费充值业务,组装话费充值请求消息,发起话费充值,,,");
				//话费充值业务
				BiProd bp = new BiProd();
				String[] result = bp.getEmploy(db, strs[12], tp.getUser_site()
						+ "", strs[13], tp.getUser_no(),"2", Integer.parseInt(strs[6]) / 1000, 1, Integer.parseInt(tp.getGroups()));
				if (null == result) {
					rechargeStatus="1002";
					Log.info("回调成功,支付成功,,,订单号:"+order_no+",,话费充值业务,本地佣金获取失败,");
				}else{
					int k = bp.nationFill(1 + "", tp.getUser_no(), strs[13], tp.getUser_site()+ "", strs[12], strs[8], order_no, Double.parseDouble(strs[6]), result, tp.getSa_zone(), db, tp.getUser_login(), tp.getIp(),"OF", "1");
					if (k == 0 || k >= 20) {
						rechargeStatus="1000";//成功
					}else if (k == 1 || k == -1) {
						rechargeStatus="1002";//"充值失败"
					} else if(k==-5){
						rechargeStatus="1002";//"账户余额不足"
					}else if(k==6){
						rechargeStatus="1002";//"流水号重复交易失败"
					}else {
						rechargeStatus="1003";//处理中
					}
				}
				db.update("UPDATE "+tableName+" SET rechargeStatus="+rechargeStatus+" WHERE orderNum='"+order_no.trim()+"'");
				Log.info("回调成功,支付成功,,,订单号:"+order_no+",,话费充值业务,话费充值处理完成,");
			}else if("10000002".equals(strs[3])){
				String productId=strs[2];
				String phoneType=strs[12];
				String name=strs[4].substring(0,strs[4].indexOf("mb"));
				String userno=strs[1];
				String phone=strs[8];
				String orderid=order_no;
				String areaId=strs[13];
				String realPrice=strs[6];
				//流量充值业务
				String interId=BiProd.getFlowInterface(phoneType,areaId)+"";//BiProd.getFlowInterface(Integer.parseInt(phoneType),Integer.parseInt(name))+"".trim();
				HashMap<String,String> map=null;
				if(HttpFillOP.FLOw1.equals(interId)){//省流量
					map= new HttpFillOP().fillFlow(orderid, productId, phone,
						Integer.parseInt(realPrice), HttpFillOP.ACTIVITYID, userno, tp.getUser_login(),tp.getSa_zone(),
						areaId, tp.getUser_site()+"",
						tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
				}else if(HttpFillOP.FLOw2.equals(interId)){//北京联通流量
					map=BeanFlow.fillFlow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
				}else if(HttpFillOP.FLOW3.equals(interId)){//思空移动流量
					map=BeanFlow.sikong_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"1");//移动
				}else if(HttpFillOP.FLOW4.equals(interId)){//思空联通流量
					map=BeanFlow.sikong_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//联通
				}else if(HttpFillOP.FLOW5.equals(interId)){//思空电信流量
					map=BeanFlow.sikong_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"",
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//电信
				}else if(HttpFillOP.FLOW6.equals(interId)){//北京移动流量
					map=BeanFlow.WebServices_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
				}else if(HttpFillOP.FLOW8.equals(interId)){//大汉三通移动流量
					map=BeanFlow.Dhst_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"1");//移动
				}else if(HttpFillOP.FLOW9.equals(interId)){//大汉三通联通流量
					map=BeanFlow.Dhst_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//联通
				}else if(HttpFillOP.FLOW7.equals(interId)){//大汉三通电信流量
					map=BeanFlow.Dhst_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"",
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//电信
				}else if(HttpFillOP.FLOW11.equals(interId)){//乐流移动流量
					map=BeanFlow.LeLiu_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"1");//移动
				}else if(HttpFillOP.FLOW12.equals(interId)){//乐流联通流量
					map=BeanFlow.LeLiu_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//联通
				}else if(HttpFillOP.FLOW10.equals(interId)){//乐流电信流量
					map=BeanFlow.LeLiu_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"",
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//电信
				}else if(HttpFillOP.FLOW14.equals(interId)){//连连移动流量
					map=BeanFlow.Lianlian_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"1");//移动
				}else if(HttpFillOP.FLOW15.equals(interId)){//连连联通流量
					map=BeanFlow.Lianlian_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"2");//联通
				}else if(HttpFillOP.FLOW13.equals(interId)){//连连电信流量
					map=BeanFlow.Lianlian_flow(orderid, productId, phone,
							Integer.parseInt(realPrice), HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"",
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name,"0");//电信
				}else if(HttpFillOP.flow16.equals(interId)){//正邦移动流量
					map=BeanFlow.zb_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
				}else if(HttpFillOP.flow17.equals(interId)){//自由移动流量
					map=BeanFlow.zy_flow(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
				}else if(HttpFillOP.flow18.equals(interId)){//流量饭 联通
					map=BeanFlow.LiuLiangFan(orderid, productId, phone,
							Integer.parseInt(realPrice),HttpFillOP.ZDY, userno, tp.getUser_login(),tp.getSa_zone(),
							areaId, tp.getUser_site()+"", 
							tp.getUserPt(),tp.getFlowgroups(),interId,"1",name);
				}else{
					rechargeStatus="1002";//失败
					Log.info("回调成功,支付成功,,,订单号:"+order_no+",,流量充值业务,本地未配置接口,充值失败,");
					db.update("UPDATE "+tableName+" SET rechargeStatus="+rechargeStatus+" WHERE orderNum='"+order_no.trim()+"'");
					return ;
				}
				if(null!=map){
					String code =map.get("code")+"".trim();
					if("0".equals(code)){
						rechargeStatus="1000";//成功
					}else if("-5".equals(code) ||"1".equals(code) ||"-1".equals(code) ||"8".equals(code) ||"6".equals(code)){
						rechargeStatus="1002";//失败
					}else{
						rechargeStatus="1003";//处理中
					}
				}else{
					rechargeStatus="1003";//处理中
				}
				db.update("UPDATE "+tableName+" SET rechargeStatus="+rechargeStatus+" WHERE orderNum='"+order_no.trim()+"'");
				Log.info("回调成功,支付成功,,,订单号:"+order_no+",,流量充值业务,流量充值处理完成,");
				
			}else{
				Log.info("回调成功,支付成功,,,订单号:"+order_no+",,我们系统问题,未知业务类型,reutrn null");
				return ;
			}
		} catch (Exception e) {
			Log.error("回调成功,支付成功,进入业务处理方法,,,订单号:"+order_no+",,系统异常,ex:"+e);
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
		
	}
	public static String getRandomDigit() {
		Random r = new Random();
		int n = r.nextInt(100);
		String m;
		if (n < 70) {
			m = "0";
			return m;
		} else if (n < 90) {
			m = "-1";
			return m;
		}else {
			m = "-2";
			return m;
		}
		
	}
}
