<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
 	 String path = request.getContextPath();
 %>
 <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>���ͨ</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript" src="../js/util.js"></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type=text/javascript>
showMessage("${mess}");
function check(){
  	with(document.forms[0]){
		if(username.value == ""){
			alert("�������ʺ�!");
			username.focus();
			return;
		}
		if(username.value.length<6 || username.value.length>11){
			alert("�ʺų���Ϊ6��11λ!");
			username.focus();
			return;
		}
		var t=/^[A-Za-z0-9]+$/;
		if(!t.test(username.value)){
			alert('�ʺ�ֻ�������ֺ��ַ�!');
			username.focus();
			return ;
		}
		if(phone.value == ""){
			alert("��������ն��ŵĺ���!");
			phone.focus();
			return;
		}
		if(!isMobileNO(phone)){
			return;
		}
		document.getElementById("checkCode").disable=false;
		submit();
		}
		}
	//��֤�ֻ�����
function isMobileNO(obj){
	var objValue = obj.value;
	if(typeof(objValue)=="undefined")
		return false;
	if(objValue=="" || objValue==null){ 
		return false;
	} 
	if(objValue.length!=11){
		alert('�˺�Ϊ11λ�ֻ����룡');
		return false;
	}
	var myreg = /^(1[3-9]{1}[0-9]{1})\d{8}$/;
	if(!myreg.test(objValue)){
		alert('������Ϸ����ֻ������ʺţ�');
		obj.value="";
		obj.focus();
		return false;
	}
	return true;
}

function  god(className) {
  if(className.value.length>=1 && className.value.length<6){
  	alert("��¼�ʺų��Ȳ���!");
  	return ;
  }
}
</script>
</head>

<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<br><input type="hidden" id="rootPath" value="<%=path %>"/>
<html:form  method="post" action="/rights/sysuser.do?method=updatePwd">
<div class="pass_main">
    <div class="pass_list">
    	<ul>
    		<li><div class="pass_title_1">�û���������</div></li>
            <li><strong>��½�ʺţ�</strong><span><input name="username" type="text" id="username"  maxlength="11"  onblur="god(this)"/><font color="red">*(����д���ĵ�½�˺�)</font></span></li>
			<li><strong>���ն����ֻ��ţ�</strong><span><input name="phone" type="text"  maxlength="11" id="phone" /><font color="red">*(���ڽ������ú������)</font></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="ȷ��" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="����" id="checkCode" onclick="location.href='<%=path %>/rights/wltlogin.jsp'" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>