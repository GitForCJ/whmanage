<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
 <%
 	 String path = request.getContextPath();
	 String userid=request.getParameter("uid");
	 String op=request.getParameter("op");
   	 request.setAttribute("userinfo", user.getUserAccount(userid));
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	//$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
//	$("#chargeFee").keyup(function(){
//		$(this).val($(this).val().replace(/\D/g,''));
//	})
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});
function submitForm(){
  	with(document.forms[0]){
  		if($("#chargeFee").val().Trim().length<=0)
  		{
  			alert("请输入充值金额!");
  			return false;
  		}
  		var t=/^\d+(\.\d+)?$/;
  		if(!t.test($("#chargeFee").val().Trim())){
  			alert("请输入正确的充值金额!");
  			return false;
  		}
  		if($("#chargeFee").val().Trim()<=0){
  			alert("请输入正确的充值金额!");
  			return false;
  		}
  		if($("#chargeFee").val()>2000000)
	  	{
	  		alert("一次只能充值200万以内!");
  			return false;
	  	}
		submit();
	}
}
// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 
</SCRIPT>
</head>
<body>
<html:form  method="post" action="/rights/sysuser.do?method=charge">
<input type="hidden" name="user_id" value="<%=userid %>"/>
<input type="hidden" name="op" value="<%=op %>"/>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<input type="hidden" name="redUrl" value="/rights/wltaccountCharge.jsp?uid=<%=userid %>&op=<%=op%>"/>
<div class="pass_main">
	<div class="pass_title_1">资金账户充值</div>
    <div class="pass_list">
    	<ul>
            <li><strong>资金账户：</strong><span>${userinfo.accountAmount/1000}元</span></li>
            <li><strong>充值金额：</strong><span><input name="chargeFee" id="chargeFee" type="text"   onkeyup="value=value.replace(/[^\d.]/g,'') " /></span></li>
 <li><strong>翼支付网关订单号：</strong><span><input name="orderid" id="orderid" type="text"   onkeyup="value=value.replace(/[^\d]/g,'') " /><font color="red">&nbsp;非翼支付网关手动加款不需要填写</font></span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="充值" id="btnSubmit" onclick="submitForm();" class="field-item_button" />
   <input type="button" value="返回" id="checkCode" onclick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>