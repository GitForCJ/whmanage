<%@ page language="java" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="../css/common.css" type="text/css" media="screen">
<script type="text/javascript">
	//用户退出登陆
	function logout()
	{ 
			 window.location.href("<%=request.getContextPath() %>/rights/logout2.jsp");   
	}

</script>
<TABLE cellSpacing=0 cellPadding=0 border=0 align="center" width="100%" style="background: url('../images/top_bg.gif') repeat-x;">
		<TR>
			<TD width=2%;>
				&nbsp;
			</TD>
			<TD align="left" width="56%">
				<IMG height=70 alt=logo src="<%=request.getContextPath() %>/images/logo.gif">
			</TD>
		<TD align="center">
			<TABLE height=52 cellSpacing=0 cellPadding=0 border=0 >
				<TR>
					<TD valign="top" align="right"><blockquote><span style="font-family: '黑体'; font-size: 17px;"><%@include file="showdatetime.jsp"%> </span>&nbsp;&nbsp;</blockquote></TD>
				</TR>
				<TR>
					<TD vAlign="bottom" colspan="4" class="top_font"><span class="top_username">${userSession.login }</span>，您好！欢迎使用万汇通支付平台&nbsp;&nbsp; 
					    <c:if test="${userSession.login != null}">
						<a href="javascript:logout();" style="color: #248AAB;">[返回首页]</a> 
						</c:if>
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>
