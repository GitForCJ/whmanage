package com.wlt.webm.scpdb;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scpcommon.RespCode;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.scputil.Tools;

/**
 * 创建日期：2013-11-26
 * <p>@Title: 深圳康索特电子代办系统业务控制中心模块</p>
 * <p>@Description: 资金帐户相关数据库操作</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: 深圳市宝恒科技有限公司 </p> 
 * <p>@author caiSJ</p>
 * <p>@version 1.0.0</p>
 */
public class FundAcctDao {
	
	/**
	 * 数据库连接对象
	 */
	private DBService db = null;
	
	/**
	 * 构造方法 
	 * @param db 连接对象
	 */
	public FundAcctDao(DBService db)
	{
		this.db = db;
	}
	
	/**
	 * 资金帐户转帐
	 * @param outFacct	转出资金帐户
	 * @param inFacct	转入资金帐户
	 * @param fee	交易金额
	 * @return	返回true表示转帐成功,反之失败
	 * @throws Exception 数据库操作异常
	 */
	public boolean updateFacctToFacct(String outFacct,String inFacct,int fee) throws Exception
	{
		int num = this.updateChildLeft(outFacct,-fee,Constant.FACCT_TO_FACCT_OUT) + this.updateChildLeft(inFacct,fee,Constant.FACCT_TO_FACCT_OUT);
		if(num < 4)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * 更新佣金账户的余额
	 * @param childfacct 佣金帐户
	 * @param fee 金额
	 * @return	返回处理数
	 * @throws Exception 数据库操作异常
	 */
	public  int updateEngageLeft(String childfacct,int fee) throws Exception{
		long startTime=System.currentTimeMillis();
		StringBuffer sql = new StringBuffer();
		String[] str = new String[1];
		sql.append("update wht_childfacct set accountleft=accountleft-").append(fee);
		sql.append(" where childfacct='").append(childfacct).append("'");
		str[0] = sql.toString();
		int num = -1;
		try
		{
			num = db.update(str);
		}catch(Exception ex)
		{
			Log.error("更新佣金帐户余额出错:",ex);
			throw ex;
		}
		long endTime=System.currentTimeMillis();
		Log.info("更新佣金账户时间差："+(endTime-startTime));
		return num;
	}
	
	
	/**
	 * 更新资金帐户余额
	 * @param childfacct 子资金帐户
	 * @param fee 金额
	 * @param tradeType 交易类型
	 * @return	返回处理数
	 * @throws Exception 数据库操作异常  -1失败
	 */
	public  int  updateChildLeft(String childfacct,int fee,String tradeType) throws Exception
	{
		long startTime=System.currentTimeMillis();
		StringBuffer sql = new StringBuffer();
		String[] str = new String[2];
		sql.append("update wht_childfacct set accountleft=accountleft+").append(fee);
		//只有充值时更新
		if(tradeType!=null && tradeType.trim().equals(Constant.FACCT_TRADE_FILL))
		{
			sql.append(",awardleft=awardleft+").append(fee);
		}
		sql.append(" where childfacct='").append(childfacct).append("'");
		str[0] = sql.toString();
		
		sql.delete(0,sql.length());
		sql.append("update wht_facct set accountleft=accountleft+").append(fee);
		
		if(tradeType!=null && tradeType.trim().equals(Constant.FACCT_TRADE_FILL))
		{
			sql.append(",awardleft=awardleft+").append(fee);
		}
		/*sql.append(" where fundacct in (select fundacct from ec_childfacct where childfacct='")
		   .append(childfacct).append("')");*/
		
		sql.append(" where fundacct='").append(childfacct.substring(0, childfacct.length()-2)).append("'");
		
		str[1] = sql.toString();
		
		int num = -1;
		num = db.update(str);
		return num;
	}
	

	
	/**
	 * 修改资金帐户日志状态
	 * @param date 日期
	 * @param seqNo 流水号
	 * @param state 状态
	 * @return 返回修改的记录条数
	 * @throws Exception 数据库操作异常
	 */
	public int updateFacctBillSts(String date,String seqNo,int state) throws Exception
	{
		String yearMonth = date.substring(2,6);
		String tableName = "wht_acctbill_"+yearMonth;
		String sql = "update "+tableName+" set state="+state+","+
		"distime='"+DateParser.getNowDateTime()+"' where dealserial='"+seqNo+"'";
		
		return db.update(sql);
	}
	
	/**
	 * 查检资金帐户是否销户
	 * @param fundAcct 资金帐户
	 * @return	返回true表示已正常
	 */
	public boolean isDrop(String fundAcct)
	{
		boolean check = false;
		StringBuffer sql = new StringBuffer();
		sql.append("select fundacct from wht_facct where fundacct='")
		   .append(fundAcct).append("' and state=1");
		try
		{
			check = db.hasData(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询资金帐户是否销户出错:",ex);
		}
		
		return check;
	}
	
	/**
	 * 判断资金帐户是否存
	 * @param fundAcct 资金帐号
	 * @return 返回true表示存在,反之表示不存在
	 */
	public boolean isFacctExist(String fundAcct)
	{
		boolean check = false;
		String sql = "select fundacct from wht_facct where fundacct='"+fundAcct+"'";

		try
		{
			check = db.hasData(sql);
		}catch(Exception ex)
		{
			Log.error("查询资金帐户是否存在出错:",ex);
		}
		
		return check;
	}
	
	/**
	 * 资金帐户合法性验证
	 * @param fundAcct 资金帐户
	 * @param childFacct 子资金帐户
	 * @param payFee 交易金额
	 * @return 返回true表示验证成功,反之表示失败
	 */
	public String checkFacct(String fundAcct,String childFacct,double payFee)
	{
		//如果资金帐户状态正常
		if(this.isDrop(fundAcct))
		{
			long left = this.getChildFacctLeft(childFacct);
			Log.debug("账户余额："+left+" <> "+"缴费金额："+payFee);
			//余额不够
			if(left < payFee||payFee<0)
			{
				Log.debug("资金账户余额不足");
				return RespCode.RC_FACCT_NOLEFT;
			}
			return ""+left;
		}else
		{
			Log.debug("资金账户已经销户");
		}
		
		return RespCode.RC_FACCT_DROP;
	}
	
	/**
	 * 判断余额是否小于预警值
	 * @param childFacct 子资金帐户
	 * @return 小于,返回失败.反之返回成功
	 */
	public String checkWarnFee(String childFacct)
	{
		long left = this.getChildFacctLeft(childFacct);
		if(left<0){
			Log.debug("查询账户余额异常");
			return RespCode.RC_FACCT_LESSWARN;
		}
		///获取资金帐户额度限制信息
		Map limitInf = this.getAcctLimit(childFacct);
		if(limitInf == null || limitInf.size() < 1)
		{
			Log.debug("资金账户额度限制信息为空");
			//return RespCode.RC_FACCT_NOLIMIT;
			return RespCode.RC_SUCCESS;
			
		}
		//预警值
		int warnFee = Integer.parseInt((String)limitInf.get(Constant.FACCT_LIMIT_WARN_TYPE+""));
		if(left < warnFee)
		{
			Log.debug("账户余额已少于预警值");
			return RespCode.RC_FACCT_LESSWARN;
		}
		
		return RespCode.RC_SUCCESS;
	}
	
	/**
	 * 检查资金帐户交易记录是否存在
	 * @param childFacct 资金帐户
	 * @param seqNo 交易流水号
	 * @param date 日期
	 * @param type 交易类型
	 * @return 返回true表示存在,反之表示不存在
	 * @throws Exception 数据库异常
	 */
	public boolean checkBill(String childFacct,String seqNo,String date,int type) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		String tableName = "ec_acctbill_"+date.substring(2,6);
		sql.append("select id from ").append(tableName)
		   .append(" where childfacct='").append(childFacct)
		   .append("' and dealserial='").append(seqNo)
		   .append("' and state=0 and tradetype=").append(type);
		
		boolean check = false;
		
		try
		{
			check = db.hasData(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询资金帐户交易记录是否存在出错:",ex);
			throw ex;
		}
		
		return check;
	}
	
	/**
	 * 查询资金帐户交易流水号是否存在
	 * @param seqNo 流水号
	 * @return 返回true表示已存在,反之表示不存在
	 */
	public boolean checkSeq(String seqNo)
	{
		StringBuffer sql = new StringBuffer();
		String tableName = "wht_orderform_"+Tools.getNow().substring(2,6);
		sql.append("select id from ").append(tableName)
		   .append(" where tradeserial='").append(seqNo)
		   .append("'");
		
		boolean check = false;
		
		try
		{
			check = db.hasData(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询资金帐户交易流水号是否存在出错:",ex);
		}
		
		return check;
	}
	
	/**
	 * 获得已销户资金帐户
	 * @return 返回资金帐户例表
	 */
	public List getDropFacctList()
	{
		List rsList = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select fundacct,areacode,state from ec_facct where state=")
		   .append(Constant.FACCT_REMOVE_STATE).append(" or state=")
		   .append(Constant.FACCT_RECKONING_STATE);
		
		try
		{
			rsList = db.getList(sql.toString());
		}catch(Exception ex)
		{
			Log.error("获得已销户资金帐户信息",ex);
		}
		return rsList;
	}
	
	/**
	 * 获取资金帐户所属区号
	 * @param fundacct 资金帐户
	 * @return 返回区号
	 */
	public String getFacctArea(String fundacct)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select areacode from ec_facct where fundacct='")
		   .append(fundacct).append("'");
		String str = null;
		try
		{
			str = db.getString(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询资金帐户区号出错:",ex);
		}
		
		return str;
	}
	
	/**
	 * 根据资金帐户获得其绑定的终端用户
	 * @param fundacct 资金帐户
	 * @return 返回终端用户列表
	 */
	public List getTermUser(String fundacct)
	{
		List rsList = null;
		try
		{
			String sql = "select termid from ec_terminal where " +
					"agent="+Constant.BASE_AGENT+" and fundacct='"+fundacct+"'";
			rsList = db.getList(sql);
		}catch(Exception ex)
		{
			Log.error("根据资金帐户获得其绑定的终端用户出错",ex);
		}
		return rsList;
	}
	

	/**
	 * 根据资金帐户获得子资金帐号
	 * @param fundAcct 资金帐户
	 * @param type 帐户类型
	 * @return 返回string类型的子资金帐号
	 */
	public String getChildFacct(String fundAcct,String type)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select childfacct from ec_childfacct where fundacct='")
		   .append(fundAcct).append("' and accttypeid='").append(type).append("'");
		
		//Log.info("查找子账户---------->" + sql.toString());
		
		String str = null;
		try
		{
			str = db.getString(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询子资金帐号出错:"	+fundAcct,ex);
		}
		
		return str;
	}
	
	/**
	 * 根据子资金帐户获得其额度限制信息
	 * @param childfacct 子资金帐户
	 * @return 返回Map; key为额度限制类型,value为额度值
	 */
	public Map getAcctLimit(String childfacct)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select type,personnum from ec_acctlimit where childfacct='")
		   .append(childfacct).append("' and state=0 order by type");
		//Log.info("--------------------------->>>" + sql);
		Map rsMap = null;
		
		try
		{
			rsMap = Tools.getStringMap(db.getList(sql.toString()));
		}catch(Exception ex)
		{
			Log.error("查询资金帐户额度限制信息出错:",ex);
		}
		
		return rsMap;
	}
	
	/**
	 * 获得资金帐户余额
	 * @param fundAcct 资金帐户
	 * @return 返回资金帐户余额
	 */
	public long getFacctLeft(String fundAcct)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select accountleft from wht_facct where fundacct='")
		   .append(fundAcct).append("'");

		long fee = 0;
		try
		{
			fee = db.getLong(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询资金帐号余额出错:",ex);
		}
		return fee;
	}
	
	/**
	 * 获得子资金帐户余额
	 * @param childFacct 资金子帐户
	 * @return 返回资金子帐户余额
	 */
	public long getChildFacctLeft(String childFacct)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select accountleft from wht_childfacct where childfacct='")
		   .append(childFacct).append("'");

		long fee = 0;
		try
		{
			fee = db.getLong(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询子资金帐号余额出错:",ex);
			return -1;
		}
		return fee;
	}
	
	
	/**
	 * 获得资金帐户余额
	 * @param fundAcct 资金帐户
	 * @param acctType 账户类型
	 * @return 返回余额
	 * @throws Exception
	 */
	public int getChildFacctsLeft(String fundAcct,String acctType) throws Exception
	{
	    
		String sql ="select accountleft from wht_childfacct where fundacct='"+
		fundAcct+"' and type='"+acctType+"'";
		try
		{
			return db.getInt(sql);
		}catch(Exception ex)
		{
			throw ex;
		}
	}
	
	/**
	 * 根据资金帐户获得其绑定的代理商
	 * @param fundacct 资金帐户
	 * @return 返回代理商列表
	 */
	public List getAgentList(String fundacct)
	{
		List rsList = null;
		try
		{
			rsList = db.getList("select agentid from ec_agentfacct_grp where state=0 and fundacct='"+fundacct+"'");
		}catch(Exception ex)
		{
			Log.error("根据资金帐户获得其绑定的代理商出错",ex);
		}
		return rsList;
	}
	/**
	 * 插入资金帐户信息
	 * @param fundAcct 资金帐号
	 * @param pwd  密码
	 * @param servid 交易类型
	 * @param areaCode 区号
	 * @param custid  客户编号
	 * @param buildTime  开户时间
	 * @param state 状态
	 * @param disTime 处理时间
	 * @param isDeposit 是否创建押金帐户
	 * @throws Exception
	 */
	public void insertFacct(String fundAcct,String pwd,String servid,String areaCode,String custid,
			String buildTime,int state,String disTime,int isDeposit) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		String[] str = new String[3];;
		//需要创建押金帐户
		if(isDeposit == 1)
		{
			str = new String[4];
		}
		//创建资金帐户
		sql.append("insert into ec_facct values('")
		   .append(fundAcct).append("','")
		   .append(pwd).append("',")
		   .append(custid).append(",'")
		   .append(buildTime).append("','")
		   .append(areaCode).append("',0,0,0,'")
		   .append(buildTime.substring(0,8)).append("','00000000','")
		   .append(buildTime).append("',")
		   .append(state).append(",'")
		   .append(disTime).append("')");
		str[0] = sql.toString();
		
		Log.debug("--创建资金帐户--:"+sql.toString());
		//子资金帐户
		String childFacct = fundAcct+Constant.FACCT_CASH_TYPE;
		
		//创建现金资金帐户
		sql.delete(0,sql.length());
		sql.append("insert into ec_childfacct values('")
		   .append(childFacct).append("','").append(fundAcct)
		   .append("','").append(Constant.FACCT_CASH_TYPE)
		   .append("',0,0,0,").append(state).append(",'")
		   .append(DateParser.getNowDateTime()).append("')");
		str[1] = sql.toString();
		
		//平台与综服关系表
		sql.delete(0,sql.length());
		sql.append("insert into ec_sp_facct values('")
		   .append(fundAcct).append("',").append(servid).append(")");
		str[2] = sql.toString();
		
		//创建押金帐户
		if(isDeposit == 1)
		{
			childFacct = fundAcct+Constant.FACCT_DEPOSIT_TYPE;
			sql.delete(0,sql.length());
			sql.append("insert into ec_childfacct values('")
			   .append(childFacct).append("','").append(fundAcct)
			   .append("','").append(Constant.FACCT_DEPOSIT_TYPE)
			   .append("',0,0,0,").append(state).append(",'")
			   .append(DateParser.getNowDateTime()).append("')");
			str[3] = sql.toString();
		}
		
		try
		{
			db.update(str);
		}catch(Exception ex)
		{
			Log.error("创建资金帐户出错:",ex);
			throw ex;
		}
	}
	
	/**
	 * 根据客户编号获取资金帐号
	 * @param custid 客户编号
	 * @return 返回资金帐号
	 * @throws Exception
	 */
	public String getFacct(String custid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select fundacct from ec_facct where custid=").append(custid);
		String str = null;
		try
		{
			str = db.getString(sql.toString());
		}catch(Exception ex)
		{
			Log.error("根据客户获取资金帐号出错:",ex);
			throw ex;
		}
		
		return str;
	}
	/**
	 * 根据综服服务实例编号获取资金帐号
	 * @param servid 综服实例号
	 * @return 返回资金帐号
	 * @throws Exception  
	 */
	public String getFacctOfServId(String servid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select fundacct from ec_sp_facct where servid=").append(servid);
		String str = null;
		try
		{
			str = db.getString(sql.toString());
		}catch(Exception ex)
		{
			Log.error("根据服务实例号获取资金帐号出错:",ex);
			throw ex;
		}
		
		return str;
	}
	
	/**
	 * 统计资金帐户日交易笔数和金额
	 * @param type 交易类型
	 * @param date 日期
	 * @return 返回String[],分别为交易笔数和交易金额
	 * @throws Exception 数据库异常
	 */
	public String[] getDayBill(int type,String date) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		String tableName = "ec_acctbill_"+date.substring(2,6);
		sql.append("select count(*),sum(tradefee) from ").append(tableName)
		   .append(" where substr(tradetime,1,8)='").append(date)
		   .append("' and state=0 and tradetype=").append(type);
		
		Log.debug("统计资金帐户日交易笔数和金额: "+sql.toString());
		String[] str =  null;
		try
		{
			str = db.getStringArray(sql.toString());
		}catch(Exception ex)
		{
			Log.error("统计资金帐户日交易笔数和金额出错:",ex);
			throw ex;
		}
		
		return str;
	}
	/**
	 * 修改转账scp的状态
	 * @param 时间
	 * @param 交易单号
	 * @throws Exception 数据库异常
	 */
	public void updateScpState(String t,String l) throws Exception
	{
		String sql="update ec_tenpaytransferlog set scpflag='0',scpstatechg='"+t+"' where transaction_id='"+l+"'";
		try
		{
			db.update(sql);
		}catch(Exception ex)
		{
			Log.error("修改资金帐户密码出错:",ex);
			throw ex;
		}
	}
	
	/**
	 * 修改转账QB scp的状态
	 * @param 时间
	 * @param 交易单号
	 * @throws Exception 数据库异常
	 */
	public void updateQBState(String t,String l) throws Exception
	{
		String sql="update ec_QBtransferlog set scpFlag='0',scpstatechg='"+t+"' where tran_seq='"+l+"'";
		try
		{
			db.update(sql);
		}catch(Exception ex)
		{
			Log.error("修改资金帐户密码出错:",ex);
			throw ex;
		}
	}
	
	/**
	 * 获取佣金比例
	 * @param phonetype
	 * @param userPid
	 * @param phonePid
	 * @param userno
	 * @param flag
	 * @param payfee
	 * @param parentid
	 * @return
	 */
	public String[] getEmploy(String phonetype,String userPid,String phonePid,String userno,String flag,int payfee,int parentid){
		String cm_id;
		try {
			cm_id = db.getString("select cm_id from sys_valueRange where min<="+payfee+
					" and max>"+payfee);
		} catch (SQLException e1) {
			e1.printStackTrace();
			return null;
		}
		String sql=null;
		if("1".equals(flag)){//直营
			sql="select value from sys_employ2 where pid="+phonePid+
			" and type="+phonetype+" and cm_id='"+cm_id+"'";
		}else {//非直营
		if(!userPid.equals(phonePid)){
			sql="select value from sys_employ1 where pid="+phonePid+
			" and type="+phonetype+" and cm_id='"+cm_id+"'";
		}else{
			sql="select value0,value1 from wht_agentAnduser where userno='"+userno+
			"' and parentid="+parentid+" and type="+phonetype+" and cmid='"+cm_id+"'";
		}
		}
		try {
			return db.getStringArray(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据用户系统编号查询 账号
	 * @param userno
	 * @return
	 */
	public String getFacctByUserno(String userno)
	{
		String sql="select fundacct from wht_facct where user_no='"+userno+"'";
		try
		{
		  return db.getString(sql);
		}catch(Exception ex)
		{
			Log.error("根据用户系统编号获取账户失败:",ex);
			return null;
		}
	}
	
	 /**
	  * 根据用户id查询 账号
	  * @param userid
	  * @return  用户平台账户
	  */
	public String getFacctByUserID(int userid)
	{
		String sql="select fundacct from wht_facct where user_no=(select user_no from sys_user where user_id="+userid+")";
		try
		{
		  return db.getString(sql);
		}catch(Exception ex)
		{
			Log.error("根据用户系统编号获取账户失败:",ex);
			return null;
		}
	}
	
	
}
