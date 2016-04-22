<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="sion" scope="page" class="com.wlt.webm.business.bean.SysCommission"/>
<%
  String path = request.getContextPath();
  List lists=sion.getRebatinfo();
  request.setAttribute("comList",lists);
%>
<html:html>
<jsp:useBean id="area" scope="page" class="com.wlt.webm.rights.bean.SysArea"/>
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
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<SCRIPT type=text/javascript>
showMessage("${mess}");
	function ha_add(){
	 with(document.forms[0]){
	  action="../employ/whtrebatadd.jsp";
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
			  	action = "../business/prod.do?method=delRebat&flag=0";
			  	target="_self";
			  	submit();
	  		}
	  }
	}
</SCRIPT>
</head>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<html:form action="/business/prod.do?method=emlist" method="post" >
 <div id="div_scroll">
<table id="myTable">
<thead>
<tr>
<th>序号</th>
<th>编号</th>
<th>业务类型</th>
<th>佣金百分比</th>
<th>佣金方式</th>
<th>选择</th>
</tr>
</thead>
<tbody id="tab">
 <logic:iterate id="cominfo" name="comList" indexId="i">
<tr>
<td>${i + 1}</td>
<logic:iterate id="info" name="cominfo" indexId="j">
<td>${info }</td>
</logic:iterate>
   <td align="center"> <input name="ids" type="checkbox" value="${cominfo[0]}#${cominfo[1]}#${cominfo[2]}#${cominfo[3]}" onclick="setParentid('${userinfo[0]}')"></td>
</tr>
</logic:iterate>
</tbody>
<tfoot>
</tfoot>
</table>
<p align="center">
  <input name="Button" type="button" value="增加" class="onbutton" onClick="ha_add()">
  <input type="button" name="Submit2" value="修改" class="onbutton" onClick="ha_del()">
</p>
</div>
</html:form>
</body>
</html:html>