package chinapay.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chinapay.Base64;
import chinapay.PrivateKey;
import chinapay.SecureLink;
import chinapay.model.bean.TransactionBean;
import chinapay.util.Config;

/**
 * @author caiSJ
 * 
 */

public class QuerySendServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String KEY_CHINAPAY_MERID = "chinapay.merid";
	private static final String KEY_CHINAPAY_MERKEY_FILEPATH = "chinapay.merkey.filepath";
	private static final String QUERY_INPUT_JSP = "/bank/queryInput.jsp";
	private static final String QUERY_JSP = "/bank/queryCommit.jsp";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		String MerKeyPath = null;
		String merId = null;
		List errorList = new ArrayList();
		try {
			Properties config = Config.getInstance().getProperties();
			MerKeyPath = config.getProperty(KEY_CHINAPAY_MERKEY_FILEPATH);
			merId = config.getProperty(KEY_CHINAPAY_MERID);
		} catch (Exception e) {
			errorList.add("errors_properties_init_failed");
		}
		if (!errorList.isEmpty()) {
			request.setAttribute("errors", errorList);
			request.getRequestDispatcher(QUERY_INPUT_JSP).forward(request,
					response);
			return;
		}

		// 查询订单数据准备
		// String merId = request.getParameter("MerId");
		String version = request.getParameter("Version");
		String orderNo = request.getParameter("OrdId");
		String transDate = request.getParameter("TransDate");// 8
		String transType = request.getParameter("TransType");// 4
		String priv1 = toUnicode(request.getParameter("Priv1"));
		/*
		 * try { Priv1 = Base64Util.base64Decoder(Priv1); } catch (Exception e1)
		 * { // TODO Auto-generated catch block e1.printStackTrace(); }
		 */
		String chkValue = null;

		boolean buildOK = false;
		int KeyUsage = 0;
		PrivateKey key = new PrivateKey();
		try {
			buildOK = key.buildKey(merId, KeyUsage, MerKeyPath);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		if (!buildOK) {
			System.out.println("build error!");
			errorList.add("build error!");
		}
		if (!errorList.isEmpty()) {
			request.setAttribute("errors", errorList);
			request.getRequestDispatcher(QUERY_INPUT_JSP).forward(request,
					response);
			return;
		}
		try {

			String Data = merId + transType + orderNo + transDate + version;
			System.out.println("字符串数据拼装：" + Data);

			String plainData = new String(Base64.encode(Data.getBytes()));
			System.out.println("转换成Base64后数据：" + plainData);

			SecureLink sl = new SecureLink(key);
			chkValue = sl.Sign(plainData);
			System.out.println("签名内容：" + chkValue);
			System.out.println("签名成功！");

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("签名失败！");
		}

		TransactionBean query = new TransactionBean();
		query.setMerId(merId);
		query.setOrdNo(orderNo);
		query.setTransDate(transDate);
		query.setTransType(transType);
		query.setVersion(version);
		query.setPriv1(priv1);
		query.setChkValue(chkValue);

		System.out.println(query.toString());

		request.setAttribute("queryInput", query);
		request.getRequestDispatcher(QUERY_JSP).forward(request, response);
		return;

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
