package com.wlt.webm.business.bean;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionForward;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.form.BankLogForm;
import com.wlt.webm.business.form.SysInvokeForm;
import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpdb.DBLog;
import com.wlt.webm.scpdb.DBService;
import com.wlt.webm.scpdb.FundAcctDao;
import com.wlt.webm.scputil.bean.FacctBillBean;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.util.Tools;


/**
 * 转款日志管理
 */
public class SysBankLog
{
	/**
	 * 转款日志
	 * @param blForm 
	 * @return 记录数
	 * @throws Exception
	 */
	public BankLogForm getBankLog(BankLogForm blForm) throws Exception {
		com.wlt.webm.db.DBService dbService = new com.wlt.webm.db.DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select dealserial,tradetime,tradefee from sys_banklog where id=?");
		String[] params = { blForm.getId() };
        String[] fields = { "dealserial","tradetime", "tradefee"};
        
        dbService.populate(blForm, fields, sql.toString(), params);
		
		return blForm;
	}
	/**
	 * 转款日志列表
	 * @param blForm 
	 * @return 记录数
	 * @throws Exception
	 */
	public List list(BankLogForm blForm) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("select id,dealserial,tradeaccount,DATE_FORMAT(tradetime,'%Y-%m-%d %k:%i:%s'),tradefee,tradetype,`explain`,state,DATE_FORMAT(distime,'%Y-%m-%d %k:%i:%s'),returnmsg,checkmsg,qrymsg" +
				" from sys_banklog");
		sql.append(" order by tradetime desc");
		List list = dbService.getList(sql.toString());
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			if(null != temp[5] && !"".equals(temp[5])){
				if("0".equals(temp[5])){
					temp[5] = "深圳银联转款";
				}else if("1".equals(temp[5])){
					temp[5] = "财付通转款";
				}
			}
			if(null != temp[7] && !"".equals(temp[7])){
				if("0".equals(temp[7])){
					temp[7] = "成功";
				}else if("1".equals(temp[7])){
					temp[7] = "失败";
				}else if("2".equals(temp[7])){
					temp[7] = "正在处理";
				}else if("3".equals(temp[7])){
					temp[7] = "正在处理中";
				}
			}
		}
		return list;
	}
	/**
	 * 添加转款日志
	 * @param blForm 
	 * @return 记录数
	 * @throws Exception
	 */
	public int add(BankLogForm blForm) throws Exception {
		DBService dbService = new DBService();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into sys_banklog(dealserial,tradeaccount,tradetime,tradefee,tradetype,`explain`,state,distime,returnmsg,checkmsg,qrymsg) " +
				"values('"+blForm.getDealserial()+"','"+blForm.getTradeaccount()+"','"+blForm.getTradetime()+"','"+blForm.getTradefee()+"'," +
						"'"+blForm.getTradetype()+"','"+blForm.getExplain()+"','"+blForm.getState()+"','"+blForm.getDistime()+"','"+blForm.getReturnmsg()+"','"+blForm.getCheckmsg()+"','"+blForm.getQrymsg()+"')");
		System.out.println("慧付款添加转款日志"+sql.toString());
		dbService.update(sql.toString());
		return 0;
	}
	/**
	 * 添加转款日志
	 * @param blForm 
	 * @return 记录数
	 * @throws Exception
	 */
	public int updateAcct(String fee,FacctBillBean facct,SysUserForm userForm) throws Exception {
		DBService dbService = new DBService();
		int result = 0;
		try {
			dbService.setAutoCommit(false);
			//记录账户明细
			FundAcctDao facctDB = new FundAcctDao(dbService);
			DBLog dbLog = new DBLog(dbService);
			dbLog.insertAcctLog(facct);
			//更新资金账户余额
			String sqlAcct = "select fundacct from wlt_facct where termid = "+userForm.getUser_id();
    		String acct = dbService.getString(sqlAcct);
    		StringBuffer sql = new StringBuffer();
            sql.append("update wlt_childfacct set");
            sql.append(" usableleft=usableleft+"+fee+"");
            sql.append(" where");
            sql.append(" accttypeid = '02' and fundacct = '"+acct+"'");
            dbService.update(sql.toString());
            StringBuffer sql1 = new StringBuffer();
            sql1.append("update wlt_facct set accountleft = accountleft+"+fee+" where fundacct = '"+acct+"'");
            dbService.update(sql1.toString());
            dbService.commit();
        } catch (Exception ex) {
        	dbService.rollback();
        	result = 1;
            throw ex;
        } finally {
        	dbService.close();
        }
		return result;
	}
	
	

	/**
	 * 慧付款转账
	 * @param hfkserial
	 * @param serial
	 * @param fee
	 * @param phone
	 * @param date
	 * @param time
	 * *@param cardType
	 * @return
	 * @throws Exception
	 */
	public static int hfkAcct(String hfkserial,String serial,int fee,String phone, String date,String time,String cardType){
		SysBankLog bankLog = new SysBankLog();
		Log.info("慧付款转账开始"+serial);
		String fundAcct=null;
		int acctLeft=0;
		DBService dbService = null;
		int money=fee;
		try {
			dbService = new DBService();
        	String[] array=bankLog.getHfkGz(phone.trim());
			if(null==array){
				 Log.info("慧付款转账收到未绑定号码"+phone.trim());
	             return 2;
			}
			fundAcct = array[0];//用户账户
			StringBuffer sq = new StringBuffer(300);
			String yearMonth = Tools.getNow3().substring(2, 6);
			String tableName = "wht_acctbill_"+yearMonth;
			sq.append("select 1 from "+tableName+" where tradeserial=?");
            if (dbService.hasData(sq.toString(), new String[] {hfkserial}))
            {
            	Log.info("慧付款转账收到重复流水号:"+hfkserial);
                return 0;
            }
				double mon=FloatArith.mul(fee, 0.0055);
				if(mon<=12000){
					fee=(int) FloatArith.sub(fee, mon);
				}else if(mon>12000){
					fee=(int) FloatArith.sub(fee, 12000);
				}
			acctLeft = Integer.parseInt(bankLog.getFundAcctLeft(fundAcct+"01"));
			} catch (Exception ex) {
				Log.error("慧付款转账处理失败!!!!!!!"+ex.toString());
	        	return 2;
	        } finally {
	        	dbService.close();
	        }
		try {
			dbService = new DBService();
			//更新资金账户余额
			dbService.setAutoCommit(false);
    		StringBuffer sql = new StringBuffer();
            sql.append("update wht_childfacct set");
            sql.append(" accountleft=accountleft+"+fee+",awardleft=awardleft+"+fee+"");
            sql.append(" where");
            sql.append(" type = '01' and fundacct = '"+fundAcct+"'");
            dbService.update(sql.toString());
            StringBuffer sql1 = new StringBuffer();
            sql1.append("update wht_facct set accountleft = accountleft+"+fee+",awardleft=awardleft+"+fee+" where fundacct = '"+fundAcct+"'");
            dbService.update(sql1.toString());
            Object[] acctObj1={null,fundAcct+"01",serial,phone,time,money,
					fee, 17,"慧付款转账",0,time,acctLeft+fee,
					hfkserial,fundAcct+"01",2};
            dbService.logData(15, acctObj1, "wht_acctbill_"+time.substring(2,6));
            dbService.commit();
        } catch (Exception ex) {
        	dbService.rollback();
				Log.error("慧付款转账--失败--更改状态失败,慧付款流水号:"+hfkserial);
        	return 2;
        } finally {
        	dbService.close();
        }
		return 0;
	}
	
	 public  double div(double v1, double v2, int scale) {
		  if (scale < 0) {
		   System.err.println("除法精度必须大于0!");
		   return 0;
		  }
		  BigDecimal b1 = new BigDecimal(Double.toString(v1));
		  BigDecimal b2 = new BigDecimal(Double.toString(v2));
		  return (b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP)).doubleValue();
		 }
	
	/**
	 * 更新转款日志状态
	 * @param blForm 
	 * @return 记录数
	 * @throws Exception
	 */
	public int update(String state,String ordId) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("update sys_banklog set state = '"+state+"' where dealserial = '"+ordId+"'");
			dbService.update(sql.toString());
		} catch (Exception e) {
			throw e;
		}finally{
			dbService.close();
		}
		return 0;
	}
	
/**
 * 修改慧付款转账状态
 * @param state
 * @param ordId
 * @return
 * @throws Exception
 */
	public int update2(String state,String ordId) throws Exception {
		DBService dbService = new DBService();
		try {
			StringBuffer sql = new StringBuffer();
			String nowTime=Tools.getNow();
			String tableName = "wlt_acctbill_"+nowTime.substring(2, 6);
			sql.append("update "+tableName+" set state = '"+state+"' where tradeserial = '"+ordId+"'");
			dbService.update(sql.toString());
		} catch (Exception e) {
			throw e;
		}finally{
			dbService.close();
		}
		return 0;
	}
	
	
	/**
	 * 获取资金账号
	 * @return 资金账号
	 * @throws Exception
	 */
	public String getFundAcct(String uid) throws Exception {
        DBService db = new DBService();
        try {
        	String fundSql = "select fundacct from wlt_facct where termid = "+uid;
            return db.getString(fundSql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 获取资金账号
	 * @return 资金账号
	 * @throws Exception
	 */
	public String getHfkFundAcct(String phone) throws Exception {
        DBService db = new DBService();
        try {
        	String fundSql = "SELECT fundacct FROM sys_hfk WHERE phone='"+phone+"'";
            return db.getString(fundSql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }

	
	
	/**
	 * 获取慧付款绑定账号
	 * @return 资金账号
	 * @throws Exception
	 */
	public String getHfkAcct(String phone) throws Exception {
        DBService db = new DBService();
        try {
        	String fundSql = "SELECT hfkid FROM sys_hfk WHERE phone= "+phone;
            return db.getString(fundSql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 获取慧付款绑定规则
	 * @return 资金账号
	 * @throws Exception
	 */
	public String[] getHfkGz(String phone) throws Exception {
        DBService db=null;
        try {
        	db = new DBService();
        	String fundSql = "SELECT fundacct,hfkid,gz FROM sys_hfk WHERE phone='"+phone+"'";
            return db.getStringArray(fundSql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	
	/**
	 * 
	 * @param phone
	 * @param db
	 * @return  用户资金账户  会付款ID 手续费规则 用户系统编号
	 * @throws SQLException 
	 */
	public String[] getHfkGzT1(String phone,DBService db) throws SQLException{
        	String fundSql = "SELECT fundacct,hfkid,gz,resourceid FROM sys_hfk WHERE phone='"+phone+"'";
            return db.getStringArray(fundSql.toString());
    }
	/**
	 * 获取资金账号子账号余额
	 * @return 资金账号子账号余额
	 * @throws Exception
	 */
	public String getFundAcctLeft(String fundAcct) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select accountleft")
                    .append(" from wht_childfacct ")
                    .append(" where childfacct = '"+fundAcct+"'");
            return db.getString(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 更新账户明细
	 * @return 
	 * @throws Exception
	 */
	public void updAccount(String fee,String tableName,String dealserial) throws Exception {
        DBService db = new DBService();
        //更新账户明细
        StringBuffer sql4 = new StringBuffer();
        sql4.append("update "+tableName+" set accountleft = accountleft-"+fee+",state = 1 where dealserial = '"+dealserial+"'");
        db.update(sql4.toString());
    }
	/**
	 * 更新账户余额
	 * @return 
	 * @throws Exception
	 */
	public void updAccountFee(SysUserForm userForm,String fee) throws Exception {
		DBService dbService = new DBService();
		int result = 0;
		try {
			dbService.setAutoCommit(false);
			//更新资金账户余额
			String sqlAcct = "select fundacct from wlt_facct where termid = "+userForm.getUser_id();
    		String acct = dbService.getString(sqlAcct);
    		StringBuffer sql = new StringBuffer();
            sql.append("update wlt_childfacct set");
            sql.append(" usableleft=usableleft-"+fee+"");
            sql.append(" where");
            sql.append(" accttypeid = '02' and fundacct = '"+acct+"'");
            dbService.update(sql.toString());
            StringBuffer sql1 = new StringBuffer();
            sql1.append("update wlt_facct set accountleft = accountleft-"+fee+" where fundacct = '"+acct+"'");
            dbService.update(sql1.toString());
            dbService.commit();
        } catch (Exception ex) {
        	dbService.rollback();
        	result = 1;
            throw ex;
        } finally {
        	dbService.close();
        }
    }
/**
 * 重置密码
 * @param login  用户名
 * @param passwd 密码
 * @return
 */
	public static int updPwd(String login,String passwd){
		DBService db=null;
        try {
			 db= new DBService();
			String sql0="update sys_loginuser set user_pass='"+passwd+"'"+ "where user_name='"+login+"'";
			String sql1="update sys_user set user_pass='"+passwd+"'"+ "where user_name='"+login+"'";
			String sql2="select user_id from sys_loginuser where user_name='"+login+"'";
			try{ 
			db.getInt(sql2);
			}catch (SQLException e) {
				return 1;
			}
			db.update(sql0);
			db.update(sql1);
		} catch (Exception e) {
			e.printStackTrace();
			return 2;
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
		return 0;

    }
	
	
        
	/**
	 * 获取用户的银行卡信息
	 * @param userno 用户系统编号
	 * @param flag   0 取借记卡和存折  1取信用卡 
	 * @return
	 * @throws Exception 
	 */
		public List<String[]> getUnionCardInfo(String userno,int flag) throws Exception{
			DBService db=null;
			List<String[]> lists= null;
			 String sql="SELECT a.userno,a.username,a.credentialType,b.identityname,a.credentialNum,a.bankID,d.unionname,a.bankflag,c.cardname,a.banknum,a.isbanding,a.submit "
				 +"FROM wht_netpay a,wht_identitytype b,wht_uniontype d,wht_cardtype c";
	        try {
				 db= new DBService();
				 if(flag==0){
					 sql+=" where bankflag in(0,1) and userno='"+userno+"'";
				 }else{
					 sql=" where bankflag=2 and userno='"+userno+"'";
				 }
				 lists=db.getList(sql);
			} catch (Exception e) {
				e.printStackTrace();
			    throw e;
			}finally{
				if(null!=db){
					db.close();
					db=null;
				}
			}
			return lists;

	    }
		
		public static void main(String[] args) {
			String cardType="贷记卡";
			int fee=1000000;
			if(-1!=cardType.indexOf("贷记卡")){//信用卡
				double mon=FloatArith.mul(fee, 0.002);
				if(mon>4000&&mon<=10000){
					fee=(int) FloatArith.sub(fee, mon);
				}else if(mon>10000){
					fee=(int) FloatArith.sub(fee, 10000);
				}else if(mon<=4000){
					fee=(int) FloatArith.sub(fee, 4000);
				}
				
			}else{
				fee=fee-4000;
			}
			System.out.println("===:"+fee);
		}
		
		/**
		 * 慧付款转账
		 * @param hfkserial
		 * @param serial
		 * @param fee
		 * @param phone
		 * @param date
		 * @param time
		 * *@param cardType
		 * @return
		 * @throws Exception
		 */
		public static int hfkAcctT1(String hfkserial,String serial,double fee,String phone, String date,String time,String cardType){
			SysBankLog bankLog = new SysBankLog();
			Log.info("慧付款转账开始"+serial);
			DBService dbService = null;
			double money=fee;
			try {
				dbService = new DBService();
	        	String[] array=bankLog.getHfkGzT1(phone.trim(),dbService);
				if(null==array){
					 Log.info("慧付款转账收到未绑定号码"+phone.trim()+" 无效请求");
		             return 2;
				}
				String sq="select 1 from sys_hfk_record where hfknum=?";
	            if (dbService.hasData(sq, new String[] {hfkserial}))
	            {
	            	Log.info("慧付款转账收到重复流水号:"+hfkserial);
	                return 0;
	            }
					double mon=FloatArith.mul(fee, 0.0055);
					if(mon<=12000){
						fee=(int) FloatArith.sub(fee, mon);
					}else if(mon>12000){
						fee=(int) FloatArith.sub(fee, 12000);
					}
				   dbService.logData(17, new Object[]{null,phone,array[3],array[0],
						   hfkserial,serial,money,fee,date,time,null,cardType,0,null,null,null,null}, "sys_hfk_record");
				} catch (Exception ex) {
					Log.error("慧付款转账处理失败! "+hfkserial+ex.toString());
		        	return 2;
		        } finally {
		        	if(null!=dbService){
			        	dbService.close();	
		        	}
		        }
			return 0;
		}
}

