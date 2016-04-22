package com.wlt.webm.business;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.SysPhoneArea;
import com.wlt.webm.business.form.SysPhoneAreaForm;
import com.wlt.webm.business.form.SysUserInterfaceForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.tool.Tools;


/**
 * 全国充值
 */
public class MobileChargeService
{
	
	/**
	 * 同步锁
	 */
	private Object getSeq = new Object();

	 /**
	 * 获取号码区域信息
	 * @return 号码区域信息
	 * @throws Exception
	 */
	public SysPhoneAreaForm getPhoneInfo(String pnum) throws Exception {
        DBService db = new DBService();
        SysPhoneAreaForm spForm = new SysPhoneAreaForm();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select province_code, phone_type")
                    .append(" from sys_phone_area ")
                    .append(" where phone_num=? ");

            String[] params = { pnum };
            String[] fields = { "province_code","phone_type"};
            
            db.populate(spForm, fields, sql.toString(), params);

            return spForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	 /**
	 * 获取用户接口
	 * @return 用户接口
	 * @throws Exception
	 */
	public String getInterfaceId(SysUserInterfaceForm sui) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select in_id")
                    .append(" from sys_user_interface ")
                    .append(" where user_id="+sui.getUser_id()+" and charge_type = "+sui.getCharge_type()+" or charge_type=0 and province_code = "+sui.getProvince_code());
            return db.getString(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 获取用户类型
	 * @return 用户类型
	 * @throws Exception
	 */
	public String getUserRoleType(String sr_id) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select sr_type")
                    .append(" from sys_role ")
                    .append(" where sr_id="+sr_id);
            return db.getString(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 获取用户佣金组
	 * @return 用户佣金组
	 * @throws Exception
	 */
	public String getUserCommission(String uid) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select a.sg_id")
                    .append(" from sys_commissionuser a")
                    .append(" where a.su_id = "+uid);
            System.out.println("sql  "+sql);
            return db.getString(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 获取资金账号子账号
	 * @param fundType
	 * @param uid
	 * @return
	 * @throws Exception
	 */
	public String getUserFundAcct(String fundType,String userno) throws Exception {
        DBService db = new DBService();
        try {
        	String fundSql = "select fundacct from wht_facct where user_no = "+userno;
        	String fundAcct = db.getString(fundSql);
            StringBuffer sql = new StringBuffer();
            sql.append("select childfacct")
                    .append(" from wht_childfacct ")
                    .append(" where type = '"+fundType+"' and fundacct = '"+fundAcct+"'");
            return db.getString(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 获取资金账号子账号状态
	 * @return 资金账号子账号状态
	 * @throws Exception
	 */
	public String getFundAcctState(String fundAcct) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select state")
                    .append(" from wlt_childfacct ")
                    .append(" where childfacct = '"+fundAcct+"'");
            return db.getString(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
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
	 * 获取佣金明细
	 * @return 佣金明细
	 * @throws Exception-scId------55   2    46   35 1000-------------------------------26---2---35---35--3000
	 */
	public String getCommMx(String sg_id,String sType,int sa_id,int pcode,String fee) throws Exception {
        DBService db = new DBService();
        String value = null;
        try {
        	if(sa_id != pcode){
    			String sql="select sc_id from sys_commission where sc_other=0 and sg_id = "+sg_id;
    			value=db.getString(sql);
    			System.out.println("sql1:"+sql);
    		}else{
    			String sql="select sc_id from sys_commission where sc_other=1 and sg_id="+sg_id+
    			" and sc_tradertype="+sType+" and sc_traderbegin*100<="+fee+" and sc_traderend*100>="+fee;
    			value=db.getString(sql);
    			System.out.println("sql2:"+sql);
    		}
            return value;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	
	public String getCommMx1(String sg_id,String sType,int sa_id,int pcode,String fee) throws Exception {
        DBService db = new DBService();
        String value = null;
        try {
        	if(sa_id != pcode){
    			String sql="select sc_id from sys_commission where sc_other=0 and sg_id = "+sg_id;
    			value=db.getString(sql);
    			System.out.println("sql1:"+sql);
    		}else{
    			String sql="select sc_id from sys_commission where sg_id="+sg_id+
    			" and sc_tradertype="+sType+" and sc_traderbegin*100<="+fee+" and sc_traderend*100>="+fee;
    			value=db.getString(sql);
    			System.out.println("sql2:"+sql);
    		}
            return value;
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	
	/**
	 * 短时间内不能重复获取序列号
	 * @return 返回是否可以对号码缴费
	 * @throws Exception 
	 */
	public String getNoSix_bank()throws Exception {
		synchronized (getSeq) {
			 DBService db = new DBService();
			String sequence="";
			try {
				StringBuffer sql = new StringBuffer();
	            sql.append("update wlt_code_sequence set sequence=sequence+1 where code='ec_serverSeq_id'");
	            db.update(sql.toString());
	            sql.delete(0, sql.length());
	            sql.append("select sequence from wlt_code_sequence where code='ec_serverSeq_id'");
	            sequence = Tools.formatSn(db.getInt(sql.toString()),7);
	            sql.delete(0, sql.length());
	            if(sequence.equals("9999999")){
	            	sql.append("update wlt_code_sequence set sequence=0 where code='ec_serverSeq_id'");
	                db.update(sql.toString());
	            }
			} catch (Exception e) {
				Log.error("生成的移动流水号出错",e);
				throw e;
			}
			finally
			{
				if(db!=null)
				db.close();
			}
			return sequence;
		}
	}

	/**
	 * 获取交易类型
	 * @return 交易类型
	 * @throws Exception
	 */
	public String getTradeType(String inId) {
		String result = "";
		if("0001".equals(inId)){
			result = "44";
		}else if("0002".equals(inId)){
			result = "36";
		}else if("0003".equals(inId)){
			result = "41";
		}else if("0004".equals(inId)){
			result = "34";
		}else if("0005".equals(inId)){
			result = "43";
		}else if("0006".equals(inId)){
			result = "42";
		}else if("A999".equals(inId)){
			result = "40";
		}else if("Q0001".equals(inId)){
			result = "35";
		}else if("C0001".equals(inId)){
			result = "37";
		}else if("0007".equals(inId)){
			result = "47";
		}else if("0008".equals(inId)){
			result = "46";
		}
		else if("1001".equals(inId)){//湖北移动
			result = "50";
		}
		else if("1002".equals(inId)){//湖北联通
			result = "51";
		}
		else if("1003".equals(inId)){//湖北电信
			result = "52";
		}
		else if("0009".equals(inId)){//手拉手
			result = "54";
		}
		return result;
    }
	/**
	 * 获取用户区域
	 * @return 用户区域
	 * @throws Exception
	 */
	public String getUserArea(String uid) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select user_site")
                    .append(" from sys_user ")
                    .append(" where user_id = "+uid);
            return db.getString(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 更新订单状态
	 * @return 
	 * @throws Exception
	 */
	public void updOrderState(String tableName,String tradeNo) throws Exception {
        DBService db = new DBService();
        try {
            //更新账户明细
            StringBuffer sql = new StringBuffer();
            sql.append("update "+tableName+" set state = 1 where tradeserial = "+tradeNo);
            db.update(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	
	/**
	 * 交易状态插入
	 * @param SendSeqNo
	 * @param state
	 * @throws Exception
	 */
	public void insertState(String SendSeqNo,String state) throws Exception {
		String date =Tools.getNow3();
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("insert into sys_paystate(sendseqno,state,qwe1,qwe2) values('"+SendSeqNo+"','"+state+"','"+date+"','"+null+"')");
            db.update(sql.toString());
        } catch (Exception ex) {
        	Log.error("交易状态插入出错"+ex.toString());
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 交易状态查询
	 * @param SendSeqNo
	 * @return
	 * @throws Exception
	 */
	public String selectState(String SendSeqNo) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select state")
                    .append(" from sys_paystate ")
                    .append(" where sendseqno='"+SendSeqNo+"'");
            return db.getString(sql.toString());
        } catch (Exception ex) {
        	Log.error("交易状态查询出错"+ex.toString());
            throw ex;
        } finally {
            db.close();
        }
    }
	
	/**
	 * 交易状态删除
	 * @param SendSeqNo
	 * @return
	 * @throws Exception
	 */
	public void deleteState(String SendSeqNo) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("delete from sys_paystate ")
                    .append(" where sendseqno='"+SendSeqNo+"'");
            db.update(sql.toString());
        } catch (Exception ex) {
        	Log.error("交易状态删除出错"+ex.toString());
            throw ex;
        } finally {
            db.close();
        }
    }
	
	
	
	/**
	 * 更新资金账户，账户明细
	 * @return 
	 * @throws Exception
	 */
	public void updAccount(int fee,String fundacct,String empFee,String tableName,String tradeserial) throws Exception {
        DBService db = new DBService();
        try {
        	db.setAutoCommit(false);
        	//更新资金账户余额
            StringBuffer sql = new StringBuffer();
            sql.append("update wlt_facct set accountleft = accountleft+"+fee+" where fundacct = '"+fundacct+"'");
            db.update(sql.toString());
            //更新押金账户余额
            StringBuffer sql1 = new StringBuffer();
            sql1.append("update wlt_childfacct set usableleft = usableleft+"+fee+" where childfacct = '"+fundacct+"02'");
            db.update(sql1.toString());
            //更新佣金账户余额
            StringBuffer sql2 = new StringBuffer();
            sql2.append("update wlt_childfacct set usableleft = usableleft-"+empFee+" where childfacct = '"+fundacct+"03'");
            db.update(sql2.toString());
            //更新账户明细
            StringBuffer sql3 = new StringBuffer();
            sql3.append("update "+tableName+" set accountleft = accountleft+"+fee+",state = 1 where tradeserial = '"+tradeserial+"' and childfacct = '"+fundacct+"02'");
            db.update(sql3.toString());
            StringBuffer sql4 = new StringBuffer();
            sql4.append("update "+tableName+" set accountleft = accountleft-"+empFee+",state = 1 where tradeserial = '"+tradeserial+"' and childfacct = '"+fundacct+"03'");
            db.update(sql4.toString());
            db.commit();
        } catch (Exception ex) {
        	db.rollback();
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 更新资金账户，账户明细---交通罚款
	 * @return 
	 * @throws Exception
	 */
	public void updAccountJtfk(int fee,String userid,String tableName,String tradeserial) throws Exception {
        DBService db = new DBService();
        try {
        	db.setAutoCommit(false);
        	String fundacct = db.getString("select fundacct from wlt_facct where termid = "+userid);
        	//更新资金账户余额
            StringBuffer sql = new StringBuffer();
            sql.append("update wlt_facct set accountleft = accountleft+"+fee+" where fundacct = '"+fundacct+"'");
            db.update(sql.toString());
            //更新押金账户余额
            StringBuffer sql1 = new StringBuffer();
            sql1.append("update wlt_childfacct set usableleft = usableleft+"+fee+" where childfacct = '"+fundacct+"02'");
            db.update(sql1.toString());
            //更新账户明细
            StringBuffer sql3 = new StringBuffer();
            sql3.append("update "+tableName+" set accountleft = accountleft+"+fee+",state = 1 where tradeserial = '"+tradeserial+"' and childfacct = '"+fundacct+"02'");
            db.update(sql3.toString());
            db.commit();
        } catch (Exception ex) {
        	db.rollback();
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 更新上级账户明细
	 * @return 
	 * @throws Exception
	 */
	public void updEmpAccount(String fundacctEmp,String empFee,String tableName,String dealserial) throws Exception {
        DBService db = new DBService();
        try {
        	db.setAutoCommit(false);
            //更新佣金账户余额
            StringBuffer sql2 = new StringBuffer();
            sql2.append("update wlt_childfacct set usableleft = usableleft-"+empFee+" where childfacct = '"+fundacctEmp+"03'");
            db.update(sql2.toString());
            //更新账户明细
            StringBuffer sql4 = new StringBuffer();
            sql4.append("update "+tableName+" set accountleft = accountleft-"+empFee+",state = 1 where tradeserial = "+dealserial+" and childfacct = '"+fundacctEmp+"03'");
            db.update(sql4.toString());
            db.commit();
        } catch (Exception ex) {
        	db.rollback();
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 更新资金账户，账户明细
	 * @return 
	 * @throws Exception
	 */
	public void updAccountForReverse(int fee,String fundacct,String empFee,String tableName,String tradeserial) throws Exception {
        DBService db = new DBService();
        try {
        	db.setAutoCommit(false);
        	//更新资金账户余额
            StringBuffer sql = new StringBuffer();
            sql.append("update wlt_facct set accountleft = accountleft+"+fee+" where fundacct = '"+fundacct+"'");
            db.update(sql.toString());
            //更新押金账户余额
            StringBuffer sql1 = new StringBuffer();
            sql1.append("update wlt_childfacct set usableleft = usableleft+"+fee+" where childfacct = '"+fundacct+"02'");
            db.update(sql1.toString());
            //更新佣金账户余额
            StringBuffer sql2 = new StringBuffer();
            sql2.append("update wlt_childfacct set usableleft = usableleft-"+empFee+" where childfacct = '"+fundacct+"03'");
            db.update(sql2.toString());
            //更新账户明细
            StringBuffer sql3 = new StringBuffer();
            sql3.append("update wlt_acctbill_"+tableName+" set accountleft = accountleft+"+fee+",state = 1 where tradeserial = '"+tradeserial+"' and childfacct = '"+fundacct+"02'");
            db.update(sql3.toString());
            StringBuffer sql4 = new StringBuffer();
            sql4.append("update wlt_acctbill_"+tableName+" set accountleft = accountleft-"+empFee+",state = 1 where tradeserial = '"+tradeserial+"' and childfacct = '"+fundacct+"03'");
            db.update(sql4.toString());
            //更新订单状态为返销
            StringBuffer sql5 = new StringBuffer();
            sql5.append("update wlt_orderForm_"+tableName+" set state = 5 where tradeserial = '"+tradeserial+"'");
            db.update(sql5.toString());
            db.commit();
        } catch (Exception ex) {
        	db.rollback();
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 更新上级账户明细
	 * @return 
	 * @throws Exception
	 */
	public void updEmpAccountForReverse(String fundacctEmp,String empFee,String tableName,String dealserial) throws Exception {
        DBService db = new DBService();
        try {
        	db.setAutoCommit(false);
            //更新佣金账户余额
            StringBuffer sql2 = new StringBuffer();
            sql2.append("update wlt_childfacct set usableleft = usableleft-"+empFee+" where childfacct = '"+fundacctEmp+"03'");
            db.update(sql2.toString());
            //更新账户明细
            StringBuffer sql4 = new StringBuffer();
            sql4.append("update wlt_acctbill_"+tableName+" set accountleft = accountleft-"+empFee+",state = 1 where tradeserial = '"+dealserial+"' and childfacct = '"+fundacctEmp+"03'");
            db.update(sql4.toString());
            //更新订单状态为返销
            StringBuffer sql5 = new StringBuffer();
            sql5.append("update wlt_orderForm_"+tableName+" set state = 5 where tradeserial = '"+dealserial+"'");
            db.update(sql5.toString());
            db.commit();
        } catch (Exception ex) {
        	db.rollback();
            throw ex;
        } finally {
            db.close();
        }
    }
	/**
	 * 修改订单状态
	 * @return 
	 * @throws Exception
	 */
	public void updOrderStatus(String dealserial,String state,String tableName) throws Exception {
        DBService db=null;
        try {
        	db=  new DBService();
            StringBuffer sql2 = new StringBuffer();
            sql2.append("update "+tableName+" set state = "+state+" where tradeserial = '"+dealserial+"'");
            db.update(sql2.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=db)
            db.close();
        }
    }
	/**
	 * 查询订单状态
	 * @return 
	 * @throws Exception
	 */
	public String getOrderStatus(String dealserial,String tableName) throws Exception {
        DBService db = null;
        try {
        	db=new DBService();
            StringBuffer sql2 = new StringBuffer();
            sql2.append("select state from "+tableName+" where tradeserial = '"+dealserial+"'");
            return db.getString(sql2.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
        	if(null!=db)
            db.close();
        }
    }
	
	/**
	 * 判断是否在限制日期内
	 * 
	 * @return true 是限制日期
	 */
	public boolean isDateLimit(String time) {
		String sql = "select date from wht_datelimit";
		DBService db=null;
		try {
			 db = new DBService();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			cal.setTime(sf.parse(time));
			int a=cal.get(Calendar.DAY_OF_WEEK);
			if(a==6||a==7){
				return true;
			}
			List list = db.getStringList(sql);
			if(null!=list){
				for(Object st:list){
					System.out.println((String)st);
				}
				return list.contains(time.substring(0,8));
			}
			return false;
		} catch (Exception ex) {
			Log.error("判断是否在限制日期内:", ex);
			return false;
		}finally{
			if(null!=db)
				db.close();
		}
	}
}

