package com.wlt.webm.business.bean.liansheng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.scputil.MD5Util;
import com.wlt.webm.tool.Tools;

/**
 * @author 施建桥
 * Recharge
	Correct
	Query
 */
public class Mobile_Interface {
	
	public static void main(String[] args) {
		String orderid=Tools.getNow()+""+((char)(new Random().nextInt(26) + (int)'a'))+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+1000);
	
//		System.out.println("订单号:"+orderid+",,充值结果:"+Recharge(orderid,"18824588427","30","1"));
		//20150723113431k1956I1085
		//20150724093417l1334B1048
		//20150724134815o1299E1065
	
//		System.out.println(Query("20150724134815o1299E1065"));
		
//		System.out.println(Correct("20150724134815o1299E1065", "30", "18824588427",""));
	}
	
	/**
	 * 商户号
	 */
	public static final String agentid="88800006";
	
	/**
	 * 商户秘钥
	 */
	public static final String merchantKey="ba83fa05ff75070e224c8699b52d4c5d";
	
	/**
	 * 充值url 
	 */
	public static final String R_URL="http://www.szsz1000.com/GateWay/Phone/Default.aspx";
	
	/**
	 * 订单查询URL
	 */
	public static final String Q_URL="http://www.szsz1000.com/GateWay/Phone/Search.aspx";
	
	/**
	 * 冲正URL
	 */
	public static final String C_URL="http://www.szsz1000.com/GateWay/Phone/UnRecharge.aspx";
	
	/**
	 * 冲正状态查询
	 */
	public static final String C_Q_URL="http://www.szsz1000.com/GateWay/Phone/UnSearch.aspx";
	
	/** 连胜移动充值返回码
	 *  001	接口关闭
		002	ip未被授权
		003	参数不完整
		004	MD5检验失败
		005	没有对应产品，不支持此金额充值
		006	余额不足
		007	提交失败
		008	提交成功
		010	没有此用户或没开通接口业务
		011	流水号已存在，请更换订单流水号
		012	不支持此地区号码
		013	提交的号码运营商和实际不符
		014	提交异常,请通过前台来确定是否提交成功
	 */
	
	/**
	 * 连胜移动充值   1连胜移动 2联通 3电信
	 * @param orderid  订单号
	 * @param mobilenum 充值号码
	 * @param money 充值金额
	 * @param telephonetype 充值类型  1连胜移动 2联通 3电信
	 * @return String  0成功  -1 失败  2卡单，异常  3余额不足
	 */
	public static String Recharge(String orderid,String mobilenum,String money,String telephonetype){
		//万恒0电信 1连胜移动 2联通  转换成连胜 1连胜移动 2联通 3电信
		if("0".equals(telephonetype)){
			telephonetype="3";
		}else if("1".equals(telephonetype)){
			telephonetype="1";
		}else if("2".equals(telephonetype)){
			telephonetype="2";
		}
		
		if(orderid==null || "".equals(orderid)
				||mobilenum==null || "".equals(mobilenum)
				|| money==null || "".equals(money) 
				|| telephonetype==null || "".equals(telephonetype)){
			Log.info("连胜移动充值,参数不足,,,,");
			return "-1";
		}
		
		StringBuffer buf=new StringBuffer();
		buf.append("agentid="+agentid);
		buf.append("&orderid="+orderid);
		buf.append("&money="+money);
		buf.append("&telephonetype="+telephonetype);
		buf.append("&mobilenum="+mobilenum);
		
		String verifyString=MD5Util.MD5Encode(buf.toString()+"&merchantKey="+merchantKey,"utf-8").toLowerCase();
		
		StringBuffer buffer=new StringBuffer();
		buffer.append(R_URL);
		buffer.append("?");
		buffer.append(buf);
		buffer.append("&verifyString="+verifyString);
		
		Log.info("连胜移动充值,订单号:"+orderid+",,号码:"+mobilenum+",,充值请求url:"+buffer.toString());
		
		HttpClient client = null;
		PostMethod post = null;
		try {
			client = new HttpClient();
			post = new PostMethod(buffer.toString());
			int status = client.executeMethod(post);
			Log.info("连胜移动充值,订单号:"+orderid+",,号码:"+mobilenum+",,http响应状态:"+status);
			if (status == 200) {
				String result = post.getResponseBodyAsString().replace(" ","").trim();
				if("001".equals(result) 
					|| "002".equals(result)
					|| "003".equals(result)
					|| "004".equals(result)
					|| "005".equals(result)
					|| "007".equals(result)
					|| "010".equals(result)
					|| "011".equals(result)
					|| "012".equals(result)
					|| "013".equals(result)){
					Log.info("连胜移动充值,充值失败,订单号:"+orderid+",,号码:"+mobilenum+",,响应状态:"+result+",,return -1 提交失败");
					return "-1";
				}else if("006".equals(result)){
					Log.info("连胜移动充值,充值失败,订单号:"+orderid+",,号码:"+mobilenum+",,响应状态:"+result+",,return 3 提交失败,余额不足");
					return "3";
				}else{
					//调用查询接口
					Log.info("连胜移动充值,订单号:"+orderid+",,号码:"+mobilenum+",,响应状态:"+result+",,调用查询接口");
				}
			}
		} catch (Exception e) {
			Log.error("连胜移动充值,系统异常,订单号:"+orderid+",,号码:"+mobilenum+",,ex:"+e);
		} finally {
			post.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
			if (null != client) {
				client = null;
			}
		}
		Log.info("连胜移动充值,调用查询接口,订单号:"+orderid+",,号码:"+mobilenum);
		return Query(orderid);
	}
	
	/**连胜移动订单查询接口
	 * @param orderid 订单号
	 * @return String  0成功  -1 失败  2卡单，异常  3余额不足
	 */
	public static final String Query(String orderid){
		
		StringBuffer buf=new StringBuffer();
		buf.append("agentid="+agentid);
		buf.append("&orderid="+orderid);
		
		String verifyString=MD5Util.MD5Encode(buf.toString()+"&merchantKey="+merchantKey,"utf-8").toLowerCase();
		
		StringBuffer buffer=new StringBuffer();
		buffer.append(Q_URL);
		buffer.append("?");
		buffer.append(buf);
		buffer.append("&verifyString="+verifyString);
		
		Log.info("连胜移动订单查询,订单号:"+orderid+",,查询请求url:"+buffer.toString());
		
		int con=0;
		while(con<24){
			con++;
			try {
				Thread.sleep(5*1000);
			} catch (Exception e) {}
			
			HttpClient client = null;
			PostMethod post = null;
			try {
				client = new HttpClient();
				post = new PostMethod(buffer.toString());
				int status = client.executeMethod(post);
				Log.info("连胜移动订单查询,订单号:"+orderid+",,第:"+con+"次循环查询,,http响应状态:"+status);
				if (status == 200) {
					String result = post.getResponseBodyAsString().replace(" ","").trim();
					if("007".equals(result) || "077".equals(result)){
						Log.info("连胜移动订单查询,订单号:"+orderid+",,第:"+con+"次循环查询,,订单响应状态:"+result+",,return -1 订单失败处理");
						return "-1";
					}else if("008".equals(result)){
						Log.info("连胜移动订单查询,订单号:"+orderid+",,第:"+con+"次循环查询,,订单响应响应状态:"+result+",,return 0 订单成功处理");
						return "0";
					}else{ 
						Log.info("连胜移动订单查询,订单号:"+orderid+",,第:"+con+"次循环查询,,订单响应响应状态:"+result+",,状态不明确,继续查询,,");
					}
				}
			} catch (Exception e) {
				Log.error("连胜移动订单查询,订单号:"+orderid+",,第:"+con+"次循环查询,,系统异常,ex:"+e);
			} finally {
				post.releaseConnection();
				((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
				if (null != client) {
					client = null;
				}
			}
		}
		Log.info("连胜移动订单查询,订单号:"+orderid+",,第:"+con+"次循环查询,,无明确结果,return 2 当订单处理中状态");
		return "2";
	}
	
	/**连胜移动冲正接口
	 * @param orderid  冲正订单号
	 * @param money  冲正金额
	 * @param mobilenum 冲正号码
	 * @return String  0成功  -1 失败  2卡单，异常
	 */
	@SuppressWarnings("unchecked")
	public static final String Correct_LianSheng(String orderid,String money,String mobilenum){
		if(orderid==null || "".equals(orderid) 
			|| money==null || "".equals(money)
			|| mobilenum==null || "".equals(mobilenum)){
			Log.info("连胜移动冲正,参数不足,,,");
			return "-1";
		}
		StringBuffer buf=new StringBuffer();
		buf.append("agentid="+agentid);
		buf.append("&orderid="+orderid);
		buf.append("&money="+money);
		buf.append("&mobilenum="+mobilenum);
		
		String verifyString=MD5Util.MD5Encode(buf.toString()+"&merchantKey="+merchantKey,"utf-8").toLowerCase();
		
		StringBuffer buffer=new StringBuffer();
		buffer.append(C_URL);
		buffer.append("?");
		buffer.append(buf);
		buffer.append("&verifyString="+verifyString);
		
		Log.info("连胜移动订单冲正,订单号:"+orderid+",,冲正请求url:"+buffer.toString());
		
		HttpClient client = null;
		PostMethod post = null;
		try {
			client = new HttpClient();
			post = new PostMethod(buffer.toString());
			int status = client.executeMethod(post);
			Log.info("连胜移动订单冲正,订单号:"+orderid+",,http响应状态:"+status);
			if (status == 200) {
				String result = post.getResponseBodyAsString().trim();
				if(result==null || "".equals(result)){
					Log.info("连胜移动订单冲正,订单号:"+orderid+",,无响应内容,reutrn -1 冲正失败处理");
					return "-1";
				}
				
				Log.info("连胜移动订单冲正,订单号:"+orderid+",,响应内容:"+result);
				
				Map<String,String> resultMap=new HashMap<String,String>();
				Document docResult=DocumentHelper.parseText(result);
				Element rootResult = docResult.getRootElement();
				List<Element> results=rootResult.elements();
				for(Element item1:results){
					resultMap.put(item1.getName(), item1.getText());
				}
				result=resultMap.get("ret_code");
				if("001".equals(result) 
					|| "002".equals(result)
					|| "003".equals(result)
					|| "004".equals(result)
					|| "007".equals(result)
					|| "010".equals(result)
					|| "011".equals(result)
					|| "012".equals(result)){
					Log.info("连胜移动订单冲正,订单号:"+orderid+",,result:"+result+",,reutrn -1 冲正失败处理");
					return "-1";
				}else{
					//调用查询接口
					Log.info("连胜移动订单冲正,订单号:"+orderid+",,result:"+result+",,调用查询接口");
				}
			}else{
				Log.info("连胜移动订单冲正,订单号:"+orderid+",,http响应状态:"+status+",,reutrn -1 冲正失败处理");
				return "-1";
			}
		} catch (Exception e) {
			Log.info("连胜移动订单冲正,订单号:"+orderid+",,系统异常,ex:"+e);
		} finally {
			post.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
			if (null != client) {
				client = null;
			}
		}
		
		Log.info("连胜移动订单冲正,订单号:"+orderid+",,调用冲正状态查询接口");
		return Correct_Query(orderid);
	}
	
	/**冲正状态查询
	 * @param orderid 订单号
	 * @return   0成功  -1 失败  2卡单，异常
	 */
	@SuppressWarnings("unchecked")
	public static String Correct_Query(String orderid){
		StringBuffer buf=new StringBuffer();
		buf.append("agentid="+agentid);
		buf.append("&orderid="+orderid);
		
		String verifyString=MD5Util.MD5Encode(buf.toString()+"&merchantKey="+merchantKey,"utf-8").toLowerCase();
		
		StringBuffer buffer=new StringBuffer();
		buffer.append(C_Q_URL);
		buffer.append("?");
		buffer.append(buf);
		buffer.append("&verifyString="+verifyString);
		
		Log.info("连胜移动冲正订单查询,订单号:"+orderid+",,冲正查询请求url:"+buffer.toString());
		
		int con=0;
		while(con<24){
			con++;
			try {
				Thread.sleep(5*1000);
			} catch (Exception e) {}
			
			HttpClient client = null;
			PostMethod post = null;
			try {
				client = new HttpClient();
				post = new PostMethod(buffer.toString());
				int status = client.executeMethod(post);
				Log.info("连胜移动冲正订单查询,订单号:"+orderid+",,第:"+con+"次循环查询,,http响应状态:"+status);
				if (status == 200) {
					String result = post.getResponseBodyAsString().trim();
					if(result==null || "".equals(result)){
						Log.info("连胜移动冲正订单查询,订单号:"+orderid+",,第:"+con+"次循环查询,,result 为空");
						continue;
					}
					
					Map<String,String> resultMap=new HashMap<String,String>();
					Document docResult=DocumentHelper.parseText(result);
					Element rootResult = docResult.getRootElement();
					List<Element> results=rootResult.elements();
					for(Element item1:results){
						resultMap.put(item1.getName(), item1.getText());
					}
					result=resultMap.get("ret_code");
					if("007".equals(result)){
						Log.info("连胜移动冲正订单查询,订单号:"+orderid+",,第:"+con+"次循环查询,,订单响应状态:"+result+",,return -1 订单失败处理");
						return "-1";
					}else if("008".equals(result)){
						Log.info("连胜移动冲正订单查询,订单号:"+orderid+",,第:"+con+"次循环查询,,订单响应响应状态:"+result+",,return 0 订单成功处理");
						return "0";
					}else{
						Log.info("连胜移动冲正订单查询,订单号:"+orderid+",,第:"+con+"次循环查询,,订单响应响应状态:"+result+",,继续循环查询");
					}
				}
			} catch (Exception e) {
				Log.info("连胜移动冲正订单查询,订单号:"+orderid+",,第:"+con+"次循环查询,,系统异常,ex:"+e);
			} finally {
				post.releaseConnection();
				((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
				if (null != client) {
					client = null;
				}
			}
		}
		Log.info("连胜移动冲正订单查询,订单号:"+orderid+",,第:"+con+"次循环查询,,无明确结果,return 2 当订单处理中状态");
		return "2";
	}
	
	/**http 冲正业务处理
	 * @param userno 用户系统编号
	 * @param tradeserial 
	 * @return String 0 成功  -1 失败  2异常
	 */
	public static String Correct_Orders(String userno,String tradeserial)
	{
		Log.info("连胜移动订单冲正，，进入退费业务处理模块,,,开始退费,,,退费用户userno="+userno+",,,tradeserial="+tradeserial);
		String date=Tools.getNow3().substring(2,6);
		String orderTable="wht_orderform_"+date;
		String acctTable="wht_acctbill_"+date;
		//收支分开, 失败重新生成退款记录
		//修改订单状态
		DBService db=null;
		try{
			db=new DBService();
			db.setAutoCommit(false);
			Log.info("连胜移动订单冲正，，，进入退费业务处理模块,,,开始退费,进入,db.setAutoCommit(false);事物,退费用户userno="+userno+",,,tradeserial="+tradeserial);
			String sql1="update "+orderTable+" set state=5 where tradeserial='"+tradeserial+"'";
			db.update(sql1);
			String newtime=Tools.getNow3();
			
			String sql0="select childfacct,infacctfee,tradeaccount from " +
			acctTable+" where tradetype=4 and tradeserial='"+tradeserial+"'";
			String[] str=db.getStringArray(sql0);
			if(null==str){
				return "2";
			}
			String fialAcct1=Tools.getAccountSerial(Tools.getNow3(), "TY"+str[2].substring(1, 11));
			String ss="select accountleft from wht_childfacct where childfacct='"+str[0]+"'";
			long userleft=db.getLong(ss);
			if(userleft==-1){
				return "2";
			}
			Object[] acctObj1={null,str[0],fialAcct1,str[2],newtime,Integer.parseInt(str[1]),Integer.parseInt(str[1]),
					0,"移动话费(话费冲正)",0,newtime,userleft+Integer.parseInt(str[1]),tradeserial,str[0],2};
			db.logData(15, acctObj1, acctTable);
			db.update("update wht_childfacct set accountleft=accountleft+"+Integer.parseInt(str[1])+
					" where childfacct='"+str[0]+"'");
			
			String sql4="select childfacct,infacctfee from " +
			acctTable+" where tradetype=15 and tradeserial='"+tradeserial+"'";
			String[] str1=db.getStringArray(sql4);
			if(null!=str1){
				String fialAcct2=Tools.getAccountSerial(newtime, "TY"+str[2].substring(0, 10));	
				String bb="select accountleft from wht_childfacct where childfacct='"+str1[0]+"'";
				long parentleft=db.getLong(bb);
				Object[] acctObjP={null,str1[0],fialAcct2,str[2],newtime,Integer.parseInt(str1[1]),Integer.parseInt(str1[1]),
						0,"移动话费(交易撤销)",0,newtime,parentleft-Integer.parseInt(str1[1]),
						tradeserial,str[0],1};
				db.logData(15, acctObjP, acctTable);
				db.update("update wht_childfacct set accountleft=accountleft-"+Integer.parseInt(str1[1])+
						" where childfacct='"+str1[0]+"'");
			}
			db.update("insert into wlt_revertlimit values(null,'" + userno + "','" +Tools.getNow3().substring(0,6)+ "')");
			//收支分开, 失败重新生成退款记录 结束
			db.commit();
			Log.info("进入连胜移动冲正，，，进入退费业务处理模块,,,开始退费,进入,db.commit();冲正业务处理成功，退费用户userno="+userno+",,,tradeserial="+tradeserial);
			return "0";
		}catch(Exception ex){
			db.rollback();
			Log.error("进入连胜移动冲正，，，进入退费业务处理模块,,,开始退费,进入,db.rollback();冲正业务处理异常，退费用户userno="+userno+",,,tradeserial="+tradeserial,ex);
			return "2";
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
	}

	/**
	 * 连胜移动冲正分发
	 * @param orderid 订单
	 * @param money 钱 
	 * @param mobilenum 号码
	 * @param userno 用户
	 * @return String 0冲正成功  -1冲正失败  2冲正异常
	 */
	public static String Correct(String orderid,String money,String mobilenum,String userno){
		String r=Correct_LianSheng(orderid,money,mobilenum);
		if("0".equals(r)){
			return Correct_Orders(userno,orderid);
		}else{
			return r;
		}
	}
}
