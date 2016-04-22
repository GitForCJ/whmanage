package com.wlt.webm.business.bean.lechong;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.struts.action.ActionForward;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.commsoft.epay.util.logging.Log;
import com.sun.org.apache.commons.collections.Buffer;
import com.wlt.webm.db.DBService;
import com.wlt.webm.scputil.MD5Util;
import com.wlt.webm.tool.Tools;

public class MobileRecharge {

	// ��ֵ����url
	private static final String RECHARGE_URL = "http://121.201.96.49:8107/MainServiceBusiness/SendPhoneChargeInfo";
	// ��ֵ��ѯurl
	private static final String RECHARGE_QUERY_URL = "http://121.201.96.49:8107/MainServiceBusiness/GetOrderInfo";
	// ��������
	private static final String CORRECT_URL = "http://121.201.96.49:8107/MainServiceBusiness/SendClientPhoneReverseInfo";
	// ������ѯ
	private static final String CORRECT_QUERY_URL = "http://121.201.96.49:8107/MainServiceBusiness/GetPhoneReverseInfo";

	private static final String chargenumbertype = "1"; // �ֻ�����Ϊ1 �̻�����Ϊ2
	private static final String agentid = "10478";// ������id
	private static final String returntype = "2";// 1��ʾget����// 2��ʾ����XML��Ϣ��Ŀǰֻ֧��xml��ʽ���ء�
	private static final String ispname = "";// �̻�ʱ��Ҫ�����ƶ�����ͨ�����Ŵ����֣���utf-8���룬����MD5����ʱ���ñ��룬�ֻ��ſɿա�
	private static final String source = "2"; // ����������д2���˴������д2�������������ύ�������ظ���

	private static final String merchantKey = "fa79e92d213a46bd995970970d362d5a";// md5 // ����key

	public static void main(String[] args) {
		// Recharge("1111","18824588427","1");
		// Query("1111");
		// Correct("1111","18824588427","1");
		CorrectQuery("150617142530563947800004");
	}

	/**
	 * �ƶ���ֵ
	 * 
	 * @param orderid
	 * @param phone
	 * @param fee
	 * @return -1ʧ�� 0�ɹ� ����������
	 */
	@SuppressWarnings("unchecked")
	public static int Recharge(String orderid, String phone, String fee) {
		if (orderid == null || "".equals(orderid) || phone == null
				|| "".equals(phone) || phone.trim().length() != 11
				|| fee == null || "".equals(fee)) {
			Log.info("�ֳ��ƶ�,,��ֵ����,,��������,,orderid:" + orderid + ",phone:" + phone
					+ ",fee:" + fee + ",return -1,ʧ�ܴ���");
			return -1;
		}

		String str = "chargenumbertype=" + chargenumbertype + "&agentid="
				+ agentid + "&returntype=" + returntype + "&orderid=" + orderid
				+ "&chargenumber=" + phone.trim() + "&amountmoney="
				+ fee.trim() + "&ispname=" + ispname + "&source=" + source
				+ "&merchantKey=" + merchantKey;
		String verifystring = MD5Util.MD5Encode(str, "utf-8");

		StringBuffer buf = new StringBuffer(RECHARGE_URL);
		buf.append("?");
		buf.append("chargenumbertype=" + chargenumbertype);
		buf.append("&agentid=" + agentid);
		buf.append("&returntype=" + returntype);
		buf.append("&orderid=" + orderid);
		buf.append("&chargenumber=" + phone.trim());
		buf.append("&amountmoney=" + fee.trim());
		buf.append("&ispname=" + ispname);
		buf.append("&source=" + source);
		buf.append("&verifystring=" + verifystring);
		Log.info("�ֳ��ƶ�,,��ֵ����,,orderid:" + orderid + ",phone:" + phone + ",url:"
				+ buf.toString());

		HttpClient client = null;
		GetMethod get = null;
		try {
			client = new HttpClient();
			get = new GetMethod(buf.toString());
			// ����ʱ�� 30�룬��λ�Ǻ���
			client.getHttpConnectionManager().getParams().setConnectionTimeout(
					60 * 1000);
			// ��ȡʱ�� 10�룬��λ�Ǻ���
			client.getHttpConnectionManager().getParams().setSoTimeout(
					60 * 1000);
			int status = client.executeMethod(get);
			Log.info("�ֳ��ƶ�,,��ֵ����,,http��Ӧ״̬,,orderid:" + orderid + ",phone:"
					+ phone + ",status:" + status);
			if (status == 200) {
				String result = get.getResponseBodyAsString();
				Log.info("�ֳ��ƶ�,,��ֵ����,,��Ӧxml����,,orderid:" + orderid + ",phone:"
						+ phone + ",result,xml:" + result);

				if (result != null && !"".equals(result)) {
					HashMap<String, String> resultMap = new HashMap<String, String>();
					Document docResult = DocumentHelper.parseText(result);
					Element rootResult = docResult.getRootElement();
					List<Element> results = rootResult.elements();
					for (Element item1 : results) {
						resultMap.put(item1.getName(), item1.getText());
					}
					if (resultMap.size() > 0) {
						String rsCode = resultMap.get("resultno");
						if ("0014".equals(rsCode)) {
							Log.info("�ֳ��ƶ�,,��ֵ����,,����״̬,,orderid:" + orderid
									+ ",phone:" + phone + ",return:0,��ֵ�ɹ�!");
							// ��ֵ�ɹ�,������֤��������
							return 0;
						} else if ("0001".equals(rsCode)
								|| "0002".equals(rsCode)
								|| "0003".equals(rsCode)
								|| "0004".equals(rsCode)
								|| "0005".equals(rsCode)
								|| "0006".equals(rsCode)
								|| "0007".equals(rsCode)
								|| "0009".equals(rsCode)
								|| "0011".equals(rsCode)
								|| "0012".equals(rsCode)
								|| "0013".equals(rsCode)
								|| "0015".equals(rsCode)) {
							// ȷ����ֵʧ��
							Log.info("�ֳ��ƶ�,,��ֵ����,,����״̬,,orderid:" + orderid
									+ ",phone:" + phone + ",return:-1,��ֵʧ��!");
							return -1;
						}
					}
				}
			} else {
				// �ύʧ��,ϵͳ�쳣
				Log.info("�ֳ��ƶ�,,��ֵ����,,orderid:" + orderid + ",phone:" + phone
						+ ",return:-1,http�ύʧ��!");
				return -1;
			}
		} catch (Exception e) {
			Log.error("�ֳ��ƶ�,,��ֵ����,,ϵͳ�쳣,,orderid:" + orderid + ",phone:"
					+ phone + ",ex:" + e);
		} finally {
			get.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager())
					.shutdown();
			if (null != client) {
				client = null;
			}
		}
		Log.info("�ֳ��ƶ�,,��ֵ����,,����״̬δ֪,���ò�ѯ�ӿ�,,orderid:" + orderid + ",phone:"
				+ phone);
		// ���ò�ѯ�ӿ�
		int result_code = Query(orderid);
		Log.info("�ֳ��ƶ�,,��ֵ����,���ò�ѯ�ӿڷ���,,orderid:" + orderid + ",phone:" + phone
				+ ",,����ֵ(0�ɹ�,-1ʧ��,����������):" + result_code);
		return result_code;
	}

	/**
	 * ������ѯ
	 * 
	 * @param orderid
	 * @return -1ʧ�� 0�ɹ� ���������� (����״̬)
	 */
	public static int Query(String orderid) {
		String str = "agentid=" + agentid + "&returntype=" + returntype
				+ "&orderid=" + orderid + "&merchantKey=" + merchantKey;
		String verifystring = MD5Util.MD5Encode(str, "utf-8");
		StringBuffer buf = new StringBuffer(RECHARGE_QUERY_URL);
		buf.append("?");
		buf.append("agentid=" + agentid);
		buf.append("&returntype=" + returntype);
		buf.append("&orderid=" + orderid);
		buf.append("&verifystring=" + verifystring);

		Log.info("�ֳ��ƶ�,��ֵ��ѯ,��ʼѭ����ѯ,������:" + orderid + ",����url:" + buf.toString());

		int con = 0;
		while (con < 20) {
			con++;
			try {
				Thread.sleep(5 * 1000);
			} catch (Exception e) {
			}

			HttpClient client = null;
			GetMethod get = null;
			try {
				client = new HttpClient();
				get = new GetMethod(buf.toString());
				client.getHttpConnectionManager().getParams()
						.setConnectionTimeout(10 * 1000);
				client.getHttpConnectionManager().getParams().setSoTimeout(
						10 * 1000);
				int status = client.executeMethod(get);
				Log.info("�ֳ��ƶ�,��ֵ��ѯ,��:" + con + "��ѭ��,������:" + orderid
						+ ",http��Ӧ״̬status:" + status);
				if (status == 200) {
					String result = get.getResponseBodyAsString();
					Log.info("�ֳ��ƶ�,��ֵ��ѯ,��:" + con + "��ѭ��,������:" + orderid
							+ ",��Ӧxml,result:" + result);
					if (result != null && !"".equals(result)) {
						HashMap<String, String> resultMap = new HashMap<String, String>();
						Document docResult = DocumentHelper.parseText(result);
						Element rootResult = docResult.getRootElement();
						List<Element> results = rootResult.elements();
						for (Element item1 : results) {
							resultMap.put(item1.getName(), item1.getText());
						}
						if (resultMap.size() > 0) {
							String rsCode = resultMap.get("resultno");
							if ("0014".equals(rsCode)) {
								Log.info("�ֳ��ƶ�,��ֵ��ѯ,��:" + con + "��ѭ��,������:"
										+ orderid + ",return:0,��ֵ�ɹ�!");
								// ��ֵ�ɹ�,������֤��������
								return 0;
							} else if ("0015".equals(rsCode)) {
								// ȷ����ֵʧ��
								Log.info("�ֳ��ƶ�,��ֵ��ѯ,��:" + con + "��ѭ��,������:"
										+ orderid + ",return:-1,��ֵʧ��!");
								return -1;
							}
						}
					}
				}
			} catch (Exception e) {
				Log.error("�ֳ��ƶ�,��ֵ��ѯ,��:" + con + "��ѭ��,������:" + orderid
						+ ",ϵͳ�쳣,ex:" + e);
			} finally {
				get.releaseConnection();
				((SimpleHttpConnectionManager) client
						.getHttpConnectionManager()).shutdown();
				if (null != client) {
					client = null;
				}
			}
		}
		Log.info("�ֳ��ƶ�,��ֵ��ѯ,��:" + con + "��ѭ��,������ѯѭ��,����ȷ�������,������:" + orderid
				+ ",return 3,������!");
		return 3;
	}

	/**
	 * �ƶ�����
	 * 
	 * @param orderid
	 * @param phone
	 * @param fee
	 * @return -1ʧ�� 0�ɹ� ����������
	 */
	public static int Correct(String orderid, String phone, String fee) {

		String str = "agentid=" + agentid + "&returntype=" + returntype
				+ "&chargenumber=" + phone + "&amountmoney=" + fee
				+ "&merchantKey=" + merchantKey;
		String verifystring = MD5Util.MD5Encode(str, "utf-8");

		StringBuffer buf = new StringBuffer(CORRECT_URL);
		buf.append("?");
		buf.append("agentid=" + agentid);
		buf.append("&returntype=" + returntype);
		buf.append("&orderid=" + orderid);
		buf.append("&chargenumber=" + phone);
		buf.append("&amountmoney=" + fee);
		buf.append("&verifystring=" + verifystring);
		Log.info("�ֳ��ƶ�,,��������,,orderid:" + orderid + ",phone:" + phone + ",url:" + buf.toString());

		HttpClient client = null;
		GetMethod get = null;
		try {
			client = new HttpClient();
			get = new GetMethod(buf.toString());
			// ����ʱ�� 30�룬��λ�Ǻ���
			client.getHttpConnectionManager().getParams().setConnectionTimeout(
					60 * 1000);
			// ��ȡʱ�� 10�룬��λ�Ǻ���
			client.getHttpConnectionManager().getParams().setSoTimeout(
					60 * 1000);
			int status = client.executeMethod(get);
			Log.info("�ֳ��ƶ�,,��������,,orderid:" + orderid + ",phone:" + phone
					+ ",http��Ӧ״̬status:" + status);
			if (status == 200) {
				String result = get.getResponseBodyAsString();
				Log.info("�ֳ��ƶ�,,��������,,��Ӧxml����,,orderid:" + orderid + ",phone:"
						+ phone + ",result,xml:" + result);
				if (result != null && !"".equals(result)) {
					HashMap<String, String> resultMap = new HashMap<String, String>();
					Document docResult = DocumentHelper.parseText(result);
					Element rootResult = docResult.getRootElement();
					List<Element> results = rootResult.elements();
					for (Element item1 : results) {
						resultMap.put(item1.getName(), item1.getText());
					}
					if (resultMap.size() > 0) {
						String rsCode = resultMap.get("resultno");
						if ("0024".equals(rsCode)) {
							Log.info("�ֳ��ƶ�,,��������,,orderid:" + orderid
									+ ",phone:" + phone + ",return:0,�����ɹ�!");
							// �����ɹ�,������֤��������
							return 0;
						} else if ("0025".equals(rsCode)
								|| "0119".equals(rsCode)
								|| "0111".equals(rsCode)) {
							// ȷ������ʧ��
							Log.info("�ֳ��ƶ�,,��������,,orderid:" + orderid + ",phone:" + phone + ",return:-1,����ʧ��!");
							return -1;
						}else{
							Log.info("�ֳ��ƶ�,,��������,,����������,���ó��������ѯ,,orderid:"+ orderid + ",phone:" + phone);
							int result_code = CorrectQuery(orderid);
							Log.info("�ֳ��ƶ�,,��������,,���������ѯ����,,orderid:" + orderid + ",phone:" + phone + ",return:" + result_code);
							return result_code;
						}
					}
				}
			}
		} catch (Exception e) {
			Log.error("�ֳ��ƶ�,,��������,,orderid:" + orderid + ",phone:" + phone + ",ϵͳ�쳣ex:" + e);
		} finally {
			get.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager())
					.shutdown();
			if (null != client) {
				client = null;
			}
		}
		Log.info("�ֳ��ƶ�,,��������,,����ȷ�������,,orderid:" + orderid + ",phone:" + phone +",,return 3,������״̬");
		// ����������
		return 3;
	}

	/**
	 * ���������ѯ
	 * 
	 * @param orderid
	 * @return -1ʧ�� 0�ɹ� ����������
	 */
	public static int CorrectQuery(String orderid) {
		String str = "agentid=" + agentid + "&returntype=" + returntype
				+ "&orderid=" + orderid + "&merchantKey=" + merchantKey;
		String verifystring = MD5Util.MD5Encode(str, "utf-8");
		StringBuffer buf = new StringBuffer(CORRECT_QUERY_URL);
		buf.append("?");
		buf.append("agentid=" + agentid);
		buf.append("&returntype=" + returntype);
		buf.append("&orderid=" + orderid);
		buf.append("&verifystring=" + verifystring);

		Log.info("�ֳ��ƶ�,,���������ѯ,,��ʼѭ����ѯ�������״̬,,orderid:" + orderid + ",url:" + buf.toString());
		int con = 0;
		while (con < 20) {
			con++;
			try {
				Thread.sleep(5 * 1000);
			} catch (Exception e) { }

			HttpClient client = null;
			GetMethod get = null;
			try {
				client = new HttpClient();
				get = new GetMethod(buf.toString());
				client.getHttpConnectionManager().getParams()
						.setConnectionTimeout(10 * 1000);
				client.getHttpConnectionManager().getParams().setSoTimeout(
						10 * 1000);
				int status = client.executeMethod(get);
				Log.info("�ֳ��ƶ�,���������ѯ,��:" + con + "��ѭ��,������:" + orderid
						+ ",http��Ӧ״̬status:" + status);
				if (status == 200) {
					String result = get.getResponseBodyAsString();
					Log.info("�ֳ��ƶ�,���������ѯ,��:" + con + "��ѭ��,������:" + orderid
							+ ",��Ӧxml,result:" + result);
					if (result != null && !"".equals(result)) {
						HashMap<String, String> resultMap = new HashMap<String, String>();
						Document docResult = DocumentHelper.parseText(result);
						Element rootResult = docResult.getRootElement();
						List<Element> results = rootResult.elements();
						for (Element item1 : results) {
							resultMap.put(item1.getName(), item1.getText());
						}
						if (resultMap.size() > 0) {
							String rsCode = resultMap.get("resultno");
							if ("0024".equals(rsCode)) {
								Log.info("�ֳ��ƶ�,���������ѯ,��:" + con + "��ѭ��,������:" + orderid + ",return:0,�����ɹ�!");
								// ��ֵ�ɹ�,������֤��������
								return 0;
							} else if ("0025".equals(rsCode)
									|| "0119".equals(rsCode)
									|| "0111".equals(rsCode)) {
								// ȷ����ֵʧ��
								Log.info("�ֳ��ƶ�,���������ѯ,��:" + con + "��ѭ��,������:" + orderid + ",return:-1,����ʧ��!");
								return -1;
							}
						}
					}
				}
			} catch (Exception e) {
				Log.error("�ֳ��ƶ�,���������ѯ,��:" + con + "��ѭ��,������:" + orderid + ",ϵͳ�쳣,ex:" + e);
			} finally {
				get.releaseConnection();
				((SimpleHttpConnectionManager) client
						.getHttpConnectionManager()).shutdown();
				if (null != client) {
					client = null;
				}
			}
		}
		Log.info("�ֳ��ƶ�,���������ѯ,��:" + con + "��ѭ��,�޳����ɹ���ʧ�ܽ��,������:" + orderid + ",return:3,������!");
		return 3;
	}

	/**
	 * �����ַ�����
	 * 
	 * @param tradeserial
	 *            ��ֵ������
	 * @return int 0 �����ɹ�����-1����ʧ�� ����������
	 */
	public static int Correct_Confim(String tradeserial) {
		Log.info("�ֳ��ƶ�,,����������,,����������(tradeserial):" + tradeserial);
		String phone = "";
		String fee = "";
		String userno = "";
		DBService dbService = null;
		try {
			dbService = new DBService();
			String[] s = dbService
					.getStringArray("select tradeobject,fee,userno from wht_orderform_"
							+ Tools.getNow3().substring(2, 6)
							+ " where tradeserial='" + tradeserial + "'");
			if (s == null || "".equals(s) || s.length <= 0) {
				Log.info("�ֳ��ƶ�,,����������,,����������(tradeserial):" + tradeserial
						+ ",����ʧ��,û�ж�Ӧ��������Ϣ,δ�������γ�������");
				return -1;
			}
			phone = s[0];
			fee = Float.parseFloat(s[1]) / 1000 + "";
			userno = s[2];
		} catch (Exception ex) {
			Log.info("�ֳ��ƶ�,,����������,,����������(tradeserial):" + tradeserial
					+ ",����ʧ��,ϵͳ�쳣,δ�������γ�������,ex:" + ex);
			return -1;
		} finally {
			if (dbService != null) {
				dbService.close();
				dbService = null;
			}
		}
		//�������󷽷�
		int rsCode = Correct(tradeserial, phone, fee);
		//����ʧ�ܻ��쳣
		if (rsCode != 0) {
			return rsCode;
		} else {
			// �˷�ҵ��
			Log.info("�ֳ��ƶ�,,�����ɹ�,,����������(tradeserial):" + tradeserial+ ",phone:" + phone + ",fee:" + fee + ",userno:" + userno+ ",��ʼ�˷�ҵ��");
			String date = Tools.getNow3().substring(2, 6);
			String orderTable = "wht_orderform_" + date;
			String acctTable = "wht_acctbill_" + date;
			// ��֧�ֿ�, ʧ�����������˿��¼
			// �޸Ķ���״̬
			DBService db = null;
			try {
				db = new DBService();
				db.setAutoCommit(false);
					db.update("update " + orderTable + " set state=5 where tradeserial='" + tradeserial+ "'");
					String newtime = Tools.getNow3();
	
					String sql0 = "select childfacct,infacctfee,tradeaccount from "
							+ acctTable + " where tradetype=4 and tradeserial='"
							+ tradeserial + "'";
					String[] str = db.getStringArray(sql0);
					if (null == str) {
						return 3;// �쳣
					}
					String fialAcct1 = Tools.getAccountSerial(Tools.getNow3(), "TY"
							+ str[2].substring(1, 11));
					String ss = "select accountleft from wht_childfacct where childfacct='"
							+ str[0] + "'";
					long userleft = db.getLong(ss);
					if (userleft == -1) {
						return 3;// �쳣
					}
					Object[] acctObj1 = { null, str[0], fialAcct1, str[2], newtime,
							Integer.parseInt(str[1]), Integer.parseInt(str[1]), 0,
							"�ƶ�����(���ѳ���)", 0, newtime,
							userleft + Integer.parseInt(str[1]), tradeserial,
							str[0], 2 };
					db.logData(15, acctObj1, acctTable);
					db.update("update wht_childfacct set accountleft=accountleft+"
							+ Integer.parseInt(str[1]) + " where childfacct='"
							+ str[0] + "'");
	
					String sql4 = "select childfacct,infacctfee from " + acctTable
							+ " where tradetype=15 and tradeserial='" + tradeserial
							+ "'";
					String[] str1 = db.getStringArray(sql4);
					if (null != str1) {
						String fialAcct2 = Tools.getAccountSerial(newtime, "TY"
								+ str[2].substring(0, 10));
						String bb = "select accountleft from wht_childfacct where childfacct='"
								+ str1[0] + "'";
						long parentleft = db.getLong(bb);
						Object[] acctObjP = { null, str1[0], fialAcct2, str[2],
								newtime, Integer.parseInt(str1[1]),
								Integer.parseInt(str1[1]), 0, "�ƶ�����(���׳���)", 0,
								newtime, parentleft - Integer.parseInt(str1[1]),
								tradeserial, str[0], 1 };
						db.logData(15, acctObjP, acctTable);
						db.update("update wht_childfacct set accountleft=accountleft-"
										+ Integer.parseInt(str1[1])
										+ " where childfacct='" + str1[0] + "'");
					}
					db.update("insert into wlt_revertlimit values(null,'" + userno
							+ "','" + Tools.getNow3().substring(0, 6) + "')");
					//��֧�ֿ�, ʧ�����������˿��¼ ����
				db.commit();
				Log.info("�ֳ��ƶ�,,�����ɹ�,,����������(tradeserial):" + tradeserial+ ",phone:" + phone + ",fee:" + fee + ",userno:" + userno+ ",�˷ѳɹ�");
				return 0;//�ɹ�
			} catch (Exception ex) {
				db.rollback();
				Log.info("�ֳ��ƶ�,,�����ɹ�,,����������(tradeserial):" + tradeserial+ ",phone:" + phone + ",fee:" + fee + ",userno:" + userno+ ",�˷��쳣,ex:"+ex);
				return 3;//�쳣
			} finally {
				if (null != db) {
					db.close();
					db=null;
				}
			}
		}
	}
}
