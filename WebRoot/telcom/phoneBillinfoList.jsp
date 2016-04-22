<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String numtype = request.getParameter("numType");
	System.out.println("numtype===" + numtype);
%>
<html>
	<head>
		<title>万汇通</title>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
		<link REL="stylesheet" TYPE="text/css" HREF="../css/common.css">
		<script type="text/javascript" src="../js/util.js"></script>
		<link href="../css/chongzhi_mobile.css" rel="stylesheet" type="text/css" />
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<link href="../css/style.css" rel="stylesheet" type="text/css" />
		<script language="JavaScript">
	showMessage("${mess}");
	
	function goback(){
	  document.URL="telcomPhonepay.jsp"
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
#tabbox {
	width: 100%;
	overflow: hidden;
	margin: 0px auto;
}

#tabs_title {
	background: #f8f8f8;
	height: 40px;
	border-bottom: 0px solid #e1e2e2;
	width: 100%;
	float: right;
}

.tabs_wzdh {
	float: left;
	font-family: '宋体';
	font-size: 14px;
}
</style>
	</head>
	<body rightmargin="0" leftmargin="0" topmargin="0">
		<div id="tabbox">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li></li>
					<li>
						<B>广东电信账单查询</B>
					</li>
					<li></li>
				</ul>
			</div>
		</div>
		<br />
		<div style="font-size: 16px;">
			<html:form action="/dianxin/telecom.do?method=payBill" method="post">
				<input type="hidden" name="tradeObject"
					value="${phonePayForm.tradeObject}">
				<input type="hidden" name="seqNo" value="${phonePayForm.seqNo}">
				<table width="60%" border="0" align="center" cellpadding="0"
					cellspacing="0">
					<tr >
						<td >
							<table width="100%" border="0" >
								<tr>
									<td width="50%" height="28" style="text-align:right;">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交易类型：
									</td>
									<td width="50%" align="left" height="35" style="text-align:left;">
										电信缴费
									</td>
								</tr>
								<tr>
									<td width="50%" height="28"  style="text-align:right;">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户名称：
									</td>
									<td width="50%" align="left" height="35"  style="text-align:left;">
										${phonePayForm.userName}
										<input type="hidden" name="userName"
											value="${phonePayForm.userName}">
									</td>
								</tr>
								<tr>
									<td width="50%" height="28"  style="text-align:right;">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;客户号码：
									</td>
									<td width="50%" align="left" height="35"  style="text-align:left;">
										${phonePayForm.tradeObject}
									</td>
								</tr>
								<tr>
									<td width="50%" height="28" style="text-align:right;">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账户余额：
									</td>
									<td width="50%" align="left" height="35" style="text-align:left;">
										${-(phonePayForm.totalFee/100.0)}元
										<input type="hidden" name="totalFee"
											value="${phonePayForm.totalFee}">
									</td>
								</tr>
								<tr>
									<td width="50%" height="28" style="text-align:right;">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本次充值金额：
									</td>
									<td width="50%" align="left" height="25" style="text-align:left;">
										<c:if test="${(-(phonePayForm.totalFee/100.0))>=0}">
											<input
												style="font-family: '宋体'; font-size: 20px; font-weight: bold; width: 80px;"
												type="text" name=payFee   value="0">元				
										</c:if>
										<c:if test="${(-(phonePayForm.totalFee/100.0))<0}">
											<input
												style="font-family: '宋体'; font-size: 20px; font-weight: bold; width: 80px;"
												type="text" name=payFee
												  value="${fn:substring(phonePayForm.totalFee/100.0,0,100)}">元
										</c:if>
									</td>
								</tr>
								<tr><td style="text-align:right;">&nbsp;</td><td style="text-align:left;">&nbsp;</td></tr>
								<tr>
									<td align="right" height="35">
										<input type="button" id="cz" name="Button" value="充值"
											onclick="check()"
											style="background: url(../images/button_out.png); display: block; border: 0px; width: 85px; height: 32px; color: #fff; font-weight: bold;float:right;">
									</td>
									<td>
										<input type="reset" name="Button2" value="返回"
											onclick="window.location='<%=path%>/telcom/telcomPhonepay.jsp'"
											style="background: url(../images/button_out.png); display: block; border: 0px; width: 85px; height: 32px; color: #fff; font-weight: bold;">
										<input type="hidden" name="numType" value="<%=numtype%>" />
										<input type="hidden" id="haoma" value="${phonePayForm.tradeObject}" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<br>
<div style="margin-top:5px;border:1px solid #cccccc;">
	<table width="100%" style="text-align:center;">
	 	<thead>
			<tr>
				<th style="height:25px;border:1px solid #cccccc;" >序号</th>
				<th style="height:25px;border:1px solid #cccccc;">发票项代码</th>
				<th style="height:25px;border:1px solid #cccccc;">发票项名称</th>
				<th style="height:25px;border:1px solid #cccccc;">客户号码</th>
				<th style="height:25px;border:1px solid #cccccc;">发票项费用(元)</th>
				<th style="height:25px;border:1px solid #cccccc;">帐期标识</th>
				<th style="height:25px;border:1px solid #cccccc;">运营商代码</th>
			</tr>
		</thead>
		<c:forEach  items="${arry}" var="li" varStatus="index">
			<tr>
				<td style='border:1px solid #cccccc;height:25px;'>${index.index+1}</td>
				<td style='border:1px solid #cccccc;height:25px;'>${li[0]}</td>
				<td style='border:1px solid #cccccc;height:25px;'>${li[1]}</td>
				<td style='border:1px solid #cccccc;height:25px;'>${li[2]}</td>
				<td style='border:1px solid #cccccc;height:25px;'>${li[3]}</td>
				<td style='border:1px solid #cccccc;height:25px;'>${li[4]}</td>
				<td style='border:1px solid #cccccc;height:25px;'>${li[5]}</td>
			</tr>
		</c:forEach>
	</table>
</div>
			</html:form>
		</div>
	</body>
</html>
