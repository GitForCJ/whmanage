<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="d" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="bip" scope="page" class="com.wlt.webm.business.bean.BiProd" />
<%
  String path = request.getContextPath();
  List users=bip.getAgents();
  request.setAttribute("users",users);
%>
<html:html>
<jsp:useBean id="area" scope="page" class="com.wlt.webm.rights.bean.SysArea"/>
<head>
<title>接口商流量交易时间统计</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href=../css/common.css>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
if("${mess}"!=null&&"${mess}"!=""){
	alert("${mess}");
	}

function query(a){
	var start=document.getElementById("startDate").value;
	var end=document.getElementById("endDate").value;
	var user=document.getElementById("tradeTypesID").value;
	if(null==start||""==start||null==endDate||""==endDate){
		alert("请选择日期");
		return ;
		}
		
    if(confirm("确定该操作继续进行?")){
    document.getElementById("flag").value=a;
	document.forms[0].submit();
	}
}
</SCRIPT>
<style type="text/css">
#div_scroll td{border:1px solid #999;}
</style>
</head>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<html:form action="/rights/reversal.do?method=flowtradetime" method="post" >
<input type="hidden" name="flag" value="" id="flag" />
 <table width="100%" border="0" cellspacing="0" cellpadding="0" >
  <tr>
<td style="padding: 0">接口商:</td>
<td style="padding: 0">		
<select style="width:130px;height:20px;background-color:white;" name="user" id="tradeTypesID">
			<option value="">请选择</option>
<logic:iterate id="inter" name="users">
			<option value="${inter[1]}" <c:if test="${inter[1]==id}">selected="selected"</c:if> >${inter[0]}</option>
</logic:iterate>
		</select>
</td >
    <td style="padding: 0">开始日期:</td>
    <td style="padding: 0;text-align: left"><input class="Wdate" id="startDate" name="startDate" type="text" onClick="WdatePicker()" value="${requestScope.starttime }"></td>
    <td style="padding: 0">结束日期:</td>
    <td style="padding: 0"><input class="Wdate" id="endDate"  name="endDate" type="text" onClick="WdatePicker()" value="${requestScope.endtime }"></td>
    <td style="padding: 0"> <input name="Button" type="button" value="查询" class="onbutton" onClick="query('0')"></td>
    <td style="padding: 0"> <input name="Button" type="button" value="导出" class="onbutton" onClick="query('1')"></td>
  </tr>
</table>
 <div id="div_scroll" style="width:100%;">
<table id="myTable" style="border:1px solid #999;">
<thead>
<tr>
<th style="height:25px;border:1px solid #cccccc;">日期</th>
<th style="height:25px;border:1px solid #cccccc;">接口商编号</th>
<th style="height:25px;border:1px solid #cccccc;">时间范围</th>
<th style="height:25px;border:1px solid #cccccc;">笔数</th>
<th style="height:25px;border:1px solid #cccccc;">总量</th>
<th style="height:25px;border:1px solid #cccccc;">比例</th>
</tr>
</thead>
<tbody id="tab">
<c:if test="${! empty datas}">
 <logic:iterate id="proportion" name="datas" indexId="i">
<tr>
<td>${proportion[0]}</td>
<td>${proportion[1]}</td>
<td>${proportion[2]}</td>
<td>${proportion[3]}</td>
<td>${proportion[4]}</td>
<td>${proportion[5]}%</td>
</tr>
</logic:iterate>
</c:if>
<c:if test="${empty datas}">
<tr ><td colspan="7" height="50px;">
<span style="font-size: 200%;color: red;">暂无数据&nbsp;&nbsp;${mess}</span>
</td></tr>
</c:if>
</tbody>
</table>
<p style="text-align: center;"><a href="<%=path%>/rights/flowcount.jsp" style="color: red;font-size: 22px;">返回</a></p>
</div>
</html:form>
</body>
</html:html>