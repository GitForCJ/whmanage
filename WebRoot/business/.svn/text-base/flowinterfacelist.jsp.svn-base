<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<html:html>
<head>
	<title>万汇通</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
	<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href=../css/common.css>
	<style type="text/css">
.tabs_wzdh {
	width: 100px;
	float: left;
}

.tabs_wzdh li {
	height: 35px;
	line-height: 35px;
	margin-top: 5px;
	padding-left: 20px;
	font-size: 14px;
	font-weight: bold;
}

/*tree_nav*/
.tree_nav {
	border: 1px solid #CCC;
	background: #f5f5f5;
	padding: 10px;
	margin-top: 20px;
}

.field-item_button_m2 {
	margin: 0 auto;
	text-align: center;
}

.field-item_button2 {
	border: 0px;
	width: 136px;
	height: 32px;
	color: #fff;
	background: url(../images/button_out2.png) no-repeat;
	cursor: pointer;
	margin-right: 20px;
}

.field-item_button2:hover {
	border: 0px;
	width: 136px;
	height: 32px;
	color: #fff;
	background: url(../images/button_on2.png) no-repeat;
	margin-right: 20px;
}
</style>
	<link href="../css/selectCss.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../js/util.js"></script>
	<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script>
	<SCRIPT type=text/javascript>
	showMessage("${mess}");
	function ha_change() {
		with (document.forms[0]) {
			target = "_self";
			submit();
		}
	}
</SCRIPT>
</head>
<div id="popdiv">
	<span id="sellist"></span>
	<div style="clear: both"></div>
</div>
<body rightmargin="0" leftmargin="0" topmargin="0" class="body_no_bg">
	<form action="<%=path%>/business/prod.do?method=flowinterList" method="post">
		<table width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<strong>运营商类型:</strong>
					<input name="type" type="radio"
						<c:if test="${type==0}">checked="checked" </c:if> value="0" />
					电信
					<input name="type" type="radio"
						<c:if test="${type==1}">checked="checked" </c:if> value="1" />
					移动
					<input name="type" type="radio"
						<c:if test="${type==2}">checked="checked" </c:if> value="2" />
					联通
				</td>
				<td style="text-align: left; padding-left: 0px;">
					<input type="button" name="Button" value="查询"
						class="field-item_button" onClick="ha_change()">
				</td>
				<td style="text-align: left; padding: 0px 0px 0px 0px; width: 80px;">
					<a href="<%=path%>/business/BatchUpdateflowInterface.jsp"
						style="font-size: 13px; color: red; font-weight: bold; text-decoration: underline">增加或修改</a>
				</td>
			</tr>
		</table>
		<table id="myTable">
			<thead>
				<tr>
					<th>
						序号
					</th>
					<th>
						省份
					</th>
					<th>
						运营商
					</th>
					<th>
						接口信息
					</th>
				</tr>
			</thead>
			<tbody id="tab">
<c:forEach items="${comList}" var="li" varStatus="ok">
						<tr>
							<td>
								${ok.index+1}
							</td>
							<td>
								${li[1]}
							</td>
							<c:if test="${li[0]==0}">
								<td>
									电信
								</td>
							</c:if>
							<c:if test="${li[0]==1}">
								<td>
									移动
								</td>
							</c:if>
							<c:if test="${li[0]==2}">
								<td>
									联通
								</td>
							</c:if>
							<td>
								${li[2]}
							</td>
						</tr>
					</c:forEach>
			</tbody>
			<tfoot>
			</tfoot>
		</table>
	</form>
</body>
</html:html>