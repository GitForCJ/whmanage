package com.wlt.webm.business.dianx.bean;

import java.io.ByteArrayOutputStream;

import com.wlt.webm.uictool.Constant;
import com.wlt.webm.uictool.MD5;
import com.wlt.webm.uictool.Tools;

public class RequestAgentQuery {

	/**
	 * 流水号
	 */
	private byte[] serialNo = new byte[10];
	
	/**
	 * 包总长度
	 */
	private byte[] bagLength = new byte[8];

	/**
	 * 查询消息组包
	 * @return 返回查询消息包
	 */
	public byte[] queryMsg(){
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		try{
			//包头信息
			bout.write(Constant.BAG_HEAD);
			bout.write(serialNo);
			bout.write(bagLength);
			bout.write(Constant.QUERY_TYPE);
			bout.write("1".getBytes());//扁平结构
			bout.write("0".getBytes());//不校验
			bout.write("01".getBytes());//参数个数
			bout.write(Constant.SEND_ID);
			bout.write(Constant.RECEIVE_ID);
			bout.write(Constant.BLANK_SPACE);
			bout.write(Constant.CHECK_INFO);
			//以下为包体信息
			bout.write("708".getBytes());
			bout.write("001".getBytes());
			bout.write("03".getBytes());
			
			bout.write("7081".getBytes());
			bout.write("0004".getBytes());
			bout.write(Constant.AGENT_CODE.getBytes());
			
			bout.write("7082".getBytes());
			bout.write(Tools.add0(String.valueOf(Constant.AGENT_PASSWORD.length()), 4).getBytes());
			bout.write(Constant.AGENT_PASSWORD.getBytes());
			
			bout.write("7093".getBytes());
			bout.write(Tools.add0(String.valueOf(Constant.AGENT_IP.length()),4).getBytes());
			bout.write(Constant.AGENT_IP.getBytes());
			
		}catch(Exception e){
			System.out.println("查询组包异常"+e.toString());
		}
		System.out.println("代理商余额查询报文:"+ new String(bout.toByteArray()));
		System.out.println(new String(bout.toByteArray()).length());
		return bout.toByteArray();
	}
	
	/**
	 * 包的总长度
	 * @param bagLength
	 */
	public void setBagLength(int length) {
		int passLen = MD5.encode(Constant.AGENT_PASSWORD).trim().length();
		this.bagLength = Tools.add0(String.valueOf(Constant.BAG_LENGTH_MAC + Constant.AGENT_IP.length()),8).getBytes();
	}

	/**
	 * 流水号
	 * @param serialNo
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = Tools.add0(serialNo,10).getBytes();
	}
}
