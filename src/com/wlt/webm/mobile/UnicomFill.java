package com.wlt.webm.mobile;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts.action.ActionForward;


import com.commsoft.epay.util.DateParser;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.InnerProcessDeal;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.dianx.bean.TelcomPayBean;
import com.wlt.webm.business.dianx.form.TelcomForm;
import com.wlt.webm.business.form.SysPhoneAreaForm;
import com.wlt.webm.business.form.SysUserInterfaceForm;
import com.wlt.webm.business.unicom.FillBusiness;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MsgCache;
import com.wlt.webm.pccommon.UniqueNo;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.tool.Tools;

	/**
	 * �ֻ���ͨ
	 * @author 
	 *
	 */
public class UnicomFill {
	
	   
	   /**
	    * app ��ͨ��ֵ���� com.wlt.webm.mobile.UnicomFill.telMobile(,,)
	    * @param phonePayForm TelcomForm���� ���У������ֶα�����
	    *                     tradeObject  �绰����
	    *                     seqNo   ������ˮ�ţ�26λΨһ��
	    *                     payFee  ���׽�� ��Ԫ��
	    *                     
	    *                                 
	    * @param user  session�л�ȡ��SysUserForm
	    * 
	    * @return  1;�޶�Ӧ��ֵ�ӿ�   2;δ֪�û�����  3;�޶�ӦӶ���� 4;Ѻ���˺Ų�����
	    *          5;Ӷ�����˺Ų�����  6:Ѻ���˺Ų�����   7;Ӷ�����˺Ų�����   8;Ѻ���˺�����
	    *          9;Ӷ����ϸ������  10;���ɶ���ʧ��  11;���ݴ������  12;��ֵ�쳣,����ϵ�ͷ�
	    *          0:��ֵ�ɹ�  -1;//��ֵʧ�� -2;//��ֵ����Ӧ
	    * @throws Exception
	    */
	   public static int telMobile(TelcomForm phonePayForm, SysUserForm user) throws Exception{
			String payNo=phonePayForm.getTradeObject().trim();
	        String paraMoney = phonePayForm.getPayFee();
	        String mon=Tools.yuanToFen(paraMoney);
	        System.out.println("moblie��ͨ����:"+payNo+"   ���:"+mon);
	        String sepNo=phonePayForm.getSeqNo();//Tools.getSeqNo(payNo);
	        phonePayForm.setSeqNo(sepNo);
	        String sqlno=Constant.FACTORYID_UNICOM+DateParser.getNowDateTime().substring(4)+UniqueNo.getInstance().getNoTwo();
	        int payFee=Integer.parseInt(mon);
	        String nowTime=Tools.getNow();
	        MobileChargeService service = new MobileChargeService();
	        //�û�����
	        String userArea = service.getUserArea(user.getUser_id());
	        //�������ͺ͹���id�Ƿ����
	        SysPhoneAreaForm spa = service.getPhoneInfo(payNo.substring(0,7));
	        spa.setProvince_code(new Integer(35));
	        spa.setPhone_type(2);
	        //�û��ӿ�id�Ƿ����
	        SysUserInterfaceForm sui = new SysUserInterfaceForm();
	        sui.setProvince_code(spa.getProvince_code());
	        sui.setCharge_type(spa.getPhone_type());
	        sui.setUser_id(Integer.parseInt(user.getUser_id()));
	        //�û���Ӷ�����Ƿ����
	        String roleType = service.getUserRoleType(user.getUser_role());
	        if(null == roleType || "".equals(roleType)){
	           return 2;//δ֪�û�����
	        }
	        String sgId = service.getUserCommission(user.getUser_id());
	        if(null == sgId || "".equals(sgId)){
	        	return 3;//�޶�ӦӶ����
	        }
	        //Ӷ�����˺ź�Ѻ���˺��Ƿ����
	        String fundAcct02 = service.getUserFundAcct("02", user.getUser_id());
	        if(null == fundAcct02 || "".equals(fundAcct02)){
	           return 4;//Ѻ���˺Ų�����
	        }
	        String fundAcct03 = service.getUserFundAcct("03", user.getUser_id());
	        if(null == fundAcct03 || "".equals(fundAcct03)){
	        	    return 5;//Ӷ�����˺Ų�����
	        }
	        //Ӷ�����˺ź�Ѻ���˺�״̬�Ƿ�����
	        String state = service.getFundAcctState(fundAcct02);
	        if(!"1".equals(state)){
	        	return 6;//Ѻ���˺Ų�����
	        }
	        String state1 = service.getFundAcctState(fundAcct03);
	        if(!"1".equals(state1)){
                 return 7;//Ӷ�����˺Ų�����
	        }
	        //Ѻ�����˺ŵĽ���Ƿ���ڿ۷ѽ��
	        int acctLeft = Integer.parseInt(service.getFundAcctLeft(fundAcct02));
	        if(0 == acctLeft || acctLeft - Integer.parseInt(mon) < 0){
	        return 8;//Ѻ���˺�����
	        }
	        //Ӷ����ϸ�Ƿ����
	        String scId = service.getCommMx(sgId, "2",Integer.parseInt(userArea),spa.getProvince_code(),mon);
	        if(null == scId || "".equals(scId)){
	        	    return 9;//Ӷ����ϸ������
	        }
	        //��¼����
	        int isSuccess = 0;
	        try {
	        	isSuccess = InnerProcessDeal.logOrder(String.valueOf(spa.getProvince_code()), sepNo, payNo, "0007", Constant.TELCOM_FILL, Integer.parseInt(mon), fundAcct02.substring(0, fundAcct02.length()-2),
	        			nowTime, nowTime, sqlno, "", "0", user.getUser_id(), acctLeft - Integer.parseInt(mon), user.getUsername(), String.valueOf(spa.getPhone_type()));
			} catch (Exception e) {
				   return 10;//���ɶ���ʧ��
			}
			if(0 != isSuccess){
				return 10;//���ɶ���ʧ��
			}
			Map dealMap = new HashMap();
	        if(0 == isSuccess){
	        	try {
	        		//�ڲ� �ۿ��Ӷ�����Ķ���״̬ 
	        		dealMap = InnerProcessDeal.indeal(fundAcct02.substring(0, fundAcct02.length()-2), payNo, nowTime, Integer.parseInt(mon), String.valueOf(spa.getProvince_code()), service.getTradeType("0007"), "", 
	        				spa.getPhone_type(), Integer.parseInt(userArea), Integer.parseInt(user.getUser_id()), Integer.parseInt(user.getUser_role()), spa.getProvince_code(), sepNo, sgId);
				} catch (Exception e) {
					return 11;//���ݴ������
				}
	        }
	        int dealFlag = (Integer)dealMap.get("flag");
	        if(1 == dealFlag){
	        	return 11;//���ݴ������
	        }
	        //���ó�ֵ�ӿ�
	        try {
	        	FillBusiness business=new FillBusiness(payNo,mon,sqlno);
        		business.deal();
	        } catch (Exception e) {
	        	service.updOrderStatus(sepNo, "6", "wlt_orderForm_"+nowTime.substring(2, 6));
				return 12;//��ֵ�쳣,����ϵ�ͷ�
				
			}
	        
	        Thread.sleep(1000);
    		String mainKey=null;
    		int times=0;
			while(times < 120){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(null==MsgCache.unicom.get("unicomPay"+sqlno)){
					times++;
				}else{
					mainKey=(String)MsgCache.unicom.get("unicomPay"+sqlno);
					break;
				}
			}
			if(times == 120){
				service.updOrderStatus(sepNo, "2", "wlt_orderForm_"+nowTime.substring(2, 6));
				return -2;
			}
	        
			if("0".equals(mainKey)){//��ֵ�ɹ�,�޸Ķ���״̬�ɹ�
				service.updOrderStatus(sepNo, "0", "wlt_orderForm_"+nowTime.substring(2, 6));
		        return 0;//��ֵ�ɹ�
			}else if("1".equals(mainKey)){//��ֵʧ��,�޸Ķ���״̬ʧ�ܣ���Ǯ����Ӷ��
				//���¶���״̬
				service.updOrderState("wlt_orderForm_"+nowTime.substring(2, 6), sepNo);
				//�������Ӷ���˻���ϸ
				if(null != dealMap){
					//����Ӷ���
					String empFee = String.valueOf(dealMap.get("empFee-self"));
					//�˻���ϸ����
					String tableName = "wlt_acctbill_"+nowTime.substring(2, 6);
					//��һ���˻�
					String empAcctLevlOne = (String)dealMap.get("acct-levelone");
					String empFeeLevlOne = (String)dealMap.get("empfee-levelone");
					//����һ���˻�
//					String empAcctLevlTwo = (String)dealMap.get("acct-leveltwo");
//					String empFeeLevlTwo = (String)dealMap.get("empfee-leveltwo");
					//������,�˻�����Ӷ�𲢸����˻���ϸ
					service.updAccount(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,sepNo);
					//�ϼ�����Ӷ�𲢸����˻���ϸ
					if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
						service.updEmpAccount(empAcctLevlOne, empFeeLevlOne, tableName,sepNo);
					}
				}
				return -1;//��ֵʧ��
			}
		else {//����Ӧ,�޸Ķ���״̬����Ӧ
				service.updOrderStatus(sepNo, "2", "wlt_orderForm_"+nowTime.substring(2, 6));
				return -2;//��ֵ����Ӧ
			}

		}
	   

}
