<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
 	 String path = request.getContextPath();
 %>
 <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="../js/util.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type=text/javascript>
showMessage("${mess}");
function check(){
  	with(document.forms[0]){
		if(username.value == ""){
			alert("请输入帐号!");
			username.focus();
			return;
		}
		if(username.value.length<6 || username.value.length>11){
			alert("帐号长度为6到11位!");
			username.focus();
			return;
		}
		var t=/^[A-Za-z0-9]+$/;
		if(!t.test(username.value)){
			alert('帐号只能是数字和字符!');
			username.focus();
			return ;
		}
		if(phone.value == ""){
			alert("请输入接收短信的号码!");
			phone.focus();
			return;
		}
		if(!isMobileNO(phone)){
			return;
		}
		document.getElementById("checkCode").disable=false;
		submit();
		}
		}
	//验证手机号码
function isMobileNO(obj){
	var objValue = obj.value;
	if(typeof(objValue)=="undefined")
		return false;
	if(objValue=="" || objValue==null){ 
		return false;
	} 
	if(objValue.length!=11){
		alert('账号为11位手机号码！');
		return false;
	}
	var myreg = /^(1[3-9]{1}[0-9]{1})\d{8}$/;
	if(!myreg.test(objValue)){
		alert('请输入合法的手机号码帐号！');
		obj.value="";
		obj.focus();
		return false;
	}
	return true;
}

function  god(className) {
  if(className.value.length>=1 && className.value.length<6){
  	alert("登录帐号长度不足!");
  	return ;
  }
}
</script>
</head>

<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<br><input type="hidden" id="rootPath" value="<%=path %>"/>
<html:form  method="post" action="/rights/sysuser.do?method=updatePwd">
<div class="pass_main">
    <div class="pass_list">
    	<ul>
    		<li><div class="pass_title_1">用户密码重置</div></li>
            <li><strong>登陆帐号：</strong><span><input name="username" type="text" id="username"  maxlength="11"  onblur="god(this)"/><font color="red">*(请填写您的登陆账号)</font></span></li>
			<li><strong>接收短信手机号：</strong><span><input name="phone" type="text"  maxlength="11" id="phone" /><font color="red">*(用于接收重置后的密码)</font></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="返回" id="checkCode" onclick="location.href='<%=path %>/rights/wltlogin.jsp'" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>