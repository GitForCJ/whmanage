<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="cardBean" scope="page" class="com.commsoft.epay.pc.operation.bean.SellCardBean" />
<jsp:useBean id="userSession" scope="session" class="com.commsoft.epay.pc.rights.form.UserForm" />
<%
  String resourceid=userSession.getResourceId();
  String cardType = request.getParameter("cardType");
  request.setAttribute("list", cardBean.getCardFeeList(cardType,resourceid));
%>
<?xml version="1.0" encoding="GBK"?>
<fees> 
<logic:iterate id="info" name="list">
	<fee>
	<code>${info[0]}</code>
	<name>${info[1]}</name>
	</fee>   
</logic:iterate>
</fees>