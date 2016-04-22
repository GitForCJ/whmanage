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

	// 充值请求url
	private static final String RECHARGE_URL = "http://121.201.96.49:8107/MainServiceBusiness/SendPhoneChargeInfo";
	// 充值查询url
	private static final String RECHARGE_QUERY_URL = "http://121.201.96.49:8107/MainServiceBusiness/GetOrderInfo";
	// 冲正请求
	private static final String CORRECT_URL = "http://121.201.96.49:8107/MainServiceBusiness/SendClientPhoneReverseInfo";
	// 冲正查询
	private static final String CORRECT_QUERY_URL = "http://121.201.96.49:8107/MainServiceBusiness/GetPhoneReverseInfo";

	private static final String chargenumbertype = "1"; // 手机号码为1 固话号码为2
	private static final String agentid = "10478";// 代理商id
	private static final String returntype = "2";// 1表示get返回// 2表示返回XML信息。目前只支持xml方式返回。
	private static final String ispname = "";// 固话时需要输入移动、联通、电信传汉字，用utf-8编码，进行MD5加密时不用编码，手机号可空。
	private static final String source = "2"; // 代理商请填写2，此处务必填写2，否则可能造成提交订单号重复。

	private static final String merchantKey = "fa79e92d213a46bd995970970d362d5a";// md5 // 加密key

	public static void main(String[] args) {
		// Recharge("1111","18824588427","1");
		// Query("1111");
		// Correct("1111","18824588427","1");
		CorrectQuery("150617142530563947800004");
	}

	/**
	 * 移动充值
	 * 
	 * @param orderid
	 * @param phone
	 * @param fee
	 * @return -1失败 0成功 其他处理中
	 */
	@SuppressWarnings("unchecked")
	public static int Recharge(String orderid, String phone, String fee) {
		if (orderid == null || "".equals(orderid) || phone == null
				|| "".equals(phone) || phone.trim().length() != 11
				|| fee == null || "".equals(fee)) {
			Log.info("乐充移动,,充值请求,,参数不足,,orderid:" + orderid + ",phone:" + phone
					+ ",fee:" + fee + ",return -1,失败处理");
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
		Log.info("乐充移动,,充值请求,,orderid:" + orderid + ",phone:" + phone + ",url:"
				+ buf.toString());

		HttpClient client = null;
		GetMethod get = null;
		try {
			client = new HttpClient();
			get = new GetMethod(buf.toString());
			// 链接时间 30秒，单位是毫秒
			client.getHttpConnectionManager().getParams().setConnectionTimeout(
					60 * 1000);
			// 读取时间 10秒，单位是毫秒
			client.getHttpConnectionManager().getParams().setSoTimeout(
					60 * 1000);
			int status = client.executeMethod(get);
			Log.info("乐充移动,,充值请求,,http响应状态,,orderid:" + orderid + ",phone:"
					+ phone + ",status:" + status);
			if (status == 200) {
				String result = get.getResponseBodyAsString();
				Log.info("乐充移动,,充值请求,,响应xml数据,,orderid:" + orderid + ",phone:"
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
							Log.info("乐充移动,,充值请求,,订单状态,,orderid:" + orderid
									+ ",phone:" + phone + ",return:0,充值成功!");
							// 充值成功,加密验证，，，，
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
							// 确定充值失败
							Log.info("乐充移动,,充值请求,,订单状态,,orderid:" + orderid
									+ ",phone:" + phone + ",return:-1,充值失败!");
							return -1;
						}
					}
				}
			} else {
				// 提交失败,系统异常
				Log.info("乐充移动,,充值请求,,orderid:" + orderid + ",phone:" + phone
						+ ",return:-1,http提交失败!");
				return -1;
			}
		} catch (Exception e) {
			Log.error("乐充移动,,充值请求,,系统异常,,orderid:" + orderid + ",phone:"
					+ phone + ",ex:" + e);
		} finally {
			get.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager())
					.shutdown();
			if (null != client) {
				client = null;
			}
		}
		Log.info("乐充移动,,充值请求,,订单状态未知,调用查询接口,,orderid:" + orderid + ",phone:"
				+ phone);
		// 调用查询接口
		int result_code = Query(orderid);
		Log.info("乐充移动,,充值请求,调用查询接口方法,,orderid:" + orderid + ",phone:" + phone
				+ ",,返回值(0成功,-1失败,其他处理中):" + result_code);
		return result_code;
	}

	/**
	 * 订单查询
	 * 
	 * @param orderid
	 * @return -1失败 0成功 其他处理中 (订单状态)
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

		Log.info("乐充移动,充值查询,开始循环查询,订单号:" + orderid + ",请求url:" + buf.toString());

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
				Log.info("乐充移动,充值查询,第:" + con + "次循环,订单号:" + orderid
						+ ",http响应状态status:" + status);
				if (status == 200) {
					String result = get.getResponseBodyAsString();
					Log.info("乐充移动,充值查询,第:" + con + "次循环,订单号:" + orderid
							+ ",响应xml,result:" + result);
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
								Log.info("乐充移动,充值查询,第:" + con + "次循环,订单号:"
										+ orderid + ",return:0,充值成功!");
								// 充值成功,加密验证，，，，
								return 0;
							} else if ("0015".equals(rsCode)) {
								// 确定充值失败
								Log.info("乐充移动,充值查询,第:" + con + "次循环,订单号:"
										+ orderid + ",return:-1,充值失败!");
								return -1;
							}
						}
					}
				}
			} catch (Exception e) {
				Log.error("乐充移动,充值查询,第:" + con + "次循环,订单号:" + orderid
						+ ",系统异常,ex:" + e);
			} finally {
				get.releaseConnection();
				((SimpleHttpConnectionManager) client
						.getHttpConnectionManager()).shutdown();
				if (null != client) {
					client = null;
				}
			}
		}
		Log.info("乐充移动,充值查询,第:" + con + "次循环,结束查询循环,无明确订单结果,订单号:" + orderid
				+ ",return 3,处理中!");
		return 3;
	}

	/**
	 * 移动冲正
	 * 
	 * @param orderid
	 * @param phone
	 * @param fee
	 * @return -1失败 0成功 其他处理中
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
		Log.info("乐充移动,,冲正请求,,orderid:" + orderid + ",phone:" + phone + ",url:" + buf.toString());

		HttpClient client = null;
		GetMethod get = null;
		try {
			client = new HttpClient();
			get = new GetMethod(buf.toString());
			// 链接时间 30秒，单位是毫秒
			client.getHttpConnectionManager().getParams().setConnectionTimeout(
					60 * 1000);
			// 读取时间 10秒，单位是毫秒
			client.getHttpConnectionManager().getParams().setSoTimeout(
					60 * 1000);
			int status = client.executeMethod(get);
			Log.info("乐充移动,,冲正请求,,orderid:" + orderid + ",phone:" + phone
					+ ",http响应状态status:" + status);
			if (status == 200) {
				String result = get.getResponseBodyAsString();
				Log.info("乐充移动,,冲正请求,,响应xml数据,,orderid:" + orderid + ",phone:"
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
							Log.info("乐充移动,,冲正请求,,orderid:" + orderid
									+ ",phone:" + phone + ",return:0,冲正成功!");
							// 冲正成功,加密验证，，，，
							return 0;
						} else if ("0025".equals(rsCode)
								|| "0119".equals(rsCode)
								|| "0111".equals(rsCode)) {
							// 确定冲正失败
							Log.info("乐充移动,,冲正请求,,orderid:" + orderid + ",phone:" + phone + ",return:-1,冲正失败!");
							return -1;
						}else{
							Log.info("乐充移动,,冲正请求,,冲正处理中,调用冲正结果查询,,orderid:"+ orderid + ",phone:" + phone);
							int result_code = CorrectQuery(orderid);
							Log.info("乐充移动,,冲正请求,,冲正结果查询返回,,orderid:" + orderid + ",phone:" + phone + ",return:" + result_code);
							return result_code;
						}
					}
				}
			}
		} catch (Exception e) {
			Log.error("乐充移动,,冲正请求,,orderid:" + orderid + ",phone:" + phone + ",系统异常ex:" + e);
		} finally {
			get.releaseConnection();
			((SimpleHttpConnectionManager) client.getHttpConnectionManager())
					.shutdown();
			if (null != client) {
				client = null;
			}
		}
		Log.info("乐充移动,,冲正请求,,无明确冲正结果,,orderid:" + orderid + ",phone:" + phone +",,return 3,处理中状态");
		// 冲正处理中
		return 3;
	}

	/**
	 * 冲正结果查询
	 * 
	 * @param orderid
	 * @return -1失败 0成功 其他处理中
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

		Log.info("乐充移动,,冲正结果查询,,开始循环查询冲正结果状态,,orderid:" + orderid + ",url:" + buf.toString());
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
				Log.info("乐充移动,冲正结果查询,第:" + con + "次循环,订单号:" + orderid
						+ ",http响应状态status:" + status);
				if (status == 200) {
					String result = get.getResponseBodyAsString();
					Log.info("乐充移动,冲正结果查询,第:" + con + "次循环,订单号:" + orderid
							+ ",响应xml,result:" + result);
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
								Log.info("乐充移动,冲正结果查询,第:" + con + "次循环,订单号:" + orderid + ",return:0,冲正成功!");
								// 充值成功,加密验证，，，，
								return 0;
							} else if ("0025".equals(rsCode)
									|| "0119".equals(rsCode)
									|| "0111".equals(rsCode)) {
								// 确定充值失败
								Log.info("乐充移动,冲正结果查询,第:" + con + "次循环,订单号:" + orderid + ",return:-1,冲正失败!");
								return -1;
							}
						}
					}
				}
			} catch (Exception e) {
				Log.error("乐充移动,冲正结果查询,第:" + con + "次循环,订单号:" + orderid + ",系统异常,ex:" + e);
			} finally {
				get.releaseConnection();
				((SimpleHttpConnectionManager) client
						.getHttpConnectionManager()).shutdown();
				if (null != client) {
					client = null;
				}
			}
		}
		Log.info("乐充移动,冲正结果查询,第:" + con + "次循环,无冲正成功或失败结果,订单号:" + orderid + ",return:3,处理中!");
		return 3;
	}

	/**
	 * 冲正分发处理
	 * 
	 * @param tradeserial
	 *            充值订单号
	 * @return int 0 冲正成功，，-1冲正失败 其他处理中
	 */
	public static int Correct_Confim(String tradeserial) {
		Log.info("乐充移动,,冲正请求处理,,冲正订单号(tradeserial):" + tradeserial);
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
				Log.info("乐充移动,,冲正请求处理,,冲正订单号(tradeserial):" + tradeserial
						+ ",冲正失败,没有对应订单的信息,未发起上游冲正请求");
				return -1;
			}
			phone = s[0];
			fee = Float.parseFloat(s[1]) / 1000 + "";
			userno = s[2];
		} catch (Exception ex) {
			Log.info("乐充移动,,冲正请求处理,,冲正订单号(tradeserial):" + tradeserial
					+ ",冲正失败,系统异常,未发起上游冲正请求,ex:" + ex);
			return -1;
		} finally {
			if (dbService != null) {
				dbService.close();
				dbService = null;
			}
		}
		//冲正请求方法
		int rsCode = Correct(tradeserial, phone, fee);
		//冲正失败或异常
		if (rsCode != 0) {
			return rsCode;
		} else {
			// 退费业务
			Log.info("乐充移动,,冲正成功,,冲正订单号(tradeserial):" + tradeserial+ ",phone:" + phone + ",fee:" + fee + ",userno:" + userno+ ",开始退费业务");
			String date = Tools.getNow3().substring(2, 6);
			String orderTable = "wht_orderform_" + date;
			String acctTable = "wht_acctbill_" + date;
			// 收支分开, 失败重新生成退款记录
			// 修改订单状态
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
						return 3;// 异常
					}
					String fialAcct1 = Tools.getAccountSerial(Tools.getNow3(), "TY"
							+ str[2].substring(1, 11));
					String ss = "select accountleft from wht_childfacct where childfacct='"
							+ str[0] + "'";
					long userleft = db.getLong(ss);
					if (userleft == -1) {
						return 3;// 异常
					}
					Object[] acctObj1 = { null, str[0], fialAcct1, str[2], newtime,
							Integer.parseInt(str[1]), Integer.parseInt(str[1]), 0,
							"移动话费(话费冲正)", 0, newtime,
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
								Integer.parseInt(str1[1]), 0, "移动话费(交易撤销)", 0,
								newtime, parentleft - Integer.parseInt(str1[1]),
								tradeserial, str[0], 1 };
						db.logData(15, acctObjP, acctTable);
						db.update("update wht_childfacct set accountleft=accountleft-"
										+ Integer.parseInt(str1[1])
										+ " where childfacct='" + str1[0] + "'");
					}
					db.update("insert into wlt_revertlimit values(null,'" + userno
							+ "','" + Tools.getNow3().substring(0, 6) + "')");
					//收支分开, 失败重新生成退款记录 结束
				db.commit();
				Log.info("乐充移动,,冲正成功,,冲正订单号(tradeserial):" + tradeserial+ ",phone:" + phone + ",fee:" + fee + ",userno:" + userno+ ",退费成功");
				return 0;//成功
			} catch (Exception ex) {
				db.rollback();
				Log.info("乐充移动,,冲正成功,,冲正订单号(tradeserial):" + tradeserial+ ",phone:" + phone + ",fee:" + fee + ",userno:" + userno+ ",退费异常,ex:"+ex);
				return 3;//异常
			} finally {
				if (null != db) {
					db.close();
					db=null;
				}
			}
		}
	}
}
