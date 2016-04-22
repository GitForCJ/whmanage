<%@ page contentType="text/html; charset=GBK" language="java"%>

<%@ page import="java.util.*" %>
<%@ page import="com.wlt.webm.util.*"%>
<%@ page import="com.commsoft.epay.util.logging.Log"%>
<%@ page import="com.wlt.webm.ten.service.*" %>
<%@ page import="com.wlt.webm.ten.bean.HttpRequest" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
<script type="text/javascript" src="../js/jquery-1.4.1.min.js"></script>
</head>
<%
String qbRebate=request.getParameter("qbRebate");
String in_acct=request.getParameter("in_acct");
String order_price=request.getParameter("order_price");
String posid=request.getParameter("posid");
%>
<body style=" background-color: white;">
<center>
		<div id="contentsecond" style="top:15%;position:relative;">
                  <img alt="数据装载中..." src="../images/img-loading.gif" align="middle" />
<br>
                  <font color="red">正在充值(最多耗时2分钟)请勿刷新以免重复扣款</font>
			<div id="realcontent"></div>
		</div>
</center>
</body>
<script language="JavaScript">
$(document).ready(function(){
	$("#realcontent").load("qqtransfer2.jsp?qbRebate="+<%=qbRebate %>+"&order_price="+<%=order_price %>+"&in_acct="+<%=in_acct %>+"&posid="+<%=posid %>);
	$("#contentsecond img font").bind("ajaxStart",function(){
		$("#realcontent").html("");
		$(this).show();
	}).bind("ajaxStop",function(){	
		$(this).slideUp("1000");
	});
});
</script>
</html>
