package com.wlt.webm.ten.bean;

import java.sql.SQLException;
import java.util.List;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.db.DBService;
import com.wlt.webm.rights.form.SysUserForm;


/**
 * 财付通转账日志处理<br>
 */
public class QBLogInfoBean {
	
	/**
	 * 商户号
	 */
	private String bargainor_id;
	/**
	 * 交易订单号
	 */
	private String transaction_id;
	/**
	 * 内部流水号
	 */
	private String sp_billno;
	/**
	 * 用户账户
	 */
	private String inacct;
	/**
	 * 交易数量
	 */
	private int num;
	/**
	 * 手续费
	 */
	private int poundage;
	/**
	 * 交易日期
	 */
	private String date;
	/**
	 * 交易时间(秒)
	 */
	private String tradetime; 
	/**
	 * 终端编号
	 */
	private String resourceid;
	/**
	 * 
	 */
	private int tol;
	/**
	 * 
	 * @param bargainor_id
	 * @param tradeDate
	 * @param tran_seq
	 * @param in_acct
	 * @param num
	 * @param tradetime
	 * @param tradeNum
	 * @param resourceid
	 */
	 public QBLogInfoBean(String bargainor_id,String tradeDate,String tran_seq,String in_acct,
			 int num,String tradetime,String tradeNum,String resourceid,String tol){
				this.bargainor_id=bargainor_id;
				this.transaction_id=tran_seq;
				this.sp_billno=tradeNum;
				this.inacct=in_acct;
				this.num=num;
				this.poundage=0;
				this.date=tradeDate;
				this.tradetime=tradetime;
				this.resourceid=resourceid;
		        this.tol=Integer.parseInt(tol);
	 }
	
	/** 
	 * 插入财付通交易日志表
	 * @param userForm
	 */
	public int Log(SysUserForm userForm,String flag){
		String facct="";  //子帐号
		DBService dbService=null;
		Object[] params={resourceid};
		String sql ="select fundacct from  wlt_facct where  termid =?";
		String sql0="select user_name,user_site  from  sys_user where  user_id =?";
		try {
			dbService = new DBService();
			List listAcount =dbService.getStringList(sql, params);
			List lists=dbService.getStringList(sql0, params);
			if(listAcount.size()!=0){
				facct =(String) listAcount.get(0)+"02";
				Log.info("资金子帐号："+facct);
			}
			else{
				Log.info("没查到资金子帐号");
			}
	        MobileChargeService service = new MobileChargeService();
	        int acctLeft = Integer.parseInt(service.getFundAcctLeft(facct));
			
			Object[] params1={null,
					(String)lists.get(1),
					transaction_id,
					inacct,
					"Q0001",
					"Q0001",
					num,
					facct,
					tradetime,
					tradetime,
					3,
					tol,
					" ",
					0,
					resourceid,
					acctLeft,
					(String)lists.get(0),
					"Q1"
					};
			String sql1 ="insert into wlt_orderForm_"+tradetime.substring(2, 6)+
			"  values(?,?,?,?,?,?,?,?,?,?,?,?,? ,?,?,?,?,?)";
			dbService.update( sql1,params1);
		} catch (Exception e) {
			Log.info("QBLogInfoBean插入数据库异常:"+transaction_id);
			e.printStackTrace();
			return -1;
		} finally{
			dbService.close();
		}
		return 0;
	}
}
