<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="itype" scope="page" class="com.wlt.webm.business.bean.SysInterfaceType"/>
 <%
 	 String path = request.getContextPath();
	 String siid=request.getParameter("siid");
   	 request.setAttribute("itypeinfo", itype.getSiTypeInfo(siid));
 %>
 <html>
<head>
<title>万汇通</title>
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
});
function submitForm(){
  	with(document.forms[0]){
		submit();
	}
}
</SCRIPT>
</head>
<body>
<html:form  method="post" action="/business/sit.do?method=update">
<input type="hidden" name="in_id" value="<%=siid %>"/>
<div class="pass_main">
	<div class="pass_title_1">充值接口信息</div>
    <div class="pass_list">
    	<ul>
    		<li><strong>接口标识：</strong><span><input name="in_id" type="text" value="${itypeinfo.in_id }"/></span></li>
    		<li><strong>接口名称：</strong><span><input name="in_name" type="text" value="${itypeinfo.in_name }"/></span></li>
            <li><strong>接口描述：</strong><span><input name="in_explain" type="text" value="${itypeinfo.in_explain }"/></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="保存" id="btnSubmit" onclick="submitForm();" class="field-item_button" />
   <input type="button" value="返回" id="checkCode" onclick="location.href='<%=path %>/business/wltinterfacetypelist.jsp'" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>