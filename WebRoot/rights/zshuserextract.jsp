<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
 <%
 	 String path = request.getContextPath();
	 String userid=userSession.getUserno();
   	 request.setAttribute("userinfo", user.getUserAccount(userid));
 %>
<html>
<head>
<title>万汇通</title>
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<script  type="text/javascript" src="../js/util.js"></script>
<script type=text/javascript>
$(function(){
	$("#tradePass").val("");
	$("#chargeFee").val("");
	if($("#mess").val() != ''){
		alert($("#mess").val());
	}
	 $("#chargeFee").keydown(function(event) {
             var keyCode = event.which;
             if (keyCode == 46 || keyCode == 8 || keyCode == 190 || (keyCode >= 48 && keyCode <= 57) || (keyCode >= 96 && keyCode <= 105) || keyCode == 110) {
                 return true;
             } else { 
             	return false 
             }
       });
});


function submitForm(){
  	with(document.forms[0]){
  		if($("#chargeFee").val() == ""){
  			alert("提取金额不能为空");
  			$("#chargeFee").focus();
  			return;
  		}
  		var reg=/^\d+(?=\.{0,1}\d+$|$)/
  		var v=$("#chargeFee").val();
  		if($("#chargeFee").val() != "")
  		{
  			if(!reg.test(v)){
  				alert("提取金额格式不正确");
  					$("#chargeFee").focus();
  				return;
  			}
  			
  		}
  		var acc=$("#accYLeft").val();
  		if(parseFloat(v)>parseFloat(acc)){
  			alert("提取金额不能大于最大提取金额");
  			$("#chargeFee").focus();
  			return;
  		}
  		if($("#tradePass").val() == ""){
  			alert("交易密码不能为空");
  				$("#tradePass").focus();
  			return;
  		}
		submit();
	}
}




</script>
</head>
<body>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" id="feeshortflag" value="${userinfo.feeshortflag }"/>
<html:form  method="post" action="/rights/sysuser.do?method=zshuserextact">
<input type="hidden" name="userNo" value="<%=userSession.getUserno() %>"/>
<input type="hidden" name="userSNo" value="${userNo}"/>
<div class="pass_main">
    <div class="pass_list">
    	<ul>
            <li><strong>资金账户可用余额：</strong><span>${accLeft}元</span></li>
            <li><strong>可最大提取金额：</strong><span>${accYLeft}元</span>
			<input id="accYLeft"  type="hidden" value="${accYLeft}"/>
			</li>
            <li><strong>提取金额：</strong><span><input id="chargeFee"  name="chargeFee" type="text"/>元</span></li>
			<li><strong>交易密码：</strong><span>
			<input type="password" name="tradePass" id="tradePass" >
			</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
	<input type="button" value="提取" id="btnSubmit" onclick="submitForm();" class="field-item_button" />
   <input type="button" value="返回" id="checkCode" onclick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>

</html:form>
</body>
</html>