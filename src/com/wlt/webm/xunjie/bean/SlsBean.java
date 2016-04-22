package com.wlt.webm.xunjie.bean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.wlt.webm.util.MD5;

/**
 * 杭州手拉手
 * 
 * @author lenovo
 * 
 */
public class SlsBean {

	/**
	 * 手拉手充值
	 * @param phone   11位手机号
	 * @param orderID 26位  订单号
	 * @param fee  元
	 * @return  0表示成功  1表示失败 2、内部异常联系客服 3、接口存疑联系客服 4、正在处理联系客服
	 */
	public int slsFill(String phone, String orderID, String fee) {
		int n = 0;
		try {
		String url = URLEncoder.encode(XunJConstant.SLSRETURNURL, "UTF-8");
		String sign = MD5.encode(XunJConstant.SLSKEY + XunJConstant.SLSID
				+ phone + fee + orderID + "0" + XunJConstant.SLSPWD
				+ XunJConstant.SLSKEY);

		String urlto = XunJConstant.SLSFILLURL + "UserID=" + XunJConstant.SLSID
				+ "&Phone=" + phone + "&Money=" + fee + "&OrderNo=" + orderID
				+ "&NoticeUrl=" + url + "&IsLocalPay=" + "0" + "&PayPwd="
				+ XunJConstant.SLSPWD + "&Sign=" + sign;
		System.out.println(" sls订单提交请求:"+urlto);
		String rs="";
			rs = getFillMsg(urlto);
			if (rs.length()<2) {
				String query = slsQueryUrl(orderID);
				n = slsQueryRs(query);
			} else {
				String result = rs.substring(rs.indexOf("<Result>")
						+ "<Result>".length(), rs.indexOf("</Result>"));
				if (result.equals("1")) {
					String query = slsQueryUrl(orderID);
						n = slsQueryRs(query);
				} else {
					n= 1;//充值失败
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("sls exception:"+e1.toString());
			n=2;
		}

		return n;
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String getUrlMsg(String url) throws Exception {
		StringBuffer sf = new StringBuffer();
		URL getUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String lines;
			sf.delete(0, sf.length());
			while ((lines = reader.readLine()) != null) {
				sf.append(lines);
			}
				reader.close();
				connection.disconnect();
		System.out.println("手拉手返回:" + sf.toString());
		return sf.toString();
	}
	
	/**
	 * 手拉手查询接口组装
	 * @param orderNo
	 * @return  url
	 * @throws Exception
	 */
	public String   slsQueryUrl(String orderNo ){
		String sign=MD5.encode(XunJConstant.SLSKEY+XunJConstant.SLSID+orderNo+XunJConstant.SLSKEY);
		String usrl=XunJConstant.SLSQUERYURL+"UserID="+XunJConstant.SLSID
		+"&OrderNo="+orderNo+"&Sign="+sign;
		System.out.println("查询请求地址:"+usrl);
		return usrl;
	}

	/**
	 * 手拉手查询结果
	 * @param usrl
	 * @return   0表示成功  1表示失败 3、存疑 4、超时
	 * @throws Exception
	 */
	public int slsQueryRs(String usrl) throws Exception{
		int k=0;
		int n=4;
		while(true){
		k++;
		Thread.sleep(5000);
		if(k>23){
			n=4;
			break;
		}
		String rs=getUrlMsg(usrl);
		System.out.println("手拉手查询结果："+ResponseCodeSet.responseCode.get(rs.trim()));
		if(rs.equals("601")){
			n= 0;
			break;
		}else if(rs.equals("006")||rs.equals("101")){
			n= 3;
			break;
		}else if(rs.equals("610")||rs.equals("603")){
			continue;
		}else{
			n= 1;
			break;
		}
		}
		return n;
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String getFillMsg(String url) throws Exception {
		StringBuffer sf = new StringBuffer();
		URL getUrl = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		connection.setRequestMethod("GET");
		connection.connect();
		int i = 0;
			Thread.sleep(1000);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String lines;
			sf.delete(0, sf.length());
			while ((lines = reader.readLine()) != null) {
				sf.append(lines);
			}
				reader.close();
				connection.disconnect();
		System.out.println("手拉手提交订单返回:" + sf.toString());
		return sf.toString();
	}
	
	public static void main(String[] args) throws Exception {
		SlsBean sls=new SlsBean();
		sls.slsFill("18926512055", "111111111111111111122","30");
		sls.slsQueryRs(sls.slsQueryUrl("111111111111111111122"));
	}
}
