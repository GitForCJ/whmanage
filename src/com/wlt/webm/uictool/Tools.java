package com.wlt.webm.uictool;

import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import cn.com.WebXml.Single;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.scputil.TenpayUtil;
/**
 * <p>Description: ������</p>
 */
public class Tools {
	
	  /**
     * ��������ǰ��0
     * @param str ������ַ���
     * @param num ����
     * @return String ǰ��0����ַ���
     */
    public static String headFillZero(String str,int num){
    	//stringbuffer���0
    	StringBuffer buffer = new StringBuffer();
    	//�������С��ָ�����ȣ���ǰ��0
    	if(str.length() < num){
    		for(int i = 0 ; i < num - str.length(); i ++){
    			buffer.append("0");
    		}
    	}
    	//���ص��ַ���
    	str = buffer.toString() + str;
    	
    	return str;
    }
    
    /**
     * �������ֺ��油�ո�
     * @param str ������ַ���
     * @param num ����
     * @return String ���油�ո����ַ���
     */
    public static String endFillSpace(String str,int num){
    	StringBuffer buffer = new StringBuffer();
    	//�������С��ָ�����ȣ���ǰ��0
    	if(str.length() < num){
    		for(int i = 0 ; i < num - str.length(); i ++){
    			buffer.append(" ");
    		}
    	}
    	//���ص��ַ���
    	str = str + buffer.toString();    	
    	return str;
    }
    /**
     * ��������ǰ���ո�
     * @param str ������ַ���
     * @param num ����
     * @return String ���油�ո����ַ���
     */
    public static String headFillSpace(String str,int num){
    	StringBuffer buffer = new StringBuffer();
    	//�������С��ָ�����ȣ���ǰ��0
    	if(str.length() < num){
    		for(int i = 0 ; i < num - str.length(); i ++){
    			buffer.append(" ");
    		}
    	}
    	//���ص��ַ���
    	str = buffer.toString()+str;    	
    	return str;
    }
    
    /**
	 * ��ȡ�ֽ�����
	 * 
	 * @param src
	 *            byte[] ���ص�����
	 * @param beg
	 *            int ��ʼλ��
	 * @param end
	 *            int ����λ��
	 * @return byte[] ��ȡ�������
	 */
	public static byte[] ByteToByte(byte src[], int beg, int end) {
		byte bytes[] = new byte[end - beg + 1];
		for (int i = beg; i <= end; i++) {
			bytes[i - beg] = src[i];
		}
		return bytes;
	}
	/**
	 * ���ַ���ת��ΪDATE
	 * 
	 * @return Date
	 */
	public static Date fomatStrToDate(String seqFlag) {
		Date t = null;
		try {
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			t = simpleFormat.parse(seqFlag);
		} catch (Exception e) {
			Log.error("��ʽ��Ϣ��ʽ����", e);
		}
		return t;
	}
	/**
	 * �������ܣ���õ�ǰ����
	 * 
	 * @return String ���ص�ǰ���ڵ��ַ���������"20070625"
	 */
	public static String getNowDate() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		str = formatter.format(d);
		return str;
	}
	/**
	 * ��õ�ǰtime
	 * 
	 * @return String ��õ�ǰʱ����ַ�����������"235959"
	 */
	public static String getTime() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		str = formatter.format(d);
		return str;
	}
	/**
	 * ��õ�ǰtime
	 * 
	 * @return String ��õ�ǰʱ����ַ�����������"dd101211"
	 */
	public static String getOptTime() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("ddHHmmss");
		str = formatter.format(d);
		return str;
	}
	  public static Date getDateTime(String seqFlag) {
		  Date t = null;
		  try{
			  SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			  t = simpleFormat.parse(seqFlag);
		  }catch(Exception e){
			 Log.error(e);
		  }
		  return t;
	  }
	/**
	 * ��õ�ǰʱ��
	 * 
	 * @return String ��õ�ǰʱ����ַ�����������"20030114101211"
	 */
	public static String getNow() {
		Date d = new Date();
		String str = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		str = formatter.format(d);
		return str;
	}
	/**
     * ��¼��־��16���ƣ�
     * @param data ��־byte����
     * @return String ��־ת������ַ���
     */
    public static String hexLog(byte[] data){
        /**
         * ��¼��־
         */
    	StringBuffer logmes = new StringBuffer();		
    	
    	for(int i = 0;i < data.length; i ++){
    		
    		String temp = Integer.toHexString(data[i] & 0xff);
    		
    		if(temp.length() == 1){
    			logmes.append("0");
    		}
    		logmes.append(temp)
    			  .append(" ");
    	}
    	
    	return logmes.toString();
    }
    
    /**
     * ��ת��ΪԪ
     * @param yuan ������ַ�������	
     * @return	String ������ַ�����Ԫ
     */
    public static String fenToYuan(String fen){
    	//ת�����Ԫ
    	String yuan = "";
    	//�ַ���ת��Ϊ����
    	long fenInt = Long.parseLong(fen.trim());
    	//��ֵΪԪ
    	yuan = (fenInt / 100.00) + "";
    	//����õ���Ԫֻ����β��ֻ����һ�����������һ��0
    	if(yuan.lastIndexOf(".") == (yuan.length() - 2)){
    		yuan += "0";
    	}
  	
    	return yuan;
    }
    
    /**
     * ��Ԫת��Ϊ��
     * @param yuan ������ַ�����Ԫ	
     * @return	String ������ַ�������
     */
    public static String yuanToFen(String yuan){
    	String fen = "";
    	//���ֻ������
    	if(yuan.indexOf(".") == yuan.length() - 1){
    		yuan += "00";
    	}
    	//���������ַ���������С����
    	if(yuan.indexOf(".") == -1){
    		fen = yuan + "00";
    	}else{//�������С����
    		//ȥ��С����
    		String[] temp = yuan.split("\\.");
    		//�����ֻ��1λ������ϸ�0
    		if(temp[1].length() == 1){
    			temp[1] += "0";
    		}
    		fen = temp[0] + temp[1];
    	}
    	return fen;
    }

    /**
     * ��������ǰ��0
     * @param str ������ַ���
     * @param num ����
     * @return ǰ��0����ַ���
     */
    public static String add0(String str,int num){
    	//stringbuffer���0
    	StringBuffer buffer = new StringBuffer();
    	//�������С��ָ�����ȣ���ǰ��0
    	if(str.length() < num){
    		for(int i = 0 ; i < num - str.length(); i ++){
    			buffer.append("0");
    		}
    	}
    	//���ص��ַ���
    	str = buffer.toString() + str;
    	
    	return str;
    }
    
    /**
     * �������ܣ������ַ���������ת��Ϊ��Ӧ���ַ����飬�󲹿ո�
     * @param str ������ַ��� 
     * @param num ���鳤��
     * @return byte[] ���ص��ַ�����
     */
    public static byte[] addBlank(String str,int num){
    	//�����ַ�����
    	byte[] result = new byte[num];
    	//���strΪnull����str�ĳ���Ϊ0,����ո�,��32
    	if(str == null || str.length() == 0){
    		for(int i = 0; i < num; i ++){
    			result[i] = 32;
    		}
    		return result;
    	}
    	//�����ַ���ת��Ϊ�ַ������ĳ���
    	int strNum = str.getBytes().length;
    	//����ַ����ĳ��ȴ�������ĳ��ȣ����ȡ
    	if(strNum > num){
    		//��������ַ���copy��result��
    		System.arraycopy(str.getBytes(),0,result,0,num);
    		
    		return result;
    		//�ַ����ĳ���С������ĳ��ȣ�����ӿո�
    	}else if(strNum < num){
    		//stringBuffer��ӿո�
    		StringBuffer temp = new StringBuffer();
    		//���ٵĿո�
    		for(int i = 0; i < num - strNum; i ++){
    			temp.append(" ");
    		}
    		str += temp.toString();   		
    		return str.getBytes();
    	}else{//�����ȣ��򷵻������ַ������ַ�����
    		return str.getBytes();
    	}  
    }
    
    /**
     * �Ƚϵ�ǰ���ں�ָ�����ڵ��������
     * @param pastDate  ��Ҫ���Ƚϵ����� ��ʽ��yyyyMMdd
     * @param temp  ���������
     * @return  ���ز���ֵ
     */
    public static boolean compareDate(String pastDate, int temp){
    	boolean flag = false;
    	Calendar cal = Calendar.getInstance();
    	cal.add(Calendar.DATE, temp);
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    	String lastDate = formatter.format(cal.getTime());
    	if(lastDate.equals(pastDate)){
    		flag = true;
    	}
    	return flag;
    }
    /**
     * ��¼��־��16���ƣ�
     * @param data ��־byte����
     * @return String ��־ת������ַ���
     */
    public static String hexlog(byte[] data){
        /**
         * ��¼��־
         */
    	StringBuffer logmes = new StringBuffer();		
    	
    	for(int i = 0;i < data.length; i ++){
    		
    		String temp = Integer.toHexString(data[i] & 0xff);
    		
    		if(temp.length() == 1){
    			logmes.append("0");
    		}
    		logmes.append(temp)
    			  .append(" ");
    	}
    	
    	return logmes.toString();
    }
    
    
	/**
	 * byte�������
	 */
	public static byte[] byteAappend(byte src[], byte src2[]) {
		if (src == null)
			return src2;
		if (src2 == null)
			return src;
		int srcLen = src.length;
		int destLen = src2.length;
		byte dest[] = new byte[srcLen + destLen];
		int i = 0;
		for (i = 0; i < srcLen; i++) {
			dest[i] = src[i];
		}
		for (int n = i; n < srcLen + destLen; n++) {
			dest[n] = src2[n - i];
		}
		return dest;
	}
	
	
	/**
	 * �����ֽڳ���
	 * @param str ��Ҫ������ֽ�
	 * @param num ��num�����ֱ�ʾ���ȣ���numΪ4��ʾ����15Ϊ0015��
	 * @return
	 */
	public static byte[] getByteLength(byte[] str, int num) {
		int s = 0;
		if(str!=null) 
		{
			s=str.length;
		}
		int strLen = String.valueOf(s).length();
		int sub = num - strLen;
		String tmp = "";
		while(sub>0)
		{
			tmp= tmp+"0";
			sub--;
		}
		return  subString(tmp+String.valueOf(s), num).getBytes();
	}
	public static String subString(String str, int num) {
		String substring = str;
		if (str != null && str.length() > num) {
			substring = str.substring(0, num);
		}
		return substring;
	}

	/**
	 * ��ȡ�ֽ�����
	 * 
	 * @param src
	 *            byte[] ���ص�����
	 * @param beg
	 *            int ��ʼλ��
	 * @param end
	 *            int ����λ��
	 * @return byte[] ��ȡ�������
	 */
	public static byte[] subByte(byte src[], int beg, int end) {
		byte bytes[] = new byte[end - beg + 1];
		for (int i = beg; i <= end; i++) {
			bytes[i - beg] = src[i];
		}
		return bytes;
	}
    
	/**
	   * ���ֽ�����ת��Ϊ�ַ���
	   * @param src byte[] ����Ա
	   * @param beg int ��ʼλ��
	   * @param end int ����λ��
	   * @return String �����ַ���
	   */
	  public static String bytesToStr(byte src[], int beg, int end) {
	    String hs = "";
	    char ch = '\0';
	    for (int n = beg; n <= end; n++) {
	      ch = (char) src[n];
	      hs = hs + ch;
	    }
	    return hs;
	  }
	  
	  
		/**
		 * ��õ�ǰʱ��
		 * 
		 * @return ��õ�ǰʱ����ַ�����������"20030114101211"
		 */
		public static String getNow3() {
			Date d = new Date();
			String str = "";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			str = formatter.format(d);
			return str;
		}
		
		/**
		 * ��������ǰ��0
		 * 
		 * @param str
		 *            ������ַ���
		 * @param num
		 *            ����
		 * @return ǰ��0����ַ���
		 */
		public static String addLeftZero(String str, int num) {
			// stringbuffer���0
			StringBuffer buffer = new StringBuffer();
			// �������С��ָ�����ȣ���ǰ��0
			if (str.length() < num) {
				for (int i = 0; i < num - str.length(); i++) {
					buffer.append("0");
				}
			}
			// ���ص��ַ���
			str = buffer.toString() + str;
			return str;
		}
		
		/**
		 * ����ʱ���ǰ8λ
		 * @return
		 */
		public static String getDate() {
			SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddhhmmss");
			String date = simpleFormat.format(new Date());
			return date.substring(0, 8);
		}
		
		
		

		/**
			 * ��÷���scp����Ϣ��ˮ��
			 * 
			 * @param terminal
			 *            �ն˺���
			 * @return
			 */
			public static String getStreamSerial(String terminal) {
				StringBuffer buff = new StringBuffer();
				buff.append(getStreamDate());
				buff.append(headFillStr(terminal, 12));
				int no = getScpStreamAscNum();
				if (no <= 9) {
					buff.append("0");
					buff.append(no);
				} else {
					buff.append(no);
				}
				return buff.toString();
			}


			/**
			 * �����ˮ����������
			 * 
			 * @return ��õ�ǰʱ����ַ�����������"080114101211"
			 */
			public static String getStreamDate() {
				Date d = new Date();
				String str = "";
				SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
				str = formatter.format(d);
				return str;
			}

			/**
			 * �ַ���ǰ���0
			 * 
			 * @param src
			 *            Դ����
			 * @param number
			 *            ��Ҫ�ĳ���
			 * @return des Ŀ���ַ���
			 */
			public static String headFillStr(String src, int number) {
				if (src.length() < number) {
					String temp = "";
					for (int i = 0; i < number - src.length(); i++) {
						temp += "0";
					}
					src = temp + src;
				} else if (src.length() > number) {
					src = src.substring(src.length() - number, src.length());
				}
				return src;
			}

			/**
			 * SCP��ˮ���
			 * 
			 * @return int
			 */
			public static int streamAsc=0;
			public static synchronized int getScpStreamAscNum() {
				streamAsc++;
				if (streamAsc > 99) {
					streamAsc = 0;
				}
				return streamAsc;
			}
			/**
			 * �ƶ��ɷ���ˮ��
			 */
			public static int BeginSeq=0;
			public static synchronized String getCMPaySeq(String phone,int NO){
				if(BeginSeq==0){
					BeginSeq=NO;
				}else if(BeginSeq==9999999){
					BeginSeq=1;
				}else{
					BeginSeq++;
				}
				String a="HR"+phone;
				String b=headFillStr(BeginSeq+"",7);
				a+=b;
				return a;
			}
			/**
			 *  �ƶ��������к�
			 */
			public static int Seq=0;
			public static synchronized String getCMPayNO(){
				if(Seq==9999999){
					Seq=1;
				}else{
					Seq++;
				}
				String b=headFillStr(Seq+"",10);
				return b;
			}
			
			/**
			 * ȡ��һ��ָ�����ȴ�С�����������.
			 * 
			 * @param length
			 *            int �趨��ȡ��������ĳ��ȡ�lengthС��11
			 * @return int �������ɵ��������
			 */
			public static  int buildRandom(int length) {
				int num = 1;
				double random = Math.random();
				if (random < 0.1) {
					random = random + 0.1;
				}
				for (int i = 0; i < length; i++) {
					num = num * 10;
				}
				return (int) ((random * num));
             }
			
			
			/**
			 * ��ô����̺��� ����13λǰ���ո�
			 * @param agentID
			 * @return
			 */
			public static String getAgentID(String agentID){
				String str="";
                for(int i=0;i<13-agentID.length();i++){
                	str+=" ";
                }
				return str+agentID;
			}
			
			/**
			 * ��ô��������� ����8λǰ��0
			 * @param agentID
			 * @return
			 */
			public static String getAgentPWD(String pwd){
				String str="";
                for(int i=0;i<8-pwd.length();i++){
                	str+="0";
                }
				return str+pwd;
			}
			
			/**
			 * ����50λ�󲹿ո�
			 * @param clientph
			 * @return
			 */
			public static String dealClientPh(String clientph){
				int length=50-clientph.length();
				for(int i=0;i<length;i++){
					clientph+=" ";
				}
				return clientph;
				
			}

			/**
			 * ����16λǰ��0
			 * @param cash
			 * @return
			 */
			public static String dealCash(String cash){
				int length=16-cash.length();
				String supply="";
				for(int i=0;i<length;i++){
					supply+="0";
				}
				return supply+cash;
				
			}
			
			/**
			 * ����20λ�󲹿ո�
			 * @param cash
			 * @return
			 */
			public static String dealSerial(String serial){
				int length=20-serial.length();
				for(int i=0;i<length;i++){
					serial+=" ";
				}
				return serial;
				
			}
			
			/**
			 * �õ�20λ�Ľ�����ˮ��
			 * @param cooperate
			 * @return
			 */
			public static String getSerialNumber(){
				String x=buildRandom(4)+"";
				String y=buildRandom(5)+"";
				String z=buildRandom(3)+"";
				String str=z+y+x+getNow3().substring(8);
				return str;
			}


			/**
			 * �ж�ip�Ƿ��ǹ㶫ʡ
			 * @param ip
			 * @return
			 * @throws RemoteException
			 * @throws ServiceException
			 * @throws UnsupportedEncodingException 
			 */
			public static boolean validateIp(String ip) throws RemoteException, ServiceException, UnsupportedEncodingException{
				boolean flag=false;
				String[] str1=Single.getSrvInstance().getIpAddressSearchWebServiceSoap().getCountryCityByIp(ip);
				if(null!=str1&&str1.length>1&&((new String(str1[1].getBytes("GBK"))).indexOf("�㶫")!=-1)){
					flag=true;
				}
				return flag;
			}
			
}
