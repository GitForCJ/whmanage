package com.wlt.webm.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.NetPayUtil;
import com.wlt.webm.db.DBService;
import com.wlt.webm.db.DBToolSCP;
import com.wlt.webm.message.YMmessage;
import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.MD5;

	/**
	 * 手机移动commList
	 * @author 
	 *
	 */
public class SmallRequest {
	
	   
	   /**查询佣金
	    * @param userForm  session中获取的SysUserForm(必须包含User_id)
	    * 
	    * @return  佣金余额(int)单位分
	    * @throws Exception
	    */
	   public static int getCommission(SysUserForm userForm){
		   String userid = userForm.getUser_id();
		   DBService db=null;
		   String facct="";
		   int fee=0;
		   try {
			   db =new DBService();
		       facct= db.getString("select fundacct from wlt_facct where termid = "+userid);
		       fee=db.getInt("select usableleft from wlt_childfacct where childfacct ="+facct+"03");
			} catch (Exception e) {
				System.out.println("APP查询佣金余额出错"+userid+"--"+facct+"--"+fee);
			}finally{
				db.close();
			}
		        return fee;
		    }
	   
	   
	   /**佣金转押金
	    * @param userForm  session中获取的SysUserForm(必须包含User_id)
	    * 		 String fee  转账金额  单位分
	    * 
	    * @return  0成功，1失败 2余额不足
	    * @throws Exception
	    */
	   public static int transChild(SysUserForm userForm,String fee){
		   String userid = userForm.getUser_id();
	       String sepNo=Tools.getSeqNo("");
	       String nowTime=Tools.getNow();
	       MobileChargeService service = new MobileChargeService();
		   SysUser user=new SysUser();
		   String fromAccount="";
		   String toAccount="";
		   String acctLeft="";
		   try {
			   fromAccount = service.getUserFundAcct("03",userid);
			   acctLeft = service.getFundAcctLeft(fromAccount);
			   if(null != acctLeft && !"".equals(acctLeft)){
				    int tmp = Integer.parseInt(acctLeft);
		        	if(tmp - Integer.parseInt(fee) < 0){
		        		return 2;
		        	}
				   toAccount = service.getUserFundAcct("02",userid);
				   user.transChild(fromAccount, toAccount, fee, sepNo, nowTime, "", "45", sepNo);
			   }else{
				   return 1;
			   }
		       
		   } catch (Exception e) {
			   System.out.println("APP佣金转押金出错"+fromAccount+"--"+toAccount);
			   return 1;
		   }
		       return 0;
	   }
	   
	   /**修改登陆密码
	    * @param userForm  session中获取的SysUserForm(必须包含User_id,Username)
	    * 								
	    * 		 String passWord  原始密码
	    * 		 String newPass	  新密码
	    * 
	    * @return  0成功，1失败 2原始密码不正确
	    * @throws Exception
	    */
	   public static int updatePass(SysUserForm userForm,String passWord,String newPass){
		   String pwd="";
		   try {
			    StringBuffer sql = new StringBuffer();
		        sql.append("select user_pass from sys_loginuser");
		        sql.append(" where");
		        sql.append(" user_id = '"+userForm.getUser_id()+"' and user_name='"+userForm.getUsername()+"'");
		        pwd=DBToolSCP.getString(sql.toString());
		        if(!MD5.encode(passWord).equals(pwd)){
		        	return 2;
				}
			    StringBuffer sql2 = new StringBuffer();
		        sql.append("update sys_loginuser set");
		        sql.append(" user_pass='"+MD5.encode(newPass)+"'");
		        sql.append(" where");
		        sql.append(" user_id = '"+userForm.getUser_id()+"' and user_name='"+userForm.getUsername()+"'");
		        DBToolSCP.update(sql2.toString());
			} catch (Exception e) {
				System.out.println("APP登陆密码修改出错"+pwd+"--"+passWord+"--"+newPass);
				return 1;
			}
		        return 0;
		    }
	   
	   
	   
	   /**修改交易密码
	    * @param userForm  session中获取的SysUserForm(必须包含User_id,Username)
	    * 								
	    * 		 String passWord  原始密码
	    * 		 String newPass	  新密码
	    * 
	    * @return  0成功，1失败 2原始密码不正确
	    * @throws Exception
	    */
	   public static int updatePassWord(SysUserForm userForm,String passWord,String newPass){
		   String pwd="";
		   try {
			    StringBuffer sql = new StringBuffer();
		        sql.append("select user_dealpass from sys_loginuser");
		        sql.append(" where");
		        sql.append(" user_id = '"+userForm.getUser_id()+"' and user_name='"+userForm.getUsername()+"'");
		        pwd=DBToolSCP.getString(sql.toString());
		        if(!MD5.encode(passWord).equals(pwd)){
		        	return 2;
				}
			    StringBuffer sql2 = new StringBuffer();
			    sql.append("update sys_loginuser set");
		        sql.append(" user_dealpass='"+userForm.getUserpassword()+"'");
		        sql.append(" where");
		        sql.append(" user_id = '"+userForm.getUser_id()+"' and user_name='"+userForm.getUsername()+"'");
		        DBToolSCP.update(sql2.toString());
			} catch (Exception e) {
				System.out.println("APP交易密码修改出错"+pwd+"--"+passWord+"--"+newPass);
				return 1;
			}
		        return 0;
		    }
	   
	   
	 
	   /**发送APP激活的验证码
	    * @param userForm  session中获取的SysUserForm(必须包含Username)
	    * 								
	    * @return String 1 发送短信失败 2 Username未获取到  返回其他字符则是发送的验证码并且发送成功
	    */
	   public static String sendValidateCode(SysUserForm userForm){
		   String phone=userForm.getUsername();
		   String msgCode="";
		   if(null != phone && !"".equals(phone)){
			   msgCode = NetPayUtil.getRandEight();
			   String content = "激活验证码:"+msgCode;
			   if(YMmessage.ymSendSMS(new String[]{phone}, content)){
				   Log.info("发送APP激活的验证码成功"+phone+"---"+msgCode);
			   }else{
				   return "1";
			   }
		   }else{
			   return "2";
		   }
		       return msgCode;
	   
	   }
	   
	   
	   /**修改用户为已激活状态
	    * @param userForm  session中获取的SysUserForm(必须包含Login)
	    * 								
	    * @return  0成功，1失败 
	    */
	   public static int updateState(SysUserForm userForm){
		   String sql="update sys_user set isActivate=2 where user_name='"+userForm.getLogin()+"'";
				 DBService db =null;
				 try{
				 db= new DBService();
				 db.update(sql);
				 }catch (Exception e) {
					 Log.info("修改用户为已激活状态出错"+userForm.getLogin()+e.toString());
					 return 1;
				 }
				 finally{
					 if(null!=db)
					 db.close();
				 }
		        return 0;
		    }
	   
	   
	   

}
