package com.wlt.webm.mobile;

import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.tool.Tools;

/**
 * �ֻ��� ���ת��
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
	 * @param userForm  �������dealpass��user_id
	 * @param mon       ת����֣�   ����Ϊ��
	 * @param tofacct   �տ����˻� ����Ϊ��
	 * @param tradePwd  �������� ����Ϊ��
	 * @return 
	 * 1 ת�����ʽ��˺Ų�����
	 * 2 ת�����ʽ��˺Ų�����
	 * 3 ת�����ʽ��˺�����
	 * 4 ���������������
	 * 5 ��ת�����ʽ��˺Ų�����
	 * 6 ��ת�����ʽ��˺Ų�����      
	 * 8 �����쳣       
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
    		return 1; //ת�����ʽ��˺Ų�����
    	}
    	if(!"1".equals(state)){
    		return 2;//ת�����ʽ��˺Ų�����
    	}
    	String state1= user.getUserFundAcct(tofacct);
    	if(null == state1 || "".equals(state1)){
    		return 5; //��ת�����ʽ��˺Ų�����
    	}
    	if(!"1".equals(state1)){
    		return 6;//��ת�����ʽ��˺Ų�����
    	}
    	int acctLeft = Integer.parseInt(service.getFundAcctLeft(facct+"02"));
        if(0 == acctLeft || acctLeft - mon < 0){
        	return 3;//ת�����ʽ��˺�����
        }
    	if(null != userForm.getDealpass() && !"".equals(userForm.getDealpass())){
        	if(!userForm.getDealpass().equals(tradePwd)){
        		return 4;//���������������
        	}
        }else{
        	return 4;//���������������
        }
			user.trans(userid,tofacct,mon+"", sepNo, nowTime, "", "39", sepNo);
		} catch (Exception e) {
			e.printStackTrace();
			return 8;//�����쳣
		}
       return 0;
    
	}
	
	/**
	 * ��ѯ���컧���
	 * @param userid �û����
	 * @return  "001" ��ʾ�����useridΪ�գ��������ʾ��ǰ�û���λΪԪ
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
