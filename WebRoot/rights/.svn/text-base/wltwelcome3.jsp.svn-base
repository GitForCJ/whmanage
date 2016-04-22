<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="notice" scope="page" class="com.wlt.webm.rights.bean.SysNotice"/>
<%
  String path = request.getContextPath();
  List list = notice.getSysNoticeListLatest();
  request.setAttribute("noticeList",list);
%>
<html>
<head>
<title>万汇通</title>
<link href="css/member.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="rcontent" style="width: auto;display:none;">
<table>
<thead>
<tr>
<th>最新公告</th>
</tr>
</thead>
<tbody>
<tr>
<td>
 <logic:iterate id="noticeinfo" name="noticeList" indexId="i">
<div class="notice">
<div class="left_txt">
<div  class="left_tit" >
<a href="<%=path %>/rights/wltnoticeinfo.jsp?uid=${noticeinfo[0]}">${noticeinfo[1]}</a>
</div>
<div class="left_con">${noticeinfo[7]}</div>
</div><div class="date">${noticeinfo[4]}</div>
</div>
</logic:iterate>
</td>


</tr>
</tbody>
</table>
</div>

<div>
<img src="image/zsh.jpg" width="100%" height="100%"
 style="z-index:-1;" />
</div>
</body>
</html>