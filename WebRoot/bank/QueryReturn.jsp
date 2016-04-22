<%@ page language="java" import="chinapay.model.bean.*" pageEncoding="GBK"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="payInput" scope="request" class="chinapay.model.bean.TransactionBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>QueryReturn.jsp</title>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK" />
  </head>
  
  <body>
 	   <center><strong><font size="3"> Chinapay控台返回信息：</font></strong></center><br>
 	   <%
	String  MerId = payInput.getMerId();
	String  OrdId = payInput.getOrdNo();
	String  TransDate = payInput.getTransDate();
	String  TransType = payInput.getTransType();
	String  TransAmt = payInput.getTransAmt();
	String  CuryId = payInput.getCuryId();
	String  GateId = payInput.getGateId();
	String  Version	= payInput.getVersion();
	String  ChkValue = payInput.getChkValue();
	String  Priv1 = payInput.getPriv1();
	String  Purpose = payInput.getPurpose();
	String  UserName = payInput.getUserName();
	String  CardNo = payInput.getCardNo();
	String  CardType = payInput.getCardType();
	String  OpenBankId = payInput.getOpenBankId();
	String  CertId = payInput.getCertId();
	String  CertType = payInput.getCertType();
	String  ResposeCode = payInput.getResponseCode();
	String  TransStat = payInput.getTransStatus();

%>	
  <table  border="1" cellspacing="0" cellpadding="0" width="550"  bordercolor="#2B91D5" align="center">
	   <tr>
	     <td colspan="2"><center><font color="blue"> 交易状态信息 </font></center></td>
	  </tr>
	  <tr>
		  <td>交易应答码:</td>
		  <td><%= ResposeCode %></td>
	  </tr>
		   
	  <tr>
		  <td>交易状态：</td>
		  <td><%= TransStat %></td>
	   </tr>
	  <tr>
	     <td colspan="2"><center><font color="blue"> 交易银行卡信息 </font></center></td>
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
		  <td>开户银行号:</td>
		  <td><%= OpenBankId  %></td>
	  </tr> 
	  <tr>
		  <td>卡折标志：</td>
		  <td><%= CardType  %></td>
	  </tr>
	  <tr>
		  <td>卡号/折号：</td>
		  <td><%= CardNo  %></td>
	  </tr> 
	  <tr>
	     <td colspan="2"><center><font color="blue"> 商户交易信息 </font></center></td>
	  </tr>  
	  <tr>
		  <td>商户号:</td>
		  <td><%= MerId %></td>
	  </tr>		   
	  <tr>
		  <td>订单号:</td>
		  <td><%= OrdId %></td>
	   </tr>
	   <tr>
		  <td>交易金额:</td>
		  <td><%= TransAmt %></td>
	  </tr>
	  <tr>
		  <td>交易币种:</td>
		  <td><%= CuryId %></td>
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
		  <td>私有域:</td>
		  <td><%= Priv1 %></td>
	  </tr>
	  <tr>
	     <td colspan="2"><center><font color="red"> 验签数据正确！</font></center></td>
	  </tr>
  </table>
	   <br>
       <center>
  	<a href="./index.jsp">返回</a></center><br><br><br>
  </body>
</html>
