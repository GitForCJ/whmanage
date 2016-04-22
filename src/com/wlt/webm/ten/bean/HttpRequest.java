package com.wlt.webm.ten.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.SCPConstants;
import com.wlt.webm.ten.service.TenpayConfigParser;
import com.wlt.webm.ten.service.TenpayXmlPath;
import com.wlt.webm.tool.Tools;

/**
 * 
 * @author lenovo
 *
 */
public class HttpRequest {
	
	
	public static Map<String ,String> maps=new HashMap<String, String>();
	

	/**
	 * 将链接以流的形式传送到TX的服务端
	 * 
	 * @param GET_URL
	 * @param order
	 * @param qq
	 * @return
	 */
	public static  String readContentFromGet(String GET_URL, String order,
			String qq) {
		TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
		String host = TenpayConfigParser.getConfig().getQbhost().trim();
		String actionUrl=host+"/jf/qb/qbResult.do?"+GET_URL;
		Log.info("请求url:"+actionUrl);
		int i = 0;
		String result = null;
		StringBuffer sf = new StringBuffer();

		DBService db=null;
		try {
			URL getUrl = new URL(actionUrl);
			// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型
			HttpURLConnection connection = (HttpURLConnection) getUrl
					.openConnection();
			connection.setRequestMethod("POST");
			connection.connect();
			if (!(connection.getResponseCode() == HttpURLConnection.HTTP_OK)) {
				Log.info("---不能连接");
				sf.delete(0, sf.length());
				sf.append("<script language=javascript>window.location.href='");
				sf.append(host + "/qb/fill.do?order=0&qq=" + qq);
				sf.append("';</script>");
				
				return sf.toString();
			}
			
			while (true) {
				Log.info("Qb进入获取");
				i++;
				Thread.sleep(1000);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream(),
								"UTF-8"));
				String lines;
				sf.delete(0, sf.length());
				while ((lines = reader.readLine()) != null) {
					 sf.append(lines);
				}
				if (null != sf && sf.length() > 0) {
					reader.close();
					connection.disconnect();
					break;
				}
				if (i > 10) {
					sf.delete(0, sf.length());
					sf.append("<script language=javascript>window.location.href='");
					sf.append(host + "/qb/fill.do?order=1&qq="
							+ qq + "&tran=" + order);
					sf.append("';</script>");
					//发送扣费请求
					String sql="select * from wlt_orderForm_"+Tools.getNow3().substring(2, 6)+" where tradeserial=? and state in(0,4)";
		            Object[] params={order};
		            if(!getRecord(sql,params,"1")){
		    			//向SCP发送消息进行扣费动作
		            	HttpServletRequest request=null;
						new ToScpMsg(order,request,qq).start();
		            }
					Log.info("Q币充值返回超时 " + order);
					return sf.toString();
				}
			}
			Log.info(sf.toString());
			result = sf.toString();
			System.out.println("======="+result);
		    String ret=result.substring(result.indexOf("ret=")+4,result.indexOf("&mch_id"));
		    System.out.println("ret:"+ret);
			if("0".equals(ret)){//
				String sql="select * from wlt_orderForm_"+Tools.getNow3().substring(2, 6)+" where tradeserial=? and state in(0,4)";
	            Object[] params={order};
	            if(!getRecord(sql,params,"1")){
	    			//向SCP发送消息进行扣费动作
	            	HttpServletRequest request=null;
					new ToScpMsg(order,request,qq).start();
	            }
				String sql1="update wlt_orderForm_"+Tools.getNow().substring(2, 6)+" set state=0 ,chgtime='"+Tools.getNow3()+"' where tradeserial='"+order+"'";
				 db =new DBService();
				db.update(sql1);
			}
		} catch (Exception e) {
			Log.error("Q币充值HttpRequest Exception:" + e.toString());
			sf.delete(0, sf.length());
			sf.append("<script language=javascript>window.location.href='");
			sf.append(host + "/qb/fill.do?order=0&qq=" + qq);
			sf.append("';</script>");
			return sf.toString();
		}finally{
			if(db!=null){
			db.close();
			}
		}
		return result;
	}
	
	/**
	 * 测试用
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// String get="http://esales.qq.com/cgi-bin/esales_sell_dir.cgi";
		// HttpRequest.readContentFromGet(get,"","");
		/**
		 * <script language=javascript>window.location.href=
		 * 'http://localhost:8080/pc/operation/qbpayResult.do?
		 * ret=3&mch_id=qbas_hrdtech
		 * &tran_date=20120404&tran_seq=qbas_hrdtech01204041814292527&in_acct=1
		 * &in_acct_type=&sell_type=0&sell_sub_type=0&num=0&time=1333534471&
		 * ret_msg=uin error[1]
		 * &sign=e9a585ffcae1a015956114cabcea9443&PcacheTime
		 * =1333534471';</script>
		 */
		String result = "<script language=javascript>window.location.href='http://localhost:8080/pc/operation/qbpayResult.do?ret=3&mch_id=qbas_hrdtech&tran_date=20120404&tran_seq=qbas_hrdtech01204041814292527&in_acct=1&in_acct_type=&sell_type=0&sell_sub_type=0&num=0&time=1333534471&ret_msg=uin error[1]&sign=e9a585ffcae1a015956114cabcea9443&PcacheTime=1333534471';</script>";
		System.out.println(result.substring(result.indexOf("'") + 1, result
				.lastIndexOf("'")));
	}

	
	/**
	 * 查询是否存在某条记录  或更新某条记录
	 * @param sql
	 * @param params
	 * @param lg 
	 * @return flag
	 */
	public static boolean getRecord(String sql,Object[] params,String lg){
		boolean flag =false;
		DBService dbService=null;
		try {
				dbService = new DBService();
				if(lg.equals("1")){
				List list =dbService.getStringList(sql, params) ;
				if(list.size()!=0){
					flag=true;
					Log.info("收到重复消息不做处理");
				}
				else{
					flag=false;
					Log.info("没查到记录...");
				}
				}
				else{
					int i=0;
					i=dbService.update(sql, params);
					if(i!=0)
						flag=true;
				}	
		} catch (SQLException e) {
			Log.info("QB异常"+e.toString());
			e.printStackTrace();
		}finally{
			dbService.close();
		}
		return flag;
	}
	
	/**
	 * 内部线程类
	 */
	 static class ToScpMsg extends Thread{
		/**
		 * 
		 */
		private String transaction_id;
		private HttpServletRequest request;
		private String qq;
		
		/**
		 * @param transaction_id
		 * @param request
		 */
		public ToScpMsg(String transaction_id,HttpServletRequest request,String qq) {
			this.transaction_id=transaction_id;
			this.request=request;
			this.qq=qq;
		}
		
		/**
		 * 
		 */
		public void run() {
			QBSendMsgScpBean toScp =new QBSendMsgScpBean(transaction_id,qq);	
            toScp.run(SCPConstants.QQB);
		}
	}
	
	
}
