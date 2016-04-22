package com.wlt.webm.business.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.db.DBService;
import com.wlt.webm.message.PortalSend;
import com.wlt.webm.tool.MD5;
import com.wlt.webm.util.Tools;

/**
 * @author ʩ����
 */
public class Tencent_Flows extends DispatchAction {
	/**
	 * 
	 */
	public static final String sign_key = "f47j8i4tr6ds8n2err65g8d45hw54w6a";

	//��ʽ
	public static final String userno = "0004611683";
	public static final String fundacct = "00006222747168301";
	
	//����
//	public static final String userno = "0000000201";
//	public static final String fundacct = "00000622231820101";
	
	public static final String refundFee="100";
	
	public static int seqNum=0;
	static{
		DBService db=null;
		try {
			db=new DBService();
			String firsit_order=db.getString("SELECT MAX(id)+1 FROM wht_orderform_"+Tools.getNow3().substring(2,6));
			if(firsit_order!=null && !"".equals(firsit_order.trim())){
				seqNum=Integer.parseInt(firsit_order);
			}
		} catch (Exception e) {
			Log.error("ϵͳ������Ѷ���򶩵��쳣������ex:"+e);
		}finally{
			if(db!=null){
				db.close();
				db=null;
			}
		}
	}
	private static Object lock = new Object();
	public static int getWeiyi(){
		synchronized (lock) {
			if(seqNum>=9999990){
				seqNum=0;
			}
			seqNum++;
			return seqNum;
		}
	}
	
	/**
	 * ��0 7λ
	 * @param ss
	 * @return
	 */
	public static String addLeft(String ss){
		String str="";
		for(int i=0;i<(7-ss.length());i++){
			str=str+"0";
		}
		return str+ss;
	}
	
	/**
	 * ��Ѷ����ֱ������
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return null
	 */
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
				Log.info("��Ѷ������ֵ����,xml����С��0,,");
				return null;
			}
		} catch (Exception e) {
			Log.error("��Ѷ����ֱ��������������쳣,,ex," + e);
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
			Log.error("��Ѷ����ֱ��xml�����쳣,,xml����:" + result + ",,,,ex:" + e);
			return null;
		}
		Log.info("��Ѷ����ֱ����������,,," + map);

		String paipai_dealid = map.get("paipai_dealid");// ��Ѷ��˾������
		String cardid = map.get("cardid");// ��ֵ����Ʒ���
		String num = map.get("num");// ��ֵ������
		String customer = map.get("customer");// ����ֵ�ʺŻ��ֻ�����
		String pay = map.get("pay");// ��λΪ�֣���200����2Ԫ
		String price = map.get("price");// ��Ѷ��˾���ۼ۸�(�ɱ���)
		String deal_time = map.get("deal_time");// ��Ѷ��˾����ʱ��

		if (cardid == null || "".equals(cardid.trim()) || customer == null
				|| "".equals(customer.trim()) || paipai_dealid == null
				|| "".equals(paipai_dealid.trim()) || num == null
				|| "".equals(num.trim()) || pay == null
				|| "".equals(pay.trim()) || price == null
				|| "".equals(price.trim()) || deal_time == null
				|| "".equals(deal_time.trim())) {
			Log.info("��Ѷ������ֵ����,��������,,,,��Ѷ������:"+paipai_dealid);
			// ��������
			send(getXmlString(map.get("cmdid"), map.get("sversion"), map
					.get("spid"), map.get("seqno"), "0", "", paipai_dealid, "",
					"REQUEST_FAILED", "", "", "", "Insufficient parameter"), response);
			return null;
		}

		// ǩ����֤
		String str = MD5.encode(result.substring(result.indexOf("<head>"),
				result.indexOf("</body>") + "</body>".length()).toString()
				+ sign_key);
		if (!str.equals(map.get("sign"))) {
			Log.info("��Ѷ������ֵ����,md5ǩ����֤����,,,,��Ѷ������:"+paipai_dealid);
			// ǩ������
			send(getXmlString(map.get("cmdid"), map.get("sversion"), map
					.get("spid"), map.get("seqno"), "0", "", paipai_dealid, "",
					"REQUEST_FAILED", "", "", "", "MD5 signature error"), response);
			return null;
		}

		String operator = cardid.substring(2, 4);
		if ("yd".equals(operator)) {
			operator = "1";
		} else if ("lt".equals(operator)) {
			operator = "2";
		} else if ("dx".equals(operator)) {
			operator = "0";
		} else {
			Log.info("��Ѷ������ֵ����,cardid������Ӫ�̴���,,��Ѷ������:"+paipai_dealid);
			// δ֪��Ӫ��
			send(getXmlString(map.get("cmdid"), map.get("sversion"), map
					.get("spid"), map.get("seqno"), "0", "", paipai_dealid, "",
					"REQUEST_FAILED", "", "", "", "Cardid parsing error"), response);
			return null;
		}
		// ʱ��
		String time = Tools.getNow3();
		// ��������ƽ̨Ψһ��ˮ�ţ���ʽ yyyyMMdd+7λΨһ��
		String seqNo1 = time.substring(0,8)+addLeft(getWeiyi()+"");
		
		//��װ��Ѷ������Ϣ��������Ѷ����������
		TencentOrdersBean bean=new TencentOrdersBean(paipai_dealid,cardid,num,customer,pay,price,deal_time,seqNo1,userno,operator);
		boolean bool=PortalSend.getInstance().Send_Tencent_Message(bean);
		if(!bool){
			Log.info("������Ѷ������ֵ������������Ϣ�ж�ʧ�ܣ���Ѷ�����ţ�"+paipai_dealid);
			return null;
		}
		
		//��Ӧ��ֵ������
		send(getXmlString(map.get("cmdid"), map.get("sversion"), map
				.get("spid"), map.get("seqno"), "0", "", paipai_dealid,
				seqNo1, "UNDERWAY", "", Tools.getNow(), "", ""),
				response);
		
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
	public static String getXmlString(String cmdid, String sversion, String spid,
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
		String sign = MD5.encode(str + sign_key);
		buf.append("<sign>" + sign + "</sign></response>");
		return buf.toString();
	}

	/**
	 * ��Ӧ
	 * 
	 * @param str
	 * @param response
	 */
	private void send(String str, HttpServletResponse response) {
		Log.info("��Ӧ��Ѷxml:"+str);
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(str);
			out.flush();
			out.close();
		} catch (Exception e) {
			Log.error("��Ӧ��������쳣,xml:"+str+",,,ex:" + e);
		}
	}

	/**
	 * @param orderId ��Ѷ������
	 * @param spOrderId ��㶩����
	 * @param refundFee 
	 * @return string 
	 * @throws Exception 
	 * @throws Exception
	 */
	public static String Success_Back(String orderId,String spOrderId,String refundFee) throws Exception{
		String httpUrl = "http://api.chong.qq.com/v2/virbiz/supplierReplyUpdate.xhtml?";
		String url = "/v2/virbiz/supplierReplyUpdate.xhtml";
		String type = "GET";
		String secretOAuthKey = "e8d5df4g4e8p93z1";

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appOAuthID", "700196312");
		params.put("timeStamp", System.currentTimeMillis() + "");
		params.put("accessToken", "t7g9s41oz3b1l6s48y7qx341ij9d42fg");
		params.put("charset", "gbk");
		params.put("format", "xml");
		params.put("randomValue", ((int) (Math.random() * 10000) + 10000) + "");
		params.put("uin", "3331663323");
		params.put("sellerUin", "3331663323");
		params.put("orderId", orderId);
		params.put("spOrderId",spOrderId);
		params.put("spOrderStatus", "0");
		params.put("orderStatus", "SUCCESS");
		params.put("price", refundFee);
		params.put("dealSuccessTime",Tools.getNow());
		params.put("extFlag", "0");
		params.put("extInfo", "0x000");
		params.put("failedCode", "0x000");
		params.put("failedReason", "0x000");

		String sign="";
		try {
			sign = com.wlt.webm.tool.Tools.PaiPaiSign(url, params, type, secretOAuthKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		params.put("sign", sign);
		
		String cs = "";
		for (String key : params.keySet()) {
			cs += (key + "=" + URLEncoder.encode(params.get(key),"gbk")) + "&";
		}
		cs=cs.substring(0,cs.length()-1);
		return Send_Http(httpUrl + cs);
	}

	/**
	 * @param payId ֧��������
	 * @param spRefundId �˷Ѷ�����
	 * @param refundFee �˷ѽ��
	 * @return string
	 * @throws Exception
	 */
	public static String Failure_Back(String payId,String spRefundId,String refundFee) throws Exception {
		String httpUrl = "http://api.chong.qq.com/v2/virbiz/refund.xhtml?";
		String url = "/v2/virbiz/refund.xhtml";
		String type = "GET";
		String secretOAuthKey = "e8d5df4g4e8p93z1";

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("appOAuthID", "700196312");
		params.put("timeStamp", System.currentTimeMillis() + "");
		params.put("accessToken", "t7g9s41oz3b1l6s48y7qx341ij9d42fg");
		params.put("charset", "gbk");
		params.put("format", "xml");
		params.put("randomValue", ((int) (Math.random() * 10000) + 10000) + "");
		params.put("uin", "3331663323");
		params.put("sellerUin", "3331663323");
		params.put("payId",payId);
		params.put("spRefundId", spRefundId);
		params.put("refundFee",refundFee);
		params.put("chongAmount", "0");
		params.put("failedCode", "12");
		params.put("failedReason","12");
		
		String sign = com.wlt.webm.tool.Tools.PaiPaiSign(url, params, type, secretOAuthKey);
		params.put("sign", sign);
		
		String cs = "";
		for (String key : params.keySet()) {
			cs += (key + "=" + URLEncoder.encode(params.get(key),"gbk")) + "&";
		}
		cs=cs.substring(0,cs.length()-1);
		return Send_Http(httpUrl + cs);
	}

	/**
	 * ��Ʒ�ϼ� �¼�
	 * 
	 * @return
	 * @throws Exception
	 */
	public  String Openation() throws Exception {
		String httpUrl = "http://api.chong.qq.com/virbiz/submitItemTask.xhtml?";
		String url = "/virbiz/submitItemTask.xhtml";
		String type = "GET";
		String secretOAuthKey = "e8d5df4g4e8p93z1";

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("timeStamp", System.currentTimeMillis() + "");
		params.put("randomValue", ((int) (Math.random() * 10000) + 10000) + "");
		params.put("uin", "3331663323");
		params.put("accessToken", "t7g9s41oz3b1l6s48y7qx341ij9d42fg");
		params.put("appOAuthID", "700196312");
		params.put("format", "xml");
		params.put("pureData", "0");
		params.put("needRoot", "0");
		params.put("callback", "action");
		
		params.put("itemCode","ahltll20");//��Ʒ���룺��gdyd100����ʾ�㶫�ƶ�100Ԫ
		params.put("businessId", "1");//ҵ�����ͣ�1�����ѳ�ֵҵ��2�����γ�ֵҵ��
		params.put("actionType", "2");//�������ͣ�1����Ʒ�ϼܣ�2����Ʒ�¼�
		
		String sign = com.wlt.webm.tool.Tools.PaiPaiSign(url, params, type, secretOAuthKey);
		params.put("sign", sign);
		
		String cs = "";
		for (String key : params.keySet()) {
			cs += (key + "=" + URLEncoder.encode(params.get(key),"gbk")) + "&";
		}
		cs=cs.substring(0,cs.length()-1);
		return Send_Http(httpUrl + cs);
	}

	/**
	 * @param url
	 * @return string
	 */
	public static String Send_Http(String url) {
		Log.info("http֪ͨ��Ѷ����url��"+url);
		HttpClient client = null;
		GetMethod get = null;
		try {
			client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(30*1000);//����ʱ�� 20�룬��λ�Ǻ���
			client.getHttpConnectionManager().getParams().setSoTimeout(20*1000);//��ȡʱ�� 20�룬��λ�Ǻ���
			get = new GetMethod(url);
			int status = client.executeMethod(get);
			Log.info("http֪ͨ��Ѷ����,http״̬:"+status+",,����url��"+url);
			if (status == 200) {
				String resultString=inputStream2String(get.getResponseBodyAsStream(),"gbk");
				Log.info("http֪ͨ��Ѷ����,,����url��"+url+",,��Ѷ��Ӧ����:"+resultString);
				return resultString;
			}
		} catch (Exception e) {
			Log.error("http֪ͨ��Ѷ��,,����url��"+url+",,,ϵͳ�쳣����ex:"+e);
		} finally {
			get.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager())
					.shutdown();
			if (null != client) {
				client = null;
			}
		}
		return null;
	}
	
	 /**http ���� InputStream  ת string
     * @param in
     * @param encoding
     * @return string
     * @throws IOException
     */
    public static String inputStream2String (InputStream in , String encoding) throws IOException {
	    StringBuffer out = new StringBuffer();
	    InputStreamReader inread = new InputStreamReader(in,encoding);
	    char[] b = new char[4096];
	    for (int n; (n = inread.read(b)) != -1;) {
	        out.append(new String(b, 0, n));
	    }
	    return out.toString();
    } 
}
