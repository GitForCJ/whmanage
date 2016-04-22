<%@ page contentType="text/html; charset=gb2312" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
%>

<html>
<head>
<title>银联转款</title>
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function sendMsg(){
	$.post(
    	$("#rootPath").val()+"/business/bank.do?method=sendMsg",
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
function btnSubmit(){
   // var a=$("#msgcode").val();
   // if(a.length!=8){
   // alert("请输入正确的短信验证码");
   // return false;
   // }
	$("#form1").submit();
}
$(function(){
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
})
</script>
</head>
<body>
<input type="hidden" id="rootPath" value="<%=path %>"/>
    <div class="mobile-charge">
<form action="<%=path %>/business/bank.do?method=trans" id="form1" method="post" class="form">
        <fieldset>
          <div class="form-line">
            <label class="label" for="mobile-num">转款金额：</label>
            <div class="element clearfix">
				${fee }元
				<input type="hidden" name="fee" value="${fee }"/>
            </div>
          </div>
          <c:if test="${loginUserForm.feeshortflag == 0}">
          <div class="form-line">
            <label class="label" for="mobile-num">短信验证：</label>
            <div class="element clearfix">
				<input type="text" name="msgcode" id="msgcode"><a href="javascript:sendMsg();">发送短信验证码</a>
            </div>
          </div>
          </c:if>
<div class="field-item_button_m" id="btnChag">
<input type="button" name="Button" value="确认" class="field-item_button" onclick="btnSubmit();">
<input type="button" name="Button" value="返回" class="field-item_button" onclick="history.go(-1)"></div>
        </fieldset>
      </form>
    </div>
</body>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
</html>
