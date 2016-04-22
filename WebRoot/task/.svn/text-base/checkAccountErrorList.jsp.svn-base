<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
 $("#myTable tr:odd").addClass("odd");//odd为偶数行
});
function checksubmit(){
	$("#form1").first();
	$("#form1").attr("action","<c:url value='/business/checkAccountError.do?method=list'/>");
	$("#form1").submit();//提交
	
}
//显示详细信息
function showInfo(pid,tradetime){
	$.ajax({
		type:'POST',
		url:$("#rootPath").val()+"/business/checkAccountError.do?method=getCheckAccErrorInfo&pid="+pid+"&tradetime="+tradetime,
		dataType:"json",
		cache: false,
		success: function(response){
			window.showModalDialog("../task/showinfo.jsp",response,"dialogWidth=400px;dialogHeight=300px");
		}
	});

}
function excelExport(){
	$("#form1").first();
	$("#form1").attr("action","<c:url value='/business/checkAccountError.do?method=excelExport'/>");
	$("#form1").submit();//提交
}

</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<input type="hidden" id="rootPath" value="<%=path %>"/>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>交易对账单</li></ul>
  </div>
</div>
<form id="form1" action="<%=path %>/business/checkAccountError.do?method=list" method="post">
<div class="header">
	<div class="topCtiy clear">
	<nobr>
		<ul>
           <li>
           <div class="sub-input"><a class="sia-2 selhover" id="sl-buyid" href="javascript:void(0);">接口商</a></div>
		  	<input nname="nname" name="buyid" id="buyid" defaultsel="${requestScope.itypeSel }" type="hidden" value="${checkAccountError.buyid }" />
           </li>
		</ul>
		<li><input type="button" name="Button" value="查询" class="field-item_button" onClick="checksubmit()"></li>
		<li><input type="button" name="Button" value="导出" class="field-item_button" onClick="excelExport();"></li>
		</nobr>
	</div>
</div>
<div class="selCity" id="allCity" style="DISPLAY: block;">
  <table width="20%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>交易日期：</td>
    <td align='left'><input class="Wdate" name="tradetime" type="text" onClick="WdatePicker()" value="${requestScope.checkAccountError.tradetime}"></td>
  </tr>
</table>
<div class="none"><A id=foldin href="javascript:;" style="cursor: default;">收起</A></div>
</div>
<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line">
<thead>
<tr height="25" class="tr_line_bottom">
<%--<th>序号</th>--%>
<th>接口商</th>
<th>交易号码</th>
<th>交易金额</th>
<th>交易时间</th>
<th>比对结果</th>
</tr>
</thead>
<tbody id="tab">
<logic:iterate id="check" name="checkAccountErrorList" indexId="i">
<tr>
<%--<td class="td_line_left">${i + 1}</td>--%>
<td class="td_line">${check[3]}</td>
<td class="td_line"><a href="javascript:showInfo('${check[9]}','${check[5]}');">${check[2]}</a></td>
<td class="td_line">${check[4]/1000.0}</td>
<td class="td_line">${check[5]}</td>
<td class="td_line">${check[8]}</td>
</tr>
</logic:iterate>
<tr><td colspan="9">&nbsp;</td></tr>
<tr><td colspan="9" style="text-align: left;">当前页:异常交易总额：${requestScope.curTotal/1000.0 }</td></tr>
<tr><td colspan="9" style="text-align: left;">总计:异常交易总额：${requestScope.totalFee[0]/1000.0 }&nbsp;&nbsp;异常笔数：${requestScope.totalFee[1]}</td></tr>
</tbody>
<tfoot>
<tr height="30">
	<td colspan="9" class="tr_line_top">
		<div class="grayr">
			<wlt:pager url="business/checkAccountError.do?method=list${requestScope.checkAccountError.paramUrl}" page="${requestScope.page}" style="black" />
		</div>
	</td>
</tr>
</tfoot>
</table>
</form>
</body>
</html:html>
