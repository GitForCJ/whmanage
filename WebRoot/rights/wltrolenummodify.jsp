<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html:html>
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
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
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<%
	String roleid = request.getParameter("roleid");
	List list = role.getMenuIdByRole(roleid);
	String menuStr = "";
	if(null != list && list.size() > 0){
		for(int i = 0; i < list.size(); i++){
			String[] tmp = (String[])list.get(i);
			menuStr += tmp[0]+",";
		}
	}
 %>
</head>
<script language="JavaScript"> 
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
                srcelement.src="../images/tree_plus.gif";
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
function onbox(box){
	var _boxVal = $(box).val();
	var _bool = false;
	if($(box).attr("checked") == "checked"){
		_bool = true;
	}
	$("input[type='checkbox' ][id='"+_boxVal+"']").each(function(){
		$(this).attr("checked",_bool);
	})
}
function onbox1(box){
	
}
function onbox_old(box)
{
  var checked = box.checked;
  var ids = new Array();
  ids[ids.length] = box.value;
  var nextids;
  var allBoxes = document.getElementsByName("modules");
  do//向下查找
  {
    if (nextids)
    {
      ids = nextids;
    }
    nextids = new Array();

    for (var i = 0; i < ids.length; i++)
    {
      for (var j = 0; j < allBoxes.length; j++)
      {
        if (allBoxes[j].id == ids[i])
        {
          allBoxes[j].checked = checked;
          nextids[nextids.length] = allBoxes[j].value;
        }
      }
    }
  }
  while (nextids.length > 0);

  var id = box.id;
  var idBox;
  var end = false;//是否终止继续向上查找
  while (id != 0)//向上查找
  {
    for (var i = 0; i < allBoxes.length; i++)
    {
      if (allBoxes[i].value == id)
      {
        idBox = allBoxes[i];
        id = idBox.id;
        break;
      }
    }

    if (checked)
    {
      idBox.checked = true;
    }
    else
    {
      for (var i = 0; i < allBoxes.length; i++)
      {
        if (allBoxes[i].id == idBox.value)
        {
          if (allBoxes[i].checked)
          {
            end = true;
            break;
          }
        }
      }
      if (!end)
      {
        idBox.checked = false;
      }
    }

    if (end)
    {
      break;
    }
  }//end while
}

function onclear(){
	  with(document.forms[0]){
  if(reset1.value=="全选"){
  	for(var p=0;p<document.forms[0].length;p++){
  		if(elements[p].type=="checkbox")
  			elements[p].checked=true;	
  	}
  	reset1.value="清空";
  } 
  else if(reset1.value=="清空"){
  	for(var p=0;p<document.forms[0].length;p++){
  		if(elements[p].type=="checkbox")
  			elements[p].checked=false;	
  	}
  	reset1.value="全选";
  }
  }
}
$(function(){
	var _menuVal = $("#menuStr").val();
	$("input[type='checkbox']").each(function(){
		if(_menuVal.indexOf($(this).val()+",") != -1){
			$(this).attr("checked",true);
		}
	});
})


function funNotNull()
{
	var arr=document.getElementsByName("modules");
	var flag=false;
	for(var i=0;i<arr.length;i++)
	{
		if(arr[i].checked)
		{
			flag=true;
			break;
		}
	}
	if(!flag)
	{
		alert("请选择子功能模块!");
		return false;
	}
	return true;
}
</script>
<body rightmargin="0" leftmargin="0"  topmargin="0" class="body_no_bg">
<input type="hidden" id="menuStr" value="<%=menuStr %>"/>
<html:form method="post" action="/rights/sysrole.do?method=setModuleRightsFunc">
<!-- 部门树开始-->
<table class="tablecontent" width="100%" cellspacing="0" cellpadding="0">
					<tr>
				<td>
					<%
						List roleList = role.getSyslevel();
						if (roleList != null && roleList.size() > 0) {
							List firstrole = role.getChildroleList(roleList,"0");
							for (int i = 0; i < firstrole.size(); i++) {
								String[] firstroleinfo = (String[]) firstrole.get(i);
								List secondrole = role.getChildroleList(roleList,firstroleinfo[0]);
								if (secondrole != null && secondrole.size() > 0) {
									for (int j = 0; j < secondrole.size(); j++) {
										String[] secondroleinfo = (String[]) secondrole.get(j);
										List tirdrole = role.getChildroleList(roleList,secondroleinfo[0]);
					%>
		<table class="tablecontent" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="../images/tree_line.gif" border="0"></td>
				<td><img src="../images/tree_minusl.gif" id="MEMU_<%=secondroleinfo[1]%>" class="outline" style="cursor:hand" onclick="myclick(this)"></td>
				<td><input disabled_false onclick="onbox(this)"  id="<%=(String) secondroleinfo[2]%>" type="checkbox" value="<%=(String) secondroleinfo[0]%>"
      name="firstBox" (String) <%=(String) secondroleinfo[1]%> ></td>	
				<td colspan="2"><a href="javascript:myclick(document.all.MEMU_<%=secondroleinfo[1]%>)">&nbsp;<%=secondroleinfo[1].toString()%></a></td>
			</tr>
		</table>
		<table class="tablecontent" border="0" cellspacing="0" cellpadding="0" id="MEMU_<%=secondroleinfo[1]%>d" >
					<tr>
				<td>
				<%
						for (int k = 0; k < tirdrole.size(); k++) {
							String[] thirdroleinfo = (String[]) tirdrole.get(k);
				%>
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="../images/tree_line.gif" border="0"></td>
				<td><img src="../images/tree_line.gif" border="0"></td>
				<td><img src="../images/tree_blank.gif"></td>
				<td><input disabled_false onclick="onbox(this)" id="<%=(String) thirdroleinfo[2]%>" type="checkbox" value="<%=(String) thirdroleinfo[0]%>"
      name="modules" <%=(String) thirdroleinfo[1]%> ></td>	
				<td colspan="2">&nbsp;<%=(String) thirdroleinfo[1]%></td>
			</tr>
		</table>
			<%
				}
			%>
						</tr>
</table>
			<%
				}
						}}
					}
				%>
	</td>
	</tr>
	</table>
	<div class="field-item_button_m2">
	<input type="hidden" name="roleid" value="<%=roleid%>"> 
	<input type="submit" onclick="return funNotNull()" name="buttong1"  value="提交" class="onbutton" >
	<input type="button" name="reset1" onclick="onclear()" value="全选" class="onbutton">
	<input type="button" name="reset2" onclick="javascript:history.go(-1)" value="返回" class="onbutton">
	</div>
</html:form>
</body>
</html:html>