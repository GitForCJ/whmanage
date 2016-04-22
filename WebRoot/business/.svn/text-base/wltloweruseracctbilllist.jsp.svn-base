<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

 .odd{ background-color:#A3A3A3;}
 .even{background-color:#A3A3A3;}
</style>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	//$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
 $("#myTable tr:odd").addClass("odd");//odd为偶数行
 // $("#myTable tr:even").addClass("even")//even为偶数行
});

function checksubmit(){

	$("#form1").first();
	$("#form1").attr("action","<c:url value='/business/acctbill.do?method=loweruserlist'/>");
	$("#chaxun").attr("disabled",false);
	$("#form1").submit();//提交
	funLoad();
}
function funLoad()
{
	if(!document.getElementById("load"))
	{
		var body=document.getElementsByTagName('body')[0];
		var left=(document.body.offsetWidth-350)/2;
		var div="<div id='load' style='border:1px solid black;background-color:white;position:absolute;top:280px;left:"+left+"px;width:350px;height:90px;z-Index:999;text-align:center;font-weight:bold;'><div style='border-bottom:1px solid #cccccc;height:25px;line-height:25px;text-align:left;padding-left:10px;'>温馨提示</div><div style='width:100%;height:65px;line-height:65px;'><img src='<%=path%>/images/load.gif' />&nbsp;数据加载中，请稍后，，，，</div></div>";
		body.innerHTML=body.innerHTML+div;
	}
}
function excelExport(){

	$("#form1").first();
	$("#form1").attr("action","<c:url value='/business/acctbill.do?method=lowerUserExcelExportList'/>");
	$("#daochu").attr("disabled",false);
	$("#form1").submit();//提交
	
}



</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>账务查询</li></ul>
  </div>
</div>
<form id="form1" name="form1" action="<%=path %>/business/acctbill.do?method=loweruserlist" method="post">
<div class="header">
	<div class="topCtiy clear">
		<ul>
<c:if test="${(sessionScope.userSession.roleType == 0) || (sessionScope.userSession.roleType == 1)}">
		  <li>
		  <c:if test="${sessionScope.userSession.roleType == 0}">
		  <div class="sub-input"><a class="sia-2 selhover" id="sl-areacode" href="javascript:void(0);">区域</a></div>
		  <input nname="nname" name="areacode" id="areacode" defaultsel="${requestScope.areaSel }" type="hidden" value="${acctbill.areacode }" />
		  </c:if>
		  </li>
</c:if>
		  <li>
		  <div class="sub-input"><a class="sia-2 selhover" id="sl-tradetype" href="javascript:void(0);">交易类型</a></div>
		  <input nname="nname" name="tradetype" id="tradetype" defaultsel="${requestScope.tradeSel }" type="hidden" value="${acctbill.tradetype }" />
		  </li>
           <li>
           <div class="sub-input"><a class="sia-2 selhover" id="sl-pay_type" href="javascript:void(0);">账目类别</a></div>
		   <input nname="nname" name="pay_type" id="pay_type" defaultsel="账目类别[]|支出[1]|存入[2]|" type="hidden" value="${acctbill.pay_type }" />
           </li>
           <li>
           <div class="sub-input"><a class="sia-2 selhover" id="sl-state" href="javascript:void(0);">状态</a></div>
		   <input nname="nname" name="state" id="state" defaultsel="状态[]|正常[0]|取消[1]" type="hidden" value="${acctbill.state }" />
		  </li>
			<li>
		  <div class="sub-input"><a class="sia-2 selhover" id="sl-dailishang" href="javascript:void(0);">代理商</a></div>
		  	<input nname="nname" name="dailishang" id="dailishang" defaultsel="${requestScope.dailishangSel}" type="hidden" value="${acctbill.dailishang }" />
		  </li>	
		</ul>
		<c:if test="${(sessionScope.userSession.roleType == 0) || (sessionScope.userSession.roleType == 1)}">
			<br/>
		</c:if>
		<li><input id="chaxun" type="button" name="Button" value="查询" class="field-item_button" onClick="checksubmit()"></li>
		<li><input id="daochu" type="button" name="Button" value="导出" class="field-item_button" onClick="excelExport();"></li>
	</div>
</div>
<div class="selCity" id="allCity" style="DISPLAY: block;margin-top: 35px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>账号类型:</td>
    <td>
	<div class="sub-input"><a class="sia-2 selhover" id="sl-acctType" href="javascript:void(0);">账号类型</a></div>
    <input nname="nname" name="acctType" id="acctType" defaultsel="账号类型[]|押金账户[01]|佣金账户[02]|冻结账户[03]" type="hidden" value="${acctbill.acctType }" />
	</td> 
    <td>开始日期：</td>
    <td><input class="Wdate" name="startDate" type="text" onClick="WdatePicker()" value="${requestScope.acctbill.startDate }"></td>
    <td>结束日期：</td>
    <td><input class="Wdate" name="endDate" type="text" onClick="WdatePicker()" value="${requestScope.acctbill.endDate }"></td>
</tr>
<c:if test="${(sessionScope.userSession.roleType == 0) || (sessionScope.userSession.roleType == 1)}">
  <tr>
    <td>登录账号：</td>
    <td><input name="tradeaccount" type="text" value="${requestScope.acctbill.tradeaccount }"/></td>
  </tr>
</c:if>
</table>
<div class="none"><A id=foldin href="javascript:;" style="cursor: default;">收起</A></div>
</div>
<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line">
<thead>
<tr height="25" class="tr_line_bottom">
<th>交易账号</th>
<th>交易时间</th>
<c:if test="${(sessionScope.userSession.roleType == 0) || (sessionScope.userSession.roleType == 1)|| (sessionScope.userSession.roleType == 3)|| (sessionScope.userSession.roleType == 4)}">
<th>交易金额(元)</th>
</c:if>
<c:if test="${(sessionScope.userSession.roleType ==2)}">
<th>提成佣金(元)</th>
</c:if>
<th>账号余额(元)</th>
<th>交易描述</th>
<th>状态</th>
</tr>
</thead>
<tbody id="tab">
<logic:iterate id="ordinfo" name="orderList" indexId="i">
<tr>
<td class="td_line">${ordinfo[2]}</td>
<td class="td_line">${ordinfo[3]}</td>

<c:if  test="${ordinfo[13]=='支出'}">
<td class="td_line">-${ordinfo[5]/1000.0}</td>
</c:if>
<c:if  test="${ordinfo[13]!='支出'}">
<td class="td_line">${ordinfo[5]/1000.0}</td>
</c:if>


<td class="td_line">${ordinfo[10]/1000.0}</td>


<td class="td_line">${ordinfo[7]}
<c:if  test="${ordinfo[13]!='存入'}">
<font color="black">(${ordinfo[13]})</font>
</c:if>
<c:if  test="${ordinfo[13]!='支出'}">
<font color="red">(${ordinfo[13]})</font>
</c:if>
</td>
<td class="td_line">${ordinfo[8]}</td>

</td>
</tr>
</logic:iterate>
<tr><td colspan="8">&nbsp;</td></tr>
<tr><td colspan="8" align="left">当前页交易总额(元)：${requestScope.curTotal }</td></tr>
<tr><td colspan="8" align="left">交易总额(元)：<fmt:formatNumber value="${requestScope.totalFee }" pattern="#0.000"/>&nbsp;交易笔数:${requestScope.totalCount }</td></tr>
</tbody>
<tfoot>
<tr height="30">
<td colspan="9" class="tr_line_top">
<div class="grayr">
<wlt:pager url="business/acctbill.do?method=loweruserlist${requestScope.acctbill.paramUrl}" page="${requestScope.page}" style="black" />
</div>
</td>
</tr>
</tfoot>
</table>
</form>
</body>
</html:html>