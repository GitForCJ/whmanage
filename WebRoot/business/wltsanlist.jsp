<br><%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="biProd" scope="page" class="com.wlt.webm.business.bean.BiProd"/>
<%
  String path = request.getContextPath();
  List list = biProd.listsan(); 
  request.setAttribute("prodList",list); 
%>
<html:html>
<head>
<title>万汇通</title>
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
$(function(){
	if($("#mess").val() != ''){
		alert($("#mess").val());
	}
});

	function ha_add(){
	 with(document.forms[0]){
	  action="wltsanadd.jsp";
	  target="_self";
	  submit();
	  }
	}
	
	function isSelectRole(){
		  with(document.forms[0]){
		var count = checkedSize(ids);
	  	if(count == 0){
	    	alert("请选择");
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
				 	alert("只能选择一个，请重新选择");
					return;
				}
		  		var tmp = confirm("您确定要删除所选的记录？");
			  	if(tmp == false){
			    	return;
			  	}
			  	action = "prod.do?method=sandel";
			  	target="_self";
			  	submit();
	  		}
	  }
	}
	
</script>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<html:form method="post" action="/business/prod.do?method=sandel">
		<tr class="trcontent">
		<td colspan="4" align="center" class="fontcontitle">&nbsp;第三方接口管理信息&nbsp;</td>
		</tr>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tablelist">
  <tr class="trlist">
    <td colspan="9"><div align="center" class="fontlisttitle">第三方接口管理信息</div></td>
  </tr>
  <tr class="fontcoltitle">
    <td nowrap="nowrap"><div align="center" >序号</div></td>
    <td nowrap="nowrap"><div align="center" >类型</div></td>
    <td nowrap="nowrap"><div align="center" >产品面额</div></td>
    <td nowrap="nowrap"><div align="center" >接口名称</div></td>
    <td ><div align="center"><input name="button1" type="button" onClick="setCheckedState(this.form.ids)" value="全选" class="onbutton">
        <input type="hidden" name="check" value="0">
      </div></td>
  </tr>
  <logic:iterate id="prodinfo" name="prodList" indexId="i">
  <tr class="tr1">
    <td align="center">${i + 1}</td>
    <logic:iterate id="info" name="prodinfo" indexId="j">
    <logic:notEqual name="j" value="0">
    <td align="center">${info }</td>
    </logic:notEqual>
    </logic:iterate>
    <td align="center"> <input name="ids" type="checkbox" value="${prodinfo[0]}"></td>
  </tr>
  </logic:iterate>
</table>
<p align="center">
  <input name="Button" type="button" value="增加" class="onbutton" onClick="ha_add()">
  <input type="button" name="Submit2" value="删除" class="onbutton" onClick="ha_del()">
</p>
</html:form>
</body>
</html:html>