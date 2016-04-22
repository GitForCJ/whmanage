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
	 * 内部交易流水号
	 */
	private String seqNo = null;

	/**
	 * 自增流水号
	 */
	private String serialNo = null;

	/**
	 * 缴费金额
	 */
	private String payFee = null;
	/**
	 * 号码类型
	 */
	private String numType = null;
	/**
	 *  号码
	 */
	private String PayNo=null;
	/**
	 * 区号
	 */
	private String areaCode=null;
	/**
	 * 终端电话号码
	 */
	private String termNo=null;
	/**
	 * 销账流水号
	 */
	private String tradeSerialNo=null;
	
	/**
	 *默认为先查询在缴费
	 */
	private int  flag=0; 
	
	
	/**
	 * 广东电信缴费类构造方法
	 * @param seqNo  内部交易流水号
	 * @param numType 号码类型
	 * @param payFee   金额（分）
	 * @param PayNo     缴费号码
	 * @param areaCode  区号
	 * @param termNo    终端电话
	 * @param tradeSerialNo 销账流水号 
	 * @param flag  0表示先查询再缴费 1表示直接缴费
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
     * @return 008 电信缴费连接主机异常 503 缴费失败，计费返回超时
     */
	public ArrayList<Object> deal(){
		ArrayList<Object> resultList=new ArrayList<Object>();
		RequestFill fill = new RequestFill();
		try {
			// 获取号码
			String phone = this.PayNo;
			// 自增流水号
			serialNo = TelecomMsg.getSerial();			 
			// 操作码--合同缴费
			String operCode = Constant.OPERATE_CODE_CONTACT;
			if(numType.equals("01")){
				operCode=Constant.OPERATE_CODE_PHONE;//电话充值
			}else if(numType.equals("02")){
				operCode=Constant.OPERATE_CODE_BROADBAND;//宽带充值
			}else{
				operCode=Constant.OPERATE_CODE_CONTACT;//合同缴费
			}
			Log.info("缴费号码类型："+operCode);
			String tradeAccountNo = phone;
			// 判断交易账号是否带区号或者是手机用户
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
			fill.setAccountNo(tradeAccountNo);// 交易账号
			fill.setPayFee(Tools.fenToYuan(payFee));// 缴费金额
			fill.setSerialNo(Constant.AGENT_CODE + serialNo);// 包头流水号
			fill.setTermPhoneNum(termNo);// 终端电话号码
			fill.setTermSerialNo(serialNo);// 终端流水号
			fill.setTradeSerialNo(tradeSerialNo);// 交易流水号
			fill.setBagLength(Tools.fenToYuan(payFee).trim().length()
					+ termNo.trim().length() + tradeAccountNo.trim().length());
			byte[] sendMsg = fill.fillMsg();
			Telcom telcom = new Telcom(Constant.IP, Constant.PORT);
			boolean isConnect = telcom.connect();
			if (!isConnect) {
				Log.error("电信缴费连接主机异常，业务流水号:" + seqNo);
				resultList.add("008");
				return resultList;
			}
			telcom.setTimeOut(80);// 设置缴费时间
			// 发送缴费信息
			telcom.send(sendMsg);
			// 接收计费返回消息
			byte[] receiveMsg = telcom.receive();
			// 关闭连接
			telcom.socketClose();
			if (receiveMsg == null
					|| new String(receiveMsg).trim().length() < 5) {
				Log.error("电信缴费信息接收失败，业务流水号：" + seqNo + "; receive == null");
				Log.info("正在返销..........");
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
				Log.error("电信返销连接主机异常，业务流水号:" + seqNo);
				resultList.add("008");
				return resultList;
				}
				telcom.setTimeOut(80);
				telcom.send(senMsg);//发送返销消息
				byte[] recRollMsg = telcom.receive();//接收返销消息
				Log.error("业务流水号：" + seqNo + ";缴费失败，计费返回超时.seqno: " + seqNo + ";号码：" + phone);
				resultList.add("503");
				return resultList;
			}

			// 电信返回缴费解析
			ResponseFill response = new ResponseFill(receiveMsg);
			// 将计费返回码转换为SCP返回码
			String respScpCode = TelecomMsg.telcomToScpRecode(response
					.getResponseCode());
			// 对响应码进行判断
			if (!response.getResponseCode().equals(Constant.RESPONSE_SUCCESS)) {
			Log.error("业务流水号：" + seqNo + "; 响应码:"
						+ response.getResponseCode() + "; 详细说明："
						+ response.getResponseInfo());
				resultList.add(respScpCode);
				return resultList;
			}

			// 返回成功，则继续解析
			// 交易账号
			String accountNo = response.getAccountNo();
			// 支付金额
			String payFee = String.valueOf(Integer.parseInt(response
					.getPayFee()));
			// 充值/缴费类型
			//String payType = response.getPayType();
			// 账号类型
			//String accountType = response.getAccountType();
			// 账号余额
			String leftFee = response.getLeftFee();
			// 代理商账号余额
			//String agentAccount = response.getAgentAccount();

			// 对账所需数据
			String checkBill = tradeSerialNo + "|" + termNo + "|" + operCode
					+ "|" + accountNo + "|" + payFee;
			// 返销所需数据
			String rollBill = tradeSerialNo + "#" + payFee + "#" + termNo;
			// 获取查询时保存的明细信息，打印出来
			StringBuffer toScpMsg = new StringBuffer();
			if(flag==0)
			{
				toScpMsg.append(Constant.getToscpmessage(seqNo));
			}
			String rst = "缴费金额$" + Tools.fenToYuan(payFee) + "元" + "|账号余额$"
			+ leftFee + "元";
//			if (!toScpMsg.toString().equals("0")) {
//				rst = toScpMsg.append("|").append(rst).toString();
//			}
			Map<String,String> mes = fillResponse(seqNo, tradeSerialNo,
					respScpCode, payFee, serialNo, accountNo, rst, checkBill,
					rollBill);
			Log.info("电信缴费返回的消息，流水号：" + serialNo + "; 计费返回码：" + respScpCode
					+ "|" + mes.toString());
			resultList.add(respScpCode);
			resultList.add(mes);
			return resultList;
		} catch (Exception e) {
			Log.error("固网缴费，解析包错误", e);
			resultList.add("001");
			return resultList;
 		}

	}
	
	/**
	 * 缴费返回消息封装
	 * @param seqNo1		内部流水号
	 * @param tradeSerialNo1	计费流水号
	 * @param respCode1	返回码
	 * @param payfee1	缴费金额
	 * @param serialNo1	初始流水号
	 * @param payno1	缴费号码
	 * @param rst1	账单明细
	 * @param checkbill1	对账信息
	 * @param rollmess1		返销信息
	 * @return map
	 */
	public static Map<String,String> fillResponse(String seqNo1,String tradeSerialNo1,String respCode1,
			String payfee1,String serialNo1, String payno1, String rst1, String checkbill1,String rollmess1)
	{
		System.out.println("组装电信缴费内部返回消息"+checkbill1+"   "+rollmess1);
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
