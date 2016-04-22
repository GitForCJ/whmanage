package com.wlt.webm.business.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.AcctBillBean;
import com.wlt.webm.business.bean.BiProd;
import com.wlt.webm.business.bean.OrderBean;
import com.wlt.webm.db.DBService;
import com.wlt.webm.gm.bean.GuanMingBean;
import com.wlt.webm.gm.form.Game;
import com.wlt.webm.gm.form.GameArea;
import com.wlt.webm.gm.form.GameInterface;
import com.wlt.webm.rights.form.SysUserForm;
import com.wlt.webm.scpdb.FundAcctDao;
import com.wlt.webm.ten.service.TenpayXmlPath;
import com.wlt.webm.tool.FloatArith;
import com.wlt.webm.tool.Tools;
import com.wlt.webm.util.TenpayUtil;

/**
 * ֧����ҵ����
 * 
 * @author tanwanlong
 * 
 */
public class ZhiFuBaoChargAction extends DispatchAction {

	/**
	 * �жϷ��ؽ����Action
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward zhiFuBaoTransfer(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		if (null == request.getSession().getAttribute("userSession")) {
			request.setAttribute("mess", "���ȵ�¼");
			return new ActionForward("/rights/wltlogin.jsp");
		}
		String ip = com.wlt.webm.util.Tools.getIpAddr(request);
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		if (!userForm.getRoleType().equals("3")) {
			request.setAttribute("mess", "��ǰ�û����Ͳ�֧�ֽ���");
			return new ActionForward("/business/zhifubaopay.jsp");
		}
		if (!Tools.validateTime()) {
			request.setAttribute("mess", "23:50��00:10��������");
			return new ActionForward("/business/zhifubaopay.jsp");
		}
		String order_price = request.getParameter("order_price");
		
		String[] strss=new BiProd().getPlaceMoney("2",userForm.getUserno());
		if((Float.parseFloat(strss[0])-(Float.parseFloat(strss[1])+Float.parseFloat(order_price)))<0)
		{
			request.setAttribute("mess", "������������,��Ӷ��,����ϵ����Ա!");
			return new ActionForward("/business/zhifubaopay.jsp");
		}
		String in_acct = request.getParameter("in_acct");
		if ("0".equals(order_price) || null == order_price || null == in_acct
				|| order_price == "" || in_acct == "") {
			request.setAttribute("mess", "����������������");
			return new ActionForward("/business/zhifubaopay.jsp");
		}
		if (Integer.parseInt(order_price) - 100 < 0) {
			request.setAttribute("mess", "�������ٳ�ֵ100Ԫ");
			return new ActionForward("/business/zhifubaopay.jsp");
		}
		if (Integer.parseInt(order_price) - 1000 > 0) {
			request.setAttribute("mess", "��������ֵ1000Ԫ");
			return new ActionForward("/business/zhifubaopay.jsp");
		}
		if (order_price.indexOf("-") != -1) {
			request.setAttribute("mess", "���׽��Ϸ�!");
			return new ActionForward("/business/zhifubaopay.jsp");
		}
		String user_no = userForm.getUserno();
		String sql0 = "select fundacct from wht_facct where user_no='"
				+ user_no + "'";
		DBService dbService = null;
		String currTime = TenpayUtil.getCurrTime();
		String seqNo = Tools.getStreamSerial(in_acct);
		String time = Tools.getNow3();
		String serialNo = "wh" + Tools.getNow3().substring(6)
				+ Tools.buildRandom(2) + Tools.buildRandom(3); // ֧������ֵ
		String writeoff = serialNo + "#" + user_no + "#" + in_acct + "#"
				+ Integer.parseInt(order_price) * 1000;
		String checkAccount = seqNo + "#" + serialNo + "#" + user_no + "#"
				+ in_acct + "#" + Integer.parseInt(order_price) * 1000;
		DBService db = null;
		com.wlt.webm.scpdb.DBService db1 = null;
		try {
			db1 = new com.wlt.webm.scpdb.DBService();
			FundAcctDao fd = new FundAcctDao(db1);
			db = new DBService();
			dbService = new DBService();
			if (dbService.hasData("select tradeserial from wht_orderform_"
					+ currTime.substring(2, 6) + " where tradeserial='" + seqNo
					+ "'")) {
				request.setAttribute("mess", "�ظ�����");
				return new ActionForward("/business/zhifubaopay.jsp");
			}
			String facct = dbService.getString(sql0);
			String sql = "select accountleft from  wht_childfacct where  childfacct ='"
					+ facct + "01'";
			int money = dbService.getInt(sql);
			int rebate = 1000;// ����
			if (money - Integer.parseInt(order_price) * 1000 <= 0) {
				if (null != dbService) {
					dbService.close();
				}
				request.setAttribute("mess", "�˻�����");
				return new ActionForward("/business/zhifubaopay.jsp");
			}
			String flag = AcctBillBean.getStatus(userForm.getParent_id()) == true ? "1"
					: "0";
			//BiProd bp = new BiProd();
			db.setAutoCommit(false);
			Integer facctFee = null;
			Integer parentfee = null;
			if ("1".equals(flag)) {// ֱӪ
				BigDecimal bd1 = new BigDecimal(order_price);
				BigDecimal bd2 = new BigDecimal(0.01);
				facctFee = (int) ((bd1.multiply(bd2).add(bd1)).doubleValue() * 1000);
			} else {
				BigDecimal bd1 = new BigDecimal(order_price);
				BigDecimal bd2 = new BigDecimal(0.01);
				BigDecimal bd3 = new BigDecimal(0.002);
				facctFee = (int) ((bd1.multiply(bd2).add(bd1)).doubleValue() * 1000);
				parentfee = (int) ((bd1.multiply(bd3)).doubleValue() * 1000);
			}
			String areaCode = "381";// ȫ��
			Object[] orderObj = { null, areaCode, seqNo, in_acct, 10, "0008",
					Integer.parseInt(order_price) * 1000, facctFee,
					facct + "01", time, time, 4, writeoff, checkAccount, "0",
					user_no, userForm.getUsername(), "5", areaCode, null };
			String acct1 = Tools.getAccountSerial(time, user_no);
			Object[] acctObj = { null, facct + "01", acct1, in_acct, time,
					Integer.parseInt(order_price) * 1000, facctFee, "23",
					"֧������ֵ", 0, time, money - facctFee, seqNo, facct + "01", 1 };
			db.update("update wht_childfacct set accountleft=accountleft-"
					+ facctFee + " where childfacct='" + facct + "01'");
			String tableName = "wht_acctbill_" + time.substring(2, 6);
			db.logData(20, orderObj, "wht_orderform_" + time.substring(2, 6));
			db.logData(15, acctObj, tableName);
			String acct2 = null;
			String parentFacct = "";
			Integer parentLeft = 0;
			if (!"1".equals(flag)) {
				parentFacct = fd.getFacctByUserID(Integer.parseInt(userForm
						.getParent_id()));
				parentLeft = fd.getChildFacctsLeft(parentFacct, "02");
				db.update("update wht_childfacct set accountleft=accountleft+"
						+ parentfee + " where childfacct='" + parentFacct
						+ "02'");
				acct2 = Tools.getAccountSerial(time, user_no);
				Object[] acctObj1 = { null, parentFacct + "02", acct2, in_acct,
						time, parentfee, parentfee, 15, "�¼����׷�Ӷ", 0, time,
						parentLeft + parentfee, seqNo, parentFacct + "02", 2 };
				db.logData(15, acctObj1, tableName);
			}
			db.commit();
			GuanMingBean gmb = new GuanMingBean();
			Map map = new HashMap<String, String>();
			map.put("gameapi", "epayeszfb");
			map.put("account", in_acct);
			map.put("buynum", order_price);
			map.put("sporderid", seqNo);
			map.put("gamecode", null);
			map.put("gamearea", null);
			map.put("gameserver", null);
			map.put("gamerole", null);
			map.put("clientip", ip);
			String returl = "http://www.wanhuipay.com/business/bank.do?method=GuanMingCallBack";
			// String returl =
			// "http://shijianqiao321.xicp.net:8888/business/bank.do?method=GuanMingCallBack"
			// ;
			map.put("returl", returl);
			Map<String, String> rmMap = gmb.fillGameOrder(map);
			String state = rmMap.get("state");
			Log.info("������:" + seqNo + " ��ֵ���=" + state);
			if (state.equals("0")) {
				request.setAttribute("mess", "��ֵ�ɹ�");
				return new ActionForward("/business/zhifubaopay.jsp");
			} else if (state.equals("-1")) {
				OrderBean.httpReturnDeal(-1, seqNo, 10);
				request.setAttribute("mess", "��ֵʧ��");
				return new ActionForward("/business/zhifubaopay.jsp");
			} else {
				request.setAttribute("mess", "�����쳣����ϵ�ͷ�");
				OrderBean.httpReturnDeal(-1, seqNo, 10);
				return new ActionForward("/business/zhifubaopay.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			db.rollback();
			request.setAttribute("mess", "ϵͳ�쳣����ϵ�ͷ�");
			return new ActionForward("/business/zhifubaopay.jsp");
		} finally {
			if (null != db) {
				db.close();
			}
			if (null != db1) {
				db1.close();
			}
			if (null != dbService) {
				dbService.close();
			}
		}
	}

	/**
	 * ��ȡ��Ϸ�б�
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward gameList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if (null == request.getSession().getAttribute("userSession")) {
				request.setAttribute("mess", "���ȵ�¼");
				return new ActionForward("/rights/wltlogin.jsp");
			}
			GuanMingBean gmb = new GuanMingBean();
			List<GameInterface> gameInterfaceList = gmb.queryGameInterface();
			
			TreeMap<String, List> map = new TreeMap<String, List>();
			for (GameInterface gif : gameInterfaceList) {
				List<Game> list = gmb.queryGame(gif.getGameapi());
				map.put(gif.getGameapi() + "," + gif.getGamename(), list);
			}
			request.setAttribute("list", gameInterfaceList);
			request.setAttribute("map", map);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("mess", "��Ϸ�б��ȡ����");
			return new ActionForward("/business/gamelist.jsp");
		}
		return new ActionForward("/business/gamelist.jsp");
	}


	/**
	 * ��Ϸ��ֵ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward qbTransfer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("html/json; charset=UTF-8");
		PrintWriter pWriter = null;
		try {
			pWriter = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (null == request.getSession().getAttribute("userSession")) {
			String mess = "��ֵʧ��,���ȵ�½";
			pWriter.print("[{\"mess\":\"" + mess + "\"}]");
			pWriter.flush();
			pWriter.close();
			return null;
		}
		SysUserForm userForm = (SysUserForm) request.getSession().getAttribute(
				"userSession");
		if (!userForm.getRoleType().equals("3")) {
			String mess = "��ǰ�û����Ͳ�֧�ֽ���";
			pWriter.print("[{\"mess\":\"" + mess + "\"}]");
			pWriter.flush();
			pWriter.close();
			return null;
		}
		if (!Tools.validateTime()) {
			String mess = "23:50��00:10��������";
			pWriter.print("[{\"mess\":\"" + mess + "\"}]");
			pWriter.flush();
			pWriter.close();
			return null;
		}
		String num = (null == request.getParameter("qbnum")) ? request
				.getParameter("order_price") : request.getParameter("qbnum");
		String in_acct = request.getParameter("in_acct");
		String gameapi = request.getParameter("gameapi"); // ��ֵ���ͱ��
		String gameId = request.getParameter("gameId");
		String gamecode = request.getParameter("gamecode");
		String gameserver=request.getParameter("gameaddress");
		if ("0".equals(num) || null == num || null == in_acct || num == ""
				|| in_acct == "" || gameapi == null || gameapi.equals("")) {
			String mess = "����������������";
			pWriter.print("[{\"mess\":\"" + mess + "\"}]");
			pWriter.flush();
			pWriter.close();
			return null;
		}
		if (num.indexOf("-") != -1) {
			String mess = "���׽��Ϸ�";
			pWriter.print("[{\"mess\":\"" + mess + "\"}]");
			pWriter.flush();
			pWriter.close();
			return null;
		}
		Integer price = Integer.parseInt(num);
		if (gameapi.equals("qqes4x")) {
			// num=(Integer.parseInt(num)*10)+"";
			price = price * 10;
		}
		String user_no = userForm.getUserno();
		String sql0 = "select fundacct from wht_facct where user_no='"
				+ user_no + "'";
		DBService dbService = null;
		// ��ǰʱ�� yyyyMMddHHmmss
		TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
		String currTime = TenpayUtil.getCurrTime();
		String transaction_id = TenpayUtil.getQBChars(currTime);
		String account = "";
		try {
			dbService = new DBService();
			if (dbService.hasData("select tradeserial from wht_orderform_"
					+ currTime.substring(2, 6) + " where tradeserial='"
					+ transaction_id + "'")) {
				String mess = "�ظ�����";
				pWriter.print("[{\"mess\":\"" + mess + "\"}]");
				pWriter.flush();
				pWriter.close();
				return null;
			}
			String facct = dbService.getString(sql0);
			String sql = "select accountleft from  wht_childfacct where  childfacct ='"
					+ facct + "01'";
			int money = dbService.getInt(sql);
			int rebate = 1000;// ����
			if (money - price * 1000 <= 0) {
				if (null != dbService) {
					dbService.close();
				}
				String mess = "�˻�����";
				pWriter.print("[{\"mess\":\"" + mess + "\"}]");
				pWriter.flush();
				pWriter.close();
				return null;
			}
			String flag = AcctBillBean.getStatus(userForm.getParent_id()) == true ? "1"
					: "0";
			BiProd bp = new BiProd();
			String[] employFee = bp.getZZEmploy(dbService, gameapi, flag,
					user_no, Integer.parseInt(userForm.getParent_id()));
			if (null == employFee) {
				String mess = "Ӷ��Ȳ���������ϵ�ͷ�";
				pWriter.print("[{\"mess\":\"" + mess + "\"}]");
				pWriter.flush();
				pWriter.close();
				return null;
			}

			// ����Ӷ��
			int facctfee = price * rebate;
			int parentfee = 0;
			if ("1".equals(flag)) {
				facctfee = (int) FloatArith.mul(facctfee, 1 - FloatArith.div(
						Double.valueOf(employFee[0]), 100));
			} else {
				double a = FloatArith.div(Double.valueOf(employFee[0]), 100);// ��Ӷ���
				double b = FloatArith.mul(facctfee, a);// ��Ӷ��

				double c = FloatArith.mul(b, FloatArith.div(Double
						.valueOf(employFee[1]), 100));// �����Ӧ��Ӷ��

				facctfee = (int) FloatArith.sub(facctfee, c);// Ӧ�۽��
				parentfee = (int) FloatArith.sub(b, c);// �ϼ��ڵ�Ӧ��Ӷ��
			}
			Object[] orderObj = { null, userForm.getSa_zone(), transaction_id,
					in_acct, "10", gameapi, price * rebate, facctfee,
					facct + "01", currTime, currTime, 4, "",
					transaction_id + "#" + price * rebate, "0", user_no,
					userForm.getUsername(), 4, 381, null };// 20
			String acct1 = Tools.getAccountSerial(currTime, user_no);
			Object[] acctObj = { null, facct + "01", acct1, in_acct, currTime,
					price * rebate, facctfee, 7, "��Ϸ�㿨", 0, currTime,
					money - facctfee, transaction_id, facct + "01", 1 };
			boolean rsFlag = false;
			String acct2 = Tools.getAccountSerial(currTime, user_no);
			String parentFacct = "";
			if ("1".equals(flag)) {
				rsFlag = bp.innerDeal(dbService, orderObj, acctObj, null, flag,
						facct, null, currTime, facctfee, 0);
			} else {
				parentFacct = bp.getFacctByUserID(dbService, Integer
						.parseInt(userForm.getParent_id()));
				String sqlq = "select accountleft from  wht_childfacct where  childfacct ='"
						+ parentFacct + "02'";
				int moneyq = dbService.getInt(sqlq);
				Object[] acctObj1 = { null, parentFacct + "02", acct2, in_acct,
						currTime, parentfee, parentfee, 15, "�¼����׷�Ӷ", 0,
						currTime, moneyq + parentfee, transaction_id,
						parentFacct + "02", 2 };
				rsFlag = bp
						.innerDeal(dbService, orderObj, acctObj, acctObj1,
								flag, facct, parentFacct, currTime, facctfee,
								parentfee);
			}
			if (rsFlag == false) {
				String mess = "����ʧ��";
				pWriter.print("[{\"mess\":\"" + mess + "\"}]");
				pWriter.flush();
				pWriter.close();
				return null;
			}
			String ip = Tools.getIpAddr(request);
			Map map = new HashMap<String, String>();
			map.put("gameapi", gameapi);
			map.put("account", in_acct);
			map.put("buynum", num);
			map.put("sporderid", transaction_id);
			map.put("gamecode", gamecode);
			map.put("gamearea", gameId);
			map.put("gameserver", gameserver);
			map.put("gamerole", null);
			map.put("clientip", ip);
			String returl = "http://www.wanhuipay.com/business/bank.do?method=GuanMingCallBack";
//			 String returl=
//			 "http://shijianqiao321a.xicp.net:8888/wh/business/bank.do?method=GuanMingCallBack"
//			 ;
			map.put("returl", returl);
			GuanMingBean gmBean = new GuanMingBean();
			Map<String, String> rs = gmBean.fillGameOrder(map);
			String state = rs.get("state");
			String msg = "";
			String orderTable = "wht_orderform_" + currTime.substring(2, 6);
			String acctTable = "wht_acctbill_" + currTime.substring(2, 6);
			if (state.equals("0")) {
				msg = "���׳ɹ�";
				bp.innerQQSuccessDeal(dbService, orderTable, transaction_id
						+ "#" + price * rebate + "#" + rs.get("orderid"),
						transaction_id, user_no,"2");
			} else if (state.equals("-1")) {
				msg = "���� ʧ��";
				if ("1".equals(flag)) {
					bp.innerFailDeal(dbService, orderTable, acctTable,
							transaction_id + "#" + price * rebate + "#"
									+ rs.get("orderid"), transaction_id,
							user_no, acct1, "", facctfee, 0, facct, "", "1");
				} else {
					bp.innerFailDeal(dbService, orderTable, acctTable,
							transaction_id + "#" + price * rebate + "#"
									+ rs.get("orderid"), transaction_id,
							user_no, acct1, acct2, facctfee, parentfee, facct,
							parentFacct, "0");
				}
			} else {
				msg = "���״���������ϵ�ͷ�";
				bp.innerTimeOutDeal(dbService, orderTable, transaction_id,
						user_no);
			}

			pWriter.print("[{\"mess\":\"" + msg + "\"}]");
			pWriter.flush();
			pWriter.close();
			return null;

		} catch (Exception e) {
			String mess = "ϵͳ�쳣����ϵ�ͷ�";
			pWriter.print("[{\"mess\":\"" + mess + "\"}]");
			pWriter.flush();
			pWriter.close();
			return null;
		}
	}

	/**
	 * ��ѯ��Ϸ������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward queryGameArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("html/json; charset=UTF-8");
		PrintWriter pWriter = null;
		try {
			pWriter = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (null == request.getSession().getAttribute("userSession")) {
			String mess = "��ѯʧ��,���ȵ�½";
			pWriter.print("[{\"mess\":\"" + mess + "\"}]");
			pWriter.flush();
			pWriter.close();
			return null;
		}
		try {
			String gameCode = request.getParameter("gameCode");
			String gameapi = request.getParameter("gameapi"); // ��ֵ���ͱ��
			if (null == gameCode || gameCode.equals("") || null == gameapi
					|| gameapi.equals("")) {
				String mess = "����������������";
				pWriter.print("[{\"mess\":\"" + mess + "\"}]");
				pWriter.flush();
				pWriter.close();
				return null;
			}
			GuanMingBean gmb = new GuanMingBean();
			List<GameArea> list = gmb.queryGameArea(gameapi, gameCode);
			JSONArray json = JSONArray.fromObject(list);
			pWriter.print(json);
			pWriter.flush();
			pWriter.close();
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			String mess = "ϵͳ�쳣����ϵ�ͷ�";
			pWriter.print("[{\"mess\":\"" + mess + "\"}]");
			pWriter.flush();
			pWriter.close();
			return null;
		}
	}

}
