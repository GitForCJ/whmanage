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
<title>���ͨ</title>
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

	function ha_add(){
	 with(document.forms[0]){
	  action="jtfksetadd.jsp";
	  target="_self";
	  submit();
	  }
	}
	
		

	function isSelectReversal(){
		  with(document.forms[0]){
		var count = checkedSize(ids);
	  	if(count == 0){
	    	alert("��ѡ��һ������");
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
				 	alert("ֻ��ѡ��һ������,������ѡ��");
					return;
				}
			  	action = "jtfksetupdate.jsp";
			  	target="_self";
			  	submit();
	  		}
	  }
	}
	
</SCRIPT>
</head>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<html:form action="/rights/reversal.do?method=jtfklist&flag=0" method="post" >
 <div id="div_scroll" style="width:100%;">
<table id="myTable" >
<thead>
<tr>
<th style="height:25px;border:1px solid #cccccc;">��������</th>
<th style="height:25px;border:1px solid #cccccc;">��������</th>
<th style="height:25px;border:1px solid #cccccc;">������ʽ</th>
<th style="height:25px;border:1px solid #cccccc;">ѡ��</th>
</tr>
</thead>
<tbody id="tab">
 <logic:iterate id="reversalInfo" name="jtfkList" indexId="i">
<tr>
<td style="border:1px solid #cccccc;">${reversalInfo[4]}</td>
<td style="border:1px solid #cccccc;">${reversalInfo[5]}</td>
<td style="border:1px solid #cccccc;">${reversalInfo[6]}</td>
  <td align="center"  style="border:1px solid #cccccc;">
	<input name="ids" type="checkbox" value="${reversalInfo[0]}#${reversalInfo[1]}#${reversalInfo[2]}#${reversalInfo[3]}" >
</td>
</tr>
</logic:iterate>
</tbody>
<tfoot>
</tfoot>
</table>
<p align="center">
  <input name="Button" type="button" value="����" class="onbutton" onClick="ha_add()">
  <input type="button" name="Submit2" value="�޸�" class="onbutton" onClick="ha_udate()">
</p>
</div>
</html:form>
</body>
</html:html>