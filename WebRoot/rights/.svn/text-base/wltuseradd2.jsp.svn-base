<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
 <%
 	 String path = request.getContextPath();
 	 String pid=userSession.getUsersite();
 	 String pidName=role.getAreaName(pid);
 	 List areaList = role.getDownAreaList(pid);
 	 request.setAttribute("downlist",areaList);
 %>
 <html>
<head>
<title>万汇通</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script language="javascript" type="text/javascript" src="../js/util.js"></script>
<script type=text/javascript>
showMessage("${mess}");
function check(){
  	with(document.forms[0]){
		if(user_site_city.value == ""){
			alert("请选择用户所属市!");
			return;
		}
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
		if(userpassword.value == ""){
			alert("请输入用户密码!");
			userpassword.focus();
			return;
		}
		if(userpassword.value.split("").length < 8){
			alert("密码至少为8位!");
			userpassword.focus();
			return;
		}
		if(!(/[0-9]+[a-z,A-Z]+/.test(userpassword.value)||/[a-z,A-Z]+[0-9]+/.test(userpassword.value))){
		   alert("登录密码必须为数字加字母组成");
		   userpassword.focus();
		   return;
		 }
	  	if(userpassword2.value == ""){
			alert("请输入确认登陆密码!");
			userpassword2.focus();
			return;
		}
	    if (userpassword.value != userpassword2.value) {
		      alert("两次密码输入不一致");
		      userpassword.focus();
		      return;
	     }
		
		if(userename.value == ""){
			alert("请输入真实姓名!");
			userename.focus();
			return;
		}
		if(!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(usersf.value))
		{
			 alert("身份证输入不合法");
			 usersf.focus();
			 return ;
		}
		if(dealpass.value == ""){
			alert("请输入交易密码!");
			dealpass.focus();
			return;
		}
		if(feepass.value == ""){
			alert("请输入确认交易密码!");
			feepass.focus();
			return;
		}
		if (dealpass.value != feepass.value) {
	      alert("两次密码输入不一致");
	      feepass.focus();
	      return;
	    }
		if(phone.value == ""){
			alert("请输入联系电话!");
			phone.focus();
			return;
		}
		if(phone.value.length!=11){
			alert('手机号码必须为11位！');
			phone.focus();
			return ;
		}
		var myreg = /^(1[3-9]{1}[0-9]{1})\d{8}$/;
		if(!myreg.test(phone.value)){
			alert('请输入合法的手机号码帐号！');
			phone.value="";
			phone.focus();
			return ;
		}
		//验证用户是否存在
		$.post(
	    	$("#rootPath").val()+"/rights/sysuser.do?method=checkName",
	      	{
	      		name :$("#username").val(),
	      		userpassword :$("#userpassword").val()
	      	},
	      	function (data){
	      		if(0 == data){
	      			alert("账号已经存在");
	      			return;
	      		}else if(1 == data){
					action="sysuser.do?method=addUser";
					target="_self";
					document.getElementById("checkCode").value="注册中...";
					document.getElementById("checkCode").disabled="disabled";
					submit();
					return ;
	      		}else{
	      			alert("后台处理异常!");
	      			return ;
	      		}
	      	}
		)
	}
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
<input type="hidden" id="rootPath" value="<%=path %>"/>
<html:form  method="post" action="/rights/sysuser.do?method=addUser">
<input type="hidden" id="stype" name="stype" value="${param.st }"/>
<div class="pass_main">
    <div class="pass_list">
    	<ul>
    		<li><div class="pass_title_1">添加用户</div></li>
  		  	<li id="province">
   	 		<strong>所属省：	</strong>
   	 		<span><%=pidName %>
				<input name="wltarea" id="wltarea"  value="<%=pid %>" readonly="readonly" type="hidden">
            </span>
  		  	</li>
  		  	<li id="user_siteCity">
   	 		<strong>所属市：	</strong>
<select name="user_site_city"  value=""">
<logic:iterate id="ar" name="downlist" indexId="j">
<option value="${ar[0]}" >${ar[1] }</option>
</logic:iterate>
</select>
  		  	</li>
            <li><strong>登陆帐号：</strong><span><input name="username" id="username" type="text" maxlength="11"  onblur="god(this)"/><font color="red">*(系统登录账号,长度必须大于6位,小于11位,字符和数字组成)</font></span></li>
            <li><strong>登陆密码：</strong><span><input name="userpassword" id="userpassword" type="password" /><font color="red">*(密码长度不小于8位，由字母和数字混合组成)</font></span></li>
 			<li><strong>确认登陆密码：</strong><span><input name="userpassword2" type="password" /><font color="red">*</font></span></li>
  		  	<li><strong>交易密码：</strong><span><input name="dealpass" type="password" /><font color="red">*(转款需要该密码)</font></span></li>
			<li><strong>确认交易密码：</strong><span><input name="feepass" type="password" /><font color="red">*</font></span></li>            
			<li><strong>真实姓名：</strong><span><input name="userename" type="text" /><font color="red">*</font></span></li>
			<li><strong>身份证号：</strong><span><input name="usersf" type="text" style="width: 230px;"/><font color="red"></font></span></li>			
			<li><strong>详细地址：</strong><span><input name="address" type="text" /><font color="red">*</font></span></li>
			<li><strong>短信验证手机号：</strong><span><input name="phone" type="text"  maxlength=11 onkeyup="value=value.replace(/[^\d]/g,'')"/><font color="red">*(登录短信验证手机号)</font></span></li>
        </ul>
           <ul>
           
  		  </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   </div>
</div>


<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、一个代理商每天最多增加50个下级代理点。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">2、一个代理商,一共可以拥有1000个下级代理点。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">3、如有疑问请联系管理员。</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</html:form>
</body>
</html>