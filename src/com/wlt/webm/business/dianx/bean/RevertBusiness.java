package com.wlt.webm.business.dianx.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.TelecomMsg;
import com.wlt.webm.uictool.Constant;
import com.wlt.webm.uictool.Tools;

public class RevertBusiness {
	
	private String payNo=null;
	/**
	 *  ��Ϣ��ˮ��
	 */
	private String seqNo=null;
	
	/**
	 * �Ʒ�������ˮ��
	 */
	private String rollSerialNo=null;
	/**
	 * �Ʒ���ˮ
	 */
	private String serialNo=null;
	private String roll=null;
	
	
	public RevertBusiness(String rollBack,String payNo,String seqNo){
		this.seqNo=seqNo;
		this.roll=rollBack;
		this.payNo=payNo;
	}
	
	/**
	 * ҵ������
	 */
	public ArrayList<Object> deal() {
		ArrayList<Object> resultList=new ArrayList<Object>();
		RequestRoll roll = new RequestRoll();
		try{
			//��ȡ����
			String phone =this.payNo;
			//������ˮ��
			serialNo = TelecomMsg.getSerial();
			//����������Ϣ
			String rollback = this.roll;
			String[] str = rollback.split("#");
			rollSerialNo=str[0];
			roll.setAccountNo(phone);
			roll.setPayFee(str[1]);
			roll.setRollSerialNo(rollSerialNo);
			roll.setSerialNo(Constant.AGENT_CODE + serialNo);
			roll.setTermPhoneNum(str[2]);
			roll.setMessageSerialNo(Tools.getNow() + Constant.AGENT_CODE + serialNo);
			roll.setBagLength(str[1].length() +  phone.length());
			byte[] sendMsg = roll.rollMsg();
			
			Telcom telcom = new Telcom(Constant.IP, Constant.PORT);
			boolean isConnect = telcom.connect();
		    if(!isConnect){
				Log.error("���ŷ������������쳣��ҵ����ˮ��:" + rollSerialNo);
				resultList.add("008");
				return resultList;
		    }
			telcom.setTimeOut(80);
			telcom.send(sendMsg);
			byte[] receiveMsg = telcom.receive();
			telcom.socketClose();
			
			if(receiveMsg == null || receiveMsg.length < 5){
				Log.error("���ŷ�����Ϣ����ʧ�ܣ�ҵ����ˮ�ţ�" + serialNo + "; receive == null");
				resultList.add("666");
				return resultList;
			}
			
			ResponseRoll response = new ResponseRoll(receiveMsg);
			
			//���Ʒѵ���Ӧ��ת����SCP����Ӧ��
			String respScpCode = TelecomMsg.telcomToScpRecode(response.getResponseCode());
			
			if(!response.getResponseCode().equals(Constant.RESPONSE_SUCCESS)
					&& !response.getResponseCode().equals("5109")){
				Log.error("ҵ����ˮ�ţ�" + serialNo + "; ��Ӧ��:" + response.getResponseCode() + "; ��ϸ˵����" + response.getResponseInfo());
				resultList.add(respScpCode);
				return resultList;
			}
			
			Map<String,String> maps= revertResponse(seqNo, rollSerialNo, respScpCode);
			resultList.add(respScpCode);
			resultList.add(maps);
			return resultList;
		}catch(Exception e){
			Log.error("��������,����������", e);
			resultList.add("001");
			return resultList;
		}
	}

	/**
	 * 
	 * @param seqNo1
	 * @param rollSerialNo1
	 * @param respCode1
	 * @return map
	 */
	public  Map<String,String> revertResponse(String seqNo1 ,String rollSerialNo1,String respCode1)
	{
		 Map<String,String> map=new HashMap<String, String>();
		        map.put("seqNo", seqNo1);
				map.put("SerialNo", rollSerialNo1);
				map.put("respCode", respCode1);
			    return map;
	}
	
	
	public static void main(String[] args){
		RevertBusiness bus =new RevertBusiness("201304221426570007000001#1234500#04075582025590",
				"075582025590","22222222222222222222222222");
		List list=bus.deal();
		System.out.println((String)list.get(0));
		System.out.println((Map)list.get(1));
		
		
		System.out.println("HRD00912208012000000140111080000111901016163278126958030000EBA06E24DCE6FABDE2C18FF3B1589915".substring(59, 67));
		
	}


}
