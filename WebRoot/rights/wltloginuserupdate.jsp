<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysLoginUser"/>
 <%
 	 String path = request.getContextPath();
 	 String userId = request.getParameter("uid");
	 String userName = request.getParameter("uname");
 	 request.setAttribute("userLogin", user.getLoginUserInfo(userName));
 %>
 <html>
<head>
<title>���ͨ</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<script language='javascript' src='../js/select.js'></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	$("#macflag").change(function(){
		var _mf = $(this).val();
		if(_mf == '1'){
			$("#mac").val("");
			$("#macAddr").hide();
		}else{
			$("#macAddr").show();
		}
	});
});
function check(){
  	with(document.forms[0]){
		/* areacopeΪ�������ţ�Ҳ����ϵ��α��еĽڵ����� */
		if(userpassword.value == ""){
		alert("�������û�����!");
		return;
		}
		if(dealpass.value == ""){
		alert("�����뽻������!");
		return;
		}
		if(feepass.value == ""){
		alert("������ת������!");
		return;
		}
		if(phone.value == ""){
		alert("�������ֻ���!");
		return;
		}
		action="sysloginuser.do?method=update";
		target="_self";
		submit();
	}
}
</SCRIPT>
</head>
<body>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<html:form  method="post" action="/rights/sysloginuser.do?method=update">
<input type="hidden" name="user_id" value="<%=userId %>"/>
<input type="hidden" name="username" value="${userLogin.username }"/>
<input type="hidden" name="pass" value="${userLogin.userpassword }"/>
<div class="pass_main">
	<div class="pass_title_1">��½�˺Ÿ���</div>
    <div class="pass_list">
    	<ul>
            <li><strong>�ʺţ�</strong><span>${userLogin.username }</span></li>
            <li><strong>���룺</strong><span><input name="userpassword" type="password" value="${userLogin.userpassword }"/></span></li>
  		  	<li><strong>�������룺</strong><span><input name="dealpass" type="text" value="${userLogin.dealpass }"/></span></li>
			<li><strong>ת�����룺</strong><span><input name="feepass" type="text" value="${userLogin.feepass }"/></span></li>
			<li><strong>������֤�ֻ��ţ�</strong><span><input name="phone" type="text" value="${userLogin.phone }"/></span></li>
			<li><strong>״̬��</strong><span>
				<select name="user_state" id="user_state">
					<option value="0" <c:if test="${userLogin.user_state == 0 }">selected</c:if>>����</option>
					<option value="1" <c:if test="${userLogin.user_state == 1 }">selected</c:if>>����</option>
				</select>
			</span></li>
			<li><strong>�ʽ��˻�ת������Ƿ���֤��</strong><span>
				<select name="feeshortflag" id="feeshortflag">
					<option value="0" <c:if test="${userLogin.feeshortflag == 0 }">selected</c:if>>��֤</option>
					<option value="1" <c:if test="${userLogin.feeshortflag == 1 }">selected</c:if>>����֤</option>
				</select>
			</span></li>
			<li><strong>Mac��ַ�Ƿ���֤��</strong><span>
				<select name="macflag" id="macflag">
					<option value="0" <c:if test="${userLogin.macflag == 0 }">selected</c:if>>��֤</option>
					<option value="1" <c:if test="${userLogin.macflag == 1 }">selected</c:if>>����֤</option>
				</select>
			</span></li>
			<li id="macAddr"><strong>Mac��ַ��</strong><span><input name="mac" id="mac" type="text" value="${userLogin.mac }"/></span></li>
			 <li><strong>��½�����Ƿ���֤��</strong><span>
				<select name="passflag" id="passflag">
					<option value="0" <c:if test="${userLogin.passflag == 0 }">selected</c:if>>��֤</option>
					<option value="1" <c:if test="${userLogin.passflag == 1 }">selected</c:if>>����֤</option>
				</select>
			</span></li>
			<li><strong>��¼�����Ƿ���֤��</strong><span>
				<select name="shortflag" id="shortflag">
					<option value="0" <c:if test="${userLogin.passflag == 0 }">selected</c:if>>��֤</option>
					<option value="1" <c:if test="${userLogin.passflag == 1 }">selected</c:if>>����֤</option>
				</select>
			</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="ȷ��" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="����" id="btnBack" onclick="javascript:history.go(-1);" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>