package chinapay.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import chinapay.PrivateKey;
import chinapay.SecureLink;
import chinapay.model.bean.TransactionBean;
import chinapay.util.Config;

/**
 * @authorcaiSJ
 * 
 */

public class WhiteListReturnServlet extends HttpServlet {

	private static final String Payment = "/WhiteListInput.jsp";
	private static final String PaymentUrl = "chinapay.WhiteList.url";
	private static final String KEY_CHINAPAY_MERKEY_FILEPATH = "chinapay.merkey.filepath";
	private static final String KEY_CHINAPAY_PUBKEY_FILEPATH = "chinapay.pubkey.filepath";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		List errorList = new ArrayList();
		String MerKeyPath = null;
		String PubKeyPath = null;
		String pay_url = null;
		try {
			Properties config = Config.getInstance().getProperties();
			MerKeyPath = config.getProperty(KEY_CHINAPAY_MERKEY_FILEPATH);
			PubKeyPath = config.getProperty(KEY_CHINAPAY_PUBKEY_FILEPATH);// chinapay.merkey.filepath
			pay_url = config.getProperty(PaymentUrl); // chinapay.payment.url
		} catch (Exception e) {
			errorList.add("私钥初始化失败！");
		}

		System.out.println(MerKeyPath);
		System.out.println(PubKeyPath);

		if (!errorList.isEmpty()) {
			request.setAttribute("errors", errorList);
			request.getRequestDispatcher(Payment).forward(request, response);
			return;
		}

		String merId = request.getParameter("merId"); // 15
		String certType = request.getParameter("certType");
		String cardNo = request.getParameter("cardNo");
		String certId = request.getParameter("certId");
		String usrName = request.getParameter("usrName"); // Test data!
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String chkValue = request.getParameter("chkValue");

		HttpClient httpClient = new HttpClient();
		System.out.println("HttpClient方法创建！");
		httpClient.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");
		String url = pay_url;
		System.out.println(url);
		PostMethod postMethod = new PostMethod(url);
		System.out.println("Post方法创建！");
		// 填入各个表单域的值
		NameValuePair[] data = { new NameValuePair("merId", merId),
				new NameValuePair("certType", certType),
				new NameValuePair("cardNo", cardNo),
				new NameValuePair("usrName", usrName),
				new NameValuePair("certId", certId),
				new NameValuePair("mobile", mobile),
				new NameValuePair("email", email),
				new NameValuePair("address", address),
				new NameValuePair("chkValue", chkValue) };

		System.out.println(data);

		// 将表单的值放入postMethod中
		postMethod.setRequestBody(data);
		// 执行postMethod
		int statusCode = 0;
		try {
			statusCode = httpClient.executeMethod(postMethod);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 读取内容
		InputStream resInputStream = null;
		try {
			resInputStream = postMethod.getResponseBodyAsStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理内容
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				resInputStream));
		String tempBf = null;
		StringBuffer html = new StringBuffer();
		while ((tempBf = reader.readLine()) != null) {

			html.append(tempBf);
		}
		String resMes = html.toString();
		System.out.println(resMes);
		int dex = resMes.lastIndexOf("=");

		String str[] = resMes.split("&");
		for (int i = 0; i < str.length; i++) {
			System.out.println(str[i]);
		}
		System.out.println(str.length);
		// 提取返回数据

		int Res_Code = str[0].indexOf("=");
		int Res_transStat = str[1].indexOf("=");
		int Res_message = str[2].indexOf("=");
		int Res_chkValue = str[3].indexOf("=");

		String responseCode = str[0].substring(Res_Code + 1);
		
		//transStat表示状态,只有"1001"表示成功，其他状态均表示失败
		String TransStat = str[1].substring(Res_transStat + 1);
		String Message = str[2].substring(Res_message + 1);
		String ChkValue = str[3].substring(Res_chkValue + 1);

		System.out.println("responseCode=" + responseCode);
		System.out.println("message=" + tozhCN(Message)); // 还需转换为中文字符
		System.out.println("transStat=" + TransStat);
		System.out.println("chkValue=" + ChkValue);

		String tiakong = resMes.substring(0, dex + 1);

		// 对收到的ChinaPay应答传回的域段进行验签
		boolean buildOK = false;
		boolean res = false;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
		try {
			buildOK = key.buildKey("999999999999999", KeyUsage, PubKeyPath);
			System.out.println("公钥创建成功！");
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (!buildOK) {
			System.out.println("build error!");
			errorList.add("build error!");
		}
		if (!errorList.isEmpty()) {
			request.setAttribute("errors", errorList);
			return;
		}

		SecureLink sl = new SecureLink(key);
		res = sl.verifyAuthToken(tiakong, resMes.substring(dex + 1));
		System.out.println(res);
		if (res) {
			System.out.println("验签数据正确!");
			//验证签名数据通过后，一定要判定交易状态代码是否为"1001"，实现代码请商户自行编写
			
			TransactionBean pay = new TransactionBean();
			pay.setResponseCode(responseCode);
			pay.setMessage(tozhCN(Message));
			pay.setTransStatus(TransStat);

			request.setAttribute("payInput", pay);
			request.getRequestDispatcher("./TransactionFail.jsp").forward(
					request, response);

		} else
			System.out.println("签名数据不匹配！");
		return;

	}

	public static String tozhCN(String unicode) {
		StringBuffer gbk = new StringBuffer();
		String hex[] = unicode.split("\\\\u");
		for (int i = 1; i < hex.length; i++) { // 注意要从 1 开始，而不是从0开始。第一个是空。
			int data = Integer.parseInt(hex[i], 16); // 将16进制数转换为 10进制的数据。
			gbk.append((char) data); // 强制转换为char类型就是我们的中文字符了。
		}
		System.out.println("这是从 Unicode编码 转换为 中文字符了: " + gbk.toString());
		return gbk.toString();
	}

	public static String toUnicode(String zhStr) {
		StringBuffer unicode = new StringBuffer();
		for (int i = 0; i < zhStr.length(); i++) {
			char c = zhStr.charAt(i);
			unicode.append("\\u" + Integer.toHexString(c));
		}
		System.out.println(unicode.toString());
		System.out.println(zhStr + "转换为unicode码成功！");
		return unicode.toString();
	}

}
