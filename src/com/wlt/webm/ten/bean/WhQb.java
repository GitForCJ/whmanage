package com.wlt.webm.ten.bean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import whmessgae.TradeMsg;

import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.PortalSend;
import com.commsoft.epay.util.logging.Log;

/**
 * 2014-12-17
 * @author caiSJ
 *万恒充值平台Q币充值接口
 */
public class WhQb {

	/**
    * 根据管理员配置 Q币数量判断是否启用Q币接口
    * @param db
    * @param num  Q币数量
    * @return true false
	 */
	public static boolean isUse(DBService db,int num){
	  boolean flag=false;
	  int a;
	try {
		a = db.getInt("SELECT reversalcount FROM sys_reversalcount WHERE tradetype=4 and user_login='admin' AND user_no='0000000001'");
		if(a==1&&(num==1||num==5||num==10||num==20||num==30)){
			flag=true;
		}
	} catch (SQLException e) {
		e.printStackTrace();
		Log.error("查询Q币接口是否启用失败:"+e.toString());
	}
	  return flag;
	}
	
	/**
	 * Q币充值
	 * @param seqNo   消息流水号
	 * @param orderid   订单号
	 * @param qbaccount Q币账号
	 * @param tradefee  金额(厘)
	 * @return  0成功 -1失败 -2其他超时  
	 */
	public static int useQB(String seqNo,String orderid,String qbaccount,int tradefee){
		int n=-2;
		TradeMsg msg = new TradeMsg();
		msg.setSeqNo(seqNo);
		msg.setFlag("0");// 有内容
		msg.setMsgFrom2("0103");
		msg.setServNo("00");
		msg.setTradeNo("09024");
		Map<String, String> content = new HashMap<String, String>();
		content.put("phoneNum", qbaccount);
		content.put("serialNo", orderid);
		content.put("tradeFee", tradefee+"");
        msg.setContent(content);
        //发送
		if (!PortalSend.getInstance().sendmsg(msg)) {
			n=-1;
			return n;
		}
		int k=0;
		TradeMsg rsMsg = null;
		while (k < 190) {
			k++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			rsMsg = MemcacheConfig.getInstance().get(seqNo);
			if (null == rsMsg) {
				continue;
			} else {
				MemcacheConfig.getInstance().delete(seqNo);
				break;
			}
		}
		if (null != rsMsg && k<90) {
			String code = rsMsg.getRsCode();
			// 000成功  501余额不足 001失败 500系统异常  007连接失败
	        if("000".equals(code)){
	        	n=0;
	        }else if("501".equals(code)){
	        	n=-1;
	        	//更新数据库状态
	        	DBService db=null;
	        	try {
					db=new DBService();
					db.update("update sys_reversalcount set reversalcount=2 WHERE tradetype=4 and user_login='admin' AND user_no='0000000001'");
				} catch (SQLException e) {
					e.printStackTrace();
					Log.error("更新Q币接口状态失败:"+e.toString());
				}finally{
					if(null!=db){
						db.close();
					}
				}
	        }else if("001".equals(code)||"007".equals(code)){
	        	n=-1;
	        }
		}
	    return n;
	}
	
	/**
	 * 查询Q币多通道 总开关
	 * @return boolean 
	 */
	public static boolean IsMultiChannel(){
		DBService db=null;
		try {
			db=new DBService();
			int a = db.getInt("SELECT reversalcount FROM sys_reversalcount WHERE tradetype=4 and user_login='admin' AND user_no='0000000001'");
			if(a==1){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			Log.error("查询Q币多通道总开关是否启用失败:"+e.toString());
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
		  return false;
	}
}
