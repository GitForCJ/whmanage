package com.wlt.webm.business.dianx.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.message.TelecomMsg;
import com.wlt.webm.uictool.Constant;
import com.wlt.webm.uictool.Tools;

/**
 *
 * @author lenovo
 *
 */
public class FillBusiness {
	/**
	 * �ڲ�������ˮ��
	 */
	private String seqNo = null;

	/**
	 * ������ˮ��
	 */
	private String serialNo = null;

	/**
	 * �ɷѽ��
	 */
	private String payFee = null;
	/**
	 * ��������
	 */
	private String numType = null;
	/**
	 *  ����
	 */
	private String PayNo=null;
	/**
	 * ����
	 */
	private String areaCode=null;
	/**
	 * �ն˵绰����
	 */
	private String termNo=null;
	/**
	 * ������ˮ��
	 */
	private String tradeSerialNo=null;
	
	/**
	 *Ĭ��Ϊ�Ȳ�ѯ�ڽɷ�
	 */
	private int  flag=0; 
	
	
	/**
	 * �㶫���Žɷ��๹�췽��
	 * @param seqNo  �ڲ�������ˮ��
	 * @param numType ��������
	 * @param payFee   ���֣�
	 * @param PayNo     �ɷѺ���
	 * @param areaCode  ����
	 * @param termNo    �ն˵绰
	 * @param tradeSerialNo ������ˮ�� 
	 * @param flag  0��ʾ�Ȳ�ѯ�ٽɷ� 1��ʾֱ�ӽɷ�
	 */
		public FillBusiness(String seqNo,String numType,String payFee,String PayNo,String areaCode
				,String termNo,String tradeSerialNo,int flag){
			this.seqNo=seqNo;
			this.numType=numType;
			this.payFee=payFee;
			this.areaCode=areaCode;
			this.termNo=termNo;
			this.tradeSerialNo=tradeSerialNo;
			System.out.println("tradeSerialNo:"+tradeSerialNo);
			this.PayNo=PayNo;
			this.flag=flag;
		}

    /**
     * 
     * @return 008 ���Žɷ����������쳣 503 �ɷ�ʧ�ܣ��Ʒѷ��س�ʱ
     */
	public ArrayList<Object> deal(){
		ArrayList<Object> resultList=new ArrayList<Object>();
		RequestFill fill = new RequestFill();
		try {
			// ��ȡ����
			String phone = this.PayNo;
			// ������ˮ��
			serialNo = TelecomMsg.getSerial();			 
			// ������--��ͬ�ɷ�
			String operCode = Constant.OPERATE_CODE_CONTACT;
			if(numType.equals("01")){
				operCode=Constant.OPERATE_CODE_PHONE;//�绰��ֵ
			}else if(numType.equals("02")){
				operCode=Constant.OPERATE_CODE_BROADBAND;//�����ֵ
			}else{
				operCode=Constant.OPERATE_CODE_CONTACT;//��ͬ�ɷ�
			}
			Log.info("�ɷѺ������ͣ�"+operCode);
			String tradeAccountNo = phone;
			// �жϽ����˺��Ƿ�����Ż������ֻ��û�
			if (!phone.startsWith("0") && !phone.startsWith("1")&&!numType.equals("02")) {
				if("0020".equals(areaCode)){
					areaCode="020";
				}
				tradeAccountNo = areaCode + phone;
			}
			if(phone.startsWith("0")&&(phone.length()<9)){
				tradeAccountNo = areaCode + phone;
			}
			Log.info("tradeAccountNo"+tradeAccountNo);
			fill.setOperCode(operCode);
			fill.setAccountNo(tradeAccountNo);// �����˺�
			fill.setPayFee(Tools.fenToYuan(payFee));// �ɷѽ��
			fill.setSerialNo(Constant.AGENT_CODE + serialNo);// ��ͷ��ˮ��
			fill.setTermPhoneNum(termNo);// �ն˵绰����
			fill.setTermSerialNo(serialNo);// �ն���ˮ��
			fill.setTradeSerialNo(tradeSerialNo);// ������ˮ��
			fill.setBagLength(Tools.fenToYuan(payFee).trim().length()
					+ termNo.trim().length() + tradeAccountNo.trim().length());
			byte[] sendMsg = fill.fillMsg();
			Telcom telcom = new Telcom(Constant.IP, Constant.PORT);
			boolean isConnect = telcom.connect();
			if (!isConnect) {
				Log.error("���Žɷ����������쳣��ҵ����ˮ��:" + seqNo);
				resultList.add("008");
				return resultList;
			}
			telcom.setTimeOut(80);// ���ýɷ�ʱ��
			// ���ͽɷ���Ϣ
			telcom.send(sendMsg);
			// ���ռƷѷ�����Ϣ
			byte[] receiveMsg = telcom.receive();
			// �ر�����
			telcom.socketClose();
			if (receiveMsg == null
					|| new String(receiveMsg).trim().length() < 5) {
				Log.error("���Žɷ���Ϣ����ʧ�ܣ�ҵ����ˮ�ţ�" + seqNo + "; receive == null");
				Log.info("���ڷ���..........");
				RequestRoll roll = new RequestRoll();
				roll.setAccountNo(phone);
				roll.setRollSerialNo(tradeSerialNo);
				roll.setPayFee(Tools.fenToYuan(payFee));
				roll.setSerialNo(Constant.AGENT_CODE + serialNo);
				roll.setTermPhoneNum(termNo);
				roll.setMessageSerialNo(Tools.getNow() + Constant.AGENT_CODE + serialNo);
				roll.setBagLength(Tools.fenToYuan(payFee).trim().length() + phone.trim().length());
				byte[] senMsg = roll.rollMsg();
				telcom = new Telcom(Constant.IP, Constant.PORT);
				isConnect = telcom.connect();
				if (!isConnect) {
				Log.error("���ŷ������������쳣��ҵ����ˮ��:" + seqNo);
				resultList.add("008");
				return resultList;
				}
				telcom.setTimeOut(80);
				telcom.send(senMsg);//���ͷ�����Ϣ
				byte[] recRollMsg = telcom.receive();//���շ�����Ϣ
				Log.error("ҵ����ˮ�ţ�" + seqNo + ";�ɷ�ʧ�ܣ��Ʒѷ��س�ʱ.seqno: " + seqNo + ";���룺" + phone);
				resultList.add("503");
				return resultList;
			}

			// ���ŷ��ؽɷѽ���
			ResponseFill response = new ResponseFill(receiveMsg);
			// ���Ʒѷ�����ת��ΪSCP������
			String respScpCode = TelecomMsg.telcomToScpRecode(response
					.getResponseCode());
			// ����Ӧ������ж�
			if (!response.getResponseCode().equals(Constant.RESPONSE_SUCCESS)) {
			Log.error("ҵ����ˮ�ţ�" + seqNo + "; ��Ӧ��:"
						+ response.getResponseCode() + "; ��ϸ˵����"
						+ response.getResponseInfo());
				resultList.add(respScpCode);
				return resultList;
			}

			// ���سɹ������������
			// �����˺�
			String accountNo = response.getAccountNo();
			// ֧�����
			String payFee = String.valueOf(Integer.parseInt(response
					.getPayFee()));
			// ��ֵ/�ɷ�����
			//String payType = response.getPayType();
			// �˺�����
			//String accountType = response.getAccountType();
			// �˺����
			String leftFee = response.getLeftFee();
			// �������˺����
			//String agentAccount = response.getAgentAccount();

			// ������������
			String checkBill = tradeSerialNo + "|" + termNo + "|" + operCode
					+ "|" + accountNo + "|" + payFee;
			// ������������
			String rollBill = tradeSerialNo + "#" + payFee + "#" + termNo;
			// ��ȡ��ѯʱ�������ϸ��Ϣ����ӡ����
			StringBuffer toScpMsg = new StringBuffer();
			if(flag==0)
			{
				toScpMsg.append(Constant.getToscpmessage(seqNo));
			}
			String rst = "�ɷѽ��$" + Tools.fenToYuan(payFee) + "Ԫ" + "|�˺����$"
			+ leftFee + "Ԫ";
//			if (!toScpMsg.toString().equals("0")) {
//				rst = toScpMsg.append("|").append(rst).toString();
//			}
			Map<String,String> mes = fillResponse(seqNo, tradeSerialNo,
					respScpCode, payFee, serialNo, accountNo, rst, checkBill,
					rollBill);
			Log.info("���Žɷѷ��ص���Ϣ����ˮ�ţ�" + serialNo + "; �Ʒѷ����룺" + respScpCode
					+ "|" + mes.toString());
			resultList.add(respScpCode);
			resultList.add(mes);
			return resultList;
		} catch (Exception e) {
			Log.error("�����ɷѣ�����������", e);
			resultList.add("001");
			return resultList;
 		}

	}
	
	/**
	 * �ɷѷ�����Ϣ��װ
	 * @param seqNo1		�ڲ���ˮ��
	 * @param tradeSerialNo1	�Ʒ���ˮ��
	 * @param respCode1	������
	 * @param payfee1	�ɷѽ��
	 * @param serialNo1	��ʼ��ˮ��
	 * @param payno1	�ɷѺ���
	 * @param rst1	�˵���ϸ
	 * @param checkbill1	������Ϣ
	 * @param rollmess1		������Ϣ
	 * @return map
	 */
	public static Map<String,String> fillResponse(String seqNo1,String tradeSerialNo1,String respCode1,
			String payfee1,String serialNo1, String payno1, String rst1, String checkbill1,String rollmess1)
	{
		System.out.println("��װ���Žɷ��ڲ�������Ϣ"+checkbill1+"   "+rollmess1);
				Map<String, String> map = new HashMap<String,String>();
				map.put("seqNo", seqNo1);
				map.put("SerialNo", tradeSerialNo1);
				map.put("PayFee", payfee1);
				map.put("PayNo", payno1);
				map.put("TradeNo", serialNo1);
				map.put("Num", "1");
				map.put("Rst1", rst1);
				map.put("CheckBill", checkbill1);
				map.put("RollBack", rollmess1);
				map.put("response", respCode1);
			
			
			return map;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args){

		StringBuffer toScpMsg = new StringBuffer();
		if(!toScpMsg.toString().equals("0")){
			System.out.println("=========");
		}
	
	}

	
}
