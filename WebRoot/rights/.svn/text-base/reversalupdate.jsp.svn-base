<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
<html>
	<head>
		<title>万汇通</title>
		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<SCRIPT type=text/javascript>
jQuery(function(){
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});
function check(){
  	with(document.forms[0]){
		var userlogin=user_login.value;
		var count=reversalcount.value;
		if(userlogin == ""){
			alert("请输入登陆账号 !");
			user_login.focus();
			return false;
		}
		if(count == ""){
			alert("请输入数量 !");
			reversalcount.focus();
			return false;
		}
     	var pattern=/^[0-9]*[0-9][0-9]*$/;
     	if(!pattern.test(count)){
			alert("冲正次数格式不正确 !");
			reversalcount.focus();
			return false;
		}
	    if(confirm("确认如此分配?")){
		    document.getElementById("checkCode").value="请稍后...";
			document.getElementById("checkCode").disabled="disabled";
			target="_self";
			submit();
		}else{
			return false;
		}
	}
}
</SCRIPT>
	</head>
	<body>
		<div id="popdiv">
			<span id="sellist"></span>
			<div style="clear: both"></div>
		</div>
		<input type="hidden" id="mess" value="${requestScope.mess }" />
		<html:form method="post"
			action="/rights/reversal.do?method=updateReversal">
			<input name="emtype" value="0" type="hidden">
			<div class="pass_main">
				<div class="pass_title_1">
					修改冲正次数
				</div>
				<div class="pass_list">
					<ul>
						<li>
							<strong>登陆账号：</strong><span><input name="user_login"
									type="text" value="${str[0]}" readonly="readonly" /><font
								color="red" size="3">*</font>
							</span>
						</li>
						<c:if test="${str[3]==3 || str[3]==4}">
							<li>
								<strong>权限：</strong>
								<span>
									<select name="reversalcount" style="width:120px;height:23px;background-color:white;">
										<option value="0">禁用</option>
										<option value="1">启用</option>
									</select>
								</span>
							</li>
						</c:if>
						<c:if test="${str[3]!=3 && str[3]!=4}">
							<li>
								<strong>数量：</strong><span><input name="reversalcount"
										type="text" value="${str[2]}" /><font color="red" size="3">*</font>
								</span>
							</li>
						</c:if>
					</ul>
				</div>
				<div class="field-item_button_m">
					<input type="button" value="确认" id="checkCode" onclick="check()"
						class="field-item_button" />
					<input type="button" value="取消" id="checkCode1"
						onClick="javascript:history.go(-1)" class="field-item_button" />
				</div>
			</div>
			<input type="hidden" value="${str[4]}" name="id"/>
		</html:form>
	</body>
</html>