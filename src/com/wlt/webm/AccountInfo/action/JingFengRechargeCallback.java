package com.wlt.webm.AccountInfo.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.AccountInfo.JingFengInter;
import com.wlt.webm.business.bean.OrderBean;
import com.wlt.webm.util.Digest;

/**
 * @author adminA
 *
 */
public class JingFengRechargeCallback extends DispatchAction {

	/**���� �ӿڻص���ȫ������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String R0_biztype=request.getParameter("R0_biztype");
		String R1_agentcode=request.getParameter("R1_agentcode");
		String R2_mobile=request.getParameter("R2_mobile");
		String R3_parvalue=request.getParameter("R3_parvalue");
		String R4_trxamount=request.getParameter("R4_trxamount");
		String R5_productcode=request.getParameter("R5_productcode");
		String R6_requestid=request.getParameter("R6_requestid");
		String R7_trxid=request.getParameter("R7_trxid");
		String R8_returncode=request.getParameter("R8_returncode");
		String R9_extendinfo=request.getParameter("R9_extendinfo");
		String R10_trxDate=request.getParameter("R10_trxDate");
		String hmac=request.getParameter("hmac");
		if(!"mobiletopup".equals(R0_biztype)||
				R1_agentcode==null || "".equals(R1_agentcode) ||
				R2_mobile==null || "".equals(R2_mobile) ||
				R3_parvalue==null || "".equals(R3_parvalue)||
				R4_trxamount==null || "".equals(R4_trxamount)||
				R5_productcode==null || "".equals(R5_productcode)||
				R6_requestid==null || "".equals(R6_requestid)||
				R7_trxid==null || "".equals(R7_trxid) ||
				R8_returncode==null || "".equals(R8_returncode)||
				R9_extendinfo==null || "".equals(R9_extendinfo)||
				R10_trxDate==null || "".equals(R10_trxDate)||
				hmac==null || "".equals(hmac)){
			Log.info("����ص���������R0_biztype:"+R0_biztype+",R1_agentcode:"+R1_agentcode+",R2_mobile:"+R2_mobile+",R3_parvalue:"+R3_parvalue+",R4_trxamount:"+R4_trxamount+",R5_productcode:"+R5_productcode+",R8_returncode:"+R8_returncode+",R9_extendinfo:"+R9_extendinfo+",R10_trxDate:"+R10_trxDate+",hmac:"+hmac+",�ص�������R7_trxid��"+R7_trxid+",ԭ������ˮ��R6_requestid��"+R6_requestid);
			return null;
		}
		String str=R0_biztype+R1_agentcode+R2_mobile+R3_parvalue+R4_trxamount+R5_productcode+R6_requestid+R7_trxid+R8_returncode+R9_extendinfo+R10_trxDate;
		if(!hmac.equals(Digest.hmacSign(str, JingFengInter.key))){
			Log.info("����ص�ǩ������,,,�ص������ţ�"+R7_trxid+",ԭ������ˮ�ţ�"+R6_requestid);
			return null;
		}
		PrintWriter out = response.getWriter();
		out.write("success");
		Log.info("�ص������ţ�"+R7_trxid+",ԭ������ˮ�ţ�"+R6_requestid+",,���ܻص��ɹ�,���success���");
		if("2".equals(R8_returncode)){
			Log.info("�ص������ţ�"+R7_trxid+",ԭ������ˮ�ţ�"+R6_requestid+",�ص�����ɹ�,����ƽ̨ҵ����");
			OrderBean.httpReturnDeal(0, R6_requestid,17);
		}else if("6".equals(R8_returncode)){
			Log.info("�ص������ţ�"+R7_trxid+",ԭ������ˮ�ţ�"+R6_requestid+",�ص����ʧ��,����ƽ̨�˷�ҵ����");
			OrderBean.httpReturnDeal(-1, R6_requestid,17);
		}else{
			Log.info("�ص������ţ�"+R7_trxid+",ԭ������ˮ�ţ�"+R6_requestid+",�ص������Ч,ƽ̨������,");
		}
		out.flush();
		out.close();
		return null;
	}
}
