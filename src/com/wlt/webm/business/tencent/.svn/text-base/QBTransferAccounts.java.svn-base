package com.wlt.webm.business.tencent;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scpdb.DBOperator;
import com.wlt.webm.scpdb.DBService;
import com.wlt.webm.scpdb.FundAcctDao;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.scputil.Tools;

/**
 * <p>@Description: Q币内部业务处理</p>
 */ 
public class QBTransferAccounts {
	

/**
 * 
 * @param transaction_id
 * @param time
 * @param total_fee
 * @param facct
 * @param qq
 * @return
 */
	public static String  deal(String transaction_id,String time,
			String total_fee,String facct,String qq){
		DBService db = null;
		String flag="000"; 
		try {
			db = new DBService();
			db.setAutoCommit(false);
			// 判断流水号是否存在, 避免重复消息
			String yearMonth = DateParser.getNowDate().substring(2, 6);
			String tableName = "wlt_acctbill_" + yearMonth;
			String sql = "select childfacct from " + tableName
					+ " where dealserial='" + transaction_id+"'";
			System.out.println("sql:"+sql);
			if(db.hasData(sql))
			{
				flag = "001";
				Log.info("QB充值:收到重复消息");
			}else {
				String remark = "QB充值";
				// 数据库操作
				DBOperator dbOper = new DBOperator(db);
				// 到押金账户;
				FundAcctDao facctDB = new FundAcctDao(db);
				//记录账户明细表
	            StringBuffer sql2 = new StringBuffer();	
	            sql2.append("insert into wlt_acctbill_"+yearMonth+"(childfacct,dealserial,tradeaccount,tradetime,tradefee,tradetype,`explain`,state,distime,accountleft,tradeserial,other_childfacct,pay_type)" +
	            		" values(");
	            sql2.append("'"+facct+"',");
	            sql2.append("'"+transaction_id+"',");
	            sql2.append("'"+qq+"',");
	            sql2.append("'"+time+"',");
	            sql2.append("'"+total_fee+"',");
	            sql2.append("35,");
	            sql2.append("'Q币充值',");
	            sql2.append("0,");
	            sql2.append("'"+DateParser.getNowDateTime()+"',");
	            String sqlFuncLeft = "select usableleft from wlt_childfacct where childfacct = "+facct;
	            sql2.append("'"+(Integer.parseInt(db.getString(sqlFuncLeft))-Integer.parseInt(total_fee))+"',");
	            sql2.append("'"+transaction_id+"',");
	            sql2.append("'',");
	            sql2.append("1");
	            sql2.append(")");
				db.update(sql2.toString());
				sql2=null;
				// 更新资金帐户余额
				facctDB.updateChildLeft(facct, -(Integer.parseInt(total_fee)),
						null);
				String current=Tools.getNow();
				facctDB.updateQBState(current, transaction_id);
			} 
			db.commit();
		}catch (Exception ex) {
			db.rollback();
			flag="001";
			Log.info("QB业务内部处理出错"+ex.toString());
		}
		finally
		{
			if(db!=null)
			db.close();
		}

		Log.info("处理QB业务内部处理结束");
		return flag;
	}

}
