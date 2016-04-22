<%@ page contentType="text/html; charset=gbk" language="java"%>
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
	 //List areaList = role.getAreaList();
	 List areaList = role.getAreaList(userSession.getRoleType(),userSession.getUsersite());
	 StringBuffer sBuffer = new StringBuffer();
	 sBuffer.append("��ѡ��[]");
	 for(Object tmp : areaList){
		String[] temp = (String[])tmp;
		sBuffer.append("|"+temp[1]+"["+temp[0]+"]");
	 }
	 
	// List rolelist = role.getRoleType(userSession.getRoleType());
	 StringBuffer sBuffer1 = new StringBuffer();
	 sBuffer1.append("��ѡ��[]|������[2]");
	// for(Object tmp : rolelist){
	//	String[] temp = (String[])tmp;
	//	sBuffer1.append("|"+temp[1]+"["+temp[0]+"]");
	// }
 	 request.setAttribute("areaSel", sBuffer.toString());
   	 request.setAttribute("roleSel", sBuffer1.toString());
 %>
 <html>
<head>
<title>���ͨ</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk">
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"charset="gbk"><//script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
	var _stype = $("#stype").val();
	if(_stype != ''){
		if('1' == _stype){
			$("#rolecheck").attr("defaultsel","����Ա[1]");
			$("#sl-rolecheck").text("����Ա");
		}else if('2' == _stype){
			$("#rolecheck").attr("defaultsel","������[2]");
			$("#sl-rolecheck").text("������");
		}else if('3' == _stype){
			$("#rolecheck").attr("defaultsel","�����[3]");
			$("#sl-rolecheck").text("�����");
		}else if('4' == _stype){
			$("#rolecheck").attr("defaultsel","�ӿڴ�����[4]");
			$("#sl-rolecheck").text("�ӿڴ�����");
		}
		$("#rolecheck").val(_stype);
	}
})
function check(){
  	with(document.forms[0]){
		/* areacopeΪ�������ţ�Ҳ����ϵ��α��еĽڵ����� */
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
		if(userpassword.value == ""){
			alert("�������û�����!");
			userpassword.focus();
			return;
		}
		if(userpassword.value.split("").length < 8){
			alert("��������Ϊ8λ!");
			userpassword.focus();
			return;
		}
		if(!(/[0-9]+[a-z,A-Z]+/.test(userpassword.value)||/[a-z,A-Z]+[0-9]+/.test(userpassword.value))){
		   alert("��¼�������Ϊ���ּ���ĸ���");
		   userpassword.focus();
		   return;
		 }
	  	if(userpassword2.value == ""){
			alert("������ȷ�ϵ�½����!");
			userpassword2.focus();
			return;
		}
	    if (userpassword.value != userpassword2.value) {
	      alert("�����������벻һ��");
	      userpassword.focus();
	      return;
	    }
		if(rolecheck.value == ""){
			alert("��ѡ���û�����!");
			rolecheck.focus();
			return;
		}
		//if($("#rolecheck").val() != 1){
		if(wltarea.value == ""){
			alert("��ѡ���û�����ʡ!");
			return;
		}
		if(user_site_city.value == ""){
			alert("��ѡ���û�������!");
			return;
		}
		//}
		if(user_role.value == ""){
			alert("��ѡ���ɫ!");
			return;
		}
		
		if(userename.value == ""){
			alert("��������ʵ����!");
			userename.focus();
			return;
		}
		if(!/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(usersf.value))
		{
			 alert("���֤���벻�Ϸ�");
			 usersf.focus();
			 return ;
		}
		if(dealpass.value == ""){
			alert("�����뽻������!");
			dealpass.focus();
			return;
		}
		if(feepass.value == ""){
			alert("������ȷ�Ͻ�������!");
			feepass.focus();
			return;
		}
		if (dealpass.value != feepass.value) {
	      alert("�����������벻һ��");
	      feepass.focus();
	      return;
	    }
    	if(phone.value == ""){
			alert("��������ϵ�绰!");
			phone.focus();
			return;
		}
		if(phone.value.length!=11){
			alert('�ֻ��������Ϊ11λ��');
			phone.focus();
			return ;
		}
		var myreg = /^(1[3-9]{1}[0-9]{1})\d{8}$/;
		if(!myreg.test(phone.value)){
			alert('������Ϸ����ֻ������ʺţ�');
			phone.value="";
			phone.focus();
			return ;
		}
		if(operatename.value == ""){
			alert("�����뿪��������!");
			operatename.focus();
			return;
		}
		
		//��֤�û��Ƿ����
		$.post(
	    	$("#rootPath").val()+"/rights/sysuser.do?method=checkName",
	      	{
	      		name :$("#username").val(),
	      		userpassword :$("#userpassword").val()
	      	},
	      	function (data){
	      		if(0 == data){
	      			alert("�˺��Ѿ�����");
	      			return;
	      		}else if(1 == data){
					action="sysuser.do?method=addOneAgentUser";
					target="_self";
					document.getElementById("checkCode").value="ע����...";
					document.getElementById("checkCode").disabled="disabled";
					submit();
					return ;
	      		}else{
	      			alert("��̨�����쳣!");
	      			return ;
	      		}
	      	}
		)
	}
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
<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<html:form  method="post" action="/rights/sysuser.do?method=addOneAgentUser">
<input type="hidden" id="stype" name="stype" value="${param.st }"/>
<div class="pass_main">
    <div class="pass_list">
    	<ul>
    		<li><div class="pass_title_1">����û�</div></li>
            <li>
	            <strong>�ʺţ�</strong>
	            <span style="display:inline;width:400px;">
	            	<input name="username" id="username" type="text" maxlength="11" onblur="god(this)" style="float:left;"/>
	            	<div style="float:left;">
	            		<font color="red" >
	            			*(ϵͳ��¼�˺�,���ȱ������6λ,С��11λ,�ַ����������)
	            		</font>
            		</div>
	            </span>
            </li>
            <li><strong>��½���룺</strong><span><input name="userpassword" id="userpassword" type="password" /><font color="red">*(���볤�Ȳ�С��8λ������ĸ�����ֻ�����)</font></span></li>
 			<li><strong>ȷ�ϵ�½���룺</strong><span><input name="userpassword2" type="password" /><font color="red">*</font></span></li>
            <li><div class="pass_title_1">����û�</div></li>
            <li>
   	 		<strong>�û����ͣ�	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-rolecheck" href="javascript:void(0);">��ѡ��</a></div>
				<input name="rolecheck" id="rolecheck" defaultsel="${requestScope.roleSel }" type="hidden" value="" />
            </span><font color="red">*</font>
  		  	</li>
  		  	<li id="province">
   	 		<strong>����ʡ��	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-wltarea" href="javascript:void(0);">��ѡ��</a></div>
				<input name="wltarea" id="wltarea" defaultsel="${requestScope.areaSel }" type="hidden" value="" />
            </span><font color="red">*</font>
  		  	</li>
  		  	<li id="user_siteCity">
   	 		<strong>�û������У�	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-user_site_city" href="javascript:void(0);">��ѡ��</a></div>
				<input name="user_site_city" id="user_site_city" defaultsel="" type="hidden" value="" />
            </span><font color="red">*</font>
  		  	</li>
  		  	<li id="city" style="display:none">
   	 		<strong>��ɫ�����У�	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-areaid" href="javascript:void(0);">��ѡ��</a></div>
				<input name="areaid" id="areaid" defaultsel="" type="hidden" value="" />
            </span>
  		  	</li>
  		  	<li id="urole">
   	 		<strong>��ɫ��	</strong>
   	 		<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-user_role" href="javascript:void(0);">��ѡ��</a></div>
				<input name="user_role" id="user_role" defaultsel="" type="hidden" value="" />
            </span><font color="red">*</font>
  		  	</li>
			<li><strong>��ʵ������</strong><span><input name="userename" type="text" /><font color="red">*</font></span></li>
			<li><strong>���֤�ţ�</strong><span><input name="usersf" type="text" style="width: 230px;"/><font color="red"></font></span></li>
			<li><strong>��ϸ��ַ��</strong><span><input name="address" type="text" /><font color="red">*</font></span></li>
  		  	<li><strong>�������룺</strong><span><input name="dealpass" type="password" /><font color="red">*(ת����Ҫ������)</font></span></li>
			<li><strong>ȷ�Ͻ������룺</strong><span><input name="feepass" type="password" /><font color="red">*</font></span></li>
			<li><strong>�ͻ�����:</strong><span><input name="operatename" type="text" /><font color="red">*</font></span></li>
			<li><strong>������֤�ֻ��ţ�</strong><span><input name="phone" type="text"  maxlength=11 onkeyup="value=value.replace(/[^\d]/g,'')"/><font color="red">*(��¼������֤�ֻ���)</font></span></li>
        </ul>
           <ul>
           
  		  </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="ȷ��" id="checkCode" onclick="check()" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>