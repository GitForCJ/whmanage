package com.wlt.webm.mobile;

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
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.tencent.QBTransferAccounts;
import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.SCPConstants;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.ten.bean.QBSendMsgScpBean;
import com.wlt.webm.ten.service.QBPayRequestHandler;
import com.wlt.webm.ten.service.TenpayConfigParser;
import com.wlt.webm.ten.service.TenpayXmlPath;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.TenpayUtil;

/**
 * 
 * @author lenovo
 *
 */
public class HttpRequest {
	
	
	public static Map<String ,String> maps=new HashMap<String, String>();
	

     /**
      * 
      * @param num
      * @param in_acct
      * @param posid
      * @param request
      * @param response
      * @return
      * @throws SQLException
      */
	public static  int readContentFromGet(String num, String in_acct,
			String posid,HttpServletRequest request,HttpServletResponse response) throws SQLException {
		int totalFee=0;
		String requestUrl ="";
		if(null==num||null==in_acct||num==""||in_acct==""){
			return  7;// �������ݲ��Ϸ�
		}
		String sql0="select fundacct from wlt_facct where termid='"+posid+"'";
		DBService dbService=null;
		//��ǰʱ�� yyyyMMddHHmmss
		TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
	    String currTime = TenpayUtil.getCurrTime();
		String transaction_id =TenpayUtil.getQBChars(currTime);
		String account="";
		try{
		dbService=new DBService(); 
		String facct=dbService.getString(sql0);
		String sql="select usableleft from  wlt_childfacct where  childfacct ='"+facct+"02'";
		int money=dbService.getInt(sql);
		String str = "select sc_traderpercent from sys_rebate where sc_tradertype=1";
		int rebate=dbService.getInt(str);
		if(money-Integer.parseInt(num)*100<=0){
			if(null!=dbService){
				dbService.close();
			}
			return 11;// "�˻�����");
		}
		
		totalFee=(Integer.valueOf(num).intValue())*(Integer.valueOf(rebate).intValue());	
		//�̻���
		String bargainor_id = TenpayConfigParser.getConfig().getQbid();
		//��Կ
		String key = TenpayConfigParser.getConfig().getQbsign();
		//�ص�֪ͨURL
		String return_url = TenpayConfigParser.getConfig().getQburl();
		//����PayRequestHandlerʵ��
		QBPayRequestHandler reqHandler = new QBPayRequestHandler(request,response);
			//��ʼ��
		reqHandler.init();
		//������Կ
		reqHandler.setKey(key);
		//����֧������
		reqHandler.setParameter("mch_id", bargainor_id); //�̻���
		reqHandler.setParameter("tran_seq", transaction_id); //���׵���
		reqHandler.setParameter("ret_url", return_url); //֧��֪ͨurl
		reqHandler.setParameter("num", num); //��������
		reqHandler.setParameter("in_acct", in_acct); //�˺�
		Log.info("Q�ҷ�����ˮ��:"+transaction_id);
		String sql00="select fundacct from  wlt_facct where  termid ="+posid;
		account=dbService.getString(sql00);
		//��¼��־
	    int n=reqHandler.logTransferToDB(bargainor_id,currTime.substring(0,8),transaction_id, in_acct,totalFee,currTime,num,posid,null);
		if(n!=0){
			if(null!=dbService){
				dbService.close();
			}
				return -1;//ϵͳ��æ���Ժ�����
		}
		if(!"000".equals(QBTransferAccounts.deal(transaction_id, currTime, totalFee+"", account+"02",in_acct))){
			if(null!=dbService){
				dbService.close();
			}
			return -1;//ϵͳ��æ���Ժ�����
		}
		
	    //��ȡ�����������url
		requestUrl = reqHandler.getRequestURL();
		Log.info("requestUrl:" + requestUrl);
		}catch (Exception e) {
			if(null!=dbService){
				dbService.close();
			}
			return -1;//ϵͳ��æ���Ժ�����
		}

		int i = 0;
		String GET_URL="http://esales.qq.com/cgi-bin/esales_sell_dir.cgi?"+requestUrl;
		StringBuffer sf = new StringBuffer();
//		HttpClient client = new HttpClient();
//	    PostMethod post = new PostMethod(GET_URL);
//		HttpConnectionParams.setConnectionTimeout(post.getParams(), 10000);
//		HttpConnectionParams.setSoTimeout(post.getParams(), 120000); 
//	    HttpConnectionManagerParams pm=client.getHttpConnectionManager().getParams();
//	    pm.setConnectionTimeout(10000);
	    int status;
	    String resultStr = "";
		try {
//			status = client.executeMethod(post);
//	    if(status==200){
//	    	resultStr = post.getResponseBodyAsString();
//	    	Log.info("Q �ҽ��:"+resultStr);
//		 } 
			URL getUrl = new URL(GET_URL);
			HttpURLConnection connection = (HttpURLConnection) getUrl
					.openConnection();
			connection.setConnectTimeout(10000);
			connection.connect();
			while (true) {
				i++;
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(connection.getInputStream(),
								"utf-8"));
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
				if (i > 100) {
					Log.error("localQ�ҳ�ֵ���س�ʱ "+GET_URL);
					break;
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			String sql1="update wlt_orderForm_"+Tools.getNow().substring(2, 6)+" set state=2 ,chgtime='"+Tools.getNow3()+"' where tradeserial='"+transaction_id+"'";
			dbService.update(sql1);
			Log.error("Q ��exception:"+e.toString());
				e.printStackTrace();
				if(null!=dbService){
					dbService.close();
				}
				return 13;//�����Ѵ����Ժ����ڲ�ѯ�˵����в�ѯ,������:"+transaction_id);
			}
		resultStr=sf.toString();
		if(!resultStr.equals("")){
		String ret=resultStr.substring(resultStr.indexOf("ret=")+4,resultStr.indexOf("&mch_id"));
		if("0".equals(ret)){
			String sql1="update wlt_orderForm_"+Tools.getNow().substring(2, 6)+" set state=0 ,chgtime='"+Tools.getNow3()+"' where tradeserial='"+transaction_id+"'";
			dbService.update(sql1);
			if(null!=dbService){
				dbService.close();
			}
			return 0;
		}else{
			String sql1="update wlt_orderForm_"+Tools.getNow().substring(2, 6)+" set state=1 ,chgtime='"+Tools.getNow3()+"' where tradeserial='"+transaction_id+"'";
			dbService.update(sql1);
			String sql2="update wlt_acctbill_"+Tools.getNow().substring(2, 6)+" set state=1 "+ "where dealserial='"+transaction_id+"'";
			dbService.update(sql2);
			String sql3="update wlt_childfacct set usableleft = usableleft+"+totalFee+ " where childfacct='"+account+"02'";
			dbService.update(sql3);
			String sql5="update wlt_facct set accountleft = accountleft+"+totalFee+ " where fundacct='"+account+"'";
			dbService.update(sql5);
			if(null!=dbService){
				dbService.close();
			}
			return -2;//����ʧ��
		}
		}else{
			String sql1="update wlt_orderForm_"+Tools.getNow().substring(2, 6)+" set state=6 ,chgtime='"+Tools.getNow3()+"' where tradeserial='"+transaction_id+"'";
			dbService.update(sql1);
			if(null!=dbService){
				dbService.close();
			}
			return 13;//�����Ѵ����Ժ����ڲ�ѯ�˵����в�ѯ,������:"+transaction_id);
		}
	
	}
	
	
	/**
	 * ������
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
	 * ��ѯ�Ƿ����ĳ����¼  �����ĳ����¼
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
					Log.info("�յ��ظ���Ϣ��������");
				}
				else{
					flag=false;
					Log.info("û�鵽��¼...");
				}
				}
				else{
					int i=0;
					i=dbService.update(sql, params);
					if(i!=0)
						flag=true;
				}	
		} catch (SQLException e) {
			Log.info("QB�쳣"+e.toString());
			e.printStackTrace();
		}finally{
			dbService.close();
		}
		return flag;
	}
	
	/**
	 * �ڲ��߳���
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
