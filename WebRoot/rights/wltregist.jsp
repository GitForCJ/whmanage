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
	 String userrole=userSession.getUser_role();
	 List areaList = role.getAreaList();
	 StringBuffer sBuffer = new StringBuffer();
	 sBuffer.append("请选择[]");
	 for(Object tmp : areaList){
		String[] temp = (String[])tmp;
		sBuffer.append("|"+temp[1]+"["+temp[0]+"]");
	 }
	 
	 List rolelist = role.getRoleType(userrole);
	 StringBuffer sBuffer1 = new StringBuffer();
	 sBuffer1.append("请选择[]");
	 for(Object tmp : rolelist){
		String[] temp = (String[])tmp;
		sBuffer1.append("|"+temp[1]+"["+temp[0]+"]");
	 }
 	 request.setAttribute("areaSel", sBuffer.toString());
   	 request.setAttribute("roleSel", sBuffer1.toString());
 %>
 <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script type="text/javascript" src="../js/util.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type=text/javascript>
showMessage("${mess}");
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	
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
	$("#username").keyup(function(){
		$(this).val($(this).val().replace(/\D/g,''));
	})
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
		if(wltarea.value == ""){
			alert("请选择用户所属省!");
			wltarea.focus();
			return;
		}
		if(user_site_city.value == ""){
			alert("请选择用户所属市!");
			user_site_city.focus();
			return;
		}
		if(userename.value == ""){
			alert("请输入用户姓名!");
			userename.focus();
			return;
		}
		if(address.value == ""){
			alert("请输入详细地址!");
			address.focus();
			return;
		}
		if(dealpass.value == ""){
			alert("请输入交易密码!");
			dealpass.focus();
			return;
		}
		if(dealpass.value.split("").length < 6){
			alert("交易密码至少为6位!");
			dealpass.focus();
			return;
		}
		if(!(/[0-9]+[a-z,A-Z]+/.test(dealpass.value)||/[a-z,A-Z]+[0-9]+/.test(dealpass.value))){
		    alert("交易密码必须为数字加字母组成");
		    dealpass.focus();
		    return;
	    }
		if(dealpass2.value == ""){
			alert("请输入确认交易密码!");
			dealpass2.focus();
			return;
		}
	  if (dealpass.value != dealpass2.value) {
	      alert("两次密码输入不一致");
	      dealpass2.focus();
	      return;
	    }
    	if(phone.value == ""){
			alert("请输入短信验证手机号!");
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
	      		name :$("#username").val()
	      	},
	      	function (data){
	      		if(0 == data){
	      			alert("账号已经存在");
	      			return;
	      		}else{
					action="sysuser.do?method=regist";
					target="_self";
					submit();
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
<input type="hidden" id="stype" value="${param.st }"/>
<html:form  method="post" action="/rights/sysuser.do?method=regist">
<div class="pass_main">
    <div class="pass_list">
    	<ul>
    		<li><div class="pass_title_1">用户注册</div></li>
            <li><strong>帐号：</strong><span><input name="username" type="text" id="username" maxlength="11"  onblur="god(this)"/><font color="red">*(系统登录账号,长度必须大于6位,小于11位,字符和数字组成)</font></span></li>
            <li><strong>登陆密码：</strong><span><input name="userpassword" type="text" /><font color="red">*(密码长度不小于8位，由字母和数字混合组成)</font></span></li>
		    <li><strong>确认登陆密码：</strong><span><input name="userpassword2" type="text" /><font color="red">*</font></span></li>
            <li style="display:none"><div class="pass_title_1"></div></li>
            <li style="display:none">
   	 		<strong>用户类型：	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-rolecheck" href="javascript:void(0);">代办点</a></div>
				<input name="rolecheck" id="rolecheck" defaultsel="代办点[3]" type="hidden" value="3" />
            </span><font color="red">*</font>
  		  	</li>
  		  	<li id="province">
   	 		<strong>所属省：	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-wltarea_regist" href="javascript:void(0);">请选择</a></div>
				<input name="wltarea" id="wltarea_regist" defaultsel="${requestScope.areaSel }" type="hidden" value="" />
            </span><font color="red">*</font>
  		  	</li>
  		  	<li id="user_siteCity">
   	 		<strong>用户所属市：	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-user_site_city" href="javascript:void(0);">请选择</a></div>
				<input name="user_site_city" id="user_site_city" defaultsel="" type="hidden" value="" />
            </span><font color="red">*</font>
  		  	</li>



  		  	<li style="display:none"><div class="pass_title_1"></div></li>
<!--  		  	<li id="city">-->
<!--   	 		<strong>角色所属市：	</strong>-->
<!--   	 		<span>-->
<!--				<div class="sub-input"><a class="sia-2 selhover" id="sl-areaid_regist" href="javascript:void(0);">请选择</a></div>-->
<!--				<input name="areaid_regist" id="areaid_regist" defaultsel="" type="hidden" value="" />-->
<!--            </span>-->
<!--  		  	</li>-->
  		  	<li id="urole" style="display:none">
   	 		<strong>角色：	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-user_role" href="javascript:void(0);">请选择</a></div>
				<input name="user_role" id="user_role" defaultsel="" type="hidden" value="" />
            </span>
  		  	</li>
  		  	<li id="ucommission" style="display:none">
   	 		<strong>佣金组：	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-user_comm" href="javascript:void(0);">请选择</a></div>
				<input name="user_comm" id="user_comm" defaultsel="" type="hidden" value="" />
            </span>
  		  	</li>



			<li><strong>用户姓名：</strong><span><input name="userename" type="text" /><font color="red">*</font></span></li>
			<li><strong>详细地址：</strong><span><input name="address" type="text" /><font color="red">*</font></span></li>
  		  	<li><strong>交易密码：</strong><span><input name="dealpass" type="text" /><font color="red">*(转款需要该密码)</font></span></li>
			<li><strong>确认交易密码：</strong><span><input name="dealpass2" type="text" /><font color="red">*</font></span></li>
			<li style="display:none"><strong>转款密码：</strong><span><input name="feepass" type="text" /></span></li>
			<li><strong>短信验证手机号：</strong><span><input name="phone" type="text"  maxlength=11 onkeyup="value=value.replace(/[^\d]/g,'')" /><font color="red">*(短信验证手机号)</font></span></li>
			<li style="display:none"><strong>资金账户转款短信是否验证：</strong><span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-feeshortflag" href="javascript:void(0);">请选择</a></div>
				<input name="feeshortflag" id="feeshortflag" defaultsel="请选择[]|验证[0]|不验证[1]" type="hidden" value="0" />
			</span><font color="red">*</font></li>
			<li style="display:none"><strong>Mac地址是否验证：</strong><span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-macflag" href="javascript:void(0);">不验证</a></div>
				<input name="macflag" id="macflag" defaultsel="不验证[1]" type="hidden" value="1" />
			</span><font color="red">*</font></li>
			 <li style="display:none"><strong>登陆密码是否验证：</strong><span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-passflag" href="javascript:void(0);">验证</a></div>
				<input name="passflag" id="passflag" defaultsel="验证[0]|不验证[1]" type="hidden" value="0" />
			</span></li>
			<li style="display:none"><strong>登录短信是否验证：</strong><span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-shortflag" href="javascript:void(0);">验证</a></div>
				<input name="shortflag" id="shortflag" defaultsel="验证[0]|不验证[1]" type="hidden" value="1" />
			</span></li>
        </ul>
           <ul>
           
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