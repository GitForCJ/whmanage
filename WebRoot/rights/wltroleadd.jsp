<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
 <%
   String path = request.getContextPath();
   String userrole=userSession.getUser_role();
   request.setAttribute("prole", userrole);
   List areaList = role.getAreaList();
	 StringBuffer sBuffer = new StringBuffer();
	 sBuffer.append("请选择[]");
	 for(Object tmp : areaList){
		String[] temp = (String[])tmp;
		sBuffer.append("|"+temp[1]+"["+temp[0]+"]");
	 }
	 
	 List rolelist = role.getRoleType(null);
	 StringBuffer sBuffer1 = new StringBuffer();
	 sBuffer1.append("请选择[]");
	 for(Object tmp : rolelist){
		String[] temp = (String[])tmp;
		sBuffer1.append("|"+temp[1]+"["+temp[0]+"]");
	 }
	 request.setAttribute("areaSel", sBuffer.toString());
 	 request.setAttribute("roleSel", sBuffer1.toString());
 %>
 <html>
<head>
<title>万汇通</title>
<script type="text/javascript" src="../js/jquery-1.8.2.min.js"></script>
<link href="../css/main_style.css" rel="stylesheet" type="text/css" />
<link href="../css/selectCss.css" rel="stylesheet" type="text/css" /> 
<script src="../js/selectCss.js" type="text/javascript"></script> 
<script language="javascript" type="text/javascript" src="../My97DatePicker/WdatePicker.js"></script>
<SCRIPT type=text/javascript>
jQuery(function(){
	$("#changeCity").click(function(a){$("#allCity").slideDown(0);a.stopPropagation();$(this).blur()});$("#allCity").click(function(a){a.stopPropagation()});$(document).click(function(a){a.button!=2&&$("#allCity").slideUp(0)});$("#foldin").click(function(){$("#allCity").slideUp(0)})
	if($("#mess").val() != ""){
		alert($("#mess").val());
	}
});
function check(){
  	with(document.forms[0]){
		/* areacope为区域区号，也即体系层次表中的节点区号 */
		if(rolename.value == ""){
		alert("请输入角色名称!");
		return;
		}
		if(sg_default.value == 0){
			if(user_site_city.value != ''){
				alert("角色默认只能归属省份");
				return;
			}
		}
		action="sysrole.do?method=add";
		target="_self";
		submit();
	}
}

</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" id="mess" value="${requestScope.mess }"/>
<html:form  method="post" action="/rights/sysrole.do?method=add">
<div class="pass_main">
	<div class="pass_title_1">添加角色</div>
    <div class="pass_list">
    	<ul>
            <li><strong>角色名称：</strong><span><input name="rolename" type="text" /></span></li>
        </ul>
           <ul>
           <li>
   	 		<tr>
   	 		<strong>用户类型：</strong>
   	 		<span>
   	 		<div class="sub-input"><a class="sia-2 selhover" id="sl-roleType" href="javascript:void(0);">请选择</a></div>
			<input name="roleType" id="roleType" defaultsel="${requestScope.roleSel }" type="hidden" value="" />
            </span>
  		  	</tr>
  		  	</li>
  		  </ul>
        <ul>
           <li>
   	 		<tr>
   	 		<strong>省级：</strong>
   	 		<span>
				<td>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-wltarea_role" href="javascript:void(0);">请选择</a></div>
				<input name="wltarea" id="wltarea_role" defaultsel="${requestScope.areaSel }" type="hidden" value="" />
				</td>
			</span>
  		  	</tr>
  		  	</li>
  		  </ul>
  		          <ul>
           <li>
  		  	<tr>
				<strong>市级：</strong>
				<span>
				<td>
					<div class="sub-input"><a class="sia-2 selhover" id="sl-user_site_city" href="javascript:void(0);">请选择</a></div>
					<input name="user_site_city" id="user_site_city" defaultsel="" type="hidden" value="" />
				</td>
			</span>
  		  	</tr>
  		  </li>
  		  <li>
				<strong>默认：</strong>
				<span>
				<div class="sub-input"><a class="sia-2 selhover" id="sl-sg_default" href="javascript:void(0);">非默认</a></div>
				<input name="sg_default" id="sg_default" defaultsel="默认[0]|非默认[1]" type="hidden" value="1" />
			</span>
  		  </li>
				<li id="orAgent" >
					<strong>是否一级代理：</strong>
					<span>
						<input type="radio" value="" name="oneAgents"  checked="checked" style="width:20px;height:14px;"/>否
						&nbsp;
						<input type="radio" value="1" name="oneAgents" style="width:20px;height:14px;"/>是
					</span>
					
				</li>
			 <li  >
			 <strong>&nbsp;</strong>
				<font style="color:red">一级代理仅用于管理员角色</font>
			</li>
  		  </ul>
   </div>
   <div class="field-item_button_m">
   <input name="prole" type="hidden" value="${prole}" />
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="取消" id="checkCode" onClick="javascript:history.go(-1)" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>