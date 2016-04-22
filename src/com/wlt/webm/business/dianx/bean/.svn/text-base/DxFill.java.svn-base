package com.wlt.webm.business.dianx.bean;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.Business;

import com.wlt.webm.message.TelecomMsg;
import com.wlt.webm.uictool.Constant;
import com.wlt.webm.uictool.Tools;

/**
 * 电信充值
 * @author Administrator
 *
 */
public class DxFill implements Business {
	/**
	 * 内部交易流水号
	 */
	private String seqNo = null;
	/**
	 * 自增流水号
	 */
	private String serialNo = null;
	/**
	 * 缴费号码
	 */
	private String phone=null;
	/**
	 * 缴费金额
	 */
	private String payFee = null;
	/**
	 * 区号
	 */
	private String areaCode=null;
	/**
	 * 号码类型
	 */
	private String numType = null;
    /**
     * 销账流水号
     */
	private String tradeSerialNo=null;
	
	
	public DxFill(){
		
	}
	
	public DxFill(String seqNo,String phone,String payFee,
			String areaCode,String numType,String tradeSerialNo){
		this.phone=phone;
		this.seqNo=seqNo;
		this.payFee=payFee;
		this.areaCode=areaCode;
		this.numType=numType;
		this.tradeSerialNo=tradeSerialNo;
	}
	
	public int  deal() {
			RequestFill fill = new RequestFill();
			try {
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
				Log.info("tradeAccountNo"+tradeAccountNo);
				fill.setOperCode(operCode);
				fill.setAccountNo(tradeAccountNo);// 交易账号
				fill.setPayFee(Tools.fenToYuan(payFee));// 缴费金额
				fill.setSerialNo(Constant.AGENT_CODE + serialNo);// 包头流水号
				fill.setTermPhoneNum("04075582025590");// 终端电话号码
				fill.setTermSerialNo(serialNo);// 终端流水号
				fill.setTradeSerialNo(tradeSerialNo);// 交易流水号
				fill.setBagLength(Tools.fenToYuan(payFee).trim().length()
						+ "04075582025590".length() + tradeAccountNo.trim().length());
				byte[] sendMsg = fill.fillMsg();
				Telcom telcom = new Telcom(Constant.IP, Constant.PORT);
				boolean isConnect = telcom.connect();
				if (!isConnect) {
					Log.error("电信缴费连接主机异常，业务流水号:" + seqNo);
					return -1;
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
					roll.setTermPhoneNum("04075582025590");
					roll.setMessageSerialNo(Tools.getNow() + Constant.AGENT_CODE + serialNo);
					roll.setBagLength(Tools.fenToYuan(payFee).trim().length() + phone.trim().length());
					byte[] senMsg = roll.rollMsg();
					telcom = new Telcom(Constant.IP, Constant.PORT);
					isConnect = telcom.connect();
					if (!isConnect) {
						Log.error("电信返销连接主机异常，业务流水号:" + seqNo);
						return -1;
					}
					telcom.setTimeOut(80);
					telcom.send(senMsg);//发送返销消息
					byte[] recRollMsg = telcom.receive();//接收返销消息
					
					Log.error("业务流水号：" + seqNo + ";缴费失败，计费返回超时.seqno: " + seqNo + ";号码：" + phone);
					//计费未返回缴费消息，则发起返销请求后，向SCP返回503--系统繁忙，请稍候再试
					return -1;
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
					return -1;
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
				String checkBill = tradeSerialNo + "|" + "04075582025590" + "|" + operCode
						+ "|" + accountNo + "|" + payFee;
				// 返销所需数据
				String rollBill = tradeSerialNo + "#" + payFee + "#" + "04075582025590";
				// 获取查询时保存的明细信息，打印出来
				StringBuffer toScpMsg = new StringBuffer().append(Constant
						.getToscpmessage(seqNo));

//				String rst = "缴费金额$" + Tools.fenToYuan(payFee) + "元";
				String rst = "缴费金额$" + Tools.fenToYuan(payFee) + "元" + "|账号余额$"
				+ leftFee + "元";
				if (!toScpMsg.toString().equals("0")) {
					rst = toScpMsg.append("|").append(rst).toString();
				}
				Log.info("缴费返回的消息发送给SCP，流水号：" + serialNo + "; 计费返回码：" + respScpCode
						+ "|");
			} catch (Exception e) {
				Log.error("固网缴费，解析包错误", e);
				return -1;
			}
			return 0;
	}

}
