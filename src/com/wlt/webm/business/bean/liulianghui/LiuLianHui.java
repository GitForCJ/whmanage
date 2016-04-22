package com.wlt.webm.business.bean.liulianghui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import com.commsoft.epay.util.logging.Log;
import com.wlt.webm.scputil.MD5Util;

/**
 * @author Lijin
 *˵�����ڷ�����ʽƽ̨��ʱ�������Ӧ�ĵ�ַ�С�.cn���ĳɡ�.com��,�Լ��û��˺���ϢҲ���Ϊ��ʽ��Ϣ
 */
public class LiuLianHui {
	/**
	 * ����ʱ����Ϊ��ʽkey
	 */
	private static String APP_KEY = "9c9fe4ce062ef84e028733b4c886091c";//����ʹ��
	/**
	 * ����ʱ����Ϊ��ʽuser
	 */
	private static String API_USER = "1178623819";//����ʹ��
	public static void main(String[] args) {
		String danhao = "zb0z20356721809";
		String str = null;
		int a  = LiuLianHui.liuLiangDingGou("13811497810", "1", "30",danhao);					
		System.out.println("��ֵ״̬��" + a);
		if (a == 0) {
			System.out.println("�ύ�����ɹ�/��ֵ�ɹ�");
			System.out.println("�����ţ�" + danhao);
			int bv = liuLiangDingDanFind(danhao);
			System.out.println("ִ������������ѯ���:" + bv);
		}
		str = liuLiangBaoJia();
		System.out.println(str);
		if (str != null) {
			if (str.equals("-1"))
				return;
			else if (str.equals("2"))
				return;
			JSONObject obj2 = JSONObject.fromObject(str);
			try {
				JSONArray array = new JSONArray(obj2.getString("data"));
				for (int i = 0; i < array.length(); i++) {
					org.json.JSONObject obj = array.getJSONObject(i);
					System.out.println("id=" + obj.getInt("id") + "||name="
							+ obj.getString("name") + "||��������С="
							+ obj.getString("amount") + "M||�۸�"
							+ obj.getString("price"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		 str=liuLiangBaoFindByMobile("13811497810");
		 System.out.println(str);
		 str=yuEFind();
		 System.out.println(str);
	}

	/**
	 * �ʻ�����ѯ
	 * 
	 * @return String
	 */
	public static String yuEFind() {
		String tr = "app_id=" + API_USER + "_" + APP_KEY;
		String md5 = MD5Util.MD5Encode(tr, "utf-8").toLowerCase();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // ���ò���
		formparams.add(new BasicNameValuePair("app_id", API_USER));
		formparams.add(new BasicNameValuePair("sign", md5));
		String str = post(formparams,
				"http://api.17llh.cn/package/balance_query");
		Log.info(str);
		/*if (str != null) {
			if (str.equals("-1"))
				return "-1";
			else if (str.equals("2"))
				return "2";
			JSONObject obj = JSONObject.fromObject(str);
			System.out.println(obj.getString("msg"));
		}*/
		return str;
	}

	/**
	 * �����ֻ������ѯ�ײͰ�
	 * 
	 * @param mobile
	 *            /�ֻ�����
	 * @return String
	 */
	public static String liuLiangBaoFindByMobile(String mobile) {
		String tr = "app_id=" + API_USER + "_mobile=" + mobile + "_" + APP_KEY;
		String md5 = MD5Util.MD5Encode(tr, "utf-8").toLowerCase();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // ���ò���
		formparams.add(new BasicNameValuePair("app_id", API_USER));
		formparams.add(new BasicNameValuePair("mobile", mobile));
		formparams.add(new BasicNameValuePair("sign", md5));
		String str = post(formparams,
				"http://api.17llh.cn/package/mobile_query");
		Log.info(str);
		/*if (str != null) {
			if (str.equals("-1"))
				return "-1";
			else if (str.equals("2"))
				return "2";
			JSONObject obj = JSONObject.fromObject(str);
			System.out.println(obj.getString("msg"));
		}*/
		return str;
	}

	/**
	 * �������۲�ѯ
	 * 
	 * @return String
	 */
	public static String liuLiangBaoJia() {
		String tr = "app_id=" + API_USER + "_" + APP_KEY;
		String md5 = MD5Util.MD5Encode(tr, "utf-8").toLowerCase();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // ���ò���
		formparams.add(new BasicNameValuePair("app_id", API_USER));
		formparams.add(new BasicNameValuePair("sign", md5));
		String str = post(formparams, "http://api.17llh.cn/package/quotation");
		Log.info("���۲�ѯ��"+str);
		// if (str != null) {
		// if (str.equals("-1"))
		// return "-1";
		// else if(str.equals("2"))
		// return "2";
		// JSONObject obj = JSONObject.fromObject(str);
		// System.out.println(obj.getString("msg"));
		// }
		return str;
	}

	/**
	 * ��ѯ�� ���� 0 �ɹ� -1ʧ�� 2�����У��쳣 ������ѯ
	 * 
	 * @param statement
	 *            /������
	 * @return String
	 */
	public static int liuLiangDingDanFind(String statement) {
		String tr = "app_id=" + API_USER + "_statement=" + statement + "_"
				+ APP_KEY;
		String md5 = MD5Util.MD5Encode(tr, "utf-8").toLowerCase();
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // ���ò���
		formparams.add(new BasicNameValuePair("app_id", API_USER));
		formparams.add(new BasicNameValuePair("statement", statement));
		formparams.add(new BasicNameValuePair("sign", md5));
		String str = post(formparams, "http://api.17llh.cn/package/order_query");
		Log.info("������ѯ��"+str);
		if (str != null)
		{  
			if (str.equals("-1"))
			return -1;
		else if (str.equals("2"))
			return 2;
		JSONObject obj = JSONObject.fromObject(str);
		 if (obj.getString("code").equals("200"))//����������100018
			{
				obj = JSONObject.fromObject(obj.getString("data"));
//				Log.info("��ֵ�ײ�����" + obj.getString("package_name")
//						+ "   ��ֵ״̬��" + obj.getString("status"));
				if (obj.getString("status").equals("1"))
					return 0;
				else if (obj.getString("status").equals("0"))
					return 2;
				else if (obj.getString("status").equals("-1"))
					return -1;
			}else if(obj.getString("code").equals("100018")){
				return -1;
			}else
				return 2;
		}
		return 2;
	}

	/**
	 * ��ֵ
	 * 
	 * @param mobile
	 *            �ֻ�����
	 * @param pingpai
	 *            Ʒ���� ��0�� ��ʾ����  ��1����ʾ�ƶ�   ��2�� ��ʾ��ͨ
	 * @param streamsize
	 *            ��������С
	 * @param statement
	 *            ���� �Զ��嶩����С�ڵ���16λ
	 * @return int 0 �ɹ� -1ʧ�� 2 ������ ��������
	 */
	public static int liuLiangDingGou(String mobile, String pingpai,
			String streamsize, String statement) {
		int pid = getPid(Integer.parseInt(pingpai),
				Integer.parseInt(streamsize));
		if (pid == 0)// û��ƥ���idʱ
			return -1;
		String tr = "app_id=" + API_USER + "_mobile=" + mobile + "_pid=" + pid
				+ "_statement=" + statement + "_" + APP_KEY;
		String md5 = MD5Util.MD5Encode(tr, "utf-8").toLowerCase();
		Log.info(tr);
		List<NameValuePair> formparams = new ArrayList<NameValuePair>(); // ���ò���
		formparams.add(new BasicNameValuePair("app_id", API_USER));
		formparams.add(new BasicNameValuePair("mobile", mobile));
		formparams.add(new BasicNameValuePair("pid", "" + pid));
		formparams.add(new BasicNameValuePair("statement", statement));
		formparams.add(new BasicNameValuePair("sign", md5));
		String str = post(formparams, "http://api.17llh.cn/package/dinggou");
		Log.info("�ύ�Ķ���״̬��" + str);
		if (str != null) {
			if (str.equals("-1"))
				return -1;
			else if (str.equals("2"))
				return 2;
			JSONObject obj = JSONObject.fromObject(str);
			if (obj.getInt("code") == 100015) {
				obj = JSONObject.fromObject(obj.getString("data"));
				if (obj.getString("status").equals("1"))
					return 0;
			}
		}
		return -1;
	}

	/**
	 * 
	 * @param params
	 * @param url
	 * @return String
	 */
	public static String post(List<NameValuePair> params, String url) {
		CloseableHttpClient client=HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		UrlEncodedFormEntity uefEntity = null;
		CloseableHttpResponse res = null;
		try {
			uefEntity = new UrlEncodedFormEntity(params, "UTF-8");
			post.setEntity(uefEntity);
			Log.info("��������..." + url);
			res = client.execute(post);
			Log.info("���ӳɹ�");
			HttpEntity entity = res.getEntity();
			int statusCode = res.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK)
				if (entity != null) {
					InputStream instreams = null;
					String str = null;
					instreams = entity.getContent();
					str = convertStreamToString(instreams);
					//Object obj=JSONValue.parse(str);//δ�ύjson_simple.jar
					//return obj.toString();
					str = new String(str.getBytes(), "UTF-8");
					return str;
				}
		} catch (ClientProtocolException e) {
			Log.error(e);
			return "2";
		} catch (IOException e) {
			Log.error(e);
			return "2";
		} finally {
			try{
			post.abort();
			res.close();
			client.close();
			}catch(IOException e){
				Log.error("�ر������������쳣"+e.toString());
			}
		}
		return null;
	}

	/**
	 * ��inputstream�л�ȡ�ַ���
	 * 
	 * @param is
	 * @return String
	 */
	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * ����Ʒ���̺���������С�������еĶ�Ӧid
	 * 
	 * @param pingpai Ʒ����
	 *            ��0�� ��ʾ���� ��1����ʾ�ƶ� ��2�� ��ʾ��ͨ
	 * @param streamsize
	 *            ��������С
	 * @return int
	 */
	public static int getPid(int pingpai, int streamsize) {
		int temp = 0;
		if (pingpai == 1)
			switch (streamsize) {
			case 10:
				temp = 1;
				break;
			case 30:
				temp = 2;
				break;
			case 70:
				temp = 3;
				break;
			case 150:
				temp = 4;
				break;
			case 500:
				temp = 5;
				break;
			case 1024:
				temp = 6;
				break;
			case 2048:
				temp = 7;
				break;
			case 3072:
				temp = 8;
				break;
			case 4096:
				temp = 9;
				break;
			case 6144:
				temp = 10;
				break;
			case 11264:
				temp = 11;
				break;
			}
		else if (pingpai == 0)
			switch (streamsize) {
			case 5:
				temp = 14;
				break;
			case 10:
				temp = 15;
				break;
			case 30:
				temp = 16;
				break;
			case 50:
				temp = 17;
				break;
			case 100:
				temp = 18;
				break;
			case 200:
				temp = 19;
				break;
			case 500:
				temp = 20;
				break;
			case 1024:
				temp = 21;
				break;
			}
		else if (pingpai == 2)
			switch (streamsize) {
			case 20:
				temp = 26;
				break;
			}
		return temp;
	}

}
