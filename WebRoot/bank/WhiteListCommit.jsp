<%@ page language="java" import="chinapay.model.bean.*" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="payInput" scope="request" class="chinapay.model.bean.TransactionBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>WhiteListCommit.jsp</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK" />
  </head>
  
  <body>
 	   <center><strong><font size="3"> 交易信息：</font></strong></center><br>
 	   <form name="WhiteListReturn" action="WhiteListReturn" method="POST" >
 	   <%
	String  MerId = payInput.getMerId();
	String  ChkValue = payInput.getChkValue();
	String  UserName = payInput.getUserName();
	String  CardNo = payInput.getCardNo();
	String  CertId = payInput.getCertId();
	String  CertType = payInput.getCertType();
	String  Mobile = payInput.getMobile();
	String  Address = payInput.getAddress();
	String  Email = payInput.getEmail();

%>	
  <table  border="1" cellspacing="0" cellpadding="0" width="650"  bordercolor="#2B91D5" align="center">
	  <tr>
	     <td colspan="2"><center><font color="blue"> 交易银行卡信息 </font></center></td>
	  </tr>
	  <tr>
		  <td>商户号:</td>
		  <td><%= MerId %></td>
	  </tr>
	  <tr>
		  <td>持卡人姓名:</td>
		  <td><%= UserName %></td>
	  </tr>
		   
	  <tr>
		  <td>证件类型：</td>
		  <td><%= CertType %></td>
	   </tr>
	   <tr>
		  <td>证件号码：</td>
		  <td><%= CertId %></td>
	  </tr>
	  <tr>
		  <td>卡号/折号：</td>
		  <td><%= CardNo  %></td>
	  </tr> 
	  <tr>
		  <td>手机号码：</td>
		  <td><%= Mobile  %></td>
	  </tr> 
	  <tr>
		  <td>住址：</td>
		  <td><%= Address  %></td>
	  </tr> 
	  <tr>
		  <td>邮箱：</td>
		  <td><%= Email  %></td>
	  </tr> 
	  <tr>
		  <td>数字签名:</td>
		  <td width="500" style="word-break:break-all">
                     <%= ChkValue %>
                </td>
	  </tr>
  </table>
<input type=hidden name="merId" value="<%=MerId%>">
<input type=hidden name="chkValue" value="<%=ChkValue%>">
<input type=hidden name="usrName" value="<%=UserName%>">
<input type=hidden name="certType" value="<%=CertType%>">
<input type=hidden name="certId" value="<%=CertId%>">
<input type=hidden name="cardNo" value="<%=CardNo%>">
<input type=hidden name="mobile" value="<%=Mobile%>">
<input type=hidden name="address" value="<%=Address%>">
<input type=hidden name="email" value="<%=Email%>">
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
