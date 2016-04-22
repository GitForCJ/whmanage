<%@ page contentType="text/html; charset=GBK" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:useBean id="userSession" scope="session" class="com.wlt.webm.rights.form.SysUserForm" />
<jsp:useBean id="role" scope="page" class="com.wlt.webm.rights.bean.SysRole"/>
 <%
 	 String path = request.getContextPath();
 	 String roleId = request.getParameter("rid");
 	 request.setAttribute("roleInfo", role.getSysRoleInfo(roleId));
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
});
function check(){
  	with(document.forms[0]){
		if(rolename.value == ""){
		alert("请输入角色名称!");
		return;
		}
		if(sg_default.value == '0'){
			if(pid.value != '' && pid.value != '1'){
			alert("角色不能修改为默认!");
			return;
			}
		}
		action="sysrole.do?method=update";
		target="_self";
		submit();
	}
}
</SCRIPT>
</head>
<body>
<div id="popdiv"><span id="sellist"></span><div style="clear:both"></div></div>
<input type="hidden" id="rootPath" value="<%=path %>"/>
<input type="hidden" id="pid" name="pid" value="${roleInfo.prole }"/>
<html:form  method="post" action="/rights/sysrole.do?method=update">
<input type="hidden" name="roleid" value="<%=roleId %>"/>
<input type="hidden" name="wltarea" value="${roleInfo.wltarea }"/>
<div class="pass_main">
	<div class="pass_title_1">角色更新</div>
    <div class="pass_list">
    	<ul>
            <li><strong>角色名称：</strong><span><input name="rolename" type="text" value="${roleInfo.rolename }"/></span></li>
            <li>
				<strong>默认:</strong>
				<span>
					<div class="sub-input"><a class="sia-2 selhover" id="sl-sg_default" href="javascript:void(0);">非默认</a></div>
					<input nname="nname" name="sg_default" id="sg_default" defaultsel="默认[0]|非默认[1]" type="hidden" value="1" />
			</span>
  		  </li>
        </ul>
   </div>
   <div class="field-item_button_m">
   <input type="button" value="确认" id="checkCode" onclick="check()" class="field-item_button" />
   <input type="button" value="返回" id="btnBack" onclick="javascript:history.go(-1);" class="field-item_button" />
   </div>
</div>
</html:form>
</body>
</html>