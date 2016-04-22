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
function btnSubmit(){
	var _fee = $("#fee").val();
	if(_fee == ''){
		alert("金额不能为空");
		return;
	}
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
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>银联转款</li></ul>
  </div>
</div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
    <div class="mobile-charge">
<form id="form1" name="form1" action="<%=path %>/business/bank.do?method=transNext" method="post" class="form">
        <fieldset>
          <div class="form-line">
            <label class="label" for="mobile-num">卡号：</label>
            <div class="element clearfix">
				${bankinfo.user_bankcard }
            </div>
          </div>
          <div class="form-line">
            <label class="label" for="mobile-num">证件类型：</label>
            <div class="element clearfix">
				${bankinfo.user_icard_type }
            </div>
          </div>
          <div class="form-line">
            <label class="label" for="mobile-num">证件号：</label>
            <div class="element clearfix">
				${bankinfo.user_icard }
            </div>
          </div>
			<div class="form-line">
            <label class="label" for="mobile-num">转账金额：</label>
            <div class="element clearfix">
				<input type="text" name="fee" id="fee"/>元
            </div>
          </div>
			<div class="form-line">
            <label class="label" for="mobile-num">交易密码：</label>
            <div class="element clearfix">
				<input type="password" name=password id="password"/>
            </div>
          </div>
          
<div class="field-item_button_m" id="btnChag"><input type="button" name="Button" value="下一步" class="field-item_button" onclick="btnSubmit();"></div>
        </fieldset>
      </form>
    </div>
    <font style="font-family:'宋体'; font-size:17px; " color="red">单笔转账要求金额不低于1000元、不高于99999元，免转账手续费。低于1000元按每笔1元收取手续费，直接在额度中扣减，节假日按照银行规定来操作。</font>
</body>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
</html>
