/**
 * 
 */
package com.wlt.webm.scpdb;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scpcommon.RespCode;
import com.commsoft.epay.util.logging.Log;

/**
 * <p>@Description: 业务代办处理类</p>
 */ 
public class TermDao {
	
	/**
	 * 数据库连接对象
	 */
	private DBService db = null;
	
	/**
	 * 构造方法 
	 * @param db 连接对象
	 */
	public TermDao(DBService db)
	{
		this.db = db;
	}
	
	/**
	 * 根据终端ID返回终端编号
	 * @param posid 终端ID
	 * @return	返回String类型的终端编号
	 * @throws Exception 
	 */
	public String getTermAcct(String posid) throws Exception
	{
		String str = "";
		try
		{
			String sql = "select resourceid from ec_resources where posid='"+posid+"'";
			str = db.getString(sql);
		}catch(Exception ex)
		{
			Log.error("查询终端帐号出错:",ex);
			throw ex;
		}
		
		return str;
	}
	/**
	 * 根据终端ID返回终端绑定资金帐户
	 * @param posid 终端ID
	 * @return 返回String类型的资金帐户
	 * @throws Exception 
	 */
	public String getFundAcct(String posid) throws Exception
	{
		String str = "";
		try
		{
			StringBuffer sql = new StringBuffer();
//			sql.append("select fundacct from ec_terminal where termid=")
//			   .append("(select termid from ec_resources where posid='")
//			   .append(posid).append("')");
			sql.append("select fundacct from ec_resources where posid='")
			   .append(posid).append("'");
			str = db.getString(sql.toString());
			
			//Log.info("查找账户---------->" + sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("查询终端帐号出错:",ex);
			throw ex;
		}
		
		return str;
	}
	
	/**根据统一支付账号获取可用余额
	 * @param fundacct 统一支付账号
	 * @return 可用余额
	 * @throws Exception
	 */
	public int getAccountleftByFundAcc(String fundacct) throws Exception
	{
		int accountleft = 0;
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select accountleft from ec_facct where fundacct='")
			   .append(fundacct).append("'");
			accountleft = db.getInt(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询终端帐号出错:",ex);
			throw ex;
		}
		
		return accountleft;
	}
	
	/**
	 * 获得终端信息
	 * @param resourceID  终端id
	 * @return 返回终端信息
	 */
	public String[] getPosMes(String resourceID) {
		String sql = "select b.termid,b.termname,c.agentid,c.agentname " +
				"from ec_resources a,ec_terminal b,ec_agent c " +
				"where a.termid=b.termid and b.agent=c.agentid and a.resourceid='"
				+ resourceID  + "'";
		String[] str = null;
		try {
			str = db.getStringArray(sql);
		} catch (Exception ex) {
			Log.error("获得返销所需数据出错:", ex);
		}
		return str;
	}
	/**
	 * 获得交易信息
	 * @param
	 * @return
	 */
	public String[] getPosQryMes(String termAcct,String phone,String service) {
		
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        String date = sdf.format(new Date());
		String yearMonth = date.substring(2,6);
		String tableName = "ec_tradelog_"+yearMonth;
		
		String sql = "select tradetime,duefee from " + tableName + 
		"where resourceid='" + termAcct + "'" + 
		" and tradeobject='" + phone + "'" + 
		" and service='" + service + "'";
		Log.info("sql===sx22======"+sql.toString());
		String[] str = null;
		try {
			str = db.getStringArray(sql);
		} catch (Exception ex) {
			Log.error("获得交易记录数据出错:", ex);
		}
		return str;
	}
	/**
	 * 根据终端ID返回终端所属地市区号
	 * @param posid 终端ID
	 * @return 返回String类型的地市区号
	 * @throws Exception 
	 */
	public String getAreaCode(String posid) throws Exception
	{
		String str = "";
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select areacode from ec_terminal where termid=")
			   .append("(select termid from ec_resources where posid='")
			   .append(posid).append("')");
			str = db.getString(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询终端所属地市区号出错:",ex);
			throw ex;
		}
		
		return str;
	}
	
	/**
	 * 查检销售资源是否正常
	 * @param posid posID
	 * @return	返回true表示正常,反之不正常
	 * @throws Exception 
	 */
	public boolean isPosDrop(String posid) throws Exception
	{
		boolean check = false;
		try
		{
			check = db.hasData("select posid from ec_resources where posid='"+posid+"' and state in(1,3)");
			
			Log.debug("------select posid from ec_resources where posid='"+posid+"' and state in(1,3)");
			
		}catch(Exception ex)
		{
			Log.error("查询销售资源是否停机或销户出错:",ex);
			throw ex;
		}
		
		return check;
	}
	
	/**
	 * 查检销售资源对应的终端用户是否正常
	 * @param posid posID
	 * @return	返回true表示正常,反之不正常
	 * @throws Exception 
	 */
	public boolean isTermDrop(String posid) throws Exception
	{
		boolean check = false;
		StringBuffer sql = new StringBuffer();
		//
		sql.append("select termid from ec_terminal where termid=")
		   .append("(select termid from ec_resources where posid='")
		   .append(posid).append("' and state=1) and state=").append(Constant.TERM_NOMAL);
//		   .append(Constant.TERM_NEW_STATE).append(",")
//		   .append(Constant.TERM_REOPEN_STATE).append(")");
		
		try
		{
			check = db.hasData(sql.toString());
			
			Log.debug("------"+sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("查询销售资源对应的终端用户是否正常出错:" + posid,ex);
			throw ex;
		}
		
		return check;
	}
	
	/**
	 * @param termid
	 * @return 是否为合法的代办户
	 * @throws Exception
	 */
	public boolean isTerminal(String termid) throws Exception
	{
		boolean check = false;
		StringBuffer sql = new StringBuffer();
		sql.append("select termid from ec_terminal where termid='")
			.append(termid).append("'");
		
		try
		{
			check = db.hasData(sql.toString());
			
			Log.debug("------"+sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("查询代办户是否合法出错:" + termid,ex);
			throw ex;
		}
		
		return check;
	}
	
	
	/**
	 * 终端是否绑定电话号码
	 * @param posid	终端ID
	 * @param accnbr	电话号码
	 * @param type 是否绑定
	 * @return	返回true表示已绑定，反之未绑定
	 * @throws Exception 
	 */
	public boolean isBindAccnbr(String posid,String accnbr,int type) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		boolean check = false;
		boolean hasPhone = true;
		try
		{
//			sql.append("select posid from ec_resourcesbind where posid='").append(posid)
//				.append("' and bindnbr='").append(accnbr).append("' ");
			sql.append("select a.resourceid from ec_resources a,ec_resourcesbind b where a.resourceid=b.resourceid and a.posid='")
			   .append(posid).append("' and b.bindnbr='").append(accnbr).append("' ");
			
			if(type >=0)
			{
				sql.append("and b.iscall=").append(type);
			}
			check = db.hasData(sql.toString());
			
			Log.debug("------" + sql.toString());
			
			sql = new StringBuffer();
			sql.append("select b.bindnbr from ec_resources a,ec_resourcesbind b where a.resourceid=b.resourceid and a.posid='")
			   .append(posid).append("' ");
			hasPhone = db.hasData(sql.toString());
			
			Log.debug("------" + sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("查询终端绑定号码出错:",ex);
			throw ex;
		}
		return check || !hasPhone;
	}
//	public static void main(String[] args) {
//		LoadConfig.getConfig();
//		DBService db = null;
//		db = new DBService();
//		TermDao term = new TermDao(db);
//		try {
//			System.out.println(term.isBindAccnbr("080808080809","5583636958",-1));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	/**
	 * 查询销售资源是否存在
	 * @param posid  资源ID
	 * @return 返回true表示存在，反之不存在
	 * @throws Exception
	 */
	public boolean isResources(String posid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		boolean check = false;
		try
		{
			sql.append("select posid from ec_resources where posid='")
			   .append(posid).append("'");

			check = db.hasData(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询销售资源是否存在出错:",ex);
			throw ex;
		}
		return check;
	}
	
	/**
	 * 终端是否绑定psam卡
	 * @param posid	终端ID
	 * @param psamCard	psam卡号
	 * @return	返回true表示已绑定，反之未绑定
	 * @throws Exception 
	 */
	public boolean isBindPsamNo(String posid,String psamCard) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		boolean check = false;
		//psam卡号为默认号,示已绑定
		if(psamCard.equals("0000000000000000"))
		{
			return true;
		}
		try
		{
			sql.append("select termid from ec_resources where posid='").append(posid)
				.append("' and (psamcard1='").append(psamCard)
				.append("' or psamcard2='").append(psamCard).append("')");

			check = db.hasData(sql.toString());
			
			Log.debug("------" + sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("查询终端绑定psam卡出错:",ex);
			throw ex;
		}
		return check;
	}
	
	/**
	 * 根据终端ID返回终端使用类型
	 * @param posid 终端ID
	 * @return 返回String类型的终端使用类型
	 * @throws Exception 
	 */
	public int getUseType(String posid) throws Exception
	{
		int type = 0;
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select usetype from ec_terminal where termid=")
			   .append("(select termid from ec_resources where posid='")
			   .append(posid).append("')");
			type = db.getInt(sql.toString());
			
			Log.debug("------" + sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("查询终端使用类型出错:",ex);
			throw ex;
		}
		
		return type;
	}
	
	/**
	 * 插入终端用户信息
 	 * @param termId  终端编号
	 * @param servid 业务类型
	 * @param areaCode 区号
	 * @param custid  客户编号
	 * @param usertype 使用类型
	 * @param fundAcct  绑定资金帐户
	 * @param buildTime 创建时间
	 * @param state 状态
	 * @throws Exception
	 */
	public void insertTerminal(String termId,String servid,String areaCode,
			String custid,String usertype,String fundAcct,String buildTime,int state) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		String[] str = new String[2];
		String nu=null;
		//插入终端用户信息
		sql.append("insert into ec_terminal values('")
		   .append(termId).append("',null,'") //所属片区,插0
		   .append(areaCode).append("',")
		   .append(custid).append(",null,") //所属代理商,插0
		   .append(usertype).append(",'")
		   .append(fundAcct).append("',null,'") //所属分组,插0
		   .append(buildTime).append("','00000000','00000000',")
		   .append(state).append(",'")
		   .append(buildTime).append("',' ')");  //开户时,状态时间和开户时间相同
		str[0] = sql.toString();
		Log.debug("--插入终端用户信息1-:"+sql.toString());
		
		//插入平台与综服终端用户关系
		sql.delete(0,sql.length());
		sql.append("insert into ec_sp_terminal values('")
		   .append(termId).append("',").append(servid).append(")");
		str[1] = sql.toString();
		Log.debug("--插入终端用户信息2-:"+sql.toString());
		
		
		try
		{
			db.update(str);
		}catch(Exception ex)
		{
			Log.error("插入终端用户信息出错:",ex);
			throw ex;
		}
	}
	
	/**
	 * 插入销售资源信息
	 * @param posid  资源ID
	 * @param termId  终端ID
	 * @param posType 资源类型
	 * @param addr   安装地址
	 * @param posModel  资源型号
	 * @param psam  psam卡号1
	 * @param psamR psam卡号2
	 * @param isPrint 是否配置打印机
	 * @param state  状态
	 * @param disTime 变更时间
	 * @throws Exception
	 */
	public void insertResources(String posid,String termId,int posType,
			String addr,String posModel,String psam,String psamR,int isPrint,int state,String disTime) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ec_resources values('")
		   .append(posid).append("','")
		   .append(termId).append("',")
		   .append(posType).append(",'")
		   .append(addr).append("','")
		   .append(posModel).append("','")
		   .append(psam).append("','")
		   .append(psamR).append("',")
		   .append(isPrint).append(",0,")
		   .append(state).append(",'")
		   .append(disTime).append("')");
		Log.debug("--插入销售资源信息-:"+sql.toString());
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("插入销售资源信息出错:",ex);
			throw ex;
		}
	}
	
	/**
	 * 插入销售资源绑定信息
	 * @param posid  资源ID
	 * @param bindnbr  绑定号码
	 * @throws Exception
	 */
	public void insertResourceBind(String posid,String bindnbr) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ec_resourcesbind values(")
		   .append("ec_resourcesbind_id.nextval,'").append(posid)
		   .append("','").append(bindnbr).append("',1)");
		
		Log.debug("--插入销售资源绑定信息-:"+sql.toString());
		
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("插入销售资源绑定信息出错:",ex);
			throw ex;
		}
	}
	
	/**
	 * 更新终端用户绑定资金帐户
	 * @param fundAcct 资金帐户
	 * @param termId  终端编号
	 * @throws Exception
	 */
	public void updateTermFacct(String fundAcct,String termId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_terminal set fundacct='")
		   .append(fundAcct).append("' where termid='")
		   .append(termId).append("'");
		
		Log.debug("--更新终端用户绑定资金帐户--:"+sql.toString());
		
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("更新终端用户绑定资金帐户出错:",ex);
			throw ex;
		}
	}
	
	/**
	 * 更新终端用户状态
	 * @param state  用户状态
	 * @param termId 终端编号
	 * @param agentId 代理商编号
	 * @param stsTime 办理时间
	 * @throws Exception
	 */
	public void updateTermState(int state,String termId,long agentId,String stsTime) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_terminal set state=")
		   .append(state).append(",distime='").append(stsTime)
		   .append("' where 1=1");
		
		if(termId != null && !termId.trim().equals(""))
		{
			sql.append(" and termid='").append(termId).append("'");
		}
		
		if(agentId > 0)
		{
			sql.append(" and agent=").append(agentId);
		}
		
		Log.debug("--更新终端用户状态--:"+sql.toString());
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("更新终端用户状态出错:",ex);
			throw ex;
		}
	}
	
	/**
	 * 修改销售资源信息
	 * @param posid  资源ID
	 * @param termId  终端ID
	 * @param posType 资源类型
	 * @param addr   安装地址
	 * @param posModel  资源型号
	 * @param psam  psam卡号1
	 * @param psamR psam卡号2
	 * @param isPrint 是否配置打印机
	 * @param state  状态
	 * @throws Exception
	 */
	public void updateResources(String posid, String termId, int posType,
			String addr, String posModel, String psam, String psamR,
			int isPrint, int state) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_resources set termid='").append(termId)
			.append("',type=").append(posType).append(",address='")
				.append(addr).append("',model='").append(posModel).append("',psamcard1='")
				.append(psam).append("',psamcard2='").append(psamR).append("',isprint=").append(
						isPrint).append(",state=").append(state)
						.append(" where posid='").append(posid).append("'");
		
		Log.debug("--修改销售资源信息--:"+sql.toString());
		
		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("修改销售资源信息出错:", ex);
			throw ex;
		}
	}
	
	/**
	 * 更新销售资源状态
	 * @param state  用户状态
	 * @param posid  pos编号
	 * @param termId  终端编号
	 * @param stsTime 办理时间
	 * @throws Exception
	 */
	public void updateResourcesState(int state,String posid,String termId,String stsTime) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_resources set state=")
		   .append(state).append(",distime='").append(stsTime)
		   .append("' where 1=1");
		
		if(posid != null && !posid.trim().equals(""))
		{
			sql.append("and posid='").append(posid).append("' ");
		}
		
		if(termId != null && !termId.trim().equals(""))
		{
			sql.append("and termid='").append(termId).append("'");
		}
		
		Log.debug("--更新销售资源状态--:"+sql.toString());
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("更新销售资源状态出错:",ex);
			throw ex;
		}
	}
	
	/**
	 * 修改销售资源绑定信息
	 * @param posid  资源ID
	 * @param bindnbr  绑定号码
	 * @throws Exception
	 */
	public void updateResourceBind(String posid,String bindnbr) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_resourcesbind set ")
		   .append("bindnbr='").append(bindnbr)
		   .append("' where posid='").append(posid).append("'");
		
		Log.debug("--修改销售资源绑定信息--:"+sql.toString());
		
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("修改销售资源绑定信息出错:",ex);
			throw ex;
		}
	}
	
	/**
	 * 根据代理商修改销售资源状态
	 * @param state  用户状态
	 * @param agentId  代理商编号
	 * @param stsTime 办理时间
	 * @throws Exception
	 */
	public void updateResOfAgent(int state,long agentId,String stsTime) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_resources set state=")
		   .append(state).append(",distime='").append(stsTime)
		   .append("' where termid in(")
		   .append("select termid from ec_terminal where agent=")
		   .append(agentId).append(")");
		
		try
		{
			db.update(sql.toString());
		}catch(Exception ex)
		{
			Log.error("根据代理商修改销售资源状态出错:",ex);
			throw ex;
		}
	}
	
	
	/**
	 * 查询佣金
	 * @param posid 终端ID
	 * @param month 月份
	 * @return 返回佣金
	 * @throws Exception
	 */
	public int getCommision(String posid) throws Exception
	{
		String  comm = "";
		int fee =0;
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select fundacct from ec_resources where posid ='")
			   .append(posid).append("'");
			Log.info("000000000000000012"+sql.toString());
			comm = db.getString(sql.toString())+"03";
			sql.delete(0, sql.length());
			sql.append("select usableleft from ec_childfacct where childfacct ='")
			   .append(comm).append("'");
			fee=db.getInt(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("查询佣金出错:",ex);
			throw ex;
		}
		
		return fee;
	}
	
	/**
	 * 查询电信返销流水号
	 * @throws Exception
	 */
	public String getDxRollBack(String termAcct,String phone,String service) throws Exception
	{
		String str = "";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        String date = sdf.format(new Date());
		String yearMonth = date.substring(2,6);
		String tableName = "ec_tradelog_"+yearMonth;
		try
		{
			StringBuffer sql = new StringBuffer();
			sql.append("select  first 1 tradeserial  from ").append(tableName)
			.append(" where tradeobject='").append(phone)
			.append("' and resourceid='").append(termAcct)
			.append("' and service='").append(service)
			.append("' order by tradetime  desc ");
			str = db.getString(sql.toString());
			Log.info("sql===sx======"+sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询电信返销流水号出错:",ex);
			throw ex;
		}
		
		return str;
	}
	
	/**
	 * 查询代理商对应的终端用户
	 * @param agentId 代理商编号
	 * @return 返回查询代理商对应的终端用户
	 * @throws Exception
	 */
	public List getTermIdList(int agentId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select termid from ec_terminal where agent=").append(agentId);
		List rsList = null;
		
		try
		{
			rsList = db.getList(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询代理商对应的终端用户出错:",ex);
			throw ex;
		}
		
		return rsList;
	}
	
	/**
	 * 查询代理商是否有终端用户
	 * @param agentId
	 * @return 是表示有终端 否表示没有
	 * @throws Exception
	 */
	public boolean checkAgent(long agentId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select termid from ec_terminal where state<=")
		   .append(Constant.TERM_REOPEN_STATE).append(" and agent=").append(agentId);
		boolean check = false;
		
		try
		{
			check = db.hasData(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询代理商是否有终端用户出错:",ex);
			throw ex;
		}
		
		return check;
	}
	/**
	 * 根据综服服务实例编号获取终端用户编号
	 * @param servid 客户编号
	 * @return 返回终端用户编号
	 * @throws Exception
	 */
	public String getTermIdOfServId(String servid) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select termid from ec_sp_terminal where servid=").append(servid);
		String str = null;
		try
		{
			str = db.getString(sql.toString());
		}catch(Exception ex)
		{
			Log.error("根据综服服务实例号获取终端用户编号出错:",ex);
		}
		
		return str;
	}
	
	/**
	 * 查检终端用户是否清算
	 * @param termid 终端用户
	 * @return	返回true表示已清算,反之未清算
	 */
	public boolean isTermSett(String termid)
	{
		boolean check = false;
		StringBuffer sql = new StringBuffer();
		sql.append("select termid from ec_termsett where state=1 and termid='")
		  .append(termid).append("'");
		  
		try
		{
			check = db.hasData(sql.toString());
		}catch(Exception ex)
		{
			Log.error("查询终端用户是否清算出错:",ex);
		}
		
		return check;
	}
	
	/**
	 * 获取终端用户结算金额
	 * @param termid  终端账号
	 * @return 结算金额
	 * @throws Exception
	 */
	public int getTermSettFee(String termid)throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select sum(settfee) from ec_termsett where state=1 and termid='")
		   .append(termid).append("'");
		
		int fee = 0;
		try
		{
			fee = db.getInt(sql.toString());
		}catch(Exception ex)
		{
			Log.error("获取终端用户结算金额出错:",ex);
		}
		
		return fee;
	}
	
	/**
	 * @return 获取银联转账功能开关
	 */
	public String getUpswitch() {
		String upswitch = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select upswitch from ec_switch where cftswitch=0");
		try {
			upswitch = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("获取银联转账功能开关出错:",ex);
		}
		return upswitch;
	}
	
	/**
	 * @return 获取天翼转账功能开关
	 */
	public String getTYswitch() {
		String upswitch = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select upswitch from ec_switch where cftswitch=6");
		try {
			upswitch = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("获取银联转账功能开关出错:",ex);
		}
		return upswitch;
	}
	
	/**
	 * 根据终端编号得到电话号码
	 * @param resourceid 终端编号
	 * @param flag   模块标识
	 * @return	返回String类型的电话号码
	 * @throws Exception 
	 */
	public String getPhone(String resourceid,String flag) throws Exception
	{
		String str = "";
		String sql ="select a.phone from ec_customer a,ec_terminal b,ec_resources c " +
				"where a.custid=b.custid and b.termid=c.termid and c.resourceid='"+resourceid+"'";
//		if(flag.equals(Constant.IPC_FLAG)){
//			sql = "select bindnbr from ec_resourcesbind where resourceid='"+resourceid+"'";
//		}else if(flag.equals(Constant.PC_FLAG)||flag.equals(Constant.FEP_FLAG)){
//			sql = "select bindinfo from ec_pcip where type=3 and resourceid='"+resourceid+"'";
//		}else{
//			sql = "select posid from ec_resources where type=3 and posid[1,8]!='11111111' and resourceid='"+resourceid+"'";
//		}
		try
		{
			str = db.getString(sql);
		}catch(Exception ex)
		{
			Log.error("查询终端电话号码出错:",ex);
			throw ex;
		}
		
		return str;
	}
	
	
	
    public static void main(String[] args) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
        String date = sdf.format(new Date());
        System.out.println(date.substring(2, 6));
        }
}
