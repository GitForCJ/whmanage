<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm" />
	<%
  String path = request.getContextPath();
%>
<html>
	<head>
		<title>万汇通</title>
		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="../js/xml_cascade.js"></script>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<link href="../css/selectCss.css" rel="stylesheet" type="text/css" />
		<script src="../js/selectCss.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
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
			alert("格式不正确 !");
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

function change(className)
{
	var val=className.value;
	if(val=="3")
	{
		$("#aa").html("<strong>权限：</strong><span><select name='reversalcount' style='width:120px;height:23px;background-color:white;' ><option value='1'>启用</option><option value='0'>禁用</option></select></span>");
		return ;
	}
	else
	{
		$("#aa").html("<strong>数量：</strong><span><input name='reversalcount' type='text' /><font color='red' size='3'></font></span>");
		return ;
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
			action="/rights/reversal.do?method=addReversal">
			<input name="emtype" value="0" type="hidden">
			<div class="pass_main">
				<div class="pass_title_1">
					添加冲正次数
				</div>
				<div class="pass_list">
					<ul>
						<li>
							<strong>登陆账号：</strong><span><input name="user_login"
									type="text" /><font color="red" size="3"></font>
							</span>
						</li>
						<li>
							<strong>类型：</strong>
							<span>
								<input type="radio" style="width:20px;height:15px;" value="0" name="tradeTypes" checked onchange="change(this);"/>冲正
								&nbsp;&nbsp;
								<input type="radio" style="width:20px;height:15px;" value="1" name="tradeTypes"  onchange="change(this);"/>Q币
								&nbsp;&nbsp;
								<input type="radio" style="width:20px;height:15px;" value="2" name="tradeTypes"  onchange="change(this);"/>支付宝
								&nbsp;&nbsp;
								<input type="radio" style="width:20px;height:15px;" value="3" name="tradeTypes" onchange="change(this);" />额度转移
								
							</span>
						</li>
						<li id="aa">
							<strong>数量：</strong>
							<span>
								<input name="reversalcount" type="text" /><font color="red" size="3"></font>
							</span>
						</li>
						<li>
							
						</li>
					</ul>
				</div>
				<div class="field-item_button_m">
					<input type="button" value="确认" id="checkCode" onclick="check()"
						class="field-item_button" />
					<input type="button" value="返回" id="checkCode1"
						onClick="javascript:window.location.href='<%=path %>/rights/reversal.do?method=list'" class="field-item_button" />
				</div>
			</div>
		</html:form>
	</body>
</html>