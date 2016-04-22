package com.wlt.webm.business.tencent;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scpdb.DBOperator;
import com.wlt.webm.scpdb.DBService;
import com.wlt.webm.scpdb.FundAcctDao;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.scputil.Tools;

/**
 * <p>@Description: 通过腾讯财付通向平台账户划账</p>
 * <p>@version 3.0.3.0</p>
 */ 
public class TransferAccounts  {
	

	/**
	 * 默认
	 */
	public TransferAccounts(){
		
	}
	
    /**
     * 划账处理方法
     */
	public  static String  deal(String transaction_id,String time,String total_fee,String HandCharge,
			String resourceid,String serialNumber){
		String totalfee1 ="";
		DBService db = null;
		String flag="000";
		try {
			db = new DBService();
			db.setAutoCommit(false);
			// 判断流水号是否存在, 避免重复消息
			String yearMonth = DateParser.getNowDate().substring(2, 6);
			String tableName = "wlt_acctbill_" + yearMonth;
			String sql = "select childfacct from " + tableName
					+ " where dealserial='" + serialNumber+"'";
			if(db.hasData(sql))
			{
				Log.info("财付通转账业务:收到重复消息");
				flag= "001";
			} else {
				String remark = "财付通接口转账";
				// 数据库操作
				DBOperator dbOper = new DBOperator(db);
				String facct = dbOper.getBindFacct(resourceid);
				// 充值到押金账户
				String childFacct = facct + Constant.FACCT_DEPOSIT_TYPE;


				FundAcctDao facctDB = new FundAcctDao(db);
				// 判断是否需要手续费
				if (HandCharge != null && !HandCharge.equals("0")) {
					// 实际转账金额
					totalfee1 = ""+ (Integer.parseInt(total_fee) - Integer.parseInt(HandCharge));
				}
				totalfee1=""+Integer.parseInt(total_fee);
				//记录账户明细表
	            StringBuffer sql2 = new StringBuffer();	
	            sql2.append("insert into wlt_acctbill_"+yearMonth+"(childfacct,dealserial,tradeaccount,tradetime,tradefee,tradetype,`explain`,state,distime,accountleft,tradeserial,other_childfacct,pay_type)" +
	            		" values(");
	            sql2.append("'"+childFacct+"',");
	            sql2.append("'"+serialNumber+"',");
	            sql2.append("'"+childFacct+"',");
	            sql2.append("'"+time+"',");
	            sql2.append("'"+totalfee1+"',");
	            sql2.append("33,");
	            sql2.append("'财付通转账',");
	            sql2.append("0,");
	            sql2.append("'"+DateParser.getNowDateTime()+"',");
	            String sqlFuncLeft = "select usableleft from wlt_childfacct where childfacct = "+childFacct;
	            sql2.append("'"+db.getString(sqlFuncLeft)+"',");
	            sql2.append("'"+transaction_id+"',");
	            sql2.append("'"+childFacct+"',");
	            sql2.append("2");
	            sql2.append(")");
				db.update(sql2.toString());
				sql2=null;
				//更新资金帐户余额
				facctDB.updateChildLeft(childFacct, Integer.parseInt(totalfee1),
						null);
				String current=Tools.getNow();
				facctDB.updateScpState(current, transaction_id);
			} 
			db.commit();
		}catch (Exception ex) {
			flag="001";
			db.rollback();
			Log.info("财付通转账业务内部处理出错了"+ex.toString());
		}
		finally
		{
			if(db!=null)
			db.close();
		}

		Log.info("处理财付通转账业务内部处理结束");
		return flag;
	}
	
}
