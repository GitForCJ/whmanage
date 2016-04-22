<%@ page contentType="text/html; charset=GBK" language="java"%>

<%@ page import="java.util.*" %>
<%@ page import="com.wlt.webm.util.*"%>
<%@ page import="com.commsoft.epay.util.logging.Log"%>
<%@ page import="com.wlt.webm.ten.service.*" %>
<%@ page import="com.wlt.webm.ten.bean.HttpRequest" %>
<%
	TenpayXmlPath tenpayxmlpath = new TenpayXmlPath();
	//交易数量
	String num=request.getParameter("order_price"); 
    String rebate =request.getParameter("qbRebate");
	int totalFee=(Integer.valueOf(num).intValue())*(Integer.valueOf(rebate).intValue());	
	//交易账号
	String in_acct=request.getParameter("in_acct");
	System.out.println(num+rebate+in_acct); 
	//商户号
	String bargainor_id = TenpayConfigParser.getConfig().getQbid();
	//密钥
	String key = TenpayConfigParser.getConfig().getQbsign();
	//回调通知URL
	String return_url = TenpayConfigParser.getConfig().getQburl();
	//当前时间 yyyyMMddHHmmss
    String currTime = TenpayUtil.getCurrTime();
	String transaction_id =TenpayUtil.getQBChars(currTime);
	//创建PayRequestHandler实例
	QBPayRequestHandler reqHandler = new QBPayRequestHandler(request,response);
		//初始化
	reqHandler.init();
	//设置密钥
	reqHandler.setKey(key);
	//设置支付参数
	//-----------------------------
	reqHandler.setParameter("mch_id", bargainor_id); //商户号
	reqHandler.setParameter("tran_seq", transaction_id); //交易单号
	reqHandler.setParameter("ret_url", return_url); //支付通知url
	reqHandler.setParameter("num", num); //交易数量
	reqHandler.setParameter("in_acct", in_acct); //账号
	System.out.println("posid:"+(String)request.getParameter("posid"));
	HttpRequest.maps.put(transaction_id.trim(),(String)request.getParameter("posid"));
	System.out.println("----============"+transaction_id);
	System.out.println(HttpRequest.maps.get(transaction_id.trim()));
	//记录日志
   // reqHandler.logTransferToDB(bargainor_id,currTime.substring(0,8),transaction_id, in_acct,totalFee,currTime,num,(String)request.getParameter("posid"));
	//获取请求带参数的url
	String requestUrl = reqHandler.getRequestURL();
	//获取debug信息
	String debuginfo = reqHandler.getDebugInfo();
	Log.info("requestUrl:" + requestUrl);
	Log.info("debuginfo:" + debuginfo);
	response.setHeader("Cache-Control","no-cache");
 	String url=HttpRequest.readContentFromGet(requestUrl,transaction_id,in_acct);
	String strHtml = "<html><head>\r\n" +url+"\r\n</head><body></body></html>";
	System.out.println(strHtml);
	out.print(strHtml);
	//out.flush();
	//out=pageContext.pushBody();
%>

