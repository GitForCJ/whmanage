<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="tools" scope="page" class="com.commsoft.epay.pc.operation.bean.FineinFormationCount" />
<%
  request.setAttribute("list", tools.getFeeamountList(request.getParameter("type")));
%>
<?xml version="1.0" encoding="GBK"?>
<fees> 
	<logic:iterate id="info" name="list">
  <sys>
    <id>${info[1]}</id>
    <name>${info[1]}</name>
  </sys>
</logic:iterate>
</fees>