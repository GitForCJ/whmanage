<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<%
  String path = request.getContextPath();
%>
<html:html>
<jsp:useBean id="area" scope="page" class="com.wlt.webm.rights.bean.SysArea"/>
<head>
<title>万汇通</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
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
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
</SCRIPT>
</head>
<script language="JavaScript">
	
</script>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<form action="<%=path %>/rights/sysuser.do?method=zshlist" method="post">
<div class="selCity">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>账号：</td>
    <td><input name="username" type="text" value="${requestScope.uform.username }"/></td>
  </tr>
	<c:if test="${(sessionScope.userSession.roleType == 0) || (sessionScope.userSession.roleType == 1)}">
	  <tr>
	  	<td>角色类型：</td>
	    <td>
		<div class="sub-input"><a class="sia-2 selhover" id="sl-roleType" href="javascript:void(0);">请选择角色类型</a></div>
	  	<input nname="nname" name="roleType" id="roleType" defaultsel="${requestScope.roleSel }" type="hidden" value="${uform.roleType }" />
		</td>
	    <td>状态：</td>
	    <td>
		<div class="sub-input"><a class="sia-2 selhover" id="sl-userstatus" href="javascript:void(0);">请选择状态</a></div>
	  	<input nname="nname" name="userstatus" id="userstatus" defaultsel="请选择状态[]|正常[0]|注销[1]|暂停[2]" type="hidden" value="${uform.userstatus }" />
		</td>
	    <td>&nbsp;</td>
	    <td></td>
	  </tr>
	</c:if>
  <tr>
    <td>开始日期：</td>
    <td><input class="Wdate" name="startDate" type="text" onClick="WdatePicker()" value="${requestScope.uform.startDate }"></td>
    <td>结束日期：</td>
    <td><input class="Wdate" name="endDate" type="text" onClick="WdatePicker()" value="${requestScope.uform.endDate }"></td>
    <td>&nbsp;</td>
    <td><input type="button" name="Button" value="查询" class="field-item_button" onClick="document.forms[0].submit()"></td>
  </tr>
</table>

    <div class="none"><A id=foldin href="javascript:;">收起</A></div>
</div>
</form>
 <div id="div_scroll">
<table id="myTable">
<thead>
<tr>
<th>序号</th>
<th>登陆账号</th>
<th>用户姓名</th>
<th>所属地市</th>
<th>可用余额</th>
<th>状态</th>
<th>操作</th>
</tr>
</thead>
<tbody id="tab">
 <logic:iterate id="info" name="userList" indexId="i">
<tr>
<td>${i + 1}</td>
<td>${info[0] }</td>
<td>${info[2] }</td>
<td>${info[3] }</td>
<td>${info[6]/1000 }</td>
<td>${info[4] }</td>
<td>
	<a href="<%=path %>/rights/wltuserupdate1.jsp?uid=${info[1]}&ph=${info[5]}">用户详情</a>
	<c:if test="${info[7]>99}">
		<font color=red >(附属油站)</font>
	</c:if>
	<c:if test="${info[7]<=99}">
		<c:if test="${info[6]/1000>100&&sessionScope.userSession.roleType==2}">
			<a href="<%=path %>/rights/sysuser.do?method=getZshUserInfo&uid=${info[1]}">提取</a>
		</c:if>
		<a href="<%=path %>/rights/wltuserTransHref.jsp?uLogin=${info[0]}&uName=${info[2]}&type=2">额度转移</a>
	</c:if>
</td>
</tr>
</logic:iterate>
</tbody>
<tfoot>
<tr>
<td colspan="12">
<div class="grayr">
<wlt:pager url="rights/sysuser.do?method=zshlist${uform.paramUrl}&st=${requestScope.st}" page="${requestScope.page}" style="black" />
</div>
</td>
</tr>
</tfoot>
</table>
</div>
</body>
</html:html>