<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
  String path = request.getContextPath();
  String st=(String)request.getAttribute("st");
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
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<SCRIPT type=text/javascript>
jQuery(function(){
	if(${"mess"} != ""){
		alert(${"mess"});
	}
});


	function ha_add(){
	 with(document.forms[0]){
	  action="../setup/setupgpadd.jsp";
	  target="_self";
	  submit();
	  }
	}
	
	function isSelectRole(){
		  with(document.forms[0]){
		var count = checkedSize(ids);
	  	if(count == 0){
	    	alert("请选择一条数据");
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
				 	alert("只能选择一条数据,请重新选择");
					return;
				}
		  		var tmp = confirm("您确定要删除所选的数据？");
			  	if(tmp == false){
			    	return false;
			  	}
			  	action = "../business/prod.do?method=delsetUPgp";
			  	target="_self";
			  	submit();
	  		}
	  }
	}
</SCRIPT>
</head>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<html:form action="/business/prod.do?method=setupGplist" method="post" >
 <div id="div_scroll">
<table id="myTable">
<thead>
<tr>
<th>序号</th>
<th>组编号</th>
<th>组类型</th>
<th>组名</th>
<th>是否默认</th>
<th>选择</th>
</tr>
</thead>
<tbody id="tab">
 <logic:iterate id="gpinfo" name="gplist" indexId="i">
<tr>
<td>${i + 1}</td>
<td>${gpinfo[0]}</td>
<td>${gpinfo[2]}</td>
<td>${gpinfo[3]}</td>
<td>${gpinfo[5]}</td>
<td align="center"> <input name="ids" type="checkbox" value="${gpinfo[0]}"></td>
</tr>
</logic:iterate>
</tbody>
<tfoot>
<tr>
<td colspan="12">
<div class="grayr">
 <wlt:pager url="business/prod.do?method=setupGplist&st=${st}&${userForm.paramUrl}" page="${requestScope.page}" style="black" />
</div>
</td>
</tr>
</tfoot>
</table>
<p align="center">
  <input name="Button" type="button" value="增加" class="onbutton" onClick="ha_add()">
  <input type="button" name="Submit2" value="删除" class="onbutton" onClick="ha_del()">
</p>
</div>
<input name="st" value="<%=st %>" type="hidden">
</html:form>
</body>
</html:html>