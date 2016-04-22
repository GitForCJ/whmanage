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

 .odd{ background-color:#A3A3A3;}
 .even{background-color:#A3A3A3;}
</style>
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
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
	$("#form1").attr("action","<c:url value='/business/order.do?method=listout'/>");
	$("#form1").submit();//提交
	
}

function excelExport(){

	$("#form1").first();
	$("#form1").attr("action","<c:url value='/business/order.do?method=outExcelExport'/>");
	$("#form1").submit();//导出
	
}

</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>扣款统计</li></ul>
  </div>
</div>
<form id="form1" action="<%=path %>/business/order.do?method=listout" method="post">
<div class="header">
	<div class="topCtiy clear">
	<nobr>
		<ul>
           <li>
           <div class="sub-input"><a class="sia-2 selhover" id="sl-buyid" href="javascript:void(0);">请选择接口商</a></div>
		  	<input nname="nname" name="buyid" id="buyid" defaultsel="${requestScope.itypeSel }" type="hidden" value="${order.buyid }" />
           </li>
    <!--         <li>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-state" href="javascript:void(0);">请选择状态</a></div>
		  	<input nname="nname" name="state" id="state" defaultsel="请选择订单状态[]|成功[0]|失败[1]|正在处理[2]|未扣款[3]|已扣款[4]|返销[5]|异常订单[6]" type="hidden" value="${order.state }" />
		  </li>-->
		  <li>
		  <div class="sub-input"><a class="sia-2 selhover" id="sl-service" href="javascript:void(0);">请选择业务类型</a></div>
		  	<input nname="nname" name="service" id="service" defaultsel="${requestScope.serviceSel }" type="hidden" value="${order.service }" />
		  </li>
		</ul>
		<li><input type="button" name="Button" value="查询" class="field-item_button" onClick="checksubmit()"></li>
		<li><input type="button" name="Button" value="导出" class="field-item_button" onClick="excelExport();"></li>
		</nobr>
	</div>
</div>
<div class="selCity" id="allCity" style="DISPLAY: block;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>开始日期：</td>
    <td><input class="Wdate" name="startDate" type="text" onClick="WdatePicker()" value="${requestScope.order.startDate }"></td>
    <td>结束日期：</td>
    <td><input class="Wdate" name="endDate" type="text" onClick="WdatePicker()" value="${requestScope.order.endDate }"></td>
  </tr>
</table>
<div class="none"><A id=foldin href="javascript:;" style="cursor: default;">收起</A></div>
</div>
<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line">
<thead>
<tr height="25" class="tr_line_bottom">
<%--<th>序号</th>--%>
<th>产品类型</th>
<th>接口商</th>
<th>状态</th>
<th>总交易量</th>
<th>面值总额</th>
<th>交易总额</th>
<th>交易日期</th>
</tr>
</thead>
<tbody id="tab">
<logic:iterate id="ordinfo" name="orderList" indexId="i">
<tr>
<td class="td_line">${ordinfo[0]}</td>
<td class="td_line">${ordinfo[1]}</td>
<td class="td_line">${ordinfo[2]}</td>
<td class="td_line">${ordinfo[3]}</td>
<td class="td_line">${ordinfo[4]/1000.0}</td>
<td class="td_line">${ordinfo[5]/1000.0}</td>
<td class="td_line">${ordinfo[6]}</td>
</tr>
</logic:iterate>
<tr><td colspan="8">&nbsp;</td></tr>
</tbody>
<tfoot>
<tr height="30">
<td colspan="9" class="tr_line_top">
总计:&nbsp;${str}
</td>
<!--
<td colspan="8">
<div class="grayr"><span class="disabled"> < </span><span class="current">1</span><a href="#?page=2" _fcksavedurl="#?page=2">2</a><a href="#?page=3" _fcksavedurl="#?page=3">3</a><a href="#?page=4" _fcksavedurl="#?page=4">4</a><a href="#?page=5" _fcksavedurl="#?page=5">5</a><a href="#?page=6" _fcksavedurl="#?page=6">6</a><a href="#?page=7" _fcksavedurl="#?page=7">7</a>...<a href="#?page=199" _fcksavedurl="#?page=199">199</a><a href="#?page=200" _fcksavedurl="#?page=200">200</a><a href="#?page=2" _fcksavedurl="#?page=2"> > </a></div>
<div id="page">
<a href="">首　页</a><a href="">上一页</a><a href="">下一页</a><a href="">末　页</a></div>
</td>
-->
</tr>
</tfoot>
</table>
</form>
</body>
</html:html>