package com.wlt.webm.business.AppRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.AppRequest.alipay.ALiPayUtils;
import com.wlt.webm.business.AppRequest.baidu.SDKBaiduPay;
import com.wlt.webm.business.AppRequest.entity.ActivityBean;
import com.wlt.webm.business.AppRequest.entity.FlowOrderBean;
import com.wlt.webm.business.AppRequest.entity.FlowProductsBean;
import com.wlt.webm.business.AppRequest.entity.FlowProductsBeanList;
import com.wlt.webm.business.AppRequest.entity.One;
import com.wlt.webm.business.AppRequest.entity.OneList;
import com.wlt.webm.business.AppRequest.entity.OrderBean;
import com.wlt.webm.business.AppRequest.entity.OrdersListBean;
import com.wlt.webm.business.AppRequest.entity.QbPrice;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.unicom3gquery.FillProductDetail;
import com.wlt.webm.business.bean.unicom3gquery.FillProductInfo;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.MD5;
import com.wlt.webm.util.Tools;

/**
 * @author 施建桥
 */
public class App extends DispatchAction {
	
	public static final String apkey="6fe0ce61f6c59b088a50093f7c8e5a83";
	
	/**
	 * 支付宝回调地址
	 */
	private static final String AlipayBack="http://www.wanhuipay.com/AlipayBack.do";
	
	/**
	 * 百度钱包回调
	 */
	private static final String baiduBack="http://www.wanhuipay.com/BaiduBack.do";
	
	
	/**
	 * 根据手机号请求可充值的流量包
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward getFlowProducts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		FlowProductsBean fbean=new FlowProductsBean();
		
		String phoneNum=request.getParameter("phoneNum");//充值手机号码11位
		String userno=request.getParameter("sysNum");//系统编号
		if(phoneNum==null || "".equals(phoneNum.trim()) || phoneNum.length()<7 || userno==null || "".equals(userno.trim())){
			Log.info("app请求获取可充值的流量包,参数错误，，");
			fbean.setCode("1004");
			fbean.setDesc("请求数据错误001,请联系商家");
			String jsonString=JSON.toJSONString(fbean);
			ResponseContent(jsonString,response);
			return null;
		}
		
		TPForm tp = MemcacheConfig.getInstance().getTP(userno);
		if (tp == null) {
			Log.info("app请求获取手机号段,接口商未配置，，userno:"+userno);
			fbean.setCode("1004");
			fbean.setDesc("无权限002,请联系商家");
			String jsonString=JSON.toJSONString(fbean);
			ResponseContent(jsonString,response);
			return null;
		}
		
		String phonString=phoneNum.substring(0, 7);
		BiProd biProd = new BiProd();
		String[] str = biProd.getPhoneInfo(phonString);
		if(str==null || str.length<=0){
			Log.info("app请求获取可充值的流量包,未知号段,,,号码："+phoneNum+",,系统编号:"+userno);
			fbean.setCode("1004");
			fbean.setDesc("未知号码号段");
			String jsonString=JSON.toJSONString(fbean);
			ResponseContent(jsonString,response);
			return null;
		}
		String phonePid=str[0];
		String phoneType=str[1];
		String phoneArea="";
		if (phoneType.equals("0")) {
			phoneArea = "电信";
		} else if (phoneType.equals("1")) {
			phoneArea = "移动";
		} else  if (phoneType.equals("2")) {
			phoneArea = "联通";
		}else{
			phoneArea="未知";
		}
		fbean.setMobileTypeCode(phoneType);//运营商类型编码 0电信 1移动 2联通
		fbean.setMobileAreaCode(phonePid); //省份编码
		fbean.setMobileArea(str[2]+" "+phoneArea); //省份名称
		
		FillProductInfo fileBean=(FillProductInfo)HttpFillOP.filterProductJSON(phoneNum,HttpFillOP.phoneAreas1.get(phonePid),phoneType,"bean",tp.getFlowgroups());
		if(fileBean==null || fileBean.getData()==null || fileBean.getData().getList()==null || fileBean.getData().getList().size()<=0){
			Log.info("app请求获取可充值的流量包,获取流量产品面额失败,,,号码："+phoneNum+",,系统编号:"+userno);
			fbean.setCode("1006");
			fbean.setDesc("获取流量产品失败");
			String jsonString=JSON.toJSONString(fbean);
			ResponseContent(jsonString,response);
			return null;
		}
		
		List<FlowProductsBeanList> fBeanList=new ArrayList<FlowProductsBeanList>();
		for(FillProductDetail ds:fileBean.getData().getList()){
			FlowProductsBeanList bb=new FlowProductsBeanList();
			bb.setProduct_id(ds.getProduct_id());
			bb.setProduct_name(ds.getProduct_name());
			bb.setProduct_price(ds.getPrice_num()+"");
			fBeanList.add(bb);
		}
		Log.info("app请求获取可充值的流量包,成功获取流量产品,,,号码："+phoneNum+",,系统编号:"+userno);
		fbean.setCode("1000");
		fbean.setDesc("成功获取流量产品");
		fbean.setProducts(fBeanList);
		String jsonString=JSON.toJSONString(fbean);
		ResponseContent(jsonString,response);
		return null;
		
	}
	
	/**
	 * 订单查询
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getFlowOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String userno=request.getParameter("sysNum");//系统编号
		String userName=request.getParameter("userName");//设备id
		String phoneNum=request.getParameter("phoneNum");//手机号或者QQ号
		String page=request.getParameter("page");//从第几条开始获取
		String limit=request.getParameter("limit");//获取几条
		String month=request.getParameter("month");//月份
		
		FlowOrderBean fbean=new FlowOrderBean();
		
		if(userno==null || "".equals(userno.trim()) ||
				page==null || "".equals(page.trim())||
				limit==null || "".equals(limit.trim())||
				userName==null || "".equals(userName.trim()) ||
				month==null || "".equals(month.trim())){
			Log.info("app查询订单,参数错误，，");
			fbean.setCode("1004");
			fbean.setDesc("请求数据错误001,请联系商家");
			String jsonString=JSON.toJSONString(fbean);
			ResponseContent(jsonString,response);
			return null;
		}
		
		TPForm tp = MemcacheConfig.getInstance().getTP(userno);
		if (tp == null) {
			Log.info("app查询订单,接口商未配置，，userno:"+userno);
			fbean.setCode("1004");
			fbean.setDesc("无权限002,请联系商家");
			String jsonString=JSON.toJSONString(fbean);
			ResponseContent(jsonString,response);
			return null;
		}
		
		DBService db=null;
		try {
			String w="";
			if(phoneNum!=null && !"".equals(phoneNum.trim())){
				w=" and phoneNum='"+phoneNum+"' ";
			}
			
			if(month.length()<2){
				month=("0"+month);
			}
			
			String sql="SELECT orderNum,productName," +
			"productBody,price,realPrice,phoneNum," +
			"payStatus,rechargeStatus,orderStartTime," +
			"orderEndTime,aliPay,wxPay,productId,Spare1 FROM app_RechargeOrder_"+Tools.getNow3().substring(2,4)+month+" " +
			"WHERE userName='"+userName+"' "+w+" ORDER BY orderStartTime DESC " +
					"LIMIT "+page+","+limit;
			
			db=new DBService();
			List<String[]> arsy=db.getList(sql);
			if(arsy==null || arsy.size()<=0){
				Log.info("app查询订单,暂无订单信息，，userno:"+userno);
				fbean.setCode("1004");
				fbean.setDesc("暂无订单信息");
				String jsonString=JSON.toJSONString(fbean);
				ResponseContent(jsonString,response);
				return null;
			}
			
			FlowOrderBean f=new FlowOrderBean();
			f.setCode("1000");
			f.setDesc("成功查询订单");
			List<OrdersListBean> orders =new ArrayList<OrdersListBean>();
			for(String[] s:arsy){
				OrdersListBean olist=new OrdersListBean();
				olist.setOrderNum(s[0]);
				olist.setProductName(s[1]);
				olist.setProductBody(s[2]);
				olist.setPrice(s[3]);
				olist.setRealPrice(s[4]);
				olist.setPhoneNum(s[5]);
				olist.setPayStatus(s[6]);
				olist.setRechargeStatus(s[7]);
				olist.setOrderStartTime(s[8]);
				olist.setOrderEndTime(s[9]);
				olist.setProductId(s[12]);
				olist.setBdPay(s[13]);
				olist.setAliPay(s[10]);
				orders.add(olist);
			}
			f.setOrders(orders);
			String jsonString=JSON.toJSONString(f);
			ResponseContent(jsonString,response);
		} catch (Exception e) {
			Log.error("app查询订单,系统异常，，userno:"+userno+",,,ex:"+e);
			fbean.setCode("1004");
			fbean.setDesc("系统异常,请稍候再试");
			String jsonString=JSON.toJSONString(fbean);
			ResponseContent(jsonString,response);
			return null;
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		return null;
	}
	
	/**
	 * 请求话费充值订单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward RechargeOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String userno=request.getParameter("sysNum");//系统编号
		String userName=request.getParameter("userName");//设备唯一标示或者用户名
		String productId=request.getParameter("productId");//产品id
		String productName=request.getParameter("productName");//话费充值商品编号为：10000000 Q币充值商品编号:10000001  流量商品编号:10000002
		String productBody=request.getParameter("productBody");//商品描述
		String amount=request.getParameter("amount");//商品的数量 话费充值为1;Q币充值为实际的充值Q币个数;流量充值为1;
		String price=request.getParameter("price");//充值总价 里
		String realPrice=request.getParameter("realPrice");//优惠后的总价 里
		String phoneNum=request.getParameter("phoneNum"); //充值手机号 或QQ号
		String phoneType=request.getParameter("phoneType");//运营商类型编码 0电信 1移动 2联通
		String areaId=request.getParameter("areaId");//省份编码
		String sign=request.getParameter("sign");//MD5(sysNum +price+realPrice+phoneNum+apkey)
		
		String otherInfo=request.getParameter("otherInfo");//ip信息
		
		OrderBean obean=new OrderBean();
		if(userno==null || "".equals(userno.trim()) ||
				userName==null || "".equals(userName.trim())||
				productId==null || "".equals(productId.trim()) ||
				productName==null || "".equals(productName.trim())||
				productBody==null || "".equals(productBody.trim())||
				amount==null || "".equals(amount.trim())||
				price==null || "".equals(price.trim())||
				realPrice==null || "".equals(realPrice.trim())||
				phoneNum==null || "".equals(phoneNum.trim())||
				phoneType==null || "".equals(phoneType.trim())||
				areaId==null || "".equals(areaId.trim()) ||
				sign==null || "".equals(sign.trim())){
			Log.info("app请求充值订单,参数错误，，");
			obean.setCode("1003");
			obean.setDesc("请求数据错误,请联系商家");
			String jsonString=JSON.toJSONString(obean);
			ResponseContent(jsonString,response);
			return null;
		}
		
		TPForm tp = MemcacheConfig.getInstance().getTP(userno);
		if (tp == null) {
			Log.info("app请求充值订单,接口商未配置，，userno:"+userno);
			obean.setCode("1003");
			obean.setDesc("无权限002,请联系商家");
			String jsonString=JSON.toJSONString(obean);
			ResponseContent(jsonString,response);
			return null;
		}
		if(!MD5.encode(userno +price+realPrice+phoneNum+apkey).equals(sign)){
			Log.info("app请求充值订单,md5签名错误，，userno:"+userno);
			obean.setCode("1003");
			obean.setDesc("认证错误1003,请联系商家");
			String jsonString=JSON.toJSONString(obean);
			ResponseContent(jsonString,response);
			return null;
		}
		DBService db=null;
		try {
			if(otherInfo==null || "".equals(otherInfo.trim())){
				otherInfo=Tools.getIpAddr(request);
			}else{
				otherInfo=new String(otherInfo.getBytes("iso-8859-1"),"gbk");
			}
			
			productBody=new String(productBody.getBytes("iso-8859-1"),"gbk");
			db=new DBService();
			
			if("10000001".equals(productName)){//Q币
				int facctfee = (int) FloatArith.mul(Integer.parseInt(amount)*1000, 1 - FloatArith.div(Double.valueOf(tp.getQbCommission()), 100));
				if(Integer.parseInt(price)!=(Integer.parseInt(amount)*1000) || Integer.parseInt(realPrice)!=facctfee){
					Log.info("app请求充值订单,Q币面值错误，，userno:"+userno);
					obean.setCode("1004");
					obean.setDesc("Q币面值错误");
					String jsonString=JSON.toJSONString(obean);
					ResponseContent(jsonString,response);
					return null;
				}
			}else if("10000000".equals(productName)){//话费充值
				Boolean boolean1=false;
				String[] result = new BiProd().getEmploy(db, phoneType, tp.getUser_site()+"", areaId,
						tp.getUser_no(), "2",Integer.parseInt(price)/1000, 1, Integer.parseInt(tp.getGroups()));
				if(result!=null || result.length>0){
					int facctfee = 0;
					if (Integer.parseInt(price) > 0 && Integer.parseInt(price) < 30000) {
						facctfee=Integer.parseInt(result[0]);
					}else{
						facctfee= (int) FloatArith.mul(Integer.parseInt(price), 1 - FloatArith.div(Double.valueOf(result[0]), 100));
					}
					if(Integer.parseInt(realPrice)!=facctfee){
						//错误
						boolean1=true;
					}
				}else{
					//错误
					boolean1=true;
				}
				if(boolean1){
					Log.info("app请求充值订单,话费面值错，，userno:"+userno);
					obean.setCode("1004");
					obean.setDesc("话费面值错");
					String jsonString=JSON.toJSONString(obean);
					ResponseContent(jsonString,response);
					return null;
				}
			}else if("10000002".equals(productName)){//流量充值
				//先不处理
			}else{
				Log.info("app请求充值订单,未知业务类型，，userno:"+userno);
				obean.setCode("1003");
				obean.setDesc("未知业务类型");
				String jsonString=JSON.toJSONString(obean);
				ResponseContent(jsonString,response);
				return null;
			}
			
			String payStatus="1004";//,1004:订单未支付
			String rechargeStatus="1004";//1004:订单未充值
			
			//订单只保存一个小时
			long orderStartTime =System.currentTimeMillis();
			long orderEndTime =orderStartTime+(60*60*1000);//一小时
			
			//订单号
			String orderNum=Tools.getNow4()+
							((int)(Math.random()*100000)+100000)+
							((char)(new Random().nextInt(26) + 
							(int)'a'))+((int)(Math.random()*100)+100)+
							((char)(new Random().nextInt(26) + (int)'A'));
			//支付宝签名信息
			DecimalFormat decimalFormat=new DecimalFormat("0.00");
			String alipay=ALiPayUtils.getSignOrderInfo("充值", "充值业务", decimalFormat.format(Float.parseFloat(realPrice)/1000)+"", orderNum,AlipayBack);
			//百度签名
			String bdPay=new SDKBaiduPay().getBdPayParameter("充值", orderNum,Tools.getNow3(), "充值业务",baiduBack,Integer.parseInt(realPrice)/10);
			
			StringBuffer buf=new StringBuffer("INSERT INTO app_RechargeOrder_"+orderNum.substring(2,6)+"("+
					"userno,userName,productId,productName,productBody,amount,price,realPrice,"+
					"phoneNum,orderNum,payStatus,rechargeStatus,orderStartTime,orderEndTime,createOrderTime,Spare1,phoneType,areaId,Spare2,aliPay"+
					") VALUES(");
			buf.append("'"+userno+"',");
			buf.append("'"+userName+"',");
			buf.append("'"+productId+"',");
			buf.append("'"+productName+"',");
			buf.append("'"+productBody+"',");
			buf.append("'"+amount+"',");
			buf.append("'"+price+"',");
			buf.append("'"+realPrice+"',");
			buf.append("'"+phoneNum+"',");
			buf.append("'"+orderNum+"',");
			buf.append("'"+payStatus+"',");
			buf.append("'"+rechargeStatus+"',");
			buf.append("'"+orderStartTime+"',");
			buf.append("'"+orderEndTime+"',");
			buf.append("'"+Tools.getNow3()+"',");
			buf.append("'"+bdPay+"',");
			buf.append("'"+phoneType+"',");
			buf.append("'"+areaId+"',");
			buf.append("'"+otherInfo+"',");
			buf.append("'"+alipay+"'");
			buf.append(")");
		
			db.update(buf.toString());
			
			OrderBean o=new OrderBean();
			o.setCode("1000");
			o.setDesc("请求成功订单信息可用");
			o.setOrderNum(orderNum);
			o.setProductId(productId);
			o.setProductName(productName);
			o.setProductBody(productBody);
			o.setPrice(price);
			o.setRealPrice(realPrice);
			o.setPhoneNum(phoneNum);
			o.setPayStatus(payStatus);
			o.setRechargeStatus(rechargeStatus);
			o.setOrderStartTime(orderStartTime+"");
			o.setOrderEndTime(orderEndTime+"");
			o.setAliPay(alipay);
			o.setBdPay(bdPay);
			
			String jsonString=JSON.toJSONString(o);
			ResponseContent(jsonString,response);
			return null;
		} catch (Exception e) {
			Log.error("app请求充值订单,系统异常，，userno:"+userno+",,,ex:"+e);
			obean.setCode("1003");
			obean.setDesc("系统异常,请稍候再试");
			String jsonString=JSON.toJSONString(obean);
			ResponseContent(jsonString,response);
			return null;
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
	}

	/**
	 * 获取Q币单价
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward getQbUnitPrice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		QbPrice qbprice=new QbPrice();
		
		String userno=request.getParameter("sysNum");//系统编号
		if(userno==null || "".equals(userno.trim())){
			Log.info("app请求获取Q币单价,参数错误，，");
			qbprice.setCode("1004");
			qbprice.setDesc("请求数据错误,请联系商家");
			String jsonString=JSON.toJSONString(qbprice);
			ResponseContent(jsonString,response);
			return null;
		}
		
		TPForm tp = MemcacheConfig.getInstance().getTP(userno);
		if (tp == null) {
			Log.info("app请求获取Q币单价,接口商未配置，，userno:"+userno);
			qbprice.setCode("1004");
			qbprice.setDesc("权限不足,请联系商家");
			String jsonString=JSON.toJSONString(qbprice);
			ResponseContent(jsonString,response);
			return null;
		}
		
		int price=1*1000;
		int facctfee = (int) FloatArith.mul(price, 1 - FloatArith.div(Double.valueOf(tp.getQbCommission()), 100));
		Log.info("app请求获取Q币单价,获取Q币单价成功，，userno:"+userno);
		qbprice.setCode("1000");
		qbprice.setDesc("获取成功");
		qbprice.setPrice(price+"");
		qbprice.setRealPrice(facctfee+"");
		String jsonString=JSON.toJSONString(qbprice);
		ResponseContent(jsonString, response);
		return null;
	}
	
	/**
	 * 根据手机号码前7位获取可充值面额 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward getPhoneProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		One one=new One();
		
		String phoneNum=request.getParameter("phoneNum");//充值手机号码11位
		String userno=request.getParameter("sysNum");//系统编号
		if(phoneNum==null || "".equals(phoneNum.trim()) || phoneNum.length()<7 || userno==null || "".equals(userno.trim())){
			Log.info("app请求获取手机号段,参数错误，，");
			one.setCode("1004");
			one.setDesc("请求数据错误,请联系商家");
			String jsonString=JSON.toJSONString(one);
			ResponseContent(jsonString,response);
			return null;
		}
		
		TPForm tp = MemcacheConfig.getInstance().getTP(userno);
		if (tp == null) {
			Log.info("app请求获取手机号段,接口商未配置，，userno:"+userno);
			one.setCode("1004");
			one.setDesc("权限不足,请联系商家");
			String jsonString=JSON.toJSONString(one);
			ResponseContent(jsonString,response);
			return null;
		}
		
		String phonString=phoneNum.substring(0, 7);
		BiProd biProd = new BiProd();
		String[] str = biProd.getPhoneInfo(phonString);
		if(str==null || str.length<=0){
			Log.info("app请求获取手机号段,未知号段,,,号码："+phoneNum+",,系统编号:"+userno);
			one.setCode("1004");
			one.setDesc("未知号码号段");
			String jsonString=JSON.toJSONString(one);
			ResponseContent(jsonString,response);
			return null;
		}
		String phonePid=str[0];
		String phoneType=str[1];
		String phoneArea="";
		if (phoneType.equals("0")) {
			phoneArea = "电信";
		} else if (phoneType.equals("1")) {
			phoneArea = "移动";
		} else  if (phoneType.equals("2")) {
			phoneArea = "联通";
		}else{
			phoneArea="未知";
		}
		one.setPhoneNum(phonString);
		one.setPhoneNumDesc(str[2]+" "+phoneArea);
		one.setAreaId(phonePid);
		one.setPhoneType(phoneType);
		
		DBService db=null;
		try {
			db=new DBService();
			List<String> miane=db.getStringList("SELECT Recharge_value FROM app_Denomination WHERE Operator='"+phoneType+"'");
			if(miane==null || miane.size()<=0){
				Log.info("app请求获取手机号段,服务端未配置面额,号码："+phoneNum+",,系统编号:"+userno);
				one.setCode("1004");
				one.setDesc("充值面额有误,请联系商家");
				String jsonString=JSON.toJSONString(one);
				ResponseContent(jsonString,response);
				return null;
			}
			
			List<OneList> arrysList=new ArrayList<OneList>();
			
			for(String s:miane){
				int money=Integer.parseInt(s) / 1000;
				String[] result = biProd.getEmploy(db, phoneType, tp.getUser_site()+"", phonePid,
						tp.getUser_no(), "2",money, 1, Integer.parseInt(tp.getGroups()));
				if(result==null || result.length<=0){
					Log.info("app请求获取手机号段,未能获取佣金,面额:"+money+",号码："+phoneNum+",,系统编号:"+userno);
					one.setCode("1004");
					one.setDesc("获取数据出错1004,请联系商家");
					String jsonString=JSON.toJSONString(one);
					ResponseContent(jsonString,response);
					return null;
				}
				int facctfee = 0;
				if (Integer.parseInt(s) > 0 && Integer.parseInt(s) < 30000) {
					facctfee=Integer.parseInt(result[0]);
				}else{
					facctfee= (int) FloatArith.mul(Integer.parseInt(s), 1 - FloatArith.div(Double.valueOf(result[0]), 100));
				}
				OneList oList=new OneList();
				oList.setPrice(s);
				oList.setRealPrice(facctfee+"");
				arrysList.add(oList);
			}
			Log.info("app请求获取手机号段,充值面额获取成功,号码："+phoneNum+",,系统编号:"+userno);
			one.setCode("1000");
			one.setDesc("充值面额获取成功");
			one.setPhoneProducts(arrysList);
			String jsonString=JSON.toJSONString(one);
			ResponseContent(jsonString,response);
			return null;
		} catch (Exception e) {
			Log.error("app请求获取手机号段,系统异常,号码："+phoneNum+",,系统编号:"+userno+",,,ex:"+e);
			one.setCode("1004");
			one.setDesc("系统异常,请联系商家");
			String jsonString=JSON.toJSONString(one);
			ResponseContent(jsonString,response);
			return null;
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
	}
	
	/**
	 * 响应客户端 app json 字符串
	 * @param jsonString json字符串
	 * @param response 
	 */
	@SuppressWarnings("unused")
	private void ResponseContent(String jsonString,HttpServletResponse response)
	{
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out=null;
		try {
			out=response.getWriter();
			out.write(jsonString);
			out.flush();
			out.close();
			Log.info("响应客户端app内容完成,json："+jsonString);
		} catch (IOException e) {
			Log.error("响应客户端app内容异常,,json："+jsonString+",,,,ex:"+e);
		}
	}

	/**
	 * 活动
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null;
	 */
	public ActionForward getActivityContent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String sysNum=request.getParameter("sysNum");
		if(sysNum==null || "".equals(sysNum.trim())){
			return null;
		}
		ActivityBean bean=new ActivityBean();
		
		
		
		return null;
		
	}
	
	public static void main(String[] args) {
		DecimalFormat decimalFormat=new DecimalFormat("0.00");
		
		System.out.println(decimalFormat.format(Float.parseFloat("1000")/1000));
	}
}
