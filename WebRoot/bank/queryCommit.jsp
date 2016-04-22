<%@ page language="java" import="chinapay.model.bean.*" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="queryInput" scope="request" class="chinapay.model.bean.TransactionBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>QueryCommit.jsp</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK" />
  </head>
  
  <body>
 	   <center><strong><font size="3"> 单笔查询信息：</font></strong></center><br>
 	   <form name="QueryReturn" action="QueryReturn" method="POST" >
 	   <%
	String  MerId = queryInput.getMerId();
	String  OrdId = queryInput.getOrdNo();
	String  TransDate = queryInput.getTransDate();
	String  TransType = queryInput.getTransType();
	String  Version	= queryInput.getVersion();
	String  ChkValue = queryInput.getChkValue();
	String  Priv1 = queryInput.getPriv1();

%>	
  <table  border="1" cellspacing="0" cellpadding="0" width="650"  bordercolor="#2B91D5" align="center">
	  <tr>
		  <td>商户号:</td>
		  <td><%= MerId %></td>
	  </tr>		   
	  <tr>
		  <td>订单号:</td>
		  <td><%= OrdId %></td>
	   </tr>
	  <tr>
		  <td>交易日期:</td>
		  <td><%= TransDate  %></td>
	  </tr>
	  <tr>
		  <td>交易类型:</td>
		  <td><%= TransType  %></td>
	  </tr>   
		
	  <tr>
		  <td>接入版本号:</td>
		  <td><%= Version  %></td>
	  </tr> 
	  <tr>
		  <td>私有域:</td>
		  <td><%= Priv1 %></td>
	  </tr>
	  <tr>
		  <td>数字签名:</td>
		  <td width="500" style="word-break:break-all">
                     <%= ChkValue %>
                </td>
	  </tr>
  </table>
<input type=hidden name="merId" value="<%=MerId%>">
<input type=hidden name="orderNo" value="<%=OrdId%>">
<input type=hidden name="transDate" value="<%=TransDate%>">
<input type=hidden name="transType" value="<%=TransType%>">
<input type=hidden name="version" value="<%=Version%>">
<input type=hidden name="priv1" value="<%=Priv1%>">
<input type=hidden name="chkValue" value="<%=ChkValue%>">
    <br>
  	   <center>
	  	   <input type="submit" name="submitButton" value="提交" class="cmd"/>
	   </center>
	   <br>
       <center>
  	   <a href="./index.jsp">返回</a></center><br><br><br>
  	</form>
  </body>
</html>
