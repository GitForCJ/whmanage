package com.wlt.webm.business.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ejet.util.DataUtil;
import com.wlt.webm.business.form.AcctBillForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.util.PageAttribute;
import com.wlt.webm.util.Tools;


/**
 * 账户明细管理
 */
public class AcctBillBean
{
	/**
	 * 获取交易总额
	 * @return 交易总额
	 * @throws Exception
	 */
	public double getFeeForTotal(String tableName,AcctBillForm acctForm) throws Exception {
		if(null!=acctForm.getStartDate()&&!"".equals(acctForm.getStartDate())){
			tableName="wht_acctbill_"+(String) acctForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService db = new DBService(Constants.DBNAME_SCP);
        try {

        	StringBuffer sql = new StringBuffer();
			sql.append(" select sum(a.infacctfee)");
			sql.append(" from "+tableName+" a ");
			sql.append(" LEFT JOIN wht_acctradetype b on a.tradetype = b.code");
			sql.append(" LEFT JOIN wht_childfacct c on a.childfacct = c.childfacct");
			sql.append(" LEFT JOIN wht_facct d on c.fundacct = d.fundacct");
			sql.append(" LEFT JOIN sys_user e on d.user_no=e.user_no");
			sql.append(" LEFT JOIN sys_area g on e.user_site=g.sa_id");
//			sql.append(" LEFT JOIN wlt_orderform_"+DateParser.getNowDateTable()+" h on a.tradeserial = h.tradeserial");
			sql.append(" where  a.state=0 ");
			if(null != acctForm.getDealserial() && !"".equals(acctForm.getDealserial())){
				sql.append(" and a.dealserial = '"+acctForm.getDealserial()+"'");
			}
			if(null != acctForm.getStartDate() && !"".equals(acctForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+acctForm.getStartDate().replaceAll("-", "")+"000000'");
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				acctForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			if(null != acctForm.getEndDate() && !"".equals(acctForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+acctForm.getEndDate().replaceAll("-", "")+"235959'");
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				acctForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			if(null != acctForm.getAreacode() && !"".equals(acctForm.getAreacode())){
				sql.append(" and e.user_site = "+acctForm.getAreacode());
			}
			if(null != acctForm.getTradetype() && !"".equals(acctForm.getTradetype())){
				sql.append(" and a.tradetype ="+acctForm.getTradetype());
			}
			if(null != acctForm.getPay_type() && !"".equals(acctForm.getPay_type())){
				if("0".equals(acctForm.getPay_type())){
					sql.append(" and a.pay_type <> 1 ");
				}else {
					sql.append(" and a.pay_type = '"+acctForm.getPay_type()+"'");
				}
			}
			if(null != acctForm.getState() && !"".equals(acctForm.getState())){
				sql.append(" and a.state = '"+acctForm.getState()+"'");
			}
			if(null != acctForm.getAcctType() && !"".equals(acctForm.getAcctType())){
				sql.append(" and c.type = '"+acctForm.getAcctType()+"'");
			}
			if(null != acctForm.getTradeaccount() && !"".equals(acctForm.getTradeaccount())){
				sql.append(" and e.user_login = '"+acctForm.getTradeaccount()+"'");
			}
			if(null != acctForm.getUserId() && !"".equals(acctForm.getUserId())){
				sql.append(" and e.user_id = '"+acctForm.getUserId()+"'");
			}
			if(null != acctForm.getTradeserial() && !"".equals(acctForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+acctForm.getTradeserial()+"'");
			}
			if(null != acctForm.getRoleFilter() && !"".equals(acctForm.getRoleFilter())){
				sql.append(acctForm.getRoleFilter());
			}
            return db.getDouble(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=db)
        		db.close();
        }
	}
	/**
	 * 获取账户明细列表 
	 * @return 账户明细列表 
	 * @throws Exception
	 */
	public int countAcctBill(String tableName,AcctBillForm acctForm,PageAttribute page) throws Exception {
		if(null!=acctForm.getStartDate()&&!"".equals(acctForm.getStartDate())){
			tableName="wht_acctbill_"+(String) acctForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService = new DBService();
		try {
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*)");
			sql.append(" from "+tableName+" a ");
			sql.append(" LEFT JOIN wht_acctradetype b on a.tradetype = b.`code`");
			sql.append(" LEFT JOIN wht_childfacct c on a.childfacct = c.childfacct");
			sql.append(" LEFT JOIN wht_facct d on c.fundacct = d.fundacct");
			sql.append(" LEFT JOIN sys_user e on d.user_no=e.user_no");
			sql.append(" LEFT JOIN sys_area g on e.user_site=g.sa_id");
//			sql.append(" LEFT JOIN wlt_orderform_"+DateParser.getNowDateTable()+" h on a.tradeserial = h.tradeserial");
			sql.append(" where  1=1 ");
			if(null != acctForm.getDealserial() && !"".equals(acctForm.getDealserial())){
				sql.append(" and a.dealserial = '"+acctForm.getDealserial()+"'");
				paraBuffer.append("&dealserial="+acctForm.getDealserial());
			}
			if(null != acctForm.getStartDate() && !"".equals(acctForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+acctForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+acctForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				acctForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != acctForm.getEndDate() && !"".equals(acctForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+acctForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+acctForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				acctForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != acctForm.getAreacode() && !"".equals(acctForm.getAreacode())){
				sql.append(" and e.user_site = "+acctForm.getAreacode());
				paraBuffer.append("&areacode="+acctForm.getAreacode());
			}
			if(null != acctForm.getTradetype() && !"".equals(acctForm.getTradetype())){
				sql.append(" and a.tradetype = '"+acctForm.getTradetype()+"'");
				paraBuffer.append("&tradetype="+acctForm.getTradetype());
			}
			if(null != acctForm.getPay_type() && !"".equals(acctForm.getPay_type())){
				if("0".equals(acctForm.getPay_type())){
					sql.append(" and a.pay_type <> 1 ");
				}else {
					sql.append(" and a.pay_type = '"+acctForm.getPay_type()+"'");
				}
				paraBuffer.append("&pay_type="+acctForm.getPay_type());
			}
			if(null != acctForm.getState() && !"".equals(acctForm.getState())){
				sql.append(" and a.state = '"+acctForm.getState()+"'");
				paraBuffer.append("&state="+acctForm.getState());
			}
			if(null != acctForm.getAcctType() && !"".equals(acctForm.getAcctType())){
				sql.append(" and c.type = '"+acctForm.getAcctType()+"'");
				paraBuffer.append("&acctType="+acctForm.getAcctType());
			}
			if(null != acctForm.getTradefee() && !"".equals(acctForm.getTradefee())){
				sql.append(" and a.tradefee/1000 = '"+acctForm.getTradefee()+"'");
				paraBuffer.append("&tradefee="+acctForm.getTradefee());
			}
			if(null != acctForm.getTradeaccount() && !"".equals(acctForm.getTradeaccount())){
				sql.append(" and e.user_login = '"+acctForm.getTradeaccount()+"'");
				paraBuffer.append("&tradeaccount="+acctForm.getTradeaccount());
			}
			if(null != acctForm.getUsername() && !"".equals(acctForm.getUsername())){
				sql.append(" and f.user_name = '"+acctForm.getUsername()+"'");
				paraBuffer.append("&username="+acctForm.getUsername());
			}
			if(null != acctForm.getUserId() && !"".equals(acctForm.getUserId())){
				sql.append(" and e.user_id = '"+acctForm.getUserId()+"'");
				paraBuffer.append("&userId="+acctForm.getUserId());
			}
			if(null != acctForm.getUser_ename() && !"".equals(acctForm.getUser_ename())){
				sql.append(" and e.user_ename = '"+acctForm.getUser_ename()+"'");
				paraBuffer.append("&user_ename="+acctForm.getUser_ename());
			}
			if(null != acctForm.getTradeserial() && !"".equals(acctForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+acctForm.getTradeserial()+"' ");
				paraBuffer.append("&tradeserial="+acctForm.getTradeserial());
			}
			if(null!=acctForm.getTradeaccountNumber() && !"".equals(acctForm.getTradeaccountNumber())){
				sql.append(" and a.tradeaccount='"+acctForm.getTradeaccountNumber()+"'");
				paraBuffer.append("&tradeaccountNumber="+acctForm.getTradeaccountNumber());
			}
			if(null != acctForm.getRoleFilter() && !"".equals(acctForm.getRoleFilter())){
				sql.append(acctForm.getRoleFilter());
			}
			
			int result = dbService.getInt(sql.toString());
			return result;
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
	/**
	 * @return 奖励明细列表 
	 * @throws Exception
	 */
	public int countAwards(String tableName,AcctBillForm acctForm,PageAttribute page) throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*)");
			sql.append(" from "+tableName+" a ");
			sql.append(" where  1=1 ");
			if(null != acctForm.getStartDate() && !"".equals(acctForm.getStartDate())){
				sql.append(" and a.statisticdate >= '"+acctForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+acctForm.getStartDate());
			}else{
				sql.append(" and a.statisticdate >= '"+Tools.getNow4()+"000000'");
				acctForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != acctForm.getEndDate() && !"".equals(acctForm.getEndDate())){
				sql.append(" and a.statisticdate <= '"+acctForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+acctForm.getEndDate());
			}else{
				sql.append(" and a.statisticdate <= '"+Tools.getNow4()+"235959'");
				acctForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != acctForm.getState() && !"".equals(acctForm.getState())){
				sql.append(" and a.state = '"+acctForm.getState()+"'");
				paraBuffer.append("&state="+acctForm.getState());
			}
			int result = dbService.getInt(sql.toString());
			return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
	
	
	/**
	 * 获取交易总额
	 * @return 交易总额
	 * @throws Exception
	 */
	public int getTotalFee(String userId,String acctType,String payType) throws Exception {
		DBService db = new DBService(Constants.DBNAME_SCP);
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT sum(tradefee) from wlt_acctbill_"+DateParser.getNowDateTable()+" a " +
            		" LEFT JOIN wlt_childfacct c on a.childfacct = c.childfacct" +
            		" LEFT JOIN wlt_facct d on c.fundacct = d.fundacct" +
            		" LEFT JOIN sys_user e on d.termid=e.user_id" +
            		" where e.user_id="+userId);
            if(null != acctType){
            	sql.append(" and c.accttypeid = '"+acctType+"'");
            }
            if("0".equals(payType)){
				sql.append(" and a.pay_type <> 1 ");
			}else {
				sql.append(" and a.pay_type = '"+payType+"'");
			}

            return db.getInt(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(db!=null)
        		db.close();
        }
	}
	/**
	 * 获取账户明细列表 
	 * @return 账户明细列表 
	 * @throws Exception
	 */
	public List listAcctBill(String tableName,AcctBillForm acctForm,PageAttribute page) throws Exception {
		if(null!=acctForm.getStartDate()&&!"".equals(acctForm.getStartDate())){
			tableName="wht_acctbill_"+(String) acctForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService = new DBService();
		try {
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.dealserial,a.childfacct,a.tradeaccount,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s'),a.tradefee,a.infacctfee,b.name,a.expl,a.state,DATE_FORMAT(a.distime,'%Y-%m-%d %k:%i:%s'),a.accountleft,a.tradeserial,a.other_childfacct,a.pay_type,c.type,g.sa_name,e.user_login,e.user_site ");
			if("11".equals(acctForm.getTradetype()))
			{
				sql.append(" ,(SELECT ss.user_ename FROM sys_user ss WHERE ss.user_login=a.tradeaccount) aabbcc ");
			}
			sql.append(" from "+tableName+" a ");
			sql.append(" LEFT JOIN wht_acctradetype b on a.tradetype = b.code");
			sql.append(" LEFT JOIN wht_childfacct c on a.childfacct = c.childfacct");
			sql.append(" LEFT JOIN wht_facct d on c.fundacct = d.fundacct");
			sql.append(" LEFT JOIN sys_user e on d.user_no=e.user_no");
			sql.append(" LEFT JOIN sys_area g on e.user_site=g.sa_id");
//			sql.append(" LEFT JOIN wlt_orderform_"+DateParser.getNowDateTable()+" h on a.tradeserial = h.tradeserial");
			sql.append(" where  1=1 ");
			if(null != acctForm.getDealserial() && !"".equals(acctForm.getDealserial())){
				sql.append(" and a.dealserial = '"+acctForm.getDealserial()+"'");
				paraBuffer.append("&dealserial="+acctForm.getDealserial());
			}
			if(null != acctForm.getStartDate() && !"".equals(acctForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+acctForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+acctForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				acctForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != acctForm.getEndDate() && !"".equals(acctForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+acctForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+acctForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				acctForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != acctForm.getAreacode() && !"".equals(acctForm.getAreacode())){
				sql.append(" and e.user_site = "+acctForm.getAreacode());
				paraBuffer.append("&areacode="+acctForm.getAreacode());
			}
			if(null != acctForm.getTradetype() && !"".equals(acctForm.getTradetype())){
				sql.append(" and a.tradetype ="+acctForm.getTradetype());
				paraBuffer.append("&tradetype="+acctForm.getTradetype());
			}
			if(null != acctForm.getPay_type() && !"".equals(acctForm.getPay_type())){
				if("0".equals(acctForm.getPay_type())){
					sql.append(" and a.pay_type <> 1 ");
				}else {
					sql.append(" and a.pay_type = '"+acctForm.getPay_type()+"'");
				}
				paraBuffer.append("&pay_type="+acctForm.getPay_type());
			}
			if(null != acctForm.getState() && !"".equals(acctForm.getState())){
				sql.append(" and a.state = '"+acctForm.getState()+"'");
				paraBuffer.append("&state="+acctForm.getState());
			}
			if(null != acctForm.getAcctType() && !"".equals(acctForm.getAcctType())){
				sql.append(" and c.type = '"+acctForm.getAcctType()+"'");
				paraBuffer.append("&acctType="+acctForm.getAcctType());
			}
			if(null != acctForm.getTradeaccount() && !"".equals(acctForm.getTradeaccount())){
				sql.append(" and e.user_login = '"+acctForm.getTradeaccount()+"'");
				paraBuffer.append("&tradeaccount="+acctForm.getTradeaccount());
			}
			if(null != acctForm.getUserId() && !"".equals(acctForm.getUserId())){
				sql.append(" and e.user_id = '"+acctForm.getUserId()+"'");
				paraBuffer.append("&userId="+acctForm.getUserId());
			}
			if(null != acctForm.getTradeserial() && !"".equals(acctForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+acctForm.getTradeserial()+"' ");
				paraBuffer.append("&tradeserial="+acctForm.getTradeserial());
			}
			if(null != acctForm.getRoleFilter() && !"".equals(acctForm.getRoleFilter())){
				sql.append(acctForm.getRoleFilter());
				paraBuffer.append("&dailishang="+acctForm.getDailishang());
			}
//			if(null != acctForm.getDailishang() && !"".equals(acctForm.getDailishang())){
//				sql.append(" and e.user_id="+acctForm.getDailishang());
//			}
			if(null!=acctForm.getTradeaccountNumber() && !"".equals(acctForm.getTradeaccountNumber())){
				sql.append(" and a.tradeaccount='"+acctForm.getTradeaccountNumber()+"'");
				paraBuffer.append("&tradeaccountNumber="+acctForm.getTradeaccountNumber());
			}
			sql.append(" order by a.id desc");
			//增加excel导出
			if(!acctForm.isExcel())
			{
			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			}
			acctForm.setParamUrl(paraBuffer.toString());
			List list = dbService.getList(sql.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[8] && !"".equals(temp[8])){
					if("0".equals(temp[8])){
						temp[8] = "正常";
					}else if("1".equals(temp[8])){
						temp[8] = "取消";
					}
				}
				if(null != temp[13] && !"".equals(temp[13])){
					if("1".equals(temp[13])){
						temp[13] = "支出";
					}else if("2".equals(temp[13])){
						temp[13] = "存入";
					}else if("3".equals(temp[13])){
						temp[13] = "下级交易返佣";
					}else if("4".equals(temp[13])){
						temp[13] = "奖励佣金";
					}
				}
				if(null != temp[14] && !"".equals(temp[14])){
					if("01".equals(temp[13])){
						temp[13] = "押金账户";
					}else if("02".equals(temp[13])){
						temp[13] = "佣金账户";
					}else if("03".equals(temp[13])){
						temp[13] = "冻结账户";
					}
				}
				if(null != temp[17] && !"".equals(temp[17])){
					String citySql = "select sa_name from  sys_area where sa_id = "+temp[17];
					temp[17] = dbService.getString(citySql);
				}
			}
			return list;
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
		/**
		 * 获取奖励明细
		 * @param tableName
		 * @param acctForm
		 * @param page
		 * @return 
		 * @throws Exception
		 */
	public List listAwards(String tableName,AcctBillForm acctForm,PageAttribute page) throws Exception {
		DBService dbService = null;
		try {
			 dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT a.login,a.usertype,a.money,SUBSTRING(a.money*(b.commissionrate/100),1,INSTR(a.money*(b.commissionrate/100),'.')-1),CONCAT(b.minmoney,'-',b.maxmoney),b.commissionrate,a.statisticdate,a.state,a.childfacct,a.id ");
			sql.append("FROM wht_monthawards a,wht_awardsrule b WHERE a.money>=(b.minmoney*1000) AND a.money<(b.maxmoney*1000) AND a.usertype=b.usertype");
			if(null != acctForm.getStartDate() && !"".equals(acctForm.getStartDate())){
				sql.append(" and a.statisticdate >= '"+acctForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+acctForm.getStartDate());
			}else{
				sql.append(" and a.statisticdate >= '"+Tools.getNow4()+"000000'");
				acctForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != acctForm.getEndDate() && !"".equals(acctForm.getEndDate())){
				sql.append(" and a.statisticdate <= '"+acctForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+acctForm.getEndDate());
			}else{
				sql.append(" and a.statisticdate <= '"+Tools.getNow4()+"235959'");
				acctForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != acctForm.getState() && !"".equals(acctForm.getState())){
				sql.append(" and a.state = '"+acctForm.getState()+"'");
				paraBuffer.append("&state="+acctForm.getState());
			}
			sql.append(" order by a.statisticdate desc");
			//增加excel导出
			if(!acctForm.isExcel())
			{
			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			}
			acctForm.setParamUrl(paraBuffer.toString());
			List list = dbService.getList(sql.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[1] && !"".equals(temp[1])){
					if("2".equals(temp[1])){
						temp[1] = "代理商";
					}else if("4".equals(temp[1])){
						temp[1] = "接口商";
					}
				}
				if(null != temp[7] && !"".equals(temp[7])){
					if("0".equals(temp[7])){
						temp[7] = "已奖励";
					}else if("1".equals(temp[7])){
						temp[7] = "未奖励";
					}
				}
			}
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
	/**
	 * 获取佣金明细列表 
	 * @return 账户明细列表 
	 * @throws Exception
	 */
	public List listyjBill(String tableName,AcctBillForm acctForm,PageAttribute page) throws Exception {
		if(null!=acctForm.getStartDate()&&!"".equals(acctForm.getStartDate())){
			tableName="wlt_acctbill_"+(String) acctForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService = new DBService();
		try {
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.childfacct,a.dealserial,a.tradeaccount,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s'),a.tradefee,b.name,a.explain,a.state,DATE_FORMAT(a.distime,'%Y-%m-%d %k:%i:%s'),a.accountleft,a.tradeserial,a.other_childfacct,a.pay_type,c.accttypeid,g.sa_name,e.user_ename,e.user_site_city");
			sql.append(" from "+tableName+" a ");
			sql.append(" LEFT JOIN wlt_acctradetype b on a.tradetype = b.code");
			sql.append(" LEFT JOIN wlt_childfacct c on a.childfacct = c.childfacct");
			sql.append(" LEFT JOIN wlt_facct d on c.fundacct = d.fundacct");
			sql.append(" LEFT JOIN sys_user e on d.termid=e.user_id");
			sql.append(" LEFT JOIN sys_loginuser f on e.user_id = f.user_id");
			sql.append(" LEFT JOIN sys_area g on e.user_site=g.sa_id");
//			sql.append(" LEFT JOIN wlt_orderform_"+DateParser.getNowDateTable()+" h on a.tradeserial = h.tradeserial");
			sql.append(" where  a.state=0 ");
			if(null != acctForm.getDealserial() && !"".equals(acctForm.getDealserial())){
				sql.append(" and a.dealserial = '"+acctForm.getDealserial()+"'");
				paraBuffer.append("&dealserial="+acctForm.getDealserial());
			}
			if(null != acctForm.getStartDate() && !"".equals(acctForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+acctForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+acctForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != acctForm.getEndDate() && !"".equals(acctForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+acctForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+acctForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != acctForm.getAreacode() && !"".equals(acctForm.getAreacode())){
				sql.append(" and e.user_site = "+acctForm.getAreacode());
				paraBuffer.append("&areacode="+acctForm.getAreacode());
			}
			if(null != acctForm.getTradetype() && !"".equals(acctForm.getTradetype())){
				sql.append(" and a.tradetype = '"+acctForm.getTradetype()+"'");
				paraBuffer.append("&tradetype="+acctForm.getTradetype());
			}
			if(null != acctForm.getPay_type() && !"".equals(acctForm.getPay_type())){
				if("0".equals(acctForm.getPay_type())){
					sql.append(" and a.pay_type <> 1 ");
				}else {
					sql.append(" and a.pay_type = '"+acctForm.getPay_type()+"'");
				}
				paraBuffer.append("&pay_type="+acctForm.getPay_type());
			}
			if(1==1){
				sql.append(" and a.state = 0 ");
				paraBuffer.append("&state=0 ");
			}
			if(null != acctForm.getAcctType() && !"".equals(acctForm.getAcctType())){
				sql.append(" and c.accttypeid = '"+acctForm.getAcctType()+"'");
				paraBuffer.append("&acctType="+acctForm.getAcctType());
			}
			if(null != acctForm.getTradefee() && !"".equals(acctForm.getTradefee())){
				sql.append(" and a.tradefee/100 = '"+acctForm.getTradefee()+"'");
				paraBuffer.append("&tradefee="+acctForm.getTradefee());
			}
			if(null != acctForm.getTradeaccount() && !"".equals(acctForm.getTradeaccount())){
				sql.append(" and d.fundacct = '"+acctForm.getTradeaccount()+"'");
				paraBuffer.append("&tradeaccount="+acctForm.getTradeaccount());
			}
			if(null != acctForm.getUsername() && !"".equals(acctForm.getUsername())){
				sql.append(" and f.user_name = '"+acctForm.getUsername()+"'");
				paraBuffer.append("&username="+acctForm.getUsername());
			}
			if(null != acctForm.getUserId() && !"".equals(acctForm.getUserId())){
				sql.append(" and e.user_id = '"+acctForm.getUserId()+"'");
				paraBuffer.append("&userId="+acctForm.getUserId());
			}
			if(null != acctForm.getUser_ename() && !"".equals(acctForm.getUser_ename())){
				sql.append(" and e.user_ename = '"+acctForm.getUser_ename()+"'");
				paraBuffer.append("&user_ename="+acctForm.getUser_ename());
			}
			if(null != acctForm.getUser_site_city() && !"".equals(acctForm.getUser_site_city())){
				sql.append(" and e.user_site_city = '"+acctForm.getUser_site_city()+"'");
				paraBuffer.append("&user_site_city="+acctForm.getUser_site_city());
			}
			if(null != acctForm.getTradeserial() && !"".equals(acctForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+acctForm.getTradeserial()+"'");
				paraBuffer.append("&tradeserial="+acctForm.getTradeserial());
			}
			sql.append(" order by a.tradetime desc");
			if(!acctForm.isExcel())
			{
			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			}
			acctForm.setParamUrl(paraBuffer.toString());
			List list = dbService.getList(sql.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[7] && !"".equals(temp[7])){
					if("0".equals(temp[7])){
						temp[7] = "正常";
					}else if("1".equals(temp[7])){
						temp[7] = "取消";
					}
				}
				if(null != temp[12] && !"".equals(temp[12])){
					if("1".equals(temp[12])){
						temp[12] = "支出";
					}else if("2".equals(temp[12])){
						temp[12] = "存入";
					}else if("3".equals(temp[12])){
						temp[12] = "一级存入";
					}else if("4".equals(temp[12])){
						temp[12] = "二级存入";
					}
				}
				if(null != temp[13] && !"".equals(temp[13])){
					if("02".equals(temp[13])){
						temp[13] = "押金账户";
					}else if("03".equals(temp[13])){
						temp[13] = "佣金账户";
					}else if("04".equals(temp[13])){
						temp[13] = "冻结账户";
					}
				}
				if(null != temp[16] && !"".equals(temp[16])){
					String citySql = "select sa_name from  sys_area where sa_id = "+temp[16];
					temp[16] = dbService.getString(citySql);
				}
/*				if(null != temp[17] && !"".equals(temp[17])){
					if("Q1".equals(temp[17])){
						temp[17] = "QQ号";
					}else if("1".equals(temp[17])){
						temp[17] = "移动";
					}else if("2".equals(temp[17])){
						temp[17] = "联通";
					}else if("3".equals(temp[17])){
						temp[17] = "电信";
					}else if("A1".equals(temp[17])){
						temp[17] = "汽车拍照";
					}
				}
				if(null != temp[18] && !"".equals(temp[18])){
					if("0".equals(temp[18])){
						temp[18] = "PC";
					}else if("1".equals(temp[18])){
						temp[18] = "android";
					}else if("2".equals(temp[18])){
						temp[18] = "iphone";
					}else if("3".equals(temp[18])){
						temp[18] = "对外接口模式";
					}
				}*/
			}
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
//	/**
//	 * 获取账户明细列表 
//	 * @return 账户明细列表 
//	 * @throws Exception
//	 */
//	public List listAcctBill_Excel(String tableName,AcctBillForm acctForm,PageAttribute page) throws Exception {
//		DBService dbService = new DBService();
//		try {
//			StringBuffer paraBuffer = new StringBuffer();
//			StringBuffer sql = new StringBuffer();
//			sql.append(" select a.childfacct,a.dealserial,a.tradeaccount,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s'),a.tradefee,b.name,a.explain,a.state,DATE_FORMAT(a.distime,'%Y-%m-%d %k:%i:%s'),a.accountleft,a.tradeserial,a.other_childfacct,a.pay_type,c.accttypeid,g.sa_name,e.user_ename,e.user_site_city");
//			sql.append(" from "+tableName+" a ");
//			sql.append(" LEFT JOIN wlt_acctradetype b on a.tradetype = b.code");
//			sql.append(" LEFT JOIN wlt_childfacct c on a.childfacct = c.childfacct");
//			sql.append(" LEFT JOIN wlt_facct d on c.fundacct = d.fundacct");
//			sql.append(" LEFT JOIN sys_user e on d.termid=e.user_id");
//			sql.append(" LEFT JOIN sys_loginuser f on e.user_id = f.user_id");
//			sql.append(" LEFT JOIN sys_area g on e.user_site=g.sa_id");
////			sql.append(" LEFT JOIN wlt_orderform_"+DateParser.getNowDateTable()+" h on a.tradeserial = h.tradeserial");
//			sql.append(" where  a.state=0 ");
//			if(null != acctForm.getDealserial() && !"".equals(acctForm.getDealserial())){
//				sql.append(" and a.dealserial = '"+acctForm.getDealserial()+"'");
//				paraBuffer.append("&dealserial="+acctForm.getDealserial());
//			}
//			if(null != acctForm.getStartDate() && !"".equals(acctForm.getStartDate())){
//				sql.append(" and a.tradetime >= '"+acctForm.getStartDate().replaceAll("-", "")+"000000'");
//				paraBuffer.append("&startDate="+acctForm.getStartDate());
//			}else{
//				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
//				paraBuffer.append("&startDate="+Tools.getNow2());
//			}
//			if(null != acctForm.getEndDate() && !"".equals(acctForm.getEndDate())){
//				sql.append(" and a.tradetime <= '"+acctForm.getEndDate().replaceAll("-", "")+"235959'");
//				paraBuffer.append("&endDate="+acctForm.getEndDate());
//			}else{
//				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
//				paraBuffer.append("&endDate="+Tools.getNow2());
//			}
//			if(null != acctForm.getAreacode() && !"".equals(acctForm.getAreacode())){
//				sql.append(" and e.user_site = "+acctForm.getAreacode());
//				paraBuffer.append("&areacode="+acctForm.getAreacode());
//			}
//			if(null != acctForm.getTradetype() && !"".equals(acctForm.getTradetype())){
//				sql.append(" and a.tradetype = '"+acctForm.getTradetype()+"'");
//				paraBuffer.append("&tradetype="+acctForm.getTradetype());
//			}
//			if(null != acctForm.getPay_type() && !"".equals(acctForm.getPay_type())){
//				if("0".equals(acctForm.getPay_type())){
//					sql.append(" and a.pay_type <> 1 ");
//				}else {
//					sql.append(" and a.pay_type = '"+acctForm.getPay_type()+"'");
//				}
//				paraBuffer.append("&pay_type="+acctForm.getPay_type());
//			}
//			if(null != acctForm.getState() && !"".equals(acctForm.getState())){
//				sql.append(" and a.state = '"+acctForm.getState()+"'");
//				paraBuffer.append("&state="+acctForm.getState());
//			}
//			if(null != acctForm.getAcctType() && !"".equals(acctForm.getAcctType())){
//				sql.append(" and c.accttypeid = '"+acctForm.getAcctType()+"'");
//				paraBuffer.append("&acctType="+acctForm.getAcctType());
//			}
//			if(null != acctForm.getTradefee() && !"".equals(acctForm.getTradefee())){
//				sql.append(" and a.tradefee/100 = '"+acctForm.getTradefee()+"'");
//				paraBuffer.append("&tradefee="+acctForm.getTradefee());
//			}
//			if(null != acctForm.getTradeaccount() && !"".equals(acctForm.getTradeaccount())){
//				sql.append(" and a.tradeaccount = '"+acctForm.getTradeaccount()+"'");
//				paraBuffer.append("&tradeaccount="+acctForm.getTradeaccount());
//			}
//			if(null != acctForm.getUsername() && !"".equals(acctForm.getUsername())){
//				sql.append(" and f.user_name = '"+acctForm.getUsername()+"'");
//				paraBuffer.append("&username="+acctForm.getUsername());
//			}
//			if(null != acctForm.getUserId() && !"".equals(acctForm.getUserId())){
//				sql.append(" and e.user_id = '"+acctForm.getUserId()+"'");
//				paraBuffer.append("&userId="+acctForm.getUserId());
//			}
//			if(null != acctForm.getUser_ename() && !"".equals(acctForm.getUser_ename())){
//				sql.append(" and e.user_ename = '"+acctForm.getUser_ename()+"'");
//				paraBuffer.append("&user_ename="+acctForm.getUser_ename());
//			}
//			if(null != acctForm.getUser_site_city() && !"".equals(acctForm.getUser_site_city())){
//				sql.append(" and e.user_site_city = '"+acctForm.getUser_site_city()+"'");
//				paraBuffer.append("&user_site_city="+acctForm.getUser_site_city());
//			}
//			if(null != acctForm.getTradeserial() && !"".equals(acctForm.getTradeserial())){
//				sql.append(" and a.tradeserial = '"+acctForm.getTradeserial()+"'");
//				paraBuffer.append("&tradeserial="+acctForm.getTradeserial());
//			}
//			sql.append(" order by a.tradetime desc");
////			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
//			acctForm.setParamUrl(paraBuffer.toString());
//			List list = dbService.getList(sql.toString());
//			for(Object tmp : list){
//				String[] temp = (String[])tmp;
//				if(null != temp[7] && !"".equals(temp[7])){
//					if("0".equals(temp[7])){
//						temp[7] = "正常";
//					}else if("1".equals(temp[7])){
//						temp[7] = "取消";
//					}
//				}
//				if(null != temp[12] && !"".equals(temp[12])){
//					if("1".equals(temp[12])){
//						temp[12] = "支出";
//					}else if("2".equals(temp[12])){
//						temp[12] = "存入";
//					}else if("3".equals(temp[12])){
//						temp[12] = "一级存入";
//					}else if("4".equals(temp[12])){
//						temp[12] = "二级存入";
//					}
//				}
//				if(null != temp[13] && !"".equals(temp[13])){
//					if("02".equals(temp[13])){
//						temp[13] = "押金账户";
//					}else if("03".equals(temp[13])){
//						temp[13] = "佣金账户";
//					}else if("04".equals(temp[13])){
//						temp[13] = "冻结账户";
//					}
//				}
//				if(null != temp[16] && !"".equals(temp[16])){
//					String citySql = "select sa_name from  sys_area where sa_id = "+temp[16];
//					temp[16] = dbService.getString(citySql);
//				}
//				if(null != temp[17] && !"".equals(temp[17])){
//					if("Q1".equals(temp[17])){
//						temp[17] = "QQ号";
//					}else if("1".equals(temp[17])){
//						temp[17] = "移动";
//					}else if("2".equals(temp[17])){
//						temp[17] = "联通";
//					}else if("3".equals(temp[17])){
//						temp[17] = "电信";
//					}else if("A1".equals(temp[17])){
//						temp[17] = "汽车拍照";
//					}
//				}
//				if(null != temp[18] && !"".equals(temp[18])){
//					if("0".equals(temp[18])){
//						temp[18] = "PC";
//					}else if("1".equals(temp[18])){
//						temp[18] = "android";
//					}else if("2".equals(temp[18])){
//						temp[18] = "iphone";
//					}else if("3".equals(temp[18])){
//						temp[18] = "对外接口模式";
//					}
//				}
//			}
//			return list;
//        } catch (Exception ex) {
//            throw ex;
//        } finally {
//        	if(null!=dbService)
//				dbService.close();
//        }
//	}
	
	public int countAcctBill(String tableName,AcctBillForm acctForm) throws Exception {
		if(null!=acctForm.getStartDate()&&!"".equals(acctForm.getStartDate())){
			tableName="wht_acctbill_"+(String) acctForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*)");
			sql.append(" from "+tableName+" a ");
			sql.append(" LEFT JOIN wht_acctradetype b on a.tradetype = b.code");
			sql.append(" LEFT JOIN wht_childfacct c on a.childfacct = c.childfacct");
			sql.append(" LEFT JOIN wht_facct d on c.fundacct = d.fundacct");
			sql.append(" LEFT JOIN sys_user e on d.user_no=e.user_no");
			sql.append(" LEFT JOIN sys_area g on e.user_site=g.sa_id");
//			sql.append(" LEFT JOIN wlt_orderform_"+DateParser.getNowDateTable()+" h on a.tradeserial = h.tradeserial");
			sql.append(" where  1=1");
			if(null != acctForm.getStartDate() && !"".equals(acctForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+acctForm.getStartDate().replaceAll("-", "")+"000000'");
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				acctForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			if(null != acctForm.getEndDate() && !"".equals(acctForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+acctForm.getEndDate().replaceAll("-", "")+"235959'");
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				acctForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			if(null != acctForm.getAreacode() && !"".equals(acctForm.getAreacode())){
				sql.append(" and e.user_site = "+acctForm.getAreacode());
			}
			if(null != acctForm.getTradetype() && !"".equals(acctForm.getTradetype())){
				sql.append(" and a.tradetype = "+acctForm.getTradetype());
			}
			if(null != acctForm.getPay_type() && !"".equals(acctForm.getPay_type())){
				if("0".equals(acctForm.getPay_type())){
					sql.append(" and a.pay_type <> 1 ");
				}else {
					sql.append(" and a.pay_type = '"+acctForm.getPay_type()+"'");
				}
			}
			if(null != acctForm.getState() && !"".equals(acctForm.getState())){
				sql.append(" and a.state = '"+acctForm.getState()+"'");
			}
			if(null != acctForm.getAcctType() && !"".equals(acctForm.getAcctType())){
				sql.append(" and c.accttypeid = '"+acctForm.getAcctType()+"'");
			}
			if(null != acctForm.getTradeaccount() && !"".equals(acctForm.getTradeaccount())){
				sql.append(" and e.user_login = '"+acctForm.getTradeaccount()+"'");
			}
			if(null != acctForm.getUserId() && !"".equals(acctForm.getStartDate())){
				sql.append(" and e.user_id = "+acctForm.getUserId());
			}
			if(null != acctForm.getTradeserial() && !"".equals(acctForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+acctForm.getTradeserial()+"'");
			}
			if(null != acctForm.getRoleFilter() && !"".equals(acctForm.getRoleFilter())){
				sql.append(acctForm.getRoleFilter());
			}
			if(null != acctForm.getDailishang() && !"".equals(acctForm.getDailishang())){
				sql.append(" and e.user_id="+acctForm.getDailishang());
			}
			if(null!=acctForm.getTradeaccountNumber() && !"".equals(acctForm.getTradeaccountNumber())){
				sql.append(" and a.tradeaccount='"+acctForm.getTradeaccountNumber()+"'");
			}
			int result = dbService.getInt(sql.toString());
			return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
	/**
	 * 佣金报表
	 * @param tableName
	 * @param acctForm
	 * @return
	 * @throws Exception
	 */
	public int countyjBill(String tableName,AcctBillForm acctForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*)");
			sql.append(" from "+tableName+" a,wlt_acctradetype b,wlt_childfacct c,wlt_facct d,sys_user e,sys_loginuser f,sys_area g");
			sql.append(" where a.tradetype = b.code and a.childfacct = c.childfacct and c.fundacct = d.fundacct and d.termid=e.user_id and e.user_id = f.user_id and e.user_site=g.sa_id ");
			if(null != acctForm.getDealserial() && !"".equals(acctForm.getDealserial())){
				sql.append(" and a.dealserial = '"+acctForm.getDealserial()+"'");
			}
			if(null != acctForm.getStartDate() && !"".equals(acctForm.getStartDate())){
				sql.append(" and a.tradetime > '"+acctForm.getStartDate().replaceAll("-", "")+"000000'");
			}
			if(null != acctForm.getEndDate() && !"".equals(acctForm.getEndDate())){
				sql.append(" and a.tradetime < '"+acctForm.getEndDate().replaceAll("-", "")+"235959'");
			}
			if(null != acctForm.getAreacode() && !"".equals(acctForm.getAreacode())){
				sql.append(" and e.user_site = "+acctForm.getAreacode());
			}
			if(null != acctForm.getTradetype() && !"".equals(acctForm.getTradetype())){
				sql.append(" and a.tradetype = '"+acctForm.getTradetype()+"'");
			}
			if(null != acctForm.getPay_type() && !"".equals(acctForm.getPay_type())){
				if("0".equals(acctForm.getPay_type())){
					sql.append(" and a.pay_type <> 1 ");
				}else {
					sql.append(" and a.pay_type = '"+acctForm.getPay_type()+"'");
				}
				sql.append("&pay_type="+acctForm.getPay_type());
			}
//			if(null != acctForm.getPay_type() && !"".equals(acctForm.getPay_type())){
//				sql.append(" and a.pay_type = '"+acctForm.getPay_type()+"'");
//			}
			if(1==1){
				sql.append(" and a.state = 0 ");
			}
			if(null != acctForm.getAcctType() && !"".equals(acctForm.getAcctType())){
				sql.append(" and c.accttypeid = '"+acctForm.getAcctType()+"'");
			}
			if(null != acctForm.getTradefee() && !"".equals(acctForm.getTradefee())){
				sql.append(" and a.tradefee = '"+acctForm.getTradefee()+"'");
			}
			if(null != acctForm.getTradeaccount() && !"".equals(acctForm.getTradeaccount())){
				sql.append(" and a.tradeaccount = '"+acctForm.getTradeaccount()+"'");
			}
			if(null != acctForm.getUsername() && !"".equals(acctForm.getUsername())){
				sql.append(" and f.user_name = '"+acctForm.getUsername()+"'");
			}
			if(null != acctForm.getUserId() && !"".equals(acctForm.getUserId())){
				sql.append(" and e.user_id = '"+acctForm.getUserId()+"'");
			}
			if(null != acctForm.getUser_ename() && !"".equals(acctForm.getUser_ename())){
				sql.append(" and e.user_ename = '"+acctForm.getUser_ename()+"'");
			}
			if(null != acctForm.getUser_site_city() && !"".equals(acctForm.getUser_site_city())){
				sql.append(" and e.user_site_city = '"+acctForm.getUser_site_city()+"'");
			}
			if(null != acctForm.getTradeserial() && !"".equals(acctForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+acctForm.getTradeserial()+"'");
			}
			int result = dbService.getInt(sql.toString());
			return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
	/**
	 * 业务类型列表
	 * @param  
	 * @return 
	 * @throws Exception
	 */
	public List listSevice() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("select code ,name from wlt_service");
			return dbService.getList(sql3.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	/**
	 * 交易类型列表
	 * @param  
	 * @return 
	 * @throws Exception
	 */
	public List listTrade() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("select code ,name from wht_acctradetype ");
			return dbService.getList(sql3.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
	
	/**
	 * 佣金交易类型列表
	 * @param  
	 * @return 
	 * @throws Exception
	 */
	public List listyjTrade() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("SELECT CODE ,NAME FROM wlt_acctradetype WHERE CODE IN('41','43','44','46','47','48','50','51','52')");
			return dbService.getList(sql3.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	/**
	 * 获取区域列表 
	 * @return 区域列表 
	 * @throws Exception
	 */
	public List listArea() throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select sa_id,sa_name ")
			.append(" from sys_area")
			.append(" where sa_pid = 1");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	/**
	 * 获取区域列表 
	 * @return 区域列表 
	 * @throws Exception
	 */
	public List listArea(String pid) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select sa_id,sa_name ")
			.append(" from sys_area")
			.append(" where sa_pid = "+pid);
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
	
	/**
	 * 获取当前用户所属省份
	 * @return 区域列表 
	 * @throws Exception
	 */
	public List listProvince(String pid) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select sa_id,sa_name ")
			.append(" from sys_area")
			.append(" where sa_id = "+pid);
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	/**
	 * 获取用户姓名
	 * @return 
	 * @throws Exception
	 */
	public String getUserNameByTradeaccount(String tradeaccount) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select c.user_ename ")
			.append(" from wlt_childfacct a left join wlt_facct b on a.fundacct = b.fundacct left join sys_user c on b.termid = c.user_id")
			.append(" where a.childfacct = "+tradeaccount);
			return dbService.getString(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
	/**
	 * @return 
	 * @throws Exception
	 */
	public int countUnionUser(String tableName,AcctBillForm acctForm,PageAttribute page) throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*)");
			sql.append(" from "+tableName+" a ");
			sql.append(" where  1=1 ");
			if(null != acctForm.getStartDate() && !"".equals(acctForm.getStartDate())){
				sql.append(" and a.submit >= '"+acctForm.getStartDate()+"'");
				paraBuffer.append("&startDate="+acctForm.getStartDate());
			}else{
				sql.append(" and a.submit >= '"+Tools.getNow4()+"'");
				acctForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != acctForm.getEndDate() && !"".equals(acctForm.getEndDate())){
				sql.append(" and a.submit <= '"+acctForm.getEndDate()+"'");
				paraBuffer.append("&endDate="+acctForm.getEndDate());
			}else{
				sql.append(" and a.submit <= '"+Tools.getNow4()+"'");
				acctForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != acctForm.getState() && !"".equals(acctForm.getState())){
				sql.append(" and a.isbanding = '"+acctForm.getState()+"'");
				paraBuffer.append("&state="+acctForm.getState());
			}
			int result = dbService.getInt(sql.toString());
			return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
	/**
	 * 判断传进来的id是否是管理员或超级管理员
	 * @param user_pt
	 * @return boolean 
	 * @throws Exception 
	 */
	public static  boolean getStatus(String user_pt) throws Exception
	{
		DBService db = null;
		String sql="SELECT 1  FROM sys_user u,sys_role r WHERE u.user_role=r.sr_id AND r.sr_type in (0,1) AND u.user_id='"+user_pt+"'";
		try {
			db = new DBService();
			return db.getInt(sql)>0;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(null!=db){
				db.close();
			}
			
		}
	}
	/**
	 * 获取奖励明细
	 * @param tableName
	 * @param acctForm
	 * @param page
	 * @return 
	 * @throws Exception
	 */
public List listUnionUser(String tableName,AcctBillForm acctForm,PageAttribute page) throws Exception {
	DBService dbService = null;
	try {
		 dbService = new DBService();
		StringBuffer paraBuffer = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.userno,a.username,a.credentialType,b.identityname,a.credentialNum,a.bankID,d.unionname,a.bankflag,c.cardname,a.banknum,a.isbanding,a.submit");
		sql.append(" FROM wht_netpay a,wht_identitytype b,wht_uniontype d,wht_cardtype c");
		if(null != acctForm.getStartDate() && !"".equals(acctForm.getStartDate())){
			sql.append(" and a.submit >= '"+acctForm.getStartDate().replaceAll("-", "")+"000000'");
			paraBuffer.append("&startDate="+acctForm.getStartDate());
		}else{
			sql.append(" and a.submit >= '"+Tools.getNow4()+" '");
			acctForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			paraBuffer.append("&startDate="+Tools.getNow2());
		}
		if(null != acctForm.getEndDate() && !"".equals(acctForm.getEndDate())){
			sql.append(" and a.submit <= '"+acctForm.getEndDate()+"'");
			paraBuffer.append("&endDate="+acctForm.getEndDate());
		}else{
			sql.append(" and a.submit <= '"+Tools.getNow4()+"'");
			acctForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			paraBuffer.append("&endDate="+Tools.getNow2());
		}
		if(null != acctForm.getState() && !"".equals(acctForm.getState())){
			sql.append(" and a.isbanding = '"+acctForm.getState()+"'");
			paraBuffer.append("&state="+acctForm.getState());
		}
		sql.append(" order by a.submit desc");
		//增加excel导出
		if(!acctForm.isExcel())
		{
		sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
		}
		acctForm.setParamUrl(paraBuffer.toString());
		List list = dbService.getList(sql.toString());
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			if(null != temp[10] && !"".equals(temp[10])){
				if("0".equals(temp[10])){
					temp[10] = "未验证";
				}else if("1".equals(temp[10])){
					temp[10] = "已验证";
				}
			}
		}
		return list;
    } catch (Exception ex) {
        throw ex;
    } finally {
    	if(null!=dbService)
    		dbService.close();
    }
}
	/**
	 * 获得下级代理商
	 * @return
	 * @throws Exception 
	 * @throws Exception 
	 */
	public List getDaiLiUser(String user_id) throws Exception {
		DBService db = null;
        try {
        	db=new DBService(Constants.DBNAME_SCP);
            StringBuffer sql = new StringBuffer();
            sql.append("select user_id from sys_user where user_pt="+user_id);
            return db.getStringList(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=db)
        		db.close();
        }
	}
}

