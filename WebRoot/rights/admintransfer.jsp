<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script type=text/javascript>
$(function(){
	if($("#mess").val() != ''){
		alert($("#mess").val());
	}
});
function submitForm(){
  	with(document.forms[0]){
  	    if(!/^[0-9]+(\.[0-9]+){0,1}$/.test($("#chargeFee").val())||/^0+[0-9]+(\.[0-9]+){0,1}$/.test($("#chargeFee").val())){
	        alert("请输入正确的 金额");
	        return false;
            }
//	     if($("#chargeFee").val().split(".")[1].length>2){
    // 	    alert("金额的小数点只能有两位！");
	//	    return false;
	 //   } 
  		if($("#username").val() == ""){
  			alert("请输入转款账户");
  			return false;
  		}
  		if($("#username").val() != $("#confirmname").val()){
  			alert("两次输入不一致");
  			return false;
  		}
		submit();
		return true;
	}
}
</script>
</head>
<body>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>账户提现</li></ul>
  </div>
</div>
<html:form  method="post" action="/rights/sysuser.do?method=adminTransfer">
<div class="pass_main">
    <div class="pass_list">
    	<ul>
            <li><strong>提现金额：</strong><span><input name="chargeFee" id="chargeFee" type="text"/>元</span></li>
            <li><strong>提现账户：</strong><span>
			<input type="text" name="username" id="username"><font color="red">(账号为资金账号)</font>
			</span></li>
			<li><strong>重复账户：</strong><span>
			<input type="text" name="confirmname" id="confirmname">
			</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="提现" id="btnSubmit" onclick="return submitForm();" class="field-item_button" />
   </div>
    
</div>
  <div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
	<ul>
	<li style="list-style:none;">1、提现功能不可逆,请仔细确认账户信息谨慎操作。</li>
	<li style="list-style:none;">&nbsp;</li>
	</ul>
	</div>
</html:form>
</body>
</html>