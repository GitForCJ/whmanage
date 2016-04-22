<%@ page language="java" import="chinapay.model.bean.*" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="payInput" scope="request" class="chinapay.model.bean.TransactionBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>TransFail.jsp</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK" />
  </head>
  
  <body>
 	   <center><strong><font size="3"> Chinapay控台返回信息：</font></strong></center><br>
 	   <%
	String  ResponseCode = payInput.getResponseCode();
//	String  Stat = payInput.getStat();

%>	
  <table  border="1" cellspacing="0" cellpadding="0" width="300"  bordercolor="#2B91D5" align="center">
	   <tr>
	     <td colspan="2"><center><font color="blue"> 交易状态信息 </font></center></td>
	  </tr>
	  <tr>
		  <td>交易应答码:</td>
		  <td><%= ResponseCode %></td>
	  </tr>

	  <tr>
	     <td colspan="2"><center><font color="red"> 验签失败，签名数据不匹配！</font></center></td>
	  </tr>
  </table>

	   <br>
       <center>
  	<a href="./index.jsp">返回</a></center><br><br><br>
  </body>
</html>
