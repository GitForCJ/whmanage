<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="notice" scope="page" class="com.wlt.webm.rights.bean.SysNotice"/>
<%
  String path = request.getContextPath();
  List list = notice.getSysNoticeList(); 
  request.setAttribute("noticeList",list); 
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
<script type="text/javascript" src="../js/util.js"></script>
<SCRIPT src="../js/more_tiaojian.js" type=text/javascript></SCRIPT>
<script language='javascript' src='../js/jquery.js'></script>
<script language='javascript' src='../js/select.js'></script>
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/jquery-1.8.2.min.js" type="text/javascript"></script> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
});
</SCRIPT>
</head>
<script language="JavaScript">
	
</script>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<table id="myTable" >
<thead>
<tr>
<th>序号</th>
<th>公告标题</th>
<th>面向地市</th>
<th>公告类型</th>
<th>公告生效时间</th>
<th>公告截止时间</th>
<th>是否有效</th>
<th>操作</th>
</tr>
</thead>
<tbody id="tab">
 <logic:iterate id="noticeinfo" name="noticeList" indexId="i">
<tr>
<td>${i + 1}</td>
<td>${noticeinfo[1]}</td>
<td>${noticeinfo[6]}</td>
<td>${noticeinfo[7]}</td>
<td>${noticeinfo[3]}</td>
<td>${noticeinfo[4]}</td>
<td>${noticeinfo[5]}</td>
<td><a href="<%=path %>/rights/wltnoticeupdate.jsp?uid=${noticeinfo[0]}">修改</a>|<a href="<%=path %>/rights/wltnoticeinfo.jsp?type=update&uid=${noticeinfo[0]}">详细</a></td>
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