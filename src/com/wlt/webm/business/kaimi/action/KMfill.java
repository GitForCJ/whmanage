package com.wlt.webm.business.kaimi.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.wlt.webm.business.InnerProcessDeal;
import com.wlt.webm.business.MobileChargeService;
import com.wlt.webm.business.form.SysPhoneAreaForm;
import com.wlt.webm.business.form.SysUserInterfaceForm;
import com.wlt.webm.business.kaimi.bean.KmInterface;
import com.wlt.webm.db.DBService;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.tool.Tools;

/**
 * ����
 * @author lenovo
 *
 */
public class KMfill extends DispatchAction{


	/**
	 * ���ܳ�ֵ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward kmFill(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception  {
		String payNo=request.getParameter("tradeObject").replaceAll(" ", "").trim();
        String paraMoney = request.getParameter("money");
        String pArea =new String(request.getParameter("pArea").getBytes("ISO-8859-1"), "gb2312");
        System.out.println("���ܳ�ֵ��Ϣ  payNo:"+payNo +"  paraMoney:"+paraMoney+"  pArea:"+pArea);
        String mon=Tools.yuanToFen(paraMoney);
        int payFee=Integer.parseInt(mon);
        String nowTime=Tools.getNow();
        String sepNo=payNo.substring(4)+KmInterface.buildRandom(2)+KmInterface.buildRandom(4);
        SysUserForm user = (SysUserForm) request.getSession().getAttribute("userSession");
        MobileChargeService service = new MobileChargeService();
        //�û�����
        String userArea = service.getUserArea(user.getUser_id());
        //�������ͺ͹���id�Ƿ����
        SysPhoneAreaForm spa = service.getPhoneInfo(payNo.substring(0,7));
        spa.setProvince_code(new Integer(35));
        spa.setPhone_type(3);
        //�û��ӿ�id�Ƿ����
        SysUserInterfaceForm sui = new SysUserInterfaceForm();
        sui.setProvince_code(spa.getProvince_code());
        sui.setCharge_type(spa.getPhone_type());
        sui.setUser_id(Integer.parseInt(user.getUser_id()));
        String inId ="0003";
        //�û���Ӷ�����Ƿ����
        String roleType = service.getUserRoleType(user.getUser_role());
        if(null == roleType || "".equals(roleType)){
        	request.setAttribute("mess", "δ֪�û�����");
        	return new ActionForward("/business/kamibusiness.jsp");
        }
        String sgId = service.getUserCommission(user.getUser_id());
        if(null == sgId || "".equals(sgId)){
        	request.setAttribute("mess", "�޶�ӦӶ����");
        	return new ActionForward("/business/kamibusiness.jsp");
        }
        //Ӷ�����˺ź�Ѻ���˺��Ƿ����
        String fundAcct02 = service.getUserFundAcct("02", user.getUser_id());
        if(null == fundAcct02 || "".equals(fundAcct02)){
        	request.setAttribute("mess", "Ѻ���˺Ų�����");
        	return new ActionForward("/business/kamibusiness.jsp");
        }
        String fundAcct03 = service.getUserFundAcct("03", user.getUser_id());
        if(null == fundAcct03 || "".equals(fundAcct03)){
        	request.setAttribute("mess", "Ӷ�����˺Ų�����");
        	return new ActionForward("/business/kamibusiness.jsp");
        }
        //Ӷ�����˺ź�Ѻ���˺�״̬�Ƿ�����
        String state = service.getFundAcctState(fundAcct02);
        if(!"1".equals(state)){
        	request.setAttribute("mess", "Ѻ���˺Ų�����");
        	return new ActionForward("/business/kamibusiness.jsp");
        }
        String state1 = service.getFundAcctState(fundAcct03);
        if(!"1".equals(state1)){
        	request.setAttribute("mess", "Ӷ�����˺Ų�����");
        	return new ActionForward("/business/kamibusiness.jsp");
        }
        //Ѻ�����˺ŵĽ���Ƿ���ڿ۷ѽ��
        int acctLeft = Integer.parseInt(service.getFundAcctLeft(fundAcct02));
        if(0 == acctLeft || acctLeft - Integer.parseInt(mon) < 0){
        	request.setAttribute("mess", "Ѻ���˺�����");
        	return new ActionForward("/business/kamibusiness.jsp");
        }
        //Ӷ����ϸ�Ƿ����
        String scId = service.getCommMx(sgId, String.valueOf(spa.getPhone_type()),Integer.parseInt(userArea),spa.getProvince_code(),mon);
        if(null == scId || "".equals(scId)){
        	request.setAttribute("mess", "Ӷ����ϸ������");
        	return new ActionForward("/business/kamibusiness.jsp");
        }
        //��¼����
        int isSuccess = 0;
        try {
        	isSuccess = InnerProcessDeal.logOrder(String.valueOf(spa.getProvince_code()), sepNo, payNo, "0003", Constant.TELCOM_FILL, Integer.parseInt(mon), fundAcct02.substring(0, fundAcct02.length()-2),
        			nowTime, nowTime, "", "", "0", user.getUser_id(), acctLeft - Integer.parseInt(mon), user.getUsername(), String.valueOf(spa.getPhone_type()));
		} catch (Exception e) {
			request.setAttribute("mess", "���ɶ���ʧ��");
        	return new ActionForward("/business/kamibusiness.jsp");
		}
		if(0 != isSuccess){
			request.setAttribute("mess", "���ɶ���ʧ��");
        	return new ActionForward("/business/kamibusiness.jsp");
		}
		Map dealMap = new HashMap();
        if(0 == isSuccess){
        	try {
        		//�ڲ� �ۿ��Ӷ�����Ķ���״̬ 
        		dealMap = InnerProcessDeal.indeal(fundAcct02.substring(0, fundAcct02.length()-2), payNo, nowTime, Integer.parseInt(mon), String.valueOf(spa.getProvince_code()), service.getTradeType(inId), "", 
        				spa.getPhone_type(), Integer.parseInt(userArea), Integer.parseInt(user.getUser_id()), Integer.parseInt(user.getUser_role()), spa.getProvince_code(), sepNo, sgId);
			} catch (Exception e) {
				request.setAttribute("mess", "���ݴ������");
	        	return new ActionForward("/business/kamibusiness.jsp");
			}
        }
        int dealFlag = (Integer)dealMap.get("flag");
        if(1 == dealFlag){
        	request.setAttribute("mess", "���ݴ������");
        	return new ActionForward("/business/kamibusiness.jsp");
        }
        //���ó�ֵ�ӿ�
        int status = 0;
        try {
            if(KmInterface.kmFill(sepNo, payNo, paraMoney, "1", "����", "", "")==0){
            	status = 0;
            }else{
            int count=0;
            while(count<=360){
            	Thread.sleep(1000);
            	count++;
            	status=KmInterface.kmquery(sepNo);
            	if(status==2||status==4||status==6||status==7){
            		break;
            	}
            }
            }
        } catch (Exception e) {
			//���¶���״̬
			service.updOrderState("wlt_orderForm_"+nowTime.substring(2, 6), sepNo);
			//�������Ӷ���˻���ϸ--
			if(null != dealMap){
				//����Ӷ���
				String empFee = String.valueOf(dealMap.get("empFee-self"));
				//�˻���ϸ����
				String tableName = "wlt_acctbill_"+nowTime.substring(2, 6);
				//��һ���˻�
				String empAcctLevlOne = (String)dealMap.get("acct-levelone");
				String empFeeLevlOne = (String)dealMap.get("empfee-levelone");
				//����һ���˻�
				String empAcctLevlTwo = (String)dealMap.get("acct-leveltwo");
				String empFeeLevlTwo = (String)dealMap.get("empfee-leveltwo");
				//������,�˻�����Ӷ�𲢸����˻���ϸ
				service.updAccount(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,sepNo);
				//�ϼ�����Ӷ�𲢸����˻���ϸ
				if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
					service.updEmpAccount(empAcctLevlOne, empFeeLevlOne, tableName,sepNo);
				}
				//���ϼ�����Ӷ�𲢸����˻���ϸ
				if(null != empAcctLevlTwo && !"".equals(empAcctLevlTwo)){
					service.updEmpAccount(empAcctLevlTwo, empFeeLevlTwo, tableName,sepNo);
				}
			}
			request.setAttribute("mess", "��ֵ�ӿ��쳣");
	        return new ActionForward("/business/kamibusiness.jsp");
			
		}
		if(4 == status){//��ֵ�ɹ�,�޸Ķ���״̬�ɹ�
			service.updOrderStatus(sepNo, "0", "wlt_orderForm_"+nowTime.substring(2, 6));
		}else if(status == 6||status == 7||status == 2||status == 0){//��ֵʧ��,�޸Ķ���״̬ʧ�ܣ���Ǯ����Ӷ��
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
				String empAcctLevlTwo = (String)dealMap.get("acct-leveltwo");
				String empFeeLevlTwo = (String)dealMap.get("empfee-leveltwo");
				//������,�˻�����Ӷ�𲢸����˻���ϸ
				service.updAccount(payFee, fundAcct02.substring(0, fundAcct02.length()-2), empFee, tableName,sepNo);
				//�ϼ�����Ӷ�𲢸����˻���ϸ
				if(null != empAcctLevlOne && !"".equals(empAcctLevlOne)){
					service.updEmpAccount(empAcctLevlOne, empFeeLevlOne, tableName,sepNo);
				}
				//���ϼ�����Ӷ�𲢸����˻���ϸ
				if(null != empAcctLevlTwo && !"".equals(empAcctLevlTwo)){
					service.updEmpAccount(empAcctLevlTwo, empFeeLevlTwo, tableName,sepNo);
				}
			}
			request.setAttribute("mess", "��ֵʧ��");
	        return new ActionForward("/business/kamibusiness.jsp");
		}else if(9 == status){//�޷��ؽ��,�޸Ķ���״̬�쳣
			service.updOrderStatus(sepNo, "6", "wlt_orderForm_"+nowTime.substring(2, 6));
			request.setAttribute("mess", "��ֵ�쳣");
	        return new ActionForward("/business/kamibusiness.jsp");
		}else if(8 == status){//����Ӧ,�޸Ķ���״̬����Ӧ
			service.updOrderStatus(sepNo, "2", "wlt_orderForm_"+nowTime.substring(2, 6));
			request.setAttribute("mess", "��ֵ����Ӧ");
	        return new ActionForward("/business/kamibusiness.jsp");
		}
        request.setAttribute("mess", "��ֵ�ɹ�");
        return new ActionForward("/business/kamibusiness.jsp");
	}

	 /**
	 * ��ȡ�û��ӿ�
	 * @return �û��ӿ�
	 * @throws Exception
	 */
	public String getInterfaceId(SysUserInterfaceForm sui) throws Exception {
        DBService db = new DBService();
        try {
            StringBuffer sql = new StringBuffer();
            sql.append("select in_id")
                    .append(" from sys_user_interface ")
                    .append(" where user_id="+sui.getUser_id()+" and charge_type = "+sui.getCharge_type()+"  and province_code = "+sui.getProvince_code());
            System.out.println("slq:"+sql);
            return db.getString(sql.toString());
        } catch (Exception ex) {
            throw ex;
        } finally {
            db.close();
        }
    }
	

}
