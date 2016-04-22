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
.field-item_button2 { border:0px;width:70px; height:25px; color:#fff; background:url(../images/button_out2.png) no-repeat; cursor:pointer;}
.field-item_button2:hover { border:0px; width:86px; height:32px; color:#fff; background:url(../images/button_on2.png) no-repeat; cursor:pointer;}
</style>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>

<SCRIPT type=text/javascript>
  
  function checksubmit(){
	  	alert("tjiao");
		$("#taskform").first();
		$("#taskform").attr("action","<c:url value='/report/tasklogmanage.do?method=listTasklog'/>");	
		$("#taskform").submit();//提交
		
	}
 
</SCRIPT>
</head>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="tabbox">

  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li style="width: 150%">后台任务完成情况</li></ul>
  </div>
</div>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<form method="post" id="taskform" name="taskform" action="/report/tasklogmanage.do?method=listTasklog" >
<input type="hidden" id="rootPath" value="<%=request.getContextPath() %>"/>
<div class="header">
	<div class="topCtiy clear">
		<ul>
		
		  <li>
		  <td width="50%"> 任务名称</td>
          <td><input name="name" type="text" class="inputtext" size="15" ></td>
		  </li>
		  
		   <li>
		   <td> 任务类别</td>
            <td><select name="type" class="sia-2 selhover">
              <option value="">所有类别</option>
              <option value="1">数据库同步</option>
              <option value="2">结算程序</option>
              <option value="3">汇总程序</option>
              <option value="4">对账程序</option>
              <option value="5">计费汇总推送程序</option>
              <option value="6">计费资金账户对账</option>
              <option value="7">建表程序</option>
              </select>
            </td>
		  </li>
		   <li>
		    任务完成日期： 
          <input class="Wdate" name="startdate" style="width: 90px;" onClick="new WdatePicker({dateFmt:'yyyyMMdd'})" > 
                          至
         <input class="Wdate" name="enddate" style="width: 90px;" onClick="new WdatePicker({dateFmt:'yyyyMMdd'})" > 
		   </li>
		   <li>
		   <div>
		   <input type="button" value="查询"  onclick="checksubmit()" class="field-item_button2" /> 
		   </div>
		   </li> 
		</ul>
		 
		 
	</div>
</div>
</form>

  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tab_line">
    <tr class="tr_line_bottom">
      <td nowrap="nowrap"><div align="center" >序号2</div></td>
      <td nowrap="nowrap"><div align="center" >任务名称</div></td>
      <td nowrap="nowrap"><div align="center" >任务类别</div></td>
      <td nowrap="nowrap"><div align="center" >任务执行级别</div></td>
      <td nowrap="nowrap"><div align="center" >任务描述</div></td>
      <td nowrap="nowrap"><div align="center" >任务应完成日期</div></td>
      <td nowrap="nowrap"><div align="center" >任务实际完成日期</div></td>
      <td nowrap="nowrap"><div align="center" >任务日志状态</div></td>
      <td><div align="center"><input name="ids" type="button" onClick="setCheckedState(this.form.ids)" value="全选" class="onbutton"></div></td>
    </tr>
	<logic:iterate id="info" name="tasklogList" indexId="i">
      <tr class="tr1" onMouseOver="overRow(this)" onMouseOut="outRow(this)" onClick="clickRow(this)">
      <td align="center">${i + 1}</td>
      <td align="center">${info[1]}</td>
      <td align="center">${info[2]}</td>
      <td align="center">${info[3]}</td>
      <td align="center">${info[4]}</td>
      <td align="center">${info[5]}</td>
      <td align="center">${info[6]}</td>
      <td align="center">${info[7]}</td>
      <td align="center"><c:if test="${info[8] != 7}"><input name="ids" type="checkbox" value="${info[0]}"></c:if></td>
      </tr>
	</logic:iterate>
  </table>
  <jsp:include page="../common/pagination.jsp" flush="true">
    <jsp:param name="submitForm" value="forms[0]"/>
  </jsp:include>
  <p align="center">
    <input type="button" name="Submit2" value="重执行" class="onbutton" onClick="check()">
    <input name="button2" type="button" value="返回" class="onbutton" onClick="goback()">
   </p>

</body>
</html:html>