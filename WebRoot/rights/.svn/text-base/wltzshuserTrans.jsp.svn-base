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
  		if($("#username").val() == ""){
  			alert("请输入转款账号");
  			return;
  		}
  		if($("#chargeFee").val() == ""){
  			alert("请输入转款金额");
  			return;
  		}
  		if($("#chargeFee").val()<0){
  			alert("转款金额不能为负数");
  			return;
  		}
  		if($("#username").val() != $("#confirmname").val()){
  			alert("两次输入不一致");
  			return;
  		}
  		if($("#tradePass").val() == ""){
  			alert("交易密码不能为空");
  			return;
  		}
		submit();
	}
}
function send(){
	$.post(
	    	$("#rootPath").val()+"/rights/sysuser.do?method=sendMsgAcctTrans",
	      	{
	      	},
	      	function (data){
	      		if(data == 0){
	      			alert("短信发送成功");
	      		}else{
	      			alert("短信发送失败");
	      		}
	      	}
		)
}

function upup(){
	with(document.forms[0]){
		if(username.value==""){
			alert("请输入合法的转款账户");
			}
		else{
			document.getElementById("yz").value="验证中...";
			document.getElementById("yz").disabled="disabled";
			getCmprod(username.value);
		}
	}
}


	function getCmprod(a){
		var userid=$("#user_id").val();
		$.post(
	    	$("#rootPath").val()+"/business/prod.do?method=zshyanzheng&tradeObject="+a+"&userid="+userid,
	      	{
	      	},
	      	function (data){
	      		var phoneInfo;
      			phoneInfo = "对方用户名:"+data[0].flag;
	      			    $("#phinfo").html("");
	      			    $("#phinfo").html(phoneInfo);
	      			     document.getElementById("yz").value="验证";
						document.getElementById("yz").disabled="true";
	      		return ;
	      	},"json"
		)
	}
</script>
</head>
<body>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" id="feeshortflag" value="${userinfo.feeshortflag }"/>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>额度转移</li></ul>
  </div>
</div>
<html:form  method="post" action="/rights/sysuser.do?method=transZshUser">
<input type="hidden" name="fee_flag" value="${userinfo.feeshortflag}"/>
<input type="hidden" name="user_id" value="<%=userSession.getUsername() %>" id="user_id"/>
<div class="pass_main">
    <div class="pass_list">
    	<ul>
            <li><strong>资金账户金额：</strong><span>${userinfo.accountAmount/1000.0 }元</span></li>
              <c:if test="${userinfo.feeshortflag == 2}">
            <li><strong>令牌验证码：</strong><span><input name="inputMsgCode" type="text"/><!--<a href="javascript:send();">发送验证码</a>--></span></li>
            </c:if>
            <c:if test="${userinfo.feeshortflag == 1}">
            <li><strong>短信验证码：</strong><span><input name="inputMsgCode" type="text"/><a id="a" href="javascript:send();"><span id="msg">(点击获取短信验证码)</span></a>
            </c:if>
            <li><strong>转款金额：</strong><span><input name="chargeFee" id="chargeFee" type="text"/>元</span></li>
            <li><strong>收款账户：</strong><span>
			<input type="text" maxlength="11" name="username" id="username">&nbsp;&nbsp;
   		 	<input style="background:#1e90ff;color: white;width: 50px;border: thick;" type="button" name="yz" id="yz" value="验证" onclick="upup();"/>

			</span></li>
<li style="margin-left: 150px;" id="showinfo" ><span id="phinfo" style="color: red;">(账号为对方登陆账号)</span></li>
			<li><strong>重复账户：</strong><span>
			<input type="text" name="confirmname" id="confirmname" maxlength="11">
			</span></li>
			<li><strong>交易密码：</strong><span>
			<input type="password" name="tradePass" id="tradePass">
			</span></li>
        </ul>
   </div>
   <div class="field-item_button_m">
		<input type="button" value="转款" id="btnSubmit" onclick="submitForm();" class="field-item_button" />
	<input type="button" value="返回" id="checkCode" onclick="javascript:history.go(-1)" class="field-item_button" />
   </div>
 
</div>
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、只允许将自身资金账户内的资金转移给系统内其他下级用户的资金账户,请谨慎操作。</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>


</html:form>
</body>
</html>