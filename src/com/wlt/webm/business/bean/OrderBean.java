package com.wlt.webm.business.bean;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.form.BiProdForm;
import com.wlt.webm.business.form.JtfkForm;
import com.wlt.webm.business.form.OrderForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.Constants;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.scputil.TenpayUtil;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.util.PageAttribute;
import com.wlt.webm.util.Tools;


/**
 * 订单管理
 */
public class OrderBean
{
	
	/**
	 * 添加订单信息
	 * @param ordForm 
	 * @return 记录数
	 * @throws Exception
	 */
	public int add(OrderForm ordForm) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("insert into "+ordForm.getTableName()+"(areacode,tradeserial,tradeobject,buyid,service,fee,fundacct,tradetime,chgtime,state,writeoff,writecheck,term_type,userid,accountleft,user_name,phone_type) values(");
			sql3.append("'"+ordForm.getAreacode()+"',");
			sql3.append("'"+ordForm.getTradeserial()+"',");
			sql3.append("'"+ordForm.getTradeobject()+"',");
			sql3.append("'"+ordForm.getBuyid()+"',");
			sql3.append("'"+ordForm.getService()+"',");
			sql3.append("'"+ordForm.getFee()+"',");
			sql3.append("'"+ordForm.getFundacct()+"',");
			sql3.append("'"+ordForm.getTradetime()+"',");
			sql3.append("'"+ordForm.getChgtime()+"',");
			sql3.append("'"+ordForm.getState()+"',");
			sql3.append("'"+ordForm.getWriteoff()+"',");
			sql3.append("'"+ordForm.getWritecheck()+"',");
			sql3.append("'"+ordForm.getTerm_type()+"',");
			sql3.append("'"+ordForm.getUserId()+"',");
			sql3.append("'"+ordForm.getAccountleft()+"',");
			sql3.append("'"+ordForm.getUsername()+"',");
			sql3.append("'"+ordForm.getPhone_type()+"'");
			sql3.append(")");
			return dbService.update(sql3.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=null)
        		dbService.close();
        }
	}
	/**
	 * 获取id
	 * @param ordForm 
	 * @return 记录数
	 * @throws Exception
	 */
	public OrderForm getOrderId(OrderForm ordForm) throws Exception {
		DBService db = new DBService(Constants.DBNAME_SCP);
        BiProdForm prodForm = new BiProdForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT MAX(id)+1 from "+ordForm.getTableName());

            String[] params = {  };
            String[] fields = { "id"};
            
            db.populate(ordForm, fields, sql.toString(), params);

            return ordForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(db!=null)
        		db.close();
        }
	}
	/**
	 * 获取交易总额
	 * @param ordForm 
	 * @return 交易总额
	 * @throws Exception
	 */
	public int getTotalFee(String userId) throws Exception {
		DBService db = null;
        try {
        	db=new DBService(Constants.DBNAME_SCP);
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT sum(tradefee) from wht_orderform_"+DateParser.getNowDateTable()+" where userno='"+userId+"'");

            return db.getInt(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(db!=null)
        		db.close();
        }
	}
    /**
     * 获取下级交易总额,总面额,总笔数
     * @param tableName
     * @param ordForm
     * @param userId
     * @return
     * @throws Exception
     */
	public String[] getuserTotalFee(String tableName,OrderForm ordForm,String userId) throws Exception {
		DBService db = null;
		if(null!=ordForm.getStartDate()&&!"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+(String) ordForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		String[] str=null;
        try {
        	db = new DBService(Constants.DBNAME_SCP);
            StringBuffer sql = new StringBuffer();
            sql.append("SELECT sum(tradefee),sum(fee),count(*) from "+tableName+" a,sys_user b where a.userno=b.user_no and b.user_pt in ("+userId+")");
//			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
//				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
//			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and b.user_login = '"+ordForm.getUsername()+"'");
			}
			sql.append(" order by a.tradetime desc");
            str=db.getStringArray(sql.toString());
            if(null==str){
            	str=new String[]{"0","0","0"};
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=db)
        		db.close();
        }
        return str;
	}
	
	/**
	 * 获取订单列表 
	 * @return 订单列表 
	 * @throws Exception
	 */
	public List listUserOrder(String tableName,OrderForm ordForm,PageAttribute page,String userpt) throws Exception {
		if(null!=ordForm.getStartDate()&&!"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+(String) ordForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select b.sa_name,a.tradeserial,a.tradeobject,a.buyid,f.name,a.fee,a.tradefee,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s'),DATE_FORMAT(a.chgtime,'%Y-%m-%d %k:%i:%s'),a.state,a.phone_type,c.user_login,e.sr_typename,a.service,g.name,f.name,a.phoneleft ");
			sql.append(" from "+tableName+" a left join sys_area b on a.phone_pid = b.sa_id " +
					" left join sys_user c on a.userno = c.user_no" +
					" left join sys_role d on c.user_role = d.sr_id" +
					" left join sys_roletype e on d.sr_type=e.sr_type" +
					" left join wht_service f on a.service = f.code" +
					" left join sys_interface g on a.buyid=g.id");
			sql.append(" where c.user_pt in ( ").append(userpt+" )");
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
				paraBuffer.append("&tradeobject="+ordForm.getTradeobject());
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
				paraBuffer.append("&state="+ordForm.getState());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != ordForm.getUserId() && !"".equals(ordForm.getUserId())){
				sql.append(" and a.userno = "+ordForm.getUserId());
				paraBuffer.append("&userId="+ordForm.getUserId());
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and c.user_login = '"+ordForm.getUsername()+"'");
				paraBuffer.append("&username="+ordForm.getUsername());
			}
			if(null != ordForm.getPhone_type() && !"".equals(ordForm.getPhone_type())){
				sql.append(" and a.phone_type = "+ordForm.getPhone_type());
				paraBuffer.append("&phone_type="+ordForm.getPhone_type());
			}
			if(null != ordForm.getFee() && !"".equals(ordForm.getFee())){
				double mon = FloatArith.yun2li(ordForm.getFee());
				int fee=(int)mon;
				sql.append(" and a.fee="+fee);
				paraBuffer.append("&fee="+ordForm.getFee());
			}
//			if(null != ordForm.getDailishang() && !"".equals(ordForm.getDailishang())){
//				sql.append(" and c.user_id="+ordForm.getDailishang());
//				paraBuffer.append("&dailishang="+ordForm.getDailishang());
//			}
			sql.append(" order by a.tradetime desc");
			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			List list = dbService.getList(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[10] && !"".equals(temp[10])){
					if("0".equals(temp[10])){
						temp[10] = "电信";
					}else if("1".equals(temp[10])){
						temp[10] = "移动";
					}else if("2".equals(temp[10])){
						temp[10] = "联通";
					}else if("3".equals(temp[10])){
						temp[10] = "Q币充值";
					}
				}
				if(null != temp[9] && !"".equals(temp[9])){
					if("0".equals(temp[9])){
						temp[9] = "成功";
					}else if("1".equals(temp[9])){
						temp[9] = "失败";
					}else if("4".equals(temp[9])){
						temp[9] = "处理中";
					}else if("5".equals(temp[9])){
						temp[9] = "冲正";
					}else if("6".equals(temp[9])){
						temp[9] = "异常订单";
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
	 * 扣款总额统计 
	 * @return  
	 * @throws Exception
	 */
	public List listout(String tableName,OrderForm ordForm) throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT  b.name,c.name,a.state,a.total,a.totalmoney,a.facctmoney,DATE_FORMAT(a.date,'%Y-%m-%d')");
			sql.append(" from "+tableName+" a ,wht_service b,sys_interface c ");
			sql.append(" where a.code=b.code AND a.buyid=c.id ");
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.code = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getBuyid() && !"".equals(ordForm.getBuyid())){
				sql.append(" and a.buyid = '"+ordForm.getBuyid()+"'");
				paraBuffer.append("&buyid="+ordForm.getBuyid());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.date >= '"+ordForm.getStartDate().replaceAll("-", "")+"'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.date >= '"+TenpayUtil.getYesterdayTime()+"'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+TenpayUtil.getYesterday());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.date <= '"+ordForm.getEndDate().replaceAll("-", "")+"'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.date <= '"+Tools.getNow4()+"'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			sql.append(" GROUP BY a.code,a.buyid,a.state ORDER BY a.buyid");
			List list = dbService.getList(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[2] && !"".equals(temp[2])){
					if("0".equals(temp[2])){
						temp[2] = "成功";
					}else if("1".equals(temp[2])){
						temp[2] = "失败";
					}else if("4".equals(temp[2])){
						temp[2] = "处理中";
					}else if("5".equals(temp[2])){
						temp[2] = "冲正";
					}else if("6".equals(temp[2])){
						temp[2] = "异常订单";
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
	 * 统计扣款总额
	 * @param tableName
	 * @param ordForm
	 * @return
	 * @throws Exception
	 */
	public String sumcount(String tableName,OrderForm ordForm) throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			StringBuffer sql1 = new StringBuffer();
			sql.append("SELECT a.state,SUM(a.total),SUM(a.totalmoney),SUM(a.facctmoney)");
			sql.append(" from "+tableName+" a ");
			sql.append(" where 1=1 ");
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.code = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.date >= '"+ordForm.getStartDate().replaceAll("-", "")+"'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.date >= '"+TenpayUtil.getYesterdayTime()+"'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+TenpayUtil.getYesterday());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.date <= '"+ordForm.getEndDate().replaceAll("-", "")+"'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.date <= '"+Tools.getNow4()+"'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			sql.append(" group by a.state");
			List list = dbService.getList(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[0] && !"".equals(temp[0])){
					if("0".equals(temp[0])){
						sql1.append(" 成功:"+temp[1]+"笔").append(" 交易金额:"+temp[3]);
					}else if("1".equals(temp[0])){
						sql1.append(" 失败:"+temp[1]+"笔").append(" 交易金额:"+temp[3]);
					}else if("4".equals(temp[1])){
						sql1.append(" 处理中:"+temp[1]+"笔").append(" 交易金额:"+temp[3]);
					}else if("5".equals(temp[0])){
						sql1.append(" 冲正:"+temp[1]+"笔").append(" 交易金额:"+temp[3]);
					}else if("6".equals(temp[0])){
						sql1.append(" 异常订单:"+temp[1]+"笔").append(" 交易金额:"+temp[3]);
					}
				}
			}
			
			return sql1.toString();
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
	/**
	 * 加款总额统计
	 * @param tableName
	 * @param ordForm
	 * @return
	 * @throws Exception
	 */
	public List listin(String tableName,OrderForm ordForm) throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT  b.name,sum(a.total),sum(a.totalmoney),DATE_FORMAT(a.date,'%Y-%m-%d')");
			sql.append(" from "+tableName+" a ,wht_acctradetype b ");
			sql.append(" where a.code=b.code ");
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.code = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.date >= '"+ordForm.getStartDate().replaceAll("-", "")+"'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.date >= '"+TenpayUtil.getYesterdayTime()+"'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+TenpayUtil.getYesterday());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.date <= '"+ordForm.getEndDate().replaceAll("-", "")+"'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.date <= '"+Tools.getNow4()+"'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			sql.append(" GROUP BY a.code ORDER BY a.code,a.date");
			List list = dbService.getList(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
	/**
	 * 统计加款总额
	 * @return 订单列表 
	 * @throws Exception
	 */
	public String[] sumincount(String tableName,OrderForm ordForm) throws Exception {
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT SUM(a.total),SUM(a.totalmoney) ");
			sql.append(" from "+tableName+" a ");
			sql.append(" where 1=1 ");
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.code = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.date >= '"+ordForm.getStartDate().replaceAll("-", "")+"'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.date >= '"+TenpayUtil.getYesterdayTime()+"'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+TenpayUtil.getYesterday());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.date <= '"+ordForm.getEndDate().replaceAll("-", "")+"'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.date <= '"+Tools.getNow4()+"'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			String[]  list = dbService.getStringArray(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
	
	/**
	 * 获取订单列表 
	 * @return 订单列表 
	 * @throws Exception
	 */
	public List listOrder(String tableName,OrderForm ordForm,PageAttribute page) throws Exception {
		if(null!=ordForm.getStartDate()&&!"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+(String) ordForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select b.sa_name,a.tradeserial,a.tradeobject,a.buyid,f.name,a.fee,a.tradefee,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s'),DATE_FORMAT(a.chgtime,'%Y-%m-%d %k:%i:%s'),a.state,a.phone_type,c.user_login,e.sr_typename,a.service,g.name,f.name,a.phoneleft,a.writeoff ");
			sql.append(" from "+tableName+" a left join sys_area b on a.phone_pid = b.sa_id " +
					" left join sys_user c on a.userno = c.user_no" +
					" left join sys_role d on c.user_role = d.sr_id" +
					" left join sys_roletype e on d.sr_type=e.sr_type" +
					" left join wht_service f on a.service = f.code" +
					" left join sys_interface g on a.buyid=g.id");
			sql.append(" where 1=1 ");
			if(null != ordForm.getAreacode() && !"".equals(ordForm.getAreacode())){
				sql.append(" and a.phone_pid = "+ordForm.getAreacode());
				paraBuffer.append("&areacode="+ordForm.getAreacode());
			}
			if(null != ordForm.getTradeserial() && !"".equals(ordForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+ordForm.getTradeserial()+"'");
				paraBuffer.append("&tradeserial="+ordForm.getTradeserial());
			}
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
				paraBuffer.append("&tradeobject="+ordForm.getTradeobject());
			}
			if(null != ordForm.getBuyid() && !"".equals(ordForm.getBuyid())){
				sql.append(" and a.buyid = '"+ordForm.getBuyid()+"'");
				paraBuffer.append("&buyid="+ordForm.getBuyid());
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
				paraBuffer.append("&state="+ordForm.getState());
			}
			if(null != ordForm.getTerm_type() && !"".equals(ordForm.getTerm_type())){
				sql.append(" and a.term_type = "+ordForm.getTerm_type());
				paraBuffer.append("&term_type="+ordForm.getTerm_type());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != ordForm.getUserId() && !"".equals(ordForm.getUserId())){
				sql.append(" and a.userno = "+ordForm.getUserId());
				paraBuffer.append("&userId="+ordForm.getUserId());
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and c.user_login = '"+ordForm.getUsername()+"'");
				paraBuffer.append("&username="+ordForm.getUsername());
			}
			if(null != ordForm.getRoletype() && !"".equals(ordForm.getRoletype())){
				sql.append(" and d.sr_type = "+ordForm.getRoletype());
				paraBuffer.append("&roletype="+ordForm.getRoletype());
			}
			if(null != ordForm.getLogaccount() && !"".equals(ordForm.getLogaccount())){
				sql.append(" and a.user_name = '"+ordForm.getLogaccount()+"'");
				paraBuffer.append("&logaccount="+ordForm.getLogaccount());
			}
			if(null != ordForm.getPhone_type() && !"".equals(ordForm.getPhone_type())){
				sql.append(" and a.phone_type = "+ordForm.getPhone_type());
				paraBuffer.append("&phone_type="+ordForm.getPhone_type());
			}
			if(null != ordForm.getFilterinfo() && !"".equals(ordForm.getFilterinfo())){
				sql.append(" and a.buyid in ("+ordForm.getFilterinfo()+")");
				paraBuffer.append("&filterinfo="+ordForm.getFilterinfo());
			}
			if(null != ordForm.getRoleFilter() && !"".equals(ordForm.getRoleFilter())){
				sql.append(" and a.userno in ("+ordForm.getRoleFilter()+")");
				paraBuffer.append("&roleFilter="+ordForm.getRoleFilter());
			}
			if(null != ordForm.getFee() && !"".equals(ordForm.getFee())){
				double mon = FloatArith.yun2li(ordForm.getFee());
				int fee=(int)mon;
				sql.append(" and a.fee="+fee);
				paraBuffer.append("&fee="+ordForm.getFee());
			}
			sql.append(" order by a.tradetime desc");
			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			List list = dbService.getList(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[10] && !"".equals(temp[10])){
					if("0".equals(temp[10])){
						temp[10] = "电信";
					}else if("1".equals(temp[10])){
						temp[10] = "移动";
					}else if("2".equals(temp[10])){
						temp[10] = "联通";
					}else if("3".equals(temp[10])){
						temp[10] = "Q币充值";
					}
				}
				if(null != temp[9] && !"".equals(temp[9])){
					if("0".equals(temp[9])){
						temp[9] = "成功";
					}else if("1".equals(temp[9])){
						temp[9] = "失败";
					}else if("4".equals(temp[9])){
						temp[9] = "处理中";
					}else if("5".equals(temp[9])){
						temp[9] = "冲正";
					}else if("6".equals(temp[9])){
						temp[9] = "异常订单";
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
		 * 获取订单总量  
		 * @param tableName
		 * @param ordForm
		 * @param page
		 * @return
		 * @throws Exception
		 */
	public String[] orderCount(String tableName,OrderForm ordForm) {
		if(null!=ordForm.getStartDate()&&!"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+(String) ordForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select sum(a.fee),sum(a.tradefee),count(*) ");
			sql.append(" from "+tableName+" a left join sys_area b on a.phone_pid = b.sa_id " +
					" left join sys_user c on a.userno = c.user_no" +
					" left join sys_role d on c.user_role = d.sr_id" +
					" left join sys_roletype e on d.sr_type=e.sr_type" +
					" left join wht_service f on a.service = f.code" +
					" left join sys_interface g on a.buyid=g.id");
			sql.append(" where 1=1 ");
			if(null != ordForm.getAreacode() && !"".equals(ordForm.getAreacode())){
				sql.append(" and a.phone_pid = "+ordForm.getAreacode());
				paraBuffer.append("&areacode="+ordForm.getAreacode());
			}
			if(null != ordForm.getTradeserial() && !"".equals(ordForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+ordForm.getTradeserial()+"'");
				paraBuffer.append("&tradeserial="+ordForm.getTradeserial());
			}
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
				paraBuffer.append("&tradeobject="+ordForm.getTradeobject());
			}
			if(null != ordForm.getBuyid() && !"".equals(ordForm.getBuyid())){
				sql.append(" and a.buyid = '"+ordForm.getBuyid()+"'");
				paraBuffer.append("&buyid="+ordForm.getBuyid());
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
				paraBuffer.append("&state="+ordForm.getState());
			}
			if(null != ordForm.getTerm_type() && !"".equals(ordForm.getTerm_type())){
				sql.append(" and a.term_type = "+ordForm.getTerm_type());
				paraBuffer.append("&term_type="+ordForm.getTerm_type());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != ordForm.getUserId() && !"".equals(ordForm.getUserId())){
				sql.append(" and a.userno = "+ordForm.getUserId());
				paraBuffer.append("&userId="+ordForm.getUserId());
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and c.user_login = '"+ordForm.getUsername()+"'");
				paraBuffer.append("&username="+ordForm.getUsername());
			}
			if(null != ordForm.getRoletype() && !"".equals(ordForm.getRoletype())){
				sql.append(" and d.sr_type = "+ordForm.getRoletype());
				paraBuffer.append("&roletype="+ordForm.getRoletype());
			}
			if(null != ordForm.getLogaccount() && !"".equals(ordForm.getLogaccount())){
				sql.append(" and a.user_name = '"+ordForm.getLogaccount()+"'");
				paraBuffer.append("&logaccount="+ordForm.getLogaccount());
			}
			if(null != ordForm.getPhone_type() && !"".equals(ordForm.getPhone_type())){
				sql.append(" and a.phone_type = "+ordForm.getPhone_type());
				paraBuffer.append("&phone_type="+ordForm.getPhone_type());
			}
			if(null != ordForm.getFilterinfo() && !"".equals(ordForm.getFilterinfo())){
				sql.append(" and a.buyid in ("+ordForm.getFilterinfo()+")");
				paraBuffer.append("&filterinfo="+ordForm.getFilterinfo());
			}
			if(null != ordForm.getRoleFilter() && !"".equals(ordForm.getRoleFilter())){
				sql.append(" and a.userno in ("+ordForm.getRoleFilter()+")");
				paraBuffer.append("&roleFilter="+ordForm.getRoleFilter());
			}
			if(null != ordForm.getFee() && !"".equals(ordForm.getFee())){
				double mon = FloatArith.yun2li(ordForm.getFee());
				int fee=(int)mon;
				sql.append(" and a.fee="+fee);
				paraBuffer.append("&fee="+ordForm.getFee());
			}
			String[] str = dbService.getStringArray(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			if(null==str[0]||"".equals(str[0])){
				str=new String[]{"0","0","0"};
			}
			return str;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new String[]{"0","0","0"};
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	
	/**
	 * 获取订单列表 
	 * @return 订单列表 
	 * @throws Exception
	 */
	public List listOrder_Excel(String tableName,OrderForm ordForm,PageAttribute page) throws Exception {
		if(null!=ordForm.getStartDate()&&!"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+(String) ordForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService=null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select b.sa_name,a.tradeserial,a.tradeobject,a.buyid,f.name,a.fee,a.tradefee,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s'),DATE_FORMAT(a.chgtime,'%Y-%m-%d %k:%i:%s'),a.state,a.term_type,c.user_login,e.sr_typename,a.service,g.name,f.name,a.phoneleft ");
			sql.append(" from "+tableName+" a left join sys_area b on a.phone_pid = b.sa_id " +
					" left join sys_user c on a.userno = c.user_no" +
					" left join sys_role d on c.user_role = d.sr_id" +
					" left join sys_roletype e on d.sr_type=e.sr_type" +
					" left join wht_service f on a.service = f.code" +
					" left join sys_interface g on a.buyid=g.id");
			sql.append(" where 1=1 ");
			if(null != ordForm.getAreacode() && !"".equals(ordForm.getAreacode())){
				sql.append(" and a.phone_pid = "+ordForm.getAreacode());
				paraBuffer.append("&areacode="+ordForm.getAreacode());
			}
			if(null != ordForm.getTradeserial() && !"".equals(ordForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+ordForm.getTradeserial()+"'");
				paraBuffer.append("&tradeserial="+ordForm.getTradeserial());
			}
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
				paraBuffer.append("&tradeobject="+ordForm.getTradeobject());
			}
			if(null != ordForm.getBuyid() && !"".equals(ordForm.getBuyid())){
				sql.append(" and a.buyid = '"+ordForm.getBuyid()+"'");
				paraBuffer.append("&buyid="+ordForm.getBuyid());
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
				paraBuffer.append("&state="+ordForm.getState());
			}
			if(null != ordForm.getTerm_type() && !"".equals(ordForm.getTerm_type())){
				sql.append(" and a.term_type = "+ordForm.getTerm_type());
				paraBuffer.append("&term_type="+ordForm.getTerm_type());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != ordForm.getUserId() && !"".equals(ordForm.getUserId())){
				sql.append(" and a.userno = "+ordForm.getUserId());
				paraBuffer.append("&userId="+ordForm.getUserId());
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and c.user_login = '"+ordForm.getUsername()+"'");
				paraBuffer.append("&username="+ordForm.getUsername());
			}
			if(null != ordForm.getRoletype() && !"".equals(ordForm.getRoletype())){
				sql.append(" and d.sr_type = "+ordForm.getRoletype());
				paraBuffer.append("&roletype="+ordForm.getRoletype());
			}
			if(null != ordForm.getLogaccount() && !"".equals(ordForm.getLogaccount())){
				sql.append(" and a.user_name = '"+ordForm.getLogaccount()+"'");
				paraBuffer.append("&logaccount="+ordForm.getLogaccount());
			}
			if(null != ordForm.getPhone_type() && !"".equals(ordForm.getPhone_type())){
				sql.append(" and a.phone_type = "+ordForm.getPhone_type());
				paraBuffer.append("&phone_type="+ordForm.getPhone_type());
			}
			if(null != ordForm.getFilterinfo() && !"".equals(ordForm.getFilterinfo())){
				sql.append(" and a.buyid in ("+ordForm.getFilterinfo()+")");
				paraBuffer.append("&filterinfo="+ordForm.getFilterinfo());
			}
			if(null != ordForm.getRoleFilter() && !"".equals(ordForm.getRoleFilter())){
				sql.append(" and a.userno in ("+ordForm.getRoleFilter()+")");
				paraBuffer.append("&roleFilter="+ordForm.getRoleFilter());
			}
			if(null != ordForm.getFee() && !"".equals(ordForm.getFee())){
				double mon = FloatArith.yun2li(ordForm.getFee());
				int fee=(int)mon;
				sql.append(" and a.fee="+fee);
				paraBuffer.append("&fee="+ordForm.getFee());
			}
			sql.append(" order by a.tradetime desc");
//			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			List list = dbService.getList(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
//				if(null != temp[9] && !"".equals(temp[9])){
//					if("0".equals(temp[9])){
//						temp[9] = "PC";
//					}else if("1".equals(temp[9])){
//						temp[9] = "android";
//					}else if("2".equals(temp[9])){
//						temp[9] = "iphone";
//					}else if("3".equals(temp[9])){
//						temp[9] = "对外接口模式";
//					}
//				}
				if(null != temp[9] && !"".equals(temp[9])){
					if("0".equals(temp[9])){
						temp[9] = "成功";
					}else if("1".equals(temp[9])){
						temp[9] = "失败";
					}else if("4".equals(temp[9])){
						temp[9] = "处理中";
					}else if("5".equals(temp[9])){
						temp[9] = "冲正";
					}else if("6".equals(temp[9])){
						temp[9] = "异常订单";
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
	 * 获取订单列表 
	 * @return 订单列表 
	 * @throws Exception
	 */
	public List userOrder_Excel(String tableName,OrderForm ordForm,PageAttribute page,String userpt) throws Exception {
		if(null!=ordForm.getStartDate()&&!"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+(String) ordForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService = null;
		try {
			dbService=new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select b.sa_name,a.tradeserial,a.tradeobject,a.buyid,f.name,a.fee,a.tradefee,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s'),DATE_FORMAT(a.chgtime,'%Y-%m-%d %k:%i:%s'),a.state,a.term_type,c.user_login,e.sr_typename,a.service,g.name,f.name,a.phoneleft ");
			sql.append(" from "+tableName+" a left join sys_area b on a.phone_pid = b.sa_id " +
					" left join sys_user c on a.userno = c.user_no" +
					" left join sys_role d on c.user_role = d.sr_id" +
					" left join sys_roletype e on d.sr_type=e.sr_type" +
					" left join wht_service f on a.service = f.code" +
					" left join sys_interface g on a.buyid=g.id");
			sql.append(" where c.user_pt in ( ").append(userpt+" )");
			if(null != ordForm.getAreacode() && !"".equals(ordForm.getAreacode())){
				sql.append(" and a.phone_pid = "+ordForm.getAreacode());
				paraBuffer.append("&areacode="+ordForm.getAreacode());
			}
			if(null != ordForm.getTradeserial() && !"".equals(ordForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+ordForm.getTradeserial()+"'");
				paraBuffer.append("&tradeserial="+ordForm.getTradeserial());
			}
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
				paraBuffer.append("&tradeobject="+ordForm.getTradeobject());
			}
			if(null != ordForm.getBuyid() && !"".equals(ordForm.getBuyid())){
				sql.append(" and a.buyid = '"+ordForm.getBuyid()+"'");
				paraBuffer.append("&buyid="+ordForm.getBuyid());
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
				paraBuffer.append("&state="+ordForm.getState());
			}
			if(null != ordForm.getTerm_type() && !"".equals(ordForm.getTerm_type())){
				sql.append(" and a.term_type = "+ordForm.getTerm_type());
				paraBuffer.append("&term_type="+ordForm.getTerm_type());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != ordForm.getUserId() && !"".equals(ordForm.getUserId())){
				sql.append(" and a.userno = "+ordForm.getUserId());
				paraBuffer.append("&userId="+ordForm.getUserId());
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and c.user_login = '"+ordForm.getUsername()+"'");
				paraBuffer.append("&username="+ordForm.getUsername());
			}
			if(null != ordForm.getRoletype() && !"".equals(ordForm.getRoletype())){
				sql.append(" and d.sr_type = "+ordForm.getRoletype());
				paraBuffer.append("&roletype="+ordForm.getRoletype());
			}
			if(null != ordForm.getLogaccount() && !"".equals(ordForm.getLogaccount())){
				sql.append(" and a.user_name = '"+ordForm.getLogaccount()+"'");
				paraBuffer.append("&logaccount="+ordForm.getLogaccount());
			}
			if(null != ordForm.getPhone_type() && !"".equals(ordForm.getPhone_type())){
				sql.append(" and a.phone_type = "+ordForm.getPhone_type());
				paraBuffer.append("&phone_type="+ordForm.getPhone_type());
			}
			if(null != ordForm.getFilterinfo() && !"".equals(ordForm.getFilterinfo())){
				sql.append(" and a.buyid in ("+ordForm.getFilterinfo()+")");
				paraBuffer.append("&filterinfo="+ordForm.getFilterinfo());
			}
//			if(null != ordForm.getRoleFilter() && !"".equals(ordForm.getRoleFilter())){
//				sql.append(" and a.userno in ("+ordForm.getRoleFilter()+")");
//				paraBuffer.append("&roleFilter="+ordForm.getRoleFilter());
//			}
			if(null != ordForm.getFee() && !"".equals(ordForm.getFee())){
				double mon = FloatArith.yun2li(ordForm.getFee());
				int fee=(int)mon;
				sql.append(" and a.fee="+fee);
				paraBuffer.append("&fee="+ordForm.getFee());
			}
			sql.append(" order by a.tradetime desc");
//			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			List list = dbService.getList(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
//				if(null != temp[9] && !"".equals(temp[9])){
//					if("0".equals(temp[9])){
//						temp[9] = "PC";
//					}else if("1".equals(temp[9])){
//						temp[9] = "android";
//					}else if("2".equals(temp[9])){
//						temp[9] = "iphone";
//					}else if("3".equals(temp[9])){
//						temp[9] = "对外接口模式";
//					}
//				}
				if(null != temp[9] && !"".equals(temp[9])){
					if("0".equals(temp[9])){
						temp[9] = "成功";
					}else if("1".equals(temp[9])){
						temp[9] = "失败";
					}else if("4".equals(temp[9])){
						temp[9] = "处理中";
					}else if("5".equals(temp[9])){
						temp[9] = "冲正";
					}else if("6".equals(temp[9])){
						temp[9] = "异常订单";
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
	 * 获取订单列表 
	 * @return 订单列表 
	 * @throws Exception
	 */
	public int countUserOrder(String tableName,OrderForm ordForm,String userpt) throws Exception {
		if(null!=ordForm.getStartDate()&&!"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+(String) ordForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService =null;
		try {
			dbService= new DBService();
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*)");
			sql.append(" from "+tableName+" a left join sys_area b on a.areacode = b.sa_id " +
					" left join sys_user c on a.userno = c.user_no" +
					" left join sys_role d on c.user_role = d.sr_id" +
					" left join sys_roletype e on d.sr_type=e.sr_type" +
					" left join wht_service f on a.service = f.code" +
					" left join sys_interface g on a.buyid=g.id");
			sql.append(" where c.user_pt in ( ").append(userpt+" )");
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and c.user_login = '"+ordForm.getUsername()+"'");
			}
			if(null != ordForm.getLogaccount() && !"".equals(ordForm.getLogaccount())){
				sql.append(" and a.user_name = '"+ordForm.getLogaccount()+"'");
			}
			if(null != ordForm.getPhone_type() && !"".equals(ordForm.getPhone_type())){
				sql.append(" and a.phone_type = "+ordForm.getPhone_type());
			}
			if(null != ordForm.getFee() && !"".equals(ordForm.getFee())){
				double mon = FloatArith.yun2li(ordForm.getFee());
				int fee=(int)mon;
				sql.append(" and a.fee="+fee);
			}
//			if(null != ordForm.getDailishang() && !"".equals(ordForm.getDailishang())){
//				sql.append(" and c.user_id="+ordForm.getDailishang());
//			}
			int totalCount = dbService.getInt(sql.toString());
			return totalCount;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
	/**
	 * 获取订单列表 
	 * @return 订单列表 
	 * @throws Exception
	 */
	public int countOrder(String tableName,OrderForm ordForm) throws Exception {
		if(null!=ordForm.getStartDate()&&!"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+(String) ordForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService =null;
		try {
			dbService= new DBService();
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*)");
			sql.append(" from "+tableName+" a left join sys_area b on a.phone_pid = b.sa_id " +
					" left join sys_user c on a.userno = c.user_no" +
					" left join sys_role d on c.user_role = d.sr_id" +
					" left join sys_roletype e on d.sr_type=e.sr_type" +
					" left join wht_service f on a.service = f.code" +
					" left join sys_interface g on a.buyid=g.id");
			sql.append(" where 1=1 ");
			if(null != ordForm.getAreacode() && !"".equals(ordForm.getAreacode())){
				sql.append(" and a.phone_pid = "+ordForm.getAreacode());
			}
			if(null != ordForm.getTradeserial() && !"".equals(ordForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+ordForm.getTradeserial()+"'");
			}
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
			}
			if(null != ordForm.getBuyid() && !"".equals(ordForm.getBuyid())){
				sql.append(" and a.buyid = '"+ordForm.getBuyid()+"'");
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
			}
			if(null != ordForm.getTerm_type() && !"".equals(ordForm.getTerm_type())){
				sql.append(" and a.term_type = "+ordForm.getTerm_type());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			if(null != ordForm.getUserId() && !"".equals(ordForm.getUserId())){
				sql.append(" and a.userno = "+ordForm.getUserId());
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and c.user_login = '"+ordForm.getUsername()+"'");
			}
			if(null != ordForm.getRoletype() && !"".equals(ordForm.getRoletype())){
				sql.append(" and d.sr_type = "+ordForm.getRoletype());
			}
			if(null != ordForm.getLogaccount() && !"".equals(ordForm.getLogaccount())){
				sql.append(" and a.user_name = '"+ordForm.getLogaccount()+"'");
			}
			if(null != ordForm.getPhone_type() && !"".equals(ordForm.getPhone_type())){
				sql.append(" and a.phone_type = "+ordForm.getPhone_type());
			}
			if(null != ordForm.getFilterinfo() && !"".equals(ordForm.getFilterinfo())){
				sql.append(" and a.buyid in ("+ordForm.getFilterinfo()+")");
			}
			if(null != ordForm.getRoleFilter() && !"".equals(ordForm.getRoleFilter())){
				sql.append(" and a.userno in ("+ordForm.getRoleFilter()+")");
			}
			if(null != ordForm.getFee() && !"".equals(ordForm.getFee())){
				double mon = FloatArith.yun2li(ordForm.getFee());
				int fee=(int)mon;
				sql.append(" and a.fee="+fee);
			}
			int totalCount = dbService.getInt(sql.toString());
			return totalCount;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	/**
	 * 获取订单列表 
	 * @return 订单列表 
	 * @throws Exception
	 */
	public List listOrderReverse(String tableName,OrderForm ordForm,PageAttribute page,String roleType) throws Exception {
		DBService dbService = null;
		if(null!=ordForm.getStartDate()&& !"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+ordForm.getStartDate().replaceAll("-", "").substring(2,6);
		}
		try {
			StringBuffer paraBuffer = new StringBuffer();
			dbService = new DBService();
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.id,a.tradeserial,a.tradeobject,a.tradetime,a.fee,a.service,a.state,a.userno,a.buyid");
			sql.append(" from "+tableName+" a where 1=1 ");
			if(null != ordForm.getAreacode() && !"".equals(ordForm.getAreacode())){
				sql.append(" and a.phone_pid = "+ordForm.getAreacode());
				paraBuffer.append("&areacode="+ordForm.getAreacode());
			}
			if(null != ordForm.getTradeserial() && !"".equals(ordForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+ordForm.getTradeserial()+"'");
				paraBuffer.append("&tradeserial="+ordForm.getTradeserial());
			}
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
				paraBuffer.append("&tradeobject="+ordForm.getTradeobject());
			}
			if(null != ordForm.getBuyid() && !"".equals(ordForm.getBuyid())){
				sql.append(" and a.service = '"+ordForm.getBuyid()+"'");
				paraBuffer.append("&buyid="+ordForm.getBuyid());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				paraBuffer.append("&startDate="+Tools.getNow2());
				ordForm.setStartDate(Tools.getNow2());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				paraBuffer.append("&endDate="+Tools.getNow2());
				ordForm.setEndDate(Tools.getNow2());
			}
			if(null != ordForm.getRoleFilter() && !"".equals(ordForm.getRoleFilter())){
				sql.append(" and a.userno in ("+ordForm.getRoleFilter()+")");
			}
			if (ordForm.getTradeobject()!=null&&ordForm.getTradeobject().length()>0) {
				sql.append(" order by a.tradetime desc");
			}else{
				sql.append(" and (a.state<>1 and a.state<>0) order by a.tradetime desc");
			}
			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			List list = dbService.getList(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[6] && !"".equals(temp[6])){
					if("0".equals(temp[6])){
						temp[6] = "成功";
					}else if("1".equals(temp[6])){
						temp[6] = "失败";
					}else if("4".equals(temp[6])){
						temp[6] = "处理中";
					}else if("5".equals(temp[6])){
						temp[6] = "冲正";
					}else if("6".equals(temp[6])){
						temp[6] = "异常订单";
					}
				}
				if(null != temp[5] && !"".equals(temp[5])){
					if("0001".equals(temp[5])){
						temp[5] = "电信";
					}else if("0002".equals(temp[5])){
						temp[5] = "移动";
					}else if("0003".equals(temp[5])){
						temp[5] = "联通";
					}else if("0005".equals(temp[5])){
						temp[5] = "QQ币";
					}else if("0006".equals(temp[5])){
						temp[5] = "游戏币";
					}else if("0004".equals(temp[5])){
						temp[5] = "交通罚款";
					}else if("0007".equals(temp[5])){
						temp[5] = "航空票务";
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
	 * 获取订单列表 
	 * @return 订单列表 
	 * @throws Exception
	 */
	public int countOrderReverse(String tableName,OrderForm ordForm) throws Exception {
		DBService dbService = null;
		if(null!=ordForm.getStartDate()&& !"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+ordForm.getStartDate().replaceAll("-", "").substring(2,6);
		}
		try {
			dbService = new DBService();
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(DISTINCT a.tradeserial)");
			sql.append(" from "+tableName+" a where 1=1 ");
			if(null != ordForm.getAreacode() && !"".equals(ordForm.getAreacode())){
				sql.append(" and a.phone_pid = "+ordForm.getAreacode());
			}
			if(null != ordForm.getTradeserial() && !"".equals(ordForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+ordForm.getTradeserial()+"'");
			}
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
			}
			if(null != ordForm.getBuyid() && !"".equals(ordForm.getBuyid())){
				sql.append(" and a.service = '"+ordForm.getBuyid()+"'");
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
			}
			if(null != ordForm.getRoleFilter() && !"".equals(ordForm.getRoleFilter())){
				sql.append(" and a.userno in ("+ordForm.getRoleFilter()+")");
			}
			if (ordForm.getTradeobject()!=null&&ordForm.getTradeobject().length()>0) {
				sql.append(" order by a.tradetime desc");
			}else{
				sql.append(" and a.state<>1 and a.state<>0 order by a.tradetime desc");
			}
			int totalCount = dbService.getInt(sql.toString());
			return totalCount;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
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
		DBService dbService =null;
		try {
			dbService= new DBService();
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("select code ,name from wht_service");
			return dbService.getList(sql3.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	
	/**
	 * 获取加款的交易类型
	 * @param  
	 * @return 
	 * @throws Exception
	 */
	public List listtradeSevice() throws Exception {
		DBService dbService =null;
		try {
			dbService= new DBService();
			StringBuffer sql3 = new StringBuffer();	
			sql3.append("select code ,name from wht_acctradetype " +
					"where code in(10,11,12,15,16,17,18)");
			return dbService.getList(sql3.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	
	/**
	 * 违章详情 
	 * @return 表名
	 * @throws Exception
	 */
	public JtfkForm getOrderJtfk(String ordno) throws Exception {
		DBService dbService = new DBService();
		try {
			JtfkForm jtfkForm = new JtfkForm();
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.violationid,a.viloationlocation,a.violationtime,a.totalfee,a.finefee,a.dealfee," +
			"a.latefee,a.dealtime,a.viloationdetail,a.dealflag,a.wsh ")
			.append(" from wlt_jtfklog a")
			.append(" where a.orderno = ?");
			String[] params = { ordno };
			String[] fields = { "violationId","viloationLocation","violationTime","totalFee","fineFee","dealFee","lateFee","dealTime","viloationDetail","dealFlag","wsh"};
			
			dbService.populate(jtfkForm, fields, sql.toString(), params);
			return jtfkForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	/**
	 * 获取订单详情
	 * @return 表名
	 * @throws Exception
	 */
	public OrderForm getOrderInfo(String ordid,String tableName) throws Exception {
		DBService dbService = new DBService();
		try {
			OrderForm ordForm = new OrderForm();
			StringBuffer sql = new StringBuffer();
			sql.append("select tradeserial,tradeobject,writeoff,buyid,fee from "+tableName+" where id = ?");
			String[] params = { ordid };
			String[] fields = { "tradeserial","tradeobject","writeoff","buyid","fee"};
			dbService.populate(ordForm, fields, sql.toString(), params);
			return ordForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	/**
	 * 根据流水号获取订单详情
	 * @return 表名
	 * @throws Exception
	 */
	public OrderForm getOrderInfotwo(String tradeserial,String tableName) throws Exception {
		DBService dbService = new DBService();
		try {
			OrderForm ordForm = new OrderForm();
			StringBuffer sql = new StringBuffer();
			sql.append("select tradeserial,tradeobject,writeoff,buyid,fee from "+tableName+" where tradeserial = ?");
			String[] params = { tradeserial };
			String[] fields = { "tradeserial","tradeobject","writeoff","buyid","fee"};
			dbService.populate(ordForm, fields, sql.toString(), params);
			return ordForm;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if(dbService!=null)
        		dbService.close();
		}
	}
	/**
	 * 获取账户明细信息-根据订单号
	 * @return 面额信息列表 
	 * @throws Exception
	 */
	public List listAcct(String tableName,String tradeserial) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select childfacct,tradeaccount,tradefee,pay_type,dealserial ")
			.append(" from wlt_acctbill_"+tableName)
			.append(" where tradeserial = '"+tradeserial+"'");
			List list = dbService.getList(sql.toString());
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	/**
	 * 中石化订单列表
	 * @param tableName
	 * @param ordForm
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List listZshOrder(String tableName, OrderForm ordForm,
			PageAttribute page) throws Exception {
		if(null!=ordForm.getStartDate()&&!"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+(String) ordForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select b.sa_name,a.tradeserial,a.tradeobject,a.buyid,f.name,a.fee,a.tradefee,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s'),DATE_FORMAT(a.chgtime,'%Y-%m-%d %k:%i:%s'),a.state,a.phone_type,c.user_login,e.sr_typename,a.service,g.name,f.name,h.barcodeId,a.writeoff  ");
			sql.append(" from "+tableName+" a left join sys_area b on a.phone_pid = b.sa_id " +
					" left join sys_user c on a.userno = c.user_no" +
					" left join sys_role d on c.user_role = d.sr_id" +
					" left join sys_roletype e on d.sr_type=e.sr_type" +
					" left join wht_service f on a.service = f.code" +
					" left join sys_interface g on a.buyid=g.id" +
					" left join wht_barcode_order h on (a.tradeserial=h.orderId OR a.writeoff=h.orderId) "
					);
			sql.append(" where 1=1 ");
			if(null != ordForm.getAreacode() && !"".equals(ordForm.getAreacode())){
				sql.append(" and a.phone_pid = "+ordForm.getAreacode());
				paraBuffer.append("&areacode="+ordForm.getAreacode());
			}
			if(null != ordForm.getTradeserial() && !"".equals(ordForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+ordForm.getTradeserial()+"'");
				paraBuffer.append("&tradeserial="+ordForm.getTradeserial());
			}
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
				paraBuffer.append("&tradeobject="+ordForm.getTradeobject());
			}
			if(null != ordForm.getBuyid() && !"".equals(ordForm.getBuyid())){
				sql.append(" and a.buyid = '"+ordForm.getBuyid()+"'");
				paraBuffer.append("&buyid="+ordForm.getBuyid());
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
				paraBuffer.append("&state="+ordForm.getState());
			}
			if(null != ordForm.getTerm_type() && !"".equals(ordForm.getTerm_type())){
				sql.append(" and a.term_type = "+ordForm.getTerm_type());
				paraBuffer.append("&term_type="+ordForm.getTerm_type());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != ordForm.getUserId() && !"".equals(ordForm.getUserId())){
				sql.append(" and a.userno = "+ordForm.getUserId());
				paraBuffer.append("&userId="+ordForm.getUserId());
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and c.user_login = '"+ordForm.getUsername()+"'");
				paraBuffer.append("&username="+ordForm.getUsername());
			}
			if(null != ordForm.getRoletype() && !"".equals(ordForm.getRoletype())){
				sql.append(" and d.sr_type = "+ordForm.getRoletype());
				paraBuffer.append("&roletype="+ordForm.getRoletype());
			}
			if(null != ordForm.getLogaccount() && !"".equals(ordForm.getLogaccount())){
				sql.append(" and a.user_name = '"+ordForm.getLogaccount()+"'");
				paraBuffer.append("&logaccount="+ordForm.getLogaccount());
			}
			if(null != ordForm.getPhone_type() && !"".equals(ordForm.getPhone_type())){
				sql.append(" and a.phone_type = "+ordForm.getPhone_type());
				paraBuffer.append("&phone_type="+ordForm.getPhone_type());
			}
			if(null != ordForm.getFilterinfo() && !"".equals(ordForm.getFilterinfo())){
				sql.append(" and a.buyid in ("+ordForm.getFilterinfo()+")");
				paraBuffer.append("&filterinfo="+ordForm.getFilterinfo());
			}
			if(null != ordForm.getRoleFilter() && !"".equals(ordForm.getRoleFilter())){
				sql.append(" and a.userno in ("+ordForm.getRoleFilter()+")");
				paraBuffer.append("&roleFilter="+ordForm.getRoleFilter());
			}
			if(null != ordForm.getBarcode_type() && !"".equals(ordForm.getBarcode_type())){
				sql.append(" and h.barcodeId in ("+ordForm.getBarcode_type()+")");
				paraBuffer.append("&barcode_type="+ordForm.getBarcode_type());
			}
			sql.append(" order by a.tradetime desc");
			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			List list = dbService.getList(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[10] && !"".equals(temp[10])){
					if("0".equals(temp[10])){
						temp[10] = "电信";
					}else if("1".equals(temp[10])){
						temp[10] = "移动";
					}else if("2".equals(temp[10])){
						temp[10] = "联通";
					}else if("3".equals(temp[10])){
						temp[10] = "Q币充值";
					}
				}
				if(null != temp[9] && !"".equals(temp[9])){
					if("0".equals(temp[9])){
						temp[9] = "成功";
					}else if("1".equals(temp[9])){
						temp[9] = "失败";
					}else if("4".equals(temp[9])){
						temp[9] = "处理中";
					}else if("5".equals(temp[9])){
						temp[9] = "冲正";
					}else if("6".equals(temp[9])){
						temp[9] = "异常订单";
					}
				}
			}
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	/**
	 * 中石化订单数量
	 * @param tableName
	 * @param ordForm
	 * @return
	 */
	public String[] orderZshCount(String tableName, OrderForm ordForm) {
		if(null!=ordForm.getStartDate()&&!"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+(String) ordForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select sum(a.fee),sum(a.tradefee),count(*) ");
			sql.append(" from "+tableName+" a left join sys_area b on a.phone_pid = b.sa_id " +
					" left join sys_user c on a.userno = c.user_no" +
					" left join sys_role d on c.user_role = d.sr_id" +
					" left join sys_roletype e on d.sr_type=e.sr_type" +
					" left join wht_service f on a.service = f.code" +
					" left join sys_interface g on a.buyid=g.id"+
					" left join wht_barcode_order h on (a.tradeserial=h.orderId OR a.writeoff=h.orderId) ");
			sql.append(" where 1=1 ");
			if(null != ordForm.getAreacode() && !"".equals(ordForm.getAreacode())){
				sql.append(" and a.phone_pid = "+ordForm.getAreacode());
				paraBuffer.append("&areacode="+ordForm.getAreacode());
			}
			if(null != ordForm.getTradeserial() && !"".equals(ordForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+ordForm.getTradeserial()+"'");
				paraBuffer.append("&tradeserial="+ordForm.getTradeserial());
			}
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
				paraBuffer.append("&tradeobject="+ordForm.getTradeobject());
			}
			if(null != ordForm.getBuyid() && !"".equals(ordForm.getBuyid())){
				sql.append(" and a.buyid = '"+ordForm.getBuyid()+"'");
				paraBuffer.append("&buyid="+ordForm.getBuyid());
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
				paraBuffer.append("&state="+ordForm.getState());
			}
			if(null != ordForm.getTerm_type() && !"".equals(ordForm.getTerm_type())){
				sql.append(" and a.term_type = "+ordForm.getTerm_type());
				paraBuffer.append("&term_type="+ordForm.getTerm_type());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != ordForm.getUserId() && !"".equals(ordForm.getUserId())){
				sql.append(" and a.userno = "+ordForm.getUserId());
				paraBuffer.append("&userId="+ordForm.getUserId());
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and c.user_login = '"+ordForm.getUsername()+"'");
				paraBuffer.append("&username="+ordForm.getUsername());
			}
			if(null != ordForm.getRoletype() && !"".equals(ordForm.getRoletype())){
				sql.append(" and d.sr_type = "+ordForm.getRoletype());
				paraBuffer.append("&roletype="+ordForm.getRoletype());
			}
			if(null != ordForm.getLogaccount() && !"".equals(ordForm.getLogaccount())){
				sql.append(" and a.user_name = '"+ordForm.getLogaccount()+"'");
				paraBuffer.append("&logaccount="+ordForm.getLogaccount());
			}
			if(null != ordForm.getPhone_type() && !"".equals(ordForm.getPhone_type())){
				sql.append(" and a.phone_type = "+ordForm.getPhone_type());
				paraBuffer.append("&phone_type="+ordForm.getPhone_type());
			}
			if(null != ordForm.getFilterinfo() && !"".equals(ordForm.getFilterinfo())){
				sql.append(" and a.buyid in ("+ordForm.getFilterinfo()+")");
				paraBuffer.append("&filterinfo="+ordForm.getFilterinfo());
			}
			if(null != ordForm.getRoleFilter() && !"".equals(ordForm.getRoleFilter())){
				sql.append(" and a.userno in ("+ordForm.getRoleFilter()+")");
				paraBuffer.append("&roleFilter="+ordForm.getRoleFilter());
			}
			if(null != ordForm.getBarcode_type() && !"".equals(ordForm.getBarcode_type())){
				sql.append(" and h.barcodeId in ("+ordForm.getBarcode_type()+")");
				paraBuffer.append("&barcode_type="+ordForm.getBarcode_type());
			}
			String[] str = dbService.getStringArray(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			if(null==str[0]||"".equals(str[0])){
				str=new String[]{"0","0","0"};
			}
			return str;
        } catch (Exception ex) {
            ex.printStackTrace();
            return new String[]{"0","0","0"};
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	/**
	 * @param tableName
	 * @param ordForm
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List zshListOrder_Excel(String tableName, OrderForm ordForm,
			PageAttribute page) throws Exception {
		if(null!=ordForm.getStartDate()&&!"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+(String) ordForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService = new DBService();
		try {
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select b.sa_name,a.tradeserial,a.tradeobject,a.buyid,f.name,a.fee,a.tradefee,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s'),DATE_FORMAT(a.chgtime,'%Y-%m-%d %k:%i:%s'),a.state,a.term_type,c.user_login,e.sr_typename,a.service,g.name,f.name,h.barcodeId ");
			sql.append(" from "+tableName+" a left join sys_area b on a.phone_pid = b.sa_id " +
					" left join sys_user c on a.userno = c.user_no" +
					" left join sys_role d on c.user_role = d.sr_id" +
					" left join sys_roletype e on d.sr_type=e.sr_type" +
					" left join wht_service f on a.service = f.code" +
					" left join sys_interface g on a.buyid=g.id"+
					" left join wht_barcode_order h on a.tradeserial=h.orderId");
			sql.append(" where 1=1 ");
			if(null != ordForm.getAreacode() && !"".equals(ordForm.getAreacode())){
				sql.append(" and a.phone_pid = "+ordForm.getAreacode());
				paraBuffer.append("&areacode="+ordForm.getAreacode());
			}
			if(null != ordForm.getTradeserial() && !"".equals(ordForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+ordForm.getTradeserial()+"'");
				paraBuffer.append("&tradeserial="+ordForm.getTradeserial());
			}
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
				paraBuffer.append("&tradeobject="+ordForm.getTradeobject());
			}
			if(null != ordForm.getBuyid() && !"".equals(ordForm.getBuyid())){
				sql.append(" and a.buyid = '"+ordForm.getBuyid()+"'");
				paraBuffer.append("&buyid="+ordForm.getBuyid());
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
				paraBuffer.append("&state="+ordForm.getState());
			}
			if(null != ordForm.getTerm_type() && !"".equals(ordForm.getTerm_type())){
				sql.append(" and a.term_type = "+ordForm.getTerm_type());
				paraBuffer.append("&term_type="+ordForm.getTerm_type());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != ordForm.getUserId() && !"".equals(ordForm.getUserId())){
				sql.append(" and a.userno = "+ordForm.getUserId());
				paraBuffer.append("&userId="+ordForm.getUserId());
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and c.user_login = '"+ordForm.getUsername()+"'");
				paraBuffer.append("&username="+ordForm.getUsername());
			}
			if(null != ordForm.getRoletype() && !"".equals(ordForm.getRoletype())){
				sql.append(" and d.sr_type = "+ordForm.getRoletype());
				paraBuffer.append("&roletype="+ordForm.getRoletype());
			}
			if(null != ordForm.getLogaccount() && !"".equals(ordForm.getLogaccount())){
				sql.append(" and a.user_name = '"+ordForm.getLogaccount()+"'");
				paraBuffer.append("&logaccount="+ordForm.getLogaccount());
			}
			if(null != ordForm.getPhone_type() && !"".equals(ordForm.getPhone_type())){
				sql.append(" and a.phone_type = "+ordForm.getPhone_type());
				paraBuffer.append("&phone_type="+ordForm.getPhone_type());
			}
			if(null != ordForm.getFilterinfo() && !"".equals(ordForm.getFilterinfo())){
				sql.append(" and a.buyid in ("+ordForm.getFilterinfo()+")");
				paraBuffer.append("&filterinfo="+ordForm.getFilterinfo());
			}
			if(null != ordForm.getRoleFilter() && !"".equals(ordForm.getRoleFilter())){
				sql.append(" and a.userno in ("+ordForm.getRoleFilter()+")");
				paraBuffer.append("&roleFilter="+ordForm.getRoleFilter());
			}
			if(null != ordForm.getBarcode_type() && !"".equals(ordForm.getBarcode_type())){
				sql.append(" and h.barcodeId in ("+ordForm.getBarcode_type()+")");
				paraBuffer.append("&barcode_type="+ordForm.getBarcode_type());
			}
			sql.append(" order by a.tradetime desc");
//			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			List list = dbService.getList(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
//				if(null != temp[9] && !"".equals(temp[9])){
//					if("0".equals(temp[9])){
//						temp[9] = "PC";
//					}else if("1".equals(temp[9])){
//						temp[9] = "android";
//					}else if("2".equals(temp[9])){
//						temp[9] = "iphone";
//					}else if("3".equals(temp[9])){
//						temp[9] = "对外接口模式";
//					}
//				}
				if(null != temp[9] && !"".equals(temp[9])){
					if("0".equals(temp[9])){
						temp[9] = "成功";
					}else if("1".equals(temp[9])){
						temp[9] = "失败";
					}else if("4".equals(temp[9])){
						temp[9] = "处理中";
					}else if("5".equals(temp[9])){
						temp[9] = "冲正";
					}else if("6".equals(temp[9])){
						temp[9] = "异常订单";
					}
				}
			}
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	/**
	 * 中石化下级用户交易统计数量
	 * @param tableName
	 * @param ordForm
	 * @param userpt
	 * @return count  tradefee  fee
	 * @throws Exception 
	 */
	public String[] countZshUserOrder(String tableName, OrderForm ordForm, int userpt) throws Exception {
		if(null!=ordForm.getStartDate()&&!"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+(String) ordForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" select count(*) con ,sum(a.tradefee) tradefee,sum(a.fee) fee ");
			sql.append(" from "+tableName+" a left join sys_area b on a.phone_pid = b.sa_id " +
					" left join (SELECT * FROM sys_user WHERE user_pt="+userpt+" and user_site=382) c on a.userno = c.user_no" +
					" left join sys_role d on c.user_role = d.sr_id" +
					" left join sys_roletype e on d.sr_type=e.sr_type" +
					" left join wht_service f on a.service = f.code" +
					" left join sys_interface g on a.buyid=g.id"+
					" left join wht_barcode_order h on (a.tradeserial=h.orderId OR a.writeoff=h.orderId) ");
			sql.append(" where c.user_pt="+userpt+" and c.user_site=382 ");
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and (c.user_login = '"+ordForm.getUsername()+"' or c.exp1='"+ordForm.getUsername()+"') ");
			}
//			if(null != ordForm.getLogaccount() && !"".equals(ordForm.getLogaccount())){
//				sql.append(" and a.user_name = '"+ordForm.getLogaccount()+"' or a.exp1='"+ordForm.getLogaccount());
//				//新增  or 以后  +"' or a.exp1='"+ordForm.getLogaccount()
//			}
			if(null != ordForm.getPhone_type() && !"".equals(ordForm.getPhone_type())){
				sql.append(" and a.phone_type = "+ordForm.getPhone_type());
			}
			if(null != ordForm.getBarcode_type() && !"".equals(ordForm.getBarcode_type())){
				sql.append(" and h.barcodeId in ("+ordForm.getBarcode_type()+")");
			}
//			if(null != ordForm.getRoleFilter() && !"".equals(ordForm.getRoleFilter())){
//				sql.append(" and a.userno in ("+ordForm.getRoleFilter()+")");
//			}
//			System.out.println("查询总数sql:"+sql.toString());
//			int totalCount = dbService.getInt(sql.toString());
//			return totalCount;
			return dbService.getStringArray(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	/**
	 * 中石化下级用户交易统计
	 * @param string
	 * @param orderForm
	 * @param page
	 * @param userpt
	 * @return
	 * @throws Exception 
	 */
	public List listZshUserOrder(String tableName, OrderForm ordForm,
			PageAttribute page, int userpt,int type) throws Exception {
		if(null!=ordForm.getStartDate()&&!"".equals(ordForm.getStartDate())){
			tableName="wht_orderform_"+(String) ordForm.getStartDate().replaceAll("-", "").subSequence(2, 6);
		}
		DBService dbService = null;
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select b.sa_name,a.tradeserial,a.tradeobject,a.buyid,f.name,a.fee,a.tradefee,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s'),DATE_FORMAT(a.chgtime,'%Y-%m-%d %k:%i:%s'),a.state,a.phone_type,c.user_login,e.sr_typename,a.service,g.name,f.name,h.barcodeId ");
			sql.append(" from "+tableName+" a left join sys_area b on a.phone_pid = b.sa_id " +
					" left join (SELECT * FROM sys_user WHERE user_pt="+userpt+" and user_site=382) c on a.userno = c.user_no" +
					" left join sys_role d on c.user_role = d.sr_id" +
					" left join sys_roletype e on d.sr_type=e.sr_type" +
					" left join wht_service f on a.service = f.code" +
					" left join sys_interface g on a.buyid=g.id"+
					" left join wht_barcode_order h on (a.tradeserial=h.orderId OR a.writeoff=h.orderId) ");
			sql.append(" where c.user_pt="+userpt+" and c.user_site=382 ");
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
				paraBuffer.append("&tradeobject="+ordForm.getTradeobject());
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
				paraBuffer.append("&state="+ordForm.getState());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != ordForm.getUserId() && !"".equals(ordForm.getUserId())){
				sql.append(" and a.userno = "+ordForm.getUserId());
				paraBuffer.append("&userId="+ordForm.getUserId());
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and (c.user_login ='"+ordForm.getUsername()+"' or c.exp1='"+ordForm.getUsername()+"') ");
				paraBuffer.append("&username="+ordForm.getUsername());
			}
			if(null != ordForm.getPhone_type() && !"".equals(ordForm.getPhone_type())){
				sql.append(" and a.phone_type = "+ordForm.getPhone_type());
				paraBuffer.append("&phone_type="+ordForm.getPhone_type());
			}
			if(null != ordForm.getBarcode_type() && !"".equals(ordForm.getBarcode_type())){
				sql.append(" and h.barcodeId in ("+ordForm.getBarcode_type()+")");
				paraBuffer.append("&barcode_type="+ordForm.getBarcode_type());
			}
//			if(null != ordForm.getRoleFilter() && !"".equals(ordForm.getRoleFilter())){
//				sql.append(" and a.userno in ("+ordForm.getRoleFilter()+")");
//				paraBuffer.append("&roleFilter="+ordForm.getRoleFilter());
//			}
			sql.append(" order by a.tradetime desc");
			if(type==99){
				sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			}
//			System.out.println("明细sql:"+sql.toString());
			List list = dbService.getList(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
				if(null != temp[10] && !"".equals(temp[10])){
					if("0".equals(temp[10])){
						temp[10] = "电信";
					}else if("1".equals(temp[10])){
						temp[10] = "移动";
					}else if("2".equals(temp[10])){
						temp[10] = "联通";
					}else if("3".equals(temp[10])){
						temp[10] = "Q币充值";
					}
				}
				if(null != temp[9] && !"".equals(temp[9])){
					if("0".equals(temp[9])){
						temp[9] = "成功";
					}else if("1".equals(temp[9])){
						temp[9] = "失败";
					}else if("4".equals(temp[9])){
						temp[9] = "处理中";
					}else if("5".equals(temp[9])){
						temp[9] = "冲正退款";
					}else if("6".equals(temp[9])){
						temp[9] = "异常订单";
					}
				}
			}
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	/**
	 * 中石化导出订单列表
	 * @param tableName
	 * @param ordForm
	 * @param page
	 * @param userpt
	 * @return List
	 * @throws Exception 
	 */
	public List zshuserOrder_Excel(String tableName, OrderForm ordForm,
			PageAttribute page, int userpt) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(" select b.sa_name,a.tradeserial,a.tradeobject,a.buyid,f.name,a.fee,a.tradefee,DATE_FORMAT(a.tradetime,'%Y-%m-%d %k:%i:%s'),DATE_FORMAT(a.chgtime,'%Y-%m-%d %k:%i:%s'),a.state,a.term_type,c.user_login,e.sr_typename,a.service,g.name,f.name,h.barcodeId ");
			sql.append(" from "+tableName+" a left join sys_area b on a.phone_pid = b.sa_id " +
					" left join (SELECT * FROM sys_user WHERE user_pt="+userpt+" and user_site=382) c on a.userno = c.user_no" +
					" left join sys_role d on c.user_role = d.sr_id" +
					" left join sys_roletype e on d.sr_type=e.sr_type" +
					" left join wht_service f on a.service = f.code" +
					" left join sys_interface g on a.buyid=g.id"+
					" left join wht_barcode_order h ON (a.tradeserial=h.orderId OR a.writeoff=h.orderId) ");
			sql.append(" where c.user_pt="+userpt+" and c.user_site=382 ");
			if(null != ordForm.getAreacode() && !"".equals(ordForm.getAreacode())){
				sql.append(" and a.phone_pid = "+ordForm.getAreacode());
				paraBuffer.append("&areacode="+ordForm.getAreacode());
			}
			if(null != ordForm.getTradeserial() && !"".equals(ordForm.getTradeserial())){
				sql.append(" and a.tradeserial = '"+ordForm.getTradeserial()+"'");
				paraBuffer.append("&tradeserial="+ordForm.getTradeserial());
			}
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.tradeobject = '"+ordForm.getTradeobject()+"'");
				paraBuffer.append("&tradeobject="+ordForm.getTradeobject());
			}
			if(null != ordForm.getBuyid() && !"".equals(ordForm.getBuyid())){
				sql.append(" and a.buyid = '"+ordForm.getBuyid()+"'");
				paraBuffer.append("&buyid="+ordForm.getBuyid());
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and a.service = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.state = "+ordForm.getState());
				paraBuffer.append("&state="+ordForm.getState());
			}
			if(null != ordForm.getTerm_type() && !"".equals(ordForm.getTerm_type())){
				sql.append(" and a.term_type = "+ordForm.getTerm_type());
				paraBuffer.append("&term_type="+ordForm.getTerm_type());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != ordForm.getUserId() && !"".equals(ordForm.getUserId())){
				sql.append(" and a.userno = "+ordForm.getUserId());
				paraBuffer.append("&userId="+ordForm.getUserId());
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and c.user_login = '"+ordForm.getUsername()+"'");
				paraBuffer.append("&username="+ordForm.getUsername());
			}
			if(null != ordForm.getRoletype() && !"".equals(ordForm.getRoletype())){
				sql.append(" and d.sr_type = "+ordForm.getRoletype());
				paraBuffer.append("&roletype="+ordForm.getRoletype());
			}
			if(null != ordForm.getLogaccount() && !"".equals(ordForm.getLogaccount())){
				sql.append(" and a.user_name = '"+ordForm.getLogaccount()+"'");
				paraBuffer.append("&logaccount="+ordForm.getLogaccount());
			}
			if(null != ordForm.getPhone_type() && !"".equals(ordForm.getPhone_type())){
				sql.append(" and a.phone_type = "+ordForm.getPhone_type());
				paraBuffer.append("&phone_type="+ordForm.getPhone_type());
			}
			if(null != ordForm.getFilterinfo() && !"".equals(ordForm.getFilterinfo())){
				sql.append(" and a.buyid in ("+ordForm.getFilterinfo()+")");
				paraBuffer.append("&filterinfo="+ordForm.getFilterinfo());
			}
//			if(null != ordForm.getRoleFilter() && !"".equals(ordForm.getRoleFilter())){
//				sql.append(" and a.userno in ("+ordForm.getRoleFilter()+")");
//				paraBuffer.append("&roleFilter="+ordForm.getRoleFilter());
//			}
			if(null != ordForm.getBarcode_type() && !"".equals(ordForm.getBarcode_type())){
				sql.append(" and h.barcodeId in ("+ordForm.getBarcode_type()+")");
				paraBuffer.append("&barcode_type="+ordForm.getBarcode_type());
			}
			sql.append(" order by a.tradetime desc");
//			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			List list = dbService.getList(sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			for(Object tmp : list){
				String[] temp = (String[])tmp;
//				if(null != temp[9] && !"".equals(temp[9])){
//					if("0".equals(temp[9])){
//						temp[9] = "PC";
//					}else if("1".equals(temp[9])){
//						temp[9] = "android";
//					}else if("2".equals(temp[9])){
//						temp[9] = "iphone";
//					}else if("3".equals(temp[9])){
//						temp[9] = "对外接口模式";
//					}
//				}
				if(null != temp[9] && !"".equals(temp[9])){
					if("0".equals(temp[9])){
						temp[9] = "成功";
					}else if("1".equals(temp[9])){
						temp[9] = "失败";
					}else if("4".equals(temp[9])){
						temp[9] = "处理中";
					}else if("5".equals(temp[9])){
						temp[9] = "冲正";
					}else if("6".equals(temp[9])){
						temp[9] = "异常订单";
					}
				}
			}
			return list;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(dbService!=null)
        		dbService.close();
        }
	}
	
	/**
	 * 回调内部处理
	 * @param st 订单状态 0成功 -1失败
	 * @param ptOrderNo 订单号
	 * @param buyid  内部接口id
	 */
	public static void httpReturnDeal(int st,String ptOrderNo,int buyid){
		Log.info("回调业务处理方法，订单号:"+ptOrderNo+",状态："+st+",接口id："+buyid);
		DBService db =null;
		try {
			db=new DBService();
			String sql="select userno,tradefee,tradeserial from wht_orderform_"+Tools.getNow3().substring(2,6)
			+" where buyid="+buyid+" and  state not in(0,1) and (tradeserial='"+ptOrderNo+"' or writeoff='"+ptOrderNo+"')";
			Log.info("回调业务处理方法:"+sql);
			String[] str=db.getStringArray(sql);
			if(null!=str){
				Log.info("回调业务处理方法，订单号:"+ptOrderNo+",状态："+st+",接口id："+buyid+",进入业务处理");
				String newtime=Tools.getNow3();
				String stracct0="select childfacct,dealserial,tradetype from wht_acctbill_"+newtime.substring(2,6)+
				" where tradetype!=15 and tradeserial='"+str[2]+"'";
				String[] str1=db.getStringArray(stracct0);
				String stracct1="select childfacct,dealserial,infacctfee from wht_acctbill_"+newtime.substring(2,6)+
				" where tradetype=15 and tradeserial='"+str[2]+"'";
				String[] str2=db.getStringArray(stracct1);
				String flag=(null==str2)? "1":"0";
				BiProd bp=new BiProd();
				if(flag.equals("1")){  //直营
					Log.info("回调业务处理方法，订单号:"+ptOrderNo+",状态："+st+",接口id："+buyid+",进入业务处理,直营,,");
					bp.returnDeal(newtime, st+"", str[2],
						str[0], str1[1], str1[0].substring(0, str1[0].length()-2), 
						flag,null, null,str[1], null,Integer.parseInt(str1[2]));
				}else{
					Log.info("回调业务处理方法，订单号:"+ptOrderNo+",状态："+st+",接口id："+buyid+",进入业务处理,非直营,,,");
					bp.returnDeal(newtime, st+"", str[2],
							str[0], str1[1], str1[0].substring(0, str1[0].length()-2), 
							flag,str2[1], str2[0].substring(0, str1[0].length()-2),str[1], str2[2],Integer.parseInt(str1[2]));
				}
			}else{
				Log.info("回调业务处理方法，订单号:"+ptOrderNo+",状态："+st+",接口id："+buyid+",未查询订单");
			}
		} catch (Exception e) {
			Log.error("http回调异常,订单号："+ptOrderNo+" 状态:"+st+"   "+e.toString());
			e.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
			}
		}
	
	}
	/**
	 * 获得下级代理商
	 * @return
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
        	ex.printStackTrace();
            throw ex;
        } finally {
        	if(null!=db){
				db.close();
			}
        }
	}
	 /* 下级代理商
	 * @param  
	 * @return 
	 * @throws Exception
	 */
	public List getDaiLiShangUser(String user_id) throws Exception {
		DBService db = null;
        try {
        	db=new DBService(Constants.DBNAME_SCP);
            StringBuffer sql = new StringBuffer();
            sql.append("select user_id,user_login from sys_user where user_pt="+user_id);
            return db.getList(sql.toString());
        } catch (Exception ex) {
        	ex.printStackTrace();
            throw ex;
        } finally {
        	if(null!=db){
				db.close();
			}
        }
	}

	/**
	 * 拆单统计条数
	 * @param userno 系统编号
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param tradeobject 交易号码
	 * @param oldorderid 关联号码
	 * @return int
	 */
	public static int caidanCount(String userno,String startDate,String endDate,String tradeobject,String oldorderid){
		DBService db=null;
		try{
			db=new DBService();
			StringBuffer sql=new StringBuffer("SELECT COUNT(aa.id) con FROM wht_qbbreakrecord_"+Tools.getNow3().substring(2,6)+" aa,sys_interface bb ,wht_service cc WHERE aa.buyid=bb.id AND aa.service=cc.code");
			if(userno!=null && !"".equals(userno.trim())){
				sql.append(" and aa.userno='"+userno+"'");
			}
			if(startDate!=null && !"".equals(startDate)){
				sql.append(" and aa.tradetime>='"+startDate+"'");
			}
			if(endDate!=null && !"".equals(endDate)){
				sql.append(" and aa.tradetime<='"+endDate+"'");
			}
			if(tradeobject!=null && !"".equals(tradeobject)){
				sql.append(" and aa.tradeobject='"+tradeobject+"'");
			}
			if(oldorderid!=null && !"".equals(oldorderid)){
				sql.append(" and aa.oldorderid='"+oldorderid+"'");
			}
			return db.getInt(sql.toString());
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
		return 0;
	}
	/**
	 * 拆单列表
	 * @param userno 系统编号
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param tradeobject 交易号码
	 * @param oldorderid 关联号码
	 * @param index 当前页
	 * @param pagsize 每页条数
	 * @return List
	 */
	public static List<Object[]> caidanList(String userno,String startDate,String endDate,String tradeobject,String oldorderid,int index,int pagsize){
		DBService db=null;
		try{
			db=new DBService();
			StringBuffer sql=new StringBuffer("SELECT aa.oldorderid,aa.tradeserial,aa.tradeobject,bb.NAME,cc.name,ROUND(aa.fee/1000,2),aa.tradetime,CASE WHEN aa.state=0 THEN '成功' WHEN aa.state=1 THEN '失败' WHEN aa.state=4 THEN '处理中'   WHEN aa.state=5 THEN '冲正'  WHEN aa.state=6 THEN '异常' ELSE '未知' END  AS statess,aa.userno FROM wht_qbbreakrecord_"+Tools.getNow3().substring(2,6)+" aa,sys_interface bb ,wht_service cc WHERE aa.buyid=bb.id AND aa.service=cc.code");
			if(userno!=null && !"".equals(userno.trim())){
				sql.append(" and aa.userno='"+userno+"'");
			}
			if(startDate!=null && !"".equals(startDate)){
				sql.append(" and aa.tradetime>='"+startDate+"'");
			}
			if(endDate!=null && !"".equals(endDate)){
				sql.append(" and aa.tradetime<='"+endDate+"'");
			}
			if(tradeobject!=null && !"".equals(tradeobject)){
				sql.append(" and aa.tradeobject='"+tradeobject+"'");
			}
			if(oldorderid!=null && !"".equals(oldorderid)){
				sql.append(" and aa.oldorderid='"+oldorderid+"'");
			}
			sql.append(" order by aa.id desc limit "+(index-1)*pagsize+" , "+pagsize);
			return db.getList(sql.toString());
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
		return null;
	}
	
	/**
	 * 获取流量补充订单列表
	 * @param tableName
	 * @param ordForm
	 * @param page
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> listflowOrder(String tableName,OrderForm ordForm,PageAttribute page,String flag) throws Exception {
		DBService dbService = null;
		int a=0;
		HashMap<String, Object> rs=new HashMap<String, Object>();
		try {
			dbService = new DBService();
			StringBuffer paraBuffer = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			sql.append(tableName).append(" a LEFT JOIN sys_interface b ON o_buyid=b.id where 1=1 ");
			StringBuffer sql1 = new StringBuffer();
			sql1.append("select (CASE WHEN r_dis='0' THEN '原始' WHEN r_dis='1' THEN '补充' END) AS ismore,o_tradeserial,o_tradeobject,o_buyid,")
			.append("(CASE WHEN o_service='0009' THEN '联通' WHEN o_service='0010' THEN '移动' WHEN o_service='0011' THEN '电信' END) AS teltype,o_tradetime,o_chgtime,")
			.append("(CASE WHEN o_state='0' THEN '成功' WHEN o_state='1' THEN '失败' WHEN o_state='4' THEN '处理中' END) AS state,o_writeoff,o_writecheck,b.name FROM ");
			String sql2="SELECT COUNT(o_tradeserial) from ";
			if(null != ordForm.getAreacode() && !"".equals(ordForm.getAreacode())){
				sql.append(" and a.o_city = "+ordForm.getAreacode());
				paraBuffer.append("&areacode="+ordForm.getAreacode());
			}
			if(null != ordForm.getTradeobject() && !"".equals(ordForm.getTradeobject())){
				sql.append(" and a.o_tradeobject = '"+ordForm.getTradeobject()+"'");
				paraBuffer.append("&tradeobject="+ordForm.getTradeobject());
			}
			if(null != ordForm.getBuyid() && !"".equals(ordForm.getBuyid())){
				sql.append(" and a.o_buyid = '"+ordForm.getBuyid()+"'");
				paraBuffer.append("&buyid="+ordForm.getBuyid());
			}
			if(null != ordForm.getService() && !"".equals(ordForm.getService())){
				sql.append(" and o_service = '"+ordForm.getService()+"'");
				paraBuffer.append("&service="+ordForm.getService());
			}
			if(null != ordForm.getState() && !"".equals(ordForm.getState())){
				sql.append(" and a.o_state = "+ordForm.getState());
				paraBuffer.append("&state="+ordForm.getState());
			}
			if(null != ordForm.getStartDate() && !"".equals(ordForm.getStartDate())){
				sql.append(" and a.o_tradetime >= '"+ordForm.getStartDate().replaceAll("-", "")+"000000'");
				paraBuffer.append("&startDate="+ordForm.getStartDate());
			}else{
				sql.append(" and a.o_tradetime >= '"+Tools.getNow4()+"000000'");
				ordForm.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&startDate="+Tools.getNow2());
			}
			if(null != ordForm.getEndDate() && !"".equals(ordForm.getEndDate())){
				sql.append(" and a.o_tradetime <= '"+ordForm.getEndDate().replaceAll("-", "")+"235959'");
				paraBuffer.append("&endDate="+ordForm.getEndDate());
			}else{
				sql.append(" and a.o_tradetime <= '"+Tools.getNow4()+"235959'");
				ordForm.setEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
				paraBuffer.append("&endDate="+Tools.getNow2());
			}
			if(null != ordForm.getUsername() && !"".equals(ordForm.getUsername())){
				sql.append(" and a.o_userno = '"+ordForm.getUsername()+"'");
				paraBuffer.append("&username="+ordForm.getUsername());
			}
			if(null != ordForm.getExp1() && !"".equals(ordForm.getExp1())){
				sql.append(" and a.o_tradeserial  = "+ordForm.getExp1());
				paraBuffer.append("&exp1="+ordForm.getExp1());
			}
			if(null != ordForm.getDailishang() && !"".equals(ordForm.getDailishang())){
				sql.append(" and a.o_writecheck ='"+ordForm.getDailishang()+"'");
				paraBuffer.append("&dailishang="+ordForm.getDailishang());
			}
			if("0".equals(flag)){
			a=dbService.getInt(sql2+sql.toString());
			page.setRsCount(a);
			sql.append(" order by a.o_tradetime desc");
			sql.append(" limit "+(page.getCurPage()-1)*page.getPageSize()+","+page.getPageSize());
			}
			List list = dbService.getList(sql1.toString()+sql.toString());
			ordForm.setParamUrl(paraBuffer.toString());
			rs.put("total", a);
			rs.put("datas", list);
			return rs;
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=dbService)
        		dbService.close();
        }
	}
	
}

