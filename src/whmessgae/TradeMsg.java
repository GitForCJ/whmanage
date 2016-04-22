package whmessgae;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * �������ڣ�2013-11-25
 * <p>@Title: ���ͨϵͳҵ���������ģ��</p>
 * <p>@Description: �����������</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: �����б���Ƽ����޹�˾ </p> 
 * <p>@author caiSJ</p>
 * <p>@version 1.0.0.0</p>
 */
public class TradeMsg implements Serializable{
	/**
	 * ��ˮ��
	 */
	private String seqNo = null;
	/**
	 * ��Ϣ����
	 */
	private String msgFrom2 = null;
	/**
	 * ��������
	 */
	private String tradeNo = null;
	/**
	 * ����������
	 */
	private String servNo = null;
	/**
	 * ������
	 */
	private String rsCode = null;
	/**
	 * �Ƿ��и�������
	 */
	private String flag = null;
	/**
	 * ��������
	 */
	private Map<String, String> content = null;

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	

	public String getMsgFrom2() {
		return msgFrom2;
	}

	public void setMsgFrom2(String msgFrom2) {
		this.msgFrom2 = msgFrom2;
	}

	public String getServNo() {
		return servNo;
	}

	public void setServNo(String servNo) {
		this.servNo = servNo;
	}

	public Map<String, String> getContent() {
		return content;
	}

	public void setContent(Map<String, String> content) {
		this.content = content;
	}

	@Override
	public String toString() {
		if("0".equals(flag)){
		return "[seqNo:"+seqNo+",msgFrom2:"+msgFrom2+",tradeNo:"+tradeNo+",servNo:"+servNo+",rsCode:"+rsCode+",flag:"+flag+",content"+content.toString()+"]";
		}else{
			return "[seqNo:"+seqNo+",msgFrom2:"+msgFrom2+",tradeNo:"+tradeNo+",servNo:"+servNo+",rsCode:"+rsCode+",flag:"+flag+"]";			
		}
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getFlag() {
		return flag;
	}
	
	
	
	public String getRsCode() {
		return rsCode;
	}

	public void setRsCode(String rsCode) {
		this.rsCode = rsCode;
	}

	public static void main(String[] args) {
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("1", "xx");
		maps.put("2", "xxx");
		maps.put("3", "xxxx");
		TradeMsg msg=new TradeMsg();
		msg.setSeqNo("5555555555555555");
		msg.setTradeNo("0001");
		msg.setServNo("01");
		msg.setFlag("0");
		msg.setMsgFrom2("0103");
		msg.setRsCode("000");
		msg.setContent(maps);
		System.out.println(msg.toString());
	}

}
