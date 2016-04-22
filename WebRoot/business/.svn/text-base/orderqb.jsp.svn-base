`<%@ page contentType="text/html; charset=utf-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
 <%
 	 String path = request.getContextPath();
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
</head>
<body>
<div class="pass_main">
	<div class="pass_title_1">订单信息</div>
    <div class="pass_list">
    	<ul>
            <li><strong>充值QQ号：</strong><span>${param.v1}</span></li>
            <li><strong>充值金额：</strong><span>${param.v2/100}</span><span>元</span></li>
            <li><strong>接口商名称：</strong><span>${param.v3 }</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="返回" id="checkCode" onclick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</body>
</html>