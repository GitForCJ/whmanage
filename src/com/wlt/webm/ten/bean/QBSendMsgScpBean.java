package com.wlt.webm.ten.bean;

import java.sql.SQLException;
import java.util.List;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.tencent.QBTransferAccounts;
import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.UniqueNo;
import com.wlt.webm.util.Tools;

/**
 * 财付通转账发送SCP业务处理<br>
 */
public class QBSendMsgScpBean {

	/**
	 * 交易订单号
	 */
	private String transaction_id;
	private String qq;

	/**
	 * 
	 */
	public QBSendMsgScpBean() {

	}

	/**
	 * @param transaction_id
	 */
	public QBSendMsgScpBean(String transaction_id,String qq) {
		this.transaction_id = transaction_id;
		this.qq=qq;
	}

	/**
	 * 获得交易流水号
	 * @param userNo
	 * @return 交易流水号
	 */
	public static String getSeqNo(String userNo) {
		return Tools.getNow3().substring(2) + userNo
				+ UniqueNo.getInstance().getNoTwo();
	}

	/**
      *用户资金账户划账
	 */
	public void run(String tradeNo) {
		Log.info("QB run()......");
		int payfee = 0,poundage=0;
		String timeString = "";
		String resourceid ="";
		DBService dbService = null;
		String sqlString = "select tradetime , fundacct  from wlt_orderForm_"+Tools.getNow3().substring(2, 6)+" where tradeserial='"+transaction_id+"'";
		String sqlInt = "select fee  from wlt_orderForm_"+Tools.getNow3().substring(2, 6)+" where tradeserial='"+transaction_id+"'";
		int money = 0;
		List list = null;
		try {
			dbService = new DBService();
			money = dbService.getInt(sqlInt);
			list = dbService.getStringList(sqlString);
		if (0!=money) {
			payfee = money;
			Log.info("payfee:"+payfee);
			timeString = (String) list.get(0);
			resourceid = (String) list.get(1);
			QBTransferAccounts.deal(transaction_id, timeString, payfee+"", resourceid,qq);
			} 
		} catch (NumberFormatException e) {
				Log.error("QB"+e.toString());
				e.printStackTrace();
			} catch (Exception e) { 
				Log.error("QB"+e.toString());
				e.printStackTrace();
			} 
			finally{
				Log.info("Q币充值完成");
				dbService.close();
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
			Log.info("更改scp状态成功");
		} catch (SQLException e) {
			Log.info("更改scp状态失败");
			e.printStackTrace();
		} finally{
			dbService1.close();
		}
	}
	
	public static void main(String[] args){
		QBSendMsgScpBean sb =new QBSendMsgScpBean();
		sb.run("10004");
	}
}
