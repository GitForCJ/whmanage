package com.wlt.webm.scpdb;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.scputil.Tools;
import com.wlt.webm.scputil.bean.CustErrorOrder;
import com.wlt.webm.scputil.bean.CustOrderLog;
import com.commsoft.epay.util.logging.Log;

/**
 * <p>@Title: 深圳康索特电子代办系统业务控制中心模块</p>
 * <p>@Description: 数据库操作类</p>
 */
public class DBOperator {

	private DBService db = null;

	/**
	 * @param db 数据库操作类
	 * 
	 */
	public DBOperator(DBService db) {
		this.db = db;
	}

	/**
	 * 根据业务类型获取托收方名称
	 * @param service 业务类型
	 * @return 托收方名称
	 */
	public String getTrusteeName(String service) {
		StringBuffer sql = new StringBuffer();
		sql.append("select trusteename from ec_trustservice a,ec_trustee b ")
				.append("where a.trustcode=b.trusteeid and a.service='")
				.append(service).append("'");
		String str = null;
		try {
			str = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("查询业务类型获取托收方名称出错:", ex);
		}
		return str;
	}
	/**
	 * 根据手机号码前7位获取手机归属地
	 */
	public String getPhonecode(String code) {
		StringBuffer sql = new StringBuffer();
		sql.append("select areacode from ec_phonecode a ")
				.append("where a.phone='")
				.append(code).append("'");

		String str = null;
		try {
			str = db.getString(sql.toString());
			if(str==null){str="0000";}
		} catch (Exception ex) {
			Log.error("根据手机号码前7位获取手机归属地出错:", ex);
		}
		return str;
	}
	/**
	 * 
	 * @param code
	 * @return fee
	 */
	public String getFee(String code) {
		StringBuffer sql = new StringBuffer();
		sql.append("select a.fundacct from ec_facct a,ec_resources b where a.fundacct=b.fundacct and ")
				.append(" b.resourceid='")
				.append(code).append("'");

		String str1 = null;
		String str2 = null;
		try {
			str1 = db.getString(sql.toString());
			sql.delete(0,sql.length());
			sql.append("select usableleft from ec_childfacct where ")
			.append(" childfacct='")
			.append(str1).append("02").append("'");
			str2 = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("根据终端编号查余额", ex);
		}
		return str2;
	}
	/**
	 * 根据业务类型获取托收方名称
	 * @param resouceId 
	 * @param payNo 
	 */
	public void deletePrintMes(String resouceId,String payNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete ec_reprint where resourceid='").append(resouceId)
				.append("' and account='")
				.append(payNo).append("'");
		Log.info("deletePrintMes"+sql);
		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("查询业务类型获取托收方名称出错:", ex);
		}
	}

	/**
	 * 获得返销所需数据
	 * @param tradeSeqNo  交易流水号
	 * @return 返回返销数据
	 */
	public String[] getYplWriteOffInfo(String tradeSeqNo) {
		String date = DateParser.getNowDate();
		String month = date.substring(2, 6);
		String sql = "select tradeserial,writeoff,tradeobject,fee,tradetime from ec_writeoff_"
				+ month + " where tradeserial='" + tradeSeqNo + "'";
		String[] str = null;
		try {
			str = db.getStringArray(sql);
		} catch (Exception ex) {
			Log.error("获得返销所需数据出错:", ex);
		}
		return str;
	}
	/**
	 * 获得返销所需数据
	 * @param tradeSeqNo  交易流水号
	 * @return 返回返销数据
	 */
	public String[] getWriteOffInfo(String tradeSeqNo) {
		String date = DateParser.getNowDate();
		String month = date.substring(2, 6);
		String sql = "select areacode,tradeobject,writeoff from ec_writeoff_"
				+ month + " where tradeserial='" + tradeSeqNo + "'";
		String[] str = null;
		try {
			str = db.getStringArray(sql);
		} catch (Exception ex) {
			Log.error("获得返销所需数据出错:", ex);
		}
		return str;
	}
	/**
	 * 获得返销所需数据
	 * @param tradeSeqNo  交易流水号
	 * @return 返回返销数据
	 */
	public String getCMCCWriteOffInfo(String tradeSeqNo) {
		String date = DateParser.getNowDate();
		String month = date.substring(2, 6);
		String sql = "select writeoff from ec_writeoff_"
				+ month + " where tradeserial='" + tradeSeqNo + "'";
		String str = null;
		try {
			str = db.getString(sql);
		} catch (Exception ex) {
			Log.error("获得返销所需数据出错:", ex);
		}
		return str;
	}

	/**
	 * 插入客户信息
	 * @param custid 客户编号
	 * @param cusname 客户姓名
	 * @param areaCode 区号
	 * @param idType 证件类型
	 * @param idCard 证件号码
	 * @param bank  开户行
	 * @param bankCard  银行卡号
	 * @param buildtime 开户时间
	 * @param buildaddr 开户地点
	 * @param phone 联系电话
	 * @param address 联系地址
	 * @throws Exception 数据库异常
	 */
	public void insertCustomer(String custid, String cusname, String areaCode,
			String idType, String idCard, String bank, String bankCard,
			String buildtime, String buildaddr, String phone, String address)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select custid from ec_customer where custid=").append(
				custid);

		try {
			boolean check = db.hasData(sql.toString());
			if (check) {
				return;
			}
		} catch (Exception ex) {
			Log.error("插入客户信息出错:", ex);
			throw ex;
		}
		sql.delete(0, sql.length());
		sql.append("insert into ec_customer values(").append(custid).append(
				",'").append(cusname).append("','").append(areaCode).append(
				"',").append(idType).append(",'").append(idCard).append("','")
				.append(bank).append("','").append(bankCard).append("','")
				.append(buildtime).append("','").append(buildaddr)
				.append("','").append(phone).append("','").append(address)
				.append("')");

		Log.debug("==插入客户信息sql==:" + sql.toString());
		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("插入客户信息出错:", ex);
			throw ex;
		}

	}

	/**
	 * 修改客户信息
	 * @param custid 客户编号
	 * @param cusname 客户姓名
	 * @param areaCode 区号
	 * @param idType 证件类型
	 * @param idCard 证件号码
	 * @param bank  开户行
	 * @param bankCard  银行卡号
	 * @param buildtime 开户时间
	 * @param buildaddr 开户地点
	 * @param phone 联系电话
	 * @param address 联系地址
	 * @throws Exception 数据库异常
	 */
	public void updateCustomer(String custid, String cusname, String areaCode,
			String idType, String idCard, String bank, String bankCard,
			String buildtime, String buildaddr, String phone, String address)
			throws Exception {
		String[] param = { cusname, areaCode, idType, idCard, bank, bankCard,
				buildtime, buildaddr, phone, address, custid };
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						"update ec_customer set name=?,areacode=?,idtype=?,idcard=?,bank=?,bankcard=?,")
				.append(
						"builddatetime=?,buildaddress=?,phone=?,address=? where custid=?");

		Log.debug("--修改客户资料--:" + sql.toString());

		try {
			db.update(sql.toString(), param);
		} catch (Exception ex) {
			Log.error("修改客户信息出错:", ex);
			throw ex;
		}
	}

	/**
	 * 生成代理商编号
	 * @param areaCode  区号
	 * @param agentName 代理商名称
	 * @return 返回代理商编号
	 * @throws Exception
	 */
	public long createAgentId(String areaCode, String agentName)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		String[] params = null;
		int parentid = 0;
		//获得区域编号
		sql.append("select areaid from ec_area where areacode='").append(
				areaCode).append("'");
		try {
			parentid = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("获得区域编号出错:", ex);
			throw ex;
		}
		//生成代理商
		sql.delete(0, sql.length());
		sql.append("insert into ec_syslevel values(0,?,?,?,2,0)");
		params = new String[] { agentName, String.valueOf(parentid), "1" };
		//代理商编号
		long agentid = 0;

		try {
			agentid = db.getGeneratedKey(sql.toString(), params);
		} catch (Exception ex) {
			Log.error("生成代理商编号出错:", ex);
			throw ex;
		}

		return agentid;
	}

	/**
	 * 插入代理商信息
	 * @param custid  客户编号
	 * @param servid  综服服务实例号
	 * @param agentid 代理商编号
	 * @param agentName 代理商名称
	 * @throws Exception
	 */
	public void insertAgent(String custid, String servid, long agentid,
			String agentName) throws Exception {
		StringBuffer sql = new StringBuffer();
		String[] params = null;
		sql.append("insert into ec_agent values(?,?,?,1)");
		params = new String[] { String.valueOf(agentid), agentName, custid };

		try {
			db.update(sql.toString(), params);
		} catch (Exception ex) {
			Log.error("添加代理商信息出错:", ex);
			throw ex;
		}
		//平台与综服关系表
		sql.delete(0, sql.length());
		sql.append("insert into ec_sp_agent values(").append(agentid).append(
				",").append(servid).append(")");
		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("添加平台与综服代理关系出错:", ex);
			throw ex;
		}
	}

	/**
	 * 插入代理商资金帐户关系
	 * @param agentid 代理商编号
	 * @param fundAcct 资金帐户
	 * @throws Exception
	 */
	public void insertAgentFacct(int agentid, String fundAcct) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ec_agentfacct_grp values(").append(agentid)
				.append(",").append(fundAcct).append(",0)");

		Log.debug("--插入代理商资金帐户关系息--:" + sql.toString());

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("添加代理商和资金帐户关系出错:", ex);
			throw ex;
		}
	}

	/**
	 * 修改代理商绑定信息
	 * @param agentid 代理商编号
	 * @param fundAcct 资金帐户
	 * @param state 状态
	 * @throws Exception
	 */
	public void updateAgentFacct(int agentid, String fundAcct, int state)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_agentfacct_grp set state=").append(state).append(
				" where agentid=").append(agentid).append(" and fundacct='")
				.append(fundAcct).append("'");

		Log.debug("--修改代理商绑定信息--:" + sql.toString());

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("修改代理商绑定信息出错:", ex);
			throw ex;
		}
	}

	/**
	 * 修改代理商状态
	 * @param agentid 代理商编号
	 * @param state 状态
	 * @throws Exception 数据库异常
	 */
	public void updateAgent(long agentid, int state) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_agent set state=").append(state).append(
				" where agentid=").append(agentid);

		Log.debug("--修改代理商状态--:" + sql.toString());
		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("修改代理商状态出错:", ex);
			throw ex;
		}
	}

	/**
	 * 根据客户编号获取代理商编号
	 * @param custid 客户编号
	 * @return 返回代理商编号
	 * @throws Exception
	 */
	public int getAgentId(String custid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select agentid from ec_agent where custid=").append(custid);
		int agent = 0;
		try {
			agent = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("根据客户获取代理商编号出错:", ex);
			throw ex;
		}

		return agent;
	}

	/**
	 * 根据综服服务实例编号获取代理商编号
	 * @param servid 客户编号
	 * @return 返回代理商编号
	 */
	public int getAgentIdOfServId(String servid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select agentid from ec_sp_agent where servid=").append(
				servid);
		int agent = -1;
		try {
			agent = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("根据综服服务实例号获取代理商编号出错:", ex);
		}

		return agent;
	}

	/**
	 * 查检代理商是否结算
	 * @param agentid 代理商
	 * @return	返回true表示已结算,反之未结算
	 */
	public boolean isAgentSett(String agentid) {
		boolean check = false;
		String month = DateParser.getNowMonth();
		String preMonth = DateParser.getPreMonth(month, -1);
		StringBuffer sql = new StringBuffer();
		sql.append("select agentid from ec_agentsett where month in('").append(
				month).append("','").append(preMonth)
				.append("') and agentid='").append(agentid).append("'");
		try {
			check = db.hasData(sql.toString());
		} catch (Exception ex) {
			Log.error("查询代理商是否结算出错:", ex);
		}

		return check;
	}

	/**
	 * 获取代理商清算金额
	 * @param agentid  代理商
	 * @return 结算金额
	 */
	public int getAgentSettFee(String agentid) {
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						"select sum(settfee) from ec_agentsett where state=1 and agentid='")
				.append(agentid).append("'");

		int fee = 0;
		try {
			fee = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("获取代理商清算金额出错:", ex);
		}

		return fee;
	}

	/**
	 * 查询联通缴费面值
	 * @param posid 终端编号
	 * @param services 业务类型
	 * @return 返回缴费面值信息
	 * @throws Exception
	 */
	public List getFillFee(String posid, String services[]) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer service = new StringBuffer("");
		for(int i = 0;i < services.length-1;i++){
			service.append("'").append(services[i]).append("',");
		}
		service.append("'").append(services[services.length-1]).append("'");
		//卡类别编号,卡类别名称,卡面值编号,卡面值
		sql.append("select id,name from ec_fillfee  \n")
			.append("where state=0 and service='01011' \n")
			//.append("and service in(").append(service).append(")")
			.append(" order by id");
		
		List rs = null;
		try {
			rs = db.getList(sql.toString());
		} catch (Exception ex) {
			Log.error("查询缴费面值出错:", ex);
			throw ex;
		}
		
		return rs;
	}
	/**
	 * 查询移动缴费面值
	 * @param posid 终端编号
	 * @param services 业务类型
	 * @return 返回缴费面值信息
	 * @throws Exception
	 */
	public List getFee(String posid, String services[]) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer service = new StringBuffer("");
		for(int i = 0;i < services.length-1;i++){
			service.append("'").append(services[i]).append("',");
		}
		service.append("'").append(services[services.length-1]).append("'");
		//卡类别编号,卡类别名称,卡面值编号,卡面值
		sql.append("select id,name from ec_fillfee  \n")
			.append("where state=0 and service='01021'  \n")
			//.append("and service in(").append(service).append(")")
			.append(" order by id");
		
		List rs = null;
		try {
			rs = db.getList(sql.toString());
		} catch (Exception ex) {
			Log.error("查询缴费面值出错:", ex);
			throw ex;
		}
		
		return rs;
	}
	/**
	 * 查询天作缴费面值
	 * @param posid 终端编号
	 * @return 返回缴费面值信息
	 * @throws Exception
	 */
	public List getTZFillFee(String posid) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer service = new StringBuffer("");
		//卡类别编号,卡类别名称,卡面值编号,卡面值
		sql.append("select id,name from ec_fillfee  \n")
			.append("where state=0 and service='01012' \n")
			.append(" order by id");
		
		List rs = null;
		try {
			rs = db.getList(sql.toString());
		} catch (Exception ex) {
			Log.error("查询缴费面值出错:", ex);
			throw ex;
		}
		
		return rs;
	}
	
	/**
	 * 根据终端编号查询绑定资金账号
	 * @param resourceid 终端编号
	 * @return 绑定资金账号信息
	 * @throws Exception
	 */
	public String getBindFacct(String resourceid) throws Exception {
		StringBuffer sql = new StringBuffer();
		String fundAcct="";
		sql.append("select fundacct from  wlt_facct   \n")
		.append("where  termID ='").append(resourceid).append("'");
		
		try {
			fundAcct = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("查询代办户银行绑定账号:", ex);
			throw ex;
		}
		return fundAcct;
	}
	/**
	 * 根据代办户查询绑定天翼银行姓名
	 * @param termId 代办户编号
	 * @return 绑定资金账号信息
	 * @throws Exception
	 */
	public String getBindTYNAMEFacct(String termId) throws Exception {
		StringBuffer sql = new StringBuffer();
		String name="";
		sql.append("select a.tybankna from  ec_terminal a,ec_resources b where a.termid=b.termid   \n")
		.append("and  b.posid ='").append(termId).append("'");
		
		try {
			name = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("根据代办户查询绑定天翼银行姓名出错:", ex);
			throw ex;
		}
		return name;
	}
	/**
	 * 根据代办户查询绑定天翼银行名字
	 * @param termId 代办户编号
	 * @return 绑定资金账号信息
	 * @throws Exception
	 */
	public String getBindTYBANKNAMEFacct(String termId) throws Exception {
		StringBuffer sql = new StringBuffer();
		String name="";
		sql.append("select a.tybankname from ec_terminal a,ec_resources b where a.termid=b.termid   \n")
		.append("and  b.posid ='").append(termId).append("'");
		
		try {
			name = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("根据代办户查询绑定天翼银行名字出错:", ex);
			throw ex;
		}
		return name;
	}
	/**
	 * 根据代办户查询绑定天翼的签约ID 
	 * @param termId 代办户编号
	 * @return 绑定资金账号信息
	 * @throws Exception
	 */
	public String getBindTYCid(String termId) throws Exception {
		StringBuffer sql = new StringBuffer();
		String cid="";
		sql.append("select a.cid from ec_terminal a,ec_resources b where a.termid=b.termid   \n")
		.append("and  b.posid ='").append(termId).append("'");
		
		try {
			cid = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("根据代办户查询绑定天翼的签约ID出错:", ex);
			throw ex;
		}
		return cid;
	}
	
	/**
	 * @param name
	 * @return 返回天翼绑定银行的编号
	 */
	public String getBindTYBANKNUMFacct(String name){
		String num="00";
		if("邮政储蓄".equals(name)){
			num="60";
		}
		else if("中国银行".equals(name)){
			num="61";
		}
		else if("工商银行".equals(name)){
			num="62";
		}
		else if("农业银行".equals(name)){
			num="63";
		}
		else if("广东交通银行".equals(name)){
			num="64";
		}
		else if("建设银行".equals(name)){
			num="65";
		}
		else if("民生银行".equals(name)){
			num="66";
		}
		else if("广发银行".equals(name)){
			num="68";
		}
		else if("招商银行".equals(name)){
			num="69";
		}
		else if("广州银行".equals(name)){
			num="70";
		}
		else if("上海浦发银行".equals(name)){
			num="71";
		}
		else if("光大银行".equals(name)){
			num="72";
		}
		else if("广东省农信社".equals(name)){
			num="73";
		}
		else if("中信银行".equals(name)){
			num="74";
		}
		else if("兴业银行".equals(name)){
			num="76";
		}
		else if("广州农村商业银行".equals(name)){
			num="77";
		}
		else if("东莞农村商业银行".equals(name)){
			num="78";
		}
		else if("东莞银行".equals(name)){
			num="79";
		}
		else if("华夏银行".equals(name)){
			num="80";
		}
		return num;
	}
	
	
	/**
	 * 查询代办户银行绑定账号
	 * @param posid 终端编号
	 * @param password 统一支付账户密码
	 * @return 银行绑定账号信息
	 * @throws Exception
	 */
	public int validatePassword(String posid,String password) throws Exception {
		StringBuffer sql = new StringBuffer();
		String tradepwd="";
		sql.append("select tradepwd from ec_facct a,ec_resources b  \n")
		.append("where  a.fundacct =b.fundacct   \n")
		.append("and b.posid ='").append(posid).append("'");
		
		try {
			tradepwd = db.getString(sql.toString());
			Log.info("验证交易密码："+tradepwd+" : "+password);
			if(tradepwd.equals(password)){
				return 0;
			}
		} catch (Exception ex) {
			Log.error("查询代办户银行绑定账号:", ex);
			throw ex;
		}
		return -1;
	}
	
	/**
	 * 修改统一支付账号交易密码
	 * @param posid 终端编号
	 * @param oldPassword 原交易密码
	 * @param newPassword 
	 * @return 银行绑定账号信息
	 * @throws Exception
	 */
	public int updateFacctPWD(String posid,String oldPassword,String newPassword) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_facct set tradepwd='")
			.append(newPassword).append("'")
			.append(" where  fundacct in (")
			.append("select fundacct from ec_resources b where b.posid='")
			.append(posid).append("')")
			.append(" and tradepwd ='").append(oldPassword).append("'");
		int result = 0;
		try {
			result = db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("查询代办户银行绑定账号:", ex);
			throw ex;
		}
		return result;
	}
	
	/**
	 * 查询代办户银联银行绑定账号
	 * @param posid 终端编号
	 * @return 银行绑定账号信息
	 * @throws Exception
	 */
	public String getBankAccount(String posid) throws Exception {
		StringBuffer sql = new StringBuffer();
		String bankAccount="";
		sql.append("select bankaccount from ec_terminal a,ec_resources b  \n")
			.append("where  a.termid =b.termid and a.state=0  \n")
			.append("and b.posid ='").append(posid).append("'");
		
		try {
			bankAccount = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("查询代办户银联银行绑定账号:", ex);
			throw ex;
		}
		
		return bankAccount;
	}
	/**
	 * 查询代办户天翼银行绑定账号
	 * @param posid 终端编号
	 * @return 银行绑定账号信息
	 * @throws Exception
	 */
	public String getTYBankAccount(String posid) throws Exception {
		StringBuffer sql = new StringBuffer();
		String bankAccount="";
		sql.append("select tybankaccount from ec_terminal a,ec_resources b  \n")
			.append("where  a.termid =b.termid and a.state=0  \n")
			.append("and b.posid ='").append(posid).append("'");
		
		try {
			bankAccount = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("查询代办户天翼银行绑定账号:", ex);
			throw ex;
		}
		
		return bankAccount;
	}
	/**
	 * 查询卡类别
	 * @param posid 终端编号
	 * @param services 业务类型
	 * @return 返回卡类别信息
	 * @throws Exception
	 */
	public List getCardTypes(String posid, String services[]) throws Exception {
		StringBuffer sql = new StringBuffer();
		StringBuffer service = new StringBuffer("");
		for(int i = 0;i < services.length-1;i++){
			service.append("'").append(services[i]).append("',");
		}
		service.append("'").append(services[services.length-1]).append("'");
		//卡类别编号,卡类别名称,卡面值编号,卡面值
		sql
				.append(
						"select a.id,a.name,b.id,b.name from ec_cardtype a,ec_cardfee b \n")
				.append("where a.id=b.cardtype and a.state=0 and b.state=0 \n")
				.append("and a.service in(")
				.append(service)
				.append(
						") and b.id in(select cardfee from ec_cardsyslevel where \n")
				.append(
						"district in (select district from ec_terminal where termid in \n")
				.append("(select termid from ec_resources where posid='")
				.append(posid).append("'))) order by a.id,cast(b.name as int)");

		List rs = null;
		try {
			rs = db.getList(sql.toString());
		} catch (Exception ex) {
			Log.error("查询卡类别出错:", ex);
			throw ex;
		}

		return rs;
	}
	/**
	 * 根据卡面值编号获得卡面值
	 * @param cardFeeNo 卡面值编号
	 * @return 返回卡面值
	 */
	public int getCardFee(String cardFeeNo)
	{
		StringBuffer sql = new StringBuffer();
		sql.append("select name*100 from ec_cardfee where id=").append(cardFeeNo);
		int fee = 0;
		
		try
		{
			fee = db.getInt(sql.toString());
		}catch(Exception ex)
		{
			Log.error("根据卡面值编号获得卡面值出错:",ex);
		}
		
		return fee;
	}
	/**
	 * 根据卡面值编号获得折扣价
	 * @param cardFeeNo 卡面值编号
	 * @param cardType 卡类型
	 * @param termNo 终端号
	 * @return 返回卡面值
	 */
	public int getCardFee(String cardFeeNo, String cardType,String termNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select name*100 from ec_cardfee where id=")
				.append(cardFeeNo);
		int fee = 0;
		int costtype = 1;
		String[] groups = null;
		try {
			fee = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("根据卡面值编号获得卡面值卡类型出错:", ex);
		}
		sql.delete(0, sql.length());
		sql.append("select costtype from ec_cardtype where id=").append(
				cardType);
		try {
			costtype = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("根据卡类型编号获得扣款方式出错:", ex);
		}
		if (costtype == 2) {
			sql.delete(0, sql.length());
			sql
					.append(
							"select a.groupid from ec_termrulebind a,ec_commrulegroup b ")
					.append(
							"where a.groupid = b.id and  a.termid=(select termid from ec_resources c where c.resourceid='")
					.append(termNo).append("');");
			try {
				groups = db.getStringArray(sql.toString());
				for (int n = 0; groups != null&&n < groups.length; n++) {
					String groupid = groups[n];
					int [] rule = null;
					sql.delete(0, sql.length());
					sql.append("select setmode, value ")
					.append("from ec_termcardrule where groupid = ").append(groupid)
					.append("and cardtype=").append(cardType)
					.append("and cardfee=").append(cardFeeNo);
					try {
						rule = db.getIntArray(sql.toString());
					} catch (Exception ex) {
						Log.error("根据扣款方式获得规则组出错:", ex);
					}
					if(rule != null){
						if(rule[0] == 1){
							fee = rule[1]; 
						}
						if(rule[0] == 2){
							fee = fee*rule[1]/10000;
						}
						break;
					}

				}
			} catch (Exception ex) {
				Log.error("根据扣款方式获得规则组出错:", ex);
			}
		}
		return fee;
	}

	/**
	 * 获得代办信息
	 * @param goodsNo 代办商品编号
	 * @return 返回代办商品信息
	 */
	public String[] getBusiFee(int goodsNo) {
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						"select charge ,name ,goodsinfo,remark from ec_busiagentgoods where code=")
				.append(goodsNo);
		String[] str = null;

		try {
			str = db.getStringArray(sql.toString());
		} catch (Exception ex) {
			Log.error("根据代办商品扣款值出错:", ex);
		}

		return str;
	}

	/**
	 * 根据卡面值编号获得卡面值
	 * @param typeNo 代办类型编号
	 * @return 返回代办名称，业务类型
	 */
	public String[] getBusiType(int typeNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select service ,name from ec_busiagenttype where code=")
				.append(typeNo);
		String[] str = null;

		try {
			str = db.getStringArray(sql.toString());
		} catch (Exception ex) {
			Log.error("根据代办商品扣款值出错:", ex);
		}

		return str;
	}

	/**
	 * 获取售卡信息
	 * @return 返回String[],包括卡号,卡批次,密码,有效期,卡面值,卡志号,充值拨号方式,充值拨号说明
	 * @param cardType 卡类型编号
	 * @param cardFeeNo 卡面值编号
	 * @throws Exception
	 */
	public synchronized String[] getCardInf(int cardType, int cardFeeNo)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		//		sql.append("select first 1 cardno,cardbatch,password,overtime,c.name,cardsign, \n")
		//		   .append("case filltype when 1 then '充值方式' else '拨号方式' end,content \n")
		//		   .append("from ec_card a,ec_cardtype b,ec_cardfee c \n")
		//		   .append("where a.cardtype=b.id and a.cardfee=c.id and a.state=1 \n")
		//		   .append("and a.cardtype=").append(cardType)
		//		   .append(" and a.cardfee=").append(cardFeeNo)
		//		   .append(" and substr(a.overtime,1,8)>='").append(DateParser.getNowDate()).append("'");
		sql
				.append(
						"select  first 1 a.cardno,a.cardbatch,a.password,a.overtime,c.name,")
				.append(
						"b.pwdtype,b.fillphone,b.content,c.present,b.service ,a.cardsign from ec_card a,ec_cardtype b,ec_cardfee c")
				.append(
						" where a.cardtype=b.id and a.cardfee=c.id and a.state=1 ")
				.append("and a.cardtype=").append(cardType).append(
						" and a.cardfee=").append(cardFeeNo).append(
						" and a.overtime>='").append(
						DateParser.getNowDateTime()).append("' order by intime");

		String[] str = null;

		try {
			str = db.getStringArray(sql.toString());

			if (str != null) {
				this.updateCardState(str[0], Constant.CARD_FINAL_SELL);
			}
		} catch (Exception ex) {
			Log.error("获取卡号信息出错:", ex);
			throw ex;
		}

		return str;
	}
	/**
	 * 获取售卡信息
	 * @param cardNo 卡号
	 * @return 返回String[],包括卡号,卡批次,密码,有效期,卡面值,卡志号,充值拨号方式,充值拨号说明
	 * @throws Exception
	 */
	public synchronized String[] getCardInf(String cardNo)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		//		sql.append("select first 1 cardno,cardbatch,password,overtime,c.name,cardsign, \n")
		//		   .append("case filltype when 1 then '充值方式' else '拨号方式' end,content \n")
		//		   .append("from ec_card a,ec_cardtype b,ec_cardfee c \n")
		//		   .append("where a.cardtype=b.id and a.cardfee=c.id and a.state=1 \n")
		//		   .append("and a.cardtype=").append(cardType)
		//		   .append(" and a.cardfee=").append(cardFeeNo)
		//		   .append(" and substr(a.overtime,1,8)>='").append(DateParser.getNowDate()).append("'");
		sql
				.append(
						"select  first 1 a.overtime,b.name,c.name,")
				.append(
						"b.content,c.present  from ec_card a,ec_cardtype b,ec_cardfee c")
				.append(
						" where a.cardtype=b.id and a.cardfee=c.id ")
				.append("and a.cardno='").append(cardNo).append("'");

		String[] str = null;

		try {
			str = db.getStringArray(sql.toString());

			if (str != null) {
				this.updateCardState(str[0], Constant.CARD_FINAL_SELL);
			}
		} catch (Exception ex) {
			Log.error("获取卡号信息出错:", ex);
			throw ex;
		}

		return str;
	}

	/**
	 * 更新卡状态
	 * @param cardNo 卡号
	 * @param state 状态
	 * @throws Exception
	 */
	public synchronized void updateCardState(String cardNo, int state)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_card set state=").append(state).append(
				",distime='").append(DateParser.getNowDateTime()).append("'")
				.append(" where cardno='").append(cardNo).append("'");

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("更新卡状态出错:", ex);
			throw ex;
		}
	}

	/**
	 * 查询该工单是否为未处理的异常工单
	 * @param seqNo 流水号
	 * @param disState 更新状态
	 * @param state 状态
	 * @return 返回true表示是未处理的异常工单,反之表示不是
	 * @throws Exception 数据库异常
	 */
	public boolean isError(String seqNo, int disState, int state)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select seqno from ec_custerror where seqno='")
				.append(seqNo).append("'");

		if (disState >= 0) {
			sql.append(" and disstate=").append(disState);
		}

		if (state >= 0) {
			sql.append(" and state=").append(state);
		}

		boolean check = false;
		try {
			check = db.hasData(sql.toString());
		} catch (Exception ex) {
			Log.error("查询综服异常工单日志出错:", ex);
			throw ex;
		}

		return check;
	}

	/**
	 * @param seqNo 流水号
	 * @return 错误数量
	 * @throws Exception
	 */
	public int getErrNum(String seqNo) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select disnum from ec_custerror where seqno='").append(
				seqNo).append("'");

		int num = 0;
		try {
			num = db.getInt(sql.toString());
		} catch (Exception ex) {
			Log.error("查询综服异常工单处理失败次数出错:", ex);
			throw ex;
		}
		return num;
	}

	/**
	 * 查询正常工单日志表中是否存在该笔工单
	 * @param seqNo 流水号
	 * @return 返回true表示有,反之表示没有
	 * @throws Exception
	 */
	private boolean isSuccess(String seqNo) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select seqno from ec_custlog where seqno='").append(seqNo)
				.append("'");

		boolean check = false;
		try {
			check = db.hasData(sql.toString());
		} catch (Exception ex) {
			Log.error("查询综服工单处理日志出错:", ex);
			throw ex;
		}

		return check;
	}

	/**
	 * 判断该工单是否需要处理
	 * @param seqNo 流水号
	 * @param compdate 完成日期
	 * @param action 执行指令号
	 * @param servid 业务类型
	 * @return 返回true表示需要处理,反之表示不需要处理
	 * @throws Exception
	 */
	public int isNeedDisOrder(String seqNo, String compdate, int action,
			String servid) throws Exception {
		if (this.isSuccess(seqNo)) {
			this.updateErrOrderDis(seqNo, 2);
			return 1; //直接返回成功
		} else if (this.isError(seqNo, 2, 0)) //该工单已处理
		{
			return 1; //直接返回成功
		} else if (!this.isError(seqNo, -1, 0)) //正常日志表和异常日志表都没记录
		{
			return 9; //需要处理
		} else if (this.isIncludeOrder(seqNo, compdate, action, servid, 1) != null) {
			return -1; //直接记录失败日志
		}

		return -9;
	}

	//判断是否
	/**
	 * @param seqNo 流水号
	 * @param compdate 完成日期
	 * @param action 执行动作
	 * @param servid 业务类型
	 * @param source 交易源
	 * @return 流水号
	 */
	public String isIncludeOrder(String seqNo, String compdate, int action,
			String servid, int source) {
		StringBuffer sql = new StringBuffer();
		String seqno = null;
		sql.append("select first 1 seqno from ec_custerror where servid=")
				.append(servid).append(" and compdate<'").append(compdate)
				.append("' and seqno!='").append(seqNo).append(
						"' and disstate!=2 and state=0 and source=").append(
						source).append(" order by compdate;");

		try {
			seqno = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("判断综服工单与已有异常工单是否相容出错:", ex);
		}

		return seqno;
	}

	/**
	 * @param errOrder 错误信息
	 * @param type 类型
	 * @return 成功状态
	 * @throws Exception
	 */
	public int insertErrorOrder(CustErrorOrder errOrder, int type)
			throws Exception {
		if (this.isError(errOrder.getSeqNo(), -1, -1)) {
			if (type == 1) //当type=1时,累加处理失败次数
			{
				this.updateErrorNums(errOrder.getSeqNo());
			}
			return this.getErrNum(errOrder.getSeqNo());
		}
		String sql = "insert into ec_custerror values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			db.update(sql, errOrder.getCustErrorOrder());
		} catch (Exception ex) {
			Log.error("插入综服异常工单日志出错:", ex);
			throw ex;
		}

		return this.getErrNum(errOrder.getSeqNo());
	}

	/**
	 * @param seqNo 流水号
	 * @param areaCode 区号
	 * @param servId 服务号
	 * @param custid 客户号码
	 * @param custName 客户名称
	 * @param action 之性动作
	 * @param prodid proid
	 * @param compDate 完成日期
	 * @param stsDate 开始日期
	 * @param disState 变更日期
	 * @param state 状态
	 * @param errorExp 错误说明
	 * @throws Exception 
	 */
	public void insertErrorOrder(String seqNo, String areaCode, String servId,
			String custid, String custName, int action, int prodid,
			String compDate, String stsDate, int disState, int state,
			String errorExp) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ec_custerror values ('").append(seqNo).append(
				"','").append(areaCode).append("',").append(servId).append(",")
				.append(custid).append(",'").append(custName).append("',")
				.append(action).append(",").append(prodid).append(",'").append(
						compDate).append("','").append(stsDate).append("',")
				.append(disState).append(",").append(state).append(",'")
				.append(errorExp).append("')");

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("插入综服异常工单日志出错:", ex);
			throw ex;
		}
	}

	/**
	 * @param orderLog 
	 * @throws Exception
	 */
	public void insertSuccOrder(CustOrderLog orderLog) throws Exception {
		String sql = "insert into ec_custlog values (?,?,?,?,?,?,?,?)";

		try {
			db.update(sql, orderLog.getCustOrderLog());
		} catch (Exception ex) {
			Log.error("插入综服工单处理日志出错:", ex);
			throw ex;
		}
	}

	/**
	 * @param seqNo
	 * @param areaCode
	 * @param servId
	 * @param custid
	 * @param custName
	 * @param action
	 * @param prodid
	 * @param compDate
	 * @throws Exception
	 */
	public void insertSuccOrder(String seqNo, String areaCode, String servId,
			String custid, String custName, int action, int prodid,
			String compDate) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ec_custlog values ('").append(seqNo).append(
				"','").append(areaCode).append("',").append(servId).append(",")
				.append(custid).append(",'").append(custName).append("',")
				.append(action).append(",").append(prodid).append(",'").append(
						compDate).append("')");

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("插入综服工单处理日志出错:", ex);
			throw ex;
		}
	}

	public void updateErrorNums(String seqNo) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_custerror set disnum=disnum+1").append(
				" where seqno='").append(seqNo).append("'");

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("更新综服异常工单处理次数出错:", ex);
			throw ex;
		}
	}

	/**
	 * @param tradeNo
	 * @param posid
	 * @return
	 * @throws Exception
	 */
	public List getAgentTypes(String tradeNo, String posid) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql
				.append(
						"select a.code,a.name,b.code,b.name,b.charge,b.goodsinfo from ec_busiagenttype a,ec_busiagentgoods b \n")
				.append("where a.code=b.type and a.state=0 and b.state=0 \n")
				.append("and a.service='")
				.append(tradeNo)
				.append(
						"' and b.code in(select goodscode from ec_areaagentgoods where \n")
				.append(
						"district in (select district from ec_terminal where termid in \n")
				.append("(select termid from ec_resources where posid='")
				.append(posid).append("'))) order by a.showgrade");
		List rs = null;
		try {
			rs = db.getList(sql.toString());
		} catch (Exception ex) {
			Log.error("查询代办业务出错:", ex);
			throw ex;
		}

		return rs;
	}

	//修改异常工单处理状态
	/**
	 * @param seqNo
	 * @param state
	 * @throws Exception
	 */
	public void updateErrOrderDis(String seqNo, int state) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_custerror set disnum=0,disstate=").append(state)
				.append(",stsdate='").append(DateParser.getNowDateTime())
				.append("' where seqno='").append(seqNo).append("'");

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("更新综服异常工单处理状态出错:", ex);
			throw ex;
		}
	}

	//修改异常工单状态
	/**
	 * @param seqNo
	 * @param state
	 * @throws Exception
	 */
	public void updateErrOrderSts(String seqNo, int state) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ec_custerror set state=").append(state).append(
				",stsdate='").append(DateParser.getNowDateTime()).append(
				"' where seqno='").append(seqNo).append("'");
		Log.debug("--修改综服异常工单状态--:" + sql.toString());

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("修改综服异常工单状态出错:", ex);
			throw ex;
		}
	}

	/**
	 * 根据编码名称从SCP库表中获得对应的增长序列值，并用0在前面补全为固定长度的字符串。
	 * @param code 编码名称
	 * @param length 长度
	 * @return 增长序列值
	 * @throws SQLException
	 */
	public static String getCodeSequence(String code, int length)
			throws SQLException {
		int sequence = 0;
		DBService db = null;
		try {
			db = new DBService();
			db.setAutoCommit(false);

			StringBuffer sql = new StringBuffer(100);
			sql
					.append(
							"update ec_code_sequence set sequence=sequence+1 where code='")
					.append(code).append("'");
			db.update(sql.toString());

			sql.delete(0, sql.length());
			sql.append("select sequence from ec_code_sequence where code='")
					.append(code).append("'");
			sequence = db.getInt(sql.toString());

			db.commit();
		} catch (SQLException e) {
			db.rollback();
			throw e;
		} finally {
			db.close();
		}

		DecimalFormat df = new DecimalFormat(Tools.repeat("0", length));
		return df.format(sequence);
	}

	/**
	 * 获取消息流水号
	 * @param type 消息类型码
	 * @return 返回消息流水号
	 */
	public String getSeqNo(String type) {
		String msgSeqNo = null;
		String time = DateParser.getNowDateTime().substring(2);
		try {
			String sql = "select ec_takedeposit_id.nextval from dual";
			DecimalFormat df = new DecimalFormat("0000000000");
			String seq = df.format(db.getLong(sql));
			//获得流水号
			msgSeqNo = time + type + seq;
		} catch (Exception ex) {
			Log.error("获取发送消息流水号出错", ex);
		}

		return msgSeqNo;
	}

	public List getRePrintService(String areaCode) {
		StringBuffer sql = new StringBuffer();
		List rsList = null;

		try {
			sql.append(
					"select a.code,a.name from ec_service a,ec_reprintset b ")
					.append("where a.code=b.service and b.areacode='").append(
							areaCode).append("'");

			rsList = db.getList(sql.toString());

		} catch (Exception ex) {
			Log.error("查询重打印业务类型出错", ex);
		}

		return rsList;
	}

	public String getRePrintInfo(String termId, String service, String payNo,
			String date) {
		StringBuffer sql = new StringBuffer();
		String str = null;

		try {
			sql
					.append("select first 1 printmsg from ec_reprint where ")
					.append(
							"resourceid=(select resourceid from ec_resources where posid='")
					.append(termId).append("') and service='").append(service)
					.append("' and account='").append(payNo).append(
							"' and tradetime like '").append(date).append(
							"%' order by tradetime desc");
			str = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("查询重打印数据出错", ex);
		}

		return str;
	}
	
	/**
	 * 获取重缴费信息
	 * @return List
	 */
	public List getPayInfo()
	{
		StringBuffer sql = new StringBuffer();
		List rsList = null;
		
		try
		{
			sql.append("select first 10 tradeserial,paydata,serviceseqno ,number from ec_payinfo ")
			   .append("where state=1 and number<'5' ");
			
			rsList = db.getList(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("查询重缴费信息出错",ex);
		}
		
		return rsList;
	} 
	/**
	 * 获取重返销信息
	 * @return List
	 */
	public List getBackInfo()
	{
		StringBuffer sql = new StringBuffer();
		List rsList = null;
		
		try
		{
			sql.append("select first 10 tradeserial,backdata,serviceseqno,area from ec_backinfo ")
			.append("where state=1 and number<5 ");
			rsList = db.getList(sql.toString());
			//Log.info("获取重返销信息记录数："+rsList.size());
		}catch(Exception ex)
		{
			Log.error("查询重返销信息出错",ex);
		}
		
		return rsList;
	} 
	
	public int getPayState(String seqNo)
	{
		StringBuffer sql = new StringBuffer();
		int state = 0;
		
		try
		{
			sql.append("select state from ec_payinfo ")
			   .append("where tradeserial='")
			   .append(seqNo)
			   .append("';");
			
			state = db.getInt(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("查询重打印业务类型出错",ex);
		}
		
		return state;
	} 
	
	/**
	 * 修改重缴费次数
	 * @param seqNo 交易流水号
	 * @param num 重缴费次数
	 */
	public void updatePayInfo(String seqNo,String num)
	{
		StringBuffer sql = new StringBuffer();
		
		try
		{
			sql.append("update ec_payinfo set number='").append(num)
			.append("' where tradeserial='").append(seqNo).append("';");
			db.update(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("修改重缴费次数出错",ex);
		}
		
	}
	/**
	 * 修改重返销次数
	 * @param seqNo 交易流水号
	 */
	public void updateBackInfoNum(String seqNo)
	{
		StringBuffer sql = new StringBuffer();
		
		try
		{
			sql.append("update ec_backinfo set number=number+1 ")
			   .append(" where tradeserial='").append(seqNo).append("';");
			Log.info("修改重返销次数SQL:"+sql.toString());
			db.update(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("修改重返销次数出错",ex);
		}
		
	}

	/**
	 * 修改重缴费状态
	 * @param seqNo
	 * @param state
	 */
	public void updatePayInfo(String seqNo,int state)
	{
		StringBuffer sql = new StringBuffer();
		
		try
		{
			sql.append("update ec_payinfo set state='").append(state)
			.append("' where tradeserial='").append(seqNo).append("';");
			
			db.update(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("修改重缴费状态出错",ex);
		}
		
	}
	/**
	 * 修改重返销状态
	 * @param seqNo
	 * @param state
	 */
	public void updateBackInfoState(String seqNo,int state)
	{
		StringBuffer sql = new StringBuffer();
		
		try
		{
			sql.append("update ec_backinfo set state='").append(state)
			.append("' where serviceseqno='").append(seqNo).append("';");
			Log.info("修改重返销SQL:"+sql);
			db.update(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("修改重返销信息出错",ex);
		}
		
	}
	/**
	 * 修改订单返销状态
	 * @param seqNo
	 * @param state
	 */
	public void updateOrderState(String seqNo,int state)
	{
		StringBuffer sql = new StringBuffer();
		String date = DateParser.getNowDate();
		String month = date.substring(2, 6);
		try
		{
			sql.append("update ec_orderForm_"+month+" set state='").append(state)
			.append("' where tradeserial='").append(seqNo).append("';");
			Log.info("修改订单返销SQL:"+sql);
			db.update(sql.toString());
			
		}catch(Exception ex)
		{
			Log.error("修改订单返销信息出错",ex);
		}
		
	}
	
	public void addRePrint(String termId, String service, String tradeTime,
			String payNo, String rePrintMsg) throws Exception {
		StringBuffer sql = new StringBuffer();
		/*sql.append("select count(*) from ec_reprint where resourceid='").append(termId)
				.append("' and service='").append(service).append(
						"' and account='").append(payNo).append("'");
		int count=db.getInt(sql.toString());
		sql.delete(0, sql.length());
		if(count>0){
			sql.append("update ec_reprint set tradetime='").append(tradeTime).append("'")
			   .append(",printmsg='").append(rePrintMsg).append("'")
			   .append(" where resourceid='").append(termId)
			   .append("' and service='").append(service)
			   .append("' and account='").append(payNo).append("'");
		}else{*/
			sql.append("insert into ec_reprint values ('").append(termId).append(
			    "','").append(service).append("','").append(tradeTime).append(
			    "','").append(payNo).append("','").append(rePrintMsg).append(
			    "',null)");
		//}

		try {
			db.update(sql.toString());
		} catch (Exception ex) {
			Log.error("添加重打印数据出错", ex);
			throw ex;
		}
	}
	
	/**
	 * 根据posid查询绑定资金账号
	 * @param posid 终端ID
	 * @return 绑定资金账号信息
	 * @throws Exception
	 */
	public String getFacct(String posid) throws Exception {
		StringBuffer sql = new StringBuffer();
		String fundAcct="";
		sql.append("select fundacct from  ec_resources   \n")
		.append("where  posid ='").append(posid).append("'");
		
		try {
			fundAcct = db.getString(sql.toString());
		} catch (Exception ex) {
			Log.error("查询代办户银行绑定账号:", ex);
			throw ex;
		}
		return fundAcct;
	}
}
