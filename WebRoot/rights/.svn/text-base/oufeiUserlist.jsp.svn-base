<br><%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="user" scope="page" class="com.wlt.webm.rights.bean.SysUser"/>
<%
  String path = request.getContextPath();
  List list = user.oufeiList(); 
  request.setAttribute("prodList",list); 
%>
<html:html>
<head>
<title>万汇通</title>
<link rel="stylesheet" type="text/css" href=../css/common.css>
<style type="text/css">
.tabs_wzdh { width: 100px; float:left;}
.tabs_wzdh li { height:35px; line-height:35px; margin-top:5px; padding-left:20px; font-size:14px; font-weight:bold;}
/*tree_nav*/oufeiList
.tree_nav { border:1px solid #CCC; background:#f5f5f5; padding:10px; margin-top:20px;}
.field-item_button_m2 { margin:0 auto; text-align:center;}
.field-item_button2 { border:0px;width:136px; height:32px; color:#fff; background:url(../images/button_out2.png) no-repeat; cursor:pointer;margin-right:20px;}
.field-item_button2:hover { border:0px; width:136px; height:32px; color:#fff; background:url(../images/button_on2.png) no-repeat; margin-right:20px;}
</style>
<script type="text/javascript" src="../js/util.js"></script>
</head>
<script language="JavaScript">
showMessage("${mess}");

	
	function isSelectRole(){
		  with(document.forms[0]){
		var count = checkedSize(ids);
	  	if(count == 0){
	    	alert("请选择数据");
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
				 	alert("只能选择一条数据，请重新选择");
					return;
				}
		  		var tmp = confirm("您确定要删除所选的记录？");
			  	if(tmp == false){
			    	return;
			  	}
			  	action = "sysuser.do?method=ghdel";
			  	target="_self";
			  	submit();
	  		}
	  }
	}
	
		function ha_up(){
	  with(document.forms[0]){
	  		if(isSelectRole()){
	  			var count = checkedSize(ids);
				if(count>1){
				 	alert("只能选择一条数据，请重新选择");
					return;
				}
		  		var tmp = confirm("您确定要修改所选的记录？");
			  	if(tmp == false){
			    	return;
			  	}
			  	action = "sysuser.do?method=ghupstate";
			  	target="_self";
			  	submit();
	  		}
	  }
	}
	
</script>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<html:form method="post" action="/business/prod.do?method=del">
<table width="100%" border="2" align="center" cellpadding="0" cellspacing="1" class="tablelist">
  
		<tr class="trcontent">
		<td colspan="6" align="center" class="fontcontitle">&nbsp;供货信息&nbsp;</td>
		</tr>
<tr class="fontcoltitle">
    <td nowrap="nowrap"><div align="center" >系统编号<br/>资金账号</div></td>
    <td nowrap="nowrap"><div align="center" >殴飞ID<br/>绑定时间</div></td>
    <td nowrap="nowrap"><div align="center" >模板编号<br/>供货秘钥</div></td>
    <td nowrap="nowrap"><div align="center" >状态</div></td>
     <td nowrap="nowrap"><div align="center" >供货类型</div></td>
    <td ><div align="center"><input name="button1" type="button" onClick="setCheckedState(this.form.ids)" value="单选" class="onbutton">
        <input type="hidden" name="check" value="0">
      </div></td>
  </tr>
  <logic:iterate id="prodinfo" name="prodList" indexId="i">
  <tr class="tr1">
    <td align="center">${prodinfo[0]}<br/>${prodinfo[1]}</td>
<td align="center">${prodinfo[2]}<br/>${prodinfo[5]}</td>
<td align="center">${prodinfo[3]}<br/>${prodinfo[4]}</td>
<td align="center">${prodinfo[6]}</td>
<td align="center">${prodinfo[12]}</td>
    <td align="center"> <input name="ids" type="checkbox" value="${prodinfo[0]}#${prodinfo[6]}#${prodinfo[13]}"></td>
  </tr>
  </logic:iterate>
</table>
<p align="center">
  <input type="button" name="Submit2" value="删除" class="onbutton" onClick="ha_del()">
&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" name="Submit3" value="状态修改" class="onbutton" onClick="ha_up()">
</p>
</html:form>
<div style="padding-left:40px;padding-top:10px;font-size: 16px; color: #FF6600;font-weight:bold;border:1px solid #FF9E4d;background-color: #FFFFE5;">
<ul>
<li style="list-style:none;">状态0表示正常，1表示禁用</li>
</ul>
</div>
</body>
</html:html>