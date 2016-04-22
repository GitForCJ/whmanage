package com.wlt.webm.AccountInfo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.AccountInfo.bean.SiChuanDianXinBean;
import com.wlt.webm.db.DBService;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.scputil.MD5Util;

/**
 * @author adminA
 *
 */
public class scdx {
	/**
	 *  �̻�id			
	 */
	public static String merchNo="2692";
	/**
	 * ��Կ
	 */
	public static String key="PDNxP6fTgl9NrTqA";
	/**
	 * �Ĵ����� ��ֵ
	 * @param orderNo ������
	 * @param phoneNumber �绰����
	 * @param time ����
	 * @param moneyStr ԪΪ��λ
	 * @param interType ��������
	 * @param phoneType ��������
	 * @param Parameter ��������|�������
	 * @return int
	 */
	@SuppressWarnings("static-access")
	public static int sjytczFile(String orderNo,String time,String moneyStr,String phoneNumber,String interType,String phoneType,String Parameter)
	{
		String money=Float.parseFloat(moneyStr)/1000+"".trim();
		Log.info("�Ĵ����� ��ֵ����");
		if(orderNo==null || "".equals(orderNo) || time==null || "".equals(time) || money==null || "".equals(money) || phoneNumber==null || "".equals(phoneNumber))
		{
			Log.info("�Ĵ����� ��ֵ���� �������� orderNO="+orderNo+"   time="+time+"    money="+money+"   phoneNumber="+phoneNumber);
			return -1;
		}
		//0 ���� 1�ƶ�2��ͨ
		if("0".equals(phoneType))
			phoneType="11";
		if("1".equals(phoneType))
			phoneType="9";
		if("2".equals(phoneType))
			phoneType="10";
		SiChuanDianXinBean sc=new SiChuanDianXinBean();
		sc.setRequestUrl("http://sup.314pay.com/orderReceive.aspx");
		sc.setMerchNo(merchNo);
		sc.setAccount(phoneNumber);
		sc.setProductId(phoneType);
		sc.setAmt(Float.parseFloat(money));
		sc.setParameter(Parameter);
		sc.setOrderNo(orderNo);//������
//		sc.setMerchBackUrl("http://shijianqiao321a.xicp.net:8888/wh/business/bank.do?method=SCCallBack");//���Իص�url
		sc.setMerchBackUrl("http://www.wanhuipay.com/business/bank.do?method=SCCallBack");//��ʽ�ص�url
		
		String str="merchNo="+sc.getMerchNo()+
				"&account="+sc.getAccount() +
						"&productId="+sc.getProductId() +
								"&amt="+sc.getAmt() +
										"&parameter="+sc.getParameter() +
												"&orderNo="+sc.getOrderNo()+
														"&merchBackUrl="+sc.getMerchBackUrl();
		str=str+key;
		String mm=MD5Util.MD5Encode(str,"GB2312");
		sc.setSignMsg(mm);//MACУ����
		
	    //����url
	    StringBuffer buf=new StringBuffer();
	    buf.append(sc.getRequestUrl());
		buf.append("?");
		buf.append("merchNo="+sc.getMerchNo());
		buf.append("&account="+sc.getAccount());
		buf.append("&productId="+sc.getProductId());
		buf.append("&amt="+sc.getAmt());
		buf.append("&parameter="+sc.getParameter());
		buf.append("&orderNo="+sc.getOrderNo());
		buf.append("&merchBackUrl="+sc.getMerchBackUrl());
		buf.append("&signMsg="+sc.getSignMsg());
		Log.info("�Ĵ����� �������г�ֵ"+"   ��������:"+interType+"  url : "+buf.toString());
		try {
			URL u = new URL(buf.toString());
			HttpURLConnection conn=(HttpURLConnection)u.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");
			InputStream in = conn.getInputStream();       
		    byte b[] =new byte[512];
		    in.read(b);
		    String result=new String(b,"utf-8").trim();
		    in.close();
		    conn.disconnect();
		    Log.info("�Ĵ� ��ֵ���󷵻��ַ��� �� "+result+"   ��������:"+interType);
		    if("0".equals(result.trim()))
		    {
		    	Log.info("�Ĵ� ��ֵ �����ύ�ɹ� ��"+orderNo+"   ��������:"+interType);
//		    	//��������ɹ�, ÿ20��ȥ��ѯ
		    	if("OF".equals(interType))
		    	{
			    	boolean flag=false;
			    	int con=0;
			    	while(true)
			    	{
			    		if(con>=36)
			    		{
			    			break;
			    		}
			    		Thread.sleep(5000);
			    		 Log.info("�Ĵ� OF ��ֵ ��ѯ����,,,,�����ţ�"+orderNo);
			    		 String a1=scdx.getOrderState(orderNo);
			    		if("0".equals(a1))
			    		{
			    			Log.info("�Ĵ���ֵOF����״̬:��ֵ�ɹ�,,,,�����ţ�"+orderNo+",����״̬:"+a1);
			    			return 0;
			    		}
			    		if("-1".equals(a1) || "-2".equals(a1) || "-3".equals(a1) || "-9".equals(a1))
			    		{
			    			Log.info("�Ĵ���ֵOF����״̬:��ֵ��ʧ��,,,,�����ţ�"+orderNo+",����״̬:"+a1);
			    			return -1;
			    		}
			    		con++;
			    	}
			    	return -2;
		    	}
		    	else
		    	{
			    	Log.info("�Ĵ� ��ֵ ������������ɹ�,,,,,�����ţ�"+orderNo);
			    	return 0;
		    	}
		    }
		} catch (Exception e) {
			Log.error("�Ĵ�  �����ֵ���� �쳣,,,�����ţ�"+orderNo+"   ��������:"+interType+"  �쳣��"+e);
			return -2;
		}
		Log.info("�Ĵ� ��ֵ������֤ʧ��,,,,,�����ţ�"+orderNo+"   ��������:"+interType);
    	//��������ʧ��
        return -1;
		
	}
	
	/**������ѯ
	 * @param orderNo
	 * @return  ����״̬
	 */
	public static String getOrderState(String orderNo)
	{
		Log.info("�Ĵ� ������ѯ���� ������������:"+orderNo);
		StringBuffer buffer=new StringBuffer();
		buffer.append("http://sup.314pay.com/orderReceiveSearch.aspx");
		buffer.append("?");
		buffer.append("merchNo="+merchNo);
		buffer.append("&orderNo="+orderNo);
		buffer.append("&signMsg="+MD5Util.MD5Encode("merchNo="+merchNo+"&orderNo="+orderNo+key,"GB2312"));
		Log.info("�Ĵ� ������ѯ���� url="+buffer.toString());
		try
		{
			URL u = new URL(buffer.toString());
			HttpURLConnection conn=(HttpURLConnection)u.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");
			InputStream in = conn.getInputStream();
		    byte b[] =new byte[1024];
		    in.read(b);
		    String result=new String(b,"utf-8").trim();
		    in.close();
		    conn.disconnect();
		    Log.info("�Ĵ� ������:"+orderNo+"��ѯ���󷵻ؽ�� "+result);
  		    if(result!=null && !"".equals(result))
		    {
		    	if(result.indexOf("orderStatus=")!=-1)
		    	{
		    		String str=result.substring(result.indexOf("orderStatus=")+"orderStatus=".length());
		    		if(str.indexOf("&")!=-1)
		    		{
		    			str=str.substring(0,str.indexOf("&"));
		    		}
		    		return str;
		    	}
		    }
		    return "-2";
		}catch(Exception e)
		{
			Log.error("�Ĵ� ��ѯ�����쳣,,,,,"+e);
			return "-2";
		}
	}
	
	
//	/**
//	 * @param arg
//	 */
//	public static void main(String[] arg)
//	{
//		//13350852605
//		System.out.println("return jieguo:"+scdx.sjytczFile("2014061716380012314","20140617163800","10","18926512055","","0"));
//		//201405261700001000
//		//201405261700001001
//		//201405261700001002
//		//201405261700001003
//		//201405261700001004
//		//201405261700001005
//		//201405261700001006
//		//201405271038001007
//		//System.out.println("��ѯ��"+getOrderState("201405271044001008"));
//	}
}
