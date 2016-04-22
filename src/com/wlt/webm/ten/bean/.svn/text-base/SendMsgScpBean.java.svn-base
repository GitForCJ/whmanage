package com.wlt.webm.ten.bean;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import whmessgae.TradeMsg;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.message.Msg;
import com.wlt.webm.message.PortalReceive;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.pccommon.UniqueNo;
import com.wlt.webm.util.Tools;

/**
 * 财付通转账发送SCP业务处理<br>
 */
public class SendMsgScpBean {

	/**
	 * 交易订单号
	 */
	private String transaction_id;

	/**
	 * 
	 */
	public SendMsgScpBean() {

	}

	/**
	 * @param transaction_id
	 */
	public SendMsgScpBean(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	/**
	 * 获得交易流水号
	 * 
	 * @param userNo
	 * @return 交易流水号
	 */
	public static String getSeqNo(String userNo) {
		return Tools.getNow3().substring(2) + userNo
				+ UniqueNo.getInstance().getNoTwo();
	}

	/**
	 * 向用户资金账户划账
	 */
	public void run(String tradeNo) {
		Log.info("财付通内部转账...");
		String TradeSeqNo = "";
		int payfee = 0, poundage = 0;
		String timeString = "";
		String userno ="";
		Object[] params = { transaction_id };
		DBService dbService = null;
		String sqlString = "select txstatechg , sp_billno ,rolenum  from wht_tenpaytransferlog where transaction_id=?";
		String sqlInt = "select pay_fee , poundage from wht_tenpaytransferlog where transaction_id=?";
		int[] money = null;
		List list = null;
		try {
			dbService = new DBService();
			money = dbService.getIntArray(sqlInt, params);
			list = dbService.getStringList(sqlString, params);
		} catch (SQLException e1) {
			Log.info("查询数据库获取财付通转账部分日志信息失败");
			e1.printStackTrace();
		}
		if ((money != null) && (list != null)) {
			payfee = money[0];
			poundage = money[1];
			Log.info("财付通：payfee:"+payfee);
			Log.info(list.get(0).toString());
			timeString = (String) list.get(0);
			// 流水号
			TradeSeqNo = (String) list.get(1);
			userno=  (String) list.get(2);
			TradeMsg msg=new TradeMsg();
			msg.setSeqNo(TradeSeqNo);
			msg.setFlag("0");//有内容
			msg.setMsgFrom2("0103");
			msg.setServNo("00");
			msg.setTradeNo("10001");
			msg.setRsCode("000");
			Map<String, String> content =new HashMap<String, String>();
			content.put("transaction_id", transaction_id);
			content.put("time", timeString);
			content.put("total_fee", payfee+"");
			content.put("HandCharge", poundage+"");
			content.put("userno", userno);
			msg.setContent(content);
			PortalSend.getInstance().sendmsg(msg);
			int k=0;
			TradeMsg rsMsg=null;
			while(k<180){
				k++;
				try {
					Thread.sleep(1000);
				}catch (InterruptedException e) {
					e.printStackTrace();
				}
				rsMsg=MemcacheConfig.getInstance().get(TradeSeqNo);
				if(null==rsMsg){
					continue;
				}else{
					break;
				}
			}
			if(null!=rsMsg){
			String flag=msg.getRsCode();
			if("000".equals(flag)){
				Log.info("财付通内部划账成功"+transaction_id);
			}else{
				Log.info("财付通内部划账失败"+transaction_id);
			}
			}else{
				Log.info("财付通内部划账超时"+transaction_id);
			}
	}
	}
	
	/**
	 * @param sql
	 */
	public void updateScpFlag(String sql){
		DBService dbService1 =null;
		try {
			dbService1=new DBService();
			dbService1.update(sql);
			Log.info("更新财付通/新生转账日志表SCP状态成功");
		} catch (SQLException e) {
			Log.info("更新财付通/新生转账日志表SCP状态失败");
			e.printStackTrace();
		} finally{
			dbService1.close();
		}
	}
}
