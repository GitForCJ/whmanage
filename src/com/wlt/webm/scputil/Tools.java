package com.wlt.webm.scputil;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ������
 */
public class Tools {
	/**
	 * ���ҵ���������ϵͳĿ¼
	 */
	public static final String SCP_HOME = System.getProperty("user.dir");
	/**
	 * �Ʒ�ͬ����ˮ��
	 */
	public static int BillSerialNbrAsc = 0;
	 
	/**
	 * ���ַ������б���ת������ISO����ת��ΪGBK����
	 * 
	 * @param output
	 *            String
	 * @return String
	 */
	public static String decode(String output) {
		try {
			return new String(output.getBytes("ISO-8859-1"), "GBK");
		} catch (Exception ex) {
			return "";
		}
	}

	/**
	 * ���ַ������б���ת������GBK����ת��ΪISO����
	 * 
	 * @param output
	 *            String
	 * @return String
	 */
	public static String encode(String input) {
		try {
			return new String(input.getBytes("GBK"), "ISO-8859-1");
		} catch (Exception ex) {
			return "";
		}
	}
	
	/**
     * �ظ��ַ����涨�Ĵ���
     * @param s Ҫ�ظ����ַ���
     * @param num �ظ��Ĵ������������0��Ϊ0ʱ����""��
     * @return �����ظ�����ַ���
     */
    public static String repeat(String s, int num)
    {
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < num; i++)
        {
            str.append(s);
        }
        return str.toString();
    }


	/**
	 * ɾ��Ŀ¼�Լ�Ŀ¼�������ļ�
	 * 
	 * @param dir
	 *            String
	 * @return String
	 */
	public static void delDir(File file) {
		// �����м���
		//boolean rslt = true;
		// �ȳ���ֱ��ɾ��
		if (!(file.delete())) {
			// ���ļ��зǿա�ö�١��ݹ�ɾ����������
			File subs[] = file.listFiles();
			for (int i = 0; i <= subs.length - 1; i++) {
				if (subs[i].isDirectory()) {
					// �ݹ�ɾ�����ļ�������
					Tools.delDir(subs[i]);
				}
				// ɾ�����ļ��б���
				subs[i].delete();
			}
			// ɾ�����ļ��б���
			file.delete();
		}
	}
	
	
	/**
	 * ���ؽ��׷���ƽ̨
	 * @param sendID ϵͳ���巢�ͷ�����
	 * @return ���ط���ƽ̨ID
	 */
	public static String getTradeSource(String sendID)
	{
		int flag = Integer.parseInt(sendID);
		String source = null;
		switch(flag)
		{
			case 2: //smp
			{
				source = "7";
				break;
			}
			case 3: //pc
			{
				source = "3";
				break;
			}
			case 4: //ipc,��pos
			{
				source = "2";
				break;
			}
			case 5: //ipc,��pos
			{
				source = "3";
				break;
			}
			case 10: //portal
			{
				source = "1";
				break;
			}
			case 11: //�ճ��ն�
			{
				source = "8";
				break;
			}
			case 12: //FEP�ⲿ���Žӿڽ���
			{
				source = "9";
				break;
			}
			
		}
		
		return source;
	}

	
	/**
     * ����List�е����ݻ��һ��Map������Map����
     * @param list List��Ԫ��ΪString[]����Ԫ�ظ���������ڵ���2��
     * @return Map(key��String��value��String)
     */
    public static Map getStringMap(List list)
    {
        Map map = new LinkedHashMap();

        for (int i = 0; i < list.size(); i++)
        {
            String[] info = (String[]) list.get(i);
            map.put(info[0], info[1]);
        }

        return map;
    }

    
    /**
     * ���ؼƷѱ�����Ӫҵ��ID
     * @param areaCode  ����
     * @return ���ؼƷѱ�����Ӫҵ��ID
     */
    public static String getBillLoaclNetId(String areaCode)
    {
    	int net = Integer.parseInt(areaCode.substring(1));
    	String netId = "";
    	switch(net)
    	{
	    	case 310: //����
	    	{
	    		netId = "1304";
	    		break;
	    	}
	    	case 311: //ʯ��ׯ
	    	{
	    		netId = "1301";
	    		break;
	    	}
	    	
	    	case 312: //����
	    	{
	    		netId = "1306";
	    		break;
	    	}
	    	case 313: //�żҿ�
	    	{
	    		netId = "1307";
	    		break;
	    	}
	    	case 314:  //�е�
	    	{
	    		netId = "1308";
	    		break;
	    	}
	    	case 315: //��ɽ
	    	{
	    		netId = "1302";
	    		break;
	    	}
	    	case 316:  //�ȷ�
	    	{
	    		netId = "1310";
	    		break;
	    	}
	    	case 317:  //����
	    	{
	    		netId = "1309";
	    		break;
	    	}
	    	case 318:  //��ˮ
	    	{
	    		netId = "1311";
	    		break;
	    	}
	    	case 319: //��̨
	    	{
	    		netId = "1305";
	    		break;
	    	}
	    	case 335:  //�ػʵ�
	    	{
	    		netId = "1303";
	    		break;
	    	}
    	}
    	
    	return netId;
    }
    
    /**
     * �����ƶ�G���ɷѳ��д���
     * @param areaCode  ����
     * @return ���س��д���
     */
    public static String getMobileCityFlag(String areaCode)
    {
    	int net = Integer.parseInt(areaCode.substring(1));
    	String netId = "";
    	switch(net)
    	{
	    	case 310: //����
	    	{
	    		netId = "F";
	    		break;
	    	}
	    	case 311: //ʯ��ׯ
	    	{
	    		netId = "H";
	    		break;
	    	}
	    	
	    	case 312: //����
	    	{
	    		netId = "G";
	    		break;
	    	}
	    	case 313: //�żҿ�
	    	{
	    		netId = "D";
	    		break;
	    	}
	    	case 314:  //�е�
	    	{
	    		netId = "J";
	    		break;
	    	}
	    	case 315: //��ɽ
	    	{
	    		netId = "A";
	    		break;
	    	}
	    	case 316:  //�ȷ�
	    	{
	    		netId = "C";
	    		break;
	    	}
	    	case 317:  //����
	    	{
	    		netId = "W";
	    		break;
	    	}
	    	case 318:  //��ˮ
	    	{
	    		netId = "I";
	    		break;
	    	}
	    	case 319: //��̨
	    	{
	    		netId = "E";
	    		break;
	    	}
	    	case 335:  //�ػʵ�
	    	{
	    		netId = "B";
	    		break;
	    	}
    	}
    	
    	return netId;
    }
    
    /**
	 * ��÷����Ʒ�ͬ����ˮ��
	 * @param synSerial ����Ʒѵ�ͬ����ˮ��
	 * @return
	 */
	public static String getBillSerialNbr() {
		StringBuffer buff = new StringBuffer();
		buff.append(DateParser.getNowDateTime().substring(8));
		buff.append(Tools.headFillStr(String.valueOf(getBillSerialNbrAsc()), 6));
		return buff.toString();
	}
	
	/**
	 * ���G���ɻ�,�����Ʒ�ͬ����ˮ��
	 * @param synSerial ����Ʒѵ�ͬ����ˮ��
	 * @return
	 */
	public static String getMobileSerialNbr()
	{
		StringBuffer buff = new StringBuffer();
		buff.append(DateParser.getNowDateTime());
		buff.append(Tools.headFillStr(String.valueOf(getBillSerialNbrAsc()), 6));
		return buff.toString();
	}
	
	/**
	 * ͬ����ˮ���
	 * @return int
	 */
	public static synchronized int getBillSerialNbrAsc() {
		BillSerialNbrAsc++;
		if (BillSerialNbrAsc > 999999) {
			BillSerialNbrAsc = 0;
		}
		return BillSerialNbrAsc;
	}

	/**
	 * �ַ���ǰ���0
	 * @param src  Դ����
	 * @param number ��Ҫ�ĳ���
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
	   * ����Ÿ�ʽ��Ϊ�ַ���������λ����ǰ�油0
	   * @param sn int  ���
	   * @param length int  �ַ���λ��
	   * @return String
	   */
	  public static String formatSn(long sn, int length) {
		  String str = sn+"";
		  String strSn = str;
		  for (int i = 0; i < length - str.length(); i++) {
			  strSn = "0" + strSn;
		  }
		  return strSn;
	  }
	public static void main(String args[]) {
		System.out.println(Tools.getMobileSerialNbr());
		
//		File nul= new File("D:/����/edwe");
//		Tools tools =new Tools();
//		tools.delDir(nul);
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
	   * ��ʽ��double����Ϊ�ַ�������ʽΪ##################0.00
	   * @param b double
	   * @return String
	   */
	  public static String formatE(double b) {
	    try {
	      DecimalFormat df1 = new DecimalFormat("##################0.00");
	      return df1.format(b);
	    }
	    catch (Exception ex) {
	      return "";
	    }
	  }
	  
	  
	  /**
	   * ��ת��ΪԪ
	   * @param fen ������ַ�������	
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
	
		public static String getNowDate() {
			Date d = new Date();
			String str = "";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			str = formatter.format(d);
			return str;
		}
}
