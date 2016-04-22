<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
  String path = request.getContextPath();
%>
<html:html>
<head>
<title>万汇通</title>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href=../css/common.css>
<style type="text/css">
.tabs_wzdh { width: 100px; float:left;}
.tabs_wzdh li { height:35px; line-height:35px; margin-top:5px; padding-left:20px; font-size:14px; font-weight:bold;}
/*tree_nav*/
.tree_nav { border:1px solid #CCC; background:#f5f5f5; padding:10px; margin-top:20px;}
.field-item_button_m2 { margin:0 auto; text-align:center;}
.field-item_button2 { border:0px;width:136px; height:32px; color:#fff; background:url(../images/button_out2.png) no-repeat; cursor:pointer;margin-right:20px;}
.field-item_button2:hover { border:0px; width:136px; height:32px; color:#fff; background:url(../images/button_on2.png) no-repeat; margin-right:20px;}
</style>
<script type="text/javascript" src="../js/util.js"></script>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function funList(Number){
		window.location.href="<%=path%>/business/wlthfklistTwo.jsp?termId="+Number;
		return;
	}
</script>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>慧付款信息</li></ul>
  </div>
</div>

<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line">
<thead>
<tr height="25" class="tr_line_bottom">
<%--<th>序号</th>--%>
<th>已绑定手机号</th>
<th>资金账号</th>
<th>刷卡器编号</th>
<th>身份证号</th>
<th>绑定时间</th> 
<th>操作</th>
</tr>
</thead>
<c:if test="${orderList!=null}">
<logic:iterate id="ordinfo" name="orderList" indexId="i">
<tr>
<td style="border:1px solid #cccccc;">${ordinfo[0]}</td>
<td style="border:1px solid #cccccc;">${ordinfo[1]}</td>
<td style="border:1px solid #cccccc;">${ordinfo[2]}</td>
<td style="border:1px solid #cccccc;">${ordinfo[3]}</td>
<td style="border:1px solid #cccccc;">${ordinfo[4]}</td>
<td style="border:1px solid #cccccc;text-decoration:underline;"><a href="#" onclick="funList('${ordinfo[0]}')">转款记录</a></td>
</tr>
</logic:iterate>
</c:if>
<c:if test="${orderList==null}">
<tr height="50px"><td colspan="5" align="center" valign="middle"><font style="font-size: 18px;" color="red">暂无慧付款绑定数据</font></td></tr>
</c:if>

</table>
<div
			style="padding-left: 40px; padding-top: 10px;font-size: 13px; color: #FF6600; font-weight: bold; border: 1px solid #FF9E4d; background-color: #FFFFE5;">
			<ul>
				<li style="list-style: none;">
					1、账户加款精确到小数点后三位。 
				</li>
				<li style="list-style:none;">&nbsp;</li>
				<li style="list-style: none;">
					2、T+0/结算时收取2元/笔手续费。 
				</li>
				<li style="list-style:none;">&nbsp;</li>
				<li style="list-style: none;">
					3、自动结算时间为：T+1/06:00自动结算。
				</li>
				<li style="list-style:none;">&nbsp;</li>
			</ul>
		</div>
</body>
</html:html>