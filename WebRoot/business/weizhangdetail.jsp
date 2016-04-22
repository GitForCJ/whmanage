<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
 <%
 	 String path = request.getContextPath();
 %>
 <html>
<head>
<%@ page contentType="text/html; charset=gb2312" language="java"%> 
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
	$("#isMail").change(function(){
		var _mailVal = $("#isMail").val();
		var _totalFee = $("#totalFee").text();
		if(_mailVal == '0'){
			$("#resultFee").html(_totalFee);
		}else{
			$("#resultFee").html(parseInt(_totalFee)+15.0);
		}
	});
	$("#btnPay").click(function(){
		if(!isMobileNO(document.getElementById("phone"))){
			return;
		}else{
			$("#form1").submit();
		}
	});
});
	//验证手机号码
	function isMobileNO(obj){
		var objValue = obj.value;
		if(typeof(objValue)=="undefined")
			return false;
		if(objValue=="" || objValue==null){ 
			alert("请输入手机号");
			return false;
		} 
		if(objValue.length!=11){
			alert('请输入11位手机号码！');
			return false;
		}
		var myreg = /^(1[3-9]{1}[0-9]{1})\d{8}$/;
		if(!myreg.test(objValue)){
			alert('请输入合法的手机号码！');
			obj.value="";
			obj.focus();
			return false;
		}
		return true;
	}
</SCRIPT>
</head>
<body>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>交通违章</li></ul>
  </div>
</div>
<form name="form1" id="form1" action="<%=path %>/business/jtfk.do?method=payOrder" method="post">
<input type="hidden" name="totalFee" value="${param.totalFee}"/>
<input type="hidden" name="violationId" value="${param.violationId}"/>
<input type="hidden" name="violationTime" value="${param.violationTime}"/>
<input type="hidden" name="viloationLocation" value="${param.viloationLocation}"/>
<input type="hidden" name="fineFee" value="${param.fineFee}"/>
<input type="hidden" name="dealFee" value="${param.dealFee}"/>
<input type="hidden" name="lateFee" value="${param.lateFee}"/>
<input type="hidden" name="dealTime" value="${param.dealTime}"/>
<input type="hidden" name="otherWay" value="${param.otherWay}"/>
<input type="hidden" name="policeContact" value="${param.policeContact}"/>
<input type="hidden" name="policeAddress" value="${param.policeAddress}"/>
<input type="hidden" name="viloationDetail" value="${param.viloationDetail}"/>
<input type="hidden" name="dealFlag" value="${param.dealFlag}"/>
<input type="hidden" name="dealMsg" value="${param.dealMsg}"/>
<input type="hidden" name="liveBill" value="${param.liveBill}"/>
<input type="hidden" name="referFee" value="${param.referFee}"/>
<input type="hidden" name="wsh" value="${param.wsh}"/>
<div class="pass_main">
	<div class="pass_title_1">违章信息</div>
    <div class="pass_list">
    	<ul>
    		<li><strong>手机号：</strong><span><input type="text" name="phone" id="phone"/></span></li>
            <li><strong>违章单号：</strong><span>${param.violationId }</span></li>
            <li><strong>违章时间：</strong><span>${param.violationTime}</span></li>
            <li><strong>违章地点：</strong><span>${param.viloationLocation }</span></li>
            <li><strong>违章费用：</strong><span id="totalFee">${param.totalFee/100}元</span><span></span></li>
            <li><strong>罚款金额：</strong><span>${param.fineFee/100}元</span><span></span></li>
            <li><strong>代办金额：</strong><span>${param.dealFee/100 }元</span><span></span></li>
            <li><strong>滞纳金：</strong><span>${param.lateFee/100 }元</span><span></span></li>
            <li><strong>代办周期：</strong><span>${param.dealTime }</span></li>
            <li><strong>其他处理方式：</strong><span>${param.otherWay }</span></li>
            <li><strong>当地交警联系方式：</strong><span>${param.policeContact}</span></li>
            <li><strong>当地交警地址：</strong><span>${param.policeAddress}</span></li>
            <li><strong>违章细节：</strong><span>${param.viloationDetail}</span></li>
            <li><strong>能否处理：</strong><span>${param.dealFlag}</span></li>
            <li><strong>填充不能办理的原因：</strong><span>${param.dealMsg}</span></li>
            <li><strong>是否现场单：</strong><span>${param.liveBill}</span></li>
            <li><strong>参考报价：</strong><span>${param.referFee}</span></li>
            <li><strong>文书号：</strong><span>${param.wsh}</span></li>
            <li><strong>是否邮递：</strong><span>
			<select name="isMail" id="isMail">
			<option value="0">不邮递</option>
			<option value="1">邮递</option>
			</select>
			</span></li>
			<li><strong>邮递地址：</strong><span><input type="text" name="mailAddr" id="mailAddr"/></span></li>
			<li><strong>代办总费用：</strong><span id="resultFee">${param.totalFee/100 }</span><span>元</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="支付订单" id="btnPay" class="field-item_button" />
   <input type="button" value="返回" id="checkCode" onclick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</form>
</body>
</html>