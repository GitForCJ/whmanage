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
			action="/rights/reversal.do?method=addFlowprice">
			<input name="emtype" value="0" type="hidden">
			<div class="pass_main">
				<div class="pass_title_1">
					添加流量产品和价格
				</div>
				<div class="pass_list">
					<ul>
	<li>
							<strong>类型：</strong>
							<span>
								<input type="radio" style="width:20px;height:15px;" value="0" name="tradeTypes" />电信
								&nbsp;&nbsp;
								<input type="radio" style="width:20px;height:15px;" value="1" name="tradeTypes" />移动
								&nbsp;&nbsp;
								<input type="radio" style="width:20px;height:15px;" value="2" name="tradeTypes" checked />联通
								&nbsp;&nbsp;
							</span>
						</li>
						<li>
							<strong>产品名称：</strong><span><input name="user_login"
									type="text" /><font color="red" size="3">*MB</font>
							</span>
						</li>
						<li id="aa">
							<strong>产品价格：</strong>
							<span>
								<input name="reversalcount" type="text" /><font color="red" size="3">*元(最多两位小数)</font>
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
						onClick="javascript:window.location.href='<%=path %>/rights/reversal.do?method=flowlist'" class="field-item_button" />
				</div>
			</div>
		</html:form>
	</body>
</html>