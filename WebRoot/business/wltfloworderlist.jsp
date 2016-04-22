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
<title>万汇通流量补充订单</title>
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
 $("#myTable tr:odd").addClass("odd");//odd为偶数行
});

function checksubmit(a){
	if(confirm("确定进行该操作?")){
	document.getElementById("flag").value=a;
	document.forms[0].submit();
	}
}

function chang(a,b){
	var val=document.getElementById(a).value;
	if('4'==val){
		alert("只能修改为成功或者失败");
		return ;
		}
	if(confirm("确定修改该订单状态?")){
	$.post(
    	$("#rootPath").val()+"/business/order.do?method=changeflow",
      	{
      	orderid:a,
      	otype:b,
      	st:val
      	},
      	function (data){
          if(data=='0'){
        	  document.getElementById(a).disabled="disabled";
        	  alert("修改成功");
              }else if(data=='1'){
            	  alert("修改失败:参数错误");
                  }	else if(data=='2'){
                	  alert("修改失败:DB操作失败");
                      }else{
                    	  alert("修改失败:未知");
                          }
      	}
	)
	}
}
</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>流量补充</li></ul>
  </div>
</div>
<form id="form1" action="<%=path %>/business/order.do?method=flow2list" method="post">
<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" name="flag" value="" id="flag" />
<div class="header">
	<div class="topCtiy clear">
		<ul>
		  <li>
		  		<div class="sub-input"><a class="sia-2 selhover" id="sl-areacode" href="javascript:void(0);">区域</a></div>
		 	 	<input nname="nname" name="areacode" id="areacode" defaultsel="${requestScope.areaSel}" type="hidden" value="${order.areacode }" />
		  </li>
           <li>
           <div class="sub-input"><a class="sia-2 selhover" id="sl-buyid" href="javascript:void(0);">接口商</a></div>
		  	<input nname="nname" name="buyid" id="buyid" defaultsel="${requestScope.itypeSel }" type="hidden" value="${order.buyid }" />
           </li>
    <!--         <li>
            <div class="sub-input"><a class="sia-2 selhover" id="sl-state" href="javascript:void(0);">订单状态</a></div>
		  	<input nname="nname" name="state" id="state" defaultsel="订单状态[]|成功[0]|失败[1]|处理中[4]" type="hidden" value="${order.state }" />
		  </li>
-->
		  <li>
		  <div class="sub-input"><a class="sia-2 selhover" id="sl-service" href="javascript:void(0);">业务类型</a></div>
		  	<input nname="nname" name="service" id="service" defaultsel="业务类型[]|联通流量[0009]|移动流量[0010]|电信流量[0011]" type="hidden" value="${order.service }" />
		  </li>

		  <li><input type="button" name="Button" value="查询" class="field-item_button" onClick="checksubmit('0');"></li>
	 	<li><input type="button" name="Button" value="导出" class="field-item_button" onClick="checksubmit('1');"></li>
		</ul>
	</div>
</div>
<div class="selCity" id="allCity" style="DISPLAY: block;margin-top: 33px;">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
 <td>交易号码：</td>
    <td><input name="tradeobject" type="text" value="${requestScope.order.tradeobject }"/></td>
    <td>开始日期：</td>
    <td><input class="Wdate" name="startDate" type="text" onClick="WdatePicker()" value="${requestScope.order.startDate }"></td>
    <td>结束日期：</td>
    <td><input class="Wdate" name="endDate" type="text" onClick="WdatePicker()" value="${requestScope.order.endDate }"></td>
  </tr>
<c:if test="${(sessionScope.userSession.roleType == 0) || (sessionScope.userSession.roleType == 1)}">
  <tr>
 <td>内部订单号</td>
    <td><input name="exp1" type="text" value="${requestScope.order.exp1 }"/></td>
<td>客户订单号</td>	
<td><input name="dailishang" type="text" value="${requestScope.order.dailishang }"/></td>      
<td>系统编号</td>
    <td><input name="username" type="text" value="${requestScope.order.username }"/></td>
</tr>
</c:if>
</table>
<div class="none"><A id=foldin href="javascript:;" style="cursor: default;">收起</A></div>
</div>
<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line">
<thead>
<tr height="25" class="tr_line_bottom">
<th>说明(状态)</th>
<th>号码</th>
<th>渠道商</th>
<th>内部订单号</th>
<th>交易时间</th>
<th>结束时间</th>
</tr>
</thead>
<tbody id="tab">
<c:if test="${! empty orderList}">
<logic:iterate id="ordinfo" name="orderList" indexId="i">
<tr>
<td class="td_line">${ordinfo[0]}
(<select <c:if test="${ordinfo[7]=='成功'||ordinfo[7]=='失败'}">disabled="disabled"</c:if>  name="st" id="${ordinfo[1]}" onchange="chang('${ordinfo[1]}','${ordinfo[9]}');" style="color: red;">
<option value="0" <c:if test="${ordinfo[7]=='成功'}">selected="selected"</c:if>>成功</option>
<option value="1" <c:if test="${ordinfo[7]=='失败'}">selected="selected"</c:if>>失败</option>
<option value="4" <c:if test="${ordinfo[7]=='处理中'}">selected="selected"</c:if>>处理中</option>
</select>)
</td>
<td class="td_line">${ordinfo[2]}</td>
<td class="td_line">${ordinfo[10]}</td>
<!--  <td class="td_line">${ordinfo[4]}</td>-->
<td class="td_line">${ordinfo[1]}</td>
<td class="td_line">${ordinfo[5]}</td>
<td class="td_line">${ordinfo[6]}</td>
</tr>
</logic:iterate>
<tr><td colspan="8">&nbsp;</td></tr>
<tr><td colspan="8" align="left">
	交易总笔数：${requestScope.totalFee}
	</td>
</tr>
</c:if>
</tbody>
<tfoot>
<tr height="30">
<td colspan="9" class="tr_line_top">
<div class="grayr">
<wlt:pager url="business/order.do?method=flow2list${requestScope.order.paramUrl}" page="${requestScope.page}" style="black" />
</div>
</td>
</tr>
</tfoot>
</table>
</form>
</body>
</html:html>