<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@page import="com.wlt.webm.rights.form.SysUserBankForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="userBank" scope="page" class="com.wlt.webm.rights.bean.SysUserBank"/>
 <%
 	 String path = request.getContextPath();
	 String userid=request.getParameter("uid");
	 SysUserBankForm bank = userBank.getUserBankInfo(userid);
	 String isNullBank = "false";
	 if(null == bank.getUser_id()){
		 isNullBank = "true";
	 }
   	 request.setAttribute("bankinfo", bank);
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
function submitForm(){
	if($("user_bankcard").val() == ''){
		alert("银行卡号不能为空");
		return;
	}
	if($("user_name").val() == ''){
		alert("用户姓名不能为空");
		return;
	}
	if($("user_icard_type").val() == ''){
		alert("身份证类型不能为空");
		return;
	}
	if($("user_icard").val() == ''){
		alert("用户证件号不能为空");
		return;
	}
  	with(document.forms[0]){
		submit();
	}
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<html:form  method="post" action="/rights/sysuserbank.do?method=bankBound">
<input type="hidden" name="user_id" value="<%=userid %>"/>
<input type="hidden" name="isNullBank" value="<%=isNullBank %>"/>
<div class="pass_main">
	<div class="pass_title_1">用户信息</div>
    <div class="pass_list">
    	<ul>
            <li><strong>用户账号：</strong><span>${param.uname }</span></li>
            <li><strong>银行卡号：</strong><span><input name="user_bankcard" id="user_bankcard" type="text" value="${bankinfo.user_bankcard }"/></span></li>
            <li><strong>用户姓名：</strong><span><input name="user_name" id="user_name" type="text" value="${bankinfo.user_name }"/></span></li>
            <li><strong>身份证类型：</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-user_icard_type" href="javascript:void(0);">请选择</a></div>
			<input nname="nname" name="user_icard_type" id="user_icard_type" defaultsel="请选择[]|身份证[01]|军官证[02]|护照[03]|回乡证[04]|台胞证[05]|警官证[06]|士兵证[07]|其它证件[99]" type="hidden" value="${bankinfo.user_icard_type}" />
			</span></li>
            <li><strong>用户证件号：</strong><span><input name="user_icard" id="user_icard" type="text" value="${bankinfo.user_icard }"/></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="保存" id="btnSubmit" onclick="submitForm();" class="field-item_button" />
   <input type="button" value="返回" id="checkCode" onclick="location.href='<%=path %>/rights/wltuserbanklist.jsp'" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>