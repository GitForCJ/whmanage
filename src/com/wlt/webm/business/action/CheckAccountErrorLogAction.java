package com.wlt.webm.business.action;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.ejet.util.DataUtil;
import com.ejet.util.Excel;
import com.wlt.webm.business.bean.CheckAccountErrorLogBean;
import com.wlt.webm.business.bean.SysUserInterface;
import com.wlt.webm.business.form.CheckAccountErrorLogForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.util.PageAttribute;


/**
 * ������־����<br>
 */
public class CheckAccountErrorLogAction extends DispatchAction
{
	
	
	/**
	 * ������־�б�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckAccountErrorLogForm checkAcountErrorLogForm=(CheckAccountErrorLogForm)form;
		CheckAccountErrorLogBean checkAccountErrorLogBean= new CheckAccountErrorLogBean();
		PageAttribute page = new PageAttribute(checkAcountErrorLogForm.getCurPage(),Constant.PAGE_SIZE);
		String[] totalFee = checkAccountErrorLogBean.checkAccountErrorLogCount("wht_accountErrorLog",checkAcountErrorLogForm);
		page.setRsCount(Integer.parseInt(totalFee[0]));
		SysUserInterface sit = new SysUserInterface();
		List list = checkAccountErrorLogBean.listCheckAccountErrorLog("wht_accountErrorLog",checkAcountErrorLogForm,page);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("itypeSel",getStingSel(sit.listInterfaceType(),"�ӿ���")); 
		request.setAttribute("checkAccountErrorLog",checkAcountErrorLogForm); 
		request.setAttribute("page",page); 
		request.setAttribute("checkAccountErrorLogList",list); 
		return new ActionForward("/task/checkAccountErrorLogList.jsp");
	}
	public static String getStingSel(List list,String fix){
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("��ѡ��"+fix+"[]");
		  for(Object tmp : list){
			String[] temp = (String[])tmp;
			sBuffer.append("|"+temp[1]+"["+temp[0]+"]");
		  }
		return sBuffer.toString();
	}
	/**
	 * ����������־��ϸ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward excelExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckAccountErrorLogForm checkAcountErrorLogForm=(CheckAccountErrorLogForm)form;
		CheckAccountErrorLogBean checkAccountErrorLogBean= new CheckAccountErrorLogBean();
		PageAttribute page = new PageAttribute(checkAcountErrorLogForm.getCurPage(),Constant.PAGE_SIZE);
		String[] totalFee = checkAccountErrorLogBean.checkAccountErrorLogCount("wht_accountErrorLog",checkAcountErrorLogForm);
		page.setRsCount(Integer.parseInt(totalFee[0]));
		List list = checkAccountErrorLogBean.listCheckAccountErrorLog_EXCEL("wht_accountErrorLog",checkAcountErrorLogForm,page);
		//����excel
		String[][] colTitles = new String[][] { {"���","�ӿ���", "��Ŀ����", "����ִ������", "���˽��"} };
		int size = colTitles[0].length ;
		List  body = new ArrayList();
		//ֻȡ����ĳЩ�ֶ�
		int  count=0;
		int sum1=0;
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			String[] recode = new String[size];
			//ȡ����ĳЩ�ֶ�
			int i  = 0;
			recode[i++] = ++count+"";
			recode[i++] = temp[0];
			recode[i++] = temp[1];
			recode[i++] = temp[2];
			recode[i++] = temp[3];
			body.add(recode);
		}
		Map rsMap = null;
		//ת����excel��ʽ����
		if(body.size()>0){
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}
		
		Excel excel = new Excel();
	    excel.setCols(colTitles[0].length);
	    excel.createCaption("������־��ϸ����");
	    excel.createColCaption(colTitles);
	    if(rsMap!=null){
	        excel.createBody(rsMap);
	    }
       // excel.createRemarks("������ϸ");
	    String excelFileName = URLEncoder.encode("������־��ϸ����" + ".xls", "UTF-8");
        response.addHeader("Content-Disposition", "attachment; filename=" + excelFileName);
        OutputStream out = null;
        try
        {
            out = response.getOutputStream();
            excel.createFile(out);
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch(IOException e)
                {
                }
            }
            checkAccountErrorLogBean=null;
            checkAcountErrorLogForm= null;
        }
		return mapping.findForward(null);
	}
	public ActionForward changeCheckAccountErrorLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		CheckAccountErrorLogForm checkAcountErrorLogForm=(CheckAccountErrorLogForm)form;
		CheckAccountErrorLogBean checkAccountErrorLogBean= new CheckAccountErrorLogBean();
		checkAccountErrorLogBean.changeCheckAccountErrorLog(checkAcountErrorLogForm);//ִ���ض���
		PageAttribute page = new PageAttribute(checkAcountErrorLogForm.getCurPage(),Constant.PAGE_SIZE);
		String[] totalFee = checkAccountErrorLogBean.checkAccountErrorLogCount("wht_accountErrorLog",checkAcountErrorLogForm);
		page.setRsCount(Integer.parseInt(totalFee[0]));
		SysUserInterface sit = new SysUserInterface();
		List list = checkAccountErrorLogBean.listCheckAccountErrorLog("wht_accountErrorLog",checkAcountErrorLogForm,page);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("itypeSel",getStingSel(sit.listInterfaceType(),"�ӿ���")); 
		request.setAttribute("checkAccountErrorLog",checkAcountErrorLogForm); 
		request.setAttribute("page",page); 
		request.setAttribute("checkAccountErrorLogList",list); 
		return new ActionForward("/task/checkAccountErrorLogList.jsp");
	}
	
}