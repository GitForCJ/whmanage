package com.wlt.webm.business.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.CheckAccountErrorBean;
import com.wlt.webm.business.bean.SysUserInterface;
import com.wlt.webm.business.form.CheckAccountErrorForm;
import com.wlt.webm.db.DBConfig;
import com.wlt.webm.db.DBService;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.ten.service.TenpayXmlPath;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.util.PageAttribute;
import com.wlt.webm.util.format.Formatter;


/**
 * 对账管理<br>
 */
public class CheckAccountErrorAction extends DispatchAction
{
	
	
	/**
	 * 对账列表
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
		CheckAccountErrorForm checkAcountErrorForm=(CheckAccountErrorForm)form;
		CheckAccountErrorBean checkAccountErrorBean= new CheckAccountErrorBean();
		PageAttribute page = new PageAttribute(checkAcountErrorForm.getCurPage(),Constant.PAGE_SIZE);
		String[] totalFee = checkAccountErrorBean.checkAccountErrorCount("wht_accountError_"+DateParser.getNowDateTable(),checkAcountErrorForm);
		page.setRsCount(Integer.parseInt(totalFee[1]));
		SysUserInterface sit = new SysUserInterface();
		List list = checkAccountErrorBean.listCheckAccountError("wht_accountError_"+DateParser.getNowDateTable(),checkAcountErrorForm,page);
		int curTotal = 0;
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			curTotal += Integer.parseInt(temp[4]);
		}
		request.setAttribute("curTotal", curTotal);
		request.setAttribute("totalFee", totalFee);
		request.setAttribute("itypeSel",getStingSel(sit.listInterfaceType(),"接口商")); 
		request.setAttribute("checkAccountError",checkAcountErrorForm); 
		request.setAttribute("page",page); 
		request.setAttribute("checkAccountErrorList",list); 
		return mapping.findForward("success");
	}
	public static String getStingSel(List list,String fix){
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("请选择"+fix+"[]");
		  for(Object tmp : list){
			String[] temp = (String[])tmp;
			sBuffer.append("|"+temp[1]+"["+temp[0]+"]");
		  }
		return sBuffer.toString();
	}
	 /**
     * 获取单条记录明细（AJAX）
     * @param mapping 
     * @param form 
     * @param request 
     * @param response 
     * @return ActionForward
	 * @throws IOException 
     * @throws Exception 
     */
    public ActionForward getCheckAccErrorInfo(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.setContentType("html/json; charset=GBK"); 
    	response.setCharacterEncoding("GBK");
    	PrintWriter pWriter = response.getWriter();
    	StringBuffer sBuffer = new StringBuffer();
    	CheckAccountErrorBean checkAccountErrorBean= new CheckAccountErrorBean();
    	try{
		SysUserForm loginUser = (SysUserForm) request.getSession().getAttribute(
		"userSession");
		if(null==loginUser){
			request.setAttribute("mess", "登录超时,请重新登录");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		String pid=request.getParameter("pid");
		String tradetime=request.getParameter("tradetime");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyMM");
		Date date=sdf1.parse(tradetime);
		String suffixName=sdf2.format(date);
		List list=checkAccountErrorBean.getCheckAccError(pid,suffixName);
		String[] str=(String[]) list.get(0);
		sBuffer.append("[{\"tradeserial\":\""+str[0]+"\"," +
				"\"externalserial\":\""+str[1]+"\"," +
				"\"tradeobject\":\""+str[2]+"\"," +
				"\"buyid\":\""+str[3]+"\"," +
				"\"tradefee\":\""+str[4]+"\","+
				"\"tradetime\":\""+str[5]+"\","+
				"\"StateOne\":\""+str[6]+"\","+
				"\"StateTwo\":\""+str[7]+"\","+
				"\"Contrast_state\":\""+str[8]+"\"}]");
		} catch (Exception e) {
			e.printStackTrace();
			sBuffer.delete(0, sBuffer.length());
		}
        pWriter.print(sBuffer.toString());
    	pWriter.flush();
    	pWriter.close();
        return null;
    }
	/**
	 * 导出对账明细
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
		CheckAccountErrorForm checkAcountErrorForm=(CheckAccountErrorForm)form;
		CheckAccountErrorBean checkAccountErrorBean= new CheckAccountErrorBean();
		PageAttribute page = new PageAttribute(checkAcountErrorForm.getCurPage(),Constant.PAGE_SIZE);
		String[] totalFee = checkAccountErrorBean.checkAccountErrorCount("wht_accountError_"+DateParser.getNowDateTable(),checkAcountErrorForm);
		page.setRsCount(Integer.parseInt(totalFee[1]));
		List list = checkAccountErrorBean.listCheckAccountError_EXCEL("wht_accountError_"+DateParser.getNowDateTable(),checkAcountErrorForm,page);
		//导出excel
		String[][] colTitles = new String[][] { {"编号","接口商", "内部流水号", "外部流水号", "交易号码", "交易金额","交易时间", "我方状态","对方状态","对比结果"} };
		int size = colTitles[0].length ;
		List  body = new ArrayList();
		//只取其中某些字段
		int  count=0;
		int sum1=0;
		for(Object tmp : list){
			String[] temp = (String[])tmp;
			String[] recode = new String[size];
			//取其中某些字段
			int i  = 0;
			recode[i++] = ++count+"";
			recode[i++] = temp[3];
			recode[i++] = temp[0];
			recode[i++] = temp[1];
			recode[i++] = temp[2];
			recode[i++] = Float.parseFloat(temp[4])/1000+"";
			recode[i++] = temp[5];
			recode[i++] = temp[6];
			recode[i++] = temp[7]; 
			recode[i++] = temp[8]; 
			sum1+= Integer.parseInt(temp[4]);
			body.add(recode);
		}
		String[] sum = new String[size];
		sum[4]="总计：";
		sum[5]=Formatter.format(sum1, Formatter.D1000F2);
		body.add(sum);
		Map rsMap = null;
		//转换成excel格式数据
		if(body.size()>0){
			rsMap = DataUtil.toNestedStringsListMap(1, body);
		}
		
		Excel excel = new Excel();
	    excel.setCols(colTitles[0].length);
	    excel.createCaption("对账明细报表");
	    excel.createColCaption(colTitles);
	    if(rsMap!=null){
	        excel.createBody(rsMap);
	    }
       // excel.createRemarks("交易明细");
	    String excelFileName = URLEncoder.encode("对账明细报表" + ".xls", "UTF-8");
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
            checkAcountErrorForm=null;
    		checkAccountErrorBean= null;
        }
		return mapping.findForward(null);
	}
	
}