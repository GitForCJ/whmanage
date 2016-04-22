package com.wlt.webm.AccountInfo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.util.MD5;
import com.wlt.webm.util.Tools;

/**
 * @author adminA
 * 亿快  移动充值
 */
public class NineteenRecharge 
{
	/**
	 * 代理号码
	 */
	public static String agttel="15012880537";
	/**
	 * 密码
	 */
	public static String pwd="999999";
	/**
	 * 密钥
	 */
	public static String key="e6ec3f310f07ac1d87d22dcd82340bee";
	
	/**
	 * 话费充值 
	 * @param orderNum 
	 * @param phoneNum 
	 * @param money 
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static String getOrderState(String orderNum,String phoneNum,String money)
	{
		Log.info("进入亿快移动充值，getOrderState，方法,,订单号："+orderNum);
		if(orderNum==null || "".equals(orderNum) || phoneNum==null || "".equals(phoneNum) || money==null || "".equals(money)){
			Log.info("进入亿快移动充值，getOrderState，方法，，缺少参数");
			return "-1";
		}
		String URL="http://www.ek10010.com:8888/api/Recharge.php";
		String sign=MD5.encode(agttel+phoneNum+money+pwd+orderNum+key);
		sign=sign.toUpperCase();
		StringBuffer buf=new StringBuffer();
		buf.append(URL);
		buf.append("?");
		buf.append("agttel="+agttel);
		buf.append("&phone="+phoneNum);
		buf.append("&money="+money);
		buf.append("&pwd="+pwd);
		buf.append("&sn="+orderNum);
		buf.append("&sign="+sign);
		
		Log.info("进入亿快移动充值，getOrderState，方法，，请求地址："+buf.toString());
		
		Map<String, String> map =null;
		HttpClient client=null;
		GetMethod post =null;
		try{
			client= new HttpClient();
			post=new GetMethod(buf.toString());
			int status = client.executeMethod(post);
			Log.info("进入亿快移动充值，getOrderState，方法，，发送请求，，订单号："+orderNum);
			if(status==200){
				map=new HashMap<String, String>();
				Log.info("进入亿快移动充值，getOrderState，方法，，http状态为200,,,订单号："+orderNum);
//				String result = post.getResponseBodyAsString().trim();
				InputStream in=post.getResponseBodyAsStream();
				String result=inputStream2String(in,"utf-8");
//				result=new String(result.getBytes("ISO-8859-1"),"utf-8");
				Log.info("进入亿快移动充值，getOrderState，方法，,,订单号："+orderNum+",,返回xml字符串："+result);
				Document docResult=DocumentHelper.parseText(result);
				Element rootResult = docResult.getRootElement();
				List<Element> results=rootResult.elements();
				Log.info("进入亿快移动充值，getOrderState，方法，解析xml字符串，订单号："+orderNum);
				for(Element item1:results){
					map.put(item1.getName(), item1.getText());
				}
			}
		}catch (Exception e) {
			Log.info("进入亿快移动充值，getOrderState，方法，"+orderNum+"，http请求异常，，"+e);
			return "-2";
		}finally{
			post.releaseConnection();
			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
			if(null!=client){
				client=null;
			}
		}
		if(map!= null && "10000000".equals(map.get("retcode"))){
			Log.info("进入亿快移动充值，getOrderState，方法，"+orderNum+"，解析xml字符串，retcode=10000000,订单提交成功,,账户余额balance="+map.get("balance"));
			int con=0;
			while(true){ //循环 200秒
				try{
					if(con>6){
						return "-2";//超时
					}
					if(con==0){
						Thread.sleep(3*1000);
					}else{
						Thread.sleep(30*1000);
					}
					int phoneState=query(orderNum,Integer.parseInt(money),phoneNum);
					if(phoneState==0){ //成功
						Log.info("亿快移动充值,订单提交成功,循环查询充值，订单号："+orderNum+",金额："+money+",手机号码:"+phoneNum+",第"+(con+1)+"次循环,充值成功");
						return "0";
					}
					if(phoneState==1){//失败
						Log.info("亿快移动充值,订单提交成功,循环查询充值，订单号："+orderNum+",金额："+money+",手机号码:"+phoneNum+",第"+(con+1)+"次循环,充值失败");
						return "-1";
					}
					con++;
				}catch(Exception e){
					Log.info("亿快移动充值,订单提交成功,循环查询充值，订单号："+orderNum+",金额："+money+",手机号码:"+phoneNum+",第"+(con+1)+"次循环，，，异常"+e);
				}
			}
		}
		else{
			Log.info("进入亿快移动充值，getOrderState，方法，"+orderNum+"，解析xml字符串，retcode="+map.get("retcode")+",订单提交失败");
			return "-1";
		}
	}
	
	
	/**
	 * 话费冲正  订单状态 000成功   001失败
	 * @param userno 
	 * @param phoneNum 
	 * @param money 
	 * @param tradeserial 
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public static String reverseServices(String tradeserial,String phoneNum,String money)
	{
		Log.info("进入亿快移动冲正入库订单，tradeserial="+tradeserial+"，冲正号码："+phoneNum);
		String witeoff="";
		String userno="";
		DBService db=null;
		try{
			db=new DBService();
			String[] ssaa=db.getStringArray("select writeoff,userno from wht_orderform_"+Tools.getNow3().substring(2,6)+" where tradeserial='"+tradeserial+"'");
			if(ssaa==null || "".equals(ssaa) || ssaa.length<=0){
				Log.info("进入亿快移动冲正，，订单，，tradeserial="+tradeserial+"，不存在");
				return "001";
			}
			witeoff=ssaa[0];
			userno=ssaa[1];
			if(db.update("INSERT INTO wht_yikuai_reverse(phone,datetimes) VALUES('"+phoneNum+"','"+Tools.getNow3()+"')")<=0)
			{
				Log.info("进入亿快移动冲正入库订单，，tradeserial="+tradeserial+"，入库失败，，冲正号码："+phoneNum);
				return "001";
			}
			Log.info("进入亿快移动冲正入库订单，，tradeserial="+tradeserial+"，入库成功，，冲正号码："+phoneNum);
		}catch(Exception e){
			Log.info("进入亿快移动冲正入库订单，，tradeserial="+tradeserial+"，入库异常,，冲正号码："+phoneNum+",,,,,e:"+e);
			return "001";
		}finally{
			if(null!=db)
				db.close();
		}
		
		Log.info("进入亿快移动冲正，reverse，方法，，tradeserial="+tradeserial+"，冲正号码："+phoneNum);
		if( phoneNum==null || "".equals(phoneNum) || money==null || "".equals(money)){
			Log.info("进入亿快移动冲正，reverse，方法，，tradeserial="+tradeserial+"，，缺少参数，冲正号码："+phoneNum);
			return "001";
		}
		String URL="http://www.ek10010.com:8888/api/Reverse.php";
		String sign=MD5.encode(agttel+phoneNum+money+pwd+key);
		sign=sign.toUpperCase();
		StringBuffer buf=new StringBuffer();
		buf.append(URL);
		buf.append("?");
		buf.append("agttel="+agttel);
		buf.append("&phone="+phoneNum);
		buf.append("&money="+money);
		buf.append("&pwd="+pwd);
		buf.append("&sign="+sign);
		
		Log.info("进入亿快移动冲正，reverse，方法，，tradeserial="+tradeserial+"，冲正号码："+phoneNum+"，请求地址："+buf.toString());
		
		Map<String, String> map = new HashMap<String, String>();
		HttpClient client=null;
		GetMethod post =null;
		try{
			client= new HttpClient();
			post=new GetMethod(buf.toString());
			int status = client.executeMethod(post);
			Log.info("进入亿快移动冲正，reverse，方法，tradeserial="+tradeserial+"，，，发送请求，，冲正冲正："+phoneNum);
			if(status==200){
				Log.info("进入亿快移动冲正，reverse，方法，，tradeserial="+tradeserial+"，冲正号码："+phoneNum+"，http状态为200");
//				String result = post.getResponseBodyAsString();
				InputStream in=post.getResponseBodyAsStream();
				String result=inputStream2String(in,"utf-8");
				Log.info("进入亿快移动冲正，reverse，方法，，tradeserial="+tradeserial+"，冲正号码："+phoneNum+"，返回xml字符串："+result);
				Document docResult=DocumentHelper.parseText(result);
				Element rootResult = docResult.getRootElement();
				List<Element> results=rootResult.elements();
				Log.info("进入亿快移动冲正，reverse，方法，，tradeserial="+tradeserial+"，解析xml字符串，，冲正号码："+phoneNum);
				for(Element item1:results){
					map.put(item1.getName(), item1.getText());
				}
			}
		}catch (Exception e) {
			Log.info("进入亿快移动冲正，reverse，方法，，tradeserial="+tradeserial+"，冲正号码："+phoneNum+"，http请求异常，，"+e);
			return "001";
		}finally{
			post.releaseConnection();
			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
			if(null!=client){
				client=null;
			}
		}
		if(map!= null && map.get("retcode").equals("10000000")){
			Log.info("进入亿快移动冲正，reverse，方法，，tradeserial="+tradeserial+"，冲正号码："+phoneNum+"，解析xml字符串，retcode=10000000,订单提交成功");
			int con=0;
			while(true){ //循环 200秒
				try{
					if(con>6){
						return "-2";//超时
					}
					if(con==0){
						Thread.sleep(3*1000);
					}else{
						Thread.sleep(30*1000);
					}
					int phoneState=query(witeoff,Integer.parseInt(money),phoneNum);
					if(phoneState==2){ //冲正成功
						Log.info("进入亿快移动冲正，reverse，方法,循环查询冲正，订单号："+witeoff+",金额："+money+",手机号码:"+phoneNum+",第"+(con+1)+"次循环,冲正成功");
						break;
					}
					if(phoneState==0){//冲正失败
						return "001";
					}
					con++;
				}catch(Exception e){
					Log.info("进入亿快移动冲正，reverse，方法,循环查询冲正，订单号："+witeoff+",金额："+money+",手机号码:"+phoneNum+",第"+(con+1)+"次循环，，，异常"+e);
				}
			}
			Log.info("进入亿快移动冲正，上游冲正成，，，进入冲正退费业务处理模块，，，BusinessProcessing，，，冲正退费用户userno="+userno+",,,tradeserial="+tradeserial);
			return BusinessProcessing(userno,tradeserial);//000成功  001失败
		}
		else{
			Log.info("进入亿快移动冲正，reverse，方法，冲正号码："+phoneNum+"，解析xml字符串，retcode="+map.get("retcode")+",订单提交失败");
			return "001";
		}
	}
	
	/**
	 * 亿快 查询功能   0成功，，，1失败，，9处理中
	 * @param orderNum
	 * @param money  金额 元 
	 * @param phone 
	 * @return int 1交易成功，1交易失败，-1处理中
	 */
	@SuppressWarnings("unchecked")
	public static int query(String orderNum,int money,String phone) 
	{
		Log.info("进入亿快移动查询，query，方法");
		if(orderNum==null || "".equals(orderNum) || money<=0 || phone==null || "".equals(phone)){
			Log.info("进入亿快移动查询，query，，缺少参数");
			return -1;
		}
		String URL="http://www.ek10010.com:8888/api/Mut_query.php";
		String sign=MD5.encode(agttel+phone+money+orderNum+key);
		sign=sign.toUpperCase();
		StringBuffer buf=new StringBuffer();
		buf.append(URL);
		buf.append("?");
		buf.append("agttel="+agttel);
		buf.append("&phone="+phone);
		buf.append("&money="+money);
		buf.append("&sn="+orderNum);
		buf.append("&sign="+sign);
		
		Log.info("进入亿快移动查询，query，，请求地址："+buf.toString());
		
		Map<String, String> map=null;
		HttpClient client=null;
		GetMethod post =null;
		try{
			client= new HttpClient();
			post=new GetMethod(buf.toString());
			int status = client.executeMethod(post);
			Log.info("进入亿快移动查询，query，，，发送请求，，");
			if(status==200){
				map = new HashMap<String, String>();
				Log.info("进入亿快移动查询，query，，，http状态为200");
//				String result = post.getResponseBodyAsString();
				InputStream in=post.getResponseBodyAsStream();
				String result=inputStream2String(in,"utf-8");
				Log.info("进入亿快移动查询，query，，返回xml字符串："+result);
				Document docResult=DocumentHelper.parseText(result);
				Element rootResult = docResult.getRootElement();
				List<Element> results=rootResult.elements();
				Log.info("进入亿快移动查询，query，，解析xml字符串，");
				for(Element item1:results){
					map.put(item1.getName(), item1.getText());
				}
			}
		}catch (Exception e) {
			Log.info("进入亿快移动查询，query，，，http请求异常，，"+e);
			return -1;
		}finally{
			post.releaseConnection();
			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
			if(null!=client){
				client=null;
			}
		}
		if(map!= null && map.get("retcode").equals("10000000")){
			Log.info("进入亿快移动查询，query，，解析xml字符串，retcode=10000000,订单提交成功");
			if("0".equals(map.get("state"))){
				return 0;//交易成功
			}else if("1".equals(map.get("state")) || "8".equals(map.get("state"))){
				return 1;//交易失败
			}else if("2".equals(map.get("state")) || "3".equals(map.get("state"))){
				return 2;//冲正已受理 or 冲正已成功
			}else{
				return -1;
			}
		}
		else{
			Log.info("进入亿快移动查询，query，，解析xml字符串，retcode="+map.get("retcode")+",订单提交失败");
			return -1;
		}
	}
	
	/**http 冲正业务处理
	 * @param userno 用户系统编号
	 * @param tradeserial 
	 * @return String 000 成功  001 失败
	 */
	public static String BusinessProcessing(String userno,String tradeserial)
	{
		Log.info("进入亿快移动冲正，上游冲正成，，，进入退费业务处理模块,,,开始退费,,,退费用户userno="+userno+",,,tradeserial="+tradeserial);
		String date=Tools.getNow3().substring(2,6);
		String orderTable="wht_orderform_"+date;
		String acctTable="wht_acctbill_"+date;
		//收支分开, 失败重新生成退款记录
		//修改订单状态
		DBService db=null;
		try{
		db=new DBService();
		db.setAutoCommit(false);
		Log.info("进入亿快移动冲正，上游冲正成，，，进入退费业务处理模块,,,开始退费,进入,db.setAutoCommit(false);事物,退费用户userno="+userno+",,,tradeserial="+tradeserial);
		String sql1="update "+orderTable+" set state=5 where tradeserial='"+tradeserial+"'";
		db.update(sql1);
		String newtime=Tools.getNow3();
//		String tableName="wht_acctbill_"+newtime.substring(2, 6);
		
		String sql0="select childfacct,infacctfee,tradeaccount from " +
		acctTable+" where tradetype=4 and tradeserial='"+tradeserial+"'";
		String[] str=db.getStringArray(sql0);
		if(null==str){
			return "001";
		}
		String fialAcct1=Tools.getAccountSerial(Tools.getNow3(), "TY"+str[2].substring(1, 11));
		String ss="select accountleft from wht_childfacct where childfacct='"+str[0]+"'";
		long userleft=db.getLong(ss);
		if(userleft==-1){
			return "001";
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
		Log.info("进入亿快移动冲正，上游冲正成，，，进入退费业务处理模块,,,开始退费,进入,db.commit();冲正业务处理成功，返回000,退费用户userno="+userno+",,,tradeserial="+tradeserial);
		return "000";
		}catch(Exception ex)
		{
			db.rollback();
			Log.error("进入亿快移动冲正，上游冲正成，，，进入退费业务处理模块,,,开始退费,进入,db.rollback();冲正业务处理异常，返回000,退费用户userno="+userno+",,,tradeserial="+tradeserial,ex);
			return "001";
		}finally{
			if(null!=db){
				db.close();
			}
		}
	}
	
	public static void main(String[] arg){
		BusinessProcessing("0000000204","141027183527448803400001");
	}
	
	
    /**http 返回 InputStream  转 string
     * @param in
     * @param encoding
     * @return string
     * @throws IOException
     */
    public static String inputStream2String (InputStream in , String encoding) throws IOException {
	    StringBuffer out = new StringBuffer();
	    InputStreamReader inread = new InputStreamReader(in,encoding);
	    char[] b = new char[4096];
	    for (int n; (n = inread.read(b)) != -1;) {
	        out.append(new String(b, 0, n));
	    }
	    return out.toString();
    } 

}
