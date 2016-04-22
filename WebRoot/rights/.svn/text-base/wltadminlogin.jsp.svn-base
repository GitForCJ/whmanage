<%@ page contentType="text/html; charset=utf-8" session="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
String path = request.getContextPath();
System.out.println(path);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>万汇通欢迎您</title>
<link href="css/style.css" type=text/css rel=stylesheet ></link>
<script src="js/jquery.min.js" type="text/javascript"></script>
<script src="js/jquery.jslider.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/des.js"></script>
<script type="text/javascript" src="../js/check.js"></script>
<script type="text/javascript">
    if (top.location != self.location){  
    top.location=self.location;  
    }  
function refresh()
{
	document.getElementById("authImg").src="<%=path %>/authImg?now="+new Date();
}
      
	function runB1(form){
		var checkCode = document.getElementById("check").value.toUpperCase();
		if(checkCode.length==""||checkCode.length==0){
			alert("请输入验证码");
			document.getElementById("check").focus();
			return;
		}
		var name = navigator.appName;
		var version = navigator.appVersion;
		if (!check())return;
		document.getElementById("userpassword").value=strEnc(document.getElementById("userpassword").value,'<%=application.getAttribute("key")%>');
        var date = new Date((new Date()).getTime() + 30 * 24 * 60 * 60 * 1000);
        document.cookie = "login=" + escape(document.getElementById("username").value) + "; expires=" + date.toGMTString();
		document.forms[0].submit();
		return;
	}
	function check() {
		CT=document.getElementById("username");
		CTV=CT.value;
		if(CTV == ""){
			validatePrompt (CT,"登录名不能为空。");
			return (false);
		}
		CT=document.getElementById("userpassword");
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
	    alert(PromptStr);
		Ct.focus();
		return;
	}
	function logFocus(){
		refresh();
	
		var form = document.forms[0];
		var name = navigator.appName;
		var version = navigator.appVersion;
		if(name != "Microsoft Internet Explorer" ){
			//alert("建议使用ie系列浏览器");
			return;
		}
        var cookies = document.cookie.split(";");
        for (var i = 0; i < cookies.length; i++)
        {
          var cookie = cookies[i].split("=");
          if (cookie[0].trim() == "login") {
           if(form.login){
           	 form.login.value = unescape(cookie[1]);
            }
          }
        }
        if (form.username.value.length == 0) {
           	if(form.login){
          		form.login.focus();
          }
        } else {
         if(form.password){
          	form.password.focus();
          }
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


</script>
</head>

<body onLoad="createCode();logFocus()">
<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" id="mess" value="${requestScope.msg }"/>
<html:form  method="post" action="/rights/sysuser.do?method=login" >
<div class="header">
<div class="content">
<div class="logo"></div>
<div class="tel"></div>
</div>
</div>

<div class="swap">
<div class="login">
<div class="login_in">
<div class="login_bg">
<div class="loginFuncNormal" >用户登录 </div>
<div class="login_field">
   <input type="text" name="username" id="username"  value="用户账号" onfocus="if (value =='用户账号'){value =''}" onblur="if (value ==''){value='用户账号'}"  />
             </div>

<div class="login_field">
  <input type="password" value="账号密码" name="userpassword" id="userpassword" onfocus="if(this.value==defaultValue) {this.value='';}" onblur="if(!value) {value=defaultValue;}"  />
             </div>
             
<div class="login_field1">
   <input type="text" id="check" name="vercode" onpaste="return false" value="" onfocus="if (value ==''){value =''}" onblur="if (value ==''){value=''}" /> 
             </div>
             <div class="login_ver">
						<img alt="" onclick="refresh()" src="" id="authImg" style="margin-top:10px;" align="absmiddle"/>
</div>

<div class="login_field2">
   <input type="button" value="安全登录" onclick="runB1(this.form)"/>
             </div>
             <div  class="login_ver" id="ddd">
		<a href="password.jsp" style="line-height:50px;font-size:14px;">忘记密码?</a>
	</div>

</div></div></div>
	<ul id="slider">
  <li style="background:url(images/ad1.jpg) center center no-repeat #ffffff;" ></li>
		<li style="background:url(images/ad2.jpg) center center no-repeat #ffffff;"></li>
        <li style="background:url(images/ad3.jpg) center center no-repeat #ffffff;"></li>
        <li style="background:url(images/ad4.jpg) center center no-repeat #ffffff;"></li>
		<li style="background:url(images/ad5.jpg) center center no-repeat #ffffff;">
		</li>
	</ul>
</div>
<ul id="naviSlider">
	<li sindex="1" class="on"></li>
	<li sindex="2"></li>
	<li sindex="3"></li>
    <li sindex="4"></li>
	<li sindex="5"></li>
</ul>

<script type="text/javascript">
$(document).ready(function(){
	$("#slider").jSlider({
			pause:4000,
			naviSlider:'naviSlider'
		}
	);
	document.onkeydown = function(e){ 
    var ev = document.all ? window.event : e;
    if(ev.keyCode==13) {
    	runB1('form');
     	}
	}			
})
</script>
<div  class="footer">
<p>招商加盟:4006-220-216&nbsp;&nbsp;|&nbsp;&nbsp;<a href="xieyi.html" target="_blank">服务协议</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="http://www.wanhengtech.com" target="_blank">关于我们</a>&nbsp;&nbsp;|&nbsp;&nbsp; 粤ICP备11104967-5  </p>
<p>Copyright© 2013-2015 wanhengtech, All Rights Reserved</p>
<p>深圳市万恒科技有限公司&nbsp;&nbsp;版权所有</p>
</div>
</html:form>
</body>
</html>