<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="busiagent" scope="page" class="com.commsoft.epay.pc.operation.bean.BusiAgentBean" />
<%
  String type = request.getParameter("type");
  request.setAttribute("list", busiagent.getBusiAgentGoodsList(type));
%>
<?xml version="1.0" encoding="GBK"?>
<goodss> 
<logic:iterate id="info" name="list"> 
	<goods>
	<code>${info[0]}</code>
	<name>${info[1]}</name>
	</goods>    
</logic:iterate>
</goodss>
