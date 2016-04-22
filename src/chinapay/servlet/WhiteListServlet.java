package chinapay.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chinapay.model.bean.TransactionBean;
import chinapay.util.Config;
import chinapay.PrivateKey;
import chinapay.SecureLink;

/**
 * @author caiSJ
 * 
 */

public class WhiteListServlet extends HttpServlet {

	private static final long serialVersionUID = -8900442198891786032L;

	private static final String KEY_CHINAPAY_MERID = "chinapay.merid";
	private static final String PayCommit = "/WhiteListCommit.jsp";
	private static final String Payment = "/WhiteListInput.jsp";
	private static final String KEY_CHINAPAY_MERKEY_FILEPATH = "chinapay.merkey.filepath";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException { 

		request.setCharacterEncoding("GBK");
		List errorList = new ArrayList();
		String MerKeyPath = null;
		String merId = null;
		try {
			Properties config = Config.getInstance().getProperties();
			MerKeyPath = config.getProperty(KEY_CHINAPAY_MERKEY_FILEPATH); // chinapay.merkey.filepath
			merId = config.getProperty(KEY_CHINAPAY_MERID);
		} catch (Exception e) {
			errorList.add("私钥初始化失败！");
		}

		System.out.println(MerKeyPath);

		if (!errorList.isEmpty()) {
			request.setAttribute("errors", errorList);
			request.getRequestDispatcher(Payment).forward(request, response);
			return;
		}

		// String merId = request.getParameter("MerId"); //15
		String cardNo = request.getParameter("CardNo");
		String certType = request.getParameter("CertType");
		String certId = request.getParameter("CertId");
		String mobile = request.getParameter("Mobile");
		String email = request.getParameter("Email");

		String usrName = toUnicode(request.getParameter("UserName"));
		String address = toUnicode(request.getParameter("Address"));

		String chkValue = null;
		boolean buildOK = false;
		PrivateKey key = new PrivateKey();
		try {
			buildOK = key.buildKey(merId, 0, MerKeyPath);
			System.out.println(merId);
			System.out.println(buildOK);
		} catch (Exception e) {
			System.out.println("私钥创建失败！");
		}

		String Data = merId + certType + cardNo + usrName + certId + mobile
				+ address + email;
		System.out.println("字符串数据拼装：" + Data);

		SecureLink sl = new SecureLink(key);
		chkValue = sl.Sign(Data);
		System.out.println("签名内容：" + chkValue);
		System.out.println("签名成功！");

		TransactionBean pay = new TransactionBean();
		pay.setMerId(merId);
		pay.setUserName(usrName);
		pay.setCertId(certId);
		pay.setCertType(certType);
		pay.setCardNo(cardNo);
		pay.setAddress(address);
		pay.setEmail(email);
		pay.setMobile(mobile);
		pay.setChkValue(chkValue);

		request.setAttribute("payInput", pay);
		request.getRequestDispatcher(PayCommit).forward(request, response);
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
