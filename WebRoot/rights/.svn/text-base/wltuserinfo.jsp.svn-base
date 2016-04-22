<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
 <%
 	 String path = request.getContextPath();
	 String userid=request.getParameter("uid");
   	 request.setAttribute("userinfo", user.getUserInfo(userid));
   	 List areaList = role.getAreaList();
	 StringBuffer sBuffer = new StringBuffer();
	 sBuffer.append("请选择[]");
	 for(Object tmp : areaList){
		String[] temp = (String[])tmp;
		sBuffer.append("|"+temp[1]+"["+temp[0]+"]");
	 }
	 request.setAttribute("areaSel", sBuffer.toString());
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
</SCRIPT>
</head>
<body>
<html:form  method="post" action="/rights/sysuser.do?method=add">
<div class="pass_main">
	<div class="pass_title_1">用户信息</div>
    <div class="pass_list">
    	<ul>
            <li><strong>帐号：</strong><span>${userinfo.username }</span></li>
            <li><strong>用户编号：</strong><span>${userinfo.userno }</span></li>
            <li><strong>用户姓名：</strong><span>${userinfo.userename }</span></li>
            <li><strong>角色：</strong><span>${userinfo.role_name }</span></li>
            <li><strong>邮箱：</strong><span>${userinfo.usermail }</span></li>
            <li><strong>电话：</strong><span>${userinfo.usertel }</span></li>
            <li><strong>状态：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-userstatus" href="javascript:void(0);">正常</a></div>
			<input nname="nname" name="userstatus" id="userstatus" defaultsel="正常[0]|禁用[1]|销户[2]" type="hidden" value="${userinfo.userstatus}" />
            </span></li>
            <li><strong>区域：</strong><span>
            	<div class="sub-input"><a class="sia-2 selhover" id="sl-usersite" href="javascript:void(0);">请选择</a></div>
				<input nname="nname" name="usersite" id="usersite" defaultsel="${requestScope.areaSel }" type="hidden" value="${userinfo.usersite }" />
            </span></li>
            <li><strong>描述：</strong><span>${userinfo.userdesc }</span></li>
            <li><strong>创建时间：</strong><span>${userinfo.usercreatedate }</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="返回" id="checkCode" onclick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>