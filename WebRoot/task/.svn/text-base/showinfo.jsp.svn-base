<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>详细信息</title>
	<script language='javascript' src='<%=basePath%>js/jquery-1.7.2.min.js'></script>
	<script>
		var obj = window.dialogArguments;
		jQuery(document).ready(function(){
			$("#buyid").val(obj[0].buyid);
			$("#tradeserial").val(obj[0].tradeserial);
			$("#externalserial").val(obj[0].externalserial);
			$("#tradeobject").val(obj[0].tradeobject);
			$("#tradefee").val(obj[0].tradefee/1000);
			$("#tradetime").val(obj[0].tradetime);
			$("#StateOne").val(obj[0].StateOne);
			$("#StateTwo").val(obj[0].StateTwo);
			$("#Contrast_state").val(obj[0].Contrast_state);
		});
	</script>
  </head>
  <body>
	<table width="100%">
		<tr><td>接口商:</td><td align="left"><input id="buyid"/></td></tr>
		<tr><td>内部流水号:</td><td align="left"><input id="tradeserial"/></td></tr>
		<tr><td>外部流水号:</td><td align="left"><input id="externalserial"/></td></tr>
		<tr><td>交易号码:</td><td align="left"><input id="tradeobject"/></td></tr>
		<tr><td>交易金额:</td><td align="left"><input id="tradefee"/></td></tr>
		<tr><td>交易时间:</td><td align="left"><input id="tradetime"/></td></tr>
		<tr><td>我方状态:</td><td align="left"><input id="StateOne"/></td></tr>
		<tr><td>上游状态:</td><td align="left"><input id="StateTwo"/></td></tr>
		<tr><td>比对结果:</td><td align="left"><input id="Contrast_state"/></td></tr>
	</table>
  </body>
</html>
