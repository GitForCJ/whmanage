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
 * @author ʩ����
 * Recharge
	Correct
	Query
 */
public class Mobile_Interface {
	
	public static void main(String[] args) {
		String orderid=Tools.getNow()+""+((char)(new Random().nextInt(26) + (int)'a'))+((int)(Math.random()*1000)+1000)+""+((char)(new Random().nextInt(26) + (int)'A'))+""+((int)(Math.random()*100)+1000);
	
//		System.out.println("������:"+orderid+",,��ֵ���:"+Recharge(orderid,"18824588427","30","1"));
		//20150723113431k1956I1085
		//20150724093417l1334B1048
		//20150724134815o1299E1065
	
//		System.out.println(Query("20150724134815o1299E1065"));
		
//		System.out.println(Correct("20150724134815o1299E1065", "30", "18824588427",""));
	}
	
	/**
	 * �̻���
	 */
	public static final String agentid="88800006";
	
	/**
	 * �̻���Կ
	 */
	public static final String merchantKey="ba83fa05ff75070e224c8699b52d4c5d";
	
	/**
	 * ��ֵurl 
	 */
	public static final String R_URL="http://www.szsz1000.com/GateWay/Phone/Default.aspx";
	
	/**
	 * ������ѯURL
	 */
	public static final String Q_URL="http://www.szsz1000.com/GateWay/Phone/Search.aspx";
	
	/**
	 * ����URL
	 */
	public static final String C_URL="http://www.szsz1000.com/GateWay/Phone/UnRecharge.aspx";
	
	/**
	 * ����״̬��ѯ
	 */
	public static final String C_Q_URL="http://www.szsz1000.com/GateWay/Phone/UnSearch.aspx";
	
	/** ��ʤ�ƶ���ֵ������
	 *  001	�ӿڹر�
		002	ipδ����Ȩ
		003	����������
		004	MD5����ʧ��
		005	û�ж�Ӧ��Ʒ����֧�ִ˽���ֵ
		006	����
		007	�ύʧ��
		008	�ύ�ɹ�
		010	û�д��û���û��ͨ�ӿ�ҵ��
		011	��ˮ���Ѵ��ڣ������������ˮ��
		012	��֧�ִ˵�������
		013	�ύ�ĺ�����Ӫ�̺�ʵ�ʲ���
		014	�ύ�쳣,��ͨ��ǰ̨��ȷ���Ƿ��ύ�ɹ�
	 */
	
	/**
	 * ��ʤ�ƶ���ֵ   1��ʤ�ƶ� 2��ͨ 3����
	 * @param orderid  ������
	 * @param mobilenum ��ֵ����
	 * @param money ��ֵ���
	 * @param telephonetype ��ֵ����  1��ʤ�ƶ� 2��ͨ 3����
	 * @return String  0�ɹ�  -1 ʧ��  2�������쳣  3����
	 */
	public static String Recharge(String orderid,String mobilenum,String money,String telephonetype){
		//���0���� 1��ʤ�ƶ� 2��ͨ  ת������ʤ 1��ʤ�ƶ� 2��ͨ 3����
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
			Log.info("��ʤ�ƶ���ֵ,��������,,,,");
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
		
		Log.info("��ʤ�ƶ���ֵ,������:"+orderid+",,����:"+mobilenum+",,��ֵ����url:"+buffer.toString());
		
		HttpClient client = null;
		PostMethod post = null;
		try {
			client = new HttpClient();
			post = new PostMethod(buffer.toString());
			int status = client.executeMethod(post);
			Log.info("��ʤ�ƶ���ֵ,������:"+orderid+",,����:"+mobilenum+",,http��Ӧ״̬:"+status);
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
					Log.info("��ʤ�ƶ���ֵ,��ֵʧ��,������:"+orderid+",,����:"+mobilenum+",,��Ӧ״̬:"+result+",,return -1 �ύʧ��");
					return "-1";
				}else if("006".equals(result)){
					Log.info("��ʤ�ƶ���ֵ,��ֵʧ��,������:"+orderid+",,����:"+mobilenum+",,��Ӧ״̬:"+result+",,return 3 �ύʧ��,����");
					return "3";
				}else{
					//���ò�ѯ�ӿ�
					Log.info("��ʤ�ƶ���ֵ,������:"+orderid+",,����:"+mobilenum+",,��Ӧ״̬:"+result+",,���ò�ѯ�ӿ�");
				}
			}
		} catch (Exception e) {
			Log.error("��ʤ�ƶ���ֵ,ϵͳ�쳣,������:"+orderid+",,����:"+mobilenum+",,ex:"+e);
		} finally {
			post.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
			if (null != client) {
				client = null;
			}
		}
		Log.info("��ʤ�ƶ���ֵ,���ò�ѯ�ӿ�,������:"+orderid+",,����:"+mobilenum);
		return Query(orderid);
	}
	
	/**��ʤ�ƶ�������ѯ�ӿ�
	 * @param orderid ������
	 * @return String  0�ɹ�  -1 ʧ��  2�������쳣  3����
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
		
		Log.info("��ʤ�ƶ�������ѯ,������:"+orderid+",,��ѯ����url:"+buffer.toString());
		
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
				Log.info("��ʤ�ƶ�������ѯ,������:"+orderid+",,��:"+con+"��ѭ����ѯ,,http��Ӧ״̬:"+status);
				if (status == 200) {
					String result = post.getResponseBodyAsString().replace(" ","").trim();
					if("007".equals(result) || "077".equals(result)){
						Log.info("��ʤ�ƶ�������ѯ,������:"+orderid+",,��:"+con+"��ѭ����ѯ,,������Ӧ״̬:"+result+",,return -1 ����ʧ�ܴ���");
						return "-1";
					}else if("008".equals(result)){
						Log.info("��ʤ�ƶ�������ѯ,������:"+orderid+",,��:"+con+"��ѭ����ѯ,,������Ӧ��Ӧ״̬:"+result+",,return 0 �����ɹ�����");
						return "0";
					}else{ 
						Log.info("��ʤ�ƶ�������ѯ,������:"+orderid+",,��:"+con+"��ѭ����ѯ,,������Ӧ��Ӧ״̬:"+result+",,״̬����ȷ,������ѯ,,");
					}
				}
			} catch (Exception e) {
				Log.error("��ʤ�ƶ�������ѯ,������:"+orderid+",,��:"+con+"��ѭ����ѯ,,ϵͳ�쳣,ex:"+e);
			} finally {
				post.releaseConnection();
				((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
				if (null != client) {
					client = null;
				}
			}
		}
		Log.info("��ʤ�ƶ�������ѯ,������:"+orderid+",,��:"+con+"��ѭ����ѯ,,����ȷ���,return 2 ������������״̬");
		return "2";
	}
	
	/**��ʤ�ƶ������ӿ�
	 * @param orderid  ����������
	 * @param money  �������
	 * @param mobilenum ��������
	 * @return String  0�ɹ�  -1 ʧ��  2�������쳣
	 */
	@SuppressWarnings("unchecked")
	public static final String Correct_LianSheng(String orderid,String money,String mobilenum){
		if(orderid==null || "".equals(orderid) 
			|| money==null || "".equals(money)
			|| mobilenum==null || "".equals(mobilenum)){
			Log.info("��ʤ�ƶ�����,��������,,,");
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
		
		Log.info("��ʤ�ƶ���������,������:"+orderid+",,��������url:"+buffer.toString());
		
		HttpClient client = null;
		PostMethod post = null;
		try {
			client = new HttpClient();
			post = new PostMethod(buffer.toString());
			int status = client.executeMethod(post);
			Log.info("��ʤ�ƶ���������,������:"+orderid+",,http��Ӧ״̬:"+status);
			if (status == 200) {
				String result = post.getResponseBodyAsString().trim();
				if(result==null || "".equals(result)){
					Log.info("��ʤ�ƶ���������,������:"+orderid+",,����Ӧ����,reutrn -1 ����ʧ�ܴ���");
					return "-1";
				}
				
				Log.info("��ʤ�ƶ���������,������:"+orderid+",,��Ӧ����:"+result);
				
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
					Log.info("��ʤ�ƶ���������,������:"+orderid+",,result:"+result+",,reutrn -1 ����ʧ�ܴ���");
					return "-1";
				}else{
					//���ò�ѯ�ӿ�
					Log.info("��ʤ�ƶ���������,������:"+orderid+",,result:"+result+",,���ò�ѯ�ӿ�");
				}
			}else{
				Log.info("��ʤ�ƶ���������,������:"+orderid+",,http��Ӧ״̬:"+status+",,reutrn -1 ����ʧ�ܴ���");
				return "-1";
			}
		} catch (Exception e) {
			Log.info("��ʤ�ƶ���������,������:"+orderid+",,ϵͳ�쳣,ex:"+e);
		} finally {
			post.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
			if (null != client) {
				client = null;
			}
		}
		
		Log.info("��ʤ�ƶ���������,������:"+orderid+",,���ó���״̬��ѯ�ӿ�");
		return Correct_Query(orderid);
	}
	
	/**����״̬��ѯ
	 * @param orderid ������
	 * @return   0�ɹ�  -1 ʧ��  2�������쳣
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
		
		Log.info("��ʤ�ƶ�����������ѯ,������:"+orderid+",,������ѯ����url:"+buffer.toString());
		
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
				Log.info("��ʤ�ƶ�����������ѯ,������:"+orderid+",,��:"+con+"��ѭ����ѯ,,http��Ӧ״̬:"+status);
				if (status == 200) {
					String result = post.getResponseBodyAsString().trim();
					if(result==null || "".equals(result)){
						Log.info("��ʤ�ƶ�����������ѯ,������:"+orderid+",,��:"+con+"��ѭ����ѯ,,result Ϊ��");
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
						Log.info("��ʤ�ƶ�����������ѯ,������:"+orderid+",,��:"+con+"��ѭ����ѯ,,������Ӧ״̬:"+result+",,return -1 ����ʧ�ܴ���");
						return "-1";
					}else if("008".equals(result)){
						Log.info("��ʤ�ƶ�����������ѯ,������:"+orderid+",,��:"+con+"��ѭ����ѯ,,������Ӧ��Ӧ״̬:"+result+",,return 0 �����ɹ�����");
						return "0";
					}else{
						Log.info("��ʤ�ƶ�����������ѯ,������:"+orderid+",,��:"+con+"��ѭ����ѯ,,������Ӧ��Ӧ״̬:"+result+",,����ѭ����ѯ");
					}
				}
			} catch (Exception e) {
				Log.info("��ʤ�ƶ�����������ѯ,������:"+orderid+",,��:"+con+"��ѭ����ѯ,,ϵͳ�쳣,ex:"+e);
			} finally {
				post.releaseConnection();
				((SimpleHttpConnectionManager) client.getHttpConnectionManager()).shutdown();
				if (null != client) {
					client = null;
				}
			}
		}
		Log.info("��ʤ�ƶ�����������ѯ,������:"+orderid+",,��:"+con+"��ѭ����ѯ,,����ȷ���,return 2 ������������״̬");
		return "2";
	}
	
	/**http ����ҵ����
	 * @param userno �û�ϵͳ���
	 * @param tradeserial 
	 * @return String 0 �ɹ�  -1 ʧ��  2�쳣
	 */
	public static String Correct_Orders(String userno,String tradeserial)
	{
		Log.info("��ʤ�ƶ������������������˷�ҵ����ģ��,,,��ʼ�˷�,,,�˷��û�userno="+userno+",,,tradeserial="+tradeserial);
		String date=Tools.getNow3().substring(2,6);
		String orderTable="wht_orderform_"+date;
		String acctTable="wht_acctbill_"+date;
		//��֧�ֿ�, ʧ�����������˿��¼
		//�޸Ķ���״̬
		DBService db=null;
		try{
			db=new DBService();
			db.setAutoCommit(false);
			Log.info("��ʤ�ƶ��������������������˷�ҵ����ģ��,,,��ʼ�˷�,����,db.setAutoCommit(false);����,�˷��û�userno="+userno+",,,tradeserial="+tradeserial);
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
					0,"�ƶ�����(���ѳ���)",0,newtime,userleft+Integer.parseInt(str[1]),tradeserial,str[0],2};
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
						0,"�ƶ�����(���׳���)",0,newtime,parentleft-Integer.parseInt(str1[1]),
						tradeserial,str[0],1};
				db.logData(15, acctObjP, acctTable);
				db.update("update wht_childfacct set accountleft=accountleft-"+Integer.parseInt(str1[1])+
						" where childfacct='"+str1[0]+"'");
			}
			db.update("insert into wlt_revertlimit values(null,'" + userno + "','" +Tools.getNow3().substring(0,6)+ "')");
			//��֧�ֿ�, ʧ�����������˿��¼ ����
			db.commit();
			Log.info("������ʤ�ƶ����������������˷�ҵ����ģ��,,,��ʼ�˷�,����,db.commit();����ҵ����ɹ����˷��û�userno="+userno+",,,tradeserial="+tradeserial);
			return "0";
		}catch(Exception ex){
			db.rollback();
			Log.error("������ʤ�ƶ����������������˷�ҵ����ģ��,,,��ʼ�˷�,����,db.rollback();����ҵ�����쳣���˷��û�userno="+userno+",,,tradeserial="+tradeserial,ex);
			return "2";
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
	}

	/**
	 * ��ʤ�ƶ������ַ�
	 * @param orderid ����
	 * @param money Ǯ 
	 * @param mobilenum ����
	 * @param userno �û�
	 * @return String 0�����ɹ�  -1����ʧ��  2�����쳣
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
