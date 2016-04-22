<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysLoginUser"/>
 <%
 	 String path = request.getContextPath();
 	 String userId = request.getParameter("uid");
 	 String userName = request.getParameter("uname");
 	 request.setAttribute("userLogin", user.getLoginUserInfo(userName));
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<script language='javascript' src='../js/select.js'></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	$("#macflag").change(function(){
		var _mf = $(this).val();
		if(_mf == '1'){
			$("#mac").val("");
			$("#macAddr").hide();
		}else{
			$("#macAddr").show();
		}
	});
});
function check(){
  	with(document.forms[0]){
		/* areacope为区域区号，也即体系层次表中的节点区号 */
		if(username.value == ""){
		alert("请输入用户名称!");
		return;
		}
		if(userpassword.value == ""){
		alert("请输入用户密码!");
		return;
		}
		action="sysloginuser.do?method=add";
		target="_self";
		submit();
	}
}
</SCRIPT>
</head>
<body>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>安全登录选择</li></ul>
  </div>
</div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<html:form  method="post" action="/rights/sysloginuser.do?method=add">
<input type="hidden" name="user_id" value="<%=userId %>"/>
<div class="pass_main">
	<div class="pass_title_1">登陆账号详细</div>
    <div class="pass_list">
    	<ul>
            <li><strong>帐号：</strong><span>${userLogin.username }</span></li>
			<li><strong>短信验证手机号：</strong><span>${userLogin.phone }</span></li>
			<li><strong>状态：</strong><span>
				<select name="user_state" id="user_state" disabled="disabled">
					<option value="0"  <c:if test="${userLogin.user_state == 0 }">selected</c:if>>正常</option>
					<option value="1"   <c:if test="${userLogin.user_state == 1 }">selected</c:if>>禁用</option>
				</select>
			</span></li>
			<li><strong>资金账户转款短信是否验证：</strong><span>
				<select name="feeshortflag" id="feeshortflag" disabled="disabled">
					<option value="0"  <c:if test="${userLogin.feeshortflag == 0 }">selected</c:if>>验证</option>
					<option value="1"  <c:if test="${userLogin.feeshortflag == 1 }">selected</c:if>>不验证</option>
				</select>
			</span></li>
			<li><strong>Mac地址是否验证：</strong><span>
				<select name="macflag" id="macflag" disabled="disabled">
					<option value="0"   <c:if test="${userLogin.macflag == 0 }">selected</c:if>>验证</option>
					<option value="1" <c:if test="${userLogin.macflag == 1 }">selected</c:if>>不验证</option>
				</select>
			</span></li>
			<li id="macAddr"><strong>Mac地址：</strong><span>${userLogin.mac }</span></li>
			 <li><strong>登陆密码是否验证：</strong><span>
				<select name="passflag" id="passflag" disabled="disabled">
					<option value="0"   <c:if test="${userLogin.passflag == 0 }">selected</c:if>>验证</option>
					<option value="1" <c:if test="${userLogin.passflag == 1 }">selected</c:if>>不验证</option>
				</select>
			</span></li>
			<li><strong>登录短信是否验证：</strong><span>
				<select name="shortflag" id="shortflag" disabled="disabled">
					<option value="0"  <c:if test="${userLogin.passflag == 0 }">selected</c:if>>验证</option>
					<option value="1"  <c:if test="${userLogin.passflag == 1 }">selected</c:if>>不验证</option>
				</select>
			</span></li>
        </ul>
           <ul>
           
  		  </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="返回" id="checkCode" onclick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>