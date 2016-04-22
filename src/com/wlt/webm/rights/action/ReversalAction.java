package com.wlt.webm.rights.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.rights.bean.Reversal;
import com.wlt.webm.rights.bean.SysUser;
import com.wlt.webm.rights.form.ReversalForm;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.PageAttribute;

/**
 * 冲正管理<br>
 */
public class ReversalAction extends DispatchAction {
	/**
	 * 冲正数列表
	 * 
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
		ReversalForm reversalForm = (ReversalForm) form;
		PageAttribute page = new PageAttribute(reversalForm.getCurPage(),
				Constant.PAGE_SIZE);
		Reversal rs = new Reversal();
		page.setRsCount(rs.ReversalSum(reversalForm));
		List list = rs.getReversalinfo(reversalForm,page);
		request.setAttribute("reversalList", list);
		request.setAttribute("page", page);
		request.setAttribute("reversalForm",reversalForm);
		request.setAttribute("flowvalue",Tools.FLOW_SWITCH_FLAG==0?"关闭":"开启");
		return new ActionForward("/rights/reversal.jsp");
	}

	/**
	 * 添加冲正次数信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addReversal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Reversal rs = new Reversal();
		String user_login = request.getParameter("user_login");
		String reversalcount = request.getParameter("reversalcount");
		String tradeTypes=request.getParameter("tradeTypes");
		SysUser user = new SysUser();
		SysUserForm userForm = user.getUserNoForm(user_login);
		if (userForm.getUserno() == null && userForm.getUsername() == null) {
			request.setAttribute("mess", "登陆账号不存在");
			return new ActionForward("/rights/reversaladd.jsp");
		} else {
			int count = rs.getReversalCountByUserLogin(userForm.getUserno(),
					userForm.getUsername(),tradeTypes);
			if (count >= 1) {
				request.setAttribute("mess", "已添加此账号!");
				return new ActionForward("/rights/reversaladd.jsp");
			}
		}
		request.setAttribute("mess", true == rs.saveReverSal(user_login,
				userForm.getUserno(), reversalcount,tradeTypes) ? "添加成功" : "添加失败");
		return new ActionForward("/rights/reversal.do?method=list");
	}

	/**
	 * 修改冲正次数信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward updateReversal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Reversal rs = new Reversal();
		String user_login = request.getParameter("user_login");
		String reversalcount = request.getParameter("reversalcount");
		String id=request.getParameter("id");
		request.setAttribute("mess", true == rs.updateReverSal(user_login,
				reversalcount,id) ? "修改成功" : "修改失败");
		return new ActionForward("/rights/reversal.do?method=list");
	}

	/**
	 * 打开修改冲正次数页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward openReversal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ids");
		if (null == id) {
			request.setAttribute("mess", "修改出错");
			return new ActionForward("/rights/reversal.jsp");
		}
		String[] str = id.split("#");
		request.setAttribute("str", str);
		return new ActionForward("/rights/reversalupdate.jsp");
	}
	
	/**
	 * 交通罚款配置信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward jtfklist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String flag=request.getParameter("flag");
		if(flag.equals("0")){//列表
		Reversal rs = new Reversal();
		request.setAttribute("jtfkList", rs.getjtfkList());
		return new ActionForward("/rights/jffkinterfacesetl.jsp");
		}else if(flag.equals("1")){//增加
			Reversal rs = new Reversal();
			String car=request.getParameter("car");
			String op=request.getParameter("op");
			String qd=request.getParameter("qd");
			if(rs.addJtfk(car, op, qd)){
				request.setAttribute("mess", "操作成功");
			}else{
				request.setAttribute("mess", "操作失败");
			}
			return new ActionForward("/rights/jtfksetadd.jsp");
		}else{//修改
			Reversal rs = new Reversal();
			request.setAttribute("jtfkList", rs.getjtfkList());
			return new ActionForward("/rights/jtfksetupdate.jsp");
		}
	}
	
	/**
	 * 冲正数列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward flowlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReversalForm reversalForm = (ReversalForm) form;
		PageAttribute page = new PageAttribute(reversalForm.getCurPage(),
				Constant.PAGE_SIZE);
		Reversal rs = new Reversal();
		page.setRsCount(rs.ReversalSum(reversalForm));
		List list = rs.getFlowinfo(reversalForm,page);
		request.setAttribute("reversalList", list);
		request.setAttribute("page", page);
		request.setAttribute("reversalForm",reversalForm);
		return new ActionForward("/rights/flowpriceset.jsp");
	}
	
	/**
	 * 增加流量产品和价格
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addFlowprice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Reversal rs = new Reversal();
		String user_login = request.getParameter("user_login");
		String reversalcount = request.getParameter("reversalcount");
		String tradeTypes=request.getParameter("tradeTypes");
		request.setAttribute("mess", true == rs.saveFlowprice(tradeTypes,reversalcount,user_login) ? "添加成功" : "添加失败");
		return new ActionForward("/rights/reversal.do?method=flowlist");
	}
	
	/**
	 * 打开修改流量产品和价格的页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward openflowprice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ids");
		if (null == id) {
			request.setAttribute("mess", "删除出错");
			return new ActionForward("/rights/flowpriceset.jsp");
		}
		Reversal rs = new Reversal();
		request.setAttribute("mess", true == rs.saveFlowprice(id) ? "删除成功" : "删除失败");
		return new ActionForward("/rights/reversal.do?method=flowlist");
	}
	
	/**
	 * 渠道商 省份统计质量信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return  
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward flowcount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id=request.getParameter("interTypes");
		String starttime=request.getParameter("startDate");
		String endtime=request.getParameter("endDate");
		String flag=request.getParameter("flag");
		if(null==starttime||"".equals(starttime)||null==endtime||"".equals(endtime)){
			request.setAttribute("mess","请选择开始时间结束");
			return new ActionForward("/rights/flowproportioncount.jsp");
		}
		DBService db=null;
		try{
		db=new DBService();
		if("0".equals(flag)){//查询重洗加载数据
		String sql0="delete from sys_flowcountRecord where 1=1";
		String sql="INSERT INTO sys_flowcountRecord(bishu,state,buyid,buyname,sa_id,sa_name)"+ 
        " SELECT COUNT(a.tradeserial) AS bishu,a.state,b.id,b.name,c.sa_id,c.sa_name"+
        " FROM wht_orderform_"+starttime.replace("-", "").substring(2, 6)+" a LEFT JOIN  sys_interface b"+
        " ON a.buyid=b.id LEFT JOIN sys_area c ON a.phone_pid=c.sa_id"+
        " WHERE "+(!"".equals(id)?"a.buyid="+id+" and ":"")+" a.tradetime>'"+starttime.replace("-", "")+"000000' AND a.tradetime<'"+endtime.replace("-", "")+"235959' and b.flag=2 GROUP BY a.state,a.buyid,a.phone_pid  ORDER BY a.buyid";
		db.setAutoCommit(false);
		db.update(sql0);
		db.update(sql);
		db.commit();
		db.setAutoCommit(true);
		}
		String tj="where 1=1 ";
//		if(null!=id&&!"".equals(id)){
//			tj+=" and buyid="+id;
//		}
//		if(null!=starttime&&!"".equals(starttime)){
//			tj+=" and riqi>='"+starttime.replace("-", "")+"' ";
//		}
//		if(null!=endtime&&!"".equals(endtime)){
//			tj+=" and riqi<='"+endtime.replace("-", "")+"' ";
//		}
		if("0".equals(flag)){//查询
			String sql1="(SELECT bishu,state,buyid,buyname,sa_id,sa_name FROM sys_flowcountRecord "+
			tj+") AS a,";
			String sql2="(SELECT buyid,sa_id,SUM(bishu) AS total FROM sys_flowcountRecord "+
			tj+" GROUP BY buyid,sa_id) AS b";
			
			String realsql="SELECT a.bishu,(CASE WHEN a.state='0' THEN '成功' WHEN a.state='1' THEN '失败' ELSE '异常' END)"+
	        ",a.buyid,a.buyname,a.sa_id,a.sa_name,b.total,ROUND(bishu/total*100,3) FROM "+
			sql1+sql2+" WHERE a.buyid=b.buyid AND a.sa_id=b.sa_id order by a.buyid,a.sa_id";
			
			List lists=db.getList(realsql);
		    request.setAttribute("lists", lists);
		}
		if("1".equals(flag)){//导出
			String sql1="(SELECT bishu,state,buyid,buyname,sa_id,sa_name FROM sys_flowcountRecord "+
			tj+") AS a,";
			String sql2="(SELECT buyid,sa_id,SUM(bishu) AS total FROM sys_flowcountRecord "+
			tj+" GROUP BY buyid,sa_id) AS b";
			
			String realsql="SELECT a.buyname,a.sa_name,(CASE WHEN a.state='0' THEN '成功' WHEN a.state='1' THEN '失败' ELSE '异常' END)"+
	        ",a.bishu,b.total,ROUND(bishu/total*100,3) FROM "+
			sql1+sql2+" WHERE a.buyid=b.buyid AND a.sa_id=b.sa_id order by a.buyid,a.sa_id";
			db=new DBService();
			List lists=db.getList(realsql);
	  		ArrayList list=new ArrayList();
	  		String[] headerNameList=new String[]{"渠道商","省份","状态","笔数","总量","比例%"};
	  		list.add(headerNameList);
	  		request.setAttribute("count",headerNameList.length);//总列数
	  		request.setAttribute("excelName","流量统计"+Tools.getNowDate());//输出的 excel 文件名称
	  		request.setAttribute("HeaderName","流量统计"); //表格标题名称
	  		request.setAttribute("HeaderList",list); //表格标头 类别
	  		request.setAttribute("DataList",lists); //数据列表
	    	return new ActionForward("/rights/flowdownload.jsp");
		}
		}catch (Exception e) {
			Log.error(e.toString());
			if(null!=db){
				db.rollback();
			}
			throw new Exception("流量统计异常");
		}finally{
			if(null!=db){
				db.close();
			}
		}
		request.setAttribute("id", id);
		request.setAttribute("starttime",starttime);
		request.setAttribute("endtime",endtime);
		return new ActionForward("/rights/flowproportioncount.jsp");
	}
	
	/**
	 * 按照接口商 交易时间 统计状态占比
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward flowuserst(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String user=request.getParameter("user");
		String starttime=request.getParameter("startDate");
		String endtime=request.getParameter("endDate");
		String sql="SELECT userno,(CASE WHEN service='0009' THEN '联通' WHEN service='0010' THEN '移动' WHEN service='0011' THEN '电信' ELSE '未知' END) AS fuwu,"
		+" (CASE WHEN state='0' THEN '成功' WHEN state='1' THEN '失败' ELSE '异常' END) AS st,"
		+"COUNT(tradeserial) FROM wht_orderform_"+starttime.replace("-", "").substring(2,6)+" WHERE  userno='"
		+user+"' AND service IN('0009','0010','0011') and tradetime>'"+starttime.replace("-", "")+"000000' AND tradetime<'"+endtime.replace("-", "")
		+"235959' GROUP BY userno,service,state";
		DBService db=null;
		int count =0;
		try{
		db=new DBService();
		List datas =db.getList(sql);
		if(null!=datas&&datas.size()>0){
			for(Object obj:datas){
				String[] str=(String[])obj;
				count+=Integer.parseInt(str[3]);
			}
			request.setAttribute("datas", datas);
			request.setAttribute("count", count);
		}
		}catch (Exception e) {
			Log.error("按照接口商 交易时间 统计状态占比:"+e.toString());
		}finally{
			if(null!=db){
				db.close();
			}
		}
		request.setAttribute("id", user);
		request.setAttribute("starttime",starttime);
		request.setAttribute("endtime",endtime);
		return new ActionForward("/rights/flowusertatecount.jsp");
	}
	
	/**
	 * 查询交易时间状况
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward flowtradetime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String user=request.getParameter("user");
		String starttime=request.getParameter("startDate");
		String endtime=request.getParameter("endDate");
		String flag=request.getParameter("flag");
		DBService db=null;
		try{
		db=new DBService();
		int count =0;
		String tj="where 1=1 ";
		if(null!=user&&!"".equals(user)){
			tj+=" and userno="+user;
		}
		if(null!=starttime&&!"".equals(starttime)){
			tj+=" and riqi>='"+starttime.replace("-", "")+"' ";
		}
		if(null!=endtime&&!"".equals(endtime)){
			tj+=" and riqi<='"+endtime.replace("-", "")+"' ";
		}
		if("0".equals(flag)){//查询
			String realsql="SELECT a.riqi,a.userno,a.timerange,a.num ,b.total,ROUND(a.num/b.total*100,3) FROM "
			+"(SELECT riqi,userno,timerange,num FROM sys_flowtimerecord "+tj+") AS a,"
			+"(SELECT userno,SUM(num) AS total FROM sys_flowtimerecord "+tj+" GROUP BY userno) AS b "+ 
			" WHERE a.userno=b.userno order by a.userno";
			List lists=db.getList(realsql);
		    request.setAttribute("datas", lists);
		}
		if("1".equals(flag)){//导出
			String realsql="SELECT a.riqi,a.userno,a.timerange,a.num ,b.total,ROUND(a.num/b.total*100,3) FROM "
				+"(SELECT riqi,userno,timerange,num FROM sys_flowtimerecord "+tj+") AS a,"
				+"(SELECT userno,SUM(num) AS total FROM sys_flowtimerecord "+tj+" GROUP BY userno) AS b "+ 
				" WHERE a.userno=b.userno order by a.userno";
			db=new DBService();
			List lists=db.getList(realsql);
	  		ArrayList list=new ArrayList();
	  		String[] headerNameList=new String[]{"日期","渠道商编号","交易时间范围","笔数","总数","比例%"};
	  		list.add(headerNameList);
	  		request.setAttribute("count",headerNameList.length);//总列数
	  		request.setAttribute("excelName","接口商流量交易时间统计");//输出的 excel 文件名称
	  		request.setAttribute("HeaderName","接口商流量交易时间统计"); //表格标题名称
	  		request.setAttribute("HeaderList",list); //表格标头 类别
	  		request.setAttribute("DataList",lists); //数据列表
	  		return new ActionForward("/rights/flowdownload.jsp");
		}
		}catch (Exception e) {
			Log.error("查询交易时间比例 :"+e.toString());
		}finally{
			if(null!=db){
				db.close();
			}
		}
		request.setAttribute("id", user);
		request.setAttribute("starttime",starttime);
		request.setAttribute("endtime",endtime);
		return new ActionForward("/rights/flowtradetimerange.jsp");
	}
	
	/**
	 * 修改流量压单标示
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cgflowswitch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 if(0==Tools.FLOW_SWITCH_FLAG){
			 Tools.FLOW_SWITCH_FLAG=1;
		 }else{
			DBService db=null;
			try {
				db=new DBService();
				//腾讯
				List<String[]> arsList=db.getList("SELECT pid,interid FROM sys_flowinterfaceMaping WHERE TYPE=2 AND pid IN (SELECT SUBSTRING_INDEX(exp1,'#',-1) FROM tencent_orders WHERE operator=2 GROUP BY SUBSTRING_INDEX(exp1,'#',-1))");
				if(arsList!=null && arsList.size()>0){
					for(String[] strs:arsList){
						String sqlString="UPDATE tencent_orders SET exp1='"+(strs[1]+"#"+strs[0])+"' WHERE operator=2 AND SUBSTRING_INDEX(exp1,'#',-1)='"+strs[0]+"'";
						Log.info("腾讯,压单关闭操作,修改订单接口sql:"+sqlString);
						if(db.update(sqlString)<=0){
							 Log.info("腾讯,操作失败,修改压单订单接口错误,,当前压单状态:"+Tools.FLOW_SWITCH_FLAG);
							 PrintWriter wr=response.getWriter();
							 wr.print(Tools.FLOW_SWITCH_FLAG);
							 wr.flush();
							 wr.close();
							 return null;
						}
					}
					int abc=0;
					Calendar c = Calendar.getInstance();
					int today = c.get(c.DAY_OF_MONTH);
					if(today ==1){
						c.setTime(new Date());//把当前时间赋给日历
						c.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
						String fots=new SimpleDateFormat("yyyyMMdd").format(c.getTime()); 
						abc=abc+db.update("UPDATE wht_orderform_"+fots.substring(2,6)+" ors,tencent_orders ten  SET ors.buyid=SUBSTRING_INDEX(ten.exp1,'#',1) WHERE ten.seqNo1=ors.tradeserial AND ten.paipai_dealid=ors.writecheck");
					}
					abc=abc+db.update("UPDATE wht_orderform_"+Tools.getDate().substring(2,6)+" ors,tencent_orders ten  SET ors.buyid=SUBSTRING_INDEX(ten.exp1,'#',1) WHERE ten.seqNo1=ors.tradeserial AND ten.paipai_dealid=ors.writecheck");
					if(abc<=0){
						 Log.info("腾讯,操作失败,修改订单表接口错误,,当前压单状态:"+Tools.FLOW_SWITCH_FLAG);
						 PrintWriter wr=response.getWriter();
						 wr.print(Tools.FLOW_SWITCH_FLAG);
						 wr.flush();
						 wr.close();
						 return null;
					}
				}
				//京东
				List<String[]> jd_arsList_lt=db.getList("SELECT pid,interid FROM sys_flowinterfaceMaping WHERE TYPE=2 AND pid IN (SELECT SUBSTRING_INDEX(exp1,'#',-1) FROM jd_orders WHERE wh_mutCode=2 GROUP BY SUBSTRING_INDEX(exp1,'#',-1))");
				List<String[]> jd_arsList_yd=db.getList("SELECT pid,interid FROM sys_flowinterfaceMaping WHERE TYPE=1 AND pid IN (SELECT SUBSTRING_INDEX(exp1,'#',-1) FROM jd_orders WHERE wh_mutCode=1 GROUP BY SUBSTRING_INDEX(exp1,'#',-1))");
				List<String[]> jd_arsList_dx=db.getList("SELECT pid,interid FROM sys_flowinterfaceMaping WHERE TYPE=0 AND pid IN (SELECT SUBSTRING_INDEX(exp1,'#',-1) FROM jd_orders WHERE wh_mutCode=0 GROUP BY SUBSTRING_INDEX(exp1,'#',-1))");
				if(jd_arsList_lt!=null && jd_arsList_lt.size()>0){
					for(String[] jd_strs:jd_arsList_lt){
						String jd_sql="UPDATE jd_orders SET exp1='"+(jd_strs[1]+"#"+jd_strs[0])+"' WHERE wh_mutCode=2 AND SUBSTRING_INDEX(exp1,'#',-1)='"+jd_strs[0]+"'";
						Log.info("京东,压单关闭操作,联通,修改订单接口sql:"+jd_sql);
						if(db.update(jd_sql)<=0){
							 Log.info("京东,操作失败,修改压单订单接口错误,联通,,当前压单状态:"+Tools.FLOW_SWITCH_FLAG);
							 PrintWriter wr=response.getWriter();
							 wr.print(Tools.FLOW_SWITCH_FLAG);
							 wr.flush();
							 wr.close();
							 return null;
						}
					}
				}
				if(jd_arsList_yd!=null && jd_arsList_yd.size()>0){
					for(String[] jd_strs:jd_arsList_yd){
						String jd_sql="UPDATE jd_orders SET exp1='"+(jd_strs[1]+"#"+jd_strs[0])+"' WHERE wh_mutCode=1 AND SUBSTRING_INDEX(exp1,'#',-1)='"+jd_strs[0]+"'";
						Log.info("京东,压单关闭操作,移动,修改订单接口sql:"+jd_sql);
						if(db.update(jd_sql)<=0){
							 Log.info("京东,操作失败,修改压单订单接口错误,移动,,当前压单状态:"+Tools.FLOW_SWITCH_FLAG);
							 PrintWriter wr=response.getWriter();
							 wr.print(Tools.FLOW_SWITCH_FLAG);
							 wr.flush();
							 wr.close();
							 return null;
						}
					}
				}
				if(jd_arsList_dx!=null && jd_arsList_dx.size()>0){
					for(String[] jd_strs:jd_arsList_dx){
						String jd_sql="UPDATE jd_orders SET exp1='"+(jd_strs[1]+"#"+jd_strs[0])+"' WHERE wh_mutCode=0 AND SUBSTRING_INDEX(exp1,'#',-1)='"+jd_strs[0]+"'";
						Log.info("京东,压单关闭操作,电信,修改订单接口sql:"+jd_sql);
						if(db.update(jd_sql)<=0){
							 Log.info("京东,操作失败,修改压单订单接口错误,电信,,当前压单状态:"+Tools.FLOW_SWITCH_FLAG);
							 PrintWriter wr=response.getWriter();
							 wr.print(Tools.FLOW_SWITCH_FLAG);
							 wr.flush();
							 wr.close();
							 return null;
						}
					}
				}
				if((jd_arsList_lt!=null && jd_arsList_lt.size()>0) || (jd_arsList_yd!=null && jd_arsList_yd.size()>0) || (jd_arsList_dx!=null && jd_arsList_dx.size()>0)){
					int jd_abc=0;
					Calendar jd_c = Calendar.getInstance();
					int jd_today = jd_c.get(jd_c.DAY_OF_MONTH);
					if(jd_today ==1){
						jd_c.setTime(new Date());//把当前时间赋给日历
						jd_c.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
						String jd_fots=new SimpleDateFormat("yyyyMMdd").format(jd_c.getTime()); 
						jd_abc=jd_abc+db.update("UPDATE wht_orderform_"+jd_fots.substring(2,6)+" ors,jd_orders ten  SET ors.buyid=SUBSTRING_INDEX(ten.exp1,'#',1) WHERE ten.wh_order_num=ors.tradeserial AND ten.fillOrderNo=ors.writecheck");
					}
					jd_abc=jd_abc+db.update("UPDATE wht_orderform_"+Tools.getDate().substring(2,6)+" ors,jd_orders ten  SET ors.buyid=SUBSTRING_INDEX(ten.exp1,'#',1) WHERE ten.wh_order_num=ors.tradeserial AND ten.fillOrderNo=ors.writecheck");
					if(jd_abc<=0){
						 Log.info("京东,操作失败,修改订单表接口错误,,当前压单状态:"+Tools.FLOW_SWITCH_FLAG);
						 PrintWriter wr=response.getWriter();
						 wr.print(Tools.FLOW_SWITCH_FLAG);
						 wr.flush();
						 wr.close();
						 return null;
					}
				}
			} catch (Exception e) {
				Log.error("压单关闭操作,修改当前压单订单接口异常，，ex:"+e);
				PrintWriter wr=response.getWriter();
				wr.print(Tools.FLOW_SWITCH_FLAG);
				wr.flush();
				wr.close();
				return null;
			}finally{
				if(db!=null){
					db.close();
					db=null;
				}
			}
			Tools.FLOW_SWITCH_FLAG=0;
		 }
		 Log.info("修改流量压单阀值为:"+Tools.FLOW_SWITCH_FLAG);
		 PrintWriter wr=response.getWriter();
		 wr.print(Tools.FLOW_SWITCH_FLAG);
		 wr.flush();
		 wr.close();
		 return null;
	}
	
	/**
	 * 修改短信通道
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward cgdxswitch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 String flag=request.getParameter("dx");
		 if(null!=flag&&!"".equals(flag))
		 Tools.DUANXIN_SWITCH_FLAG=Integer.parseInt(flag);
		 Log.info("短信通道修改为:"+flag);
		 PrintWriter wr=response.getWriter();
		 wr.print(null==flag?-1:Tools.DUANXIN_SWITCH_FLAG);
		 wr.flush();
		 wr.close();
		 return null;
		 }
	
}