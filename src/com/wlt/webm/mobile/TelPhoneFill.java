package com.wlt.webm.mobile;

import java.util.HashMap;
import java.util.Map;


import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.InnerProcessDeal;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.dianx.bean.TelcomPayBean;
import com.wlt.webm.business.dianx.form.TelcomForm;
import com.wlt.webm.business.form.SysPhoneAreaForm;
import com.wlt.webm.business.form.SysUserInterfaceForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.pccommon.UniqueNo;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.tool.Tools;

	/**
	 * �ֻ�����
	 * @author lenovo
	 *
	 */
public class TelPhoneFill {
	
	   public TelPhoneFill(){
		   
	   }
	   
	   /**
	    * app ���ų�ֵ���� com.wlt.webm.mobile.TelPhoneFill.telMobile(,,)
	    * @param phonePayForm TelcomForm���� ���У������ֶα�����
	    *                     tradeObject  �绰����
	    *                     seqNo   ������ˮ�ţ�26λΨһ��
	    *                     payFee  ���׽�� ��Ԫ��
	    *                     numType �������� 01 ��һ�ɷ� 02 ����ɷ� 03 �ۺϽɷ� 
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
	        String numType=phonePayForm.getNumType();
	        String mon=Tools.yuanToFen(paraMoney);
	        System.out.println("moblie����:"+payNo+"   ���:"+mon+"   ����:"+numType);
	        String sepNo=phonePayForm.getSeqNo();//Tools.getSeqNo(payNo);
	        phonePayForm.setSeqNo(sepNo);
	        int payFee=Integer.parseInt(mon);
	        String nowTime=Tools.getNow();
	        //������������
	 		String serialNo = Tools.getNow() + Constant.AGENT_CODE+ UniqueNo.getInstance().getNoSix();
	 		String rollbackData=serialNo + "#" + mon + "#04075582025590";
	        //
	        MobileChargeService service = new MobileChargeService();
	        //�û�����
	        String userArea = service.getUserArea(user.getUser_id());
	        //�������ͺ͹���id�Ƿ����
	        SysPhoneAreaForm spa = service.getPhoneInfo(payNo.substring(0,7));
//	        if(null == spa.getProvince_code() || "".equals(spa.getProvince_code()) || null == spa.getPhone_type() || "".equals(spa.getPhone_type())){
//	        	request.setAttribute("mess", "�޴˺�������");
//	        	return new ActionForward("/telcom/telcomPhonepay.jsp");
//	        }
	        spa.setProvince_code(new Integer(35));
	        spa.setPhone_type(3);
	        //�û��ӿ�id�Ƿ����
	        SysUserInterfaceForm sui = new SysUserInterfaceForm();
	        sui.setProvince_code(spa.getProvince_code());
	        sui.setCharge_type(spa.getPhone_type());
	        sui.setUser_id(Integer.parseInt(user.getUser_id()));
	        String inId ="0001";//getInterfaceId(sui);
	        System.out.println("inid:"+inId);
	        if(null == inId || "".equals(inId)){
         	   return 1;//�޶�Ӧ��ֵ�ӿ�
	        }
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
	        String scId = service.getCommMx(sgId, String.valueOf(spa.getPhone_type()),Integer.parseInt(userArea),spa.getProvince_code(),mon);
	        if(null == scId || "".equals(scId)){
	        	    return 9;//Ӷ����ϸ������
	        }
	        //��¼����
	        int isSuccess = 0;
	        try {
	        	isSuccess = InnerProcessDeal.logOrder(String.valueOf(spa.getProvince_code()), sepNo, payNo, "0001", Constant.TELCOM_FILL, Integer.parseInt(mon), fundAcct02.substring(0, fundAcct02.length()-2),
	        			nowTime, nowTime, rollbackData, "", "0", user.getUser_id(), acctLeft - Integer.parseInt(mon), user.getUsername(), String.valueOf(spa.getPhone_type()));
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
	        		dealMap = InnerProcessDeal.indeal(fundAcct02.substring(0, fundAcct02.length()-2), payNo, nowTime, Integer.parseInt(mon), String.valueOf(spa.getProvince_code()), service.getTradeType(inId), "", 
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
	        int status = 0;
	        Map result=null;
	        try {
	        	TelcomPayBean bean = new TelcomPayBean();
	        	 result=bean.payBill(phonePayForm, user,serialNo);
	        	status=(Integer)result.get("code");
	        } catch (Exception e) {
	        	service.updOrderStatus(sepNo, "6", "wlt_orderForm_"+nowTime.substring(2, 6));
				return 12;//��ֵ�쳣,����ϵ�ͷ�
				
			}
			if(1 == status){//��ֵ�ɹ�,�޸Ķ���״̬�ɹ�
				service.updOrderStatus(sepNo, "0", "wlt_orderForm_"+nowTime.substring(2, 6));
				DBService xx=new DBService();
				String a=(String)result.get("CheckBill");
				String b=(String)result.get("RollBack");
				String sql="update "+"wlt_orderForm_"+nowTime.substring(2, 6)+" set writeoff = '"+b+"',"+"writecheck='"+ a+"'  where tradeserial = '"+sepNo+"'";
				Log.info("���ųɹ����³�������"+sql);
				xx.update(sql);
				xx.close();
				xx=null;
		        return 0;//��ֵ�ɹ�
			}else if(status == -1){//��ֵʧ��,�޸Ķ���״̬ʧ�ܣ���Ǯ����Ӷ��
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
					String empFeeLevlOne = String.valueOf(dealMap.get("empfee-levelone"));
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
	   
	   public static void main(String[] args) throws Exception {
		   TelcomForm tel=new TelcomForm();
		   tel.setSeqNo("130812180112011530277749202");
		   tel.setTradeObject("15302777492");
		   tel.setPayFee("100");
		   tel.setNumType("01");
		   
		   SysUserForm user=new SysUserForm();
		   user.setDealpass("123456");
		   user.setFeeshortflag("1");
		   user.setLogin("15302777492");
		   user.setMacflag("1");
		   user.setPhone("15302777492");
		   user.setSa_zone("0756");
		   user.setShortflag("1");
		   user.setRoleType("3");
		   user.setShortflag("1");
		   user.setUser_id("133");
		   user.setUser_role("42");
		   user.setPassword("defe12aad396f90e6b179c239de260d4");
		   user.setUser_site_city("101");
		   user.setUsersite("35");
		   System.out.println("result:"+TelPhoneFill.telMobile(tel, user));
		   
		   
		   
	}
	   /**
	    * �����ֻ��˲�ѯ������  com.wlt.webm.mobile.TelPhoneFill
	    * @param phonePayForm   ������� tradeObject seqNo��ˮ�� numType(03�ۺϽɷ�,01��һ�ɷ�,02����ɷ�)
	    * @param userForm       ������� sa_zone ���ţ�user_id,userename   
	    * @return    1 �ɹ�   ��ѯ�˵��ɹ���phonePayForm��������Ӧ�˵���Ϣ
	    *                    ���а��� seqNo��ˮ�š�totalFeeӦ�ս�tradeObject�ͻ����롢userName�ͻ�����
	    *                    billList�˵���ϸ����������map,key��ʾ��Ŀ��value��ʾ�������ݣ������ݲ������������ֵ��ʾ
	    *            2 �쳣
	    *            ���� ��ѯ�˵�ʧ��
	    */
	   public static int telcomQuery(TelcomForm phonePayForm,SysUserForm userForm){
			TelcomPayBean bean = new TelcomPayBean();
			int state;
			try {
				state = bean.queryBill(phonePayForm, userForm);
			} catch (Exception e) {
				e.printStackTrace();
				return 2;
			}
		   return state;
		   
	   }
	   
	   
	   /**
	    * app ���ŵ�����ֵ���� com.wlt.webm.mobile.TelPhoneFill.outTelMobile(,,)
	    * @param phonePayForm TelcomForm���� ���У������ֶα�����
	    *                     tradeObject  �绰����
	    *                     seqNo   ������ˮ�ţ�26λΨһ��
	    *                     payFee  ���׽�� ��Ԫ��
	    *                     numType �������� 01 ��һ�ɷ� 02 ����ɷ� 03 �ۺϽɷ� 
	    *                     
	    *                                 
	    * @param user  session�л�ȡ��SysUserForm
	    * 
	    * @return  0:��ֵ�ɹ�  -1;//��ֵʧ�� -2;//��ֵ����Ӧ
	    * @throws Exception
	    */
	   public static int outTelMobile(TelcomForm phonePayForm, SysUserForm user) throws Exception{
	        	TelcomPayBean bean = new TelcomPayBean();
	        	Map result=bean.payBill(phonePayForm, user,phonePayForm.getSeqNo());
	        	int status=(Integer)result.get("code");
	        	if(1 == status){
		        return 0;
			}else if(status == -1){
				return -1;
			}
			else {
				return -2;
			}

		}
	   

}
