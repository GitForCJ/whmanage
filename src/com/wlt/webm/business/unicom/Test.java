package com.wlt.webm.business.unicom;

import java.io.File;

import com.commsoft.epay.util.DateParser;
import com.wlt.webm.tool.MD5;
import com.wlt.webm.tool.Tools;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("开始缴费...");
		/*Unicom tel=new Unicom("218.17.225.81",12112);
		tel.connect();
		if(tel.isConnected()){
			System.out.println("建立链接成功！");
		}else{
			System.out.println("建立链接失败！");
		}*/
		/*RequestPing ping = new RequestPing();
		byte[] sendMsg=ping.pingMsg();
		tel.send(sendMsg);
		//接收计费返回消息
		byte[] receiveMsg = tel.receive();
		tel.socketClose();//关闭socket连接
		if(receiveMsg == null || new String(receiveMsg).trim().length() < 5	){
			System.out.println("电信查询消息接收失败，业务流水号：" );
			System.out.println("receiveMsg="+receiveMsg);
			System.out.println("new String(receiveMsg).trim().length()="+new String(receiveMsg).trim().length());
			System.out.println("new String(receiveMsg).trim()="+new String(receiveMsg).trim());
			return;
		}
		System.out.println("心跳接收消息："+new String(receiveMsg));*/
		
		
		
		/*TestSignIn signIn = new TestSignIn();
		RequestSignIn request =new RequestSignIn();
		byte[] message = request.signInMsg();
		signIn.send(tel,message);		
		System.out.println(tel.receive().length);
		String frameID = getFrameId();
		//byte[] macStr = getMacStr("1034"+"20001034"+frameID+"as234q");
		byte[] macStr = Unicom.getMacStr(new String(Constant.FACTORYID)+new String(Constant.TERMINALID)+frameID+new String(Constant.UNIPASSWORD));
		 //reqTraceID="1034"+Tools.getNow().substring(2);
		 String reqTraceID=Constant.FACTORYID_UNICOM+DateParser.getNowDateTime().substring(4)+UniqueNo.getInstance().getNoTwo();*/
		/**
		 * 查询模拟测试
		 */
	  /*RequestQuery query = new RequestQuery();
		query.setBillValue("0000010000".getBytes());
		frameID = getFrameId();
		macStr = getMacStr("1034"+"20001034"+frameID+"as234q");
		query.setFrameID(frameID.getBytes());
		query.setMacStr(macStr);
		String type=Constant.CMD_QUERY_TYPE;
		query.setType(type.getBytes());
		query.setQueryReqTraceID("1034101101110534".getBytes());
		query.setReqTraceID(reqTraceID.getBytes());
		query.setUserNumber("18602030356         ".getBytes());
		byte[] mess = query.queryMsg();
		System.out.println(new String(mess));
		tel.send(mess);
		//接收计费返回消息
		byte[] receiveMsg = tel.receive();
		System.out.println("接收交易查询响应消息"+new String(receiveMsg));
		tel.socketClose();//关闭socket连接
		if(receiveMsg == null || new String(receiveMsg).trim().length() < 5	){
			System.out.println("电信查询消息接收失败，业务流水号：" );
			System.out.println("receiveMsg="+receiveMsg);
			System.out.println("new String(receiveMsg).trim().length()="+new String(receiveMsg).trim().length());
			System.out.println("new String(receiveMsg).trim()="+new String(receiveMsg).trim());
			return;
		}
		 
		//电信查询信息解析类
		ResponseQuery response = new ResponseQuery(receiveMsg);
		TelecomMsg.initTelecomRecode();
		//转换为SCP的响应码
		String respScpCode = TelecomMsg.telcomToScpRecode(response.getResponseCode());
		
		System.out.println("响应码：" + response.getResponseCode());
		System.out.println("响应码：" + respScpCode);*/
		
		
		
		
		
		/**
		 * 冲正模拟测试
		 */
		/*RequestRoll roll = new RequestRoll();
		reqTraceID="1034"+Tools.getNow().substring(2);
		roll.setBillValue("0000010000".getBytes());
		frameID = getFrameId();
		macStr = getMacStr("1034"+"20001034"+frameID+"as234q");
		roll.setFrameID(frameID.getBytes());
		String type=Constant.CMD_ROLL_TYPE;
		roll.setType(type.getBytes());
		roll.setMacStr(macStr);
		roll.setOldReqTraceID("1034101101105727".getBytes());
		roll.setReqTraceID(reqTraceID.getBytes());
		roll.setUserNumber("18602030356         ".getBytes());
		byte[] mess=roll.rollMsg();
		tel.send(mess);
		//接收计费返回消息
		byte[] receiveMsg = tel.receive();
		tel.socketClose();//关闭socket连接
		if(receiveMsg == null || new String(receiveMsg).trim().length() < 5	){
			System.out.println("电信查询消息接收失败，业务流水号：" );
			System.out.println("receiveMsg="+receiveMsg);
			System.out.println("new String(receiveMsg).trim().length()="+new String(receiveMsg).trim().length());
			System.out.println("new String(receiveMsg).trim()="+new String(receiveMsg).trim());
			return;
		}
		 
		//电信查询信息解析类
		ResponseRoll response = new ResponseRoll(receiveMsg);
		TelecomMsg.initTelecomRecode();
		//转换为SCP的响应码
		String respScpCode = TelecomMsg.telcomToScpRecode(response.getResponseCode());
		
		System.out.println("响应码：" + response.getResponseCode());
		System.out.println("响应码：" + respScpCode);*/
		
		/**
		 * 缴费模拟测试
		 */
		/*RequestFill fill = new RequestFill();
		System.out.println("缴费流水号"+frameID);
		fill.setBillValue("0000003000".getBytes());
		frameID = getFrameId();
		//macStr = getMacStr("1034"+"20001034"+frameID+"as234q");
		fill.setFrameID(frameID.getBytes());
		fill.setMacStr(macStr);
		fill.setReqTraceID(reqTraceID.getBytes());
		String type=Constant.CMD_FILL_TYPE;
		fill.setType(type.getBytes());
		System.out.println("------------->"+reqTraceID);
		fill.setUserNumber("18675520378         ".getBytes());		
		byte[] mess=fill.fillMsg();
        tel.send(mess);
       
        //System.out.println(tel.receive().length);
        //接收计费返回消息
		byte[] receiveMsg = tel.receive();
		System.out.println("收到充值消息："+new String(receiveMsg));
        tel.socketClose();
        if(receiveMsg == null || new String(receiveMsg).trim().length() < 5	){
			System.out.println("电信查询消息接收失败，业务流水号：" );
			System.out.println("receiveMsg="+receiveMsg);
			System.out.println("new String(receiveMsg).trim().length()="+new String(receiveMsg).trim().length());
			System.out.println("new String(receiveMsg).trim()="+new String(receiveMsg).trim());
			return;
		}*/
		//电信查询信息解析类
		/*String str="WT020810381220124001120310112923522300380005D0FFF87D51714E6D51FCB9744860AFBF20101129235254                10341129235223022018675520378         00000030000005";
		byte[] receiveMsg =str.getBytes();
		ResponseFill response = new ResponseFill(receiveMsg);
		TelecomMsg.initTelecomRecode();
		//转换为SCP的响应码
		String respScpCode = TelecomMsg.telcomToScpRecode(response.getResponseCode());
	    
		System.out.println("头部响应码：" + response.getErrStatus());
		System.out.println("计费响应码：" + response.getResponseCode());
		System.out.println("响应码：" + respScpCode);*/
		/*String getIp="218.17.225.84";
		String getUser="20124001";
		String getPwd="fu88fajf";
		FtpGetFile ftp =  new FtpGetFile(getIp,getUser,getPwd);
		boolean load = false;
		String filldir = "/";
		String unicomTerminal="20124001";
		String yesterdate = DateParser.getYesterdayDate();
		filldir+= unicomTerminal+"_" + yesterdate; 
		String localFile="F:/test2.doing";
		load = ftp.getFile(filldir, localFile);
		String putIp="192.168.1.118";
		String putUser="root";
		String putPwd="commsoft";
		String ftpPath="/ftp/";
		FtpPutFile ftp2=new FtpPutFile(putIp,putUser,putPwd,ftpPath);
		String ftpFileName="F:/test2.doing";
		String tofile="test3.doing";
		String fileResult="test3.doing";
		ftp2.putFile(ftpFileName, tofile, fileResult);*/
		String ftpFileName="F:/test2.doing";
		if(new File(ftpFileName).exists()){
			System.out.println("文件存在");
		}else{
			System.out.println("文件不存在");
		}
		System.out.println("取文件结束");
	}
	private static int number =0;
	public static String getFrameId(){
		number++;
		if(number>9999)
			number = 1;
		 String frameID = Tools.headFillZero(number+"", 4);
		 frameID = Tools.getNow().substring(2)+frameID;
		return frameID;
	}
	public static byte[] getMacStr(String str){
		return MD5.encode(str).toUpperCase().getBytes();
	}
}
