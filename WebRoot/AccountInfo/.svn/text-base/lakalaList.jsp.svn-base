<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wlt.webm.rights.form.SysUserForm"%>
<jsp:useBean id="bi" scope="page" class="com.wlt.webm.business.bean.BiProd" />
<%
  String path = request.getContextPath();
  SysUserForm userSession = (SysUserForm) request.getSession().getAttribute("userSession");
  if(null==userSession||userSession.getUsername()==null){
      response.sendRedirect(path+"/rights/wltlogin.jsp");
      return ;
	} 
 List list=bi.getLaklaList(userSession.getUserno());
 request.setAttribute("arryList",list);
%>
<html>
<head>
<title>万汇通</title>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href=../css/common.css>
<style type="text/css">
	a:hover{
		color:red;
	}
</style>
<script type="text/javascript" src="../js/util.js"></script>
<script language='javascript' src='../js/jquery.js'></script>
<SCRIPT type=text/javascript>

// 去空格
String.prototype.Trim = function() 
{ 
	return this.replace(/(^\s*)|(\s*$)/g, ""); 
} 

function funShow(termId)
{
	window.location.href="<%=path%>/AccountInfo/lakalaListTwo.jsp?termId="+termId;
	return;
}
</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>拉卡拉转账</li></ul>
  </div>
</div>

<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line" style="width:100%;text-align:left;">
<thead>
<tr height="25" class="tr_line_bottom">
<th style="border:1px solid #cccccc;">拉卡拉终端编号</th>
<th style="border:1px solid #cccccc;">万汇通系统编号</th>
<th style="border:1px solid #cccccc;">万汇通登录账号</th>
<th style="border:1px solid #cccccc;">万汇通资金账号</th>
<th style="border:1px solid #cccccc;">绑定日期</th>
<th style="border:1px solid #cccccc;">操作</th>
</tr>
</thead>
<tbody id="tab">
<c:forEach items="${arryList}" var="li" >
<tr>
<td style="border:1px solid #cccccc;">${li[0]}</td>
<td style="border:1px solid #cccccc;">${li[1]}</td>
<td style="border:1px solid #cccccc;">${li[2]}</td>
<td style="border:1px solid #cccccc;">${li[3]}</td>
<td style="border:1px solid #cccccc;">${fn:substring(li[4], 0, 4)}/${fn:substring(li[4], 4, 6)}/${fn:substring(li[4], 6, 8)}&nbsp;${fn:substring(li[4], 8,10)}:${fn:substring(li[4], 10,12)}:${fn:substring(li[4], 12,14)}</td>
<td style="border:1px solid #cccccc;text-decoration:underline;"><a href="javascript:funShow('${li[0]}')">转款记录</a></td>
</tr>
</c:forEach>
</table>
<br/>
<br/>
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">1、账户加款精确到小数点后三位。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">2、T+1/结算费率为：0.38%，T+0/结算加收0.05%手续费。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">3、自动结算时间为：T+1/06:00自动结算。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">4、每天23:00后刷卡交易为第二天交易，自动结算前23:00不能手动提取。</li>
<li style="list-style:none;">&nbsp;</li>
<li style="list-style:none;">5、用户刷卡消费后，请留意帐户资金变动，可在“拉卡拉交易”菜单中查询刷卡金额，或者在财务查询中查询刷卡金额到帐记录。</li>
<li style="list-style:none;">&nbsp;</li>
</ul>
</div>
</body>
</html>