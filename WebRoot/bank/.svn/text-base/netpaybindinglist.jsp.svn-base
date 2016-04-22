<%@ page language="java" contentType="text/html; charset=utf-8"%>
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
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
 $("#myTable tr:odd").addClass("odd");//odd为偶数行
 // $("#myTable tr:even").addClass("even")//even为偶数行
});

function checksubmit(){

	$("#form1").first();
	$("#form1").attr("action","<c:url value='/business/acctbill.do?method=netpayList'/>");
	$("#chaxun").attr("disabled",false);
	$("#form1").submit();//提交
	
}

function excelExport(){

	$("#form1").first();
	$("#form1").attr("action","<c:url value='/business/acctbill.do?method=excelExportList'/>");
	$("#form1").submit();//提交
	
}

function ha_update(a,b,c,d){
	  with(document.forms[0]){
            if(confirm("确认对"+a+"进行验证？操作不可逆请谨慎")){
            sendMsg(a,b,c,d);
	  		}else{
	  		return false;
	  		}
	  }
	}


function sendMsg(a,b,c,d){
	$.post(
    	$("#rootPath").val()+"/business/prod.do?method=awardsOP",
      	{
      	login:a,
      	facct:b,
      	money:d,
      	id:c
      	},
      	function (data){
      		if(data == 0){
      		 document.getElementById(c).value="验证";
		     document.getElementById(c).disabled="disabled";
		     document.getElementById(c).style.color="gray";
      			alert("验证成功");
      		}else{
      		 document.getElementById(c).value="验证";
		     document.getElementById(c).disabled="";
      			alert("验证失败,稍后再试");
      		}
      	}
	)
}


</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>奖励详情</li></ul>
  </div>
</div>
<form id="form1" name="form1" action="<%=path %>/business/acctbill.do?method=netpayList" method="post">
<input type="hidden" id="rootPath" value="<%=path %>"/>
<div class="header">
	<div class="topCtiy clear">
		<ul>
<li>开始日期:<input class="Wdate" name="startDate" type="text" onClick="WdatePicker()" value="${requestScope.acctbill.startDate }"></li>
<li>结束日期:<input class="Wdate" name="endDate" type="text" onClick="WdatePicker()" value="${requestScope.acctbill.endDate }"></li>
           <li>
           <div class="sub-input"><a class="sia-2 selhover" id="sl-state" href="javascript:void(0);">状态</a></div>
		   <input nname="nname" name="state" id="state" defaultsel="状态[]|未验证[0]|已验证[1]" type="hidden" value="${acctbill.state }" />
		  </li>
		</ul>
		<li><input id="chaxun" type="button" name="Button" value="查询" class="field-item_button" onClick="checksubmit()"></li>
	</div>
</div>
<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line">
<thead>
<tr height="25" class="tr_line_bottom">
<th>用户编号</th>
<th>用户姓名</th>
<th>详细信息</th>
<th>卡类型</th>
<th>提交日期</th>
<th>状态</th>
<th>操作</th>
</tr>
</thead>
<tbody id="tab">
<logic:iterate id="ordinfo" name="netpayList" indexId="i">
<tr>
<td class="td_line">${ordinfo[0]}</td>
<td class="td_line">${ordinfo[1]}</td>
<td class="td_line">${ordinfo[3]}:${ordinfo[4]}<br/>${ordinfo[6]}:${ordinfo[9]}</td>
<td class="td_line">${ordinfo[8]}</td>
<td class="td_line">${ordinfo[11]}</td>
<td class="td_line">${ordinfo[10]}</td>
<c:if test="${ordinfo[10]=='已验证'}">
<td><input name="update" type="button" value="验证" class="onbutton" style="color: gray;" disabled="disabled" ></td>
</c:if>
<c:if test="${ordinfo[10]=='未验证'}">
<td><input name="update" type="button" value="验 证" id="${ordinfo[9]}" class="onbutton" onClick="ha_update('${ordinfo[0]}','${ordinfo[8]}','${ordinfo[9]}','${ordinfo[3]}')"></td>
</c:if>
</tr>
</logic:iterate>
</tbody>
<tfoot>
<tr height="30">
<td colspan="9" class="tr_line_top">
<div class="grayr">
<wlt:pager url="business/acctbill.do?method=netpayList${requestScope.acctbill.paramUrl}" page="${requestScope.page}" style="black" />
</div>
</td>
</tr>
</tfoot>
</table>
</form>
</body>
</html:html>