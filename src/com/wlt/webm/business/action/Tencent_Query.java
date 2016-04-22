package com.wlt.webm.business.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.business.bean.unicom3gquery.HttpFillOP;
import com.wlt.webm.business.form.TPForm;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.MemcacheConfig;
import com.wlt.webm.tool.MD5;
import com.wlt.webm.util.Tools;

public class Tencent_Query extends DispatchAction {

	/**
	 * ��Ѷ������ѯ
	 */
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)  {
		StringBuffer result = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "GBK"));
			String lines;
			result.delete(0, result.length());
			while ((lines = reader.readLine()) != null) {
				result.append(lines);
			}
			if (result.length() <= 0) {
				Log.info("��Ѷ������ѯ��������,xml length<0 ");
				return null;
			}
		} catch (Exception e) {
			Log.error("��Ѷ������ѯ�������������쳣,,ex," + e);
			return null;
		}

		Map<String, String> map = new HashMap<String, String>();

		try {
			String resutXML=result.substring(4,result.length());
			
			Document docResult = DocumentHelper.parseText(resutXML);
			Element rootResult = docResult.getRootElement();
			List<Element> results = (List<Element>) rootResult.elements();
			for (Element item : results) {
				if ("sign".equals(item.getName())) {
					map.put(item.getName(), item.getText());
				}
				if ("head".equals(item.getName())) {
					List<Element> rs = item.elements();
					for (Element r : rs) {
						map.put(r.getName(), r.getText());
					}
				}
				if ("body".equals(item.getName())) {
					List<Element> bList = item.elements();
					for (Element b : bList) {
						map.put(b.getName(), b.getText());
					}
				}
			}
		} catch (Exception e) {
			Log.error("��Ѷ������ѯxml�����쳣,,xml����:" + result + ",,,,ex:" + e);
			return null;
		}
		Log.info("��Ѷ������ѯ��������,,," + map);
		
		String paipai_dealid = map.get("paipai_dealid");
		if (paipai_dealid == null || "".equals(paipai_dealid)) {
			Log.info("��Ѷ������ѯ��������,��������");
			// ��������
			send(getXmlString(map.get("cmdid"), map.get("sversion"), map
					.get("spid"), map.get("seqno"), "0", "", paipai_dealid, "",
					"REQUEST_FAILED", "", "", "", "Insufficient parameter"), response);
			return null;
		}

		// ǩ����֤
		String str = MD5.encode(result.substring(result.indexOf("<head>"),
				result.indexOf("</body>") + "</body>".length()).toString()
				+ Tencent_Flows.sign_key);
		if (!str.equals(map.get("sign"))) {
			Log.info("��Ѷ������ѯ��������,ǩ������,,��Ѷ������:" + paipai_dealid);
			// ǩ������
			send(getXmlString(map.get("cmdid"), map.get("sversion"), map
					.get("spid"), map.get("seqno"), "0", "", paipai_dealid, "",
					"REQUEST_FAILED", "", "", "", "MD5 signature error"), response);
			return null;
		}
		
		if(MemcacheConfig.getInstance().get1("tencent_flow_"+paipai_dealid)){
			Log.info("��Ѷ����������ѯ,һ�����ڲ�ѯ�����Ч,��Ѷ�����ţ�"+paipai_dealid);
			return null;
		}else{
			MemcacheConfig.getInstance().AddOuFei("tencent_flow_"+paipai_dealid,"");
		}

		DBService db = null;
		try {
			db = new DBService();
			ArrayList<String[]> arry=(ArrayList<String[]>)db.getList("SELECT o_tradeserial,o_writecheck,o_state,r_dis FROM wht_flow_Reissue WHERE o_writecheck='"+paipai_dealid+"' AND o_type="+HttpFillOP.tencent_code+
				" UNION "+
				" SELECT tradeserial,writecheck,state,'2' FROM wht_orderform_"+Tools.getNow3().substring(2,6)+" WHERE writecheck='"+paipai_dealid+"' AND phoneleft="+HttpFillOP.tencent_code);
			if(arry==null || arry.size()<=0){
				Log.info("��Ѷ������ѯ��������,����������,,��Ѷ������:" + paipai_dealid);
				send(getXmlString(map.get("cmdid"), map.get("sversion"), map
						.get("spid"), map.get("seqno"), "0", "", paipai_dealid,
						"", "ORDER_NOT_EXIST ", "", "", "", "Order does not exist"), response);
				return null;
			}
			//�������¼����������ɹ������У�ֱ����Ӧ 
			if(arry.size()==1){
				String[] st=arry.get(0);
				if(st!=null && "2".equals(st[3])){
					if("0".equals(st[2])){
						send(getXmlString(map.get("cmdid"), map.get("sversion"), map
								.get("spid"), map.get("seqno"), "0", "", paipai_dealid,
								st[0], "SUCCESS", "", Tools.getNow(), "", ""),
								response);
						return null;
					}else if("4".equals(st[2])){
						send(getXmlString(map.get("cmdid"), map.get("sversion"), map
								.get("spid"), map.get("seqno"), "0", "", paipai_dealid,
								st[0], "UNDERWAY", "", "", "", ""), response);
						return null;
					}else if("1".equals(st[2])){
						new Tencent_Flows().Failure_Back(paipai_dealid,st[0],Tencent_Flows.refundFee);
						
						Log.info("��Ѷ������ѯ����,��Ѷ������:" + paipai_dealid+",,,֪ͨ����ʧ�����������111,,");
						
						send(getXmlString(map.get("cmdid"), map.get("sversion"), map
								.get("spid"), map.get("seqno"), "0", "", paipai_dealid,
								st[0], "REFUND_SUCCESS", "", "", "12", "Recharge failure"),
								response);
						return null;
					}
				}
				return null;
			}
			String wh_order_num="";
			String state="";
			for(String[] s:arry){
				if("1".equals(s[3])){//�����,���䶩��
					state=s[2];
					continue;
				}
				if("0".equals(s[3])){//�����,����ԭʼ��¼
					wh_order_num=s[0];
					continue;
				}
			}
			//����� ��¼��
			if ("0".equals(state)) {
				send(getXmlString(map.get("cmdid"), map.get("sversion"), map
						.get("spid"), map.get("seqno"), "0", "", paipai_dealid,
						wh_order_num, "SUCCESS", "", Tools.getNow(), "", ""),
						response);
			} else if ("1".equals(state)) {
				new Tencent_Flows().Failure_Back(paipai_dealid,wh_order_num,Tencent_Flows.refundFee);
				
				Log.info("��Ѷ������ѯ����,��Ѷ������:" + paipai_dealid+",,,֪ͨ����ʧ�����������222,,");
				
				send(getXmlString(map.get("cmdid"), map.get("sversion"), map
						.get("spid"), map.get("seqno"), "0", "", paipai_dealid,
						wh_order_num, "REFUND_SUCCESS", "", "", "12", "Recharge failure"),
						response);
			} else {
				send(getXmlString(map.get("cmdid"), map.get("sversion"), map
						.get("spid"), map.get("seqno"), "0", "", paipai_dealid,
						wh_order_num, "UNDERWAY", "", "", "", ""), response);
			}
		} catch (Exception e) {
			Log.error("��Ѷ��ѯ�����쳣,,��Ѷ������:" + paipai_dealid + ",,,,ex:" + e);
		} finally {
			if (db != null) {
				db.close();
				db = null;
			}
		}
		return null;
	}
	

	/**
	 * ����xml�ַ�����װ
	 * 
	 * @param cmdid
	 * @param sversion
	 * @param spid
	 * @param seqno
	 * @param retcode
	 * @param errinfo
	 * @param paipai_dealid
	 * @param sp_order_id
	 * @param sp_deal_status
	 * @param price
	 * @param success_time
	 * @param failed_code
	 * @param failed_reason
	 * @return String
	 */
	@SuppressWarnings("unused")
	private String getXmlString(String cmdid, String sversion, String spid,
			String seqno, String retcode, String errinfo, String paipai_dealid,
			String sp_order_id, String sp_deal_status, String price,
			String success_time, String failed_code, String failed_reason) {
		StringBuffer buf = new StringBuffer(
				"<?xml version='1.0' encoding='GBK'?><response>");
		String str = "<head>" + "<cmdid>" + cmdid + "</cmdid>" + "<sversion>"
				+ sversion + "</sversion>" + "<spid>" + spid + "</spid>"
				+ "<time>" + Tools.getNow() + "</time>" + "<seqno>" + seqno
				+ "</seqno>" + "<retcode>" + retcode + "</retcode >"
				+ "<errinfo>" + errinfo + "</errinfo >" + "</head>" + "<body>"
				+ "<paipai_dealid>" + paipai_dealid + "</paipai_dealid>"
				+ "<sp_order_id>" + sp_order_id + "</sp_order_id>"
				+ "<sp_deal_status>" + sp_deal_status + "</sp_deal_status>"
				+ "<price>" + price + "</price>" + "<success_time>"
				+ success_time + "</success_time>" + "<failed_code>"
				+ failed_code + "</failed_code>" + "<failed_reason>"
				+ failed_reason + "</failed_reason>" + "</body>";
		buf.append(str);
		String sign = MD5.encode(str + Tencent_Flows.sign_key);
		buf.append("<sign>" + sign + "</sign></response>");
		return buf.toString();
	}

	/**
	 * ��Ӧ
	 * 
	 * @param str
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void send(String str, HttpServletResponse response) {
		Log.info("��Ѷ��ѯ����,��Ӧ��Ѷxml:"+str);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(str);
			out.flush();
			out.close();
		} catch (Exception e) {
			Log.error("��Ѷ��ѯ����,��Ӧ��������쳣,xml:"+str+",,,ex:" + e);
		}
	}

}
