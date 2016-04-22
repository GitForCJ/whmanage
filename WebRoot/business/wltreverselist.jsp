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
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
	//$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
</SCRIPT>
</head>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>交易处理</li></ul>
  </div>
</div>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<form action="<%=path %>/business/order.do?method=listReverse" method="post">
<div class="header">
	<div class="topCtiy clear">
		<ul>
		  <li>
		  <c:if test="${sessionScope.userSession.roleType == 0}">
		  	<div class="sub-input"><a class="sia-2 selhover" id="sl-areacode" href="javascript:void(0);">请选择区域</a></div>
		  	<input nname="nname" name="areacode" id="areacode" defaultsel="${requestScope.areaSel }" type="hidden" value="${order.areacode }" />
		  </c:if>
		  </li>
           <li>
           <div class="sub-input"><a class="sia-2 selhover" id="sl-buyid" href="javascript:void(0);">请选择业务类型</a></div>
		  	<input nname="nname" name="buyid" id="buyid" defaultsel="请选择业务类型[]|电信充值[0001]|联通充值[0003]|移动充值[0002]|Q币[0005]|游戏币[0006]|支付宝[0008]" type="hidden" value="${requestScope.order.buyid}" />     
          </li>
		  <li>
		</ul>
		<li><input name="提交" type=submit value="查询" class="field-item_button"/></li> 
</div>
</div>
<div class="selCity" id="allCity" style="DISPLAY: block">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>交易流水号：</td>
    <td><input name="tradeserial" type="text" value="${requestScope.order.tradeserial}"/></td>
    <td>开始日期：</td>
    <td><input class="Wdate" name="startDate" type="text" onClick="WdatePicker()" value="${requestScope.order.startDate }"></td>
  </tr>
  <tr>
    <td>交易账号：</td>
    <td><input name="tradeobject" type="text" value="${requestScope.order.tradeobject}"/></td>
  <td>结束日期：</td>
    <td><input class="Wdate" name="endDate" type="text" onClick="WdatePicker()" value="${requestScope.order.endDate }"></td>
  </tr>
</table>
<div class="none"><A id=foldin href="javascript:;">收起</A></div>
</div>
<table id="myTable">
<thead>
<tr>
<th>序号</th>
<th>交易类型</th>
<th>订单/交易(号)</th>
<th>交易时间/金额</th>
<th>状态</th>
<th>操作</th>
<th>操作</th>
<th>操作</th>

</tr>
</thead>
<tbody id="tab">
<logic:iterate id="ordinfo" name="orderList" indexId="i">
<tr>
<td>${i + 1}</td>
<td>${ordinfo[5]}</td>
<td>${ordinfo[1]}<br/>${ordinfo[2]}</td>
<td>${ordinfo[3]}<br/>${ordinfo[4]/1000}</td>
<td>${ordinfo[6]}</td>
<td>
<a href="<%=path %>/business/mobileCharge.do?method=reverse&ordid=${ordinfo[1]}&ordno=${ordinfo[8]}" >冲正</a>
</td>
<td>
<a href="<%=path %>/business/mobileCharge.do?method=returnFee&ordid=${ordinfo[1]}&ordno=${ordinfo[3]}">退费</a>
</td>
<td>
<a href="<%=path %>/business/mobileCharge.do?method=success&ordid=${ordinfo[1]}&ordno=${ordinfo[3]}">成功</a>
</td>
</tr>
</logic:iterate>
</tbody>
<tfoot>
<tr>
<td colspan="8">
<div class="grayr">
<wlt:pager url="business/order.do?method=listReverse${order.paramUrl}" page="${requestScope.page}" style="black" />
</div>
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
<div style="padding-left:40px;padding-top:10px;margin-left: 20px;font-size: 13px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
	<ul>
	<li style="list-style:none;">1、只对选择了广东电信移动联通菜单发起的交易号码进行冲正，广东电信预付费24小时冲正有效，后付费号码6小时有效，广东移动和联通号码24小时内有效。</li>
	<li style="list-style:none;">&nbsp;</li>
	</ul>
	</div>
</form>
</body>
</html:html>