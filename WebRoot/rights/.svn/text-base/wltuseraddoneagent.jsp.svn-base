<%@ page contentType="text/html; charset=gbk" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
 <%
 	 String path = request.getContextPath();
	 String userrole=userSession.getUser_role();
	 //List areaList = role.getAreaList();
	 List areaList = role.getAreaList(userSession.getRoleType(),userSession.getUsersite());
	 StringBuffer sBuffer = new StringBuffer();
	 sBuffer.append("请选择[]");
	 for(Object tmp : areaList){
		String[] temp = (String[])tmp;
		sBuffer.append("|"+temp[1]+"["+temp[0]+"]");
	 }
	 
	// List rolelist = role.getRoleType(userSession.getRoleType());
	 StringBuffer sBuffer1 = new StringBuffer();
	 sBuffer1.append("请选择[]|代理商[2]");
	// for(Object tmp : rolelist){
	//	String[] temp = (String[])tmp;
	//	sBuffer1.append("|"+temp[1]+"["+temp[0]+"]");
	// }
 	 request.setAttribute("areaSel", sBuffer.toString());
   	 request.setAttribute("roleSel", sBuffer1.toString());
 %>
 <html>
<head>
<title>万汇通</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk">
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"charset="gbk"><//script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
	var _stype = $("#stype").val();
	if(_stype != ''){
		if('1' == _stype){
			$("#rolecheck").attr("defaultsel","管理员[1]");
			$("#sl-rolecheck").text("管理员");
		}else if('2' == _stype){
			$("#rolecheck").attr("defaultsel","代理商[2]");
			$("#sl-rolecheck").text("代理商");
		}else if('3' == _stype){
			$("#rolecheck").attr("defaultsel","代办点[3]");
			$("#sl-rolecheck").text("代办点");
		}else if('4' == _stype){
			$("#rolecheck").attr("defaultsel","接口代理商[4]");
			$("#sl-rolecheck").text("接口代理商");
		}
		$("#rolecheck").val(_stype);
	}
})
function check(){
  	with(document.forms[0]){
		/* areacope为区域区号，也即体系层次表中的节点区号 */
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
		if(rolecheck.value == ""){
			alert("请选择用户类型!");
			rolecheck.focus();
			return;
		}
		//if($("#rolecheck").val() != 1){
		if(wltarea.value == ""){
			alert("请选择用户所属省!");
			return;
		}
		if(user_site_city.value == ""){
			alert("请选择用户所属市!");
			return;
		}
		//}
		if(user_role.value == ""){
			alert("请选择角色!");
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
		if(operatename.value == ""){
			alert("请输入开户人姓名!");
			operatename.focus();
			return;
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
					action="sysuser.do?method=addOneAgentUser";
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
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<html:form  method="post" action="/rights/sysuser.do?method=addOneAgentUser">
<input type="hidden" id="stype" name="stype" value="${param.st }"/>
<div class="pass_main">
    <div class="pass_list">
    	<ul>
    		<li><div class="pass_title_1">添加用户</div></li>
            <li>
	            <strong>帐号：</strong>
	            <span style="display:inline;width:400px;">
	            	<input name="username" id="username" type="text" maxlength="11" onblur="god(this)" style="float:left;"/>
	            	<div style="float:left;">
	            		<font color="red" >
	            			*(系统登录账号,长度必须大于6位,小于11位,字符和数字组成)
	            		</font>
            		</div>
	            </span>
            </li>
            <li><strong>登陆密码：</strong><span><input name="userpassword" id="userpassword" type="password" /><font color="red">*(密码长度不小于8位，由字母和数字混合组成)</font></span></li>
 			<li><strong>确认登陆密码：</strong><span><input name="userpassword2" type="password" /><font color="red">*</font></span></li>
            <li><div class="pass_title_1">添加用户</div></li>
            <li>
   	 		<strong>用户类型：	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-rolecheck" href="javascript:void(0);">请选择</a></div>
				<input name="rolecheck" id="rolecheck" defaultsel="${requestScope.roleSel }" type="hidden" value="" />
            </span><font color="red">*</font>
  		  	</li>
  		  	<li id="province">
   	 		<strong>所属省：	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-wltarea" href="javascript:void(0);">请选择</a></div>
				<input name="wltarea" id="wltarea" defaultsel="${requestScope.areaSel }" type="hidden" value="" />
            </span><font color="red">*</font>
  		  	</li>
  		  	<li id="user_siteCity">
   	 		<strong>用户所属市：	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-user_site_city" href="javascript:void(0);">请选择</a></div>
				<input name="user_site_city" id="user_site_city" defaultsel="" type="hidden" value="" />
            </span><font color="red">*</font>
  		  	</li>
  		  	<li id="city" style="display:none">
   	 		<strong>角色所属市：	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-areaid" href="javascript:void(0);">请选择</a></div>
				<input name="areaid" id="areaid" defaultsel="" type="hidden" value="" />
            </span>
  		  	</li>
  		  	<li id="urole">
   	 		<strong>角色：	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-user_role" href="javascript:void(0);">请选择</a></div>
				<input name="user_role" id="user_role" defaultsel="" type="hidden" value="" />
            </span><font color="red">*</font>
  		  	</li>
			<li><strong>真实姓名：</strong><span><input name="userename" type="text" /><font color="red">*</font></span></li>
			<li><strong>身份证号：</strong><span><input name="usersf" type="text" style="width: 230px;"/><font color="red"></font></span></li>
			<li><strong>详细地址：</strong><span><input name="address" type="text" /><font color="red">*</font></span></li>
  		  	<li><strong>交易密码：</strong><span><input name="dealpass" type="password" /><font color="red">*(转款需要该密码)</font></span></li>
			<li><strong>确认交易密码：</strong><span><input name="feepass" type="password" /><font color="red">*</font></span></li>
			<li><strong>客户经理:</strong><span><input name="operatename" type="text" /><font color="red">*</font></span></li>
			<li><strong>短信验证手机号：</strong><span><input name="phone" type="text"  maxlength=11 onkeyup="value=value.replace(/[^\d]/g,'')"/><font color="red">*(登录短信验证手机号)</font></span></li>
        </ul>
           <ul>
           
  		  </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>