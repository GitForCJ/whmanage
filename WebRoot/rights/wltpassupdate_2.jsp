<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysLoginUser"/>
 <%
 	 String path = request.getContextPath();
 	 String userId = userSession.getUser_id();
 	 String username=userSession.getUsername();
 %>
 <html>
<head>
<title>���ͨ</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type=text/javascript>
$(function(){
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
	$("#checkCode").click(function(){
		if($("#userpassword").val() == ""){
			alert("������ԭʼ��������");
			return;
		}
		$.post(
	    	$("#rootPath").val()+"/rights/sysloginuser.do?method=checkPassWord",
	      	{
	      		password : $("#userpassword").val(),
	      		userid   : $("#user_id").val(),
	      		username : $("#username").val()
	      	},
	      	function (data){
	      		if(data == 0){
	      			if($("#newpass").val() == ""){
	      				alert("�����벻��Ϊ��");
	      				return;
	      			}
	      			if($("#newpass").val() != $("#confirmpass").val()){
	      				alert("�������벻һ��");
	      				return;
	      			}
	      			$("#form1").submit();
	      		}else{
	      			alert("ԭʼ���������������");
	      			return;
	      		}
	      	}
		)
	});
});
</script>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<form  method="post" id="form1" action="<%=path %>/rights/sysloginuser.do?method=updatePassWord">
<input type="hidden" id="user_id" name="user_id" value="<%=userId %>"/>
<input type="hidden" id="mess" value="${requestScope.msg }"/>
<div class="pass_main">
    <div class="pass_list">
    	<ul>
            <li><strong>��½�ʺţ�</strong><span><%=username %></span></li>
            <li><strong>ԭʼ�������룺</strong><span><input id="userpassword" name="userpassword" type="password"/></span></li>
  		  	<li><strong>�½������룺</strong><span><input id="newpass" name="newpass" type="password"/></span></li>
			<li><strong>ȷ�Ͻ������룺</strong><span><input id="confirmpass" name="confirmpass" type="password"/></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="ȷ��" id="checkCode" onclick="check()" class="field-item_button" />
   </div>
</div>
</form>
</body>
</html>