<br><%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="rebate" scope="page" class="com.wlt.webm.business.bean.SysRebate"/>
<%
  String path = request.getContextPath();
  List list = rebate.listRebate(); 
  request.setAttribute("rebateList",list); 
%>
<html:html>
<jsp:useBean id="area" scope="page" class="com.wlt.webm.rights.bean.SysArea"/>
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
	function ha_add(){
	 with(document.forms[0]){
	  action="wltrebateadd.jsp";
	  target="_self";
	  submit();
	  }
	}
	
		function hasRole(){
		var size = <%=list.size()%>;
		if(size<=0){
			alert("请先添加角色");
			return false;
		}
		return true;
	}
	
	function isSelectRole(){
		  with(document.forms[0]){
		var count = checkedSize(ids);
	  	if(count == 0){
	    	alert("请选择折扣率");
			return false;
	  	}
	  	return true;
	  	 }
	}
	
	function ha_del(){
	  with(document.forms[0]){
	  		if(hasRole() && isSelectRole()){
	  			var count = checkedSize(ids);
				if(count>1){
				 	alert("只能选择一个折扣率，请重新选择");
					return;
				}
		  		var tmp = confirm("您确定要删除所选的记录？");
			  	if(tmp == false){
			    	return;
			  	}
			  	action = "sysrebate.do?method=del";
			  	target="_self";
			  	submit();
	  		}
	  }
	}
	
</script>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<html:form method="post" action="/business/sysrebate.do?method=del">
		<tr class="trcontent">
		<td colspan="4" align="center" class="fontcontitle">&nbsp;折扣率信息&nbsp;</td>
		</tr>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" class="tablelist">
  <tr class="trlist">
    <td colspan="9"><div align="center" class="fontlisttitle">折扣率信息</div></td>
  </tr>
  <tr class="fontcoltitle">
    <td nowrap="nowrap"><div align="center" >序号</div></td>
    <td nowrap="nowrap"><div align="center" >角色</div></td>
    <td nowrap="nowrap"><div align="center" >交易类型</div></td>
    <td nowrap="nowrap"><div align="center" >折扣率比例</div></td>
    <td nowrap="nowrap"><div align="center" >操作</div></td>
    <td ><div align="center"><input name="button1" type="button" onClick="setCheckedState(this.form.ids)" value="全选" class="onbutton">
        <input type="hidden" name="check" value="0">
      </div></td>
  </tr>
  <logic:iterate id="rebateinfo" name="rebateList" indexId="i">
  <tr class="tr1">
    <td align="center">${i + 1}</td>
    <logic:iterate id="info" name="rebateinfo" indexId="j">
    <logic:notEqual name="j" value="0">
    <td align="center">${info }</td>
    </logic:notEqual>
    </logic:iterate>
    <td align="center"><a href="<%=path %>/business/wltrebateupdate.jsp?srid=${rebateinfo[0]}">查看</a></td>
    <td align="center"> <input name="ids" type="checkbox" value="${rebateinfo[0]}"></td>
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