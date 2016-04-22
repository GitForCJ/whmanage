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
 * �ڿ�  �ƶ���ֵ
 */
public class NineteenRecharge 
{
	/**
	 * �������
	 */
	public static String agttel="15012880537";
	/**
	 * ����
	 */
	public static String pwd="999999";
	/**
	 * ��Կ
	 */
	public static String key="e6ec3f310f07ac1d87d22dcd82340bee";
	
	/**
	 * ���ѳ�ֵ 
	 * @param orderNum 
	 * @param phoneNum 
	 * @param money 
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static String getOrderState(String orderNum,String phoneNum,String money)
	{
		Log.info("�����ڿ��ƶ���ֵ��getOrderState������,,�����ţ�"+orderNum);
		if(orderNum==null || "".equals(orderNum) || phoneNum==null || "".equals(phoneNum) || money==null || "".equals(money)){
			Log.info("�����ڿ��ƶ���ֵ��getOrderState����������ȱ�ٲ���");
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
		
		Log.info("�����ڿ��ƶ���ֵ��getOrderState���������������ַ��"+buf.toString());
		
		Map<String, String> map =null;
		HttpClient client=null;
		GetMethod post =null;
		try{
			client= new HttpClient();
			post=new GetMethod(buf.toString());
			int status = client.executeMethod(post);
			Log.info("�����ڿ��ƶ���ֵ��getOrderState�����������������󣬣������ţ�"+orderNum);
			if(status==200){
				map=new HashMap<String, String>();
				Log.info("�����ڿ��ƶ���ֵ��getOrderState����������http״̬Ϊ200,,,�����ţ�"+orderNum);
//				String result = post.getResponseBodyAsString().trim();
				InputStream in=post.getResponseBodyAsStream();
				String result=inputStream2String(in,"utf-8");
//				result=new String(result.getBytes("ISO-8859-1"),"utf-8");
				Log.info("�����ڿ��ƶ���ֵ��getOrderState��������,,�����ţ�"+orderNum+",,����xml�ַ�����"+result);
				Document docResult=DocumentHelper.parseText(result);
				Element rootResult = docResult.getRootElement();
				List<Element> results=rootResult.elements();
				Log.info("�����ڿ��ƶ���ֵ��getOrderState������������xml�ַ����������ţ�"+orderNum);
				for(Element item1:results){
					map.put(item1.getName(), item1.getText());
				}
			}
		}catch (Exception e) {
			Log.info("�����ڿ��ƶ���ֵ��getOrderState��������"+orderNum+"��http�����쳣����"+e);
			return "-2";
		}finally{
			post.releaseConnection();
			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
			if(null!=client){
				client=null;
			}
		}
		if(map!= null && "10000000".equals(map.get("retcode"))){
			Log.info("�����ڿ��ƶ���ֵ��getOrderState��������"+orderNum+"������xml�ַ�����retcode=10000000,�����ύ�ɹ�,,�˻����balance="+map.get("balance"));
			int con=0;
			while(true){ //ѭ�� 200��
				try{
					if(con>6){
						return "-2";//��ʱ
					}
					if(con==0){
						Thread.sleep(3*1000);
					}else{
						Thread.sleep(30*1000);
					}
					int phoneState=query(orderNum,Integer.parseInt(money),phoneNum);
					if(phoneState==0){ //�ɹ�
						Log.info("�ڿ��ƶ���ֵ,�����ύ�ɹ�,ѭ����ѯ��ֵ�������ţ�"+orderNum+",��"+money+",�ֻ�����:"+phoneNum+",��"+(con+1)+"��ѭ��,��ֵ�ɹ�");
						return "0";
					}
					if(phoneState==1){//ʧ��
						Log.info("�ڿ��ƶ���ֵ,�����ύ�ɹ�,ѭ����ѯ��ֵ�������ţ�"+orderNum+",��"+money+",�ֻ�����:"+phoneNum+",��"+(con+1)+"��ѭ��,��ֵʧ��");
						return "-1";
					}
					con++;
				}catch(Exception e){
					Log.info("�ڿ��ƶ���ֵ,�����ύ�ɹ�,ѭ����ѯ��ֵ�������ţ�"+orderNum+",��"+money+",�ֻ�����:"+phoneNum+",��"+(con+1)+"��ѭ���������쳣"+e);
				}
			}
		}
		else{
			Log.info("�����ڿ��ƶ���ֵ��getOrderState��������"+orderNum+"������xml�ַ�����retcode="+map.get("retcode")+",�����ύʧ��");
			return "-1";
		}
	}
	
	
	/**
	 * ���ѳ���  ����״̬ 000�ɹ�   001ʧ��
	 * @param userno 
	 * @param phoneNum 
	 * @param money 
	 * @param tradeserial 
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public static String reverseServices(String tradeserial,String phoneNum,String money)
	{
		Log.info("�����ڿ��ƶ�������ⶩ����tradeserial="+tradeserial+"���������룺"+phoneNum);
		String witeoff="";
		String userno="";
		DBService db=null;
		try{
			db=new DBService();
			String[] ssaa=db.getStringArray("select writeoff,userno from wht_orderform_"+Tools.getNow3().substring(2,6)+" where tradeserial='"+tradeserial+"'");
			if(ssaa==null || "".equals(ssaa) || ssaa.length<=0){
				Log.info("�����ڿ��ƶ�����������������tradeserial="+tradeserial+"��������");
				return "001";
			}
			witeoff=ssaa[0];
			userno=ssaa[1];
			if(db.update("INSERT INTO wht_yikuai_reverse(phone,datetimes) VALUES('"+phoneNum+"','"+Tools.getNow3()+"')")<=0)
			{
				Log.info("�����ڿ��ƶ�������ⶩ������tradeserial="+tradeserial+"�����ʧ�ܣ����������룺"+phoneNum);
				return "001";
			}
			Log.info("�����ڿ��ƶ�������ⶩ������tradeserial="+tradeserial+"�����ɹ������������룺"+phoneNum);
		}catch(Exception e){
			Log.info("�����ڿ��ƶ�������ⶩ������tradeserial="+tradeserial+"������쳣,���������룺"+phoneNum+",,,,,e:"+e);
			return "001";
		}finally{
			if(null!=db)
				db.close();
		}
		
		Log.info("�����ڿ��ƶ�������reverse����������tradeserial="+tradeserial+"���������룺"+phoneNum);
		if( phoneNum==null || "".equals(phoneNum) || money==null || "".equals(money)){
			Log.info("�����ڿ��ƶ�������reverse����������tradeserial="+tradeserial+"����ȱ�ٲ������������룺"+phoneNum);
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
		
		Log.info("�����ڿ��ƶ�������reverse����������tradeserial="+tradeserial+"���������룺"+phoneNum+"�������ַ��"+buf.toString());
		
		Map<String, String> map = new HashMap<String, String>();
		HttpClient client=null;
		GetMethod post =null;
		try{
			client= new HttpClient();
			post=new GetMethod(buf.toString());
			int status = client.executeMethod(post);
			Log.info("�����ڿ��ƶ�������reverse��������tradeserial="+tradeserial+"�������������󣬣�����������"+phoneNum);
			if(status==200){
				Log.info("�����ڿ��ƶ�������reverse����������tradeserial="+tradeserial+"���������룺"+phoneNum+"��http״̬Ϊ200");
//				String result = post.getResponseBodyAsString();
				InputStream in=post.getResponseBodyAsStream();
				String result=inputStream2String(in,"utf-8");
				Log.info("�����ڿ��ƶ�������reverse����������tradeserial="+tradeserial+"���������룺"+phoneNum+"������xml�ַ�����"+result);
				Document docResult=DocumentHelper.parseText(result);
				Element rootResult = docResult.getRootElement();
				List<Element> results=rootResult.elements();
				Log.info("�����ڿ��ƶ�������reverse����������tradeserial="+tradeserial+"������xml�ַ��������������룺"+phoneNum);
				for(Element item1:results){
					map.put(item1.getName(), item1.getText());
				}
			}
		}catch (Exception e) {
			Log.info("�����ڿ��ƶ�������reverse����������tradeserial="+tradeserial+"���������룺"+phoneNum+"��http�����쳣����"+e);
			return "001";
		}finally{
			post.releaseConnection();
			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
			if(null!=client){
				client=null;
			}
		}
		if(map!= null && map.get("retcode").equals("10000000")){
			Log.info("�����ڿ��ƶ�������reverse����������tradeserial="+tradeserial+"���������룺"+phoneNum+"������xml�ַ�����retcode=10000000,�����ύ�ɹ�");
			int con=0;
			while(true){ //ѭ�� 200��
				try{
					if(con>6){
						return "-2";//��ʱ
					}
					if(con==0){
						Thread.sleep(3*1000);
					}else{
						Thread.sleep(30*1000);
					}
					int phoneState=query(witeoff,Integer.parseInt(money),phoneNum);
					if(phoneState==2){ //�����ɹ�
						Log.info("�����ڿ��ƶ�������reverse������,ѭ����ѯ�����������ţ�"+witeoff+",��"+money+",�ֻ�����:"+phoneNum+",��"+(con+1)+"��ѭ��,�����ɹ�");
						break;
					}
					if(phoneState==0){//����ʧ��
						return "001";
					}
					con++;
				}catch(Exception e){
					Log.info("�����ڿ��ƶ�������reverse������,ѭ����ѯ�����������ţ�"+witeoff+",��"+money+",�ֻ�����:"+phoneNum+",��"+(con+1)+"��ѭ���������쳣"+e);
				}
			}
			Log.info("�����ڿ��ƶ����������γ����ɣ�������������˷�ҵ����ģ�飬����BusinessProcessing�����������˷��û�userno="+userno+",,,tradeserial="+tradeserial);
			return BusinessProcessing(userno,tradeserial);//000�ɹ�  001ʧ��
		}
		else{
			Log.info("�����ڿ��ƶ�������reverse���������������룺"+phoneNum+"������xml�ַ�����retcode="+map.get("retcode")+",�����ύʧ��");
			return "001";
		}
	}
	
	/**
	 * �ڿ� ��ѯ����   0�ɹ�������1ʧ�ܣ���9������
	 * @param orderNum
	 * @param money  ��� Ԫ 
	 * @param phone 
	 * @return int 1���׳ɹ���1����ʧ�ܣ�-1������
	 */
	@SuppressWarnings("unchecked")
	public static int query(String orderNum,int money,String phone) 
	{
		Log.info("�����ڿ��ƶ���ѯ��query������");
		if(orderNum==null || "".equals(orderNum) || money<=0 || phone==null || "".equals(phone)){
			Log.info("�����ڿ��ƶ���ѯ��query����ȱ�ٲ���");
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
		
		Log.info("�����ڿ��ƶ���ѯ��query���������ַ��"+buf.toString());
		
		Map<String, String> map=null;
		HttpClient client=null;
		GetMethod post =null;
		try{
			client= new HttpClient();
			post=new GetMethod(buf.toString());
			int status = client.executeMethod(post);
			Log.info("�����ڿ��ƶ���ѯ��query�������������󣬣�");
			if(status==200){
				map = new HashMap<String, String>();
				Log.info("�����ڿ��ƶ���ѯ��query������http״̬Ϊ200");
//				String result = post.getResponseBodyAsString();
				InputStream in=post.getResponseBodyAsStream();
				String result=inputStream2String(in,"utf-8");
				Log.info("�����ڿ��ƶ���ѯ��query��������xml�ַ�����"+result);
				Document docResult=DocumentHelper.parseText(result);
				Element rootResult = docResult.getRootElement();
				List<Element> results=rootResult.elements();
				Log.info("�����ڿ��ƶ���ѯ��query��������xml�ַ�����");
				for(Element item1:results){
					map.put(item1.getName(), item1.getText());
				}
			}
		}catch (Exception e) {
			Log.info("�����ڿ��ƶ���ѯ��query������http�����쳣����"+e);
			return -1;
		}finally{
			post.releaseConnection();
			((SimpleHttpConnectionManager)client.getHttpConnectionManager()).shutdown(); 
			if(null!=client){
				client=null;
			}
		}
		if(map!= null && map.get("retcode").equals("10000000")){
			Log.info("�����ڿ��ƶ���ѯ��query��������xml�ַ�����retcode=10000000,�����ύ�ɹ�");
			if("0".equals(map.get("state"))){
				return 0;//���׳ɹ�
			}else if("1".equals(map.get("state")) || "8".equals(map.get("state"))){
				return 1;//����ʧ��
			}else if("2".equals(map.get("state")) || "3".equals(map.get("state"))){
				return 2;//���������� or �����ѳɹ�
			}else{
				return -1;
			}
		}
		else{
			Log.info("�����ڿ��ƶ���ѯ��query��������xml�ַ�����retcode="+map.get("retcode")+",�����ύʧ��");
			return -1;
		}
	}
	
	/**http ����ҵ����
	 * @param userno �û�ϵͳ���
	 * @param tradeserial 
	 * @return String 000 �ɹ�  001 ʧ��
	 */
	public static String BusinessProcessing(String userno,String tradeserial)
	{
		Log.info("�����ڿ��ƶ����������γ����ɣ����������˷�ҵ����ģ��,,,��ʼ�˷�,,,�˷��û�userno="+userno+",,,tradeserial="+tradeserial);
		String date=Tools.getNow3().substring(2,6);
		String orderTable="wht_orderform_"+date;
		String acctTable="wht_acctbill_"+date;
		//��֧�ֿ�, ʧ�����������˿��¼
		//�޸Ķ���״̬
		DBService db=null;
		try{
		db=new DBService();
		db.setAutoCommit(false);
		Log.info("�����ڿ��ƶ����������γ����ɣ����������˷�ҵ����ģ��,,,��ʼ�˷�,����,db.setAutoCommit(false);����,�˷��û�userno="+userno+",,,tradeserial="+tradeserial);
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
		Log.info("�����ڿ��ƶ����������γ����ɣ����������˷�ҵ����ģ��,,,��ʼ�˷�,����,db.commit();����ҵ����ɹ�������000,�˷��û�userno="+userno+",,,tradeserial="+tradeserial);
		return "000";
		}catch(Exception ex)
		{
			db.rollback();
			Log.error("�����ڿ��ƶ����������γ����ɣ����������˷�ҵ����ģ��,,,��ʼ�˷�,����,db.rollback();����ҵ�����쳣������000,�˷��û�userno="+userno+",,,tradeserial="+tradeserial,ex);
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
	
	
    /**http ���� InputStream  ת string
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
