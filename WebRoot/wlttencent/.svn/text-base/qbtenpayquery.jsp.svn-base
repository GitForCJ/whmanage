<%@ page contentType="text/html; charset=GBK" language="java"%>
<jsp:directive.page import="com.wlt.webm.db.DBConfig" />
<jsp:useBean id="userSession" scope="session"
	class="com.wlt.webm.rights.form.SysUserForm"></jsp:useBean>
<%@ page import="com.wlt.webm.util.*" %>
<%
String nowdate = Tools.getNow2();
request.setAttribute("nowdate",nowdate);
%>
<html>
	<head>
		<title>电子代办平台PC系统</title>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
		<link REL="stylesheet" TYPE="text/css" HREF="../css/common.css">
		<script type="text/javascript" src="../js/util.js"></script>
		<script type="text/javascript" src="../js/calendar.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
		<script language="javascript">
	    function payFrm() {
	    var val = directFrm.order.value;
		if (val == "" || val == 0) {
			alert("提醒:请填写订单号!");
			directFrm.order.focus();
			return false;
		}
		if (!/^[0-9A-Za-z_]*$/.test(val)) {
			alert("提醒:请填写正确的订单号!");
			directFrm.order.focus();
			return false;
		}
		document.forms[0].OK.disabled = true;
		return true;
	}

	function keydownEvent() {
		if (event.keyCode == 13) {
			event.returnValue = false;
		}
	}
</script>

	</head>

	<body rightmargin="0" leftmargin="0" topmargin="0">
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>Q币充值结果查询</li>
	</ul>
  </div>
</div>
		<form action="/jf/qb/query.do" method="post"
			name="directFrm" onSubmit="return payFrm();">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="tableoutline">
				<tr class="trcontent">
					<td colspan="4" align="center" class="fontcontitle">
						&nbsp;输入信息&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" align="center" class="tablecontent" cellspacing="5" >
							<tr>
								<td width="30%" align="right">
									订单号:&nbsp;
								</td>
								<td width="70%" align="left">
									<input type="text" name="order" size="20" maxlength="60"
										class="inputdistinct"
									    value=""
										onkeydown="keydownEvent()">
									<font color="#FF0000">*请输入充值订单号</font>
								</td>
							</tr>
							<tr>
								<td width="30%" align="right">
									选择日期:&nbsp;
								</td>
								<td width="70%" align="left">
									<input name="newstartdate" type="text" id="newstartdate" size="10"  value="${nowdate}"  onClick="ShowDate.fPopCalendar('newstartdate','newstartdate',event);" class="inputtext">
									<font color="#FF0000">* 请选择日期</font>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" align="center" class="tablecontent">
							<tr>
								<td width="50%" align="center">
									<font color="#FF0000"><br>欢迎使用Q币订单查询业务</font>
								</td>
							</tr>
							<tr>
								<td width="50%" align="center">
									<font color="#FF0000"><br>若返回未成功,请及时联系万恒客服进行确认</font>
								</td>
							</tr>
							<tr>
								<td width="50%" align="center">
									<font color="#FF0000">如有疑问请拨打4000-800-706</font>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<p align="center">
				<input type="submit" name="OK" value="提交" class="onbutton">
				<input type="reset" name="Submit" value="重写" class="onbutton">
			</p>
		</form>
	</body>
</html>
