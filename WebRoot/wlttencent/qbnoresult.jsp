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
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="menu_table">
			<tr>
				<td width="129">
					<img src="../images/lgo_0hm_129x36.on.gif" width="129" height="33">
				</td>
				<td width="125" background="../images/menu0.on.bg.gif" nowrap>
					<div align="center">
						<a href="#" class="fontmenu">系统提示</a>
					</div>
				</td>
				<td width="11">
					<img src="../images/menu0.on.end.gif" width="11" height="33">
				</td>
				<td class="menubg">
					&nbsp;
				</td>       
			</tr>
			<tr>
				<td height="10" colspan="4" background="../images/menu.bg.gif">
					<div align="center"></div>
				</td>
			</tr>
			<tr>
				<td height="10" colspan="4" background="../images/menu.bg.gif"><br><br>
					
				<br></td>
			</tr>
		</table>
		<form>
			<table width="60%" border="0" align="center" cellpadding="0"
				cellspacing="1" class="tableoutline">
				<tr class="trcontent">
					<td colspan="4" align="center" class="fontcontitle">
						&nbsp;提示信息&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" border="0" align="center" class="tablecontent">
							<tr>
								<td width="50%" align="center">
									&nbsp;&nbsp;<img src="../images/commonTip.gif" width="25" height="25"/>&nbsp;&nbsp;
<font color="#FF0000" size="30">
<c:if test="${flag==1}">尊敬的客户:财付通转账功能暂未开放</c:if>
<c:if test="${flag==2}">尊敬的客户:请使用财付通支付</c:if>
<c:if test="${flag==3}">尊敬的客户:Q币充值功能暂未开放</c:if>
<c:if test="${flag!=2&&flag!=1&&flag!=3}">错误页面</c:if>
</font>
						</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<br><br><br>
			<p align="center">
				<font color="#0000CC">详情请联系:深圳市万恒科技有限公司</font>
			</p>
		</form>
	</body>
</html>
