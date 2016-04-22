<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysLoginUser"/>
<%
  String path = request.getContextPath();
  String userId = userSession.getUser_id();
  List list = user.getSysLoginUserList(userId); 
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
<script language="JavaScript">
	
</script>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>安全登录选择</li></ul>
  </div>
</div>
<table id="myTable">
<thead>
<tr>
<th>序号</th>
<th>登录名</th>
<th>短信验证手机号</th>
<!-- 
<th>Mac地址验证</th>
<th>Mac地址</th>
<th>登陆密码验证</th>
<th>登录短信验证</th>
<th>资金账户转款短信验证</th>
 -->
<th>状态</th>
<th>操作</th>
</tr>
</thead>
<tbody id="tab">
<logic:iterate id="userinfo" name="userList" indexId="i">
<tr>
<td>${i + 1}</td>
<logic:iterate id="info" name="userinfo">
<td>${info }</td>
</logic:iterate>
<td><a href="<%=path %>/rights/wltsafeloginuserupdate.jsp?uid=<%=userId %>&uname=${userinfo[0]}">修改</a>|<a href="<%=path %>/rights/wltsafeloginuserdetail.jsp?uid=<%=userId %>&uname=${userinfo[0]}">详细</a></td>
</tr>
</logic:iterate>
</tbody>
<!--
<tfoot>
<tr>
<td></td>
<td colspan="8">
<div class="grayr"><span class="disabled"> < </span><span class="current">1</span><a href="#?page=2" _fcksavedurl="#?page=2">2</a><a href="#?page=3" _fcksavedurl="#?page=3">3</a><a href="#?page=4" _fcksavedurl="#?page=4">4</a><a href="#?page=5" _fcksavedurl="#?page=5">5</a><a href="#?page=6" _fcksavedurl="#?page=6">6</a><a href="#?page=7" _fcksavedurl="#?page=7">7</a>...<a href="#?page=199" _fcksavedurl="#?page=199">199</a><a href="#?page=200" _fcksavedurl="#?page=200">200</a><a href="#?page=2" _fcksavedurl="#?page=2"> > </a></div>
<div id="page">
<a href="">首　页</a><a href="">上一页</a><a href="">下一页</a><a href="">末　页</a></div>
</td>
</tr>
</tfoot>
-->
<!-- 
<tr>
<td colspan="9"><input class="field-item_button" type="button" value="新增登录账号" onclick="javascript:location.href='<%=path %>/rights/wltsafeloginuseradd.jsp?uid=<%=userId %>'"/></td>
</tr>
 -->
</table>
<!-- 
<font style="font-family:'宋体'; font-size:17px; " color="red">请用户按照自身的习惯选择安全验证方式</font>
 -->
</body>
</html:html>