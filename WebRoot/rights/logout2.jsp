<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
<head>
<html:base/>
<title></title>
<%
  response.setHeader("Cache-Control","no-cache");
  String jumpto="<script language='JavaScript'>"+
				"  top.window.location='frame.jsp';" +
                "</script>";
  out.print(jumpto);
%>
</head>
<body>
</body>
</html>
