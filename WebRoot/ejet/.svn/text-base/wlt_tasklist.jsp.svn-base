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
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
function checksubmit(){

	$("#form1").first();
	$("#form1").attr("action","<c:url value='/report/tasklogmanage.do?method=listTasklog'/>");	
	$("#form1").submit();//提交
	
}

function reexecute(){

	$("#form1").first();
	$("#form1").attr("action","<c:url value='/report/tasklogmanage.do?method=reExecute'/>");	
	$("#form1").submit();//提交
	
}
</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>任务管理</li></ul>
  </div>
</div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<form id="form1" name="form1" action="<%=path %>/report/tasklogmanage.do?method=listTasklog" method="post">
<div class="header">
	<div class="topCtiy clear">
		<ul>
		  <li>
		           任务类别： 
		      <select name="type" class="sia-2 selhover">
              <option value="">所有类别</option>
              <option value="2">地市、代理商日交易统计</option>
              <option value="3">代理点阶梯佣金统计</option>
              </select>
		  </li>
  		 <li>
		    任务完成日期： 
   		<input class="Wdate" name="startdate" type="text" onClick="WdatePicker()" value="${requestScope.taskForm.startdate }">
   		至
    	<input class="Wdate" name="enddate" type="text" onClick="WdatePicker()" value="${requestScope.taskForm.enddate }">
    	<%--  
		    
          <input class="Wdate" name="startdate" style="width: 90px;" onClick="new WdatePicker({dateFmt:'yyyyMMdd'})" > 
                          至
         <input class="Wdate" name="enddate" style="width: 90px;" onClick="new WdatePicker({dateFmt:'yyyyMMdd'})" > 
		   --%>
		   </li>	

		</ul>
		<li><input type="button" name="Button" value="提交" class="field-item_button" onClick="checksubmit()"></li>
		<li><input type="button" name="Button" value="重执行" class="field-item_button" onClick="reexecute();"></li>
	</div>
</div>
<table id="myTable" cellspacing="0" cellpadding="0" class="tab_line">
<thead>
<tr height="25" class="tr_line_bottom">
<th><div align="center"><input name="ids" type="button" onClick="setCheckedState(this.form.ids)" value="全选" class="onbutton"></div></th>
<th>序号</th>
<th>类别</th>
<th>应完成日期</th>
<th>实际完成时间</th>
<th>状态</th>
<th>修改时间</th>
<th>描述</th>
</tr>
</thead>
<tbody id="tab">
<logic:iterate id="ordinfo" name="tasklogList" indexId="i">
<tr>
<td><input name="ids" type="checkbox" value="${ordinfo[7]}"></td>
<td class="td_line_left">${i + 1}</td>
<td class="td_line">${ordinfo[1]}</td>
<td class="td_line">${ordinfo[2]}</td>
<td class="td_line">${ordinfo[3]}</td>
<td class="td_line">${ordinfo[4]}</td>
<td class="td_line">${ordinfo[5]}</td>
<td class="td_line">${ordinfo[6]}</td>
</tr>
</logic:iterate>
</tbody>
<tfoot>
<tr height="30">
<td colspan="14" class="tr_line_top">
<jsp:include page="../common/pagination.jsp" flush="true">
    <jsp:param name="submitForm" value="forms[0]"/>
</jsp:include>
</td>
</tr>
</tfoot>
</table>
</form>
</body>
</html:html>