<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<%
  String path = request.getContextPath();
  String srType = request.getParameter("st");
  List list = user.getSysUserInterfaceList(srType,request.getSession()); 
  request.setAttribute("userList",list); 
%>
<html:html>
<head>
<title>万汇通</title>
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
<html:form method="post" action="/business/sui.do?method=del">
		<tr class="trcontent">
		<td colspan="4" align="center" class="fontcontitle">&nbsp;用户接口列表&nbsp;</td>
		</tr>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tablelist">
  <tr class="trlist">
    <td colspan="9"><div align="center" class="fontlisttitle">用户接口</div></td>
  </tr>
  <tr class="fontcoltitle">
    <td nowrap="nowrap"><div align="center" >序号</div></td>   
    <td nowrap="nowrap"><div align="center" >账号</div></td>
    <td nowrap="nowrap"><div align="center" >角色类型</div></td>
    <td nowrap="nowrap"><div align="center" >角色名称</div></td>
    <td nowrap="nowrap"><div align="center" >状态</div></td>
    <td nowrap="nowrap"><div align="center" >操作</div></td>
  </tr>
  <logic:iterate id="userinfo" name="userList" indexId="i">
  <tr class="tr1">
    <td align="center">${i + 1}</td>
    <logic:iterate id="info" name="userinfo" indexId="j">
    <logic:notEqual name="j" value="0">
    <td align="center">${info }</td>
    </logic:notEqual>
    </logic:iterate>
    <td align="center"> <a href="<%=path %>/business/wltuserinterfacelist.jsp?uid=${userinfo[0]}">查看用户接口</a></td>
  </tr>
  </logic:iterate>
</table>
</html:form>
</body>
</html:html>