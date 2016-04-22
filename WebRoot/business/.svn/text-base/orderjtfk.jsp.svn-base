<%@ page contentType="text/html; charset=utf-8" language="java"%>
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
    		<li><strong>手机号：</strong><span>${requestScope.jtfk.phone }</span></li>
            <li><strong>违章单号：</strong><span>${requestScope.jtfk.violationId }</span></li>
            <li><strong>违章时间：</strong><span>${jtfk.violationTime}</span></li>
            <li><strong>违章地点：</strong><span>${jtfk.viloationLocation }</span></li>
            <li><strong>违章费用：</strong><span id="totalFee">${jtfk.totalFee/100}</span><span>元</span></li>
            <li><strong>罚款金额：</strong><span>${jtfk.fineFee/100}</span><span>元</span></li>
            <li><strong>代办金额：</strong><span>${jtfk.dealFee/100 }</span><span>元</span></li>
            <li><strong>滞纳金：</strong><span>${jtfk.lateFee/100 }</span><span>元</span></li>
            <li><strong>代办周期：</strong><span>${jtfk.dealTime }</span></li>
            <li><strong>其他处理方式：</strong><span>${jtfk.otherWay }</span></li>
            <li><strong>当地交警联系方式：</strong><span>${jtfk.policeContact}</span></li>
            <li><strong>当地交警地址：</strong><span>${jtfk.policeAddress}</span></li>
            <li><strong>违章细节：</strong><span>${jtfk.viloationDetail}</span></li>
            <li><strong>能否处理：</strong><span>${jtfk.dealFlag}</span></li>
            <li><strong>填充不能办理的原因：</strong><span>${jtfk.dealMsg}</span></li>
            <li><strong>是否现场单：</strong><span>${jtfk.liveBill}</span></li>
            <li><strong>参考报价：</strong><span>${jtfk.referFee}</span></li>
            <li><strong>文书号：</strong><span>${jtfk.wsh}</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="返回" id="checkCode" onclick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</body>
</html>