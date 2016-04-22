<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ taglib uri="/WEB-INF/wlt-tags.tld" prefix="wlt" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<%
  String path = request.getContextPath();
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
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<SCRIPT type=text/javascript>
if("${mess}"!=null&&"${mess}"!=""){
	alert("${mess}");
	}

	function ha_add(){
	 with(document.forms[0]){
	  action="flowpriceadd.jsp";
	  target="_self";
	  submit();
	  }
	}
	
		

	function isSelectReversal(){
		  with(document.forms[0]){
		var count = checkedSize(ids);
	  	if(count == 0){
	    	alert("请选择一条数据");
			return false;
	  	}
	  	return true;
	  	 }
	}
	function ha_udate(){
	  with(document.forms[0]){
	  		if(isSelectReversal()){
	  			var count = checkedSize(ids);
				if(count>1){
				 	alert("只能选择一条数据,请重新选择");
					return;
				}
				if(confirm("确认如此分配?")){
			  	action = "../rights/reversal.do?method=openflowprice";
			  	target="_self";
			  	submit();
				}else{
				return ;
					}
	  		}
	  }
	}
	
	function getList()
	{
	  with(document.forms[0]){
		  action="<%=path%>/rights/reversal.do?method=flowlist";
		  target="_self";
		  submit();
	  }
	}
	window.onload=function()
	{
		document.getElementById("tradeTypesID").value="${reversalForm.tradeTypes}";
	}
</SCRIPT>
</head>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<html:form action="/rights/reversal.do?method=flowlist" method="post" >
<ul style="padding-left:20px;">
	<li>
		运营商类型:
		<select style="width:120px;height:23px;background-color:white;" name="tradeTypes" id="tradeTypesID">
			<option value="">请选择</option>
			<option value="0">电信</option>
			<option value="1">移动</option>
			<option value="2">联通</option>
		</select>
		&nbsp;
		&nbsp;
		<input onclick="javascript:getList();" type="button" style="width:85px; height:32px; border:0px;color:#fff; font-weight:bold;background:url(<%=path%>/images/button_out.png) no-repeat bottom; cursor:pointer;" value="查询"/>
	</li>
</ul>
 <div id="div_scroll" style="width:100%;">
<table id="myTable" >
<thead>
<tr>
<th style="height:25px;border:1px solid #cccccc;">序号</th>
<th style="height:25px;border:1px solid #cccccc;">运营商类型</th>
<th style="height:25px;border:1px solid #cccccc;">产品名称</th>
<th style="height:25px;border:1px solid #cccccc;">价格</th>
<th style="height:25px;border:1px solid #cccccc;">选择</th>
</tr>
</thead>
<tbody id="tab">
 <logic:iterate id="reversalInfo" name="reversalList" indexId="i">
<tr>
<td style="border:1px solid #cccccc;">${i + 1}</td>

<td style="border:1px solid #cccccc;">
<c:if test="${reversalInfo[3]=='0'}">电信</c:if>
<c:if test="${reversalInfo[3]=='1'}">移动</c:if>
<c:if test="${reversalInfo[3]=='2'}">联通</c:if>
</td>
<td style="border:1px solid #cccccc;">${reversalInfo[1]}</td>
<td style="border:1px solid #cccccc;">${reversalInfo[2]}</td>

  <td align="center"  style="border:1px solid #cccccc;"> 
	<input name="ids" type="checkbox" value="${reversalInfo[0]}" >
</td>
</tr>
</logic:iterate>
</tbody>
<tfoot>
<tr>
<td colspan="12">
<div class="grayr">
 <wlt:pager url="${requestScope.path}rights/reversal.do?method=flowlist" page="${requestScope.page}" style="black" />
</div>
</td>
</tr>
</tfoot>
</table>
<p align="center">
  <input name="Button" type="button" value="增加" class="onbutton" onClick="ha_add()">
  <input type="button" name="Submit2" value="删除" class="onbutton" onClick="ha_udate()">
</p>
</div>
</html:form>
</body>
</html:html>