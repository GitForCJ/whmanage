<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<%@ page import="com.wlt.webm.rights.form.SysUserForm"%>
<%
String path = request.getContextPath();
String userno=userSession.getUserno();
SysUserForm ss=user.getUserAccount(userno);
float mon=user.getableMoney(userno,ss.getCommissionAmount());
request.setAttribute("userinfo", ss);
%>

<html>
<head>
<title>佣金账户转款</title>
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function btnSubmit(){
var a="";
var temp=document.getElementsByName("num");
  for (i=0;i<temp.length;i++){
    if(temp[i].checked){
    a=temp[i].value;
    }
    }
    if(a==1){
	var _fee = $("#fee").val();
	var _ablefee = "<%=mon %>";
	if(_fee == ''||parseFloat(_ablefee)==0){
		alert("金额不能为空或0");
		return;
	}
	if(parseFloat(_fee)>parseFloat(_ablefee)){
		alert("金额不能大于可转金额");
		return;
	}
	}else{
		var _fee = $("#fee3").val();
	var _ablefee = "${userinfo.frozenAmount/1000 }";
	if(_fee == ''||parseFloat(_ablefee)==0){
		alert("金额不能为空或0");
		return;
	}
	if(parseFloat(_fee)>parseFloat(_ablefee)){
		alert("金额不能大于可转金额");
		return;
	}
	}
	$("#form1").submit();
}
$(function(){
	if($("#mess").val() != ''){
		alert($("#mess").val());
	}
})
function dianji(c){
if(c==1){
$("#fee3").val("");
}else {
$("#fee").val("");
}
}

</script>
</head>
<body>
<div id="tabbox">
  <div id="tabs_title" style="background-color: white;">
    <ul class="tabs_wzdh">
      <li>资金账户</li></ul>
  </div>
</div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<form id="form1" name="form1" action="<%=path %>/rights/sysuser.do?method=transChild" method="post" class="form">
<br/>       
 <fieldset>
			<div class="form-line" style="margin-left: 90px;font-size: 16px;">
            <label for="mobile-num">资金账户余额：</label>
				${userinfo.accountAmount/1000 }&nbsp;元
          </div>
          <div class="form-line" style="margin-left: 90px;font-size: 16px;">
            <label  for="mobile-num">佣金账户余额：</label>
				${userinfo.commissionAmount/1000 }&nbsp;元
          </div>
          <div class="form-line" style="margin-left: 90px;font-size: 16px;">
            <label  for="mobile-num">冻结账户余额：</label>
				${userinfo.frozenAmount/1000 }&nbsp;元
          </div>
          <div class="form-line" style="margin-left: 90px;font-size: 16px;">
            <label  for="mobile-num">佣金转资金账户：</label>
				<input type="text"  name="transFee" id="fee" style="font-size: 20px;height: 30px;width:120px;color: blue;border-bottom-style:solid;" />&nbsp;元
<input onclick="dianji(1)" type="radio" checked="checked" name="num" value="1" style="border:0px; height:15px;width:15px;"/>
</div>
<br>
          <div class="form-line" style="margin-left: 90px;font-size: 16px;">
            <label  for="mobile-num">冻结转资金账户：</label>
				<input type="text"  name="transFee3" id="fee3" style="font-size: 20px;height: 30px;width:120px;color: blue;border-bottom-style: solid;" />&nbsp;元
<input onclick="dianji(3)"  type="radio"  name="num" value="3" style="border:0px; height:15px;width:15px;"/>
</div>

</br>
          <div class="form-line" style="margin-left: 90px;font-size: 16px;">
<label for="mobile-num"><font color="red">佣金账户可转出的金额为:<%=mon %>元</font></label>
<br/>
<label for="mobile-num"><font color="red">冻结账户可转出的金额为:${userinfo.frozenAmount/1000 }元</font></label>
</div>
<br/>
            
<div class="field-item_button_m" id="btnChag"><input  type="button" name="Button" value="下一步" class="field-item_button" onclick="btnSubmit();"></div>
        </fieldset>
      </form>
<br/>
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、代理商可以将佣金账户中的佣金进行提取，佣金提取只能是当前时间48小时前的交易佣金，系统默认48小时以内的佣金不允许提取。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">2、星期五、星期六或者法定节假日（详情见公告）冻结账户的金额无法转出到资金账户。</li>
</ul>
</div>
<input type="hidden" id="mess" value="${requestScope.mess }" />
</body>
</html>
