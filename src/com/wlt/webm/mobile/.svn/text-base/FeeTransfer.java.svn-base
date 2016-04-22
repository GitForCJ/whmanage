package com.wlt.webm.mobile;

import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.tool.Tools;

/**
 * 手机端 额度转移
 * @author lenovo
 *
 */
public class FeeTransfer {
	
	/**
	 * 
	 */
	public FeeTransfer() {
	}
	
	/**
	 * 
	 * @param userForm  必须包含dealpass、user_id
	 * @param mon       转款金额（分）   不能为空
	 * @param tofacct   收款人账户 不能为空
	 * @param tradePwd  交易密码 不能为空
	 * @return 
	 * 1 转款人资金账号不存在
	 * 2 转款人资金账号不可用
	 * 3 转款人资金账号余额不足
	 * 4 交易密码输入错误
	 * 5 被转款人资金账号不存在
	 * 6 被转款人资金账号不可用      
	 * 8 交易异常       
	 *       
	 *       
	 */
	public static int feeTransfer(SysUserForm userForm,int mon,String tofacct,String tradePwd){

    	MobileChargeService service = new MobileChargeService();
    	String userid = userForm.getUser_id();
    	String sepNo=Tools.getSeqNo("");
    	String nowTime=Tools.getNow();
    	SysUser user=new SysUser();
    	try {
    	com.wlt.webm.scpdb.DBService db = new com.wlt.webm.scpdb.DBService();
    	String facct= db.getString("select fundacct from wlt_facct where termid = "+userid);
    	System.out.println("from :"+facct);
    	String state = user.getUserFundAcct(facct);
    	if(null == state || "".equals(state)){
    		return 1; //转款人资金账号不存在
    	}
    	if(!"1".equals(state)){
    		return 2;//转款人资金账号不可用
    	}
    	String state1= user.getUserFundAcct(tofacct);
    	if(null == state1 || "".equals(state1)){
    		return 5; //被转款人资金账号不存在
    	}
    	if(!"1".equals(state1)){
    		return 6;//被转款人资金账号不可用
    	}
    	int acctLeft = Integer.parseInt(service.getFundAcctLeft(facct+"02"));
        if(0 == acctLeft || acctLeft - mon < 0){
        	return 3;//转款人资金账号余额不足
        }
    	if(null != userForm.getDealpass() && !"".equals(userForm.getDealpass())){
        	if(!userForm.getDealpass().equals(tradePwd)){
        		return 4;//交易密码输入错误
        	}
        }else{
        	return 4;//交易密码输入错误
        }
			user.trans(userid,tofacct,mon+"", sepNo, nowTime, "", "39", sepNo);
		} catch (Exception e) {
			e.printStackTrace();
			return 8;//交易异常
		}
       return 0;
    
	}
	
	/**
	 * 查询代办户余额
	 * @param userid 用户编号
	 * @return  "001" 表示传入的userid为空，其他则表示当前用户余额单位为元
	 */
	public static String getLeftMoney(String userid){	
		if(null!=userid&&!userid.equals("null")){
		SysUser user=new SysUser();
		return user.getUseLeft(userid);
		}else{
		return "001";
	}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SysUserForm userForm=new SysUserForm();
		userForm.setUser_id("138");
		userForm.setDealpass("123");
		int mon=200;
		String tofacct="10000086";
		String tradePwd="123";
		System.out.println(FeeTransfer.feeTransfer(userForm, mon, tofacct, tradePwd));

	}

}
