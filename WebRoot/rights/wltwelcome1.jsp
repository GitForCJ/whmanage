<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
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

<script type="text/javascript">
	function fun(type)
	{
		/**
		var arry=document.getElementsByName(type);
		for(var i=0;i<arry.length;i++)
		{
			arry[i].style.display="block";
		}
		document.getElementById(type+"_").style.display="none";
		*/
		window.location.href="<%=path%>/rights/gg.jsp?type="+type;
	}
</script>
</head>
<body>

<div class="rcontent" style="background-color:white;border:0px;width:98.5%;padding-top:10px;">
<table style="width:49.8%;float:left;">
<thead>
<tr>
<th style="position:relative;"><div><img style="width:20px;height:20px;margin-top:-4px;" alt="消息" src="<%=path %>/images/aaa1.png"/>&nbsp;最新公告</div><a id="A_" style="position:absolute;right:10px;bottom:7px;font-size:13px;" href="javascript:fun('A')"><img style="width:20px;height:20px;" src="<%=path %>/images/add.png"/>更多>></a></th>
</tr>
</thead>
<tbody>
<tr>
<td>
<%
	int conc=0;
 %>
<logic:iterate id="noticeinfo" name="noticeList" indexId="i">
	<c:if test="${noticeinfo[8]==1}">
	<%
			conc++;
			if(conc>=11)
			{
		 %>
		<div class="notice" name="A" style="border:0px;border-bottom:1px solid #cccccc; height:25px;display:none;">
		<%
			}
			else
			{
		 %>
		 <div class="notice"  style="border:0px;border-bottom:1px solid #cccccc; height:25px;">
		 <%
		 }
		  %>
		<div class="left_txt" style="height:23px;position:relative;width:100%">
				<img style="position:absolute;left:3px;bottom:3px;" src="<%=path %>/images/lb.png"/>
				<c:if test="${noticeinfo[9]==1}">
					<div style="height:20px;position:absolute;left:10px;bottom:0px;"><a style="font-size:14px;color:black;" href="<%=path %>/rights/wltnoticeinfo.jsp?uid=${noticeinfo[0]}">${noticeinfo[1]}</a></div>
				</c:if>
				<c:if test="${noticeinfo[9]==0}">
					<div style="height:20px;position:absolute;left:10px;bottom:0px;"><a style="font-size:14px;color:black;" href="<%=path %>/rights/wltnoticeinfo.jsp?uid=${noticeinfo[0]}">
						${noticeinfo[1]}&nbsp;&nbsp;
						<img style="" src="<%=path %>/images/add.gif"/></a></div>
				</c:if>
				<div style="height:20px;position:absolute;right:0px;bottom:0px;"><a style="font-size:14px;color:black;" href="<%=path %>/rights/wltnoticeinfo.jsp?uid=${noticeinfo[0]}">${noticeinfo[3]}</a></div>
			</div>
		</div>
	</c:if>
</logic:iterate>
<%
		if(conc<10)
		{
			for(int dd=conc;dd<10;dd++)
			{
				%>
				<div class="notice" name="A" style="border:0px; height:26px;">&nbsp;</div>
				<%
			}
		}
	 %>
</td>
</tr>
</tbody>
</table>

<table style="width:49.8%;float:right;">
<thead>
<tr>
<th style="position:relative;"><div><img style="width:20px;height:20px;margin-top:-4px;" alt="消息"  src="<%=path %>/images/aaa1.png"/>&nbsp;最新活动</div><a id="B_" style="position:absolute;right:10px;bottom:7px;font-size:13px;" href="javascript:fun('B')"><img style="width:20px;height:20px;" src="<%=path %>/images/add.png"/>更多>></a></th>
</tr>
</thead>
<tbody>
<tr>
<td>
<%
	int con=0;
 %>
<logic:iterate id="noticeinfo" name="noticeList" indexId="i">
	<c:if test="${noticeinfo[8]==2}">
		<%
			con++;
			if(con>=11)
			{
		 %>
		<div class="notice" name="B" style="border:0px;border-bottom:1px solid #cccccc; height:25px;display:none;">
		<%
			}
			else
			{
		 %>
		 <div class="notice"  style="border:0px;border-bottom:1px solid #cccccc; height:25px;">
		 <%
		 }
		  %>
			<div class="left_txt" style="height:23px;position:relative;width:100%">
				<img style="position:absolute;left:3px;bottom:3px;" src="<%=path %>/images/lb.png"/>
				<c:if test="${noticeinfo[9]==1}">
					<div style="height:20px;position:absolute;left:10px;bottom:0px;"><a style="font-size:14px;color:black;"  href="<%=path %>/rights/wltnoticeinfo.jsp?uid=${noticeinfo[0]}">${noticeinfo[1]}</a></div>
				</c:if>
				<c:if test="${noticeinfo[9]==0}">
					<div style="height:20px;position:absolute;left:10px;bottom:0px;"><a style="font-size:14px;color:black;"  href="<%=path %>/rights/wltnoticeinfo.jsp?uid=${noticeinfo[0]}">
						${noticeinfo[1]}&nbsp;&nbsp;
						<img style="" src="<%=path %>/images/add.gif"/></a></div>
				</c:if>
				<div style="height:20px;position:absolute;right:0px;bottom:0px;"><a style="font-size:14px;color:black;"  href="<%=path %>/rights/wltnoticeinfo.jsp?uid=${noticeinfo[0]}">${noticeinfo[3]}</a></div>
			</div>
		</div>
	</c:if>
</logic:iterate>
	<%
		if(con<10)
		{
			for(int cc=con;cc<10;cc++)
			{
				%>
				<div class="notice" name="B" style="border:0px;height:26px;">&nbsp;</div>
				<%
			}
		}
	 %>
</td>
</tr>
</tbody>
</table>

</div>


</body>
</html>