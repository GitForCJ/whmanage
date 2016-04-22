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
 * ��������<br>
 */
public class ReversalAction extends DispatchAction {
	/**
	 * �������б�
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
		request.setAttribute("flowvalue",Tools.FLOW_SWITCH_FLAG==0?"�ر�":"����");
		return new ActionForward("/rights/reversal.jsp");
	}

	/**
	 * ��ӳ���������Ϣ
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
			request.setAttribute("mess", "��½�˺Ų�����");
			return new ActionForward("/rights/reversaladd.jsp");
		} else {
			int count = rs.getReversalCountByUserLogin(userForm.getUserno(),
					userForm.getUsername(),tradeTypes);
			if (count >= 1) {
				request.setAttribute("mess", "����Ӵ��˺�!");
				return new ActionForward("/rights/reversaladd.jsp");
			}
		}
		request.setAttribute("mess", true == rs.saveReverSal(user_login,
				userForm.getUserno(), reversalcount,tradeTypes) ? "��ӳɹ�" : "���ʧ��");
		return new ActionForward("/rights/reversal.do?method=list");
	}

	/**
	 * �޸ĳ���������Ϣ
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
				reversalcount,id) ? "�޸ĳɹ�" : "�޸�ʧ��");
		return new ActionForward("/rights/reversal.do?method=list");
	}

	/**
	 * ���޸ĳ�������ҳ��
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
			request.setAttribute("mess", "�޸ĳ���");
			return new ActionForward("/rights/reversal.jsp");
		}
		String[] str = id.split("#");
		request.setAttribute("str", str);
		return new ActionForward("/rights/reversalupdate.jsp");
	}
	
	/**
	 * ��ͨ����������Ϣ
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
		if(flag.equals("0")){//�б�
		Reversal rs = new Reversal();
		request.setAttribute("jtfkList", rs.getjtfkList());
		return new ActionForward("/rights/jffkinterfacesetl.jsp");
		}else if(flag.equals("1")){//����
			Reversal rs = new Reversal();
			String car=request.getParameter("car");
			String op=request.getParameter("op");
			String qd=request.getParameter("qd");
			if(rs.addJtfk(car, op, qd)){
				request.setAttribute("mess", "�����ɹ�");
			}else{
				request.setAttribute("mess", "����ʧ��");
			}
			return new ActionForward("/rights/jtfksetadd.jsp");
		}else{//�޸�
			Reversal rs = new Reversal();
			request.setAttribute("jtfkList", rs.getjtfkList());
			return new ActionForward("/rights/jtfksetupdate.jsp");
		}
	}
	
	/**
	 * �������б�
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
	 * ����������Ʒ�ͼ۸�
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
		request.setAttribute("mess", true == rs.saveFlowprice(tradeTypes,reversalcount,user_login) ? "��ӳɹ�" : "���ʧ��");
		return new ActionForward("/rights/reversal.do?method=flowlist");
	}
	
	/**
	 * ���޸�������Ʒ�ͼ۸��ҳ��
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
			request.setAttribute("mess", "ɾ������");
			return new ActionForward("/rights/flowpriceset.jsp");
		}
		Reversal rs = new Reversal();
		request.setAttribute("mess", true == rs.saveFlowprice(id) ? "ɾ���ɹ�" : "ɾ��ʧ��");
		return new ActionForward("/rights/reversal.do?method=flowlist");
	}
	
	/**
	 * ������ ʡ��ͳ��������Ϣ
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
			request.setAttribute("mess","��ѡ��ʼʱ�����");
			return new ActionForward("/rights/flowproportioncount.jsp");
		}
		DBService db=null;
		try{
		db=new DBService();
		if("0".equals(flag)){//��ѯ��ϴ��������
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
		if("0".equals(flag)){//��ѯ
			String sql1="(SELECT bishu,state,buyid,buyname,sa_id,sa_name FROM sys_flowcountRecord "+
			tj+") AS a,";
			String sql2="(SELECT buyid,sa_id,SUM(bishu) AS total FROM sys_flowcountRecord "+
			tj+" GROUP BY buyid,sa_id) AS b";
			
			String realsql="SELECT a.bishu,(CASE WHEN a.state='0' THEN '�ɹ�' WHEN a.state='1' THEN 'ʧ��' ELSE '�쳣' END)"+
	        ",a.buyid,a.buyname,a.sa_id,a.sa_name,b.total,ROUND(bishu/total*100,3) FROM "+
			sql1+sql2+" WHERE a.buyid=b.buyid AND a.sa_id=b.sa_id order by a.buyid,a.sa_id";
			
			List lists=db.getList(realsql);
		    request.setAttribute("lists", lists);
		}
		if("1".equals(flag)){//����
			String sql1="(SELECT bishu,state,buyid,buyname,sa_id,sa_name FROM sys_flowcountRecord "+
			tj+") AS a,";
			String sql2="(SELECT buyid,sa_id,SUM(bishu) AS total FROM sys_flowcountRecord "+
			tj+" GROUP BY buyid,sa_id) AS b";
			
			String realsql="SELECT a.buyname,a.sa_name,(CASE WHEN a.state='0' THEN '�ɹ�' WHEN a.state='1' THEN 'ʧ��' ELSE '�쳣' END)"+
	        ",a.bishu,b.total,ROUND(bishu/total*100,3) FROM "+
			sql1+sql2+" WHERE a.buyid=b.buyid AND a.sa_id=b.sa_id order by a.buyid,a.sa_id";
			db=new DBService();
			List lists=db.getList(realsql);
	  		ArrayList list=new ArrayList();
	  		String[] headerNameList=new String[]{"������","ʡ��","״̬","����","����","����%"};
	  		list.add(headerNameList);
	  		request.setAttribute("count",headerNameList.length);//������
	  		request.setAttribute("excelName","����ͳ��"+Tools.getNowDate());//����� excel �ļ�����
	  		request.setAttribute("HeaderName","����ͳ��"); //����������
	  		request.setAttribute("HeaderList",list); //����ͷ ���
	  		request.setAttribute("DataList",lists); //�����б�
	    	return new ActionForward("/rights/flowdownload.jsp");
		}
		}catch (Exception e) {
			Log.error(e.toString());
			if(null!=db){
				db.rollback();
			}
			throw new Exception("����ͳ���쳣");
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
	 * ���սӿ��� ����ʱ�� ͳ��״̬ռ��
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
		String sql="SELECT userno,(CASE WHEN service='0009' THEN '��ͨ' WHEN service='0010' THEN '�ƶ�' WHEN service='0011' THEN '����' ELSE 'δ֪' END) AS fuwu,"
		+" (CASE WHEN state='0' THEN '�ɹ�' WHEN state='1' THEN 'ʧ��' ELSE '�쳣' END) AS st,"
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
			Log.error("���սӿ��� ����ʱ�� ͳ��״̬ռ��:"+e.toString());
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
	 * ��ѯ����ʱ��״��
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
		if("0".equals(flag)){//��ѯ
			String realsql="SELECT a.riqi,a.userno,a.timerange,a.num ,b.total,ROUND(a.num/b.total*100,3) FROM "
			+"(SELECT riqi,userno,timerange,num FROM sys_flowtimerecord "+tj+") AS a,"
			+"(SELECT userno,SUM(num) AS total FROM sys_flowtimerecord "+tj+" GROUP BY userno) AS b "+ 
			" WHERE a.userno=b.userno order by a.userno";
			List lists=db.getList(realsql);
		    request.setAttribute("datas", lists);
		}
		if("1".equals(flag)){//����
			String realsql="SELECT a.riqi,a.userno,a.timerange,a.num ,b.total,ROUND(a.num/b.total*100,3) FROM "
				+"(SELECT riqi,userno,timerange,num FROM sys_flowtimerecord "+tj+") AS a,"
				+"(SELECT userno,SUM(num) AS total FROM sys_flowtimerecord "+tj+" GROUP BY userno) AS b "+ 
				" WHERE a.userno=b.userno order by a.userno";
			db=new DBService();
			List lists=db.getList(realsql);
	  		ArrayList list=new ArrayList();
	  		String[] headerNameList=new String[]{"����","�����̱��","����ʱ�䷶Χ","����","����","����%"};
	  		list.add(headerNameList);
	  		request.setAttribute("count",headerNameList.length);//������
	  		request.setAttribute("excelName","�ӿ�����������ʱ��ͳ��");//����� excel �ļ�����
	  		request.setAttribute("HeaderName","�ӿ�����������ʱ��ͳ��"); //����������
	  		request.setAttribute("HeaderList",list); //����ͷ ���
	  		request.setAttribute("DataList",lists); //�����б�
	  		return new ActionForward("/rights/flowdownload.jsp");
		}
		}catch (Exception e) {
			Log.error("��ѯ����ʱ����� :"+e.toString());
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
	 * �޸�����ѹ����ʾ
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
				//��Ѷ
				List<String[]> arsList=db.getList("SELECT pid,interid FROM sys_flowinterfaceMaping WHERE TYPE=2 AND pid IN (SELECT SUBSTRING_INDEX(exp1,'#',-1) FROM tencent_orders WHERE operator=2 GROUP BY SUBSTRING_INDEX(exp1,'#',-1))");
				if(arsList!=null && arsList.size()>0){
					for(String[] strs:arsList){
						String sqlString="UPDATE tencent_orders SET exp1='"+(strs[1]+"#"+strs[0])+"' WHERE operator=2 AND SUBSTRING_INDEX(exp1,'#',-1)='"+strs[0]+"'";
						Log.info("��Ѷ,ѹ���رղ���,�޸Ķ����ӿ�sql:"+sqlString);
						if(db.update(sqlString)<=0){
							 Log.info("��Ѷ,����ʧ��,�޸�ѹ�������ӿڴ���,,��ǰѹ��״̬:"+Tools.FLOW_SWITCH_FLAG);
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
						c.setTime(new Date());//�ѵ�ǰʱ�丳������
						c.add(Calendar.DAY_OF_MONTH, -1);  //����Ϊǰһ��
						String fots=new SimpleDateFormat("yyyyMMdd").format(c.getTime()); 
						abc=abc+db.update("UPDATE wht_orderform_"+fots.substring(2,6)+" ors,tencent_orders ten  SET ors.buyid=SUBSTRING_INDEX(ten.exp1,'#',1) WHERE ten.seqNo1=ors.tradeserial AND ten.paipai_dealid=ors.writecheck");
					}
					abc=abc+db.update("UPDATE wht_orderform_"+Tools.getDate().substring(2,6)+" ors,tencent_orders ten  SET ors.buyid=SUBSTRING_INDEX(ten.exp1,'#',1) WHERE ten.seqNo1=ors.tradeserial AND ten.paipai_dealid=ors.writecheck");
					if(abc<=0){
						 Log.info("��Ѷ,����ʧ��,�޸Ķ�����ӿڴ���,,��ǰѹ��״̬:"+Tools.FLOW_SWITCH_FLAG);
						 PrintWriter wr=response.getWriter();
						 wr.print(Tools.FLOW_SWITCH_FLAG);
						 wr.flush();
						 wr.close();
						 return null;
					}
				}
				//����
				List<String[]> jd_arsList_lt=db.getList("SELECT pid,interid FROM sys_flowinterfaceMaping WHERE TYPE=2 AND pid IN (SELECT SUBSTRING_INDEX(exp1,'#',-1) FROM jd_orders WHERE wh_mutCode=2 GROUP BY SUBSTRING_INDEX(exp1,'#',-1))");
				List<String[]> jd_arsList_yd=db.getList("SELECT pid,interid FROM sys_flowinterfaceMaping WHERE TYPE=1 AND pid IN (SELECT SUBSTRING_INDEX(exp1,'#',-1) FROM jd_orders WHERE wh_mutCode=1 GROUP BY SUBSTRING_INDEX(exp1,'#',-1))");
				List<String[]> jd_arsList_dx=db.getList("SELECT pid,interid FROM sys_flowinterfaceMaping WHERE TYPE=0 AND pid IN (SELECT SUBSTRING_INDEX(exp1,'#',-1) FROM jd_orders WHERE wh_mutCode=0 GROUP BY SUBSTRING_INDEX(exp1,'#',-1))");
				if(jd_arsList_lt!=null && jd_arsList_lt.size()>0){
					for(String[] jd_strs:jd_arsList_lt){
						String jd_sql="UPDATE jd_orders SET exp1='"+(jd_strs[1]+"#"+jd_strs[0])+"' WHERE wh_mutCode=2 AND SUBSTRING_INDEX(exp1,'#',-1)='"+jd_strs[0]+"'";
						Log.info("����,ѹ���رղ���,��ͨ,�޸Ķ����ӿ�sql:"+jd_sql);
						if(db.update(jd_sql)<=0){
							 Log.info("����,����ʧ��,�޸�ѹ�������ӿڴ���,��ͨ,,��ǰѹ��״̬:"+Tools.FLOW_SWITCH_FLAG);
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
						Log.info("����,ѹ���رղ���,�ƶ�,�޸Ķ����ӿ�sql:"+jd_sql);
						if(db.update(jd_sql)<=0){
							 Log.info("����,����ʧ��,�޸�ѹ�������ӿڴ���,�ƶ�,,��ǰѹ��״̬:"+Tools.FLOW_SWITCH_FLAG);
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
						Log.info("����,ѹ���رղ���,����,�޸Ķ����ӿ�sql:"+jd_sql);
						if(db.update(jd_sql)<=0){
							 Log.info("����,����ʧ��,�޸�ѹ�������ӿڴ���,����,,��ǰѹ��״̬:"+Tools.FLOW_SWITCH_FLAG);
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
						jd_c.setTime(new Date());//�ѵ�ǰʱ�丳������
						jd_c.add(Calendar.DAY_OF_MONTH, -1);  //����Ϊǰһ��
						String jd_fots=new SimpleDateFormat("yyyyMMdd").format(jd_c.getTime()); 
						jd_abc=jd_abc+db.update("UPDATE wht_orderform_"+jd_fots.substring(2,6)+" ors,jd_orders ten  SET ors.buyid=SUBSTRING_INDEX(ten.exp1,'#',1) WHERE ten.wh_order_num=ors.tradeserial AND ten.fillOrderNo=ors.writecheck");
					}
					jd_abc=jd_abc+db.update("UPDATE wht_orderform_"+Tools.getDate().substring(2,6)+" ors,jd_orders ten  SET ors.buyid=SUBSTRING_INDEX(ten.exp1,'#',1) WHERE ten.wh_order_num=ors.tradeserial AND ten.fillOrderNo=ors.writecheck");
					if(jd_abc<=0){
						 Log.info("����,����ʧ��,�޸Ķ�����ӿڴ���,,��ǰѹ��״̬:"+Tools.FLOW_SWITCH_FLAG);
						 PrintWriter wr=response.getWriter();
						 wr.print(Tools.FLOW_SWITCH_FLAG);
						 wr.flush();
						 wr.close();
						 return null;
					}
				}
			} catch (Exception e) {
				Log.error("ѹ���رղ���,�޸ĵ�ǰѹ�������ӿ��쳣����ex:"+e);
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
		 Log.info("�޸�����ѹ����ֵΪ:"+Tools.FLOW_SWITCH_FLAG);
		 PrintWriter wr=response.getWriter();
		 wr.print(Tools.FLOW_SWITCH_FLAG);
		 wr.flush();
		 wr.close();
		 return null;
	}
	
	/**
	 * �޸Ķ���ͨ��
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
		 Log.info("����ͨ���޸�Ϊ:"+flag);
		 PrintWriter wr=response.getWriter();
		 wr.print(null==flag?-1:Tools.DUANXIN_SWITCH_FLAG);
		 wr.flush();
		 wr.close();
		 return null;
		 }
	
}