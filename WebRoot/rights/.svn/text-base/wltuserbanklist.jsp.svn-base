<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<%
  String path = request.getContextPath();
  String srType = request.getParameter("st");
  List list = user.getSysUserBankList(srType,request.getSession()); 
  request.setAttribute("userList",list); 
%>
<html:html>
<jsp:useBean id="area" scope="page" class="com.wlt.webm.rights.bean.SysArea"/>
<head>
<title>万汇通</title>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href=../css/common.css>
<style type="text/css">
.tabs_wzdh { width: 100px; float:left;}
.tabs_wzdh li { height:35px; line-height:35px; margin-top:5px; padding-left:20px; font-size:14px; font-weight:bold;}
/*tree_nav*/
.tree_nav { border:1px solid #CCC; background:#f5f5f5; padding:10px; margin-top:20px;}
.field-item_button_m2 { margin:0 auto; text-align:center;}
.field-item_button2 { border:0px;width:136px; height:32px; color:#fff; background:url(../images/button_out2.png) no-repeat; cursor:pointer;margin-right:20px;}
.field-item_button2:hover { border:0px; width:136px; height:32px; color:#fff; background:url(../images/button_on2.png) no-repeat; margin-right:20px;}
</style>
<script type="text/javascript" src="../js/util.js"></script>
</head>
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script language="JavaScript">
	$(function(){
		if($("#mess").val() != ""){
			alert($("#mess").val());
		}
	})
</script>
<div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>银行卡管理</li></ul>
</div>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<html:form  method="post" action="/business/bank.do?method=userBank">
网点账户:<input name="loginname"  /><input type="submit"  value="提交"/>
</html:form>
<table id="myTable">
<thead>
<tr>
<th>序号</th>
<th>账号</th>
<th>角色类型</th>
<th>角色名称</th>
<th>邮箱</th>
<th>联系电话</th>
<th>状态</th>
<th>创建时间</th>
<th>操作</th>
</tr>
</thead>
<tbody id="tab">
 <logic:iterate id="userinfo" name="userList" indexId="i">
<tr>
<td>${i + 1}</td>
<logic:iterate id="info" name="userinfo" indexId="j">
<logic:notEqual name="j" value="0">
<td>${info }</td>
</logic:notEqual>
</logic:iterate>
<td><a href="<%=path %>/rights/wltuserbankbound.jsp?uid=${userinfo[0]}&uname=${userinfo[1]}">绑定</a>|<a href="<%=path %>/rights/wltuserbankinfo.jsp?uid=${userinfo[0]}&uname=${userinfo[1]}">详细</a></td>
</tr>
</logic:iterate>
</tbody>
<tfoot>
<tr>
<td>lo</td>
<!--
<td colspan="8">
<div class="grayr"><span class="disabled"> < </span><span class="current">1</span><a href="#?page=2" _fcksavedurl="#?page=2">2</a><a href="#?page=3" _fcksavedurl="#?page=3">3</a><a href="#?page=4" _fcksavedurl="#?page=4">4</a><a href="#?page=5" _fcksavedurl="#?page=5">5</a><a href="#?page=6" _fcksavedurl="#?page=6">6</a><a href="#?page=7" _fcksavedurl="#?page=7">7</a>...<a href="#?page=199" _fcksavedurl="#?page=199">199</a><a href="#?page=200" _fcksavedurl="#?page=200">200</a><a href="#?page=2" _fcksavedurl="#?page=2"> > </a></div>
<div id="page">
<a href="">首　页</a><a href="">上一页</a><a href="">下一页</a><a href="">末　页</a></div>
</td>
-->
</tr>
</tfoot>
</table>
</body>
</html:html>