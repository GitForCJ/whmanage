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
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});
function check(){
  	with(document.forms[0]){
		if(userno.value == ""){
		alert("请输入代理商系统编号!");
		userno.focus();
		return;
		}
		if(fundacct.value == ""||fundacct.value.length!=15){
		alert("请输入正确的资金账号!");
		fundacct.focus();
		return;
		}
		target="_self";
			 document.getElementById("btnSubmit").value="请稍后...";
		     document.getElementById("btnSubmit").disabled="disabled";
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
      <li>供货开关</li></ul>
  </div>
</div>
<html:form  method="post" action="/rights/sysuser.do?method=oufeiSwitch">
<input type="hidden" name="user_id" value="${userinfo.user_id }"/>
<input type="hidden" name="userename" value="${requestScope.userinfo.userename }"/>
<input type="hidden" id="mess" value="${requestScope.msg }"/>
<div class="pass_main">
    <div class="pass_list">
    	<ul>
            <li><strong>操&nbsp;作&nbsp;人：</strong><span>${userinfo.username }</span></li>
<li><strong style="color: red;font-size: 15px;">万汇通相关信息:</strong></li>			
	<li><strong>代理商系统编号：</strong><span><input name="userno" maxlength="15" type="text"/></span><font color="#FF0000">  * 输入正确的用户系统编号</font></li>
			<li><strong>代理商资金账号：</strong><span><input name="fundacct" maxlength="15" type="text"/></span><font color="#FF0000">  * 输入用户编号对应的资金账号</font></li> 
	<li>
		<strong>选择绑定类型:</strong>
		<span>	
			<input name="btype" type="radio" checked="checked" value="0" style="border: 0px;width: 30px;height: 15px;"/>话费
			<input name="btype" type="radio" value="1"  style="border: 0px;width: 30px;height: 15px;"/>Q币
		</span>
	</li>      
  </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="提交" id="btnSubmit" onclick="check();" class="field-item_button" />
   </div>

</div>
</html:form>
</body>
</html>