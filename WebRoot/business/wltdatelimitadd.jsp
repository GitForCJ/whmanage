<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="prod" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript" src="../js/util.js"></script>
<SCRIPT type=text/javascript>
showMessage("${mess}");
function check(){
  	with(document.forms[0]){
		if(startDate.value == ""){
		alert("请选择日期 !");
		return;
		}
		action="prod.do?method=adddate";
		target="_self";
		submit();
	}
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<html:form  method="post" action="/business/prod.do?method=adddate">
<div class="pass_main">
	<div class="pass_title_1">添加日期</div>
    <div class="pass_list">
    	<ul>
            <li><strong>日期：</strong><span><input class="Wdate" name="startDate" type="text" onClick="WdatePicker()" value="" /></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="取消" id="checkCode" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>