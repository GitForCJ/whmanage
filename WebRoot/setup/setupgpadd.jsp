<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
 <%
	// request.setAttribute("arealist", prod.getAreaList());
	   String st=request.getParameter("st");
	  System.out.println("==="+st);
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/xml_cascade.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<SCRIPT type=text/javascript>
jQuery(function(){
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});
function check(){
  	with(document.forms[0]){
	    if(cm_type.value==""){
	    alert("请选择组类型 !");
	     return false
	    }
	    if(cm.value==""){
	    alert("请选择是否默认!");
	     return false
	    }
	    if(setupname.value==""){
	    alert("请输入组名称!");
	     return false
	    }
	    if(confirm("确认添加?")){
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
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<html:form  method="post" action="/business/prod.do?method=addsetup">
<input type="hidden" name="st"  value="<%=st %>" />
<input name="emtype" value="0" type="hidden">
<div class="pass_main">
	<div class="pass_title_1">添加阶梯组</div>
    <div class="pass_list">
    	<ul>
            <li><strong>组类型：</strong><span>
            <select name="cm_type" style="border-color:#0x8398ac;color:#0x8398ac;">
<option  value="">请选择</option>
            <option  value="0">一级代理商阶梯</option>
            </select>
			</li>
            <li><strong>是否默认：</strong><span>
            <select name="cm" style="border-color:blue;color:#0x8398ac;">
<option  value="">请选择</option>
            <option  value="0">非默认</option>
<option  value="1">默认</option>
            </select>
</li>
<li><strong>组名称：</strong><span><input name="setupname" type="text" /><font color="red" size="3">*</font></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="取消" id="checkCode1" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>