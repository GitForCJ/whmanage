package com.wlt.webm.business.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.alibaba.fastjson.JSON;
import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.AcctBillBean;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.SysCommission;
import com.wlt.webm.business.bean.beijingFlow.BeanFlow;
import com.wlt.webm.business.bean.unicom3gquery.FillProductDetail;
import com.wlt.webm.business.bean.unicom3gquery.FillProductInfo;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.business.form.BiProdForm;
import com.wlt.webm.business.form.BiSanForm;
import com.wlt.webm.db.DBConfig;
import com.wlt.webm.db.DBService;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpcommon.Constant;
import com.wlt.webm.ten.service.TenpayXmlPath;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.PageAttribute;

/**
 * ������<br>
 */
public class BiProdAction extends DispatchAction {

	/**
	 * ��������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fee0 = request.getParameter("cm_fee");
		String fee1 = request.getParameter("cm_fee1");
		BiProd biProd = new BiProd();
		request.setAttribute("mess", biProd.add(Integer.parseInt(fee0), Integer
				.parseInt(fee1)) == 0 ? "��ӳɹ�" : "�����ظ�");
		return new ActionForward("/business/wltprodadd.jsp");
	}

	/**
	 * ����¶Ƚ���������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward awardsadd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fee0 = request.getParameter("cm_fee");
		String fee1 = request.getParameter("cm_fee1");
		String fee2 = request.getParameter("cm_fee2");
		String type = request.getParameter("usertype");
		BiProd biProd = new BiProd();
		request.setAttribute("mess", biProd.addawards(Integer.parseInt(type),
				Integer.parseInt(fee0), Integer.parseInt(fee1), Float
						.parseFloat(fee2)) == 0 ? "��ӳɹ�" : "�����ظ�");
		return new ActionForward("/business/whtawardsruleadd.jsp");
	}

	/**
	 * ɾ�����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ids");
		BiProd biProd = new BiProd();
		request.setAttribute("mess", biProd.del(id) == true ? "ɾ���ɹ�" : "����ʧ��");
		return new ActionForward("/business/wltprodlist.jsp");
	}

	/**
	 * ɾ���¶Ƚ�������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward delawards(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ids");
		BiProd biProd = new BiProd();
		request.setAttribute("mess", biProd.delawards(id) == true ? "ɾ���ɹ�"
				: "����ʧ��");
		return new ActionForward("/business/whtawardsrules.jsp");
	}

	/**
	 * ���������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BiProdForm sForm = (BiProdForm) form;
		BiProd biProd = new BiProd();
		biProd.update(sForm);
		return mapping.findForward("success");
	}

	/**
	 * ��ȡ��Ʒid��AJAX��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getCmprod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("html/json; charset=UTF-8");
		PrintWriter pWriter = null;
		String cmProd = "-9";// ʡ�ݱ��
		String phoneArea = "δ֪";// ��Ӫ������
		String phoneInfo = "δ֪"; // ʡ�ݼӵ���
		String flag = "0";
		String phoneType = "x"; // ��Ӫ�����ͱ��
		StringBuffer sBuffer = new StringBuffer();
		String barcode = "";
		try {
			pWriter = response.getWriter();
			String phone = request.getParameter("tradeObject").replaceAll(" ",
					"").substring(0, 7);
			BiProd biProd = new BiProd();
			String[] str = biProd.getPhoneInfo(phone);
			if (null != str && str.length == 3) {
				cmProd = str[0];
				phoneType = str[1];
				phoneInfo = str[2];
				flag = "1";
				if (phoneType.equals("0")) {
					phoneArea = "����";
				} else if (phoneType.equals("1")) {
					phoneArea = "�ƶ�";
				} else {
					phoneArea = "��ͨ";
				}
			}

			// �㶫ʡ��ͨ���ƶ���ȫ������
			if (Integer.parseInt(cmProd) == 35
					|| Integer.parseInt(phoneType) == 0) {
				barcode = "1";
			}
			// ȫ���ƶ�
			else if (Integer.parseInt(cmProd) != 35
					&& Integer.parseInt(phoneType) == 1) {
				barcode = "2";
			}
			// ȫ����ͨ
			else if (Integer.parseInt(cmProd) != 35
					&& Integer.parseInt(phoneType) == 2) {
				barcode = "3";
			}
			// QQ��ֵ
			else {
				barcode = "4";
			}
			sBuffer.append("[{\"flag\":\"" + flag + "\",\"phoneType\":\""
					+ phoneType + "\",\"cmProd\":\"" + cmProd
					+ "\",\"phoneArea\":\"" + phoneArea + "\",\"barcode\":\""
					+ barcode + "\",\"phoneInfo\":\"" + phoneInfo + "\"}]");
			pWriter.print(sBuffer.toString());
			pWriter.flush();
			pWriter.close();
		} catch (Exception e) {
			sBuffer.delete(0, sBuffer.length());
			sBuffer.append("[{\"flag\":\"" + flag + "\",\"phoneType\":\""
					+ phoneType + "\",\"cmProd\":\"" + cmProd
					+ "\",\"phoneArea\":\"" + phoneArea + "\",\"barcode\":\""
					+ barcode + "\",\"phoneInfo\":\"" + phoneInfo + "\"}]");
			pWriter.print(sBuffer.toString());
			pWriter.flush();
			pWriter.close();
		}

		return null;
	}

	
	/**
	 * ��ȡ��ǰ�û����ʮ��������Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	public ActionForward getUserAccountList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("html/json; charset=UTF-8");
		SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
		PrintWriter pWriter = null;
		List arryList=null;
		String listtype=request.getParameter("listtype");
		String sql="";
		try {
			pWriter = response.getWriter();
			DBService dbService = null;
			try {
				dbService = new DBService();
				if("0".equals(listtype))//ȫ����ֵ
				{
				 sql= " SELECT tradeobject,fee/1000,tradefee/1000,FORMAT(phoneleft/1000,2),DATE_FORMAT(tradetime,'%Y-%m-%d %k:%i:%s'),state,phone_pid,tradeserial,user_name,id,tradetime,buyid  " +
						" FROM wht_orderform_"+Tools.getNow3().substring(2,6)+" WHERE userno='"+userSession.getUserno()+"' AND SUBSTRING(tradeobject,1,1)<>'0' AND SUBSTRING(tradeobject,1,1)='1' AND phone_type not in (3,4,5) ORDER BY id DESC LIMIT 0,10 ";
				}
				if("1".equals(listtype))//�㶫����
				{
				 sql= " SELECT tradeobject,fee/1000,tradefee/1000,FORMAT(phoneleft/1000,2),DATE_FORMAT(tradetime,'%Y-%m-%d %k:%i:%s'),state,phone_pid,tradeserial,user_name,id,tradetime,buyid  " +
						" FROM wht_orderform_"+Tools.getNow3().substring(2,6)+" WHERE userno='"+userSession.getUserno()+"' AND (buyid='9' or buyid='19') ORDER BY id DESC LIMIT 0,10 ";
				}
				if("2".equals(listtype))//֧����
				{
				 sql= " SELECT tradeobject,fee/1000,tradefee/1000,FORMAT(phoneleft/1000,2),DATE_FORMAT(tradetime,'%Y-%m-%d %k:%i:%s'),state,phone_pid,tradeserial,user_name,id,tradetime,buyid  " +
						" FROM wht_orderform_"+Tools.getNow3().substring(2,6)+" WHERE userno='"+userSession.getUserno()+"' AND phone_type=5 ORDER BY id DESC LIMIT 0,10 ";
				}
				if("3".equals(listtype))//qq��
				{
				 sql= " SELECT tradeobject,fee/1000,tradefee/1000,FORMAT(phoneleft/1000,2),DATE_FORMAT(tradetime,'%Y-%m-%d %k:%i:%s'),state,phone_pid,tradeserial,user_name,id,tradetime,buyid  " +
						" FROM wht_orderform_"+Tools.getNow3().substring(2,6)+" WHERE userno='"+userSession.getUserno()+"' AND phone_type=3 ORDER BY id DESC LIMIT 0,10 ";
				}
				if("4".equals(listtype))//ȫ���̻�
				{
				 sql= " SELECT tradeobject,fee/1000,tradefee/1000,FORMAT(phoneleft/1000,2),DATE_FORMAT(tradetime,'%Y-%m-%d %k:%i:%s'),state,phone_pid,tradeserial,user_name,id,tradetime,buyid  " +
						" FROM wht_orderform_"+Tools.getNow3().substring(2,6)+" WHERE userno='"+userSession.getUserno()+"' AND buyid='6' AND SUBSTRING(tradeobject,1,1)='0' ORDER BY id DESC LIMIT 0,10 ";
				}
				if("5".equals(listtype))//��ʯ����ֵ
				{
				 sql= " SELECT tradeobject,fee/1000,tradefee/1000,FORMAT(phoneleft/1000,2),DATE_FORMAT(tradetime,'%Y-%m-%d %k:%i:%s'),state,phone_pid,tradeserial,user_name,id,tradetime,buyid  " +
						" FROM wht_orderform_"+Tools.getNow3().substring(2,6)+" WHERE userno='"+userSession.getUserno()+"' AND SUBSTRING(tradeobject,1,1)<>'0' AND SUBSTRING(tradeobject,1,1)='1' AND phone_type not in (3,4,5) ORDER BY id DESC LIMIT 0,10 ";
				}
				if("6".equals(listtype))//�㶫��ͨ
				{
				 sql= " SELECT tradeobject,fee/1000,tradefee/1000,FORMAT(phoneleft/1000,2),DATE_FORMAT(tradetime,'%Y-%m-%d %k:%i:%s'),state,phone_pid,tradeserial,user_name,id,tradetime,buyid  " +
						" FROM wht_orderform_"+Tools.getNow3().substring(2,6)+" WHERE userno='"+userSession.getUserno()+"' AND buyid='15' ORDER BY id DESC LIMIT 0,10 ";
				}
				if("7".equals(listtype))//�㶫�ƶ�
				{
				 sql= " SELECT tradeobject,fee/1000,tradefee/1000,FORMAT(phoneleft/1000,2),DATE_FORMAT(tradetime,'%Y-%m-%d %k:%i:%s'),state,phone_pid,tradeserial,user_name,id,tradetime,buyid  " +
						" FROM wht_orderform_"+Tools.getNow3().substring(2,6)+" WHERE userno='"+userSession.getUserno()+"' AND buyid='8' AND phone_pid=35 ORDER BY id DESC LIMIT 0,10 ";
				}
				arryList=dbService.getList(sql, null);
			} catch (Exception ex) {
				Log.error("��ȡ��ǰ�û���ʮ�����׼�¼���쳣������"+ex);
			} finally {
				if(dbService!=null)
					dbService.close();
			}
			JSONArray json = JSONArray.fromObject(arryList);
			pWriter.print(json);
			pWriter.flush();
			pWriter.close();
		} catch (Exception e) {
			pWriter.print("");
			pWriter.flush();
			pWriter.close();
		}
		return null;
	}

	/**
	 * ��֤�ʽ��˺��Ƿ����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward yanzheng(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("html/json; charset=UTF-8");
		PrintWriter pWriter = null;
		StringBuffer sBuffer = new StringBuffer();
		try {
			pWriter = response.getWriter();
			String phone = request.getParameter("tradeObject").replaceAll(" ",
					"");
			BiProd biProd = new BiProd();
			String str = biProd.getuserName(phone);
			String flag = "δ֪";
			if (null != str) {
				flag = "*" + str.substring(str.length() - 1);
			}
			sBuffer.append("[{\"flag\":\"" + flag + "\"}]");
			pWriter.print(sBuffer.toString());
			pWriter.flush();
			pWriter.close();
		} catch (Exception e) {
			sBuffer.delete(0, sBuffer.length());
			sBuffer.append("[{\"flag\":\"ϵͳ��æ\"}]");
			pWriter.print(sBuffer.toString());
			pWriter.flush();
			pWriter.close();
		}

		return null;
	}

	/**
	 * ��֤��ʯ���˺��Ƿ���ڼ��Ƿ�֧��ת��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward zshyanzheng(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("html/json; charset=UTF-8");
		PrintWriter pWriter = null;
		StringBuffer sBuffer = new StringBuffer();
		try {
			BiProd biProd = new BiProd();
			pWriter = response.getWriter();
			String userno = request.getParameter("userid");// ת���˺�id
			String otherUserid = request.getParameter("tradeObject")
					.replaceAll(" ", "");// �Է��û�id
			String str1 = biProd.compareUser(userno);// ��ȡת���˺Ž�ɫ����
			String str2 = biProd.findUserType(userno, otherUserid);// ��ȡ�Է��˺Ž�ɫ����
			if (str2 != null) {
				boolean b = false;
				if (Integer.parseInt(str2) > Integer.parseInt(str1)
						&& Integer.parseInt(str2) == 3
						&& Integer.parseInt(str1) == 2) {
					b = true;
				}
				if (b) {
					String str = biProd.getuserName(otherUserid);
					String flag = "δ֪";
					if (null != str) {
						flag = "*" + str.substring(str.length() - 1);
					}
					sBuffer.append("[{\"flag\":\"" + flag + "\"}]");
					pWriter.print(sBuffer.toString());
					pWriter.flush();
					pWriter.close();
				} else {
					sBuffer.append("[{\"flag\":\"��֧�ֶԴ��˺�ת��\"}]");
					pWriter.print(sBuffer.toString());
					pWriter.flush();
					pWriter.close();
				}
			} else {
				sBuffer.append("[{\"flag\":\"��֧�ֶԴ��˺�ת��\"}]");
				pWriter.print(sBuffer.toString());
				pWriter.flush();
				pWriter.close();
			}
		} catch (Exception e) {
			sBuffer.delete(0, sBuffer.length());
			sBuffer.append("[{\"flag\":\"ϵͳ��æ\"}]");
			pWriter.print(sBuffer.toString());
			pWriter.flush();
			pWriter.close();
		}

		return null;
	}

	/**
	 * ��ȡ��Ʒid��AJAX��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward getMoney(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("html/json; charset=UTF-8");
		PrintWriter pWriter = null;
		StringBuffer sBuffer = new StringBuffer();
		DBService db = null;
		try {
			SysUserForm loginUser = (SysUserForm) request.getSession()
					.getAttribute("userSession");
			if (null == loginUser) {
				request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
				return new ActionForward("/rights/wltlogin.jsp");
			}
			String phonePid = request.getParameter("prodId");
			String phone = request.getParameter("tradeObject").replaceAll(" ",
					"");
			String phoneType = request.getParameter("telType");
			String payfee = request.getParameter("money1");
			String userPid = loginUser.getUsersite();
			String parentID = loginUser.getParent_id();
			String userno = loginUser.getUserno();
			String flag = "0";
			double fee = FloatArith.yun2li(payfee);
			pWriter = response.getWriter();
			db = new DBService();
			if (AcctBillBean.getStatus(parentID)) {// ֱӪ
				flag = "1";
			}
			BiProd bp = new BiProd();
			String[] result =null;
			if(com.wlt.webm.util.Tools.isJC(phone)){
				result=bp.getJCEmploy(db,flag, userPid, phonePid,Integer.parseInt(payfee),phoneType);
			}else{
			result = bp.getEmploy(db, phoneType, userPid, phonePid,
					userno, flag, Integer.parseInt(payfee), Integer
							.parseInt(parentID),0);
			}
			if (null == result) {
				sBuffer.delete(0, sBuffer.length());
				sBuffer.append("[{\"mon\":\"" + "�ݲ�֧�ָ�����ֵ" + "\"}]");
			} else {
				//		
				double facctfee = 0;
				double parentfee = 0;
				if (fee > 0 && fee < 30000) {
					if ("1".equals(flag)) {// ֱӪ
						facctfee = Integer.parseInt(result[0]);
					} else {
						facctfee = Integer.parseInt(result[0]);
						parentfee = Integer.parseInt(result[1]);
					}
				} else {
					if ("1".equals(flag)) {// ֱӪ
						facctfee = FloatArith.mul(fee, 1 - FloatArith.div(
								Double.valueOf(result[0]), 100));
					} else if (!userPid.equals(phonePid)) {// ��ֱӪ �Ǳ�ʡ
						TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
						facctfee = FloatArith.mul(fee, 1 - FloatArith.div(
								Double.valueOf(result[0]), 100));
						parentfee = FloatArith.mul(fee, FloatArith.div(Double
								.valueOf(DBConfig.getInstance()
										.getOtherRebate()), 100));
					} else {// ��ֱӪ ��ʡ
						double a = FloatArith.div(Double.valueOf(result[0]),
								100);// ��Ӷ���
						double b = FloatArith.mul(fee, a);// ��Ӷ��
						double c = FloatArith.mul(b, FloatArith.div(Double
								.valueOf(result[1]), 100));// �����Ӧ��Ӷ��
						facctfee = FloatArith.sub(fee, c);// Ӧ�۽��
						parentfee = FloatArith.sub(b, c);// �ϼ��ڵ�Ӧ��Ӷ��
					}
				}
				//
				sBuffer.append("[{\"mon\":\""
						+ FloatArith.div(facctfee, 1000, 2)
						+ "Ԫ , ����:"
						+ FloatArith
								.div(FloatArith.sub(fee, facctfee), 1000, 2)
						+ "Ԫ\"}]");
			}

		} catch (Exception e) {
			sBuffer.delete(0, sBuffer.length());
			sBuffer.append("[{\"mon\":\"" + "ϵͳ��æ..�ɹ��۸����޷���ʾ" + "\"}]");
		}finally{
			if(db!=null){
				db.close();
			}
		}
		pWriter.print(sBuffer.toString());
		pWriter.flush();
		pWriter.close();
		return null;
	}

	/**
	 * ��ӵ������ӿ�������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward sanadd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BiSanForm srForm = (BiSanForm) form;
		BiProd biProd = new BiProd();
		int state = biProd.sanadd(srForm);
		if (state == -1) {
			request.setAttribute("mess", "�����ظ���ӣ�");
			return new ActionForward("/business/wltsanadd.jsp");
		}
		request.setAttribute("mess", "��ӳɹ�");
		return mapping.findForward("success");
	}

	/**
	 * ɾ���������ӿ�����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward sandel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BiSanForm srForm = (BiSanForm) form;
		BiProd biProd = new BiProd();
		biProd.sandel(srForm);
		return mapping.findForward("success");
	}

	/**
	 * ���µ������ӿ�������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward sanupdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BiSanForm srForm = (BiSanForm) form;
		BiProd biProd = new BiProd();
		biProd.sanupdate(srForm);
		return mapping.findForward("success");
	}

	/**
	 * ɾ��Ӷ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward delEmploy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BiSanForm userForm = (BiSanForm) form;
		String type = request.getParameter("st");
		String k = request.getParameter("k");
		String curPage = request.getParameter("curPage");
		if ("0".equals(k)) {
			String id = request.getParameter("ids");
			if (null == id) {
				request.setAttribute("mess", "�޸ĳ���");
				return new ActionForward("/employ/whtcommissionadd.jsp");
			}
			String[] str = id.split("#");
			request.setAttribute("st", type);
			request.setAttribute("str", str);
			request.setAttribute("id", str[0]);
			request.setAttribute("curPage", curPage);
			request.setAttribute("userForm",userForm);
			return new ActionForward("/employ/whtcommissionupdate.jsp");
		} else {
			BiProd biProd = new BiProd();
			String st = request.getParameter("st");
			String id = request.getParameter("id");
			String cm_prod = request.getParameter("cm_prod");
			request.setAttribute("curPage", curPage);
			request.setAttribute("userForm",userForm);
			request.setAttribute("mess", true == biProd.delEmploy(cm_prod,
					Integer.parseInt(id), st) ? "�޸ĳɹ�" : "�޸�ʧ��");
			return new ActionForward("/business/prod.do?method=emlist&st="+st+"&curPage="+curPage+"&userForm="+userForm);
		}
	}

	/**
	 * �޸Ľӿ���Ӷ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward tpdelEmploy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String k = request.getParameter("k");
		String curPage = request.getParameter("curPage");
		if ("0".equals(k)) {
			String id = request.getParameter("ids");
			if (null == id) {
				request.setAttribute("mess", "�޸ĳ���");
				return new ActionForward("/employ/tpcommissionadd.jsp");
			}
			String[] str = id.split("#");
			request.setAttribute("str", str);
			request.setAttribute("id", str[0]);
			request.setAttribute("curPage", curPage);
			
			request.setAttribute("cm_one",request.getParameter("cm_one"));
			request.setAttribute("yunyingshang",request.getParameter("yunyingshang"));
			request.setAttribute("miane",request.getParameter("miane"));
			request.setAttribute("groups",request.getParameter("groups"));
			return new ActionForward("/employ/tpcommissionupdate.jsp");
		} else {
			BiProd biProd = new BiProd();
			String id = request.getParameter("id");
			String cm_prod = request.getParameter("cm_prod");
			request.setAttribute("mess", true == biProd.tpdelEmploy(cm_prod,
					Integer.parseInt(id)) ? "�޸ĳɹ�" : "�޸�ʧ��");
			return new ActionForward("/business/prod.do?method=tpemlist&curPage="+curPage+"&cm_one="+request.getParameter("cm_one")+"&yunyingshang="+request.getParameter("yunyingshang")
					+"&miane="+request.getParameter("miane")+"&groups="+request.getParameter("groups"));
		}
	}

	/**
	 * ɾ��Ӷ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward delminEmploy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String k = request.getParameter("k");
		String curPage = request.getParameter("curPage");
		if ("0".equals(k)) {
			String id = request.getParameter("ids");
			if (null == id) {
				request.setAttribute("mess", "�޸ĳ���");
				return new ActionForward("/employ/whtmincommissionadd.jsp");
			}
			String[] str = id.split("#");
			request.setAttribute("str", str);
			request.setAttribute("id", str[0]);
			request.setAttribute("curPage", curPage);
			return new ActionForward("/employ/whtmincommissionupdate.jsp");
		} else {
			BiProd biProd = new BiProd();
			String st = request.getParameter("st");
			String id = request.getParameter("id");
			String cm_prod = request.getParameter("cm_prod");
			String cm_prod1 = request.getParameter("cm_prod1");
			request.setAttribute("mess", true == biProd.delEmploy(Integer
					.parseInt(cm_prod), Integer.parseInt(id), Integer
					.parseInt(cm_prod1)) ? "�޸ĳɹ�" : "�޸�ʧ��");
			return new ActionForward("/business/prod.do?method=minemlist&st="+st+"&curPage="+curPage);
		}
	}

	/**
	 * ���Ӷ����Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addemploy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String st = request.getParameter("st");
		String value = request.getParameter("cm_prod");
		String cm_type = request.getParameter("cm_type");
		String cm_value = request.getParameter("cm_value");
		String pid = request.getParameter("cm_area");
		BiProd biProd = new BiProd();
		request.setAttribute("mess", biProd.addEmploy(st,
				Integer.parseInt(pid), cm_type, cm_value, Float
						.parseFloat(value)) == 0 ? "��ӳɹ�" : "�����ظ�");
		request.setAttribute("sst", st);
		return new ActionForward("/employ/whtcommissionadd.jsp");
	}

	/**
	 * ��ӵ����������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward tpaddemploy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String value = request.getParameter("cm_prod");
		String cm_type = request.getParameter("cm_type");
		String cm_value = request.getParameter("cm_value");
		String pid = request.getParameter("cm_area");
		String interName=request.getParameter("cm_interName");
		BiProd biProd = new BiProd();
		request.setAttribute("mess", biProd.tpddEmploy(Integer.parseInt(pid),
				cm_type, cm_value, Float.parseFloat(value),Integer.parseInt(interName)) == 0 ? "��ӳɹ�"
				: "�����ظ�");
		return new ActionForward("/employ/tpcommissionadd.jsp");
	}
	//tpaddemployTwo
	/**
	 * ��ӵ����������Ϣ ��������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward tpaddemployTwo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cm_type = request.getParameter("cm_type");
		String cm_groups=request.getParameter("cm_groups");
		
		String[] sheng=request.getParameterValues("sheng");
		String[] jiage=request.getParameterValues("money");
		String[] bilu=request.getParameterValues("val");
		
		boolean bool=false;
		DBService dbService = null;
		try {
			dbService = new DBService();
			for(int i=0;i<bilu.length;i++)
			{
				if(!"".equals(bilu[i].trim()))
				{
					String sql="select * from sys_tpemploy where pid= "+sheng[i] 
					      + " and cm_id='"+jiage[i]+"' and type='" + cm_type + "' and groups="+cm_groups;
					if (dbService.hasData(sql)) {
						bool=true;
						break;
					}
				}
			}
			if(bool)
			{
				request.setAttribute("mess","�����ظ�!");
				return new ActionForward("/employ/tpcommissionadd.jsp");
			}
			for(int i=0;i<bilu.length;i++)
			{
				if(!"".equals(bilu[i].trim()))
				{
					
					String ss="INSERT INTO  sys_tpemploy(pid,TYPE,cm_id,VALUE,groups) VALUES('"+sheng[i]+"'," +
							"'"+cm_type+"','"+jiage[i]+"','"+bilu[i]+"','"+cm_groups+"')";
//					System.out.println(ss);
					dbService.update(ss,null);
				}
			}
			request.setAttribute("mess","��ӳɹ�!");
			return new ActionForward("/employ/tpcommissionadd.jsp");
		}catch (Exception ex)
		{
			Log.error("��ӽӿ���Ӷ���쳣������"+ex);
			request.setAttribute("mess","ϵͳ�쳣!");
			return new ActionForward("/employ/tpcommissionadd.jsp");
		}
		finally{
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	/**
	 * ��ͨ����Ӷ��ģ�� ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward JfAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String cm_groups=request.getParameter("cm_groups");
		
		String[] sheng=request.getParameterValues("sheng");
		String[] jiage=request.getParameterValues("money");
		String[] bilu=request.getParameterValues("val");
		
		boolean bool=false;
		DBService dbService = null;
		try {
			dbService = new DBService();
			for(int i=0;i<bilu.length;i++)
			{
				if(!"".equals(bilu[i].trim()))
				{
					String sql="select * from sys_tpemploy_jf where pid= "+sheng[i] 
					      + " and carid="+jiage[i]+" and groups="+cm_groups;
					if (dbService.hasData(sql)) {
						bool=true;
						break;
					}
				}
			}
			if(bool)
			{
				request.setAttribute("mess","�����ظ�!");
				return new ActionForward("/employ/interJFadd.jsp");
			}
			for(int i=0;i<bilu.length;i++)
			{
				if(!"".equals(bilu[i].trim()))
				{
					
					String ss="INSERT INTO  sys_tpemploy_jf(pid,carid,val,groups) VALUES("+sheng[i]+","+jiage[i]+","+bilu[i]+",'"+cm_groups+"')";
					dbService.update(ss,null);
				}
			}
			request.setAttribute("mess","��ӳɹ�!");
			return new ActionForward("/business/prod.do?method=InterJFlist");
		}catch (Exception ex)
		{
			Log.error("��ӽӿ���Ӷ���쳣������"+ex);
			request.setAttribute("mess","ϵͳ�쳣!");
			return new ActionForward("/employ/interJFadd.jsp");
		}
		finally{
			if(dbService!=null){
				dbService.close();
			}
		}
	}
	/**
	 * ���С�����Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addminEmploy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String value = request.getParameter("cm_prod");
		String value1 = request.getParameter("cm_prod1");
		String cm_type = request.getParameter("cm_type");
		String cm_value = request.getParameter("cm_value");
		String pid = request.getParameter("cm_area");
		BiProd biProd = new BiProd();
		request.setAttribute("mess", biProd.addminEmploy(Integer
				.parseInt(value1), Integer.parseInt(pid), cm_type, cm_value,
				Integer.parseInt(value)) == 0 ? "��ӳɹ�" : "�����ظ�");
		return new ActionForward("/employ/whtmincommissionadd.jsp");
	}

	/**
	 * Ӷ���б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response 
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward emlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BiSanForm userForm = (BiSanForm) form;
		PageAttribute page = new PageAttribute(userForm.getCurPage(),
				Constant.PAGE_SIZE);
		String srType = request.getParameter("st");
		SysCommission sc = new SysCommission();
		page.setRsCount(sc.employSum(srType,userForm));
		List list = sc.getEmployinfo(page, srType,userForm);
		request.setAttribute("userList", list);
		request.setAttribute("page", page);
		request.setAttribute("curPage", userForm.getCurPage());
		request.setAttribute("st", srType);
		request.setAttribute("userForm",userForm);
		return new ActionForward("/employ/employlist.jsp");
	}
	
	/**
	 * ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response 
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward oneagentList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BiSanForm userForm = (BiSanForm) form;
		PageAttribute page = new PageAttribute(userForm.getCurPage(),
				Constant.PAGE_SIZE);
		if(request.getParameter("type")!=null && "-100".equals(request.getParameter("type")))
		{
			String setid=request.getParameter("setid");
			String delSql="DELETE FROM  sys_oneagent WHERE setupID="+setid;
			DBService db=null;
			try {
				db=new DBService();
				if(db.update(delSql)>0)
				{
					request.setAttribute("mess","ɾ���ɹ�!");
				}
				else
				{
					request.setAttribute("mess","ɾ��ʧ��!");
				}
			} catch (Exception e) {
				Log.error("ɾ�����ݣ�ϵͳ�쳣������"+e);
				request.setAttribute("mess","ϵͳ�쳣!");
			}
			finally
			{
				if(null!=db)
					db.close();
			}
		}
		SysCommission sc = new SysCommission();
		page.setRsCount(sc.getOneAgentCount(userForm));
		List list = sc.getOneAgentList(page,userForm);
		request.setAttribute("userList", list);
		request.setAttribute("page", page);
		request.setAttribute("curPage", userForm.getCurPage());
		request.setAttribute("userForm",userForm);
		return new ActionForward("/setup/setupOneAgentList.jsp");
	}
	
	/**
	 * ��ӽ�������  ӳ���ϵ configureList
	 * @param mapping 
	 * @param form 
	 * @param request 
	 * @param response 
	 * @return null
	 * @throws Exception 
	 */
	public ActionForward configureList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BiSanForm userForm = (BiSanForm) form;
		PageAttribute page = new PageAttribute(userForm.getCurPage(),
				Constant.PAGE_SIZE);
		if(request.getParameter("type")!=null && "-100".equals(request.getParameter("type")))
		{
			String userno=request.getParameter("userno");
			String gid=request.getParameter("groupsID");
			String delSql="DELETE FROM  sys_oneagentmaps WHERE userno='"+userno+"' AND groupsID="+gid;
			DBService db=null;
			try {
				db=new DBService();
				if(db.update(delSql)>0)
				{
					request.setAttribute("mess","ɾ���ɹ�!");
				}
				else
				{
					request.setAttribute("mess","ɾ��ʧ��!");
				}
			} catch (Exception e) {
				Log.error("ɾ�����ݣ�ϵͳ�쳣������"+e);
				request.setAttribute("mess","ϵͳ�쳣!");
			}
			finally
			{
				if(null!=db)
					db.close();
			}
		}
		SysCommission sc = new SysCommission();
		page.setRsCount(sc.getconfigureCount(userForm));
		List list = sc.getconfigureList(page,userForm);
		request.setAttribute("userList", list);
		request.setAttribute("page", page);
		request.setAttribute("curPage", userForm.getCurPage());
		request.setAttribute("userForm",userForm);
		return new ActionForward("/setup/setupConfigureList.jsp");
	}
	
	/**��ӽ����� 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward insertOneAgent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String a=request.getParameter("a");
		String a2=request.getParameter("a2");
		String a3=request.getParameter("a3");
		String a4=request.getParameter("a4");
		String a5=request.getParameter("a5");
		String a6=request.getParameter("a6");
		
		String sql="SELECT COUNT(*) FROM sys_oneagent WHERE groupsID='"+a3+"' AND tradetype='"+a2+"' AND ((monbegin<="+Double.parseDouble(a4)*1000+" AND monend>"+Double.parseDouble(a4)*1000+") OR (monbegin<="+Double.parseDouble(a5)*1000+" AND monend>"+Double.parseDouble(a5)*1000+") OR (monbegin>="+Double.parseDouble(a4)*1000+" AND monend<="+Double.parseDouble(a5)*1000+"))";
		DBService db=null;
		try {
			db=new DBService();
			if(db.getInt(sql,null)<=0)
			{
				String insql="INSERT INTO sys_oneagent(groupsID,tradetype,monbegin,monend,percent) VALUE('"+a3+"','"+a2+"',"+String.format("%.0f",Double.parseDouble(a4)*1000)+","+String.format("%.0f",Double.parseDouble(a5)*1000)+","+a6+")";
				if(db.update(insql,null)>0)
				{
					request.setAttribute("mess","��ӳɹ�!");
				}
				else
				{
					request.setAttribute("mess","���ʧ��!");
				}
			}
			else
			{
				request.setAttribute("mess","��������ظ�!");
			}
		} catch (Exception e) {
			request.setAttribute("mess","ϵͳ�쳣!");
			Log.error("��ӽ���ס��ϵͳ�쳣������"+e);
		}
		return new ActionForward("/setup/setupadd.jsp");
	}
	/**��ӽ�����ӳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward insertConfigure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String a1=request.getParameter("a1");
		String a2=request.getParameter("a2");
		
		String sql="SELECT count(*) con FROM sys_oneagentmaps WHERE userno='"+a2+"'";
		DBService db=null;
		try {
			db=new DBService();
			if(db.getInt(sql,null)<=0)
			{
				String insql="INSERT INTO sys_oneagentmaps(groupsID,userno) VALUE("+a1+",'"+a2+"')";
				if(db.update(insql,null)>0)
				{
					request.setAttribute("mess","��ӳɹ�!");
				}
				else
				{
					request.setAttribute("mess","���ʧ��!");
				}
			}
			else
			{
				request.setAttribute("mess","���û��Ѿ�ӳ���˽�����,�����ظ����!");
			}
		} catch (Exception e) {
			request.setAttribute("mess","ϵͳ�쳣!");
			Log.error("��ӽ�����ӳ�䣬ϵͳ�쳣������"+e);
		}
		return new ActionForward("/setup/setupConfigureadd.jsp");
	}
	/**
	 * �ӿ���Ӷ���б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward tpemlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BiSanForm userForm = (BiSanForm) form;
		PageAttribute page = new PageAttribute(userForm.getCurPage(),Constant.PAGE_SIZE);
		SysCommission sc = new SysCommission();
		page.setRsCount(sc.tpemploySum(userForm));
		
		String bool=request.getParameter("bool");
		if("-11".equals(bool)){
			//����
			page.setCurPage(1);
			page.setPageSize(page.getRsCount());
			List list = sc.tpgetEmployinfo(page,userForm);
			request.setAttribute("userList", list);
			request.setAttribute("userForm",userForm);
			return new ActionForward("/employ/tpemployExcel.jsp");
		}
		List list = sc.tpgetEmployinfo(page,userForm);
		request.setAttribute("userList", list);
		request.setAttribute("page", page);
		request.setAttribute("curPage", userForm.getCurPage());
		request.setAttribute("userForm",userForm);
		return new ActionForward("/employ/tpemploylist.jsp");
	}
	/**
	 * ���� �б�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward Flowlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BiSanForm userForm = (BiSanForm) form;
		String sql="SELECT CASE b.TYPE WHEN 0 THEN '����' WHEN 1 THEN '�ƶ�' WHEN 2 THEN '��ͨ' END AS a,"
			+" CASE b.cm_addr WHEN  0 THEN 'ȫ��' WHEN  1 THEN 'ʡ��' ELSE c.sa_name END  AS t,"
			+" VALUE,groups,id FROM sys_tpemploy_Flow b LEFT JOIN sys_area c ON b.cm_addr=c.sa_id WHERE 1=1 ";
		if(userForm.getGroups()!=null && !"".equals(userForm.getGroups())){
			sql=sql+" AND groups="+userForm.getGroups()+"";
		}
		List arry=null;
		DBService db=null;
		try {
			db=new DBService();
			arry=db.getList(sql);
		} catch (Exception e) {
			Log.error("������ѯ�б��쳣����e:"+e);
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
		request.setAttribute("userList", arry);
		request.setAttribute("userForm",userForm);
		
		String bool=request.getParameter("bool");
		if("-11".equals(bool)){
			//����
			return new ActionForward("/employ/FlowExcel.jsp");
		}
		return new ActionForward("/employ/Flow.jsp");
	}
	/**
	 * ����Ӷ�� �޸�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null;
	 * @throws Exception
	 */
	public ActionForward FlowUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String bool=request.getParameter("flag");
//		request.setAttribute("groups",request.getParameter("groups"));
		
		if("add".equals(bool)){
			String cm_groups=request.getParameter("cm_groups");
			String strs=request.getParameter("strs");
			strs=!"".equals(strs)?strs.substring(0,strs.length()-1):strs;
			if("".equals(strs) || "".equals(cm_groups)){
				request.setAttribute("mess","����Ӷ�����ʧ��!");
			}else{
				DBService db=null;
				try {
					db=new DBService();
					boolean f=false;
					String[] s1=strs.split("#");
					for(int i=0;i<s1.length;i++){
						String[] s2=s1[i].split("@");
						String val=db.getString("SELECT value FROM sys_tpemploy_Flow WHERE groups="+cm_groups+" AND TYPE="+s2[0]+" AND cm_addr="+s2[1]);
						if(val!=null && !"".equals(val)){
							f=true;
							break;
						}
					}
					if(f){
						request.setAttribute("mess","����Ӷ�����ʧ��,�ظ����!");
					}else{
						f=true;
						for(int i=0;i<s1.length;i++){
							String[] s2=s1[i].split("@");
							int c=db.update("INSERT INTO sys_tpemploy_Flow(TYPE,cm_addr,VALUE,groups) VALUE("+s2[0]+","+s2[1]+","+s2[2]+","+cm_groups+")");
							if(c<=0){
								f=false;
								break;
							}
						}
						if(f){
							request.setAttribute("mess","����Ӷ����ӳɹ�!");
						}else{
							request.setAttribute("mess","����Ӷ����Ӳ���ʧ��!");
						}
					}
				} catch (Exception e) {
					request.setAttribute("mess","����Ӷ������쳣!");
				}finally{
					if(null!=db){
						db.close();
						db=null;
					}
				}
			}
		}else if("update".equals(bool)){
			String id=request.getParameter("id");
			String fe=request.getParameter("cm_prod");
			DBService db=null;
			try {
				db=new DBService();
				int con=db.update("UPDATE sys_tpemploy_Flow SET VALUE="+fe+"  WHERE id="+id);
				if(con>=1){
					request.setAttribute("mess","����Ӷ���޸ĳɹ�!");
				}else{
					request.setAttribute("mess","����Ӷ���޸�ʧ��!");
				}
			} catch (Exception e) {
				Log.error("����Ӷ�� �޸ģ��쳣����e:"+e);
				request.setAttribute("mess","����Ӷ���޸��쳣!");
			}finally{
				if(null!=db){
					db.close();
					db=null;
				}
			}
		}
		return Flowlist(mapping,form,request,response);
	}
	/**
	 * �ӿ��̽�ͨ����Ӷ���б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward InterJFlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String bool=request.getParameter("bool");//��ѯ����������
		String cm_one=request.getParameter("cm_one");//����
		String groups=request.getParameter("groups");//������
		String carType=request.getParameter("carType");//��������
		SysCommission sc = new SysCommission();
		
		int index=1;
		int lastIndex=1;
	    int pagesize=15;
		
		if(request.getParameter("index")!=null && !"".equals(request.getParameter("index")))
		{
			index=Integer.parseInt(request.getParameter("index"));
		}
		if(index<=0)
			index=1;
		int count=sc.JFCount(cm_one, carType, groups);
		if(count>0)
			lastIndex=count%pagesize==0?count/pagesize:count/pagesize+1;
		
		if(index>=lastIndex)
			index=lastIndex;
		request.setAttribute("cm_one",cm_one);
		request.setAttribute("groups",groups);
		request.setAttribute("carType",carType);
		request.setAttribute("index",index);
		request.setAttribute("lastIndex",lastIndex);

		if("-12".equals(bool)){
			//�޸Ĳ�ѯ
			String abc=request.getParameter("abc");//�޸�����
			List arryList=sc.JFUpdateID(abc.trim());
			request.setAttribute("arryList",arryList);
			return new ActionForward("/employ/interJFupdate.jsp");
		}
		if("-13".equals(bool)){
			//�޸�
			String val=request.getParameter("val");
			String id=request.getParameter("id");
			boolean flag=sc.JFUpdate(val, id);
			if(flag){
				request.setAttribute("mess","�޸ĳɹ�!");
			}else{
				request.setAttribute("mess","�޸�ʧ��!");
			}
		}
		
		if("-11".equals(bool)){
			//����
			List arrList=sc.JFList(cm_one, carType, groups, index, pagesize,0);
			request.setAttribute("arrList",arrList);
			request.setAttribute("groups",groups);
			return new ActionForward("/employ/interJFExcel.jsp");
		}
		//�б�
		List arrList=sc.JFList(cm_one, carType, groups, index, pagesize,1);
		request.setAttribute("arrList",arrList);
		return new ActionForward("/employ/interJFlist.jsp");
	}
	/**
	 * С���Ӷ���б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward minemlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BiSanForm userForm = (BiSanForm) form;
		PageAttribute page = new PageAttribute(userForm.getCurPage(),
				Constant.PAGE_SIZE);
		SysCommission sc = new SysCommission();
		page.setRsCount(sc.minemploySum());
		List list = sc.getminEmployinfo(page);
		request.setAttribute("userList", list);
		request.setAttribute("page", page);
		request.setAttribute("curPage", userForm.getCurPage());
		return new ActionForward("/employ/minemploylist.jsp");
	}

	/**
	 * ��ֵҵ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward rebatList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BiSanForm userForm = (BiSanForm) form;
		PageAttribute page = new PageAttribute(userForm.getCurPage(),
				Constant.PAGE_SIZE);
		String srType = request.getParameter("st");
		SysCommission sc = new SysCommission();
		page.setRsCount(sc.employSum(srType,userForm));
		List list = sc.getEmployinfo(page, srType,userForm);
		request.setAttribute("userList", list);
		request.setAttribute("page", page);
		request.setAttribute("st", srType);
		return new ActionForward("/employ/employlist.jsp");
	}

	/**
	 * ɾ��Ӷ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward delRebat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String flag = request.getParameter("flag");
		if ("0".equals(flag)) {
			String id = request.getParameter("ids");
			if (null == id) {
				request.setAttribute("mess", "�޸�ʧ��");
				return new ActionForward("/employ/rebatemploylist.jsp");
			} else {
				String[] str = id.split("#");
				request.setAttribute("str", str);
				return new ActionForward("/employ/whtrebatupdate.jsp");
			}
		}
		BiProd biProd = new BiProd();
		String id = request.getParameter("id");
		String cm_prod = request.getParameter("cm_prod");
		request.setAttribute("mess", true == biProd.delRebat(Integer
				.parseInt(id), Float.valueOf(cm_prod)) ? "�޸ĳɹ�" : "�޸�ʧ��");
		return new ActionForward("/employ/rebatemploylist.jsp");
	}

	/**
	 * ����ۺ�ҵ��Ӷ����Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addRebat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String value = request.getParameter("cm_prod");
		String cm_type = request.getParameter("cm_type");
		String cm_value = request.getParameter("cm_value");
		BiProd biProd = new BiProd();
		request.setAttribute("mess", biProd.addRebat(Integer.parseInt(cm_type),
				cm_value, Float.parseFloat(value)) == 0 ? "��ӳɹ�" : "�����ظ�");
		return new ActionForward("/employ/rebatemploylist.jsp");
	}

	/**
	 * ����ۺ�ҵ��Ӷ����Ϣ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addInter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] value = request.getParameterValues("province");
		String[] moneyValues=request.getParameterValues("cmid");
		String face = request.getParameter("face0");
		String yun = request.getParameter("type");
		BiProd biProd = new BiProd();
		request.setAttribute("mess", biProd.addInterMaping(value, Integer
				.parseInt(yun), Integer.parseInt(face),moneyValues) == 0 ? "��ӳɹ�" : "�����ظ�");
		return new ActionForward("/employ/whtinterfaceadd.jsp");
	}
	
	/**
	 * �����޸Ľӿ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null; 
	 */
	public ActionForward BatchUpdateInterface(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		if (null == request.getSession().getAttribute("userSession")) {
			request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		SysUserForm loginUser = (SysUserForm) request.getSession().getAttribute("userSession");
		if(!"0".equals(loginUser.getRoleType())){
			request.setAttribute("mess", "��û��Ȩ��!");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		
		String[] value = request.getParameterValues("province");//ʡ��
		String[] moneyValues=request.getParameterValues("cmid");//���
		String face = request.getParameter("face0");//�ӿ�
		String yun = request.getParameter("type");//��Ӫ��
		List<String[]> arrsy=null;
		if(face==null || "".equals(face) ||yun==null || "".equals(yun)||value==null || value.length<=0 || moneyValues==null || moneyValues.length<=0){
			request.setAttribute("mess","ϵͳ�쳣,��������,����ʧ��!");
			request.setAttribute("arrsy",arrsy);
			return new ActionForward("/employ/BatchUpdateInterface.jsp");
		}
		boolean bool=false;
		DBService db=null;
		try {
			db=new DBService();
			for(int i=0;i<value.length;i++){
				for(int k=0;k<moneyValues.length;k++){
					String v=value[i];
					String m=moneyValues[k];
					String sql="UPDATE sys_interfaceMaping SET interid="+face+" WHERE pid="+v+" AND TYPE="+yun+" AND cmid='"+m+"'";
					try {
						db.update(sql);
						if(!bool){
							bool=true;
						}
					} catch (Exception ex) {
						Log.error("�����޸��쳣,ѭ���޸�sql���,error sql:"+sql+",,,,,ex:"+ex);
						if(arrsy==null){
							arrsy=new ArrayList<String[]>();
						}
						arrsy.add(new String[]{yun,v,m,face});//��Ӫ��,ʡ��,���,�ӿ�
					}
				}
			}
			if(arrsy!=null){
				if(!bool){
					request.setAttribute("mess","ȫ���޸�ʧ��!");
				}else{
					request.setAttribute("mess","�����޸�ʧ��!");
				}
			}else{
				request.setAttribute("mess","�����޸ĳɹ�!");
			}
		} catch (Exception e) {
			Log.error("�����޸Ľӿڻ�ȡdb�쳣,,,ex:"+e);
			request.setAttribute("mess","ϵͳ�쳣,ȫ���޸�ʧ��!");
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		request.setAttribute("arrsy",arrsy);
		return new ActionForward("/employ/BatchUpdateInterface.jsp");
	}
	
	/**
	 * չʾ�ӿڶ�Ӧ��ϵ��
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward interList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String face = request.getParameter("type");
		String cm_one = request.getParameter("cm_one");
		BiProd biProd = new BiProd();
		request.setAttribute("comList", biProd.getInterfaceMap(Integer
				.parseInt(face),cm_one));
		request.setAttribute("type",face);
		request.setAttribute("cm_one",cm_one);
		return new ActionForward("/employ/interfacelist.jsp");
	}

	/**
	 * չʾ�ӿڶ�Ӧ��ϵ�޸�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward interUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		String id = request.getParameter("cmid");
		String face = request.getParameter("intercm");
		String moenyId=request.getParameter("moenyId");
		BiProd biProd = new BiProd();
		int k = biProd.updateInterfaceMap(Integer.parseInt(type), Integer
				.parseInt(id), Integer.parseInt(face),moenyId);
//		request.setAttribute("mess", k == 0 ? "�ɹ�" : "ʧ��");
//		request.setAttribute("comList", biProd.getInterfaceMap(Integer
//				.parseInt(type)));
//		request.setAttribute("type",type);
//		return new ActionForward("/employ/interfacelist.jsp");
		PrintWriter printWriter = null;
		printWriter = response.getWriter();
		printWriter.print(k);
		printWriter.flush();
		printWriter.close();
		return null;
	}

	/**
	 * ��Ӵ��컧������Ӷ�����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward addEmployAvg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String down = request.getParameter("userno");
		String parentid = request.getParameter("parentid");
		// ${face[0]}#${face[1]}#${face[3]} ��Ӫ�� �������id �ܵ�ֵ Ӷ����
		String[] mobile = request.getParameterValues("ids");
		List<Object[]> lists = new ArrayList<Object[]>();
		List<Object[]> lists1 = new ArrayList<Object[]>();
		for (String string : mobile) {
			String[] value0 = string.split("#");
			String value1 = request.getParameter(string);
			if (Float.parseFloat(value1) > 100
					&& !(Float.parseFloat(value0[2]) < 0)) {
				request.setAttribute("mess", "��ֵҵ��Ӷ�������������");
				return new ActionForward("/employ/whtagentviewemploy.jsp");
			}
			Object[] params = { value0[3], value1 };
			lists.add(params);
		}
		// ${zg[0]}#${zg[1]} code name
		String[] yewu = request.getParameterValues("elids");
		for (String string : yewu) {
			String[] value = string.split("#");
			String value0 = request.getParameter("zh" + string);
			String value1 = request.getParameter(string);
			if (Float.parseFloat(value1) > 100) {
				request.setAttribute("mess", "��ֵҵ��Ӷ�������������");
				return new ActionForward("/employ/whtagentviewemploy.jsp");
			}
			Object[] params = { value[2], value1 };
			lists1.add(params);
		}
		BiProd biProd = new BiProd();
		int k = biProd.agentAndUserEmploy(lists, down, parentid, lists1);
		if (k == 0) {
			request.setAttribute("mess", "����ɹ�");
			return new ActionForward("/rights/wltuseradd2.jsp");
		} else {
			request.setAttribute("mess", "����ʧ�����Ժ�����");
			return new ActionForward("/rights/wltuseradd2.jsp");
		}
	}

	/**
	 * �۸������Ϣ��ѯ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gethfkList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (null == request.getSession().getAttribute("userSession")) {
			request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		SysUserForm loginUser = (SysUserForm) request.getSession()
				.getAttribute("userSession");

		BiProd biProd = new BiProd();
		List list = biProd.getHfkList(loginUser.getUserno());
		request.setAttribute("orderList", list);
		return new ActionForward("/business/wlthfklist.jsp");
	}

	/**
	 * �¶Ƚ���
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward awardsOP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (null == request.getSession().getAttribute("userSession")) {
			request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		SysUserForm loginUser = (SysUserForm) request.getSession()
				.getAttribute("userSession");

		String login = request.getParameter("login");
		String facct = request.getParameter("facct");
		String money = request.getParameter("money");
		String id = request.getParameter("id");
		BiProd biProd = new BiProd();
		int n = biProd.awardsOP(login, facct, money, loginUser.getUsername(),
				id);
		PrintWriter printWriter = null;
		printWriter = response.getWriter();
		printWriter.print(n);
		printWriter.flush();
		printWriter.close();
		return null;
	}

	/**
	 * ɾ����������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deldate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("ids");
		BiProd biProd = new BiProd();
		request.setAttribute("mess", biProd.deldate(id.replaceAll("-", "")) == true ? "ɾ���ɹ�" : "����ʧ��");
		return new ActionForward("/business/whtdatelimitlist.jsp");
	}
	
	/**
	 * �����������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward adddate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String fee0 = request.getParameter("startDate");
		BiProd biProd = new BiProd();
		request.setAttribute("mess", biProd.adddate(fee0.replaceAll("-","")) == 0 ? "��ӳɹ�" : "�����ظ�");
		return new ActionForward("/business/wltdatelimitadd.jsp");
	}
	
	
	/**
	 * �������б�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response 
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward setupGplist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BiSanForm userForm = (BiSanForm) form;
		PageAttribute page = new PageAttribute(userForm.getCurPage(),
				Constant.PAGE_SIZE);
		String srType = request.getParameter("st");
		SysCommission sc = new SysCommission();
		page.setRsCount(sc.setupGpSum(srType,userForm));
		List list = sc.getSetupGpinfo(page, srType,userForm);
		request.setAttribute("gplist", list);
		request.setAttribute("page", page);
		request.setAttribute("curPage", userForm.getCurPage());
		request.setAttribute("st", srType);
		request.setAttribute("userForm",userForm);
		return new ActionForward("/setup/setupGplist.jsp");
	}
	
	/**
	 * ��ӽ�������Ϣ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward addsetup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String setupname = request.getParameter("setupname");
		String cm_type = request.getParameter("cm_type");
		String cm = request.getParameter("cm");
		BiProd biProd = new BiProd();
		request.setAttribute("mess", biProd.addSetup(
				setupname,cm_type,cm) == 0 ? "��ӳɹ�" : "�����ظ�");
		return new ActionForward("/setup/setupadd.jsp");
	}
	
	
	/**
	 * ɾ��������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward delsetUPgp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			String id = request.getParameter("ids");
			if (null == id) {
				request.setAttribute("mess", "�޸ĳ���");
				return new ActionForward("/employ/whtmincommissionadd.jsp");
			}
			BiProd biProd = new BiProd();
			request.setAttribute("mess", true == biProd.delSetupGp(id) ? "ɾ���ɹ�" : "ɾ��ʧ��");
			return new ActionForward("/business/prod.do?method=setupGplist&st=0");
		}
	
	/**
	 * �Ḷ��T+0����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward hfkAddMoney(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String data="1";
		String id=request.getParameter("id");
		if (null == request.getSession().getAttribute("userSession")) {
			 data="2";//δ��¼
		}else if(null==id||id==""){
			 data="3";//idΪ��
		}else{
			SysUserForm loginUser = (SysUserForm) request.getSession()
			.getAttribute("userSession");
			BiProd biProd = new BiProd();
			data = ""+biProd.hfkAddMoney(loginUser.getUserno(),id,Tools.getNow3());
		}
		    PrintWriter printWriter = response.getWriter();
			printWriter.print(data);
			printWriter.flush();
			printWriter.close();
		return null;
	}

//	/**
//	 * ��ȡ��ǰ������ͨ������Ʒ 
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return null
//	 */
//	public ActionForward getUnicomFlow(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		response.setContentType("html/json; charset=UTF-8");
//		PrintWriter pWriter = null;
//		String phone=request.getParameter("tradeObject");
//		String areaId=request.getParameter("areaId");
//		String proid="";
//		Map<String, String> phoneAreas=HttpFillOP.phoneAreas;
//		Set<String> keys=phoneAreas.keySet();
//		for(String str:keys){
//			if(phoneAreas.get(str).equals(areaId)){
//				proid=str;
//				break;
//			}
//		}
//		String ACTIVITYID=request.getParameter("activityId");
//		String str=HttpFillOP.filterProductJSON(phone, proid, ACTIVITYID,"json").toString();
//		try {
//			pWriter = response.getWriter();
//			pWriter.print(str);
//			pWriter.flush();
//			pWriter.close();
//		} catch (Exception e) {
//			pWriter.print("");
//			pWriter.flush();
//			pWriter.close();
//		}
//		return null;
//	}
	
	
//	/**
//	 * ���컧������ֵ
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return null
//	 */
//	public ActionForward fillUnicomFlow(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		String phone=request.getParameter("tradeObject");
//		String areaId=request.getParameter("prodId");//ʡ��ID
//		String interfaceid=request.getParameter("interfaceid");
//		String name=request.getParameter("liu");
//		name=name.substring(0,name.length()-2);
//		int realPrice=(int)(Float.parseFloat(request.getParameter("price"))*100);//��
//		SysUserForm loginUser = (SysUserForm) request.getSession()
//		.getAttribute("userSession");
//		String productId="llcz";
//		Map<String, String> content = null;
//		if(interfaceid.equals(HttpFillOP.FLOw1)){
//			//�����˾�ӿ��ȵ��ò�Ʒͬ���ӿڻ�ȡ ��ƷID
//			FillProductInfo infos=HttpFillOP.filterProduct(phone, HttpFillOP.phoneAreas1.get(areaId), HttpFillOP.ACTIVITYID);
//			if(null==infos||!infos.status.equals("0")){
//				request.setAttribute("mess", "��ֵʧ��ͬ����Ʒʧ��");
//				return new ActionForward("/business/unicomflowcharge.jsp");	
//			}
//			boolean flag=false;
//			ArrayList<FillProductDetail> list=(ArrayList<FillProductDetail>)infos.getData().getList();
//			for(FillProductDetail detail:list){
//				if((name+"MB").equalsIgnoreCase(detail.getProduct_name())){
//					flag=true;
//					productId=detail.getProduct_id();
//					break;
//				}
//			}
//			if(!flag){
//				request.setAttribute("mess", "��ֵʧ�ܣ��޶�Ӧ��Ʒ");
//				return new ActionForward("/business/unicomflowcharge.jsp");		
//			}
//			content=HttpFillOP.fillFlow(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+(int) (Math.random() * (1000 - 100) + 100), 
//					productId, phone, realPrice, HttpFillOP.ACTIVITYID, 
//					loginUser.getUserno(), loginUser.getUsername(),
//					loginUser.getSa_zone(), areaId, loginUser.getUsersite(),
//					loginUser.getParent_id(),"", interfaceid, "0",name);
//		}else if(interfaceid.equals(HttpFillOP.FLOw2)){//������ͨ����
//			content=BeanFlow.fillFlow(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+(int) (Math.random() * (1000 - 100) + 100), 
//					productId, phone, realPrice, HttpFillOP.BEIFENUNICOM, 
//					loginUser.getUserno(), loginUser.getUsername(),
//					loginUser.getSa_zone(), areaId, loginUser.getUsersite(),
//					loginUser.getParent_id(),"", interfaceid, "0",name);
//		}else if(HttpFillOP.FLOW3.equals(interfaceid)){//˼���ƶ�����
//			content=BeanFlow.sikong_flow(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+(int) (Math.random() * (1000 - 100) + 100), 
//					productId, phone, realPrice, HttpFillOP.BEIFENUNICOM, 
//					loginUser.getUserno(), loginUser.getUsername(),
//					loginUser.getSa_zone(), areaId, loginUser.getUsersite(),
//					loginUser.getParent_id(),"", interfaceid, "0",name,"1");//�ƶ�
//		}else if(HttpFillOP.FLOW4.equals(interfaceid)){//˼����ͨ����
//			content=BeanFlow.sikong_flow(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+(int) (Math.random() * (1000 - 100) + 100), 
//					productId, phone, realPrice, HttpFillOP.BEIFENUNICOM, 
//					loginUser.getUserno(), loginUser.getUsername(),
//					loginUser.getSa_zone(), areaId, loginUser.getUsersite(),
//					loginUser.getParent_id(),"", interfaceid, "0",name,"2");//��ͨ
//		}else if(HttpFillOP.FLOW5.equals(interfaceid)){//˼�յ�������
//			content=BeanFlow.sikong_flow(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+(int) (Math.random() * (1000 - 100) + 100), 
//					productId, phone, realPrice, HttpFillOP.BEIFENUNICOM, 
//					loginUser.getUserno(), loginUser.getUsername(),
//					loginUser.getSa_zone(), areaId, loginUser.getUsersite(),
//					loginUser.getParent_id(),"", interfaceid, "0",name,"0");//����
//		}else if(HttpFillOP.FLOW6.equals(interfaceid)){//�����ƶ�����
//			content=BeanFlow.WebServices_flow(new SimpleDateFormat("yyyyMMdd").format(new Date())
//					+ ((int) (Math.random() * 100) + 100) + ""
//					+ ((char) (new Random().nextInt(26) + (int) 'A')) + ""
//					+ ((int) (Math.random() * 100) + 100)+((char) (new Random().nextInt(26) + (int) 'a')), 
//					productId, phone, realPrice, HttpFillOP.ZDY, 
//					loginUser.getUserno(), loginUser.getUsername(),
//					loginUser.getSa_zone(), areaId, loginUser.getUsersite(),
//					loginUser.getParent_id(),"", interfaceid, "0",name);
//		}else if(HttpFillOP.FLOW8.equals(interfaceid)){//����ͨ�ƶ�����
//			content=BeanFlow.Dhst_flow(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+(int) (Math.random() *10000 + 10000), 
//					productId, phone, realPrice, HttpFillOP.BEIFENUNICOM, 
//					loginUser.getUserno(), loginUser.getUsername(),
//					loginUser.getSa_zone(), areaId, loginUser.getUsersite(),
//					loginUser.getParent_id(),"", interfaceid, "0",name,"1");//�ƶ�
//		}else if(HttpFillOP.FLOW9.equals(interfaceid)){//����ͨ��ͨ����
//			content=BeanFlow.Dhst_flow(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+(int) (Math.random() *10000 + 10000), 
//					productId, phone, realPrice, HttpFillOP.BEIFENUNICOM, 
//					loginUser.getUserno(), loginUser.getUsername(),
//					loginUser.getSa_zone(), areaId, loginUser.getUsersite(),
//					loginUser.getParent_id(),"", interfaceid, "0",name,"2");//��ͨ
//		}else if(HttpFillOP.FLOW7.equals(interfaceid)){//����ͨ��������
//			content=BeanFlow.Dhst_flow(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+(int) (Math.random() *10000 + 10000), 
//					productId, phone, realPrice, HttpFillOP.BEIFENUNICOM, 
//					loginUser.getUserno(), loginUser.getUsername(),
//					loginUser.getSa_zone(), areaId, loginUser.getUsersite(),
//					loginUser.getParent_id(),"", interfaceid, "0",name,"0");//����
//		}
//		
//		if(null!=content){
//		String code =content.get("code");
//		if(code.equals("-1")||code.equals("1")||code.equals("-5")||code.equals("8")){
//			request.setAttribute("mess", "��ֵʧ�ܣ��������"+code);
//			return new ActionForward("/business/unicomflowcharge.jsp");
//		}else{
//			request.setAttribute("mess", "��ֵ�ɹ�");
//			return new ActionForward("/business/unicomflowcharge.jsp");
//		}
//		}else{
//			request.setAttribute("mess", "��ֵʧ��");
//			return new ActionForward("/business/unicomflowcharge.jsp");	
//		}
//	}
	
//	/**
//	 * ������Ӫ�̻�ȡ���Ͷ�Ӧ�ӿ� 
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return null
//	 */
//	public ActionForward getFaceBytype(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		response.setContentType("html/json; charset=UTF-8");
//		PrintWriter pWriter = null;
//		String types=request.getParameter("phtype");
//		List lists=BiProd.getFaceBytype(types);
//		String result=JSON.toJSONString(lists);
//		try {
//			pWriter = response.getWriter();
//			pWriter.print(result);
//			pWriter.flush();
//			pWriter.close();
//		} catch (Exception e) {
//			pWriter.print("");
//			pWriter.flush();
//			pWriter.close();
//		}
//		return null;
//	}
	
	/**
	 * �����޸������ӿ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null; 
	 */
	public ActionForward BatchUpdateFlowInterface(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		if (null == request.getSession().getAttribute("userSession")) {
			request.setAttribute("mess", "��¼��ʱ,�����µ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		SysUserForm loginUser = (SysUserForm) request.getSession().getAttribute("userSession");
		if(!"0".equals(loginUser.getRoleType())){
			request.setAttribute("mess", "��û��Ȩ��!");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		
		String[] value = request.getParameterValues("province");//ʡ��
		String face = request.getParameter("face0");//�ӿ�
		String yun = request.getParameter("type");//��Ӫ��
		List<String[]> arrsy=null;
		if(face==null || "".equals(face) ||yun==null || "".equals(yun)||value==null || value.length<=0){
			request.setAttribute("mess","ϵͳ�쳣,��������,����ʧ��!");
			request.setAttribute("arrsy",arrsy);
			return new ActionForward("/business/flowinterfacelist.jsp");
		}
		boolean bool=false;
		DBService db=null;
		try {
			db=new DBService();
			db.setAutoCommit(false);
			for(int i=0;i<value.length;i++){
				 String query=" SELECT interid FROM sys_flowinterfaceMaping WHERE pid="+value[i]
				 + " AND TYPE="+yun;
				 if(null==db.getString(query)){
					 db.update("insert into sys_flowinterfaceMaping(pid,type,interid) values("+
							 value[i]+","+yun+","+face+")");
				 }else{
					 db.update("update sys_flowinterfaceMaping set interid="+face+
							 " where pid="+value[i]+" and type="+yun);
				 }
			}
			db.commit();
		} catch (Exception e) {
			db.rollback();
			Log.error("�����޸������ӿڻ�ȡdb�쳣,,,ex:"+e);
			request.setAttribute("mess","ϵͳ�쳣,����ʧ��");
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
		request.setAttribute("type", yun);
		request.setAttribute("mess","�����ɹ�");
		return new ActionForward("/business/flowinterfacelist.jsp");
	}
	
	/**
	 * չʾ�����ӿڶ�Ӧ��ϵ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward flowinterList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		DBService db=null;
		List<String[]> comList=null;
		try {
			db=new DBService();
			comList=db.getList("SELECT a.type,b.sa_name ,c.name FROM sys_flowinterfaceMaping a LEFT JOIN sys_area b ON a.pid=b.sa_id LEFT JOIN sys_interface c ON a.interid=c.id WHERE a.type="+type);
		    if(null==comList||comList.size()<1){
		    	comList=null;
		    }	
		} catch (Exception e) {
				Log.error("չʾ�����ӿ�,,,ex:"+e);
			}finally{
				if(db!=null){
					db.close();
					db=null;
				}
			}
		request.setAttribute("type",type);
		request.setAttribute("comList", comList);
		return new ActionForward("/business/flowinterfacelist.jsp");
	}
	
	/**
	 * �ƶ���������ʡ������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 * @throws Exception
	 */
	public ActionForward Ydflowadd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	    String groupid=request.getParameter("cm_groups");
	    String values=request.getParameter("strs");
	    System.out.println(groupid+"\n"+values);
	    String[] datas=values.split("#");
		DBService db=null;
		String str="��ӳɹ�";
		try {
			db=new DBService();
			db.setAutoCommit(false);
			for(String data:datas){
				if(null!=data&&!"".equals(data)){
			String[] d=data.split("\\|");
			db.update("INSERT INTO sys_tpemploy_Flow(TYPE,cm_addr,VALUE,groups) VALUES(1,'"+d[0]+"',"+d[1]+",'"+groupid+"')");
				}
				}
			db.commit();
		} catch (Exception e) {
			db.rollback();
			Log.error("�ƶ�����ʡ��Ӷ������쳣����e:"+e);
			str="���ʧ��";
		}finally{
			if(null!=db){
				db.close();
				db=null;
			}
		}
		request.setAttribute("mess", str);
		return new ActionForward("/employ/FlowAddYdong.jsp");
	}
}