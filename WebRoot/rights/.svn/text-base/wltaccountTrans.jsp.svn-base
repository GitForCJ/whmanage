<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
 <%
 	 String path = request.getContextPath();
	 String userid=request.getParameter("uid");
   	 request.setAttribute("userinfo", user.getUserAccount(userid));
   	 request.setAttribute("accountlist", user.getUserAccountList());
 %>
 <html>
<head>
<title>万汇通</title>
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(function(){
	if($("#mess").val() != ''){
		alert($("#mess").val());
	}
});
function submitForm(){
  	with(document.forms[0]){
		submit();
	}
}
function send(){
	$.post(
	    	$("#rootPath").val()+"/rights/sysuser.do?method=sendMsgAdminTrans",
	      	{
	      		user_id : $("#userId").val()
	      	},
	      	function (data){
	      		if(data == 0){
	      			alert("短信发送成功");
	      		}else{
	      			alert("短信发送失败");
	      		}
	      	}
		)
}
</script>
</head>
<body>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" id="feeshortflag" value="${userinfo.feeshortflag }"/>
<form  method="post" action="<%=path %>/rights/sysuser.do?method=trans">
<input type="hidden" name="user_id" id="userId" value="<%=userid %>"/>
<div class="pass_main">
	<div class="pass_title_1">资金账户转款</div>
    <div class="pass_list">
    	<ul>
            <li><strong>账户金额：</strong><span>${userinfo.accountAmount/100 }元</span></li>
            <li><strong>短信验证码：</strong><span><input name="inputMsgCode" type="text"/><a href="javascript:send();">发送验证码</a></span></li>
            <li><strong>转款金额：</strong><span><input name="chargeFee" type="text"/>元</span></li>
            <li><strong>转款账户：</strong><span>
			<input type="text" name="username" id="username">
			</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="转款" id="btnSubmit" onclick="submitForm();" class="field-item_button" />
   <input type="button" value="返回" id="checkCode" onclick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</form>
</body>
</html>