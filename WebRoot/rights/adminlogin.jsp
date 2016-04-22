<%@ page contentType="text/html; charset=gb2312" session="false"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link REL="stylesheet"  TYPE="text/css"  HREF="../css/1.css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../js/util.js"></script>
<script type="text/javascript" src="../js/check.js"></script>

<script LANGUAGE="JavaScript">
  			var i = 80;
        function msgShow() {
            document.getElementById("msg").innerHTML ="<font color=red>"+i+"</font>���δ�յ�����,�����»�ȡ ";
            if (i > 0) {
                i--;
            }
            else {
                document.getElementById("msg").innerHTML ="ʹ�ö�����֤��";
            }
            setTimeout('msgShow()', 1000);
        }

	function runB1(form){
		var checkCode = document.getElementById("check").value.toUpperCase();
		if(checkCode.length=="" ){
			alert("��������֤��");
			return;
		}
		if(checkCode != code){
			alert("��֤�벻��ȷ");
			return;
		}
		var name = navigator.appName;
		var version = navigator.appVersion;
		if (!check(form))return;
        var date = new Date((new Date()).getTime() + 30 * 24 * 60 * 60 * 1000);
        document.cookie = "login=" + escape(form.login.value) + "; expires=" + date.toGMTString();
		form.submit();
		return;
	}
	function check(form) {
		CT=form.login;
		CTV=CT.value;
		if(CTV == ""){
			validatePrompt (CT,"��¼������Ϊ�ա�");
			return (false);
		}
		CT=form.password;
		CTV=CT.value;
		if (CTV ==""){
			validatePrompt (CT,"���벻��Ϊ�ա�");
			return (false);
		}
		if(CTV.indexOf("'") > -1){
			validatePrompt (CT,"���벻�ܰ�����Ч�ַ�'��");
			return (false);
		}
		return (true);
	}
	function validatePrompt (Ct, PromptStr) {
		Ct.focus();
		return;
	}
	function logFocus(){
		var form = document.forms[0];
		var name = navigator.appName;
		var version = navigator.appVersion;
		if(name != "Microsoft Internet Explorer" ){
			alert("��ϵͳ���밲װIE8.0���ϲ�������ʹ��");
			return;
		}
        var cookies = document.cookie.split(";");
        for (var i = 0; i < cookies.length; i++)
        {
          var cookie = cookies[i].split("=");
          if (cookie[0].trim() == "login") {
            form.login.value = unescape(cookie[1]);
          }
        }
        if (form.login.value.length == 0) {
          form.login.focus();
        } else {
          form.password.focus();
        }
		window.status="���";
	}
	function onkeyboard(){
		var a = event.keyCode
		if(a == 13){
			runB1(form);
		}
	}
	function clearlogin(txt){
	if(txt.value == "�ֻ�����"){
	txt.value = "";
	}
	}
	function setlogin(txt){
	if(txt.value == ""){
	txt.value = "�ֻ�����";
	}
	}
	function clearpw(txt){
	if(txt.value == "����������"){
	txt.value = "";
	}
	}
	document.onkeydown = onkeyboard;
	showMessage("${mess}");
$(function(){
	$("#msg").click(function(){
		var username = $("input[name='login']").val();
		if(username.length!=11||!/^(13[0-9]|15[0-9]|18[0-9])\d{8}$/.test(username)){
			alert("��������Ч���ֻ�����");
			return;
		}
		$.post(
	    	$("#rootPath").val()+"/rights/sysuser.do?method=sendMsg",
	      	{
	      		username : username
	      	},
	      	function (data){
	      		if(data == 0){
	      		    msgShow();
	      			alert("���ŷ��ͳɹ�");
	      		}else{
	      			alert("���ŷ���ʧ��");
	      		}
	      	}
		)
	});
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
})
</script>

<title>���ͨ�ۺϷ���ƽ̨</title>
</head>
<body  onLoad="createCode();logFocus()" >
<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" id="mess" value="${requestScope.msg }"/>
<html:form  method="post" action="/rights/sysuser.do?method=login&flag=0" >
<!--ͷ��-->
<div id="top_home">
  <div id="top_home_main">
	<div class="top_home_left">
    	<ul>
        	<li class="top_phone"><a href="#">�ֻ����ͨ</a></li>
         </ul>
     </div>
    <div class="top_home_right">
    	<ul>
            <li><a href="<%=path %>">��¼</a>&nbsp;&nbsp;-&nbsp;&nbsp;<a href="<%=path %>/rights/wltregist.jsp">ע��</a></li>
      </ul>
     </div>
 </div>
</div>
<!--logo&nav-->
<div id="header_main">
  <div id="header">
    <div class="logo"><a href="#"><img src="../images/logo.png" width="303" height="77" /></a></div>
  </div>
</div>
<!--��¼-->
<div id="login_box">
  <div id="login_main">
    <div id="login">
    	<h3>���ͨ��¼</h3>
    	<ul class="login_ul">
        <li><span>�û�����&nbsp;&nbsp;<input name="login" type="text"  onclick="clearlogin(this)" onblur="setlogin(this)"/><a id="msg" href="javascript:void(0);">ʹ�ö�����֤��</a></span></li>
        <li><span>��&nbsp;&nbsp;&nbsp;�룺<input style="margin-left:8px;" name="password" type="password" /></span></li>
        <li><span>��֤�룺&nbsp;&nbsp;<input type="text" id="check" onpaste="return false" maxLength="8" /><input type="text" onclick="createCode()" readonly="readonly" id="checkCode" onselectstart= "return false" /></span></li>
<!--        <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="CheckboxGroup1" value="��ѡ��" id="CheckboxGroup1_0" />��ס�û���</li>-->
        <li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="" type="button" class="login_button" value="��¼" onclick="runB1(this.form)" /> </li>
        </ul>
    </div>
  </div>
</div>
<!--��ݷ�ʽͼ��-->
<div id="kjicon">
	    <div class="index-app dark-gray-links clearfix">
      <ul>
              <li><a><img src="../images/app_shoujicz.png"><strong>�ֻ���ֵ</strong></a></li>
              <li><a><img src="../images/app_zuojicz.png"><strong>������ҵ�ɷ�</strong></a></li>
              <li><a><img src="../images/app_kuandaicz.png"><strong>�����ֵ</strong></a></li>
              <li><a><img src="../images/app_chongzhik.png"><strong>��Ϸ��</strong></a></li>
              <li><a><img src="../images/app_qbicz.png"><strong>Q�ҳ�ֵ</strong></a></li>
              <li><a><img src="../images/app_jiaotongfk.png"><strong>��ͨ����</strong></a></li>
      </ul>
    </div>
</div>
<!--ҳβ ��ʼ-->
<%@ include file="../common/include_footer.jsp"%>
<!--ҳβ ����-->
</html:form>
</body>
</html>
