<%@ page contentType="text/html; charset=gb2312" session="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link REL="stylesheet"  TYPE="text/css"  HREF="../css/1.css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/check.js"></script>

<script LANGUAGE="JavaScript">
  			var i = 80;
        function msgShow() {
            document.getElementById("msg").innerHTML ="<font color=red>"+i+"</font>秒后未收到短信,请重新获取 ";
            if (i > 0) {
                i--;
            }
            else {
                document.getElementById("msg").innerHTML ="使用短信验证码";
            }
            setTimeout('msgShow()', 1000);
        }

	function runB1(form){
		var checkCode = document.getElementById("check").value.toUpperCase();
		if(checkCode.length=="" ){
			alert("请输入验证码");
			return;
		}
		if(checkCode != code){
			alert("验证码不正确");
			return;
		}
		var name = navigator.appName;
		var version = navigator.appVersion;
		if (!check(form))return;
        var date = new Date((new Date()).getTime() + 30 * 24 * 60 * 60 * 1000);
        document.cookie = "login=" + escape(form.login.value) + "; expires=" + date.toGMTString();
		form.submit();
		return;
	}
	function check(form) {
		CT=form.login;
		CTV=CT.value;
		if(CTV == ""){
			validatePrompt (CT,"登录名不能为空。");
			return (false);
		}
		CT=form.password;
		CTV=CT.value;
		if (CTV ==""){
			validatePrompt (CT,"密码不能为空。");
			return (false);
		}
		if(CTV.indexOf("'") > -1){
			validatePrompt (CT,"密码不能包含无效字符'。");
			return (false);
		}
		return (true);
	}
	function validatePrompt (Ct, PromptStr) {
		Ct.focus();
		return;
	}
	function logFocus(){
		var form = document.forms[0];
		var name = navigator.appName;
		var version = navigator.appVersion;
		if(name != "Microsoft Internet Explorer" ){
			alert("本系统必须安装IE8.0以上才能正常使用");
			return;
		}
        var cookies = document.cookie.split(";");
        for (var i = 0; i < cookies.length; i++)
        {
          var cookie = cookies[i].split("=");
          if (cookie[0].trim() == "login") {
            form.login.value = unescape(cookie[1]);
          }
        }
        if (form.login.value.length == 0) {
          form.login.focus();
        } else {
          form.password.focus();
        }
		window.status="软件";
	}
	function onkeyboard(){
		var a = event.keyCode
		if(a == 13){
			runB1(form);
		}
	}
	function clearlogin(txt){
	if(txt.value == "手机号码"){
	txt.value = "";
	}
	}
	function setlogin(txt){
	if(txt.value == ""){
	txt.value = "手机号码";
	}
	}
	function clearpw(txt){
	if(txt.value == "请输入密码"){
	txt.value = "";
	}
	}
	document.onkeydown = onkeyboard;
	showMessage("${mess}");
$(function(){
	$("#msg").click(function(){
		var username = $("input[name='login']").val();
		if(username.length!=11||!/^(13[0-9]|15[0-9]|18[0-9])\d{8}$/.test(username)){
			alert("请输入有效的手机号码");
			return;
		}
		$.post(
	    	$("#rootPath").val()+"/rights/sysuser.do?method=sendMsg",
	      	{
	      		username : username
	      	},
	      	function (data){
	      		if(data == 0){
	      		    msgShow();
	      			alert("短信发送成功");
	      		}else{
	      			alert("短信发送失败");
	      		}
	      	}
		)
	});
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
})
</script>

<title>万汇通综合服务平台</title>
</head>
<body  onLoad="createCode();logFocus()" >
<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" id="mess" value="${requestScope.msg }"/>
<html:form  method="post" action="/rights/sysuser.do?method=login&flag=0" >
<!--头部-->
<div id="top_home">
  <div id="top_home_main">
	<div class="top_home_left">
    	<ul>
        	<li class="top_phone"><a href="#">手机万汇通</a></li>
         </ul>
     </div>
    <div class="top_home_right">
    	<ul>
            <li><a href="<%=path %>">登录</a>&nbsp;&nbsp;-&nbsp;&nbsp;<a href="<%=path %>/rights/wltregist.jsp">注册</a></li>
      </ul>
     </div>
 </div>
</div>
<!--logo&nav-->
<div id="header_main">
  <div id="header">
    <div class="logo"><a href="#"><img src="../images/logo.png" width="303" height="77" /></a></div>
  </div>
</div>
<!--登录-->
<div id="login_box">
  <div id="login_main">
    <div id="login">
    	<h3>万汇通登录</h3>
    	<ul class="login_ul">
        <li><span>用户名：&nbsp;&nbsp;<input name="login" type="text"  onclick="clearlogin(this)" onblur="setlogin(this)"/><a id="msg" href="javascript:void(0);">使用短信验证码</a></span></li>
        <li><span>密&nbsp;&nbsp;&nbsp;码：<input style="margin-left:8px;" name="password" type="password" /></span></li>
        <li><span>验证码：&nbsp;&nbsp;<input type="text" id="check" onpaste="return false" maxLength="8" /><input type="text" onclick="createCode()" readonly="readonly" id="checkCode" onselectstart= "return false" /></span></li>
<!--        <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="CheckboxGroup1" value="复选框" id="CheckboxGroup1_0" />记住用户名</li>-->
        <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="login_button" value="登录" onclick="runB1(this.form)" /> </li>
        </ul>
    </div>
  </div>
</div>
<!--快捷方式图标-->
<div id="kjicon">
	    <div class="index-app dark-gray-links clearfix">
      <ul>
              <li><a><img src="../images/app_shoujicz.png"><strong>手机充值</strong></a></li>
              <li><a><img src="../images/app_zuojicz.png"><strong>公共事业缴费</strong></a></li>
              <li><a><img src="../images/app_kuandaicz.png"><strong>宽带充值</strong></a></li>
              <li><a><img src="../images/app_chongzhik.png"><strong>游戏卡</strong></a></li>
              <li><a><img src="../images/app_qbicz.png"><strong>Q币充值</strong></a></li>
              <li><a><img src="../images/app_jiaotongfk.png"><strong>交通罚款</strong></a></li>
      </ul>
    </div>
</div>
<!--页尾 开始-->
<%@ include file="../common/include_footer.jsp"%>
<!--页尾 结束-->
</html:form>
</body>
</html>
