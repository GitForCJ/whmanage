package com.wlt.webm.business.TZ.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.tp.ThreadPool;
import com.commsoft.epay.util.tp.ThreadProvider;
import com.wlt.webm.business.Business;
import com.wlt.webm.business.Processer;
import com.wlt.webm.business.TZ.bean.TopUpBusiness;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.scpdb.DBLog;
import com.wlt.webm.scpdb.DBService;
import com.wlt.webm.scputil.DateParser;
import com.wlt.webm.scputil.UniqueNo;
import com.wlt.webm.tool.Tools;

public class TZAction  extends DispatchAction{
	
    /**
     * 天作充值
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	public ActionForward tzFill(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String payNo=request.getParameter("tradeObject");
        String mon=Tools.yuanToFen(request.getParameter("money"));
        System.out.println("号码:"+payNo+"   金额:"+mon);
		String resultMsg="";
        String serverSeq = Constant.FACTORYID_TZ+DateParser.getNowDateTime().substring(6)+UniqueNo.getInstance().getNoTwo();
        String termAcct=null;
        String sepNo=Tools.getSeqNo(payNo);
        int payFee=Integer.parseInt(mon);
        String accNbr=null;
        String nowTime=Tools.getNow();
        //记录订单
//       DBService db = new DBService();
//       DBLog dbLog = new DBLog(db);
//       dbLog.insertOrderForm("000",termAcct,  sepNo,
//			payNo, payFee,"天作缴费",  accNbr,
//			nowTime,"",2);
        //扣押金账户的钱  给自己返佣金   给上级节点返佣金 并记录
        
        Business business=new TopUpBusiness(sepNo,payNo,mon,nowTime,serverSeq);
        new Processer(business).start();
        request.setAttribute("mess", "充值成功");
		return new ActionForward("/business/tztransfer.jsp");
	}
	
	

}
