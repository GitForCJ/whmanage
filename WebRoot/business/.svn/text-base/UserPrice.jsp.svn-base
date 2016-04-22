<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wlt.webm.rights.form.SysUserForm"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
%>
<html>
	<head>
		<title>万汇通</title>
		<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
		<script type="text/javascript" src="../js/util.js"></script>
		<script language="javascript" type="text/javascript"
			src="../My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">
#tables td {
	padding: 0px 0px 0px 0px;
	border: 1px solid #cccccc;
	border-right: 0px;
	border-bottom: 0px;
	height: 30px;
}
</style>
		<script type="text/javascript">
	function checksubmit(){
		document.getElementById("form1").action="<%=path%>/business/acctbill.do?method=UserPrice";
		document.getElementById("form1").submit();
		funLoad()
		return ;
	}
</script>
	</head>
	<body>
		<div id="tabbox">
			<div id="tabs_title">
				<ul class="tabs_wzdh">
					<li>
						盈利明细
					</li>
				</ul>
			</div>
		</div>
		<form id="form1" action="" method="post">
			<div class="header">
				<div class="topCtiy clear" style="padding-left: 15px;">
					<li>
						<input id="chaxun" type="button" name="Button" value="查询"
							class="field-item_button" onClick="checksubmit()">
					</li>
				</div>
			</div>
			<div class="selCity" id="allCity">
				<table border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=4>
							统计日期:
							<input class="Wdate" id="indate" name="indate" type="text"
								style="width: 120px; height: 25px;" onClick="WdatePicker()"
								value="${indate}">
							至
							<input class="Wdate" id="enddate" name="enddate" type="text"
								style="width: 120px; height: 25px;" onClick="WdatePicker()"
								value="${enddate}">
						</td>
					</tr>
				</table>
			</div>
			<div
				style="width: 100%; height: 30px; text-align: right; font-weight: bold; line-height: 30px; font-size: 15px; color: red;">
				不支持跨月统计数据&nbsp;&nbsp;
			</div>
			<div id="tables" style="width: 100%; overflow-x: auto;">
				<table cellspacing="0" cellpadding="0" width="100%"
					style="font-size: 13px; border-bottom: 1px solid #cccccc; border-right: 1px solid #cccccc">
					<tr style="background-color: #E6EFF6; font-weight: bold;">
						<td style="">
							业务类型
						</td>
						<td style="">
							销售总额
						</td>
						<td style="">
							实际交易总额
						</td>
						<td style="">
							利润总额
						</td>
					</tr>
					<c:forEach items="${arryList}" var="arrList" varStatus="index">
						<c:if test="${(index.index+1)%2==0}">
							<tr style="color: red;">
						</c:if>
						<c:if test="${(index.index+1)%2!=0}">
							<tr style="color: red; border: 1px solid #cccccc">
						</c:if>
						<td style="color: black;">
							${arrList[0]}
						</td>
						<td style="">
							<fmt:formatNumber value="${arrList[1]}" pattern="#0.00" />
							&nbsp;
						</td>
						<td style="">
							<fmt:formatNumber value="${arrList[2]}" pattern="#0.00" />
							&nbsp;
						</td>
						<td style="">
							<fmt:formatNumber value="${arrList[3]}" pattern="#0.00" />
							&nbsp;
						</td>
						</tr>
					</c:forEach>
					<tr>
						<td style="color: black;">
							汇总
						</td>
						<td style="">
							<fmt:formatNumber value="${strs[0]}" pattern="#0.00" />
							&nbsp;
						</td>
						<td style="">
							<fmt:formatNumber value="${strs[1]}" pattern="#0.00" />
							&nbsp;
						</td>
						<td style="">
							<fmt:formatNumber value="${strs[2]}" pattern="#0.00" />
							&nbsp;
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
