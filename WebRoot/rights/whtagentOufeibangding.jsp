<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="session"
	class="com.wlt.webm.rights.bean.SysUser" />
<%
	String path = request.getContextPath();
	request.setAttribute("userinfo", userSession);
	
	request.setAttribute("arrylist",user.OuFeiTypeList(userSession.getUserno()));
%>
<html>
	<head>
		<title>万汇通</title>
		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
		<link href="../css/selectCss.css" rel="stylesheet" type="text/css" />
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
		<SCRIPT type=text/javascript>
jQuery(function(){
	//$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});
function check(){
  	with(document.forms[0]){
		if(userno.value == ""){
		alert("请输入用户编号!");
		userno.focus();
		return;
		}
		if(fundacct.value == ""||fundacct.value.length!=15){
		alert("请输入正确的资金账号!");
		fundacct.focus();
		return;
		}
		
		if(userpassword.value == ""){
		alert("请输入资金账号!");
		userpassword.focus();
		return;
		}
				if(oufeiID.value == ""){
		alert("请输合作商代码!");
		fundacct.focus();
		return;
		}
				if(oufeiMoban.value == ""){
		alert("模板编号!");
		fundacct.focus();
		return;
		}
				if(oufeiSign.value == ""){
		alert("请输入秘钥!");
		fundacct.focus();
		return;
		}
		action="sysuser.do?method=addOufei";
		target="_self";
		method="post"
		submit();
	}
}
</SCRIPT>
	</head>
	<body>
		<div id="popdiv">
			<span id="sellist"></span>
			<div style="clear: both"></div>
		</div>
		<div id="tabbox">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						供货绑定
					</li>
				</ul>
			</div>
		</div>
		<html:form method="post" action="/rights/sysuser.do?method=addOufei">
			<input type="hidden" name="user_id" value="${userinfo.user_id }" />
			<input type="hidden" name="userename"
				value="${requestScope.userinfo.userename }" />
			<input type="hidden" id="mess" value="${requestScope.msg }" />
			<div class="pass_main">
				<div class="pass_list">
					<ul>
						<li>
							<strong>操&nbsp;作&nbsp;人：</strong><span>${userinfo.username
								}</span>
						</li>
						<li>
							<strong style="color: red; font-size: 15px;">万汇通相关信息:</strong>
						</li>
						<li>
							<strong>代办点系统编号：</strong><span><input name="userno"
									maxlength="15" type="text" />
							</span><font color="#FF0000"> * 输入正确的用户系统编号</font>
						</li>
						<li>
							<strong>代办点资金账号：</strong><span><input name="fundacct"
									maxlength="15" type="text" />
							</span><font color="#FF0000"> * 输入用户编号对应的资金账号</font>
						</li>
						<li>
							<strong>登录密码:：</strong><span><input name="userpassword"
									type="password" />
							</span><font color="#FF0000"> * 输入用户的登录密码</font>
						</li>
						<li>
							<strong style="color: red; font-size: 15px;">第三方供货信息:</strong>
						</li>
						<li>
							<strong>合作商代码：</strong><span><input name="oufeiID"
									maxlength="20" type="text" />
							</span>
						</li>
						<li>
							<strong>模板编号：</strong><span><input name="oufeiMoban"
									maxlength="30" type="text" />
							</span>
						</li>
						<li>
							<strong>秘钥：</strong><span><input name="oufeiSign"
									maxlength="100" type="text" style="width: 300px;" />
							</span>
						</li>
					    <li>
					    	<strong>选择绑定类型:</strong>
							<span>	
									<c:forEach items="${arrylist}" var="arry" varStatus="index">
										<c:if test="${arry==0}">
											<input name="btype" type="radio" <c:if test="${index.index==0}">checked="checked"</c:if> value="0" style="border: 0px;width: 30px;height:15px;"/>话费
										</c:if>
										<c:if test="${arry==1}">
											<input name="btype" type="radio"  <c:if test="${index.index==0}">checked="checked"</c:if> value="1" style="border: 0px;width: 30px;height:15px;"/>Q币
										</c:if>
									</c:forEach>
							</span>
						</li>
					</ul>
				</div>
				<div class="field-item_button_m">
					<input type="button" value="提交" id="btnSubmit" onclick="check();"
						class="field-item_button" />
				</div>

			</div>
		</html:form>
	</body>
</html>