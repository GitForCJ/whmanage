package com.wlt.webm.scpdb;

import java.util.List;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.scputil.bean.BillBean;
import com.wlt.webm.scputil.bean.BusiAgentBean;
import com.wlt.webm.scputil.bean.FacctBillBean;
import com.wlt.webm.scputil.bean.TradeBean;


/**
 * <p>@Description: 日志相关数据库操作</p>
 */
public class DBLog {
	
	/**
	 * 数据库连接对象
	 */
	private DBService db = null;

	/**
	 * 构造方法 
	 * @param db 连接对象
	 */
	public DBLog(DBService db) {
		this.db = db;
	}
	
	
/**
 * 
 * @param areaCode
 * @param termAcct
 * @param sepNo
 * @param payNo
 * @param payFee
 * @param remark
 * @param accNbr
 * @param nowTime
 * @param tradeNo
 * @param source
 * @param storeSeq 
 * @param writeoff 
 * @param state 
 * @return check
 * @throws Exception
 */
	public boolean insertOrderForm(String areaCode, String termAcct, String sepNo,
			String payNo, int payFee, String remark, String accNbr,
			String nowTime, String tradeNo,int state) throws Exception {
		boolean check = false;
		StringBuffer sql = new StringBuffer();
		String yearMonth = nowTime.substring(2,6);
		String tableName = "ec_orderForm_"+yearMonth;
		sql.append("insert into "+tableName+" values (0,'").append(areaCode).append(
				"','").append(termAcct).append("','").append(sepNo).append("','")
				.append(payNo).append("',").append(payFee).append(",'")
				.append(remark).append("','").append(accNbr).append("','").append(
				nowTime).append("','").append(tradeNo).append("',")
				.append(",'").append("','").append("',")
				.append(state).append(")");
		try {
			db.update(sql.toString());
			check = true;
		}catch(Exception ex)
		{
			check = false;
			Log.error("记录订单信息日志出错:",ex);
			throw ex;
		}
		
		return check;
	}
	
	
	
	/**
	 * 记录交易日志
	 * @param tradeInf 日志信息
	 * @return 返回true表示处理成功,反之失败 
	 * @throws Exception 
	 */
	public boolean insertTradelog(TradeBean tradeInf) throws Exception {
		boolean check = false;
		String yearMonth = tradeInf.getDisTime().substring(2,6);
		String tableName = "ec_tradelog_"+yearMonth;
		String sql = "insert into "+tableName+" values (0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try
		{
//			String[] num =tradeInf.getTradeInf();
//			Log.info(num[9]+"-1--");
//			Log.info(num[10]+"--2-");
//			Log.info(num[11]+"--3-");
//			Log.info(num[12]+"-4--");
//			Log.info(num[13]+"--5-");
//			Log.info(num[14]+"--6-");
//			Log.info(num[15]+"-7--");
			db.update(sql,tradeInf.getTradeInf());
			check = true;
		}catch(Exception ex)
		{
			check = false;
			Log.error("记录交易日志出错:",ex);
			throw ex;
		}
		
		return check;
	}
	
	/**
	 * 记录资金帐户的帐户日志
	 * @param facct 帐户日志信息
	 * @return 返回true表示处理成功,反之失败 
	 * @throws Exception 
	 */
	public boolean insertAcctLog(FacctBillBean facct) throws Exception 
	{
		boolean check = false;
//		String childfacct = facct.getFundAcct();
//		long accountleft = getChildFacctLeft(childfacct)+facct.getTradeFee();
//		facct.setAccountleft(String.valueOf(accountleft));
		String yearMonth = facct.getFacctBill()[3].substring(2, 6);
		String tableName = "wlt_acctbill_"+yearMonth;
		String sql = "insert into "+tableName+"(childfacct,dealserial,tradeaccount,tradetime,tradefee,tradetype,`explain`,state,distime,accountleft,tradeserial,other_childfacct,pay_type) " +
				"values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try
		{
			db.update(sql,facct.getFacctBill());
			check = true;
		}catch(Exception ex)
		{
			check = false;
			Log.error("记录资金帐户的帐户日志出错:",ex);
			throw ex;
		}
		return check;
	}
	
	/**
	 * 获得子资金帐户余额
	 * @param childFacct 资金子帐户
	 * @return 返回资金子帐户余额
	 */
	public long getChildFacctLeft(String childFacct)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select usableleft from wlt_childfacct where childfacct='")
		   .append(childFacct).append("'");

		long fee = 0;
		try
		{
			fee = db.getLong(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询子资金帐号余额出错:",ex);
		}
		return fee;
	}
	/**
	 * 记录代办商品日志
	 * @param busBean 代办商品日志信息
	 * @return 返回true表示处理成功,反之失败 
	 * @throws Exception 
	 */
	public boolean insertBusiAgentlog(BusiAgentBean busBean) throws Exception 
	{
		boolean check = false;
		String tableName = "ec_busiagentlog";
		String sql = "insert into "+tableName+" values (0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try
		{
			db.update(sql,busBean.getBusiAgent());
			check = true;
		}catch(Exception ex)
		{
			check = false;
			Log.error("记录资金帐户的帐户日志出错:",ex);
			throw ex;
		}
		return check;
	}
	/**
	 * 记录销帐日志
	 * @param billBean  销帐日志信息
	 * @return 返回true表示处理成功,反之失败 
	 * @throws Exception 
	 */
	public boolean insertPayBillLog(BillBean billBean) throws Exception 
	{
		boolean check = false;
		String yearMonth = billBean.getDisTime().substring(2,6);
		String tableName = "wlt_writeoff_"+yearMonth;
		String sql = "insert into "+tableName+" values (0,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try
		{
			String[] bill = billBean.getPayBillInf();
			/*for(int i = 0; i < bill.length; i ++){
				Log.info( i + "*********" + bill[i]);
			}*/
			db.update(sql,bill);
			
			
			check = true;
		}catch(Exception ex)
		{
			check = false;
			Log.error("记录销帐日志出错:",ex);
			throw ex;
		}
		
		return check;
	}
	/**
	 * 修改新银行转账日志状态
	 * @param seqNo 流水号
	 * @param state 状态
	 * @throws Exception 
	 */
	public void updateBankTransferLog(String seqNo,int state) throws Exception 
	{
		String sql = "update ec_banktransferlog set state="+state+"," +
		"distime='"+DateParser.getNowDateTime()+"' where serial='"+seqNo+"'";
		
		try
		{
			db.update(sql);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			Log.error("修改银行转账日志出错:",ex);
		}
	}
	/**
	 * 修改新销帐日志状态
	 * @param seqNo 流水号
	 * @param state 状态
	 * @throws Exception 
	 */
	public void updatePayBillLog(String seqNo,int state) throws Exception 
	{
		String yearMonth = seqNo.substring(0,4);
		String tableName = "ec_writeoff_"+yearMonth;
		String sql = "update "+tableName+" set state="+state+"," +
		"distime='"+DateParser.getNowDateTime()+"' where tradeserial='"+seqNo+"'";

		try
		{
			db.update(sql);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			Log.error("修改销帐日志出错:",ex);
		}
	}
	/**
	 * 修改新销帐日志状态
	 * @param seqNo 流水号
	 * @param state 状态
	 * @param checkState 状态
	 * @throws Exception 
	 */
	public void updatePayBillLog(String seqNo ,int state , int checkState) throws Exception 
	{
		String yearMonth = DateParser.getNowMonth().substring(2);
		String tableName = "ec_writeoff_"+yearMonth;
		String sql = "update "+tableName+" set state="+state+", checkstate=  " +checkState +
		", distime='"+DateParser.getNowDateTime()+"' where writeoffseq='"+seqNo+"'";
        Log.info("修改writeoff="+sql);
		try
		{
			db.update(sql);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			Log.error("修改销帐日志出错:",ex);
		}
	}
	/**
	 * 修改交易日志状态
	 * @param seqNo 流水号
	 * @param state 状态
	 * @throws Exception 
	 */
	public void updateTradeLog(String seqNo,int state) throws Exception 
	{
		String yearMonth = seqNo.substring(0,4);
		String tableName = "ec_tradelog_"+yearMonth;
		String sql = "update "+tableName+" set state="+state+","+
		"distime='"+DateParser.getNowDateTime()+"' where tradeserial='"+seqNo+"'";
		Log.info("ec_tradelog="+sql);
		try
		{
			db.update(sql);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			Log.error("修改状态日志出错:",ex);
			throw ex;
		}
	}
	
	
	/**
	 * 修改交易日志状态
	 * @param seqNo 流水号
	 * @throws Exception 
	 */
	public void updateTaskLog(String seqNo) throws Exception 
	{
		String shoulddate = "20"+seqNo.substring(0,6);
		String taskName = "'em_tradelog','em_writeoff'," +
				"'em_agenttrade_day',"+
			    "'em_termtradenum_day','em_termtrade_day'";
		String sql = "delete ec_tasklog "+
		"where shoulddate='"+shoulddate+"' and taskname in(" 
		+taskName+ ")";
		Log.info("task sql : "+sql);

		try
		{
			db.update(sql);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			Log.error("修改状态日志出错:",ex);
			throw ex;
		}
	}
	/**
	 * 修改代办日志状态
	 * @param seqNo 流水号
	 * @param state 状态
	 * @throws Exception 
	 */
	public void updateBusiLog(String seqNo,String state) throws Exception 
	{
		String tableName = "ec_busiagentlog";
		String sql = "update "+tableName+" set state="+state+","+
		"distime='"+DateParser.getNowDateTime()+"' where account='"+seqNo+"'";
		
		try
		{
			db.update(sql);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			Log.error("修改状态日志出错:",ex);
			throw ex;
		}
	}
	

	/**
	 * 查询交易日志信息
	 * @param seqNo  流水号
	 * @return 返回TradeBean对象
	 * @throws Exception
	 */
	public TradeBean getTradeLog(String seqNo) throws Exception
	{
		String yearMonth = seqNo.substring(0,4);
		String tableName = "ec_tradelog_"+yearMonth;
		TradeBean tradeInf = null;
		String sql = "select areacode,resourceid,accessnbr,tradeserial,tradeobject,tradeoppos," +
		"payaccount,tradetime,duefee,fee,explain,paytype,service,distime,state," +
		"source from "+tableName+" where state!=1 and state!=2 and tradeserial='"+seqNo+"'";
		Log.info("sql===="+sql);
		String[] rsStr = null;
		
		try
		{
			rsStr = db.getStringArray(sql);
			if(rsStr != null)
			{
				tradeInf = new TradeBean();
				tradeInf.setTradeInf(rsStr);
			}
		}catch(Exception ex)
		{
			Log.error("查询交易日志信息出错:",ex);
			throw ex;
		}
		
		return tradeInf;
	}
	/**
	 * 查询冲正信息
	 * @param serviceseqno  流水号
	 * @return String 冲正数据
	 * @throws Exception
	 */
	public String getBackInfo(String serviceseqno) throws Exception
	{
		StringBuffer sql=new StringBuffer();
		sql.append("select backdata from ec_backinfo ")
		   .append(" where serviceseqno='").append(serviceseqno).append("'");
		String rsStr = null;
		rsStr = db.getString(sql.toString());
		return rsStr;
	}
	
	/**
	 * 查询销账信息日志信息
	 * @param seqNo  流水号
	 * @return 返回TradeBean对象
	 * @throws Exception
	 */
	public String[] getWriteOffLog(String seqNo) throws Exception
	{
		String yearMonth = DateParser.getNowMonth().substring(2);
		String tableName = "ec_writeoff_"+yearMonth;
		String sql = "select * from "+
		tableName+" where (checkstate=1 or checkstate=2) and writeoffseq='"+seqNo+"'";
		String[] rsStr = null;
		Log.info("查询销账信息:"+sql);
		try
		{
			rsStr = db.getStringArray(sql);
		}catch(Exception ex)
		{
			Log.error("查询交易日志信息出错:",ex);
			throw ex;
		}
		
		return rsStr;
	}
	/**
	 * 获取帐户日志
	 * @param seqNo 流水号
	 * @return 返回String[],包括子资金帐户,流水号,交易帐号,交易金额
	 * @throws Exception
	 */
	public String[] getFacctBill(String seqNo) throws Exception 
	{
		String yearMonth = seqNo.substring(0,4);
		String tableName = "ec_acctbill_"+yearMonth;
		String sql = "select childfacct,dealserial,tradeaccount,tradefee from "+tableName+" where dealserial='"+seqNo+"'";
		Log.info("获取资金帐户Sql:"+sql);
		String[] rsStr = null;
		
		try
		{
			rsStr = db.getStringArray(sql);
		}catch(Exception ex)
		{
			Log.error("获取资金帐户的帐户日志出错:",ex);
			throw ex;
		}
		
		return rsStr;
	}
	
	/**
	 * 查询查询资金帐户日志是否存在
	 * @param seqNo 流水号
	 * @param date 日期
	 * @param tradetype 交易类型
	 * @return 返回true表示已存在,反之表示不存在
	 * @throws Exception
	 */
	public boolean billIsExist(String seqNo,String date,String tradetype) throws Exception
	{
		String yearMonth = date.substring(2,6);
		String tableName = "ec_acctbill_"+yearMonth;
		String sql = "select * from "+tableName+" where dealserial='"+seqNo+"' and tradetype="+tradetype;
		boolean check = false;
		
		try
		{
			check = db.hasData(sql);
		}catch(Exception ex)
		{
			Log.error("查询资金帐户日志是否存在出错:",ex);
			throw ex;
		}
		
		return check;
	}
	
	/**
	 * 查询查询资金帐户日志是否存在
	 * @param payNo 交易号码
	 * @return 返回true表示已存在,反之表示不存在
	 * @throws Exception
	 */
	public String getSeqNo(String payNo) throws Exception
	{
		String rsStr = null;
		String yearMonth = DateParser.getNowMonth().substring(2, 6);
		String tableName = "ec_tradelog_"+yearMonth;
		String sql = "select first 1 tradeserial  from "+tableName+" where tradeobject='"+payNo+"' and tradetime like '"+DateParser.getNowDate()+"%' order by tradetime desc";
		
		try
		{
			rsStr = db.getString(sql);
		}catch(Exception ex)
		{
			Log.error("查询资金帐户日志是否存在出错:",ex);
			throw ex;
		}
		
		return rsStr;
	}
	
	/**
	 * 统计各业务类型的交易笔数和交易金额
	 * @param userAcct 用户帐号(终端编号或portal帐号)
	 * @param sdate 起始日期(yyyyMMdd)
	 * @param edate 终止日期(yyyyMMdd)
	 * @return 返回统计数据
	 * @throws Exception
	 */
	public List getTradeCount(String userAcct,String sdate,String edate) throws Exception
	{
		List allMonth = DateParser.getAllMonth(sdate.substring(0,6),edate.substring(0,6)); 
		StringBuffer sql =new StringBuffer();
		for(int i=0;i<allMonth.size();i++)
		{
			String date = (String)allMonth.get(i);
			String tableName = "ec_tradelog_"+date.substring(2,6);
			
			if(i == 0)
			{
				sql.append("select name,count(id) nums,sum(duefee) fee,state from \n")
				   .append(tableName).append(" a,ec_service b where a.service=b.code \n")
				   .append("and resourceid='").append(userAcct)
				   .append("' and tradetime>='")
				   .append(sdate).append("000000' and tradetime<='")
				   .append(edate).append("235959' group by name,state\n")
				   .append("into temp aa with no log; \n");
				Log.info("i==0"+sql );
			}else
			{
				sql.delete(0,sql.length());
				sql.append("insert into aa \n")
				   .append("select name,count(id) ,sum(duefee),state from \n")
				   .append(tableName).append(" a,ec_service b where a.service=b.code \n")
				   .append("and resourceid='").append(userAcct)
				   .append("' and tradetime>='")
				   .append(sdate).append("000000' and tradetime<='")
				   .append(edate).append("235959' group by name,state; \n");
				Log.info("i!=0"+sql );
			}
			
			try
			{
				db.update(sql.toString());
			}catch(Exception ex)
			{
				Log.error("统计交易日志,创建临时表出错:",ex);
			}
		}
		
		sql.delete(0,sql.length());
		sql.append("select name,sum(nums),sum(fee),state from aa group by name,state");
		Log.info("select"+sql );
		List rsList = null;
		try
		{
			rsList = db.getList(sql.toString());
		}catch(Exception ex)
		{
			Log.error("统计交易日志出错:",ex);
			throw ex;
		}
		
		finally
		{
			sql.delete(0,sql.length());
			sql.append("drop table aa;");
			try
			{
				db.update(sql.toString());
			}catch(Exception ex)
			{
				Log.error("统计交易日志,删除临时表出错:",ex);
			}
		}
		
		return rsList;
	}
}
