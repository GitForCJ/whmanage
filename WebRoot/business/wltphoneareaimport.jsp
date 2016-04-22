<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="spa" scope="page" class="com.wlt.webm.business.bean.SysPhoneArea"/>
<%
  String path = request.getContextPath();
%>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
function check(){
  	with(document.forms[0]){
		/* areacope为区域区号，也即体系层次表中的节点区号 */
		if(phoneAreaFile.value == ""){
		alert("请选择文件 !");
		return;
		}
		target="_self";
		submit();
	}
}
</SCRIPT>
</head>
<body>
<html:form  method="post" action="/business/spa.do?method=importFile" enctype="multipart/form-data">
<div class="pass_main">
	<div class="pass_title_1">导入号码区域</div>
    <div class="pass_list">
    	<ul>
            <li><strong>文件：</strong><span><input name="file" id="phoneAreaFile" type="file" /></span></li>
        </ul>
    	<ul><li></li>
            <li><strong>文件格式：</strong><font color="red" size="3">七位号段,省份,城市,运营商</font></li>
 <li><strong>例如：</strong><font color="red" size="3">1868203,广东,深圳,联通</font></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="reset" value="取消" id="checkCode" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>