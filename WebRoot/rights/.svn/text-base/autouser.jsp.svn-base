<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
 <%
 	 String path = request.getContextPath();
 %>
 <html>
<head>
<title>批量开代办户</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script  type="text/javascript" src="../js/util.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	a:hover{
		text-decoration:underline;
	}
</style>

<SCRIPT type=text/javascript>
showMessage("${mess}");

function check(){
  with(document.forms[0]){
   if(login.value==""){
          alert("请输入代理商登录账号");
         return;
         }
      if(sourcefile.value==""){
          alert("请选择文件");
         return;
         }
    document.getElementById("btnSubmit").value="请等待...";
	document.getElementById("btnSubmit").disabled=true;
  	submit();
  	return;
  }
}
</SCRIPT>
</head>
<body style="overflow-y:hidden;">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>

<html:form  method="post" action="/rights/auto.do?method=autoAddusers" enctype="multipart/form-data">
<div class="pass_main">
    <div class="pass_list">
    	<ul>
			<li style="margin-top:20px;"><strong>代理商登录账号：</strong><span><input name="login" id="login"   style="width:300px;height:30px;font-size:20px;"  type="text"/></span></li>
			<li style="margin-top:20px;"><strong>代办点信息：</strong><span><html:file property="sourcefile"style="width:300px;height:30px;font-size:20px;"></html:file></span></li>
			<li style="margin-top:20px;color: red;"><strong>*</strong><span>文件不要有空行,最后一行不允许存在换行符</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="提交" id="btnSubmit" onclick="check();" class="field-item_button" />
   </div>
</div>
</html:form>
<br /><br />
<div style="padding-left:40px;padding-top:10px;margin-left: 20px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;font-size: 15px;">1、文件格式为:登录账号#登录密码#交易密码#姓名#市区编号#地址#邮件#身份证</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</body>
</html>