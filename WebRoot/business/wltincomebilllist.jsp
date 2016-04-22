<%@ page language="java" contentType="text/html; charset=UTF-8"%>
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
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});

function checksubmit(){

	$("#form1").first();
	$("#form1").attr("action","<c:url value='/business/acctbill.do?method=incomeList'/>");
	$("#form1").submit();//提交
	
}

function excelExport(){

	$("#form1").first();
	$("#form1").attr("action","<c:url value='/business/acctbill.do?method=excelExportIncomeList'/>");
	$("#form1").submit();//提交
	
}

</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>收益统计</li></ul>
  </div>
</div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<form id="form1" name="form1" action="<%=path %>/business/acctbill.do?method=incomeList" method="post">
<div class="header">
	<div class="topCtiy clear">
		<ul>
		  <li>
		  <div class="sub-input"><a class="sia-2 selhover" id="sl-areacode" href="javascript:void(0);">请选择省份</a></div>
		  <input nname="nname" name="areacode" id="areacode" defaultsel="${requestScope.areaSel }" type="hidden" value="${acctbill.areacode }" />
		  </li>
		   <li>
		  <div class="sub-input"><a class="sia-2 selhover" id="sl-ucity" href="javascript:void(0);">请选择地市</a></div>
		  <input nname="nname" name="user_site_city" id="ucity" defaultsel="" type="hidden" value="${acctbill.user_site_city }" />
		  </li>
		  <li>
		  <div class="sub-input"><a class="sia-2 selhover" id="sl-tradetype" href="javascript:void(0);">请选择交易类型</a></div>
		  <input nname="nname" name="tradetype" id="tradetype" defaultsel="${requestScope.tradeSel }" type="hidden" value="${acctbill.tradetype }" />
		  </li>
		</ul>
		<li><input type="button" name="Button" value="查询" class="field-item_button" onClick="checksubmit()"></li>
		<li><input type="button" name="Button" value="导出" class="field-item_button" onClick="excelExport();"></li>
		 <li class="i2" id="changeCity">更多条件</li>
	</div>
</div>
<div class="selCity" id="allCity" style="DISPLAY: none">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>交易流水号：</td>
    <td><input name="dealserial" type="text" value="${requestScope.acctbill.dealserial }"/></td>
    <td>开始日期：</td>
    <td><input class="Wdate" name="startDate" type="text" onClick="WdatePicker()" value="${requestScope.acctbill.startDate }"></td>
    <td>结束日期：</td>
    <td><input class="Wdate" name="endDate" type="text" onClick="WdatePicker()" value="${requestScope.acctbill.endDate }"></td>
  </tr>
  <tr>
    <td>交易账号：</td>
    <td><input name="tradeaccount" type="text" value="${requestScope.acctbill.tradeaccount }"/></td>
<c:if test="${requestScope.roleType=='0'||requestScope.roleType=='1'}">
<td>网点账户:</td>
    <td><input name="username" type="text" value="${requestScope.acctbill.username }"/></td>
</c:if>
    <td></td>
    <td></td>
  </tr>
</table>
<div class="none"><A id=foldin href="javascript:;">收起</A></div>
</div>
<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line">
<thead>
<tr height="25" class="tr_line_bottom">
<%--<th>序号</th>--%>
<th>支付账号</th>
<th>代理姓名</th>
<th>下级用户</th>
<th>账户类型</th>
<th>省份</th>
<th>地市</th>
<th>交易类型</th>
<th>状态</th>
<th>交易流水号</th>
<th>交易账号</th>
<th>交易时间</th>
<th>交易金额(元)</th>
<th>收益金额</th>
<th>受理平台</th>
</tr>
</thead>
<tbody id="tab">
<logic:iterate id="ordinfo" name="orderList" indexId="i">
<tr>
<%--<td class="td_line_left">${i + 1}</td>--%>
<td class="td_line">${ordinfo[2]}</td>
<td class="td_line">${ordinfo[15]}</td>
<td class="td_line">${ordinfo[19]}</td>
<td class="td_line">${ordinfo[13]}</td>
<td class="td_line">${ordinfo[14]}</td>
<td class="td_line">${ordinfo[16]}</td>
<td class="td_line">${ordinfo[5]}</td>
<td class="td_line">${ordinfo[7]}</td>
<td class="td_line">${ordinfo[1]}</td>
<td class="td_line">${ordinfo[2]}</td>
<td class="td_line">${ordinfo[3]}</td>
<td class="td_line">${ordinfo[4]/100}</td>
<td class="td_line">${ordinfo[4]/100}</td>
<td class="td_line">${ordinfo[18]}</td>
</tr>
</logic:iterate>
<tr><td colspan="8">&nbsp;</td></tr>
<tr><td colspan="8" align="left">当前页交易总额：${requestScope.curTotal/100 }</td></tr>
<tr><td colspan="8" align="left">交易总额：${requestScope.totalFee/100 }&nbsp;交易笔数:${requestScope.totalCount }</td></tr>
</tbody>
<tfoot>
<tr height="30">
<td colspan="14" class="tr_line_top">
<div class="grayr">
<wlt:pager url="business/acctbill.do?method=commList${requestScope.acctbill.paramUrl}" page="${requestScope.page}" style="black" />
</div>
</td>
</tr>
</tfoot>
</table>
</form>
</body>
</html:html>