<br><%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<%
  String path = request.getContextPath();
  List list = user.oufeiList(userSession.getUser_id()); 
  request.setAttribute("prodList",list); 
%>
<html:html>
<head>
<title>万汇通</title>
<link rel="stylesheet" type="text/css" href=../css/common.css>
<style type="text/css">
.tabs_wzdh { width: 100px; float:left;}
.tabs_wzdh li { height:35px; line-height:35px; margin-top:5px; padding-left:20px; font-size:14px; font-weight:bold;}
/*tree_nav*/oufeiList
.tree_nav { border:1px solid #CCC; background:#f5f5f5; padding:10px; margin-top:20px;}
.field-item_button_m2 { margin:0 auto; text-align:center;}
.field-item_button2 { border:0px;width:136px; height:32px; color:#fff; background:url(../images/button_out2.png) no-repeat; cursor:pointer;margin-right:20px;}
.field-item_button2:hover { border:0px; width:136px; height:32px; color:#fff; background:url(../images/button_on2.png) no-repeat; margin-right:20px;}
</style>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<html:form action="/rights/sysuser.do?method=addOufei" method="post">
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tablelist">
		<tr class="trcontent">
		<td colspan="4" align="center" class="fontcontitle">&nbsp;供货信息&nbsp;</td>
		</tr> 
 <tr class="fontcoltitle">
    <td nowrap="nowrap"><div align="center" >下级账号<br/>资金账号</div></td>
    <td nowrap="nowrap"><div align="center" >供货ID<br/>绑定时间</div></td>
    <td nowrap="nowrap"><div align="center" >模板编号<br/>供货秘钥</div></td>
    <td nowrap="nowrap"><div align="center" >供货类型</div></td>
  </tr>
  <logic:iterate id="prodinfo" name="prodList" indexId="i">
  <tr class="tr1">
    <td align="center">${prodinfo[10]}<br/>${prodinfo[1]}</td>
<td align="center">${prodinfo[2]}<br/>${prodinfo[5]}</td>
<td align="center">${prodinfo[3]}<br/>${prodinfo[4]}</td>
<td align="center">${prodinfo[12]}</td>
  </tr>
  </logic:iterate>
</table>
</html:form>
</body>
</html:html>