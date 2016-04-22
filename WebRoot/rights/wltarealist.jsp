<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
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
function insert(){
	with(document.forms[0]){
		/* areacope为区域区号，也即体系层次表中的节点区号 */
		action="sysarea.do?method=showAddTPage";
		target="_self";
		alert(action);
		submit();
	}
}
function insertnext(){
	with(document.forms[0]){
		action="sysarea.do?method=showAddUPage";
		target="_self";
		submit();
	}
}
function setParentid(areavalue){
	document.forms[0].areacope.value=areavalue;	
}
function modify(){
	with(document.forms[0]){
		action="sysarea.do?method=toModify";
		target="_self";
		submit();
	}
}
function del(){
	if(confirm("你确定要删除所选的记录?")){
		with(document.forms[0]){
			var str = document.getElementsByName("areacope");
			action="sysarea.do?method=del";
			target="_self";
			submit();
		}
	}else{
		return false;
	}	
}
	var openedid;
	var openedid_ft;
//-------- 部门点击事件 -------
function myclick(srcelement){
  	var targetid,srcelement,targetelement;
  	var strbuf;
//-------- 如果点击了展开或收缩按钮---------
  	if(srcelement.className=="outline"){
     	targetid=srcelement.id+"d";
     	targetelement=document.all(targetid);
     	if (targetelement.style.display=="none"){
        	targetelement.style.display="inline";
        	strbuf=srcelement.src;
        	if(strbuf.indexOf("plus.gif")>-1)
                srcelement.src="../images/tree_minus.gif";
        	else
                srcelement.src="../images/tree_minusl.gif";
     	}else{
        	targetelement.style.display="none";
        	strbuf=srcelement.src;
        	if(strbuf.indexOf("minus.gif")>-1)
                srcelement.src="../images/tree_plus.gif";
        	else
                srcelement.src="../images/tree_plusl.gif";
     	}
  	}
}
</script>
<body rightmargin="0" leftmargin="0" onload="setParentid('1')" topmargin="0" class="body_no_bg">
<html:form method="post" action="/rights/sysarea.do?method=showAddTPage">
<!-- 部门树开始-->
		<tr class="trcontent">
		<td colspan="4" align="center" class="fontcontitle">&nbsp;区域信息&nbsp;</td>
		</tr>
		<table class="tablecontent" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="../images/tree_minus.gif" id="MEMU_0" class="outline" style="cursor:hand" onclick="myclick(this)"></td>								
				<td><input type="radio" name="areacode" checked grade="0" onclick="setParentid('1')"></td>								
				<td colspan="3"><a href="javascript:myclick(document.all.MEMU_0)">&nbsp;区域列表</a></td>
			</tr>
		</table>
		<table class="tablecontent" border="0" cellspacing="0" cellpadding="0" id="MEMU_0d" style="DISPLAY: inline">
					<tr>
				<td>
					<%
						List areaList = area.getSyslevel();
						if (areaList != null && areaList.size() > 0) {
							List firstarea = area.getChildareaList(areaList,"1");
							for (int i = 0; i < firstarea.size(); i++) {
								String[] firstareainfo = (String[]) firstarea.get(i);
								List secondarea = area.getChildareaList(areaList,firstareainfo[0]);
								if (secondarea != null && secondarea.size() > 0) {
					%>
		<table class="tablecontent" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="../images/tree_line.gif" border="0"></td>
				<td><img src="../images/tree_minus.gif" id="MEMU_<%=firstareainfo[1]%>" class="outline" style="cursor:hand" onclick="myclick(this)"></td>
				<td><input father="<%=firstareainfo[2]%>" type="radio" value="<%=(String) firstareainfo[0]%>" name="areacode" onclick="setParentid(this.value)">
				</td>
				<td colspan="2"><a href="javascript:myclick(document.all.MEMU_<%=firstareainfo[1]%>)">&nbsp;<%=firstareainfo[1].toString()+(firstareainfo[4].toString().equals("0")?"(虚拟区域)":"")%></a></td>
			</tr>
		</table>
		<table class="tablecontent" border="0" cellspacing="0" cellpadding="0" id="MEMU_<%=firstareainfo[1]%>d" style="DISPLAY: inline">
					<tr>
				<td>
				<%
						for (int j = 0; j < secondarea.size(); j++) {
							String[] secondareainfo = (String[]) secondarea.get(j);
				%>
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="../images/tree_line.gif" border="0"></td>
				<td><img src="../images/tree_line.gif" border="0"></td>
				<td><img src="../images/tree_blank.gif"></td>
				<td><input type="radio" father="<%=secondareainfo[2]%>" value="<%=(String) secondareainfo[0]%>" name="areacode" onclick="setParentid(this.value)">
				</td>
				<td colspan="2">&nbsp;<%=(String) secondareainfo[1]%>&nbsp;&nbsp;[区号:<%=(String) secondareainfo[3]%>]</td>
			</tr>
		</table>
			<%
				}
			%>
						</tr>
</table>
			<%
				} else {
			%>
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="../images/tree_line.gif" border="0"></td>
				<td><img src="../images/tree_blank.gif"></td>
				<td><input type="radio" father="<%=firstareainfo[2]%>" value="<%=(String) firstareainfo[0]%>" name="areacode" onclick="setParentid(this.value)">
				</td>
				<td colspan="2">&nbsp;<%=(String) firstareainfo[1]%></td>
			</tr>
		</table>
				<%
							}
						}
					}
				%>
	</td>
	</tr>
	</table>
	<div class="field-item_button_m2">
			<html:hidden property="areacope" value="${areacope}" />
	  		<input type="button" name="add" value="增加同级区域" class="onbutton" onclick="insert()">
			<input type="button" name="add1" value="增加下级区域" class="onbutton" onclick="insertnext()">
			<input type="button" name="update" value="修改" class="onbutton" onclick="modify()">
			<input type="button" name="delete" value="删除" class="onbutton" onclick="del()">
	</div>
</html:form>
</body>
</html:html>