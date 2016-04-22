<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
Map map = new LinkedHashMap();
Enumeration names = request.getParameterNames();
while (names.hasMoreElements())
{
    String name = (String) names.nextElement();
    String[] values = request.getParameterValues(name);
    map.put(name, values);
}
session.setAttribute("params", map);//表单参数
request.setAttribute("max", new Integer(30));//进度块的最大个数
%>
<html>
<head>
<title></title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
<link REL="stylesheet"  TYPE="text/css"  HREF="../css/common.css">
</head>
<script language="JavaScript">
var number = 0;
var max = ${max};

function check()
{
  start();
  document.form.submit();
}

function start()
{
  number++;

  if (number > max)
  {
    for (var i = 1; i <= max; i++)
    {
      document.getElementById("span" + i).style.backgroundColor = "transparent";
    }
    number = 0;
  }
  else
  {
    document.getElementById("span" + number).style.backgroundColor = "green";
  }

  setTimeout("start()", 500);
}

</script>

<body onload="check()">
<%-- 表单 --%>
<form name="form" method="post" action="${param.url}">
<logic:iterate id="entry" name="params">
<bean:define id="name" name="entry" property="key"></bean:define>
<bean:define id="values" name="entry" property="value"></bean:define>
<logic:iterate id="value" name="values">
<input type="hidden" name="${name}" value="${value}">
</logic:iterate>
</logic:iterate>
</form>
<%-- 预留高度 --%>
<table width="100%" height="25%"><tr><td></td></tr></table>
<%-- 进度条 --%>
<div align="center">
<p>正在进行操作，请稍后......</p>
<span style="font-size: 12px; padding: 2px; border: thin solid 1px;">
<c:forEach var="i" begin="1" end="${max}">
<span id="span${i}">&nbsp;</span>
</c:forEach>
</span>
</div>
</body>
</html>
