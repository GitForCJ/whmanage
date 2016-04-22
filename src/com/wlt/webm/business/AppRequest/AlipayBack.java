package com.wlt.webm.business.AppRequest;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wlt.webm.business.AppRequest.alipay.AlipayNotify;

public class AlipayBack extends DispatchAction {

	/**
	 * ֧�����ص�
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		try {
			//������
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
			Log.info("���յ�֧����֪ͨ��Ϣ,,������:"+out_trade_no);
			
			// ��ȡ֧����POST����������Ϣ
			Map<String, String> params = new HashMap<String, String>();
			Map requestParams = request.getParameterMap();
			
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				params.put(name,valueStr);
			}
			
			Log.info("���յ�֧����֪ͨ��Ϣ,,������:"+out_trade_no+",,�ص�����:"+params.toString());
			
			if (AlipayNotify.verify(params)) {// ��֤�ɹ�
				// ����״̬
				String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
				
				Log.info("֧�����ص�֪ͨ����RSAУ��ɹ�������������:"+out_trade_no+",,����״̬:"+trade_status);
				
				if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
					
					PrintWriter out = response.getWriter();
					
					Log.info("֧�����ص�֪ͨ����RSAУ��ɹ�������������:"+out_trade_no+",,֧���ɹ����������ֵҵ�񷽷�");
					BaiduBack.services_oper(out_trade_no);
					
					out.println("success"); // �벻Ҫ�޸Ļ�ɾ��
					out.flush();
					out.close();
					Log.info("֧�����ص�֪ͨ����RSAУ��ɹ�������������:"+out_trade_no+",,��Ӧ֧����seccess,���");
				}
			}else{
				Log.info("֧�����ص�֪ͨ����RSAУ��ʧ�ܣ�����������:"+out_trade_no);
			}
		} catch (Exception e) {
			Log.info("֧�����ص�֪ͨ��ϵͳ�쳣,,ex:"+e);
		}
		return null;
	}
}
