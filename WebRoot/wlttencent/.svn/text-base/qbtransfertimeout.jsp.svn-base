<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f"%>
<html>
	<head>
		<title>万汇通</title>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK">
		<link REL="stylesheet" TYPE="text/css" HREF="../css/common.css">
	</head>

	<body rightmargin="0" leftmargin="0" topmargin="0">
		
		<form action="/jf/wlttencent/qbpay.jsp" method="get" name="directFrm"
			onSubmit="return payFrm();">
<c:if test="${flag==1}">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="tableoutline">
				<tr class="trcontent">
					<td colspan="4" align="center" class="fontcontitle">
						&nbsp;Q币充值处理中,请尽快复制订单号,联系客服进行查询&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" align="center" class="tablecontent">
							<tr>
								<td width="50%" align="left">
									&nbsp;&nbsp;<img src="../images/commonTip.gif" width="25" height="25"/>&nbsp;&nbsp;<font color="#FF0000">QQ账号:${qq}</font>
								</td>
							</tr>
							<tr>
								<td width="50%" align="left">
									&nbsp;&nbsp;<img src="../images/commonTip.gif" width="25" height="25"/>&nbsp;&nbsp;<font color="#FF0000"> 订单号:${tranid}</font>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<p align="center">
				<a href="/jf/wlttencent/qbtenpayquery.jsp" target="_self">确定</a>
			</p>
</c:if>
<c:if test="${flag==0}">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" >
				<tr class="trcontent">
					<td colspan="4" align="center" class="fontcontitle">
						&nbsp;连接服务异常&nbsp;
					</td>
				</tr>
				<tr height="20">
				<td width="60%" align="center">
					无法正常连接Q币充值服务,请稍后再试
				</td>
				</tr>
			</table>
			<p align="center">
				<a href="/jf/wlttencent/qbpay.jsp" target="_self">确定</a>
			</p>
</c:if>
<c:if test="${flag==2}">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="tableoutline">
				<tr class="trcontent">
					<td colspan="4" align="center" class="fontcontitle">
						&nbsp;查询有误&nbsp;
					</td>
				</tr>
				<tr class="trcontent">
					<td colspan="4" align="center">
						&nbsp;${order_status}&nbsp;
					</td>
				</tr>
			</table>
			<p align="center">
				<a href="/jf/wlttencent/qbtenpayquery.jsp" target="_self">返回</a>
			</p>
</c:if>
<c:if test="${flag==3}">
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="tableoutline">
				<tr class="trcontent">
					<td colspan="4" align="center" class="fontcontitle">
						&nbsp;Q币交易查询结果&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" align="center" class="tablecontent">
							<tr>
								<td width="50%">
									&nbsp;&nbsp;<img src="../images/commonTip.gif" width="25" height="25"/>&nbsp;&nbsp;<font color="#FF0000">订单号:${tran_seq}</font>
								</td>
							</tr>
							<tr>
								<td width="50%" >
									&nbsp;&nbsp;<img src="../images/commonTip.gif" width="25" height="25"/>&nbsp;&nbsp;<font color="#FF0000">订单状态:${order_status} </font>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<p align="center">
				<a href="/jf/wlttencent/qbtenpayquery.jsp" target="_self">确定</a>
			</p>
</c:if>

			<br><br>
			<p align="center">
				<font color="#0000CC">详情请联系:深圳市万恒科技有限公司</font>
			</p>
		</form>
	</body>
</html>
