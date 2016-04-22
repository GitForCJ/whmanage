package com.wlt.webm.business.dianx.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
public class QueryBusiness{
	
	/**
	 *  消息流水号
	 */
	private String seqNo = null;
	/**
	 * 计费流水
	 */
	private String serialNo = null;
	
	private String termNo=null;
	private String areaCode=null;
	private String numType=null;
	private String PayNo=null;
	
	   
	/**
	 * 广东电信查询接口
	 * @param seqNo  内部流水号
	 * @param PayNo  交易号码
	 * @param termNo  终端号码
	 * @param areaCode 区号
	 * @param NumType  缴费类型
	 */
	      public QueryBusiness(String seqNo,String PayNo,String termNo,String areaCode,String NumType){
	    	  this.seqNo=seqNo;
	    	  this.PayNo=PayNo;
	    	  this.termNo=termNo;
	    	  this.areaCode=areaCode;
	          this.numType=NumType;
	      }
	    	  
	
	/**
	 * 业务流程
	 * @return  ArrayList<Object> deal()
	 */
	public ArrayList<Object> deal() {
		ArrayList<Object> resultList=new ArrayList<Object>();
	    RequestQuery query = new RequestQuery();
	  try{
			String phone = this.PayNo;
			//自增流水号
			serialNo = TelecomMsg.getSerial();
			//包头流水号
			String packageSerial = Constant.AGENT_CODE + serialNo;
			//操作码
			String operCode = Constant.OPERATE_CODE_CONTACT;
			if(numType.equals("01")){
				operCode=Constant.OPERATE_CODE_PHONE;//电话充值
			}else if(numType.equals("02")){
				operCode=Constant.OPERATE_CODE_BROADBAND;//宽带充值
			}else{
				operCode=Constant.OPERATE_CODE_CONTACT;//合同缴费
			}
			Log.info("缴费号码类型："+operCode);
			//交易账号
			String accountPhone = areaCode + phone;
			//不带区号的号码
			String realPhone = phone;
			if(phone.startsWith("1")){
				accountPhone = phone;
			}else if(phone.startsWith("02")){
				areaCode = phone.substring(0, 3);
				realPhone = phone.substring(3, phone.length());
				accountPhone = areaCode + realPhone;
			}else if(phone.startsWith("07")){
				areaCode = phone.substring(0, 4);
				realPhone = phone.substring(4, phone.length());
				accountPhone = areaCode + realPhone;
			}else if(phone.startsWith("06")){
				areaCode = phone.substring(0, 4);
				realPhone = phone.substring(4, phone.length());
				accountPhone = areaCode + realPhone;
			}
			
			query.setOperCode(operCode);//操作码
			query.setAccountNo(accountPhone);//交易账号
			query.setSerialNo(packageSerial);//包头流水号
			query.setTermPhoneNum(termNo);//终端编号
			query.setBagLength(accountPhone.length() + termNo.length());//包长度
			
			byte[] sendMsg = query.queryMsg();
			
			Telcom telcom = new Telcom(Constant.IP, Constant.PORT);
			boolean isConnect = telcom.connect();
			if(!isConnect){
				Log.error("电信缴费连接主机异常，业务流水号:" + serialNo + ": receive == null");
				resultList.add("008");
				return resultList;
			}
			telcom.setTimeOut(80);
//			发送查询请求
			telcom.send(sendMsg);
			
//			接收计费返回消息
			byte[] receiveMsg = telcom.receive();
			telcom.socketClose();//关闭socket连接
			if(receiveMsg == null || new String(receiveMsg).trim().length() < 5	|| !new String(receiveMsg).trim().startsWith("FFFF" + packageSerial)){
				Log.error("电信查询消息接收失败，业务流水号：" + serialNo);
				resultList.add("008");
				return resultList;
			}
			//电信查询信息解析类
			ResponseQuery response = new ResponseQuery(receiveMsg);
			//转换为SCP的响应码
			String respScpCode = TelecomMsg.telcomToScpRecode(response.getResponseCode());
			
			Log.info("响应码：" + response.getResponseCode());
			
			//对响应码进行判断
			if(!response.getResponseCode().equals(Constant.RESPONSE_SUCCESS)){
				Log.error("业务流水号：" + serialNo + "响应码：" + response.getResponseCode() + "; 详细说明：" + response.getResponseInfo());
				resultList.add(respScpCode);
				return resultList;
			}
			//返回成功，则继续解析
			
			//充值/缴费类型
			String payType = response.getPayType();
			Log.info("充值缴费类型：" + payType);
			
			//号码归属地
			String numAddr = response.getNumAddr();
			Log.info("号码归属地" + numAddr);
			
			//账号余额
			String leftFee = response.getLeftFee();
			String leftFee1 = "";
			if(leftFee.startsWith("-")){
				leftFee1 = leftFee.substring(1, leftFee.length());
			}
			//账号类型
			String accountType = response.getAccountType();
			String payInfo = termNo;
			String rst = "账号余额$" + leftFee + "元" + "|账号类型$" + accountType;
			Constant.setToscpmessage(seqNo, "0");
			String accountNo = response.getAccountNo();
			Map<String,Object> mes =queryResponse(seqNo,serialNo, phone, "预付费号码", Tools.yuanToFen(leftFee1), respScpCode, payInfo, rst);
			//如果是后付费用户，则自动调用INF0033接口查询详单
			if(payType.equals("1")){
				Log.info("后付费号码:" + realPhone + ",调用后付费查询接口");
				//设置查询的相关信息
				RequestQueryDetail queryDetail = new RequestQueryDetail();
//				自增流水号
				serialNo = TelecomMsg.getSerial();
				queryDetail.setSerialNo(Constant.AGENT_CODE + serialNo);//包头流水号
				queryDetail.setAccountNo(realPhone);
				queryDetail.setAreaCode(numAddr);
				queryDetail.setBagLength(realPhone.length() + numAddr.length());
				String feeType="0";
				if(numType.equals("03")){
					feeType="1";//电话充值
				}
				queryDetail.setFeeType(feeType);
				//组成发送包
				byte[] send = queryDetail.queryMsg();
				Telcom tel = new Telcom(Constant.IP, Constant.PORT);
				boolean connect = tel.connect();
				if(!connect){
					Log.error("电信缴费连接主机异常，业务流水号:" + serialNo + ": receive == null");
					resultList.add("008");
					return resultList;
				}
				tel.setTimeOut(50);
				//发送查询请求
				tel.send(send);
				//接收信息
				byte[] receive = tel.receive();
				
 				tel.socketClose();//关闭socket连接
				if(receive == null || new String(receive).trim().length() < 5){
					Log.error("电信查询消息接收失败，业务流水号：" + serialNo + ": receive == null");
					resultList.add("008");
					return resultList;
				}
				
				//电信查询信息解析类
				ResponseQueryDetail resp = new ResponseQueryDetail(receive,areaCode);
				//转换为SCP的响应码
				String respCode = TelecomMsg.telcomToScpRecode(resp.getResponseCode());
				Log.info("响应码：" + resp.getResponseCode() + "|" + respCode);
				
				//对响应码进行判断
				if(!resp.getResponseCode().equals(Constant.RESPONSE_SUCCESS) && !resp.getResponseCode().equals("0")){
					Log.error("业务流水号：" + serialNo + "响应码：" + resp.getResponseCode() + "; 详细说明：" + resp.getResponseInfo());				
					resultList.add(respCode);
					return resultList;
				}
				//用户名
				String userName = resp.getUserName();
				//费用
//				String fee = resp.getFee();   此处用INF0016接口返回的金额来代替
				//账单日期
				String accountDate = "";	
				//发票明细信息
				StringBuffer detail = new StringBuffer("");
				//判断是否有话费查询详细信息
				if(resp.getParamsNum() == 3){
					//发票明细
					HashMap detailMap = resp.getDetailMap();
//					StringBuffer detail = new StringBuffer();
					//解析发票明细信息
					Iterator it = detailMap.keySet().iterator();
					while(it.hasNext()){
						String key = (String)it.next();
						String value = (String)detailMap.get(key);
						detail.append(key)
						.append("$")
						.append(Tools.fenToYuan(value) + "元")
						.append("|");
					}
					
					accountDate = resp.getAccountBegin() + "-" + resp.getAccountEnd();
					detail.append("计费周期")
					.append("$")
					.append(accountDate);
				}
				
				if(resp.getParamsNum() == 3){
					rst = detail.toString();
					Constant.setToscpmessage(seqNo, rst);
				}else{
					rst = "账号余额$" + leftFee  + "元" + "|账号类型$" + accountType;
					Constant.setToscpmessage(seqNo, "0");
				}				
				mes = queryResponse(seqNo, serialNo, phone, userName, Tools.yuanToFen(leftFee1), respCode, payInfo, rst);
				resultList.add("000");
				resultList.add(mes);
				return resultList;
			}else{
				resultList.add("000");
				resultList.add(mes);
				return resultList;
			}
		}catch(Exception e){
			Log.error("固网查询，解析包错误", e);
			resultList.add("001");
			return resultList;
		}
	}

	
	
	/**
	 * 
	 * @param seqNo
	 * @param serialNo
	 * @param phone
	 * @param userName
	 * @param totalFee
	 * @param respCode
	 * @param payInfo
	 * @param rst
	 * @return Map<String,Object> map
	 */
	public  Map<String,Object> queryResponse(String seqNo,String serialNo,String phone, String userName,
			String totalFee,String respCode,String payInfo,String rst)
	{
		 Map<String,Object> map=new HashMap<String, Object>();
			//设置消息流水号
			    map.put("seqNo",seqNo);
		    	map.put("respCode",respCode);
				map.put("PayNo", phone);
				map.put("UserName", userName);
				map.put("monthNum", "1");
				//账单内容
				HashMap<String,String> detailMap= new HashMap<String,String>();
				detailMap.put("Date", "00000000");
				detailMap.put("Num", "1");			
				detailMap.put("TotalFee", totalFee);
				detailMap.put("PayInfo1", payInfo);
				detailMap.put("Rst1_1",rst);	
				map.put("RsMon1", detailMap);
			return map;
	}
	
	
	/**
	 * 测试用
	 * @param args
	 */
	public static void main(String[] args){
		
		QueryBusiness bus =new QueryBusiness("29999","075582871089","04111111","0755","03");
		bus.deal();
		
		
	}
		
}
