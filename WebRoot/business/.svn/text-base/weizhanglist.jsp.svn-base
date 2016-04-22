<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%
  String path = request.getContextPath();
%>
<html:html>
<jsp:useBean id="area" scope="page" class="com.wlt.webm.rights.bean.SysArea"/>
<head>
<%@ page contentType="text/html; charset=gb2312" language="java"%> 
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
</head>
<script language="JavaScript">
	
</script>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<div id="tabbox">
  <div id="tabs_title">
    <ul class="tabs_wzdh">
      <li>交通违章</li></ul>
  </div>
</div>
<table id="myTable">
<thead>
<tr>
<th>序号</th>
<th>违章单号</th>
<th>违章时间</th>
<th>违章地点</th>
<th>费用总和</th>
<th>代办周期</th>
<th>可用操作</th>
</tr>
</thead>
<tbody id="tab">
 <logic:iterate id="jtfk" name="jtfkList" indexId="i">
<tr>
<td>${i + 1}</td>
<td>${jtfk.violationId }</td>
<td>${jtfk.violationTime }</td>
<td>${jtfk.viloationLocation }</td>
<td>${jtfk.totalFee/100 }元</td>
<td>${jtfk.dealTime }天</td>
<td><a href="<%=path %>/business/weizhangdetail.jsp?${jtfk.detailUrl }">详情</a>&nbsp;&nbsp;${jtfk.dealMsg }</td>
</tr>
</logic:iterate>
</tbody>
<tfoot>
<tr>
<td></td>
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
</body>
</html:html>