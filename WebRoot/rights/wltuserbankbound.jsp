<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@page import="com.wlt.webm.rights.form.SysUserBankForm"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="userBank" scope="page" class="com.wlt.webm.rights.bean.SysUserBank"/>
 <%
 	 String path = request.getContextPath();
	 String userid=request.getParameter("uid");
	 SysUserBankForm bank = userBank.getUserBankInfo(userid);
	 String isNullBank = "false";
	 if(null == bank.getUser_id()){
		 isNullBank = "true";
	 }
   	 request.setAttribute("bankinfo", bank);
 %>
 <html>
<head>
<title>���ͨ</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
function submitForm(){
	if($("user_bankcard").val() == ''){
		alert("���п��Ų���Ϊ��");
		return;
	}
	if($("user_name").val() == ''){
		alert("�û���������Ϊ��");
		return;
	}
	if($("user_icard_type").val() == ''){
		alert("���֤���Ͳ���Ϊ��");
		return;
	}
	if($("user_icard").val() == ''){
		alert("�û�֤���Ų���Ϊ��");
		return;
	}
  	with(document.forms[0]){
		submit();
	}
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<html:form  method="post" action="/rights/sysuserbank.do?method=bankBound">
<input type="hidden" name="user_id" value="<%=userid %>"/>
<input type="hidden" name="isNullBank" value="<%=isNullBank %>"/>
<div class="pass_main">
	<div class="pass_title_1">�û���Ϣ</div>
    <div class="pass_list">
    	<ul>
            <li><strong>�û��˺ţ�</strong><span>${param.uname }</span></li>
            <li><strong>���п��ţ�</strong><span><input name="user_bankcard" id="user_bankcard" type="text" value="${bankinfo.user_bankcard }"/></span></li>
            <li><strong>�û�������</strong><span><input name="user_name" id="user_name" type="text" value="${bankinfo.user_name }"/></span></li>
            <li><strong>���֤���ͣ�</strong><span>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-user_icard_type" href="javascript:void(0);">��ѡ��</a></div>
			<input nname="nname" name="user_icard_type" id="user_icard_type" defaultsel="��ѡ��[]|���֤[01]|����֤[02]|����[03]|����֤[04]|̨��֤[05]|����֤[06]|ʿ��֤[07]|����֤��[99]" type="hidden" value="${bankinfo.user_icard_type}" />
			</span></li>
            <li><strong>�û�֤���ţ�</strong><span><input name="user_icard" id="user_icard" type="text" value="${bankinfo.user_icard }"/></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="����" id="btnSubmit" onclick="submitForm();" class="field-item_button" />
   <input type="button" value="����" id="checkCode" onclick="location.href='<%=path %>/rights/wltuserbanklist.jsp'" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>