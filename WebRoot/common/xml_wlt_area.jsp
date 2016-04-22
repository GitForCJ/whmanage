<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="area" scope="page" class="com.wlt.webm.rights.bean.SysArea"/>
<%
  String areaid = request.getParameter("areaid");
  request.setAttribute("list", area.getNextList(areaid));
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