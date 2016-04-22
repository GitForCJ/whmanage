<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="sit" scope="page" class="com.wlt.webm.business.bean.SysUserInterface"/>
<jsp:useBean id="ccc" scope="page" class="com.wlt.webm.business.action.OrderAction"/>
<jsp:useBean id="biProd" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
<%
  String path = request.getContextPath();
  String st=(String)request.getAttribute("st");
  
    request.setAttribute("areaSel",ccc.getStingSel(sit.listArea(),"区域"));
  
  String yunyingshang="请选择运营商[]|电信[0]|移动[1]|联通[2]";
  request.setAttribute("yunyingshang",yunyingshang);
  
  request.setAttribute("miane",ccc.getStingSel(biProd.listvalueRangeTwo(),"面额"));
%>
<html:html>
<head>
<title>万汇通</title>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
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
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});

	function ha_add(){
	 with(document.forms[0]){
	  action="../employ/whtcommissionadd.jsp?stype=${st}";
	  target="_self";
	  submit();
	  }
	}
	
		function setParentid(areavalue){
	document.forms[0].areacope.value=areavalue;	
	}
	
		function ha_modify(){
	 	with(document.forms[0]){
	 	var count = checkedSize(ids);
	  	if(count == 0){
	    	alert("请选择佣金组");
			return false;
	  	}
	 	 action="wltcommissionmodify.jsp?stype=1&sg_id="+areacope.value;
	  	target="_self";
	  	submit();
	 	 }
	 	 }
	 	 
	 	function ha_checkmx(){
	 	with(document.forms[0]){
	 	var count = checkedSize(ids);
	  	if(count == 0){
	    	alert("请选择佣金组");
			return false;
	  	}
	 	 action="wltcommissioncheckmx.jsp?sg_id="+areacope.value;
	  	target="_self";
	  	submit();
	 	 }
	 	 }
	 	 
	 	function ha_addmx(){
	 	with(document.forms[0]){
	 	 action="wltcommissionmodify.jsp?sg_id="+areacope.value;
	  	target="_self";
	  	submit();
	 	 }
	 	 }	 	 
	
	
	function isSelectRole(){
		  with(document.forms[0]){
		var count = checkedSize(ids);
	  	if(count == 0){
	    	alert("请选择佣金");
			return false;
	  	}
	  	return true;
	  	 }
	}
		function ha_del(){
			
	  with(document.forms[0]){
	  		if(isSelectRole()){
	  			var count = checkedSize(ids);
				if(count>1){
				 	alert("只能选择一条佣金,请重新选择");
					return;
				}
		  		var tmp = confirm("您确定要修改所选的记录？");
			  	if(tmp == false){
			    	return false;
			  	}
			  //	var cm_one=cm_one.value;
			  //	var yunyingshang=yunyingshang.value;
			  	//var miane=miane.value;
			  //	alert(cm_one);
			  	//action="../employ/whtcommissionupdate.jsp?stype=${st}";
			  	action = "../business/prod.do?method=delEmploy&k=0&curPage=${curPage}";
			  	target="_self";
			  	submit();
	  		}
	  }
	}
	function checksubmit()
	{
		with(document.forms[0]){
			submit();
		}
	}
</SCRIPT>
</head>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<html:form action="/business/prod.do?method=emlist" method="post" >

<div class="header">
	<div class="topCtiy clear">
		<ul>
		  <li>
	  		<div class="sub-input"><a class="sia-2 selhover" id="sl-cm_one" href="javascript:void(0);">区域</a></div>
	 	 	<input nname="nname" name="cm_one" id="cm_one" defaultsel="${requestScope.areaSel }" type="hidden" value="${userForm.cm_one}" />
		  </li>
		  <li>
	  		<div class="sub-input"><a class="sia-2 selhover" id="sl-yunyingshang" href="javascript:void(0);">运营商</a></div>
	 	 	<input nname="nname" name="yunyingshang" id="yunyingshang" defaultsel="${requestScope.yunyingshang }" type="hidden" value="${userForm.yunyingshang}" />
		  </li>
		   <li>
	  		<div class="sub-input"><a class="sia-2 selhover" id="sl-miane" href="javascript:void(0);">面额</a></div>
	 	 	<input nname="nname" name="miane" id="miane" defaultsel="${requestScope.miane }" type="hidden" value="${userForm.miane}" />
		  </li>
		  <li><input type="button" name="Button" value="查询" class="field-item_button" onClick="checksubmit();"></li>
		</ul>
	</div>
</div>
 <div id="div_scroll">
<table id="myTable">
<thead>
<tr>
<th>序号</th>
<th>编号</th>
<th>省份</th>
<th>运营商</th>
<th>面额区间</th>
<th>佣金百分比</th>
<th>选择</th>
</tr>
</thead>
<tbody id="tab">
 <logic:iterate id="userinfo" name="userList" indexId="i">
<tr>
<td>${i + 1}</td>
<logic:iterate id="info" name="userinfo" indexId="j">
<td>${info }</td>
</logic:iterate>
   <td align="center"> <input name="ids" type="checkbox" value="${userinfo[0]}#${userinfo[1]}#${userinfo[2]}#${userinfo[3]}#${userinfo[4]}" onclick="setParentid('${userinfo[0]}')"></td>
</tr>
</logic:iterate>
</tbody>
<tfoot>
<tr>
<td colspan="12">
<div class="grayr">
 <wlt:pager url="business/prod.do?method=emlist&st=${st}&${userForm.paramUrl}" page="${requestScope.page}" style="black" />
</div>
</td>
</tr>
</tfoot>
</table>
<p align="center">
  <input name="Button" type="button" value="增加" class="onbutton" onClick="ha_add()">
  <input type="button" name="Submit2" value="修改" class="onbutton" onClick="ha_del()">
</p>
</div>
<input name="st" value="<%=st %>" type="hidden">
</html:form>
</body>
</html:html>