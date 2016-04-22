package com.wlt.webm.ten.bean;

import java.sql.SQLException;
import java.util.List;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBConfig;
import com.wlt.webm.db.DBService;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.util.TenpayUtil;


/**
 * 财付通转账日志处理<br>
 */
public class LogInfoBean {
	
	/**
	 * 商户号
	 */
	private String bargainor_id;
	/**
	 * 交易订单号
	 */
	private String transaction_id;
	/**
	 * 内部订单号
	 */
	private String sp_billno;
	/**
	 * 客户端IP
	 */
	private String clientIP;
	/**
	 * 交易金额
	 */
	private int pay_fee;
	/**
	 * 手续费
	 */
	private int poundage=0;
	/**
	 * 交易日期
	 */
	private String date;
	/**
	 * 交易时间(秒)
	 */
	private String tradetime; 
	/**
	 * 
	 */
	public LogInfoBean(){
		
	}
	
	/**
	 * @param bargainor_id
	 * @param transaction_id
	 * @param sp_billno
	 * @param clientIP
	 * @param pay_fee
	 * @param currentTime
	 */
	public LogInfoBean(String bargainor_id, String transaction_id, String sp_billno,
			String clientIP, String pay_fee, String currentTime){
		this.bargainor_id=bargainor_id;
		this.transaction_id=transaction_id;
		this.sp_billno=sp_billno;
		this.clientIP=clientIP;
		this.pay_fee=Integer.parseInt(pay_fee);
		
//		
//		//手续费计算，暂时不用
//		if(this.pay_fee>=1000){
//			this.poundage=0;
//		}else {
//			this.poundage=1;
//		}
		this.date=currentTime.substring(0,8); //YYYYMMDDHHMMSS
		this.tradetime=currentTime;
	}
	
	/** 
	 * 插入财付通交易日志表
	 * @param userForm
	 */
	public void Log(SysUserForm userForm,int flag){
		if(16==flag){
			int  cftminTransferFee = TenpayUtil.yuanToFen(DBConfig.getInstance().getCftMinFee().trim());
			//int  cftmaxTransferFee = Integer.parseInt(DBConfig.getInstance().getCftMaxFee().trim());	
			int  cfthandlingCharge = TenpayUtil.yuanToFen(DBConfig.getInstance().getCftHandleCharge().trim());
			if(this.pay_fee>0&&this.pay_fee<cftminTransferFee){
				this.poundage=cfthandlingCharge;	
			}
	}
		Log.info("财付通转账手续费-----："+this.poundage+"  "+userForm.getUsername()+"  "+userForm.getUser_id()+"  "+userForm.getUser_role());
		String roleTypeString=userForm.getUser_role(); //角色编号
		String facct="";  //子帐号
		DBService dbService=null;
		Object[] params={userForm.getUserno()};
		String sql ="select fundacct from  wht_facct where user_no=?";
		try {
			dbService = new DBService();
			List listAcount =dbService.getStringList(sql, params);
			if(listAcount.size()!=0){
				facct =((String) listAcount.get(0)).trim()+"01";
				Log.info("财付通---资金子帐号："+facct);
			}
			else{
				Log.info("财付通---没查到资金子帐号");
			}
			Object[] params1={null,
					roleTypeString,
					userForm.getUserno(),
					facct,
					"深圳市万恒科技有限公司额度充值",
					flag,
					bargainor_id,
					transaction_id,
					sp_billno,
					clientIP,
					pay_fee*10,
					poundage*10,
					date,
					tradetime,
					tradetime,
					tradetime,
					"2",
					"2",
					"1",
					};
			String sql1 ="insert into wht_tenpaytransferlog values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			dbService.update( sql1,params1);
		}catch (SQLException e) {
			Log.info("财付通交易日志插入数据库异常");
			e.printStackTrace();
		} finally{
			dbService.close();
		}

		} 
}
