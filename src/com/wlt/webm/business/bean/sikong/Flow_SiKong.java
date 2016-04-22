package com.wlt.webm.business.bean.sikong;

import java.util.List;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.tool.MD5;
import com.wlt.webm.tool.Tools;

public class Flow_SiKong {
	//测试
	private static final String userId="khtest";//用户名
	private static final String userPwd="123456";//MD5校验码 MD5(userPwd|timeStamp)
	private static final String serviceCode="test";//业务代码
	private static final String url="http://103.26.1.139:8080/flow_interface/cClientFlowOrderMountInfo.do";
	private static final String QueryURL="http://103.26.1.139:8080/flow_interface/cClientGetReportInfoByMsgId.do";
	//正式 
//	private static final String userId="whkj001";//用户名
//	private static final String userPwd="whkj001";//MD5校验码 MD5(userPwd|timeStamp)
//	private static final String serviceCode="whkj001";//业务代码
//	private static final String url="http://flow.hbsmservice.com:8080/flow_interface/cClientFlowOrderMountInfo.do";
//	private static final String QueryURL="http://flow.hbsmservice.com:8080/flow_interface/cClientGetReportInfoByMsgId.do";
	/**
	 * 思空流量订购
	 * @param msgId 订单号
	 * @param mobile 电话
	 * @param orderMmount 交易金额 元
	 * @return String 0成功  -1失败  2处理中  3用户余额不足
	 */
	public static String Recharge(String msgId,String mobile,String orderMmount){
		String timeStamp=Tools.getNow();
		String sign=MD5.encode(userPwd+"|"+timeStamp);
		
		StringBuffer buffer=new StringBuffer();
		buffer.append(url);
		buffer.append("?");
		buffer.append("timeStamp="+timeStamp);
		buffer.append("&userId="+userId);
		buffer.append("&userPwd="+sign);
		buffer.append("&mobile="+mobile);
		buffer.append("&orderMmount="+orderMmount);
		buffer.append("&orderTime=1");
		buffer.append("&serviceCode="+serviceCode);
		buffer.append("&msgId="+msgId);
		buffer.append("&effectiveDate=0");//立即生效 
		buffer.append("&extend=");
		
		Log.info("思空流量订购,订单号:"+msgId+",手机号:"+mobile+",充值(M):"+orderMmount+",充值请求url:"+buffer.toString());
		
		HttpClient client = null;
		GetMethod get = null;
		try {
			client = new HttpClient();
			get = new GetMethod(buffer.toString());
			// 链接时间 3秒，单位是毫秒
			client.getHttpConnectionManager().getParams().setConnectionTimeout(60 * 1000);
			// 读取时间 3秒，单位是毫秒
			client.getHttpConnectionManager().getParams().setSoTimeout(60 * 1000);
			int status = client.executeMethod(get);
			Log.info("思空流量订购,订单号:"+msgId+",手机号:"+mobile+",充值(M):"+orderMmount+",http响应状态:"+status);
			if (status == 200) {
				String result = get.getResponseBodyAsString();
				Log.info("思空流量订购,订单号:"+msgId+",手机号:"+mobile+",充值(M):"+orderMmount+",响应json字符串:"+result);
				if(result!=null && !"".equals(result)){
					SiKong_Bean bean=JSON.parseObject(result,SiKong_Bean.class);
					if("0000".equals(bean.getCode())){
						Log.info("思空流量订购,订单号:"+msgId+",手机号:"+mobile+",充值(M):"+orderMmount+",提交成功,调用查询接口");
						return "0";
					}else if("0013".equals(bean.getCode())){
						Log.info("思空流量订购,订单号:"+msgId+",手机号:"+mobile+",充值(M):"+orderMmount+",return -1 万恒账户余额不足");
						return "-1";//用户余额不足
					}else {
						Log.info("思空流量订购,订单号:"+msgId+",手机号:"+mobile+",充值(M):"+orderMmount+",return -1 提交失败,上游result code:"+bean.getCode());
						return "-1";//失败
					}
				}
			}
		} catch (Exception e) {
			Log.info("思空流量订购,订单号:"+msgId+",手机号:"+mobile+",充值(M):"+orderMmount+",ex:"+e);
		} finally {
			get.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
			if (null != client) {
				client = null;
			}
		}
//		Log.info("思空流量订购,订单号:"+msgId+",手机号:"+mobile+",充值(M):"+orderMmount+",调用查询接口");
//		return Query(msgId);
		return "2";
	}
	
	/**
	 * 思空 流量查询接口
	 * @param msgId 充值订单号
	 * @return String 0成功  -1失败  2处理中  3用户余额不足
	 */
	public static String Query(String msgId){
		String timeStamp=Tools.getNow();
		String sign=MD5.encode(userPwd+"|"+timeStamp);
		StringBuffer buffer=new StringBuffer();
		buffer.append(QueryURL);
		buffer.append("?");
		buffer.append("timeStamp="+timeStamp);
		buffer.append("&userId="+userId);
		buffer.append("&userPwd="+sign);
		buffer.append("&msgId="+msgId);
		buffer.append("&extend=");
		Log.info("思空流量查询,订单号:"+msgId+",充值请求url:"+buffer.toString());
		
		int con=0;
		while(con<=24){
			con++;
			if(con!=1){
				try {
					Thread.sleep(5000);
				} catch (Exception e) {}
			}
			HttpClient client = null;
			GetMethod get = null;
			try {
				client = new HttpClient();
				get = new GetMethod(buffer.toString());
				// 链接时间 3秒，单位是毫秒
				client.getHttpConnectionManager().getParams().setConnectionTimeout(20 * 1000);
				// 读取时间 3秒，单位是毫秒
				client.getHttpConnectionManager().getParams().setSoTimeout(20 * 1000);
				int status = client.executeMethod(get);
				Log.info("思空流量查询,订单号:"+msgId+",第:"+con+"次循环,http响应状态:"+status);
				if (status == 200) {
					String result = get.getResponseBodyAsString();
					Log.info("思空流量查询,订单号:"+msgId+",第:"+con+"次循环,响应json字符串:"+result);
					if(result!=null && !"".equals(result)){
						QueryResult bean=JSON.parseObject(result,QueryResult.class);
						if("0000".equals(bean.getCode())){
							if("".equals(bean.getResult()) || null==bean.getResult()){
								continue;
							}
							List<Result> rList=JSON.parseArray(bean.getResult(),Result.class);
							if("0".equals(rList.get(0).getErr())){
								Log.info("思空流量查询,订单号:"+msgId+",循环:"+con+"次,return 0 成功");
								return "0";
							}else if("45".equals(rList.get(0).getErr())){
								Log.info("思空流量查询,订单号:"+msgId+",循环:"+con+"次,return -1 失败");
								return "-1";
							}
						}
					}
				}
			} catch (Exception e) {
				Log.info("思空流量查询,订单号:"+msgId+",第:"+con+"次循环,ex:"+e);
			} finally {
				get.releaseConnection();
				((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
				if (null != client) {
					client = null;
				}
			}
		}
		Log.info("思空流量查询,订单号:"+msgId+",循环:"+con+"次,return 2 未知结果,当处理中");
		return "2";
	}
	
	public static void main(String[] args) throws InterruptedException {
		for(int i=0;i<1;i++){
			String orderString=Tools.getNow()+new Random(10000).nextInt();
			String rString=Recharge(orderString,"18926512055","30M");
			System.out.println("============================="+orderString+",state:"+rString);
			Thread.sleep(2*1000);
		}

//		Query("20150701135721");
	}
}
