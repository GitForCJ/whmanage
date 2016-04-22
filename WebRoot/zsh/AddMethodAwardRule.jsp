<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<%
  String path = request.getContextPath();
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script language="javascript" type="text/javascript" src="../js/util.js"></script>
<SCRIPT type=text/javascript>
showMessage("${mess}");

function check(){
  	with(document.forms[0]){
		/* areacope为区域区号，也即体系层次表中的节点区号 */
		if(minmoney.value == ""||maxmoney.value==""||rate.value==""){
		alert("请输入正确数据!");
		return;
		}
		var myreg = /^[1-9]*[0-9]*$/;
		if(!myreg.test(minmoney.value)||!myreg.test(maxmoney.value)){
		alert("请输入正确的数据 !");
		return;
		}
		
		if(!isInt(minmoney.value) || !isInt(maxmoney.value))
		{
			alert("金额必须是整数!");
			return ;
		}
		
		if(parseFloat(minmoney.value)>=parseFloat(maxmoney.value)){
		alert("最小金额不能大于或等于最大金额!");
		return;
		}
		if(!isFloat($("#bl").val()) && !isInt($("#bl").val()))
		{
			alert("只能输入整数和小数!");
			return;
		}
		$.ajax({
			type:"post",
			url:"<%=path%>/MonthAwardRule/showMonthAwardRule.do?method=getVerifyMoney&userno="+$("#userno").val()+"&min="+minmoney.value+"&max="+maxmoney.value+"&faceValue="+$('input[name="facevalue"]:checked').val(),
			dataType:"json",
			success:function(obj){
				if(!obj)
				{
					alert("佣金添加区间段重复!");
					return ;
				}
				else
				{
					submit();
				}
			}
		}); 
	}
}

function funHistory()
{
	window.location.href="<%=path%>/MonthAwardRule/showMonthAwardRule.do?method=showAwardsAction";
	return;
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<form  method="post" action="<%=path%>/MonthAwardRule/showMonthAwardRule.do?method=saveMonthAwardRule">
<div class="pass_main">
	<div class="pass_title_1">新增交易佣金规则</div>
    <div class="pass_list">
    	<ul>
            <li><strong>登陆账号：</strong><span>
            <select name="userno" id="userno" style="width:145px;height:23px;">
	          <c:forEach items="${arryList}" var="arr">
				<option value="${arr[2]};${arr[0]}">${arr[1]}</option>         	
	          </c:forEach>
            </select>
            </span></li>
            <li><strong>金额最小值：</strong><span><input name="minmoney" type="text" /></span></li>
            <li><strong>金额最大值：</strong><span><input name="maxmoney" type="text" /></span></li>
            <li><strong>奖励百分比：</strong><span><input id="bl" name="rate" type="text" /></span></li>
             <li>
             	<strong>条码选择：</strong>
             	<input type="radio" name="facevalue" value="0" style="height:15px;width:25px;" checked/>无&nbsp;
             	<input type="radio" name="facevalue" value="1" style="height:15px;width:25px;"/>条码一&nbsp;
             	<input type="radio" name="facevalue" value="2" style="height:15px;width:25px;"/>条码二&nbsp;
             	<input type="radio" name="facevalue" value="3" style="height:15px;width:25px;"/>条码三&nbsp;
             	<input type="radio" name="facevalue" value="4" style="height:15px;width:25px;"/>条码四
             </li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="取消" id="checkCode" onClick="javascript:funHistory()" class="field-item_button" />
   </div>
</div>
</form>
</body>
</html>