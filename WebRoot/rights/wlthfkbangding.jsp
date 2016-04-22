<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
 <%
 	 String path = request.getContextPath();
   	 request.setAttribute("userinfo", userSession);
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});
function check(){
  	with(document.forms[0]){
  	if(hfkphone.value == ""){
		alert("请输入手机号码!");
		phone.focus();
		return;
		}
		if(userno.value == ""){
		alert("请输入用户编号!");
		userno.focus();
		return;
		}
		if(fundacct.value == ""){
		alert("请输入资金账号!");
		fundacct.focus();
		return;
		}
		action="sysuser.do?method=addhfk";
		target="_self";
		method="post"
		submit();
	}
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>慧付款绑定</li></ul>
  </div>
</div>
<html:form  method="post" action="/rights/sysuser.do?method=addhfk">
<input type="hidden" name="user_id" value="${userinfo.user_id }"/>
<input type="hidden" name="userename" value="${requestScope.userinfo.userename }"/>
<input type="hidden" id="mess" value="${requestScope.msg }"/>
<div class="pass_main">
    <div class="pass_list">
    	<ul>
            <li><strong>用户帐号：</strong><span>${userinfo.username }</span></li>
			<li><strong>手机号码：</strong><span><input name="hfkphone"  maxlength="11" type="text"/></span><font color="#FF0000"> * 输入使用慧付款产品的手机号码</font></li>
			<li><strong>用户编号：</strong><span><input name="userno" maxlength="15" type="text"/></span><font color="#FF0000">  * 输入正确的用户编号</font></li>
			<li><strong>资金账号：</strong><span><input name="fundacct" maxlength="15" type="text"/></span><font color="#FF0000">  * 输入用户编号对应的资金账号</font></li>
			<li><strong>慧付款ID：</strong><span><input name="hfkID" maxlength="20" type="text"/></span></li>
			<li><strong>身份证：</strong><span><input name="sfz" maxlength="20" type="text"/></span></li>
			<li><strong>使用规则：</strong><span>
							<select name="hfkgz">
								<option value="1">扣费规则A</option>
								<option value="2">扣费规则B</option>
							</select></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="提交" id="btnSubmit" onclick="check();" class="field-item_button" />
   </div>

</div>
</html:form>
<br /><br />
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、扣费规则A:转账金额低于5千收取3元手续费。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">2、扣费规则B:每笔转账收取3元手续费。</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</body>
</html>