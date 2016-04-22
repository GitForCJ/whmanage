<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
%>
<html>
<head>
<title>万汇通</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
<link REL="stylesheet"  TYPE="text/css"  HREF="../css/common.css">
<script type="text/javascript" src="../js/util.js"></script>
<script language="JavaScript">
	showMessage("${mess}");
	
	function goback(){
	  document.URL="<%=path %>/lt/payment.jsp"
	}
	function check(){
		with(document.forms[0]){
			if(payFee.value.trim().length==0){
				alert("交易金额不能为空");
				payFee.focus();
				return false;
			}
			 if(compareFloat(payFee.value.trim(),10)<0){
		    	alert("交易金额不能小于10元!");
				form1.fee.focus();
				return (false);
 		  	}
			if(compareFloat(payFee.value.trim(),500)>0){
				alert("交易金额必须小于500元!");
				payFee.focus();
				return false;
			}
	
		 if(confirm("确认缴费,号码:"+document.getElementById("haoma").value+"，金额为："+payFee.value+"元")){
			 document.getElementById("cz").value="充值中...";
		     document.getElementById("cz").disabled="disabled";
          target="_self";
          submit(); 
      }
		} 
	  
	}
</script>
<style>
#tabbox{ width:100%; overflow:hidden; margin:0px auto;}
#tabs_title { background:#f8f8f8; height:40px; border-bottom:0px solid #e1e2e2; width:100%; float:right;}
.tabs_wzdh {  float:left;font-family:'宋体'; font-size:14px; }
</style>
</head>
<body rightmargin="0" leftmargin="0" topmargin="0" >
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh" >
    <li></li>
      <li><B>广东联通账单查询</B></li>
<li></li>
	</ul>
  </div>
</div>
<br/>
<div  style="font-size: 16px;">
<html:form action="/dianxin/telecom.do?method=UserPayMent" method="post">
	<input type="hidden" name="tradeObject" id="haoma" value="${phonePayForm.tradeObject}">
	<table width="60%" border="0" align="center" cellpadding="0" cellspacing="0" >
	    <tr>
		<td>
			<table width="100%" border="0" align="center" >
<tr>
		<td width="50%" height="28" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易类型：</td>
		<td width="50%" align="left"  height="35">联通缴费</td>
	</tr>
	<tr>
		<td width="50%" height="28" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户号码：</td>
		<td width="50%" align="left"  height="35">${phonePayForm.tradeObject}</td>
	</tr>
	<tr>
		<td width="50%" height="28" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账户余额：</td>
		<td width="50%" align="left"  height="35">${phonePayForm.totalFee}元</td>
		<td><input type="hidden" name="totalFee"  value="${phonePayForm.totalFee}"></td>
	</tr>
	<tr>
		<td width="50%" height="28" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本次充值金额：</td>
		<td width="50%" align="left"  height="25">
			<c:if test="${bool==true}">
				<input style="font-family:'宋体'; font-size:20px; font-weight:bold;width: 80px;" type="text" name="payFee"   ${readonly}  value="0">元				
			</c:if>
			<c:if test="${bool==false}">
				<input style="font-family:'宋体'; font-size:20px; font-weight:bold;width: 80px;" type="text" name="payFee"   ${readonly}  value="${fn:substring(phonePayForm.totalFee,1,100)}">元
			</c:if>
		</td>
	</tr>
<tr>
	<tr>
		<td align="center" height="35">
			<input type="button" id="cz" name="Button" value="充值" onclick="check()" style="background:url(../images/button_out.png);display:block; border:0px; width:85px; height:32px; color:#fff; font-weight:bold;">
</td>
<td>																	
<input type="reset" name="Button2" value="返回" onclick="window.location='<%=path %>/lt/payment.jsp'" style="background:url(../images/button_out.png);display:block; border:0px; width:85px; height:32px; color:#fff; font-weight:bold;">
<input type="hidden" name="numType" value="${phonePayForm.numType}"/>
		</td>
	</tr>
	        </table>
	  	</td>
	  </tr>
	  </table>
</html:form>  
</div>
</body>
</html>
